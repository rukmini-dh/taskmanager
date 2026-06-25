const BASE_URL = "http://localhost:8080/auth";

// 🔹 GET all tasks
export const getUsers = async () => {
  const response = await fetch(BASE_URL,
  {
    method: "GET", credentials: "include"
  });
  if (!response.ok) {
    throw new Error("Failed to fetch Users");
  }

  return response.json();
};

// 🔹 ADD task
export const addUser = async (user) => {
    console.log("****adding");
  const response = await fetch("http://localhost:8080/auth/register", {
    method: "POST", credentials:"include",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(user)
  });

  const data = await response.json();

if (!response.ok) {
  console.log("DATA",data);
   throw new Error(data.message);
}

return data;

  
};
export const verifyUser = async (user) => {
  console.log("****verifying",user);
const response = await fetch("http://localhost:8080/auth/signin", {
  method: "POST",credentials:"include",
  headers: {
    "Content-Type": "application/json"
  },
  body: JSON.stringify(user)
});

if (!response.ok) {
  throw new Error("User not found! ");
}

return response.json();
};
export const logoutUser = async () => {

  const response = await fetch(
    "http://localhost:8080/auth/logout",
    {
      method: "POST",
      credentials: "include"
    }
  );
  
  if (!response.ok) {
    throw new Error("Logout failed");
  }

  return response.text();
};
export const getCurrentUser = async () => {
  const token = localStorage.getItem("token");
  console.log("TOKEN FROM STORAGE", token);

  const response = await fetch(
    "http://localhost:8080/auth/me",
    {
      method: "GET",
      headers: { Authorization: `Bearer ${token}`
    }
});

  if (!response.ok) {
    throw new Error("Not authenticated");
  }
   console.log("in current user", response)
  return response.json();
};
// forgot password
export const forgotPassword=async(username) =>{
  console.log("in authservic",username)
  const response= await fetch(`${BASE_URL}/forgotPassword/${username}`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json"
    }
  });

  if (!response.ok) {
    console.group("not ok in auth servuce");
    const errorMessage = await response.text();
     throw new Error(errorMessage);
     console.group("not ok in auth servuce",errorMessage);
     return await response.json();
}else{
  console.log("all good in uauth Service",response);
  return response.json
}

}
 
// Change Password
export const changePassword=async(newPassword,currentPassword,username)=>{
  const response = await fetch(`${BASE_URL}/changePassword/${username}`, {
    method: "PUT",credentials:"include",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      currentPassword,
      newPassword
  })
  });
  if (!response.ok) {
    const errorMessage = await response.text();
     throw new Error(errorMessage);
     return await response.json();
}

}
//fetch(`${BASE_URL}/${id}`
// 🔹 UPDATE task
export const updateUser = async (id, user) => {
  console.log(" in updateuserk******* ",user.id) ;
  const response = await fetch(`${BASE_URL}/${id}`, {
    method: "PUT",credentials:"include",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(user)
  });

  if (!response.ok) {
    console.log("did not update") ;
    throw new Error("Failed to update user");
  }
  //onsole.log(response.json());
  console.log("JSON.stringify(task)",JSON.stringify(user));
  return response.json();
};

// 🔹 DELETE task
export const deleteUser = async (id) => {
  const response = await fetch(`${BASE_URL}/${id}`, {
    method: "DELETE",credentials:"include"
  });

  if (!response.ok) {
    throw new Error("Failed to delete user");
  }
};