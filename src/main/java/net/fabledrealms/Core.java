package net.fabledrealms;

import net.fabledrealms.character.CharacterManager;
import net.fabledrealms.commands.GraveyardCommand;
import net.fabledrealms.graveyards.GraveyardManager;
import net.fabledrealms.gui.GUIManager;
import net.fabledrealms.listeners.*;
import net.fabledrealms.util.LogUtil;
import net.fabledrealms.util.StringUtil;
import net.fabledrealms.wrappers.DatabaseWrapper;
import net.fabledrealms.wrappers.FileWrapper;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    //Plugin Startup

    private FileWrapper configFileWrapper;
    private FileWrapper langFileWrapper;
    private FileWrapper guiItemWrapper;
    private DatabaseWrapper playerDatabaseWrapper;
    private GraveyardManager graveyardManager;
    private CharacterManager characterManager;
    private GUIManager guiManager;
    private StringUtil stringUtil;

    @Override
    public void onEnable() {
        registerManagers();
        registerDatabases();
        registerUtility();
        registerEvents();
        registerCommands();
        LogUtil.sendLog("Startup complete!");
    }

    private void registerManagers(){
        this.configFileWrapper = new FileWrapper(this, this.getDataFolder().getPath(), "config.yml");
        this.langFileWrapper = new FileWrapper(this, this.getDataFolder().getPath(), "lang.yml");
        this.guiItemWrapper = new FileWrapper(this,this.getDataFolder().getPath(),"gui-items.yml");
        this.graveyardManager = new GraveyardManager(this);
        this.characterManager = new CharacterManager(this);
        this.guiManager = new GUIManager(this);
    }
    private void registerUtility(){
        this.stringUtil = new StringUtil(this);
    }
    private void registerEvents(){
        new ServerPingListener(this);
        new PlayerJoinListener(this);
        new PlayerDeathListener(this);
        new PlayerInteractListener(this);
        new InventoryClickListener(this);
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
    public FileWrapper getGuiItemWrapper(){return guiItemWrapper;}
    public DatabaseWrapper getPlayerDatabase(){return playerDatabaseWrapper;}
    public StringUtil getStringUtil() {
        return stringUtil;
    }
    public GraveyardManager getGraveyardManager(){return graveyardManager;}
    public CharacterManager getCharacterManager(){return characterManager;}
    public GUIManager getGuiManager(){return guiManager;}


    //Plugin Shutdown
    @Override
    public void onDisable() {
        characterManager.saveAllCharacters();
        playerDatabaseWrapper.disconnect();
    }
}
