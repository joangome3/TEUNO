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
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;

public class dao_parametros_generales_1 {

	public Long obtenerNuevoId(int tipo)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir()
					.prepareStatement("{CALL parametros_generales_obtenerNuevoIDParametros1(?)}");
			consulta.setInt(1, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_parametro") + 1;
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
		return id;
	}

	public List<modelo_parametros_generales_1> obtenerParametros()
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_parametros_generales_1> lista_parametros_generales_1 = new ArrayList<modelo_parametros_generales_1>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL parametros_generales_obtenerParametros1()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_parametros_generales_1.add(new modelo_parametros_generales_1(resultado.getLong("id_parametro"),
						resultado.getString("validar_codigo_articulo"), resultado.getString("activar_guardar_imagen"),
						resultado.getString("activar_borrar_imagen"),
						resultado.getString("redirecciona_usuario_diners"),
						resultado.getString("url_aplicacion_externa_1"), resultado.getLong("id_categoria_reporte_1"),
						resultado.getLong("id_categoria_reporte_2"), resultado.getLong("secuencia_control_cambio"),
						resultado.getLong("localidad_control_cambio"),
						resultado.getString("redirecciona_usuario_control_cambio"),
						resultado.getString("url_aplicacion_externa_2"), resultado.getString("etiqueta_bitacora"),
						resultado.getLong("secuencia_bitacora"), resultado.getLong("id_perfil_revisor_bitacora"),
						resultado.getString("etiqueta_tarea_proveedor"), resultado.getLong("secuencia_tarea_proveedor"),
						resultado.getLong("estado_bitacora_1"), resultado.getLong("estado_bitacora_2"),
						resultado.getLong("id_ubicacion_reporte_1"), resultado.getLong("id_ubicacion_reporte_2")));
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
		return lista_parametros_generales_1;
	}

	public void insertarParametros(modelo_parametros_generales_1 parametros_generales_1, int tipo) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		long id = obtenerNuevoId(tipo);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement(
					"{CALL parametros_generales_insertarParametros1(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, id);
			consulta.setString(2, parametros_generales_1.getValidar_codigo_articulo());
			consulta.setString(3, parametros_generales_1.getActivar_borrar_imagen());
			consulta.setString(4, parametros_generales_1.getActivar_borrar_imagen());
			consulta.setString(5, parametros_generales_1.getRedirecciona_usuario_diners());
			consulta.setString(6, parametros_generales_1.getUrl_aplicacion_externa_1());
			consulta.setLong(7, parametros_generales_1.getId_categoria_reporte_1());
			consulta.setLong(8, parametros_generales_1.getId_categoria_reporte_2());
			consulta.setLong(9, parametros_generales_1.getSecuencia_control_cambio());
			consulta.setLong(10, parametros_generales_1.getLocalidad_control_cambio());
			consulta.setString(11, parametros_generales_1.getRedirecciona_usuario_control_cambio());
			consulta.setString(12, parametros_generales_1.getUrl_aplicacion_externa_2());
			consulta.setString(13, parametros_generales_1.getEtiqueta_bitacora());
			consulta.setLong(14, parametros_generales_1.getSecuencia_bitacora());
			consulta.setLong(15, parametros_generales_1.getId_perfil_revisor_bitacora());
			consulta.setString(16, parametros_generales_1.getEtiqueta_tarea_proveedor());
			consulta.setLong(17, parametros_generales_1.getSecuencia_tarea_proveedor());
			consulta.setLong(18, parametros_generales_1.getEstado_bitacora_1());
			consulta.setLong(19, parametros_generales_1.getEstado_bitacora_2());
			consulta.setLong(20, parametros_generales_1.getId_ubicacion_reporte_1());
			consulta.setLong(21, parametros_generales_1.getId_ubicacion_reporte_2());
			consulta.executeUpdate();
			consulta.close();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		} catch (java.lang.NullPointerException ex) {
			throw new java.lang.NullPointerException();
		} finally {
			conexion.cerrar();
		}
	}

	public void modificarParametros(modelo_parametros_generales_1 parametros_generales_1) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement(
					"{CALL parametros_generales_modificarParametros1(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setString(1, parametros_generales_1.getValidar_codigo_articulo());
			consulta.setString(2, parametros_generales_1.getActivar_guardar_imagen());
			consulta.setString(3, parametros_generales_1.getActivar_borrar_imagen());
			consulta.setString(4, parametros_generales_1.getRedirecciona_usuario_diners());
			consulta.setString(5, parametros_generales_1.getUrl_aplicacion_externa_1());
			consulta.setLong(6, parametros_generales_1.getId_categoria_reporte_1());
			consulta.setLong(7, parametros_generales_1.getId_categoria_reporte_2());
			consulta.setLong(8, parametros_generales_1.getSecuencia_control_cambio());
			consulta.setLong(9, parametros_generales_1.getLocalidad_control_cambio());
			consulta.setString(10, parametros_generales_1.getRedirecciona_usuario_control_cambio());
			consulta.setString(11, parametros_generales_1.getUrl_aplicacion_externa_2());
			consulta.setString(12, parametros_generales_1.getEtiqueta_bitacora());
			consulta.setLong(13, parametros_generales_1.getSecuencia_bitacora());
			consulta.setLong(14, parametros_generales_1.getId_perfil_revisor_bitacora());
			consulta.setString(15, parametros_generales_1.getEtiqueta_tarea_proveedor());
			consulta.setLong(16, parametros_generales_1.getSecuencia_tarea_proveedor());
			consulta.setLong(17, parametros_generales_1.getEstado_bitacora_1());
			consulta.setLong(18, parametros_generales_1.getEstado_bitacora_2());
			consulta.setLong(19, parametros_generales_1.getId_ubicacion_reporte_1());
			consulta.setLong(20, parametros_generales_1.getId_ubicacion_reporte_2());
			consulta.setLong(21, parametros_generales_1.getId_parametro());
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
