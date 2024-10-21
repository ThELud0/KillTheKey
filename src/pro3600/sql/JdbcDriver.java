package pro3600.sql;

public enum JdbcDriver {
	MYSQL("com.mysql.cj.jdbc.Driver", "jdbc:mysql:"),
	POSTGRESQL("org.postgresql.Driver", "jdbc:postgresql:" ),
	MARIADB(null, "jdbc:mariadb:"),
	;
	
	
	private String driverClass;
	private String urlPrefix;
	
	private JdbcDriver(final String driverClass, final String urlPrefix) {
		this.driverClass = driverClass;
		this.urlPrefix = urlPrefix;
		
	}

	public String getUrlPrefix() {
		return urlPrefix;
	}

	public void load() throws ClassNotFoundException {
		if (driverClass != null) {
			Class.forName(driverClass);
		}
	}

}
