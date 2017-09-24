package nl.han.ica.FishCatcher;

import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import processing.core.PConstants;
import processing.core.PGraphics;

/**
 * Wordt gebruikt om een tekst te kunnen afbeelden
 * 
 * @date 02-04-2017
 * @author Dibran & Marnix
 */
public class TextObject extends GameObject {

	private String text;
	private int textSize;
	private int color;

	/**
	 * Constructor van het TextObject
	 * 
	 * @param text
	 *            - Text die je wilt laten zien.
	 */
	public TextObject(String text) {
		this.text = text;
		this.textSize = 50;
		this.color = 0xFFFFFFFF;
	}

	/**
	 * Constructor van het TextObject
	 * 
	 * @param text
	 *            - Text die je wilt laten zien
	 * @param textSize
	 *            - De grootte van de text
	 */
	public TextObject(String text, int textSize) {
		this.text = text;
		this.textSize = textSize;
		this.color = 0xFFFFFFFF;
	}

	/**
	 * Methode om de text van een object te zetten
	 * 
	 * @param text
	 *            - Text om te zetten
	 */
	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void update() {

	}

	@Override
	public void draw(PGraphics g) {
		g.textAlign(PConstants.LEFT, PConstants.TOP);
		g.textSize(textSize);
		g.fill(color);
		g.text(text, getX(), getY());
	}

	/**
	 * Methode om de kleur van de text te zetten
	 * 
	 * @param color
	 *            - kleur van de text
	 */
	public void setTextColor(int color) {
		this.color = color;
	}
}
