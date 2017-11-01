package de.timgoll.facading.client.gui.buttons;

import de.timgoll.facading.Facading;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class GuiFacading extends GuiButton {
    final ResourceLocation texture;
    private int x, y, textureX, textureY, textureX_hover, textureY_hover, textureX_enable, textureY_enable;

    public GuiFacading(int buttonId, int x, int y, int textureX, int textureY, int textureX_hover, int textureY_hover, int textureX_enable, int textureY_enable, int width, int height, String resourcePath) {
        super(buttonId, x, y, width, height, "");
        this.x = x;
        this.y = y;
        this.textureX = textureX;
        this.textureY = textureY;
        this.textureX_hover = textureX_hover;
        this.textureY_hover = textureY_hover;
        this.textureX_enable = textureX_enable;
        this.textureY_enable = textureY_enable;

        texture = new ResourceLocation(Facading.MODID, resourcePath);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (visible) {
            mc.renderEngine.bindTexture(texture);

            int textureY_tmp, textureX_tmp;

            if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height) {
                hovered = true;
            } else {
                hovered = false;
            }
            if (!enabled) {
                textureX_tmp = textureX_enable;
                textureY_tmp = textureY_enable;
            } else if(hovered) {
                textureX_tmp = textureX_hover;
                textureY_tmp = textureY_hover;
            } else {
                textureX_tmp = textureX;
                textureY_tmp = textureY;
            }
            drawTexturedModalRect(x, y, textureX_tmp, textureY_tmp, width, height);
        }
    }
}
