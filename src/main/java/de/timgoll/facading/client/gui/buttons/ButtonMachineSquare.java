package de.timgoll.facading.client.gui.buttons;

public class ButtonMachineSquare extends ButtonMachine {

    public ButtonMachineSquare(int buttonId, int x, int y, int textureX, int textureY, int textureWidth, int textureHeight) {
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
                "textures/gui/machinefacadingbench.png"
        );
    }


}
