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
        for(let i=0; i<starIndex; i++) {
            if(i <= starIndex) {
                console.log(stars[i].props.fill = "red");
            }
        }
    }

    const getStarsIcon = useCallback(() => {
        let stars = [];
        for(let i=1; i<=totalVotes; ++i) {
                stars.push(
                    <FaStar key={i} fill="grey" size={30} onMouseEnter={() => applyRangeOfVotes(i)} />
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


