import React from "react";
import useRandomContext from "../../contexts/RandomContext";

export default function Furnitures() {
    const randomContext = useRandomContext();
    console.log(randomContext);
    return (
        <div>Furnitures</div>
    );
}
