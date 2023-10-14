package net.fabledrealms.compass;

import net.fabledrealms.Core;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CompassBar {

    // Characters for the directions, largest to the smallest
    // Upper case = cardinal, lower case = ordinal
    private static final String[] compassSize = new String[] {
            "N", "n", "E", "e", "S", "s", "W", "w",
            "O", "o", "F", "f", "T", "t", "X", "x", //Add varying sized unicode icons to add a bubble effect
            "P", "p", "G", "g", "U", "u", "Y", "y",
            "Q", "q", "H", "h", "V", "v", "Z", "z"
    };
    // Lines for separating directions, from largest to smallest
    private static final String[] lines = new String[] {
            "|", "|", "|", "|" //Add varying sized unicode icons to add a bubble effect
    };
    // 3 2 2 5 2 2 3
    // Map for the char sizes of the compass
    // 0 = largest, 3 = smallest.
    private static final int[] sizes = new int[] {
            3, 3, 3, 2, 2, 1, 1, 0, 0, 0, 0, 0, 1, 1, 2, 2, 3, 3, 3
    };

    private final Player owner;
    private final TextComponent compass;
    private final List<ICompassTrackable> trackers = new ArrayList<>();
    private final Map<Integer, ICompassTrackable> tempTrackers = new HashMap<>();
    private BossBar compassBar = null;

    public CompassBar(Player player) {
        owner = player;
        compass = new TextComponent();
        //compass.setFont("compass");
    }

    public void addTracker(ICompassTrackable tracker) {
        // Avoid spamming, delete later
        trackers.clear();
        trackers.add(tracker);
    }

    public void displayCompass() {
        compass.setText(getCompassDisplay(owner.getLocation().getYaw() + 180));
        NamespacedKey compassKey = new NamespacedKey(Core.getPlugin(Core.class),"compassbar");
        if (compassBar == null) {
            this.compassBar = Bukkit.createBossBar(compassKey, compass.getText(), BarColor.WHITE, BarStyle.SOLID);
            compassBar.addPlayer(owner);
            compassBar.setVisible(true);
            return;
        }
        compassBar.setTitle(compass.getText());
        compassBar.setVisible(true);
    }

    private String getCompassDisplay(float yaw) {
        int index = (int) (((yaw + 2.5) / 5 + 63) % 72);
        StringBuilder builder = new StringBuilder();

        for(ICompassTrackable tracker : trackers)
            tempTrackers.put(normalizeYaw(tracker.getAngle(owner.getLocation().toVector())), tracker);

        String icon;
        for(int i = 0; i <= 18; ++i) {
            if(tempTrackers.containsKey(index)) {
                icon = tempTrackers.get(index).getTrackerIcon(owner.getLocation().toVector());
            }else if(index % 9 == 0) {
                int id = (index / 9) + 8 * sizes[i];
                icon = compassSize[id];
            }else {
                icon = lines[sizes[i]];
            }
            builder.append("|").append(icon).append("|");
            index = (index + 1) % 72;
        }
        tempTrackers.clear();
        return builder.toString();
    }

    // Convert yaw to match the precision of the compass
    private int normalizeYaw(float yaw) {
        return (int) (((yaw + 2.5) / 5) % 72);
    }

}
