import React,{useState,useEffect} from "react"; 
import {useTasks} from "../hooks/useTasks";
import TaskCard from "../components/TaskCard";
import TaskForm from "../components/TaskForm";
import { updateTask } from "../services/taskService";
import { useAuthContext } from "../context/AuthContext";
import {analyseTitle} from "../services/alService";
function Tasks() {
  //initialising variables
  const [error, setError] = useState("");
  const [filter, setFilter] = useState("ALL");
  const [searchTerm, setSearchTerm] = useState("");
  const [toast, setToast] = useState("");
  const { tasks, editTask,loading, deleteTask, addTask, loadingMap,undoDelete } = useTasks();
  const [lastDeleted, setLastDeleted] = useState(null);
  const role = localStorage.getItem("role");
  const userName = localStorage.getItem("userName");
  const { currentUser } = useAuthContext();
 
 
  const [taskForm, setTaskForm] = useState({
    title: "",
    description: "",
    completed: false,
    priority: "LOW",
    dueDate: "",
    deleted:false,
    userName:localStorage.getItem("userName")   
  
  });
  
 
// console.log("in tasskform",taskForm);
   useEffect(() => {
    console.log("loadingMap changed:", loadingMap);
  }, [loadingMap]);
 
  const [editingId, setEditingId] = useState(null);
  const [analysis, setAnalysis] = useState(null);

    const handleUndo = async () => {
      if (!lastDeleted) return;
    
      await undoDelete(lastDeleted);
      setLastDeleted(null);
    };
    
 
  const handleDelete = (task) => {
    setToast("Task "+task.id +" deleted");
    setLastDeleted(task);        // store deleted task
    console.log("in delete");
    const updatedTask = {
      ...task,
      deleted: true
    };
  
    deleteTask( updatedTask);
    setTimeout(() => {
      setLastDeleted(null);
   }, 5000);
  };
  
//analyse the entered title
const handleAnalyse = async () => {
console.log("in analyse title",taskForm.title);
  if (!taskForm.title.trim()) return;

  const result =
      await analyseTitle(taskForm.title);
console.log("Result in HandleAnalyse",result);
  setAnalysis(result);
};
   // 🔹 Add or Update Task
    const handleSubmit = async () => {
      console.log("submitted");
      setLastDeleted(null);
    if (!taskForm.title.trim()) 
    {setError("Please enter a task title");
      return;}
      setError("");
      console.log(editingId);
    if (editingId) {
      await editTask(editingId, taskForm);

    } else {
      console.log("in adding",taskForm);
      await addTask({
        ...taskForm,
        completed: false
      });
    
  }
  
    resetForm();
  };
 
   const onSave=  async(id,updatedTask)=>
   {
     await editTask(id,updatedTask);
     setToast("Task saved!");
    
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
  useEffect(() => {
    if (!toast) return;
  
    const timer = setTimeout(() => setToast(""), 2000);
    return () => clearTimeout(timer);
  }, [toast]);
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
  if (loading) return <p>Loading...</p>;
  if (error) return <p>{error}</p>;
 /*  if (!currentUser) {
    console.log("no user!")
    return (
        <div>
            Please sign in to view tasks.
        </div>
    );
} */
  return (
    <div className="container">
          {analysis?.matchedKeywords?.length > 0 && (
  <p className="plan-context">
    Plan based on: {analysis.matchedKeywords.join(", ")}
  </p>
)}
  {analysis?.extractedPriority && (
  <p className="plan-priority">
    Priority detected: {analysis.extractedPriority}
  </p>
)}
      
    {/*   !currentUser && return (  <div>
            Please sign in to view tasks.
        </div>) ;
       */}
      <div className="card">
        <TaskForm
          taskForm={taskForm}
          setTaskForm={setTaskForm}
          handleSubmit={handleSubmit}
          handleAnalyse={handleAnalyse}
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
      
          {toast && <div className="toast">{toast}</div>}
     
      {lastDeleted && (
  <div className="undo-bar">
   
    <button onClick={handleUndo}>Undo</button>
  </div>
)}
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
    onDelete= {()=> handleDelete(task)}
    onToggle={toggleStatus}
    onSave={onSave}
    loadingState={loadingMap[task.id]} 
    
       
  />
))}
        
    </div>
  );
}
  
  export default Tasks;