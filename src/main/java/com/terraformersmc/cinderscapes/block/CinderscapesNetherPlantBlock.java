package com.terraformersmc.cinderscapes.block;

import com.terraformersmc.cinderscapes.util.StateShapeSupplier;
import net.minecraft.block.*;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;

/**
 * [REVIEWED]
 *
 * The root class for nether plants of varying bounding box shapes
 *
 * @author <Wtoll> Will Toll on 2020-05-02
 * @project Cinderscapes
 */
public class CinderscapesNetherPlantBlock extends PlantBlock {
    private static StateShapeSupplier SHAPE_SUPPLIER;

    public CinderscapesNetherPlantBlock(Settings settings, StateShapeSupplier supplier) {
        super(settings);
        SHAPE_SUPPLIER = supplier;
    }

    @Override
    protected boolean canPlantOnTop(BlockState floor, BlockView world, BlockPos pos) {
        Block block = floor.getBlock();
        return BlockTags.NYLIUM.contains(block) || block == Blocks.SOUL_SOIL || super.canPlantOnTop(floor, world, pos);
    }

    @SuppressWarnings("deprecation")
    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE_SUPPLIER.apply(state);
    }
}
