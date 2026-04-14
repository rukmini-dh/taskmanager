import React, { useState, useEffect } from "react";
import TaskForm from "./components/TaskForm";
import TaskList from "./components/TaskList";
import "./App.css";
import { getTasks,addTask,updateTask,deleteTask} from "./services/taskService";



function App() {
  const [tasks, setTasks] = useState([]);

  const [taskForm, setTaskForm] = useState({
    title: "",
    description: "",
    completed: false,
    priority: "LOW",
    dueDate: ""
  });
 
  const [editingId, setEditingId] = useState(null);

  // 🔹 Fetch Tasks
  const fetchTasks = async () => {
    try {
      const data = await getTasks();
      setTasks(data);
    } catch (err) {
      console.error(err);
    }
  };
 

  useEffect(() => {
    fetchTasks();
  }, []);
  // 🔹 Add or Update Task
  const handleSubmit = async () => {
    try {
      if (!taskForm.title) {
        alert("Title is required");
        return;
      }
  
      if (editingId) {
        await updateTask(editingId, taskForm);
      } else {
        await addTask(taskForm);
      }
  
      fetchTasks();
      resetForm();
    } catch (err) {
      console.error(err);
    }
  };

  // 🔹 Start Edit
  const startEdit = (task) => {
    setTaskForm(task);
    setEditingId(task.id);
  };
  const handleDelete = async (id) => {
    try {
      await deleteTask(id);
      fetchTasks();
    } catch (err) {
      console.error(err);
    }
  };
  const toggleStatus = async (task) => {
    try {
      await updateTask(task.id, {
        ...task,
        completed: !task.completed
      });
  
      fetchTasks();
    } catch (err) {
      console.error(err);
    }
  };
  // 🔹 Toggle Status
  

  // 🔹 Reset form
  const resetForm = () => {
    setTaskForm({
      title: "",
      description: "",
      completed: false,
      priority: "LOW",
      dueDate: ""
    });
    setEditingId(null);
  };

  return (
    <div className="container">
      <h1>Task Manager</h1>
  
      <div className="card">
        <TaskForm
          taskForm={taskForm}
          setTaskForm={setTaskForm}
          handleSubmit={handleSubmit}
          editingId={editingId}
        />
      </div>
  
      <TaskList
        tasks={tasks}
        handleDelete={handleDelete}
        startEdit={startEdit}
        toggleStatus={toggleStatus}
      />
    </div>
  );
}

export default App;