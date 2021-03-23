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
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_campo_mantenimiento;

public class dao_relacion_campo {

	public List<modelo_relacion_campo_mantenimiento> obtenerRelacionesMantenimientos(String campo, String mantenimiento,
			int tipo) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		PreparedStatement consulta = null;
		List<modelo_relacion_campo_mantenimiento> lista_relacion = new ArrayList<modelo_relacion_campo_mantenimiento>();
		try {
			consulta = conexion.abrir().prepareStatement("{CALL relacion_obtenerRelacionesCampos(?, ?, ?)}");
			consulta.setString(1, campo);
			consulta.setString(2, mantenimiento);
			consulta.setInt(3, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_relacion.add(new modelo_relacion_campo_mantenimiento(resultado.getLong("id_relacion"),
						resultado.getLong("id_campo"), resultado.getString("nom_campo"),
						resultado.getLong("id_mantenimiento"), resultado.getString("est_relacion"),
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

	public void insertarRelacion(List<modelo_relacion_campo_mantenimiento> relacionMantenimiento, long id_mantenimiento)
			throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		PreparedStatement consulta = null;
		conexion.abrir().setAutoCommit(false);
		try {
			consulta = conexion.abrir().prepareStatement("{CALL relacion_eliminarMantenimientoCampos(?)}");
			consulta.setLong(1, id_mantenimiento);
			consulta.executeUpdate();
			for (int i = 0; i < relacionMantenimiento.size(); i++) {
				Long id = (long) 0;
				consulta = conexion.abrir().prepareStatement("{CALL relacion_obtenerNuevoIDMantenimientoCampos()}");
				ResultSet resultado = consulta.executeQuery();
				while (resultado.next()) {
					id = resultado.getLong("id_relacion") + 1;
				}
				consulta = conexion.abrir()
						.prepareStatement("{CALL relacion_insertarMantenimientoCampos (?, ?, ?, ?, ?, ?)}");
				consulta.setLong(1, id);
				consulta.setLong(2, relacionMantenimiento.get(i).getId_campo());
				consulta.setLong(3, relacionMantenimiento.get(i).getId_mantenimiento());
				consulta.setString(4, relacionMantenimiento.get(i).getEst_relacion());
				consulta.setString(5, relacionMantenimiento.get(i).getUsu_ingresa());
				consulta.setTimestamp(6, relacionMantenimiento.get(i).getFec_ingresa());
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
