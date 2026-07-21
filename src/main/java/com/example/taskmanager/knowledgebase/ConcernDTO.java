package com.example.taskmanager.knowledgebase;

public class ConcernDTO {

    private String name;

    private int timesSuggested;

    private int timesAccepted;

    private int timesRejected;
   public void setName(String name){this.name=name;}
   public void setTimesSuggetsed(int timesSuggested){this.timesSuggested=timesSuggested;}
   public void setTimesRejected(int timesRejected){this.timesRejected =timesRejected;}
   public void setTimesAccepted(int timesAccepted){this.timesAccepted=timesAccepted;}
   public int getTimesRejected(){return timesRejected;}
   public int getTimesAccepted(){return timesAccepted;}
   public  int getTimesSuggested(){return timesSuggested;} 
   public String getName(){return name;}

   

}
