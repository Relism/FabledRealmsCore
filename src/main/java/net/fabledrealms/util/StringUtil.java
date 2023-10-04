package net.fabledrealms.util;

import net.fabledrealms.Core;
import org.bukkit.ChatColor;

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

}
