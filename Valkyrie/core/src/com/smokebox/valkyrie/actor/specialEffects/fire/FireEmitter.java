package com.smokebox.valkyrie.actor.specialEffects.fire;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.smokebox.lib.utils.MathUtils;
import com.smokebox.lib.utils.Vector2;
import com.smokebox.valkyrie.actor.specialEffects.EffectsHandler.Size;
import com.smokebox.valkyrie.actor.specialEffects.SmallEffect;

import java.util.ArrayList;

public class FireEmitter implements SmallEffect {
	
	private float radius;
	private float intensity;
	
	private ArrayList<FireParticle> particles;
	
	private float maxTime;
	private float timeAlive;
	
	private Vector2 pos;
	private Vector2 prevPos;
	
	Size emitterSize;
	
	private FireHandler handler;
	
	private float leftOverPoints = 0;
	private float largesLeft = 0;
	private float medsLeft = 0;
	
	public FireEmitter(Vector2 pos, Size size, float radius, float intensity, 
			float duration, FireHandler handler) {
		this.radius = radius;
		this.intensity = intensity;
		maxTime = duration;
		timeAlive = 0;
		this.pos = pos;
		prevPos = new Vector2(pos);
		particles = new ArrayList<FireParticle>();
		this.handler = handler;
		emitterSize = size;
	}
	
	public void deactivate() {
		maxTime = 0;
	}
	
	private void createParticles(float amount, float delta) {
		largesLeft += amount*FireHandler.largesAmount;
		medsLeft += amount*FireHandler.mediumsAmount;
		float i = amount + leftOverPoints; 
		for(; i >= 1; i--) {
			float a = amount + leftOverPoints;
			float t = (a - i) / a;
			float timeElapsed = (1 - t)*delta;
			Vector2 vel = new Vector2(Math.random()*2*Math.PI).scl(Math.random()*FireHandler.particleInitVelScale*emitterSize.scale);
			float px = MathUtils.lerp(prevPos.x, pos.x, t) + vel.x*delta;
			float py = MathUtils.lerp(prevPos.y, pos.y, t) + vel.y*delta;
			Size s = Size.SMALL;
			if(largesLeft >= 1) {
				s = Size.LARGE;
				largesLeft -= 1;
			} else if(medsLeft >= 1) {
				s = Size.MEDIUM;
				medsLeft -= 1;
			}
			particles.add(new FireParticle(
					new Vector2(px, py).add(new Vector2(Math.random()*2*Math.PI).scl((1/s.scale)*radius*handler.random.nextGaussian())),
					vel,
					FireHandler.particleMaxLife - timeElapsed,
					s, handler));
		}
		leftOverPoints = leftOverPoints%1 + amount%1;
	}
	
	/**
	 * Updates the emitter and spawns particles
	 * @param delta	Time since last frame
	 * @return	True if emitter is still active. False if not active.
	 */
	public boolean update(float delta) {
		float amount = intensity*delta;
		if(maxTime == 0 || timeAlive < maxTime) createParticles(amount, delta);
		
		timeAlive += delta;
		
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
		
		prevPos.set(pos);
		return maxTime == 0 || timeAlive < maxTime;
	}
	
	@Override
	public void draw(SpriteBatch sb) {
		for(FireParticle p : particles) p.draw(sb);
	}

	@Override
	public void shapeDraw(ShapeRenderer sr) {
		sr.setColor(0, 1, 0, 1);
		for(FireParticle p : particles) p.shapeDraw(sr);
	}

	public void printStatus() {
		System.out.println("\tParticles active: " + particles.size());
	}
}
