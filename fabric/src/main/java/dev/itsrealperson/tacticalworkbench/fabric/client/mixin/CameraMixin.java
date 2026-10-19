package dev.itsrealperson.tacticalworkbench.fabric.client.mixin;

import dev.itsrealperson.tacticalworkbench.client.camera.DiegeticCameraController;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Camera.class)
public abstract class CameraMixin {
    @Inject(method = "setup", at = @At("TAIL"))
    private void tacticalworkbench$onCameraSetup(BlockGetter level, Entity entity, boolean detached, boolean mirrored, float partialTick, CallbackInfo ci) {
        DiegeticCameraController.applyCameraTransform((Camera) (Object) this, partialTick);
    }
}
