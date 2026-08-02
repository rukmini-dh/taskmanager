package com.example.taskmanager.knowledgebase;

import java.util.ArrayList;
import java.util.List;

public class ConcernDTO {

    private String name;

    private int timesSuggested;

    private int timesAccepted;

    private int timesRejected;
    private List<TemplateDTO> templates=new ArrayList<>();

   public void setName(String name){this.name=name;}
   public void setTimesSuggested(int timesSuggested){this.timesSuggested=timesSuggested;}
   public void setTimesRejected(int timesRejected){this.timesRejected =timesRejected;}
   public void setTimesAccepted(int timesAccepted){this.timesAccepted=timesAccepted;}
   public int getTimesRejected(){return timesRejected;}
   public int getTimesAccepted(){return timesAccepted;}
   public  int getTimesSuggested(){return timesSuggested;} 
   public String getName(){return name;}
   public void setTemplates(List<TemplateDTO> templates){this.templates=templates;}
   public List<TemplateDTO> getTemplates(){return templates;}
   

}
