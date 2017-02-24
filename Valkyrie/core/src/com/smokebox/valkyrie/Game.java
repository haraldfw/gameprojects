package com.smokebox.valkyrie;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.XmlReader;
import com.badlogic.gdx.utils.XmlReader.Element;
import com.smokebox.lib.utils.Vector2;
import com.smokebox.lib.utils.geom.Circle;
import com.smokebox.lib.utils.geom.Rectangle;
import com.smokebox.valkyrie.actor.ability.*;
import com.smokebox.valkyrie.actor.character.player.PlayableCharacter;
import com.smokebox.valkyrie.actor.character.player.Thor;
import com.smokebox.valkyrie.actor.character.weapon.GeneralWeapon;
import com.smokebox.valkyrie.actor.character.weapon.munition.Bullet;
import com.smokebox.valkyrie.actor.character.weapon.munition.GeneralMunition;
import com.smokebox.valkyrie.actor.drop.DropHandler;
import com.smokebox.valkyrie.actor.specialEffects.EffectsHandler;
import com.smokebox.valkyrie.actor.specialEffects.fire.FireHandler;
import com.smokebox.valkyrie.actor.specialEffects.lightning.LightningHandler;
import com.smokebox.valkyrie.actor.specialEffects.spark.SparkHandler;
import com.smokebox.valkyrie.event.Continuous;
import com.smokebox.valkyrie.event.OnPlayerHit;
import com.smokebox.valkyrie.event.OnRoomEnter;
import com.smokebox.valkyrie.event.OnSuccessfulAttack;
import com.smokebox.valkyrie.module.input.InputModule;
import com.smokebox.valkyrie.module.input.KeyboardAndMouse;
import com.smokebox.valkyrie.module.input.XboxController;
import com.smokebox.valkyrie.module.movement.ME_ForceIntegration;
import com.smokebox.valkyrie.module.movement.MM_Input;
import com.smokebox.valkyrie.module.movement.MoveMotivationModule;
import com.smokebox.valkyrie.module.movement.MovementExecutionModule;
import com.smokebox.valkyrie.trigger.*;
import com.smokebox.valkyrie.world.Floor;
import com.smokebox.valkyrie.world.Room;

import java.io.IOException;
import java.util.ArrayList;

public class Game extends ApplicationAdapter {
	
	OrthographicCamera cam;
	Vector2 camPos = new Vector2();
	Vector2 mousePos = new Vector2();
	ShapeRenderer sr;
	SpriteBatch sb;
    TiledMapRenderer tmr;
	AssetManager astmng;

	private boolean constructorFin = false;
	
	ArrayList<Movable> movables;
    public void addMovable(Movable m) {movables.add(m);}
	ArrayList<Drawable> drawables;
	public void addDrawable(Drawable draw) {drawables.add(draw);}
	ArrayList<SkillUser> skillUsers;
	public void addSkillUser(SkillUser user) {skillUsers.add(user);}
	ArrayList<GeneralMunition> munitions;
	public void addMunition(GeneralMunition munition) {munitions.add(munition);}
	
	ArrayList<Trigger> triggers;
	
	ArrayList<Continuous> continuousEffects;
	ArrayList<OnPlayerHit> onPlayerHitEffects;
	ArrayList<OnSuccessfulAttack> onSuccessfulAttackEffects;
	ArrayList<OnRoomEnter> onRoomEnterEffects;
	
	PlayableCharacter player;
	public Floor currentFloor;
	EffectsHandler effects;
	DropHandler drops;
	
	InputModule inputEnt;

	ArrayList<Interactable> interactables = new ArrayList<Interactable>();
	
	public static int pixelsPerTileSide = 32;
	public static float pixelSize = 3f/(float)pixelsPerTileSide;
	public static int width = 64;
	public static int height = 36;
	public static int tileSize;
	
	boolean spaceActivated;
	
