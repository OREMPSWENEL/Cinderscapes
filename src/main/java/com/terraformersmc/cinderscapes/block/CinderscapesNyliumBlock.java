package com.terraformersmc.cinderscapes.block;

import com.terraformersmc.cinderscapes.init.CinderscapesBlocks;
import com.terraformersmc.cinderscapes.init.CinderscapesFeatures;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.NyliumBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.NetherForestVegetationFeature;
import net.minecraft.world.gen.feature.TwistingVinesFeature;

import java.util.Random;

// TODO: Look into maybe removing the method and defining that behavior at declaration
public class CinderscapesNyliumBlock extends NyliumBlock {
    public CinderscapesNyliumBlock(Settings settings) {
        super(settings);
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        Block block = world.getBlockState(pos).getBlock();
        BlockPos blockPos = pos.up();

        if (block == CinderscapesBlocks.WITHERED_NYLIUM) {
            NetherForestVegetationFeature.method_26264(world, random, blockPos, DefaultBiomeFeatures.WARPED_ROOTS_CONFIG, 3, 1);
            NetherForestVegetationFeature.method_26264(world, random, blockPos, DefaultBiomeFeatures.NETHER_SPROUTS_CONFIG, 3, 1);
            if (random.nextInt(8) == 0) {
                TwistingVinesFeature.method_26265(world, random, blockPos, 3, 1, 2);
            }
        } else if (block == CinderscapesBlocks.UMBRAL_NYLIUM) {
            NetherForestVegetationFeature.method_26264(world, random, blockPos, CinderscapesFeatures.LUMINOUS_GROVE_VEGETATION_CONFIG, 3, 1);
        }

    }
}
