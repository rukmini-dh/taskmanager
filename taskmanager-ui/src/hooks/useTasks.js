import { useState, useEffect } from "react";
import { getTasks, addTask, updateTask, deleteTask } from "../services/taskService";

export const useTasks = () => {
  const [tasks, setTasks] = useState([]);

  // 🔹 Fetch tasks
  const fetchTasks = async () => {
    try {
      const data = await getTasks();
      setTasks(data);
    } catch (err) {
      console.error("Error fetching tasks:", err);
    }
  };

  // 🔹 Load on first render
  useEffect(() => {
    fetchTasks();
  }, []);

  // 🔹 Add task
  const createTask = async (task) => {
    try {
      const newTask = await addTask(task);
      setTasks((prev) => [...prev, newTask]);
    } catch (err) {
      console.error("Error adding task:", err);
    }
  };

  // 🔹 Update task
  const editTask = async (id, updatedTask) => {
    try {
      const updated = await updateTask(id, updatedTask);
      setTasks((prev) =>
        prev.map((t) => (t.id === id ? updated : t))
      );
    } catch (err) {
      console.error("Error updating task:", err);
    }
  };
  // 🔹 change status
  
  // 🔹 Delete task
  const removeTask = async (id) => {
    try {
      await deleteTask(id);
      setTasks((prev) => prev.filter((t) => t.id !== id));
    } catch (err) {
      console.error("Error deleting task:", err);
    }
  };

  return {
    tasks,
    createTask,
    editTask,
    removeTask,
    fetchTasks
  };
};