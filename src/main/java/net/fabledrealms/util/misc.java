package net.fabledrealms.util;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

public class misc {
    public void checkSoftDep(String pluginName){
        try {
            Plugin plugin = Bukkit.getPluginManager().getPlugin(pluginName);
            if (plugin != null) {
                msg.log("&#34deeb• &fHooked into " + plugin.getName() + " &#34deebv" + plugin.getDescription().getVersion() + "&f!");
            } else {
                msg.log("&#eb4034• &fCouldnt find " + pluginName + ", continuing without it");
            }
        } catch(Error e) {
            msg.log("&#eb4034• &fCouldnt find " + pluginName + ", continuing without it");
        }
    }

    public String separator(String... args) {
        String color = "";
        String text = "";
        if (args.length > 0) {
            color = args[0] + " ";
        }
        if (args.length > 1) {
            text = args[1] + " ";
        }
        if (color.isEmpty() && text.isEmpty()) {
            return "╰─────────────────────────────────────────────────────";
        } else {
            return "&f╭──[" + color + text + "&f]──────────────────────────────────────";
        }
    }
}
