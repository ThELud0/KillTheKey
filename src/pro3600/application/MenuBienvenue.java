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

public class MenuBienvenue {

	public MenuBienvenue() {}

	Group grBienvenue = new Group(); //on crée un groupe
	Scene scBienvenue = new Scene(grBienvenue); //on associe une scene à ce groupe
	Canvas cvBienvenue = new Canvas(Stock.wid,Stock.hei); //pour afficher un dessin
	GraphicsContext gcBienvenue = cvBienvenue.getGraphicsContext2D(); //on extrait un gc pour dessiner un contenu	


	/***************message de bienvenue****************/
	String bienvenue = "WELCOME TO KILL THE KEY";
	String message = "SELECT A MODE";
	Button soloBt = new Button("SOLO");
	Button duelBt = new Button("DUEL");

	/**Placement initial du texte ainsi que des boutons SOLO et DUEL */
	public void configElmt() {
		gcBienvenue.drawImage(Stock.fond,0,0);
		
		gcBienvenue.setFont(Font.font("Helvetica", FontWeight.BOLD, Stock.wid/24));
		gcBienvenue.setStroke(Color.RED);
		gcBienvenue.setLineWidth(2);
		gcBienvenue.setTextBaseline(VPos.CENTER);
		gcBienvenue.setTextAlign(TextAlignment.CENTER);
		gcBienvenue.strokeText(bienvenue, Stock.wid/2, Stock.hei/4,Stock.wid/1.5);
		gcBienvenue.strokeText(message, Stock.wid/2, Stock.hei/3,Stock.wid/2);

		soloBt.setTranslateX(Stock.wid/2-Stock.widbouton2/2);
		soloBt.setTranslateY(Stock.hei/2);
		soloBt.setMinHeight(Stock.heibouton2);
		soloBt.setMinWidth(Stock.widbouton2);

		duelBt.setTranslateX(Stock.wid/2-Stock.widbouton2/2);
		duelBt.setTranslateY(Stock.hei/2+Stock.heibouton2*1.1);
		duelBt.setMinHeight(Stock.heibouton2);
		duelBt.setMinWidth(Stock.widbouton2);

		/**Ajout des éléments au groupe*/
		grBienvenue.getChildren().addAll(cvBienvenue,duelBt,soloBt);
		
	}

}
