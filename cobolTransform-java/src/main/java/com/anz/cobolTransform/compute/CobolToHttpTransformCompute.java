package com.anz.cobolTransform.compute;
import com.anz.cobolTransform.transform.CobolToHttpTransformer;
import com.anz.cobolTransform.transform.pojo.PurchaseData;
import com.anz.common.compute.ComputeInfo;
import com.anz.common.compute.IParser;
import com.anz.common.compute.TransformType;
import com.anz.common.compute.impl.CustomParserTransformCompute;
import com.anz.common.compute.impl.JaxbDFDLParser;
import com.anz.common.compute.impl.JsonBlobParser;
import com.anz.common.transform.ITransformer;
import com.ibm.broker.plugin.MbMessageAssembly;


public class CobolToHttpTransformCompute extends CustomParserTransformCompute<PurchaseData, PurchaseData> {

	@Override
	public TransformType getTransformationType() {
		return TransformType.HTTP_MQ;
	}

	@Override
	public IParser<PurchaseData> getInputParser() {
		return new JaxbDFDLParser<>(PurchaseData.class);
	}

	@Override
	public IParser<PurchaseData> getOutputParser() {
		return new JsonBlobParser<>(PurchaseData.class);
	}

	@Override
	public ITransformer<PurchaseData, PurchaseData> getTransformer() {
		return new CobolToHttpTransformer();
	}

	@Override
	public void prepareForTransformation(ComputeInfo metadata,
			MbMessageAssembly inAssembly, MbMessageAssembly outAssembly) {
		// TODO Auto-generated method stub
		
	}
	
	

}
