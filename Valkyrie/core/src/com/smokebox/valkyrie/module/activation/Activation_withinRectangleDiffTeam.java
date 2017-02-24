package com.smokebox.valkyrie.module.activation;

import com.smokebox.lib.utils.Vector2;
import com.smokebox.lib.utils.geom.Rectangle;
import com.smokebox.valkyrie.actor.ability.DamageReceiver;
import com.smokebox.valkyrie.actor.ability.SkillUser;

import java.util.ArrayList;

public class Activation_withinRectangleDiffTeam implements ActivationModule {

	private SkillUser user;
	private ArrayList<DamageReceiver> targets;
	
	private Vector2 shift;
	private Rectangle area;
	
	public Activation_withinRectangleDiffTeam(SkillUser user, ArrayList<DamageReceiver> targets, Vector2 shift, float width, float height) {
		this.user = user;
		this.targets = targets;
		this.shift = shift;
		area = new Rectangle(0, 0, width, height);
	}
	
	@Override
	public boolean activate() {
		float dir = user.lookingRight() ? 1 : -1;
		Vector2 v = user.getAttackRootCoordinates();
		area.x = v.x + dir*shift.x - area.width/2f;
		area.y = v.y + shift.y;
		boolean col = false;
		for(DamageReceiver target : targets) if(user.getTeam() != target.getTeam() && target.col_Rect(area)) col = true;
		return col;
	}
}
