package com.techofreak.AdvancedEnergies.tileentity;

import com.techofreak.AdvancedEnergies.AdvancedEnergies;
import com.techofreak.AdvancedEnergies.gui.GuiBasicConductionInfuser;
import com.techofreak.AdvancedEnergies.machines.BasicConductionInfuser;
import com.techofreak.AdvancedEnergies.values.BasicConductionInfuserValues;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBasicConductionInfuser extends TileEntity implements ISidedInventory{

	private String localizedName;
	
	private ItemStack[] slots = new ItemStack[7];
	private static final int[] slots_top = new int []{0, 1};
	private static final int[] slots_bottom = new int []{3};
	private static final int[] slots_side = new int []{2};
	
	public static BasicConductionInfuserValues basicConductionInfuserValues = new BasicConductionInfuserValues();
	
	//Like furnace speed
	public int machineSpeed = getMachineSpeed(100);
	//Like burn time
	public int chargeUsageTime;
	//Like current item burn time
	public int currentItemUsageTime;
	//Like cook time
	public int processingTime;
	
	public int infusingCost = 100;
	
	public int maxChargeStorage = 20000;
	public int storedCharge = 0;
	
	public void updateEntity(){
		boolean flag = this.chargeUsageTime > 0;
		boolean flag1 = false;
		
		fillCharge();
		
		if(this.isInfusing()){
			this.chargeUsageTime--;
		}
		if(!this.worldObj.isRemote){
			if(this.chargeUsageTime == 0 && this.canBeInfused()){
				
				//Take away 100 from the storedCharge
				this.currentItemUsageTime = this.chargeUsageTime = infusingCost;
				
				if(this.isInfusing()){
					flag1 = true;
				}
			}
			if(this.isInfusing() && this.canBeInfused()){
				this.processingTime++;
				this.basicConductionInfuserValues.processingTime = this.processingTime;
				//System.out.println("The progress level is " + this.processingTime);
				
				if(this.processingTime >= this.machineSpeed){
					this.processingTime = 0;
					this.infuseItem();
					flag1 = true;
				}
			}else{
				this.processingTime = 0;
				this.basicConductionInfuserValues.processingTime = this.processingTime;
			}
			
			if(flag != this.isInfusing()){
				flag1 = true;
				BasicConductionInfuser.updateBasicConductionInfuserBlockState(this.chargeUsageTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
		}
		if(flag1){
			this.markDirty();
		}
		//System.out.println("The currently stored charge is " + storedCharge);
	}
	
	/*
	public void updateEntityOld(){
		boolean flag = this.chargeUsageTime > 0;
		boolean flag1 = false;
		
		if(this.isInfusing()){
			this.chargeUsageTime--;
		}
		if(!this.worldObj.isRemote){
			if(this.chargeUsageTime == 0 && this.canBeInfused()){
				this.currentItemUsageTime = this.chargeUsageTime = AdvancedEnergies.chargehandler.getItemChargeAmount(this.slots[2].getDisplayName());
				
				if(this.isInfusing()){
					flag1 = true;
					
					if(this.slots[2] != null){
						this.slots[2].stackSize--;
						
						if(this.slots[2].stackSize == 0){
							this.slots[2] = this.slots[2].getItem().getContainerItem(this.slots[2]);
						}
					}
				}
			}
			if(this.isInfusing() && this.canBeInfused()){
				this.processingTime++;
				
				if(this.processingTime == this.machineSpeed){
					this.processingTime = 0;
					this.infuseItem();
					flag1 = true;
				}
			}else{
				this.processingTime = 0;
			}
			
			if(flag != this.isInfusing()){
				flag1 = true;
				BasicConductionInfuser.updateBasicConductionInfuserBlockState(this.chargeUsageTime > 0, this.worldObj, this.xCoord, this.yCoord, this.zCoord);
			}
		}
		if(flag1){
			this.markDirty();
		}
		fillCharge();
	}
	*/
	
	/*
	public boolean canBeInfusedOld(){
		if(this.slots[0] == null || this.slots[1] == null || this.slots[2] == null){
			return false;
		}else{
			if(!AdvancedEnergies.chargehandler.isValidCharge(this.slots[2].getDisplayName())) return false;
			
			//System.out.println(this.slots[0].getDisplayName());
			//System.out.println(this.slots[1].getDisplayName());
			ItemStack itemstack = AdvancedEnergies.infuserRecipeHandler.getInfuserResult(this.slots[0].getDisplayName(), this.slots[1].getDisplayName());
			
			//If there is an invalid item return false
			if(itemstack == null) return false;
			//If the output slot is empty return true
			if(this.slots[3] == null) return true;
			//If the item in the output slot is not the same as the result return false
			if(!this.slots[3].isItemEqual(itemstack)) return false;
			
			//Add together the current itemstack in the output slot with the resulting itemstack
			int result = this.slots[3].stackSize + itemstack.stackSize;
			
			//Check to see if the result above is <= to the slots stack limit
			//And check to see if the result is <= the maximum stack size
			//If both check out return true otherwise return false
			if(result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize()){
				return true;
			}else{
				return false;
			}
		}
	}
	*/
	
	public boolean canBeInfused(){
		if(this.slots[0] == null || this.slots[1] == null || this.storedCharge < this.infusingCost){
			return false;
		}else{
			//System.out.println(this.slots[0].getDisplayName());
			//System.out.println(this.slots[1].getDisplayName());
			ItemStack itemstack = AdvancedEnergies.infuserRecipeHandler.getInfuserResult(this.slots[0].getDisplayName(), this.slots[1].getDisplayName());
			
			//If there is an invalid item return false
			if(itemstack == null) return false;
			//If the output slot is empty return true
			if(this.slots[3] == null) return true;
			//If the item in the output slot is not the same as the result return false
			if(!this.slots[3].isItemEqual(itemstack)) return false;
			
			//Add together the current itemstack in the output slot with the resulting itemstack
			int result = this.slots[3].stackSize + itemstack.stackSize;
			
			//Check to see if the result above is <= to the slots stack limit
			//And check to see if the result is <= the maximum stack size
			//If both check out return true otherwise return false
			if(result <= getInventoryStackLimit() && result <= itemstack.getMaxStackSize()){
				return true;
			}else{
				return false;
			}
		}
	}
	
	public void infuseItem(){
		if(this.canBeInfused()){
			//Gets the resulting item from infusing
			ItemStack itemstack = AdvancedEnergies.infuserRecipeHandler.getInfuserResult(this.slots[0].getDisplayName(), this.slots[1].getDisplayName());
			
			if(this.slots[3] == null){
				//If the output slot is empty
				//Make a copy of the result and put it in the output slot
				this.slots[3] = itemstack.copy();
			}else if(this.slots[3].isItemEqual(itemstack)){
				//If the item in the output slot is the same as the resulting item
				//Add on to the stack in the output slot
				this.slots[3].stackSize += itemstack.stackSize;
			}
			
			//Remove 1 of the items from the 2 input slots
			this.slots[0].stackSize--;
			this.slots[1].stackSize--;
			this.storedCharge = this.storedCharge-this.infusingCost;
			this.basicConductionInfuserValues.storedCharge = this.storedCharge;
			
			//If the stacksize of the input slots is <= 0
			//Set the slots equal to null
			if(this.slots[0].stackSize <= 0){
				this.slots[0] = null;
			}
			if(this.slots[1].stackSize <= 0){
				this.slots[1] = null;
			}
		}
	}
	
	public void fillCharge(){
		if(this.slots[2] != null){
			//System.out.println("Slot is not null");
			int chargeAmount = AdvancedEnergies.chargehandler.getItemChargeAmount(this.slots[2].getDisplayName());
			//System.out.println("Charge amount is " + chargeAmount);
			//System.out.println("Charge is " + storedCharge + "/" + maxChargeStorage);
			//System.out.println("Current stack size is " + currentFuelStackSize);
			while(this.slots[2] != null && chargeAmount > 0 && storedCharge <= maxChargeStorage && chargeAmount+storedCharge <= maxChargeStorage){
				this.slots[2].stackSize--;
				//System.out.println("Stack size decreased to " + this.slots[2].stackSize);
				
				if(this.slots[2].stackSize > 0){
					storedCharge = chargeAmount+storedCharge;
					//System.out.println("Charge is " + storedCharge + "/" + maxChargeStorage);
					chargeAmount = AdvancedEnergies.chargehandler.getItemChargeAmount(this.slots[2].getDisplayName());
				}else{
					storedCharge = chargeAmount+storedCharge;
					this.basicConductionInfuserValues.storedCharge = this.storedCharge;
					this.slots[2] = null;
				}
			}
		}
	}
	
	public boolean isInfusing(){
		return this.chargeUsageTime > 0;
	}
	
	public void setGuiDisplayName(String displayName){
		this.localizedName = displayName;
	}

	public String getInventoryName(){
		return this.hasCustomInventoryName() ? this.localizedName : "container.basicConductionInfuser";
	}

	public boolean hasCustomInventoryName(){
		return this.localizedName != null && this.localizedName.length() > 0;
	}
	
	public int getSizeInventory(){
		return this.slots.length;
	}

	@Override
	public ItemStack getStackInSlot(int var1) {
		return this.slots[var1];
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2) {
		//This deals with when you take a stack out
		//For example when you right click on a stack and you get half
		if(this.slots[var1] != null){
			ItemStack itemstack;
			
			if(this.slots[var1].stackSize <= var2){
				itemstack = this.slots[var1];
				this.slots[var1] = null;
				return itemstack;
			}else{
				itemstack = this.slots[var1].splitStack(var2);
				
				if(this.slots[var1].stackSize == 0){
					this.slots[var1] = null;
				}
			}
		}
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		if(this.slots[i] != null){
			ItemStack itemstack = this.slots[i];
			this.slots[i] = null;
			return itemstack;
		}
		return null;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		this.slots[i] = itemstack;
		
		if(itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()){
			itemstack.stackSize = this.getInventoryStackLimit();
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : entityplayer.getDistanceSq((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D, (double) this.yCoord + 0.5D) <= 64.0D;
	}

	public void openInventory() {}
	public void closeInventory() {}

	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		if(i == 3){
			return false;
		}else if(i == 2){
			if(itemstack != null){
				return AdvancedEnergies.chargehandler.isValidCharge(itemstack.getDisplayName());
			}
		}else if(i == 4 || i == 5 || i == 6){
			//Upgrades will be applied here currently has no functionality
			return false;
		}else if(i == 0){
			if(itemstack != null){
				return AdvancedEnergies.infuserRecipeHandler.isValidIngot(itemstack.getDisplayName());
			}
		}else if(i == 1){
			if(itemstack != null){
				return AdvancedEnergies.infuserRecipeHandler.isValidBase(itemstack.getDisplayName());
			}
		}
		return true;
	}

	public int[] getAccessibleSlotsFromSide(int var1) {
		if(var1 == 0){
			return slots_bottom;
		}else if(var1 == 1){
			return slots_top;
		}else{
			return slots_side;
		}
	}

	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return this.isItemValidForSlot(i, itemstack);
	}

	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		//Test this
		return j != 0 || i != 1 || j != 3 || i != 4 || j != 5 || itemstack.getItem() == Items.bucket;
	}
	
	public int getMachineSpeed(int baseMachineSpeed){
		//Slots 4,5,6 can store upgrades
		//Fastest speed will be 1/2 second
		//Machine speed 100 is 5 seconds
		//Machine speed of 10 is 1/2
		//10 is 1/10 of the normal machine speed
		//Max speed upgrades that will be allowed is 60
		//.90/60 is .015
		double currentMultiplier = 1.0;
		//Create class that handles valid upgrades
		/*
		if(AdvancedEnergies.upgradeHandler.isValidUpgrade("basic", this.slots[4]).getDisplayName){
			if(currentMultiplier >= .115){ currentMultiplier = currentMultiplier-(.015*(double)this.slots[4].stackSize()) }
		}else if(AdvancedEnergies.upgradeHandler.isValidUpgrade("basic", this.slots[5]).getDisplayName){
			if(currentMultiplier >= .115){ currentMultiplier = currentMultiplier-(.015*(double)this.slots[5].stackSize()) }
		}else if(AdvancedEnergies.upgradeHandler.isValidUpgrade("basic", this.slots[6]).getDisplayName){
			if(currentMultiplier >= .115){ currentMultiplier = currentMultiplier-(.015*(double)this.slots[6].stackSize()) }
		}
		 */
		
		//Might make it more inefficient as speed goes up
		//There will be efficiency upgrades and power storage cap increases but those will only apply to advanced machines
		return (int) (baseMachineSpeed*currentMultiplier);
	}
	
	public int getChargeRemainingScaled(int i){
		if(this.storedCharge == 0){
			return 0;
		}
		return this.maxChargeStorage * i / this.storedCharge;
	}
	
	public int getProcessProgressScaled(int i){
		return this.processingTime * i / this.machineSpeed;
	}
	
	public int getStoredCharge(){
		return storedCharge;
	}
}
