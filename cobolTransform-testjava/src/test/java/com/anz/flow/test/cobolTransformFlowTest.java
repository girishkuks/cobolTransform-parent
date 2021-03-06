/**
 * 
 */
package com.anz.flow.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import com.anz.cobolTransform.transform.pojo.CustomerName;
import com.anz.cobolTransform.transform.pojo.PurchaseData;
import com.anz.common.error.ExceptionMessage;
import com.anz.common.test.FlowTest;
import com.anz.common.transform.TransformUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.ibm.broker.config.proxy.AttributeConstants;
import com.ibm.broker.config.proxy.ConfigManagerProxyLoggedException;
import com.ibm.broker.config.proxy.ConfigManagerProxyPropertyNotInitializedException;
import com.ibm.broker.config.proxy.ExecutionGroupProxy;
import com.ibm.broker.config.proxy.MessageFlowProxy;
import com.ibm.broker.config.proxy.RecordedTestData;

/**
 * @author sanketsw
 * 
 */
public class cobolTransformFlowTest extends FlowTest {

	private static final Logger logger = LogManager.getLogger();
	
	private Gson gson = new Gson();
	ObjectMapper objectMapper = new ObjectMapper();

	private static final String TEST_FILE_001 = "cobolTransform.Test001.xml";
	private static final String applicationName = "cobolTransform-app";
	private static final String flowName = "Main";
	private static final String injectNodeName = "HTTP Input";
	private static final String MESSAGE_FORMAT = "MessageFormat.xml";
	//private static final String MESSAGE_FORMAT = "NewMessageFormat.xml";
	
	@Override
	@Before
	public void setup()
			throws ConfigManagerProxyPropertyNotInitializedException,
			ConfigManagerProxyLoggedException, IOException {
		super.setup();	
		
		ExecutionGroupProxy serverProxy = getIntegrationServerProxy();
		MessageFlowProxy flowProxy = serverProxy.getMessageFlowByName(flowName, applicationName, null);
		
		setFlowProxy(flowProxy);
	}
	
	public void injectData() throws IOException, ConfigManagerProxyPropertyNotInitializedException, ConfigManagerProxyLoggedException {
		
		logger.info("injecting data...");
		// load test data from file
		String message = IOUtils.toString(cobolTransformFlowTest.class.getResourceAsStream(TEST_FILE_001));
		String jsonBlob = TransformUtils.getBlob(message);
		String messageFormat = IOUtils.toString(cobolTransformFlowTest.class.getResourceAsStream(MESSAGE_FORMAT));
		message = messageFormat.replace("MESSAGE_FORMAT", jsonBlob);
		
		Properties injectProps = new Properties();
		injectProps.setProperty(AttributeConstants.DATA_INJECTION_APPLICATION_LABEL, applicationName); 		
		injectProps.setProperty(AttributeConstants.DATA_INJECTION_MESSAGEFLOW_LABEL, flowName); 			
		injectProps.setProperty(AttributeConstants.DATA_INJECTION_NODE_UUID, getNodeUUID(injectNodeName));
		injectProps.setProperty(AttributeConstants.DATA_INJECTION_WAIT_TIME, "60000");
		injectProps.setProperty(AttributeConstants.DATA_INJECTION_MESSAGE_SECTION, message);

		
		// execute flow in synchronous mode
		boolean result = getIntegrationServerProxy().injectTestData(injectProps, true);
		logger.info("Message injected = {}", result);
		
	}
	
	@Test
	public void testMainFlow() throws ConfigManagerProxyPropertyNotInitializedException, ConfigManagerProxyLoggedException, IOException, XPathExpressionException, SAXException, ParserConfigurationException, TransformerException, JSONException, InterruptedException {
		
		//Inject test message
		injectData();
		
		//Test individual node outputs
		testHttpInput();
		testPreTransformNodeOutput();
		testResponseOnQueue();
		testPostTransformOutput();
		

	}
	
	public void testHttpInput() throws ConfigManagerProxyPropertyNotInitializedException, XPathExpressionException, SAXException, IOException, ParserConfigurationException {	
		
		// PreTransform Node
		List<RecordedTestData> dataList = getTestDataList("HTTP Input");
				
		String json = getNodeOutputJsonStringFromBlob(dataList.get(0));
		CustomerName out = gson.fromJson(json, CustomerName.class);

		assertNotNull(out);
		assertEquals("Deshpande", out.getSurname());

		
	}
	
	
	public void testPreTransformNodeOutput() throws ConfigManagerProxyPropertyNotInitializedException, XPathExpressionException, SAXException, IOException, ParserConfigurationException {	
		
		// PreTransform Node
		List<RecordedTestData> dataList = getTestDataList("Transform Request");
		
		Node n = getNodeOutput(dataList.get(0), "/message/DFDL/PurchaseData/CustomerSurname");
		assertNotNull(n);

		assertEquals("Deshpande", n.getTextContent());
		
	}
	
	
	public void testResponseOnQueue() throws ConfigManagerProxyPropertyNotInitializedException, XPathExpressionException, SAXException, IOException, ParserConfigurationException, InterruptedException {	
		
		// PreTransform Node
		List<RecordedTestData> dataList = getTestDataList("Get Response");
		
		Node n = getNodeOutput(dataList.get(0), "/message/DFDL/PurchaseData/CustomerSurname");
		assertNotNull(n);

		assertEquals("Deshpande", n.getTextContent());
			
	}
	
	public void testPostTransformOutput() throws ConfigManagerProxyPropertyNotInitializedException, XPathExpressionException, SAXException, IOException, ParserConfigurationException {	
		
		// PreTransform Node
		List<RecordedTestData> dataList = getTestDataList("Transform Response");
				
		String json = getNodeOutputJsonStringFromBlob(dataList.get(0));
		PurchaseData out = gson.fromJson(json, PurchaseData.class);

		assertNotNull(out);
		assertEquals("Deshpande", out.getCustomerSurname());

		
	}
}
