import { useState, useEffect } from "react";
import {
  getTasks,
  addTask as apiAddTask,
  updateTask as apiUpdateTask,
  getTasksByUser
} from "../services/taskService";

export const useTasks = () => {

  const [tasks, setTasks] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [loadingMap, setLoadingMap] = useState({});
 
  const role = localStorage.getItem("role");
  const userName = localStorage.getItem("userName");
  console.log(" before function in user'task",userName);
  const loadTasks = async () => {
    try {
      setLoading(true);
      setError(null);
      console.log("in useTasks",role);
      let data;

      if (role === "ADMIN") {
        console.log("ADMIN");
        data = await getTasks();
      } else {
        console.log("guest");
        data = await getTasksByUser(userName);
        console.log("*** in usetask as guesgt",data);
      }

      setTasks(data);

    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadTasks();
  }, []);

  // 🔹 Add Task
  const addTask = async (task) => {
    console.log("in usetasks",task);
    try {
      await apiAddTask(task);
      loadTasks();
    } catch (err) {
      console.error("Add failed", err);
    }
  };

  // 🔹 Edit Task
  const editTask = async (id, updatedTask) => {
    try {
      setLoadingMap(prev => ({ ...prev, [id]: "saving" }));

      await apiUpdateTask(id, updatedTask);
      loadTasks();

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

  // 🔹 Delete Task (soft delete)
  const deleteTask = async (task) => {
    try {
      setLoadingMap(prev => ({ ...prev, [task.id]: "deleting" }));

      await apiUpdateTask(task.id, {
        ...task,
        deleted: true
      });

      loadTasks();

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

  // 🔹 Undo delete
  const undoDelete = async (task) => {
    try {
      await apiUpdateTask(task.id, {
        ...task,
        deleted: false
      });

      loadTasks();

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
    loading,
    error,
    reload: loadTasks
  };
};