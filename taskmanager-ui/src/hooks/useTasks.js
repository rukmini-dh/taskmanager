import { useState, useEffect } from "react";
import {
  getTasks,
  addTask,
  updateTask,
  deleteTask
} from "../services/taskService";

export const useTasks = () => {
  const [tasks, setTasks] = useState([]);
  const [loadingMap, setLoadingMap] = useState({});

  // 🔹 Fetch tasks (runs once)
  useEffect(() => {
    const fetchTasks = async () => {
      try {
        const data = await getTasks();
        setTasks(data);
      } catch (err) {
        console.error("Error fetching tasks:", err);
      }
    };

    fetchTasks();
  }, []);

  // 🔹 Add task (optimistic)
  const createTask = async (task) => {
    const tempId = Date.now();
    const tempTask = { ...task, id: tempId };

    // 1. Show loading
    setLoadingMap(prev => ({
      ...prev,
      [tempId]: "creating"
    }));

    // 2. Optimistically add
    setTasks(prev => [...prev, tempTask]);

    try {
      const saved = await addTask(task);

      // 3. Replace temp with real
      setTasks(prev =>
        prev.map(t => (t.id === tempId ? saved : t))
      );
    } catch (err) {
      // 4. Rollback
      setTasks(prev => prev.filter(t => t.id !== tempId));
    } finally {
      // 5. Remove loading
      setLoadingMap(prev => {
        const copy = { ...prev };
        delete copy[tempId];
        return copy;
      });
    }
  };

  // 🔹 Edit task (optimistic)
  const editTask = async (id, updatedTask) => {
    console.log("API CALLED for id:", id);

    const previousTasks = [...tasks];

    // 1. Show loading
    setLoadingMap(prev => ({
      ...prev,
      [id]: "saving"
    }));

    // 2. Optimistic update
    setTasks(prev =>
      prev.map(t => (t.id === id ? { ...t, ...updatedTask } : t))
    );

    try {
      await updateTask(id, updatedTask);
    } catch (err) {
      // 3. Rollback
      setTasks(previousTasks);
    } finally {
      // 4. Remove loading
      setLoadingMap(prev => {
        const copy = { ...prev };
        delete copy[id];
        return copy;
      });
    }
  };

  // 🔹 Delete task (optimistic)
  const removeTask = async (id) => {
    const previousTasks = [...tasks];

    // 1. Show loading
    setLoadingMap(prev => ({
      ...prev,
      [id]: "deleting"
    }));

    // 2. Optimistic remove
    setTasks(prev => prev.filter(t => t.id !== id));

    try {
      await deleteTask(id);
    } catch (err) {
      // 3. Rollback
      setTasks(previousTasks);
    } finally {
      // 4. Remove loading
      setLoadingMap(prev => {
        const copy = { ...prev };
        delete copy[id];
        return copy;
      });
    }
  };

  return {
    tasks,
    createTask,
    editTask,
    removeTask,
    loadingMap
  };
};
export default useTasks;