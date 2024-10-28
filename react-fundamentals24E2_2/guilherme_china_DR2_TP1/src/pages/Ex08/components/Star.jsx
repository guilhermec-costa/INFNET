import React from "react";
import { FaStar } from "react-icons/fa";

const Star = ({ filled }) => {
    return filled ? <FaStar fill="red" fontSize={50} /> :
        <FaStar fill="grey" fontSize={50} />
}

export default Star;
