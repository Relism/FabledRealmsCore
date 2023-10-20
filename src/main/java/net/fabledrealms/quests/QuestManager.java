package net.fabledrealms.quests;

import net.fabledrealms.Core;
import net.fabledrealms.quests.base.QuestObjective;
import net.fabledrealms.quests.base.QuestPoint;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class QuestManager {

    private final Core core;
    private File file;
    private YamlConfiguration config;
    private ConfigurationSection section;
    private Set<Quest> quests = new HashSet<>();


    public QuestManager(Core core) {
        List<Quest> quests = new LinkedList<>();

        this.core = core;
        file = new File(core.getDataFolder() + "/quests");
        if(!file.exists())  file.mkdirs();

        for(File files : file.listFiles()) {
            config = YamlConfiguration.loadConfiguration(files);
            Set<QuestPoint> points = new HashSet<>();

            String name = config.getString("name");
            int position = config.getInt("position");

            // QuestPoint section
            section = config.getConfigurationSection("points");
            if(section != null) {
                for(String point : section.getKeys(false)) {
                    UUID id = UUID.fromString(config.getString("points."+point+".id"));
                    Location start = config.getLocation("points."+point+".start");
                    Location target = config.getLocation("points."+point+".target");
                    String nextQuest = config.getString("points."+point+".nextQuest");
                    points.add(new QuestPoint(id, start, target, false, nextQuest));
                }
            }

            // QuestObjective section
            String objective = config.getString("objective.name");
            List<String> commands = new ArrayList<>(config.getStringList("objective.commands"));



            quests.add(new Quest(name, points, new QuestObjective(objective, commands), position));
        }

    }

    public QuestPoint getNearestQuestPoint(Quest quest, Location location) {
        for(QuestPoint points : quest.getQuestPoints()) {
            // 10 blocks
            if(points.getStart().distance(location) <= 10.0d) {
                return points;
            }
        }
        return null;
    }

    public Quest getQuest(String name) {
        for(Quest quest : quests) {
            if(quest.getName().equalsIgnoreCase(name)) {
                return quest;
            }
        }
        return null;
    }

    public Set<Quest> getQuests() { return quests; }

}
