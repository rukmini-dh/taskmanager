const BASE_URL = "http://localhost:8080/auth";

// 🔹 GET all tasks
export const getUsers = async () => {
  const response = await fetch(BASE_URL);

  if (!response.ok) {
    throw new Error("Failed to fetch Users");
  }

  return response.json();
};

// 🔹 ADD task
export const addUser = async (user) => {
    console.log("****adding");
  const response = await fetch("http://localhost:8080/auth/register", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(user)
  });

  if (!response.ok) {
    throw new Error("Failed to add user");
  }

  return response.json();
};
export const verifyUser = async (user) => {
  console.log("****verifying");
const response = await fetch("http://localhost:8080/auth/signin", {
  method: "POST",
  headers: {
    "Content-Type": "application/json"
  },
  body: JSON.stringify(user)
});
console.log("*****",user);
if (!response.ok) {
  throw new Error("User not found! ");
}

return response.json();
};
//fetch(`${BASE_URL}/${id}`
// 🔹 UPDATE task
export const updateUser = async (id, user) => {
  console.log(" in updateuserk******* ",user.id) ;
  const response = await fetch(`${BASE_URL}/${id}`, {
    method: "PUT",
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
    method: "DELETE"
  });

  if (!response.ok) {
    throw new Error("Failed to delete user");
  }
};