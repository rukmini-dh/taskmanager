import React , {useState}from "react";


function TaskList({ tasks, handleDelete, startEdit, toggleStatus }) {
 
 

  return (
    
    <div>
      <div className="filters">
      {/* <input
  type="text"
  placeholder="Search tasks..."
  value={searchTerm}
  onChange={(e) => setSearchTerm(e.target.value)}
/> */}
  
</div>
      <h3>Task List</h3>

      <ul>
        {tasks.map((task) => (
          <li key={task.id} className="card">
          <h3>{task.title}</h3>
        
          <p>{task.description}</p>
        
          <p>
            Status:{" "}
            <b>{task.completed ? "Done" : "Pending"}</b>
          </p>
        
          <button className="edit" onClick={() => startEdit(task)}>
            Edit
          </button>
        
          <button className="delete" onClick={() => handleDelete(task.id)}>
            Delete
          </button>
        
          <button className="toggle" onClick={() => toggleStatus(task)}>
            Toggle
          </button>
        </li>
        ))}
      </ul>
    </div>
  );
}

export default TaskList;