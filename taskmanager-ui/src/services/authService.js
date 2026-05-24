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