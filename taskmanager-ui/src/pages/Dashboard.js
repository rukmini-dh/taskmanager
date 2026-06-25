import { useAuthContext } from "../context/AuthContext";
import { useAuth } from "../hooks/useAuth";
import { useTasks } from "../hooks/useTasks";
import "../layout/Dashboard.css";
import { getUsers } from "../services/authService";

const Dashboard = () => {
  const { currentUser } = useAuthContext();
  const { tasks } = useTasks();
  const { getUsers } = useAuth;
  const totalTasks = tasks.length;
  console.log("tasks",tasks[0]);
  const totalTasksByUsers =
  tasks.filter(task => task.userName ===currentUser.userName).length;
  const completedTasks = tasks.filter(task => task.completed).length;
  const inProgress =
    totalTasks - completedTasks;
  const completionRate =
    totalTasks === 0
      ? 0
      : Math.round(
        completedTasks * 100 / totalTasks
      );

  // tasks by priority

  if (!currentUser) {
    return (
      <div>
        <h2>Welcome to Task Manager</h2>
        <p>Please sign in to manage tasks.</p>
      </div>
    );
  }


  return (
    <div>
      <h2>Dashboard</h2>
      <h3>Welcome {currentUser.userName}</h3>
      <div className="metric-card">
        <h3>Total Tasks</h3>
        <p>{totalTasks}</p>
      </div>
      <div className="metric-card">
        <h3>Tasks Completed </h3>
        <p>{completedTasks}</p>

      </div>
      <div className="metric-card">
        <h3>In Progress</h3>
        <p>{inProgress}</p>
      </div>

      <div className="progress-container">
        <div
          className="progress-fill"
          style={{ width: `${completionRate}%` }}
        >
        </div>
      </div>

      <p>{completionRate}% Complete</p>
      <div className="metric-card">
        <h2>Task Statistics by User</h2>
        <p style={{ display: 'inline-block', marginRight: '15px' }}>User</p>
        <p style={{ display: 'inline-block', marginLeft: '60px' }}>Tasks</p>
        <h3></h3>
        <p style={{ display: 'inline-block', marginRight: '15px' }}>Admin</p>
        <p style={{ display: 'inline-block', marginLeft: '60px' }}>{totalTasksByUsers}</p>
        <h3></h3>
        <p style={{ display: 'inline-block', marginRight: '15px' }}>Guest</p>
        <p style={{ display: 'inline-block', marginLeft: '60px' }}>{totalTasksByUsers}</p>
      </div>

    </div>
  );
};


export default Dashboard;
/* Tasks by Priority
Tasks Completed This Week
AI-generated Subtasks Count */
/* User         Tasks
------------------
guest          4
admin          2
supervisor     7 */