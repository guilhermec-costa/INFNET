import { getAuth } from "firebase/auth";
import { initializeApp } from "firebase/app";
import { getFirestore } from "firebase/firestore";

export const firebaseConfig = {
  apiKey: "AIzaSyDkDq-WYmSUmQbS_mw9Bs5vn8wKct16-tc",
  authDomain: "infnet-cotation-frontend.firebaseapp.com",
  projectId: "infnet-cotation-frontend",
  storageBucket: "infnet-cotation-frontend.appspot.com",
  messagingSenderId: "147984858231",
  appId: "1:147984858231:web:fa3486365c2f2bb0a3da72",
  measurementId: "G-S6SCX2L9QV",
};

const app = initializeApp(firebaseConfig);
const auth = getAuth();
const db = getFirestore(app);

export { app, auth, db };
