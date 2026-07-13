import { useState, useEffect } from "react";
import {
  getTasks,fetchTasks,fetchSubTasks,
  addTask as apiAddTask,
  updateTask as apiUpdateTask,addSubTask as apiaddSubTask,editSubTask as apieditSubTask,saveContext as apisaveContext,
  getTasksByUser
} from "../services/taskService";
import {
  useAuthContext
}
from "../context/AuthContext";

export const useTasks = () => {
  const { currentUser } =useAuthContext();

useEffect(() => {
  setTasks([]);
  if(currentUser){loadTasks();}

}, [currentUser]);


  const [tasks, setTasks] = useState([]);
  const [subtasks, setSubTasks] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [loadingMap, setLoadingMap] = useState({});
 
  const role = localStorage.getItem("role");
  const userName = localStorage.getItem("userName");
  console.log(" before function in user'task",userName);
  const loadTasks = async () => {
    try {
      setLoading(true);
      setError(null);
      console.log("in useTasks",role);
      let data;
      data=await fetchTasks(
        currentUser.role,
        currentUser.userName
        );
      
 console.log("after fetching in usetasks",data);
      setTasks(data);

    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  };

 
  // Save context
  const saveContext = async(context)=> {
    try{
      console.log("in save context",context);
            await apisaveContext(context)
     }  
      catch(err){
      console.error("save  context failed")   } 
      
    };
 

 
  // 🔹 Add Task
  const addTask = async (task) => {
    console.log("in usetasks",task);
    try {
      const res=await apiAddTask(task);
      loadTasks();
      return res;
    } catch (err) {
      console.error("Add failed", err);
    }
   
  };
  // Edit SubTask
  const editSubTask=async(id,reviewedSubTask)=>{
        await apieditSubTask(id,reviewedSubTask)

  }

  // 🔹 Edit Task
  const editTask = async (id,updatedTask) => {
    try {
      setLoadingMap(prev => ({ ...prev, [id]: "saving" }));

      await apiUpdateTask(id, updatedTask);
      loadTasks();

    } catch (err) {
      console.error("Edit failed", err);
    } finally {
      setLoadingMap(prev => {
        const copy = { ...prev };
        delete copy[id];
        return copy;
      });
    }
  };
 //Add subtask
  const addSubTask = async (id,newSubTask) => {
    console.log("in use task for subtask");
    try {
      await apiaddSubTask(id,newSubTask);
    

    } catch (err) {
      console.error("adding subtask  failed", err);
    
    }
  };

  // 🔹 Delete Task (soft delete)
  const deleteTask = async (task) => {
    try {
      setLoadingMap(prev => ({ ...prev, [task.id]: "deleting" }));

      await apiUpdateTask(task.id, {
        ...task,
        deleted: true
      });

      loadTasks();

    } catch (err) {
      console.error("Delete failed", err);
    } finally {
      setLoadingMap(prev => {
        const copy = { ...prev };
        delete copy[task.id];
        return copy;
      });
    }
  };

  // 🔹 Undo delete
  const undoDelete = async (task) => {
    try {
      await apiUpdateTask(task.id, {
        ...task,
        deleted: false
      });

      loadTasks();

    } catch (err) {
      console.error("Undo failed", err);
    }
  };

  return {
    tasks,
    addTask,
    editTask,
    deleteTask,
    undoDelete,
    loadingMap,
    loading,
    error,
    addSubTask,
    fetchSubTasks,
    reload: loadTasks,
    editSubTask,
    saveContext
   
  };
};