package net.fabledrealms.dungeon;

public enum ChestType {

    SMALL(26),
    LARGE(53);

    private final int slots;

    ChestType(int slots) {
        this.slots = slots;
    }

    public int getSlots() {
        return slots;
    }
}
