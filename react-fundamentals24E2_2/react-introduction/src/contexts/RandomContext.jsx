import React from "react"
import { useContext } from "react";
import { createContext } from "react";

const RandomContext = createContext({ name: "Churros", _class: "Dog"});

function RandomContextProvider({props}) {
    const favoriteDog = {
        name: "Churros",
        _class: "Dog"
    }

    return (
        <RandomContext.Provider value={favoriteDog}>{props?.children}</RandomContext.Provider>
    )
}

export default function useRandomContext() {
    const randomContext = useContext(RandomContext);
    if(!randomContext) console.log("Context not found");

    return randomContext;
}
