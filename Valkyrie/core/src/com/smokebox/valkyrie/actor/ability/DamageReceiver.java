package com.smokebox.valkyrie.actor.ability;

import com.smokebox.valkyrie.actor.character.GeneralCharacter.Team;
import com.wilhelmsen.gamelib.utils.geom.Circle;
import com.wilhelmsen.gamelib.utils.geom.Line;
import com.wilhelmsen.gamelib.utils.geom.Rectangle;

public interface DamageReceiver {

    public void receiveDamage(float amount);

    public Team getTeam();

    public boolean col_line(Line l);

    public boolean col_circle(Circle c);

    public boolean col_Rect(Rectangle r);

    public float getHealth();
}
