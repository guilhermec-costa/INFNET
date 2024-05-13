// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyDkDq-WYmSUmQbS_mw9Bs5vn8wKct16-tc",
  authDomain: "infnet-cotation-frontend.firebaseapp.com",
  projectId: "infnet-cotation-frontend",
  storageBucket: "infnet-cotation-frontend.appspot.com",
  messagingSenderId: "147984858231",
  appId: "1:147984858231:web:fa3486365c2f2bb0a3da72",
  measurementId: "G-S6SCX2L9QV"
};

// Initialize Firebase
export const app = initializeApp(firebaseConfig);
export const analytics = getAnalytics(app);

