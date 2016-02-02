package com.techofreak.AdvancedEnergies.gui;

import org.lwjgl.opengl.GL11;

import com.techofreak.AdvancedEnergies.AdvancedEnergies;
import com.techofreak.AdvancedEnergies.container.ContainerBasicConductionInfuser;
import com.techofreak.AdvancedEnergies.tileentity.TileEntityBasicConductionInfuser;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class GuiBasicConductionInfuser extends GuiContainer{

	public static final ResourceLocation bground = new ResourceLocation(AdvancedEnergies.MODID + ":" + "textures/gui/guiBasicConductionInfuser.png");
	public TileEntityBasicConductionInfuser basicConductionInfuser;
	private double lastDisplayLevel = 0.0;
	private double lastProgressDisplayLevel = 0.0;
	
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
		
		int k = basicConductionInfuser.getChargeRemainingScaled(52);
		int j = 52-k;
		
		int displayLevel = 0;
		
		double storedLevel = 52.0/(double)basicConductionInfuser.maxChargeStorage;
		lastDisplayLevel = storedLevel*(double)basicConductionInfuser.basicConductionInfuserValues.storedCharge;
		
		if(lastDisplayLevel >= 52.0){
			displayLevel = 52;
		}else if(lastDisplayLevel >= 51.0 && lastDisplayLevel < 52.0){
			displayLevel = 51;
		}else if(lastDisplayLevel > 0.0 && lastDisplayLevel < 1.0){
			displayLevel = 1;
		}else{
			displayLevel = (int) lastDisplayLevel;
		}
		
		int progressDisplayLevel = 0;
		double storedProgressLevel = 0.0;
		
		if(basicConductionInfuser.basicConductionInfuserValues.processingTime != 0){
			storedProgressLevel = 30.0/(double)basicConductionInfuser.machineSpeed;
			lastProgressDisplayLevel = storedProgressLevel*(double)basicConductionInfuser.basicConductionInfuserValues.processingTime;
			System.out.println("The stored progress level is " + storedProgressLevel);
			System.out.println("The last progress display level is" + lastProgressDisplayLevel);
			if(lastProgressDisplayLevel >= 30.0){
				progressDisplayLevel = 30;
			}else if(lastProgressDisplayLevel >= 29.0 && lastProgressDisplayLevel < 30.0){
				progressDisplayLevel = 29;
			}else if(lastProgressDisplayLevel > 0.0 && lastProgressDisplayLevel < 1.0){
				progressDisplayLevel = 1;
			}else{
				progressDisplayLevel = (int) lastProgressDisplayLevel;
			}
		}else{
			progressDisplayLevel = 0;
		}
		//this.drawTexturedModalRectRevert(leftsidelocation, bottomsidelocation, leftsideloader, bottomsideloader, widthloader, -howmuchtodraw);
		this.drawTexturedModalRectRevert(guiLeft+8, guiTop+64, 176, 52, 16, -displayLevel);
		this.drawTexturedModalRect(guiLeft+80, guiTop+37, 192, 0, progressDisplayLevel, 9);
	}
	
    public void drawTexturedModalRectRevert(int par1, int par2, int par3, int par4, int par5, int par6){
        float var7 = 0.00390625F;
        float var8 = 0.00390625F;
        Tessellator var9 = Tessellator.instance;
        var9.startDrawingQuads();
        
        var9.addVertexWithUV((double)(par1 + par5), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + par5) * var7), (double)((float)(par4 + 0) * var8));
        
        var9.addVertexWithUV((double)(par1 + par5), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + par5) * var7), (double)((float)(par4 + par6) * var8));
        
        var9.addVertexWithUV((double)(par1 + 0), (double)(par2 + par6), (double)this.zLevel, (double)((float)(par3 + 0) * var7), (double)((float)(par4 + par6) * var8));
        
        var9.addVertexWithUV((double)(par1 + 0), (double)(par2 + 0), (double)this.zLevel, (double)((float)(par3 + 0) * var7), (double)((float)(par4 + 0) * var8));
        var9.draw();
        
    }
	
	public void drawChargeBar(){
		double storedStatus = basicConductionInfuser.storedCharge/basicConductionInfuser.maxChargeStorage;
		this.drawTexturedModalRect(8, 12, 176, 52, (int) (16*storedStatus), 52);
	}

}
