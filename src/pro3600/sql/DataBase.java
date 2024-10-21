package pro3600.sql;


//Attention l'usage des classes de java.sql nécessite l'ajout de "requires java.sql;" au module-info.
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Mini classe d'exemple pour l'usage de SGBD avec les pilotes Jdbc. 
 * @author Éric Lallet.
 *
 */
public class DataBase implements AutoCloseable {

	/**
	 * Référence sur la connexion au serveur.
	 */
	private Connection conn;

	/**
	 * Constructeur qui ouvre une connexion avec la base dbName d'un SGBD géré par le JdbcDriver driver.
	 * @param driver
	 *			Information de le type de SGBD utilisé.
	 * @param host
	 * 			nom de la machine hébergeant le SGBD.
	 * @param port
	 * 			port utilisé par le SGBD.
	 * @param dbName
	 * 			nom de la base de données.
	 * @param user
	 * 			id de l'utilisateur utilisé pour se connecter à la base.
	 * @param passwd
	 * 			mot de passe de l'utilisateur.
	 * @throws ClassNotFoundException
	 * 			exception levée si la classe du pilote jdcb n'est pas trouvée.
	 * @throws SQLException
	 * 			exceptions pouvant se produire lors de la connexion  à la base. 
	 */
	public DataBase(final JdbcDriver driver, final String host, final int port,
			final String dbName, final String user, final String passwd) throws ClassNotFoundException, SQLException {
		// chargement de la classe du pilote. Le pilote doit avoir été ajouté au modulepath du projet.
		driver.load();
		
		// construction de l'url de la base de données
		String dbUrl = driver.getUrlPrefix() + "//" + host + ":" + port + "/" + dbName;
		
		// connexion à la base
		conn = DriverManager.getConnection(dbUrl, user, passwd);

	}
	
	/**
	 * Calcule le résultat d'une requête SQL.
	 * @param query
	 *        la requête à exécuter.
	 * @return
	 *        le résultat de la requête.
	 * @throws SQLException
	 *          Exceptions levées en cas d'erreur lors de la création ou l'exécution de la requête.
	 */
	public ResultSet executeQuery(final String query) throws SQLException {
		Statement stmt = conn.createStatement();
	      return stmt.executeQuery(query);
	}
	
	
	/**
	 * Réalise toutes les requêtes de type update (update, insert, create, delete).
	 * @param updateSql
	 * 			la requête d'update.
	 * @throws SQLException
	 * 			 Exceptions levées en cas d'erreur lors de la préparation ou l'execution.
	 */
	public void updateStatement(final String updateSql) throws SQLException {
		PreparedStatement updateStmt = conn.prepareStatement(updateSql); 
		updateStmt.executeUpdate();
	}
	
	/**
	 * Réalise une requête d'insertion. 
	 * @param insertSql
	 *        la requête d'insertion.
	 * @throws SQLException
	 *          Exceptions levées en cas d'erreur lors de la préparation ou l'insertion.
	 */
	public void executeInsert(final String insertSql) throws SQLException {
		this.updateStatement(insertSql); // requête de type update
	}
	

	/**
     * Réalise une requête de suppression.
     * @param deleteSql
     *        la requête de supression.
     * @throws SQLException
     *          Exceptions levées en cas d'erreur lors de la préparation ou la supression.
     */
    public void executeDelete(final String deleteSql) throws SQLException {
    	this.updateStatement(deleteSql); // requête de type update
    }
    
    
    /**
     * 
     */
    public void createTable(final String createSql) throws SQLException {
    	this.updateStatement(createSql); // requête de type update
    }
    
    
    
	@Override
	public void close() throws SQLException {
		conn.close();
	}



}
