
/**
 * 
 */
package com.anz.cobolTransform.compute;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.common.compute.ComputeInfo;
import com.anz.common.compute.TransformType;
import com.anz.common.compute.impl.CommonJavaCompute;
import com.anz.common.compute.impl.ComputeUtils;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbMessageAssembly;

/**
 * @author sanketsw & psamon
 *
 */
public class AddUserDefinedProperties extends CommonJavaCompute {

	/* (non-Javadoc)
	 * @see com.anz.common.compute.impl.CommonJsonJsonTransformCompute#getTransformer()
	 */
	
	private static final Logger logger = LogManager.getLogger();

	@Override
	public void execute(MbMessageAssembly inAssembly,
			MbMessageAssembly outAssembly) throws Exception {
		
		/*// Set message ID and Correlation ID to LocalEnvironement/Destination/HTTP/RequestIdentifier
		MbElement msgId = outAssembly.getMessage().getRootElement().getFirstElementByPath("/MQMD/MsgId");
		MbElement correlId = outAssembly.getMessage().getRootElement().getFirstElementByPath("/MQMD/CorrelId");
		logger.info(msgId);
		
		msgId.setValue(outAssembly.getLocalEnvironment().getRootElement().getFirstElementByPath("/Destination/HTTP/RequestIdentifier").getValue());
		correlId.setValue(msgId.getValue());
		
		MbElement requestIdentifier = outAssembly.getLocalEnvironment().getRootElement().getFirstElementByPath("/Destination/HTTP/RequestIdentifier");
		logger.info(requestIdentifier);
		
		// During Flow test, the requestIdentifier is not set by integration server proxy. Hence it it set to Transaction id for unit test purpose
		MbElement transactionId = outAssembly.getMessage().getRootElement().getFirstElementByPath("/HTTPInputHeader/Transaction-Id");
		logger.info(transactionId);
		if(requestIdentifier == null && transactionId != null) {
			requestIdentifier = ComputeUtils.setElementInTree(transactionId.getValue(), outAssembly.getLocalEnvironment(), "Destination", "HTTP", "RequestIdentifier");
		}
		
		if(requestIdentifier != null) {
			msgId.setValue(requestIdentifier.getValue());
			correlId.setValue(requestIdentifier.getValue());
		}*/
				
		
		// Set ReplyToQ MQMD field to the UDP replyQueue
		MbElement replyToQ = ComputeUtils.setElementInTree(getUserDefinedAttribute("REPLY_QUEUE"), outAssembly.getMessage(), "MQMD","ReplyToQ");
		logger.info("{} = {}", replyToQ.getName(), replyToQ.getValueAsString());
		
		// Set the Local Environment MQ output queue parameter to the UDP providerQueue
		MbElement providerQ = ComputeUtils.setElementInTree(getUserDefinedAttribute("PROVIDER_QUEUE"), outAssembly.getLocalEnvironment(),"Destination", "MQ","DestinationData","queueName");
		logger.info("{} = {}", providerQ.getName(), providerQ.getValueAsString());	
	}

	@Override
	public TransformType getTransformationType() {
		// TODO Auto-generated method stub
		return TransformType.HTTP_MQ;
	}

	@Override
	public void prepareForTransformation(ComputeInfo metadata,
			MbMessageAssembly inAssembly, MbMessageAssembly outAssembly) {
		// TODO Auto-generated method stub
		
	}
}
