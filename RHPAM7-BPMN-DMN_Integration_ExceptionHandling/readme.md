I have attached a sample project(RHPAM7-BPMN-DMN_Integration_ExceptionHandling.zip) to case for your reference. In attached project DMN execution will fail because of invalid namespace and exception will be thrown. Exception will be caught by  event subprocess and its details will be captured in the process variable of type 'java.lang.Exception'. And exception details will be logged to log file in script task. 


