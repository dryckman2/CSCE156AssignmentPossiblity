package com.mgg;

public class Subscription extends Item {

	public Subscription(String code, String name, double basePrice) {
		super(code, name, basePrice);
	}

	@Override
	public String getType() {
		return "SB";
	}

	@Override
	public String toXMLString(int tabs) {
		String data, offset = "";
		for (int i = 1; i <= tabs; i++) {
			offset += "\t";
		}
		data = offset + "<Subscription>\n";
		data += offset + "\t<ItemCode>" + this.getCode() + "</ItemCode>\n";
		data += offset + "\t<Name>" + this.getName() + "</Name>\n";
		data += offset + "\t<AnnualFee>" + this.getBasePrice() + "</AnnualFee>\n";
		data += offset + "</Subscription>\n";
		return data;
	}

	public double getCost() {
		return this.getBasePrice();
	}
}
