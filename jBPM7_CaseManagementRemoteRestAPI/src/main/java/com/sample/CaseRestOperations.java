package com.sample;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.kie.internal.process.CorrelationKeyFactory;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.cases.CaseFile;
import org.kie.server.client.CaseServicesClient;
import org.kie.server.client.KieServicesClient;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;

public class CaseRestOperations {

	private static final String URL = "http://localhost:8080/kie-server/services/rest/server";
	private static final String USER = "bpmsAdmin";
	private static final String PASSWORD = "admin12@";
	private static final String ContainerId = "itorders_1.0.17-SNAPSHOT";
	private static final String extraClasses = "com.sample.Applicant";

	private static final MarshallingFormat FORMAT = MarshallingFormat.JSON;

	private static KieServicesConfiguration conf;
	private static KieServicesClient kieServicesClient;
	private static CorrelationKeyFactory factory;

	public void initialize() {

	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub

		conf = KieServicesFactory.newRestConfiguration(URL, USER, PASSWORD);
		conf.setMarshallingFormat(FORMAT);
		conf.setTimeout(1000);
		Set<Class<?>> classes = new HashSet<Class<?>>();
		//classes.add(com.Person.class);
		//conf.addExtraClasses(classes);

		kieServicesClient = KieServicesFactory.newKieServicesClient(conf);

		addTask("IT-0000000002");
		
	}

	public static void addTask(String caseId) {
		CaseServicesClient caseServicesClient = kieServicesClient.getServicesClient(CaseServicesClient.class);
		Map<String, Object> taskdata = new HashMap<>();

		taskdata.put("Description", "Dynamic task Description");
		caseServicesClient.addDynamicUserTask(ContainerId, caseId, "Dynamic task", "Dynamic Task subject", "bpmsAdmin,user2", null,
				taskdata);

	}

	public static String startCasExecution() {

		CaseServicesClient caseServicesClient = kieServicesClient.getServicesClient(CaseServicesClient.class);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("caseAction", "open");

		Map<String, String> groupAssignments = new HashMap<String, String>();
		groupAssignments.put("supplier", "admin");	

		Map<String, String> userAssignments = new HashMap<String, String>();
		userAssignments.put("owner", "bpmsAdmin");
		userAssignments.put("manager", "bpmsAdmin");

		CaseFile casefile = new CaseFile();
		casefile.setData(params);
		casefile.setGroupAssignments(groupAssignments);
		casefile.setUserAssignments(userAssignments);
		String caseId = caseServicesClient.startCase(ContainerId, "itorders.orderhardware", casefile);
		System.out.println("Case Instance started with case ID=" + caseId);
		return caseId;

	}

	public static void destroyCase(String caseId) {
		CaseServicesClient caseServicesClient = kieServicesClient.getServicesClient(CaseServicesClient.class);
		caseServicesClient.destroyCaseInstance(ContainerId, caseId);
	}

	public static void closeCase(String caseId) {

		CaseServicesClient caseServicesClient = kieServicesClient.getServicesClient(CaseServicesClient.class);
		String closeCasecomment = "All milestones completed so closing the case";
		caseServicesClient.closeCaseInstance(ContainerId, caseId, closeCasecomment);
	}
	
	public static void reopenCase(String caseId) {
		CaseServicesClient caseServicesClient = kieServicesClient.getServicesClient(CaseServicesClient.class);
		caseServicesClient.reopenCase(caseId, ContainerId, "itorders.orderhardware");
	}
	
	public static void triggerNodes(String caseId) {
		CaseServicesClient caseServicesClient = kieServicesClient.getServicesClient(CaseServicesClient.class);
		caseServicesClient.triggerAdHocFragment(ContainerId, caseId, "Prepare hardware spec", null);	
	}
}