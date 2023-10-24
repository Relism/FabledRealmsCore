package net.fabledrealms.bossbar;

import net.fabledrealms.Core;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class bbarManager {
    private final Map<BossbarType, Bossbar> bossbars = new HashMap<>();

    public bbarManager(Core core, Player p) {
        // Initialize and store your boss bars here
        for (BossbarType type : BossbarType.values()) {
            try {
                bossbars.put(type, type.getBossbarClass().getDeclaredConstructor().newInstance());
                bossbars.get(type).init(core, p);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void display(BossbarType type) {
        bossbars.get(type).display();
    }

    public void remove(BossbarType type) {
        bossbars.get(type).remove();
    }
}
