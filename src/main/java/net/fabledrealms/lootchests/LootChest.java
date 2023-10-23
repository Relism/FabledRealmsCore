package net.fabledrealms.lootchests;

import net.fabledrealms.Core;
import net.fabledrealms.lootchests.droptables.DropTable;
import org.bukkit.Location;
import org.bukkit.Material;

public class LootChest {

    private Material material;
    private Location location;
    private DropTable dropTable;

    private final Core core;
    private final int ID;

    public LootChest(Core core, int ID, Material material, Location location, DropTable dropTable){
        this.core = core;
        this.ID = ID;
        this.material = material;
        this.location = location;
        this.dropTable = dropTable;
    }

    public Material getMaterial() {
        return material;
    }

    public Location getLocation() {
        return location;
    }

    public DropTable getDropTable() {
        return dropTable;
    }

    public Core getCore() {
        return core;
    }

    public int getID() {
        return ID;
    }
}
