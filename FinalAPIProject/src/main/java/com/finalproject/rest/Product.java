package com.finalproject.rest;

public class Product {
	public int productCode;
	public String dateOffered;
	public String dateRetired;
	public String name;
	public String productTypeCode;

	public int getProductCode() {
		return productCode;
	}

	public void setProductCode(int productCode) {
		this.productCode = productCode;
	}

	public String getDateOffered() {
		return dateOffered;
	}

	public void setDateOffered(String dateOffered) {
		this.dateOffered = dateOffered;
	}

	public String getDateRetired() {
		return dateRetired;
	}

	public void setDateRetired(String dateRetired) {
		this.dateRetired = dateRetired;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProductTypeCode() {
		return productTypeCode;
	}

	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}

}
