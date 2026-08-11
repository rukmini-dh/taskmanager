import React, {useState}from "react";
import "./taskCard.css";
import SubTaskCard from "../components/SubTaskCard";
import { useTasks } from "../hooks/useTasks";
import { FaTrash, FaEdit,FaSave,FaTimes } from "react-icons/fa";
import {useEffect,useRef} from "react";
import { getCurrentUser } from "../services/authService";
import {generatePlan} from "../services/alService";
import {generateSubTasks } from "../services/alService";
import { fetchSubTasks } from "../services/taskService";
import { ServerRouter } from "react-router-dom";

const TaskCard=({ task, onSave, onDelete, onToggle, loadingState ,analysis,setAnalysis,generatedPlans,setGeneratedPlans}) => {
  const [isEditing, setIsEditing] = useState(false);
  const [isGenerating, setIsGenerating] = useState(false);
  const isLoading = !!loadingState; // boolean derived from status
  const [title,setTitle]=useState("");
  const [editedTask, setEditedTask] = useState(task);
  const [displayedSubTasks, setDisplayedSubTasks] = useState([]);
  const [aiPlan,setAiPlan] =useState([]);
  const isSaving = loadingState === "saving";
  const [savedSubTasks, setSavedSubTasks] = useState([]);   
  const isDeleting = loadingState === "deleting";
  const [currentUser, setCurrentUser] =    useState(null);
  const [showSubtasks, setShowSubtasks] = useState(false);
  const {addSubTask} = useTasks();
  const taskLocked = task.completed;
  const {editSubTask} = useTasks();
  const inputRef = useRef(null);
  const [error, setError] = useState("");
  const [isReviewing, setIsReviewing] = useState(false);
  const [userSubTasks, setUserSubTasks] = useState(false);
  const completedSubtasks=savedSubTasks.filter(subtask => subtask.completed).length;
  console.log("Completed subtasks",completedSubtasks);
  const [userSubTasksList, setUserSubTasksList] = useState([]);
const totalSubtasks = savedSubTasks.filter(subtask=>!subtask.deleted).length;
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
  const handleSubTaskSave = async () => {
    console.log("Saving user subtasks:", userSubTasksList);

    for (const subtask of userSubTasksList) {
        await addSubTask(task.id, subtask);
    }

    await loadSubTasks();

    setUserSubTasksList([]);
    setUserSubTasks(false);
    setShowSubtasks(true);
};
  
  const handleSave = (e) => {

    console.log("save triggered");
    if (e) e.preventDefault();
    if (isSavingRef.current) return;   

    isSavingRef.current = true;
    onSave(task.id, editedTask);
      setIsEditing(false);
      setGeneratedPlans(prev => ({
        ...prev,
        [task.id]: false
    }));
    };
    
    const handleUserGeneratedSubtasks = async()=>
    {
      setUserSubTasksList( [...userSubTasksList,
        
        {
            title: "",
            description: "",
            completed: false,
            edited: false,
            source: "USER",
            feedback:null
            
        }
    ]);
   
      setUserSubTasks(true);
      setShowSubtasks(false);
      
      console.log (" in adding subtasks");
     
      } ; 
      const handleAddToList = () => {
        setUserSubTasksList(prev => [
            ...prev,
            {
                title: "",
                description: "",
                completed: false,
                edited:false,
                feedback:null,
                source: "USER"
                
            }
        ]);
    };
    const handleAI = async () => {
      setIsGenerating(true);
  
      console.log("Handle AI called!");
      console.log("Task id", task.id);
  
      try {
          const plan = await generateSubTasks(task.title);
  
          if (plan.steps.length === 0) {
              console.log("no plan");
              return;
          }
  
          console.log("Plan received:", plan);
          console.log("plan.steps =", plan.steps);
  
          for (const step of plan.steps) {
              await addSubTask(task.id, step);
          }
  
          // The plan has now really been generated and saved
          setGeneratedPlans(prev => ({
              ...prev,
              [task.id]: true
          }));
  
          await loadSubTasks();
          setError("");
          setShowSubtasks(true);
  
      } catch (err) {
          setError(err.message);
      } finally {
          setIsGenerating(false);
      }
  };
  
  return (
   
   
    <div ref={cardRef}  className="Card"  >

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
   {/*  {(
    savedSubTasks.length === 0 ||
    !savedSubTasks.some(subtask => subtask.source === "AI")
) && ( */}
{savedSubTasks.templateId != null || savedSubTasks.length==0 && (
    <button
        className="Generate Plan"
        onClick={handleAI}
        disabled={isGenerating}
    >
        {isGenerating ? "Generating..." : "Generate Plan"}
    </button>
)}


<button onClick={()=> handleUserGeneratedSubtasks()}>Add SubTask</button>
  {savedSubTasks.length > 0  && (
   
      <button
        type="button"
        onClick={() => setShowSubtasks(!showSubtasks)}>
        {showSubtasks ? "Hide subtasks" : "Show subtasks"}
      </button>
  
   )}   
   </div>    
   
      
          {!taskLocked && (< button  disabled={currentUser?.role==="GUEST"|| currentUser?.role==="SUPERVISOR"}onClick={() => setIsEditing(true)}> Edit</button> )}
          
          {!taskLocked && (<button disabled={(currentUser?.role==="GUEST"|| currentUser?.role==="SUPERVISOR") || (isLoading)}onClick={() => onDelete(task)}   > {isDeleting ? "Deleting..." : "Delete"}</button>)}
      
         
        </div>
        {isSaving && <span className="spinner">Saving...</span>}
        {isDeleting && <span className="spinner">Deleting...</span>}
   
      
      <div className="subtask-container">
        
   

    

 {showSubtasks && (
  <div className="subtask-list"> 
  
{savedSubTasks.filter(subtask=>!subtask.deleted).map((step, index) => (

    <SubTaskCard
        key={index}
        subtask_id={step.id} 
        subtask={step}
        save_SubTask={handleSubTaskSave}
        user_SubTasks={handleUserGeneratedSubtasks}
        id={task.id}
        loadSubTasks={loadSubTasks}
        savedSubTasks={savedSubTasks}
      
       
    />

))} 
</div>
  )}
 
  
</div>
{/* First Row */}
<div>
      {
error &&
<p className="error">
    {error}
</p>
}
</div>

{(userSubTasks && showSubtasks==false) && userSubTasksList.map((subtask, index) => (

              <div className="subtask-card" key={index}>
              
              <input
                  type="text"
                  disabled={showSubtasks}
                  placeholder="Subtask Title"
                  value={subtask.title}
                  onChange={(e) => {
              
                    const updated=[...userSubTasksList];
                    updated[index].title=e.target.value;
            
                    setUserSubTasksList(updated);
                   
            
              
                  }}
              />
              
              <input
                  type="text"
                  placeholder="Describe the subtask"
                  value={subtask.description}
                  onChange={(e) => {
              
                      const updated = [...userSubTasksList];
              
                      updated[index].description = e.target.value;
                      setUserSubTasksList(updated);
                     
              
                  }}
              />
              <button onClick={handleAddToList}>Add</button>
              <button onClick={handleSubTaskSave }>Done</button>
              </div>
              
              )
              )}
          
      
     </div>
     
    
    )   
  
 
  }
export default TaskCard;