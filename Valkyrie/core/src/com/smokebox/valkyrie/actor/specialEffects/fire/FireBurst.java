package com.smokebox.valkyrie.actor.specialEffects.fire;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.wilhelmsen.gamelib.utils.Vector2;
import com.smokebox.valkyrie.actor.specialEffects.EffectsHandler;
import com.smokebox.valkyrie.actor.specialEffects.SmallEffect;

import java.util.ArrayList;

/**
 * Created by Harald Wilhelmsen on 8/22/2014.
 */
class FireBurst implements SmallEffect {


    private ArrayList<FireParticle> particles;

    public FireBurst(Vector2 pos, Vector2 initVel, int amount, float radius, EffectsHandler.Size size, FireHandler handler) {
        createParticles(pos, initVel, amount, radius, size, handler);
    }

    @Override
    public boolean update(float delta) {
        for(int i = 0; i < particles.size();) {
            FireParticle p = particles.get(i);
            // Updates all particles
            if(!p.update(delta))
                // remove if particles is done
                particles.remove(i);
            else
                // continues if particle still alive
                i++;
        }
        return !particles.isEmpty();
    }

    private void createParticles(Vector2 pos, Vector2 initVel, float amount, float radius, EffectsHandler.Size burstSize, FireHandler handler) {
        float largesLeft = amount*FireHandler.largesAmount;
        float medsLeft = amount*FireHandler.mediumsAmount;
        float i = amount;
        for(; i >= 1; i--) {
            Vector2 vel = new Vector2(initVel).scl(Math.random()*2*Math.PI).scl(Math.random()*FireHandler.particleInitVelScale*burstSize.scale);
            EffectsHandler.Size s = EffectsHandler.Size.SMALL;
            if(largesLeft >= 1) {
                s = EffectsHandler.Size.LARGE;
                largesLeft -= 1;
            } else if(medsLeft >= 1) {
                s = EffectsHandler.Size.MEDIUM;
                medsLeft -= 1;
            }
            particles.add(new FireParticle(
                    new Vector2(pos).add(new Vector2(Math.random()*2*Math.PI).scl((1/s.scale)*radius*handler.random.nextGaussian())),
                    vel,
                    FireHandler.particleMaxLife,
                    s, handler));
        }
    }

    @Override
    public void draw(SpriteBatch sb) {

    }

    @Override
    public void shapeDraw(ShapeRenderer sr) {

    }
}
