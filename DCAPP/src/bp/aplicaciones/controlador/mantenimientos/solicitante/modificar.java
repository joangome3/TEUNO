package bp.aplicaciones.controlador.mantenimientos.solicitante;

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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_empresa;
import bp.aplicaciones.mantenimientos.DAO.dao_solicitante;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_documento;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitante;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_documento;
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
	Textbox txtId, txtDocumento, txtNombres, txtApellidos, txtComentario;
	@Wire
	Combobox cmbEmpresa, cmbTipoDocumento;

	String comentario_1 = "";
	String comentario_2 = "";

	Date fecha_comentario_1 = null;
	Date fecha_comentario_2 = null;
	Date fecha_comentario_3 = null;

	List<modelo_empresa> listaEmpresa = new ArrayList<modelo_empresa>();
	List<modelo_tipo_documento> listaTipoDocumento = new ArrayList<modelo_tipo_documento>();

	long id = 0;
	long id_mantenimiento = 1;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	modelo_solicitante solicitante = (modelo_solicitante) Sessions.getCurrent().getAttribute("solicitante");

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
		txtDocumento.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtDocumento.setText(validar.soloNumeros(txtDocumento.getText()));
			}
		});
		txtNombres.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtNombres.setText(validar.soloLetrasyNumeros(txtNombres.getText()));
			}
		});
		txtApellidos.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtApellidos.setText(validar.soloLetrasyNumeros(txtApellidos.getText()));
			}
		});
		txtComentario.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtComentario.setText(txtComentario.getText().toUpperCase());
			}
		});
		cargarEmpresas();
		cargarTipoDocumentos();
		cargarDatos();
		validarCamposActualizar();
	}

	public void cargarDatos() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		txtId.setText(String.valueOf(solicitante.getId_solicitante()));
		txtDocumento.setText(solicitante.getNum_documento());
		txtNombres.setText(solicitante.getNom_solicitante());
		txtApellidos.setText(solicitante.getApe_solicitante());
		for (int i = 0; i < listaEmpresa.size(); i++) {
			if (listaEmpresa.get(i).getId_empresa() == solicitante.getId_empresa()) {
				cmbEmpresa.setText(listaEmpresa.get(i).getNom_empresa());
				i = listaEmpresa.size() + 1;
			}
		}
		for (int i = 0; i < listaTipoDocumento.size(); i++) {
			if (listaTipoDocumento.get(i).getId_tipo_documento() == solicitante.getId_tip_documento()) {
				cmbTipoDocumento.setText(listaTipoDocumento.get(i).getNom_tipo_documento());
				i = listaTipoDocumento.size() + 1;
			}
		}
	}

	public void validarCamposActualizar()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		cmbTipoDocumento.setDisabled(true);
		txtDocumento.setDisabled(true);
		txtNombres.setDisabled(true);
		txtApellidos.setDisabled(true);
		cmbEmpresa.setDisabled(true);
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento,
				solicitante.getId_solicitante(), 7);
		if (solicitud != null) {
			if (solicitud.getId_tip_solicitud() == 1 || solicitud.getId_tip_solicitud() == 2) {
				if (solicitud.getEst_solicitud().equals("T")) {
					cmbTipoDocumento.setDisabled(false);
					txtDocumento.setDisabled(false);
					txtNombres.setDisabled(false);
					txtApellidos.setDisabled(false);
					cmbEmpresa.setDisabled(false);
				} else {
					if (solicitud.getId_campo() == 8) {
						cmbTipoDocumento.setDisabled(false);
						txtDocumento.setDisabled(false);
					}
					if (solicitud.getId_campo() == 3) {
						cmbTipoDocumento.setDisabled(false);
					}
					if (solicitud.getId_campo() == 4) {
						txtDocumento.setDisabled(false);
					}
					if (solicitud.getId_campo() == 5) {
						txtNombres.setDisabled(false);
					}
					if (solicitud.getId_campo() == 6) {
						txtApellidos.setDisabled(false);
					}
					if (solicitud.getId_campo() == 7) {
						cmbEmpresa.setDisabled(false);
					}
				}
			}
		}
	}

	public List<modelo_empresa> obtenerEmpresas() {
		return listaEmpresa;
	}

	public List<modelo_tipo_documento> obtenerTipoDocumentos() {
		return listaTipoDocumento;
	}

	public void cargarEmpresas() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_empresa dao = new dao_empresa();
		String criterio = "";
		try {
			listaEmpresa = dao.obtenerEmpresas(criterio, 1, String.valueOf(id_dc), "1", 0);
			binder.loadComponent(cmbEmpresa);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las empresas. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar empresa ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarTipoDocumentos() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_documento dao = new dao_tipo_documento();
		String criterio = "";
		try {
			listaTipoDocumento = dao.obtenerTipoDocumentos(criterio);
			binder.loadComponent(cmbTipoDocumento);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los tipo de documentos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cagar tipo de documento ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@SuppressWarnings("static-access")
	@Listen("onBlur=#txtDocumento")
	public void onBlur$txtDocumento()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (cmbTipoDocumento.getSelectedItem() == null) {
			return;
		}
		if (txtDocumento.getText().length() <= 0) {
			return;
		}
		if (cmbTipoDocumento.getSelectedItem().getValue().toString().equals("2")) {
			if (validar.validarCedula(txtDocumento.getText()) == 0) {
				txtDocumento.setErrorMessage("El numero de documento es incorrecto.");
				return;
			}
		}
		dao_solicitante dao = new dao_solicitante();
		if (dao.obtenerSolicitantes(txtDocumento.getText(), 4, "", String.valueOf(solicitante.getId_solicitante()), 0)
				.size() > 0) {
			txtDocumento.setErrorMessage("El numero de documento ya se encuentra registrado.");
			return;
		}
	}

	public boolean validarSiExisteSolicitudPendienteEjecucion()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_solicitud_pendiente = false;
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento,
				solicitante.getId_solicitante(), 7);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento,
				solicitante.getId_solicitante(), 7);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento,
				solicitante.getId_solicitante(), 7);
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

	@SuppressWarnings({ "rawtypes", "unchecked", "static-access" })
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
					if (cmbTipoDocumento.getSelectedItem() == null) {
						cmbTipoDocumento.setErrorMessage("Seleccione un tipo de documento.");
						cmbTipoDocumento.setFocus(true);
						return;
					}
					if (txtDocumento.getText().length() <= 0) {
						txtDocumento.setErrorMessage("Ingrese el numero de documento.");
						txtDocumento.setFocus(true);
						return;
					}
					if (cmbTipoDocumento.getSelectedItem().getValue().toString().equals("2")) {
						if (validar.validarCedula(txtDocumento.getText()) == 0) {
							txtDocumento.setErrorMessage("El numero de documento es incorrecto.");
							txtDocumento.setFocus(true);
							return;
						}
					}
					dao_solicitante dao = new dao_solicitante();
					if (dao.obtenerSolicitantes(txtDocumento.getText(), 4, "",
							String.valueOf(solicitante.getId_solicitante()), 0).size() > 0) {
						txtDocumento.setErrorMessage("El numero de documento ya se encuentra registrado.");
						txtDocumento.setFocus(true);
						return;
					}
					if (txtNombres.getText().length() <= 0) {
						txtNombres.setErrorMessage("Ingrese el nombre.");
						txtNombres.setFocus(true);
						return;
					}
					if (txtApellidos.getText().length() <= 0) {
						txtApellidos.setErrorMessage("Ingrese el apellido.");
						txtApellidos.setFocus(true);
						return;
					}
					if (cmbEmpresa.getSelectedItem() == null) {
						cmbEmpresa.setErrorMessage("Seleccione una empresa.");
						cmbEmpresa.setFocus(true);
						return;
					}
				} else {
					if (solicitud.getId_campo() == 8) {
						if (cmbTipoDocumento.getSelectedItem() == null) {
							cmbTipoDocumento.setErrorMessage("Seleccione un tipo de documento.");
							cmbTipoDocumento.setFocus(true);
							return;
						}
						if (txtDocumento.getText().length() <= 0) {
							txtDocumento.setErrorMessage("Ingrese el numero de documento.");
							txtDocumento.setFocus(true);
							return;
						}
						if (cmbTipoDocumento.getSelectedItem().getValue().toString().equals("2")) {
							if (validar.validarCedula(txtDocumento.getText()) == 0) {
								txtDocumento.setErrorMessage("El numero de documento es incorrecto.");
								txtDocumento.setFocus(true);
								return;
							}
						}
						dao_solicitante dao = new dao_solicitante();
						if (dao.obtenerSolicitantes(txtDocumento.getText(), 4, "",
								String.valueOf(solicitante.getId_solicitante()), 0).size() > 0) {
							txtDocumento.setErrorMessage("El numero de documento ya se encuentra registrado.");
							txtDocumento.setFocus(true);
							return;
						}
					}
					if (solicitud.getId_campo() == 3) {
						if (cmbTipoDocumento.getSelectedItem() == null) {
							cmbTipoDocumento.setErrorMessage("Seleccione un tipo de documento.");
							cmbTipoDocumento.setFocus(true);
							return;
						}
					}
					if (solicitud.getId_campo() == 4) {
						if (txtDocumento.getText().length() <= 0) {
							txtDocumento.setErrorMessage("Ingrese el numero de documento.");
							txtDocumento.setFocus(true);
							return;
						}
						if (cmbTipoDocumento.getSelectedItem().getValue().toString().equals("2")) {
							if (validar.validarCedula(txtDocumento.getText()) == 0) {
								txtDocumento.setErrorMessage("El numero de documento es incorrecto.");
								txtDocumento.setFocus(true);
								return;
							}
						}
						dao_solicitante dao = new dao_solicitante();
						if (dao.obtenerSolicitantes(txtDocumento.getText(), 4, "",
								String.valueOf(solicitante.getId_solicitante()), 0).size() > 0) {
							txtDocumento.setErrorMessage("El numero de documento ya se encuentra registrado.");
							txtDocumento.setFocus(true);
							return;
						}
					}
					if (solicitud.getId_campo() == 5) {
						if (txtNombres.getText().length() <= 0) {
							txtNombres.setErrorMessage("Ingrese el nombre.");
							txtNombres.setFocus(true);
							return;
						}
					}
					if (solicitud.getId_campo() == 6) {
						if (txtApellidos.getText().length() <= 0) {
							txtApellidos.setErrorMessage("Ingrese el apellido.");
							txtApellidos.setFocus(true);
							return;
						}
					}
					if (solicitud.getId_campo() == 7) {
						if (cmbEmpresa.getSelectedItem() == null) {
							cmbEmpresa.setErrorMessage("Seleccione una empresa.");
							cmbEmpresa.setFocus(true);
							return;
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
		Messagebox.show("Esta seguro de modificar el solicitante?", ".:: Modificar solicitante ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_solicitante dao = new dao_solicitante();
							modelo_solicitante solicitante = new modelo_solicitante();
							solicitante.setId_solicitante(Long.parseLong(txtId.getText()));
							solicitante.setId_tip_documento(
									Long.parseLong(cmbTipoDocumento.getSelectedItem().getValue().toString()));
							solicitante
									.setId_empresa(Long.parseLong(cmbEmpresa.getSelectedItem().getValue().toString()));
							solicitante.setNum_documento(txtDocumento.getText());
							solicitante.setNom_solicitante(txtNombres.getText());
							solicitante.setApe_solicitante(txtApellidos.getText());
							solicitante.setUsu_modifica(user);
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							solicitante.setFec_modifica(timestamp);
							if (solicitud.getEst_solicitud().equals("T")) {
								solicitante.setEst_solicitante("PACP");
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
								solicitante.setEst_solicitante("AE");
								solicitud.setEst_solicitud("E");
								solicitud.setComentario_3(txtComentario.getText());
								solicitud.setId_user_3(id_user);
								solicitud.setFecha_3(timestamp);
								solicitud.setUsu_modifica(user);
								solicitud.setFec_modifica(timestamp);
							}
							int tipo = 1;
							try {
								dao.modificarSolicitante(solicitante, solicitud, tipo);
								if (tipo == 1) {
									Messagebox.show("La solicitante se modificó correctamente.",
											".:: Modificar solicitante ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								} else {
									Messagebox.show("No se realizaron cambios en el registro.",
											".:: Modificar solicitante ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								}
								limpiarCampos();
								Sessions.getCurrent().removeAttribute("solicitante");
								Events.postEvent(new Event("onClose", zModificar));
							} catch (Exception e) {
								Messagebox.show(
										"Error al modificar el solicitante. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Modificar solicitante ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zModificar));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtDocumento.setText("");
		txtNombres.setText("");
		txtApellidos.setText("");
		cmbTipoDocumento.setText("");
		cmbEmpresa.setText("");
	}

}
