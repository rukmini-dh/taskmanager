import React from "react";
import "./layout.css";

const MainLayout = ({ children }) => {
  return (
    <div className="dashboard">
      
      {/* Sidebar */}
      <div className="sidebar">
        <h2>📌 Task Manager</h2>
        <ul>
          <li>Dashboard</li>
          <li>Tasks</li>
          <li>Settings</li>
        </ul>
      </div>

      {/* Main section */}
      <div className="main">
        
        {/* Top bar */}
        <div className="topbar">
          <input placeholder="Search tasks..." />
          <div className="profile">👤 User</div>
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