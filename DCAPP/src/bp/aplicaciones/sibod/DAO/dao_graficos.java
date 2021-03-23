package bp.aplicaciones.sibod.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.sibod.modelo.modelo_grafico_barra;
import bp.aplicaciones.sibod.modelo.modelo_grafico_lineal;
import bp.aplicaciones.sibod.modelo.modelo_grafico_pastel;
import bp.aplicaciones.sibod.modelo.modelo_top_articulos;

//Graficos
public class dao_graficos {

	public List<modelo_grafico_barra> obtenerCategoriasGraficoDeBarra(String localidad)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_grafico_barra> lista_graficos = new ArrayList<modelo_grafico_barra>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("SELECT\r\n" + "    c.nom_categoria AS nom_categoria,\r\n"
					+ "    COUNT(c.id_categoria) AS cant_categoria\r\n" + "FROM\r\n"
					+ "    sibod_detalle_movimiento a,\r\n" + "    sibod_articulo b,\r\n" + "    sibod_categoria c,\r\n"
					+ "    sibod_movimiento h\r\n" + "WHERE\r\n" + "    (\r\n"
					+ "        a.id_movimiento = h.id_movimiento\r\n"
					+ "    ) AND(a.id_articulo = b.id_articulo) AND(\r\n"
					+ "        b.id_categoria = c.id_categoria\r\n"
					+ "    ) AND YEAR(a.fec_movimiento) = DATE_FORMAT(NOW(), '%Y%') AND h.id_localidad = '" + localidad
					+ "'\r\n" + "GROUP BY\r\n" + "    c.id_categoria\r\n" + "ORDER BY\r\n" + "    2 ASC;");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_graficos.add(new modelo_grafico_barra(resultado.getString("nom_categoria"),
						resultado.getInt("cant_categoria")));
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
		return lista_graficos;
	}

	public List<modelo_grafico_lineal> obtenerIngresosGraficoLineal(String localidad, String articulo)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_grafico_lineal> lista_graficos = new ArrayList<modelo_grafico_lineal>();
		PreparedStatement consulta = null;
		try {
			if (articulo.equals("")) {
				consulta = conexion.abrir().prepareStatement(
						"SELECT b.descripcion as mes, IFNULL(T1.total, 0) cantidad FROM sibod_mes AS b LEFT JOIN(SELECT MONTH(a.fec_movimiento) AS mes, COUNT(a.id_movimiento) AS total FROM sibod_movimiento a WHERE YEAR(a.fec_movimiento) = DATE_FORMAT(NOW(), '%Y%') and (a.id_localidad = '"
								+ localidad + "') and a.tip_movimiento = 'I' GROUP BY mes) AS T1 ON T1.mes = b.mes");
			} else {
				consulta = conexion.abrir().prepareStatement(
						"SELECT b.descripcion as mes, IFNULL(T1.total, 0) cantidad FROM sibod_mes AS b LEFT JOIN(SELECT MONTH(a.fec_movimiento) AS mes, COUNT(a.id_movimiento) AS total FROM sibod_movimiento a WHERE YEAR(a.fec_movimiento) = DATE_FORMAT(NOW(), '%Y%') and (a.id_localidad = '"
								+ localidad + "') and a.tip_movimiento = 'I' GROUP BY mes) AS T1 ON T1.mes = b.mes");
			}
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_graficos.add(new modelo_grafico_lineal(resultado.getString("mes"), resultado.getInt("cantidad")));
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
		return lista_graficos;
	}

	public List<modelo_grafico_lineal> obtenerEgresosGraficoLineal(String localidad, String articulo)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_grafico_lineal> lista_graficos = new ArrayList<modelo_grafico_lineal>();
		PreparedStatement consulta = null;
		try {
			if (articulo.equals("")) {
				consulta = conexion.abrir().prepareStatement(
						"SELECT b.descripcion as mes, IFNULL(T1.total, 0) cantidad FROM sibod_mes AS b LEFT JOIN(SELECT MONTH(a.fec_movimiento) AS mes, COUNT(a.id_movimiento) AS total FROM sibod_movimiento a WHERE YEAR(a.fec_movimiento) = DATE_FORMAT(NOW(), '%Y%') and (a.id_localidad = '"
								+ localidad + "') and a.tip_movimiento = 'E' GROUP BY mes) AS T1 ON T1.mes = b.mes");
			} else {
				consulta = conexion.abrir().prepareStatement(
						"SELECT b.descripcion as mes, IFNULL(T1.total, 0) cantidad FROM sibod_mes AS b LEFT JOIN(SELECT MONTH(a.fec_movimiento) AS mes, COUNT(a.id_movimiento) AS total FROM sibod_movimiento a WHERE YEAR(a.fec_movimiento) = DATE_FORMAT(NOW(), '%Y%') and (a.id_localidad = '"
								+ localidad + "') and a.tip_movimiento = 'E' GROUP BY mes) AS T1 ON T1.mes = b.mes");
			}
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_graficos.add(new modelo_grafico_lineal(resultado.getString("mes"), resultado.getInt("cantidad")));
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
		return lista_graficos;
	}

	public List<modelo_grafico_pastel> obtenerDatosGraficoDePastel1(String localidad, String categoria,
			String ubicacion) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_grafico_pastel> lista_graficos = new ArrayList<modelo_grafico_pastel>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement(
					"select cod_articulo, c.sto_articulo from sibod_articulo a, sibod_categoria b, sibod_relacion_articulo_ubicacion c, sibod_ubicacion d, sibod_tipo_ubicacion e where (a.id_categoria = b.id_categoria) and (a.id_articulo = c.id_articulo) and (c.id_ubicacion = d.id_ubicacion) and (d.id_tip_ubicacion = e.id_tip_ubicacion) and (a.id_localidad = '"
							+ localidad + "') and (a.id_categoria = " + categoria + ") and (e.id_tip_ubicacion = "
							+ ubicacion + ") order by 2 DESC;");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_graficos.add(new modelo_grafico_pastel(resultado.getString("cod_articulo"),
						resultado.getInt("sto_articulo")));
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
		return lista_graficos;
	}

	public List<modelo_grafico_pastel> obtenerDatosGraficoDePastel2(String localidad, String categoria,
			String ubicacion) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_grafico_pastel> lista_graficos = new ArrayList<modelo_grafico_pastel>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement(
					"select cod_articulo, c.sto_articulo from sibod_articulo a, sibod_categoria b, sibod_relacion_articulo_ubicacion c, sibod_ubicacion d, sibod_tipo_ubicacion e where (a.id_categoria = b.id_categoria) and (a.id_articulo = c.id_articulo) and (c.id_ubicacion = d.id_ubicacion) and (d.id_tip_ubicacion = e.id_tip_ubicacion) and (a.id_localidad = '"
							+ localidad + "') and (a.id_categoria = " + categoria + ") and (e.id_tip_ubicacion = "
							+ ubicacion + ") order by 2 DESC;");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_graficos.add(new modelo_grafico_pastel(resultado.getString("cod_articulo"),
						resultado.getInt("sto_articulo")));
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
		return lista_graficos;
	}

	public List<modelo_top_articulos> obtenerDatosTopArticulos(String localidad)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_top_articulos> lista_graficos = new ArrayList<modelo_top_articulos>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("SELECT\r\n"
					+ "    concat(b.cod_articulo, ' - ', b.des_articulo) AS nombre,\r\n"
					+ "    COUNT(a.id_det_movimiento) AS cantidad\r\n" + "FROM\r\n"
					+ "    sibod_detalle_movimiento a,\r\n" + "    sibod_articulo b, sibod_movimiento c\r\n"
					+ "WHERE\r\n"
					+ "    (a.id_articulo = b.id_articulo) and (a.id_movimiento = c.id_movimiento) and (a.id_movimiento = c.id_movimiento) and YEAR(a.fec_movimiento) = DATE_FORMAT(NOW(), '%Y%') and c.id_localidad = '"
					+ localidad + "'\r\n" + "GROUP BY\r\n" + "    a.id_articulo\r\n" + "ORDER BY\r\n" + "    2\r\n"
					+ "DESC\r\n" + "LIMIT 0, 10;");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_graficos
						.add(new modelo_top_articulos(resultado.getString("nombre"), resultado.getInt("cantidad")));
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
		return lista_graficos;
	}

}
