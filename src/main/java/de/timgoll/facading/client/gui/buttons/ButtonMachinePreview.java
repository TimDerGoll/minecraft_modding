package de.timgoll.facading.client.gui.buttons;

public class ButtonMachinePreview extends ButtonMachine {

    public ButtonMachinePreview(int buttonId, int x, int y, int textureX, int textureY, int textureWidth, int textureHeight) {
        super(
                buttonId,
                x,
                y,
                textureX + 2 * textureWidth,
                textureY,
                textureX + textureWidth,
                textureY,
                textureX,
                textureY,
                textureWidth,
                textureHeight,
                "textures/gui/machinefacadingbench.png"
        );
    }

}
