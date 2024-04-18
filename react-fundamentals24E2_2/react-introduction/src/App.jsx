import './App.css'
import Vote from './components/Vote'

function App() {
    return (
        <>
            <StaticVote totalStars={5} votedStars={3}/>    
            <StaticVote totalStars={7} votedStars={2}/>    
            <StaticVote totalStars={10} votedStars={5}/>    
        </>
    ) 
}

export default App
