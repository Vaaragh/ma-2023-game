import { createServer } from "http";
import { Server } from "socket.io";
import { initializeApp } from "firebase/app";
import { getFirestore, collection, getDocs } from "firebase/firestore";

const httpServer = createServer();
const io = new Server(httpServer, {
  cors: {
    origin: "*",
  },
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




const PORT = 3000;
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
