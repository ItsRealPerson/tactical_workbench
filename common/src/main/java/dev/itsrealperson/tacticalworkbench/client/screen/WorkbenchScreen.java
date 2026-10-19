package dev.itsrealperson.tacticalworkbench.client.screen;

import dev.itsrealperson.tacticalworkbench.block.entity.TacticalWorkbenchBlockEntity;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class WorkbenchScreen extends Screen {
    private final TacticalWorkbenchBlockEntity blockEntity;

    public WorkbenchScreen(TacticalWorkbenchBlockEntity blockEntity) {
        super(Component.translatable("screen.tactical_workbench.title"));
        this.blockEntity = blockEntity;
    }

    @Override
    public void renderBackground(GuiGraphics guiGraphics) {
        // Diegetic: no dark gradient background to preserve immersive 3D world view
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public TacticalWorkbenchBlockEntity getBlockEntity() {
        return blockEntity;
    }
}
