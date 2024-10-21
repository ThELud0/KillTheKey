package pro3600.application;

import pro3600.sql.JdbcDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import pro3600.sql.DataBase;

/**
 * Classe d'exemple d'usage de la classe pro3600.DataBase.
 * @author Éric Lallet, modifié par Ludovic et Mai
 *
 */
public class SqlBase implements AutoCloseable {
	/**
	 * Type du SGBD utilisé.
	 */
	private final static JdbcDriver DRIVER = JdbcDriver.MYSQL;
	/**
	 * machine hébergeant le SGBD.
	 */
	private final static String HOST = "localhost";
	/**
	 * port utilisé par le SGBD.
	 */
	private final static int PORT = 3306;
	/**
	 * nom de la base utilisée.
	 */
	private final static String BASE = "exemple";


	/**
	 * référence sur l'objet de la classe DataBase.
	 */
	private DataBase sgbd; 
	private Connection conn;

	/**
	 * Contructeur qui initialise l'attrobut sgbd.
	 * @param user
	 * 			nom de l'utilisateur ayant les accès au sbgd.
	 * @param passwd
	 * 			mot de passe de cet utilisateurs pour l'accès au sgbd.
	 * @throws ClassNotFoundException
	 * 			exception levée si la classe du pilote jdcb n'est pas trouvée.
	 * @throws SQLException
	 * 			exceptions pouvant se produire lors de la connexion  à la base. 
	 */
	public SqlBase( final String user, final String passwd) throws ClassNotFoundException, SQLException {
		sgbd = new DataBase(DRIVER, HOST, PORT, BASE, user, passwd);
		DRIVER.load();
		// construction de l'url de la base de données
		String dbUrl = DRIVER.getUrlPrefix() + "//" + HOST + ":" + PORT + "/" + BASE;
		// connexion à la base
		conn = DriverManager.getConnection(dbUrl, user, passwd);
	}


	

	/*****programme ci-dessous écrit par Ludovic *****/



	public String demande;

	/***on change le contenu de la requête sql***/
	public void tienstademande(String q) {
		demande = q;
	}

	/***on envoie la requête****/
	public void obeitmoi() throws SQLException {
		sgbd.updateStatement(demande);
	}


	/***recupere les noms de la table JOUEURS************/
	public List<String> yaqui() throws SQLException {
		ResultSet resultat = sgbd.executeQuery("SELECT nom FROM joueurs;");
		List<String> noms = new ArrayList<String>();
		// il faut utiliser la méthode next() pour obtenir une nouvelle ligne de résultat.
		while (resultat.next()) {
			noms.add(resultat.getString("nom"));
		}
		resultat.close();
		return noms;
	}


	public void changeimage(String quoi,String enquoi, int id) {
		demande = "UPDATE `exemple`.`joueurs` SET `" + quoi + "` = '" + enquoi + "' WHERE (`id` = '" + id + "');" ;
	}
	

	/****recupere les ids de la table JOUEURS*************/
	public List<Integer> yacb() throws SQLException {
		ResultSet resultat = sgbd.executeQuery("SELECT id FROM joueurs;");
		List<Integer> ids = new ArrayList<Integer>();
		// il faut utiliser la méthode next() pour obtenir une nouvelle ligne de résultat.
		while (resultat.next()) {
			ids.add(resultat.getInt("id"));
		}
		resultat.close();
		return ids;
	}

	/***recupere un id particulier lié à un nom dans la table***/
	public int getid(String nom) throws SQLException {
		ResultSet resultat = sgbd.executeQuery("SELECT id FROM joueurs WHERE nom = '" + nom + "';");
		List<Integer> ids = new ArrayList<Integer>();
		// il faut utiliser la méthode next() pour obtenir une nouvelle ligne de résultat.
		while (resultat.next()) {
			ids.add(resultat.getInt("id"));
		}
		resultat.close();
		return ids.get(0);
	}

