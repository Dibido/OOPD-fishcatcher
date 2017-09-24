package nl.han.ica.FishCatcher;

import nl.han.ica.OOPDProcessingEngineHAN.Alarm.Alarm;
import nl.han.ica.OOPDProcessingEngineHAN.Alarm.IAlarmListener;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.GameObject;
import processing.core.PConstants;
import processing.core.PGraphics;

/**
 * BarObject - Een barobject die leegloopt Created by Dibran & Marnix
 * 
 * @date 02-04-2017
 * @author Dibran & Marnix
 *
 */
public class BarObject extends GameObject implements IAlarmListener {

	private int emptycolor;
	private int filledcolor;
	private int filled;
	private double drainRate;
	private FishCatcher game;
	private int filledBegin;

	/**
	 * BarObject Constructor - maak een bar die tot een bepaalde hoeveelheid
	 * gevuld is.
	 * 
	 * @author Dibran en Marnix
	 * @param game
	 *            - om de methoden van FishCatcher te kunnen gebruiken.
	 * @param width
	 *            - breedte van het object.
	 * @param height
	 *            - hoogte van het object.
	 * @param emptycolor
	 *            - color for the empty part of the bar.
	 * @param filledcolor
	 *            - color for the filled part of the bar.
	 * @param filledBegin
	 *            - portion to be filled in pixels compared to the width in
	 *            pixels(always takes the entire height).
	 */
	public BarObject(FishCatcher game, int width, int height, int emptycolor, int filledcolor, int filledBegin,
			double drainRate) {
		this.game = game;
		this.setWidth(width);
		this.setHeight(height);
		this.emptycolor = emptycolor;
		this.filledcolor = filledcolor;
		this.filledBegin = filledBegin;
		this.filled = filledBegin;
		this.drainRate = drainRate;
		startAlarm();
	}

	@Override
	public void update() {
	}

	/**
	 * Methode voor het tekenen van de balk. Deze methode maakt een vierkant
	 * voor de balk, en daarna een vierkant voor het gevulde gedeelte met een
	 * andere kleur.
	 */
	@Override
	public void draw(PGraphics g) {
		g.rectMode(PConstants.CORNER);
		g.stroke(1);
		g.fill(emptycolor);
		g.rect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
		g.fill(filledcolor);
		g.rect(this.getX(), this.getY(), filled, this.getHeight());
	}

	/**
	 * Methode om het gevulde gedeelte van de balk te zetten.
	 * 
	 * @param filled
	 *            - hoeveelheid dat de balk gevuld moet zijn in pixels.
	 */
	public void setFilled(int filled) {
		this.filled = filled;
	}

	public void setFilledAsBeginFilled() {
		this.filled = this.filledBegin;
	}

	/**
	 * Methode om de hoeveelheid dat de balk gevuld is op te vragen in pixels.
	 * 
	 * @return filled - hoeveelheid dat de balk gevuld is.
	 */
	public int getFilled() {
		return this.filled;
	}
    
	/**
	 * Start het alarm wat gebruikt wordt om de zuurstof te verminderen
	 */
	private void startAlarm() {
		Alarm alarm = new Alarm("Zuurstof Drain", 1 / drainRate);
		alarm.addTarget(this);
		alarm.start();
	}
    
	/**
	 * als het alarm afgaat verlaag de zuurstof, of als hij op is herstartlevel.
	 */
	public void triggerAlarm(String alarmName) {
		if (this.filled <= 0) {
			this.filled = filledBegin;
			game.herstartLevel();
		}
		this.filled--;
		startAlarm();
	}
}
