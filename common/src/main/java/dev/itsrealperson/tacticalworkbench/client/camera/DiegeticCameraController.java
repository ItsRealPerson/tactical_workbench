package dev.itsrealperson.tacticalworkbench.client.camera;

import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class DiegeticCameraController {
    private static boolean isActive = false;
    private static float progress = 0.0f;
    private static BlockPos workbenchPos = null;
    private static Direction facing = Direction.NORTH;
    private static float parallaxYaw = 0.0f;
    private static float parallaxPitch = 0.0f;

    private static Vec3 initialCamPos = null;
    private static float initialYaw = 0.0f;
    private static float initialPitch = 0.0f;

    public static void start(BlockPos pos, Direction facingDirection) {
        isActive = true;
        workbenchPos = pos;
        facing = facingDirection != null ? facingDirection : Direction.NORTH;
        parallaxYaw = 0.0f;
        parallaxPitch = 0.0f;

        Minecraft mc = Minecraft.getInstance();
        if (mc.gameRenderer != null && mc.gameRenderer.getMainCamera() != null) {
            Camera mainCam = mc.gameRenderer.getMainCamera();
            initialCamPos = mainCam.getPosition();
            initialYaw = mainCam.getYRot();
            initialPitch = mainCam.getXRot();
        } else if (mc.player != null) {
            initialCamPos = mc.player.getEyePosition();
            initialYaw = mc.player.getYRot();
            initialPitch = mc.player.getXRot();
        } else {
            initialCamPos = Vec3.ZERO;
            initialYaw = 0.0f;
            initialPitch = 0.0f;
        }
    }

    public static void stop() {
        isActive = false;
    }

    public static void update(float partialTicks) {
        float step = (partialTicks > 0.0f ? partialTicks : 1.0f) * 0.08f;
        if (isActive) {
            if (progress < 1.0f) {
                progress = Math.min(1.0f, progress + step);
            }
        } else {
            if (progress > 0.0f) {
                progress = Math.max(0.0f, progress - step);
            }
        }

        // Elastic dampening of parallax back towards 0
        float damp = Mth.clamp(0.12f * (partialTicks > 0.0f ? partialTicks : 1.0f), 0.01f, 0.5f);
        parallaxYaw = Mth.lerp(damp, parallaxYaw, 0.0f);
        parallaxPitch = Mth.lerp(damp, parallaxPitch, 0.0f);

        if (Math.abs(parallaxYaw) < 0.001f) parallaxYaw = 0.0f;
        if (Math.abs(parallaxPitch) < 0.001f) parallaxPitch = 0.0f;
    }

    public static void applyCameraTransform(Camera camera, float partialTicks) {
        if (!isActive && progress <= 0.0f) {
            return;
        }

        update(partialTicks);

        if (workbenchPos == null) {
            return;
        }

        // Workbench and Seat references
        double seatX = workbenchPos.getX() + 0.5 - facing.getStepX() * 0.55;
        double seatY = workbenchPos.getY() + 0.05;
        double seatZ = workbenchPos.getZ() + 0.5 - facing.getStepZ() * 0.55;

        // Camera target slightly behind the player looking at the bench
        double targetX = seatX - facing.getStepX() * 0.40;
        double targetY = seatY + 1.25;
        double targetZ = seatZ - facing.getStepZ() * 0.40;

        float targetYaw = facing.toYRot();
        float targetPitch = 38.0f;

        float t = easeInOutCubic(progress);

        if (initialCamPos == null) {
            initialCamPos = camera.getPosition();
            initialYaw = camera.getYRot();
            initialPitch = camera.getXRot();
        }

        double camX = Mth.lerp(t, initialCamPos.x, targetX);
        double camY = Mth.lerp(t, initialCamPos.y, targetY);
        double camZ = Mth.lerp(t, initialCamPos.z, targetZ);

        float camYaw = Mth.rotLerp(t, initialYaw, targetYaw + parallaxYaw);
        float camPitch = Mth.lerp(t, initialPitch, targetPitch + parallaxPitch);

        if (camera instanceof CameraAccessor accessor) {
            accessor.tacticalworkbench$invokeSetPosition(camX, camY, camZ);
            accessor.tacticalworkbench$invokeSetRotation(camYaw, camPitch);
        } else {
            try {
                var setPosMethod = Camera.class.getDeclaredMethod("setPosition", double.class, double.class, double.class);
                setPosMethod.setAccessible(true);
                setPosMethod.invoke(camera, camX, camY, camZ);

                var setRotMethod = Camera.class.getDeclaredMethod("setRotation", float.class, float.class);
                setRotMethod.setAccessible(true);
                setRotMethod.invoke(camera, camYaw, camPitch);
            } catch (Throwable ignored) {
            }
        }
    }

    public static void onMouseTurn(double dx, double dy) {
        parallaxYaw = Mth.clamp(parallaxYaw + (float) dx * 0.14f, -14.0f, 14.0f);
        parallaxPitch = Mth.clamp(parallaxPitch + (float) dy * 0.14f, -14.0f, 14.0f);
    }

    public static float easeInOutCubic(float x) {
        x = Mth.clamp(x, 0.0f, 1.0f);
        return x < 0.5f ? 4.0f * x * x * x : 1.0f - (float) Math.pow(-2.0f * x + 2.0f, 3) / 2.0f;
    }

    public static boolean isActive() {
        return isActive;
    }

    public static float getProgress() {
        return progress;
    }

    public static BlockPos getWorkbenchPos() {
        return workbenchPos;
    }

    public static Direction getFacing() {
        return facing;
    }

    public static float getParallaxYaw() {
        return parallaxYaw;
    }

    public static float getParallaxPitch() {
        return parallaxPitch;
    }

    public static float getCurrentYaw(float partialTicks) {
        float t = easeInOutCubic(progress);
        float targetYaw = facing.toYRot() + parallaxYaw;
        return Mth.rotLerp(t, initialYaw, targetYaw);
    }

    public static float getCurrentPitch(float partialTicks) {
        float t = easeInOutCubic(progress);
        float targetPitch = 38.0f + parallaxPitch;
        return Mth.lerp(t, initialPitch, targetPitch);
    }
}
