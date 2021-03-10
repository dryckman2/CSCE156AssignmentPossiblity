package com.mgg;

public class Subscription extends Item {
	private String beginDate;
	private String endDate;

	public Subscription(String code, String name, String basePrice) {
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

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
