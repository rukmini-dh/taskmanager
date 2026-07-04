import React, {useState}from "react";
import "./taskCard.css";
import SubTaskCard from "../components/SubTaskCard";
import { useTasks } from "../hooks/useTasks";
import { FaTrash, FaEdit,FaSave,FaTimes } from "react-icons/fa";
import {useEffect,useRef} from "react";
import { getCurrentUser } from "../services/authService";
import {generatePlan} from "../services/alService";
import { fetchSubTasks } from "../services/taskService";
const TaskCard=({ task, onSave, onDelete, onToggle, loadingState }) => {
  const [isEditing, setIsEditing] = useState(false);
  const [isGenerating, setIsGenerating] = useState(false);
  const isLoading = !!loadingState; // boolean derived from status
  const [title,setTitle]=useState("");
  const [editedTask, setEditedTask] = useState(task);
  const [aiPlan,setAiPlan] =useState(null);
  const isSaving = loadingState === "saving";
  //const [generatedSteps, setGeneratedSteps] = useState([]);
  const [savedSubTasks, setSavedSubTasks] = useState([]);   
  const isDeleting = loadingState === "deleting";
  const [currentUser, setCurrentUser] =    useState(null);
  const [showSubtasks, setShowSubtasks] = useState(false);
  const {addSubTask} = useTasks();
  const taskLocked = savedSubTasks.length > 0;
  const {editSubTask} = useTasks();
  const inputRef = useRef(null);
  const [isReviewing, setIsReviewing] = useState(false);
  const completedSubtasks =
  savedSubTasks.filter(subtask => subtask.completed).length;

const totalSubtasks = savedSubTasks.length;
  useEffect(() => {
    if (isEditing) {
      inputRef.current.focus();
    }
  }, [isEditing]);
  useEffect(()=>{
    setSavedSubTasks([]);
     loadSubTasks();
    },[task.id]);
  const loadSubTasks = async () => {
    setSavedSubTasks([]);
    const data = await fetchSubTasks(task.id);
    setSavedSubTasks(data);
    
    };
  const isSavingRef = useRef(false);
  useEffect(() => {
    const handleClickOutside = (e) => {
      if (cardRef.current && !cardRef.current.contains(e.target)) {
        handleCancel();
      }
    };
  
    if (isEditing) {
      document.addEventListener("mousedown", handleClickOutside);
    }
  
    return () => {
      document.removeEventListener("mousedown", handleClickOutside);
    };
  }, [isEditing]);
  const cardRef = useRef(null);
  useEffect(() => {
    if (!loadingState) {
      isSavingRef.current = false;
    }
  }, [loadingState]);
  const handleCancel = () => {
    if(isLoading) return;
    setEditedTask(task);
    setIsEditing(false);
  };
  
  const handleSubTaskSave=async(reviewedSubTask,id)=>{
        console.log("save subtask",id);
        await editSubTask(id,reviewedSubTask);
        await loadSubTasks();
          
         setIsReviewing(false);

  };
  
  const handleSave = (e) => {

    console.log("save triggered");
    if (e) e.preventDefault();
    if (isSavingRef.current) return;   

    isSavingRef.current = true;
    onSave(task.id, editedTask);
    setIsEditing(false);};

    const handleAI = async () => {

      setIsGenerating(true);
      console.log("Handle AI called!");
      const plan= await generatePlan(task);
      console.log("PLAN =", plan);
      console.log("TYPE =", typeof plan);
      for (const step of plan.steps) {
          await addSubTask(task.id, step);
      }
      setAiPlan(plan);
          await loadSubTasks();
          
      //setGeneratedSteps(plan.steps);
      
     
     // setIsGenerating(false);
  };
    
  
    
  
  return (
    
   
    <div ref={cardRef}  className="Card"  >
      {/* First Row */}
      <div className="firstrow">
      {taskLocked ? (
    <div className="title">
        {task.title}
    </div>
    ): !isEditing ? (
  <div className="title" onClick={() => setIsEditing(true)}>
    {task.title}
    
  </div>
) : (
  <div className="edit-title">
    <input
      ref={inputRef}
      type="text"
      className="title"
      disabled={currentUser?.role==="GUEST"|| currentUser?.role==="SUPERVISOR"}
      value={editedTask.title}
      onKeyDown={(e) => {
        if (e.key === "Enter") {
          e.preventDefault();
          handleSave(e);
        }
        if (e.key === "Escape") handleCancel();
      }}
      onChange={(e) =>
        setEditedTask({
          ...editedTask,
          title: e.target.value
        })
      }
    />
  </div>
)}
  <div>     
     
       {/*  <input
          type="checkbox"
          className="status"
          checked={task.completed}
          onChange={() => onToggle(task)}
        /> */}
      </div>
    </div> 

      {/* Second Row */}

      <div className="secondrow">
      {task.completed && <div >COMPLETED</div>}       

        {!taskLocked && <div className="description">{task.description}</div>}
        {totalSubtasks > 0 && (
  <p className="subtask-summary">
    {completedSubtasks} / {totalSubtasks} subtasks completed
  </p>
)}
      </div>

      {/* Third Row */}
      <div className="thirdrow">
      
    

        {/* Left side */}
        <div className="meta">
        {!taskLocked &&    <div className={`badge ${task.priority.toLowerCase()}`}>
            {task.priority}
          </div>}

      {!taskLocked &&    <div className="duedate">{task.dueDate}</div>}
        </div>

        {/* Right side */}
        <div className="actions">
       {/*  <button onClick={() => onToggle(task)}disabled={isLoading}>
        {task.completed ? "Undo" : "Done"}
      </button> */}
      {isEditing && (
        <>
           <button onClick={handleSave} disabled={isLoading}>
          {isSaving ? "Saving..." : "Save"}
        </button>
          <button onClick={handleCancel} disabled={loadingState}>Cancel</button>
          
          
        </>
        
      ) 
      
      }
    

      {savedSubTasks.length === 0 && (
      <button
      className="Generate Plan"
      onClick={() => handleAI()}
      disabled={isSaving}
      
  >
      Generate Plan
  </button>)}
      
          {!taskLocked && (< button  disabled={currentUser?.role==="GUEST"|| currentUser?.role==="SUPERVISOR"}onClick={() => setIsEditing(true)}> Edit</button> )}
          
          {!taskLocked && (<button disabled={(currentUser?.role==="GUEST"|| currentUser?.role==="SUPERVISOR") || (isLoading)}onClick={() => onDelete(task)}   > {isDeleting ? "Deleting..." : "Delete"}</button>)}
      
         
        </div>
        {isSaving && <span className="spinner">Saving...</span>}
        {isDeleting && <span className="spinner">Deleting...</span>}
   
      </div>
      <div className="subtask-container">
        
   

  {savedSubTasks.length > 0 && (
  <>
    <button
      type="button"
      onClick={() => setShowSubtasks(!showSubtasks)}>
      {showSubtasks ? "Hide subtasks" : "Show subtasks"}
    </button>

  {showSubtasks && (
      <div className="subtask-list"> 

{savedSubTasks.map(step => (

    <SubTaskCard
        key={step.id}
        subtask_id={step.id}
        subtask={step}
        save_SubTask={handleSubTaskSave}
        id={task.id}
        loadSubTasks={loadSubTasks}
    />

))}
</div>
  )}
  </>)}
  
</div>

      
     </div>
     
    
    )}
export default TaskCard;