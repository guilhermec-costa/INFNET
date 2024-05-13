import { Route, Routes } from 'react-router-dom'
import './App.css'
import Layout from './Layout';
import { lazy } from 'react';

const Home = lazy(() => import("./pages/Home"))

function App() {
    return (
        <Routes>
            <Route path='/' element={<Layout />}>
                <Route path='/home' element={<Home />} />
            </Route>
        </Routes>
    );
}

export default App
