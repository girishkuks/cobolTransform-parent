package com.anz.cobolTransform.transform.pojo;

import com.anz.common.transform.ITransformPojo;

/**
 * @author sanketsw
 *
 */
public class CustomerName implements ITransformPojo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6170611885142061297L;
			
	String surname;

	/**
	 * @return the surname
	 */
	public String getSurname() {
		return surname;
	}

	/**
	 * @param surname the surname to set
	 */
	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	

}
