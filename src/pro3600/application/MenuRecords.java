/**
 * 
 * author Mai Lan
 * 
 * 
 */

package pro3600.application;


import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.sql.SQLException;
import java.util.List;



public class MenuRecords {
	

	private Stage stageMenu;
	Scene sceneMenu;
	private Group groupMenu;

	
	private Stage stageGlobal;
	private Scene sceneGlobal;
	private BorderPane rootGlobal;
	private Button buttonGlobal;

	private Stage stageDuel;
	private Scene sceneDuel;
	private BorderPane rootDuel;
	private Button buttonDuel;
	
	private Stage stageSolo;
	private Scene sceneSolo;
	private BorderPane rootSolo;
	private Button buttonSolo;
	
	//variables dans lesquelles sont stockées les données 
	//extraites de la relation partie_solo
	private List<String> nomsJoueursSolo;
	private List<String> dates;
	private List<String> solodates;
	private List<String> modes;
	private List<Integer> rangs;
	private List<Integer> soloranks;
	private List<String> solomodes;
	private List<Integer> scores;
	private List<Integer> soloscores;
	
	//variables dans lesquelles sont stockées les données 
	//extraites de la relation duels
	private List<String> nomsAdv;
	private List<Integer> idsAdv;
	
	//constantes
	double heiButton1 = Stock.hei/1.558 ;
	double widButton1 = Stock.wid/3.154;
	double width = Stock.wid;
	double height = Stock.hei;
	Button BtRetour = new Button();


  public void start() throws Exception {

	  createStageMenu();
	  stageGlobal = createStageGlobal();
	  stageDuel = createStageDuel();
	  stageSolo = createStageSolo();
	  
  }
  
  
//****************************************************************************************// 
//****************************************************************************************// 
//******************CREATION DU STAGE AFFICHANT LE MENU RECORDS **************************//
//****************************************************************************************// 
//****************************************************************************************// 
  
