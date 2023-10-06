package net.fabledrealms.listeners;

import net.fabledrealms.Core;
import net.fabledrealms.animations.RespawnAnimation;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Objects;

public class PlayerDeathListener implements Listener {

    private final Core main;

    public PlayerDeathListener(Core main){
        this.main = main;
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onPlayerDealtLethalDamage(EntityDamageEvent event){
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (event.getDamage() >= player.getHealth()){
                event.setCancelled(true);
                main.getGraveyardManager().getClosestGraveyard(player);
                new RespawnAnimation(main, player);
                player.teleport(main.getGraveyardManager().getClosestGraveyard(player));
                player.setHealth(Objects.requireNonNull(player.getAttribute(Attribute.GENERIC_MAX_HEALTH)).getValue());
            }
        }
    }
}
