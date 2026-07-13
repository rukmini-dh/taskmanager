package com.example.taskmanager;

 public class PlanningDecision {

    private boolean needsTesting;

    private boolean needsCodeReview;

    private boolean needsRegressionTesting;

    private boolean needsIntegrationTesting;

    private boolean needsDocumentation;

    private boolean parallelExecutionAllowed;
// setters and getters
public void setNeedsTesting(boolean needsTesting){this.needsTesting=needsTesting;}
public boolean isNeedsTesting(){return needsTesting;}
public void setNeedsRegressionTesting(boolean needsRegressionTesting){this.needsRegressionTesting=needsRegressionTesting;}
public boolean isNeedsRegressionTesting(){return needsRegressionTesting;}
public void setNeedsDocumentation(boolean needsDocumntation){this.needsDocumentation=needsDocumntation;}
public boolean isNeedsDocumentaion(){return needsDocumentation;}
public void setNeedsCodeReview(boolean needsCodeReview){this.needsCodeReview=needsCodeReview;}
public boolean isNeedsCodeReview(){return needsCodeReview;}
public void setNeedsIntegrationTesting(boolean needsIntegrationTesting){this.needsIntegrationTesting=needsIntegrationTesting;}
public boolean isNeedsIntegrationTesting(){return needsIntegrationTesting;}
public void setParallelExecutionAllowed(boolean parallelExecutionAllowed){this.parallelExecutionAllowed=parallelExecutionAllowed;}
public boolean isParallelExecutionAllowed(){return parallelExecutionAllowed;}






    
}