  private void createStageMenu() {
	  

	  Border contourBt = Stock.contourBt;
	  	  
	  Stock.configBtRetour(BtRetour);
	  	  
	  //BOUTON RECORDS GLOBAUX
	  Image fondFacile = new Image("RecordsGlobal.png",widButton1,heiButton1,false,false);
	  BackgroundImage fondFacileB = new BackgroundImage(fondFacile,null,null,null,null);
	  Background fondFacileB2 = new Background(fondFacileB);
	  buttonGlobal = new Button("GLOBAL");
	  buttonGlobal.setOnAction(e -> stageGlobal.show());
	  buttonGlobal.setTranslateX(width/2-widButton1/2);
	  buttonGlobal.setTranslateY(height/4);
	  buttonGlobal.setMinHeight(heiButton1);
	  buttonGlobal.setMinWidth(widButton1);
	  buttonGlobal.setFont(Font.font("Helvetica",FontWeight.BOLD,Stock.hei/15));
	  buttonGlobal.setTextFill(Color.RED);
	  buttonGlobal.setBorder(contourBt);
	  buttonGlobal.setBackground(fondFacileB2);
	  
	  
	  //BOUTON RECORDS DUELS
	  Image fondDifficile = new Image("RecordsDuels2.jpg",widButton1,heiButton1,false,false);
	  BackgroundImage fondDifficileB = new BackgroundImage(fondDifficile,null,null,null,null);
	  Background fondDifficileB2 = new Background(fondDifficileB);
	  buttonDuel = new Button("DUELS");
	  buttonDuel.setOnAction(e -> stageDuel.show());
	  buttonDuel.setTranslateX(width-widButton1*1.05);
	  buttonDuel.setTranslateY(height/4);
	  buttonDuel.setMinHeight(heiButton1);
	  buttonDuel.setMinWidth(widButton1);
	  buttonDuel.setFont(Font.font("Helvetica",FontWeight.BOLD,Stock.hei/15));
	  buttonDuel.setTextFill(Color.RED);
	  buttonDuel.setBorder(contourBt);
	  buttonDuel.setBackground(fondDifficileB2);
	  
	  //BOUTON RECORDS SOLO
	  Image fondImpossible = new Image("RecordsDuels.jpg",widButton1,heiButton1,false,false);
	  BackgroundImage fondImpossibleB = new BackgroundImage(fondImpossible,null,null,null,null);
	  Background fondImpossibleB2 = new Background(fondImpossibleB);
	  buttonSolo = new Button ("SOLO");
	  buttonSolo.setOnAction((e -> stageSolo.show()));
	  buttonSolo.setTranslateX(widButton1*0.05);
	  buttonSolo.setTranslateY(height/4);
	  buttonSolo.setMinHeight(heiButton1);
	  buttonSolo.setMinWidth(widButton1);
	  buttonSolo.setFont(Font.font("Helvetica",FontWeight.BOLD,Stock.hei/15));
	  buttonSolo.setTextFill(Color.RED);
	  buttonSolo.setBorder(contourBt);
	  buttonSolo.setBackground(fondImpossibleB2);
	  
	  groupMenu = new Group();
	  sceneMenu = new Scene(groupMenu, Stock.wid, Stock.hei);
	  Canvas cvRecords = new Canvas(Stock.wid,Stock.hei);
	  GraphicsContext gcRecords = cvRecords.getGraphicsContext2D();
	  gcRecords.drawImage(Stock.fond,0,0);
	  gcRecords.setFont(Font.font("Helvetica", FontWeight.BOLD, Stock.hei/12));
	  gcRecords.setStroke(Color.RED);
	  gcRecords.setLineWidth(2);
	  gcRecords.setTextAlign(TextAlignment.CENTER);
	  gcRecords.strokeText("RANKINGS", Stock.wid/2, Stock.hei/5,Stock.wid/3);
	  groupMenu.getChildren().addAll(cvRecords,buttonGlobal,buttonDuel,buttonSolo,BtRetour);
	  
	  
	  return ;
  }
  
  
  SqlBase base ;
  public void setDB(SqlBase dtb) {
		base = dtb;	
	}

  
//****************************************************************************************// 
//****************************************************************************************// 
//******************CREATION DU STAGE AFFICHANT LE CLASSEMENT GLOBAL *********************//
//****************************************************************************************// 
//****************************************************************************************// 
  
@SuppressWarnings("unchecked")
private Stage createStageGlobal() {
	  
	  try {
	  
	  stageGlobal = new Stage();
	  stageGlobal.setTitle("Classement global!!!");
	  stageGlobal.initOwner(stageMenu);
	  stageGlobal.initModality(Modality.APPLICATION_MODAL);  
	  rootGlobal = new BorderPane();
	  sceneGlobal = new Scene(rootGlobal, Stock.wid, Stock.hei);
	  
	  
	  //Création de la table et de ses colonnes
	  @SuppressWarnings("rawtypes")
	TableView table = new TableView<DataSet>();
	  @SuppressWarnings("rawtypes")
	TableColumn rank = new TableColumn<DataSet, Integer>("Rang");
	  rank.setCellValueFactory(new PropertyValueFactory<DataSet,Integer>("rang"));
	  @SuppressWarnings("rawtypes")
	TableColumn name = new TableColumn<DataSet, String>("Joueur");
	  name.setCellValueFactory(new PropertyValueFactory<DataSet,String>("name"));
	  @SuppressWarnings("rawtypes")
	TableColumn score = new TableColumn<DataSet, Integer>("Score");
	  score.setCellValueFactory(new PropertyValueFactory<DataSet,Integer>("score"));
	  @SuppressWarnings("rawtypes")
	TableColumn mode = new TableColumn<DataSet, String>("Difficulté");
	  mode.setCellValueFactory(new PropertyValueFactory<DataSet,String>("mode"));
	  @SuppressWarnings("rawtypes")
	TableColumn date = new TableColumn<DataSet, String>("Date et heure");
	  date.setCellValueFactory(new PropertyValueFactory<DataSet, String>("dateEtHeure"));

	  
	  //Ajout des colonnes à la table et apparence
	  table.getColumns().add(rank);
	  table.getColumns().add(name);
	  table.getColumns().add(score);
	  table.getColumns().add(mode);
	  table.getColumns().add(date);
	  table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
      table.getStylesheets().add("style.css");
	  
      
	  //Remplissage de la table avec les valeurs stockées dans la base de données
	  nomsJoueursSolo = base.donnerNomsJoueursSolo();
	  dates = base.donnerDatesPartiesSolo();
	  modes = base.donnerDifficultesPartiesSolo();
	  rangs = base.donnerRangsPartiesSolo();
	  scores = base.donnerScoresPartiesSolo();
	  
	  
	  for (int i=0; i < scores.size(); i++) {
		  table.getItems().add(new DataSet(nomsJoueursSolo.get(i), modes.get(i), dates.get(i), rangs.get(i), scores.get(i) ));  
		  }
	  
	  //Ajout de la table au stage des records globaux
	  rootGlobal.setCenter(table);
	  sceneGlobal.getStylesheets().add("style.css");
	  stageGlobal.setScene(sceneGlobal);
	  } catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	  
	  return stageGlobal;
  }
	

//****************************************************************************************// 
//****************************************************************************************// 
//******************CREATION DU STAGE AFFICHANT LE CLASSEMENT DUEL ***********************//
//****************************************************************************************// 
//****************************************************************************************// 

@SuppressWarnings({ "rawtypes", "unchecked" })
private Stage createStageDuel() {
	  
	  try {
	  
		  
	  //Création et initialisation du stage des records en duel
	  stageDuel = new Stage();
	  stageDuel.setTitle("Duels de " + base.getnom(Stock.choixId));
	  stageDuel.initOwner(stageMenu);
	  stageDuel.initModality(Modality.APPLICATION_MODAL);
	  rootDuel = new BorderPane();
	  sceneDuel = new Scene(rootDuel, Stock.wid, Stock.hei);
	  sceneDuel.getStylesheets().add("style.css");
	 
	  
	  //Création du tableau des records et de ses colonnes
	  TableView<DataSetDuel> table = new TableView<DataSetDuel>();
	  TableColumn namesAdv = new TableColumn<DataSetDuel, String>("Adversaire");
	  namesAdv.setCellValueFactory(new PropertyValueFactory<DataSetDuel,String>("nomAdv"));
	  TableColumn nbTotalDuels = new TableColumn<DataSetDuel, Integer>("Nombre de duels");
	  nbTotalDuels.setCellValueFactory(new PropertyValueFactory<DataSetDuel,Integer>("nbTotalDuels"));
	  TableColumn nbTotalEgal = new TableColumn<DataSetDuel, Integer>("Nombre de matchs nuls");
	  nbTotalEgal.setCellValueFactory(new PropertyValueFactory<DataSetDuel,Integer>("nbTotalEgal"));
	  TableColumn nbTotalWin = new TableColumn<DataSetDuel, Integer>("Nombre de victoires yay!!!");
	  nbTotalWin.setCellValueFactory(new PropertyValueFactory<DataSetDuel,Integer>("nbTotalWin"));
	  table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
      table.getStylesheets().add("style.css");
	  
      
      //Remplissage de la table avec les valeurs stockées dans la BD : appel aux méthodes de SqlBase
	  nomsAdv = base.donnerNomsAdversaires(Stock.choixId);
	  idsAdv = base.donnerIdsAdversaires(Stock.choixId);
	  for (int i=0; i < nomsAdv.size(); i++) {
		  int idAdv = idsAdv.get(i);
		  table.getItems().add(new DataSetDuel(Stock.choixId, idAdv, nomsAdv.get(i), base.nbTotalDuels(Stock.choixId, idAdv), base.nbTotalEgalites(Stock.choixId, idAdv), base.nbTotalVictoires(Stock.choixId, idAdv)) );
		  }
	  table.getColumns().addAll(namesAdv, nbTotalDuels, nbTotalWin, nbTotalEgal);

	  
	  
	  //Ajout de la table au stage des records en duel
	  rootDuel.setCenter(table);
	  stageDuel.setScene(sceneDuel);
	  } catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			}
	  
