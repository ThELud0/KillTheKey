/**
 * 
 * author Rayane, modifié par Ludovic
 * 
 * 
 */

package pro3600.application;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javafx.animation.AnimationTimer;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;





public class PartieSolo {


	/***********************base de données******************************/

	SqlBase test ;

	/**on choisit à quelle base se connecter*/
	public void setDB(SqlBase dtb) {
		test = dtb;	
	}

	/********************************************/	


	Collection <Compte> comptes;

	public void setComptes(Collection<Compte> comptes) {
		this.comptes=comptes;
	}
	
	private int score;
	private long time;
	private int life;
	private long immunite;
	private long apparitionSpecial;


	int WIDTH = Stock.wid;       							//Longueur de la fenetre       
	int HEIGHT = Stock.hei;      							//Hauteur de la fenetre		
	long tempsPartie = 40; 							//Temps en parties en s
	int nbvie=3;									//Nombre de vies 
	int difficulte;								//Difficulté de la partie: 1=easy, 2=medium, 3=difficile, 4=impossible

	String pathalien = "Ovni.png";
	double widthalien = WIDTH/6.25;							//Longueur de l'image des Aliens 
	double heightalien = WIDTH/11.11;						//Hauteur de l'image des Aliens
	int NBALIENS = 4;      							//Nombre d'alien max sur l'écran 
	int mincaractere = 1;  							//Nombre min de caractere par alien
	int maxcaractere = 3;  						//Nombre max de caractere par alien
	long dureeVieAlien = 3;							//le prochain alien apparait au bout de
	int dureeImmunite=1;							//Durée en seconde de l'immunité apres une erreur 
	double vitessealien = 2 ;						//Vitesse en y de l'alien 
	double widthspawnminalien = widthalien/5;
	double widthspawnmaxalien = WIDTH-widthalien;
	double heightspawnminalien = 0 ;
	double heightspawnmaxalien = HEIGHT*0.8-heightalien  ;


	double dureemaxAlienSpecial = 5;				//Duree max en s entre 2 alienSpecial
	double vitessealienspecial = 2;					//Vitesse en x de l'alien special


	double widthspaceship = HEIGHT/6.25;						//Longueur de l'image du personnage 
	double heightspaceship = HEIGHT/6.25;					//Hauteur de l'image du personnage 
	double positionXspaceship = WIDTH/2 - widthspaceship/2;				//Position x du personnage principal
	double positionYspaceship = HEIGHT*0.95-heightspaceship;				//Position en y du personnage principal

	Alien planete;
	double widthplanete = WIDTH/1.2;						 
	double heightplanete = HEIGHT/1.5;					
	double positionXplanete = WIDTH/2 - widthplanete/2;				 
	double positionYplanete = HEIGHT-heightplanete;				

	String pathlaser = "Laser.PNG"; 
	double largeurlaser = 5;						//Largeur du laser qui va tuer les aliens 
	double tempslaser = 0.3;						//Temps de laser

	String pathimpact = "Explosion.PNG"; 
	double widthimpact = widthspaceship*0.6;
	double heightimpact = heightspaceship*0.6;

	Image space;


