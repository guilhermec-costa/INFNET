import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import HomePage from './pages/HomePage';
import FormPage from './pages/FormPage';
import ListPage from './pages/ListPage';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<LoginPage />} />
        <Route path="/home" element={<HomePage />}/>
        <Route path="/form" element={<FormPage />} />
        <Route path="/list" element={<ListPage />} />
      </Routes>
    </Router>
  );
}

export default App;
