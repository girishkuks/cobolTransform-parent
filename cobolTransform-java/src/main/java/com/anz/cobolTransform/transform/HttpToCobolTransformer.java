/**
 * 
 */
package com.anz.cobolTransform.transform;

import java.math.BigDecimal;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.anz.cobolTransform.transform.pojo.CustomerName;
import com.anz.cobolTransform.transform.pojo.PurchaseData;
import com.anz.cobolTransform.transform.pojo.PurchaseData.Invoice;
import com.anz.cobolTransform.transform.pojo.PurchaseData.Invoice.Item;
import com.anz.cobolTransform.transform.pojo.PurchaseData.Invoice.Item.ItemGrocery;
import com.anz.common.compute.ComputeInfo;
import com.anz.common.transform.ITransformer;
import com.anz.common.transform.TransformUtils;

/**
 * @author sanketsw
 * 
 */
public class HttpToCobolTransformer implements
		ITransformer<CustomerName, PurchaseData> {
	private static final Logger logger = LogManager.getLogger();

	@Override
	public PurchaseData execute(CustomerName input, Logger appLogger,
			ComputeInfo metadata) throws Exception {
		
		logger.info(input!= null? input.getSurname(): input);

		PurchaseData p = new PurchaseData();
		if (input == null || input.getSurname() == null) {
			throw new NullPointerException("Invalid input");
		}
		p.setCustomerSurname(input.getSurname());

		p.getCustomerInitial().add("s");
		p.getCustomerInitial().add("d");

		p.setItemCount(1);
		p.setInvoice(new Invoice());

		ItemGrocery i = new ItemGrocery();
		i.setDescription("Winged Bovine Power Drink");
		i.setItemCode("012RBU0002");
		i.setPrice(new BigDecimal(2.99));
		i.setQuantity(6);
		i.setSellByDate(20150731);

		Item item = new Item();
		item.setItemType("G");
		item.setItemGrocery(i);

		p.getInvoice().getItem().add(item);
		return p;
	}

}
