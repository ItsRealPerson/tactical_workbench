package dev.itsrealperson.tacticalworkbench.client.screen;

import dev.itsrealperson.tacticalworkbench.block.TacticalWorkbenchBlock;
import dev.itsrealperson.tacticalworkbench.block.entity.TacticalWorkbenchBlockEntity;
import dev.itsrealperson.tacticalworkbench.client.camera.DiegeticCameraController;
import dev.itsrealperson.tacticalworkbench.item.TacticalAttachmentItem;
import dev.itsrealperson.tacticalworkbench.item.TacticalRifleItem;
import dev.itsrealperson.tacticalworkbench.registry.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.lwjgl.glfw.GLFW;

import java.util.Random;

public class WorkbenchScreen extends Screen {
    private final TacticalWorkbenchBlockEntity blockEntity;
    private final Random random = new Random();

    // Slot box layout constants
    private static final int SLOT_COUNT = 4;
    private static final int BOX_WIDTH = 70;
    private static final int BOX_HEIGHT = 46;
    private static final int BOX_SPACING = 6;

    public WorkbenchScreen(TacticalWorkbenchBlockEntity blockEntity) {
        super(Component.translatable("screen.tactical_workbench.title"));
        this.blockEntity = blockEntity;
    }

    @Override
    protected void init() {
        super.init();

        // Start diegetic camera animation
        Direction facing = Direction.NORTH;
        if (blockEntity.getBlockState().hasProperty(TacticalWorkbenchBlock.FACING)) {
            facing = blockEntity.getBlockState().getValue(TacticalWorkbenchBlock.FACING);
        }
        DiegeticCameraController.start(blockEntity.getBlockPos(), facing);

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        int btnWidth = 96;
        int btnHeight = 20;
        int btnY = centerY + 34;

        // Button 1: Ciclar Cerrojo (Cycle Bolt)
        this.addRenderableWidget(Button.builder(Component.translatable("screen.tactical_workbench.btn.bolt"), button -> {
            playUiSound(ModSounds.BOLT_CYCLE.get());
            spawnParticles(ParticleTypes.SMOKE, 6);
        }).bounds(centerX - 150, btnY, btnWidth, btnHeight).build());

        // Button 2: Limpiar y Aceitar (Clean & Oil)
        this.addRenderableWidget(Button.builder(Component.translatable("screen.tactical_workbench.btn.oil"), button -> {
            blockEntity.setCleanliness(blockEntity.getCleanliness() + 0.15f);
            playUiSound(ModSounds.OIL_WIPE.get());
            spawnParticles(ParticleTypes.CRIT, 8);
        }).bounds(centerX - 48, btnY, btnWidth, btnHeight).build());

        // Button 3: Ajustar Tornillos (Tighten Screws)
        this.addRenderableWidget(Button.builder(Component.translatable("screen.tactical_workbench.btn.screws"), button -> {
            blockEntity.setWear(blockEntity.getWear() - 0.10f);
            playUiSound(ModSounds.SCREW_TURN.get());
            spawnParticles(ParticleTypes.ENCHANTED_HIT, 5);
        }).bounds(centerX + 54, btnY, btnWidth, btnHeight).build());

        // Button 4: Salir [ESC]
        this.addRenderableWidget(Button.builder(Component.translatable("screen.tactical_workbench.btn.exit"), button -> {
            this.onClose();
        }).bounds(centerX - 50, btnY + 26, 100, 20).build());
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics) {
        // Diegetic: no dark gradient background to preserve immersive 3D world view
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        renderBackground(guiGraphics);

        int centerX = this.width / 2;
        int centerY = this.height / 2;

        // Tactical Schematic Reticle / Frame
        renderTacticalFrame(guiGraphics, centerX, centerY);

        // Render Slot Boxes
        renderSlotBoxes(guiGraphics, centerX, centerY, mouseX, mouseY);

        // Render Condition / Cleanliness Bar
        renderConditionBar(guiGraphics, centerX, centerY);

        // Render widgets (Buttons)
        super.render(guiGraphics, mouseX, mouseY, partialTicks);

        // Render Tooltips for hovered slots
        renderSlotTooltips(guiGraphics, centerX, centerY, mouseX, mouseY);
    }

