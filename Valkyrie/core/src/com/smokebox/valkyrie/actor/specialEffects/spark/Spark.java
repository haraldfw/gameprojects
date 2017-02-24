package com.smokebox.valkyrie.actor.specialEffects.spark;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.smokebox.lib.utils.Vector2;

import java.util.ArrayList;

class Spark {
	
	//private ArrayList<Vector2> positions;
	private ArrayList<SparkSegment> segments;
	private Vector2 vel;
	
	private float timeAlive;
	
	private Vector2 origin;
	private Color color;
	
	public Spark(Vector2 pos, Vector2 vel, Color color) {
//		this.positions = new ArrayList<>();
//		this.positions.add(new Vector2(pos));
		segments = new ArrayList<SparkSegment>();
		origin = new Vector2(pos);
		this.vel = new Vector2(vel);
		this.timeAlive = SparkHandler.particleTimeAlive*(float)(Math.random()/2 + 0.5f);
		this.color = color;
	}
	
	public boolean update(float delta) {
		if(timeAlive < 0) {
			if(segments.isEmpty()) return false;
			else {
				segments.remove(0);
				return true;
			}
		}
		timeAlive -= delta;
		
		Vector2 from = segments.isEmpty() ? origin : new Vector2(segments.get(segments.size() - 1).to);
		Vector2 to = new Vector2(from).addScaledVector(vel, delta);
		segments.add(new SparkSegment(from, to, 
				(float) Math.toDegrees(new Vector2(to).sub(from).getAngleAsRadians() + Math.PI), 
				color));
		
		while(segments.size() > SparkHandler.particleTrail + 1) segments.remove(0);
		
		vel.add(0, SparkHandler.particleGravitation*delta);
		
		return true;
	}
	
	public void draw(SpriteBatch sb) {
		for(SparkSegment s : segments) s.draw(sb);
	}
	
	public void shapeDraw(ShapeRenderer sr) {
		sr.setColor(1 - color.r, 1 - color.g, 1 - color.b , 1);
		for(SparkSegment s : segments) s.shapeDraw(sr);
	}
}
