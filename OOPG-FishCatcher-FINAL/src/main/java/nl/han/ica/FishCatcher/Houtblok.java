package nl.han.ica.FishCatcher;

import java.util.List;

import nl.han.ica.FishCatcher.tiles.BrozeMuur;
import nl.han.ica.FishCatcher.tiles.SolideMuur;
import nl.han.ica.FishCatcher.tiles.StilstaandeVis;
import nl.han.ica.FishCatcher.tiles.Zeewier;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.CollidedTile;
import nl.han.ica.OOPDProcessingEngineHAN.Exceptions.TileNotFoundException;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.OOPDProcessingEngineHAN.Tile.EmptyTile;
import processing.core.PVector;

/**
 * Houtblok - Created by Dibran & Marnix
 * 
 * @date 02-04-2017
 * @author Dibran & Marnix
 *
 */
public class Houtblok extends MySpriteObject {
 
	/**
	 * Constructor van het Houtblok object
	 * 
	 * @param game
	 *            - Om de methoden van FishCatcher aan te kunnen roepen
	 * @param sprite
	 *            - Sprite voor het object
	 */
	public Houtblok(FishCatcher game, Sprite sprite) {
		super(sprite, game);
		this.setGravity(0.00f);
		this.setSpeed(0.0f);
	}
 
	/**
	 * Hier worden de acties afgehandeld die gebeuren als het houtblok in contact
	 * komt met een ander GameObject.
	 */
	@Override
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
		for (GameObject g : collidedGameObjects) {
			if (g instanceof Player) {
				// Als de speler rechts gaat en tegen een houtblok komt, en het
				// houtblok ruimte heeft om te bewegen:
				if (this.getAngleFrom(g) >= 225 && this.getAngleFrom(g) < 315) {
					if (game.getTileMap().getTileOnPosition((int) this.getY(),
							((int) this.getX() + game.getTileMap().getTileSize())) instanceof EmptyTile
							&& checkRuimte("Rechts", this.getX(), this.getY() - 1)
							&& game.getTileMap().getTileOnPosition(
									(int) this.getY() + (game.getTileMap().getTileSize() - 1), ((int) this.getX()
											+ game.getTileMap().getTileSize())) instanceof EmptyTile) {
						this.setX(getX() + game.getTileMap().getTileSize());
					}
					// Als de speler rechts gaat en tegen een houtblok komt, en het
					// houtblok heeft geen ruimte om te bewegen:
					else {
						g.setX(this.getX() - g.getWidth());
					}
				// Als de speler van rechtsboven het houtblok probeert naar beneden te gaan wordt hij daar bij geholpen:
				} else if (this.getAngleFrom(g) >= 30 && this.getAngleFrom(g) <= 48) {
					g.setX(this.getX() + this.getWidth());
				}
				// Als de speler links gaat en tegen een houtblok komt, en het
				// houtblok ruimte heeft om te bewegen:
				else if (this.getAngleFrom(g) > 48 && this.getAngleFrom(g) < 135) {
					if (game.getTileMap().getTileOnPosition((int) this.getY(),
							((int) this.getX() - game.getTileMap().getTileSize())) instanceof EmptyTile
							&& checkRuimte("Links", this.getX(), this.getY() - 1)
							&& game.getTileMap().getTileOnPosition(
									(int) this.getY() + (game.getTileMap().getTileSize() - 1), ((int) this.getX()
											- game.getTileMap().getTileSize())) instanceof EmptyTile) {
						this.setX(getX() - game.getTileMap().getTileSize());
					}
					// Als de speler links gaat en tegen een houtblok komt, en het
					// houtblok heeft geen ruimte om te bewegen:
					else {
						g.setX(this.getX() + g.getWidth());
					}
			   // Als de speler van linksboven het houtblok probeert naar beneden te gaan wordt hij daar bij geholpen:	
				} else if (this.getAngleFrom(g) >= 315 && this.getAngleFrom(g) <= 330) {
					g.setX(this.getX() - this.getWidth());
				}
				// Als de speler van boven op een houtblok stuit:
				else if (this.getAngleFrom(g) > 330 || this.getAngleFrom(g) < 30) {
					g.setY(this.getY() - g.getHeight());
				}
				// Als de speler ONDER het houtblok staat
				else {
					game.herstartLevel();
				}
			    // Als het om een steen of houtblok gaat:
			} else if (g instanceof Steen || g instanceof Houtblok) {
				// Als dit houtblok wordt aangeraakt door een steen of houtblok dat vanaf
				// rechts komt:
				if (this.getAngleFrom(g) >= 45 && this.getAngleFrom(g) < 135) {
					g.setX(this.getX() + g.getWidth());
				}
				// Als dit houtblok wordt aangeraakt door een steen of houtblok dat vanaf
				// links komt:
				else if (this.getAngleFrom(g) >= 225 && this.getAngleFrom(g) < 315) {
					g.setX(this.getX() - g.getWidth());
				}
				// Als dit houtblok wordt aangeraakt door een steen of houtblok dat vanaf
				// boven komt
				else if (this.getAngleFrom(g) >= 315 || this.getAngleFrom(g) < 45) {
					g.setY(this.getY() - g.getHeight());
				}
				// Als dit houtblok wordt aangeraakt door een steen of houtblok dat eronder
				// zit
				else {
					this.setY(g.getY() - g.getHeight());
				}

			}
		}
	}
	
	/**
	 * Hier worden de acties afgehandeld als het houtblok met een andere tile in
	 * contact komt.
	 */
	@Override
	public void tileCollisionOccurred(List<CollidedTile> collidedTiles) {
		PVector vector;
		for (CollidedTile ct : collidedTiles) {
			// Als het om een tiletype gaat dat het houtblok moet tegenhouden:
			if (ct.theTile instanceof SolideMuur || ct.theTile instanceof BrozeMuur || ct.theTile instanceof Zeewier) {
				// En de betreffende tile wordt van de bovenkant geraakt:
				if (ct.collisionSide == CollidedTile.TOP) {
					// Zet het houtblok stil.
					try {
						vector = game.getTileMap().getTilePixelLocation(ct.theTile);
						setY(vector.y - getHeight());
						this.setGravity(0.0f);
						this.setSpeed(0);
					} catch (TileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
			// Als het om een stilstaande vis gaat:
			else if (ct.theTile instanceof StilstaandeVis) {
				// Als de vis van de bovenkant wordt geraakt, en de gravity van het houtblok > 0 is, maak de tile leeg.
				if (ct.collisionSide == CollidedTile.TOP && this.getGravity() > 0) {
					vector = game.getTileMap().getTilePixelLocation(ct.theTile);
					game.getTileMap().setTile((int) vector.x / game.getTileMap().getTileSize(),
							(int) vector.y / game.getTileMap().getTileSize(), -1);
				}
				// Als de vis van de bovenkant wordt geraakt, en de gravity van het houtblok 0 is, zet de steen stil.
				else if (ct.collisionSide == CollidedTile.TOP && this.getGravity() == 0.0f) {
					vector = game.getTileMap().getTilePixelLocation(ct.theTile);
					setY(vector.y - getHeight());
					this.setGravity(0);
				}
			}
		}
	}

	/**
	 * Er wordt bij elke update gekeken of het houtblok moet vallen, als dit zo is
	 * dan worden er gravity en speed toegepast.
	 */

	@Override
	public void update() {
		if (game.getTileMap().getTileOnPosition(((int) this.getY() + game.getTileMap().getTileSize()),
				(int) this.getX()) instanceof EmptyTile) {
			this.setGravity(0.07f);
			this.setySpeed(0.50f);
		}
	}
}
