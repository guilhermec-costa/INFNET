import { FaRegStar } from "react-icons/fa";
export default function Vote({ totalStars, votedStars }) 
{   const stars = [];
    for(let i=0; i<totalStars; ++i) {
        stars.push(<FaRegStar key={i} style={{ background: "red"}}/>);
    }

    return (
        <div>{stars}</div>
    );
}

