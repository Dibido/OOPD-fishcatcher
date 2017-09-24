package nl.han.ica.FishCatcher;

import nl.han.ica.OOPDProcessingEngineHAN.Collision.CollidedTile;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithGameObjects;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithTiles;
import nl.han.ica.OOPDProcessingEngineHAN.Exceptions.TileNotFoundException;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.AnimatedSpriteObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;

import java.util.List;

import nl.han.ica.FishCatcher.FishCatcher;
import nl.han.ica.FishCatcher.tiles.BrozeMuur;
import nl.han.ica.FishCatcher.tiles.Exit;
import nl.han.ica.FishCatcher.tiles.ExitToegankelijk;
import nl.han.ica.FishCatcher.tiles.SolideMuur;
import nl.han.ica.FishCatcher.tiles.StilstaandeVis;
import nl.han.ica.FishCatcher.tiles.Zeewier;
import processing.core.PConstants;
import processing.core.PVector;

/**
 * Playerobject die door de speler bestuurd wordt. Created by Dibran & Marnix
 * 
 * @date 02-04-2017
 * @author Dibran & Marnix
 *
 */
public class Player extends AnimatedSpriteObject implements ICollidableWithTiles, ICollidableWithGameObjects {

	private final int size = 25;
	private FishCatcher game;

	/**
	 * Constructor
	 * 
	 * @param game
	 *            - Om de methoden van FishCatcher te kunnen aanroepen
	 */
	public Player(FishCatcher game) {
		super(new Sprite("src/main/java/nl/han/ica/FishCatcher/media/playerCharacter.png"), 3);
		this.game = game;
		setCurrentFrameIndex(1);
		setFriction(0.09f);
	}

	/**
	 * Zorgen dat de speler niet uit het scherm kan.
	 */
	@Override
	public void update() {
		if (getX() <= 0) {
			setxSpeed(0);
			setX(0);
		}
		if (getY() <= 0) {
			setySpeed(0);
			setY(0);
		}
		if (getX() >= game.getWidth() - size) {
			setxSpeed(0);
			setX(game.getWidth() - size);
		}
		if (getY() >= game.getHeight() - size) {
			setySpeed(0);
			setY(game.getHeight() - size);
		}
	}

	/**
	 * Bewegen van de speler
	 */
	@Override
	public void keyPressed(int keyCode, char key) {
		final int speed = 5;
		if (keyCode == PConstants.LEFT) {
			setDirectionSpeed(270, speed);
			setCurrentFrameIndex(0);
		}
		if (keyCode == PConstants.UP) {
			setDirectionSpeed(0, speed);
			setCurrentFrameIndex(0);
		}
		if (keyCode == PConstants.RIGHT) {
			setDirectionSpeed(90, speed);
			setCurrentFrameIndex(1);
		}
		if (keyCode == PConstants.DOWN) {
			setDirectionSpeed(180, speed);
			setCurrentFrameIndex(2);
		}
	}

	/**
	 * Afhandelen als een speler een tile raakt.
	 */
	@Override
	public void tileCollisionOccurred(List<CollidedTile> collidedTiles) {
		PVector vector;
		for (CollidedTile ct : collidedTiles) {
			if (ct.theTile instanceof SolideMuur || ct.theTile instanceof BrozeMuur || ct.theTile instanceof Exit) {
				if (ct.collisionSide == CollidedTile.TOP) {
					try {
						vector = game.getTileMap().getTilePixelLocation(ct.theTile);
						setY(vector.y - getHeight());
					} catch (TileNotFoundException e) {
						e.printStackTrace();
					}
				}
				if (ct.collisionSide == CollidedTile.RIGHT) {
					try {
						vector = game.getTileMap().getTilePixelLocation(ct.theTile);
						setX(vector.x + getWidth());
					} catch (TileNotFoundException e) {
						e.printStackTrace();
					}
				}
				if (ct.collisionSide == CollidedTile.BOTTOM) {
					try {
						vector = game.getTileMap().getTilePixelLocation(ct.theTile);
						setY(vector.y + getHeight());
					} catch (TileNotFoundException e) {
						e.printStackTrace();
					}
				}
				if (ct.collisionSide == CollidedTile.LEFT) {
					try {
						vector = game.getTileMap().getTilePixelLocation(ct.theTile);
						setX(vector.x - getWidth());
					} catch (TileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
			if (ct.theTile instanceof Zeewier) {
				try {
					vector = game.getTileMap().getTilePixelLocation(ct.theTile);
					game.getTileMap().setTile((int) vector.x / 50, (int) vector.y / 50, -1);
				} catch (TileNotFoundException e) {
					e.printStackTrace();
				}
			}
			if (ct.theTile instanceof StilstaandeVis) {
				try {
					vector = game.getTileMap().getTilePixelLocation(ct.theTile);
					game.getTileMap().setTile((int) vector.x / 50, (int) vector.y / 50, -1);
					game.vangVis();
				} catch (TileNotFoundException e) {
					e.printStackTrace();
				}
			}
			if (ct.theTile instanceof ExitToegankelijk){
				game.nextLevel();
			}
		}
	}

	/**
	 * Afhandelen als een speler een ander object raakt.
	 */
	@Override
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
		for (GameObject go : collidedGameObjects) {
			// Als het een vangVis is, vang de vis en delete de gevangen vis.
			if (go instanceof VangVis) {
				game.vangVis();
				game.deleteGameObject(go);
			}
		}
	}
}