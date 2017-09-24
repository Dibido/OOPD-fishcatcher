package nl.han.ica.FishCatcher;

import java.util.List;

import nl.han.ica.FishCatcher.tiles.StilstaandeVis;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;

/**
 * KleefVis - Een vis die aan vriendlijke vissen blijft kleven als hij ze
 * tegenkomt. Hij blokeerd de speler.
 * 
 * Created by Dibran & Marnix
 * 
 * @date 02-04-2017
 * @author Dibran & Marnix
 *
 */
public class KleefVis extends BewegendeVis {

	/**
	 * Constructor van de KleefVis
	 * 
	 * @param game
	 *            - om de methodes van FishCatcher aan te kunnen roepen.
	 * @param sprite
	 *            - Sprite van het object.
	 * @param totalFrames
	 *            - Aantal frames van de animatedSprite.
	 */
	public KleefVis(FishCatcher game, Sprite sprite, int totalFrames) {
		super(game, sprite, totalFrames);
	}

	/**
	 * Deze methode handelt de acties af als er een ander GameObject wordt
	 * geraakt. Als de speler tegen de KleefVis aankomt wordt hij geblokkeerd.
	 */
	@Override
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
		for (GameObject go : collidedGameObjects) {
			if (go instanceof Player) {
				if (getAngleFrom(go) >= 225 && getAngleFrom(go) < 315) {
					go.setX(getX() - go.getWidth());
				} else if (getAngleFrom(go) >= 315 || getAngleFrom(go) < 45) {
					go.setY(getY() - go.getHeight());
				} else if (getAngleFrom(go) >= 45 && getAngleFrom(go) < 115) {
					go.setX(getX() + go.getWidth());
				} else if (getAngleFrom(go) >= 115 && getAngleFrom(go) < 225) {
					go.setY(getY() + go.getHeight());
				}
			}
		}
	}

	/**
	 * Checkt elke keer dat het object getekend wordt of er naast de kleefvis
	 * een stilstaandeVis is, en als dit zo is gaat deze stilstaan.
	 */
	@Override
	public void update() {
		// kleef rechts
		if (game.getTileMap().getTileOnPosition((int) getY(),
				((int) getX() - game.getTileMap().getTileSize())) instanceof StilstaandeVis) {
			if (super.getStil() == false) {
				super.zetStil(true);
				super.setCurrentFrameIndex(0);
			}
		}
		// kleef onder
		else if (game.getTileMap().getTileOnPosition(((int) getY() + game.getTileMap().getTileSize()),
				(int) getX()) instanceof StilstaandeVis) {
			if (super.getStil() == false) {
				super.zetStil(true);
				super.setCurrentFrameIndex(0);
			}
		}
		// kleef links
		else if (game.getTileMap().getTileOnPosition((int) getY(),
				((int) getX() + game.getTileMap().getTileSize())) instanceof StilstaandeVis) {
			if (super.getStil() == false) {
				super.zetStil(true);
				super.setCurrentFrameIndex(0);
			}
		}
		// kleef boven
		else if (game.getTileMap().getTileOnPosition(((int) getY() - game.getTileMap().getTileSize()),
				(int) getX()) instanceof StilstaandeVis) {
			if (super.getStil() == false) {
				super.zetStil(true);
				super.setCurrentFrameIndex(0);
			}
		} else {
			super.zetStil(false);
		}
	}
}
