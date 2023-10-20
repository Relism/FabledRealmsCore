package net.fabledrealms;

import net.fabledrealms.api.server;
import net.fabledrealms.character.CharacterManager;
import net.fabledrealms.command.manager.CommandManager;
import net.fabledrealms.compass.CompassManager;
import net.fabledrealms.dungeon.manager.DungeonManager;
import net.fabledrealms.economy.EconomyManager;
import net.fabledrealms.economy.command.EconomyCommand;
import net.fabledrealms.graveyards.GraveyardCommand;
import net.fabledrealms.graveyards.GraveyardManager;
import net.fabledrealms.gui.GUIManager;
import net.fabledrealms.itemgen.ItemManager;
import net.fabledrealms.listeners.manager.ListenerManager;
import net.fabledrealms.mongo.MongoHandler;
import net.fabledrealms.quests.QuestManager;
import net.fabledrealms.shop.command.ShopAdminCommand;
import net.fabledrealms.shop.manager.ShopManager;
import net.fabledrealms.util.LogUtil;
import net.fabledrealms.util.StringUtil;
import net.fabledrealms.util.misc;
import net.fabledrealms.util.msg;
import net.fabledrealms.wrappers.FileWrapper;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;

public final class Core extends JavaPlugin {

    private FileWrapper configFileWrapper;
    private FileWrapper langFileWrapper;
    private FileWrapper guiItemWrapper;
    private FileWrapper shopFileWrapper;
    private FileWrapper dungeonFileWrapper;
    private GraveyardManager graveyardManager;
    private CharacterManager characterManager;
    private CompassManager compassManager;
    private GUIManager guiManager;
    private StringUtil stringUtil;
    private EconomyManager economyManager;
    private DungeonManager dungeonManager;
    private QuestManager questManager;
    private ShopManager shopManager;
    private ItemManager itemManager;
    private MongoHandler mongoHandler;

    private final NamespacedKey productKey = new NamespacedKey(this, "product");
    server api = new server();
    private final misc misc = new misc();

    @Override
    public void onEnable() {
        registerUtility();
        registerManagers();
        registerDatabases();
        api.start(4007, "123");
        //this.characterManager = new CharacterManager(this);
        //this.characterManager.loadAllCharacters();
        this.economyManager = new EconomyManager(this);
        registerCommands();
        msg.log(misc.separator("&#34deeb", "DEPENDENCIES"));
        msg.log("Loading softdepends...");
        misc.checkSoftDep("PlaceholderAPI");
        msg.log("");
        LogUtil.sendLog("Startup complete!");
    }

    private void registerManagers(){
        msg.log(misc.separator("&#eb8c34", "MANAGERS REGISTERING"));
        this.configFileWrapper = new FileWrapper(this, this.getDataFolder().getPath(), "config.yml");
        this.langFileWrapper = new FileWrapper(this, this.getDataFolder().getPath(), "lang.yml");
        this.guiItemWrapper = new FileWrapper(this,this.getDataFolder().getPath(),"gui-items.yml");
        this.shopFileWrapper = new FileWrapper(this, this.getDataFolder().getPath(), "shops.yml");
        this.dungeonFileWrapper = new FileWrapper(this, this.getDataFolder().getPath(), "dungeon.yml");
        this.graveyardManager = new GraveyardManager(this);
        this.guiManager = new GUIManager(this);
        this.compassManager = new CompassManager(this);
        this.questManager = new QuestManager(this);
        new CommandManager(this);
        new ListenerManager(this);
        this.shopManager = new ShopManager(this);
        this.shopManager.loadShops();
        this.dungeonManager = new DungeonManager(this);
        this.dungeonManager.load();
        this.itemManager = new ItemManager(this);
        msg.log("");
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
        this.mongoHandler = new MongoHandler(this);
        this.mongoHandler.init();
    }

    public FileWrapper getConfigFile() {
        return configFileWrapper;
    }
    public FileWrapper getLangFile(){return langFileWrapper;}
    public FileWrapper getShopFileWrapper() {
        return shopFileWrapper;
    }
    public FileWrapper getGuiItemWrapper(){return guiItemWrapper;}
    public StringUtil getStringUtil() {
        return stringUtil;
    }

    public FileWrapper getDungeonFileWrapper() {
        return dungeonFileWrapper;
    }

    public GraveyardManager getGraveyardManager(){return graveyardManager;}
    public CharacterManager getCharacterManager(){return characterManager;}
    public GUIManager getGuiManager(){return guiManager;}

    public EconomyManager getEconomyManager() {
        return economyManager;
    }
    public CompassManager getCompassManager() { return compassManager;}
    public QuestManager getQuestManager() { return questManager; }
    public ItemManager getItemManager(){return itemManager;}

    public ShopManager getShopManager() {
        return shopManager;
    }

    public NamespacedKey getProductKey() {
        return productKey;
    }

    public MongoHandler getMongoHandler() {
        return mongoHandler;
    }

    //Plugin Shutdown
    @Override
    public void onDisable() {
        if (characterManager != null) {
            characterManager.saveAllCharacters();
        }
        if (shopManager != null) {
            shopManager.saveShops();
        }
        if (dungeonManager != null) {
            dungeonManager.save();
        }
    }
}