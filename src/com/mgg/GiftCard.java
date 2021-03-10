package com.mgg;

public class GiftCard extends Item {

	private double amount;

	public GiftCard(String code, String name, double basePrice) {
		super(code, name, basePrice);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getType() {
		return "PG";
	}
	
	public void setAmount(double a) {
		amount = a;
	}
	
	@Override
	public double getCost() {
		return amount;
	}

	@Override
	public String toXMLString(int tabs) {
		String data = null, offset = "";
		for (int i = 1; i <= tabs; i++) {
			offset += "\t";
		}
		data = offset + "<GiftCard>\n";
		data += offset + "\t<ItemCode>" + this.getCode() + "</ItemCode>\n";
		data += offset + "\t<Name>" + this.getName() + "</Name>\n";
		data += offset + "</GiftCard>\n";
		return data;
	}

}