	/***recupere un nom particulier lié à un id dans la table***/
	public String getnom(int id) throws SQLException {
		ResultSet resultat = sgbd.executeQuery("SELECT nom FROM joueurs WHERE id = " + id + ";");
		List<String> noms = new ArrayList<String>();
		// il faut utiliser la méthode next() pour obtenir une nouvelle ligne de résultat.
		while (resultat.next()) {
			noms.add(resultat.getString("nom"));
		}
		resultat.close();
		return noms.get(0);
	}


	public String getfighter(int id) throws SQLException {
		ResultSet resultat = sgbd.executeQuery("SELECT fighter FROM joueurs WHERE id = " + id + ";");
		List<String> noms = new ArrayList<String>();
		// il faut utiliser la méthode next() pour obtenir une nouvelle ligne de résultat.
		while (resultat.next()) {
			noms.add(resultat.getString("fighter"));
		}
		resultat.close();
		return noms.get(0);
	}

	public String gethome(int id) throws SQLException {
		ResultSet resultat = sgbd.executeQuery("SELECT home FROM joueurs WHERE id = " + id + ";");
		List<String> noms = new ArrayList<String>();
		// il faut utiliser la méthode next() pour obtenir une nouvelle ligne de résultat.
		while (resultat.next()) {
			noms.add(resultat.getString("home"));
		}
		resultat.close();
		return noms.get(0);
	}

	public String getennemies(int id) throws SQLException {
		ResultSet resultat = sgbd.executeQuery("SELECT ennemies FROM joueurs WHERE id = " + id + ";");
		List<String> noms = new ArrayList<String>();
		// il faut utiliser la méthode next() pour obtenir une nouvelle ligne de résultat.
		while (resultat.next()) {
			noms.add(resultat.getString("ennemies"));
		}
		resultat.close();
		return noms.get(0);
	}

	public String getuniverse(int id) throws SQLException {
		ResultSet resultat = sgbd.executeQuery("SELECT universe FROM joueurs WHERE id = " + id + ";");
		List<String> noms = new ArrayList<String>();
		// il faut utiliser la méthode next() pour obtenir une nouvelle ligne de résultat.
		while (resultat.next()) {
			noms.add(resultat.getString("universe"));
		}
		resultat.close();
		return noms.get(0);
	}
	
	/***** Fin de la partie de Ludovic dans cette classe *****/
	
	
	
	
	
	
	
	/***** Programme ci-dessous écrit par Mai *****/
	

	
	/*********************************************************************************************************************/
	/*********************************************************************************************************************/
	/************************************* REQUETES POUR RECORDS GLOBAUX *************************************************/
	/*********************************************************************************************************************/
	/*********************************************************************************************************************/
	// pour s'assurer que lors de la création des tableaux de records, les valeurs sur une même ligne
	// soient associées à un même joueur, on ordonne le résultat des requêtes dans le sens des scores décroissants


	public List<String> donnerNomsJoueursSolo() throws SQLException {
		ResultSet resultat = sgbd.executeQuery("SELECT nom FROM joueurs, parties_solo WHERE id=idjoueur ORDER BY score DESC;");
		List<String> listeNoms = new ArrayList<String>();
		while (resultat.next()) {
			listeNoms.add(resultat.getString("nom"));
		}
		resultat.close();
		return listeNoms;
	}

	public List<String> donnerDatesPartiesSolo() throws SQLException {
		ResultSet resultat = sgbd.executeQuery("SELECT dateEtHeure FROM parties_solo ORDER BY score DESC;");
		List<String> dates = new ArrayList<>();
		while (resultat.next()) {
			dates.add(resultat.getString("dateEtHeure"));
		}
		resultat.close();
		return dates;
	}

