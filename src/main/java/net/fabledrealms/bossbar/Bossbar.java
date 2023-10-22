package net.fabledrealms.bossbar;

import net.fabledrealms.Core;
import org.bukkit.entity.Player;

public interface Bossbar {
    void init(Core core, Player player);
    void display();
    void remove();
}
