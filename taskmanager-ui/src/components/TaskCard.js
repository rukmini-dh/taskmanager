import React from "react";
import "./taskCard.css";
import { FaTrash, FaEdit } from "react-icons/fa";

const TaskCard = ({ task, onEdit, onDelete, onToggle }) => {
  console.log("in taskcad");
  return (
    <div className="Card">

      {/* First Row */}
      <div className="firstrow">
        <div className={`title ${task.completed ? "completed" : ""}`}>
          {task.title}
        </div>

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
          <FaEdit className="editButton" onClick={() => onEdit(task)} />
          <FaTrash className="deleteButton" onClick={() => onDelete(task.id)} />
        </div>

      </div>
    </div>
  );
};

export default TaskCard;