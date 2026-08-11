import "./SubTaskCard.css";
import "./taskCard.css";
import {useState} from "react";
import {useEffect,useRef} from "react";
import { useTasks } from "../hooks/useTasks";
function SubTaskCard ({subtask,save_SubTask,id,loadSubTasks,savedSubTasks})  {
    const [reviewedSubTask, setReviewedSubTask] = useState(subtask);
   // const [userSubTasks, setUserSubTasks] = useState(false);
    const [isAccepted, setIsAccepted] = useState(false);
    const [isCompleted, setIsCompleted] = useState(false);
    const [isEditing,setIsEditing]=useState(false);
    const [isRejected,setIsRejected]=useState(false);
    const inputRef = useRef(null);
    const {editSubTask}=useTasks();const [userSubTasksList, setUserSubTasksList] = useState([]);
    useEffect(() => {
    if (isEditing && inputRef.current) {
        inputRef.current.focus();
    }
}, [isEditing]);
     const handleEdit = async (e) => {
        setIsEditing(true);
        if(reviewedSubTask.templateId==null)
        {
        const updatedSubTask = {
          ...reviewedSubTask,edited:true
         
      };
      await editSubTask(
        reviewedSubTask.id,
       updatedSubTask
    );
    await loadSubTasks();
        
         setReviewedSubTask(updatedSubTask);
    }else{
      const updatedSubTask = {
        ...reviewedSubTask,edited:true,feedback:"ACCEPTED"
       
    };
    await editSubTask(
      reviewedSubTask.id,
     updatedSubTask
  );
  await loadSubTasks();
        
         setReviewedSubTask(updatedSubTask);
    }
        
      
    
    }
    
   
    const handleAccept = async (e) => {
        const updatedSubTask = {
        ...reviewedSubTask,feedback:"ACCEPTED",
       
    };

    console.log("SubTask Accepted",updatedSubTask);
    
    await editSubTask(
      reviewedSubTask.id,
      updatedSubTask
  );
  console.log("***********");
await loadSubTasks();
      setIsAccepted(true);
      setReviewedSubTask(updatedSubTask);
      console.log("***********");

    }
    const handleDelete  = async (e)=>{
      const updatedSubTask = {
        ...userSubTasksList,deleted:true
       
    };
    console.log("in deletie",updatedSubTask);
    await editSubTask(
      reviewedSubTask.id,
      updatedSubTask
  );
  await loadSubTasks();
   // setIsRejected(true);
    setReviewedSubTask(updatedSubTask);
    }
    const handleReject = async (e) => {
      const updatedSubTask = {
      ...reviewedSubTask,feedback:"REJECTED",deleted:true
     
  };

  console.log("SubTask Rejected",updatedSubTask);
  
  await editSubTask(
    reviewedSubTask.id,
    updatedSubTask
);
console.log("***********");
await loadSubTasks();
    setIsRejected(true);
    setReviewedSubTask(updatedSubTask);
    console.log("***********");

  }

    const handleEditFlag = ()=>{
      setIsEditing(true);
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
           
             <p>{reviewedSubTask.title}</p> 
           
            {!reviewedSubTask.edited && reviewedSubTask.feedback!="ACCEPTED" ? (
              
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
           
             <div  className={reviewedSubTask.completed? "completed-subtask": "Not completed"}> {reviewedSubTask.description}
</div>
        </div>
      
          )}
        
        {reviewedSubTask.templateId != null &&
 reviewedSubTask.feedback == null && (
    <>
        <button onClick={handleAccept}>
            Accept
        </button>

        <button onClick={handleReject}>
            Reject
        </button>
    </>
)}



 {!reviewedSubTask.edited && reviewedSubTask.feedback != "ACCEPTED"   &&(
 
            <button onClick={handleEditFlag}>Edit</button>)}
            {reviewedSubTask.templateId==null &&  !reviewedSubTask.completed && (
           
            <button onClick={handleDelete}>Delete</button>)}

                 { isEditing && (
            <label>
           <input
           type="checkbox"
           checked={reviewedSubTask.edited}
           
           onChange ={handleEdit}
         />  Edited
         </label>)}
   
  
{!reviewedSubTask.completed && (reviewedSubTask.edited || reviewedSubTask.feedback=="ACCEPTED") && (
    
   
      
      
      <button onClick={handleCompleted}>Completed </button>
      
      
     
)}




        
           
        </div> 
   
        

        </div>




</div>
      
    
       
    );
}

export default SubTaskCard;