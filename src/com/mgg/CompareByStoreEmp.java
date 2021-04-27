package com.mgg;

/**
 * Bare Bones Comparator for sorting Sales by Store followed by employee name
 */
import java.util.Comparator;

public class CompareByStoreEmp implements Comparator<Sale> {

	public int compare(Sale o1, Sale o2) {
		int result = o2.getStoreCode().compareTo(o1.getStoreCode());
		if (result == 0) {
			result = o2.getEmployee().getLastName().compareTo(o1.getEmployee().getLastName());
			if (result == 0) {
				result = o2.getEmployee().getFirstName().compareTo(o1.getEmployee().getFirstName());
			}
		}
		return result;
	}

}