	public List<Integer> donnerRangsPartiesSolo() throws SQLException {
		ResultSet resultat = sgbd.executeQuery("SELECT row_number() OVER(ORDER BY score DESC) AS rang FROM parties_solo;");
		List<Integer> rangs = new ArrayList<>();
		while (resultat.next()) {
			rangs.add(resultat.getInt("rang"));
		}
		resultat.close();
		return rangs;
	}

	public List<String> donnerDifficultesPartiesSolo() throws SQLException {
		ResultSet resultat = sgbd.executeQuery("SELECT difficulté FROM parties_solo ORDER BY score DESC;");
		List<String> difficultes = new ArrayList<>();
		while (resultat.next()) {
			difficultes.add(resultat.getString("difficulté"));
		}
		resultat.close();
		return difficultes;
	}

	public List<Integer> donnerScoresPartiesSolo() throws SQLException {
		ResultSet resultat = sgbd.executeQuery("SELECT score FROM parties_solo ORDER BY score DESC;");
		List<Integer> scores = new ArrayList<>();
		while (resultat.next()) {
			scores.add(resultat.getInt("score"));
		}
		resultat.close();
		return scores;
	}
	
	

	/*********************************************************************************************************************/
	/*********************************************************************************************************************/
	/************************************* REQUETES POUR RECORDS SOLO *************************************************/
	/*********************************************************************************************************************/
	/*********************************************************************************************************************/
	// on trouve dans cette section les méthodes permettant de récupérer les données relatives au mode de jeu solo
	// du joueur dont l'id est passé en argument
	// ces méthodes sont appelées par la classe MenuRecords
	
	public List<Integer> donnerRangsPartiesSolo(int idJoueur) throws SQLException {
		ResultSet resultat = sgbd.executeQuery("SELECT row_number() OVER(ORDER BY score DESC) AS rangsolo\n"
				+ "FROM parties_solo\n"
				+ "WHERE idjoueur=" + idJoueur +" \n"
				+ "ORDER BY score DESC;");
		List<Integer> soloRanks = new ArrayList<>();
		while (resultat.next()) {
			soloRanks.add(resultat.getInt("rangsolo"));
		}
		resultat.close();
		return soloRanks;
	}
	
	public List<Integer> donnerScoresPartiesSolo(int idJoueur) throws SQLException {
		ResultSet resultat = sgbd.executeQuery("SELECT score\n"
				+ "FROM parties_solo\n"
				+ "WHERE idjoueur=" + idJoueur + " \n"
				+ "ORDER BY score DESC;");
		List<Integer> soloScores = new ArrayList<>();
		while (resultat.next()) {
			soloScores.add(resultat.getInt("score"));
		}
		resultat.close();
		return soloScores;
	}

	public List<String> donnerDifficultePartiesSolo(int idJoueur) throws SQLException {
		ResultSet resultat = sgbd.executeQuery("SELECT difficulté\n"
				+ "FROM parties_solo\n"
				+ "WHERE idjoueur=" + idJoueur + " \n"
				+ "ORDER BY score DESC;");
		List<String> soloModes = new ArrayList<>();
		while (resultat.next()) {
			soloModes.add(resultat.getString("difficulté"));
		}
		resultat.close();
		return soloModes;
	}

	public List<String> donnerDatesPartiesSolo(int idJoueur) throws SQLException {
		ResultSet resultat = sgbd.executeQuery("SELECT dateEtHeure \n"
				+ "FROM parties_solo\n"
				+ "WHERE idjoueur=" + idJoueur + " \n"
				+ "ORDER BY score DESC;\n"
				+ "");
		List<String> soloDates = new ArrayList<>();
		while (resultat.next()) {
			soloDates.add(resultat.getString("dateEtHeure"));
		}
		resultat.close();
		return soloDates;
	}

	/*********************************************************************************************************************/
	/*********************************************************************************************************************/
	/************************************* REQUETES POUR RECORDS DUELS ***************************************************/
	/*********************************************************************************************************************/
	/*********************************************************************************************************************/

