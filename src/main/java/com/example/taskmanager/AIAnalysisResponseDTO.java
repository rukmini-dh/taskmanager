package com.example.taskmanager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AIAnalysisResponseDTO {

    private List<String> matchedKeywords = new ArrayList<>();

private List<String> matchedIntents = new ArrayList<>();
    private Priority extractedPriority;

    private LocalDate extractedDate;
    public void setMatchedKeywords(List<String> matchedKeywords){this.matchedKeywords=matchedKeywords;}
    public void setMatchedIntents(List<String> matchedIntents){this.matchedIntents=matchedIntents;}
    public void  setExtractedPriority(Priority extractedPriority){this.extractedPriority= extractedPriority; }
    public Priority getExtractedPriority(){return extractedPriority;}
    public  List<String> getMatchedKeywords(){return matchedKeywords;}
    public  List<String> getMatchedIntents(){return matchedIntents;}
    public void setExtractedDate(LocalDate extractedDate ){this.extractedDate=extractedDate;}
    public LocalDate getExtractedDate(){return extractedDate;}
}
