package net.fabledrealms.dungeon.manager;

import net.fabledrealms.Core;
import net.fabledrealms.dungeon.Dungeon;

import java.util.*;

public class DungeonManager {

    private final Core main;
    private final Set<Dungeon> dungeons = new HashSet<>();

    public DungeonManager(Core main) {
        this.main = main;
    }

    public void load(){
        if (this.main.getDungeonFileWrapper().getFile().getConfigurationSection("dungeon") == null) return;
        for (final String dungeon : Objects.requireNonNull(this.main.getDungeonFileWrapper().getFile().getConfigurationSection("dungeon")).getKeys(false)) {
            dungeons.add(new Dungeon(this.main.getDungeonFileWrapper().getFile().getString("dungeon." + dungeon + ".name"),
                    this.main.getDungeonFileWrapper().getFile().getLocation("dungeon." + dungeon + ".spawn"),
                    this.main.getDungeonFileWrapper().getFile().getLocation("dungeon." + dungeon + ".first"),
                    this.main.getDungeonFileWrapper().getFile().getLocation("dungeon." + dungeon + ".second"),
                    (Map<Integer, List<String>>) this.main.getDungeonFileWrapper().getFile().get("dungeon." + dungeon + ".chests"),
                    (Map<Integer, List<String>>) this.main.getDungeonFileWrapper().getFile().get("dungeon." + dungeon + ".mobs")));
        }
    }

    public void save(){
        dungeons.forEach(dungeon -> {
            this.main.getDungeonFileWrapper().getFile().set("dungeon." + dungeon.getName() + ".name", dungeon.getName());
            this.main.getDungeonFileWrapper().getFile().set("dungeon." + dungeon.getName() + ".spawn", dungeon.getDungeonSpawn());
            this.main.getDungeonFileWrapper().getFile().set("dungeon." + dungeon.getName() + ".first", dungeon.getDungeonClaimOne());
            this.main.getDungeonFileWrapper().getFile().set("dungeon." + dungeon.getName() + ".second", dungeon.getDungeonClaimTwo());
            this.main.getDungeonFileWrapper().getFile().set("dungeon." + dungeon.getName() + ".chests", dungeon.getChests());
            this.main.getDungeonFileWrapper().getFile().set("dungeon." + dungeon.getName() + ".mobs", dungeon.getMobs());

            this.main.getDungeonFileWrapper().saveFile();
        });
    }

    public Set<Dungeon> getDungeons() {
        return dungeons;
    }
}
