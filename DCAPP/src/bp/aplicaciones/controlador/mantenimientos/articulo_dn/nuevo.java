package bp.aplicaciones.controlador.mantenimientos.articulo_dn;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Window;

import bp.aplicaciones.mantenimientos.modelo.modelo_categoria_dn;
import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_articulo_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_articulo_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_capacidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_articulo_ubicacion_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_respaldo;
import bp.aplicaciones.mantenimientos.modelo.modelo_ubicacion_dn;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;

@SuppressWarnings({ "serial", "deprecation" })
public class nuevo extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zNuevo;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Textbox txtId, txtCodigo, txtDescripcion, txtIDContenedor, txtBuscarUbicacion;
	@Wire
	Intbox txtCantidad;
	@Wire
	Combobox cmbLocalidad, cmbCategoria, cmbRespaldo, cmbRespaldo1, cmbCapacidad;
	@Wire
	Row rwCapacidad, rwIngresaFecha, rwEsFecha, rwFechaRespaldo, rwFechaInicio, rwFechaFin, rwTipoRespaldo,
			rwIDContenedor, rwCreacion;
	@Wire
	Checkbox chkIngresaFecha, chkEsFecha, chkCreacion;
	@Wire
	Listbox lbxUbicaciones;
	@Wire
	Bandbox bdxUbicacion;
	@Wire
	Datebox dtxFechaInicio, dtxFechaFin;

	String vcapacidad, vfechainicio, vfechafin, vtiporespaldo, vidcontenedor;

	List<modelo_localidad> listaLocalidad = new ArrayList<modelo_localidad>();
	List<modelo_categoria_dn> listaCategoria = new ArrayList<modelo_categoria_dn>();
	List<modelo_ubicacion_dn> listaUbicacion = new ArrayList<modelo_ubicacion_dn>();
	List<modelo_respaldo> listaRespaldo = new ArrayList<modelo_respaldo>();
	List<modelo_respaldo> listaRespaldo1 = new ArrayList<modelo_respaldo>();
	List<modelo_capacidad> listaCapacidad = new ArrayList<modelo_capacidad>();

	long id = 0;
	long id_mantenimiento = 16;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	String cod_sesion = (String) Sessions.getCurrent().getAttribute("cod_sesion");

	validar_datos validar = new validar_datos();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();

	private Informativos informativos = new Informativos();
	private Error error = new Error();
	private Validaciones validaciones = new Validaciones();

	private Fechas fechas = new Fechas();

	int bandera = 0;

	boolean estado = false;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		lbxUbicaciones.setEmptyMessage(informativos.getMensaje_informativo_2());
		txtCodigo.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtCodigo.setText(txtCodigo.getText().toUpperCase().trim());
			}
		});
		txtDescripcion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtDescripcion.setText(txtDescripcion.getText().toUpperCase().trim());
			}
		});
		txtIDContenedor.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtIDContenedor.setText(txtIDContenedor.getText().toUpperCase().trim());
			}
		});
		txtCantidad.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtCantidad.setText(validar.soloNumeros(txtCantidad.getText()));
			}
		});
		obtenerId();
		inicializarListas();
		setearLocalidad();
	}

	public List<modelo_localidad> obtenerLocalidades() {
		return listaLocalidad;
	}

	public List<modelo_categoria_dn> obtenerCategorias() {
		return listaCategoria;
	}

	public List<modelo_ubicacion_dn> obtenerUbicaciones() {
		return listaUbicacion;
	}

	public List<modelo_respaldo> obtenerRespaldos() {
		return listaRespaldo;
	}

	public List<modelo_respaldo> obtenerRespaldos1() {
		return listaRespaldo1;
	}

	public List<modelo_capacidad> obtenerCapacidades() {
		return listaCapacidad;
	}

	public void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaRespaldo = consultasABaseDeDatos.cargarRespaldosDN("", 6, "", "", 0);
		listaRespaldo1 = consultasABaseDeDatos.cargarRespaldosDN("", 7, "", "", 0);
		listaLocalidad = consultasABaseDeDatos.cargarLocalidades("", 4, 0, 10);
		listaCategoria = consultasABaseDeDatos.cargarCategoriasDN("", id_dc, 4, 0, 10);
		listaUbicacion = consultasABaseDeDatos.cargarUbicacionesDN("", id_dc, 6, 0, 0, 0, 10);
		listaCapacidad = consultasABaseDeDatos.cargarCapacidadesDN("", 4, 0, 0);
		binder.loadComponent(cmbRespaldo);
		binder.loadComponent(cmbRespaldo1);
		binder.loadComponent(cmbLocalidad);
		binder.loadComponent(cmbCategoria);
		binder.loadComponent(lbxUbicaciones);
		binder.loadComponent(cmbCapacidad);
	}

	public void setearLocalidad() {
		Iterator<modelo_localidad> it = listaLocalidad.iterator();
		while (it.hasNext()) {
			modelo_localidad localidad = it.next();
			if (localidad.getId_localidad() == id_dc) {
				cmbLocalidad.setText(localidad.getNom_localidad());
				break;
			}
		}
	}

	public void obtenerId() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_articulo_dn dao = new dao_articulo_dn();
		try {
			id = dao.obtenerNuevoId();
			txtId.setText(String.valueOf(id));
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onOK=#txtBuscarUbicacion")
	public void onOKtxtBuscarUbicacion()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		bdxUbicacion.setText("");
		bdxUbicacion.setTooltiptext("");
		listaUbicacion = consultasABaseDeDatos.cargarUbicacionesDN(txtBuscarUbicacion.getText().toString(), id_dc, 6, 0,
				0, 0, 10);
		lbxUbicaciones.clearSelection();
		binder.loadComponent(lbxUbicaciones);
	}

	@Listen("onSelect=#lbxUbicaciones")
	public void onSelectlbxUbicaciones()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (lbxUbicaciones.getSelectedItem() == null) {
			return;
		}
		int index = lbxUbicaciones.getSelectedIndex();
		bdxUbicacion.setText(
				listaUbicacion.get(index).getNom_ubicacion() + " - " + listaUbicacion.get(index).getPos_ubicacion());
		bdxUbicacion.setTooltiptext(
				listaUbicacion.get(index).getNom_ubicacion() + " - " + listaUbicacion.get(index).getPos_ubicacion());
	}

	@Listen("onSelect=#cmbCategoria")
	public void onSelectcmbCategoria()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (listaCategoria.size() == 0) {
			return;
		}
		if (cmbCategoria.getSelectedItem() == null) {
			return;
		}
		int index = cmbCategoria.getSelectedIndex();
		if (listaCategoria.get(index).getMos_capacidad().equals("S")) {
			rwCapacidad.setVisible(true);
			vcapacidad = "S";
		} else {
			rwCapacidad.setVisible(false);
			vcapacidad = "N";
		}
		if (listaCategoria.get(index).getMos_fec_inicio().equals("S")) {
			rwIngresaFecha.setVisible(true);
			vfechainicio = "S";
		} else {
			rwIngresaFecha.setVisible(false);
			chkIngresaFecha.setChecked(false);
			rwFechaRespaldo.setVisible(false);
			cmbRespaldo1.setText("");
			rwEsFecha.setVisible(false);
			chkEsFecha.setChecked(false);
			rwFechaInicio.setVisible(false);
			rwFechaFin.setVisible(false);
			dtxFechaInicio.setValue(null);
			dtxFechaFin.setValue(null);
			vfechainicio = "N";
		}
		if (listaCategoria.get(index).getMos_fec_fin().equals("S")) {
			vfechafin = "S";
		} else {
			vfechafin = "N";
		}
		if (listaCategoria.get(index).getMos_tip_respaldo().equals("S")) {
			rwTipoRespaldo.setVisible(true);
			vtiporespaldo = "S";
		} else {
			rwTipoRespaldo.setVisible(false);
			vtiporespaldo = "N";
		}
		if (listaCategoria.get(index).getMos_id_contenedor().equals("S")) {
			rwIDContenedor.setVisible(true);
			vidcontenedor = "S";
		} else {
			rwIDContenedor.setVisible(false);
			vidcontenedor = "N";
		}
		if (listaCategoria.get(index).getId_categoria() == 1) {
			rwCreacion.setVisible(true);
			chkCreacion.setChecked(false);
		} else {
			rwCreacion.setVisible(false);
			chkCreacion.setChecked(false);
		}
	}

	@Listen("onSelect=#cmbRespaldo")
	public void onSelectcmbRespaldo() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbRespaldo.getSelectedItem() == null) {
			cmbRespaldo.setTooltiptext("");
		} else if (cmbRespaldo.getText().length() <= 0) {
			cmbRespaldo.setTooltiptext("");
		} else {
			cmbRespaldo.setTooltiptext(cmbRespaldo.getSelectedItem().getLabel());
		}
	}

	@Listen("onSelect=#cmbRespaldo1")
	public void onSelectcmbRespaldo1() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbRespaldo1.getSelectedItem() == null) {
			cmbRespaldo1.setTooltiptext("");
		} else if (cmbRespaldo1.getText().length() <= 0) {
			cmbRespaldo1.setTooltiptext("");
		} else {
			cmbRespaldo1.setTooltiptext(cmbRespaldo1.getSelectedItem().getLabel());
		}
	}

	@Listen("onSelect=#cmbCapacidad")
	public void onSelectcmbCapacidad() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbCapacidad.getSelectedItem() == null) {
			cmbCapacidad.setTooltiptext("");
		} else if (cmbCapacidad.getText().length() <= 0) {
			cmbCapacidad.setTooltiptext("");
		} else {
			cmbCapacidad.setTooltiptext(cmbCapacidad.getSelectedItem().getLabel());
		}
	}

	@Listen("onOK=#cmbRespaldo")
	public void onOKcmbRespaldo() throws ClassNotFoundException, FileNotFoundException, IOException {
		String criterio = cmbRespaldo.getText().toString().toUpperCase();
		listaRespaldo = consultasABaseDeDatos.cargarRespaldosDN(criterio, 6, "", "", 0);
	}

	@Listen("onOK=#cmbRespaldo1")
	public void onOKcmbRespaldo1() throws ClassNotFoundException, FileNotFoundException, IOException {
		String criterio = cmbRespaldo1.getText().toString().toUpperCase();
		listaRespaldo1 = consultasABaseDeDatos.cargarRespaldosDN(criterio, 7, "", "", 0);
	}

	@Listen("onCheck=#chkIngresaFecha")
	public void onCheckchkIngresaFecha() {
		if (chkIngresaFecha.isChecked()) {
			rwFechaRespaldo.setVisible(true);
			cmbRespaldo1.setText("");
			rwEsFecha.setVisible(true);
		} else {
			rwFechaRespaldo.setVisible(false);
			cmbRespaldo1.setText("");
			rwEsFecha.setVisible(false);
			chkEsFecha.setChecked(false);
			rwFechaInicio.setVisible(false);
			rwFechaFin.setVisible(false);
			dtxFechaInicio.setValue(null);
			dtxFechaFin.setValue(null);
		}
	}

	@Listen("onCheck=#chkEsFecha")
	public void onCheckchkEsFecha() {
		if (chkEsFecha.isChecked()) {
			rwFechaRespaldo.setVisible(false);
			cmbRespaldo1.setText("");
			if (vfechainicio.equals("S")) {
				rwFechaInicio.setVisible(true);
			} else {
				rwFechaInicio.setVisible(false);
			}
			if (vfechafin.equals("S")) {
				rwFechaFin.setVisible(true);
			} else {
				rwFechaFin.setVisible(false);
			}
			dtxFechaInicio.setValue(null);
			dtxFechaFin.setValue(null);
		} else {
			rwFechaRespaldo.setVisible(true);
			cmbRespaldo1.setText("");
			rwFechaInicio.setVisible(false);
			rwFechaFin.setVisible(false);
			dtxFechaInicio.setValue(null);
			dtxFechaFin.setValue(null);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClickbtnGrabar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (txtCodigo.getText().length() <= 0) {
			txtCodigo.setErrorMessage(validaciones.getMensaje_validacion_1());
			return;
		}
		if (txtDescripcion.getText().length() <= 0) {
			txtDescripcion.setErrorMessage(validaciones.getMensaje_validacion_2());
			return;
		}
		if (cmbCategoria.getSelectedItem() == null) {
			cmbCategoria.setErrorMessage(validaciones.getMensaje_validacion_3());
			return;
		}
		if (vcapacidad.equals("S")) {
			if (cmbCapacidad.getSelectedItem() == null) {
				cmbCapacidad.setErrorMessage(validaciones.getMensaje_validacion_7());
				return;
			}
		}
		if (vfechainicio.equals("S")) {
			if (chkIngresaFecha.isChecked()) {
				if (chkEsFecha.isChecked()) {
					if (dtxFechaInicio.getValue() == null) {
						dtxFechaInicio.setErrorMessage(validaciones.getMensaje_validacion_4());
						return;
					}
				} else {
					if (cmbRespaldo1.getSelectedItem() == null) {
						cmbRespaldo1.setErrorMessage(validaciones.getMensaje_validacion_4());
					}
				}
			}
		}
		if (lbxUbicaciones.getSelectedItem() == null) {
			bdxUbicacion.setErrorMessage(validaciones.getMensaje_validacion_6());
			return;
		}
		if (txtCantidad.getText().length() <= 0) {
			txtCantidad.setErrorMessage(validaciones.getMensaje_validacion_8());
			return;
		}
		if (cmbLocalidad.getSelectedItem() == null) {
			cmbLocalidad.setErrorMessage(validaciones.getMensaje_validacion_9());
			return;
		}
		/*
		 * Se valida que no se supere la capacidad permitida en la ubicacion
		 * seleccionada
		 */
		int indexUbicacion = lbxUbicaciones.getSelectedIndex();
		String seValidaCapacidad = consultasABaseDeDatos
				.seValidaCapacidadEnUbicacionDN(listaUbicacion.get(indexUbicacion).getId_ubicacion());
		if (seValidaCapacidad.equals("S")) {
			int capacidadMaxima = consultasABaseDeDatos.capacidadMaximaEnUbicacionDN(
					listaUbicacion.get(indexUbicacion).getId_ubicacion(), seValidaCapacidad);
			int totalArticulos = consultasABaseDeDatos
					.totalArticulosEnUbicacionDN(listaUbicacion.get(indexUbicacion).getId_ubicacion());
			if (!chkCreacion.isChecked()) {
				if ((totalArticulos + 1) > capacidadMaxima) {
					Messagebox.show(informativos.getMensaje_informativo_10(), informativos.getMensaje_informativo_17(),
							Messagebox.OK, Messagebox.INFORMATION);
					return;
				}
			} else {
				if ((totalArticulos + 2) > capacidadMaxima) {
					Messagebox.show(informativos.getMensaje_informativo_121(), informativos.getMensaje_informativo_17(),
							Messagebox.OK, Messagebox.INFORMATION);
					return;
				}
			}
		}
		Messagebox.show(informativos.getMensaje_informativo_16(), informativos.getMensaje_informativo_15(),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							estado = false;
							dao_articulo_dn dao = new dao_articulo_dn();
							modelo_articulo_dn articulo = new modelo_articulo_dn();
							modelo_articulo_dn articulo1 = new modelo_articulo_dn();
							articulo.setId_categoria(
									Long.parseLong(cmbCategoria.getSelectedItem().getValue().toString()));
							articulo1.setId_categoria(articulo.getId_categoria()); //
							articulo.setCod_articulo(txtCodigo.getText().toString().trim());
							articulo1.setCod_articulo(articulo.getCod_articulo()); //
							articulo.setDes_articulo(txtDescripcion.getText().toString().trim());
							articulo1.setDes_articulo(articulo.getDes_articulo()); //
							articulo.setId_localidad(
									Long.parseLong(cmbLocalidad.getSelectedItem().getValue().toString()));
							articulo1.setId_localidad(articulo.getId_localidad()); //
							if (vcapacidad.equals("S")) {
								articulo.setId_capacidad(
										Long.valueOf(cmbCapacidad.getSelectedItem().getValue().toString()));
								articulo1.setId_capacidad(articulo.getId_capacidad()); //
							} else {
								articulo.setId_capacidad(0);
								articulo1.setId_capacidad(articulo.getId_capacidad()); //
							}
							if (vfechainicio.equals("S")) {
								if (chkIngresaFecha.isChecked()) {
									if (chkEsFecha.isChecked()) {
										articulo.setFec_inicio(
												fechas.obtenerTimestampDeDate(dtxFechaInicio.getValue()));
										articulo.setSi_ing_fec_inicio_fin("S");
										articulo.setEs_fecha("S");
										articulo1.setFec_inicio(articulo.getFec_inicio()); //
										articulo1.setSi_ing_fec_inicio_fin(articulo.getSi_ing_fec_inicio_fin()); //
										articulo1.setEs_fecha(articulo.getEs_fecha()); //
									} else {
										articulo.setId_fec_respaldo(
												Long.parseLong(cmbRespaldo1.getSelectedItem().getValue().toString()));
										articulo.setSi_ing_fec_inicio_fin("S");
										articulo.setEs_fecha("N");
										articulo1.setId_fec_respaldo(articulo.getId_fec_respaldo()); //
										articulo1.setSi_ing_fec_inicio_fin(articulo.getSi_ing_fec_inicio_fin()); //
										articulo1.setEs_fecha(articulo.getEs_fecha()); //
									}
								} else {
									articulo.setFec_inicio(null);
									articulo.setSi_ing_fec_inicio_fin("N");
									articulo.setEs_fecha("N");
									articulo.setId_fec_respaldo(0);
									articulo1.setFec_inicio(articulo.getFec_inicio()); //
									articulo1.setSi_ing_fec_inicio_fin(articulo.getSi_ing_fec_inicio_fin()); //
									articulo1.setEs_fecha(articulo.getEs_fecha()); //
									articulo1.setId_fec_respaldo(articulo.getId_fec_respaldo()); //
								}
							} else {
								articulo.setSi_ing_fec_inicio_fin("N");
								articulo.setEs_fecha("N");
								articulo.setFec_inicio(null);
								articulo.setId_fec_respaldo(0);
								articulo1.setFec_inicio(articulo.getFec_inicio()); //
								articulo1.setSi_ing_fec_inicio_fin(articulo.getSi_ing_fec_inicio_fin()); //
								articulo1.setEs_fecha(articulo.getEs_fecha()); //
								articulo1.setId_fec_respaldo(articulo.getId_fec_respaldo()); //
							}
							if (vfechafin.equals("S")) {
								if (chkIngresaFecha.isChecked()) {
									if (chkEsFecha.isChecked()) {
										if (dtxFechaFin.getValue() == null) {
											articulo.setFec_fin(null);
											articulo1.setFec_fin(articulo.getFec_fin()); //
										} else {
											articulo.setFec_fin(fechas.obtenerTimestampDeDate(dtxFechaFin.getValue()));
											articulo1.setFec_fin(articulo.getFec_fin()); //
										}
									} else {
										articulo.setFec_fin(null);
										articulo1.setFec_fin(articulo.getFec_fin()); //
									}
								} else {
									articulo.setFec_fin(null);
									articulo1.setFec_fin(articulo.getFec_fin()); //
								}
							} else {
								articulo.setFec_fin(null);
								articulo1.setFec_fin(articulo.getFec_fin()); //
							}
							if (vtiporespaldo.equals("S")) {
								if (cmbRespaldo.getSelectedItem() == null) {
									articulo.setId_tip_respaldo(0);
									articulo1.setId_tip_respaldo(articulo.getId_tip_respaldo()); //
								} else {
									articulo.setId_tip_respaldo(
											Long.valueOf(cmbRespaldo.getSelectedItem().getValue().toString()));
									articulo1.setId_tip_respaldo(articulo.getId_tip_respaldo()); //
								}
							} else {
								articulo.setId_tip_respaldo(0);
								articulo1.setId_tip_respaldo(articulo.getId_tip_respaldo()); //
							}
							if (vidcontenedor.equals("S")) {
								if (txtIDContenedor.getText().length() <= 0) {
									articulo.setId_contenedor("");
									articulo1.setId_contenedor(articulo.getId_contenedor()); //
								} else {
									articulo.setId_contenedor(txtIDContenedor.getText());
									articulo1.setId_contenedor(articulo.getId_contenedor()); //
								}
							} else {
								articulo.setId_contenedor("");
								articulo1.setId_contenedor(articulo.getId_contenedor()); //
							}
							if (dtxFechaFin.getValue() != null) {
								if (dtxFechaFin.getValue().compareTo(dtxFechaInicio.getValue()) < 0) {
									dtxFechaFin.setErrorMessage(validaciones.getMensaje_validacion_10());
									dtxFechaFin.setFocus(true);
									return;
								}
							}
							if (vfechainicio.equals("S")) {
								if (chkIngresaFecha.isChecked()) {
									if (chkEsFecha.isChecked()) {
										/*
										 * Se valida si un articulo tiene el mismo codigo, fecha inicio
										 * 
										 */
										String fecha_inicio = fechas.obtenerFechaFormateada(dtxFechaInicio.getValue(),
												"yyyy/MM/dd");
										if (dtxFechaFin.getValue() == null) {
											int totalArticulos = consultasABaseDeDatos
													.validarSiCodigoYFechaDeInicioDeArticuloExiste(txtCodigo.getText(),
															fecha_inicio, articulo.getId_categoria());
											if (totalArticulos > 0) {
												Messagebox.show(informativos.getMensaje_informativo_18(),
														informativos.getMensaje_informativo_17(), Messagebox.OK,
														Messagebox.INFORMATION);
												return;
											}
										} else {
											/*
											 * Se valida si un articulo tiene el mismo codigo, fecha inicio y fecha de
											 * fin
											 */
											String fecha_fin = fechas.obtenerFechaFormateada(dtxFechaFin.getValue(),
													"yyyy/MM/dd");
											int totalArticulos = consultasABaseDeDatos
													.validarSiCodigoYFechaDeInicioYFechaDeFinDeArticuloExiste(
															txtCodigo.getText(), fecha_inicio, fecha_fin,
															articulo.getId_categoria());
											if (totalArticulos > 0) {
												Messagebox.show(informativos.getMensaje_informativo_19(),
														informativos.getMensaje_informativo_17(), Messagebox.OK,
														Messagebox.INFORMATION);
												return;
											}
										}
									}
								}
							}
							/*
							 * Se valida si el codigo ingresado esta registrado anteriormente
							 */
							if (!chkIngresaFecha.isChecked() || !chkEsFecha.isChecked()) {
								List<modelo_articulo_dn> _listaArticulo = new ArrayList<modelo_articulo_dn>();
								_listaArticulo = consultasABaseDeDatos.cargarArticulosDN(
										txtCodigo.getText().toUpperCase().trim(), id_dc,
										String.valueOf(articulo.getId_categoria()), 13, 0, "", "");
								if (_listaArticulo.size() > 0) {
									/* Si es cinta */
									if (_listaArticulo.get(0).getId_categoria() == 1
											&& articulo.getId_categoria() == 1) {
										Messagebox.show(
												informativos.getMensaje_informativo_125().replace("?1",
														txtCodigo.getText().toUpperCase().trim()),
												informativos.getMensaje_informativo_15(),
												Messagebox.OK | Messagebox.CANCEL, Messagebox.ERROR,
												new org.zkoss.zk.ui.event.EventListener() {
													public void onEvent(Event evt)
															throws InterruptedException, ClassNotFoundException,
															FileNotFoundException, SQLException, IOException {
														if (evt.getName().equals("onOK")) {
															bandera = 1;
															if (articulo.getId_categoria() == 1) {
																articulo.setEst_articulo("PAC");
																articulo1.setEst_articulo("AE");
															} else {
																articulo.setEst_articulo("AE");
																articulo1.setEst_articulo(articulo.getEst_articulo());
															}
															articulo.setUsu_ingresa(user);
															articulo1.setUsu_ingresa(articulo.getUsu_ingresa());
															articulo.setFec_ingresa(
																	fechas.obtenerTimestampDeDate(new Date()));
															articulo1.setFec_ingresa(articulo.getFec_ingresa());
															/*
															 * Se guarda la relacion de la ubicacion con el articulo
															 */
															List<modelo_relacion_articulo_ubicacion_dn> listaRelacionArticulos = new ArrayList<modelo_relacion_articulo_ubicacion_dn>();
															List<modelo_relacion_articulo_ubicacion_dn> listaRelacionArticulos1 = new ArrayList<modelo_relacion_articulo_ubicacion_dn>();
															int pos_ubicacion = 0;
															Iterator<Listitem> it = lbxUbicaciones.getSelectedItems()
																	.iterator();
															while (it.hasNext()) {
																Listitem item = it.next();
																final int indice = item.getIndex();
																modelo_relacion_articulo_ubicacion_dn relacion_articulo_ubicacion = new modelo_relacion_articulo_ubicacion_dn();
																modelo_relacion_articulo_ubicacion_dn relacion_articulo_ubicacion1 = new modelo_relacion_articulo_ubicacion_dn();
																relacion_articulo_ubicacion.setId_articulo(
																		Long.parseLong(txtId.getText()));
																relacion_articulo_ubicacion1.setId_articulo(
																		relacion_articulo_ubicacion.getId_articulo()); //
																relacion_articulo_ubicacion.setId_ubicacion(
																		listaUbicacion.get(indice).getId_ubicacion());
																relacion_articulo_ubicacion1.setId_ubicacion(
																		relacion_articulo_ubicacion.getId_ubicacion()); //
																relacion_articulo_ubicacion.setSto_articulo(1);
																relacion_articulo_ubicacion1.setSto_articulo(
																		relacion_articulo_ubicacion.getSto_articulo()); //
																pos_ubicacion = consultasABaseDeDatos
																		.posicionMaximaEnUbicacionDN((listaUbicacion
																				.get(indice).getId_ubicacion()))
																		+ 1;
																relacion_articulo_ubicacion
																		.setPos_ubicacion(pos_ubicacion);
																relacion_articulo_ubicacion1.setPos_ubicacion(
																		relacion_articulo_ubicacion.getPos_ubicacion()
																				+ 1); //
																relacion_articulo_ubicacion.setEst_relacion("A");
																relacion_articulo_ubicacion1.setEst_relacion(
																		relacion_articulo_ubicacion.getEst_relacion()); //
																relacion_articulo_ubicacion.setUsu_ingresa(user);
																relacion_articulo_ubicacion1.setUsu_ingresa(
																		relacion_articulo_ubicacion.getUsu_ingresa()); //
																relacion_articulo_ubicacion.setFec_ingresa(
																		fechas.obtenerTimestampDeDate(new Date()));
																relacion_articulo_ubicacion1.setFec_ingresa(
																		relacion_articulo_ubicacion.getFec_ingresa()); //
																listaRelacionArticulos.add(relacion_articulo_ubicacion);
																listaRelacionArticulos1
																		.add(relacion_articulo_ubicacion1);
															}
															int se_crea_caja = 0;
															if (chkCreacion.isChecked()) {
																se_crea_caja = 1;
																String nom_ubicacion = "";
																for (int i = 0; i < listaUbicacion.size(); i++) {
																	if (listaUbicacion.get(i)
																			.getId_ubicacion() == listaRelacionArticulos
																					.get(0).getId_ubicacion()) {
																		nom_ubicacion = listaUbicacion.get(i)
																				.toStringUbicacion();
																		i = listaUbicacion.size() + 1;
																	}
																}
																if (listaRelacionArticulos.get(0).getId_ubicacion() <= 3
																		|| listaRelacionArticulos.get(0)
																				.getId_ubicacion() >= 134) {
																	Messagebox.show(
																			informativos.getMensaje_informativo_120()
																					.replace("?1", nom_ubicacion),
																			informativos.getMensaje_informativo_17(),
																			Messagebox.OK, Messagebox.EXCLAMATION);
																	return;
																}	
															} else {
																se_crea_caja = 0;
															}
															if (articulo.getId_categoria() == 2) {
																String nom_ubicacion = "";
																for (int i = 0; i < listaUbicacion.size(); i++) {
																	if (listaUbicacion.get(i)
																			.getId_ubicacion() == listaRelacionArticulos
																					.get(0).getId_ubicacion()) {
																		nom_ubicacion = listaUbicacion.get(i)
																				.toStringUbicacion();
																		i = listaUbicacion.size() + 1;
																	}
																}
																if (listaRelacionArticulos.get(0).getId_ubicacion() <= 3
																		|| listaRelacionArticulos.get(0)
																				.getId_ubicacion() >= 134) {
																	Messagebox.show(
																			informativos.getMensaje_informativo_120()
																					.replace("?1", nom_ubicacion),
																			informativos.getMensaje_informativo_17(),
																			Messagebox.OK, Messagebox.EXCLAMATION);
																	return;
																}
															}
															try {
																dao.insertarArticulo(articulo, listaRelacionArticulos,
																		se_crea_caja, articulo1,
																		listaRelacionArticulos1);
																if (bandera == 0) {
																	Messagebox.show(
																			informativos.getMensaje_informativo_20(),
																			informativos.getMensaje_informativo_17(),
																			Messagebox.OK, Messagebox.EXCLAMATION);
																} else {
																	Messagebox.show(
																			informativos.getMensaje_informativo_106()
																					.replace("?", txtCodigo.getText()
																							.toUpperCase().trim()),
																			informativos.getMensaje_informativo_17(),
																			Messagebox.OK, Messagebox.EXCLAMATION);
																}
																limpiarCampos();
																obtenerId();
															} catch (Exception e) {
																Messagebox.show(error.getMensaje_error_4()
																		+ error.getMensaje_error_1() + e.getMessage(),
																		informativos.getMensaje_informativo_17(),
																		Messagebox.OK, Messagebox.EXCLAMATION);
															}
														} else {
															estado = false;
															bandera = 0;
														}
													}
												});
									} else if (_listaArticulo.get(0).getId_categoria() == 2
											&& articulo.getId_categoria() == 2) { // Si es caja
										Messagebox.show(
												informativos.getMensaje_informativo_124().replace("?1",
														txtCodigo.getText().toUpperCase().trim()),
												informativos.getMensaje_informativo_15(),
												Messagebox.OK | Messagebox.CANCEL, Messagebox.ERROR,
												new org.zkoss.zk.ui.event.EventListener() {
													public void onEvent(Event evt)
															throws InterruptedException, ClassNotFoundException,
															FileNotFoundException, SQLException, IOException {
														if (evt.getName().equals("onOK")) {
															bandera = 0;
															if (articulo.getId_categoria() == 1) {
																articulo.setEst_articulo("PAC");
																articulo1.setEst_articulo(articulo.getEst_articulo());
															} else {
																articulo.setEst_articulo("AE");
																articulo1.setEst_articulo(articulo.getEst_articulo());
															}
															articulo.setUsu_ingresa(user);
															articulo1.setUsu_ingresa(articulo.getUsu_ingresa());
															articulo.setFec_ingresa(
																	fechas.obtenerTimestampDeDate(new Date()));
															articulo1.setFec_ingresa(articulo.getFec_ingresa());
															/*
															 * Se guarda la relacion de la ubicacion con el articulo
															 */
															List<modelo_relacion_articulo_ubicacion_dn> listaRelacionArticulos = new ArrayList<modelo_relacion_articulo_ubicacion_dn>();
															List<modelo_relacion_articulo_ubicacion_dn> listaRelacionArticulos1 = new ArrayList<modelo_relacion_articulo_ubicacion_dn>();
															int pos_ubicacion = 0;
															Iterator<Listitem> it = lbxUbicaciones.getSelectedItems()
																	.iterator();
															while (it.hasNext()) {
																Listitem item = it.next();
																final int indice = item.getIndex();
																modelo_relacion_articulo_ubicacion_dn relacion_articulo_ubicacion = new modelo_relacion_articulo_ubicacion_dn();
																modelo_relacion_articulo_ubicacion_dn relacion_articulo_ubicacion1 = new modelo_relacion_articulo_ubicacion_dn();
																relacion_articulo_ubicacion.setId_articulo(
																		Long.parseLong(txtId.getText()));
																relacion_articulo_ubicacion1.setId_articulo(
																		relacion_articulo_ubicacion.getId_articulo()); //
																relacion_articulo_ubicacion.setId_ubicacion(
																		listaUbicacion.get(indice).getId_ubicacion());
																relacion_articulo_ubicacion1.setId_ubicacion(
																		relacion_articulo_ubicacion.getId_ubicacion()); //
																relacion_articulo_ubicacion.setSto_articulo(1);
																relacion_articulo_ubicacion1.setSto_articulo(
																		relacion_articulo_ubicacion.getSto_articulo()); //
																pos_ubicacion = consultasABaseDeDatos
																		.posicionMaximaEnUbicacionDN((listaUbicacion
																				.get(indice).getId_ubicacion()))
																		+ 1;
																relacion_articulo_ubicacion
																		.setPos_ubicacion(pos_ubicacion);
																relacion_articulo_ubicacion1.setPos_ubicacion(
																		relacion_articulo_ubicacion.getPos_ubicacion()
																				+ 1); //
																relacion_articulo_ubicacion.setEst_relacion("A");
																relacion_articulo_ubicacion1.setEst_relacion(
																		relacion_articulo_ubicacion.getEst_relacion()); //
																relacion_articulo_ubicacion.setUsu_ingresa(user);
																relacion_articulo_ubicacion1.setUsu_ingresa(
																		relacion_articulo_ubicacion.getUsu_ingresa()); //
																relacion_articulo_ubicacion.setFec_ingresa(
																		fechas.obtenerTimestampDeDate(new Date()));
																relacion_articulo_ubicacion1.setFec_ingresa(
																		relacion_articulo_ubicacion.getFec_ingresa()); //
																listaRelacionArticulos.add(relacion_articulo_ubicacion);
																listaRelacionArticulos1
																		.add(relacion_articulo_ubicacion1);
															}
															int se_crea_caja = 0;
															if (chkCreacion.isChecked()) {
																se_crea_caja = 1;
																String nom_ubicacion = "";
																for (int i = 0; i < listaUbicacion.size(); i++) {
																	if (listaUbicacion.get(i)
																			.getId_ubicacion() == listaRelacionArticulos
																					.get(0).getId_ubicacion()) {
																		nom_ubicacion = listaUbicacion.get(i)
																				.toStringUbicacion();
																		i = listaUbicacion.size() + 1;
																	}
																}
																if (listaRelacionArticulos.get(0).getId_ubicacion() <= 3
																		|| listaRelacionArticulos.get(0)
																				.getId_ubicacion() >= 134) {
																	Messagebox.show(
																			informativos.getMensaje_informativo_120()
																					.replace("?1", nom_ubicacion),
																			informativos.getMensaje_informativo_17(),
																			Messagebox.OK, Messagebox.EXCLAMATION);
																	return;
																}
															} else {
																se_crea_caja = 0;
															}
															if (articulo.getId_categoria() == 2) {
																String nom_ubicacion = "";
																for (int i = 0; i < listaUbicacion.size(); i++) {
																	if (listaUbicacion.get(i)
																			.getId_ubicacion() == listaRelacionArticulos
																					.get(0).getId_ubicacion()) {
																		nom_ubicacion = listaUbicacion.get(i)
																				.toStringUbicacion();
																		i = listaUbicacion.size() + 1;
																	}
																}
																if (listaRelacionArticulos.get(0).getId_ubicacion() <= 3
																		|| listaRelacionArticulos.get(0)
																				.getId_ubicacion() >= 134) {
																	Messagebox.show(
																			informativos.getMensaje_informativo_120()
																					.replace("?1", nom_ubicacion),
																			informativos.getMensaje_informativo_17(),
																			Messagebox.OK, Messagebox.EXCLAMATION);
																	return;
																}
															}
															try {
																dao.insertarArticulo(articulo, listaRelacionArticulos,
																		se_crea_caja, articulo1,
																		listaRelacionArticulos1);
																if (bandera == 0) {
																	Messagebox.show(
																			informativos.getMensaje_informativo_20(),
																			informativos.getMensaje_informativo_17(),
																			Messagebox.OK, Messagebox.EXCLAMATION);
																} else {
																	Messagebox.show(
																			informativos.getMensaje_informativo_106()
																					.replace("?", txtCodigo.getText()
																							.toUpperCase().trim()),
																			informativos.getMensaje_informativo_17(),
																			Messagebox.OK, Messagebox.EXCLAMATION);
																}
																limpiarCampos();
																obtenerId();
															} catch (Exception e) {
																Messagebox.show(error.getMensaje_error_4()
																		+ error.getMensaje_error_1() + e.getMessage(),
																		informativos.getMensaje_informativo_17(),
																		Messagebox.OK, Messagebox.EXCLAMATION);
															}
														} else {
															estado = false;
															bandera = 0;
														}
													}
												});
									} else {
										estado = true;
										bandera = 0;
										articulo.setEst_articulo("AE");
										articulo1.setEst_articulo(articulo.getEst_articulo());
									}
								} else {
									estado = true;
									bandera = 0;
									articulo.setEst_articulo("AE");
									articulo1.setEst_articulo(articulo.getEst_articulo());
								}
							} else {
								estado = true;
								bandera = 0;
								articulo.setEst_articulo("AE");
								articulo1.setEst_articulo(articulo.getEst_articulo());
							}
							if (estado == false) {
								return;
							}
							articulo.setUsu_ingresa(user);
							articulo1.setUsu_ingresa(articulo.getUsu_ingresa());
							articulo.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
							articulo1.setFec_ingresa(articulo.getFec_ingresa());
							/*
							 * Se guarda la relacion de la ubicacion con el articulo
							 */
							List<modelo_relacion_articulo_ubicacion_dn> listaRelacionArticulos = new ArrayList<modelo_relacion_articulo_ubicacion_dn>();
							List<modelo_relacion_articulo_ubicacion_dn> listaRelacionArticulos1 = new ArrayList<modelo_relacion_articulo_ubicacion_dn>();
							int pos_ubicacion = 0;
							Iterator<Listitem> it = lbxUbicaciones.getSelectedItems().iterator();
							while (it.hasNext()) {
								Listitem item = it.next();
								final int indice = item.getIndex();
								modelo_relacion_articulo_ubicacion_dn relacion_articulo_ubicacion = new modelo_relacion_articulo_ubicacion_dn();
								modelo_relacion_articulo_ubicacion_dn relacion_articulo_ubicacion1 = new modelo_relacion_articulo_ubicacion_dn();
								relacion_articulo_ubicacion.setId_articulo(Long.parseLong(txtId.getText()));
								relacion_articulo_ubicacion1
										.setId_articulo(relacion_articulo_ubicacion.getId_articulo()); //
								relacion_articulo_ubicacion
										.setId_ubicacion(listaUbicacion.get(indice).getId_ubicacion());
								relacion_articulo_ubicacion1
										.setId_ubicacion(relacion_articulo_ubicacion.getId_ubicacion()); //
								relacion_articulo_ubicacion.setSto_articulo(1);
								relacion_articulo_ubicacion1
										.setSto_articulo(relacion_articulo_ubicacion.getSto_articulo()); //
								pos_ubicacion = consultasABaseDeDatos.posicionMaximaEnUbicacionDN(
										(listaUbicacion.get(indice).getId_ubicacion())) + 1;
								relacion_articulo_ubicacion.setPos_ubicacion(pos_ubicacion);
								relacion_articulo_ubicacion1
										.setPos_ubicacion(relacion_articulo_ubicacion.getPos_ubicacion() + 1); //
								relacion_articulo_ubicacion.setEst_relacion("A");
								relacion_articulo_ubicacion1
										.setEst_relacion(relacion_articulo_ubicacion.getEst_relacion()); //
								relacion_articulo_ubicacion.setUsu_ingresa(user);
								relacion_articulo_ubicacion1
										.setUsu_ingresa(relacion_articulo_ubicacion.getUsu_ingresa()); //
								relacion_articulo_ubicacion.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
								relacion_articulo_ubicacion1
										.setFec_ingresa(relacion_articulo_ubicacion.getFec_ingresa()); //
								listaRelacionArticulos.add(relacion_articulo_ubicacion);
								listaRelacionArticulos1.add(relacion_articulo_ubicacion1);
							}
							int se_crea_caja = 0;
							if (chkCreacion.isChecked()) {
								se_crea_caja = 1;
								String nom_ubicacion = "";
								for (int i = 0; i < listaUbicacion.size(); i++) {
									if (listaUbicacion.get(i).getId_ubicacion() == listaRelacionArticulos.get(0)
											.getId_ubicacion()) {
										nom_ubicacion = listaUbicacion.get(i).toStringUbicacion();
										i = listaUbicacion.size() + 1;
									}
								}
								if (listaRelacionArticulos.get(0).getId_ubicacion() <= 3
										|| listaRelacionArticulos.get(0).getId_ubicacion() >= 134) {
									Messagebox.show(
											informativos.getMensaje_informativo_120().replace("?1", nom_ubicacion),
											informativos.getMensaje_informativo_17(), Messagebox.OK,
											Messagebox.EXCLAMATION);
									return;
								}
								if (vfechainicio.equals("S")) {
									if (chkIngresaFecha.isChecked()) {
										if (chkEsFecha.isChecked()) {
											/*
											 * Se valida si un articulo tiene el mismo codigo, fecha inicio
											 * 
											 */
											String fecha_inicio = fechas
													.obtenerFechaFormateada(dtxFechaInicio.getValue(), "yyyy/MM/dd");
											if (dtxFechaFin.getValue() == null) {
												int totalArticulos = consultasABaseDeDatos
														.validarSiCodigoYFechaDeInicioDeArticuloExiste(
																txtCodigo.getText(), fecha_inicio, 2);
												if (totalArticulos > 0) {
													Messagebox.show(
															informativos.getMensaje_informativo_126().replace("?1",
																	txtCodigo.getText()),
															informativos.getMensaje_informativo_17(), Messagebox.OK,
															Messagebox.INFORMATION);
													return;
												}
											} else {
												/*
												 * Se valida si un articulo tiene el mismo codigo, fecha inicio y fecha
												 * de fin
												 */
												String fecha_fin = fechas.obtenerFechaFormateada(dtxFechaFin.getValue(),
														"yyyy/MM/dd");
												int totalArticulos = consultasABaseDeDatos
														.validarSiCodigoYFechaDeInicioYFechaDeFinDeArticuloExiste(
																txtCodigo.getText(), fecha_inicio, fecha_fin, 2);
												if (totalArticulos > 0) {
													Messagebox.show(
															informativos.getMensaje_informativo_126().replace("?1",
																	txtCodigo.getText()),
															informativos.getMensaje_informativo_17(), Messagebox.OK,
															Messagebox.INFORMATION);
													return;
												}
											}
										}
									}
								}
							} else {
								se_crea_caja = 0;
							}
							if (articulo.getId_categoria() == 2) {
								String nom_ubicacion = "";
								for (int i = 0; i < listaUbicacion.size(); i++) {
									if (listaUbicacion.get(i).getId_ubicacion() == listaRelacionArticulos.get(0)
											.getId_ubicacion()) {
										nom_ubicacion = listaUbicacion.get(i).toStringUbicacion();
										i = listaUbicacion.size() + 1;
									}
								}
								if (listaRelacionArticulos.get(0).getId_ubicacion() <= 3
										|| listaRelacionArticulos.get(0).getId_ubicacion() >= 134) {
									Messagebox.show(
											informativos.getMensaje_informativo_120().replace("?1", nom_ubicacion),
											informativos.getMensaje_informativo_17(), Messagebox.OK,
											Messagebox.EXCLAMATION);
									return;
								}
							}
							try {
								dao.insertarArticulo(articulo, listaRelacionArticulos, se_crea_caja, articulo1,
										listaRelacionArticulos1);
								if (bandera == 0) {
									Messagebox.show(informativos.getMensaje_informativo_20(),
											informativos.getMensaje_informativo_17(), Messagebox.OK,
											Messagebox.EXCLAMATION);
								} else {
									Messagebox.show(
											informativos.getMensaje_informativo_106().replace("?",
													txtCodigo.getText().toUpperCase().trim()),
											informativos.getMensaje_informativo_17(), Messagebox.OK,
											Messagebox.EXCLAMATION);
								}
								limpiarCampos();
								obtenerId();
							} catch (Exception e) {
								Messagebox.show(
										error.getMensaje_error_4() + error.getMensaje_error_1() + e.getMessage(),
										informativos.getMensaje_informativo_17(), Messagebox.OK,
										Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() throws ClassNotFoundException, FileNotFoundException, IOException {
		// validarSesion();
		Events.postEvent(new Event("onClose", zNuevo));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtCodigo.setText("");
		txtDescripcion.setText("");
		txtCantidad.setText("0");
		lbxUbicaciones.clearSelection();
		bdxUbicacion.setText("");
		bdxUbicacion.setTooltiptext("");
		txtBuscarUbicacion.setText("");
		cmbCategoria.setText("");
		cmbCapacidad.setText("");
		dtxFechaInicio.setValue(null);
		dtxFechaFin.setValue(null);
		cmbRespaldo.setText("");
		txtIDContenedor.setText("");
		rwCapacidad.setVisible(false);
		rwFechaInicio.setVisible(false);
		rwFechaFin.setVisible(false);
		rwTipoRespaldo.setVisible(false);
		rwIDContenedor.setVisible(false);
		rwIngresaFecha.setVisible(false);
		chkIngresaFecha.setChecked(false);
		rwFechaRespaldo.setVisible(false);
		cmbRespaldo1.setText("");
		rwEsFecha.setVisible(false);
		chkEsFecha.setChecked(false);
	}

}
