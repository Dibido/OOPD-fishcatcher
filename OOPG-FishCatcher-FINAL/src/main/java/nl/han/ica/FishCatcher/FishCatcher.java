package nl.han.ica.FishCatcher;

import nl.han.ica.OOPDProcessingEngineHAN.Dashboard.Dashboard;
import nl.han.ica.OOPDProcessingEngineHAN.Engine.GameEngine;
import nl.han.ica.OOPDProcessingEngineHAN.Objects.Sprite;
import nl.han.ica.OOPDProcessingEngineHAN.Sound.Sound;
import nl.han.ica.OOPDProcessingEngineHAN.Tile.TileMap;
import nl.han.ica.OOPDProcessingEngineHAN.Tile.TileType;
import nl.han.ica.OOPDProcessingEngineHAN.View.View;
import nl.han.ica.FishCatcher.BarObject;
import nl.han.ica.FishCatcher.TextObject;
import nl.han.ica.FishCatcher.Player;
import nl.han.ica.FishCatcher.tiles.BrozeMuur;
import nl.han.ica.FishCatcher.tiles.Exit;
import nl.han.ica.FishCatcher.tiles.ExitToegankelijk;
import nl.han.ica.FishCatcher.tiles.Explosie;
import nl.han.ica.FishCatcher.tiles.SolideMuur;
import nl.han.ica.FishCatcher.tiles.StilstaandeVis;
import nl.han.ica.FishCatcher.tiles.Zeewier;
import processing.core.PApplet;

/**
 * FishCatcher, een game waarin je vissen moet vangen en daarna naar de exit
 * moet gaan voordat je doodgaat.
 * @date 02-04-2017
 * @author Marnix Lukasse en Dibran Dokter
 */
@SuppressWarnings("serial")
public class FishCatcher extends GameEngine{

	private Sound backgroundMusic;
	private Dashboard dashboard;
	private Dashboard eindscherm;
	private TextObject fishCounterObject;
	private BarObject oxygenMeter;
	private TextObject livesCounterObject;
	private TextObject levelCounterObject;
	private TextObject textZuurstof;
	private TextObject eindschermTextObject;
	private int beginLivesCounter;
	private int livesCounter;
	private int beginFishCounter;
	private int fishCounter;
	private int levelCounter;
	private int oxygenCounter;
	private int[] exitLocatie = {22, 9};

	public static void main(String[] args) {
		PApplet.main(new String[] { "nl.han.ica.FishCatcher.FishCatcher" });
	}

	/**
	 * Setup die nodig is om spel te spelen. Hier worden objecten etc. aangemaakt.
	 */
	@Override
	public void setupGame() {
		int worldWidth = 1250;
		int worldHeight = 700;

	    initializeSound();	
		setDashboardValues(8, 6, 8, 1, 300);
		initializeDashboard(0, 600, worldWidth, 100);
		initializeEindschermDashboard(0, 0, worldWidth, worldHeight);
		createViewWithoutViewport(worldWidth, worldHeight);
		initializeTileMap(levelCounter);
		laadLevel();
	}

