//package bp.aplicaciones.controlador.mantenimientos.solicitud;
//
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.Iterator;
//import java.util.List;
//
//import org.zkoss.zk.ui.Component;
//import org.zkoss.zk.ui.Sessions;
//import org.zkoss.zk.ui.WrongValueException;
//import org.zkoss.zk.ui.event.Event;
//import org.zkoss.zk.ui.event.EventListener;
//import org.zkoss.zk.ui.event.Events;
//import org.zkoss.zk.ui.select.SelectorComposer;
//import org.zkoss.zk.ui.select.annotation.Listen;
//import org.zkoss.zk.ui.select.annotation.Wire;
//import org.zkoss.zkplus.databind.AnnotateDataBinder;
//import org.zkoss.zul.Messagebox;
//import org.zkoss.zul.Textbox;
//import org.zkoss.zul.Button;
//import org.zkoss.zul.Combobox;
//import org.zkoss.zul.Datebox;
//import org.zkoss.zul.Image;
//import org.zkoss.zul.Label;
//import org.zkoss.zul.Window;
//
//import bp.aplicaciones.cintas.modelo.modelo_movimiento_dn;
//import bp.aplicaciones.controlador.validar_datos;
//import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
//import bp.aplicaciones.extensiones.Fechas;
//import bp.aplicaciones.mantenimientos.DAO.dao_solicitud;
//import bp.aplicaciones.mantenimientos.modelo.modelo_articulo;
//import bp.aplicaciones.mantenimientos.modelo.modelo_articulo_dn;
//import bp.aplicaciones.mantenimientos.modelo.modelo_capacidad;
//import bp.aplicaciones.mantenimientos.modelo.modelo_categoria;
//import bp.aplicaciones.mantenimientos.modelo.modelo_categoria_dn;
//import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
//import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
//import bp.aplicaciones.mantenimientos.modelo.modelo_mantenimiento;
//import bp.aplicaciones.mantenimientos.modelo.modelo_opcion;
//import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_campo_mantenimiento;
//import bp.aplicaciones.mantenimientos.modelo.modelo_respaldo;
//import bp.aplicaciones.mantenimientos.modelo.modelo_solicitante;
//import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
//import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_solicitud;
//import bp.aplicaciones.mantenimientos.modelo.modelo_ubicacion;
//import bp.aplicaciones.mantenimientos.modelo.modelo_ubicacion_dn;
//import bp.aplicaciones.mantenimientos.modelo.modelo_usuario_bk;
//import bp.aplicaciones.mensajes.Error;
//import bp.aplicaciones.mensajes.Informativos;
//import bp.aplicaciones.mensajes.Validaciones;
//
//@SuppressWarnings({ "serial", "deprecation" })
//public class solicitar extends SelectorComposer<Component> {
//
//	AnnotateDataBinder binder;
//
//	@Wire
//	Window zSolicitar;
//	@Wire
//	Button btnGrabar, btnCancelar;
//	@Wire
//	Textbox txtId, txtConfiguracion, txtComentario1, txtSolicitante;
//	@Wire
//	Combobox cmbTipoSolicitud, cmbCampo;
//	@Wire
//	Datebox dtxFechaSolicitud;
//	@Wire
//	Label lCampo;
//	@Wire
//	Image iCampo;
//
//	List<modelo_opcion> listaOpcion = new ArrayList<modelo_opcion>();
//	List<modelo_mantenimiento> listaMantenimiento = new ArrayList<modelo_mantenimiento>();
//	List<modelo_tipo_solicitud> listaTipoSolicitud = new ArrayList<modelo_tipo_solicitud>();
//	List<modelo_relacion_campo_mantenimiento> listaCampo = new ArrayList<modelo_relacion_campo_mantenimiento>();
//
//	modelo_movimiento_dn movimiento_dn = new modelo_movimiento_dn();
//	modelo_capacidad capacidad = new modelo_capacidad();
//	modelo_localidad localidad = new modelo_localidad();
//	modelo_empresa empresa = new modelo_empresa();
//	modelo_usuario_bk usuario = new modelo_usuario_bk();
//	modelo_solicitante solicitante = new modelo_solicitante();
//	modelo_categoria categoria = new modelo_categoria();
//	modelo_respaldo respaldo = new modelo_respaldo();
//	modelo_ubicacion ubicacion = new modelo_ubicacion();
//	modelo_articulo articulo = new modelo_articulo();
//	modelo_categoria_dn categoria_dn = new modelo_categoria_dn();
//	modelo_ubicacion_dn ubicacion_dn = new modelo_ubicacion_dn();
//	modelo_articulo_dn articulo_dn = new modelo_articulo_dn();
//
//	long id = 0;
//
//	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
//	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
//	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
//	String user = (String) Sessions.getCurrent().getAttribute("user");
//	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
//
//	/* Se capturan las variables de sesion pasadas para la solicitud */
//	Object object = (Object) Sessions.getCurrent().getAttribute("objeto");
//	long id_mantenimiento = (long) Sessions.getCurrent().getAttribute("id_mantenimiento");
//	long id_opcion = (long) Sessions.getCurrent().getAttribute("id_opcion");
//	int tipo_solicitud = (Integer) Sessions.getCurrent().getAttribute("tipo_solicitud");
//
//	modelo_solicitud solicitud = new modelo_solicitud();
//
//	validar_datos validar = new validar_datos();
//
//	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
//	Fechas fechas = new Fechas();
//	Validaciones validaciones = new Validaciones();
//
//	Informativos informativos = new Informativos();
//	Error error = new Error();
//
//	@Override
//	public void doAfterCompose(Component comp) throws Exception {
//		super.doAfterCompose(comp);
//		binder = new AnnotateDataBinder(comp);
//		binder.loadAll();
//		/* Se eliminan las variables de sesion pasadas para la solicitud */
//		Sessions.getCurrent().removeAttribute("objeto");
//		Sessions.getCurrent().removeAttribute("id_mantenimiento");
//		Sessions.getCurrent().removeAttribute("id_opcion");
//		Sessions.getCurrent().removeAttribute("tipo_solicitud");
//		obtenerId();
//		inicializarListas();
//		inicializarSolicitud();
//		cargarFecha();
//		txtComentario1.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
//			public void onEvent(Event event) throws Exception {
//				txtComentario1.setText(txtComentario1.getText().toUpperCase());
//			}
//		});
//		txtComentario1.setDisabled(true);
//	}
//
//	public List<modelo_tipo_solicitud> obtenerTipoSolicitudes() {
//		return listaTipoSolicitud;
//	}
//
//	public List<modelo_relacion_campo_mantenimiento> obtenerCampos() {
//		return listaCampo;
//	}
//
//	public void obtenerId() throws ClassNotFoundException, FileNotFoundException, IOException {
//		dao_solicitud dao = new dao_solicitud();
//		try {
//			id = dao.obtenerNuevoId();
//			txtId.setText(String.valueOf(id));
//		} catch (SQLException e) {
//			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
//					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
//		}
//	}
//
//	public void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
//		listaOpcion = consultasABaseDeDatos.cargarOpciones("");
//		listaMantenimiento = consultasABaseDeDatos.cargarMantenimientos("", 1);
//		listaTipoSolicitud = consultasABaseDeDatos.cargarTipoDeSolicitudes(String.valueOf(tipo_solicitud),
//				validarTipoEnTipoSolicitud(tipo_solicitud));
//		listaCampo = consultasABaseDeDatos.cargarRelacionMantenimientosCampos("", String.valueOf(id_mantenimiento), 2);
//		binder.loadComponent(cmbTipoSolicitud);
//		binder.loadComponent(cmbCampo);
//	}
//
//	public int validarTipoEnTipoSolicitud(int tipo_solicitud) {
//		int tipo = 0;
//		if (tipo_solicitud == 7) { // Valido para opciones
//			tipo = 6;
//		} else if (tipo_solicitud == 1) { // Estado P
//			tipo = 2;
//		} else if (tipo_solicitud == 2) { // Estado A
//			tipo = 3;
//		} else if (tipo_solicitud == 3) { // Estado I
//			tipo = 2;
//		} else if (tipo_solicitud == 4) { // Estado A
//			tipo = 2;
//		}
//		return tipo;
//	}
//
//	public void inicializarSolicitud() throws ClassNotFoundException, FileNotFoundException, IOException {
//		long id_mantenimiento = 0;
//		String nom_mantenimiento = "";
//		long id_registro = 0;
//		String nom_registro = "";
//		if (tipo_solicitud == 7) { // Valido para opciones
//			if (id_opcion == 4) { // Gestion de cintas
//				movimiento_dn = (modelo_movimiento_dn) object;
//				id_mantenimiento = id_opcion;
//				nom_mantenimiento = setearNombreOpcion();
//				String tipo_pedido = "MOVIMIENTO";
//				if (movimiento_dn.getTip_pedido().equals("R")) {
//					tipo_pedido = "REQUERIMIENTO";
//				}
//				id_registro = movimiento_dn.getId_movimiento();
//				nom_registro = setearNombreRegistro(informativos.getMensaje_informativo_78().toUpperCase(), tipo_pedido,
//						movimiento_dn.getTck_movimiento(), nom_mantenimiento, "", "");
//			}
//		} else {
//			if (solicitar.this.id_mantenimiento == 1) { // Solicitantes
//				solicitante = (modelo_solicitante) object;
//				id_mantenimiento = solicitar.this.id_mantenimiento;
//				nom_mantenimiento = setearNombreMantenimiento();
//				id_registro = solicitante.getId_solicitante();
//				nom_registro = "";
//			}
//			if (solicitar.this.id_mantenimiento == 2) { // Ubicaciones - bodega
//				ubicacion = (modelo_ubicacion) object;
//				id_mantenimiento = solicitar.this.id_mantenimiento;
//				nom_mantenimiento = setearNombreMantenimiento();
//				id_registro = ubicacion.getId_ubicacion();
//				nom_registro = "";
//			}
//			if (solicitar.this.id_mantenimiento == 3) { // Localidades
//				localidad = (modelo_localidad) object;
//				id_mantenimiento = solicitar.this.id_mantenimiento;
//				nom_mantenimiento = setearNombreMantenimiento();
//				id_registro = localidad.getId_localidad();
//				nom_registro = "";
//			}
//			if (solicitar.this.id_mantenimiento == 4) { // Usuarios
//				usuario = (modelo_usuario_bk) object;
//				id_mantenimiento = solicitar.this.id_mantenimiento;
//				nom_mantenimiento = setearNombreMantenimiento();
//				id_registro = usuario.getId_usuario();
//				nom_registro = "";
//			}
//			if (solicitar.this.id_mantenimiento == 5) { // Categorias - bodega
//				categoria = (modelo_categoria) object;
//				id_mantenimiento = solicitar.this.id_mantenimiento;
//				nom_mantenimiento = setearNombreMantenimiento();
//				id_registro = categoria.getId_categoria();
//				nom_registro = "";
//			}
//			if (solicitar.this.id_mantenimiento == 6) { // Articulos - bodega
//				articulo = (modelo_articulo) object;
//				id_mantenimiento = solicitar.this.id_mantenimiento;
//				nom_mantenimiento = setearNombreMantenimiento();
//				id_registro = articulo.getId_articulo();
//				nom_registro = "";
//			}
//			if (solicitar.this.id_mantenimiento == 8) { // Empresas
//				empresa = (modelo_empresa) object;
//				id_mantenimiento = solicitar.this.id_mantenimiento;
//				nom_mantenimiento = setearNombreMantenimiento();
//				id_registro = empresa.getId_empresa();
//				nom_registro = "";
//			}
//			if (solicitar.this.id_mantenimiento == 12) { // Respaldos - cintas
//				respaldo = (modelo_respaldo) object;
//				id_mantenimiento = solicitar.this.id_mantenimiento;
//				nom_mantenimiento = setearNombreMantenimiento();
//				id_registro = respaldo.getId_respaldo();
//				nom_registro = "";
//			}
//			if (solicitar.this.id_mantenimiento == 13) { // Capacidades - cintas
//				capacidad = (modelo_capacidad) object;
//				id_mantenimiento = solicitar.this.id_mantenimiento;
//				nom_mantenimiento = setearNombreMantenimiento();
//				id_registro = capacidad.getId_capacidad();
//				nom_registro = "";
//			}
//			if (solicitar.this.id_mantenimiento == 14) { // Categorias - cintas
//				categoria_dn = (modelo_categoria_dn) object;
//				id_mantenimiento = solicitar.this.id_mantenimiento;
//				nom_mantenimiento = setearNombreMantenimiento();
//				id_registro = categoria_dn.getId_categoria();
//				nom_registro = "";
//			}
//			if (solicitar.this.id_mantenimiento == 15) { // Ubicaciones - cintas
//				ubicacion_dn = (modelo_ubicacion_dn) object;
//				id_mantenimiento = solicitar.this.id_mantenimiento;
//				nom_mantenimiento = setearNombreMantenimiento();
//				id_registro = ubicacion_dn.getId_ubicacion();
//				nom_registro = "";
//			}
//			if (solicitar.this.id_mantenimiento == 16) { // Articulos - cintas
//				articulo_dn = (modelo_articulo_dn) object;
//				id_mantenimiento = solicitar.this.id_mantenimiento;
//				nom_mantenimiento = setearNombreMantenimiento();
//				id_registro = articulo_dn.getId_articulo();
//				nom_registro = "";
//			}
//		}
//		solicitud.setId_mantenimiento(id_mantenimiento);
//		solicitud.setNom_mantenimiento(nom_mantenimiento);
//		solicitud.setId_registro(id_registro);
//		solicitud.setNom_registro(nom_registro);
//		txtConfiguracion.setText(nom_mantenimiento);
//		txtSolicitante.setText(nom_ape_user);
//	}
//
//	public void cargarFecha() {
//		Date fechaActual = new Date();
//		dtxFechaSolicitud.setValue(fechaActual);
//	}
//
//	public String setearNombreOpcion() throws ClassNotFoundException, FileNotFoundException, IOException {
//		String nombre_opcion = "";
//		Iterator<modelo_opcion> it = listaOpcion.iterator();
//		while (it.hasNext()) {
//			modelo_opcion opcion = it.next();
//			if (opcion.getId_opcion() == id_opcion) {
//				nombre_opcion = opcion.getNom_opcion();
//				break;
//			}
//		}
//		return nombre_opcion;
//	}
//
//	public String setearNombreMantenimiento() throws ClassNotFoundException, FileNotFoundException, IOException {
//		String nombre_mantenimiento = "";
//		Iterator<modelo_mantenimiento> it = listaMantenimiento.iterator();
//		while (it.hasNext()) {
//			modelo_mantenimiento mantenimiento = it.next();
//			if (mantenimiento.getId_mantenimiento() == id_mantenimiento) {
//				nombre_mantenimiento = mantenimiento.getNom_mantenimiento();
//				break;
//			}
//		}
//		return nombre_mantenimiento;
//	}
//
//	public String setearNombreRegistro(String parametro1, String parametro2, String parametro3, String parametro4,
//			String parametro5, String parametro6) {
//		String nombre_registro = "";
//		if (tipo_solicitud == 7) { // Valido para opciones
//			if (id_opcion == 4) { // Gestion de cintas
//				nombre_registro = parametro1.replace("?1", parametro2).replace("?2", parametro3)
//						.replace("?3", parametro4).replace("?4", parametro5).replace("?5", parametro6);
//			}
//		} else { // Valido para mantenimientos
//			nombre_registro = parametro1.replace("?1", parametro2).replace("?2", parametro3).replace("?3", parametro4)
//					.replace("?4", parametro5).replace("?5", parametro6);
//		}
//		return nombre_registro;
//	}
//
//	@Listen("onSelect=#cmbTipoSolicitud")
//	public void onSelect$cmbTipoSolicitud()
//			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
//		if (listaTipoSolicitud.size() == 0) {
//			return;
//		}
//		if (cmbTipoSolicitud.getSelectedItem() == null) {
//			return;
//		}
//		if (!cmbTipoSolicitud.getSelectedItem().getValue().toString().equals("2")) {
//			lCampo.setVisible(false);
//			iCampo.setVisible(false);
//			cmbCampo.setVisible(false);
//			cmbCampo.setText("");
//			if (tipo_solicitud == 7) { // Valido para opciones
//				if (id_opcion == 4) { // Gestion de cintas
//					txtComentario1.setText(solicitud.getNom_registro());
//				}
//			} else {
//				setearCampoComentario();
//			}
//			txtComentario1.setDisabled(false);
//		} else {
//			listaCampo = consultasABaseDeDatos.cargarRelacionMantenimientosCampos("", String.valueOf(id_mantenimiento),
//					2);
//			lCampo.setVisible(true);
//			iCampo.setVisible(true);
//			cmbCampo.setVisible(true);
//			cmbCampo.setText("");
//			txtComentario1.setText("");
//			txtComentario1.setDisabled(true);
//			binder.loadComponent(cmbCampo);
//		}
//	}
//
//	@Listen("onSelect=#cmbCampo")
//	public void onOK$cmbCampo() {
//		if (cmbCampo.getSelectedItem() == null) {
//			return;
//		}
//		txtComentario1.setText("");
//		txtComentario1.setDisabled(false);
//		if (tipo_solicitud == 7) { // Valido para opciones
//			if (id_opcion == 4) { // Gestion de cintas
//				txtComentario1.setText(solicitud.getNom_registro());
//			}
//		} else {
//			setearCampoComentario();
//		}
//	}
//
//	public void setearCampoComentario() {
//		int tipo = 1;
//		String id = "";
//		String codigo = "";
//		String descripcion = "";
//		if (id_mantenimiento == 1) { // Solicitantes
//			id = String.valueOf(solicitante.getId_solicitante());
//			codigo = solicitante.getNum_documento();
//			descripcion = solicitante.toStringSolicitante();
//			tipo = 3;
//		}
//		if (id_mantenimiento == 2) { // Ubicaciones - bodega
//			id = String.valueOf(ubicacion.getId_ubicacion());
//			codigo = ubicacion.getNom_ubicacion();
//			descripcion = ubicacion.getPos_ubicacion();
//			tipo = 4;
//		}
//		if (id_mantenimiento == 3) { // Localidades
//			id = String.valueOf(localidad.getId_localidad());
//			codigo = localidad.getNom_localidad();
//			descripcion = localidad.getDes_localidad();
//			tipo = 1;
//		}
//		if (id_mantenimiento == 4) { // Usuarios
//			id = String.valueOf(usuario.getId_usuario());
//			codigo = usuario.getUse_usuario().toUpperCase();
//			descripcion = usuario.verNombreCompleto();
//			tipo = 2;
//		}
//		if (id_mantenimiento == 5) { // Categorias - bodega
//			id = String.valueOf(categoria.getId_categoria());
//			codigo = categoria.getNom_categoria();
//			descripcion = categoria.getDes_categoria();
//			tipo = 4;
//		}
//		if (id_mantenimiento == 6) { // Articulos - bodega
//			id = String.valueOf(articulo.getId_articulo());
//			codigo = articulo.getCod_articulo();
//			descripcion = articulo.getDes_articulo();
//			tipo = 1;
//		}
//		if (id_mantenimiento == 8) { // Empresas
//			id = String.valueOf(empresa.getId_empresa());
//			codigo = empresa.getNom_empresa();
//			descripcion = empresa.getDes_empresa();
//			tipo = 1;
//		}
//		if (id_mantenimiento == 12) { // Respaldos - cintas
//			id = String.valueOf(respaldo.getId_respaldo());
//			codigo = respaldo.getNom_tip_respaldo();
//			descripcion = respaldo.getDia_respaldo();
//			tipo = 5;
//		}
//		if (id_mantenimiento == 13) { // Capacidades - cintas
//			id = String.valueOf(capacidad.getId_capacidad());
//			codigo = capacidad.getNom_capacidad();
//			descripcion = capacidad.getDes_capacidad();
//			tipo = 1;
//		}
//		if (id_mantenimiento == 14) { // Categorias - bodega
//			id = String.valueOf(categoria_dn.getId_categoria());
//			codigo = categoria_dn.getNom_categoria();
//			descripcion = categoria_dn.getDes_categoria();
//			tipo = 4;
//		}
//		if (id_mantenimiento == 15) { // Ubicaciones - cintas
//			id = String.valueOf(ubicacion_dn.getId_ubicacion());
//			codigo = ubicacion_dn.getNom_ubicacion();
//			descripcion = ubicacion_dn.getPos_ubicacion();
//			tipo = 4;
//		}
//		if (id_mantenimiento == 16) { // Articulos - cintas
//			id = String.valueOf(articulo_dn.getId_articulo());
//			codigo = articulo_dn.getCod_articulo();
//			descripcion = articulo_dn.getDes_articulo();
//			tipo = 1;
//		}
//		if (cmbTipoSolicitud.getSelectedItem().getValue().toString().equals("1")) {
//			if (tipo == 1) {
//				solicitud.setNom_registro(setearNombreRegistro(informativos.getMensaje_informativo_79().toUpperCase(),
//						id, " CODIGO " + codigo + ", DESCRIPCION " + descripcion, solicitud.getNom_mantenimiento(), "",
//						""));
//			}
//			if (tipo == 2) {
//				solicitud.setNom_registro(setearNombreRegistro(informativos.getMensaje_informativo_79().toUpperCase(),
//						id, " USUARIO " + codigo + ", NOMBRES " + descripcion, solicitud.getNom_mantenimiento(), "",
//						""));
//			}
//			if (tipo == 3) {
//				solicitud.setNom_registro(setearNombreRegistro(informativos.getMensaje_informativo_79().toUpperCase(),
//						id, " DOCUMENTO " + codigo + ", NOMBRES " + descripcion, solicitud.getNom_mantenimiento(), "",
//						""));
//			}
//			if (tipo == 4) {
//				solicitud.setNom_registro(setearNombreRegistro(informativos.getMensaje_informativo_79().toUpperCase(),
//						id, " NOMBRE " + codigo + ", DESCRIPCION " + descripcion, solicitud.getNom_mantenimiento(), "",
//						""));
//			}
//			if (tipo == 5) {
//				solicitud.setNom_registro(setearNombreRegistro(informativos.getMensaje_informativo_79().toUpperCase(),
//						id, " TIPO DE RESPALDO " + codigo + ", DESCRIPCION " + descripcion,
//						solicitud.getNom_mantenimiento(), "", ""));
//			}
//		}
//		if (cmbTipoSolicitud.getSelectedItem().getValue().toString().equals("2")) {
//			if (tipo == 1) {
//				solicitud.setNom_registro(setearNombreRegistro(informativos.getMensaje_informativo_80().toUpperCase(),
//						cmbCampo.getSelectedItem().getLabel().toString(), id,
//						" CODIGO " + codigo + ", DESCRIPCION " + descripcion, "", solicitud.getNom_mantenimiento()));
//			}
//			if (tipo == 2) {
//				solicitud.setNom_registro(setearNombreRegistro(informativos.getMensaje_informativo_80().toUpperCase(),
//						cmbCampo.getSelectedItem().getLabel().toString(), id,
//						" USUARIO " + codigo + ", NOMBRES " + descripcion, "", solicitud.getNom_mantenimiento()));
//			}
//			if (tipo == 3) {
//				solicitud.setNom_registro(setearNombreRegistro(informativos.getMensaje_informativo_80().toUpperCase(),
//						cmbCampo.getSelectedItem().getLabel().toString(), id,
//						" DOCUMENTO " + codigo + ", NOMBRES " + descripcion, "", solicitud.getNom_mantenimiento()));
//			}
//			if (tipo == 4) {
//				solicitud.setNom_registro(setearNombreRegistro(informativos.getMensaje_informativo_80().toUpperCase(),
//						cmbCampo.getSelectedItem().getLabel().toString(), id,
//						" NOMBRE " + codigo + ", DESCRIPCION " + descripcion, "", solicitud.getNom_mantenimiento()));
//			}
//			if (tipo == 5) {
//				solicitud.setNom_registro(setearNombreRegistro(informativos.getMensaje_informativo_80().toUpperCase(),
//						cmbCampo.getSelectedItem().getLabel().toString(), id,
//						" TIPO DE RESPALDO " + codigo + ", DESCRIPCION " + descripcion, "",
//						solicitud.getNom_mantenimiento()));
//			}
//		}
//		if (cmbTipoSolicitud.getSelectedItem().getValue().toString().equals("3")) {
//			if (tipo == 1) {
//				solicitud.setNom_registro(setearNombreRegistro(informativos.getMensaje_informativo_82().toUpperCase(),
//						id, " CODIGO " + codigo + ", DESCRIPCION " + descripcion, solicitud.getNom_mantenimiento(), "",
//						""));
//			}
//			if (tipo == 2) {
//				solicitud.setNom_registro(setearNombreRegistro(informativos.getMensaje_informativo_82().toUpperCase(),
//						id, " USUARIO " + codigo + ", NOMBRES " + descripcion, solicitud.getNom_mantenimiento(), "",
//						""));
//			}
//			if (tipo == 3) {
//				solicitud.setNom_registro(setearNombreRegistro(informativos.getMensaje_informativo_82().toUpperCase(),
//						id, " DOCUMENTO " + codigo + ", NOMBRES " + descripcion, solicitud.getNom_mantenimiento(), "",
//						""));
//			}
//			if (tipo == 4) {
//				solicitud.setNom_registro(setearNombreRegistro(informativos.getMensaje_informativo_82().toUpperCase(),
//						id, " NOMBRE " + codigo + ", DESCRIPCION " + descripcion, solicitud.getNom_mantenimiento(), "",
//						""));
//			}
//			if (tipo == 5) {
//				solicitud.setNom_registro(setearNombreRegistro(informativos.getMensaje_informativo_82().toUpperCase(),
//						id, " TIPO DE RESPALDO " + codigo + ", DESCRIPCION " + descripcion,
//						solicitud.getNom_mantenimiento(), "", ""));
//			}
//		}
//		if (cmbTipoSolicitud.getSelectedItem().getValue().toString().equals("4")) {
//			if (tipo == 1) {
//				solicitud.setNom_registro(setearNombreRegistro(informativos.getMensaje_informativo_81().toUpperCase(),
//						id, " CODIGO " + codigo + ", DESCRIPCION " + descripcion, solicitud.getNom_mantenimiento(), "",
//						""));
//			}
//			if (tipo == 2) {
//				solicitud.setNom_registro(setearNombreRegistro(informativos.getMensaje_informativo_81().toUpperCase(),
//						id, " USUARIO " + codigo + ", NOMBRES " + descripcion, solicitud.getNom_mantenimiento(), "",
//						""));
//			}
//			if (tipo == 3) {
//				solicitud.setNom_registro(setearNombreRegistro(informativos.getMensaje_informativo_81().toUpperCase(),
//						id, " DOCUMENTO " + codigo + ", NOMBRES " + descripcion, solicitud.getNom_mantenimiento(), "",
//						""));
//			}
//			if (tipo == 4) {
//				solicitud.setNom_registro(setearNombreRegistro(informativos.getMensaje_informativo_81().toUpperCase(),
//						id, " NOMBRE " + codigo + ", DESCRIPCION " + descripcion, solicitud.getNom_mantenimiento(), "",
//						""));
//			}
//			if (tipo == 5) {
//				solicitud.setNom_registro(setearNombreRegistro(informativos.getMensaje_informativo_81().toUpperCase(),
//						id, " TIPO DE RESPALDO " + codigo + ", DESCRIPCION " + descripcion,
//						solicitud.getNom_mantenimiento(), "", ""));
//			}
//		}
//		txtComentario1.setText(solicitud.getNom_registro());
//	}
//
//	public boolean validarSiExisteSolicitudCreada()
//			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
//		boolean existe_solicitud_creada = false;
//		modelo_solicitud solicitud = new modelo_solicitud();
//		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", solicitar.this.solicitud.getId_mantenimiento(),
//				solicitar.this.solicitud.getId_registro(), 2);
//		if (solicitud != null) {
//			String estado = solicitud.getEst_solicitud();
//			if (estado != null) {
//				if (estado.equals("P") || estado.equals("R") || estado.equals("S") || estado.equals("T")) {
//					existe_solicitud_creada = true;
//				}
//			}
//		}
//		return existe_solicitud_creada;
//	}
//
//	@SuppressWarnings({ "rawtypes", "unchecked" })
//	@Listen("onClick=#btnGrabar")
//	public void onClick$btnGrabar()
//			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
//		if (validarSiExisteSolicitudCreada() == true) {
//			Messagebox.show(informativos.getMensaje_informativo_83(), informativos.getMensaje_informativo_47(),
//					Messagebox.OK, Messagebox.EXCLAMATION);
//			return;
//		}
//		if (cmbTipoSolicitud.getSelectedItem() == null) {
//			cmbTipoSolicitud.setErrorMessage(validaciones.getMensaje_validacion_32());
//			return;
//		}
//		if (cmbTipoSolicitud.getSelectedItem().getValue().toString().equals("2")) {
//			if (cmbCampo.getSelectedItem() == null) {
//				cmbCampo.setErrorMessage(validaciones.getMensaje_validacion_33());
//				return;
//			}
//		}
//		if (txtComentario1.getText().length() <= 0) {
//			txtComentario1.setErrorMessage(validaciones.getMensaje_validacion_11());
//			return;
//		}
//		if (dtxFechaSolicitud.getValue() == null) {
//			dtxFechaSolicitud.setErrorMessage(validaciones.getMensaje_validacion_4());
//			return;
//		}
//		Messagebox.show(informativos.getMensaje_informativo_16(), informativos.getMensaje_informativo_15(),
//				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
//					@Override
//					public void onEvent(Event event) throws Exception {
//						if (event.getName().equals("onOK")) {
//							dao_solicitud dao = new dao_solicitud();
//							modelo_solicitud solicitud = new modelo_solicitud();
//							solicitud.setId_tip_solicitud(
//									Long.valueOf(cmbTipoSolicitud.getSelectedItem().getValue().toString()));
//							solicitud.setNom_tipo_solicitud(cmbTipoSolicitud.getSelectedItem().getLabel().toString());
//							solicitud.setNom_mantenimiento(solicitar.this.solicitud.getNom_mantenimiento());
//							solicitud.setId_mantenimiento(solicitar.this.solicitud.getId_mantenimiento());
//							solicitud.setId_registro(solicitar.this.solicitud.getId_registro());
//							if (cmbTipoSolicitud.getSelectedItem().getValue().toString().equals("2")) {
//								solicitud.setId_campo(Long.valueOf(cmbCampo.getSelectedItem().getValue().toString()));
//								solicitud.setNom_campo(cmbCampo.getSelectedItem().getLabel().toString());
//							} else {
//								solicitud.setNom_campo("N/A");
//							}
//							solicitud.setComentario_1(txtComentario1.getText());
//							solicitud.setComentario_2("");
//							solicitud.setComentario_3("");
//							solicitud.setComentario_4("");
//							solicitud.setComentario_5("");
//							solicitud.setId_user_1(id_user);
//							solicitud.setEst_solicitud("P");
//							solicitud.setUsu_ingresa(user);
//							solicitud.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
//							solicitud.setFecha_1(fechas.obtenerTimestampDeDate(new Date()));
//							String est_mantenimiento = solicitar.this.solicitud.getEst_solicitud();
//							long id_registro = solicitar.this.solicitud.getId_registro();
//							try {
//								dao.insertarSolicitud(solicitud, id_mantenimiento, id_registro, est_mantenimiento);
//								Messagebox.show(informativos.getMensaje_informativo_20(),
//										informativos.getMensaje_informativo_15(), Messagebox.OK,
//										Messagebox.EXCLAMATION);
//								Events.postEvent(new Event("onClose", zSolicitar));
//							} catch (Exception e) {
//								Messagebox.show(error.getMensaje_error_4() + ", " + e.getLocalizedMessage(),
//										informativos.getMensaje_informativo_15(), Messagebox.OK,
//										Messagebox.EXCLAMATION);
//							}
//						}
//					}
//				});
//	}
//
//	@Listen("onClick=#btnCancelar")
//	public void onClick$btnCancelar() throws ClassNotFoundException, FileNotFoundException, IOException {
//		Events.postEvent(new Event("onClose", zSolicitar));
//	}
//
//}
