package dev.itsrealperson.tacticalworkbench.block;

import dev.itsrealperson.tacticalworkbench.block.entity.TacticalWorkbenchBlockEntity;
import dev.itsrealperson.tacticalworkbench.client.TacticalWorkbenchClientHelper;
import dev.itsrealperson.tacticalworkbench.entity.WorkbenchSeatEntity;
import dev.itsrealperson.tacticalworkbench.registry.ModEntities;
import dev.itsrealperson.tacticalworkbench.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TacticalWorkbenchBlock extends BaseEntityBlock {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    protected static final VoxelShape TABLE_SHAPE = Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D);

    public TacticalWorkbenchBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return TABLE_SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TacticalWorkbenchBlockEntity(pos, state);
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (level.isClientSide) {
            if (level.getBlockEntity(pos) instanceof TacticalWorkbenchBlockEntity be) {
                TacticalWorkbenchClientHelper.openWorkbenchScreen(be);
            }
            return InteractionResult.SUCCESS;
        } else {
            AABB searchBox = new AABB(pos).inflate(1.2);
            List<WorkbenchSeatEntity> existingSeats = level.getEntitiesOfClass(WorkbenchSeatEntity.class, searchBox);
            WorkbenchSeatEntity seat = null;

            for (WorkbenchSeatEntity s : existingSeats) {
                if (s.isVehicle()) {
                    seat = s;
                    break;
                }
            }

            if (seat == null) {
                Direction facing = state.getValue(FACING);
                double seatX = pos.getX() + 0.5 - facing.getStepX() * 0.55;
                double seatY = pos.getY() + 0.05;
                double seatZ = pos.getZ() + 0.5 - facing.getStepZ() * 0.55;

                seat = new WorkbenchSeatEntity(ModEntities.SEAT_ENTITY.get(), level);
                seat.setWorkbenchPos(pos);
                seat.setPos(seatX, seatY, seatZ);
                seat.setYRot(facing.toYRot());
                level.addFreshEntity(seat);

                player.startRiding(seat, true);
                level.playSound(null, pos, ModSounds.SEAT_SIT.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            } else if (!seat.hasPassenger(player)) {
                return InteractionResult.CONSUME;
            }

            return InteractionResult.CONSUME;
        }
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!state.is(newState.getBlock())) {
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof TacticalWorkbenchBlockEntity workbench) {
                Containers.dropContents(level, pos, workbench);
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }
}
