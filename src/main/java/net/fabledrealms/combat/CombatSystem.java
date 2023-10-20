package net.fabledrealms.combat;

import net.fabledrealms.Core;
import net.fabledrealms.combat.skills.ZeusLightning;

import java.util.HashSet;
import java.util.Set;

public class CombatSystem {

    private Set<CombatClass> classes = new HashSet<>();
    private final Core core;


    public CombatSystem(Core core) {
        this.core = core;

        classes.add(new CombatClass("God").addSkills(new ZeusLightning()));
        core.getLogger().warning("Loading combat classes...");
        for(CombatClass clazz : classes) {
            core.getLogger().warning(clazz.getName() + " loaded!");
        }
        core.getLogger().warning("Finished loading combat classes!");
    }

}
