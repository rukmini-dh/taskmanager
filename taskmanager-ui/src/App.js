import { Routes, Route } from "react-router-dom";
import { useState } from "react";
import MainLayout from "./layout/MainLayout";
import Dashboard from "./pages/Dashboard";
import Tasks from "./pages/Tasks";
import Settings from "./pages/Settings";
import Registration from "./pages/Registration";
import Login from "./pages/Login";

function App() {
  const [loggedIn, setLoggedIn] = useState(false);
  return (
    <MainLayout>
    
    
   
      <Routes>
        <Route path="/Dashboard" element={<Dashboard />} />
        <Route path="/register" element={<Registration />} />
        <Route path="/login" element={<Login />} />
        <Route path="/tasks" element={<Tasks />} />
        <Route path="/settings" element={<Settings />} />
        
      </Routes>
     
    </MainLayout>
  );
}

export default App;