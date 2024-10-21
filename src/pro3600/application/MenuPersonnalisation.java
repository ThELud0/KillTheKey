/**
 * 
 * 
 * author Jun, modifié par Ludovic
 * 
 * 
 */

package pro3600.application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.scene.control.Button;

import javafx.scene.text.FontWeight;

public class MenuPersonnalisation {
	/***** CONSTANTES *****/

	double width = Stock.wid;
	double height = Stock.hei;
	double widButton1 = width/2 - 15 ;
	double heiButton1 = height/2 - 47.5;




	/***** CREATION SCENE 1 *****/

	Menu_Personalisation_Fighter menuFighter = new Menu_Personalisation_Fighter();
	Menu_Personalisation_Home menuHome = new Menu_Personalisation_Home();
	Menu_Personalisation_Universe menuUniverse = new Menu_Personalisation_Universe();
	Menu_Personalisation_Ennemies menuEnnemies = new Menu_Personalisation_Ennemies();

	Group group = new Group();
	Scene scene = new Scene(group);
	Canvas canvas = new Canvas(width, height);
	Image fond = new Image("space.jpg", width, height, false, false);
	GraphicsContext gc = canvas.getGraphicsContext2D();

	/****************************
	 ****CREATION 4 BOUTONS******
	 ***************************/
	
	/*** Apparence des boutons ***/

	/** Bouton Personnage **/
	Image fondPerso = new Image("fondAlien.jpg",widButton1,heiButton1,false,false);
	BackgroundImage fondPersoB = new BackgroundImage(fondPerso,null,null,null,null);
	Background fondPersoB2 = new Background(fondPersoB);

	/** Bouton Planete **/
	Image fondPlanete = new Image("fondPlanete.jpg",widButton1,heiButton1,false,false);
	BackgroundImage fondPlaneteB = new BackgroundImage(fondPlanete,null,null,null,null);
	Background fondPlaneteB2 = new Background(fondPlaneteB);

	/** Bouton Univers **/
	Image fondUnivers = new Image("fondUnivers.jpg",widButton1,heiButton1,false,false);
	BackgroundImage fondUniversB = new BackgroundImage(fondUnivers,null,null,null,null);
	Background fondUniversB2 = new Background(fondUniversB);

	/** Bouton Vaisseaux **/
	Image fondVaisseaux = new Image("fondVaisseaux.jpg",widButton1,heiButton1,false,false);
	BackgroundImage fondVaisseauxB = new BackgroundImage(fondVaisseaux,null,null,null,null);
	Background fondVaisseauxB2 = new Background(fondVaisseauxB);


	BorderWidths epaisseurcontour = new BorderWidths(5);
	BorderStroke contour = new BorderStroke(Color.GREY,BorderStrokeStyle.SOLID,null,epaisseurcontour);
	Border contourBt = new Border(contour);



	/*** Creation Bouton Retour ***/
	Button BtRetour = new Button();
	
	/*** Creation Bouton Personnage ***/
	Button fighterBt = new Button("FIGHTER");
	
	/*** Creation Bouton Planete ***/
	Button homeBt = new Button("HOME");
	
	/*** Creation Bouton Univers ***/
	Button univBt = new Button("UNIVERSE");
	
	/*** Creation Bouton Ennemies ***/
	Button ennemiesBt = new Button("ENNEMIES");
	
	
	
	public void start() {


		group.getChildren().add(canvas);

		gc.drawImage(fond, 0, 0);

		Stock.configBtRetour(BtRetour);

		fighterBt.setTranslateX(10);
		fighterBt.setTranslateY(Stock.widButtonR + 10);
		fighterBt.setMinHeight(heiButton1);
		fighterBt.setMinWidth(widButton1);
		fighterBt.setFont(Font.font("Helvetica",FontWeight.BOLD,40));
		fighterBt.setTextFill(Color.RED);
		fighterBt.setBorder(contourBt);
		fighterBt.setBackground(fondPersoB2);


		homeBt.setTranslateX((width-5)/2 +10);
		homeBt.setTranslateY(Stock.widButtonR +10);
		homeBt.setMinHeight(heiButton1);
		homeBt.setMinWidth(widButton1);
		homeBt.setFont(Font.font("Helvetica",FontWeight.BOLD,40));
		homeBt.setTextFill(Color.RED);
		homeBt.setBorder(contourBt);
		homeBt.setBackground(fondPlaneteB2);


		univBt.setTranslateX(10);
		univBt.setTranslateY((height)/2 + Stock.widButtonR-30);
		univBt.setMinHeight(heiButton1);
		univBt.setMinWidth(widButton1);
		univBt.setFont(Font.font("Helvetica",FontWeight.BOLD,40));
		univBt.setTextFill(Color.RED);
		univBt.setBorder(contourBt);
		univBt.setBackground(fondUniversB2);


		ennemiesBt.setTranslateX((width-5)/2 +10);
		ennemiesBt.setTranslateY(height/2 + Stock.widButtonR-30);
		ennemiesBt.setMinHeight(heiButton1);
		ennemiesBt.setMinWidth(widButton1);
		ennemiesBt.setFont(Font.font("Helvetica",FontWeight.BOLD,40));
		ennemiesBt.setTextFill(Color.RED);
		ennemiesBt.setBorder(contourBt);
		ennemiesBt.setBackground(fondVaisseauxB2);

		/*** Ajout des éléments au groupe ***/
		group.getChildren().addAll(ennemiesBt,univBt,homeBt,fighterBt, BtRetour);

		menuFighter.start();
		
		menuHome.start();
	
		menuUniverse.start();
		
		menuEnnemies.start();
		



		/*** action du bouton OK ***/
		menuFighter.BtOK.setOnAction(menuFighter.clickOK);
		menuHome.BtOK.setOnAction(menuHome.clickOK);
		menuUniverse.BtOK.setOnAction(menuUniverse.clickOK);
		menuEnnemies.BtOK.setOnAction(menuEnnemies.clickOK);

		/*** action du bouton Droit ***/
		menuFighter.BtDroit.setOnAction(menuFighter.clickDroit);
		menuHome.BtDroit.setOnAction(menuHome.clickDroit);
		menuUniverse.BtDroit.setOnAction(menuUniverse.clickDroit);
		menuEnnemies.BtDroit.setOnAction(menuEnnemies.clickDroit);

		/*** action du bouton Gauche ***/
		menuFighter.BtGauche.setOnAction(menuFighter.clickGauche);
		menuHome.BtGauche.setOnAction(menuHome.clickGauche);
		menuUniverse.BtGauche.setOnAction(menuUniverse.clickGauche);
		menuEnnemies.BtGauche.setOnAction(menuEnnemies.clickGauche);

	}

}
