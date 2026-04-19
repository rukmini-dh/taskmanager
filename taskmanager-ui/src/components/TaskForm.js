import React from "react";

function TaskForm({ taskForm, setTaskForm, handleSubmit, editingId,error,setError }) {
 
  return (
    <div>
      <h2>{editingId ? "Edit Task" : "Add Task"}</h2>

      <input
        type="text"
        placeholder="Enter task title"
        value={taskForm.title}
        onChange={(e) => {
          setTaskForm({ ...taskForm, title: e.target.value });

          // ✅ clear error while typing
          if (error)setError("");
        }}
        
        className={`input ${error ? "input-error" : ""}`}
      />
      {/* 🔹 Inline error message */}
      {error && <p className="error-text">{error}</p>}

      <input
        placeholder="Description"
        value={taskForm.description}
        onChange={(e) =>
          setTaskForm({ ...taskForm, description: e.target.value })
        }
      />

      <select
        value={taskForm.priority}
        onChange={(e) =>
          setTaskForm({ ...taskForm, priority: e.target.value })
        }
      >
        <option value="LOW">LOW</option>
        <option value="MEDIUM">MEDIUM</option>
        <option value="HIGH">HIGH</option>
      </select>

      <input
        type="date"
        value={taskForm.dueDate}
        onChange={(e) =>
          setTaskForm({ ...taskForm, dueDate: e.target.value })
        }
      />

      <label>
        Completed:
        <input
          type="checkbox"
          checked={taskForm.completed}
          onChange={(e) =>
            setTaskForm({
              ...taskForm,
              completed: e.target.checked
            })
          }
        />
      </label>

      <br />

      <button onClick={handleSubmit}>
        {editingId ? "Update Task" : "Add Task"}
      </button>
    </div>
  );
}

export default TaskForm;