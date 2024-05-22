import { useEffect } from "react";
import { useContext } from "react"
import { createContext } from "react"
import { env } from "../env";

const firebaseConfig = {
    apiKey: "AIzaSyDkDq-WYmSUmQbS_mw9Bs5vn8wKct16-tc",
    authDomain: "infnet-cotation-frontend.firebaseapp.com",
    projectId: "infnet-cotation-frontend",
    storageBucket: "infnet-cotation-frontend.appspot.com",
    messagingSenderId: "147984858231",
    appId: "1:147984858231:web:fa3486365c2f2bb0a3da72",
    measurementId: "G-S6SCX2L9QV"
}

const AuthContext = createContext({});
export const AuthProvider = ({ children }) => {

    useEffect(() => {
    }, []);

    return <AuthContext.Provider value={{ name: "churros" }}>{children}</AuthContext.Provider>
}

const useAuth = () => {
    const context = useContext(AuthContext);

    if (!context) {
        throw new Error("useAuth must be wrapped inside an AuthProvider");
    }

    return context;
}

export {
    useAuth,
    AuthContext
}
