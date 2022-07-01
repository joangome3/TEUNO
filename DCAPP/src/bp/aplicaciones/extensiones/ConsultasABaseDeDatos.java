package bp.aplicaciones.extensiones;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zul.Messagebox;

import bp.aplicaciones.bitacora.DAO.dao_bitacora;
import bp.aplicaciones.bitacora.DAO.dao_registra_turno;
import bp.aplicaciones.bitacora.DAO.dao_tarea_proveedor;
import bp.aplicaciones.bitacora.modelo.modelo_bitacora;
import bp.aplicaciones.bitacora.modelo.modelo_registra_turno;
import bp.aplicaciones.bitacora.modelo.modelo_tarea_proveedor;
import bp.aplicaciones.cintas.DAO.dao_movimiento_dn;
import bp.aplicaciones.cintas.modelo.modelo_movimiento_detalle_dn;
import bp.aplicaciones.cintas.modelo.modelo_movimiento_dn;
import bp.aplicaciones.controlcambio.DAO.dao_control_cambio;
import bp.aplicaciones.controlcambio.modelo.modelo_control_cambio;
import bp.aplicaciones.mantenimientos.DAO.dao_articulo_dn;
import bp.aplicaciones.mantenimientos.DAO.dao_campo;
import bp.aplicaciones.mantenimientos.DAO.dao_capacidad;
import bp.aplicaciones.mantenimientos.DAO.dao_categoria_dn;
import bp.aplicaciones.mantenimientos.DAO.dao_empresa;
import bp.aplicaciones.mantenimientos.DAO.dao_equipo;
import bp.aplicaciones.mantenimientos.DAO.dao_estado_bitacora;
import bp.aplicaciones.mantenimientos.DAO.dao_estado_equipo;
import bp.aplicaciones.mantenimientos.DAO.dao_fila;
import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
import bp.aplicaciones.mantenimientos.DAO.dao_mantenimiento;
import bp.aplicaciones.mantenimientos.DAO.dao_manual;
import bp.aplicaciones.mantenimientos.DAO.dao_marca;
import bp.aplicaciones.mantenimientos.DAO.dao_modelo;
import bp.aplicaciones.mantenimientos.DAO.dao_opcion;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_1;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_10;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_11;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_12;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_5;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_7;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_8;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_9;
import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
import bp.aplicaciones.mantenimientos.DAO.dao_rack;
import bp.aplicaciones.mantenimientos.DAO.dao_relacion_campo;
import bp.aplicaciones.mantenimientos.DAO.dao_respaldo;
import bp.aplicaciones.mantenimientos.DAO.dao_solicitante;
import bp.aplicaciones.mantenimientos.DAO.dao_solicitud;
import bp.aplicaciones.mantenimientos.DAO.dao_tarea_periodica;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_aprobador;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_clasificacion;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_conector;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_dispositivo;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_documento;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_equipo;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_ingreso;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_servicio;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_solicitud;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_tarea;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_trabajo;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_ubicacion;
import bp.aplicaciones.mantenimientos.DAO.dao_turno;
import bp.aplicaciones.mantenimientos.DAO.dao_ubicacion_dn;
import bp.aplicaciones.mantenimientos.DAO.dao_ur;
import bp.aplicaciones.mantenimientos.DAO.dao_usuario;
import bp.aplicaciones.mantenimientos.modelo.modelo_articulo_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_campo;
import bp.aplicaciones.mantenimientos.modelo.modelo_capacidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_categoria_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_equipo;
import bp.aplicaciones.mantenimientos.modelo.modelo_estado_bitacora;
import bp.aplicaciones.mantenimientos.modelo.modelo_estado_equipo;
import bp.aplicaciones.mantenimientos.modelo.modelo_fila;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_mantenimiento;
import bp.aplicaciones.mantenimientos.modelo.modelo_manual;
import bp.aplicaciones.mantenimientos.modelo.modelo_marca;
import bp.aplicaciones.mantenimientos.modelo.modelo_modelo;
import bp.aplicaciones.mantenimientos.modelo.modelo_opcion;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_10;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_11;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_12;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_5;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_7;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_8;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_9;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_rack;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_campo_mantenimiento;
import bp.aplicaciones.mantenimientos.modelo.modelo_respaldo;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitante;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
import bp.aplicaciones.mantenimientos.modelo.modelo_tarea_periodica;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_aprobador;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_clasificacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_conector;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_dispositivo;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_documento;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_equipo;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_ingreso;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_servicio;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_solicitud;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_tarea;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_trabajo;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_ubicacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_turno;
import bp.aplicaciones.mantenimientos.modelo.modelo_ubicacion_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_ur;
import bp.aplicaciones.mantenimientos.modelo.modelo_usuario;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.personal.DAO.dao_solicitud_personal;
import bp.aplicaciones.personal.modelo.modelo_detalle_solicitud_personal;
import bp.aplicaciones.personal.modelo.modelo_registro_permanencia;
import bp.aplicaciones.personal.modelo.modelo_solicitud_personal;
import bp.aplicaciones.sibod.DAO.dao_movimiento;

public class ConsultasABaseDeDatos {

	private Error error = new Error();
	private Informativos informativos = new Informativos();

	/*
	 * * Metodos que retornan información desde la base de datos para el modulo de
	 * cintas Diners
	 * 
	 */

	/*
	 * Metodo que permite validar si existe ya creada una solicitud de cintas
	 * 
	 */

	public int validarSiExisteSolicitudMovimiento(String ticket)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao_movimiento_dn dao = new dao_movimiento_dn();
		int totalSolicitudes = 0;
		try {
			totalSolicitudes = dao.validarSiExisteSolicitudMovimiento(ticket);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return totalSolicitudes;
	}

