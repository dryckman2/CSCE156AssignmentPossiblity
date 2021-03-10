package com.mgg;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Subscription extends Item {
	private LocalDate beginDate;
	private LocalDate endDate;

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

	public LocalDate getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		String tokens[] = beginDate.split("-");
		int year = Integer.parseInt(tokens[0]);
		int month = Integer.parseInt(tokens[1]);
		int day = Integer.parseInt(tokens[2]);
		this.beginDate = LocalDate.of(year,month,day);
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		String tokens[] = endDate.split("-");
		int year = Integer.parseInt(tokens[0]);
		int month = Integer.parseInt(tokens[1]);
		int day = Integer.parseInt(tokens[2]);
		this.endDate = LocalDate.of(year,month,day);
	}

	@Override
	public double getCost() {
		long days = ChronoUnit.DAYS.between(beginDate,endDate) + 1;
		double cost = basePrice * days;
		return cost;
	}
}
