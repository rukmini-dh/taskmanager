import { useAuthContext } from "../context/AuthContext";
import { useTasks } from "../hooks/useTasks";
import "../layout/Dashboard.css";
const Dashboard = () => {
  const { currentUser } = useAuthContext();
  const { tasks } = useTasks();
  const totalTasks = tasks.length;
  const completedTasks =
  tasks.filter(task => task.completed).length;
  const inProgress =
  totalTasks - completedTasks;
  const completionRate =
  totalTasks === 0
    ? 0
    : Math.round(
        completedTasks * 100 / totalTasks
      );


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
{/* <div className="metric-card">
    <h3>Completion Rate</h3> 
      <p>{completionRate}</p>
</div> */}
<div className="progress-container">
    <div
        className="progress-fill"
        style={{ width: `${completionRate}%` }}
    >
    </div>
</div>

<p>{completionRate}% Complete</p>

     
    </div>
  );
};

export default Dashboard;