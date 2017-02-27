package com.smokebox.valkyrie.actor.ability;

import com.smokebox.valkyrie.actor.character.GeneralCharacter.Team;
import com.wilhelmsen.gamelib.utils.Vector2;

public interface SkillUser {

    public void updateSkills(float delta);

    public Team getTeam();

    public Vector2 getWeaponMuzzle();

    public boolean weaponIsAvailable();

    public boolean areSkillsLocked();

    public void setSkillLockTime(float f);

    public boolean lookingRight();

    public float getInverseMass();

    public Vector2 getAttackRootCoordinates();
}
