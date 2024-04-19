import React from "react";
import { useCallback } from "react";
import { useEffect } from "react";
import { useState } from "react";
import { FaStar } from "react-icons/fa";

export default function MutableVote(
    { totalVotes, votedVotes }
) {
    const [stars, setStars] = useState([]);
    function applyRangeOfVotes(starIndex)  {
        let array = [];
        for(let i=0; i<=starIndex; i++) {
            if(i <= starIndex) {
                array[i] = stars[i].props.style.color = "red";
            }
        }
        setStars(array);
    }

    const getStarsIcon = useCallback(() => {
        let stars = [];
        for(let i=0; i<totalVotes; ++i) {
                stars.push(
                    <FaStar key={i} size={30} onMouseEnter={() => applyRangeOfVotes(i)} style={{ color: "grey"}}/>
                );
        }
        return stars;

        }, [totalVotes]
    );

    useEffect(() => {
        setStars(getStarsIcon());
    }, [getStarsIcon])

    

    return (
        <div>{stars}</div>
    );
}


