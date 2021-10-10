package bp.aplicaciones.sibod.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.sibod.modelo.modelo_mail_destinatarios;
import bp.aplicaciones.sibod.modelo.modelo_mail_parametros;

public class dao_mail {

	public List<modelo_mail_parametros> obtenerParametros(String criterio, int tipo)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_mail_parametros> lista_parametros = new ArrayList<modelo_mail_parametros>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL mail_parametro_obtenerParametros(?, ?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setInt(2, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_parametros.add(new modelo_mail_parametros(resultado.getLong("id_parametro"),
						resultado.getString("email_remitente"), resultado.getString("pass_remitente"),
						resultado.getString("email_receptor"), resultado.getString("smtp_host"),
						resultado.getString("smtp_starttls"), resultado.getString("smtp_puerto"),
						resultado.getString("smtp_auth"), resultado.getString("smtp_trust"),
						resultado.getString("smtp_debug"), resultado.getString("est_parametro"),
						resultado.getString("usu_ingresa"), resultado.getTimestamp("fec_ingresa"),
						resultado.getString("usu_modifica"), resultado.getTimestamp("fec_modifica")));

			}
			resultado.close();
			consulta.close();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		} catch (java.lang.NullPointerException ex) {
			throw new java.lang.NullPointerException();
		} finally {
			conexion.cerrar();
		}
		return lista_parametros;
	}

	public List<modelo_mail_destinatarios> obtenerDestinatarios(String criterio, int tipo)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_mail_destinatarios> lista_destinatarios = new ArrayList<modelo_mail_destinatarios>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL mail_destinatario_obtenerDestinatarios(?, ?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setInt(2, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_destinatarios.add(new modelo_mail_destinatarios(resultado.getLong("id_destinatario"),
						resultado.getLong("id_mail_parametro"), resultado.getString("email_destinatario"),
						resultado.getString("est_destinatario"), resultado.getString("usu_ingresa"),
						resultado.getTimestamp("fec_ingresa"), resultado.getString("usu_modifica"),
						resultado.getTimestamp("fec_modifica")));
			}
			resultado.close();
			consulta.close();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		} catch (java.lang.NullPointerException ex) {
			throw new java.lang.NullPointerException();
		} finally {
			conexion.cerrar();
		}
		return lista_destinatarios;
	}

}
