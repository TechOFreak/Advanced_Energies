package com.techofreak.AdvancedEnergies.gui;

import org.lwjgl.opengl.GL11;

import com.techofreak.AdvancedEnergies.AdvancedEnergies;
import com.techofreak.AdvancedEnergies.container.ContainerBasicConductionInfuser;
import com.techofreak.AdvancedEnergies.tileentity.TileEntityBasicConductionInfuser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiBasicConductionInfuser extends GuiContainer{

	public static final ResourceLocation bground = new ResourceLocation(AdvancedEnergies.MODID + ":" + "textures/gui/guiBasicConductionInfuser.png");
	public TileEntityBasicConductionInfuser basicConductionInfuser;
	
	public GuiBasicConductionInfuser(InventoryPlayer inventoryPlayer, TileEntityBasicConductionInfuser entity) {
		super(new ContainerBasicConductionInfuser(inventoryPlayer, entity));
		this.basicConductionInfuser = entity;
		
		this.xSize = 176;
		this.ySize = 166;
	}

	public void drawGuiContainerForegroundLayer(int par1, int par2){
		String name;
		if(this.basicConductionInfuser.hasCustomInventoryName()){
			name = this.basicConductionInfuser.getInventoryName();
		}else{
			name = I18n.format(this.basicConductionInfuser.getInventoryName(), new Object[0]);
		}
		
		this.fontRendererObj.drawString(name, this.xSize/2 - this.fontRendererObj.getStringWidth(name) / 2, 6, 4210752);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float var1, int var2, int var3) {
		GL11.glColor4f(1F, 1F, 1F, 1F);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(bground);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
	}

}
