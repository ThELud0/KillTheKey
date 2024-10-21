/**
 * 
 * auteur Jun, modifié par Ludo
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

public class MenuDifficulte {
	/***** CONSTANTES *****/

	double width = Stock.wid;
	double height = Stock.hei;
	double widButton1 = width/2 - 15 ;
	double heiButton1 = height/2 - 47.5;

	Group group = new Group();
	Scene scene = new Scene(group);


	Canvas canvas = new Canvas(width, height);

	Image fond = new Image("space.jpg", width, height, false, false);
	GraphicsContext gc = canvas.getGraphicsContext2D();
	

	/****************************
	 ****CREATION 4 BOUTONS******
	 ***************************/

	/*** Apparence des boutons ***/

	/** Bouton Facile **/
	Image fondFacile = new Image("AvatarYoda.jpg",widButton1,heiButton1,false,false);
	BackgroundImage fondFacileB = new BackgroundImage(fondFacile,null,null,null,null);
	Background fondFacileB2 = new Background(fondFacileB);

	/** Bouton Intermediaire **/
	Image fondIntermediaire = new Image("AvatarSpock.jpg",widButton1,heiButton1,false,false);
	BackgroundImage fondIntermediaireB = new BackgroundImage(fondIntermediaire,null,null,null,null);
	Background fondIntermediaireB2 = new Background(fondIntermediaireB);

	/** Bouton Difficile **/
	Image fondDifficile = new Image("AvatarAlien.jpg",widButton1,heiButton1,false,false);
	BackgroundImage fondDifficileB = new BackgroundImage(fondDifficile,null,null,null,null);
	Background fondDifficileB2 = new Background(fondDifficileB);

	/** Bouton Impossible **/
	Image fondImpossible = new Image("AvatarGoku.jpg",widButton1,heiButton1,false,false);
	BackgroundImage fondImpossibleB = new BackgroundImage(fondImpossible,null,null,null,null);
	Background fondImpossibleB2 = new Background(fondImpossibleB);

	Image fondImpossibleNo = new Image("fondVerrou.jpg",widButton1,heiButton1,false,false);
	BackgroundImage fondImpossibleNoB = new BackgroundImage(fondImpossibleNo,null,null,null,null);
	Background fondImpossibleNoB2 = new Background(fondImpossibleNoB);

	BorderWidths epaisseurcontour = new BorderWidths(5);
	BorderStroke contour = new BorderStroke(Color.GREY,BorderStrokeStyle.SOLID,null,epaisseurcontour);
	Border contourBt = new Border(contour);

	/***** TITRE *****/
	String titre;


	/*** Creation Bouton Retour ***/
	Button BtRetour = new Button();


	/*** Creation Bouton Facile ***/
	Button facileBt = new Button("LAME");
	

	/*** Creation Bouton Intermediaire ***/
	Button intermediaireBt = new Button("OK");
	

	/*** Creation Bouton Difficile ***/
	Button difficileBt = new Button("NOT BAD");
	

	/*** Creation Bouton Impossible ***/
	Button impossibleBt = new Button();
	
	
public void init() {
	gc.drawImage(fond, 0, 0);
	group.getChildren().add(canvas);
	gc.setFont(Font.font("Helvetica", FontWeight.BOLD, 24));
	gc.setStroke(Color.RED);
	gc.setLineWidth(2);
	gc.strokeText( titre, 250,40);
	
	Stock.configBtRetour(BtRetour);
	
	facileBt.setTranslateX(10);
	facileBt.setTranslateY(Stock.widButtonR + 10);
	facileBt.setMinHeight(heiButton1);
	facileBt.setMinWidth(widButton1);
	facileBt.setFont(Font.font("Helvetica",FontWeight.BOLD,40));
	facileBt.setTextFill(Color.RED);
	facileBt.setBorder(contourBt);
	facileBt.setBackground(fondFacileB2);

	intermediaireBt.setTranslateX((width-5)/2 +10);
	intermediaireBt.setTranslateY(Stock.widButtonR +10);
	intermediaireBt.setMinHeight(heiButton1);
	intermediaireBt.setMinWidth(widButton1);
	intermediaireBt.setFont(Font.font("Helvetica",FontWeight.BOLD,40));
	intermediaireBt.setTextFill(Color.RED);
	intermediaireBt.setBorder(contourBt);
	intermediaireBt.setBackground(fondIntermediaireB2);
	
	difficileBt.setTranslateX(10);
	difficileBt.setTranslateY((height)/2 + Stock.widButtonR-30);
	difficileBt.setMinHeight(heiButton1);
	difficileBt.setMinWidth(widButton1);
	difficileBt.setFont(Font.font("Helvetica",FontWeight.BOLD,40));
	difficileBt.setTextFill(Color.RED);
	difficileBt.setBorder(contourBt);
	difficileBt.setBackground(fondDifficileB2);
	
	impossibleBt.setTranslateX((width-5)/2 +10);
	impossibleBt.setTranslateY(height/2 + Stock.widButtonR-30);
	impossibleBt.setMinHeight(heiButton1);
	impossibleBt.setMinWidth(widButton1);
	impossibleBt.setFont(Font.font("Helvetica",FontWeight.BOLD,40));
	impossibleBt.setTextFill(Color.RED);
	impossibleBt.setBorder(contourBt);
	
	group.getChildren().addAll(impossibleBt,difficileBt,intermediaireBt,facileBt, BtRetour);
	
}
	
}
