package com.techofreak.AdvancedEnergies.handler;

import net.minecraft.item.ItemStack;

public class InfuserRecipeProperties {

	private String ingot;
	private String base;
	private ItemStack result;
	
	public InfuserRecipeProperties(String ingot, String base, ItemStack result){
		this.ingot = ingot;
		this.base = base;
		this.result = result;
	}
	
	public String getIngot(){
		return ingot;
	}
	
	public String getBase(){
		return base;
	}
	
	public ItemStack getResult(){
		return result;
	}
}
