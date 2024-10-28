import React, { useEffect, useState } from "react";
import "./style.css"
import Star from "./components/Star";

/* Crie um componente básico que exiba até 5 estrelas cinzas e algumas vermelhas para representar uma nota dada a alguma coisa, como filme, livro ou receita culinária. O componente deve ser estático. */
const Ex08 = ({ activeStars, totalStars }) => {
    const getStars = () => {
        const components = [];
        for (let i = 0; i < totalStars; i++) {
            components.push(<Star key={i} filled={i < activeStars} />);
        }
        return components
    };

    return (
        <div>{getStars().map(star => star)}</div>
    )
}

export default Ex08;
