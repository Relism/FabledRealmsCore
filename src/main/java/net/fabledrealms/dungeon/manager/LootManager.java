package net.fabledrealms.dungeon.manager;

import net.fabledrealms.Core;
import net.fabledrealms.dungeon.ChestType;
import net.fabledrealms.itemgen.ItemType;
import net.fabledrealms.util.serializer.ItemStackSerializer;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class LootManager {

    private final Core main;
    private final Map<Integer, Map<Integer, List<ItemStack>>> map = new HashMap<>();

    public LootManager(Core main) {
        this.main = main;
    }

    public void load(){
        if (this.main.getDungeonFileWrapper().getFile().getConfigurationSection("loot") == null) return;
        for (final String index : Objects.requireNonNull(this.main.getDungeonFileWrapper().getFile().getConfigurationSection("loot")).getKeys(false)) {
            ItemStack itemStack = new ItemStack(Material.valueOf(this.main.getDungeonFileWrapper().getFile().getString("loot." + index + ".item")));
            itemStack.setAmount(this.main.getDungeonFileWrapper().getFile().getInt("loot." + index + ".amount"));
            this.main.getDungeonFileWrapper().getFile().getStringList("loot." + index + ".enchantments").forEach(enchant -> {
                itemStack.addUnsafeEnchantment(Objects.requireNonNull(Enchantment.getByName(enchant.split(":")[0])), Integer.parseInt(enchant.split(":")[1]));
            });
            ItemMeta meta = itemStack.getItemMeta();
            meta.setDisplayName(this.main.getStringUtil().colorString(this.main.getDungeonFileWrapper().getFile().getString("loot." + index + ".name")));
            meta.setLore(new ArrayList<>(this.main.getDungeonFileWrapper().getFile().getStringList("loot." + index + ".lore")));

            if (map.get(this.main.getDungeonFileWrapper().getFile().getInt("loot." + index + ".floor")) == null) {
                Map<Integer, List<ItemStack>> mapped = new HashMap<>();
                List<ItemStack> items = new ArrayList<>();
                items.add(itemStack);
                mapped.put(this.main.getDungeonFileWrapper().getFile().getInt("loot." + index + ".chance"), items);

                map.put(this.main.getDungeonFileWrapper().getFile().getInt("loot." + index + ".floor"), mapped);
            } else {
                if (map.get(this.main.getDungeonFileWrapper().getFile().getInt("loot." + index + ".floor")).get(this.main.getDungeonFileWrapper().getFile().getInt("loot." + index + ".chance")) == null) {
                    List<ItemStack> items = new ArrayList<>();
                    items.add(itemStack);
                    map.get(this.main.getDungeonFileWrapper().getFile().getInt("loot." + index + ".floor")).put(this.main.getDungeonFileWrapper().getFile().getInt("loot." + index + ".chance"), items);
                } else {
                    map.get(this.main.getDungeonFileWrapper().getFile().getInt("loot." + index + ".floor")).get(this.main.getDungeonFileWrapper().getFile().getInt("loot." + index + ".chance")).add(itemStack);
                }
            }
        }
    }

    public Map<Integer, Map<Integer, List<ItemStack>>> getMap() {
        return map;
    }
}
