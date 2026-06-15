import "./SubTaskCard.css";
import {useState} from "react";
import {useEffect,useRef} from "react";
import { useTasks } from "../hooks/useTasks";
function SubTaskCard ({subtask,save_SubTask,subtask_id})  {
    const [reviewedSubTask, setReviewedSubTask] = useState(subtask);
    
    const [isReviewing, setIsReviewing] = useState(false);
    const inputRef = useRef(null);
    
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
    return (
        <div className="subtask-container">
            <div className="subtask-card">
                     
           
            {isReviewing  ? (
            <input
            type="text"
            ref={inputRef}
            value={reviewedSubTask.title}
            onChange={(e) =>
                setReviewedSubTask({...reviewedSubTask,
                  
                  title: e.target.value
                })
              } />   ):(                       
           
            <div  className="title" >{reviewedSubTask.title}</div>)}
   
   {!isReviewing && (
        <>
           <button onClick= { review_SubTask} >Review</button>
         
</>)}
            <button onClick={handleSubTaskSave }>Save </button>
           
        </div>   
        </div>

       
        
    );
}

export default SubTaskCard;