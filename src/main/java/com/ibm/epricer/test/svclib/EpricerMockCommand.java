package com.ibm.epricer.test.svclib;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.ScenarioMappingBuilder;
import com.ibm.epricer.svclib.rpc.http.HttpConstants;

/*
 * Provide simple apis to mock the remote services and call the SUT end points. It allows to create stubs by remote service, id end point id and end point version.
 * 
 *  @author Kiran Chowdhury
 */
public class EpricerMockCommand {
    
    private static final String SVCID_HEADER = "epricer-service-id";
    private static final String EPID_HEADER = "epricer-endpoint-id";
    private static final String EPVER_HEADER = "epricer-endpoint-ver";
    private static final String THROWS_HEADER = "epricer-throws";
    private static final String STATUS_HEADER = "epricer-status";
    private static final String USER_HEADER = "epricer-user-id";
    private static final String GROUPID_HEADER = "epricer-group-id";
    
    private static final int OK =200;
    private static final int INTERNAL_SERVER_ERROR=500;
    private static final String EPRICER_STATUS_SUCCESS = "0";
    private static final String EPRICER_BRE_HEADER="1";
    
    private WireMockServer server;
    
    private ObjectMapper mapper;
    
    private String serviceId;
    
    EpricerMockCommand(WireMockServer server, String serviceId) {
    	this.server=server;
    	this.serviceId=serviceId;
    	mapper = new ObjectMapper();
    }
    
    
    /**
     * Call SUT service end-point
     * 
     * @param endpointId - end-point id to call
     * @param endpointVer - version
     * @param input - service call input object
     * @return - mock request builder to pass to mock mvc
     */
    public MockHttpServletRequestBuilder callSutEndpoint(String endpointId, Integer endpointVer, String loggedInUserEmail, Object input) {
        return MockMvcRequestBuilders.post("/rpc")
                        .header(SVCID_HEADER, serviceId)
                        .header(EPID_HEADER, endpointId)
                        .header(EPVER_HEADER, endpointVer)
                        .header(USER_HEADER, loggedInUserEmail)
                        .content(marshallResultToJson(input));
    }
    
    
    /**
     * Call SUT service end-point with default endpoint version
     * 
     * @param endpointId - end-point id to call
     * @param endpointVer - version
     * @param input - service call input object
     * @return - mock request builder to pass to mock mvc
     */
    public MockHttpServletRequestBuilder callSutEndpoint(String endpointId, String loggedInUserEmail, Object input) {
        return callSutEndpoint(endpointId, 1, loggedInUserEmail, input);
    }
    
    /**
     * Call SUT service end-point
     * 
     * @param endpointId - end-point id to call
     * @param endpointVer - version
     * @param loggedInUserEmail - email id for the logged in user
     * @param selectedGroupId - group id selected by the user
     * @param input - service call input object
     * @return - mock request builder to pass to mock mvc
     */
    public MockHttpServletRequestBuilder callSutEndpoint(String endpointId, Integer endpointVer, String loggedInUserEmail, String selectedGroupId, Object input) {
        return MockMvcRequestBuilders.post("/rpc")
                        .header(SVCID_HEADER, serviceId)
                        .header(EPID_HEADER, endpointId)
                        .header(EPVER_HEADER, endpointVer)
                        .header(USER_HEADER, loggedInUserEmail)
                        .header(GROUPID_HEADER, selectedGroupId)
                        .content(marshallResultToJson(input));
    }
    
    /**
     * Call SUT service end-point with default endpoint version
     * 
     * @param endpointId - end-point id to call
     * @param endpointVer - version
     * @param loggedInUserEmail - email id for the logged in user
     * @param selectedGroupId - group id selected by the user
     * @param input - service call input object
     * @return - mock request builder to pass to mock mvc
     */
    public MockHttpServletRequestBuilder callSutEndpoint(String endpointId, String loggedInUserEmail, String selectedGroupId, Object input) {
        return callSutEndpoint(endpointId, 1, loggedInUserEmail, selectedGroupId, input);
    }
    
    /**
     * Build mock internal remote service returning successful response
     * 
     * @param remoteServiceId - service id
     * @param remoteEndpointId - end-point id
     * @param endpointVersion - end-point-version
     * @param response - remote service response
     * 
     */
    public void aSuccessResponseForDependentInternalService(String serviceId, String endpoinId, Integer endpointVersion, Object response) {
    	stubSuccessResponseForDependentInternalService(serviceId, endpoinId, endpointVersion, null, response);
    }    
    
    /**
     * Build mock internal remote service returning successful response
     * This will set the default end point version which is 1
     * 
     * @param remoteServiceId - service id
     * @param remoteEndpointId - end-point id
     * @param response - remote service response
     * 
     */
    public void aSuccessResponseForDependentInternalService(String serviceId, String endpoinId, Object response) {
    	stubSuccessResponseForDependentInternalService(serviceId, endpoinId, 1, null, response);
    }
    
    /**
     * Build mock internal remote service returning business rule violation response
     * This will use the default end point version which is 1
     * @param remoteServiceId - service id
     * @param remoteEndpointId - end-point id
     * @param endpointVersion - end-point-version
     * @param response - remote service response
     * 
     */
    public void aBusinessRuleViolationResponse(String serviceId, String endpoinId, Integer endpointVersion, Object response) {
    	stubSuccessResponseForDependentInternalService(serviceId, endpoinId, endpointVersion, Boolean.TRUE, response);
    }
    
    /**
     * Build mock internal remote service returning business rule violation response
     * This will use the default end point version which is 1
     * @param remoteServiceId - service id
     * @param remoteEndpointId - end-point id
     * @param endpointVersion - end-point-version
     * @param response - remote service response
     * 
     */
    public void aBusinessRuleViolationResponse(String serviceId, String endpoinId,  Object response) {
    	stubSuccessResponseForDependentInternalService(serviceId, endpoinId, 1, Boolean.TRUE, response);
    }
    
