import React, { useState,useEffect } from "react";
import "./layout.css";
import { Link, Navigate } from "react-router-dom";
import { FaHome, FaTasks, FaCog } from "react-icons/fa";
import { NavLink } from "react-router-dom";
import { logoutUser,getCurrentUser } from "../services/authService";
import { useNavigate } from "react-router-dom";
import { useLocation } from "react-router-dom";



const MainLayout = ({ children }) => {
  const [isSidebarOpen, setIsSidebarOpen] = useState(true);
  const [currentUser, setCurrentUser] =    useState(null);
  const navigate = useNavigate();
  console.log("in mainlayout");
  
  const handleLogout = async () => {

    await logoutUser();
    setCurrentUser(null);
    localStorage.clear();
    navigate("/");
    };
    const location = useLocation();

const loadUser = async () => {

    try {

      const data =
          await getCurrentUser();

      setCurrentUser(data);

    } catch(error) {

      console.log("No active session");

    }
};

useEffect(() => {

  loadUser();

}, [location.pathname]);
  return (
    
    <div className="dashboard">

      {/* Sidebar */}
      <div className={`sidebar ${isSidebarOpen ? "" : "closed"}`}>
     {/*  <h2 className={`title ${isSidebarOpen ? "" : "hide"}`}>
  Task Manager
</h2> */}
        <ul>
          <li>
          <NavLink to="/Dashboard" title={!isSidebarOpen ? "Dashboard" : ""} 
           className={({ isActive }) => isActive ? "active-link" : ""}>
                <FaHome className="icon" />
      {isSidebarOpen && <span>Dashboard</span>}
            </NavLink>
          </li>

  <li>
 { <NavLink
  to="/tasks"
  title={!isSidebarOpen ? "Tasks" : ""}
  className={({ isActive }) => isActive ? "active-link" : ""}
>
  <FaTasks className="icon" />
  {isSidebarOpen && <span>Tasks</span>}
</NavLink>}
  </li>
  <li>
  <NavLink to="/register" title={!isSidebarOpen ? " Register" : ""}
  className={({ isActive }) => isActive ? "active-link" : ""}>
      
      {isSidebarOpen && <span>👤 Register</span>}
    </NavLink>
  </li>
  <li>
  <NavLink to="/login" title={!isSidebarOpen ? " Sign in" : ""}
  className={({ isActive }) => isActive ? "active-link" : ""}>
     
      {isSidebarOpen && <span>👤 Sign in</span>}
    </NavLink>
  </li>
  
  <li>
  <NavLink to="/settings" title={!isSidebarOpen ? "Settings" : ""}
  className={({ isActive }) => isActive ? "active-link" : ""}>
      <FaCog className="icon" />
      {isSidebarOpen && <span>Settings</span>}
    </NavLink>
  </li>
  
</ul>
      </div>

      {/* Main section */}
      

      <div className={`main ${isSidebarOpen ? "" : "full"}`}>

        {/* Top bar */}
        <div className="topbar">
          <div className="profile">Task Manager </div> 
            <div >  Welcome <span ><span>
  {currentUser?.userName}
</span></span></div>
          <button onClick={handleLogout}>   Logout</button>  
          {/* Toggle Button */}
          <button onClick={() => setIsSidebarOpen(!isSidebarOpen)}>
          {isSidebarOpen ? "Hide Menu" : "Show Menu"}
          </button>
        </div>

        {/* Page content */}
        <div className="content">
          {children}
        </div>

      </div>

    </div>
  );
};

export default MainLayout;