	@Override
	public void create () {
		XmlReader reader = new XmlReader();
		Element root = null;
		try {
			FileHandle f = Gdx.files.internal("data/stats.xml");
			System.out.println(f);
			root = reader.parse(f);
		} catch(IOException e) {
			e.printStackTrace();
		}
		Element gameSettings = getOwnerByName("gameSettings", root);
		width = (int) getStatByName("tileWidth", gameSettings);
		height = (int) getStatByName("tileHeight", gameSettings);
		pixelsPerTileSide = (int) getStatByName("pixelsPerTile", gameSettings);
		float scl = getStatByName("resolutionScale", gameSettings);
		pixelSize = getStatByName("spriteScale", gameSettings)/(float)pixelsPerTileSide;
		tileSize = (int)scl;

		Gdx.graphics.setDisplayMode(width*(int)scl, height*(int)scl, false);

		cam = new OrthographicCamera();
		cam.setToOrtho(false, width, height);
		sr = new ShapeRenderer();
		sb = new SpriteBatch();
		
		sr.setProjectionMatrix(cam.combined);
		sb.setProjectionMatrix(cam.combined);

        movables = new ArrayList<Movable>();
		drawables = new ArrayList<Drawable>();
        skillUsers = new ArrayList<SkillUser>();
		
		triggers = new ArrayList<Trigger>();

		munitions = new ArrayList<>();

        continuousEffects = new ArrayList<Continuous>();
        onPlayerHitEffects = new ArrayList<OnPlayerHit>();
        onSuccessfulAttackEffects = new ArrayList<OnSuccessfulAttack>();
		onRoomEnterEffects = new ArrayList<OnRoomEnter>();

		astmng = new AssetManager(new InternalFileHandleResolver());
		Thor.loadAssets(astmng);
		FireHandler.loadAssets(astmng);
		LightningHandler.loadAssets(astmng);
		SparkHandler.loadAssets(astmng);
		drops.loadAssets(astmng);
		Bullet.loadAssets(astmng);
		while(!astmng.update()) {
			System.out.println(astmng.getProgress());
		}
		Bullet.onLoad(this);

		if(Controllers.getControllers().size < 1) inputEnt = new KeyboardAndMouse();
		else inputEnt = new XboxController(Controllers.getControllers().get(0));
		MoveMotivationModule mm_input = new MM_Input(inputEnt);

		MovementExecutionModule genMovement = new ME_ForceIntegration(root);
		player = new Thor(new Vector2(10, 10), mm_input, genMovement, this, root);
		drops = new DropHandler(genMovement, root, this);
		EffectsHandler.initializeConstants(root);

		currentFloor = new Floor(Floor.FloorType.LOST_CITY, 10, this);
		
		effects = new EffectsHandler(this);
		effects.newSparkBurst(player.getPos(), player.getVel(), 0, 100, true);
		//effects.createFire(player.getPos(), Size.SMALL, 0.1f, 100, 0);

		//astmng.dispose();
		constructorFin = true;
	}
	
	public void renderLine(float x, float y, float x2, float y2) {
		System.out.println("RenderLine called");
		sr.begin(ShapeType.Line);
		sr.line(x, y, x2, y2);
		sr.end();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		float delta = Gdx.graphics.getDeltaTime();
		if (delta > 0.1f) delta = 0.1f;

		sr.setProjectionMatrix(cam.combined);
		sb.setProjectionMatrix(cam.combined);

		for (int i = 0; i < triggers.size(); ) {
			System.out.println("Triggers: " + triggers.size());
			if (!triggers.get(i).isActive(delta)) triggers.remove(i);
			else i++;
		}
		for (SkillUser s : skillUsers) s.updateSkills(delta);
		for (Movable m : movables) m.update(delta);
		drops.update(player, delta);
		//activateContinuous(delta);
		effects.update(delta);

		for(int i = 0; i < munitions.size();) {
			GeneralMunition m = munitions.get(i);
			if(m.update(delta)) i++;
			else munitions.remove(i);
		}


		Vector2 p = player.getPos();

		cam.setToOrtho(false, width, height);
		camPos.lerp(p, 0.25f);

		updateMousePos();
		cam.position.set(camPos.x, camPos.y, 0);
		cam.update();

		draw();
		debugDraw();

		if (Gdx.input.isKeyPressed(Keys.P) && !spaceActivated) {
			//lightning.createBranch(new Vector2(0, 5), new Line(-5, -2, 5, -2), 0.5f, 10);
			effects.createBolt(new Vector2(5, 0), new Vector2(-10, 0), 1);
			effects.newSparkBurst(new Vector2((float) Math.random() * 5, (float) Math.random() * 5), new Vector2(), 0, 10, true);
			spaceActivated = true;
		} else if (!Gdx.input.isKeyPressed(Keys.P)) {
			spaceActivated = false;
		}
		if (Gdx.input.isKeyPressed(Keys.U))
			effects.newSparkBurst(new Vector2(player.getPos().x + (float) Math.random() * 5, player.getPos().y + (float) Math.random() * 5), new Vector2(), 0, 10, true);
		if (Gdx.input.isKeyPressed(Keys.K)) {
			drops.addKey(new Vector2(p).add((float) Math.random() * 5, (float) Math.random() * 5), Vector2.getRandom().scl(50));
			drops.addBomb(new Vector2(p).add((float) Math.random() * 5, (float) Math.random() * 5), Vector2.getRandom().scl(50));
		}
	}
	
