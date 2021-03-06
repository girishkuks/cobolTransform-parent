package com.anz.cobolTransform.compute;

import com.anz.cobolTransform.transform.HttpToCobolTransformer;
import com.anz.cobolTransform.transform.pojo.CustomerName;
import com.anz.cobolTransform.transform.pojo.PurchaseData;
import com.anz.common.compute.ComputeInfo;
import com.anz.common.compute.IParser;
import com.anz.common.compute.TransformType;
import com.anz.common.compute.impl.CustomParserTransformCompute;
import com.anz.common.compute.impl.JaxbDFDLParser;
import com.anz.common.compute.impl.JsonBlobParser;
import com.anz.common.transform.ITransformer;
import com.ibm.broker.plugin.MbMessageAssembly;

public class HttpToCobolTransformCompute extends CustomParserTransformCompute<CustomerName, PurchaseData> {

	@Override
	public TransformType getTransformationType() {
		return TransformType.HTTP_MQ;
	}

	@Override
	public IParser<CustomerName> getInputParser() {
		return new JsonBlobParser<CustomerName>(CustomerName.class);
	}

	@Override
	public IParser<PurchaseData> getOutputParser() {
		return new JaxbDFDLParser<PurchaseData>(PurchaseData.class);
	}

	@Override
	public ITransformer<CustomerName, PurchaseData> getTransformer() {
		return new HttpToCobolTransformer();
	}

	

}
