package nl.han.ica.FishCatcher;

import java.util.List;
import java.util.Vector;

import nl.han.ica.OOPDProcessingEngineHAN.Collision.CollidedTile;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithGameObjects;
import nl.han.ica.OOPDProcessingEngineHAN.Collision.ICollidableWithTiles;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.SpriteObject;
import nl.han.ica.OOPDProcessingEngineHAN.Tile.EmptyTile;

/**
 * MySpriteObject - Created by Dibran & Marnix
 * 
 * @date 02-04-2017
 * @author Dibran & Marnix
 *
 */
public abstract class MySpriteObject extends SpriteObject implements ICollidableWithGameObjects, ICollidableWithTiles {
	FishCatcher game;

	public MySpriteObject(Sprite sprite, FishCatcher game) {
		super(sprite);
		this.game = game;
	}

	@Override
	public abstract void tileCollisionOccurred(List<CollidedTile> collidedTiles);

	@Override
	public abstract void gameObjectCollisionOccurred(List<GameObject> collidedGameObjects);

	@Override
	public abstract void update();

	/**
	 * Checkt of er recht ruimte is (er geen object zich daar bevind)
	 * 
	 * @param richting
	 *            - richting om te checken vanaf het huidige object
	 * @param x
	 *            - x positie om te checken
	 * @param y
	 *            - y positie om te checken
	 * @return of er ruimte is
	 */
	public boolean checkRuimte(String richting, float x, float y) {
		Vector<GameObject> gameitems = game.getGameObjectItems();

		if (richting == "Rechts") {
			for (GameObject go : gameitems) {
				if (go.getX() >= (x + this.getWidth()) && go.getX() < x + 2 * this.getWidth()) {
					if (go.getY() > (y - this.getHeight()) && go.getY() < (y + this.getHeight())) {
						return false;
					}
				}
			}
		} else if (richting == "Links") {
			for (GameObject go : gameitems) {
				if (go.getX() > this.getX() - (2 * this.width) && go.getX() < this.getX()) {
					if (go.getY() > (y - this.getHeight()) && go.getY() < (y + this.getHeight())) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Checkt of er recht ruimte is (er geen tile zich daar bevind)
	 * 
	 * @param richting
	 *            - richting om te checken vanaf het huidige object
	 * @param x
	 *            - x positie om te checken
	 * @param y
	 *            - y positie om te checken
	 * @return of er ruimte is
	 */
	public boolean checkRuimteTile(String richting, float x, float y) {
		if (richting == "Links") {
			if (game.getTileMap().getTileOnPosition((int) y,
					(int) x - game.getTileMap().getTileSize()) instanceof EmptyTile) {
				return true;
			}
		} else if (richting == "Rechts") {
			if (game.getTileMap().getTileOnPosition((int) y,
					(int) x + game.getTileMap().getTileSize()) instanceof EmptyTile) {
				return true;
			}

		}
		return false;
	}

}