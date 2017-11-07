package de.timgoll.facading.misc;

import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;

public class EnumHandler {

    public enum MachineStates implements IStringSerializable {
        DEFAULT("default", 0),
        POWERED("powered", 1),
        WORKING("working", 2);

        private int ID;
        private String name;

        private MachineStates(String name, int ID) {
            this.ID = ID;
            this.name = name;
        }

        @Override
        public String getName() {
            return this.name;
        }

        public int getID() {
            return ID;
        }

        @Override
        public String toString() {
            return getName();
        }

    }

}
