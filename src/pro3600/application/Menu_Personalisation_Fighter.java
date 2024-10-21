/**
 * 
 * author Jun
 * 
 * 
 */



package pro3600.application;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
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
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class Menu_Personalisation_Fighter {
	
	/***********************base de données******************************/

	SqlBase test ;

	/**on choisit à quelle base se connecter*/
	public void setDB(SqlBase dtb) {
		test = dtb;	
	}
	
	/***** CONSTANTES *****/
	double width = Stock.wid;
	double height = Stock.hei;
	double widButton = 80;
	double heiButton = 75;
	double widButtonOK = 200;
	double heiButtonOK = 80;
	double widImage = width/2;
	double heiImage = width/2;
	
	Group group = new Group();
	Scene scene = new Scene(group);
	Canvas canvas = new Canvas(width, height);
	Image fond = Stock.fond;
	GraphicsContext gc = canvas.getGraphicsContext2D();
	
	/***** LISTE DES AVATARS *****/
	ArrayList <String> liste = new ArrayList<>();  // Creation d'une liste 
	int indiceImage = 0;
	
	/***** "ZONE" IMAGE *****/
	String image = "AvatarAlien.png";
	Image fondImage = new Image(image,widImage,heiImage,false,false);
	BackgroundImage fondImageB = new BackgroundImage(fondImage,null,null,null,null);
	Background fondImageB2 = new Background(fondImageB);

	Button zoneImage = new Button();
	

	/****** BOUTONS *****/

	/*** Bouton sélection droit ***/
	Image fondBtDroit = new Image("FlecheDroite.jpg",widButton,heiButton,false,false);
	BackgroundImage fondBtDroitB = new BackgroundImage(fondBtDroit,null,null,null,null);
	Background fondBtDroitB2 = new Background(fondBtDroitB);

	Button BtDroit = new Button();

	/*** Bouton sélection gauche ***/
	Image fondBtGauche = new Image("FlecheGauche.jpg",widButton,heiButton,false,false);
	BackgroundImage fondBtGaucheB = new BackgroundImage(fondBtGauche,null,null,null,null);
	Background fondBtGaucheB2 = new Background(fondBtGaucheB);

	Button BtGauche = new Button();
	
	/*** Bouton retour ***/

	Button BtRetour = new Button();
	
	/*** Bouton OK ***/
	Image fondBtOK = new Image("fondNoir.jpeg",widButtonOK,heiButtonOK,false,false);
	BackgroundImage fondBtOKB = new BackgroundImage(fondBtOK,null,null,null,null);
	Background fondBtOKB2 = new Background(fondBtOKB);
	BorderWidths epaisseurcontour = new BorderWidths(5);
	BorderStroke contour = new BorderStroke(Color.RED,BorderStrokeStyle.SOLID,null,epaisseurcontour);
	Border contourBt = new Border(contour);
	PauseTransition pause = new PauseTransition(Duration.seconds(0.1));
	Button BtOK = new Button("OK");
	

	/***** TITRE *****/
	String titre = "CHOOSE YOUR FIGHTER";

	Collection <Compte> comptes;
	public void setComptes(Collection<Compte> comptes) {
		this.comptes=comptes;
	}
	
	/***** CONSTRUCTEUR *****/ 
	public Menu_Personalisation_Fighter() {}
	
	
	/********** ACTIONS **********/
	
	 /*** Action du Bouton OK : modifie la variable "image-compte" correspondant à l'élément modifié de la classe compte ?? ***/
	 EventHandler<ActionEvent> clickOK = new EventHandler<>() {
		 public void handle(ActionEvent e) {
			 test.changeimage("fighter", image, Stock.choixId);
			 try {
					test.obeitmoi();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			 Iterator<Compte> it=comptes.iterator(); 
				while(it.hasNext()) {
					Compte avatar = it.next();
					if (avatar.id()==Stock.choixId) {
						avatar.changeFighter(image);
						avatar.boutonUpdate();
					}
				}
				
				BtOK.setMinHeight(heiButtonOK*0.95);
				BtOK.setMinWidth(widButtonOK*0.95);
				BtOK.setTranslateX(width/2 - widButtonOK*0.95/2);
				BtOK.setTranslateY(height-heiButtonOK*0.95-40);

				pause.play();
				
			 
		 }
	 };
	 
	 
	 /*** Action des boutons de sélection Droit et Gauche ***/ //parcourent la liste des images
	 EventHandler<ActionEvent> clickDroit= new EventHandler<>() {
		 public void handle(ActionEvent e) {
			 if (indiceImage<liste.size()-1) {
				indiceImage += 1;
			 }
			 else {
				 indiceImage=0;
			 }
			 image = liste.get(indiceImage);
		     fondImage = new Image(image,widImage,heiImage,false,false);
		     fondImageB = new BackgroundImage(fondImage,null,null,null,null); 


	         fondImageB2 = new Background(fondImageB);
			 zoneImage.setBackground(fondImageB2);
			// System.out.println(indiceImage);
			// System.out.println(image);
		 }
	 };
			 
	 EventHandler<ActionEvent> clickGauche= new EventHandler<>() {
		 public void handle(ActionEvent e) {
			 if (indiceImage<=0) {
				indiceImage = liste.size()-1;
			 }
			 else {
				indiceImage -= 1; 
			 }
			 image = liste.get(indiceImage);
			 fondImage = new Image(image,widImage,heiImage,false,false);
		     fondImageB = new BackgroundImage(fondImage,null,null,null,null);
	         fondImageB2 = new Background(fondImageB);
			 zoneImage.setBackground(fondImageB2);
			 //System.out.println(indiceImage);
			 //System.out.println(image);
		 }
	 };
			 

	/***** INITIALISATION *****/


	public void start() {


		group.getChildren().add(canvas);
		gc.drawImage(fond, 0, 0);
		
		/*** LISTE ***/ 
		liste.add("AvatarAlien.png");
		liste.add("AvatarCafard.png");
		liste.add("AvatarET.png");
		liste.add("AvatarGoku.png");
		liste.add("AvatarSaiki.png");
		liste.add("AvatarSpock.png");
		liste.add("AvatarYoda.png");

		/*** IMAGE ***/
		zoneImage.setTranslateX(width/2 - widImage/2);
		zoneImage.setTranslateY(height/2 - heiImage/2 - 40);
		zoneImage.setMinHeight(heiImage);
		zoneImage.setMinWidth(widImage);
		zoneImage.setBackground(fondImageB2);
		

		/*** BOUTONS ***/
		BtDroit.setTranslateX(width-widButton-40);
		BtDroit.setTranslateY(height/2 - heiButton/2);
		BtDroit.setMinHeight(heiButton);
		BtDroit.setMinWidth(widButton);
		BtDroit.setBackground(fondBtDroitB2);


		BtGauche.setTranslateX(40);
		BtGauche.setTranslateY(height/2 - heiButton/2);
		BtGauche.setMinHeight(heiButton);
		BtGauche.setMinWidth(widButton);
		BtGauche.setBackground(fondBtGaucheB2);

		
		Stock.configBtRetour(BtRetour);
		
		
		BtOK.setTranslateX(width/2 - widButtonOK/2);
		BtOK.setTranslateY(height-heiButtonOK-40);
		BtOK.setMinHeight(heiButtonOK);
		BtOK.setMinWidth(widButtonOK);
		BtOK.setBackground(fondBtOKB2);
		BtOK.setBorder(contourBt);
		BtOK.setFont(Font.font("Helvetica", FontWeight.BOLD,40));
		BtOK.setTextFill(Color.RED);
		
		
		/*** TITRE ***/
		gc.setFont(Font.font("Helvetica", FontWeight.BOLD, width/24));
		gc.setStroke(Color.RED);
		gc.setLineWidth(2);
		gc.setTextBaseline(VPos.CENTER);
		gc.setTextAlign(TextAlignment.CENTER);
		gc.strokeText(titre, width/2,40,width/2);

		/*** Ajout des éléments au groupe ***/
		group.getChildren().addAll(BtDroit, BtGauche, BtRetour, BtOK, zoneImage); 
	

		pause.setOnFinished(event -> {
			System.out.println("2");
			BtOK.setTranslateX(width/2 - widButtonOK/2);
			BtOK.setTranslateY(height-heiButtonOK-40);
			BtOK.setMinHeight(heiButtonOK);
			BtOK.setMinWidth(widButtonOK);
		});






	}
}

