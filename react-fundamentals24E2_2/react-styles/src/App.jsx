import { useState } from 'react'
import './App.css'
import StyledComponentTest from './StyledComponentTest'
import TestComponent from './testComponent'
import { Button } from 'react-bootstrap'
import { Button as MaterialButton, Input } from '@mui/material'
import { useRef } from 'react'

function App() {
    const [user, setUser] = useState("");
    const userName = useRef(null);

    const handleUsernameChange = () => {
        console.log(userName.current.value)
    }
    return (
        <div>
            <StyledComponentTest buttonProps={{ variant: "success", value: "Warning" }} user={user} />
            <TestComponent />
            <div style={{ background: "red" }}>Hello world</div>
            <MaterialButton onClick={handleUsernameChange}>Change Name</MaterialButton>
            <Input type="text" placeholder='Digite seu nome' ref={userName} />
        </div>
    )
}

export default App
