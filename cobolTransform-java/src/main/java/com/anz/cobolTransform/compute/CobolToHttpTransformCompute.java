package com.anz.cobolTransform.compute;
import com.anz.cobolTransform.error.CobolToHttpTransformer;
import com.anz.cobolTransform.transform.pojo.PurchaseData;
import com.anz.common.compute.IParser;
import com.anz.common.compute.TransformType;
import com.anz.common.compute.impl.CommonCobolTransformCompute;
import com.anz.common.compute.impl.DFDLParser;
import com.anz.common.compute.impl.JsonParser;
import com.anz.common.transform.ITransformer;


public class CobolToHttpTransformCompute extends CommonCobolTransformCompute<PurchaseData, PurchaseData> {

	@Override
	public TransformType getTransformationType() {
		return TransformType.HTTP_MQ;
	}

	@Override
	public IParser<PurchaseData> getInputParser() {
		return new DFDLParser<>(PurchaseData.class);
	}

	@Override
	public IParser<PurchaseData> getOutputParser() {
		return new JsonParser<>(PurchaseData.class);
	}

	@Override
	public ITransformer<PurchaseData, PurchaseData> getTransformer() {
		return new CobolToHttpTransformer();
	}
	
	

}
