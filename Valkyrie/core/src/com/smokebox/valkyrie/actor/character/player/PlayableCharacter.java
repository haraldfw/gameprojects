package com.smokebox.valkyrie.actor.character.player;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.smokebox.lib.utils.Vector2;
import com.smokebox.valkyrie.Game;
import com.smokebox.valkyrie.actor.ability.DamageReceiver;
import com.smokebox.valkyrie.actor.character.GeneralCharacter;
import com.smokebox.valkyrie.actor.character.weapon.GeneralWeapon;
import com.smokebox.valkyrie.module.movement.MoveMotivationModule;
import com.smokebox.valkyrie.module.movement.MovementExecutionModule;
import com.smokebox.valkyrie.module.skill.SkillModule;

import java.util.ArrayList;

public abstract class PlayableCharacter extends GeneralCharacter {
	
	Game game;
	
	private ArrayList<SkillModule> skills;
	
	private float health;
	private int bombs;
	private int keys;
	private int coins;

	Animation[] skillAnimations;

	GeneralWeapon weapon;
	
	public PlayableCharacter(Vector2 pos, 
			MoveMotivationModule mm, 
			MovementExecutionModule me,
			Animation[] animations, Game game, Element owner)
	{
        super(pos, mm, me, owner, animations, game);
		
		this.game = game;
		
		game.addDrawable(this);
		game.addMovable(this);
		game.addSkillUser(this);
	}

	@Override
	public void dealDamage(DamageReceiver rec, float dmgMul) {
		// If characters are on same team, do nothing
		//	this should be handled in worldCollision detection
		// 	so this is mainly to handle any odd cases
		if(this.getTeam() == rec.getTeam()) return;
		rec.receiveDamage(dmgMul);
		game.activateOnSuccessfulAttack(rec, dmgMul);
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		weapon.update(delta);
	}

	@Override
	public void draw(SpriteBatch sb) {
		super.draw(sb);
		weapon.draw(pos, sb);
	}

	@Override
	public void receiveDamage(float dmgMul) {
		dmgMul = game.activateOnPlayerHit(this, dmgMul);
		health -= game.getBaseDamage()*dmgMul;
	}
	
	@Override
	public Team getTeam() {
		return Team.PLAYER;
	}

	@Override
	public float getHealth() {
		return health;
	}

	@Override
	public boolean sprinting() {return game.getInputEntity().l3();}


	public void increaseBombs(int amount) {
		bombs += amount;
	}

	public void increaseKeys(int amount) {
		keys += amount;
	}

	public void increaseCoins(int amount) {
		coins += amount;
	}

	public int getBombs() {
		return bombs;
	}

	public int getKeys() {
		return keys;
	}

	public int getCoins() {
		return coins;
	}

	public void setWeapon(GeneralWeapon weapon) {
		this.weapon = weapon;
	}

	public Vector2 getAimVector2() {
		return game.playerToMouse();
	}

	public Vector2 getArmRoot() {
		return pos;
	}

	public float getDmgMul() {
		return 1;
	}
}
