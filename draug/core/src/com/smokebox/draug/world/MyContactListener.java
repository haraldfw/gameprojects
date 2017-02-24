package com.smokebox.draug.world;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.smokebox.draug.Bolt;
import com.smokebox.draug.miniGame.Ship;
import com.smokebox.draug.miniGame.projectiles.Bullet;
import com.smokebox.draug.miniGame.projectiles.GeneralProjectile;

import java.util.ArrayList;

/**
 * Created by Harald Wilhelmsen on 10/13/2014.
 */
public class MyContactListener implements ContactListener {

	private World world;
	private ArrayList<StickyContact> stickyContacts;

	public MyContactListener(World world) {
		this.world = world;
		stickyContacts = new ArrayList<StickyContact>();
	}

	/**
	 * Call right after world.step(...)
	 */
	public void update() {
		for(StickyContact s : stickyContacts){
			WeldJointDef weldDef = new WeldJointDef();
			Vector2 anchor = s.arrow.getWorldPoint(new Vector2(0.5f, 0f));

			weldDef.bodyA = s.arrow;
			weldDef.bodyB = s.target;
			weldDef.localAnchorA.set(weldDef.bodyA.getLocalPoint(anchor));
			weldDef.localAnchorB.set(weldDef.bodyB.getLocalPoint(anchor));
			weldDef.referenceAngle = weldDef.bodyB.getAngle() - weldDef.bodyA.getAngle();
			world.createJoint(weldDef);
		}
		stickyContacts.clear();
	}

	@Override
	public void beginContact(Contact contact) {
	}

	@Override
	public void endContact(Contact contact) {
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		Body arrow = contact.getFixtureA().getBody();
		Body target = contact.getFixtureB().getBody();
		Bolt userData;

		if (arrow.getUserData() instanceof Bolt) {
			userData = (Bolt) arrow.getUserData();
			if (!userData.hasStuck) {
				stickyContacts.add(new StickyContact(arrow, target));
				userData.hasStuck = true;
				contact.setEnabled(false);
			}
		} else if (target.getUserData() instanceof Bolt) {
			userData = (Bolt) target.getUserData();
			if (!userData.hasStuck) {
				stickyContacts.add(new StickyContact(target, arrow));
				userData.hasStuck = true;
				contact.setEnabled(false);
			}
		}
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		Object user1 = contact.getFixtureA().getBody().getUserData();
		Object user2 = contact.getFixtureB().getBody().getUserData();
		Boolean u1p = user1 instanceof GeneralProjectile;
		Boolean u2s = user2 instanceof Ship;
		boolean u1s = user1 instanceof Ship;
		Boolean u2p = user2 instanceof GeneralProjectile;

		if(u1p && u2s) {
			((GeneralProjectile) user1).hit((Ship)user2);
		} else if(u1s && u2p) {
			((GeneralProjectile) user2).hit((Ship)user1);
		} else if(u2p && contact.getFixtureA().getFilterData().groupIndex == (short)ContactGroups.WORLD.ordinal()) {
			((GeneralProjectile) user2).setActive(false);
		} else if(u1p && contact.getFixtureB().getFilterData().groupIndex == (short)ContactGroups.WORLD.ordinal()) {
			((GeneralProjectile) user1).setActive(false);
		}
	}
	private void checkTeamAndHit(Bullet bullet, Ship ship) {

	}

	private class StickyContact{
		public Body arrow;
		public Body target;

		public StickyContact(Body a, Body t) {
			arrow = a;
			target = t;
		}
	}
}
