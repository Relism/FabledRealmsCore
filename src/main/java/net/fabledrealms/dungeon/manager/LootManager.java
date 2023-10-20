package net.fabledrealms.dungeon.manager;

import net.fabledrealms.Core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LootManager {

    private final Core main;
    private final Map<Integer, Map<Integer, List<String>>> map = new HashMap<>();

    public LootManager(Core main) {
        this.main = main;
    }

    public void load(){

    }

    public void save(){
        for (Map.Entry<Integer, Map<Integer, List<String>>> entry : map.entrySet()) {
            this.main.getDungeonFileWrapper().getFile().set("floor." + entry.getKey() + ".floor", entry.getKey());
            entry.getValue().forEach((chance, list) -> this.main.getDungeonFileWrapper().getFile().set("floor." + entry.getKey() + ".chance." + chance + ".list", list));
            this.main.getDungeonFileWrapper().saveFile();
        }
    }

     public Map<Integer, Map<Integer, List<String>>> getMap() {
        return map;
    }
}
