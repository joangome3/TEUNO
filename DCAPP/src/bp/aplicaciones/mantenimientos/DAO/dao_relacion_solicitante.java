package bp.aplicaciones.mantenimientos.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_solicitante_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_solicitante_mantenimiento;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_solicitante_opcion;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitante;

public class dao_relacion_solicitante {

	public boolean obtenerRelacionesMantenimientos(String solicitante, String mantenimiento)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		PreparedStatement consulta = null;
		modelo_relacion_solicitante_mantenimiento relacion = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL relacion_obtenerRelacionesSolicitantes(?, ?, ?)}");
			consulta.setString(1, solicitante);
			consulta.setString(2, mantenimiento);
			consulta.setInt(3, 1);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				relacion = new modelo_relacion_solicitante_mantenimiento(resultado.getLong("id_relacion"),
						resultado.getLong("id_solicitante"), resultado.getLong("id_mantenimiento"),
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

	public boolean obtenerRelacionesOpciones(String solicitante, String opcion)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		PreparedStatement consulta = null;
		modelo_relacion_solicitante_opcion relacion = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL relacion_obtenerRelacionesSolicitantes(?, ?, ?)}");
			consulta.setString(1, solicitante);
			consulta.setString(2, opcion);
			consulta.setInt(3, 2);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				relacion = new modelo_relacion_solicitante_opcion(resultado.getLong("id_relacion"),
						resultado.getLong("id_solicitante"), resultado.getLong("id_opcion"),
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

	public boolean obtenerRelacionesLocalidades(String solicitante, String localidad)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		PreparedStatement consulta = null;
		modelo_relacion_solicitante_localidad relacion = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL relacion_obtenerRelacionesSolicitantes(?, ?, ?)}");
			consulta.setString(1, solicitante);
			consulta.setString(2, localidad);
			consulta.setInt(3, 3);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				relacion = new modelo_relacion_solicitante_localidad(resultado.getLong("id_relacion"),
						resultado.getLong("id_solicitante"), resultado.getLong("id_localidad"),
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

	public void insertarRelacion(List<modelo_relacion_solicitante_mantenimiento> relacionMantenimiento,
			List<modelo_relacion_solicitante_opcion> relacionOpcion,
			List<modelo_relacion_solicitante_localidad> relacionLocalidad, long id_solicitante,
			modelo_solicitante solicitante, int tipo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		PreparedStatement consulta = null;
		conexion.abrir().setAutoCommit(false);
		try {
			consulta = conexion.abrir().prepareStatement("{CALL relacion_eliminarMantenimientoSolicitantes(?)}");
			consulta.setLong(1, id_solicitante);
			consulta.executeUpdate();
			for (int i = 0; i < relacionMantenimiento.size(); i++) {
				Long id = (long) 0;
				consulta = conexion.abrir()
						.prepareStatement("{CALL relacion_obtenerNuevoIDMantenimientoSolicitantes()}");
				ResultSet resultado = consulta.executeQuery();
				while (resultado.next()) {
					id = resultado.getLong("id_relacion") + 1;
				}
				consulta = conexion.abrir()
						.prepareStatement("{CALL relacion_insertarMantenimientoSolicitantes (?, ?, ?, ?, ?, ?)}");
				consulta.setLong(1, id);
				consulta.setLong(2, relacionMantenimiento.get(i).getId_solicitante());
				consulta.setLong(3, relacionMantenimiento.get(i).getId_mantenimiento());
				consulta.setString(4, relacionMantenimiento.get(i).getEst_relacion());
				consulta.setString(5, relacionMantenimiento.get(i).getUsu_ingresa());
				consulta.setTimestamp(6, relacionMantenimiento.get(i).getFec_ingresa());
				consulta.executeUpdate();
			}
			consulta = conexion.abrir().prepareStatement("{CALL relacion_eliminarOpcionSolicitantes(?)}");
			consulta.setLong(1, id_solicitante);
			consulta.executeUpdate();
			for (int i = 0; i < relacionOpcion.size(); i++) {
				Long id = (long) 0;
				consulta = conexion.abrir().prepareStatement("{CALL relacion_obtenerNuevoIDOpcionSolicitantes()}");
				ResultSet resultado = consulta.executeQuery();
				while (resultado.next()) {
					id = resultado.getLong("id_relacion") + 1;
				}
				consulta = conexion.abrir()
						.prepareStatement("{CALL relacion_insertarOpcionSolicitantes (?, ?, ?, ?, ?, ?)}");
				consulta.setLong(1, id);
				consulta.setLong(2, relacionOpcion.get(i).getId_solicitante());
				consulta.setLong(3, relacionOpcion.get(i).getId_opcion());
				consulta.setString(4, relacionOpcion.get(i).getEst_relacion());
				consulta.setString(5, relacionOpcion.get(i).getUsu_ingresa());
				consulta.setTimestamp(6, relacionOpcion.get(i).getFec_ingresa());
				consulta.executeUpdate();
			}
			consulta = conexion.abrir().prepareStatement("{CALL relacion_eliminarLocalidadSolicitantes(?)}");
			consulta.setLong(1, id_solicitante);
			consulta.executeUpdate();
			for (int i = 0; i < relacionLocalidad.size(); i++) {
				Long id = (long) 0;
				consulta = conexion.abrir().prepareStatement("{CALL relacion_obtenerNuevoIDLocalidadSolicitantes()}");
				ResultSet resultado = consulta.executeQuery();
				while (resultado.next()) {
					id = resultado.getLong("id_relacion") + 1;
				}
				consulta = conexion.abrir()
						.prepareStatement("{CALL relacion_insertarLocalidadSolicitantes (?, ?, ?, ?, ?, ?)}");
				consulta.setLong(1, id);
				consulta.setLong(2, relacionLocalidad.get(i).getId_solicitante());
				consulta.setLong(3, relacionLocalidad.get(i).getId_localidad());
				consulta.setString(4, relacionLocalidad.get(i).getEst_relacion());
				consulta.setString(5, relacionLocalidad.get(i).getUsu_ingresa());
				consulta.setTimestamp(6, relacionLocalidad.get(i).getFec_ingresa());
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