	/*
	 * * Metodo que devuelve las posicion maxima de una ubicacion
	 * 
	 */

	public int posicionMaximaEnUbicacionDN(long id_ubicacion)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao_articulo_dn dao = new dao_articulo_dn();
		int posicionMaxima = 0;
		posicionMaxima = dao.obtenerPosicionMaximaEnUbicacion(id_ubicacion);
		return posicionMaxima;
	}

	/*
	 * * Metodo que devuelve la capacidad maxima de una ubicacion
	 * 
	 */

	public int capacidadMaximaEnUbicacionDN(long id_ubicacion, String valida_capacidad)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao_articulo_dn dao = new dao_articulo_dn();
		int capacidadMaxima = 0;
		capacidadMaxima = dao.obtenerCapacidadMaximaEnUbicacion(id_ubicacion, valida_capacidad);
		return capacidadMaxima;
	}

	/*
	 * * Metodo que devuelve la cantidad total de articulos de una ubicacion
	 * 
	 */

	public int totalArticulosEnUbicacionDN(long id_ubicacion)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao_articulo_dn dao = new dao_articulo_dn();
		int totalArticulos = 0;
		totalArticulos = dao.obtenerTotalArticulosEnUbicacion(id_ubicacion);
		return totalArticulos;
	}

	/*
	 * * Metodo que devuelve "S" si se valida capacidad en la ubicacion o "N" si no
	 * * se valida capacidad en la ubicacion
	 * 
	 */

	public String seValidaCapacidadEnUbicacionDN(long id_ubicacion)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao_articulo_dn dao = new dao_articulo_dn();
		String seValidaCapacidad = "N";
		seValidaCapacidad = dao.seValidaCapacidadEnUbicacion(id_ubicacion);
		return seValidaCapacidad;
	}

	/*
	 * * Metodo que permite validar si ya existe un articulo con codigo y fecha de
	 * inicio
	 * 
	 */

	public int validarSiCodigoYFechaDeInicioDeArticuloExiste(String codigo, String fecha_inicio, long categoria)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao_articulo_dn dao = new dao_articulo_dn();
		int totalArticulos = 0;
		totalArticulos = dao.validarSiCodigoYFechaDeInicioDeArticuloExiste(codigo, fecha_inicio, categoria);
		return totalArticulos;
	}

	/*
	 * * Metodo que permite validar si ya existe un articulo con codigo y fecha de
	 * inicio y fecha de fin
	 * 
	 */

	public int validarSiCodigoYFechaDeInicioYFechaDeFinDeArticuloExiste(String codigo, String fecha_inicio,
			String fecha_fin, long categoria)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao_articulo_dn dao = new dao_articulo_dn();
		int totalArticulos = 0;
		totalArticulos = dao.validarSiCodigoYFechaDeInicioYFechaDeFinDeArticuloExiste(codigo, fecha_inicio, fecha_fin,
				categoria);
		return totalArticulos;
	}

	/*
	 * * Metodo que devuelve Categorias
	 * 
	 */

	public List<modelo_categoria_dn> cargarCategoriasDN(String criterio, long localidad, int tipo, long id, int limite)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_categoria_dn dao = new dao_categoria_dn();
		List<modelo_categoria_dn> listaCategoria = new ArrayList<modelo_categoria_dn>();
		try {
			listaCategoria = dao.obtenerCategorias(criterio, String.valueOf(localidad), tipo, id, limite);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaCategoria;
	}

	/*
	 * * Metodo que devuelve Ubicaciones
	 * 
	 */

	public List<modelo_ubicacion_dn> cargarUbicacionesDN(String criterio, long localidad, int tipo, long empresa,
			long tipo_ubicacion, long id, int limite)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_ubicacion_dn dao = new dao_ubicacion_dn();
		List<modelo_ubicacion_dn> listaUbicacion = new ArrayList<modelo_ubicacion_dn>();
		try {
			listaUbicacion = dao.obtenerUbicaciones(criterio, String.valueOf(localidad), tipo, empresa, tipo_ubicacion,
					id, limite);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaUbicacion;
	}

	/*
	 * * Metodo que devuelve las solicitudes
	 * 
	 */

	public List<modelo_solicitud> cargarSolicitudes(String criterio, String fecha_inicio, String fecha_fin,
			String estado, long id_mantenimiento, long id_registro, int tipo)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_solicitud dao = new dao_solicitud();
		List<modelo_solicitud> listaSolicitud = new ArrayList<modelo_solicitud>();
		try {
			listaSolicitud = dao.obtenerSolicitudes(criterio, fecha_inicio, fecha_fin, estado, id_mantenimiento,
					id_registro, tipo);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaSolicitud;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve Articulos
	 * 
	 */

	public List<modelo_articulo_dn> cargarArticulosDN(String criterio, long localidad, String empresa, int tipo,
			int limite, String fecha_inicio, String fecha_fin)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_articulo_dn dao = new dao_articulo_dn();
		List<modelo_articulo_dn> listaArticulo = new ArrayList<modelo_articulo_dn>();
		try {
			listaArticulo = dao.obtenerArticulos(criterio, String.valueOf(localidad), empresa, tipo, limite,
					fecha_inicio, fecha_fin);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaArticulo;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve los movimientos
	 * 
	 */

	public List<modelo_movimiento_dn> cargarMovimientosDN(String criterio, String fecha_inicio, String fecha_fin,
			String localidad, int limite, int tipo, String estado)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_movimiento_dn dao = new dao_movimiento_dn();
		List<modelo_movimiento_dn> listaMovimientos = new ArrayList<modelo_movimiento_dn>();
		try {
			listaMovimientos = dao.obtenerMovimientos(criterio, fecha_inicio, fecha_fin, localidad, limite, tipo,
					estado);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaMovimientos;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve el detalle de los movimientos
	 * 
	 */

	public List<modelo_movimiento_detalle_dn> cargarDetalleMovimientosDN(String criterio, String fecha_inicio,
			String fecha_fin, String localidad, int limite, int tipo, String estado)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_movimiento_dn dao = new dao_movimiento_dn();
		List<modelo_movimiento_detalle_dn> listaDetalleMovimientos = new ArrayList<modelo_movimiento_detalle_dn>();
		try {
			listaDetalleMovimientos = dao.obtenerDetalleMovimientos(criterio, fecha_inicio, fecha_fin, localidad,
					limite, tipo, estado);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaDetalleMovimientos;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve respaldos
	 * 
	 */

	public List<modelo_respaldo> cargarRespaldosDN(String criterio, int tipo, String localidad,
			String mantenimiento_opcion, int limite) throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_respaldo dao = new dao_respaldo();
		List<modelo_respaldo> listaRespaldo = new ArrayList<modelo_respaldo>();
		try {
			listaRespaldo = dao.obtenerRespaldos(criterio, tipo, localidad, mantenimiento_opcion, limite);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaRespaldo;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve capacidades
	 * 
	 */

	public List<modelo_capacidad> cargarCapacidadesDN(String criterio, int tipo, long id, int limite)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_capacidad dao = new dao_capacidad();
		List<modelo_capacidad> listaCapacidad = new ArrayList<modelo_capacidad>();
		try {
			listaCapacidad = dao.obtenerCapacidades(criterio, tipo, id, limite);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaCapacidad;
	}

	/*
	 * * Metodos que retornan información desde la base de datos de los parametros
	 * generales y configuraciones
	 * 
	 */

	/*
	 * *
	 * 
	 * Metodo que devuelve los Parametros #1
	 * 
	 */

	public List<modelo_parametros_generales_1> cargarParametros1()
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_parametros_generales_1 dao = new dao_parametros_generales_1();
		List<modelo_parametros_generales_1> listaParametros = new ArrayList<modelo_parametros_generales_1>();
		try {
			listaParametros = dao.obtenerParametros();
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaParametros;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve los Parametros #5
	 * 
	 */

	public List<modelo_parametros_generales_5> cargarParametros5(String campo, String opcion, int tipo)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_parametros_generales_5 dao = new dao_parametros_generales_5();
		List<modelo_parametros_generales_5> listaParametros = new ArrayList<modelo_parametros_generales_5>();
		try {
			listaParametros = dao.obtenerRelacionesOpciones(campo, opcion, tipo);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaParametros;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve los Parametros #7
	 * 
	 */

	public List<modelo_parametros_generales_7> cargarParametros7(String estado, String color, long localidad, int tipo)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_parametros_generales_7 dao = new dao_parametros_generales_7();
		List<modelo_parametros_generales_7> listaParametros = new ArrayList<modelo_parametros_generales_7>();
		try {
			listaParametros = dao.obtenerRelacionesEstados(estado, color, localidad, tipo);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaParametros;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve los Parametros #8
	 * 
	 */

	public List<modelo_parametros_generales_8> cargarParametros8(String estado, String color, long localidad, int tipo)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_parametros_generales_8 dao = new dao_parametros_generales_8();
		List<modelo_parametros_generales_8> listaParametros = new ArrayList<modelo_parametros_generales_8>();
		try {
			listaParametros = dao.obtenerRelacionesEstados(estado, color, localidad, tipo);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaParametros;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve los Parametros #9
	 * 
	 */

	public List<modelo_parametros_generales_9> cargarParametros9(String empresa, String nom_ticket, long localidad,
			int tipo) throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_parametros_generales_9 dao = new dao_parametros_generales_9();
		List<modelo_parametros_generales_9> listaParametros = new ArrayList<modelo_parametros_generales_9>();
		try {
			listaParametros = dao.obtenerRelacionesEmpresas(empresa, nom_ticket, localidad, tipo);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaParametros;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve los Parametros #11
	 * 
	 */

	public List<modelo_parametros_generales_11> cargarParametros11(String opcion, String tipo_servicio,
			String localidad, int tipo) throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_parametros_generales_11 dao = new dao_parametros_generales_11();
		List<modelo_parametros_generales_11> listaParametros = new ArrayList<modelo_parametros_generales_11>();
		try {
			listaParametros = dao.obtenerRelacionesOpciones(opcion, tipo_servicio, localidad, tipo);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaParametros;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve los Parametros #12
	 * 
	 */

	public List<modelo_parametros_generales_12> cargarParametros12(String campo, String opcion, long localidad,
			int tipo) throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_parametros_generales_12 dao = new dao_parametros_generales_12();
		List<modelo_parametros_generales_12> listaParametros = new ArrayList<modelo_parametros_generales_12>();
		try {
			listaParametros = dao.obtenerRelacionesOpciones(campo, opcion, localidad, tipo);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaParametros;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve las localidades
	 * 
	 */

	public List<modelo_localidad> cargarLocalidades(String criterio, int tipo, long id, int limite)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_localidad dao = new dao_localidad();
		List<modelo_localidad> listaLocalidad = new ArrayList<modelo_localidad>();
		try {
			listaLocalidad = dao.obtenerLocalidades(criterio, tipo, id, limite);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaLocalidad;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve los mantenimientos
	 * 
	 */

	public List<modelo_mantenimiento> cargarMantenimientos(String criterio, int tipo)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_mantenimiento dao = new dao_mantenimiento();
		List<modelo_mantenimiento> listaMantenimiento = new ArrayList<modelo_mantenimiento>();
		try {
			listaMantenimiento = dao.obtenerMantenimientos(criterio, tipo);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaMantenimiento;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve las opciones
	 * 
	 */

	public List<modelo_opcion> cargarOpciones(String criterio)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_opcion dao = new dao_opcion();
		List<modelo_opcion> listaOpcion = new ArrayList<modelo_opcion>();
		try {
			listaOpcion = dao.obtenerOpciones(criterio);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaOpcion;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve las empresas
	 * 
	 */

	public List<modelo_empresa> cargarEmpresas(String criterio, int tipo, String localidad, String mantenimiento_opcion,
			int limite) throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_empresa dao = new dao_empresa();
		List<modelo_empresa> listaEmpresa = new ArrayList<modelo_empresa>();
		try {
			listaEmpresa = dao.obtenerEmpresas(criterio, tipo, localidad, mantenimiento_opcion, limite);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaEmpresa;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve los tipos de servicios
	 * 
	 */

	public List<modelo_tipo_servicio> cargarTipoDeServicios(String criterio, int tipo, long id, int limite)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_servicio dao = new dao_tipo_servicio();
		List<modelo_tipo_servicio> listaTipoDeServicio = new ArrayList<modelo_tipo_servicio>();
		try {
			listaTipoDeServicio = dao.obtenerTipoServicios(criterio, tipo, id, limite);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaTipoDeServicio;
	}

	public List<modelo_tipo_servicio> cargarTipoDeServicios1(String criterio, int tipo, long id, int limite)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_servicio dao = new dao_tipo_servicio();
		List<modelo_tipo_servicio> listaTipoDeServicio = new ArrayList<modelo_tipo_servicio>();
		try {
			listaTipoDeServicio = dao.obtenerTipoServicios(criterio, tipo, id, limite);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaTipoDeServicio;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve los tipos de solicitudes
	 * 
	 */

	public List<modelo_tipo_solicitud> cargarTipoDeSolicitudes(String criterio, int tipo)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_solicitud dao = new dao_tipo_solicitud();
		List<modelo_tipo_solicitud> listaTipoDeSolicitud = new ArrayList<modelo_tipo_solicitud>();
		try {
			listaTipoDeSolicitud = dao.obtenerTipoSolicitudes(criterio, tipo);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaTipoDeSolicitud;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve los campos
	 * 
	 */

	public List<modelo_campo> cargarCampos(String criterio, int tipo, long id)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_campo dao = new dao_campo();
		List<modelo_campo> listaDeCampos = new ArrayList<modelo_campo>();
		try {
			listaDeCampos = dao.obtenerCampos(criterio, tipo, id);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaDeCampos;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve la relacion entre los mantenimientos y los campos
	 * 
	 */

	public List<modelo_relacion_campo_mantenimiento> cargarRelacionMantenimientosCampos(String campo,
			String mantenimiento, int tipo) throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_relacion_campo dao = new dao_relacion_campo();
		List<modelo_relacion_campo_mantenimiento> listaRelacion = new ArrayList<modelo_relacion_campo_mantenimiento>();
		try {
			listaRelacion = dao.obtenerRelacionesMantenimientos(campo, mantenimiento, tipo);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaRelacion;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve los perfiles
	 * 
	 */

	public List<modelo_perfil> cargarPerfil(String criterio, int tipo, long id)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_perfil dao = new dao_perfil();
		List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
		try {
			listaPerfil = dao.obtenerPerfiles(criterio, tipo, id);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaPerfil;
	}

	/*
	 * * Metodo que permite obtener el tipo de solicitud del registro y
	 * mantenimiento
	 * 
	 */

	public int otenerTipoDeSolicitudPendienteDeAccion(long mantenimiento, long registro)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao_solicitud dao = new dao_solicitud();
		int tipoDeSolicitud = 0;
		try {
			tipoDeSolicitud = dao.otenerTipoDeSolicitudPendienteDeAccion(mantenimiento, registro);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return tipoDeSolicitud;
	}

	/*
	 * * Metodo que permite obtener el estado de la solicitud
	 * 
	 */

	public modelo_solicitud obtenerSolicitudesxEstado(String criterio, long id_mantenimiento, long id_registro,
			int tipo) throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao_solicitud dao = new dao_solicitud();
		modelo_solicitud solicitud = new modelo_solicitud();
		try {
			solicitud = dao.obtenerSolicitudesxEstado(criterio, id_mantenimiento, id_registro, tipo);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return solicitud;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve los estados de bitacora
	 * 
	 */

	public List<modelo_estado_bitacora> cargarEstadosBitacora(String criterio, int tipo, String localidad)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		List<modelo_estado_bitacora> listaEstado = new ArrayList<modelo_estado_bitacora>();
		dao_estado_bitacora dao = new dao_estado_bitacora();
		try {
			listaEstado = dao.obtenerEstados(criterio, tipo, localidad);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaEstado;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve los tipos de tareas
	 * 
	 */

	public List<modelo_tipo_tarea> cargarTipoDeTareas(String criterio, int tipo, long id, int limite)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		List<modelo_tipo_tarea> listaTipoTarea = new ArrayList<modelo_tipo_tarea>();
		dao_tipo_tarea dao = new dao_tipo_tarea();
		try {
			listaTipoTarea = dao.obtenerTipoTareas(criterio, tipo, id, limite);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaTipoTarea;
	}

	public List<modelo_tipo_clasificacion> cargarClasificaciones(String criterio, int tipo, long id, int limite)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		List<modelo_tipo_clasificacion> listaClasificacion = new ArrayList<modelo_tipo_clasificacion>();
		dao_tipo_clasificacion dao = new dao_tipo_clasificacion();
		try {
			listaClasificacion = dao.obtenerTipoClasificaciones(criterio, tipo, id, limite);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaClasificacion;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve los turnos
	 * 
	 */

	public List<modelo_turno> cargarTurnos(String criterio)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_turno dao = new dao_turno();
		List<modelo_turno> listaTurno = new ArrayList<modelo_turno>();
		try {
			listaTurno = dao.obtenerTurnos(criterio);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaTurno;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve el registro de los turnos
	 * 
	 */

	public List<modelo_registra_turno> cargarRegistroTurnos(String criterio, int tipo, long id_turno, String fecha,
			long localidad) throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_registra_turno dao = new dao_registra_turno();
		List<modelo_registra_turno> listaRegistroTurno = new ArrayList<modelo_registra_turno>();
		try {
			listaRegistroTurno = dao.obtenerRegistroTurnos(criterio, tipo, id_turno, fecha, localidad);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaRegistroTurno;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve las bitacoras
	 * 
	 */

	public List<modelo_bitacora> cargarBitacoras(String criterio, int tipo, long id_cliente, String fecha, String turno,
			long localidad, String fecha_inicio, String fecha_fin, long id_tipo_servicio, long id_tipo_tarea,
			String use_usuario, int limite) throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_bitacora dao = new dao_bitacora();
		List<modelo_bitacora> listaBitacora = new ArrayList<modelo_bitacora>();
		try {
			listaBitacora = dao.obtenerBitacoras(criterio, tipo, id_cliente, fecha, turno, localidad, fecha_inicio,
					fecha_fin, id_tipo_servicio, id_tipo_tarea, use_usuario, limite);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaBitacora;
	}

	/*
	 * Metodo que permite validar si ya existe una tarea registrada
	 * 
	 */

	public int validarSiExisteTareaRegistrada(String ticket_externo, String id_tipo_tarea)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao_bitacora dao = new dao_bitacora();
		int totalTareas = 0;
		try {
			totalTareas = dao.validarSiExisteTareaRegistrada(ticket_externo, id_tipo_tarea);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return totalTareas;
	}

	/*
	 * Metodo que permite validar si ya existe una tarea registrada en tarea de
	 * proveedor
	 * 
	 */

	public int validarSiExisteTareaRegistradaTareaProveedor(String ticket_externo, String id_tipo_tarea)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao_bitacora dao = new dao_bitacora();
		int totalTareas = 0;
		try {
			totalTareas = dao.validarSiExisteTareaRegistradaTareaProveedor(ticket_externo, id_tipo_tarea);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return totalTareas;
	}

	/*
	 * Metodo que permite validar si existen tareas programadas vencidas
	 * 
	 */

	public int validarSiExistenTareasProgramadasVencidas(String estado)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao_bitacora dao = new dao_bitacora();
		int totalTareasVencidas = 0;
		try {
			totalTareasVencidas = dao.validarSiExistenTareasProgramadasVencidas(estado);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return totalTareasVencidas;
	}

	/*
	 * Metodo que permite validar si se crearon las tareas automaticas
	 * 
	 */

	public int validarSiTareasAutomaticasSeCrearon(String fecha_inicio, String fecha_fin)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao_bitacora dao = new dao_bitacora();
		int totalTurnosCreados = 0;
		try {
			totalTurnosCreados = dao.validarSiTareasAutomaticasSeCrearon(fecha_inicio, fecha_fin);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return totalTurnosCreados;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve las tarea de proveedores
	 * 
	 */

	public List<modelo_tarea_proveedor> cargarTareasProveedores(String criterio, int tipo, long id_cliente,
			String fecha, String turno, long localidad, String fecha_inicio, String fecha_fin, long id_tipo_servicio,
			String use_usuario, long id_estado, int limite)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tarea_proveedor dao = new dao_tarea_proveedor();
		List<modelo_tarea_proveedor> listaTareaProveedor = new ArrayList<modelo_tarea_proveedor>();
		try {
			listaTareaProveedor = dao.obtenerTareasProveedores(criterio, tipo, id_cliente, fecha, turno, localidad,
					fecha_inicio, fecha_fin, id_tipo_servicio, use_usuario, id_estado, limite);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaTareaProveedor;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve las tareas periodicas
	 * 
	 */

	public List<modelo_tarea_periodica> cargarTareasPeriodicas(String criterio, int tipo, long id, String fecha,
			String turno, int limite) throws ClassNotFoundException, FileNotFoundException, IOException {
		List<modelo_tarea_periodica> listaTareasPeriodicas = new ArrayList<modelo_tarea_periodica>();
		dao_tarea_periodica dao = new dao_tarea_periodica();

		try {
			listaTareasPeriodicas = dao.obtenerTareasPeriodicas(criterio, tipo, id, fecha, turno, limite);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaTareasPeriodicas;
	}

	/*
	 * Metodo que permite validar si se crearon las tareas automaticas
	 * 
	 */

	public int validarSiTareaProgramadaExiste(String ticket_externo, String id_tipo_tarea)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao_tarea_proveedor dao = new dao_tarea_proveedor();
		int totalTareasProgramadas = 0;
		try {
			totalTareasProgramadas = dao.validarSiTareaProgramadaExiste(ticket_externo, id_tipo_tarea);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return totalTareasProgramadas;
	}

	/*
	 * Modulo de bodega
	 * 
	 */

	/*
	 * * Metodo que devuelve el id del tipo de servicio a partir del ticket
	 * 
	 */

	public long obtenerIdTipoServicioAPartirDeTicket(String ticket, long id_tipo_tarea, long id_dc)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao_movimiento dao = new dao_movimiento();
		long id_tipo_servicio = 0;
		try {
			id_tipo_servicio = dao.obtenerIdTipoServicioAPartirDeTicket(ticket, id_tipo_tarea, id_dc);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return id_tipo_servicio;
	}

	public long obtenerIdTipoClasificacionAPartirDeTicket(String ticket, long id_tipo_tarea, long id_dc)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao_movimiento dao = new dao_movimiento();
		long id_tipo_clasificacion = 0;
		try {
			id_tipo_clasificacion = dao.obtenerIdTipoClasificacionAPartirDeTicket(ticket, id_tipo_tarea, id_dc);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return id_tipo_clasificacion;
	}

	/*
	 * Modulo de personal
	 * 
	 */

	/*
	 * *
	 * 
	 * Metodo que devuelve los tipos de ingreso
	 * 
	 */

	public List<modelo_tipo_ingreso> cargarTipoIngresos(String criterio)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_ingreso dao = new dao_tipo_ingreso();
		List<modelo_tipo_ingreso> listaTipoIngreso = new ArrayList<modelo_tipo_ingreso>();
		try {
			listaTipoIngreso = dao.obtenerTipoIngresos(criterio);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaTipoIngreso;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve los tipos de aprobador
	 * 
	 */

	public List<modelo_tipo_aprobador> cargarTipoAprobadores(String criterio)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_aprobador dao = new dao_tipo_aprobador();
		List<modelo_tipo_aprobador> listaTipoAprobador = new ArrayList<modelo_tipo_aprobador>();
		try {
			listaTipoAprobador = dao.obtenerTipoAprobadores(criterio);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaTipoAprobador;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve los tipos de trabajo
	 * 
	 */

	public List<modelo_tipo_trabajo> cargarTipoTrabajos(String criterio)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_trabajo dao = new dao_tipo_trabajo();
		List<modelo_tipo_trabajo> listaTipoTrabajo = new ArrayList<modelo_tipo_trabajo>();
		try {
			listaTipoTrabajo = dao.obtenerTipoTrabajos(criterio);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaTipoTrabajo;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve los tipos de dispositivo
	 * 
	 */

	public List<modelo_tipo_dispositivo> cargarTipoDispositivos(String criterio)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_dispositivo dao = new dao_tipo_dispositivo();
		List<modelo_tipo_dispositivo> listaTipoDispositivo = new ArrayList<modelo_tipo_dispositivo>();
		try {
			listaTipoDispositivo = dao.obtenerTipoDispositivos(criterio);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaTipoDispositivo;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve los tipos de ubicaciones
	 * 
	 */

	public List<modelo_tipo_ubicacion> cargarTipoUbicaciones(String criterio, long id_tipo_trabajo, int tipo)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_ubicacion dao = new dao_tipo_ubicacion();
		List<modelo_tipo_ubicacion> listaTipoUbicacion = new ArrayList<modelo_tipo_ubicacion>();
		try {
			listaTipoUbicacion = dao.obtenerTipoUbicaciones(criterio, id_tipo_trabajo, tipo);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaTipoUbicacion;
	}

	/*
	 * Metodo que permite validar si existe ya creada una solicitud de personal
	 * 
	 */

	public int validarSiExisteSolicitudPersonal(String ticket)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		dao_solicitud_personal dao = new dao_solicitud_personal();
		int totalSolicitudes = 0;
		try {
			totalSolicitudes = dao.validarSiExisteSolicitudPersonal(ticket);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return totalSolicitudes;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve las solicitudes de personal
	 * 
	 */

	public List<modelo_solicitud_personal> cargarSolicitudesPersonal(String criterio, int tipo, long id_cliente,
			String fecha_solicitud_1, String fecha_solicitud_2, String fecha_inicio, String fecha_fin,
			long id_localidad, String estado, int limite)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_solicitud_personal dao = new dao_solicitud_personal();
		List<modelo_solicitud_personal> listaSolicitudPersonal = new ArrayList<modelo_solicitud_personal>();
		try {
			listaSolicitudPersonal = dao.obtenerSolicitudesPersonal(criterio, tipo, id_cliente, fecha_solicitud_1,
					fecha_solicitud_2, fecha_inicio, fecha_fin, id_localidad, estado, limite);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaSolicitudPersonal;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve el detalle de las solicitudes de personal
	 * 
	 */

	public List<modelo_detalle_solicitud_personal> cargarDetalleSolicitudPersonal(String criterio, int tipo)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_solicitud_personal dao = new dao_solicitud_personal();
		List<modelo_detalle_solicitud_personal> listaDetalleSolicitudPersonal = new ArrayList<modelo_detalle_solicitud_personal>();
		try {
			listaDetalleSolicitudPersonal = dao.obtenerDetalleSolicitudPersonal(criterio, tipo);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaDetalleSolicitudPersonal;
	}

	/*
	 * *
	 * 
	 * Metodo que devuelve los registros de permanencia
	 * 
	 */

	public List<modelo_registro_permanencia> cargarRegistrosPermanencia(String criterio, int tipo, String estado,
			long id_localidad, int limite, String fecha_inicio, String fecha_fin)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_solicitud_personal dao = new dao_solicitud_personal();
		List<modelo_registro_permanencia> listaRegistroPermanencia = new ArrayList<modelo_registro_permanencia>();
		try {
			listaRegistroPermanencia = dao.obtenerRegistroPermanencia(criterio, tipo, estado, id_localidad, limite,
					fecha_inicio, fecha_fin);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaRegistroPermanencia;
	}

	public List<modelo_registro_permanencia> cargarRegistrosPermanencia2(String criterio, int tipo, String estado,
			long id_localidad, int limite, String fecha_inicio, String fecha_fin)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_solicitud_personal dao = new dao_solicitud_personal();
		List<modelo_registro_permanencia> listaRegistroPermanencia = new ArrayList<modelo_registro_permanencia>();
		try {
			listaRegistroPermanencia = dao.obtenerRegistroPermanencia2(criterio, tipo, estado, id_localidad, limite,
					fecha_inicio, fecha_fin);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaRegistroPermanencia;
	}

	public List<modelo_registro_permanencia> cargarRegistrosPermanencia3(String criterio, int tipo, String estado,
			long id_localidad, int limite, String fecha_inicio, String fecha_fin)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_solicitud_personal dao = new dao_solicitud_personal();
		List<modelo_registro_permanencia> listaRegistroPermanencia = new ArrayList<modelo_registro_permanencia>();
		try {
			listaRegistroPermanencia = dao.obtenerRegistroPermanencia3(criterio, tipo, estado, id_localidad, limite,
					fecha_inicio, fecha_fin);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaRegistroPermanencia;
	}

	public List<modelo_registro_permanencia> cargarRegistrosPermanencia4(String criterio, int tipo, String estado,
			long id_localidad, int limite, String fecha_inicio, String fecha_fin)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_solicitud_personal dao = new dao_solicitud_personal();
		List<modelo_registro_permanencia> listaRegistroPermanencia = new ArrayList<modelo_registro_permanencia>();
		try {
			listaRegistroPermanencia = dao.obtenerRegistroPermanencia4(criterio, tipo, estado, id_localidad, limite,
					fecha_inicio, fecha_fin);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaRegistroPermanencia;
	}

	/*
	 * Modulo de control de cambio
	 * 
	 */

	/*
	 * *
	 * 
	 * Metodo que devuelve los controles de cambio
	 * 
	 */

	public List<modelo_control_cambio> cargarControlesDeCambio(String criterio, int tipo, long id_cliente,
			long id_localidad, int limite) throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_control_cambio dao = new dao_control_cambio();
		List<modelo_control_cambio> listaControlCambio = new ArrayList<modelo_control_cambio>();
		try {
			listaControlCambio = dao.obtenerControlCambios(criterio, tipo, id_cliente, id_localidad, limite);
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
		return listaControlCambio;
	}

	/*
	 * -----------------------------------------------------------------------------
	 * 
	 */

	/* Localidades */

	public List<modelo_localidad> consultarLocalidades(long id1, long id2, String criterio1, String criterio2,
			int limite, int tipo) {
		List<modelo_localidad> lista = new ArrayList<modelo_localidad>();
		dao_localidad dao = new dao_localidad();
		lista = dao.consultarLocalidades(id1, id2, criterio1, criterio2, limite, tipo);
		return lista;
	}

	/* Manuales */

	public List<modelo_manual> consultarManuales(long id1, long id2, String criterio1, String criterio2, int limite,
			int tipo) {
		List<modelo_manual> lista = new ArrayList<modelo_manual>();
		dao_manual dao = new dao_manual();
		lista = dao.consultarManuales(id1, id2, criterio1, criterio2, limite, tipo);
		return lista;
	}

	/* Filas */

	public List<modelo_fila> consultarFilas(long id1, long id2, String criterio1, String criterio2, int limite,
			int tipo) {
		List<modelo_fila> lista = new ArrayList<modelo_fila>();
		dao_fila dao = new dao_fila();
		lista = dao.consultarFilas(id1, id2, criterio1, criterio2, limite, tipo);
		return lista;
	}

	/* Racks */

	public List<modelo_rack> consultarRacks(long id1, long id2, String criterio1, String criterio2, int limite,
			int tipo) {
		List<modelo_rack> lista = new ArrayList<modelo_rack>();
		dao_rack dao = new dao_rack();
		lista = dao.consultarRacks(id1, id2, criterio1, criterio2, limite, tipo);
		return lista;
	}

	/* Empresas */

	public List<modelo_empresa> consultarEmpresas(long id1, long id2, String criterio1, String criterio2, int limite,
			int tipo) {
		List<modelo_empresa> lista = new ArrayList<modelo_empresa>();
		dao_empresa dao = new dao_empresa();
		lista = dao.consultarEmpresas(id1, id2, criterio1, criterio2, limite, tipo);
		return lista;
	}

	/* Marcas */

	public List<modelo_marca> consultarMarcas(long id1, long id2, String criterio1, String criterio2, int limite,
			int tipo) {
		List<modelo_marca> lista = new ArrayList<modelo_marca>();
		dao_marca dao = new dao_marca();
		lista = dao.consultarMarcas(id1, id2, criterio1, criterio2, limite, tipo);
		return lista;
	}

	/* Modelos */

	public List<modelo_modelo> consultarModelos(long id1, long id2, String criterio1, String criterio2, int limite,
			int tipo) {
		List<modelo_modelo> lista = new ArrayList<modelo_modelo>();
		dao_modelo dao = new dao_modelo();
		lista = dao.consultarModelos(id1, id2, criterio1, criterio2, limite, tipo);
		return lista;
	}

	/* Tipos de equipo */

	public List<modelo_tipo_equipo> consultarTipoEquipos(long id1, long id2, String criterio1, String criterio2,
			int limite, int tipo) {
		List<modelo_tipo_equipo> lista = new ArrayList<modelo_tipo_equipo>();
		dao_tipo_equipo dao = new dao_tipo_equipo();
		lista = dao.consultarTipoEquipos(id1, id2, criterio1, criterio2, limite, tipo);
		return lista;
	}

	/* Equipos */

	public List<modelo_equipo> consultarEquipos(long id1, long id2, String criterio1, String criterio2, int limite,
			int tipo) {
		List<modelo_equipo> lista = new ArrayList<modelo_equipo>();
		dao_equipo dao = new dao_equipo();
		lista = dao.consultarEquipos(id1, id2, criterio1, criterio2, limite, tipo);
		return lista;
	}

	/* Tipos de conector */

	public List<modelo_tipo_conector> consultarTipoConectores(long id1, long id2, String criterio1, String criterio2,
			int limite, int tipo) {
		List<modelo_tipo_conector> lista = new ArrayList<modelo_tipo_conector>();
		dao_tipo_conector dao = new dao_tipo_conector();
		lista = dao.consultarTipoConectores(id1, id2, criterio1, criterio2, limite, tipo);
		return lista;
	}

	/* Estados de equipo */

	public List<modelo_estado_equipo> consultarEstadosEquipo(long id1, long id2, String criterio1, String criterio2,
			int limite, int tipo) {
		List<modelo_estado_equipo> lista = new ArrayList<modelo_estado_equipo>();
		dao_estado_equipo dao = new dao_estado_equipo();
		lista = dao.consultarEstadosEquipo(id1, id2, criterio1, criterio2, limite, tipo);
		return lista;
	}

	/* Urs */

	public List<modelo_ur> consultarUrs(long id1, long id2, String criterio1, String criterio2, int limite, int tipo) {
		List<modelo_ur> lista = new ArrayList<modelo_ur>();
		dao_ur dao = new dao_ur();
		lista = dao.consultarUrs(id1, id2, criterio1, criterio2, limite, tipo);
		return lista;
	}

	/* Usuarios */

	public List<modelo_usuario> consultarUsuarios(long id1, long id2, String criterio1, String criterio2, int limite,
			int tipo) {
		List<modelo_usuario> lista = new ArrayList<modelo_usuario>();
		dao_usuario dao = new dao_usuario();
		lista = dao.consultarUsuarios(id1, id2, criterio1, criterio2, limite, tipo);
		return lista;
	}

	/* Perfiles */

	public List<modelo_perfil> consultarPerfiles(long id1, long id2, String criterio1, String criterio2, int limite,
			int tipo) {
		List<modelo_perfil> lista = new ArrayList<modelo_perfil>();
		dao_perfil dao = new dao_perfil();
		lista = dao.consultarPerfiles(id1, id2, criterio1, criterio2, limite, tipo);
		return lista;
	}

	/* Mantenimientos */

	public List<modelo_mantenimiento> consultarMantenimientos(long id1, long id2, String criterio1, String criterio2,
			int limite, int tipo) {
		List<modelo_mantenimiento> lista = new ArrayList<modelo_mantenimiento>();
		dao_mantenimiento dao = new dao_mantenimiento();
		lista = dao.consultarMantenimientos(id1, id2, criterio1, criterio2, limite, tipo);
		return lista;
	}

	/* Opciones */

	public List<modelo_opcion> consultarOpciones(long id1, long id2, String criterio1, String criterio2, int limite,
			int tipo) {
		List<modelo_opcion> lista = new ArrayList<modelo_opcion>();
		dao_opcion dao = new dao_opcion();
		lista = dao.consultarOpciones(id1, id2, criterio1, criterio2, limite, tipo);
		return lista;
	}

	/* Tipo Documentos */

	public List<modelo_tipo_documento> consultarTipoDocumentos(long id1, long id2, String criterio1, String criterio2,
			int limite, int tipo) {
		List<modelo_tipo_documento> lista = new ArrayList<modelo_tipo_documento>();
		dao_tipo_documento dao = new dao_tipo_documento();
		lista = dao.consultarTipoDocumentos(id1, id2, criterio1, criterio2, limite, tipo);
		return lista;
	}

	/* Solicitantes */

	public List<modelo_solicitante> consultarSolicitantes(long id1, long id2, String criterio1, String criterio2,
			int limite, int tipo) {
		List<modelo_solicitante> lista = new ArrayList<modelo_solicitante>();
		dao_solicitante dao = new dao_solicitante();
		lista = dao.consultarSolicitantes(id1, id2, criterio1, criterio2, limite, tipo);
		return lista;
	}

	/* Tipo de Servicios */

	public List<modelo_tipo_servicio> consultarTipoServicios(long id1, long id2, String criterio1, String criterio2,
			int limite, int tipo) {
		List<modelo_tipo_servicio> lista = new ArrayList<modelo_tipo_servicio>();
		dao_tipo_servicio dao = new dao_tipo_servicio();
		lista = dao.consultarTipoServicios(id1, id2, criterio1, criterio2, limite, tipo);
		return lista;
	}

	/* Parametros Generales 10 */

	public List<modelo_parametros_generales_10> consultarParametrosGenerales10(long id1, long id2, String criterio1,
			String criterio2, int limite, int tipo) {
		List<modelo_parametros_generales_10> lista = new ArrayList<modelo_parametros_generales_10>();
		dao_parametros_generales_10 dao = new dao_parametros_generales_10();
		lista = dao.consultarParametrosGenerales10(id1, id2, criterio1, criterio2, limite, tipo);
		return lista;
	}

}
