import { useRouteLoaderData } from "react-router-dom";
import { useAuthContext } from "../context/AuthContext";
import { useAuth} from "../hooks/useAuth";
import React,{useState,useEffect} from "react"; 

const Settings = () => {
  const { currentUser } = useAuthContext();
  const [changingPassword, setChangingPassword] =    useState(false);
  const {changePassword}=useAuth();
 const [newPassword,setNewPassword]=useState("");
 const [currentPassword,setCurrentPassword]=useState("");
 const [confirmPassword,setConfirmPassword]=useState("");
 const passwordsMatch =  newPassword === confirmPassword;
 const [error, setError] = useState("");
   

  if (!currentUser) {
    return (
      <div>
        <h2>Welcome to Settings</h2>
        <p>Please sign in to view your profile.</p>
      </div>
    );
  }
  const handleChangePassword= async () => {
    
    try {
      setError("");
      const res= await changePassword(newPassword,currentPassword,currentUser.userName); 
      console.log("after await",res);
      setError("");
  
  } catch(err) {
  
      setError(err.message);
      console.log ("in handl change password",err.message);
  
  }
      


       
    }
 
   
  

    return(
      <div >
           <h2>Settings Page</h2>
           <div className = "Profile">Profle  </div> 
           <p>User : {currentUser.userName}</p>
           <p>Role: {currentUser.role}</p>
          
           <button onClick={() => setChangingPassword(true)  }> Change Password</button>
          
           {changingPassword && (
            <div className ="Change Password">
               <input
          type="password" 
          placeholder="Current Password..."
          value={currentPassword}
          onChange={(e) => setCurrentPassword(e.target.value)}/>
                      
          <input
          type="password" 
          placeholder="New Password..."
          value={newPassword}
          onChange={(e) => setNewPassword(e.target.value)}/>
        
       <input
          type="password" 
          placeholder="Re enter the new password..."
          value={confirmPassword}
          onChange={(e) => setConfirmPassword(e.target.value)}/>
      
      {newPassword && confirmPassword && !passwordsMatch && (<p>Password not matching!</p>)}
 
      <button
    disabled={!passwordsMatch ||
              !newPassword ||
              !confirmPassword}
    onClick={handleChangePassword}>
    Confirm
</button>

      
        
        </div>   
        
     ) }
   
   {error && (
   <p className="error">
        {error}
    </p>
)}
    </div>
     );
    
  };
  
  export default Settings;