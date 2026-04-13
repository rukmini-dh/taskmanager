import React from "react";

function TaskList({ tasks, handleDelete, startEdit, toggleStatus }) {
  return (
    <div>
      <h3>Task List</h3>

      <ul>
        {tasks.map((task) => (
          <li key={task.id}>
            <b>{task.title}</b> - {task.completed ? "Done" : "Pending"}

            <button onClick={() => handleDelete(task.id)}>
              Delete
            </button>

            <button onClick={() => startEdit(task)}>
              Edit
            </button>

            <button onClick={() => toggleStatus(task)}>
              Toggle Status
            </button>
          </li>
        ))}
      </ul>
    </div>
  );
}

export default TaskList;