package net.fabledrealms;

import net.fabledrealms.character.CharacterManager;
import net.fabledrealms.command.manager.CommandManager;
import net.fabledrealms.compass.CompassManager;
import net.fabledrealms.graveyards.GraveyardCommand;
import net.fabledrealms.economy.EconomyManager;
import net.fabledrealms.economy.command.EconomyCommand;
import net.fabledrealms.graveyards.GraveyardManager;
import net.fabledrealms.gui.GUIManager;
import net.fabledrealms.listeners.manager.ListenerManager;
import net.fabledrealms.quests.QuestManager;
import net.fabledrealms.shop.command.ShopAdminCommand;
import net.fabledrealms.shop.manager.ShopManager;
import net.fabledrealms.util.LogUtil;
import net.fabledrealms.util.StringUtil;
import net.fabledrealms.wrappers.DatabaseWrapper;
import net.fabledrealms.wrappers.FileWrapper;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Core extends JavaPlugin {

    //Plugin Startup

    private FileWrapper configFileWrapper;
    private FileWrapper langFileWrapper;
    private FileWrapper guiItemWrapper;
    private FileWrapper shopFileWrapper;
    private DatabaseWrapper playerDatabaseWrapper;
    private GraveyardManager graveyardManager;
    private CharacterManager characterManager;
    private CompassManager compassManager;
    private GUIManager guiManager;
    private StringUtil stringUtil;
    private EconomyManager economyManager;
    private QuestManager questManager;
    private ShopManager shopManager;

    @Override
    public void onEnable() {
        registerManagers();
        registerDatabases();
        this.characterManager = new CharacterManager(this);
        this.economyManager = new EconomyManager(this);
        registerUtility();
        registerCommands();
        LogUtil.sendLog("Startup complete!");
    }

    private void registerManagers(){
        this.configFileWrapper = new FileWrapper(this, this.getDataFolder().getPath(), "config.yml");
        this.langFileWrapper = new FileWrapper(this, this.getDataFolder().getPath(), "lang.yml");
        this.guiItemWrapper = new FileWrapper(this,this.getDataFolder().getPath(),"gui-items.yml");
        this.shopFileWrapper = new FileWrapper(this, this.getDataFolder().getPath(), "shops.yml");
        this.graveyardManager = new GraveyardManager(this);
        this.guiManager = new GUIManager(this);
        this.compassManager = new CompassManager(this);
        questManager = new QuestManager(this);
        new CommandManager(this);
        new ListenerManager(this);
        this.shopManager = new ShopManager(this);
        this.shopManager.loadShops();
    }
    private void registerUtility(){
        this.stringUtil = new StringUtil(this);
    }
    private void registerCommands(){
        new GraveyardCommand(this);
        new EconomyCommand(this);
        new ShopAdminCommand(this);
    }
    private void registerDatabases(){
        this.playerDatabaseWrapper = new DatabaseWrapper(this, "player-data");
        try {
            this.playerDatabaseWrapper.initialization();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public FileWrapper getConfigFile() {
        return configFileWrapper;
    }
    public FileWrapper getLangFile(){return langFileWrapper;}

    public FileWrapper getShopFileWrapper() {
        return shopFileWrapper;
    }

    public FileWrapper getGuiItemWrapper(){return guiItemWrapper;}
    public DatabaseWrapper getPlayerDatabase(){return playerDatabaseWrapper;}
    public StringUtil getStringUtil() {
        return stringUtil;
    }
    public GraveyardManager getGraveyardManager(){return graveyardManager;}
    public CharacterManager getCharacterManager(){return characterManager;}
    public GUIManager getGuiManager(){return guiManager;}

    public EconomyManager getEconomyManager() {
        return economyManager;
    }
    public CompassManager getCompassManager() { return compassManager;}
    public QuestManager getQuestManager() { return questManager; }

    public ShopManager getShopManager() {
        return shopManager;
    }

    //Plugin Shutdown
    @Override
    public void onDisable() {
        try {
            characterManager.saveAllCharacters();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        playerDatabaseWrapper.disconnect();
    }
}
