package net.fabledrealms.util;

import org.bukkit.Bukkit;

public class LogUtil {

    public static void sendLog(String message){
        Bukkit.getLogger().info(message);
    }

}
