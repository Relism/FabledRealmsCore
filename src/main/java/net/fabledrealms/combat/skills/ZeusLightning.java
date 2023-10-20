package net.fabledrealms.combat.skills;

import net.fabledrealms.combat.base.Skills;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ZeusLightning extends Skills {

    private Map<Player, Float> cooldown = new HashMap<>();

    public ZeusLightning() {
        super("Zeus Lightning", 10.5f, 0.5,2.0, new ArrayList<>(), new ArrayList<>(), true, false, false, false);
    }


    public void execute(Player player) {
        if(isThrowLightning()) {

            Location location = player.getLocation();
            Vector vec = new Vector().add(location.getDirection());
            for (int i = 0; i < 4; i++) {
                location.getWorld().strikeLightningEffect(location.clone().add(vec.clone().multiply(i).setY(location.getY()).toLocation(location.getWorld())));
            }
        }
    }


}
