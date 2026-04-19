import React, { useState } from "react";
import "./layout.css";
import { Link } from "react-router-dom";
import { FaHome, FaTasks, FaCog } from "react-icons/fa";
import { NavLink } from "react-router-dom";


const MainLayout = ({ children }) => {
  const [isSidebarOpen, setIsSidebarOpen] = useState(true);

  return (
    
    <div className="dashboard">

      {/* Sidebar */}
      <div className={`sidebar ${isSidebarOpen ? "" : "closed"}`}>
      <h2 className={`title ${isSidebarOpen ? "" : "hide"}`}>
  Task Manager
</h2>
        <ul>
          <li>
          <NavLink to="/Dashboard" title={!isSidebarOpen ? "Dashboard" : ""} 
           className={({ isActive }) => isActive ? "active-link" : ""}>
                <FaHome className="icon" />
      {isSidebarOpen && <span>Dashboard</span>}
            </NavLink>
          </li>

  <li>
  <NavLink
  to="/tasks"
  title={!isSidebarOpen ? "Tasks" : ""}
  className={({ isActive }) => isActive ? "active-link" : ""}
>
  <FaTasks className="icon" />
  {isSidebarOpen && <span>Tasks</span>}
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
          <input placeholder="Search tasks..." />
          <div className="profile">👤 User</div>

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