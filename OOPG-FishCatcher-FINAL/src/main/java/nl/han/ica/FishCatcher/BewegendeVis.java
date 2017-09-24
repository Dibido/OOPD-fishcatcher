package nl.han.ica.FishCatcher;

import java.util.List;
import java.util.Vector;

import nl.han.ica.OOPDProcessingEngineHAN.Alarm.Alarm;
import nl.han.ica.OOPDProcessingEngineHAN.Alarm.IAlarmListener;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.CollidedTile;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithGameObjects;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithTiles;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.AnimatedSpriteObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.OOPDProcessingEngineHAN.Tile.EmptyTile;

/**
 * Bewegende vis - Deze klasse implementeerd het "left-first" pattroon dat door
 * alle vissen die hier van erven wordt uitgevoerd.
 * 
 * Created by Dibran & Marnix
 * 
 * @date 02-04-2017
 * @author Dibran & Marnix
 *
 */
public abstract class BewegendeVis extends AnimatedSpriteObject
		implements ICollidableWithGameObjects, ICollidableWithTiles, IAlarmListener {

	protected FishCatcher game;
	private RICHTING directie;
	private float beweegSnelheid;
	private boolean staatStil;
	private Alarm alarm;

	//Om de mogelijke richtingen te limiteren
	private static enum RICHTING {
		BOVEN(0.0f), ONDER(180.0f), LINKS(270.0f), RECHTS(90.0f);
		@SuppressWarnings("unused")
		private float waarde;

		private RICHTING(float waarde) {
			this.waarde = waarde;
		}
	};

	/**
	 * Constructor voor bewegendevis
	 * @param game - FishCatcher object om de methodes van FishCatcher aan te kunnen roepen.
	 * @param sprite - Sprite van het object.
	 * @param totalFrames - Aantal frames van de sprite.
	 */
	public BewegendeVis(FishCatcher game, Sprite sprite, int totalFrames) {
		super(sprite, totalFrames);
		this.game = game;
		this.beweegSnelheid = 5;
		this.directie = RICHTING.BOVEN;
		this.staatStil = false;
		setCurrentFrameIndex(0);
		startAlarm();
	}

	public void tileCollisionOccurred(List<CollidedTile> collidedTiles) {
	}

	/**
	 * Deze methode wordt gebruikt om de gameobject collisions af te handelen,
	 */
	public abstract void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects);

	@Override
	public void update() {
	}
    
	/**
	 * start het alarm voor de vissen.
	 */
	private void startAlarm() {
		alarm = new Alarm("Bewegen", 1 / beweegSnelheid);
		alarm.addTarget(this);
		alarm.start();
	}

	/**
	 * Als het alarm afgaat, moet de vis bewegen mits hij niet stilstaat
	 */
	public void triggerAlarm(String alarmName) {
		if (!staatStil) {
			beweeg();
		}
		startAlarm();
	}
   
	/**
	 * Verwijderd het alarm
	 */
	public void removeAlarm() {
		alarm.removeTarget(this);
	}

	/**
	 * Zet de vis stil
	 * @param stand - Of de vis stilstaat
	 */
	public void zetStil(boolean stand) {
		staatStil = stand;
	}

	/**
	 * Opvragen of de vis stilstaat
	 * @return stand - Of de vis stilstaat
	 */
	public boolean getStil() {
		return staatStil;
	}

	/**
	 * Methode die de left first beweging van de vissen implementeerd. Dit houdt
	 * in dat de vis als het mogelijk is vanuit zijn directie naar links gaat en
	 * als daar wat is al zijn opties afloopt.
	 */
	private void beweeg() {
		switch (directie) {
		case BOVEN:
			if (linksVrij()) {
				gaNaarLinks();
			} else if (bovenVrij()) {
				gaNaarBoven();
			} else if (rechtsVrij()) {
				gaNaarRechts();
			} else if (onderVrij()) {
				gaNaarOnder();
			}
			break;
		case RECHTS:
			if (bovenVrij()) {
				gaNaarBoven();
			} else if (rechtsVrij()) {
				gaNaarRechts();
			} else if (onderVrij()) {
				gaNaarOnder();
			} else if (linksVrij()) {
				gaNaarLinks();
			}
			break;
		case ONDER:
			if (rechtsVrij()) {
				gaNaarRechts();
			} else if (onderVrij()) {
				gaNaarOnder();
			} else if (linksVrij()) {
				gaNaarLinks();
			} else if (bovenVrij()) {
				gaNaarBoven();
			}
			break;
		case LINKS:
			if (onderVrij()) {
				gaNaarOnder();
			} else if (linksVrij()) {
				gaNaarLinks();
			} else if (bovenVrij()) {
				gaNaarBoven();
			} else if (rechtsVrij()) {
				gaNaarRechts();
			}
			break;
		}
	}

	/**
	 * Checkt of er echt ruimte is (er geen object zich daar bevind) in een
	 * bepaalde richting.
	 * 
	 * @param richting
	 * @return true of false
	 */
	public boolean checkRuimte(RICHTING richting) {
		Vector<GameObject> gameitems = new Vector<GameObject>(game.getGameObjectItems());

		switch (richting) {
		case RECHTS:
			for (GameObject go : gameitems) {
				if (go.getX() >= (this.getX() + this.getWidth()) && go.getX() < this.getX() + 2 * this.getWidth()) {
					if (go.getY() > (this.getY() - this.getHeight()) && go.getY() < (this.getY() + this.getHeight())) {
						return false;
					}
				}
			}
			return true;
		case LINKS:
			for (GameObject go : gameitems) {
				if (go.getX() > this.getX() - (2 * this.width) && go.getX() < this.getX()) {
					if (go.getY() > (this.getY() - this.getHeight()) && go.getY() < (this.getY() + this.getHeight())) {
						return false;
					}
				}
			}
			return true;
		case ONDER:
			for (GameObject go : gameitems) {
				if (go.getY() >= this.getY() + this.getHeight() && go.getY() < this.getY() + 2 * this.getHeight()) {
					if (go.getX() >= this.getX() && (go.getX() < (this.getX() + this.getWidth()))) {
						return false;
					}
				}
			}
			return true;
		case BOVEN:
			for (GameObject go : gameitems) {
				if ((go.getY() >= this.getY() - this.getHeight()) && go.getY() < this.getY()) {
					if (go.getX() >= this.getX() && (go.getX() < (this.getX() + this.getWidth()))) {
						return false;
					}
				}
			}
			return true;
		default:
			return true;
		}
	}
	
	private boolean linksVrij() {
		if (game.getTileMap().getTileOnPosition((int) getY(),
				((int) getX() - game.getTileMap().getTileSize())) instanceof EmptyTile && checkRuimte(RICHTING.LINKS)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean bovenVrij() {
		if (game.getTileMap().getTileOnPosition(((int) getY() - game.getTileMap().getTileSize()),
				(int) getX()) instanceof EmptyTile && checkRuimte(RICHTING.BOVEN)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean rechtsVrij() {
		if (game.getTileMap().getTileOnPosition((int) getY(),
				((int) getX() + game.getTileMap().getTileSize())) instanceof EmptyTile
				&& checkRuimte(RICHTING.RECHTS)) {
			return true;
		} else {
			return false;
		}
	}

	private boolean onderVrij() {
		if (game.getTileMap().getTileOnPosition(((int) getY() + game.getTileMap().getTileSize()),
				((int) getX())) instanceof EmptyTile && checkRuimte(RICHTING.ONDER)) {
			return true;
		} else {
			return false;
		}
	}

	private void gaNaarLinks() {
		this.setCurrentFrameIndex(1);
		directie = RICHTING.LINKS;
		setX(getX() - game.getTileMap().getTileSize());
	}

	private void gaNaarBoven() {
		this.setCurrentFrameIndex(3);
		directie = RICHTING.BOVEN;
		setY(getY() - game.getTileMap().getTileSize());
	}

	private void gaNaarRechts() {
		this.setCurrentFrameIndex(2);
		directie = RICHTING.RECHTS;
		setX(getX() + game.getTileMap().getTileSize());
	}

	private void gaNaarOnder() {
		this.setCurrentFrameIndex(0);
		directie = RICHTING.ONDER;
		setY(getY() + game.getTileMap().getTileSize());
	}

	
}