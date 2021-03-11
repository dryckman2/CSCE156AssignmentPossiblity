package com.mgg;
/**
 * Class that sets up template gift cards 
 * @author Matthew Bigge and David Ryckman
 *
 */
public class GiftCard extends Item {


	public GiftCard(String code, String name, double basePrice) {
		super(code, name, basePrice);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getType() {
		return "PG";
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
