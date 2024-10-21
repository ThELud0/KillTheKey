/**
 * 
 * author Ludovic
 * 
 * 
 */

package pro3600.application;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.paint.Color;

/**********
 * 
 * @author ludo
 *
 *
 *
 *On va sauvegarder ici l'état de certaines variables que l'on veut pouvoir modifier et réutiliser dans plusieurs parties du programme
 *
 */



public class Stock {

	/**variables de tailles*/
	public static int wid = 1333; //attention, changer manuellement les tailles dans le fichier .css!!
	public static int hei = 1000;
	public static int widbouton = wid/5;
	public static int heibouton = hei/15;
	public static int widbouton2 = wid/4;
	public static int heibouton2 = hei/7;
	public static int widtext = widbouton;
	public static int heitext = heibouton/2;
	public static int widButtonR = 60;
	public static int heiButtonR = 55;
	/**image de fond du jeu*/
	public static Image fond = new Image("space.jpg",Stock.wid,Stock.hei,false,false);//on charge une img de fond


	/**Apparence des boutons*/
	public static Image fond2 = new Image("UniversDore.jpeg",Stock.widbouton,Stock.heibouton,false,false);
	public static BackgroundImage fdBt = new BackgroundImage(fond2,null,null,null,null);
	public static Background fdBt2 = new Background(fdBt);
	
	
	public static BorderWidths epaisseurcontour = new BorderWidths(5);
	public static BorderStroke contour = new BorderStroke(Color.GREY,BorderStrokeStyle.SOLID,null,epaisseurcontour);
	public static BorderStroke contour2 = new BorderStroke(Color.GREY,BorderStrokeStyle.SOLID,null,epaisseurcontour);
	public static Border contourBt = new Border(contour);
	public static Border contourBt2 = new Border(contour2);
	
	/*** Bouton retour ***/
	public static Image fondBtRetour = new Image("FlecheRetour.jpg",widButtonR,heiButtonR,false,false);
	public static BackgroundImage fondBtRetourB = new BackgroundImage(fondBtRetour,null,null,null,null);
	public static Background fondBtRetourB2 = new Background(fondBtRetourB);

	/**Etat du bouton 'Confirmer' : 0 pour créer un compte et 1 pour en supprimer un*/
	public static int confirmation;

	/**Ids choisis dans le menu SELECTION*/
	public static int choixId;
	public static int choixIdAdv;

	/**un profil a été choisi ou non*/
	public static boolean aChoisi;
	public static boolean aChoisiAdv;

	/**Mode de jeu : 0 pour SOLO et 1 pour DUEL*/
	public static int choixMode;

	public static int choixDifficulte;
	
	public static void configBtRetour(Button button) {
		button.setTranslateX(10);
		button.setTranslateY(10);
		button.setMinHeight(heiButtonR);
		button.setMinWidth(widButtonR);
		button.setBackground(fondBtRetourB2);
	}

}
