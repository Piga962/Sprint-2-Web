import { useState } from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import SignIn from "./components/SignIn";
import "./App.css";
import Ticket from "./components/Task";
import Dashboard from "./components/Dashboard";
import ManagerDashboard from "./components/ManagerDashboard";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<SignIn />} />
        <Route path="/dashboard" element={<Dashboard />} />
        <Route path="/manager_dashboard" element={<ManagerDashboard />} />
      </Routes>
    </Router>
  );
}

export default App;
