package nl.han.ica.FishCatcher;

import java.util.List;

import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;

/**
 * Een bewegende vis die als hij in contact komt met de speler gevangen wordt.
 * 
 * @date 02-04-2017
 * @author Dibran & Marnix
 */
public class VangVis extends BewegendeVis {

	/**
	 * Constructor voor vangvis.
	 * 
	 * @param game
	 *            - gameobject om de methoden van FishCatcher aan te kunnen
	 *            roepen.
	 * @param sprite
	 *            - Sprite voor het object.
	 * @param totalFrames
	 *            - Aantal frames van het object, voor het simuleren van
	 *            beweging.
	 */
	public VangVis(FishCatcher game, Sprite sprite, int totalFrames) {
		super(game, sprite, totalFrames);
	}

	@Override
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
	}
}