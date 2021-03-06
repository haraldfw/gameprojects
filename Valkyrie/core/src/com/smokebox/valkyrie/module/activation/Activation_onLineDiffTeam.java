package com.smokebox.valkyrie.module.activation;

import com.smokebox.valkyrie.actor.ability.DamageReceiver;
import com.smokebox.valkyrie.actor.ability.SkillUser;
import com.wilhelmsen.gamelib.utils.Vector2;
import com.wilhelmsen.gamelib.utils.geom.Line;

public class Activation_onLineDiffTeam implements ActivationModule {

    float length;
    private SkillUser user;
    private DamageReceiver target;
    private Vector2 from;
    private Line l;

    public Activation_onLineDiffTeam(SkillUser user, DamageReceiver target, Vector2 from, float length) {
        this.user = user;
        this.target = target;
        this.from = from;
        l = new Line();
        this.length = length;
    }

    @Override
    public boolean activate() {
        float dir = user.lookingRight() ? 1 : -1;
        Vector2 a = user.getAttackRootCoordinates();
        l.x = a.x + dir * from.x - length / 2f;
        l.x2 = l.x + length;
        l.y = a.y + from.y;
        l.y2 = l.y;
        return target.col_line(l);
    }
}
