import "./SubTaskCard.css";
import "./taskCard.css";
import {useState} from "react";
import {useEffect,useRef} from "react";
import { useTasks } from "../hooks/useTasks";
function SubTaskCard ({subtask,save_SubTask,id,loadSubTasks,savedSubTasks})  {
    const [reviewedSubTask, setReviewedSubTask] = useState(subtask);
   // const [userSubTasks, setUserSubTasks] = useState(false);
    const [isReviewing, setIsReviewing] = useState(false);
    const [isCompleted, setIsCompleted] = useState(false);
    const inputRef = useRef(null);
    const {editSubTask}=useTasks();const [userSubTasksList, setUserSubTasksList] = useState([]);
   
   
    useEffect(() => {
    if (isReviewing && inputRef.current) {
        inputRef.current.focus();
    }
}, [isReviewing]);
     const review_SubTask = () =>{
        setIsReviewing(true);
      
       
    }
    
    const handleSubTaskSave = () =>{
      const updatedSubTask = {
        ...reviewedSubTask,reviewed:true,
       
    };
             console.log("Saving SubTask in subtaskcad",updatedSubTask);
         save_SubTask(updatedSubTask,id);
         

   // setIsSaved(true);
        
    }
    const handleReview = async (e) => {
        const updatedSubTask = {
        ...reviewedSubTask,reviewed:true,
       
    };

    console.log("SubTask in review",updatedSubTask);
    
    await editSubTask(
      reviewedSubTask.id,
      updatedSubTask
  );
  console.log("***********");
await loadSubTasks();
      setIsReviewing(false);
      setReviewedSubTask(updatedSubTask);
      console.log("***********");

    }

    const handleCompleted = async (e) => {
      console.log("COMPLETED");
      
     /*  const updatedSubTask = savedSubTasks.find(
        subtask => subtask.id === id
    ); */
    const updatedSubTask = {
      ...reviewedSubTask,completed:true,
     
  };
      
      console.log("COMPLETED",updatedSubTask);
      
      await editSubTask(
          reviewedSubTask.id,
          updatedSubTask
      );
   await loadSubTasks();
   setReviewedSubTask({...updatedSubTask}); 
   console.log("after completed",updatedSubTask);
   setIsCompleted(true);
  };
  
    return (
      <div>

        <div className="subtask-container">
            <div className="subtask-card">
           
            {!reviewedSubTask.reviewed && <p>{reviewedSubTask.title}</p>  }
           
            {!reviewedSubTask.reviewed  ? (
              
            <input
            type="text"
            ref={inputRef}
            value={reviewedSubTask.description}
            onChange={(e) =>
                setReviewedSubTask({...reviewedSubTask,
                  
                  description: e.target.value
                })
              } />   ):(                       
           
           <div className="Title" >
           
             <div  className={reviewedSubTask.completed? "completed-subtask": "Not completed"}> {reviewedSubTask.title}
</div>
        </div>
      
          )}
          {isReviewing && (
            <label>
           <input
           type="checkbox"
           checked={reviewedSubTask.reviewed}
           
           onChange ={handleReview}
         />  Reviewed
         </label>)}
   
  {!reviewedSubTask.reviewed   && (
    
        <>
          
          
           <button onClick= { review_SubTask} >Review</button>
          
          
         
</>)}
{!reviewedSubTask.completed  && (
    
    <>
      
      
      <button onClick={handleCompleted}>Completed </button>
      
      
     
</>)}




        
           
        </div> 
   
        

        </div>




</div>
      
    
       
    );
}

export default SubTaskCard;