	private void draw() {
        tmr.setView(cam);
        currentFloor.draw(tmr);
		sb.begin();
		player.draw(sb);
		drops.draw(sb);
		for(GeneralMunition m : munitions) m.draw(sb);
		effects.draw(sb);
		sb.end();
	}
	
	private void debugDraw() {
        sr.setProjectionMatrix(cam.combined);
		sr.setColor(1, 1, 1, 1);
		sr.begin(ShapeType.Filled);
		//for(Drawable d : drawables) d.debugDraw(sr);
		sr.circle(mousePos.x, mousePos.y, 0.5f, 10);
		//drops.debugDraw(sr);
		sr.end();
		
		sr.begin(ShapeType.Line);
		currentFloor.drawCollision(sr);
		//for(Trigger t : triggers) t.debugDraw(sr);
		//effects.shapeDraw(sr);
		sr.end();
	}
	
	public void activateOnSuccessfulAttack(DamageReceiver rec, float dmgMul) {
		for(OnSuccessfulAttack o : onSuccessfulAttackEffects) o.onSuccessfulAttack(rec, dmgMul);
	}
	
	public float activateOnPlayerHit(PlayableCharacter player, float dmgMul) {
		for(OnPlayerHit o : onPlayerHitEffects) dmgMul = o.onPlayerHit(player, dmgMul);
		return dmgMul;
	}
	
	public void activateContinous(float delta) {
		for(Continuous c : continuousEffects) c.update(delta);
	}
	
	public float getBaseDamage() {
		return 10f;
	}
	
	public void addDamageTrigger(Circle c, float duration, float dmgMul, DamageDealer dmgDealer) {
		triggers.add(new DamagingCircle(c, dmgMul, duration, dmgDealer));
	}
	
	public void addDamageTrigger(Rectangle r, float duration, float dmgMul, DamageDealer dmgDealer) {
		triggers.add(new DamagingRect(r, dmgMul, duration, dmgDealer));
	}

	public void addDamageTrigger(Vector2 from, Vector2 to, float duration, float dmgMul, DamageDealer dmgDealer) {
		triggers.add(new DamagingLine(from, to, dmgMul, duration, dmgDealer));
	}

	public void removeDamageTrigger(DamageTrigger toRemove) {
		triggers.remove(toRemove);
	}
	
	public void createLightningBolt(Vector2 source, Vector2 dest, float duration) {
		effects.createBolt(source, dest, duration);
	}

	public void handleWorldCollisions(CollidesWorld col) {
		col.setGrounded(false);
		currentFloor.worldCollision(col);
	}
	
	public InputModule getInputEntity() {
		return inputEnt;
	}
	
	public static Element getOwnerByName(String name, Element root) {
		for(int i = 0; i < root.getChildCount(); i++) {
			Element childToCheck = root.getChild(i);
			String gotten = childToCheck.get("name");
			if(gotten.equals(name)) {
				System.out.println("Owner: " + gotten);
				return childToCheck;
			}
		}
		throw new IllegalArgumentException("String '" + name + "' does not match any owners.");
	}

	public static float getStatByName(String name, Element owner) {
		Array<Element> toCheck = owner.getChildrenByName("stat");
		for(int i = 0; i < toCheck.size; i++) {
			Element childToCheck = toCheck.get(i);
			if (childToCheck.get("statName").toString().equals(name)) {
				Float f = childToCheck.getFloat("value");
				System.out.println("\t" + name + ": " + f);
				return f;
			}
		}
		throw new IllegalArgumentException("String '" + name + "' does not match any stats in owner " + owner.get("name"));
	}

    public void setMap(TiledMap map) {
        tmr = new OrthogonalTiledMapRenderer(map, 1f/8f, sb);
    }

	public void activateOnRoomEnter(Room r) {
		for(OnRoomEnter o : onRoomEnterEffects) o.onRoomEnter(r);
	}

	public void addInteractable(Interactable i) {
		interactables.add(i);
	}

	public void removeInteractable(Interactable i) {
		interactables.remove(i);
	}

	public AssetManager getAssetManager() {
		if(constructorFin) System.out.println("AssetManager class at runtime. This should not happen!");
		return astmng;
	}

	private void updateMousePos() {
		if(inputEnt instanceof KeyboardAndMouse)
		mousePos.set(camPos.x + (float)Gdx.input.getX()/(float)tileSize - width/2, camPos.y + (float)height/2 - (float)Gdx.input.getY()/(float)tileSize);
		else {
			Vector2 aim = inputEnt.aimDirection(null);
			if(aim.isZero()) aim.x = player.lookingRight() ? 1 : -1;
			mousePos.set(aim).nor().scl(5).add(player.getWeaponMuzzle());
		}
	}

	public Vector2 playerToMouse() {
		return new Vector2(mousePos).sub(player.getArmRoot()).nor();
	}
}
