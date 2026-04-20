import React, {useState}from "react";
import "./taskCard.css";
import { FaTrash, FaEdit,FaSave,FaTimes } from "react-icons/fa";
import {useEffect,useRef} from "react";
const TaskCard = ({ task, onEdit, onDelete, onToggle,onSave }) => {
  const [isEditing, setIsEditing] = useState(false);
  const [title,setTitle]=useState("");
  const [editedTask, setEditedTask,resetTask] = useState(task);
  const inputRef = useRef(null);
  useEffect(() => {
    if (isEditing) {
      inputRef.current.focus();
    }
  }, [isEditing]);
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
  const handleCancel = () => {
    setEditedTask(task);
    setIsEditing(false);
  };
  const handleSave = () => {
    onSave(task.id, editedTask);
    setIsEditing(false);};
  return (
    <div ref={cardRef}  className="Card">

      {/* First Row */}
      <div className="firstrow">
       
      {!isEditing ? (
  <div className="title">{task.title}</div>
) : (
  <div className="edit-title">
    <input ref={inputRef}
      type="text"
      className="title"
      placeholder="Enter title"
      value={editedTask.title}
      onKeyDown={(e) => {
        if (e.key === "Enter") {
          handleSave();
        }
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
        
             

        <input
          type="checkbox"
          className="status"
          checked={task.completed}
          onChange={() => onToggle(task)}
        />
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
          <FaEdit className="editButton" onClick={() => setIsEditing(true)} />
          <FaTrash className="deleteButton" onClick={() => onDelete(task.id)} />
          <FaSave  className="saveButton"   onClick={() => { onSave(task.id, editedTask);
    setIsEditing(false);
  }}
/>

          <FaTimes   className="cancelButton"   onClick={() => { setEditedTask(task);
    setIsEditing(false);
  }}
/>
         
        </div>

      </div>
    </div>
  );
};

export default TaskCard;