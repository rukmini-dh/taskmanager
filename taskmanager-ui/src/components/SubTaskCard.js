import "./SubTaskCard.css";
import {useState} from "react";
import {useEffect,useRef} from "react";
import { useTasks } from "../hooks/useTasks";
function SubTaskCard ({subtask,save_SubTask,subtask_id,loadSubTasks})  {
    const [reviewedSubTask, setReviewedSubTask] = useState(subtask);
    
    const [isReviewing, setIsReviewing] = useState(false);
    const inputRef = useRef(null);
    const {editSubTask}=useTasks();
    console.log("Reviewed Subtasks",reviewedSubTask)
    useEffect(() => {
        if (isReviewing) {
          inputRef.current.focus();
        }
      }, [isReviewing]);
     const review_SubTask = () =>{
        setIsReviewing(true);
       
    }
    const handleSubTaskSave = () =>{
             
         save_SubTask(reviewedSubTask,subtask_id);
         

    setIsReviewing(false);
        
    }
    const handleCompleted = async (e) => {
      console.log("COMPLETED");
      const updatedSubTask = {
          ...reviewedSubTask,
          completed: e.target.checked
      };
  
      setReviewedSubTask(updatedSubTask);
  
      await editSubTask(
          reviewedSubTask.id,
          updatedSubTask
      );
   await loadSubTasks();
  };
    return (
        <div className="subtask-container">
            <div className="subtask-card">
                     
           
            {!reviewedSubTask.reviewed  ? (
            <input
            type="text"
            ref={inputRef}
            value={reviewedSubTask.title}
            onChange={(e) =>
                setReviewedSubTask({...reviewedSubTask,
                  
                  title: e.target.value
                })
              } />   ):(                       
           
           <div className="Title">
            {/* <div className={reviewedSubTask.completed ? "completed-subtask" : ""  } > */}
           
      
            <input
                type="checkbox"
                checked={reviewedSubTask.completed}
                disabled={reviewedSubTask.completed}
                onChange ={handleCompleted}
              /> 
             <span  className={reviewedSubTask.completed? "completed-subtask": ""}> {reviewedSubTask.title}
</span>
        </div>
      
          )}
          
   
   {!reviewedSubTask.reviewed  && (
        <>
           <button onClick= { review_SubTask} >Review</button>
           <button onClick={handleSubTaskSave }>Save </button>
         
</>)}
        
           
        </div>   
        </div>

       
        
    );
}

export default SubTaskCard;