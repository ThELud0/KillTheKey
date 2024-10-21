/**
 * 
 * author Ludovic
 * 
 * 
 */

package pro3600.application;



import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class MenuAccueil {

	public MenuAccueil() {}

	/****************CREATION DE LA SCENE ACCUEIL****************/
	Group grAcc = new Group(); //on crée un groupe
	Scene scAcc = new Scene(grAcc); //on associe une scene à ce groupe


	/****************APPARENCE DE LA SCENE*************/

	Canvas cvAcc = new Canvas(Stock.wid,Stock.hei); //pour afficher un dessin
	GraphicsContext gcAcc = cvAcc.getGraphicsContext2D(); //on extrait un gc pour dessiner un contenu


	/****************ZONES DE TEXTE**************/
	//message ready
	String rdy = "READY TO KILL THE KEY ???";
	String affid;
	/*****************CREATION DES BOUTONS*************/
	//bouton JOUER
	Button jouerBt = new Button("PLAY");

	//bouton RECORDS
	Button recBt = new Button("HIGHSCORES");

	//bouton PERSONNALISATION
	Button customBt = new Button("CUSTOM");

	//bouton RETOUR
	Button retourBt = new Button();

	/**Placement de tous les éléments du menu Accueil*/
	public void configElmt() {



		//affichage id choisi
		gcAcc.setFont(Font.font("Helvetica", FontWeight.BOLD, Stock.wid/24));
		gcAcc.setStroke(Color.AQUAMARINE);
		gcAcc.setLineWidth(1);
		gcAcc.setTextBaseline(VPos.CENTER);
		gcAcc.setTextAlign(TextAlignment.CENTER);

		jouerBt.setTranslateX(Stock.wid/2-Stock.widbouton/1.5);
		jouerBt.setTranslateY(Stock.hei/2.5);
		jouerBt.setMinHeight(Stock.heibouton2);
		jouerBt.setMinWidth(Stock.widbouton2);


		recBt.setTranslateX(Stock.wid/4);
		recBt.setTranslateY(Stock.hei-5*Stock.heibouton);
		recBt.setMinHeight(Stock.heibouton);
		recBt.setMinWidth(Stock.widbouton);

		customBt.setTranslateX(Stock.wid/1.75);
		customBt.setTranslateY(Stock.hei-5*Stock.heibouton);
		customBt.setMinHeight(Stock.heibouton);
		customBt.setMinWidth(Stock.widbouton);

		Stock.configBtRetour(retourBt);

		grAcc.getChildren().addAll(cvAcc,jouerBt,retourBt);
	}





















}
