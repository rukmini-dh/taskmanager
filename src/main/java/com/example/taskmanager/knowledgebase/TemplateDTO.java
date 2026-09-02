package com.example.taskmanager.knowledgebase;

 public class TemplateDTO {

    private String text;

    private int weight;
    private double specificity;
    private double actionability;
    private double complexity;  

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
    public void setSpecificity(double specificity){this.specificity=specificity;}
    public void setActionability(double actionability){this.actionability=actionability;}
    public void setComplexity(double complexity){this.complexity=complexity;}
    public double getSpecificity(){return specificity;}
    public double getActionability(){return actionability;}
    public double getComplexity(){return complexity;} 
    
}
