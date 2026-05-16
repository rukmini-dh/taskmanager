import { useState, useEffect } from "react";
import {
  getTasks,
  addTask as apiAddTask,
  updateTask as apiUpdateTask
} from "../services/taskService";

export const useTasks = () => {
  const [tasks, setTasks] = useState([]);
  const [loadingMap, setLoadingMap] = useState({});

  // 🔹 Fetch all tasks (single source of truth)
  const fetchTasks = async () => {
    try {
      const data = await getTasks();
      setTasks(data);
    } catch (err) {
      console.error("Failed to fetch tasks", err);
    }
  };

  // 🔹 Initial load
  useEffect(() => {
    fetchTasks();
  }, []);

  // 🔹 Add Task
  const addTask = async (task) => {
    try {
      await apiAddTask(task);
      fetchTasks(); // ✅ always sync from backend
    } catch (err) {
      console.error("Add failed", err);
    }
  };

  // 🔹 Edit Task (title, completed, etc.)
  const editTask = async (id, updatedTask) => {
    try {
      setLoadingMap(prev => ({ ...prev, [id]: "saving" }));

      await apiUpdateTask(id, updatedTask);

      fetchTasks(); // ✅ refresh from backend
    } catch (err) {
      console.error("Edit failed", err);
    } finally {
      setLoadingMap(prev => {
        const copy = { ...prev };
        delete copy[id];
        return copy;
      });
    }
  };

  // 🔹 Delete (soft delete using deleted flag)
  const deleteTask = async (task) => {
    try {
      setLoadingMap(prev => ({ ...prev, [task.id]: "deleting" }));

      await apiUpdateTask(task.id, {
        ...task,
        deleted: true
      });

      fetchTasks(); // ✅ no manual removal
    } catch (err) {
      console.error("Delete failed", err);
    } finally {
      setLoadingMap(prev => {
        const copy = { ...prev };
        delete copy[task.id];
        return copy;
      });
    }
  };

  // 🔹 Undo Delete
  const undoDelete = async (task) => {
    try {
      await apiUpdateTask(task.id, {
        ...task,
        deleted: false
      });

      fetchTasks(); // ✅ restore from backend
    } catch (err) {
      console.error("Undo failed", err);
    }
  };

  return {
    tasks,
    addTask,
    editTask,
    deleteTask,
    undoDelete,
    loadingMap,
    fetchTasks
  };
};