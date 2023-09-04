import { createServer } from "http";
import { Server } from "socket.io";
import { initializeApp } from "firebase/app";
import { getFirestore, collection, getDocs } from "firebase/firestore";
import { randomInt, randomUUID } from "crypto";

const httpServer = createServer();
const io = new Server(httpServer, {
  cors: {
    origin: "*",
  },
});

const PORT = 3000;
httpServer.listen(PORT, () => {
  console.log(`Server listening on port ${PORT}`);
  getMastermindData()
    .then((data) => {
      console.log("Association Data:", data);
    })
    .catch((error) => {
      console.error("Error fetching Association Data:", error);
    });
  showSiz()
  
});


const firebaseConfig = {
  projectId: "slagalica-0",
  appId: "1:1045352774037:android:adecd474e04e46b1c75e9c",
};
const app = initializeApp(firebaseConfig);
const db = getFirestore();

const getCollectionData = async (name) => {
  const querySnapshot = await getDocs(collection(db, name));
  return querySnapshot.docs.map((doc) => doc.data());
};

const getAssociationData = async () => {
  const collectionData = await getCollectionData("association");
  return collectionData;
};

const getQuestionData = async () => {
  const collectionData = await getCollectionData("question");
  return collectionData;
}

const getMastermindData = async () => {
  // const collectionData = [randomInt(0,6), randomInt(0,6), randomInt(0,6), randomInt(0,6)]
  const collectionData = [1,2,3,4]
  return collectionData;
}

const showSiz = async () => {
  console.log(playerQueue.length)
  await sleep(5000)
  showSiz()  
}
const sleep = async (milliseconds) => {
  await new Promise(resolve => {
      return setTimeout(resolve, milliseconds)
  });
};

const playerQueue = [];
const match = {};
io.on('connection', (socket) => {
  console.log("connected")

  socket.on("ping", (val) => {
    socket.emit("pong", "pong")
  })

  socket.on("join", (val) => {
    console.log(val);

    playerQueue.push({socket, ...val});
    if (playerQueue.length >=2) {
      const matchId = randomUUID().toString();
      const p2 = playerQueue.pop()
      const p1 = playerQueue.pop()
      const {socket, ...player1} = p1;
      const {socket:_, ...player2} = p2;
      const matchData = {
        id : matchId,
        p1 : player1,
        p2 : player2,
        turn : player1.id,
        p1Points : 0,
        p2Points : 0,
        activeGame : 0,
        round : 1,
        questionNum:0,
        openFields: [],
        mastermindInfo: {

          masterCombo: [],
          fullMatch : 0,
          partialMatch: 0,
          activePlayer: null
        },
        questionRes: {
          
          p1:{
            time : null,
            correct : null,
          },
          p2:{
            time:null,
            correct: null
          }
        }

      }
      match[matchId] = matchData;
      p1.socket.join(matchId);
      p2.socket.join(matchId);
      io.to(matchId).emit("startGame", matchData);
      console.log("Started")

    }

  })

  socket.on("startMastermind", ({matchId}) => {
    getMastermindData().then((data) => {
      const ma = match[matchId]
      ma.masterCombo = data
      ma.mastermindInfo.activePlayer = ma.p1
      io.to(matchId).emit("mastermindList", {...ma});
      console.log(data);
      socket.on("answerMastermind", ({answer, counter}) => {
        //TODO
        // check the answer against combo
        
      io.to(matchId).emit("pegUpdate", {...ma})
      })
    })
  })

  socket.on("startAssociation", ({matchId}) => {
    getAssociationData().then((data) => {
      io.to(matchId).emit("associationList", {data});
      console.log(data)
      socket.on("associationField", ({some}) => {
        //TODO
        //add some to match.openFields array
      io.to(matchId).emit("fieldUpdate", {...ma})
      })
      socket.on("answerAssociation", ({someOther}) => {
        //TODO
        //check answer, update points....
        //make a list of correct inputs...
      io.to(matchId).emit("inputsUpdate", {...ma})
      })
    })
  })

  socket.on("startQuestion", ({matchId}) => {
    getQuestionData().then((data) => {
      io.to(matchId).emit("questionList", {data});
      console.log(data)
      socket.on("answerQuestion", ({matchId, player, time, correct}) => {
      const ma = match[matchId]
      const p1Res = ma.questionRes.p1
      const p2Res = ma.questionRes.p2

      const playerCheck = player === ma.p1.id;

      const currentPlayerResults = playerCheck
          ? p1Res
          : p2Res;

        currentPlayerResults.time = time;
        currentPlayerResults.correct = correct;

        if (p1Res.time !== null && p2Res.time !== null) {
          const bothCorrect =
            p1Res.correct && p2Res.correct;
          const player1Faster = p1Res.time < p2Res.time;

          if (bothCorrect) {
            if (player1Faster) {
              ma.p1Points += 10;
            } else {
              ma.p2Points += 10;
            }
          } else {
            if (p1Res.correct) {
              ma.p1Points += 10;
              ma.p2Points -= 5;
            } else if (p2Res.correct) {
              ma.p2Points += 10;
              ma.p1Points -= 5;
            } else {
              ma.p1Points -= 5;
              ma.p2Points -= 5;
            }
          }
          console.log(ma)
          ma.questionNum +=1;
          io.to(matchId).emit("questionUpdate", {...ma})
            console.log("update")
            console.log(ma)


          p1Res.time=null
          p2Res.time=null
          p1Res.correct=null
          p2Res.correct=null
         



        
      }})
    })
  })
})



