import React, { useState } from "react";
import "./layout.css";
import { NavLink, useNavigate } from "react-router-dom";
import { FaHome, FaTasks, FaCog } from "react-icons/fa";
import { useAuthContext} from "../context/AuthContext";
import  {useAuth} from "../hooks/useAuth";


const MainLayout = ({ children }) => {

  const [isSidebarOpen, setIsSidebarOpen] = useState(true);

  const navigate = useNavigate();

  // ✅ AuthContext (single source of truth)
  const {
    currentUser,
    logout
  } = useAuthContext();
  

  console.log("in mainlayout");

  const handleLogout = async () => {
    await logout();        // backend + context clear
    navigate("/login");    // redirect
  };

  return (
    <div className="dashboard">

      {/* Sidebar */}
      <div className={`sidebar ${isSidebarOpen ? "" : "closed"}`}>

        <ul>

          <li>
            <NavLink
              to="/Dashboard"
              className={({ isActive }) =>
                isActive ? "active-link" : ""
              }
            >
              <FaHome className="icon" />
              {isSidebarOpen && <span>Dashboard</span>}
            </NavLink>
          </li>

          <li>
            <NavLink
              to="/tasks"
              className={({ isActive }) =>
                isActive ? "active-link" : ""
              }
            >
              <FaTasks className="icon" />
              {isSidebarOpen && <span>Tasks</span>}
            </NavLink>
          </li>

          <li>
            <NavLink to="/register"
              className={({ isActive }) =>
                isActive ? "active-link" : ""
              }
            >
              {isSidebarOpen && <span>👤 Register</span>}
            </NavLink>
          </li>

          <li>
            <NavLink to="/login"
              className={({ isActive }) =>
                isActive ? "active-link" : ""
              }
            >
              {isSidebarOpen && <span>👤 Sign in</span>}
            </NavLink>
          </li>

          <li>
            <NavLink
              to="/settings"
              className={({ isActive }) =>
                isActive ? "active-link" : ""
              }
            >
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

          <div className="profile">
            Task Manager
          </div>

          {/* ✅ Dynamic user from AuthContext */}
          <div>
            Welcome{" "}
            <span>
              {currentUser?.userName || "Guest"}
            </span>
          </div>

          {/* Logout */}
          <button onClick={handleLogout}>
            Logout
          </button>

          {/* Sidebar toggle */}
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