import React from "react";
import { FaStar } from "react-icons/fa";

const Star = ({ filled, setDinamicActiveStars, starIndex }) => {

    const handleStarClick = (starIndex) => {
        setDinamicActiveStars(starIndex + 1)
    }
    return <FaStar fill={filled ? "red" : "grey"} style={{ cursor: "pointer" }}
        onClick={() => handleStarClick(starIndex)} fontSize={50} />
}

export default Star;
