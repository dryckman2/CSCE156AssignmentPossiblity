package com.mgg;

/**
 * Class to set template for Services
 * 
 * @author David Ryckman and Matt Bigge
 *
 */
public class Service extends Item {

	public Service(String code, String name, double basePrice) {
		super(code, name, basePrice);
	}

	@Override
	public String getType() {
		return "SV";
	}

	@Override
	public String toXMLString(int tabs) {
		String data, offset = "";
		for (int i = 1; i <= tabs; i++) {
			offset += "\t";
		}
		data = offset + "<Service>\n";
		data += offset + "\t<ItemCode>" + this.getCode() + "</ItemCode>\n";
		data += offset + "\t<Name>" + this.getName() + "</Name>\n";
		data += offset + "\t<HourlyRate>" + this.getBasePrice() + "</HourlyRate>\n";
		data += offset + "</Service>\n";
		return data;
	}

}
