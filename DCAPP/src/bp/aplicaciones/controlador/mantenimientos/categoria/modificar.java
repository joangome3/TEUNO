package bp.aplicaciones.controlador.mantenimientos.categoria;

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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_categoria;
import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_categoria;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
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
	Textbox txtId, txtNombre, txtDescripcion, txtComentario;
	@Wire
	Combobox cmbLocalidad;
	@Wire
	Checkbox chkMarca, chkModelo, chkSerie, chkCodigoActivo;

	List<modelo_localidad> listaLocalidad = new ArrayList<modelo_localidad>();

	String comentario_1 = "";
	String comentario_2 = "";

	Date fecha_comentario_1 = null;
	Date fecha_comentario_2 = null;
	Date fecha_comentario_3 = null;

	long id = 0;
	long id_mantenimiento = 5;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	modelo_categoria categoria = (modelo_categoria) Sessions.getCurrent().getAttribute("categoria");

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
		txtNombre.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtNombre.setText(txtNombre.getText().toUpperCase());
			}
		});
		txtDescripcion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtDescripcion.setText(txtDescripcion.getText().toUpperCase());
			}
		});
		txtComentario.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtComentario.setText(txtComentario.getText().toUpperCase());
			}
		});
		cargarLocalidades();
		cargarDatos();
		validarCamposActualizar();
	}

	public void cargarDatos() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		txtId.setText(String.valueOf(categoria.getId_categoria()));
		txtNombre.setText(categoria.getNom_categoria());
		txtDescripcion.setText(categoria.getDes_categoria());
		for (int i = 0; i < listaLocalidad.size(); i++) {
			if (listaLocalidad.get(i).getId_localidad() == categoria.getId_localidad()) {
				cmbLocalidad.setText(listaLocalidad.get(i).getNom_localidad());
				i = listaLocalidad.size() + 1;
			}
		}
		if (categoria.getMos_marca().equals("S")) {
			chkMarca.setChecked(true);
		} else {
			chkMarca.setChecked(false);
		}
		if (categoria.getMos_modelo().equals("S")) {
			chkModelo.setChecked(true);
		} else {
			chkModelo.setChecked(false);
		}
		if (categoria.getMos_serie().equals("S")) {
			chkSerie.setChecked(true);
		} else {
			chkSerie.setChecked(false);
		}
		if (categoria.getMos_cod_activo().equals("S")) {
			chkCodigoActivo.setChecked(true);
		} else {
			chkCodigoActivo.setChecked(false);
		}
	}

	public void validarCamposActualizar()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		txtNombre.setDisabled(true);
		txtDescripcion.setDisabled(true);
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, categoria.getId_categoria(),
				7);
		if (solicitud != null) {
			if (solicitud.getId_tip_solicitud() == 1 || solicitud.getId_tip_solicitud() == 2) {
				if (solicitud.getEst_solicitud().equals("T")) {
					txtNombre.setDisabled(false);
					txtDescripcion.setDisabled(false);
				} else {
					if (solicitud.getId_campo() == 1) {
						txtNombre.setDisabled(false);
					} else {
						txtNombre.setDisabled(true);
					}
					if (solicitud.getId_campo() == 2) {
						txtDescripcion.setDisabled(false);
					} else {
						txtDescripcion.setDisabled(true);
					}
					if (solicitud.getId_campo() == 9) {
						chkMarca.setDisabled(false);
					} else {
						chkMarca.setDisabled(true);
					}
					if (solicitud.getId_campo() == 10) {
						chkModelo.setDisabled(false);
					} else {
						chkModelo.setDisabled(true);
					}
					if (solicitud.getId_campo() == 11) {
						chkSerie.setDisabled(false);
					} else {
						chkSerie.setDisabled(true);
					}
					if (solicitud.getId_campo() == 12) {
						chkCodigoActivo.setDisabled(false);
					} else {
						chkCodigoActivo.setDisabled(true);
					}
				}
			}
		}
	}

	public List<modelo_localidad> obtenerLocalidades() {
		return listaLocalidad;
	}

	public void cargarLocalidades() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_localidad dao = new dao_localidad();
		String criterio = "";
		try {
			listaLocalidad = dao.obtenerLocalidades(criterio, 1, 0, 0);
			binder.loadComponent(cmbLocalidad);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las localidades. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar localidad ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onBlur=#txtNombre")
	public void onBlur$txtNombre()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (txtNombre.getText().length() <= 0) {
			return;
		}
		dao_categoria dao = new dao_categoria();
		if (dao.obtenerCategorias(txtNombre.getText(), String.valueOf(id_dc), 3, categoria.getId_categoria(), 0)
				.size() > 0) {
			txtNombre.setFocus(true);
			txtNombre.setErrorMessage("El nombre ya se encuentra registrado.");
			return;
		}
	}

	public boolean validarSiExisteSolicitudPendienteEjecucion()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_solicitud_pendiente = false;
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, categoria.getId_categoria(),
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, categoria.getId_categoria(),
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, categoria.getId_categoria(),
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
					if (txtNombre.getText().length() <= 0) {
						txtNombre.setFocus(true);
						txtNombre.setErrorMessage("Ingrese la categoría.");
						return;
					}
					dao_categoria dao = new dao_categoria();
					if (dao.obtenerCategorias(txtNombre.getText(), String.valueOf(id_dc), 3,
							categoria.getId_categoria(), 0).size() > 0) {
						txtNombre.setFocus(true);
						txtNombre.setErrorMessage("El nombre ya se encuentra registrado.");
						return;
					}
					if (txtDescripcion.getText().length() <= 0) {
						txtDescripcion.setFocus(true);
						txtDescripcion.setErrorMessage("Ingrese la contraseña.");
						return;
					}
					if (cmbLocalidad.getSelectedItem() == null) {
						cmbLocalidad.setFocus(true);
						cmbLocalidad.setErrorMessage("Seleccione una localidad.");
						return;
					}
				} else {
					if (solicitud.getId_campo() == 1) {
						if (txtNombre.getText().length() <= 0) {
							txtNombre.setFocus(true);
							txtNombre.setErrorMessage("Ingrese la categoría.");
							return;
						}
						dao_categoria dao = new dao_categoria();
						if (dao.obtenerCategorias(txtNombre.getText(), String.valueOf(id_dc), 3,
								categoria.getId_categoria(), 0).size() > 0) {
							txtNombre.setFocus(true);
							txtNombre.setErrorMessage("El nombre ya se encuentra registrado.");
							return;
						}
					}
					if (solicitud.getId_campo() == 2) {
						if (txtDescripcion.getText().length() <= 0) {
							txtDescripcion.setFocus(true);
							txtDescripcion.setErrorMessage("Ingrese la descripción.");
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
		Messagebox.show("Esta seguro de modificar la categoría?", ".:: Modificar categoría ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_categoria dao = new dao_categoria();
							modelo_categoria categoria = new modelo_categoria();
							categoria.setId_categoria(Long.parseLong(txtId.getText()));
							categoria.setNom_categoria(txtNombre.getText());
							categoria.setDes_categoria(txtDescripcion.getText());
							categoria.setId_localidad(
									Long.parseLong(cmbLocalidad.getSelectedItem().getValue().toString()));
							if (chkMarca.isChecked()) {
								categoria.setMos_marca("S");
							} else {
								categoria.setMos_marca("N");
							}
							if (chkModelo.isChecked()) {
								categoria.setMos_modelo("S");
							} else {
								categoria.setMos_modelo("N");
							}
							if (chkSerie.isChecked()) {
								categoria.setMos_serie("S");
							} else {
								categoria.setMos_serie("N");
							}
							if (chkCodigoActivo.isChecked()) {
								categoria.setMos_cod_activo("S");
							} else {
								categoria.setMos_cod_activo("N");
							}
							categoria.setUsu_modifica(user);
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							categoria.setFec_modifica(timestamp);
							if (solicitud.getEst_solicitud().equals("T")) {
								categoria.setEst_categoria("PACP");
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
								categoria.setEst_categoria("AE");
								solicitud.setEst_solicitud("E");
								solicitud.setComentario_3(txtComentario.getText());
								solicitud.setId_user_3(id_user);
								solicitud.setFecha_3(timestamp);
								solicitud.setUsu_modifica(user);
								solicitud.setFec_modifica(timestamp);
							}
							int tipo = 1;
							try {
								dao.modificarCategoria(categoria, solicitud, tipo);
								if (tipo == 1) {
									Messagebox.show("La categoría se modificó correctamente.",
											".:: Modificar categoría ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								} else {
									Messagebox.show("No se realizaron cambios en el registro.",
											".:: Modificar categoría ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								}
								limpiarCampos();
								Sessions.getCurrent().removeAttribute("categoria");
								Events.postEvent(new Event("onClose", zModificar));
							} catch (Exception e) {
								Messagebox.show(
										"Error al modificar la categoría. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Modificar categoría ::.", Messagebox.OK, Messagebox.EXCLAMATION);
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
		txtNombre.setText("");
		txtDescripcion.setText("");
		cmbLocalidad.setText("");
		chkMarca.setChecked(false);
		chkModelo.setChecked(false);
		chkSerie.setChecked(false);
		chkCodigoActivo.setChecked(false);
	}

}
