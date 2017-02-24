package com.smokebox.valkyrie.actor.ability;

import com.smokebox.lib.utils.geom.Circle;
import com.smokebox.lib.utils.geom.Line;
import com.smokebox.lib.utils.geom.Rectangle;
import com.smokebox.valkyrie.actor.character.GeneralCharacter.Team;

public interface DamageReceiver {
	
	public void receiveDamage(float amount);
	
	public Team getTeam();
	
	public boolean col_line(Line l);
	public boolean col_circle(Circle c);
	public boolean col_Rect(Rectangle r);
	
	public float getHealth();
}
