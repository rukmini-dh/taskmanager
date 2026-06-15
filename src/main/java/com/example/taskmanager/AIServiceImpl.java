package com.example.taskmanager;
import org.springframework.beans.factory.annotation.Value;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AIServiceImpl implements AIService {
      // private final RestTemplate restTemplate;*/
   @Value("${openai.api.key}")
private String apiKey;

   /*  public AIServiceImpl(RestTemplate restTemplate) {
       this.restTemplate = restTemplate;
    } */
           @Override
            public AIPlanResponseDTO generatePlan(AIPlanRequestDTO request) {
              System.out.println(apiKey);
           /*    try {
               HttpHeaders headers = new HttpHeaders();
headers.setBearerAuth(apiKey);
headers.setContentType(MediaType.APPLICATION_JSON);
String body = """
{
  "model":"gpt-4.1-mini",
  "messages":[
    {
      "role":"user",
      "content":"Say hello in one sentence"
    }
  ]
}
""";
HttpEntity<String> entity =
        new HttpEntity<>(body, headers);
        String response =
        restTemplate.postForObject(
                "https://api.openai.com/v1/chat/completions",
                entity,
                String.class);

System.out.println(response);
return new AIPlanResponseDTO();
} catch(Exception e){

        return new AIPlanResponseDTO(
                List.of(
                    new SubTaskDTO("AI unavailable", false, Source.AI)
                ));
            } */
        
            /* List<SubTaskDTO> steps=new ArrayList<SubTaskDTO>();
            SubTaskDTO st =  new SubTaskDTO("Review requirements",false,Source.AI);
            steps.add(st);
            SubTaskDTO st1 =  new SubTaskDTO("Gather data",false,Source.AI);
            steps.add(st1);
            SubTaskDTO st2 =  new SubTaskDTO("Create deliverbles",false,Source.AI);
            SubTaskDTO st3 =  new SubTaskDTO("Validate output",false,Source.AI);
            steps.add(st2);
            steps.add(st3);
            SubTaskDTO st4 =  new SubTaskDTO("Finalize Tasks",false,Source.AI);
            steps.add(st4);
            return   new AIPlanResponseDTO(steps); */
          //  }*/
       // }*/

       return new AIPlanResponseDTO(
        List.of(
                new SubTaskDTO("Review requirements", false, Source.AI,false),
                new SubTaskDTO("Gather data", false, Source.AI,false),
                new SubTaskDTO("Create deliverables", false, Source.AI,false),
                new SubTaskDTO("Validate output", false, Source.AI,false),
                new SubTaskDTO("Finalize task", false, Source.AI,false)
        )
); 
}
}
