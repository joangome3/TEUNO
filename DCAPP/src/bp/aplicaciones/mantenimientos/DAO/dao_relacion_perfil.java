package bp.aplicaciones.mantenimientos.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_perfil_mantenimiento;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_perfil_opcion;

public class dao_relacion_perfil {

	public boolean obtenerRelacionesMantenimientos(String perfil, String mantenimiento, int tipo)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		PreparedStatement consulta = null;
		modelo_relacion_perfil_mantenimiento relacion = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL relacion_obtenerRelacionesPerfiles(?, ?, ?)}");
			consulta.setString(1, perfil);
			consulta.setString(2, mantenimiento);
			consulta.setInt(3, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				relacion = new modelo_relacion_perfil_mantenimiento(resultado.getLong("id_relacion"),
						resultado.getLong("id_perfil"), resultado.getLong("id_mantenimiento"),
						resultado.getString("est_relacion"), resultado.getString("usu_ingresa"),
						resultado.getTimestamp("fec_ingresa"), resultado.getString("usu_modifica"),
						resultado.getTimestamp("fec_modifica"));
			}
			resultado.close();
			consulta.close();
			if (relacion != null) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		} catch (java.lang.NullPointerException ex) {
			throw new java.lang.NullPointerException();
		} finally {
			conexion.cerrar();
		}
	}

	public boolean obtenerRelacionesOpciones(String perfil, String opcion, int tipo)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		PreparedStatement consulta = null;
		modelo_relacion_perfil_opcion relacion = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL relacion_obtenerRelacionesPerfiles(?, ?, ?)}");
			consulta.setString(1, perfil);
			consulta.setString(2, opcion);
			consulta.setInt(3, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				relacion = new modelo_relacion_perfil_opcion(resultado.getLong("id_relacion"),
						resultado.getLong("id_perfil"), resultado.getLong("id_opcion"),
						resultado.getString("est_relacion"), resultado.getString("usu_ingresa"),
						resultado.getTimestamp("fec_ingresa"), resultado.getString("usu_modifica"),
						resultado.getTimestamp("fec_modifica"));
			}
			resultado.close();
			consulta.close();
			if (relacion != null) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException ex) {
			throw new SQLException(ex);
		} catch (java.lang.NullPointerException ex) {
			throw new java.lang.NullPointerException();
		} finally {
			conexion.cerrar();
		}
	}


	public void insertarRelacion(List<modelo_relacion_perfil_mantenimiento> relacionMantenimiento,
			List<modelo_relacion_perfil_opcion> relacionOpcion, long id_perfil) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		PreparedStatement consulta = null;
		conexion.abrir().setAutoCommit(false);
		try {
			consulta = conexion.abrir()
					.prepareStatement("{CALL relacion_eliminarMantenimientoPerfiles(?)}");
			consulta.setLong(1, id_perfil);
			consulta.executeUpdate();
			for (int i = 0; i < relacionMantenimiento.size(); i++) {
				Long id = (long) 0;
				consulta = conexion.abrir().prepareStatement(
						"{CALL relacion_obtenerNuevoIDMantenimientoPerfiles()}");
				ResultSet resultado = consulta.executeQuery();
				while (resultado.next()) {
					id = resultado.getLong("id_relacion") + 1;
				}
				consulta = conexion.abrir().prepareStatement(
						"{CALL relacion_insertarMantenimientoPerfiles (?, ?, ?, ?, ?, ?)}");
				consulta.setLong(1, id);
				consulta.setLong(2, relacionMantenimiento.get(i).getId_perfil());
				consulta.setLong(3, relacionMantenimiento.get(i).getId_mantenimiento());
				consulta.setString(4, relacionMantenimiento.get(i).getEst_relacion());
				consulta.setString(5, relacionMantenimiento.get(i).getUsu_ingresa());
				consulta.setTimestamp(6, relacionMantenimiento.get(i).getFec_ingresa());
				consulta.executeUpdate();
			}
			consulta = conexion.abrir()
					.prepareStatement("{CALL relacion_eliminarOpcionPerfiles(?)}");
			consulta.setLong(1, id_perfil);
			consulta.executeUpdate();
			for (int i = 0; i < relacionOpcion.size(); i++) {
				Long id = (long) 0;
				consulta = conexion.abrir().prepareStatement(
						"{CALL relacion_obtenerNuevoIDOpcionPerfiles()}");
				ResultSet resultado = consulta.executeQuery();
				while (resultado.next()) {
					id = resultado.getLong("id_relacion") + 1;
				}
				consulta = conexion.abrir().prepareStatement(
						"{CALL relacion_insertarOpcionPerfiles (?, ?, ?, ?, ?, ?)}");
				consulta.setLong(1, id);
				consulta.setLong(2, relacionOpcion.get(i).getId_perfil());
				consulta.setLong(3, relacionOpcion.get(i).getId_opcion());
				consulta.setString(4, relacionOpcion.get(i).getEst_relacion());
				consulta.setString(5, relacionOpcion.get(i).getUsu_ingresa());
				consulta.setTimestamp(6, relacionOpcion.get(i).getFec_ingresa());
				consulta.executeUpdate();
			}
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
