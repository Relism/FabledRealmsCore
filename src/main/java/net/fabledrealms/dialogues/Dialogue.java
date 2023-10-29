package net.fabledrealms.dialogues;

import net.fabledrealms.Core;
import net.fabledrealms.events.custom.dialogueEvent;
import net.fabledrealms.util.msg;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class Dialogue implements Listener {
    private final String dialogueID;
    private final Core FabledCore;
    private OptionHandler oh;
    private Player player;
    private JSONObject dialogueData;
    private JSONObject currentDialogueNode;
    private boolean ended;

    public Dialogue(Core FabledCore, String dialogueID, Player player) {
        this.dialogueID = dialogueID;
        this.player = player;
        this.FabledCore = FabledCore;
        loadDialogueConfig();
        this.oh = new OptionHandler(FabledCore, player, this);
        this.ended = false;
    }

    public void start() {
        if (currentDialogueNode != null) {
            oh.handleOptions(getCurrentOptions());
            parsePhaseData(currentDialogueNode);
        } else {
            msg.send(player, "Dialogue data not loaded. Unable to start the conversation.");
        }
        FabledCore.getServer().getPluginManager().registerEvents(this, FabledCore);
    }

    public void end() {
        dialogueEvent.getHandlerList().unregister(this);
        ended = true;
    }

    public boolean isEnded(){
        return ended;
    }

    private void loadDialogueConfig() {
        File dialogueFile = new File(FabledCore.getDataFolder() + "/dialogues", dialogueID + ".json");

        if (dialogueFile.exists()) {
            try {
                String jsonContent = Files.readString(dialogueFile.toPath());
                dialogueData = new JSONObject(jsonContent);
                currentDialogueNode = dialogueData;
                //msg.log("initial data : " + dialogueData.toString(2));// Start with the root node
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @EventHandler
    //listens for response for the next phase
    void onDialogueEvent(dialogueEvent event) {
        if (event.getPlayer() == player) {
            String response = event.getResponseOption();
            //msg.send(player, "Your response is: " + response);
            handleNextPhase(response);
        }
    }

    private String[] getCurrentOptions() {
        JSONArray optionsArray = currentDialogueNode.optJSONArray("options");
        if (optionsArray != null) {
            String[] options = new String[optionsArray.length()];
            for (int i = 0; i < optionsArray.length(); i++) {
                options[i] = optionsArray.getJSONObject(i).optString("text");
            }
            return options;
        }
        return null;
    }

    private void handleNextPhase(String response) {
        JSONArray optionsArray = currentDialogueNode.optJSONArray("options");
        if (optionsArray != null) {
            for (int i = 0; i < optionsArray.length(); i++) {
                if (optionsArray.getJSONObject(i).optString("text").equalsIgnoreCase(response)) {
                    JSONArray responsesArray = optionsArray.getJSONObject(i).optJSONArray("responses");
                    if (responsesArray != null && !responsesArray.isEmpty()) {
                        // Move to the next dialogue node
                        currentDialogueNode = responsesArray.getJSONObject(0); // Assuming we take the first response
                        oh.handleOptions(getCurrentOptions());
                        parsePhaseData(currentDialogueNode);
                    } else {
                        msg.send(player, "End of conversation.");
                        currentDialogueNode = null; // No more options, end of conversation
                        ended = true;
                    }
                    return;
                }
            }
        } else {
            //end of convo
            msg.send(player, "No phase options available. The conversation has ended.");
            ended = true;
        }
    }

    private void parsePhaseData(JSONObject node) {
        String phaseMessage = node.optString("message");
        if (node.optString("trigger") != null) msg.log(node.optString("trigger"));
        msg.send(player, phaseMessage);
        JSONArray optionsArray = node.optJSONArray("options");
        if (optionsArray != null) {
            for (int i = 0; i < optionsArray.length(); i++) {
                String optionText = optionsArray.getJSONObject(i).optString("text");
                msg.send(player, "Option " + (i + 1) + ": " + optionText);
            }
        }
    }
}
