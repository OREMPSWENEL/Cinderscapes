package com.terraformersmc.cinderscapes.feature;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;


/**
 * [REVIEWED]
 *
 * A large scale like stone feature
 *
 * @author <Wtoll> Will Toll
 * @project Cinderscapes
 */
public class BlackstoneShaleFeature extends Feature<DefaultFeatureConfig> {

    public BlackstoneShaleFeature() {
        super(DefaultFeatureConfig::deserialize);
    }

    // TODO: Rewrite using the upcoming shapes library
    // TODO: Rewrite using the CountFloorDecorator
    @Override
    public boolean generate(IWorld world, StructureAccessor accessor, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        int yradius = random.nextInt(5) + 5;

        while(true) {
            search: {
                // If you've gone through all layers and haven't found a suitable spot then return false
                // so that we don't get stuck in an endless loop
                if (pos.getY() <= 3) {
                    return false;
                }

                // If the shale would be generating above the nether ceiling then move down
                if (pos.getY() > (128 - 2*yradius) ) {
                    break search;
                }

                // If there's air under the shale then move down
                if (world.isAir(pos.down())) {
                    break search;
                }

                // If the four blocks below are not each one of soul sand, soul soil, or netherrack then move down
                for (int y = 1; y <= 4; y++) {
                    Block block = world.getBlockState(pos.down(y)).getBlock();
                    if (block != Blocks.SOUL_SAND && block != Blocks.SOUL_SOIL && block != Blocks.NETHERRACK) {
                        break search;
                    }
                }

                // At this point, if we haven't broken, there are suitable conditions for a shale to generate

                // Define the parameters of the ellipse relative to the random y radius
                int xradius;
                int zradius;
                // This random if statement makes it so that 50% of the time the shales are rotated 90deg
                if (random.nextBoolean()) {
                    xradius = 2;
                    zradius = (yradius * 3) / 4;
                } else {
                    zradius = 2;
                    xradius = (yradius * 3) / 4;
                }

                // Iterate through all of the x, y, and z values that might be potentially included in the ellipse
                for (int xi = - xradius; xi < xradius ; xi++) {
                    for (int yi = -yradius; yi < yradius ; yi++) {
                        for (int zi = -zradius; zi < zradius ; zi++) {
                            BlockPos question = pos.east(xi).up(yi).south(zi);
                            // This is literally just the formula for an ellipse, so yeah
                            // If the block in question is within the ellipse then fill it
                            if (Math.sqrt(    (  ( xi * xi )/(float)( xradius * xradius )  )   +   (  ( yi * yi )/(float)( yradius * yradius )  )   +   (  (zi * zi)/(float)( zradius * zradius )  )    ) <= 1) {
                                world.setBlockState(question, Blocks.BLACKSTONE.getDefaultState(), 4);
                            }
                        }
                    }
                }

                // Return because we are done generating and return true to indicate a successful generation
                return true;
            }

            pos = pos.down();
        }
    }
}
