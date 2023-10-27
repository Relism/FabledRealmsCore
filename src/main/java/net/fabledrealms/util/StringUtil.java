package net.fabledrealms.util;

import net.fabledrealms.Core;
import net.fabledrealms.character.Character;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {

    private final Core main;

    public StringUtil(Core main){
        this.main = main;
    }

    //Translates color codes for strings. Supports Hex codes
    public String colorString(String input){
            Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
            Matcher matcher = pattern.matcher(input);

            while (matcher.find()) {
                String color = input.substring(matcher.start(), matcher.end());
                input = input.replace(color, net.md_5.bungee.api.ChatColor.of(color) + "");
                matcher = pattern.matcher(input);
            }
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    /* MADE FOR LORE PURPOSE */
    public List<String> colorList(List<String> strings) {
        List<String> r = new ArrayList<>();
        strings.forEach(string -> r.add(this.colorString(string)));
        return r;
    }

    public String characterPlaceholder(Character character, String placeholder, String input){
        String message = input;
        if (placeholder.equalsIgnoreCase("id")){message = message.replaceAll("%characterID%", String.valueOf(character.getCharacterID()));}
        if (placeholder.equalsIgnoreCase("balance")){message = message.replaceAll("%characterBalance%", String.valueOf(character.getBalance()));}
        if (placeholder.equalsIgnoreCase("level")){message = message.replaceAll("%characterLevel%", String.valueOf(character.getLevelMain()));}
        if (placeholder.equalsIgnoreCase("exp")){message = message.replaceAll("%characterExp%", String.valueOf(character.getExpMain()));}
        if (placeholder.equalsIgnoreCase("class")){message = message.replaceAll("%characterClass%", character.getClassName());}
        return message;
    }

}
