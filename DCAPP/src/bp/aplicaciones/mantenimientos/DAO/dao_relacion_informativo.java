package bp.aplicaciones.mantenimientos.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_informativo_usuario;

public class dao_relacion_informativo {

	public List<modelo_relacion_informativo_usuario> obtenerRelacionesInformativos(String informativo, String usuario,
			int tipo) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		PreparedStatement consulta = null;
		List<modelo_relacion_informativo_usuario> lista_relacion = new ArrayList<modelo_relacion_informativo_usuario>();
		try {
			consulta = conexion.abrir().prepareStatement("{CALL relacion_obtenerRelacionesInformativos(?, ?, ?)}");
			consulta.setString(1, informativo);
			consulta.setString(2, usuario);
			consulta.setInt(3, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_relacion.add(new modelo_relacion_informativo_usuario(resultado.getLong("id_relacion"),
						resultado.getLong("id_informativo"), resultado.getLong("id_usuario"),
						resultado.getString("esta_leido"), resultado.getString("est_relacion"),
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
		return lista_relacion;
	}

	public void insertarRelacion(modelo_relacion_informativo_usuario relacion) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		PreparedStatement consulta = null;
		conexion.abrir().setAutoCommit(false);
		try {
			consulta = conexion.abrir()
					.prepareStatement("{CALL relacion_insertarRelacionesInformativos (?, ?, ?, ?, ?, ?)}");
			consulta.setLong(1, relacion.getId_informativo());
			consulta.setLong(2, relacion.getId_usuario());
			consulta.setString(3, relacion.getEsta_leido());
			consulta.setString(4, relacion.getEst_relacion());
			consulta.setString(5, relacion.getUsu_ingresa());
			consulta.setTimestamp(6, relacion.getFec_ingresa());
			consulta.executeUpdate();
			consulta.close();
			conexion.abrir().commit();
		} catch (SQLException ex) {
			conexion.abrir().rollback();
			throw new SQLException(ex);
		} catch (java.lang.NullPointerException ex) {
			conexion.abrir().rollback();
			throw new java.lang.NullPointerException();
		} finally {
			conexion.cerrar();
		}
	}

}
