export async function generatePlan(dto) {

    const response =
        
        await fetch( "http://localhost:8080/ai/generate-plan",
            {
                method: "POST",
                headers: {
                    "Content-Type":"application/json"
                },
                body: JSON.stringify(dto)
            });
            console.log("in aiService",dto);
    return response.json();
}