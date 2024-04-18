import { FaStar } from "react-icons/fa";
export default function StaticVote({ totalStars, votedStars }) 
{   
    const stars = [];
    for(let i=1; i<=totalStars; ++i) {
        if( i <= votedStars) {
            stars.push(<FaStar key={i} fill="orange" size={30}/>);
        } else {
            stars.push(<FaStar key={i} fill="blue" size={30}/>);
        }
    }

    return (
        <div>{stars}</div>
    );
}

