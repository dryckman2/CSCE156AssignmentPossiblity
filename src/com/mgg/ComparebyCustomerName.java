package com.mgg;

import java.util.Comparator;

public class ComparebyCustomerName implements Comparator<Sale>{

	@Override
	public int compare(Sale o1, Sale o2) {
		int result = o2.getCustomer().getLastName().compareTo(o1.getCustomer().getLastName());
		if(result == 0) {
			result = o2.getCustomer().getFirstName().compareTo(o1.getCustomer().getFirstName());
		}
		return result;
	}
	
}
