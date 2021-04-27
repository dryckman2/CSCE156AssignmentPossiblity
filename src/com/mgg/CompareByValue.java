package com.mgg;
/**
 * Bare Bones Comparator for sorting Sales by Total
 */
import java.util.Comparator;

public class CompareByValue implements Comparator<Sale>{

	public int compare(Sale o1, Sale o2) {
		return (int)Math.ceil((o1.getTotal() - o2.getTotal()));
	}

}