	public List<String> donnerNomsAdversaires(int idJoueur) throws SQLException {
		ResultSet resultat = sgbd.executeQuery("(SELECT nom\n"
				+ "FROM duels, joueurs\n"
				+ "WHERE id2=id AND id1=" + idJoueur + ") \n"
				+ "UNION \n"
				+ "(SELECT nom\n"
				+ "FROM joueurs,duels\n"
				+ "WHERE id1=id AND id2=" + idJoueur + ");");
		List<String> adversaires = new ArrayList<>();
		while (resultat.next()) {
			adversaires.add(resultat.getString("nom"));
		}
		resultat.close();
		return adversaires;
	}

	public List<Integer> donnerIdsAdversaires(int idJoueur) throws SQLException {
		ResultSet resultat = sgbd.executeQuery("(SELECT id\n"
				+ "FROM duels, joueurs\n"
				+ "WHERE id2=id AND id1=" + idJoueur + ") \n"
				+ "UNION \n"
				+ "(SELECT id\n"
				+ "FROM joueurs,duels\n"
				+ "WHERE id1=id AND id2=" + idJoueur + ");");
		List<Integer> idsAdv = new ArrayList<>();
		while (resultat.next()) {
			idsAdv.add(resultat.getInt("id"));
		}
		resultat.close();
		return idsAdv;
	}

	public int nbTotalDuels(int id1, int id2) throws SQLException {
		ResultSet resultat = sgbd.executeQuery("select sum(nbDuelsFacile + nbDuelsDifficile + nbDuelsIntermediaire) as nbTotalDuels\n"
				+ "from duels\n"
				+ "where (id1= " + id1 + " AND id2= " + id2 + ") or (id1=" + id2 + " and id2=" + id1 + ") ;");
		List<Integer> nb = new ArrayList<Integer>();
		while (resultat.next()) {
			nb.add(resultat.getInt("nbTotalDuels"));
		}
		resultat.close();
		return nb.get(0);
	}

	public int nbTotalEgalites(int id1, int id2) throws SQLException {
		ResultSet resultat = sgbd.executeQuery("select sum(nbEgaliteFacile + nbEgaliteDifficile + nbEgaliteIntermediaire) as nbTotalEgalite\n"
				+ "from duels\n"
				+ "where (id1= " + id1 + " AND id2= " + id2 + ") or (id1=" + id2 + " and id2=" + id1 + ") ;");
		List<Integer> nbEgal = new ArrayList<Integer>();
		while (resultat.next()) {
			nbEgal.add(resultat.getInt("nbTotalEgalite"));
		}
		resultat.close();
		return nbEgal.get(0);
	}

	public boolean verifierExistenceLigneBDD (int id1, int id2) throws SQLException {
		String query = "select exists (select * from duels where (id1=" + id1 + " and id2=" + id2 + ")) as bool";
		ResultSet resultat = sgbd.executeQuery(query);
		List<Integer> res = new ArrayList<Integer>();
		while (resultat.next()) {
			res.add(resultat.getInt("bool"));
		}
		resultat.close();
		return res.get(0)==1;
	}
	
