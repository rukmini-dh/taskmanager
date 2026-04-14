import React, { useState, useEffect } from "react";
import MainLayout from "./layout/MainLayout";
import { useTasks } from "./hooks/useTasks";
import TaskCard from "./components/TaskCard";
import { addTask, updateTask } from "./services/taskService";



function App() {
  const [filter, setFilter] = useState("ALL");
  const [editingTaskId, setEditingTaskId] = useState(null);
  const [searchTerm, setSearchTerm] = useState("");
  const {
    tasks,
    createTask,
    editTask,
    removeTask
  } = useTasks();

  const filteredTasks = tasks.filter((task) => {
    // 🔹 Filter condition
    if (filter === "DONE" && !task.completed) return false;
    if (filter === "PENDING" && task.completed) return false;
    if (filter === "HIGH" && task.priority !== "HIGH") return false;
  
    // 🔹 Search condition
    const search = searchTerm.toLowerCase();
    return (
      task.title.toLowerCase().includes(search) ||
      task.description.toLowerCase().includes(search)
    );
  });
  

  const [taskForm, setTaskForm] = useState({
    title: "",
    description: "",
    priority: "LOW",
    dueDate: ""
  });
  const toggleStatus = (task) => {
    editTask(task.id, {
      ...task,
      completed: !task.completed
    });
  };
  const startEdit = (task) => {
    setTaskForm(task);        // fill form
    setEditingTaskId(task.id); // mark editing mode
  };
  
  const handleSubmit = async () => {
    if (!taskForm.title.trim()) return;
  
    if (editingTaskId) {
      // UPDATE
      await editTask(editingTaskId, taskForm);
      setEditingTaskId(null);
    } else {
      // ADD
      await createTask({
        ...taskForm,
        completed: false
      });
    }
   
    // clear form after adding
    setTaskForm({
      title: "",
      description: "",
      priority: "LOW",
      dueDate: ""
    });
  };
  
  return (
    <MainLayout>
      
      <h2>Tasks</h2>

      {/* Your TaskForm here */}
      <div className="task-form">

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
    <option value="LOW">Low</option>
    <option value="MEDIUM">Medium</option>
    <option value="HIGH">High</option>
  </select>

  <input
    type="date"
    value={taskForm.dueDate}
    onChange={(e) =>
      setTaskForm({ ...taskForm, dueDate: e.target.value })
    }
  />

<button onClick={handleSubmit}>
  {editingTaskId ? "Update Task" : "Add Task"}
</button>
</div>
<div className="filters">
          <button onClick={() => setFilter("ALL")}>All</button>
          <button onClick={() => setFilter("DONE")}>Done</button>
          <button onClick={() => setFilter("PENDING")}>Pending</button>
          <button onClick={() => setFilter("HIGH")}>High</button>
      </div>
      <input
    className="search"
    placeholder="Search..."
    value={searchTerm}
    onChange={(e) => setSearchTerm(e.target.value)}
  />
      <input
  type="text"
  placeholder="Search tasks..."
  value={searchTerm}
  onChange={(e) => setSearchTerm(e.target.value)}
/>
     
      
      {filteredTasks.map((task) => (
  <TaskCard
    key={task.id}
    task={task}
    onEdit={startEdit}
    onDelete={removeTask}
    onToggle={toggleStatus}
  />
))}

    </MainLayout>
  );
}

export default App;