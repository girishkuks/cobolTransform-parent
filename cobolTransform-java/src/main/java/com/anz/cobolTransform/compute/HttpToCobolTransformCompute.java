package com.anz.cobolTransform.compute;

import com.anz.cobolTransform.error.HttpToCobolTransformer;
import com.anz.cobolTransform.transform.pojo.CustomerName;
import com.anz.cobolTransform.transform.pojo.PurchaseData;
import com.anz.common.compute.IParser;
import com.anz.common.compute.TransformType;
import com.anz.common.compute.impl.CommonCobolTransformCompute;
import com.anz.common.compute.impl.DFDLParser;
import com.anz.common.compute.impl.JsonParser;
import com.anz.common.transform.ITransformer;

public class HttpToCobolTransformCompute extends CommonCobolTransformCompute<CustomerName, PurchaseData> {

	@Override
	public TransformType getTransformationType() {
		return TransformType.HTTP_MQ;
	}

	@Override
	public IParser<CustomerName> getInputParser() {
		return new JsonParser<>(CustomerName.class);
	}

	@Override
	public IParser<PurchaseData> getOutputParser() {
		return new DFDLParser<>(PurchaseData.class);
	}

	@Override
	public ITransformer<CustomerName, PurchaseData> getTransformer() {
		return new HttpToCobolTransformer();
	}
	

}
