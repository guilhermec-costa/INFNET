import './App.css'
import StyledComponentTest from './StyledComponentTest'
import TestComponent from './testComponent'

function App() {
    return (
        <div>
            <StyledComponentTest />
            <TestComponent />
            <div style={{ background: "red" }}>Hello world</div>
        </div>
    )
}

export default App
