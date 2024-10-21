package pro3600.application;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pro3600.application.SqlBase;
import pro3600.sql.DataBase;
import pro3600.sql.JdbcDriver;

/**
 * @author mai
 * L'intégralité des méthodes publiques que j'ai écrites s'appuient sur une connexion à une 
 * BDD. J'ai pu lire qu'il fallait créer une BDD virtuelle pour tester de telles méthodes
 * mais cela m'a paru très compliqué à mettre en place. J'effectue donc mes tests sur une base locale.
 */
class SqlBaseTest {

	private final static JdbcDriver DRIVER = JdbcDriver.MYSQL;
	private final static String HOST = "localhost";
	private final static int PORT = 3306;
	private final static String BASE = "exemple";

	private SqlBase sqlBase;
	private DataBase sgbd; 
	private Connection conn;
	private Statement statement;


	void setUpDonnerNomJoueursSoloTest() throws SQLException {
		String query = "insert into joueurs(id, nom) values (1, 'chip');" +
				"insert into joueurs(id, nom) values (2, 'herisson');" +
				"insert into parties_solo(idjoueur, score, dateEtHeure) values (1 , 444, '2003-04-24');" +
				"insert into parties_solo(idjoueur, score, dateEtHeure) values (2, 500, '2004-04-24');";
		statement.executeUpdate(query);
	}


	@BeforeAll
	void setUp() throws Exception {
		sqlBase = new SqlBase("root","18042002");
		/* 
		String query = "truncate table parties_solo;"
				+ "truncate table duels;"
				+ "delete from joueurs where (id >= 0);";
		statement.executeUpdate(query);*/
	}

	@Test
	void donnerNomJoueursSoloTest() throws SQLException {
		// setup
		setUpDonnerNomJoueursSoloTest();

		// test
		List<String> listeAttendue = Arrays.asList("herisson", "chip");
		assertEquals(listeAttendue, sqlBase.donnerNomsJoueursSolo(), "La liste de noms est incorrecte");	
	}

	@AfterAll
	void tearDown() throws Exception {
		conn.close();
	}

}
