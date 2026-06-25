import { useAuthContext } from "../context/AuthContext";

import { useTasks } from "../hooks/useTasks";
import "../layout/Dashboard.css";


const Dashboard = () => {
  const { currentUser } = useAuthContext();
  const { tasks } = useTasks();
  if (!currentUser) {
    return (
      <div>
        <h2>Welcome to Task Manager</h2>
        <p>Please sign in to manage tasks.</p>
      </div>
    );
  }

  const totalTasks = tasks.length;
  console.log("tasks",tasks[0]);
  const today = new Date();
today.setHours(0,0,0,0);
const nextWeek = new Date(today);
nextWeek.setDate(today.getDate() + 7);
const overdueTasks = tasks.filter(task =>{
  if (!task.dueDate || task.completed) return false;
  return(
  !task.completed &&
  new Date(task.dueDate) < today);
}).length;
const dueToday = tasks.filter(task => {
 
  if (!task.dueDate || task.completed) return false;
  const dueDate = new Date(task.dueDate);
  return (
    !task.completed &&
    dueDate.toDateString() ===
    today.toDateString()
  );
}).length;
const dueThisWeek = tasks.filter(task => {
  if (!task.dueDate || task.completed) return false;
  const dueDate = new Date(task.dueDate);

  return (
    !task.completed &&
    dueDate >= today &&
    dueDate <= nextWeek
  );
}).length;
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
     <div className="metric-card">
      <h2>Tasks status</h2>
      <div>
     <p style={{ display: 'inline-block', marginRight: '15px' }}>Overdue Tasks</p>
        <p style={{ display: 'inline-block', marginLeft: '60px' }}>{overdueTasks}</p></div>
        <div>
        <p style={{ display: 'inline-block', marginRight: '15px' }}>Tasks due today</p>
        <p style={{ display: 'inline-block', marginLeft: '60px' }}>{dueToday}</p></div>
        <div>
        <p style={{ display: 'inline-block', marginRight: '15px' }}>Tasks due this week</p>
        <p style={{ display: 'inline-block', marginLeft: '60px' }}>{dueThisWeek}</p></div>
    </div>
    </div>
  );
};


export default Dashboard;