	/**
	 * Deze methode initialiseerd de sprites en tiletypes en maakt daarna de tilemap, afhankelijk van de parameter level.
	 * @param level geeft aan voor welk level de tilemap gemaakt moet worden.
	 */
	private void initializeTileMap(int level) {
		// laden van de sprites.
		Sprite SolideMuursprite = new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/Block.jpg");
		Sprite BrozeMuursprite = new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/Block2.jpg");
		Sprite Zeewiersprite = new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/Weed.jpg");
		Sprite StilstaandeVissprite = new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/StandaardVis.jpg");
		Sprite ExplosieSprite = new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/Explosie.jpg");
		Sprite ExitSprite = new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/Exit_Standaard.jpg");
		Sprite ExitSpriteToegankelijk = new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/Exit_Toegankelijk.jpg");
	
		// creeren van de spritetypes.
		TileType<SolideMuur> SolideMuurTileType = new TileType<>(SolideMuur.class, SolideMuursprite);
		TileType<BrozeMuur> BrozeMuurTileType = new TileType<>(BrozeMuur.class, BrozeMuursprite);
		TileType<Zeewier> ZeewierTileType = new TileType<>(Zeewier.class, Zeewiersprite);
		TileType<StilstaandeVis> StilstaandeVisTileType = new TileType<>(StilstaandeVis.class, StilstaandeVissprite);
		TileType<Explosie> ExplosieTileType = new TileType<>(Explosie.class, ExplosieSprite);
		TileType<Exit> ExitTileType = new TileType<>(Exit.class, ExitSprite);		
		TileType<ExitToegankelijk> ExitToegankelijkTileType = new TileType<>(ExitToegankelijk.class, ExitSpriteToegankelijk);
	
		@SuppressWarnings("rawtypes")
		TileType[] tileTypes = { SolideMuurTileType, BrozeMuurTileType, ZeewierTileType, StilstaandeVisTileType,
				ExplosieTileType, ExitTileType, ExitToegankelijkTileType};
		// creeren van de tilemap.
		int tileSize = 50;
	    
		if(level < 1 || level > 3){
		 level = 1;
		}
		if(level == 1){
		int tilesMap[][] = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
				{ 0, -1, -1, -1, -1, -1, -1, 2, 2, 2, 2, 2, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0 },
				{ 0, -1, -1, -1, -1, -1, -1, 2, -1, -1, -1, -1, -1, -1, -1, -1, -1, 3, 2, 2, 2, -1, 2, 2, 0 },
				{ 0, -1, -1, 3, -1, -1, -1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0 },
				{ 0, -1, -1, -1, -1, -1, -1, 2, 2, 2, 2, -1, -1, -1,-1, -1, -1, -1, 2, 2, 2, 2, 2, 2, 0 },
				{ 0, -1, -1, -1, -1, -1, -1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, -1, -1, 2, 2, 2, 2, 2, 0 },
				{ 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, -1, -1, -1 , 2, -1, 2, 2, 2, 2, 0 },
				{ 0, 2, 2, -1, 2, -1, 2, 2, 2, -1, 2, 2, 2, 2, -1, 2, -1, 2, 2,-1, 2, -1, -1, -1, 0 },
				{ 0, 2, 2, 2, 2, 3, 2, 2, 2, -1, 3, 2, 2, 2, -1, 3, -1, 2, 2, 2, 2, -1, 3, -1, 0 },
				{ 0, 2, 2, 3, 2, 2, 2, 2, 2, -1, 2, 2, 3, 2, -1, -1, -1, 2, 2, 2, 2, -1, 5, -1, 0 },
				{ 0, 2, 2, 2, 2, 2, -1, 2, 2, -1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, -1, -1, 3, -1, 0 },
				{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };
										
		tileMap = new TileMap(tileSize, tileTypes, tilesMap);
		} 
		else if(level == 2) {
			int tilesMap[][] = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 0, -1, -1, -1, -1, -1, -1, -1, 2, 2, 2, 2, 3, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0 },
					{ 0, -1, -1, -1, -1, -1, -1, 2, -1, -1, -1, -1, -1, -1, -1, -1, -1, 3, 2, 2, 2, -1, 2, 2, 0 },
					{ 0, -1, -1, 3, -1, -1, -1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0 },
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 2, 2, 0 },
					{ 0, -1, -1, -1, -1, -1, -1, 2, 2, 2, 2, 2, 2, 2, -1, -1, -1, -1, -1, -1, -1, 3, -1, 2, 0 },
					{ 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2 , 2, 2, 2, -1, -1, 2, 0 },
					{ 0, 2, 2, -1, 2, -1, 2, 2, 2, -1, 2, 2, 2, 2, -1, -1, -1, -1, -1,-1, -1, -1, -1, -1, 0 },
					{ 0, 2, 2, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 0, 2, 2, 3, 2, 2, 2, 2, 2, -1, 2, 2, 3, 2, -1, -1, -1, 2, 2, 2, 2, -1, 5, -1, 0 },
					{ 0, 2, 2, 2, 2, 2, -1, 2, 2, -1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, -1, -1, 3, -1, 0 },
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };
											
			tileMap = new TileMap(tileSize, tileTypes, tilesMap);	
		}
		else if(level == 3) {
			int tilesMap[][] = { { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
					{ 0, -1, -1, -1, -1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0 },
					{ 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0 },
					{ 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0 },
					{ 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0 },
					{ 0, -1, -1, -1, -1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, -1, -1, -1, -1, -1, 0 },
					{ 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 2, -1, -1, -1, -1, -1, 0 },
					{ 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0 },
					{ 0, -1, -1, -1, -1, 3, -1, -1, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, -1, -1, -1, 1, 1, 1, 0 },
					{ 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, -1, -1, -1, 5, -1, 0 },
					{ 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 1, -1, -1, -1, -1, -1, 0 },
					{ 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 }, };
											
			tileMap = new TileMap(tileSize, tileTypes, tilesMap);	
		}

		
	}

