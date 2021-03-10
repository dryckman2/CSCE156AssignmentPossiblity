package com.mgg;

public class Product extends Item {

	private String type;

	public Product(String code, String type, String name, String basePrice) {
		super(code, name, basePrice);
		this.type = type;
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
		switch(type){
			case "PU":
				data = offset +"<UsedProduct>\n";
				data += offset +"\t<ItemCode>" + this.getCode() + "</ItemCode>\n";
				data += offset +"\t<Name>" + this.getName() + "</Name>\n";
				data += offset +"\t<Price>" + this.getBasePrice() + "</Price>\n";
				data += offset +"</UsedProduct>\n";
			break;
			case "PN":
				data = offset + "<NewProduct>\n";
				data += offset + "\t<ItemCode>" + this.getCode() + "</ItemCode>\n";
				data += offset + "\t<Name>" + this.getName() + "</Name>\n";
				data += offset + "\t<Price>" + this.getBasePrice() + "</Price>\n";
				data += offset + "</NewProduct>\n";	
			break;	
			case "PG":
				data = offset + "<GiftCard>\n";
				data += offset + "\t<ItemCode>" + this.getCode() + "</ItemCode>\n";
				data += offset + "\t<Name>" + this.getName() + "</Name>\n";
				data += offset + "</GiftCard>\n";
			break;
		}
		return data;
	}

}
