package com.mgg;
/**
 * Takes product template and creates specific version to be inserted into list of purchased
 * @author Matthew Bigge and David Ryckman
 *
 */
public class OrderedProduct extends Product implements Purchased {

	private double quantity;

	public OrderedProduct(Product Template, double orderdAmount) {
		super(Template.getCode(), Template.getType(), Template.getName(), Template.getBasePrice(), Template.getUsed());
		this.quantity = orderdAmount;
	}

	@Override
	public double getSubTotal() {
		return Sale.changeRound(this.getBasePrice() * quantity);
	}

	@Override
	public double getTaxTotal() {
		return Sale.changeRound(getSubTotal() * 0.0725);
	}
	
	public double getBasePrice(){
		double cost = super.getBasePrice();
		if (getUsed()) {
			cost = cost * .80;
		}
		return Sale.changeRound(cost);
		
	}

	public void printReport() {
		System.out.println(this.getName());
		String formating;
		if (this.getUsed()) {
			formating = "\t(Used Item #" + this.getCode() + "at $" + Sale.changeRound(this.getBasePrice() * 0.80) + " each)";
		} else {
			formating = "\t(New Item #" + this.getCode() + "at $" + this.getBasePrice() + " each)";
		}
		System.out.printf("%-60s $ %.2f\n", formating, this.getSubTotal());
	}

}