/**
 * Methode die de dashboardwaardes naar de meegegeven waardes zet.
 * @param livesCounter
 * @param fishCounter
 * @param levelCounter
 * @param oxygenCounter
 */
	private void setDashboardValues(int livesCounter, int fishCounter, int levelCounter, int oxygenCounter) {
		this.livesCounter = livesCounter;
		this.beginFishCounter = fishCounter;
		this.fishCounter = beginFishCounter;
		this.levelCounter = levelCounter;
		this.oxygenCounter = oxygenCounter;
	}
	
/**
 * Methode die de dashboardwaardes naar de meegegeven waardes zet, nu inclusief beginLivesCounter.
 * @param beginLivesCounter
 * @param livesCounter
 * @param fishCounter
 * @param levelCounter
 * @param oxygenCounter
 */
	private void setDashboardValues(int beginLivesCounter, int livesCounter, int fishCounter, int levelCounter, int oxygenCounter) {
		this.beginLivesCounter = beginLivesCounter;
		this.livesCounter = this.beginLivesCounter;
		this.beginFishCounter = fishCounter;
		this.fishCounter = beginFishCounter;
		this.levelCounter = levelCounter;
		this.oxygenCounter = oxygenCounter;
	}

 /**
  * initialiseert het dashbord
  * @param x x-positie van het dashbord
  * @param y y-positie van het dashbord
  * @param dashboardWidth breedte van het dashbord
  * @param dashboardHeight hoogte van het dashbord
  */
	private void initializeDashboard(int x, int y, int dashboardWidth, int dashboardHeight) {
		// create dashboard
		dashboard = new Dashboard(x, y, dashboardWidth, dashboardHeight);
		dashboard.setBackground(0, 0, 0);
		livesCounterObject = new TextObject("Levens: " + beginLivesCounter, 37);
		fishCounterObject = new TextObject("Vissen: " + beginFishCounter, 37);
		levelCounterObject = new TextObject("Level:" + levelCounter, 37);
		oxygenMeter = new BarObject(this, 300, 30, 0xFFFFFFFF, 0xFF0000FF, oxygenCounter, 3);
		textZuurstof = new TextObject("Zuurstof", 37);
		dashboard.addGameObject(livesCounterObject, 225, 35, 1);
		dashboard.addGameObject(fishCounterObject, 25, 35, 1);
		dashboard.addGameObject(levelCounterObject, 1050, 35, 1);
		dashboard.addGameObject(oxygenMeter, 500, 20, 1);
		dashboard.addGameObject(textZuurstof, 575, 50, 1);
		addDashboard(dashboard);
	}
   
   /**
    * Deze methode maakt het eindscherm (wat een dashbord is).
    * @param x   x positie van het dashbord
    * @param y   y positie van het dashbord
    * @param dashboardWidth  breedte van het dashbord
    * @param dashboardHeight  hoogte van het dashbord
    */
	public void initializeEindschermDashboard(int x, int y, int dashboardWidth, int dashboardHeight) {
		eindscherm = new Dashboard(x, y, dashboardWidth, dashboardHeight);
		eindscherm.setBackground(0, 0, 0);
		eindschermTextObject = new TextObject("Helaas, u bent af! Druk op 'R' om te herstarten", 30);
		eindscherm.addGameObject(eindschermTextObject, 300, 300, 2);
		addDashboard(eindscherm);
		eindscherm.setVisible(false);
	}

	/**
	 * Methode voor als de speler een vis heeft gevangen.
	 */
	public void vangVis() {
		if (fishCounter > 0) {
			fishCounter--;
			fishCounterObject.setTextColor(0xFFFFFFFF);
		}
		if (fishCounter == 0) {
			fishCounterObject.setTextColor(0xFF00FF0F);
			getTileMap().setTile(exitLocatie[0], exitLocatie[1], 6);
		}
		fishCounterObject.setText("Vissen: " + fishCounter);

	}
    
	/**
	 * Initialiseert het geluid, het achtergrond muziekje.
	 */
	private void initializeSound() {
		backgroundMusic = new Sound(this, "src/main/java/nl/han/ica/FishCatcher/media/FishCatcher.mp3");
		backgroundMusic.loop(-1);
	}

	/**
	 * Methode die het level herstart, wordt aangeroepen als de speler af gaat of als er op R/r is gedrukt.
	 */
	public void herstartLevel() {
		if (this.livesCounter > 1) {
			dashboard.setVisible(true);
			eindscherm.setVisible(false);
			livesCounter--;
			fishCounterObject.setTextColor(0xFFFFFFFF);
			fishCounter = beginFishCounter;
			fishCounterObject.setText("Vissen: " + fishCounter);
			setDashboardValues(this.livesCounter, this.fishCounter, this.levelCounter, 300);
			oxygenMeter.setFilledAsBeginFilled();
		    livesCounterObject.setText("Levens: "+livesCounter);
			deleteAllGameOBjects();
			initializeTileMap(this.levelCounter);
			laadLevel();
		} else {
			dashboard.setVisible(false);
			deleteAllGameOBjects();
			eindscherm.setVisible(true);
			this.livesCounter = beginLivesCounter + 1;
			this.fishCounter = beginFishCounter;
			this.levelCounter = 1;
		}
	}
    
	/**
	 * Checkt of er op R is gedrukt, in dat geval -> herstartLevel()
	 */
	@Override
	public void keyPressed() {
		super.keyPressed();
		if (key == 'r' || key == 'R') {
			this.herstartLevel();
		}
	}
	
	/**
	 * methode die het volgende level laad. Wordt aangeroepen als de speler in contact is met exitToegankelijk.
	 */
	public void nextLevel(){
		this.levelCounter++;
		this.deleteAllGameOBjects();
		initializeTileMap(levelCounter);
		laadLevel();
	}
	
	/**
	 * laad een bepaald level, voegt de juiste objecten voor dat level toe.
	 */
	private void laadLevel(){
		if(levelCounter < 1 || levelCounter > 3){
			levelCounter = 1;
			livesCounter = 8;
		} 
		fishCounterObject.setTextColor(0xFFFFFFFF);
		if(levelCounter == 1){
			//Zet de dashboard waardes
			//livesCounterObject.setText("Levens: " + beginLivesCounter);
			fishCounter = 8;
			fishCounterObject.setText("Vissen: " + fishCounter);
			levelCounterObject.setText("Level: " + levelCounter);
			oxygenMeter.setFilledAsBeginFilled();
			//Maak de benodigde objecten aan
		Player player = new Player(this);
		addGameObject(player, 50, 50);
		Sprite steensprite = new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/Bal.jpg");
		steensprite.resize(50, 50);
		Sprite houtsprite = new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/Houtblok.png");
		houtsprite.resize(50, 50);
		Steen steen1 = new Steen(this, steensprite);
		Steen steen2 = new Steen(this, steensprite);
		Houtblok hout1 = new Houtblok(this, houtsprite);
		Houtblok hout2 = new Houtblok(this,houtsprite);

		addGameObject(steen1, 150, 100);
		addGameObject(steen2, 300, 500);
		addGameObject(hout1, 450, 350);
		addGameObject(hout2, 450, 450);

	
		BewegendeVis monstervis1 = new MonsterVis(this,
				new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/MonsterVisAnimatie.jpg"), 4);
		BewegendeVis vangvis = new VangVis(this,
				new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/BewegendeVisAnimatie.jpg"), 4);
		BewegendeVis kleefvis = new KleefVis(this,
				new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
		BewegendeVis kleefvis1 = new KleefVis(this,
				new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
		BewegendeVis kleefvis2 = new KleefVis(this,
				new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
		BewegendeVis kleefvis3 = new KleefVis(this,
				new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
		BewegendeVis kleefvis4 = new KleefVis(this,
				new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
		BewegendeVis kleefvis5 = new KleefVis(this,
				new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
		addGameObject(vangvis, 200, 150);
		addGameObject(monstervis1, 700, 250);
		addGameObject(kleefvis, 100, 150);
		addGameObject(kleefvis1, 500, 100);
		addGameObject(kleefvis2, 250, 300);
		addGameObject(kleefvis3, 1000, 500);
		addGameObject(kleefvis4, 1000, 400);
		addGameObject(kleefvis5, 1050, 350);
		} 
		else if(levelCounter == 2){
			//Zet de dashboard waardes
			//livesCounterObject.setText("Levens: " + beginLivesCounter);
			fishCounter = 8;
			fishCounterObject.setText("Vissen: " + fishCounter);
			levelCounterObject.setText("Level: " + levelCounter);
			oxygenMeter.setFilledAsBeginFilled();
			//Maak de benodigde objecten aan
			Player player = new Player(this);
			addGameObject(player, 50, 50);
			Sprite steensprite = new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/Bal.jpg");
			steensprite.resize(50, 50);
			Sprite houtsprite = new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/Houtblok.png");
			houtsprite.resize(50, 50);
			
			Steen steen1 = new Steen(this, steensprite);
			Steen steen2 = new Steen(this, steensprite);
			Steen steen3 = new Steen(this, steensprite);
			Steen steen4 = new Steen(this, steensprite);
			Steen steen5 = new Steen(this, steensprite);

			addGameObject(steen1, 150, 100);
			addGameObject(steen2, 300, 500);
			addGameObject(steen3, 1050, 100);
			addGameObject(steen4, 150, 350);
			addGameObject(steen5, 250, 350);
			
	
			BewegendeVis monstervis1 = new MonsterVis(this,
					new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/MonsterVisAnimatie.jpg"), 4);
			BewegendeVis vangvis = new VangVis(this,
					new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/BewegendeVisAnimatie.jpg"), 4);
			BewegendeVis vangvis2 = new VangVis(this,
					new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/BewegendeVisAnimatie.jpg"), 4);
			BewegendeVis kleefvis = new KleefVis(this,
					new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
			BewegendeVis kleefvis1 = new KleefVis(this,
					new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
			BewegendeVis kleefvis2 = new KleefVis(this,
					new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
			BewegendeVis kleefvis3 = new KleefVis(this,
					new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
			BewegendeVis kleefvis4 = new KleefVis(this,
					new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
			BewegendeVis kleefvis5 = new KleefVis(this,
					new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
			addGameObject(vangvis, 200, 150);
			addGameObject(vangvis2, 1150, 350);
			addGameObject(monstervis1, 700, 250);
			addGameObject(kleefvis, 100, 150);
			addGameObject(kleefvis1, 500, 100);
			addGameObject(kleefvis2, 250, 300);
			addGameObject(kleefvis3, 1000, 450);
			addGameObject(kleefvis4, 1000, 400);
			addGameObject(kleefvis5, 1050, 350);
			} 
		else if(levelCounter == 3){
			//Zet de dashboard waardes
			//livesCounterObject.setText("Levens: " + beginLivesCounter);
			fishCounter = 8;
			fishCounterObject.setText("Vissen: " + fishCounter);
			levelCounterObject.setText("Level: " + levelCounter);
			oxygenMeter.setFilledAsBeginFilled();
			//Maak de benodigde objecten aan
				Player player = new Player(this);
				addGameObject(player, 50, 50);
				Sprite steensprite = new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/Bal.jpg");
				steensprite.resize(50, 50);
				Sprite houtsprite = new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/Houtblok.png");
				houtsprite.resize(50, 50);
				
				Steen steen1 = new Steen(this, steensprite);
				Steen steen2 = new Steen(this, steensprite);
				Steen steen3 = new Steen(this, steensprite);
				Steen steen4 = new Steen(this, steensprite);
				Steen steen5 = new Steen(this, steensprite);
				Steen steen6 = new Steen(this, steensprite);
				Steen steen7 = new Steen(this, steensprite);
				Steen steen8 = new Steen(this, steensprite);
				Steen steen9 = new Steen(this, steensprite);
				Steen steen10 = new Steen(this, steensprite);
				
				addGameObject(steen1, 150, 100);
				addGameObject(steen2, 300, 500);
				addGameObject(steen3, 1050, 100);
				addGameObject(steen4, 150, 350);
				addGameObject(steen5, 250, 350);
				addGameObject(steen6, 150, 100);
				addGameObject(steen7, 250, 100);
				addGameObject(steen8, 350, 100);
				addGameObject(steen9, 450, 100);
				addGameObject(steen10, 650, 100);
				
			
				BewegendeVis vangvis1 = new VangVis(this,
						new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/BewegendeVisAnimatie.jpg"), 4);
				BewegendeVis vangvis2 = new VangVis(this,
						new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/BewegendeVisAnimatie.jpg"), 4);
				BewegendeVis vangvis7 = new VangVis(this,
						new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/BewegendeVisAnimatie.jpg"), 4);
				BewegendeVis vangvis8 = new VangVis(this,
						new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/BewegendeVisAnimatie.jpg"), 4);
		
				
				BewegendeVis kleefvis1 = new KleefVis(this,
						new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
				BewegendeVis kleefvis2 = new KleefVis(this,
						new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
				BewegendeVis kleefvis3 = new KleefVis(this,
						new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
				BewegendeVis kleefvis4 = new KleefVis(this,
						new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
				BewegendeVis kleefvis5 = new KleefVis(this,
						new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
				BewegendeVis kleefvis6 = new KleefVis(this,
						new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
				BewegendeVis kleefvis7 = new KleefVis(this,
						new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
				BewegendeVis kleefvis8 = new KleefVis(this,
						new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
				BewegendeVis kleefvis9 = new KleefVis(this,
						new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
				BewegendeVis kleefvis10 = new KleefVis(this,
						new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
				BewegendeVis kleefvis11 = new KleefVis(this,
						new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
				BewegendeVis kleefvis12 = new KleefVis(this,
						new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
				BewegendeVis kleefvis13 = new KleefVis(this,
						new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
				BewegendeVis kleefvis14 = new KleefVis(this,
						new Sprite("src/main/java/nl/han/ica/FishCatcher/media/tiles/KleefVisAnimatie.jpg"), 4);
				
				addGameObject(vangvis1, 50, 200);
				addGameObject(vangvis2, 350, 200);
				addGameObject(vangvis7, 650, 300);
				addGameObject(vangvis8, 950, 300);
				
				addGameObject(kleefvis1, 200, 200);
				addGameObject(kleefvis2, 500, 200);
				addGameObject(kleefvis3, 800, 200);
				addGameObject(kleefvis4, 1100, 200);
				addGameObject(kleefvis5, 200, 300);
				addGameObject(kleefvis6, 500, 300);
				addGameObject(kleefvis7, 800, 300);
				addGameObject(kleefvis8, 1100, 300);
				addGameObject(kleefvis9, 400, 350);
				addGameObject(kleefvis10, 500, 350);
				addGameObject(kleefvis11, 550, 350);
				addGameObject(kleefvis12, 650, 350);
				addGameObject(kleefvis13, 700, 350);
				addGameObject(kleefvis14, 800, 350);
				}
	}

	/**
	 * Maakt de viewport en zet de juiste achtergrond.
	 * 
	 * @param screenWidth
	 *            De schermbreedte van het speelscherm.
	 * @param screenHeight
	 *            De spelhoogte van het speelscherm.
	 */
	private void createViewWithoutViewport(int screenWidth, int screenHeight) {
		View view = new View(screenWidth, screenHeight);
		view.setBackground(loadImage("src/main/java/nl/han/ica/FishCatcher/media/waterAchtergrondResized.png"));

		setView(view);
		size(screenWidth, screenHeight);
	}

	@Override
	public void update() {
	}
}
