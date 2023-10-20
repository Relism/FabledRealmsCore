package net.fabledrealms.combat;

import net.fabledrealms.combat.base.Skills;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class CombatClass {

    private final String name;
    private Set<Skills> skills;

    public CombatClass(String name) {
        this.name = name;
        this.skills = new HashSet<>();
    }


    public CombatClass addSkills(Skills... skill) {
        Collections.addAll(skills, skill);
        return this;
    }

    public void addSkill(Skills skill) {
        if(!skills.contains(skill)) skills.add(skill);
    }

    public void removeSkill(Skills skill) {
        if(skills.contains(skill)) skills.remove(skill);
    }

    public String getName() {
        return name;
    }

    public Set<Skills> getSkills() {
        return skills;
    }


}