	  return stageDuel;
}

//****************************************************************************************// 
//****************************************************************************************// 
//******************CREATION DU STAGE AFFICHANT LE CLASSEMENT SOLO ***********************//
//****************************************************************************************// 
//****************************************************************************************// 

@SuppressWarnings({ "rawtypes", "unchecked" })
private Stage createStageSolo() {
	  
	  try {
		  
		  
      // Création et initialisation du stage des records Solo
	  String name = base.getnom(Stock.choixId);
	  stageSolo = new Stage();
	  stageSolo.setTitle("Records personnels de " + name);
	  stageSolo.initOwner(stageMenu);
	  stageSolo.initModality(Modality.APPLICATION_MODAL);
	  rootSolo = new BorderPane();
	  sceneSolo = new Scene(rootSolo, Stock.wid, Stock.hei);
	  sceneSolo.getStylesheets().add("style.css");
	 
	  
	  //Création du tableau des records et de ses colonnes
	  TableView<DataSet> table = new TableView<DataSet>();
      table.getStylesheets().add("style.css");
	  TableColumn soloRanks = new TableColumn<DataSet, Integer>("Rang");
	  soloRanks.setCellValueFactory(new PropertyValueFactory<DataSet,Integer>("rang"));
	  TableColumn soloScores = new TableColumn<DataSet, Integer>("Score");
	  soloScores.setCellValueFactory(new PropertyValueFactory<DataSet,Integer>("score"));
	  TableColumn soloDates = new TableColumn<DataSet, String>("Date et heure");
	  soloDates.setCellValueFactory(new PropertyValueFactory<DataSet,String>("dateEtHeure"));
	  TableColumn soloModes = new TableColumn<DataSet, String>("Difficulté");
	  soloModes.setCellValueFactory(new PropertyValueFactory<DataSet,String>("mode"));
	  table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
	  
	  
	  //Remplissage du tableau avec les données de la BD : appel aux méthodes de SqlBase
	  soloranks = base.donnerRangsPartiesSolo(Stock.choixId);
	  solomodes = base.donnerDifficultePartiesSolo(Stock.choixId);
	  solodates = base.donnerDatesPartiesSolo(Stock.choixId);
	  soloscores = base.donnerScoresPartiesSolo(Stock.choixId);
	  for (int i=0; i < soloranks.size(); i++) {
		  table.getItems().add(new DataSet(name, solomodes.get(i), solodates.get(i), soloranks.get(i) , soloscores.get(i) ) );
		  }
	  table.getColumns().addAll(soloRanks, soloScores, soloModes, soloDates);

	  
	  //Ajout du tableau au stage des records solo
	  rootSolo.setCenter(table);
	  stageSolo.setScene(sceneSolo);
	  } catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
	  
	  return stageSolo;
}

 }


