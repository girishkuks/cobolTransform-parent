/**
 * 
 */
package com.anz.cobolTransform.compute;

import com.anz.common.compute.LoggingTerminal;
import com.anz.common.compute.impl.CommonLoggingCompute;

/**
 * @author sanketsw
 *
 */
public class LoggingOutputCompute extends CommonLoggingCompute {

	/* (non-Javadoc)
	 * @see com.anz.common.compute.impl.CommonLoggingCompute#getLogTerminal()
	 */
	@Override
	public LoggingTerminal getLogTerminal() {
		return LoggingTerminal.OUTPUT;
	}

}