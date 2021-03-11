package com.mgg;

/**
 * Purchased items are items that have been bought and have new data from
 * purchases
 * 
 * @author dmryc
 *
 */
public interface Purchased {

	public double getSubTotal();

	public double getTaxTotal();

	public String getName();

	public void printReport();

}
