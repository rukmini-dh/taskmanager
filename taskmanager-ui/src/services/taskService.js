const BASE_URL = "http://localhost:8080/tasks";

// 🔹 GET all tasks
export const getTasks = async () => {
  const response = await fetch(BASE_URL);

  if (!response.ok) {
    throw new Error("Failed to fetch tasks");
  }

  return response.json();
};

// 🔹 ADD task
export const addTask = async (task) => {
  const response = await fetch(BASE_URL, {
    method: "POST",
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

// 🔹 UPDATE task
export const updateTask = async (id, task) => {
  const response = await fetch(`${BASE_URL}/${id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(task)
  });

  if (!response.ok) {
    throw new Error("Failed to update task");
  }

  return response.json();
};

// 🔹 DELETE task
export const deleteTask = async (id) => {
  const response = await fetch(`${BASE_URL}/${id}`, {
    method: "DELETE"
  });

  if (!response.ok) {
    throw new Error("Failed to delete task");
  }
};