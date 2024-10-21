/**
 * 
 * author Ludovic, faisant appels à des classes créées par Mai Lan, Jun et Rayane
 * 
 * 
 */

package pro3600.application;

import java.util.Iterator;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {

	public static void main(String[] args)  {



		launch(args); //lance un stage définit dans start



	}

	/**Change l'apparence d'un bouton et donne la taille 'pol' à sa police d'écriture ett un contour 'bord'*/
	public void changeBtApp(int pol, Border bord,Button... bouton) {
		for (Button bt:bouton) {
			bt.setFont(Font.font("Helvetica",FontWeight.BOLD,pol));
			bt.setTextFill(Color.RED);
			bt.setBorder(bord);
			bt.setBackground(Stock.fdBt2);
		}
	}

	public void start(Stage stage) {

		try {
			/*********************CONNECTION BASE DE DONNEES*******************/


			SqlBase base;
			base = new SqlBase("root","18042002");

			/***********************CREATION DE SCENES*************************/

			MenuSelection menuSel = new MenuSelection();
			menuSel.setDB(base); //on connecte le menu de selection à la base de données

			MenuBienvenue menuBv = new MenuBienvenue();

			MenuAccueil menuAc = new MenuAccueil();

			PartieSolo ptSolo = new PartieSolo();
			ptSolo.setDB(base);

			PartieDuel ptDuel = new PartieDuel();
			ptDuel.setDB(base);

			MenuPersonnalisation menuPer = new MenuPersonnalisation();
			menuPer.menuFighter.setDB(base);
			menuPer.menuHome.setDB(base);
			menuPer.menuEnnemies.setDB(base);
			menuPer.menuUniverse.setDB(base);

			MenuRecords menuRc = new MenuRecords();
			menuRc.setDB(base);

			MenuDifficulte menuDif = new MenuDifficulte();

			//PartieDuel ptDuel = new PartieDuel();

			/***********************************************************************************************
			 ***********************************************************************************************
			 ******************************************* MENU **********************************************
			 ***********************************************************************************************      
			 **************************************** BIENVENUE ********************************************
			 ***********************************************************************************************          
			 ***********************************************************************************************/

			stage.setTitle("WELCOME"); //titre de l'interface
			stage.setScene(menuBv.scBienvenue); //on associe la scene du menu SELECTION à notre stage
			stage.setResizable(false);//on empeche le redimensionnement de la fenêtre

			/************************ELEMENTS DE SCENES************************/

			/**on configure et place les éléments de la scène*/
			menuBv.configElmt();
			/**on change l'apparence des boutons du menu*/
			changeBtApp(40,Stock.contourBt,menuBv.soloBt,menuBv.duelBt);

			/****************************ACTIONS******************************/

			/***passage au mode SOLO quand on clique sur le bouton "SOLO"*/
			EventHandler<ActionEvent> clickSolo= new EventHandler<>() {
				public void handle(ActionEvent e) {
					stage.setTitle("SELECT"); //titre de l'interface
					stage.setScene(menuSel.scSelection); //on associe la scene du menu SELECTION à notre stage	
					Stock.aChoisi=false;//initialement aucun compte n'a été choisi
					Stock.choixMode=0; //définit le mode de jeu choisi
				}
			};
			menuBv.soloBt.setOnAction(clickSolo);

			/***passage au mode DUEL quand on clique sur le bouton "DUEL"*/
			EventHandler<ActionEvent> clickDuel= new EventHandler<>() {
				public void handle(ActionEvent e) {
					stage.setTitle("SELECT"); //titre de l'interface
					stage.setScene(menuSel.scSelection); //on associe la scene du menu SELECTION à notre stage	
					Stock.aChoisi=false;//initialement aucun compte n'a été choisi
					Stock.aChoisiAdv=false;
					Stock.choixMode=1;
				}
			};
			menuBv.duelBt.setOnAction(clickDuel);


			/*************************************************************************************************
			 *************************************************************************************************
			 ****************************************** MENU *************************************************
			 *************************************************************************************************                     
			 **************************************** SELECTION **********************************************
			 *************************************************************************************************
			 *************************************************************************************************/

			/***************************ELEMENTS DE SCENES*****************************/

			/**on configure et place tous les éléments de la scène*/
			menuSel.configElmt();

			/**on change l'apparence des boutons du menu*/
			changeBtApp(Stock.hei/40,Stock.contourBt,menuSel.SuivantBt,menuSel.SupprimerBt,menuSel.ConfirmerBt,menuSel.CreerBt,ptSolo.LeaveBt,ptDuel.LeaveBt);

			/**on ajoute les éléments présents sur le menu SELECTION, 
			 * et seulement ceux qui doivent l'être initialement, 
			 * au groupe pour qu'ils soient affichés */
			menuSel.grSelection.getChildren().addAll(menuSel.cvSelection,menuSel.CreerBt,menuSel.SupprimerBt,menuSel.RetourBt,menuSel.SuivantBt); 

			/****tests****/
			//menuSel.autoIncr(3);

			/*****************************INITIALISATION***************************************/

			/**on remplit la collection de comptes avec ceux présents dans la base de données*/
			try {

				menuSel.initComptes();
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			/**on crée les actions pour tous les boutons (profils) dans la collection pour pouvoir les choisir, et on les lie à leur bouton respectif*/
			menuSel.initBt();
			/**on lie l'action à faire qd on clique sur créer au bouton 'Créer'*/
			menuSel.CreerBt.setOnAction(menuSel.clickCreer);
			/**on lie au bouton 'Supprimer' son action*/
			menuSel.SupprimerBt.setOnAction(menuSel.clickSupprimer);
			/**on lie l'action du bouton 'Confirmer' au bouton 'Confirmer'*/
			menuSel.ConfirmerBt.setOnAction(menuSel.clickConfirmer);
			/**on lie l'action du bouton 'Annuler' au bouton 'Annuler'*/
			menuSel.AnnulerBt.setOnAction(menuSel.clickAnnuler);

			/**on retourne au menu de bienvenue en cliquant sur 'Retour'*/
			EventHandler<ActionEvent> clickRetour= new EventHandler<>() {
				public void handle(ActionEvent e) {
					stage.setTitle("WELCOME"); //titre de l'interface
					stage.setScene(menuBv.scBienvenue); //on associe la scene du menu SELECTION à notre stage
					/**en cliquant retour on déselectionne tous les comptes sur lesquels on a cliqué*/
					Iterator<Compte> it2=menuSel.comptes.iterator();	
					while(it2.hasNext()) {
						Compte av = it2.next();
						av.pasCliqué();

					}
				}
			};
			menuSel.RetourBt.setOnAction(clickRetour);

			/**on passe au menu suivant en cliquant sur 'Suivant'*/
			EventHandler<ActionEvent> clickSuivant= new EventHandler<>() {
				public void handle(ActionEvent e) {
					
					if ((Stock.choixMode==0)&&(Stock.aChoisi==true)) {
						stage.setTitle("MAIN"); //titre de l'interface
						stage.setScene(menuAc.scAcc); //on associe la scene du menu SELECTION à notre stage	
						menuAc.grAcc.getChildren().addAll(menuAc.recBt,menuAc.customBt);
						menuAc.affid=Integer.toString(Stock.choixId);
					}
					else if ((Stock.choixMode==1)&&(Stock.aChoisi==true)&&(Stock.aChoisiAdv==true)) {
						stage.setTitle("MAIN"); //titre de l'interface
						stage.setScene(menuAc.scAcc); //on associe la scene du menu SELECTION à notre stage	
						menuAc.affid=Integer.toString(Stock.choixId) + " & " + Integer.toString(Stock.choixIdAdv);	
					}
					menuAc.gcAcc.drawImage(Stock.fond, 0, 0);
					
					menuAc.gcAcc.setFont(Font.font("Helvetica", FontWeight.BOLD, 30));
					menuAc.gcAcc.setStroke(Color.RED);
					menuAc.gcAcc.setLineWidth(2);
					menuAc.gcAcc.strokeText("id : " + menuAc.affid, Stock.wid*4/5, Stock.hei/20);
					menuAc.gcAcc.strokeText(menuAc.rdy, Stock.wid/2, Stock.hei/3.5,Stock.wid/2);

					
				}
			};
			menuSel.SuivantBt.setOnAction(clickSuivant);


			/*************************************************************************************************
			 *************************************************************************************************
			 ****************************************** MENU *************************************************
			 *************************************************************************************************                     
			 ***************************************** ACCUEIL ***********************************************
			 *************************************************************************************************
			 *************************************************************************************************/

			/***************************ELEMENTS DE SCENES*****************************/

			/**on configure et place tous les éléments de la scène*/
			menuAc.configElmt();

			/**on change l'apparence des boutons*/
			changeBtApp(40,Stock.contourBt2,menuAc.jouerBt);
			changeBtApp(20,Stock.contourBt,menuAc.customBt,menuAc.recBt);


			/**en cliquant sur retour dans le menu d'accueil, on retourne dans celui de selection*/
			EventHandler<ActionEvent> clickRetourAcc= new EventHandler<>() {
				public void handle(ActionEvent e) {
					stage.setTitle("MAIN"); //titre de l'interface
					stage.setScene(menuSel.scSelection); //on associe la scene du menu SELECTION à notre stage	
					menuAc.grAcc.getChildren().removeAll(menuAc.recBt,menuAc.customBt);
				}
			};
			menuAc.retourBt.setOnAction(clickRetourAcc);


			/*************************JOUER*************************************/



			EventHandler<ActionEvent> clickJouer = new EventHandler<>() {
				public void handle(ActionEvent e) {
					stage.setTitle("CHOOSE THE DIFFICULTY");
					if ( Stock.choixMode == 0 ) {
						menuDif.titre = "HOW GOOD ARE YOU ?";
						menuDif.impossibleBt.setBackground(menuDif.fondImpossibleB2);
						menuDif.impossibleBt.setText("GOAT");
					}
					else {
						menuDif.titre = "HOW GOOD ARE THE BOTH OF YOU ?";
						menuDif.impossibleBt.setBackground(menuDif.fondImpossibleNoB2);
						menuDif.impossibleBt.setText("LOCKED");
					}
					stage.setScene(menuDif.scene);
				}
			};
			menuAc.jouerBt.setOnAction(clickJouer);



			/************************PERSONNALISER***************************/

			EventHandler<ActionEvent> clickCustom = new EventHandler<>() {
				public void handle(ActionEvent e) {
					stage.setTitle("CUSTOM");
					stage.setScene(menuPer.scene);
				}
			};
			menuAc.customBt.setOnAction(clickCustom);



			/***************************RECORDS******************************/



			EventHandler<ActionEvent> clickRecords= new EventHandler<>() {
				public void handle(ActionEvent e) {

					try {
						menuRc.start();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					stage.setTitle("HIGHSCORES");
					stage.setScene(menuRc.sceneMenu);
				}
			};

			menuAc.recBt.setOnAction(clickRecords);

			EventHandler<ActionEvent> clickLeave = new EventHandler<>() {
				public void handle(ActionEvent e) {
					stage.setTitle("MAIN");
					stage.setScene(menuAc.scAcc);

				}
			};

			menuRc.BtRetour.setOnAction(clickLeave);

			/*************************************************************************************************
			 *************************************************************************************************
			 ****************************************** MENU *************************************************
			 *************************************************************************************************                     
			 ************************************* PERSONNALISATION ******************************************
			 *************************************************************************************************
			 *************************************************************************************************/

			menuPer.start();

			/**************** ACTIONS DES BOUTONS ****************/



			menuPer.BtRetour.setOnAction(clickLeave);

			/*** Amene au menu fighter si on clique sur Fighter ***/ 
			EventHandler<ActionEvent> clickFighter= new EventHandler<>() {
				public void handle(ActionEvent e) {
					stage.setTitle("Fighter Menu");
					stage.setScene(menuPer.menuFighter.scene);
					menuPer.menuFighter.setComptes(menuSel.comptes);
				}
			};
			menuPer.fighterBt.setOnAction(clickFighter);

			/*** Amene au menu home si on clique sur Home ***/
			EventHandler<ActionEvent> clickHome= new EventHandler<>() {
				public void handle(ActionEvent e) {
					stage.setTitle("Home Menu");
					menuPer.menuHome.setComptes(menuSel.comptes);
					stage.setScene(menuPer.menuHome.scene);
				}
			};
			menuPer.homeBt.setOnAction(clickHome);

			/*** Amene au menu universe si on clique sur Universe ***/
			EventHandler<ActionEvent> clickUniverse= new EventHandler<>() {
				public void handle(ActionEvent e) {
					stage.setTitle("Universe Menu");
					menuPer.menuUniverse.setComptes(menuSel.comptes);
					stage.setScene(menuPer.menuUniverse.scene);
				}
			};
			menuPer.univBt.setOnAction(clickUniverse);

			/*** Amene au menu ennemies si on clique sur Ennemies ***/
			EventHandler<ActionEvent> clickEnnemies= new EventHandler<>() {
				public void handle(ActionEvent e) {
					stage.setTitle("Ennemies Menu");
					menuPer.menuEnnemies.setComptes(menuSel.comptes);
					stage.setScene(menuPer.menuEnnemies.scene);
				}
			};
			menuPer.ennemiesBt.setOnAction(clickEnnemies);

			/*** Action du bouton Retour des differents menus ***/ 
			EventHandler<ActionEvent> clickRetourPer= new EventHandler<>() {
				public void handle(ActionEvent e) {
					stage.setScene(menuPer.scene);
				}
			};
			menuPer.menuFighter.BtRetour.setOnAction(clickRetourPer);
			menuPer.menuHome.BtRetour.setOnAction(clickRetourPer);
			menuPer.menuUniverse.BtRetour.setOnAction(clickRetourPer);
			menuPer.menuEnnemies.BtRetour.setOnAction(clickRetourPer);

			/*************************************************************************************************
			 *************************************************************************************************
			 ****************************************** MENU *************************************************
			 *************************************************************************************************                     
			 **************************************** DIFFICULTE *********************************************
			 *************************************************************************************************
			 *************************************************************************************************/

			menuDif.init();
			ptSolo.init();
			ptDuel.init();
			ptDuel.setComptes(menuSel.comptes);
			ptDuel.LeaveBt.setOnAction(clickLeave);

			ptSolo.setComptes(menuSel.comptes);
			ptSolo.LeaveBt.setOnAction(clickLeave);


			/*** Amene au jeu en mode facile si on clique sur FACILE ***/ 
			EventHandler<ActionEvent> clickFacile= new EventHandler<>() {
				public void handle(ActionEvent e) {
					if (Stock.choixMode==0) {
						Stock.choixDifficulte=1;
						stage.setTitle("KILL THE KEY");
						stage.setScene(ptSolo.scene);
						ptSolo.start2();
					}
					else {
						Stock.choixDifficulte=1;
						stage.setTitle("KILL THE KEY (DO IT BETTER THAN YOUR FRIEND)");
						stage.setScene(ptDuel.scene);
						ptDuel.start();
					}
				}
			};
			menuDif.facileBt.setOnAction(clickFacile);

			/*** Amene au jeu en mode moyen si on clique sur MOYEN ***/
			EventHandler<ActionEvent> clickMoyen= new EventHandler<>() {
				public void handle(ActionEvent e) {
					if (Stock.choixMode==0) {
						Stock.choixDifficulte=2;
						stage.setTitle("KILL THE KEY");
						stage.setScene(ptSolo.scene);
						ptSolo.start2();
					}
					else {
						Stock.choixDifficulte=2;
						stage.setTitle("KILL THE KEY (DO IT BETTER THAN YOUR FRIEND)");
						stage.setScene(ptDuel.scene);
						ptDuel.start();
					}
				}
			};
			menuDif.intermediaireBt.setOnAction(clickMoyen);

			/*** Amene au jeu en mode difficile si on clique sur Difficile***/
			EventHandler<ActionEvent> clickDifficile= new EventHandler<>() {
				public void handle(ActionEvent e) {
					if (Stock.choixMode==0) {
						Stock.choixDifficulte=3;
						stage.setTitle("KILL THE KEY");
						stage.setScene(ptSolo.scene);
						ptSolo.start2();
					}
					else {
						Stock.choixDifficulte=3;
						stage.setTitle("KILL THE KEY (DO IT BETTER THAN YOUR FRIEND)");
						stage.setScene(ptDuel.scene);
						ptDuel.start();
					}
				}
			};
			menuDif.difficileBt.setOnAction(clickDifficile);

			/*** Amene au jeu en mode impossible si on clique sur Impossible ***/
			EventHandler<ActionEvent> clickImpossible = new EventHandler<>() {
				public void handle(ActionEvent e) {
					if (Stock.choixMode==0) {
						Stock.choixDifficulte=4;
						stage.setTitle("KILL THE KEY");
						stage.setScene(ptSolo.scene);
						ptSolo.start2();
					}
				}
			};
			menuDif.impossibleBt.setOnAction(clickImpossible);

			menuDif.BtRetour.setOnAction(clickLeave);


			stage.show();

		} catch (ClassNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

	}
}

