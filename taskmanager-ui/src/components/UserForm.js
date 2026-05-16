import React from "react";

function UserForm({ userForm, setUserForm, handleSubmit, error,setError }) {
 
  return (
    <div>
      

      <input
        type="text"
        placeholder="Enter First Name"
        value={userForm.firstName}
        onChange={(e) => {
          setUserForm({ ...userForm, firstName: e.target.value });

          // ✅ clear error while typing
          if (error)setError("");
        }}
        
        className={`input ${error ? "input-error" : ""}`}
      />
      {/* 🔹 Inline error message */}
      {error && <p className="error-text">{error}</p>}

      <input
        placeholder="Last Name"
        value={userForm.lastName}
        onChange={(e) =>
          setUserForm({ ...userForm, lastName: e.target.value })
        }
      />
      <input
        placeholder="UserName"
        value={userForm.usertName}
        onChange={(e) =>
          setUserForm({ ...userForm, userName: e.target.value })
        }
      />

      <input
      placeholder="Password"
        value={userForm.password}
        onChange={(e) =>
          setUserForm({ ...userForm, password: e.target.value })
        }
      />

      

      <br />

      <button onClick={handleSubmit}>Submit</button>   
          </div>
  );
}

export default UserForm;