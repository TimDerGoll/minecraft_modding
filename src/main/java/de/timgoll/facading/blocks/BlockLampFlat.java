package de.timgoll.facading.blocks;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockLampFlat extends BlockLampBase {

    protected static final AxisAlignedBB UP_AABB    = new AxisAlignedBB(0.0425D, 0.0D, 0.0425D, 0.9575D, 0.145D, 0.9575D);
    protected static final AxisAlignedBB DOWN_AABB  = new AxisAlignedBB(0.0425D, 1.0D, 0.0425D, 0.9575D, 0.855D, 0.9575D);
    protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0425D, 0.0425D, 0.0D, 0.9575D, 0.9575D, 0.145D);
    protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0425D, 0.0425D, 1.0D, 0.9575D, 0.9575D, 0.855D);
    protected static final AxisAlignedBB WEST_AABB  = new AxisAlignedBB(1.0D, 0.0425D, 0.0425D, 0.855D, 0.9375D, 0.9575D);
    protected static final AxisAlignedBB EAST_AABB  = new AxisAlignedBB(0.0D, 0.0425D, 0.0425D, 0.145D, 0.9375D, 0.9575D);


    public BlockLampFlat(String name) {
        super(name);
    }

    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch ((EnumFacing)state.getValue(FACING)) {
            case EAST:
                return EAST_AABB;
            case WEST:
                return WEST_AABB;
            case SOUTH:
                return SOUTH_AABB;
            case NORTH:
                return NORTH_AABB;
            case UP:
                return UP_AABB;
            case DOWN:
                return DOWN_AABB;
            default:
                return UP_AABB;
        }
    }

}
