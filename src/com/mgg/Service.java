package com.mgg;

public class Service extends Item{

	private String employeeCode;
	public String getEmployeeCode() {
		return employeeCode;
	}

	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	public double getNumOfHours() {
		return numOfHours;
	}

	public void setNumOfHours(double numOfHours) {
		this.numOfHours = numOfHours;
	}

	private double numOfHours;
	
	
	public Service(String code, String name, String basePrice) {
		super(code, name, basePrice);
		// TODO Auto-generated constructor stub
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
