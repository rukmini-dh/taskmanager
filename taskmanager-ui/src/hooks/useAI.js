const generatePlan = async (task) => {

    const response = await fetch(
        "/ai/generate-plan",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(task)
        }
    );

    return response.json();
};