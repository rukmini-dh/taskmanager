import React,{useState} from "react"; 
import {useTasks} from "../hooks/useTasks";
import TaskCard from "../components/TaskCard";
import TaskForm from "../components/TaskForm";

function Tasks() {
  const [error, setError] = useState("");
  const [filter, setFilter] = useState("ALL");
  const [searchTerm, setSearchTerm] = useState("");
  const{tasks,editTask,removeTask,createTask}=useTasks();
  const [taskForm, setTaskForm] = useState({
    title: "",
    description: "",
    completed: false,
    priority: "LOW",
    dueDate: ""
  });
 
  const [editingId, setEditingId] = useState(null);

  // 🔹 Fetch Tasks
  /* const fetchTasks = async () => {
    try {
      const data = await getTasks();
      setTasks(data);
    } catch (err) {
      console.error(err);
    }
  }; */
 

 /*  useEffect(() => {
    fetchTasks();
  }, []); */
  // 🔹 Add or Update Task
  const handleSubmit = async () => {
    console.log("submitted");
    console.log(taskForm.title.trim());
    if (!taskForm.title.trim()) 
    {setError("Please enter a task title");
      console.log("Validation failed");
      return;}
      
      setError("");
    if (editingId) {
      await editTask(editingId, taskForm);
    } else {
      await createTask({
        ...taskForm,
        completed: false
      });
    
  }
  
    resetForm();
  };
 

  const startEdit = (task) => {
    setTaskForm(task);
    setEditingId(task.id);
  };
  const toggleStatus = (task) => {
    editTask(task.id, {
      ...task,
      completed: !task.completed
    });
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
  const filteredTasks = (tasks || []).filter((task) => {
    // 🔹 Filter condition
    if (filter === "DONE" && !task.completed) return false;
    if (filter === "PENDING" && task.completed) return false;
    if (filter === "HIGH" && task.priority !== "HIGH") return false;
  
    // 🔹 Search condition
    const search = searchTerm.toLowerCase();
    return (
      task.title.toLowerCase().includes(search) ||
      (task.description || "").toLowerCase().includes(search)
    );
  });
  
  return (
    <div className="container">
      <h1>Task Manager</h1>
  
      <div className="card">
        <TaskForm
          taskForm={taskForm}
          setTaskForm={setTaskForm}
          handleSubmit={handleSubmit}
          editingId={editingId}
          error={error}
          setError={setError}
        />
      </div>
      <div className="filters">
          <button onClick={() => setFilter("ALL")}>All</button>
          <button onClick={() => setFilter("DONE")}>Done</button>
          <button onClick={() => setFilter("PENDING")}>Pending</button>
          <button onClick={() => setFilter("HIGH")}>High</button>
      </div>
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
    </div>
  );
}
  
  export default Tasks;