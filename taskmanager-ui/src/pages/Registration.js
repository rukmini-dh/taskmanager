import React,{useState,useEffect} from "react"; 
import {useAuth} from "../hooks/useAuth";
import UserForm from "../components/UserForm";
function Registration() {
  //initialising variables
  const [error, setError] = useState("");
  const { users, editUser, deleteUser, addUser} = useAuth();
  const [userForm, setUserForm] = useState({
    firstName: "",
    lastName:"",
    password:"",
    userName:""
  });

  // 🔹 Add  User
    const handleSubmit = async () => {
      console.log("submitted");
      
    if (!userForm.firstName.trim()) 
    {setError("Please enter your first name");
      return;}
      setError("");
      console.log("registring",userForm);
        await addUser({
        ...userForm     
      });
        resetForm();
  };
 
  

  // 🔹 Reset form
  const resetForm = () => {
    setUserForm({
      firstName:"",lastName:"",password:"",userName:""
    });
    
  };
    
  return (
    <div className="container">
      <h1>Register</h1>
  
      <div className="User">
        <UserForm
          userForm={userForm}
          setUserForm={setUserForm}
          handleSubmit={handleSubmit}
          error={error}
          setError={setError}
        />
      </div>
   </div>
  );
}
  
  export default Registration;