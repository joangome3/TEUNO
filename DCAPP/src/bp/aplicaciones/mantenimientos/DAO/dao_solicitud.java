package bp.aplicaciones.mantenimientos.DAO;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import bp.aplicaciones.conexion.conexion;

import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;

public class dao_solicitud {

	public Long obtenerNuevoId() throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		Long id = (long) 0;
		try {
			PreparedStatement consulta = conexion.abrir().prepareStatement("{CALL solicitud_obtenerNuevoID()}");
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				id = resultado.getLong("id_solicitud") + 1;
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

	public int otenerTipoDeSolicitudPendienteDeAccion(long mantenimiento, long registro)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		int tipoDeSolicitud = 0;
		conexion conexion = new conexion();
		CallableStatement consulta = null;
		try {
			consulta = (CallableStatement) conexion.abrir()
					.prepareCall("{? = CALL solicitud_obtenerTipoDeSolicitudPendienteDeAccion(?,?)}");
			consulta.registerOutParameter(1, Types.INTEGER);
			consulta.setLong(2, mantenimiento);
			consulta.setLong(3, registro);
			consulta.execute();
			tipoDeSolicitud = consulta.getInt(1);
			consulta.close();
		} catch (SQLException ex) {
			throw new SQLException(ex);
		} catch (java.lang.NullPointerException ex) {
			throw new java.lang.NullPointerException();
		} finally {
			if (consulta != null) {
				consulta.close();
			}
			if (conexion != null) {
				conexion.cerrar();
			}
		}
		return tipoDeSolicitud;
	}

