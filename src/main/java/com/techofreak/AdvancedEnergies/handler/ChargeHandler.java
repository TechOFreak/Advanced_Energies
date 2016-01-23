package com.techofreak.AdvancedEnergies.handler;

import java.util.ArrayList;

import com.techofreak.AdvancedEnergies.AdvancedEnergies;

import net.minecraft.item.ItemStack;

public class ChargeHandler {

	private ArrayList<ChargeProperties> validCharges = new ArrayList<ChargeProperties>();
	
	public ChargeHandler(){
		registerCharges();
	}
	
	public boolean isValidCharge(String displayName){
		for(ChargeProperties entry:validCharges){
			if(displayName.equals(entry.getChargeDisplayName())){
				return true;
			}
		}
		return false;
	}
	
	private void registerCharges(){
		//All charges to be stored
		validCharges.add(new ChargeProperties(new ItemStack(AdvancedEnergies.itemRadiantCrystal).getDisplayName(), 100));
		validCharges.add(new ChargeProperties(new ItemStack(AdvancedEnergies.itemRadiantCrystalDust).getDisplayName(), 200));
	}
	
	public void registerNewCharge(String displayName, int chargeAmount){
		//Can be used to add a new charge item based on the display name
		validCharges.add(new ChargeProperties(displayName, chargeAmount));
	}
	
	public int getItemChargeAmount(String displayName){
		if(isValidCharge(displayName)){
			for(ChargeProperties entry:validCharges){
				if(entry.getChargeDisplayName().equals(displayName)){
					return entry.getChargeAmount();
				}
			}
		}
		return 0;
	}
}