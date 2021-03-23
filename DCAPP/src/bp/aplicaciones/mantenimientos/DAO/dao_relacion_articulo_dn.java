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
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_articulo_ubicacion_dn;

public class dao_relacion_articulo_dn {

	public boolean obtenerRelacionArticuloUbicacion(long id_relacion, long id_articulo, long id_ubicacion, int tipo)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		PreparedStatement consulta = null;
		modelo_relacion_articulo_ubicacion_dn relacion = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL relacion_obtenerRelacionesArticulosDN(?, ?, ?, ?)}");
			consulta.setLong(1, id_relacion);
			consulta.setLong(2, id_articulo);
			consulta.setLong(3, id_ubicacion);
			consulta.setInt(4, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				relacion = new modelo_relacion_articulo_ubicacion_dn(resultado.getLong("id_relacion"),
						resultado.getLong("id_articulo"), resultado.getLong("id_ubicacion"),
						resultado.getInt("pos_ubicacion"), resultado.getInt("sto_articulo"),
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

	public List<modelo_relacion_articulo_ubicacion_dn> obtenerRelacionesArticulosUbicaciones(long id_relacion,
			long id_articulo, long id_ubicacion, int tipo)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		PreparedStatement consulta = null;
		List<modelo_relacion_articulo_ubicacion_dn> listaRelacionArticulos = new ArrayList<modelo_relacion_articulo_ubicacion_dn>();
		try {
			consulta = conexion.abrir().prepareStatement("{CALL relacion_obtenerRelacionesArticulosDN(?, ?, ?, ?)}");
			consulta.setLong(1, id_relacion);
			consulta.setLong(2, id_articulo);
			consulta.setLong(3, id_ubicacion);
			consulta.setInt(4, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				listaRelacionArticulos.add(new modelo_relacion_articulo_ubicacion_dn(resultado.getLong("id_relacion"),
						resultado.getLong("id_articulo"), resultado.getLong("id_ubicacion"),
						resultado.getInt("pos_ubicacion"), resultado.getInt("sto_articulo"),
						resultado.getString("est_relacion"), resultado.getString("usu_ingresa"),
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
		return listaRelacionArticulos;
	}

	public void insertarRelacion(List<modelo_relacion_articulo_ubicacion_dn> relacion, long id_articulo)
			throws SQLException, MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, IOException {
		conexion conexion = new conexion();
		PreparedStatement consulta = null;
		conexion.abrir().setAutoCommit(false);
		try {
			consulta = conexion.abrir().prepareStatement("{CALL relacion_eliminarArticuloDNUbicacion(?)}");
			consulta.setLong(1, id_articulo);
			consulta.executeUpdate();
			for (int i = 0; i < relacion.size(); i++) {
				Long id = (long) 0;
				consulta = conexion.abrir().prepareStatement("{CALL relacion_obtenerNuevoIDArticuloDNUbicacion()}");
				ResultSet resultado = consulta.executeQuery();
				while (resultado.next()) {
					id = resultado.getLong("id_relacion") + 1;
				}
				consulta = conexion.abrir()
						.prepareStatement("{CALL relacion_insertarArticuloDNUbicacion (?, ?, ?, ?, ?, ?, ?, ?)}");
				consulta.setLong(1, id);
				consulta.setLong(2, relacion.get(i).getId_articulo());
				consulta.setLong(3, relacion.get(i).getId_ubicacion());
				consulta.setInt(4, relacion.get(i).getPos_ubicacion());
				consulta.setLong(5, relacion.get(i).getSto_articulo());
				consulta.setString(6, relacion.get(i).getEst_relacion());
				consulta.setString(7, relacion.get(i).getUsu_ingresa());
				consulta.setTimestamp(8, relacion.get(i).getFec_ingresa());
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
