package net.fabledrealms.economy;

import net.fabledrealms.Core;
import org.bukkit.entity.Player;

import java.util.UUID;

public class EconomyManager {

    private final Core main;

    public EconomyManager(Core main){
        this.main = main;
    }

    public int getPlayerBalance(UUID uuid){
        return this.main.getCharacterManager().getCharacterFromMemory(uuid).getBalance();
    }

    public void setPlayerBalance(UUID uuid, int balance) {
        this.main.getCharacterManager().getCharacterFromMemory(uuid).setBalance(balance);
    }

    public void addPlayerBalance(UUID uuid, int balance) {
        this.setPlayerBalance(uuid, getPlayerBalance(uuid) + balance);
    }

    public void removePlayerBalance(UUID uuid, int balance) {
        if (getPlayerBalance(uuid) <= balance) {
            setPlayerBalance(uuid, 0);
            return;
        }

        setPlayerBalance(uuid, getPlayerBalance(uuid) - balance);
    }
}
