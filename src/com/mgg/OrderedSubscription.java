package com.mgg;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class OrderedSubscription extends Subscription implements Purchased {

	private LocalDate beginDate;
	private LocalDate endDate;

	public OrderedSubscription(Subscription Template, String begin, String end) {
		super(Template.getCode(), Template.getName(), Template.getBasePrice());
		//Set Begin
		String btokens[] = begin.split("-");
		int year = Integer.parseInt(btokens[0]);
		int month = Integer.parseInt(btokens[1]);
		int day = Integer.parseInt(btokens[2]);
		this.beginDate = LocalDate.of(year,month,day);
		//Set End
		String etokens[] = end.split("-");
		 year = Integer.parseInt(etokens[0]);
		 month = Integer.parseInt(etokens[1]);
		 day = Integer.parseInt(etokens[2]);
		this.endDate = LocalDate.of(year,month,day);
	}

	@Override
	public double getSubTotal() {
		long days = ChronoUnit.DAYS.between(beginDate,endDate) + 1;
		double cost = (this.getBasePrice() / 365)* days;
		return Sale.changeRound(cost);
	}
	
	public double getTaxTotal() {
		return 0;
	}
	
	public int getDaysLong() {
		int days = (int)ChronoUnit.DAYS.between(beginDate,endDate) + 1;
		return days;
	}

	@Override
	public void printReport() {
		System.out.println(this.getName());
		String formating = "\t(Subscription #" + this.getCode() + " " + this.getDaysLong() + "days at %" + this.getBasePrice() + " per year)";
		System.out.printf("%-60s $ %.2f\n", formating, this.getSubTotal());
	}

}
