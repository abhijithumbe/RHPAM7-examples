Repository Init Content
=======================

- To integrate DMN with jBPM, we have to use BusinessRuleTask. 
- Use Get_Values and Set_Values  for the data input and output of RuleTask
- DMN returns execution result in java.util.Map type, so we have to create one process variable of type java.util.Map and use it in the data output assignment of BusinessRuleTask which executes DMN. Also in OnExit script of RuleTask we have to add below code to read values from Map and assign them to process variables of custom type:
==========
com.Person person= new com.Person();
person.setValue1((java.math.BigDecimal)list.get("value1"));
person.setValue2((java.math.BigDecimal)list.get("value2"));
kcontext.setVariable("var1",person);
===========

-  Start process execution using REST API
 http://localhost:8080/kie-server/services/rest/server/containers/BPMNDMNTest_1.0.22-SNAPSHOT/processes/MyDMNTest.TestProcess/instances 
 Payload: 
 
 {
	"var1": 
	{
		"com.Person": 
		{
 		   "value1": 1,
	       "value2": 2
        }
    }
} 
