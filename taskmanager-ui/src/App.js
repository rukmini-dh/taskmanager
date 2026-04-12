import React, { useState, useEffect } from 'react';
import './App.css';

function App() {
  const [tasks, setTasks] = useState([]);
  const fetchTasks = () => {
    fetch("http://localhost:8080/tasks")
      .then(res => res.json())
      .then(data => setTasks(data))
      .catch(err => console.error("Error fetching tasks:", err));
  };
  const [taskForm, setTaskForm] = useState({
    title: '',
    description: '',
    completed: false,
    priority: 'LOW',
    dueDate: ''
  });
  const resetForm = () => {
    setTaskForm({
      title: '',
      description: '',
      completed: false,
      priority: 'LOW',
      dueDate: ''
    });
    setEditingId(null);
  };
  const [newTask, setNewTask] = useState({
    title: '',
    description: '',
    completed: false,
    priority: 'LOW',
    dueDate: ''
   
  });
  const [editingId, setEditingId] = useState(null);
  const handleSubmit = () => {

    if (!taskForm.title) {
      alert("Title is required");
      return;
    }
  
    if (editingId) {
      // ✏️ UPDATE
      fetch(`http://localhost:8080/tasks/${editingId}`, {
        method: "PUT",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(taskForm)
      })
        .then(() => {
          fetchTasks();
          resetForm();
        });
  
    } else {
      // ➕ ADD
      fetch("http://localhost:8080/tasks", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(taskForm)
      })
        .then(() => {
          fetchTasks();
          resetForm();
        });
    }
  };

  // ✅ FETCH TASKS (optional useEffect)
  
  useEffect(() => {
    fetchTasks();
  }, []);

  // ✅ ADD TASK
  const addTask = () => {
    if (!taskForm.title) {
      alert("Title is required");
      return;
    };
  
    fetch("http://localhost:8080/tasks", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },   
    body: JSON.stringify(taskForm)}).then(res => res.json())
    .then(() => fetchTasks())
    .catch(err => console.error(err));
};

const startEdit = (task) => {
  setTaskForm(task);      // preload existing values
  setEditingId(task.id);  // switch to edit mode
};



  
  const updateTask = (id, updatedTask) => {
    fetch(`http://localhost:8080/tasks/${id}`, {
      method: "PUT",
      headers: {
        "Content-Type": "application/json"
      },
    body: JSON.stringify(updatedTask)}).then(() => fetchTasks())
  };


  // ✅ DELETE TASK
  const deleteTask = (id) => {
    fetch(`http://localhost:8080/tasks/${id}`, {
      method: "DELETE"
    })
      .then(() => fetchTasks())   // 🔁 refresh list
      .catch(err => console.error("Error deleting task:", err));
  };

  return (
    <div>
      <h1>Task Manager</h1>

      {/* ADD TASK SECTION */}
     {/*  <h2>Add Task</h2> */}

      <input
        placeholder="Title"
        value={taskForm.title}
        onChange={(e) =>
          setTaskForm({ ...taskForm, title: e.target.value })
        }
      />

      <input
        placeholder="Description"
        value={taskForm.description}
        onChange={(e) =>
          setTaskForm({ ...taskForm, description: e.target.value })
        }
      />

      <select
        value={taskForm.priority}
        onChange={(e) =>
          setTaskForm({ ...taskForm, priority: e.target.value })
        }
      >
        <option value="LOW">LOW</option>
        <option value="MEDIUM">MEDIUM</option>
        <option value="HIGH">HIGH</option>
      </select>

      <input
        type="date"
        value={taskForm.dueDate}
        onChange={(e) =>
          setTaskForm({ ...taskForm, dueDate: e.target.value })
        }
      />
      <button onClick={handleSubmit}>
        {editingId ? "Update Task" : "Add Task"}
      </button>
     {/*  <button onClick={addTask}></button> */}

      {/* TASK LIST SECTION */}
      <h3>Task List</h3>

      <ul>
        {tasks.map((task) => (
          <li key={task.id}>
            {task.title} - {task.completed ? "Done" : "Pending"}

            <button onClick={() => deleteTask(task.id)}>
              Delete</button>
              <button onClick={() => startEdit(task)}> Edit</button>
             {/*  <button onClick={() =>
              updateTask(task.id, {      ...task, completed: !task.completed})}>Edit </button> */}

          </li>
        ))}
      </ul>
    </div>
  )

} 
export  default App;

