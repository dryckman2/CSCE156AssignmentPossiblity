package com.mgg;
/**
 * Takes gift card template and creates specific version to be inserted into list of purchased
 * @author Matthew Bigge and David Ryckman
 *
 */
public class OrderedGiftCard extends GiftCard implements Purchased{
	
	private double amount;

	public OrderedGiftCard(GiftCard template,double amount) {
		super( template.getCode(), template.getName(), template.getBasePrice());
		this.amount = amount;
	}

	@Override
	public double getSubTotal() {
		return Sale.changeRound(amount);
	}

	@Override
	public double getTaxTotal() {
		return Sale.changeRound(getSubTotal() * 0.0725);
	}
	
	public void printReport() {
		System.out.println(this.getName());
		String formating = "\t(Gift Card #" + this.getCode() + ")";
		System.out.printf("%-60s $ %.2f\n", formating, this.getSubTotal());
	}

}
