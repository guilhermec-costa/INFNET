import './App.css'
import MutableVote from './components/MutableVote'
import StaticVote from './components/StaticVote'

function App() {
    return (
        <>
            <StaticVote totalStars={5} votedStars={3}/>    
            <StaticVote totalStars={7} votedStars={2}/>    
            <StaticVote totalStars={10} votedStars={5}/>    
            <MutableVote totalVotes={5} votedVotes={3}/>
        </>
    ) 
}

export default App
