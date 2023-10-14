package net.fabledrealms.listeners.manager;

import net.fabledrealms.Core;
import net.fabledrealms.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;

import java.util.HashSet;
import java.util.Set;

public class ListenerManager {

    private final Core core;
    private Set<Listener> events = new HashSet<>();

    public ListenerManager(Core core) {
        this.core = core;


        events.add(new ServerPingListener(core));
        events.add(new PlayerJoinListener(core));
        events.add(new PlayerDeathListener(core));
        events.add(new PlayerMoveListener(core));
        events.add(new PlayerInteractListener(core));
        events.add(new InventoryClickListener(core));

        PluginManager pm = Bukkit.getPluginManager();
        for(Listener event : events) {
            pm.registerEvents(event, core);
        }
    }

}
