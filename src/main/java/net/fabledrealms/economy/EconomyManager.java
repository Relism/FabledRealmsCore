package net.fabledrealms.economy;

import net.fabledrealms.Core;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class EconomyManager {

    private final Core main;

    public EconomyManager(Core main){
        this.main = main;
    }

    public int getPlayerBalance(UUID uuid, int characterSlot){
        return this.main.getCharacterManager().getCharacter(uuid, characterSlot).getBalance();
    }

    public void setPlayerBalance(UUID uuid, int characterSlot, int balance) {
        this.main.getCharacterManager().getCharacter(uuid,characterSlot).setBalance(balance);
    }

    public void addPlayerBalance(UUID uuid, int characterSlot, int balance) {
        this.setPlayerBalance(uuid, characterSlot, getPlayerBalance(uuid, characterSlot) + balance);
    }

    public void removePlayerBalance(UUID uuid,int characterSlot, int balance) {
        if (getPlayerBalance(uuid, characterSlot) <= balance) {
            setPlayerBalance(uuid,  characterSlot,0);
            return;
        }

        setPlayerBalance(uuid,main.getCharacterManager().getCharacter(Bukkit.getPlayer(uuid)).getCharacterID(),getPlayerBalance(uuid, characterSlot) - balance);
    }
}
