import { useState, useEffect } from "react";
import {
    getUsers,
    addUser  as apiAddUser,forgotPassword as apiforgotPassword,
    updateUser as apiUpdateUser,verifyUser as apiVerifyUser,changePassword as apichangePassword
  } from "../services/authService";
   
  export const useAuth = () => {
    const [users, setUsers] = useState([]);
    const [error, setError] = useState("");

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
      console.log("in use auth");
         setError(err.message);
     }
    
  };
  const verifyUser = async (user) => {
    console.log("login details in useAuth",user);
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
           //fetchUsers(); // ✅ And reloaduser fresh from backend
    } catch (err) {
      console.error("Edit failed", err);
    } 
   
  };
  const forgotPassword= async(username)=>{
    console.log("in forgot password",username);
    try{
  return await apiforgotPassword(username);
    } catch(err) {
      console.log("Forgot Password faled!");

    }

  }
  // 🔹 Change Password 
  const changePassword = async(newPassword,currentPassword,username) => {
     return await apichangePassword(newPassword,currentPassword,username);
           //fetchUsers(); // ✅ And reloaduser fresh from backend
  
   
  };

  
  return {
    users,
    addUser,
    verifyUser,
    editUser,
    fetchUsers,
    changePassword,
    error,
    forgotPassword
  };
};


   