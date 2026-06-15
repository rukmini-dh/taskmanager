const BASE_URL = "http://localhost:8080/tasks";
const getAuthHeaders = () => {


  const token =
      localStorage.getItem("token");
  
  return {
      Authorization:
          `Bearer ${token}`,
  
      "Content-Type":
          "application/json"
  };
  
  
  };
  
// 🔹 GET all tasks
export const getTasks = async () => {
  const response = await fetch(`${BASE_URL}/all`,{method:"GET",headers:getAuthHeaders()});
  console.log("in taxservice  as ADMIN",response );
  if (!response.ok) {
    throw new Error("Failed to fetch tasks");
  }

  return response.json();
};
export const fetchSubTasks=async(id)=> {
  let url = "";
  url = `http://localhost:8080/tasks/subtasks/${id}`;
  const response = await fetch(url,{method:"GET",headers:  {
    "Content-Type": "application/json"}});
    return await response.json();

};
export const fetchTasks = async (
  role,
  userName
  ) => {
  console.log(" in taskservive",role);
  console.log(" in taskervive",userName);

  let url = "";
  
  if(role === "ADMIN"){
  
   url = "http://localhost:8080/tasks/all";
  
  } else {
  
   url =
     `http://localhost:8080/tasks/user/${userName}`;
  
  }
  console.log("url",url);
  const response = await fetch(url,{method:"GET",headers:getAuthHeaders()});
  console.log("header",getAuthHeaders());
  return await response.json();
};
export const getTasksByUser = async (userName) => {

  

  const response = await fetch(
    `${BASE_URL}/user/${userName}`,
    {
      method: "GET",
      headers:getAuthHeaders()
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
    method: "POST",
    headers: getAuthHeaders(),
    body: JSON.stringify(task)
  });

  if (!response.ok) {
    throw new Error("Failed to add task");
  }

  return response.json();
};
//fetch(`${BASE_URL}/${id}`
// 🔹 ADD subtask
export const addSubTask = async(id, subtask) => {

  console.log("sending subtask", subtask);

  const response = await fetch(
    `${BASE_URL}/subtask/${id}`,
    {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(subtask)
    }
  );

  return await response.json();
};
export const editSubTask = async (id,subtask) => {

  const response = await fetch(
    `${BASE_URL}/editsubtask/${id}`,
    {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(subtask)
    }
  );

  return await response.json();
}
export const updateTask = async (id, task) => {
  console.log(" in updatetask******* ",task.id) ;
  const response = await fetch(`${BASE_URL}/${id}`, {
    method: "PUT",
    headers: getAuthHeaders(),
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
    method: "DELETE",headers:getAuthHeaders()
  });

  if (!response.ok) {
    throw new Error("Failed to delete task");
  }
};