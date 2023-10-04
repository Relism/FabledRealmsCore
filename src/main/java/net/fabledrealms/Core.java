package net.fabledrealms;

import net.fabledrealms.commands.GraveyardCommand;
import net.fabledrealms.graveyards.GraveyardManager;
import net.fabledrealms.listeners.PlayerDeathListener;
import net.fabledrealms.listeners.PlayerJoinListener;
import net.fabledrealms.listeners.ServerPingListener;
import net.fabledrealms.util.StringUtil;
import net.fabledrealms.wrappers.DatabaseWrapper;
import net.fabledrealms.wrappers.FileWrapper;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    //Plugin Startup

    private FileWrapper configFileWrapper;
    private FileWrapper langFileWrapper;
    private DatabaseWrapper playerDatabaseWrapper;
    private GraveyardManager graveyardManager;
    private StringUtil stringUtil;

    @Override
    public void onEnable() {
        registerManagers();
        registerDatabases();
        registerUtility();
        registerEvents();
        registerCommands();
    }

    private void registerManagers(){
        this.configFileWrapper = new FileWrapper(this, this.getDataFolder().getPath(), "config.yml");
        this.langFileWrapper = new FileWrapper(this, this.getDataFolder().getPath(), "lang.yml");
        this.graveyardManager = new GraveyardManager(this);
    }
    private void registerUtility(){
        this.stringUtil = new StringUtil(this);
    }
    private void registerEvents(){
        new ServerPingListener(this);
        new PlayerJoinListener(this);
        new PlayerDeathListener(this);
    }
    private void registerCommands(){
        new GraveyardCommand(this);
    }
    private void registerDatabases(){
        this.playerDatabaseWrapper = new DatabaseWrapper(this, "player-data");
    }

    public FileWrapper getConfigFile() {
        return configFileWrapper;
    }
    public FileWrapper getLangFile(){return langFileWrapper;}
    public DatabaseWrapper getPlayerDatabase(){return playerDatabaseWrapper;}
    public StringUtil getStringUtil() {
        return stringUtil;
    }
    public GraveyardManager getGraveyardManager(){return graveyardManager;}


    //Plugin Shutdown
    @Override
    public void onDisable() {
        playerDatabaseWrapper.disconnect();
    }
}
