package bp.aplicaciones.controlador.mantenimientos.ubicacion_dn;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_empresa;
import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_ubicacion;
import bp.aplicaciones.mantenimientos.DAO.dao_ubicacion_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_ubicacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_ubicacion_dn;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;

@SuppressWarnings({ "serial", "deprecation" })
public class modificar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zModificar;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Textbox txtId, txtPosicion, txtComentario;
	@Wire
	Combobox cmbLocalidad, cmbEmpresa, cmbUbicacion;
	@Wire
	Checkbox chkCapacidad;
	@Wire
	Intbox intCapacidad;

	String comentario_1 = "";
	String comentario_2 = "";

	Date fecha_comentario_1 = null;
	Date fecha_comentario_2 = null;
	Date fecha_comentario_3 = null;

	List<modelo_localidad> listaLocalidad = new ArrayList<modelo_localidad>();
	List<modelo_empresa> listaEmpresa = new ArrayList<modelo_empresa>();
	List<modelo_tipo_ubicacion> listaUbicacion = new ArrayList<modelo_tipo_ubicacion>();

	long id = 0;
	long id_mantenimiento = 15;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	modelo_ubicacion_dn ubicacion = (modelo_ubicacion_dn) Sessions.getCurrent().getAttribute("ubicacion");
	String cod_sesion = (String) Sessions.getCurrent().getAttribute("cod_sesion");

	validar_datos validar = new validar_datos();

	modelo_solicitud solicitud = new modelo_solicitud();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();
	Validaciones validaciones = new Validaciones();

	Informativos informativos = new Informativos();
	Error error = new Error();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		txtPosicion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtPosicion.setText(txtPosicion.getText().toUpperCase());
			}
		});
		txtComentario.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtComentario.setText(txtComentario.getText().toUpperCase());
			}
		});
		cargarLocalidades();
		cargarEmpresas();
		cargarUbicaciones();
		cargarDatos();
		// validarSesion();
	}

	public void cargarDatos() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		txtId.setText(String.valueOf(ubicacion.getId_ubicacion()));
		txtPosicion.setText(ubicacion.getPos_ubicacion());
		if (ubicacion.getVal_capacidad().equals("S")) {
			chkCapacidad.setChecked(true);
			intCapacidad.setDisabled(false);
		} else {
			chkCapacidad.setChecked(false);
			intCapacidad.setDisabled(true);
		}
		intCapacidad.setValue(ubicacion.getCap_ubicacion());
		for (int i = 0; i < listaLocalidad.size(); i++) {
			if (listaLocalidad.get(i).getId_localidad() == ubicacion.getId_localidad()) {
				cmbLocalidad.setText(listaLocalidad.get(i).getNom_localidad());
				i = listaLocalidad.size() + 1;
			}
		}
		for (int i = 0; i < listaEmpresa.size(); i++) {
			if (listaEmpresa.get(i).getId_empresa() == ubicacion.getId_empresa()) {
				cmbEmpresa.setText(listaEmpresa.get(i).getNom_empresa());
				i = listaEmpresa.size() + 1;
			}
		}
		for (int i = 0; i < listaUbicacion.size(); i++) {
			if (listaUbicacion.get(i).getId_tipo_ubicacion() == ubicacion.getId_tip_ubicacion()) {
				cmbUbicacion.setText(listaUbicacion.get(i).getNom_tipo_ubicacion());
				i = listaUbicacion.size() + 1;
			}
		}
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, ubicacion.getId_ubicacion(),
				7);
		if (solicitud != null) {
			if (solicitud.getId_tip_solicitud() == 1 || solicitud.getId_tip_solicitud() == 2) {
				if (solicitud.getEst_solicitud().equals("T")) {
					cmbEmpresa.setDisabled(false);
					txtPosicion.setDisabled(false);
				} else {
					cmbUbicacion.setDisabled(true);
					cmbLocalidad.setDisabled(true);
					if (solicitud.getId_campo() == 7) {
						cmbEmpresa.setDisabled(false);
					} else {
						cmbEmpresa.setDisabled(true);
					}
					if (solicitud.getId_campo() == 13) {
						txtPosicion.setDisabled(false);
					} else {
						txtPosicion.setDisabled(true);
					}
					if (solicitud.getId_campo() == 35) {
						chkCapacidad.setDisabled(false);
					} else {
						chkCapacidad.setDisabled(true);
					}
					if (solicitud.getId_campo() == 36) {
						intCapacidad.setDisabled(false);
					} else {
						intCapacidad.setDisabled(true);
					}
					if (solicitud.getId_campo() == 37) {
						chkCapacidad.setDisabled(false);
						if (ubicacion.getVal_capacidad().equals("S")) {
							intCapacidad.setDisabled(false);
						} else {
							intCapacidad.setDisabled(true);
						}
					} else {
						chkCapacidad.setDisabled(true);
						intCapacidad.setDisabled(true);
					}
				}
			}
		}
	}

	public List<modelo_localidad> obtenerLocalidades() {
		return listaLocalidad;
	}

	public List<modelo_empresa> obtenerEmpresas() {
		return listaEmpresa;
	}

	public List<modelo_tipo_ubicacion> obtenerUbicaciones() {
		return listaUbicacion;
	}

	public void cargarLocalidades() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_localidad dao = new dao_localidad();
		String criterio = "";
		try {
			listaLocalidad = dao.obtenerLocalidades(criterio, 4, 0, 0);
			binder.loadComponent(cmbLocalidad);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las localidades. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar localidad ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
		for (int i = 0; i < listaLocalidad.size(); i++) {
			if (listaLocalidad.get(i).getId_localidad() == id_dc) {
				cmbLocalidad.setText(listaLocalidad.get(i).getNom_localidad());
				i = listaLocalidad.size() + 1;
			}
		}
	}

	public void cargarEmpresas() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_empresa dao = new dao_empresa();
		String criterio = "";
		try {
			listaEmpresa = dao.obtenerEmpresas(criterio, 1, String.valueOf(id_dc), "2", 0);
			binder.loadComponent(cmbEmpresa);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las empresas. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar empresa ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarUbicaciones() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_ubicacion dao = new dao_tipo_ubicacion();
		String criterio = "";
		try {
			listaUbicacion = dao.obtenerTipoUbicaciones(criterio);
			binder.loadComponent(cmbUbicacion);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las ubicacions. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar ubicacion ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbUbicacion")
	public void onSelect$cmbUbicacion()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (cmbUbicacion.getSelectedItem() == null) {
			return;
		}
	}

	@Listen("onBlur=#txtPosicion")
	public void onBlur$txtPosicion()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (cmbEmpresa.getSelectedItem() == null) {
			return;
		}
		if (cmbUbicacion.getSelectedItem() == null) {
			return;
		}
		if (txtPosicion.getText().length() <= 0) {
			return;
		}
		dao_ubicacion_dn dao = new dao_ubicacion_dn();
		if (dao.obtenerUbicaciones(txtPosicion.getText(), String.valueOf(id_dc), 3,
				Long.parseLong(cmbEmpresa.getSelectedItem().getValue().toString()),
				Long.parseLong(cmbUbicacion.getSelectedItem().getValue().toString()), ubicacion.getId_ubicacion(), 0)
				.size() > 0) {
			txtPosicion.setErrorMessage(
					"La posición ya se encuentra registrada para la empresa y ubicación seleccionada.");
			return;
		}
	}

	@Listen("onCheck=#chkCapacidad")
	public void onCheck$chkCapacidad() {
		if (chkCapacidad.isChecked()) {
			intCapacidad.setValue(0);
			intCapacidad.setDisabled(false);
		} else {
			intCapacidad.setValue(0);
			intCapacidad.setDisabled(true);
		}
	}

	public boolean validarSiExisteSolicitudPendienteEjecucion()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_solicitud_pendiente = false;
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, ubicacion.getId_ubicacion(),
				7);
		if (solicitud != null) {
			String estado = solicitud.getEst_solicitud();
			if (estado != null) {
				if (estado.equals("S")) {
					existe_solicitud_pendiente = true;
				}
			}
		}
		return existe_solicitud_pendiente;
	}

	public boolean validarSiExisteSolicitudPendienteActualizacion()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_solicitud_pendiente = false;
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, ubicacion.getId_ubicacion(),
				7);
		if (solicitud != null) {
			String estado = solicitud.getEst_solicitud();
			if (estado != null) {
				if (estado.equals("T")) {
					existe_solicitud_pendiente = true;
				}
			}
		}
		return existe_solicitud_pendiente;
	}

	public void setearDatosAntesDeGuardar()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, ubicacion.getId_ubicacion(),
				7);
		if (solicitud != null) {
			comentario_1 = solicitud.getComentario_1();
			comentario_2 = solicitud.getComentario_4();
			fecha_comentario_1 = solicitud.getFecha_1();
			fecha_comentario_2 = solicitud.getFecha_2();
			fecha_comentario_3 = solicitud.getFecha_4();
		}
	}

	public String setearComentario() {
		String comentario = "";
		comentario = "EN LA FECHA " + fechas.obtenerFechaFormateada(fecha_comentario_1, "dd/MM/yyyy HH:mm") + " "
				+ comentario_1 + "\n" + "EN LA FECHA "
				+ fechas.obtenerFechaFormateada(fecha_comentario_2, "dd/MM/yyyy HH:mm") + " EL APROBADOR INDICA QUE "
				+ comentario_2 + "\n" + "EN LA FECHA "
				+ fechas.obtenerFechaFormateada(fecha_comentario_3, "dd/MM/yyyy HH:mm")
				+ " SE REALIZA EL CAMBIO SOLICITADO Y SE REGISTRA COMO COMENTARIO ";
		return comentario;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (validarSiExisteSolicitudPendienteEjecucion() == false
				&& validarSiExisteSolicitudPendienteActualizacion() == false) {
			Messagebox.show(informativos.getMensaje_informativo_84(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (solicitud != null) {
			if (solicitud.getId_tip_solicitud() == 2) {
				if (solicitud.getEst_solicitud().equals("T")) {
					if (cmbEmpresa.getSelectedItem() == null) {
						cmbEmpresa.setErrorMessage("Seleccione una empresa.");
						return;
					}
					if (cmbUbicacion.getSelectedItem() == null) {
						cmbUbicacion.setErrorMessage("Seleccione una ubicacion.");
						return;
					}
					if (txtPosicion.getText().length() <= 0) {
						txtPosicion.setErrorMessage("Ingrese la ubicacion.");
						return;
					}
					if (chkCapacidad.isChecked()) {
						if (intCapacidad.getValue() <= 0) {
							intCapacidad.setErrorMessage("Ingrese la capacidad");
							intCapacidad.setFocus(true);
						}
					}
					dao_ubicacion_dn dao = new dao_ubicacion_dn();
					if (dao.obtenerUbicaciones(txtPosicion.getText(), String.valueOf(id_dc), 3,
							Long.parseLong(cmbEmpresa.getSelectedItem().getValue().toString()),
							Long.parseLong(cmbUbicacion.getSelectedItem().getValue().toString()),
							ubicacion.getId_ubicacion(), 0).size() > 0) {
						txtPosicion.setErrorMessage(
								"La posición ya se encuentra registrada para la empresa y ubicación seleccionada.");
						return;
					}
					if (cmbLocalidad.getSelectedItem() == null) {
						cmbLocalidad.setErrorMessage("Seleccione una localidad.");
						return;
					}
				} else {
					if (solicitud.getId_campo() == 7) {
						if (cmbEmpresa.getSelectedItem() == null) {
							cmbEmpresa.setErrorMessage("Seleccione una empresa.");
							return;
						}
					}
					if (solicitud.getId_campo() == 13) {
						if (txtPosicion.getText().length() <= 0) {
							txtPosicion.setErrorMessage("Ingrese la ubicacion.");
							return;
						}
						dao_ubicacion_dn dao = new dao_ubicacion_dn();
						if (dao.obtenerUbicaciones(txtPosicion.getText(), String.valueOf(id_dc), 3,
								Long.parseLong(cmbEmpresa.getSelectedItem().getValue().toString()),
								Long.parseLong(cmbUbicacion.getSelectedItem().getValue().toString()),
								ubicacion.getId_ubicacion(), 0).size() > 0) {
							txtPosicion.setErrorMessage(
									"La posición ya se encuentra registrada para la empresa y ubicación seleccionada.");
							return;
						}
					}
					if (solicitud.getId_campo() == 35) {
						if (chkCapacidad.isChecked()) {
							if (intCapacidad.getValue() <= 0) {
								intCapacidad.setErrorMessage("Ingrese la capacidad");
								intCapacidad.setFocus(true);
							}
						}
					}
					if (solicitud.getId_campo() == 36) {
						if (chkCapacidad.isChecked()) {
							if (intCapacidad.getValue() <= 0) {
								intCapacidad.setErrorMessage("Ingrese la capacidad");
								intCapacidad.setFocus(true);
							}
						}
					}
					if (solicitud.getId_campo() == 37) {
						if (chkCapacidad.isChecked()) {
							if (intCapacidad.getValue() <= 0) {
								intCapacidad.setErrorMessage("Ingrese la capacidad");
								intCapacidad.setFocus(true);
							}
						}
					}
				}
			}
		}
		if (txtComentario.getText().length() <= 0) {
			txtComentario.setErrorMessage("Ingrese un comentario.");
			return;
		}
		setearDatosAntesDeGuardar();
		Messagebox.show("Esta seguro de modificar la ubicacion?", ".:: Modificar ubicacion ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_ubicacion_dn dao = new dao_ubicacion_dn();
							modelo_ubicacion_dn ubicacion = new modelo_ubicacion_dn();
							ubicacion.setId_ubicacion(Long.parseLong(txtId.getText()));
							ubicacion.setId_tip_ubicacion(
									Long.parseLong(cmbUbicacion.getSelectedItem().getValue().toString()));
							ubicacion.setId_empresa(Long.parseLong(cmbEmpresa.getSelectedItem().getValue().toString()));
							ubicacion.setPos_ubicacion(txtPosicion.getText());
							if (chkCapacidad.isChecked()) {
								ubicacion.setVal_capacidad("S");
								ubicacion.setCap_ubicacion(intCapacidad.getValue());
							} else {
								ubicacion.setVal_capacidad("N");
								ubicacion.setCap_ubicacion(0);
							}
							ubicacion.setId_localidad(
									Long.parseLong(cmbLocalidad.getSelectedItem().getValue().toString()));
							ubicacion.setUsu_modifica(user);
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							ubicacion.setFec_modifica(timestamp);
							if (solicitud.getEst_solicitud().equals("T")) {
								ubicacion.setEst_ubicacion("PACP");
								solicitud.setEst_solicitud("R");
								solicitud.setComentario_1(setearComentario() + txtComentario.getText().toUpperCase());
								solicitud.setComentario_2("");
								solicitud.setComentario_3("");
								solicitud.setComentario_4("");
								solicitud.setComentario_5("");
								solicitud.setId_user_1(id_user);
								solicitud.setId_user_2(0);
								solicitud.setId_user_3(0);
								solicitud.setId_user_4(0);
								solicitud.setId_user_5(0);
								solicitud.setFecha_1(fechas.obtenerTimestampDeDate(fecha_comentario_1));
								solicitud.setFecha_2(null);
								solicitud.setFecha_3(null);
								solicitud.setFecha_4(null);
								solicitud.setFecha_5(null);
								solicitud.setUsu_modifica(user);
								solicitud.setFec_modifica(timestamp);
							} else {
								ubicacion.setEst_ubicacion("AE");
								solicitud.setEst_solicitud("E");
								solicitud.setComentario_3(txtComentario.getText());
								solicitud.setId_user_3(id_user);
								solicitud.setFecha_3(timestamp);
								solicitud.setUsu_modifica(user);
								solicitud.setFec_modifica(timestamp);
							}
							int tipo = 1;
							try {
								dao.modificarUbicacion(ubicacion, solicitud, tipo);
								if (tipo == 1) {
									Messagebox.show("La categoria se modificó correctamente.",
											".:: Modificar categoria ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								} else {
									Messagebox.show("No se realizaron cambios en el registro.",
											".:: Modificar categoria ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								}
								limpiarCampos();
								Sessions.getCurrent().removeAttribute("ubicacion");
								Events.postEvent(new Event("onClose", zModificar));
							} catch (Exception e) {
								Messagebox.show(
										"Error al modificar la ubicacion. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Modificar ubicacion ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() throws ClassNotFoundException, FileNotFoundException, IOException {
		Sessions.getCurrent().removeAttribute("ubicacion");
		// validarSesion();
		Events.postEvent(new Event("onClose", zModificar));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtPosicion.setText("");
		cmbLocalidad.setText("");
		cmbUbicacion.setText("");
		cmbEmpresa.setText("");
	}

}
