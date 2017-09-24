package nl.han.ica.FishCatcher;

import java.util.List;

import nl.han.ica.FishCatcher.tiles.BrozeMuur;
import nl.han.ica.FishCatcher.tiles.Exit;
import nl.han.ica.FishCatcher.tiles.ExitToegankelijk;
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
 * 
 * Created by Dibran & Marnix
 * 
 * @date 02-04-2017
 * @author Dibran & Marnix
 *
 */
public class Steen extends MySpriteObject {

	/**
	 * Constructor van het Steen object
	 * 
	 * @param game
	 *            - Om de methoden van FishCatcher aan te kunnen roepen
	 * @param sprite
	 *            - Sprite voor het object
	 */
	public Steen(FishCatcher game, Sprite sprite) {
		super(sprite, game);
		this.setGravity(0.00f);
		this.setSpeed(0.0f);
	}

	/**
	 * Hier worden de acties afgehandeld die gebeuren als de steen in contact
	 * komt met een ander GameObject.
	 */
	@Override
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
		for (GameObject g : collidedGameObjects) {
			if (g instanceof Player) {
				// Als de speler rechts gaat en tegen een steen komt, en de
				// steen ruimte heeft om te bewegen:
				if (this.getAngleFrom(g) >= 225 && this.getAngleFrom(g) < 315) {
					if (game.getTileMap().getTileOnPosition((int) this.getY(),
							((int) this.getX() + game.getTileMap().getTileSize())) instanceof EmptyTile
							&& checkRuimte("Rechts", this.getX(), this.getY() - 1)
							&& game.getTileMap().getTileOnPosition(
									(int) this.getY() + (game.getTileMap().getTileSize() - 1), ((int) this.getX()
											+ game.getTileMap().getTileSize())) instanceof EmptyTile) {
						this.setX(getX() + game.getTileMap().getTileSize());
					}
					// Als de speler rechts gaat en tegen een steen komt, en de
					// steen heeft geen ruimte om te bewegen:
					else {
						g.setX(this.getX() - g.getWidth());
					}
				// Als de speler van rechtsboven de steen probeert naar beneden te gaan wordt hij daar bij geholpen:
				} else if (this.getAngleFrom(g) >= 30 && this.getAngleFrom(g) <= 48) {
					g.setX(this.getX() + this.getWidth());
				}
				// Als de speler links gaat en tegen een steen komt, maar de
				// steen geen ruimte heeft om te bewegen:
				else if (this.getAngleFrom(g) > 48 && this.getAngleFrom(g) < 135) {
					if (game.getTileMap().getTileOnPosition((int) this.getY(),
							((int) this.getX() - game.getTileMap().getTileSize())) instanceof EmptyTile
							&& checkRuimte("Links", this.getX(), this.getY() - 1)
							&& game.getTileMap().getTileOnPosition(
									(int) this.getY() + (game.getTileMap().getTileSize() - 1), ((int) this.getX()
											- game.getTileMap().getTileSize())) instanceof EmptyTile) {
						this.setX(getX() - game.getTileMap().getTileSize());
					}
					// Als de speler links gaat en tegen een steen komt, en de
					// steen heeft geen ruimte om te bewegen:
					else {
						g.setX(this.getX() + g.getWidth());
					}
				// Als de speler van linksboven de steen probeert naar beneden te gaan wordt hij daar bij geholpen:	
				} else if (this.getAngleFrom(g) >= 315 && this.getAngleFrom(g) <= 330) {
					g.setX(this.getX() - this.getWidth());
				}
				// Als de speler van boven op een steen stuit:
				else if (this.getAngleFrom(g) > 330 || this.getAngleFrom(g) < 30) {
					g.setY(this.getY() - g.getHeight());
				}
				// Als de speler ONDER de steen staat
				else {
					game.herstartLevel();
				}
			    // Als het een steen betreft:	
			} else if (g instanceof Steen) {
				// Als deze steen wordt aangeraakt door een steen die vanaf
				// rechts komt
				if (this.getAngleFrom(g) >= 45 && this.getAngleFrom(g) < 135) {
					g.setX(this.getX() + g.getWidth());
				}
				// Als deze steen wordt aangeraakt door een steen die vanaf
				// links komt
				else if (this.getAngleFrom(g) >= 225 && this.getAngleFrom(g) < 315) {
					g.setX(this.getX() - g.getWidth());
				}
				// Als deze steen wordt aangeraakt door een steen die vanaf
				// boven komt
				else if (this.getAngleFrom(g) >= 315 || this.getAngleFrom(g) < 45) {
					g.setY(this.getY() - g.getHeight());
				}
				// Als deze steen wordt aangeraakt door een steen die eronder
				// zit
				else {
					this.setY(g.getY() - g.getHeight());
				    // Als het gebied links van de steen vrij is van objecten en tiles, verplaatst de steen naar links.
					if (((Steen) g).checkRuimte("Links", this.getX(), this.getY() + 2)
							&& ((Steen) g).checkRuimteTile("Links", this.getX(), this.getY())
							&& ((Steen) g).checkRuimteTile("Links", this.getX(),
									this.getY() + game.getTileMap().getTileSize())) {
						this.setX(g.getX() - game.getTileMap().getTileSize());
					// Als het gebied rechts van de steen vrij is van objecten en tiles, verplaatst de steen naar rechts.
					} else if (((Steen) g).checkRuimte("Rechts", this.getX(), this.getY() + 2)
							&& ((Steen) g).checkRuimteTile("Rechts", this.getX(), this.getY())
							&& ((Steen) g).checkRuimteTile("Rechts", this.getX(),
									this.getY() + game.getTileMap().getTileSize())) {
						this.setX(g.getX() + game.getTileMap().getTileSize());
					}
				}
			// Als het om een bewegende vis gaat:
			} else if (g instanceof BewegendeVis) {
				// Als de vis zich onder de steen bevind, verwijder zijn alarm en het object.
				if (this.getAngleFrom(g) > 135 && this.getAngleFrom(g) < 225) {
					((BewegendeVis) g).removeAlarm();
					game.deleteGameObject(g);
				}
			}
		}
	}

	/**
	 * Hier worden de acties afgehandeld als de steen met een andere tile in
	 * contact komt.
	 */
	@Override
	public void tileCollisionOccurred(List<CollidedTile> collidedTiles) {
		PVector vector;
		for (CollidedTile ct : collidedTiles) {
			// Als het om een tiletype gaat dat de steen moet tegenhouden:
			if (ct.theTile instanceof SolideMuur || ct.theTile instanceof BrozeMuur || ct.theTile instanceof Zeewier
					|| ct.theTile instanceof Exit || ct.theTile instanceof ExitToegankelijk) {
				// En de betreffende tile wordt van de bovenkant geraakt:
				if (ct.collisionSide == CollidedTile.TOP) {
					// Zet de steen stil.
					try {
						vector = game.getTileMap().getTilePixelLocation(ct.theTile);
						setY(vector.y - getHeight());
						this.setGravity(0.0f);
						this.setSpeed(0);
					} catch (TileNotFoundException e) {
						e.printStackTrace();
					}
				}
			// Als het om een stilstaande vis gaat:
			} else if (ct.theTile instanceof StilstaandeVis) {
				// Als de vis van de bovenkant wordt geraakt, en de gravity van de steen > 0 is, maak de tile leeg.
				if (ct.collisionSide == CollidedTile.TOP && this.getGravity() > 0) {
					vector = game.getTileMap().getTilePixelLocation(ct.theTile);
					game.getTileMap().setTile((int) vector.x / game.getTileMap().getTileSize(),
							(int) vector.y / game.getTileMap().getTileSize(), -1);
				}  
				// Als de vis van de bovenkant wordt geraakt, en de gravity van de steen 0 is, zet de steen stil.
				else if (ct.collisionSide == CollidedTile.TOP && this.getGravity() == 0.0f) {
					vector = game.getTileMap().getTilePixelLocation(ct.theTile);
					setY(vector.y - getHeight());
					this.setGravity(0);
				}
			}
		}
	}

	/**
	 * Er wordt bij elke update gekeken of de steen moet vallen, als dit zo is
	 * dan worden er gravity en speed toegepast.
	 */
	@Override
	public void update() {
		if (game.getTileMap().getTileOnPosition(((int) this.getY() + game.getTileMap().getTileSize()),
				(int) this.getX()) instanceof EmptyTile) {
			this.setGravity(0.07f);
			this.setxSpeed(0.0f);
			this.setySpeed(0.50f);
		}
	}
}
