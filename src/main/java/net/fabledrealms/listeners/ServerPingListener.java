package net.fabledrealms.listeners;

import net.fabledrealms.Core;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class ServerPingListener implements Listener {

    private final Core main;

    public ServerPingListener(Core main){
        this.main = main;
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    //Formats the player count
    public  void formatPlayerCount(ServerListPingEvent event){
        int onlinePlayers = event.getNumPlayers();
        int maxPlayers = onlinePlayers + 1;
        event.setMaxPlayers(maxPlayers);
    }

    //Formats the server list motd
    private String getMOTDLine(int line){
        String value = main.getConfigFile().getFile().getString("module.server-list.motd.line_" + line);
        value = main.getStringUtil().colorString(value);
        return value;
    }

    private void formatMOTD(ServerListPingEvent event){
        String line1 = getMOTDLine(1);
        String line2 = getMOTDLine(2);
        String motd = line1 + "\n" + line2;
        event.setMotd(motd);
    }

    //Event logic
    @EventHandler
    public void onServerListPing(ServerListPingEvent event) {
        String modulePath = "module.server-list";
        if (main.getConfigFile().getFile().getBoolean(modulePath + ".player-count.enabled")){formatPlayerCount(event);}
        if (main.getConfigFile().getFile().getBoolean(modulePath+".motd.enabled")){formatMOTD(event);}
    }

}
