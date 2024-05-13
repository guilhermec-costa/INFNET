import React, { useEffect, useState } from "react";
import "./style.css"
import Star from "./components/Star";

/* Faça com que o componete anterior seja iterativo e permita ser clicado para selecionar quantas estrelas devem ficar vermelhas. */
const Ex09 = ({ activeStars, totalStars }) => {
    const [starsComponents, setStarsComponents] = useState([]);
    const [dinamicActiveStars, setDinamicActiveStars] = useState(activeStars)

    useEffect(() => {
        const getStars = () => {
            const components = [];
            for (let i = 0; i < totalStars; i++) {
                components.push(<Star key={i} filled={i < dinamicActiveStars} setDinamicActiveStars={setDinamicActiveStars} starIndex={i}/>);
            }
            setStarsComponents(components);
        };

        getStars();
    }, [dinamicActiveStars, totalStars]);

    return (
        <div>{starsComponents}</div>
    )
}

export default Ex09;