	public int nbTotalVictoires(int id1, int id2) throws SQLException {
		boolean ligneExiste = verifierExistenceLigneBDD(id1, id2);
		
		if (ligneExiste) { //on n'a qu'à récupérer le nombre de victoires sur cette ligne
			List<Integer> nbWin = new ArrayList<Integer>();
			ResultSet resultat = sgbd.executeQuery("select (nbVictoiresFacile + nbVictoiresDifficile + nbVictoiresIntermediaire) as nbTotalVictoires\n"
					+ "from duels\n"
					+ "where (id1=" + id1 + " and id2=" + id2 + ")");
			while (resultat.next()) {
				nbWin.add(resultat.getInt("nbTotalVictoires"));
			}
			resultat.close();
			return nbWin.get(0);


		} else { // si la ligne n'existe pas, c'est que les informations sont référencées dans une ligne où id1 est en deuxième position
				 // pour obtenir le nombre de victoires, il faut faire la différence entre le nombre de parties jouées
				 // et le ( nombre de parties gagnées par le joueur 2 + le nombre de match nuls)

			ResultSet resultat = sgbd.executeQuery("select (nbDuelsFacile + nbDuelsDifficile + nbDuelsIntermediaire) as nbTotalDuels\n"
					+ "from duels\n"
					+ "where (id1=" + id2 + " and id2=" + id1 + ")");
			List<Integer> nb = new ArrayList<Integer>();
			while (resultat.next()) {
				nb.add(resultat.getInt("nbTotalDuels"));
			}
			resultat.close();
			List<Integer> nbWinEgAdv = new ArrayList<Integer>();
			ResultSet resultat1 = sgbd.executeQuery("select (nbVictoiresFacile + nbVictoiresDifficile + nbVictoiresIntermediaire + nbEgaliteFacile + nbEgaliteDifficile + nbEgaliteIntermediaire) as nbTotalVicEgAdv\n"
					+ "from duels\n"
					+ "where (id1=" + id2 + " and id2=" + id1 + ")");
			while (resultat1.next()) {
				nbWinEgAdv.add(resultat1.getInt("nbTotalVicEgAdv"));
			}
			resultat1.close();
			return nb.get(0) - nbWinEgAdv.get(0);
			

		}

	}





	/*********************************************************************************************************************/
	/*********************************************************************************************************************/
	/************************************* REMPLISSAGE DE LA BD *************************************************/
	/*********************************************************************************************************************/
	/*********************************************************************************************************************/
	// Les méthodes qui suivent sont appelées dans les classes PartieSolo et PartieDuel pour enregistrer les informations relatives
	// à chaque partie dès la fin d'une partie.
	
	
	private String difficulteIntToString(int choixDifficulte) {
		if (choixDifficulte == 1) {
			return "Facile";
		} else if (choixDifficulte == 2 ) {
			return "Intermediaire";
		} else if (choixDifficulte == 3) {
			return "Difficile";
		} else if (choixDifficulte == 4) {
			return "Impossible";
		} else {
			return null;
		}
	}
	
	private void mettreAJourBDD(String query) throws SQLException {
		Statement stmt = conn.createStatement();
		stmt.executeUpdate(query);
	}
	
	public void insererPartieSolo (int idJoueur, int choixDifficulte, int score, LocalDateTime date) throws SQLException {		
		String difficulte = difficulteIntToString(choixDifficulte);
		String insertSql = "INSERT INTO parties_solo (idjoueur, difficulté, dateEtHeure, score) VALUES (" + idJoueur + ", '" + difficulte + "', " + "'" + date + "'" + ", " + score + ")"; 
		mettreAJourBDD(insertSql);
	}
	
