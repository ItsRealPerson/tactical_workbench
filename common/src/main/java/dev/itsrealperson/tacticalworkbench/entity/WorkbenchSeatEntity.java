package dev.itsrealperson.tacticalworkbench.entity;

import dev.itsrealperson.tacticalworkbench.block.TacticalWorkbenchBlock;
import dev.itsrealperson.tacticalworkbench.registry.ModSounds;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;

public class WorkbenchSeatEntity extends Entity {
    private BlockPos workbenchPos;

    public WorkbenchSeatEntity(EntityType<? extends WorkbenchSeatEntity> entityType, Level level) {
        super(entityType, level);
        this.noPhysics = true;
        this.setInvisible(true);
        this.setNoGravity(true);
        this.setInvulnerable(true);
    }

    public void setWorkbenchPos(BlockPos pos) {
        this.workbenchPos = pos;
    }

    public BlockPos getWorkbenchPos() {
        return workbenchPos;
    }

    @Override
    protected void defineSynchedData() {
    }

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        if (tag.contains("WorkbenchPos")) {
            this.workbenchPos = NbtUtils.readBlockPos(tag.getCompound("WorkbenchPos"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        if (this.workbenchPos != null) {
            tag.put("WorkbenchPos", NbtUtils.writeBlockPos(this.workbenchPos));
        }
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide) {
            BlockPos targetPos = this.workbenchPos != null ? this.workbenchPos : this.blockPosition();
            boolean isWorkbench = this.level().getBlockState(targetPos).getBlock() instanceof TacticalWorkbenchBlock
                    || this.level().getBlockState(this.blockPosition()).getBlock() instanceof TacticalWorkbenchBlock;

            if ((this.tickCount > 2 && this.getPassengers().isEmpty()) || !isWorkbench) {
                this.discard();
            }
        }
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        if (!this.level().isClientSide) {
            this.level().playSound(null, this.blockPosition(), ModSounds.SEAT_STAND.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            this.discard();
        }
    }

    @Override
    public double getPassengersRidingOffset() {
        return 0.0D;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        return false;
    }

    @Override
    public Packet<ClientGamePacketListener> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