	String[] possible = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};
	String[] rien = {""};

	Group root = new Group();
	Scene scene = new Scene(root);
	Canvas canvas = new Canvas(WIDTH, HEIGHT);
	GraphicsContext gc = canvas.getGraphicsContext2D();


	Image impact = new Image(pathimpact, widthimpact,heightimpact , false, false);
	KeyEvent[] clavier = new KeyEvent [maxcaractere]; 
	Alien spaceship;


	Alien[] aliens ;
	Alien alienSpecial;

	Button LeaveBt = new Button("Leave");

	public void init() {
		root.getChildren().add(canvas);
		gc.setFont(Font.font("Helvetica", FontWeight.BOLD, HEIGHT/24));
		gc.setFill(Color.BISQUE);
		gc.setStroke(Color.RED);
		gc.setLineWidth(1);



		/** Interpretation lorsqu'on tape sur le clavier **/

		scene.setOnKeyPressed(new EventHandler<>() {
			public void handle(KeyEvent e) { 
				int error=1;

				for(int j=0; j<maxcaractere -1; j++) {
					clavier[j]= clavier[j+1];
				}

				clavier[maxcaractere - 1]=e;

				for(int i=0; i<NBALIENS; i++) {	

					if (aliens[i].getvivant() != 2) {

						aliens[i].valideTouch(clavier) ; 
						if (aliens[i].getvivant() == 0) {
							for (int j =0; j<maxcaractere;j++) {
								clavier[j]=null;
							}
							error=0;
						}

						else if (aliens[i].debutCaractere(clavier) == 1){
							error=0;
						}
					}
				}

				if ((difficulte>1)&(error == 1)&((System.nanoTime()/1000000-immunite)>dureeImmunite*1000)) {
					immunite= System.nanoTime()/1000000;
					life = life - 1;
					for (int j =0; j<maxcaractere;j++) {
						clavier[j]=null;
					}
				}
			}
		});


		/** Traitement de la souris (utile pour le monde impossible) **/

		EventHandler<MouseEvent> mouseHandler = new EventHandler<>() {
			public void handle(MouseEvent e) {
				double x=e.getX();
				double y=e.getY();
				if (difficulte==4) {
					if (alienSpecial.getvivant()==1) {
						if ((alienSpecial.getX() < x)&(x < alienSpecial.getX()+ widthalien) ) {

							if ((alienSpecial.getY() < y)&(y < alienSpecial.getY()+heightalien) ) {						
								alienSpecial.InitVivant(0);						
							}					
						}
					}
				}
			}
		};
		scene.setOnMouseDragged(mouseHandler);
		scene.setOnMousePressed(mouseHandler);

		LeaveBt.setTranslateX(Stock.wid/2-Stock.widbouton/2);
		LeaveBt.setTranslateY(Stock.hei/2+Stock.heitext/2);
		LeaveBt.setMinHeight(Stock.heibouton);
		LeaveBt.setMinWidth(Stock.widbouton);



	}

	public void start2() {
		Iterator<Compte> it=comptes.iterator(); 
		while(it.hasNext()) {
			Compte avatar = it.next();
			if (avatar.id()==Stock.choixId) {
				space = new Image(avatar.universe(), WIDTH, HEIGHT, false, false);
				spaceship = new Alien(avatar.fighter() , widthspaceship, heightspaceship /*, widthspawnalien, HEIGHT */, 1, 1,rien);
				spaceship.setPosition(positionXspaceship, positionYspaceship);
				pathalien = avatar.ennemies();		
				planete = new Alien(avatar.home() , widthplanete, heightplanete , 1, 1,rien);
				planete.setPosition(positionXplanete, positionYplanete);


			}
		}

		time=0;
		score=0;

		if (root.getChildren().contains(LeaveBt)) {
			root.getChildren().remove(LeaveBt);
		}

		difficulte=Stock.choixDifficulte;

		long startTime = System.nanoTime();

		/**Initialisation des parametres utiles pour la vie (hors difficulte easy)**/ 

		if (difficulte>1) {
			life = nbvie;
			immunite = (startTime/1000 - dureeImmunite * 1000000)/1000;
			if (difficulte==4) {
				apparitionSpecial=startTime;
			}
		}
		else {
			life=-1;
		}


		/** Initialisation du tableau d'aliens **/
		aliens = new Alien[NBALIENS];
		Alien alien = new Alien(pathalien, widthalien, heightalien/*, widthspawnalien,  heightspawnalien*/ , mincaractere, maxcaractere, possible);
		alien.setPosition((widthspawnmaxalien - widthspawnminalien - widthalien) * Math.random() +widthspawnminalien, heightspawnminalien);
		aliens[0] = alien;

		for(int i=1; i<NBALIENS; i++) {
			Alien nul = new Alien("", 0, 0, 0,/* 0, 0,*/ 0, rien);
			aliens[i]= nul;
		}

		/** Initialisation de l'alien special pour la difficulté IMPOSSIBLE **/

		alienSpecial = new Alien(pathalien, widthalien, heightalien/*, widthspawnalien,  heightspawnalien*/ , mincaractere, maxcaractere, rien);
		alienSpecial.setPosition(widthspawnminalien ,(heightspawnmaxalien - heightspawnminalien - heightalien) * Math.random() +heightspawnminalien);
		alienSpecial.InitVivant(2);

		/** Initialisation du tableau qui stock les touches du clavier **/
		for (int i =0; i<maxcaractere;i++) {
			clavier[i]=null;
		}

		/** Initialisation des collections d'Aliens mort et de lasers pour les afficher apres les avoir tué **/ 

		Collection<Alien> aliensmorts = new ArrayList<>(); 
		Collection<Alien> lasers = new ArrayList<>(); 

		new AnimationTimer() {          

			public void handle(long arg0) {

				if (time < tempsPartie*1000) {

					/** Afficher le fond d'ecran **/
					gc.drawImage(space, 0, 0);    


					/** Traitement de chaque alien du tableau **/
					for(int i=0; i<NBALIENS; i++) {
						int vieux =aliens[i].alientropvieux( dureeVieAlien);
						if (aliens[i].getY()>heightspawnmaxalien) {

							Alien newalien = aliens[i].fairespawn( pathalien , mincaractere , maxcaractere , possible ,(widthspawnmaxalien - widthspawnminalien - widthalien) , widthspawnminalien , heightspawnminalien , aliens );
							aliens[i] = newalien;
							life = life -1;
							if (difficulte>2) {
								aliens[i].renderHard(gc,2);
							}
							else {
								aliens[i].render(gc);
							}
						}
						else if (vieux == 1) { 
							Alien newalien = aliens[i].fairespawn( pathalien , mincaractere , maxcaractere , possible ,(widthspawnmaxalien - widthspawnminalien - widthalien) , widthspawnminalien , heightspawnminalien , aliens );
							newalien.add(aliens);
						
							if (difficulte>2) {
								aliens[i].renderHard(gc,2);
							}
							else {
								aliens[i].render(gc);
							}
						}  
						else if(aliens[i].getvivant() == 0) { 
							score ++;
							Alien newalien = aliens[i].fairespawn( pathalien , mincaractere , maxcaractere , possible ,(widthspawnmaxalien - widthspawnminalien - widthalien) , widthspawnminalien , heightspawnminalien , aliens );							
							aliensmorts.add(aliens[i]);
							lasers.add(aliens[i].laser(pathlaser,aliens[i].getX() + widthalien/2, aliens[i].getY() + heightalien/2, spaceship.getX() + widthspaceship/2, spaceship.getY() + heightspaceship/2, largeurlaser));       

							aliens[i].setTime(System.nanoTime());
							aliens[i] = newalien;


							if (difficulte>2) {
								aliens[i].renderHard(gc,2);
							}
							else {
								aliens[i].render(gc);
							}
						}

						else if (aliens[i].getvivant() == 2) {
						}

						else {							
							aliens[i].setPosition(aliens[i].getX() , aliens[i].getY() + vitessealien );

							if (difficulte>2) {
								aliens[i].renderHard(gc,2);
							}
							else {
								aliens[i].render(gc);
							}

						}				

					}


					/** Traitement de l'alien special **/

					if (difficulte ==4) {
						if (alienSpecial.getvivant()==2) {
							if ((dureemaxAlienSpecial*100*Math.random()<1)||((System.nanoTime()-apparitionSpecial)/1000>dureemaxAlienSpecial*1000000)) {
								alienSpecial.setPosition(widthspawnminalien ,(heightspawnmaxalien - heightspawnminalien - heightalien) * Math.random() +heightspawnminalien);
								alienSpecial.InitVivant(1);						
							}							 
						}

						else if (alienSpecial.getX() > widthspawnmaxalien - widthalien ) {
							apparitionSpecial=System.nanoTime();
							alienSpecial.InitVivant(2);
							life=life -1;
						}

						else if (alienSpecial.getvivant()==1){
							alienSpecial.render(gc);
							alienSpecial.setPosition(alienSpecial.getX() + vitessealienspecial, alienSpecial.getY() );

						}

						else if (alienSpecial.getvivant()==0) {
							score++;
							aliensmorts.add(alienSpecial);	
							lasers.add(alienSpecial.laser(pathlaser,alienSpecial.getX() + widthalien/2, alienSpecial.getY() + heightalien/2, spaceship.getX() + widthspaceship/2, spaceship.getY() + heightspaceship/2, largeurlaser)); 
							alienSpecial.setTime(System.nanoTime()); 

							apparitionSpecial=System.nanoTime();
							alienSpecial.InitVivant(2);
						}

					}


					/** Afficher les aliens morts **/

					Iterator<Alien> aliensmortsit = aliensmorts.iterator();
					Iterator<Alien> lasersit = lasers.iterator();


					while (aliensmortsit.hasNext()) {
						Alien alienaff = aliensmortsit.next();
						Alien laseraff = lasersit.next();

						if ((System.nanoTime()- alienaff.getTime())/ 1000000<tempslaser* 1000 ) {
							alienaff.render(gc);
							laseraff.render(gc);
							gc.drawImage(impact, alienaff.getX()+widthalien/2 - widthimpact/2, alienaff.getY()+heightalien/2 -heightimpact/2);							
						}
						else {
							aliensmortsit.remove();
							lasersit.remove();

						}

					}


					/** Arreter le jeu si le joueur n'a plus de vies **/

					if (life==0) {
						stop();
						if (!(root.getChildren().contains(LeaveBt))) {
							root.getChildren().add(LeaveBt);}
						try {
							test.insererPartieSolo(Stock.choixId, Stock.choixDifficulte, score,java.time.LocalDateTime.now() );
							//test.insert(Stock.choixId,Stock.choixId,Stock.choixId,Stock.choixMode,Stock.choixDifficulte,score,);
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}


					/**Afficher le personnage principal, le score et le temps**/
					planete.render(gc);
					spaceship.render(gc);
					String txt = "Score: " + score;
					time = Math.min((System.nanoTime() - startTime)/1000000,tempsPartie * 1000);
					long decimal = time - 1000*(time/1000);
					String temps = "Time: " + time/1000 +"."+ decimal;
					gc.setTextAlign(TextAlignment.LEFT);
					gc.fillText(txt, WIDTH/1.3, 35,WIDTH-WIDTH/1.3 );
					gc.strokeText(txt, WIDTH/1.3, 35,WIDTH-WIDTH/1.3); 
					gc.fillText(temps,  WIDTH/1.3, 70, WIDTH-WIDTH/1.3 );
					gc.strokeText(temps,  WIDTH/1.3, 70, WIDTH-WIDTH/1.3 );


					/** Affcher les vies sauf pour le mode Easy **/

					if (difficulte >1) {
						String vie = "Vie: " +life;
						gc.fillText(vie,  WIDTH/1.3, 105, WIDTH-WIDTH/1.3 );
						gc.strokeText(vie,  WIDTH/1.3, 105, WIDTH-WIDTH/1.3 );
					}
				}
				if (time >= tempsPartie*1000){
					stop();
					if (!(root.getChildren().contains(LeaveBt))) {
						root.getChildren().add(LeaveBt);}
					
					try {
						test.insererPartieSolo(Stock.choixId, Stock.choixDifficulte, score,java.time.LocalDateTime.now() );
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}

			}
		}.start();
	}


}

