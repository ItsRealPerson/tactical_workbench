package dev.itsrealperson.tacticalworkbench.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.itsrealperson.tacticalworkbench.block.entity.TacticalWorkbenchBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;

public class TacticalWorkbenchRenderer implements BlockEntityRenderer<TacticalWorkbenchBlockEntity> {
    private final ItemRenderer itemRenderer;

    public TacticalWorkbenchRenderer(BlockEntityRendererProvider.Context context) {
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public void render(TacticalWorkbenchBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        ItemStack primary = blockEntity.getPrimaryWeapon();
        if (primary.isEmpty()) {
            return;
        }

        Direction facing = Direction.NORTH;
        if (blockEntity.getBlockState().hasProperty(HorizontalDirectionalBlock.FACING)) {
            facing = blockEntity.getBlockState().getValue(HorizontalDirectionalBlock.FACING);
        }

        poseStack.pushPose();

        // Position on top of the workbench mat (14/16 block height + slight offset)
        poseStack.translate(0.5D, 0.885D, 0.5D);

        // Rotate according to workbench facing
        float yRot = -facing.toYRot();
        poseStack.mulPose(Axis.YP.rotationDegrees(yRot));

        // Lay weapon horizontally on the mat
        poseStack.mulPose(Axis.XP.rotationDegrees(90.0F));
        float scale = 0.55F;
        poseStack.scale(scale, scale, scale);

        // Render Primary Weapon resting in center
        itemRenderer.renderStatic(primary, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), 0);

        // Render Optic attachment if present (mounted on top rail)
        ItemStack optic = blockEntity.getOptic();
        if (!optic.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.02D, 0.22D, 0.02D);
            poseStack.scale(0.55F, 0.55F, 0.55F);
            itemRenderer.renderStatic(optic, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
            poseStack.popPose();
        }

        // Render Barrel attachment if present (mounted at muzzle)
        ItemStack barrel = blockEntity.getBarrel();
        if (!barrel.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.48D, 0.05D, 0.0D);
            poseStack.scale(0.50F, 0.50F, 0.50F);
            itemRenderer.renderStatic(barrel, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
            poseStack.popPose();
        }

        // Render Underbarrel attachment if present (mounted under handguard)
        ItemStack underbarrel = blockEntity.getUnderbarrel();
        if (!underbarrel.isEmpty()) {
            poseStack.pushPose();
            poseStack.translate(0.20D, -0.15D, 0.0D);
            poseStack.scale(0.50F, 0.50F, 0.50F);
            itemRenderer.renderStatic(underbarrel, ItemDisplayContext.FIXED, packedLight, packedOverlay, poseStack, bufferSource, blockEntity.getLevel(), 0);
            poseStack.popPose();
        }

        poseStack.popPose();
    }
}
