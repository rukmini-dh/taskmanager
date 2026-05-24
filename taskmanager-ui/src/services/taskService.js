const BASE_URL = "http://localhost:8080/tasks";

// 🔹 GET all tasks
export const getTasks = async () => {
  const response = await fetch(`${BASE_URL}/all`,{method:"GET",credentials:"include"});
  console.log("in taxservice  as ADMIN",response );
  if (!response.ok) {
    throw new Error("Failed to fetch tasks");
  }

  return response.json();
};
export const fetchTasks = async () => {

  const role = localStorage.getItem("role");
  const userName = localStorage.getItem("userName");

  let url = "";

  if(role === "ADMIN"){
     url = "http://localhost:8080/tasks";
  }
  else{
     url = `http://localhost:8080/tasks/user/${userName}`;
  }

  const response = await fetch(url,{method:"GET",credentials:"include"});

  return response.json();
};
export const getTasksByUser = async (userName) => {

  

  const response = await fetch(
    `${BASE_URL}/user/${userName}`,
    {
      method: "GET",
      credentials: "include"
    }
  );
  console.log("in taxservice  as GUEST",response );
  if (!response.ok) {
    throw new Error("Failed to fetch tasks");
  }

  return await response.json();
};
// 🔹 ADD task
export const addTask = async (task) => {
  console.log("in add task",task);
  const response = await fetch(BASE_URL, {
    method: "POST",credentials: "include",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(task)
  });

  if (!response.ok) {
    throw new Error("Failed to add task");
  }

  return response.json();
};
//fetch(`${BASE_URL}/${id}`
// 🔹 UPDATE task
export const updateTask = async (id, task) => {
  console.log(" in updatetask******* ",task.id) ;
  const response = await fetch(`${BASE_URL}/${id}`, {
    method: "PUT",credentials:"include",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(task)
  });

  if (!response.ok) {
    console.log("did not update") ;
    throw new Error("Failed to update task");
  }
  //onsole.log(response.json());
  console.log("JSON.stringify(task)",JSON.stringify(task));
  return response.json();
};

// 🔹 DELETE task
export const deleteTask = async (id) => {
  const response = await fetch(`${BASE_URL}/${id}`, {
    method: "DELETE",credentials:"include",
  });

  if (!response.ok) {
    throw new Error("Failed to delete task");
  }
};