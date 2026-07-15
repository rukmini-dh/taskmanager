import * as aiService from "./aiService";

export function useAI() {

    const generatePlan =
        async (id) => {

            return await aiService
                .generatePlan(id);
        };

    return { generatePlan };
}