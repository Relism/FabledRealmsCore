package net.fabledrealms;

import net.fabledrealms.managers.FileManager;
import net.fabledrealms.util.StringUtil;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    //Plugin Startup

    private FileManager configFileManager;
    private StringUtil stringUtil;

    @Override
    public void onEnable() {
    }

    private void registerManagers(){
        this.configFileManager = new FileManager(this, this.getDataFolder().getPath(), "config.yml");
        this.configFileManager = new FileManager(this, this.getDataFolder().getPath(), "lang.yml");
    }
    private void registerUtility(){
        this.stringUtil = new StringUtil(this);
    }
    private void registerEvents(){}
    private void registerCommands(){}

    public FileManager getConfigFileManager() {
        return configFileManager;
    }

    public StringUtil getStringUtil() {
        return stringUtil;
    }

    //Plugin Shutdown
    @Override
    public void onDisable() {

    }
}
