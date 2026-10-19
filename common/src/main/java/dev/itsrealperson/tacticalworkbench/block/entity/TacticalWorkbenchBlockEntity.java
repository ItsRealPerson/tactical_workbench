package dev.itsrealperson.tacticalworkbench.block.entity;

import dev.itsrealperson.tacticalworkbench.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TacticalWorkbenchBlockEntity extends BlockEntity implements Container {
    public static final int SLOT_PRIMARY = 0;
    public static final int SLOT_OPTIC = 1;
    public static final int SLOT_BARREL = 2;
    public static final int SLOT_UNDERBARREL = 3;
    public static final int TOTAL_SLOTS = 4;

    private final NonNullList<ItemStack> items = NonNullList.withSize(TOTAL_SLOTS, ItemStack.EMPTY);
    private float cleanliness = 1.0f;
    private float wear = 0.0f;

    public TacticalWorkbenchBlockEntity(BlockPos pos, BlockState blockState) {
        super(ModBlockEntities.TACTICAL_WORKBENCH_BE.get(), pos, blockState);
    }

    public ItemStack getPrimaryWeapon() {
        return items.get(SLOT_PRIMARY);
    }

    public void setPrimaryWeapon(ItemStack weapon) {
        items.set(SLOT_PRIMARY, weapon);
        sendUpdate();
    }

    public ItemStack getOptic() {
        return items.get(SLOT_OPTIC);
    }

    public void setOptic(ItemStack optic) {
        items.set(SLOT_OPTIC, optic);
        sendUpdate();
    }

    public ItemStack getBarrel() {
        return items.get(SLOT_BARREL);
    }

    public void setBarrel(ItemStack barrel) {
        items.set(SLOT_BARREL, barrel);
        sendUpdate();
    }

    public ItemStack getUnderbarrel() {
        return items.get(SLOT_UNDERBARREL);
    }

    public void setUnderbarrel(ItemStack underbarrel) {
        items.set(SLOT_UNDERBARREL, underbarrel);
        sendUpdate();
    }

    public float getCleanliness() {
        return cleanliness;
    }

    public void setCleanliness(float cleanliness) {
        this.cleanliness = Math.max(0.0f, Math.min(1.0f, cleanliness));
        sendUpdate();
    }

    public float getWear() {
        return wear;
    }

    public void setWear(float wear) {
        this.wear = Math.max(0.0f, Math.min(1.0f, wear));
        sendUpdate();
    }

    public void sendUpdate() {
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_ALL);
        }
    }

    @Override
    public int getContainerSize() {
        return TOTAL_SLOTS;
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack item : items) {
            if (!item.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int slot) {
        if (slot >= 0 && slot < TOTAL_SLOTS) {
            return items.get(slot);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItem(int slot, int amount) {
        ItemStack result = ContainerHelper.removeItem(items, slot, amount);
        if (!result.isEmpty()) {
            sendUpdate();
        }
        return result;
    }

    @Override
    public ItemStack removeItemNoUpdate(int slot) {
        ItemStack result = ContainerHelper.takeItem(items, slot);
        if (!result.isEmpty()) {
            sendUpdate();
        }
        return result;
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        if (slot >= 0 && slot < TOTAL_SLOTS) {
            items.set(slot, stack);
            if (stack.getCount() > getMaxStackSize()) {
                stack.setCount(getMaxStackSize());
            }
            sendUpdate();
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return Container.stillValidBlockEntity(this, player);
    }

    @Override
    public void clearContent() {
        items.clear();
        sendUpdate();
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        items.clear();
        ContainerHelper.loadAllItems(tag, items);
        this.cleanliness = tag.contains("Cleanliness") ? tag.getFloat("Cleanliness") : 1.0f;
        this.wear = tag.contains("Wear") ? tag.getFloat("Wear") : 0.0f;
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        ContainerHelper.saveAllItems(tag, items);
        tag.putFloat("Cleanliness", this.cleanliness);
        tag.putFloat("Wear", this.wear);
    }

    @Override
    public CompoundTag getUpdateTag() {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }
}