    private void renderTacticalFrame(GuiGraphics guiGraphics, int centerX, int centerY) {
        int frameW = 320;
        int frameH = 150;
        int x1 = centerX - frameW / 2;
        int y1 = centerY - 72;
        int x2 = x1 + frameW;
        int y2 = y1 + frameH;

        // Semi-transparent tactical blueprint background
        guiGraphics.fill(x1, y1, x2, y2, 0x660A1017);

        // Corner brackets (tactical HUD style)
        int cornerLen = 12;
        int cornerColor = 0xFF38BDF8; // Tactical cyan

        // Top-Left corner
        guiGraphics.fill(x1, y1, x1 + cornerLen, y1 + 2, cornerColor);
        guiGraphics.fill(x1, y1, x1 + 2, y1 + cornerLen, cornerColor);

        // Top-Right corner
        guiGraphics.fill(x2 - cornerLen, y1, x2, y1 + 2, cornerColor);
        guiGraphics.fill(x2 - 2, y1, x2, y1 + cornerLen, cornerColor);

        // Bottom-Left corner
        guiGraphics.fill(x1, y2 - 2, x1 + cornerLen, y2, cornerColor);
        guiGraphics.fill(x1, y2 - cornerLen, x1 + 2, y2, cornerColor);

        // Bottom-Right corner
        guiGraphics.fill(x2 - cornerLen, y2 - 2, x2, y2, cornerColor);
        guiGraphics.fill(x2 - 2, y2 - cornerLen, x2, y2, cornerColor);

        // Header Title
        String titleStr = "[ TACTICAL WORKBENCH // SYSTEM DIEGETIC ]";
        guiGraphics.drawCenteredString(this.font, titleStr, centerX, y1 + 6, 0xFF38BDF8);
    }

    private void renderSlotBoxes(GuiGraphics guiGraphics, int centerX, int centerY, int mouseX, int mouseY) {
        int totalWidth = (BOX_WIDTH * SLOT_COUNT) + (BOX_SPACING * (SLOT_COUNT - 1));
        int startX = centerX - totalWidth / 2;
        int startY = centerY - 52;

        String[] slotNames = {"PRIMARY", "OPTIC", "BARREL", "UNDERBARREL"};

        for (int i = 0; i < SLOT_COUNT; i++) {
            int boxX = startX + i * (BOX_WIDTH + BOX_SPACING);
            int boxY = startY;

            boolean hovered = mouseX >= boxX && mouseX <= boxX + BOX_WIDTH && mouseY >= boxY && mouseY <= boxY + BOX_HEIGHT;

            // Box background & border
            int bgColor = hovered ? 0x991E293B : 0x880F172A;
            int borderColor = hovered ? 0xFF00E5FF : 0xFF334155;

            guiGraphics.fill(boxX, boxY, boxX + BOX_WIDTH, boxY + BOX_HEIGHT, bgColor);
            renderOutlineBox(guiGraphics, boxX, boxY, BOX_WIDTH, BOX_HEIGHT, borderColor);

            // Slot label
            guiGraphics.drawCenteredString(this.font, slotNames[i], boxX + BOX_WIDTH / 2, boxY + 4, 0xFF94A3B8);

            // Item representation
            ItemStack item = blockEntity.getItem(i);
            int itemX = boxX + (BOX_WIDTH - 16) / 2;
            int itemY = boxY + 18;

            if (!item.isEmpty()) {
                guiGraphics.renderItem(item, itemX, itemY);
                guiGraphics.renderItemDecorations(this.font, item, itemX, itemY);
            } else {
                // Empty slot placeholder indicator
                guiGraphics.drawCenteredString(this.font, "[-]", boxX + BOX_WIDTH / 2, itemY + 4, 0xFF475569);
            }
        }
    }

    private void renderConditionBar(GuiGraphics guiGraphics, int centerX, int centerY) {
        int barW = 298;
        int barH = 10;
        int barX = centerX - barW / 2;
        int barY = centerY + 10;

        float clean = blockEntity.getCleanliness();
        float wear = blockEntity.getWear();

        // Background
        guiGraphics.fill(barX, barY, barX + barW, barY + barH, 0xFF0F172A);
        renderOutlineBox(guiGraphics, barX, barY, barW, barH, 0xFF334155);

        // Dynamic Color: Green > 70%, Yellow > 30%, Red <= 30%
        int barColor;
        if (clean > 0.70f) {
            barColor = 0xFF22C55E; // Emerald green
        } else if (clean > 0.30f) {
            barColor = 0xFFEAB308; // Amber yellow
        } else {
            barColor = 0xFFEF4444; // Warning red
        }

        int filledWidth = (int) ((barW - 2) * Math.max(0.0f, Math.min(1.0f, clean)));
        if (filledWidth > 0) {
            guiGraphics.fill(barX + 1, barY + 1, barX + 1 + filledWidth, barY + barH - 1, barColor);
        }

        // Text summary
        String statusText = String.format("COND: %d%%  |  WEAR: %d%%", (int) (clean * 100), (int) (wear * 100));
        guiGraphics.drawCenteredString(this.font, statusText, centerX, barY + 14, 0xFFCBD5E1);
    }

