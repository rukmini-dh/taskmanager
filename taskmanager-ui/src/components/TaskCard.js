import React, {useState}from "react";
import "./taskCard.css";
import { FaTrash, FaEdit,FaSave,FaTimes } from "react-icons/fa";
import {useEffect,useRef} from "react";
const TaskCard=({ task, onSave, onDelete, onToggle, loadingState }) => {
  const [isEditing, setIsEditing] = useState(false);
  const isLoading = !!loadingState; // boolean derived from status
  const [title,setTitle]=useState("");
  const [editedTask, setEditedTask] = useState(task);
  const isSaving = loadingState === "saving";
    const isDeleting = loadingState === "deleting";
  const inputRef = useRef(null);
  useEffect(() => {
    if (isEditing) {
      inputRef.current.focus();
    }
  }, [isEditing]);
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
  const handleSave = (e) => {

    console.log("save triggered");
    if (e) e.preventDefault();
    if (isSavingRef.current) return;   

    isSavingRef.current = true;
    onSave(task.id, editedTask);
    setIsEditing(false);};
   
    
  
    
  
  return (
    
    <div ref={cardRef}  className="Card">

      {/* First Row */}
      <div className="firstrow">
       
      {!isEditing ? (
  <div className="title" onClick={() => setIsEditing(true)}>
    {task.title}
    
  </div>
) : (
  <div className="edit-title">
    <input
      ref={inputRef}
      type="text"
      className="title"
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
)};
  <div>     
             

        <input
          type="checkbox"
          className="status"
          checked={task.completed}
          onChange={() => onToggle(task)}
        />
      </div>
    </div> 

      {/* Second Row */}
      <div className="secondrow">
        <div className="description">{task.description}</div>
      </div>

      {/* Third Row */}
      <div className="thirdrow">

        {/* Left side */}
        <div className="meta">
          <div className={`badge ${task.priority.toLowerCase()}`}>
            {task.priority}
          </div>

          <div className="duedate">{task.dueDate}</div>
        </div>

        {/* Right side */}
        <div className="actions">
        <button onClick={() => onToggle(task)}disabled={isLoading}>
        {task.completed ? "Undo" : "Done"}
      </button>
      {isEditing && (
        <>
           <button onClick={handleSave} disabled={isLoading}>
          {isSaving ? "Saving..." : "Save"}
        </button>
          <button onClick={handleCancel} disabled={loadingState}>Cancel</button>
          
        </>
        
      ) 
      }
          <button className="editButton" onClick={() => setIsEditing(true)}> Edit</button> 
          
          <button onClick={() => onDelete(task)} disabled={isLoading}  > {isDeleting ? "Deleting..." : "Delete"}</button>
      
         
        </div>
        {isSaving && <span className="spinner">Saving...</span>}
        {isDeleting && <span className="spinner">Deleting...</span>}

      </div>
     </div>
    
    )}
export default TaskCard;