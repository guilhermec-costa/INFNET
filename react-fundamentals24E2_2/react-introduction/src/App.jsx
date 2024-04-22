import { Route, Routes } from 'react-router-dom'
import './App.css'
import Furnitures from './pages/furnitures/Furnitures';
import Layout from './Layout';
import { RandomContextProvider } from './contexts/RandomContext';

function App() {
    return (
        <Routes>
            <Route path='/' element={<Layout />}>
                <Route path='/furniture1' element={<Furnitures />} />
                <Route path='/furniture2' element={<Furnitures />} />
                <Route path='/furniture3' element={<Furnitures />} />
            </Route>
        </Routes>
    );
}

export default App
