package com.techofreak.AdvancedEnergies.blocks;

import java.util.Random;

import com.techofreak.AdvancedEnergies.AdvancedEnergies;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;

public class blockRadiantCrystal extends Block {

	public blockRadiantCrystal(Material material) {
		super(material);
		this.setHarvestLevel("pickaxe", 2);
		this.setHardness(5.0F);
		this.setStepSound(this.soundTypeStone);
		this.setLightLevel(0.20F);
	}
	
	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune) {
		return AdvancedEnergies.itemRadiantCrystal;
	}
	
	@Override
	public int quantityDropped(Random rand) {
		return 2 + rand.nextInt(2+1);
	}
}
