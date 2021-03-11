package com.mgg;

public class OrderedProduct extends Product implements Purchased {

	private double quantity;

	public OrderedProduct(Product Template, double orderdAmount) {
		super(Template.getCode(), Template.getType(), Template.getName(), Template.getBasePrice(), Template.getUsed());
		this.quantity = orderdAmount;
	}

	@Override
	public double getSubTotal() {
		double cost = this.getBasePrice() * quantity;
		if (getUsed()) {
			cost = cost * .80;
		}
		return Sale.changeRound(cost);
	}

	@Override
	public double getTaxTotal() {
		return getSubTotal() * 0.0725;
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