    private void renderSlotTooltips(GuiGraphics guiGraphics, int centerX, int centerY, int mouseX, int mouseY) {
        int totalWidth = (BOX_WIDTH * SLOT_COUNT) + (BOX_SPACING * (SLOT_COUNT - 1));
        int startX = centerX - totalWidth / 2;
        int startY = centerY - 52;

        String[] slotDescriptions = {
                "screen.tactical_workbench.slot.primary",
                "screen.tactical_workbench.slot.optic",
                "screen.tactical_workbench.slot.barrel",
                "screen.tactical_workbench.slot.underbarrel"
        };

        for (int i = 0; i < SLOT_COUNT; i++) {
            int boxX = startX + i * (BOX_WIDTH + BOX_SPACING);
            int boxY = startY;

            if (mouseX >= boxX && mouseX <= boxX + BOX_WIDTH && mouseY >= boxY && mouseY <= boxY + BOX_HEIGHT) {
                ItemStack item = blockEntity.getItem(i);
                if (!item.isEmpty()) {
                    guiGraphics.renderTooltip(this.font, item, mouseX, mouseY);
                } else {
                    guiGraphics.renderTooltip(this.font, Component.translatable(slotDescriptions[i]), mouseX, mouseY);
                }
                break;
            }
        }
    }

    private void renderOutlineBox(GuiGraphics guiGraphics, int x, int y, int w, int h, int color) {
        guiGraphics.fill(x, y, x + w, y + 1, color);
        guiGraphics.fill(x, y + h - 1, x + w, y + h, color);
        guiGraphics.fill(x, y, x + 1, y + h, color);
        guiGraphics.fill(x + w - 1, y, x + w, y + h, color);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int centerX = this.width / 2;
        int centerY = this.height / 2;
        int totalWidth = (BOX_WIDTH * SLOT_COUNT) + (BOX_SPACING * (SLOT_COUNT - 1));
        int startX = centerX - totalWidth / 2;
        int startY = centerY - 52;

        Player player = Minecraft.getInstance().player;
        if (player != null) {
            for (int i = 0; i < SLOT_COUNT; i++) {
                int boxX = startX + i * (BOX_WIDTH + BOX_SPACING);
                int boxY = startY;

                if (mouseX >= boxX && mouseX <= boxX + BOX_WIDTH && mouseY >= boxY && mouseY <= boxY + BOX_HEIGHT) {
                    handleSlotClick(player, i);
                    return true;
                }
            }
        }

        return super.mouseClicked(mouseX, mouseY, button);
    }

    private void handleSlotClick(Player player, int slotIndex) {
        ItemStack slotItem = blockEntity.getItem(slotIndex);
        ItemStack heldItem = player.getMainHandItem();

        if (!slotItem.isEmpty()) {
            // Retrieve item from workbench to player inventory
            if (player.getInventory().add(slotItem.copy())) {
                blockEntity.setItem(slotIndex, ItemStack.EMPTY);
                playUiSound(ModSounds.ATTACH_REMOVE.get());
            }
        } else if (!heldItem.isEmpty() && isItemValidForSlot(heldItem, slotIndex)) {
            // Place item into workbench slot
            ItemStack toInsert = heldItem.split(1);
            blockEntity.setItem(slotIndex, toInsert);
            playUiSound(ModSounds.ATTACH_EQUIP.get());
        }
    }

    private boolean isItemValidForSlot(ItemStack stack, int slotIndex) {
        return switch (slotIndex) {
            case TacticalWorkbenchBlockEntity.SLOT_PRIMARY -> stack.getItem() instanceof TacticalRifleItem || !stack.isEmpty();
            case TacticalWorkbenchBlockEntity.SLOT_OPTIC -> stack.getItem() instanceof TacticalAttachmentItem att && att.getSlot() == TacticalAttachmentItem.AttachmentSlot.OPTIC;
            case TacticalWorkbenchBlockEntity.SLOT_BARREL -> stack.getItem() instanceof TacticalAttachmentItem att && att.getSlot() == TacticalAttachmentItem.AttachmentSlot.BARREL;
            case TacticalWorkbenchBlockEntity.SLOT_UNDERBARREL -> stack.getItem() instanceof TacticalAttachmentItem att && att.getSlot() == TacticalAttachmentItem.AttachmentSlot.UNDERBARREL;
            default -> false;
        };
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button, double dragX, double dragY) {
        DiegeticCameraController.onMouseTurn(dragX, dragY);
        return super.mouseDragged(mouseX, mouseY, button, dragX, dragY);
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
            this.onClose();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void onClose() {
        DiegeticCameraController.stop();
        super.onClose();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    private void playUiSound(net.minecraft.sounds.SoundEvent soundEvent) {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(soundEvent, 1.0f));
    }

    private void spawnParticles(net.minecraft.core.particles.ParticleOptions particleType, int count) {
        if (Minecraft.getInstance().level != null) {
            double px = blockEntity.getBlockPos().getX() + 0.5;
            double py = blockEntity.getBlockPos().getY() + 0.9;
            double pz = blockEntity.getBlockPos().getZ() + 0.5;

            for (int i = 0; i < count; i++) {
                double vx = (random.nextDouble() - 0.5) * 0.1;
                double vy = random.nextDouble() * 0.08 + 0.02;
                double vz = (random.nextDouble() - 0.5) * 0.1;
                Minecraft.getInstance().level.addParticle(particleType, px, py, pz, vx, vy, vz);
            }
        }
    }

    public TacticalWorkbenchBlockEntity getBlockEntity() {
        return blockEntity;
    }
}