	public List<modelo_solicitud> obtenerSolicitudes(String criterio, String fecha_inicio, String fecha_fin,
			String estado, long id_mantenimiento, long id_registro, int tipo)
			throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		List<modelo_solicitud> lista_solicitudes = new ArrayList<modelo_solicitud>();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL solicitud_obtenerSolicitudes(?, ?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setString(2, fecha_inicio);
			consulta.setString(3, fecha_fin);
			consulta.setString(4, estado);
			consulta.setLong(5, id_mantenimiento);
			consulta.setLong(6, id_registro);
			consulta.setInt(7, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				lista_solicitudes.add(new modelo_solicitud(resultado.getLong("id_solicitud"),
						resultado.getLong("id_tip_solicitud"), resultado.getString("nom_tipo_solicitud"),
						resultado.getString("comentario_1"), resultado.getString("comentario_2"),
						resultado.getString("comentario_3"), resultado.getString("comentario_4"),
						resultado.getString("comentario_5"), resultado.getLong("id_mantenimiento"),
						resultado.getString("nom_mantenimiento"), resultado.getLong("id_registro"),
						resultado.getLong("id_campo"), resultado.getLong("id_user_1"), resultado.getLong("id_user_2"),
						resultado.getLong("id_user_3"), resultado.getLong("id_user_4"), resultado.getLong("id_user_5"),
						resultado.getTimestamp("fecha_1"), resultado.getTimestamp("fecha_2"),
						resultado.getTimestamp("fecha_3"), resultado.getTimestamp("fecha_4"),
						resultado.getTimestamp("fecha_5"), resultado.getString("nom_registro"),
						resultado.getString("nom_campo"), resultado.getString("est_solicitud"),
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
		return lista_solicitudes;
	}

	public modelo_solicitud obtenerSolicitudesxEstado(String criterio, long id_mantenimiento, long id_registro,
			int tipo) throws SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		modelo_solicitud solicitud = new modelo_solicitud();
		PreparedStatement consulta = null;
		try {
			consulta = conexion.abrir().prepareStatement("{CALL solicitud_obtenerSolicitudes(?, ?, ?, ?, ?, ?, ?)}");
			consulta.setString(1, criterio.toUpperCase());
			consulta.setString(2, "");
			consulta.setString(3, "");
			consulta.setString(4, "");
			consulta.setLong(5, id_mantenimiento);
			consulta.setLong(6, id_registro);
			consulta.setInt(7, tipo);
			ResultSet resultado = consulta.executeQuery();
			while (resultado.next()) {
				solicitud = new modelo_solicitud(resultado.getLong("id_solicitud"),
						resultado.getLong("id_tip_solicitud"), resultado.getString("nom_tipo_solicitud"),
						resultado.getString("comentario_1"), resultado.getString("comentario_2"),
						resultado.getString("comentario_3"), resultado.getString("comentario_4"),
						resultado.getString("comentario_5"), resultado.getLong("id_mantenimiento"),
						resultado.getString("nom_mantenimiento"), resultado.getLong("id_registro"),
						resultado.getLong("id_campo"), resultado.getLong("id_user_1"), resultado.getLong("id_user_2"),
						resultado.getLong("id_user_3"), resultado.getLong("id_user_4"), resultado.getLong("id_user_5"),
						resultado.getTimestamp("fecha_1"), resultado.getTimestamp("fecha_2"),
						resultado.getTimestamp("fecha_3"), resultado.getTimestamp("fecha_4"),
						resultado.getTimestamp("fecha_5"), resultado.getString("nom_registro"),
						resultado.getString("nom_campo"), resultado.getString("est_solicitud"),
						resultado.getString("usu_ingresa"), resultado.getTimestamp("fec_ingresa"),
						resultado.getString("usu_modifica"), resultado.getTimestamp("fec_modifica"));
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
		return solicitud;
	}

	public void insertarSolicitud(modelo_solicitud solicitud, long mantenimiento, long id_registro,
			String est_mantenimiento) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		long id = obtenerNuevoId();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement(
					"{CALL solicitud_insertarSolicitud(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, id);
			consulta.setLong(2, solicitud.getId_tip_solicitud());
			consulta.setString(3, solicitud.getComentario_1().toUpperCase());
			consulta.setString(4, solicitud.getComentario_2().toUpperCase());
			consulta.setString(5, solicitud.getComentario_3().toUpperCase());
			consulta.setString(6, solicitud.getComentario_4().toUpperCase());
			consulta.setString(7, solicitud.getComentario_5().toUpperCase());
			consulta.setLong(8, solicitud.getId_mantenimiento());
			consulta.setLong(9, solicitud.getId_registro());
			consulta.setLong(10, solicitud.getId_campo());
			consulta.setLong(11, solicitud.getId_user_1());
			consulta.setLong(12, solicitud.getId_user_2());
			consulta.setLong(13, solicitud.getId_user_3());
			consulta.setLong(14, solicitud.getId_user_4());
			consulta.setLong(15, solicitud.getId_user_5());
			consulta.setTimestamp(16, solicitud.getFecha_1());
			consulta.setTimestamp(17, solicitud.getFecha_2());
			consulta.setTimestamp(18, solicitud.getFecha_3());
			consulta.setTimestamp(19, solicitud.getFecha_4());
			consulta.setTimestamp(20, solicitud.getFecha_5());
			consulta.setString(21, solicitud.getEst_solicitud());
			consulta.setString(22, solicitud.getUsu_ingresa());
			consulta.setTimestamp(23, solicitud.getFec_ingresa());
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

	public void modificarSolicitud(modelo_solicitud solicitud, long mantenimiento, long registro,
			String est_mantenimiento) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		conexion.abrir().setAutoCommit(false);
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement(
					"{CALL solicitud_modificarSolicitud(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
			consulta.setLong(1, solicitud.getId_tip_solicitud());
			consulta.setString(2, solicitud.getComentario_1().toUpperCase());
			consulta.setString(3, solicitud.getComentario_2().toUpperCase());
			consulta.setString(4, solicitud.getComentario_3().toUpperCase());
			consulta.setString(5, solicitud.getComentario_4().toUpperCase());
			consulta.setString(6, solicitud.getComentario_5().toUpperCase());
			consulta.setLong(7, solicitud.getId_mantenimiento());
			consulta.setLong(8, solicitud.getId_registro());
			consulta.setLong(9, solicitud.getId_campo());
			consulta.setLong(10, solicitud.getId_user_1());
			consulta.setLong(11, solicitud.getId_user_2());
			consulta.setLong(12, solicitud.getId_user_3());
			consulta.setLong(13, solicitud.getId_user_4());
			consulta.setLong(14, solicitud.getId_user_5());
			consulta.setTimestamp(15, solicitud.getFecha_1());
			consulta.setTimestamp(16, solicitud.getFecha_2());
			consulta.setTimestamp(17, solicitud.getFecha_3());
			consulta.setTimestamp(18, solicitud.getFecha_4());
			consulta.setTimestamp(19, solicitud.getFecha_5());
			consulta.setString(20, solicitud.getEst_solicitud());
			consulta.setString(21, solicitud.getUsu_modifica());
			consulta.setTimestamp(22, solicitud.getFec_modifica());
			consulta.setLong(23, solicitud.getId_solicitud());
			consulta.executeUpdate();
			if (solicitud.getEst_solicitud().equals("A")) {
				if (mantenimiento == 1) {
					consulta = conexion.abrir()
							.prepareStatement("{CALL solicitante_actualizarEstadoSolicitante(?,?,?,?)}");
				}
				if (mantenimiento == 2) {
					consulta = conexion.abrir().prepareStatement("{CALL ubicacion_actualizarEstadoUbicacion(?,?,?,?)}");
				}
				if (mantenimiento == 3) {
					consulta = conexion.abrir().prepareStatement("{CALL localidad_actualizarEstadoLocalidad(?,?,?,?)}");
				}
				if (mantenimiento == 4) {
					consulta = conexion.abrir().prepareStatement("{CALL usuario_actualizarEstadoUsuario(?,?,?,?)}");
				}
				if (mantenimiento == 5) {
					consulta = conexion.abrir().prepareStatement("{CALL categoria_actualizarEstadoCategoria(?,?,?,?)}");
				}
				if (mantenimiento == 6) {
					consulta = conexion.abrir().prepareStatement("{CALL articulo_actualizarEstadoArticulo(?,?,?,?)}");
				}
				if (mantenimiento == 8) {
					consulta = conexion.abrir().prepareStatement("{CALL empresa_actualizarEstadoEmpresa(?,?,?,?)}");
				}
				if (mantenimiento == 12) {
					consulta = conexion.abrir().prepareStatement("{CALL respaldo_actualizarEstadoRespaldo(?,?,?,?)}");
				}
				if (mantenimiento == 13) {
					consulta = conexion.abrir().prepareStatement("{CALL capacidad_actualizarEstadoCapacidad(?,?,?,?)}");
				}
				if (mantenimiento == 14) {
					consulta = conexion.abrir()
							.prepareStatement("{CALL categoriaDN_actualizarEstadoCategoria(?,?,?,?)}");
				}
				if (mantenimiento == 15) {
					consulta = conexion.abrir()
							.prepareStatement("{CALL ubicacionDN_actualizarEstadoUbicacion(?,?,?,?)}");
				}
				if (mantenimiento == 16) {
					consulta = conexion.abrir().prepareStatement("{CALL articuloDN_actualizarEstadoArticulo(?,?,?,?)}");
				}
				consulta.setString(1, "AE");
				consulta.setString(2, solicitud.getUsu_modifica());
				consulta.setTimestamp(3, solicitud.getFec_modifica());
				consulta.setLong(4, registro);
				consulta.executeUpdate();
			}
			consulta.close();
			conexion.abrir().commit();
			// redactarMail(solicitud.getId_solicitud(), solicitud, "2");
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

	public void actualizarEstadoSolicitud(modelo_solicitud solicitud) throws SQLException,
			MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL solicitud_actualizarEstadoSolicitud(?,?,?,?)}");
			consulta.setString(1, solicitud.getEst_solicitud());
			consulta.setString(2, solicitud.getUsu_modifica());
			consulta.setTimestamp(3, solicitud.getFec_modifica());
			consulta.setLong(4, solicitud.getId_solicitud());
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

	public void eliminarSolicitud(Long id_solicitud) throws SQLException, MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, IOException {
		conexion conexion = new conexion();
		try {
			PreparedStatement consulta = null;
			consulta = conexion.abrir().prepareStatement("{CALL solicitud_eliminarSolicitud(?)}");
			consulta.setLong(1, id_solicitud);
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

//	public void redactarMail(long id_solicitud, modelo_solicitud solicitud, String id_parametro)
//			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
//		@SuppressWarnings("unused")
//		mail mail = new mail();
//		dao_mail dao = new dao_mail();
//		@SuppressWarnings("unused")
//		String destinatarios[] = null;
//		@SuppressWarnings("unused")
//		String remitente = "", clave = "", asunto = "", cuerpo = "", host = "", starttls = "", port = "", auth = "",
//				ssl = "", debug = "", user = "", user1 = "", date = "";
//		List<modelo_mail_parametros> lista_parametros = new ArrayList<modelo_mail_parametros>();
//		List<modelo_mail_destinatarios> lista_destinatarios = new ArrayList<modelo_mail_destinatarios>();
//		lista_parametros = dao.obtenerParametros(id_parametro, 2);
//		if (lista_parametros.size() == 1) {
//			lista_destinatarios = dao.obtenerDestinatarios(String.valueOf(lista_parametros.get(0).getId_parametro()),
//					1);
//			// remitente = lista_parametros.get(0).getNom_remitente();
//			// clave = lista_parametros.get(0).getPas_remitente();
//			host = lista_parametros.get(0).getSmtp_host();
//			if (lista_parametros.get(0).getSmtp_starttls().equals("S")) {
//				starttls = "true";
//			} else {
//				starttls = "false";
//			}
//			port = lista_parametros.get(0).getSmtp_puerto();
//			if (lista_parametros.get(0).getSmtp_auth().equals("S")) {
//				auth = "true";
//			} else {
//				auth = "false";
//			}
//			ssl = lista_parametros.get(0).getSmtp_trust();
//			debug = lista_parametros.get(0).getSmtp_debug();
//		} else {
//			return;
//		}
//		if (lista_destinatarios.size() > 0) {
//			destinatarios = new String[lista_destinatarios.size()];
//			for (int i = 0; i < lista_destinatarios.size(); i++) {
//				// destinatarios[i] = lista_destinatarios.get(i).getMail_destinatario();
//			}
//		}
//		dao_usuario dao1 = new dao_usuario();
//		modelo_usuario_bk usuario = new modelo_usuario_bk();
//		date = new SimpleDateFormat("dd/MM/yyyy").format(solicitud.getFecha_1());
//		asunto = "#" + id_solicitud + " - SOLICITUD DE " + solicitud.getNom_tipo_solicitud() + " - " + date + "";
//		usuario = dao1.obtenerUsuario(String.valueOf(solicitud.getId_user_1()), "", 2);
//		if (usuario != null) {
//			if (usuario.getNom_usuario() != null && usuario.getApe_usuario() != null) {
//				user = usuario.getNom_usuario() + " " + usuario.getApe_usuario();
//			}
//		} else {
//			return;
//		}
//		if (solicitud.getEst_solicitud().equals("P")) {
//			cuerpo += "<b>Estimad@(s),</b></br></br>"
//					+ "Favor su ayuda con la atención de la siguiente solicitud.</br>";
//		}
//		if (solicitud.getEst_solicitud().equals("R")) {
//			usuario = dao1.obtenerUsuario(String.valueOf(solicitud.getId_user_2()), "", 2);
//			if (usuario != null) {
//				if (usuario.getNom_usuario() != null && usuario.getApe_usuario() != null) {
//					user1 = usuario.getNom_usuario() + " " + usuario.getApe_usuario();
//				}
//			} else {
//				return;
//			}
//			cuerpo += "<b>Estimad@(s),</b></br></br>" + "La solicitud se encuentra siendo revisada por " + user1
//					+ ".</br>";
//		}
//		if (solicitud.getEst_solicitud().equals("S")) {
//			usuario = dao1.obtenerUsuario(String.valueOf(solicitud.getId_user_2()), "", 2);
//			if (usuario != null) {
//				if (usuario.getNom_usuario() != null && usuario.getApe_usuario() != null) {
//					user1 = usuario.getNom_usuario() + " " + usuario.getApe_usuario();
//				}
//			} else {
//				return;
//			}
//			cuerpo += "<b>Estimad@(s),</b></br></br>" + "La solicitud se encuentra aprobada por " + user1
//					+ " considerar lo siguiente " + solicitud.getComentario_2()
//					+ ", antes de realizar los cambios en el registro.</br>";
//		}
//		if (solicitud.getEst_solicitud().equals("N")) {
//			usuario = dao1.obtenerUsuario(String.valueOf(solicitud.getId_user_2()), "", 2);
//			if (usuario != null) {
//				if (usuario.getNom_usuario() != null && usuario.getApe_usuario() != null) {
//					user1 = usuario.getNom_usuario() + " " + usuario.getApe_usuario();
//				}
//			} else {
//				return;
//			}
//			cuerpo += "<b>Estimad@(s),</b></br></br>" + "La solicitud no fue aprobada por " + user1 + " debido a que "
//					+ solicitud.getComentario_2() + ".</br>";
//		}
//		if (solicitud.getEst_solicitud().equals("A")) {
//			usuario = dao1.obtenerUsuario(String.valueOf(solicitud.getId_user_2()), "", 2);
//			if (usuario != null) {
//				if (usuario.getNom_usuario() != null && usuario.getApe_usuario() != null) {
//					user1 = usuario.getNom_usuario() + " " + usuario.getApe_usuario();
//				}
//			} else {
//				return;
//			}
//			cuerpo += "<b>Estimad@(s),</b></br></br>" + "La solicitud fue aprobada por " + user1 + " debido a que "
//					+ solicitud.getComentario_2() + ", ya puede utilizar el registro creado.</br>";
//		}
//		if (solicitud.getEst_solicitud().equals("T")) {
//			usuario = dao1.obtenerUsuario(String.valueOf(solicitud.getId_user_2()), "", 2);
//			if (usuario != null) {
//				if (usuario.getNom_usuario() != null && usuario.getApe_usuario() != null) {
//					user1 = usuario.getNom_usuario() + " " + usuario.getApe_usuario();
//				}
//			} else {
//				return;
//			}
//			cuerpo += "<b>Estimad@(s),</b></br></br>" + "Se deben realizar los siguientes cambios "
//					+ solicitud.getComentario_2() + ", antes de realizar la aprobación de la solicitud.</br>";
//		}
//		cuerpo += "<table class=\"demo\">\r\n" + "	<caption><br></caption>\r\n" + "	<thead>\r\n" + "	<tr>\r\n"
//				+ "		<th style='background:#F0F0F0;'>ID</th>\r\n"
//				+ "		<th style='background:#F0F0F0;'>TIPO SOLICITUD</th>\r\n"
//				+ "		<th style='background:#F0F0F0;'>CONFIGURACIÓN</th>\r\n"
//				+ "		<th style='background:#F0F0F0;'>CAMPO</th>\r\n"
//				+ "		<th style='background:#F0F0F0;'>FECHA SOLICITUD</th>\r\n"
//				+ "		<th style='background:#F0F0F0;'>SOLICITANTE</th>\r\n"
//				+ "		<th style='background:#F0F0F0;'>DETALLE SOLICITUD</th>\r\n"
//				+ "		<th style='background:#F0F0F0;'>ESTADO SOLICITUD</th>\r\n" + "	</tr>\r\n" + "	</thead>\r\n"
//				+ "	<tbody>\r\n" + "	<tr>\r\n" + "		<td style='text-align:center;'>&nbsp;" + id_solicitud
//				+ "</td>\r\n" + "		<td style='text-align:center;'>&nbsp;" + solicitud.getNom_tipo_solicitud()
//				+ "</td>\r\n" + "		<td style='text-align:center;'>&nbsp;" + solicitud.getNom_mantenimiento()
//				+ "</td>\r\n" + "		<td style='text-align:center;'>&nbsp;" + solicitud.getNom_campo() + "</td>\r\n"
//				+ "		<td style='text-align:center;'>&nbsp;" + date + "</td>\r\n"
//				+ "		<td style='text-align:center;'>&nbsp;" + user + "</td>\r\n"
//				+ "		<td style='text-align:center;'>&nbsp;" + solicitud.getComentario_1() + "</td>\r\n"
//				+ "		<td style='text-align:center;'>&nbsp;" + solicitud.toStringEstado() + "</td>\r\n"
//				+ "	</tr>\r\n" + "	</tbody>\r\n" + "</table></br></br>";
//		if (solicitud.getEst_solicitud().equals("P")) {
//			cuerpo += "Saludos Cordiales,\r\n</br></br>" + " \r\n" + user + "\r\n</br>"
//					+ "<b>Operador en Turno</b>\r\n</br>" + "NOC TEUNO\r\n</br>" + "operadorcca@teuno.com\r\n</br>"
//					+ "Dirección: Av. Perimetral Km. 30.5 y Av. Leopoldo Carrera Calvo \r\n</br>"
//					+ "T: (593) -4-6020660 ext. Prefijo (451) 6301.\r\n</br>" + "M: +593 9 88023236</br></br>";
//		} else {
//			cuerpo += "Saludos Cordiales,\r\n</br></br>" + " \r\n" + user1 + "\r\n</br>"
//					+ "<b>Operador en Turno</b>\r\n</br>" + "NOC TEUNO\r\n</br>" + "operadorcca@teuno.com\r\n</br>"
//					+ "Dirección: Av. Perimetral Km. 30.5 y Av. Leopoldo Carrera Calvo \r\n</br>"
//					+ "T: (593) -4-6020660 ext. Prefijo (451) 6301.\r\n</br>" + "M: +593 9 88023236</br></br>";
//		}
//		// mail.enviarMail(remitente, clave, destinatarios, asunto, cuerpo, host,
//		// starttls, port, auth, ssl, debug);
//	}

}