	public void insererPartieDuel(int id1, int id2, int idWinner, int choixDifficulte) throws SQLException {
		String insertSql = null;
		if (idWinner == 0) { // càd s'il y a eu match nul
			if (choixDifficulte == 1) {
				insertSql = "insert into duels(id1, id2, nbDuelsFacile, nbDuelsIntermediaire, nbDuelsDifficile, nbVictoiresFacile, nbVictoiresIntermediaire, nbVictoiresDifficile, nbEgaliteFacile, nbEgaliteIntermediaire, nbEgaliteDifficile) \n"
						+ "values(" + id1 + ", " + id2 + ", 1, 0, 0, 0, 0, 0, 1, 0, 0)";
			} else if (choixDifficulte == 2) {
				insertSql = "insert into duels(id1, id2, nbDuelsFacile, nbDuelsIntermediaire, nbDuelsDifficile, nbVictoiresFacile, nbVictoiresIntermediaire, nbVictoiresDifficile, nbEgaliteFacile, nbEgaliteIntermediaire, nbEgaliteDifficile) \n"
						+ "values(" + id1 + ", " + id2 + ", 0, 1, 0, 0, 0, 0, 0, 1, 0)";
			} else if (choixDifficulte == 3) {
				insertSql = "insert into duels(id1, id2, nbDuelsFacile, nbDuelsIntermediaire, nbDuelsDifficile, nbVictoiresFacile, nbVictoiresIntermediaire, nbVictoiresDifficile, nbEgaliteFacile, nbEgaliteIntermediaire, nbEgaliteDifficile) \n"
						+ "values(" + id1 + ", " + id2 + ", 0, 0, 1, 0, 0, 0, 0, 0, 1)";
			}

		} else if (idWinner == id1){ //si le joueur 1 a gagné
			if (choixDifficulte == 1) {
				insertSql = "insert into duels(id1, id2, nbDuelsFacile, nbDuelsIntermediaire, nbDuelsDifficile, nbVictoiresFacile, nbVictoiresIntermediaire, nbVictoiresDifficile, nbEgaliteFacile, nbEgaliteIntermediaire, nbEgaliteDifficile) \n"
						+ "values(" + id1 + ", " + id2 + ", 1, 0, 0, 1, 0, 0, 0, 0, 0)";
			} else if (choixDifficulte == 2) {
				insertSql = "insert into duels(id1, id2, nbDuelsFacile, nbDuelsIntermediaire, nbDuelsDifficile, nbVictoiresFacile, nbVictoiresIntermediaire, nbVictoiresDifficile, nbEgaliteFacile, nbEgaliteIntermediaire, nbEgaliteDifficile) \n"
						+ "values(" + id1 + ", " + id2 + ", 0, 1, 0, 0, 1, 0, 0, 0, 0)";
			} else if (choixDifficulte == 3) {
				insertSql = "insert into duels(id1, id2, nbDuelsFacile, nbDuelsIntermediaire, nbDuelsDifficile, nbVictoiresFacile, nbVictoiresIntermediaire, nbVictoiresDifficile, nbEgaliteFacile, nbEgaliteIntermediaire, nbEgaliteDifficile) \n"
						+ "values(" + id1 + ", " + id2 + ", 0, 0, 1, 0, 0, 1, 0, 0, 0)";
			}

		} else if (idWinner == id2) { //si le joueur2 a gagné
			if (choixDifficulte == 1) {
				insertSql = "insert into duels(id1, id2, nbDuelsFacile, nbDuelsIntermediaire, nbDuelsDifficile, nbVictoiresFacile, nbVictoiresIntermediaire, nbVictoiresDifficile, nbEgaliteFacile, nbEgaliteIntermediaire, nbEgaliteDifficile) \n"
						+ "values(" + id2 + ", " + id1 + ", 1, 0, 0, 1, 0, 0, 0, 0, 0)";
			} else if (choixDifficulte == 2) {
				insertSql = "insert into duels(id1, id2, nbDuelsFacile, nbDuelsIntermediaire, nbDuelsDifficile, nbVictoiresFacile, nbVictoiresIntermediaire, nbVictoiresDifficile, nbEgaliteFacile, nbEgaliteIntermediaire, nbEgaliteDifficile) \n"
						+ "values(" + id2 + ", " + id1 + ", 0, 1, 0, 0, 1, 0, 0, 0, 0)";
			} else if (choixDifficulte == 3) {
				insertSql = "insert into duels(id1, id2, nbDuelsFacile, nbDuelsIntermediaire, nbDuelsDifficile, nbVictoiresFacile, nbVictoiresIntermediaire, nbVictoiresDifficile, nbEgaliteFacile, nbEgaliteIntermediaire, nbEgaliteDifficile) \n"
						+ "values(" + id2 + ", " + id1 + ", 0, 0, 1, 0, 0, 1, 0, 0, 0)";
			}
		}
		mettreAJourBDD(insertSql);
	}
	
