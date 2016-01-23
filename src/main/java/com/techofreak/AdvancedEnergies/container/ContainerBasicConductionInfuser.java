package com.techofreak.AdvancedEnergies.container;

import com.techofreak.AdvancedEnergies.tileentity.TileEntityBasicConductionInfuser;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;

public class ContainerBasicConductionInfuser extends Container {

	private TileEntityBasicConductionInfuser basicConductionInfuser;
	
	//Like burn time
	public int lastChargeUsageTime;
	//Like current item burn time
	public int lastCurrentItemUsageTime;
	//Like cook time
	public int lastProcessingTime;
	
	public int maxChargeStorage = 50000;
	public int lastStoredCharge;
	
	public ContainerBasicConductionInfuser(InventoryPlayer inventory, TileEntityBasicConductionInfuser tileentity){
		this.basicConductionInfuser = tileentity;
		
		//Slot 0 is 61,20
		this.addSlotToContainer(new Slot(tileentity, 0, 62, 21));
		//Slot 1 is 61,42
		this.addSlotToContainer(new Slot(tileentity, 1, 62, 42));
		//Slot 2 is 26,47
		this.addSlotToContainer(new Slot(tileentity, 2, 27, 48));
		//Slot 3 is 115,32
		this.addSlotToContainer(new SlotFurnace(inventory.player, tileentity, 3, 116, 33));
		//Slot 4 is 151,13
		this.addSlotToContainer(new Slot(tileentity, 4, 152, 14));
		//Slot 5 is 151,30
		this.addSlotToContainer(new Slot(tileentity, 5, 152, 31));
		//Slot 6 is 151,47
		this.addSlotToContainer(new Slot(tileentity, 6, 152, 48));
		
		for(int i = 0; i < 3; i++){
			for(int j = 0; j < 9; j++){
				this.addSlotToContainer(new Slot(inventory, j + i*9 + 9, 8 + j*18, 84 + i*18));
			}
		}
		
		for(int i = 0; i < 9; i++){
			this.addSlotToContainer(new Slot(inventory, i, 8 + i*18, 142));
		}
	}
	
	public void addCraftingToCrafters(ICrafting icrafting){
		super.addCraftingToCrafters(icrafting);
		icrafting.sendProgressBarUpdate(this, 0, this.basicConductionInfuser.processingTime);
		icrafting.sendProgressBarUpdate(this, 2, this.basicConductionInfuser.chargeUsageTime);
		icrafting.sendProgressBarUpdate(this, 3, this.basicConductionInfuser.currentItemUsageTime);
	}
	
	public void detectAndSendChanges(){
		super.detectAndSendChanges();
		for(int i = 0; i < this.crafters.size(); i++){
			ICrafting icrafting = (ICrafting) this.crafters.get(i);
			
			if(this.lastProcessingTime != this.basicConductionInfuser.processingTime){
				icrafting.sendProgressBarUpdate(this, 0, this.basicConductionInfuser.processingTime);
			}
			if(this.lastChargeUsageTime != this.basicConductionInfuser.chargeUsageTime){
				icrafting.sendProgressBarUpdate(this, 2, this.basicConductionInfuser.chargeUsageTime);
			}
			if(this.lastCurrentItemUsageTime != this.basicConductionInfuser.currentItemUsageTime){
				icrafting.sendProgressBarUpdate(this, 3, this.basicConductionInfuser.currentItemUsageTime);
			}
		}
		
		this.lastProcessingTime = this.basicConductionInfuser.processingTime;
		this.lastChargeUsageTime = this.basicConductionInfuser.chargeUsageTime;
		this.lastCurrentItemUsageTime = this.basicConductionInfuser.currentItemUsageTime;
	}
	
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int slot, int newValue){
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

}