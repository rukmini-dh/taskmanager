export async function generatePlan(taskId) {

    const response = await fetch(
        `http://localhost:8080/ai/generate-plan/${taskId}`,
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            }
        }
    );

    if (!response.ok) {
        const message = await response.text();
        throw new Error(message);
    }
    
    return response.json();
}

export async function analyseTitle(title) {
console.log("in aiservice",title);
    const response =
        
        await fetch( "http://localhost:8080/ai/analyseTitle",
            {
                method: "POST",
                headers: {
                    "Content-Type":"application/json"
                },
                body: JSON.stringify({title})
            });
            
    return response.json();
}