	public void mettreAJourLigneDuelEgalite (int id1, int id2, int choixDifficulte) throws SQLException {
		String difficulte = difficulteIntToString(choixDifficulte);
		int nbDuels;
		int nbEgalite;
		
		//on récupère les valeurs à incrémenter
		String query = "select nbDuels" + difficulte + " , nbEgalite" + difficulte + " from duels where (id1=" + id1 + " and id2=" + id2 + ")" ;
		ResultSet resultat = sgbd.executeQuery(query);
		List<Integer> res = new ArrayList<Integer>();
		while (resultat.next()) {
			res.add(resultat.getInt("nbDuels" + difficulte));
			res.add(resultat.getInt("nbEgalite" + difficulte));
		}
		resultat.close();
		
		//on incrémente les valeurs
		nbDuels = res.get(0) + 1;
		nbEgalite = res.get(1) + 1;
		
		//mise à jour de la ligne concernée
		String majDuels = "UPDATE duels SET nbDuels" + difficulte + " =" + nbDuels + " WHERE (id1=" + id1 + " and id2=" + id2 + ")";
		String majEgalite ="UPDATE duels SET nbEgalite" + difficulte + " =" + nbEgalite + " WHERE (id1=" + id1 + " and id2=" + id2 + ")";
		mettreAJourBDD(majDuels);
		mettreAJourBDD(majEgalite);
	}
	
	public void mettreAJourLigneDuelVictoire (int id1, int id2, int choixDifficulte) throws SQLException {
		String difficulte = difficulteIntToString(choixDifficulte);
		int nbDuels;
		int nbVictoires;
		
		//on récupère les valeurs à incrémenter
		String query = "select nbDuels" + difficulte + " , nbVictoires" + difficulte + " from duels where (id1=" + id1 + " and id2=" + id2 + ")" ;
		ResultSet resultat = sgbd.executeQuery(query);
		List<Integer> res = new ArrayList<Integer>();
		while (resultat.next()) {
			res.add(resultat.getInt("nbDuels" + difficulte));
			res.add(resultat.getInt("nbVictoires" + difficulte));
		}
		resultat.close();
		
		//on incrémente les valeurs
		nbDuels = res.get(0) + 1;
		nbVictoires = res.get(1) + 1;
		
		//mise à jour de la ligne concernée
		String majDuels = "UPDATE duels SET nbDuels" + difficulte + " =" + nbDuels + " WHERE (id1=" + id1 + " and id2=" + id2 + ")";
		String majVictoires ="UPDATE duels SET nbVictoires" + difficulte + " =" + nbVictoires + " WHERE (id1=" + id1 + " and id2=" + id2 + ")";
		mettreAJourBDD(majDuels);
		mettreAJourBDD(majVictoires);

	}
	
	public void mettreAJourLigneDuelDefaite (int id1, int id2, int choixDifficulte) throws SQLException {
		String difficulte = difficulteIntToString(choixDifficulte);
		int nbDuels;
		
		//on récupère le nombre de duels et on l'incrémente
		String query = "select nbDuels" + difficulte + " from duels where (id1=" + id1 + " and id2=" + id2 + ")";
		ResultSet resultat = sgbd.executeQuery(query);
		List<Integer> nb = new ArrayList<Integer>();
		while (resultat.next()) {
			nb.add(resultat.getInt("nbDuels" + difficulte));
		}
		resultat.close();
		nbDuels = nb.get(0) + 1;

		//mise à jour de la ligne concernée
		String majDuels = "UPDATE duels SET nbDuels" + difficulte + " =" + nbDuels + " WHERE (id1=" + id1 + " and id2=" + id2 + ")";
		mettreAJourBDD(majDuels);

	}







	@Override
	public void close() throws Exception {
		sgbd.close();
	}



}