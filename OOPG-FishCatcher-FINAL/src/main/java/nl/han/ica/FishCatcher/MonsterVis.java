package nl.han.ica.FishCatcher;

import java.util.List;

import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;

/**
 * Monstervis - Een bewegende vis die de speler vermoord als hij met hem in
 * contact komt. Created by Dibran & Marnix
 * 
 * @date 02-04-2017
 * @author Dibran & Marnix
 *
 */
public class MonsterVis extends BewegendeVis {

	/**
	 * Constructor van de MonsterVis
	 * 
	 * @param game
	 *            - om de methodes van FishCatcher aan te kunnen roepen.
	 * @param sprite
	 *            - Sprite van het object.
	 * @param totalFrames
	 *            - Aantal frames van de animatedSprite.
	 */
	public MonsterVis(FishCatcher game, Sprite sprite, int totalFrames) {
		super(game, sprite, totalFrames);
	}

	/**
	 * Methode die de acties afhandeld als de MonsterVis een ander GameObject
	 * aanraakt.
	 */
	@Override
	public void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects) {
		for (GameObject go : collidedGameObjects) {
			if (go instanceof Player) {
				game.herstartLevel();
			}
		}
	}
}
