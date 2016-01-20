package com.techofreak.AdvancedEnergies;

import com.techofreak.AdvancedEnergies.blocks.blockRadiantCrystal;
import com.techofreak.AdvancedEnergies.items.ItemEnergizedBlend;
import com.techofreak.AdvancedEnergies.items.ItemRadiantCrystal;
import com.techofreak.AdvancedEnergies.items.ItemRadiantCrystalDust;
import com.techofreak.AdvancedEnergies.items.dustCoal;
import com.techofreak.AdvancedEnergies.machines.blockBasicConductionInfuser;
import com.techofreak.AdvancedEnergies.oregen.OreGeneration;
import com.techofreak.AdvancedEnergies.proxy.CommonProxy;

import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME,version = Reference.VERSION)
public class AdvancedEnergies {

	public static final String MODID = "ae";
	public static final String NAME = "Advanced Energies";
    public static final String VERSION = "1.7.001";
    
    @SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
    public static CommonProxy proxy;
	
    //Items with no functionality
    public static Item itemEnergizedBlend;
    public static Item itemRadiantCrystal;
    public static Item itemRadiantCrystalDust;
    public static Item dustCoal;
    
    //Ore blocks
    public static Block blockRadiantCrystal;
    
    //Machine Blocks
    public static Block blockBasicConductionInfuserIdle;
    public static Block blockBasicConductionInfuserActive;
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	registerItems();
    	registerOreBlocks();
    	registerMachineBlocks();
    	registerOreGen();
    	oreRegistration();
    }
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    	addRecipes();
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {

    }
    
    public static void registerItems(){
    	//Items with no functionality
    	itemEnergizedBlend = new ItemEnergizedBlend().setUnlocalizedName("ItemEnergizedBlend").setTextureName("ae:itemEnergizedBlend").setCreativeTab(tabAdvancedEnergies);
    	GameRegistry.registerItem(itemEnergizedBlend, itemEnergizedBlend.getUnlocalizedName().substring(5));
    	
    	itemRadiantCrystal = new ItemRadiantCrystal().setUnlocalizedName("ItemRadiantCrystal").setTextureName("ae:itemRadiantCrystal").setCreativeTab(tabAdvancedEnergies);
    	GameRegistry.registerItem(itemRadiantCrystal, itemRadiantCrystal.getUnlocalizedName().substring(5));
    	
    	itemRadiantCrystalDust = new ItemRadiantCrystalDust().setUnlocalizedName("ItemRadiantCrystalDust").setTextureName("ae:itemRadiantCrystalDust").setCreativeTab(tabAdvancedEnergies);
    	GameRegistry.registerItem(itemRadiantCrystalDust, itemRadiantCrystalDust.getUnlocalizedName().substring(5));
    	
    	dustCoal = new dustCoal().setUnlocalizedName("dustCoal").setTextureName("ae:dustCoal").setCreativeTab(tabAdvancedEnergies);
    	GameRegistry.registerItem(dustCoal, dustCoal.getUnlocalizedName().substring(5));
    	
    }
    
    public static void registerOreBlocks(){
    	blockRadiantCrystal = new blockRadiantCrystal(Material.rock).setBlockName("BlockRadiantCrystal").setBlockTextureName("ae:blockRadiantCrystal").setCreativeTab(tabAdvancedEnergies);
    	GameRegistry.registerBlock(blockRadiantCrystal, blockRadiantCrystal.getUnlocalizedName().substring(5));
    }
    
    public static void registerMachineBlocks(){
    	blockBasicConductionInfuserIdle = new blockBasicConductionInfuser(false).setBlockName("BlockBasicConductionInfuserIdle").setBlockTextureName("ae:blockBasicConductionInfuserIdle").setCreativeTab(tabAdvancedEnergies);
    	GameRegistry.registerBlock(blockBasicConductionInfuserIdle, blockBasicConductionInfuserIdle.getUnlocalizedName().substring(5));
    	
    	blockBasicConductionInfuserActive = new blockBasicConductionInfuser(true).setBlockName("BlockBasicConductionInfuserActive").setBlockTextureName("ae:blockBasicConductionInfuserActive");
    	GameRegistry.registerBlock(blockBasicConductionInfuserActive, blockBasicConductionInfuserActive.getUnlocalizedName().substring(5));
    }
    
    public static void oreRegistration(){
    	OreDictionary.registerOre("dustCoal", dustCoal);
    }
    
    public static void registerOreGen(){
    	GameRegistry.registerWorldGenerator(new OreGeneration(), 0);
    }
    
    public static void addRecipes(){
    	GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(itemEnergizedBlend,4), true, new Object[]{"AB ","CC ","   ", 'A', itemRadiantCrystalDust, 'B', Items.redstone, 'C', "dustCoal"}));
    }
    
    public static CreativeTabs tabAdvancedEnergies = new CreativeTabs("tabAdvancedEnergies"){
    	@Override
    	public Item getTabIconItem(){
    		return new ItemStack(itemEnergizedBlend).getItem();
    	}
    };
    
}