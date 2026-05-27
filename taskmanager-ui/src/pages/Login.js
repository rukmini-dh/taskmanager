import React,{useState,useEffect} from "react"; 
import {useAuth} from "../hooks/useAuth";
import LoginForm from "../components/LoginForm";
import Tasks from "./Tasks";
import { useNavigate } from "react-router-dom";
import { useAuthContext } from "../context/AuthContext";
import { getUsers, verifyUser } from "../services/authService";
function Login() {
  //initialising variables
  const [error, setError] = useState("");
  const [message, setMessage] = useState("");
  const [loggedIn, setLoggedIn] = useState(false);
  const { users, editUser, deleteUser, addUser} = useAuth();
  const {reloadUser} = useAuthContext();
  const navigate = useNavigate();
  const [loginForm, setLoginForm] = useState({
    password:"",
    userName:""
  });

  // 🔹 Verfy  User
    const handleSubmit = async () => {
      console.log("submitted");
      
    if (!loginForm.userName.trim()) 
    {setError("Please enter your username");
      return;}
      setError("");
      if (!loginForm.password.trim()) 
        {setError("Please enter your password");
          return;}
          setError("");
      console.log("verifying",loginForm);
       const result= await verifyUser(loginForm);
       setMessage(result.message);
       resetForm();
      console.log(result);
      localStorage.clear();   
      if(result.success){

        localStorage.setItem("role", result.role);
       localStorage.setItem("userName", result.userName);
       // console.log(" user name in local :",localStorage.getItem("userName"));
       // console.log(" user name from result :",result.userName);
       await reloadUser();
        navigate("/tasks");
     }
     else{
        setMessage(result.message);
     }
    
  };
 
  
  // 🔹 Reset form
  const resetForm = () => {
    setLoginForm({
      password:"",userName:""
    });
    
  };

    
  return (
    <div className="container">
     
  
      <div className="User">
        <LoginForm
          loginForm={loginForm}
          setLoginForm={setLoginForm}
          handleSubmit={handleSubmit}
          error={error}
          setError={setError}
        />
        {message && (
   <p className="login-message">
      {message}
   </p>
)}
      </div>
   
   </div>
   
  );
}
  
  export default Login;