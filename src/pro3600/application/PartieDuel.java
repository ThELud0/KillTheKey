/*
 * @autor Rayane Dahdah modifié par ludo
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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;



public class PartieDuel {


	/***********************base de données******************************/

	SqlBase test ;

	/**on choisit à quelle base se connecter*/
	public void setDB(SqlBase dtb) {
		test = dtb;	
	}


	/**************************************************/


	Collection <Compte> comptes;

	public void setComptes(Collection<Compte> comptes) {
		this.comptes=comptes;
	}


	private int idg;
	private int idd;
	private int scoreg;
	private int scored;
	private long time;
	private int lifeg;
	private int lifed;
	private long immunitegauche;
	private long immunitedroite;


	int WIDTH = Stock.wid;       							//Longueur de la fenetre       
	int HEIGHT = Stock.hei;      							//Hauteur de la fenetre
	long tempsPartie = 40; 							//Temps en parties en s
	int nbvie = 3;									//Nombre de vies 
	int difficulte = 2;								//Difficulté de la partie: 1=easy, 2=medium, 3=difficile

	String pathbackgroundgauche = "background.jpg";
	String pathbackgrounddroite = "space.jpg";


	String pathaliengauche = "Ovni.png";
	String pathaliendroite = "Ovni.png";


	double widthalien = WIDTH/6.25;							//Longueur de l'image des Aliens 
	double heightalien = WIDTH/11.11;							//Hauteur de l'image des Aliens 
	int NBALIENS = 4;      							//Nombre d'alien max sur l'écran 
	int mincaractere = 1;  							//Nombre min de caractere par alien
	int maxcaractere = 3;  							//Nombre max de caractere par alien
	long dureeVieAlien = 3;							//L'Alien disparait au bout de cb de s
	int dureeImmunite = 1;							//Durée en seconde de l'immunité apres une erreur 		
	double vitessealien = 2;						//Vitesse en y de l'alien 


	double widthspaceship = HEIGHT/6.25;					//Longueur de l'image du personnage 
	double heightspaceship = HEIGHT/6.25;					//Hauteur de l'image du personnage 				

	double widthspawnminaliengauche = 0;
	double widthspawnmaxaliengauche = WIDTH/2;				/**Un peu moins que affichageX**/
	double heightspawnminaliengauche = 0;
	double heightspawnmaxaliengauche = HEIGHT*0.8-heightalien;				/**Un peu moins que positionYspaceship**/


	double widthspawnminaliendroite = WIDTH/2;
	double widthspawnmaxaliendroite = WIDTH;				/**Un peu moins que affichageX**/
	double heightspawnminaliendroite = 0;
	double heightspawnmaxaliendroite = HEIGHT*0.8-heightalien;;				/**Un peu moins que positionYspaceship**/

	String pathspaceshipgauche;
	String pathspaceshipdroite;



	double positionXspaceshipgauche = (WIDTH*0.25)-widthspaceship/2;				//Position x du personnage principal
	double positionYspaceshipgauche = HEIGHT-heightspaceship*1.3;			   	//Position en y du personnage principal

	double positionXspaceshipdroite = (WIDTH*0.75)-widthspaceship/2;				//Position x du personnage principal
	double positionYspaceshipdroite = HEIGHT-heightspaceship*1.3;				//Position en y du personnage principal


	String pathplanetegauche;
	double widthplanetegauche = WIDTH/2.3;						 
	double heightplanetegauche = HEIGHT/3;					
	double positionXplanetegauche = WIDTH*0.25-widthplanetegauche/2;				 
	double positionYplanetegauche = HEIGHT-heightplanetegauche;	

	String pathplanetedroite;
	double widthplanetedroite = WIDTH/2.3;						 
	double heightplanetedroite = HEIGHT/3;					
	double positionXplanetedroite = WIDTH*0.75-widthplanetedroite/2;				 
	double positionYplanetedroite = HEIGHT-heightplanetedroite;	



	String pathlaser = "Laser.PNG"; 
	double largeurlaser = 5;
	double tempslaser = 0.3;

	String pathimpact = "Explosion.PNG"; 
	double widthimpact = widthspaceship*0.6;
	double heightimpact = heightspaceship*0.6;



	String[] possibleGauche = {"A","E","R", "Z","T","Q","S","D","F","G"};
	String[] possibleDroite = {"Y","U","P","O","I","M","H","J","K","L"};
	String[] rien = {""};

	Button LeaveBt = new Button("Leave");

	Group root = new Group();
	Scene scene = new Scene(root);
	Canvas canvas = new Canvas(WIDTH, HEIGHT);


	GraphicsContext gc = canvas.getGraphicsContext2D();


	Image impact = new Image(pathimpact, widthimpact,heightimpact , false, false);

	Alien[] aliensgauche;
	Alien[] aliensdroite;

	KeyEvent[] claviergauche; 
	KeyEvent[] clavierdroite;


	Image spacegauche;
	Image spacedroite;
	Alien spaceshipgauche;
	Alien spaceshipdroite;
	Alien planetegauche;
	Alien planetedroite;

	public void init() {
		LeaveBt.setTranslateX(Stock.wid/2-Stock.widbouton/2);
		LeaveBt.setTranslateY(Stock.hei/2+Stock.heitext/2);
		LeaveBt.setMinHeight(Stock.heibouton);
		LeaveBt.setMinWidth(Stock.widbouton);

		root.getChildren().add(canvas);

		gc.setFont(Font.font("Helvetica", FontWeight.BOLD, HEIGHT/24));
		gc.setFill(Color.BISQUE);
		gc.setStroke(Color.RED);
		gc.setLineWidth(1);

		/** Interpretation lorsqu'on tape sur le clavier **/

		scene.setOnKeyPressed(new EventHandler<>() {
			public void handle(KeyEvent e) { 
				int cote = 2;
				int error=1;

				for(int i=0; i<possibleGauche.length; i++) {
					if (e.getCode().getName() == possibleGauche[i]) {
						cote = 0;
					}
					else if (e.getCode().getName() == possibleDroite[i]) {
						cote = 1;
					}
				}

				if ((cote == 0) & (lifeg != 0)) {
					for(int j=0; j<maxcaractere -1; j++) {
						claviergauche[j]= claviergauche[j+1];
					}
					claviergauche[maxcaractere - 1]=e;

					for(int i=0; i<NBALIENS; i++) {	
						if (aliensgauche[i].getvivant() != 2) {

							aliensgauche[i].valideTouch(claviergauche) ;
							if (aliensgauche[i].getvivant() == 0) {
								for (int j =0; j<maxcaractere;j++) {
									claviergauche[j]=null;
								}
								error=0;
							}

							else if (aliensgauche[i].debutCaractere(claviergauche) == 1){
								error=0;
							}

						}
					}		

					if ((difficulte>1)&(error == 1)&((System.nanoTime()/1000000-immunitegauche)>dureeImmunite*1000)) {
						immunitegauche= System.nanoTime()/1000000;
						lifeg = lifeg - 1;				
						for (int j =0; j<maxcaractere;j++) {
							claviergauche[j]=null;
						}
					}


				}

				else if ((cote == 1) & (lifed != 0)) {
					for(int j=0; j<maxcaractere -1; j++) {
						clavierdroite[j]= clavierdroite[j+1];
					}
					clavierdroite[maxcaractere - 1]=e;

					for(int i=0; i<NBALIENS; i++) {	

						if (aliensdroite[i].getvivant() != 2) {

							aliensdroite[i].valideTouch(clavierdroite) ;	
							if (aliensdroite[i].getvivant() == 0) {
								for (int j =0; j<maxcaractere;j++) {
									clavierdroite[j]=null;
								}
								error=0;
							}
							else if (aliensdroite[i].debutCaractere(clavierdroite) == 1){
								error=0;
							}
						}	
					}

					if ((difficulte>1)&(error == 1)&((System.nanoTime()/1000000-immunitedroite)>dureeImmunite*1000)) {
						immunitedroite= System.nanoTime()/1000000;
						lifed = lifed - 1;				
						for (int j =0; j<maxcaractere;j++) {
							claviergauche[j]=null;
						}
					}
				}

			}
		});


	}





	public void start() {

		Iterator<Compte> it=comptes.iterator(); 
		while(it.hasNext()) {
			Compte avatar = it.next();
			if (avatar.id()==Stock.choixId) {
				spacegauche = new Image(avatar.universe(), WIDTH/2, HEIGHT, false, false);
				spaceshipgauche = new Alien(avatar.fighter() , widthspaceship, heightspaceship, 1, 1,rien);
				spaceshipgauche.setPosition(positionXspaceshipgauche , positionYspaceshipgauche);
				planetegauche = new Alien(avatar.home() , widthplanetegauche, heightplanetegauche , 1, 1,rien);
				planetegauche.setPosition(positionXplanetegauche, positionYplanetegauche);
				pathaliengauche=avatar.ennemies();
				idg=avatar.id();
			}
			else if (avatar.id()==Stock.choixIdAdv) {
				spacedroite = new Image(avatar.universe(), WIDTH/2, HEIGHT, false, false);
				spaceshipdroite = new Alien(avatar.fighter(), widthspaceship, heightspaceship, 1, 1,rien);
				spaceshipdroite.setPosition(positionXspaceshipdroite , positionYspaceshipdroite);
				planetedroite = new Alien(avatar.home() , widthplanetedroite, heightplanetedroite , 1, 1,rien);
				planetedroite.setPosition(positionXplanetedroite, positionYplanetedroite);
				pathaliendroite=avatar.ennemies();
				idd=avatar.id();
			}
		}

		time=0;
		scoreg=0;
		scored=0;

		if (root.getChildren().contains(LeaveBt)) {
			root.getChildren().remove(LeaveBt);
		}

		difficulte=Stock.choixDifficulte;

		long startTime = System.nanoTime();


		/**Initialisation des parametres utiles pour la vie (hors difficulte easy)**/ 

		if (difficulte>1) {
			lifeg=nbvie;
			lifed=nbvie;
			immunitegauche = (startTime/1000 -dureeImmunite*1000000)/1000;
			immunitedroite = (startTime/1000 -dureeImmunite*1000000)/1000;
		}
		else {
			lifeg=-1;
			lifed=-1;
		}


		/** Initialisation du tableau d'aliens **/

		aliensgauche = new Alien[NBALIENS];
		aliensdroite = new Alien[NBALIENS];

		Alien alieng = new Alien(pathaliengauche , widthalien , heightalien , mincaractere , maxcaractere , possibleGauche);
		alieng.setPosition((widthspawnmaxaliengauche - widthspawnminaliengauche - widthalien) * Math.random()  + widthspawnminaliengauche , heightspawnminaliengauche);
		aliensgauche[0] = alieng;

		Alien aliend = new Alien(pathaliendroite , widthalien , heightalien , mincaractere , maxcaractere , possibleDroite);
		aliend.setPosition((widthspawnmaxaliendroite - widthspawnminaliendroite - widthalien) * Math.random()  + widthspawnminaliendroite , heightspawnminaliendroite);
		aliensdroite[0] = aliend;

		for(int i=1; i<NBALIENS; i++) {
			Alien nul = new Alien("" , 0 , 0 , 0 , 0 , rien);
			aliensgauche[i]= nul;
			aliensdroite[i]= nul;
		}


		/** Initialisation du tableau qui stock les touches du clavier **/

		claviergauche = new KeyEvent [maxcaractere]; 
		clavierdroite = new KeyEvent [maxcaractere];

		for (int i =0; i<maxcaractere;i++) {
			claviergauche[i]=null;
			clavierdroite[i]=null;
		}

		/** Initialisation des collections d'Aliens mort et de lasers pour les afficher apres les avoir tué **/ 

		Collection<Alien> aliensmorts = new ArrayList<>(); 
		Collection<Alien> lasers = new ArrayList<>(); 


		new AnimationTimer() {          

			public void handle(long arg0) {

				if (time < tempsPartie*1000) {

					/** Afficher le fond d'ecran **/

					gc.drawImage(spacegauche, 0, 0);        
					gc.drawImage(spacedroite, WIDTH/2, 0);        


					/** Traitement de chaque alien du tableau de gauche **/

					for(int i=0; i<NBALIENS; i++) {

						int vieuxgauche =aliensgauche[i].alientropvieux( dureeVieAlien);

						if (aliensgauche[i].getY() > heightspawnmaxaliengauche - heightalien ) {
							lifeg=lifeg-1;
							Alien newalien = new Alien(pathaliengauche , widthalien , heightalien , mincaractere , maxcaractere , possibleGauche);
							newalien.setPosition((widthspawnmaxaliengauche - widthspawnminaliengauche - widthalien) * Math.random()  + widthspawnminaliengauche , heightspawnminaliengauche);
							aliensgauche[i] = newalien;

							if (difficulte>2) {
								aliensgauche[i].renderHard(gc,3);
							}
							else {
								aliensgauche[i].render(gc);
							}
						}


						else if ((lifeg!=0)&(vieuxgauche == 1)) { 

							Alien newalien = new Alien(pathaliengauche , widthalien , heightalien , mincaractere , maxcaractere , possibleGauche);
							newalien.setPosition((widthspawnmaxaliengauche - widthspawnminaliengauche - widthalien) * Math.random()  + widthspawnminaliengauche , heightspawnminaliengauche);

							newalien.add(aliensgauche);

							if (difficulte==3) {
								aliensgauche[i].renderHard(gc,3);
							}
							else {
								aliensgauche[i].render(gc);
							}
						}  


						else if ((lifeg!=0)&(aliensgauche[i].getvivant() == 0)) { 
							scoreg ++;

							Alien newalien = new Alien(pathaliengauche , widthalien , heightalien , mincaractere , maxcaractere , possibleGauche);
							newalien.setPosition((widthspawnmaxaliengauche - widthspawnminaliengauche - widthalien) * Math.random()  + widthspawnminaliengauche , heightspawnminaliengauche);
							aliensmorts.add(aliensgauche[i]);			
							lasers.add(aliensgauche[i].laser(pathlaser , aliensgauche[i].getX() + widthalien/2, aliensgauche[i].getY() + heightalien/2, positionXspaceshipgauche + widthspaceship/2, positionYspaceshipgauche + heightspaceship/2, largeurlaser));       

							aliensgauche[i].setTime(System.nanoTime());
							aliensgauche[i] = newalien;


							if (difficulte==3) {
								aliensgauche[i].renderHard(gc,3);
							}
							else {
								aliensgauche[i].render(gc);
							}
						}


						else if (aliensgauche[i].getvivant() == 2) {
						}

						else if ((lifeg==0)&(aliensgauche[i].getvivant() == 1)) {
							aliensgauche[i].render(gc);

						}

						else {
							aliensgauche[i].setPosition(aliensgauche[i].getX() , aliensgauche[i].getY() + vitessealien );

							if (difficulte==3) {
								aliensgauche[i].renderHard(gc,3);
							}
							else {
								aliensgauche[i].render(gc);
							}
						}



						int vieuxdroite =aliensdroite[i].alientropvieux( dureeVieAlien);

						if (aliensdroite[i].getY() > heightspawnmaxaliendroite - heightalien ) {
							lifed = lifed - 1;
							Alien newalien = new Alien(pathaliendroite , widthalien , heightalien , mincaractere , maxcaractere , possibleDroite);
							newalien.setPosition((widthspawnmaxaliendroite - widthspawnminaliendroite - widthalien) * Math.random()  + widthspawnminaliendroite , heightspawnminaliendroite);

							aliensdroite[i] = newalien;

							if (difficulte>2) {
								aliensdroite[i].renderHard(gc,3);
							}
							else {
								aliensdroite[i].render(gc);
							}
						}


						else if ((lifed!=0)&(vieuxdroite == 1)) { 
							Alien newalien = new Alien(pathaliendroite , widthalien , heightalien , mincaractere , maxcaractere , possibleDroite);
							newalien.setPosition((widthspawnmaxaliendroite - widthspawnminaliendroite - widthalien) * Math.random()  + widthspawnminaliendroite , heightspawnminaliendroite);

							newalien.add(aliensdroite);

							if (difficulte==3) {
								aliensdroite[i].renderHard(gc,3);
							}
							else {
								aliensdroite[i].render(gc);
							}
						}  


						else if ((lifed!=0)&(aliensdroite[i].getvivant() == 0)) { 
							scored ++;

							Alien newalien = new Alien(pathaliendroite , widthalien , heightalien , mincaractere , maxcaractere , possibleDroite);
							newalien.setPosition((widthspawnmaxaliendroite - widthspawnminaliendroite - widthalien) * Math.random()  + widthspawnminaliendroite , heightspawnminaliendroite);

							aliensmorts.add(aliensdroite[i]);			
							lasers.add(aliensdroite[i].laser(pathlaser , aliensdroite[i].getX() + widthalien/2, aliensdroite[i].getY() + heightalien/2, positionXspaceshipdroite + widthspaceship/2, positionYspaceshipdroite + heightspaceship/2, largeurlaser));       


							aliensdroite[i].setTime(System.nanoTime());
							aliensdroite[i] = newalien;


							if (difficulte==3) {
								aliensdroite[i].renderHard(gc,3);
							}
							else {
								aliensdroite[i].render(gc);
							}
						}


						else if (aliensdroite[i].getvivant() == 2) {
						}

						else if ((lifed==0)&(aliensdroite[i].getvivant() == 1)) {
							aliensdroite[i].render(gc);

						}

						else if (lifed!=0) {
							aliensdroite[i].setPosition(aliensdroite[i].getX() , aliensdroite[i].getY() + vitessealien );

							if (difficulte==3) {
								aliensdroite[i].renderHard(gc,3);
							}
							else {
								aliensdroite[i].render(gc);
							}
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


					if ((lifed==0)&(lifeg==0)) {
						stop();
						if (!(root.getChildren().contains(LeaveBt))) {
							root.getChildren().add(LeaveBt);}
						int IdWinner=0;
						if (scoreg>scored) {
							IdWinner=idg;
						}
						else if (scored>scoreg) {
							IdWinner=idd;
						}		
						try {
							boolean ligneExisteOrdre = test.verifierExistenceLigneBDD(idg, idd);
							boolean ligneExisteInverse = test.verifierExistenceLigneBDD(idd, idg);
							if (ligneExisteOrdre) {
								if (IdWinner == 0) {
									test.mettreAJourLigneDuelEgalite(idg, idd, Stock.choixDifficulte);
								} else if (IdWinner == idg) {
									test.mettreAJourLigneDuelVictoire(idg, idd, Stock.choixDifficulte);
								} else {
									test.mettreAJourLigneDuelDefaite(idg, idd, Stock.choixDifficulte);
								}
							} else if (ligneExisteInverse) {
								if (IdWinner == 0) {
									test.mettreAJourLigneDuelEgalite(idd, idg, Stock.choixDifficulte);
								} else if (IdWinner == idd) {
									test.mettreAJourLigneDuelVictoire(idd, idg, Stock.choixDifficulte);
								} else {
									test.mettreAJourLigneDuelDefaite(idd, idg, Stock.choixDifficulte);
								}
								
							} else {
								test.insererPartieDuel(idg,idd,IdWinner,Stock.choixDifficulte);
							}
						} catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}


					/**Afficher le personnage principal, le score et le temps**/

					planetegauche.render(gc);
					planetedroite.render(gc);

					spaceshipgauche.render(gc);
					spaceshipdroite.render(gc);

					String txtd = "Score: " + scored;
					String txtg = "Score: " + scoreg;
					time = Math.min((System.nanoTime() - startTime)/1000000,tempsPartie * 1000);
					long decimal = time - 1000*(time/1000);
					String temps = "Time: " + time/1000 +"."+ decimal;
					gc.setTextAlign(TextAlignment.LEFT);
					gc.fillText(txtg, 0, 36,WIDTH/5.12 );
					gc.strokeText(txtg, 0, 36,WIDTH/5.12 ); 
					gc.fillText(txtd, WIDTH-WIDTH/5.12, 36,WIDTH/5.12);
					gc.strokeText(txtd,WIDTH-WIDTH/5.12, 36,WIDTH/5.12 ); 
					gc.fillText(temps, WIDTH-WIDTH/5.12, 70,WIDTH/5.12 );
					gc.strokeText(temps, WIDTH-WIDTH/5.12, 70,WIDTH/5.12);
					gc.fillText(temps, 0, 70,WIDTH/5.12 );
					gc.strokeText(temps, 0, 70,WIDTH/5.12 );


					/** Affcher les vies sauf pour le mode Easy **/

					if (difficulte >1) {
						String vieg = "Vie: " +lifeg;
						String vied = "Vie: " +lifed;
						gc.fillText(vied, WIDTH-WIDTH/5.12, 105 ,WIDTH/5.12);
						gc.strokeText(vied, WIDTH-WIDTH/5.12, 105,WIDTH/5.12 );
						gc.fillText(vieg, 0, 105 );
						gc.strokeText(vieg, 0, 105 );						
					}	
				}
				if (time >= tempsPartie*1000){
					stop();
					if (!(root.getChildren().contains(LeaveBt))) {
						root.getChildren().add(LeaveBt);}

					int IdWinner=0;
					if (scoreg>scored) {
						IdWinner=idg;
					}
					else if (scored>scoreg) {
						IdWinner=idd;
					}		
					try {
						boolean ligneExisteOrdre = test.verifierExistenceLigneBDD(idg, idd);
						boolean ligneExisteInverse = test.verifierExistenceLigneBDD(idd, idg);
						if (ligneExisteOrdre) {
							if (IdWinner == 0) {
								test.mettreAJourLigneDuelEgalite(idg, idd, Stock.choixDifficulte);
							} else if (IdWinner == idg) {
								test.mettreAJourLigneDuelVictoire(idg, idd, Stock.choixDifficulte);
							} else {
								test.mettreAJourLigneDuelDefaite(idg, idd, Stock.choixDifficulte);
							}
						} else if (ligneExisteInverse) {
							if (IdWinner == 0) {
								test.mettreAJourLigneDuelEgalite(idd, idg, Stock.choixDifficulte);
							} else if (IdWinner == idd) {
								test.mettreAJourLigneDuelVictoire(idd, idg, Stock.choixDifficulte);
							} else {
								test.mettreAJourLigneDuelDefaite(idd, idg, Stock.choixDifficulte);
							}
							
						} else {
							test.insererPartieDuel(idg,idd,IdWinner,Stock.choixDifficulte);
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}.start();  
	}
}



