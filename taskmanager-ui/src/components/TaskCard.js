import React from "react";
import "./taskCard.css";

const TaskCard = ({ task, onEdit, onDelete, onToggle }) => {
  return (
    <div className="task-card">

      <h3 className="title">{task.title}</h3>

      <p className="desc">{task.description}</p>

      <div className="meta">
        <span className={`status ${task.completed ? "done" : "pending"}`}>
          {task.completed ? "Done" : "Pending"}
        </span>

        <span className={`priority ${task.priority}`}>
          {task.priority}
        </span>
      </div>

      <div className="actions">
        <button className="edit" onClick={() => onEdit(task)}>Edit</button>

        <button className="toggle" onClick={() => onToggle(task)}>
          
          {task.completed ? "Mark Pending" : "Mark Done"}
        </button>

        <button className="delete" onClick={() => onDelete(task.id)}>
          Delete
        </button>
      </div>

    </div>
  );
};

export default TaskCard;