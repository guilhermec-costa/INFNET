import React from "react"
import { useContext } from "react";
import { createContext } from "react";

export const RandomContext = createContext({ name: "Churros", _class: "Dog"});

export function RandomContextProvider({ children }) {
    const favoriteDog = {
        name: "Churros",
        _class: "Dog"
    }

    return (
        <RandomContext.Provider value={favoriteDog}>{ children }</RandomContext.Provider>
    )
}

export function useRandomContext() {
    const randomContext = useContext(RandomContext);
    if(!randomContext) console.log("Context not found");

    return randomContext;
}
