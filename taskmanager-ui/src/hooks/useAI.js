import * as aiService from "./aiService";

export function useAI() {

    const generatePlan =
        async (id) => {

            return await aiService
                .generatePlan(id);
        };

  


    const generateSubTasks =
        async (title) => {

            return await aiService
                .generatePlan(title);
        };

    return { generatePlan };
    return { generateSubTasks};

}