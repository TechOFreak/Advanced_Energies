package com.techofreak.AdvancedEnergies.handler;

import com.techofreak.AdvancedEnergies.AdvancedEnergies;
import com.techofreak.AdvancedEnergies.container.ContainerBasicConductionInfuser;
import com.techofreak.AdvancedEnergies.gui.GuiBasicConductionInfuser;
import com.techofreak.AdvancedEnergies.tileentity.TileEntityBasicConductionInfuser;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler{

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		
		if(entity != null){
			switch(ID){
			case AdvancedEnergies.guiIDBasicConductionInfuser:
				if(entity instanceof TileEntityBasicConductionInfuser){
					return new ContainerBasicConductionInfuser(player.inventory, (TileEntityBasicConductionInfuser) entity);
				}
				return null;
			}
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity entity = world.getTileEntity(x, y, z);
		
		if(entity != null){
			switch(ID){
			case AdvancedEnergies.guiIDBasicConductionInfuser:
				if(entity instanceof TileEntityBasicConductionInfuser){
					return new GuiBasicConductionInfuser(player.inventory, (TileEntityBasicConductionInfuser) entity);
				}
				return null;
			}
		}
		return null;
	}

}
