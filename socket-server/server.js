import { createServer } from "http";
import { Server } from "socket.io";
import { initializeApp } from "firebase/app";
import { getFirestore, collection, getDocs } from "firebase/firestore";
import { randomUUID } from "crypto";

const httpServer = createServer();
const io = new Server(httpServer, {
  cors: {
    origin: "*",
  },
});

const PORT = 3001;
httpServer.listen(PORT, () => {
  console.log(`Server listening on port ${PORT}`);
  getAssociationData()
    .then((data) => {
      console.log("Association Data:", data);
    })
    .catch((error) => {
      console.error("Error fetching Association Data:", error);
    });
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

const playerQueue = [];
const match = {};
io.on("connection", (socket) => {
  console.log("connected")

  
  socket.on("connect_error", (err) => {
    console.log(`connect_error due to ${err.message}`);
  });

  socket.on("ping", (val) => {
    socket.emit("pong", "pong")
  })

  socket.on("join", (val) => {
    console.log(playerQueue.length);

    playerQueue.push({socket, ...val});
    if (playerQueue.length >=2) {
      matchId = randomUUID().toString();
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
        round : 1
      }
      match[matchId] = matchData;
      p1.socket.join(matchId);
      p2.socket.join(matchId);
      io.to(matchId).emit("startGame", matchData);
      console.log("Started")

    }

  })
})



