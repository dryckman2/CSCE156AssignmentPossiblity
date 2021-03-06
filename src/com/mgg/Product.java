package com.mgg;

/**
 * Products is catch all for items that are not services or subscriptions
 * 
 * @author Matthew Bigge and David Ryckman
 * @cseLogin mbigge
 * @date
 *
 */
public class Product extends Item {

	private String type;
	private boolean used;

	public Product(String code, String type, String name, double basePrice, boolean used) {
		super(code, name, basePrice);
		this.type = type;
		this.used = used;
	}

	@Override
	public String getType() {
		return this.type;
	}

	@Override
	public String toXMLString(int tabs) {
		String data = null, offset = "";
		for (int i = 1; i <= tabs; i++) {
			offset += "\t";
		}
		if (used) {
			data = offset + "<UsedProduct>\n";
			data += offset + "\t<ItemCode>" + this.getCode() + "</ItemCode>\n";
			data += offset + "\t<Name>" + this.getName() + "</Name>\n";
			data += offset + "\t<Price>" + this.getBasePrice() + "</Price>\n";
			data += offset + "</UsedProduct>\n";
		} else {
			data = offset + "<NewProduct>\n";
			data += offset + "\t<ItemCode>" + this.getCode() + "</ItemCode>\n";
			data += offset + "\t<Name>" + this.getName() + "</Name>\n";
			data += offset + "\t<Price>" + this.getBasePrice() + "</Price>\n";
			data += offset + "</NewProduct>\n";
		}
		return data;
	}

	public boolean getUsed() {
		return used;
	}

}
