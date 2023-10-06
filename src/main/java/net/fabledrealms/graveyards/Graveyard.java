package net.fabledrealms.graveyards;

import net.fabledrealms.Core;

public class Graveyard {

    private final Core main;

    public Graveyard(Core main, String graveyardName, String worldName, int x, int y, int z){
        this.main = main;
        this.graveyardName = graveyardName;
        this.worldName = worldName;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    private String worldName;
    private String graveyardName;
    private int x;

    public Core getMain() {
        return main;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public String getGraveyardName() {
        return graveyardName;
    }

    public void setGraveyardName(String graveyardName) {
        this.graveyardName = graveyardName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    private int y;
    private int z;

}
