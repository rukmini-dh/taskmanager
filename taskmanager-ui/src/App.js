import { Routes, Route } from "react-router-dom";
import { useState } from "react";
import MainLayout from "./layout/MainLayout";
import Dashboard from "./pages/Dashboard";
import Tasks from "./pages/Tasks";
import Settings from "./pages/Settings";
import Registration from "./pages/Registration";
import Login from "./pages/Login";
import { Navigate } from "react-router-dom";
import ProtectedRoute from "./components/ProtectedRoute";

function App() {
  const [loggedIn, setLoggedIn] = useState(false);
  
  console.log("LOADING NEW APP.JS VERSION 2");
  return (
    <MainLayout>
    
   
   
      <Routes>
      <Route
  path="/dashboard"
  element={
    <ProtectedRoute>
      <Dashboard />
    </ProtectedRoute>
  }
/>

<Route
  path="/settings"
  element={
    <ProtectedRoute>
      <Settings />
    </ProtectedRoute>
  }
/>
        <Route path="/register" element={<Registration />} />
        <Route path="/login" element={<Login />} />
        <Route
  path="/"
  element={<Navigate to="/login" />}
/>
        <Route
  path="/tasks"
  element={
    <ProtectedRoute>
      <Tasks />
    </ProtectedRoute>
  }
/>
        
        
      </Routes>
     
    </MainLayout>
  );
}

export default App;