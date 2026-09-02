import * as aiService from "./aiService";

export function useAI() {

    const generatePlan =
        async (id) => {

            return await aiService
                .generatePlan(id);
        };

  


    const generateSubTasks =
        async (title,id) => {

            return await aiService
                .generatePlan(title,id);
        };

   // return { generatePlan };
    return { generateSubTasks};

}