    /**
     * Build mock internal remote service that throws unhandled technical exception
     * 
     * @param remoteServiceId - service id
     * @param remoteEndpointId - endpoint id
     * @param remoteEndpointVersion - endpoint version
     * @return - mock server
     */
    public void oneUnhandledExceptionResponse(String remoteServiceId, String remoteEndpointId) {
    	stubUnhandledExceptionResponse(remoteServiceId, remoteEndpointId, 1);
    }
    
    /**
     * Build mock internal remote service that throws unhandled technical exception
     * 
     * @param remoteServiceId - service id
     * @param remoteEndpointId - endpoint id
     * @param remoteEndpointVersion - endpoint version
     * @return - mock server
     */
    public void oneUnhandledExceptionResponse(String remoteServiceId, String remoteEndpointId, Integer remoteEndpointVersion) {
    	stubUnhandledExceptionResponse(remoteServiceId, remoteEndpointId, remoteEndpointVersion);
    }
    
    /*
     * Build Mock for many responses (stateful  behavior)
     */
    public void manyResponses(StatefullBehaviorStubs statefulStubs) {
    	if(!statefulStubs.lastExist) {
    		throw new IllegalStateException("No last response stub exist. Make sure you have added a stub using addLast api");
    	}
    	 
    	 statefulStubs
    		.stubs
    		.stream()
    		.forEach(stub -> stubStatefulResponse(stub, statefulStubs.scenario));
    }
    
    /*
     * Reset the gateway server by removing all request mappings. This should be call after each test runs
     */
    public void reset() {
    	server.resetAll();
    }
    
    public void shutDown() {
    	server.shutdown();
    }
    
    private void stubStatefulResponse(Stub stub, String scenario) {
    	boolean isFinal = stub.responseType.equals(Stub.UNHANDLED_TECHNICAL_RESPONSE) ? true : stub.finalStub;
    	Boolean canThrow = stub.responseType.equals(Stub.BUSINE_RULE_VIOLATION_RESPONSE) ? Boolean.TRUE : null;
    	
    	ScenarioMappingBuilder mappingBuilder = post(urlEqualTo("/"))
    									.inScenario(scenario)
    									.whenScenarioStateIs(stub.currentState)
										.withHeader(SVCID_HEADER, equalTo(stub.remoteServiceId))
										.withHeader(EPID_HEADER, equalTo(stub.remoteServiceEndpointId))
										.withHeader(EPVER_HEADER, equalTo(stub.remoteServiceEndpointVersion.toString()));
    									
		ResponseDefinitionBuilder respDef = stub.responseType.equals(Stub.UNHANDLED_TECHNICAL_RESPONSE) ?
				aUnhandledTechnicalResponse(stub.remoteServiceId) : aSuccessResponse(stub.response, canThrow);
		mappingBuilder.willReturn(respDef);
    	
    	if(!isFinal) {
    		mappingBuilder.willSetStateTo(stub.nextState);
    	}
    	server.stubFor(mappingBuilder);
    	
    }
    
    
    private void stubSuccessResponseForDependentInternalService(String serviceId, String endpoinId, Integer endpointVersion, Boolean canThrow, Object response) {
    	server.stubFor(
    			post(urlEqualTo("/"))
    			.withHeader(SVCID_HEADER, equalTo(serviceId))
    			.withHeader(EPID_HEADER, equalTo(endpoinId))
    			.withHeader(EPVER_HEADER, equalTo(endpointVersion.toString()))
    			.willReturn(aSuccessResponse(response, canThrow))
    			);
    }
    

    
    
    private void stubUnhandledExceptionResponse(String remoteServiceId, String remoteEndpointId, Integer remoteEndpointVersion) {
    	server.stubFor(
    			post(urlEqualTo("/"))
    			.withHeader(SVCID_HEADER, equalTo(remoteServiceId))
    			.withHeader(EPID_HEADER, equalTo(remoteEndpointId))
    			.withHeader(EPVER_HEADER, equalTo(remoteEndpointVersion.toString()))
    			.willReturn(aUnhandledTechnicalResponse(remoteServiceId))
    			);
    }
    
    
    private ResponseDefinitionBuilder aSuccessResponse(Object response, Boolean canThrow) {
    	ResponseDefinitionBuilder respBuilder = aResponse();
    	
        if (canThrow != null && canThrow) {
        	respBuilder.withHeader(STATUS_HEADER, EPRICER_BRE_HEADER);
        	respBuilder.withHeader(THROWS_HEADER, Boolean.toString(canThrow));
        } else {
        	respBuilder.withHeader(STATUS_HEADER, EPRICER_STATUS_SUCCESS);
        }
    	
    	return
    			respBuilder
    			.withStatus(OK)
    			.withBody(marshallResultToJson(response));
    	
    }

    
    private ResponseDefinitionBuilder aUnhandledTechnicalResponse(String remoteServiceId) {
    	String response = "@" + remoteServiceId + " Oops\nMyType|myMethod|myfile.java|12";
    	return
    			aResponse()
    			.withStatus(INTERNAL_SERVER_ERROR)
    			.withHeader("Content-Type", HttpConstants.UTE_MEDIA_TYPE.toString())
    			.withBody(response);
    }
    

    
    /**
     * Marshall object to JSON
     * 
     * @param result - input
     * @return - JSON representation of the input
     */
    public String marshallResultToJson(Object result) {
        String response = null;
        try {
            response = mapper.writeValueAsString(result);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Failed to marshall service response into JSON", e);
        }
        return response;
    }
    
}
