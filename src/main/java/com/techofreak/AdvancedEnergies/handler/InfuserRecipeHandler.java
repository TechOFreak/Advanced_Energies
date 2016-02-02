package com.techofreak.AdvancedEnergies.handler;

import java.util.ArrayList;

import com.techofreak.AdvancedEnergies.AdvancedEnergies;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

public class InfuserRecipeHandler {

	private ArrayList<InfuserRecipeProperties> infuserRecipes = new ArrayList<InfuserRecipeProperties>(); 
	private ArrayList<String> validIngots = new ArrayList<String>();
	private ArrayList<String> validBase = new ArrayList<String>(); 
	
	public InfuserRecipeHandler(){
		registerInfuserRecipes();
	}

	public ItemStack getInfuserResult(String displayName1, String displayName2){
		for(InfuserRecipeProperties entry:infuserRecipes){
			if(displayName1.equals(entry.getIngot()) && displayName2.equals(entry.getBase())){
				return entry.getResult();
			}
		}
		return null;
	}
	
	private void registerInfuserRecipes() {
		String iron = new ItemStack(Items.iron_ingot).getDisplayName();
		String energizedBase = new ItemStack(AdvancedEnergies.itemEnergizedBase).getDisplayName();
		infuserRecipes.add(new InfuserRecipeProperties(iron, energizedBase, new ItemStack(AdvancedEnergies.itemEnergizedIron)));
		validIngots.add(iron);
		validBase.add(energizedBase);
	}
	
	public void addNewInfuserRecipe(String displayName1, String displayName2, ItemStack result){
		infuserRecipes.add(new InfuserRecipeProperties(displayName1, displayName2, result));
		validIngots.add(displayName1);
		validBase.add(displayName2);
	}
	
	public boolean isValidIngot(String displayName){
		for(String ingot:validIngots){
			if(displayName.equals(ingot)){
				return true;
			}
		}
		return false;
	}
	
	public boolean isValidBase(String displayName){
		for(String base:validBase){
			if(displayName.equals(base)){
				return true;
			}
		}
		return false;
	}
}
