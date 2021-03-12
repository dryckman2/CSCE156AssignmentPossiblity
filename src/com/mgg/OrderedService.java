package com.mgg;

/**
 * Takes Service template and creates specific version to be inserted into list
 * of purchased
 * 
 * @author Matthew Bigge and David Ryckman
 *
 */
public class OrderedService extends Service implements Purchased {

	private String employeeCode;
	private double numOfHours;
	private String empName;

	public OrderedService(Service Template, String employeeCode, double numOfHours, String empName) {
		super(Template.getCode(), Template.getName(), Template.getBasePrice());
		this.employeeCode = employeeCode;
		this.numOfHours = numOfHours;
		this.empName = empName;
	}

	@Override
	public double getSubTotal() {
		return Sale.changeRound(this.getBasePrice() * numOfHours);
	}

	public double getTaxTotal() {
		return Sale.changeRound(getSubTotal() * 0.0285);
	}

	public void printReport() {
		System.out.println(this.getName());
		String formating = "\t(Service #" + this.getCode() + " by " + empName + " " + numOfHours + "hrs at $"
				+ this.getBasePrice() + "/hr)";
		System.out.printf("%-60s $ %.2f\n", formating, this.getSubTotal());
	}

}
