package net.fabledrealms.dungeon.manager;

import net.fabledrealms.Core;
import net.fabledrealms.util.serializer.ItemStackSerializer;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class LootManager {

    private final Core main;
    private final Random random = new Random();
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
                this.main.getDungeonFileWrapper().getFile().set("floor." + entry.getKey() + ".chance." + chance + ".list", list);
            });
            this.main.getDungeonFileWrapper().saveFile();
        }
    }

    public List<ItemStack> getChestReward(int floor){
        List<ItemStack> items = new ArrayList<>();

        for (int i = 0; i < this.main.getConfigFile().getFile().getInt("dungeon.item-per-chest"); i++) {
            int randomNumber = random.nextInt(100);

            for (Map.Entry<Integer, List<String>> mapped : this.map.get(floor).entrySet()) {
                if (randomNumber < mapped.getKey()) {
                    items.add(ItemStackSerializer.deSerialize(this.map.get(floor).get(mapped.getKey()).get(random.nextInt(this.map.get(floor).get(mapped.getKey()).size()))));
                }
            }
        }

        return items;
    }

    public Map<Integer, Map<Integer, List<String>>> getMap() {
        return map;
    }
}
