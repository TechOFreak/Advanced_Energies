package com.techofreak.AdvancedEnergies.handler;

public class ChargeProperties {
	
	private final String displayName;
	private final int chargeAmount;
	
	public ChargeProperties(String displayName, int chargeAmount){
		this.displayName = displayName;
		this.chargeAmount = chargeAmount;
	}
	
	public String getChargeDisplayName(){
		return displayName;
	}
	
	public int getChargeAmount(){
		return chargeAmount;
	}
	
}
