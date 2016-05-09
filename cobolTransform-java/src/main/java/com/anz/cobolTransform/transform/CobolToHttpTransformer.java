/**
 * 
 */
package com.anz.cobolTransform.transform;

import org.apache.logging.log4j.Logger;

import com.anz.cobolTransform.transform.pojo.PurchaseData;
import com.anz.common.compute.ComputeInfo;
import com.anz.common.transform.ITransformer;

/**
 * @author root
 *
 */
public class CobolToHttpTransformer implements ITransformer<PurchaseData, PurchaseData> {

	@Override
	public PurchaseData execute(PurchaseData input, Logger appLogger,
			ComputeInfo metadata) throws Exception {
	
		// perform any transformation here on input object 
		
		PurchaseData output = input;
		
		return output;
	}
	
}
