import { useState, useEffect } from "react";
import {
    getUsers,
    addUser  as apiAddUser,
    updateUser as apiUpdateUser,verifyUser as apiVerifyUser
  } from "../services/authService";
  export const useAuth = () => {
    const [users, setUsers] = useState([]);

    // 🔹 Fetch all users (single source of truth)
      const fetchUsers = async () => {
        try {
          const data = await getUsers();
          setUsers(data);
        } catch (err) {
          console.error("Failed to fetch users", err);
        }
      };

  // 🔹 Initial load
  useEffect(() => {
    fetchUsers();
  }, []);

  // 🔹 Add Users
  const addUser = async (user) => {
    try {
      await apiAddUser(user);
      //fetchUsers(); // ✅ always sync from backend
    } catch (err) {
      console.error("Add failed", err);
    }
  };
  const verifyUser = async (user) => {
    try {
      await apiVerifyUser(user);
      //fetchUsers(); // ✅ always sync from backend
    } catch (err) {
      console.error("Login failed", err);
    }
  };

  // 🔹 Edit User (firstName, etc .)
  const editUser = async (id, updatedUser) => {
    try {
           await apiUpdateUser(id, updatedUser);
           //fetchUsers(); // ✅ refresh from backend
    } catch (err) {
      console.error("Edit failed", err);
    } 
   
  };

  
  return {
    users,
    addUser,
    verifyUser,
    editUser,
    fetchUsers
  };
};


   