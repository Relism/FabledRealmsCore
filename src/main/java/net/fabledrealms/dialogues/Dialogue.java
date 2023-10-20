package net.fabledrealms.dialogues;

import net.fabledrealms.Core;
import net.fabledrealms.events.dialogueEvent;
import net.fabledrealms.util.msg;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Dialogue implements Listener {

    private final String dialogueID;
    private final Core FabledCore;
    private FileConfiguration dialogueConfig;
    private Player player;
    private int currentPhase;

    public Dialogue(Core FabledCore, String dialogueID, Player player) {
        this.dialogueID = dialogueID;
        this.player = player;
        this.currentPhase = 1;
        this.FabledCore = FabledCore;
        loadDialogueConfig();
    }

    @EventHandler
    public void onDialogueEvent(dialogueEvent event) {
        /*if(event.getPlayer() == player){
            msg.send(player,  "You selected : " + event.getOption());
        }*/
        msg.send(player,  "You selected : " + event.getOption());
    }

    public void start(){
        //i didn't work on the dialogueConfig loading implementation yet, so im hardcoding 3 options for now :P
        String[] options = {"Option 1", "Option 2", "Option 3"};
        OptionHandler oh = new OptionHandler(FabledCore, options, player);
        oh.handleOptions();
    }


    private void loadDialogueConfig() {
        File dialogueFile = new File(FabledCore.getDataFolder() + "/dialogues", dialogueID + ".yml");

        if (!dialogueFile.exists()) {
            return;
        }

        dialogueConfig = YamlConfiguration.loadConfiguration(dialogueFile);
    }
}
