package de.timgoll.facading.client.gui.buttons;

public class GuiFacadingbenchButtons_square extends GuiFacading {

    public GuiFacadingbenchButtons_square(int buttonId, int x, int y, int textureX, int textureY, int textureWidth, int textureHeight) {
        super(
                buttonId,
                x,
                y,
                textureX,
                textureY + 2 * textureHeight,
                textureX,
                textureY + textureHeight,
                textureX,
                textureY,
                textureWidth,
                textureHeight,
                "textures/gui/facadingbench.png"
        );
    }


}
