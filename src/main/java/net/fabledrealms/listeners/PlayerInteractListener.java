package net.fabledrealms.listeners;

import net.fabledrealms.Core;
import net.fabledrealms.gui.CharacterCreationGUI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;

public class PlayerInteractListener implements Listener {

    private final Core main;

    public PlayerInteractListener(Core main){
        this.main = main;
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void playerInteractWithNPC(PlayerInteractAtEntityEvent event){
        Player rightClicked = null;
        if (event.getRightClicked() instanceof Player){rightClicked = (Player) event.getRightClicked();}
        if (rightClicked.equals(null)){return;}
        if (rightClicked.getName().equals(main.getConfigFile().getFile().getString("module.character-creation.npc"))){
            main.getGuiManager().openInventory(event.getPlayer(), new CharacterCreationGUI(main, event.getPlayer()).getInventory());
        }
    }


}
