package de.timgoll.facading.blocks;

import de.timgoll.facading.init.ModRegistry;
import de.timgoll.facading.titleentities.TileBlockFacadingbench;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BlockFacadingbench_powered extends BlockFacadingbench {

    public BlockFacadingbench_powered(String name) {
        super(name);
    }


    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand) {
        EnumFacing enumfacing = state.getValue(FACING);
        double d0 = (double)pos.getX() + 0.5D;
        double d1 = (double)pos.getY() + rand.nextDouble() * 6.0D / 16.0D;
        double d2 = (double)pos.getZ() + 0.5D;
        double d3 = 0.52D;
        double d4 = rand.nextDouble() * 0.6D - 0.3D;

        //play working sound
        world.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, ModRegistry.SOUND_FACADINGBENCH_POWERED, SoundCategory.BLOCKS, 0.075F, 0.5F, false);


        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);

        switch (enumfacing) {
            case WEST:
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.WATER_DROP, d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.CLOUD, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, d0 + 0.75D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                break;
            case EAST:
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.WATER_DROP, d0 + 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.CLOUD,  d0 - 0.52D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.WATER_BUBBLE,  d0 - 0.75D, d1, d2 + d4, 0.0D, 0.0D, 0.0D);
                break;
            case NORTH:
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.WATER_DROP, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.CLOUD, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, d0 + d4, d1, d2 + 0.75D, 0.0D, 0.0D, 0.0D);
                break;
            case SOUTH:
                world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.WATER_DROP, d0 + d4, d1, d2 + 0.52D, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.CLOUD, d0 + d4, d1, d2 - 0.52D, 0.0D, 0.0D, 0.0D);
                world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, d0 + d4, d1, d2 - 0.75D, 0.0D, 0.0D, 0.0D);
        }
    }
}
