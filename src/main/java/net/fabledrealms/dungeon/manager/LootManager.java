package net.fabledrealms.dungeon.manager;

import net.fabledrealms.Core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class LootManager {

    private final Core main;
    private final Map<Integer, Map<Integer, List<String>>> map = new HashMap<>();

    public LootManager(Core main) {
        this.main = main;
    }

    public void load(){
        if (this.main.getDungeonFileWrapper().getFile().getConfigurationSection("floor") == null) return;
        for (String floor : Objects.requireNonNull(this.main.getDungeonFileWrapper().getFile().getConfigurationSection("floor")).getKeys(false)) {
            if (this.main.getDungeonFileWrapper().getFile().getConfigurationSection("floor." + floor + ".chance") == null) return;
            for (String chance : Objects.requireNonNull(this.main.getDungeonFileWrapper().getFile().getConfigurationSection("floor." + floor + ".chance")).getKeys(false)) {
                Map<Integer, List<String>> entries = new HashMap<>();
                entries.put(this.main.getDungeonFileWrapper().getFile().getInt("floor." + floor + ".chance-amount"),
                        this.main.getDungeonFileWrapper().getFile().getStringList("floor." + floor + ".chance." + chance + ".list"));
                map.put(this.main.getDungeonFileWrapper().getFile().getInt("floor." + floor + ".floor"), entries);
            }
        }
    }

    public void save(){
        for (Map.Entry<Integer, Map<Integer, List<String>>> entry : map.entrySet()) {
            this.main.getDungeonFileWrapper().getFile().set("floor." + entry.getKey() + ".floor", entry.getKey());
            entry.getValue().forEach((chance, list) -> {
                this.main.getDungeonFileWrapper().getFile().set("floor." + entry.getKey() + ".chance-amount", chance);
                this.main.getDungeonFileWrapper().getFile().set("floor." + entry.getKey() + ".chance." + chance + ".list", list)
            });
            this.main.getDungeonFileWrapper().saveFile();
        }
    }

     public Map<Integer, Map<Integer, List<String>>> getMap() {
        return map;
    }
}
