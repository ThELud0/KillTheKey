/**
 * 
 * author Ludovic
 * 
 * 
 */

package pro3600.application;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class MenuSelection {

	public MenuSelection() {}



	/***********************base de données******************************/

	SqlBase test ;

	/**on choisit à quelle base se connecter*/
	public void setDB(SqlBase dtb) {
		test = dtb;	
	}


	/**************************creation de la scene*********************/

	public  Group grSelection = new Group(); //on crée un groupe
	public  Scene scSelection = new Scene(grSelection); //on associe une scene à ce groupe
	public  Canvas cvSelection = new Canvas(Stock.wid,Stock.hei); //pour afficher un dessin
	public  GraphicsContext gcSelection = cvSelection.getGraphicsContext2D(); //on extrait un gc pour dessiner un contenu

	/***********************creation des boutons***************************/

	Button CreerBt = new Button("Create");
	Button SupprimerBt = new Button("Delete");
	TextField textProfil = new TextField();
	Button ConfirmerBt = new Button("Confirm");
	Button AnnulerBt = new Button();
	Button RetourBt = new Button();
	Button SuivantBt = new Button("Next");

	

	/**Placement de tous les éléments du menu SELECTION*/
	public void configElmt() {
		gcSelection.drawImage(Stock.fond, 0, 0);//on dessine le fond
		
		CreerBt.setTranslateX(Stock.widbouton);
		CreerBt.setTranslateY(Stock.hei-2*Stock.heibouton);
		CreerBt.setMinHeight(Stock.heibouton);
		CreerBt.setMinWidth(Stock.widbouton);

		SupprimerBt.setTranslateX(Stock.wid-2*Stock.widbouton);
		SupprimerBt.setTranslateY(Stock.hei-2*Stock.heibouton);
		SupprimerBt.setMinHeight(Stock.heibouton);
		SupprimerBt.setMinWidth(Stock.widbouton);

		textProfil.setTranslateX(Stock.wid/2-Stock.widtext/2);
		textProfil.setTranslateY(Stock.hei/2-Stock.heitext/2);
		textProfil.setMinHeight(Stock.heitext);
		textProfil.setMinWidth(Stock.widtext);

		ConfirmerBt.setTranslateX(Stock.wid/2-Stock.widbouton/2);
		ConfirmerBt.setTranslateY(Stock.hei/2+Stock.heitext/2);
		ConfirmerBt.setMinHeight(Stock.heibouton);
		ConfirmerBt.setMinWidth(Stock.widbouton);

		AnnulerBt.setTranslateX(10);
		AnnulerBt.setTranslateY(10);
		AnnulerBt.setMinHeight(Stock.heiButtonR);
		AnnulerBt.setMinWidth(Stock.widButtonR);
		AnnulerBt.setBackground(Stock.fondBtRetourB2);

		Stock.configBtRetour(RetourBt);

		SuivantBt.setTranslateX(Stock.wid/2-Stock.widbouton/2);
		SuivantBt.setTranslateY(Stock.hei/2+Stock.heitext/2);
		SuivantBt.setMinHeight(Stock.heibouton);
		SuivantBt.setMinWidth(Stock.widbouton);
		
		
	}

	/****************************INITIALISATION************************/

	Collection <Compte> comptes = new ArrayList<>(); //on crée une collection vide de comptes, c'est ici que sera stockée tous les profils du côté Java

	/**Remplissage de la collection de comptes avec ceux existants dans la base de données*/
	public void initComptes() throws ClassNotFoundException {
		try {
			for (int i=0;i<test.yacb().size();i++) {  //on la remplit avec les comptes qui sont déjà dans la base de données
				String path;
				String nomCompte=test.yaqui().get(i);
				int id=test.yacb().get(i);
				/**tous les noms, sauf ceux du groupe et alessio, ont une image par défaut*/
				/**on va chercher l'adresse des images en question*/
				if (nomCompte.equals("Jun")) { 
					path="Jun.jpg";
				}
				else if (nomCompte.equals("Ludovic")) {
					path="Ludovic.jpg";
				}
				else if (nomCompte.equals("Mai")) {
					path="Mai.jpg";
				}
				else if (nomCompte.equals("Rayane")) {
					path="Rayane.jpg";
				}
				else {path=test.getfighter(id);}
				Compte bebe = new Compte(path,test.getennemies(id),test.gethome(id),test.getuniverse(id),Stock.hei/12,Stock.hei/12,id); //on crée le compte pour celui correspondant de la base de donnée, avec le meme id que dans la base et une image
				bebe.setPosition(13+comptes.size()*(Stock.hei/12+13), Stock.hei/4); //on place l'avatar du compte sur le menu
				grSelection.getChildren().add(bebe.aBouton());

				gcSelection.setFont(Font.font("Helvetica", FontWeight.BOLD, Stock.hei/67)); 
				gcSelection.setFill(Color.WHITE);
				gcSelection.setTextAlign(TextAlignment.LEFT);
				gcSelection.setTextBaseline(VPos.BOTTOM);
				gcSelection.fillText(test.getnom(bebe.id()),13+i*(Stock.hei/12+13), Stock.hei/4+Stock.hei/12+Stock.hei/60,Stock.hei/12); //on écrit le nom des comptes en dessous de leur image

				comptes.add(bebe);//on ajoute chaque nouveau compte à la collection
			}
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("La requête SQL n'a visiblement point marché");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} 
	}


	/**Creation des actions liés aux boutons-avatars de chaque compte existant, 
	 * sur lesquels on clique pour sélectionner le ou les dits comptes dans le menu de sélection*/
	public void initBt() {
		Iterator<Compte> it=comptes.iterator(); 
		while(it.hasNext()) {
			Compte avatar = it.next();
			avatar.aBouton().setOnAction(e->{
				/**si mode SOLO*/
				if (Stock.choixMode==0) {
					/**quand on en choisit un, on retient son id*/
					avatar.estCliqué();
					Stock.aChoisi=true;
					Stock.choixId=avatar.id();
					/**et on désélectionne tous les autres*/
					Iterator<Compte> it2=comptes.iterator();	
					while(it2.hasNext()) {
						Compte av = it2.next();
						if (!(av.id()==avatar.id())) {
							av.pasCliqué();
						}
					}
					
				}
				/**si mode DUEL*/
				if (Stock.choixMode==1) {
					/**si aucun n'a été choisi, on prend le 1er qui est cliqué*/
					if ((Stock.aChoisi==false)&&(Stock.aChoisiAdv==false)) {
						avatar.estCliqué();
						Stock.aChoisi=true;
						Stock.choixId=avatar.id();
					}
					/**si un seul a été choisi et que c'est le 1er compte*/
					else if ((Stock.aChoisi==true)&&(Stock.aChoisiAdv==false)) {
						/**si le même est resélectionné, on l'enlève*/
						if ((avatar.id()==Stock.choixId)) {
							avatar.pasCliqué();
							Stock.aChoisi=false;
						}
						/**sinon on sélectionne le 2e et on retient ce 2e id*/
						else {
							avatar.estCliqué();
							Stock.aChoisiAdv=true;
							Stock.choixIdAdv=avatar.id();
						}
					}
					/**si un seul a été choisi et c'est le 2e compte*/
					else if ((Stock.aChoisi==false)&&(Stock.aChoisiAdv==true)) {
						/**si le même est resélectionné, on l'enlève*/
						if ((avatar.id()==Stock.choixIdAdv)) {
							avatar.pasCliqué();
							Stock.aChoisiAdv=false;
						}
						/**sinon on sélectionne le 2e et on retient ce 2e id*/
						else {
							avatar.estCliqué();
							Stock.aChoisi=true;
							Stock.choixId=avatar.id();
						}
					}
					/**si les deux ont été choisi*/
					else if ((Stock.aChoisi==true)&&(Stock.aChoisi==true)) {
						/**si on reclique sur un des deux, le compte est désélectionné*/
						if (avatar.id()==Stock.choixId){
							avatar.pasCliqué();
							Stock.aChoisi=false;
						}
						else if (avatar.id()==Stock.choixIdAdv) {
							avatar.pasCliqué();
							Stock.aChoisiAdv=false;
						}
					}
					
				};

			});
		};
	}


	/***************************ACTIONS**************************/

	/**quand on clique sur 'Créer'*/

	EventHandler<ActionEvent> clickCreer = new EventHandler<>() {
		public void handle(ActionEvent e) {
			if (comptes.size()<=10) {  //on ne fait qqch seulement s'il y a moins de 10 profils existants
				gcSelection.drawImage(Stock.fond,0,0); //on efface tous les dessins du menu de base
				grSelection.getChildren().removeAll(SupprimerBt,CreerBt,RetourBt,SuivantBt); //on retire aussi les boutons du menu de base
				Iterator<Compte> it=comptes.iterator(); 
				while(it.hasNext()) {
					Compte avatar = it.next();					
					grSelection.getChildren().remove(avatar.aBouton());
				}
				textProfil.setText("nom ?"); 
				grSelection.getChildren().addAll(textProfil,ConfirmerBt,AnnulerBt); //on fait apparaitre les boutons Confirmer/Annuler et la zone de texte pour entrer un nom
				Stock.confirmation=0; //on met le bouton 'Confirmer' en mode création de comptes	
			}
		};
	};

	/**quand on clique sur 'Supprimer'*/
	EventHandler<ActionEvent> clickSupprimer = new EventHandler<>() {
		public void handle(ActionEvent e) { //parallele à l'évenement clickCreer
			if (comptes.size()>1) { //on ne fait rien s'il n'y a plus que un compte (ou moins)
				gcSelection.drawImage(Stock.fond,0,0);
				grSelection.getChildren().removeAll(SupprimerBt,CreerBt,RetourBt,SuivantBt);

				Iterator<Compte> it2=comptes.iterator();	
				while(it2.hasNext()) {
					Compte av = it2.next();
					av.pasCliqué();
				}
				Stock.aChoisi=false;
				Stock.aChoisiAdv=false;

				Iterator<Compte> it=comptes.iterator(); 
				while(it.hasNext()) {
					Compte avatar = it.next();					
					grSelection.getChildren().remove(avatar.aBouton());
				}
				textProfil.setText("nom ?");
				grSelection.getChildren().addAll(textProfil,ConfirmerBt,AnnulerBt);
				Stock.confirmation=1; //seule différence ici, on met le bouton 'Confirmer' en mode suppression de comptes
			}
		}
	};

	/**le bouton 'Confirmer' nous sert à la fois pour créer et supprimer des profils, 
	 * on différencie quoi faire grâce à la variable Stock.confirmation*/
	EventHandler<ActionEvent> clickConfirmer = new EventHandler<>() {
		public void handle(ActionEvent e) {
			try { 
				/**on regarde ce qui a été écrit et on voit si le texte entré correspond à un nom dans la collection*/
				String text = textProfil.getText();
				boolean existe=false;
				for (int i=0;i<test.yacb().size();i++) {
					if (test.yaqui().get(i).equals(text)){
						existe=true;
					}
				}
				/**en mode création de profils*/
				if (Stock.confirmation==0) {

					/**si le nom entré existe déjà, on le dit et on ne fait rien d'autre (pas de doublons)*/
					if (existe==true) {textProfil.setText("Existe déjà");}
					/**longueur max du nom*/
					else if (text.length()>15) {textProfil.setText("trop long (15max)");}
					/**sinon on crée le profil*/
					else {

						gcSelection.drawImage(Stock.fond, 0, 0);//on dessine le fond (ca efface tout le reste donc il faut tout redessiner)

						/**on ajoute un profil dans la base de donnée avec le noavatar.m qui a été tapé*/
						String query = "INSERT INTO joueurs "   
								+ "(nom, dateCreation,fighter,home,universe,ennemies) "
								+ "VALUES ('" + text +  "', "
								+ "'"+java.time.LocalDate.now() + "','defaut.png','PlaneteTerre.png','background.jpg','Ovni.png')";
						test.tienstademande(query); //voir SqlExemple, c'est les methodes pour envoyer le test.
						test.obeitmoi();

						/**comme à l'initialisation, on ajoute un compte lié à ce nouveau profil dans la collection côté java maintenant*/
						String path;
						if (text.equals("Jun")) {
							path="Jun.jpg";
						}
						else if (text.equals("Mai")) {
							path="Mai.jpg";
						}
						else if (text.equals("Ludovic")) {
							path="Ludovic.jpg";
						}
						else if (text.equals("Rayane")) {
							path="Rayane.jpg";
						}
						else {path="defaut.png";}

						Compte bebe = new Compte(path,"Ovni.png","PlaneteTerre.png","background.jpg",Stock.hei/12,Stock.hei/12,test.yacb().get(comptes.size())); //ensuite, on crée le nouvel avatar en prenant l'id du profil qu'on vient d'ajouter dans la base
						
						bebe.setPosition(13+comptes.size()*(Stock.hei/12+13), Stock.hei/4); //on place l'avatar du compte sur le menu
						comptes.add(bebe);		//on l'ajoute à la collection d'avatars

						bebe.aBouton().setOnAction(ev->{
							/**si mode SOLO*/
							if (Stock.choixMode==0) {
								/**quand on en choisit un, on retient son id*/
								bebe.estCliqué();
								Stock.aChoisi=true;
								Stock.choixId=bebe.id();
								/**et on désélectionne tous les autres*/
								Iterator<Compte> it2=comptes.iterator();	
								while(it2.hasNext()) {
									Compte av = it2.next();
									if (!(av.id()==bebe.id())) {
										av.pasCliqué();
									}
								}
								
							}
							/**si mode DUEL*/
							if (Stock.choixMode==1) {
								/**si aucun n'a été choisi, on prend le 1er qui est cliqué*/
								if ((Stock.aChoisi==false)&&(Stock.aChoisiAdv==false)) {
									bebe.estCliqué();
									Stock.aChoisi=true;
									Stock.choixId=bebe.id();
								}
								/**si un seul a été choisi et que c'est le 1er compte*/
								else if ((Stock.aChoisi==true)&&(Stock.aChoisiAdv==false)) {
									/**si le même est resélectionné, on l'enlève*/
									if ((bebe.id()==Stock.choixId)) {
										bebe.pasCliqué();
										Stock.aChoisi=false;
									}
									/**sinon on sélectionne le 2e et on retient ce 2e id*/
									else {
										bebe.estCliqué();
										Stock.aChoisiAdv=true;
										Stock.choixIdAdv=bebe.id();
									}
								}
								/**si un seul a été choisi et c'est le 2e compte*/
								else if ((Stock.aChoisi==false)&&(Stock.aChoisiAdv==true)) {
									/**si le même est resélectionné, on l'enlève*/
									if ((bebe.id()==Stock.choixIdAdv)) {
										bebe.pasCliqué();
										Stock.aChoisiAdv=false;
									}
									/**sinon on sélectionne le 2e et on retient ce 2e id*/
									else {
										bebe.estCliqué();
										Stock.aChoisi=true;
										Stock.choixId=bebe.id();
									}
								}
								/**si les deux ont été choisi*/
								else if ((Stock.aChoisi==true)&&(Stock.aChoisi==true)) {
									/**si on reclique sur un des deux, le compte est désélectionné*/
									if (bebe.id()==Stock.choixId){
										bebe.pasCliqué();
										Stock.aChoisi=false;
									}
									else if (bebe.id()==Stock.choixIdAdv) {
										bebe.pasCliqué();
										Stock.aChoisiAdv=false;
									}
								}
						
							};
						});
						/**Il faut maintenant tout redessiner avec le nouveau profil en plus*/
						Iterator<Compte> it=comptes.iterator(); 
						int i=0;
						while(it.hasNext()) {
							Compte avatar = it.next();					
							grSelection.getChildren().add(avatar.aBouton());
							
							gcSelection.setFont(Font.font("Helvetica", FontWeight.BOLD, Stock.hei/67)); 
							gcSelection.setFill(Color.WHITE);
							gcSelection.setTextAlign(TextAlignment.LEFT);
							gcSelection.setTextBaseline(VPos.BOTTOM);
							gcSelection.fillText(test.getnom(avatar.id()),13+i*(Stock.hei/12+13), Stock.hei/4+Stock.hei/12+Stock.hei/60,Stock.hei/12); //on écrit le nom des comptes en dessous de leur image

							i+=1;
						}
						/**affichage du nombre de comptes*/
						gcSelection.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
						gcSelection.setFill(Color.BISQUE);
						gcSelection.fillText("Accounts count : "+Integer.toString(comptes.size()), 540, 36); 
						/**on enleve les boutons qui ont servi à la création/suppression de comptes et on remet ceux du menu de base*/
						grSelection.getChildren().removeAll(textProfil,ConfirmerBt,AnnulerBt);
						grSelection.getChildren().addAll(CreerBt,SupprimerBt,RetourBt,SuivantBt);

					}
				}

				/**en mode suppression de profils*/
				else if (Stock.confirmation==1) {
					/**si le nom entré n'existe pas, on le dit et on ne fait rien d'autre*/
					if (existe==false) {textProfil.setText("N'existe pas");}
					/**sinon s'il correspond au nom d'un profil existant, on le supprime*/
					else {
						gcSelection.drawImage(Stock.fond, 0, 0);//on dessine le fond et pareil ca efface tout le reste

						int idavatar=test.getid(text);//on va chercher dans la base de données l'id de l'avatar qu'on veut supprimer à partir de son nom
						/**on parcourt ensuite la collection pour trouver dedans le compte qui a cet id*/
						Iterator<Compte> it=comptes.iterator();
						while(it.hasNext()) {
							Compte avatar = it.next(); 
							if (avatar.id()==idavatar) {
								it.remove(); //on enleve l'avatar de la collection et donc du côté Java
								grSelection.getChildren().remove(avatar.aBouton());
							}
						}
						if (!(it.hasNext())){ //après avoir parcouru toute la collection
							/**on enleve maintenant le profil dans la base de données*/
							String query = "DELETE FROM duels WHERE (id1= '" + idavatar + "') "; 
							test.tienstademande(query);
							test.obeitmoi();
							
							query = "DELETE FROM duels WHERE (id2= '" + idavatar + "') "; 
							test.tienstademande(query);
							test.obeitmoi();
							
							query = "DELETE FROM parties_solo WHERE (idjoueur= '" + idavatar + "') "; 
							test.tienstademande(query);
							test.obeitmoi();
							
							query = "DELETE FROM joueurs WHERE (nom = '" + text + "') "; 
							test.tienstademande(query);
							test.obeitmoi();	
							/**et on redessine tout le menu sans le compte qu'on vient de retirer*/
							Iterator<Compte> it2=comptes.iterator(); 
							int i=0;
							while(it2.hasNext()) {
								Compte avatar = it2.next();
								avatar.setPosition(13+i*(Stock.hei/12+13), Stock.hei/4); //on place l'avatar du compte sur le menu
								
								grSelection.getChildren().add(avatar.aBouton());
								gcSelection.setFont(Font.font("Helvetica", FontWeight.BOLD, Stock.hei/67)); 
								gcSelection.setFill(Color.WHITE);
								gcSelection.setTextAlign(TextAlignment.LEFT);
								gcSelection.setTextBaseline(VPos.BOTTOM);
								gcSelection.fillText(test.getnom(avatar.id()),13+i*(Stock.hei/12+13), Stock.hei/4+Stock.hei/12+Stock.hei/60,Stock.hei/12); //on écrit le nom des comptes en dessous de leur image

								i+=1;
							}
							/**affichage nombre de comptes*/
							gcSelection.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
							gcSelection.setFill(Color.BISQUE);
							gcSelection.fillText("Accounts count : "+Integer.toString(comptes.size()), 540, 36);
							/**Changement des boutons à l'écran*/
							grSelection.getChildren().removeAll(textProfil,ConfirmerBt,AnnulerBt);
							grSelection.getChildren().addAll(CreerBt,SupprimerBt,RetourBt,SuivantBt);
						}
					}
				}
			}catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("Ca n'a visiblement point marché");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		}
	};

	/**pour ne pas être forcé à créer ou supprimer un compte si on clique sans faire expres, un bouton annuler*/
	EventHandler<ActionEvent> clickAnnuler = new EventHandler<>() {
		public void handle(ActionEvent e) {
			try {
				/**on redessine juste tout le menu de base sans changement*/
				gcSelection.drawImage(Stock.fond, 0, 0);
				Iterator<Compte> it=comptes.iterator(); 
				int i=0;
				while(it.hasNext()) {
					Compte avatar = it.next();					
					grSelection.getChildren().add(avatar.aBouton());
					gcSelection.setFont(Font.font("Helvetica", FontWeight.BOLD, Stock.hei/67)); 
					gcSelection.setFill(Color.WHITE);
					gcSelection.setTextAlign(TextAlignment.LEFT);
					gcSelection.setTextBaseline(VPos.BOTTOM);
					gcSelection.fillText(test.getnom(avatar.id()),13+i*(Stock.hei/12+13), Stock.hei/4+Stock.hei/12+Stock.hei/60,Stock.hei/12); //on écrit le nom des comptes en dessous de leur image

					i+=1;
				}
				gcSelection.setFont(Font.font("Helvetica", FontWeight.BOLD, 14));
				gcSelection.setFill(Color.BISQUE);
				gcSelection.fillText("Accounts count : "+Integer.toString(comptes.size()), 540, 36); 
				/**on change les boutons à l'écran*/
				grSelection.getChildren().addAll(CreerBt,SupprimerBt,RetourBt,SuivantBt);
				grSelection.getChildren().removeAll(textProfil,ConfirmerBt,AnnulerBt);
			}catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.out.println("Ca n'a visiblement point marché");
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
		}
	};

	/****reinitialise l'autoincrementation des ids de la base de données à 'nb'****/
	public void autoIncr(int nb) {
		String query2 = "ALTER TABLE joueurs AUTO_INCREMENT = " + nb + ";";
		test.tienstademande(query2);	//les id sont auto incrementés qd on ajt des nv elmt dans la base
		try {
			test.obeitmoi();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //mais qd on les enleve, l'increment continue dnc il faut le remettre à l'id qu'on vient de retirer
	}


}

