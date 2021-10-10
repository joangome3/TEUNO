package bp.aplicaciones.mantenimientos.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.mantenimientos.modelo.modelo_fila;

//Filas
public class dao_fila {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL fila_obtenerNuevoID()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_fila") + 1;
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

	public List<modelo_fila> obtenerFilas(String criterio, long id_localidad, long id_cliente, int tipo)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_fila> lista_filas = new ArrayList<modelo_fila>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL fila_obtenerFilas(?, ?, ?, ?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setLong(2, id_localidad);
			consulta.setLong(3, id_cliente);
			consulta.setInt(4, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_filas.add(new modelo_fila(resultado.getLong("id_fila"), resultado.getString("nom_fila"),
						resultado.getString("des_fila"), resultado.getLong("id_localidad"),
						resultado.getString("est_fila"), resultado.getString("usu_ingresa"),
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
		return lista_filas;
	}

}
