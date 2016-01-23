package com.techofreak.AdvancedEnergies.tileentity;

import com.techofreak.AdvancedEnergies.AdvancedEnergies;
import com.techofreak.AdvancedEnergies.machines.BasicConductionInfuser;

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
	
	//Like furnace speed
	public int machineSpeed = 100;
	//Like burn time
	public int chargeUsageTime;
	//Like current item burn time
	public int currentItemUsageTime;
	//Like cook time
	public int processingTime;
	
	public int maxChargeStorage = 50000;
	public int storedCharge = 0;
	
	public void updateEntity(){
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
	}
	
	public boolean canBeInfused(){
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
	
	public void infuseItem(){
		if(this.canBeInfused()){
			//Gets the resulting item from infusing
			ItemStack itemstack = AdvancedEnergies.infuserRecipeHandler.getInfuserResult(this.slots[0].getDisplayName(), this.slots[1].getDisplayName());
			
			System.out.println(itemstack.getDisplayName());
			
			if(this.slots[3] == null){
				System.out.println("The output slot is empty! Copying itemstack!");
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

	@Override
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
		}
		return true;
	}

	@Override
	public int[] getAccessibleSlotsFromSide(int var1) {
		if(var1 == 0){
			return slots_top;
		}else if(var1 == 1){
			return slots_bottom;
		}else{
			return slots_side;
		}
	}

	@Override
	public boolean canInsertItem(int i, ItemStack itemstack, int j) {
		return this.isItemValidForSlot(i, itemstack);
	}

	@Override
	public boolean canExtractItem(int i, ItemStack itemstack, int j) {
		return j != 0 || i != 1 || j != 3 || i != 4 || j != 5 || itemstack.getItem() == Items.bucket;
	}
	
	public int getItemUsageRemainingScaled(int i){
		if(this.currentItemUsageTime == 0){
			this.currentItemUsageTime = this.machineSpeed;
		}
		return this.chargeUsageTime * i / this.currentItemUsageTime;
	}
	
	public int getProcessProgressScaled(int i){
		return this.processingTime * i / this.machineSpeed;
	}
}
