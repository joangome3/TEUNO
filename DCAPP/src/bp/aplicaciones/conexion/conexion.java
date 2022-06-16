package bp.aplicaciones.conexion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class conexion {

	public Connection cnx = null;
	private String URL = "jdbc:mysql://localhost/dcap_bp?zeroDateTimeBehavior=convertToNull";
	private String user = "sibod";
	private String pass = "$1b0d";

	/*
	 * Metodo que permite realizar conexion a la base de datos MySQL
	 */

	public Connection abrir() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		if (cnx == null) {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				cnx = DriverManager.getConnection(URL, user, pass);
			} catch (SQLException ex) {
				System.out.println("No se puede conectar a la base de datos!");
				throw new SQLException(ex);
			} catch (ClassNotFoundException ex) {
				System.out.println("No se puede conectar a la base de datos!");
				throw new ClassNotFoundException(ex.getMessage());
			}
		}
		/* Se anade comentario */
		return cnx;
	}

	public void cerrar() throws SQLException {
		if (cnx != null) {
			cnx.close();
		}
	}

}