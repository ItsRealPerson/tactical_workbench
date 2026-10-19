package dev.itsrealperson.tacticalworkbench.mixin;

import net.minecraft.client.Camera;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Camera.class)
public interface CameraAccessor {
    @Invoker("setRotation")
    void tacticalworkbench$invokeSetRotation(float yRot, float xRot);

    @Invoker("setPosition")
    void tacticalworkbench$invokeSetPosition(double x, double y, double z);

    @Invoker("setPosition")
    void tacticalworkbench$invokeSetPosition(Vec3 pos);
}
