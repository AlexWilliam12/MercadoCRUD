package env_variables;

public final class BDConnection {
	
	private static final String SERVER = "jdbc:mysql://localhost:3306/mercadoDB";
	private static final String USER = "root";
	private static final String PASSWORD = "P@$$w0rd";
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";

	public static String getDriver() {
		return DRIVER;
	}

	public static String getPassword() {
		return PASSWORD;
	}

	public static String getServer() {
		return SERVER;
	}

	public static String getUser() {
		return USER;
	}
}
