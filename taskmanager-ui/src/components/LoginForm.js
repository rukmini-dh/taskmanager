import React from "react";

function LoginForm({ loginForm, setLoginForm, handleSubmit, error,setError }) {
 
  return (
    <div>
      <h2>Signin</h2>

      <input
        type="text"
        placeholder="Enter User Name"
        value={loginForm.userName}
        onChange={(e) => {
          setLoginForm({ ...loginForm, userName: e.target.value });

          // ✅ clear error while typing
          if (error)setError("");
        }}
        
   />
      <input
      placeholder="Password"
        value={loginForm.password}
        onChange={(e) =>
          setLoginForm({ ...loginForm, password: e.target.value })
        }
      />

      

      <br />

      <button onClick={handleSubmit}>Submit</button>   
          </div>
  );
}

export default LoginForm;