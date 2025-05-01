import { useState } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import SignIn from "./components/SignIn";
import "./App.css";
import Ticket from "./components/Task";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<SignIn />} />
        <Route path="/dashboard" element={<Ticket />} />
      </Routes>
    </Router>
  );
}

export default App;
