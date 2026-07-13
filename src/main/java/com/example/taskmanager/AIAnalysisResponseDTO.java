package com.example.taskmanager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AIAnalysisResponseDTO {

  private Set<String> matchedIntents =  new LinkedHashSet<>();
    private Set<String> matchedKeywords = new LinkedHashSet<>();
    private Priority extractedPriority;

    private LocalDate extractedDate;
   
    public void  setExtractedPriority(Priority extractedPriority){this.extractedPriority= extractedPriority; }
    public Priority getExtractedPriority(){return extractedPriority;}
    public void setMatchedIntents(Set<String> matchedIntents){this.matchedIntents=matchedIntents;}
    public Set<String> getMatchedIntents(){return matchedIntents;}
    public void setMatchedKeywords(Set<String> matchedKeywords){this.matchedKeywords=matchedKeywords;}
    public Set<String> getMatchedKeywords(){return matchedKeywords;}
    
    public void setExtractedDate(LocalDate extractedDate ){this.extractedDate=extractedDate;}
    public LocalDate getExtractedDate(){return extractedDate;}
}
