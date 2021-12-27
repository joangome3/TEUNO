package bp.aplicaciones.controlador.mantenimientos.parametros;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Intbox;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.mantenimientos.DAO.dao_categoria;
import bp.aplicaciones.mantenimientos.DAO.dao_estado_bitacora;
import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_1;
import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
import bp.aplicaciones.mantenimientos.DAO.dao_tipo_ubicacion;
import bp.aplicaciones.mantenimientos.DAO.dao_usuario;
import bp.aplicaciones.mantenimientos.modelo.modelo_categoria;
import bp.aplicaciones.mantenimientos.modelo.modelo_estado_bitacora;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_ubicacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_usuario;

@SuppressWarnings({ "serial", "deprecation" })
public class parametros1 extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Div zParametros;
	@Wire
	Button btnGrabar;
	@Wire
	Textbox txtURL1, txtURL2, txtEtiqueta1, txtEtiqueta2;
	@Wire
	Intbox intSecuencia1, intSecuencia2, intSecuencia3;
	@Wire
	Checkbox chkValidarCodigo1, chkActivarCargar, chkActivarBorrar, chkRedireccion1, chkRedireccion2;
	@Wire
	Combobox cmbCategoria1, cmbCategoria2, cmbLocalidad, cmbPerfil1, cmbEstado1, cmbEstado2, cmbUbicacion1, cmbUbicacion2;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	String consultar, insertar, modificar, relacionar, desactivar, eliminar, solicitar, revisar, aprobar, ejecutar;

	validar_datos validar = new validar_datos();

	List<modelo_parametros_generales_1> listaParametros = new ArrayList<modelo_parametros_generales_1>();
	List<modelo_perfil> listaPerfil1 = new ArrayList<modelo_perfil>();
	List<modelo_perfil> listaPerfil2 = new ArrayList<modelo_perfil>();
	List<modelo_categoria> listaCategoria = new ArrayList<modelo_categoria>();
	List<modelo_localidad> listaLocalidad = new ArrayList<modelo_localidad>();
	List<modelo_usuario> listaUsuario = new ArrayList<modelo_usuario>();
	List<modelo_estado_bitacora> listaEstado = new ArrayList<modelo_estado_bitacora>();
	List<modelo_tipo_ubicacion> listaUbicacion = new ArrayList<modelo_tipo_ubicacion>();

	long id_mantenimiento = 10;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		cargarPerfiles1();
		inicializarPermisos();
		if (insertar.equals("S")) {
			btnGrabar.setDisabled(false);
			btnGrabar.setVisible(true);
		} else {
			btnGrabar.setDisabled(true);
			btnGrabar.setVisible(false);
		}
		cargarUsuarios();
		cargarLocalidades();
		cargarUbicaciones();
		cargarEstados();
		cargarCategorias();
		cargarPerfiles2();
		cargarParametros();
	}

	public void cargarLocalidades() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_localidad dao = new dao_localidad();
		String criterio = "";
		try {
			listaLocalidad = dao.obtenerLocalidades(criterio, 1, 0, 0);
			binder.loadComponent(cmbLocalidad);
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar las localidades. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar localidad ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarUsuarios() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_usuario dao = new dao_usuario();
		String criterio = "";
		try {
			listaUsuario = dao.obtenerUsuarios(criterio, 1, 0);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los usuarios. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar usuario ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarPerfiles2() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_perfil dao = new dao_perfil();
		String criterio = "";
		try {
			listaPerfil2 = dao.obtenerPerfiles(criterio, 1, 0);
			binder.loadComponent(cmbPerfil1);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los perfiles. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarParametros() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_parametros_generales_1 dao = new dao_parametros_generales_1();
		try {
			listaParametros = dao.obtenerParametros();
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los parametros. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar parametros ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
		if (listaParametros.size() > 0) {
			if (listaParametros.get(0).getSecuencia_control_cambio() == 0) {
				intSecuencia1.setValue(0);
			} else {
				intSecuencia1.setValue(
						Integer.valueOf(String.valueOf(listaParametros.get(0).getSecuencia_control_cambio())));
			}
			if (listaParametros.get(0).getValidar_codigo_articulo() == null) {
				chkValidarCodigo1.setChecked(false);
			} else {
				if (listaParametros.get(0).getValidar_codigo_articulo().equals("S")) {
					chkValidarCodigo1.setChecked(true);
				} else {
					chkValidarCodigo1.setChecked(false);
				}
			}
			if (listaParametros.get(0).getActivar_guardar_imagen() == null) {
				chkActivarCargar.setChecked(false);
			} else {
				if (listaParametros.get(0).getActivar_guardar_imagen().equals("S")) {
					chkActivarCargar.setChecked(true);
				} else {
					chkActivarCargar.setChecked(false);
				}
			}
			if (listaParametros.get(0).getActivar_borrar_imagen() == null) {
				chkActivarBorrar.setChecked(false);
			} else {
				if (listaParametros.get(0).getActivar_borrar_imagen().equals("S")) {
					chkActivarBorrar.setChecked(true);
				} else {
					chkActivarBorrar.setChecked(false);
				}
			}
			if (listaParametros.get(0).getUrl_aplicacion_externa_1() == null) {
				txtURL1.setText("");
			} else {
				txtURL1.setText(listaParametros.get(0).getUrl_aplicacion_externa_1());
			}
			if (listaParametros.get(0).getUrl_aplicacion_externa_2() == null) {
				txtURL2.setText("");
			} else {
				txtURL2.setText(listaParametros.get(0).getUrl_aplicacion_externa_2());
			}
			if (listaParametros.get(0).getId_categoria_reporte_1() != 0) {
				for (int i = 0; i < listaCategoria.size(); i++) {
					if (listaCategoria.get(i).getId_categoria() == listaParametros.get(0).getId_categoria_reporte_1()) {
						cmbCategoria1.setText(listaCategoria.get(i).getNom_categoria());
						i = listaCategoria.size() + 1;
					}
				}
			}
			if (listaParametros.get(0).getId_categoria_reporte_2() != 0) {
				for (int i = 0; i < listaCategoria.size(); i++) {
					if (listaCategoria.get(i).getId_categoria() == listaParametros.get(0).getId_categoria_reporte_2()) {
						cmbCategoria2.setText(listaCategoria.get(i).getNom_categoria());
						i = listaCategoria.size() + 1;
					}
				}
			}
			if (listaParametros.get(0).getLocalidad_control_cambio() != 0) {
				for (int i = 0; i < listaLocalidad.size(); i++) {
					if (listaLocalidad.get(i).getId_localidad() == listaParametros.get(0)
							.getLocalidad_control_cambio()) {
						cmbLocalidad.setText(listaLocalidad.get(i).getNom_localidad());
						i = listaLocalidad.size() + 1;
					}
				}
			}
			if (listaParametros.get(0).getId_perfil_revisor_bitacora() != 0) {
				for (int i = 0; i < listaPerfil2.size(); i++) {
					if (listaPerfil2.get(i).getId_perfil() == listaParametros.get(0).getId_perfil_revisor_bitacora()) {
						cmbPerfil1.setText(listaPerfil2.get(i).getNom_perfil());
						i = listaPerfil2.size() + 1;
					}
				}
			}
			if (listaParametros.get(0).getSecuencia_bitacora() == 0) {
				intSecuencia2.setValue(0);
			} else {
				intSecuencia2.setValue(Integer.valueOf(String.valueOf(listaParametros.get(0).getSecuencia_bitacora())));
			}
			if (listaParametros.get(0).getEtiqueta_bitacora() == null) {
				txtEtiqueta1.setText("");
			} else {
				txtEtiqueta1.setText(listaParametros.get(0).getEtiqueta_bitacora());
			}
			if (listaParametros.get(0).getRedirecciona_usuario_control_cambio() == null) {
				chkRedireccion1.setChecked(false);
			} else {
				if (listaParametros.get(0).getRedirecciona_usuario_control_cambio().equals("S")) {
					chkRedireccion1.setChecked(true);
				} else {
					chkRedireccion1.setChecked(false);
				}
			}
			if (listaParametros.get(0).getRedirecciona_usuario_diners() == null) {
				chkRedireccion2.setChecked(false);
			} else {
				if (listaParametros.get(0).getRedirecciona_usuario_diners().equals("S")) {
					chkRedireccion2.setChecked(true);
				} else {
					chkRedireccion2.setChecked(false);
				}
			}
			if (listaParametros.get(0).getSecuencia_tarea_proveedor() == 0) {
				intSecuencia3.setValue(0);
			} else {
				intSecuencia3.setValue(
						Integer.valueOf(String.valueOf(listaParametros.get(0).getSecuencia_tarea_proveedor())));
			}
			if (listaParametros.get(0).getEtiqueta_tarea_proveedor() == null) {
				txtEtiqueta2.setText("");
			} else {
				txtEtiqueta2.setText(listaParametros.get(0).getEtiqueta_tarea_proveedor());
			}
			cargarEstados();
			if (listaParametros.get(0).getEstado_bitacora_1() != 0) {
				for (int i = 0; i < listaEstado.size(); i++) {
					if (listaEstado.get(i).getId_estado() == listaParametros.get(0).getEstado_bitacora_1()) {
						cmbEstado1.setText(listaEstado.get(i).getNom_estado());
						i = listaEstado.size() + 1;
					}
				}
			}
			if (listaParametros.get(0).getEstado_bitacora_2() != 0) {
				for (int i = 0; i < listaEstado.size(); i++) {
					if (listaEstado.get(i).getId_estado() == listaParametros.get(0).getEstado_bitacora_2()) {
						cmbEstado2.setText(listaEstado.get(i).getNom_estado());
						i = listaEstado.size() + 1;
					}
				}
			}
			if (listaParametros.get(0).getId_ubicacion_reporte_1() != 0) {
				for (int i = 0; i < listaUbicacion.size(); i++) {
					if (listaUbicacion.get(i).getId_tipo_ubicacion() == listaParametros.get(0)
							.getId_ubicacion_reporte_1()) {
						cmbUbicacion1.setText(listaUbicacion.get(i).getNom_tipo_ubicacion());
						i = listaUbicacion.size() + 1;
					}
				}
			}
			if (listaParametros.get(0).getId_ubicacion_reporte_2() != 0) {
				for (int i = 0; i < listaUbicacion.size(); i++) {
					if (listaUbicacion.get(i).getId_tipo_ubicacion() == listaParametros.get(0)
							.getId_ubicacion_reporte_2()) {
						cmbUbicacion2.setText(listaUbicacion.get(i).getNom_tipo_ubicacion());
						i = listaUbicacion.size() + 2;
					}
				}
			}
		}
	}

	public void cargarPerfiles1() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_perfil dao = new dao_perfil();
		String criterio = "";
		try {
			listaPerfil1 = dao.obtenerPerfiles(criterio, 4, id_perfil);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los perfiles. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarCategorias() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_categoria dao = new dao_categoria();
		String criterio = "";
		try {
			listaCategoria = dao.obtenerCategorias(criterio, String.valueOf(id_dc), 4, 0, 0);
			binder.loadComponent(cmbCategoria1);
			binder.loadComponent(cmbCategoria2);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las categorias. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar categoria ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarEstados() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_estado_bitacora dao = new dao_estado_bitacora();
		String criterio = "";
		try {
			listaEstado = dao.obtenerEstados(criterio, 1, String.valueOf(id_dc));
			binder.loadComponent(cmbEstado1);
			binder.loadComponent(cmbEstado2);
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar las empresas. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar estados ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarUbicaciones() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_tipo_ubicacion dao = new dao_tipo_ubicacion();
		String criterio = "";
		try {
			listaUbicacion = dao.obtenerTipoUbicaciones(criterio, 0, 1);
			binder.loadComponent(cmbUbicacion1);
			binder.loadComponent(cmbUbicacion2);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las ubicaciones. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar ubicacion ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void inicializarPermisos() {
		if (listaPerfil1.size() == 1) {
			if (listaPerfil1.get(0).getConsultar().equals("S")) {
				consultar = "S";
			} else {
				consultar = "N";
			}
			if (listaPerfil1.get(0).getInsertar().equals("S")) {
				insertar = "S";
			} else {
				insertar = "N";
			}
			if (listaPerfil1.get(0).getModificar().equals("S")) {
				modificar = "S";
			} else {
				modificar = "N";
			}
			if (listaPerfil1.get(0).getRelacionar().equals("S")) {
				relacionar = "S";
			} else {
				relacionar = "N";
			}
			if (listaPerfil1.get(0).getDesactivar().equals("S")) {
				desactivar = "S";
			} else {
				desactivar = "N";
			}
			if (listaPerfil1.get(0).getEliminar().equals("S")) {
				eliminar = "S";
			} else {
				eliminar = "N";
			}
			if (listaPerfil1.get(0).getSolicitar().equals("S")) {
				solicitar = "S";
			} else {
				solicitar = "N";
			}
			if (listaPerfil1.get(0).getRevisar().equals("S")) {
				revisar = "S";
			} else {
				revisar = "N";
			}
			if (listaPerfil1.get(0).getAprobar().equals("S")) {
				aprobar = "S";
			} else {
				aprobar = "N";
			}
			if (listaPerfil1.get(0).getEjecutar().equals("S")) {
				ejecutar = "S";
			} else {
				ejecutar = "N";
			}
		} else {
			Messagebox.show(
					"Existen inconsistencias con los permisos del perfil asignado al usuario. ¡Consulte al administrador del sistema!.",
					".:: Ingreso al sistema ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
	}

	public List<modelo_parametros_generales_1> obtenerParametros() {
		return listaParametros;
	}

	public List<modelo_categoria> obtenerCategorias() {
		return listaCategoria;
	}

	public List<modelo_localidad> obtenerLocalidades() {
		return listaLocalidad;
	}

	public List<modelo_estado_bitacora> obtenerEstados() {
		return listaEstado;
	}

	public List<modelo_usuario> obtenerUsuarios() {
		return listaUsuario;
	}

	public List<modelo_perfil> obtenerPerfiles1() {
		return listaPerfil1;
	}

	public List<modelo_perfil> obtenerPerfiles2() {
		return listaPerfil2;
	}

	public List<modelo_tipo_ubicacion> obtenerUbicaciones() {
		return listaUbicacion;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar() {
		Messagebox.show("Esta seguro de guardar los parametros?", ".:: Parametros generales #1 ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_parametros_generales_1 dao = new dao_parametros_generales_1();
							modelo_parametros_generales_1 parametro = new modelo_parametros_generales_1();
							if (chkValidarCodigo1.isChecked()) {
								parametro.setValidar_codigo_articulo("S");
							} else {
								parametro.setValidar_codigo_articulo("N");
							}
							if (chkActivarCargar.isChecked()) {
								parametro.setActivar_guardar_imagen("S");
							} else {
								parametro.setActivar_guardar_imagen("N");
							}
							if (chkActivarBorrar.isChecked()) {
								parametro.setActivar_borrar_imagen("S");
							} else {
								parametro.setActivar_borrar_imagen("N");
							}
							if (intSecuencia1.getValue() == null) {
								parametro.setSecuencia_control_cambio(0);
							} else {
								parametro.setSecuencia_control_cambio(intSecuencia1.getValue());
							}
							if (txtURL1.getText().length() > 0) {
								parametro.setUrl_aplicacion_externa_1(txtURL1.getText());
							}
							if (txtURL2.getText().length() > 0) {
								parametro.setUrl_aplicacion_externa_2(txtURL2.getText());
							}
							if (cmbCategoria1.getSelectedItem() != null) {
								parametro.setId_categoria_reporte_1(
										Long.parseLong(cmbCategoria1.getSelectedItem().getValue().toString()));
							} else {
								parametro.setId_categoria_reporte_1(0);
							}
							if (cmbCategoria2.getSelectedItem() != null) {
								parametro.setId_categoria_reporte_2(
										Long.parseLong(cmbCategoria2.getSelectedItem().getValue().toString()));
							} else {
								parametro.setId_categoria_reporte_2(0);
							}
							if (cmbLocalidad.getSelectedItem() != null) {
								parametro.setLocalidad_control_cambio(
										Long.parseLong(cmbLocalidad.getSelectedItem().getValue().toString()));
							} else {
								parametro.setLocalidad_control_cambio(0);
							}
							if (cmbPerfil1.getSelectedItem() != null) {
								parametro.setId_perfil_revisor_bitacora(
										Long.parseLong(cmbPerfil1.getSelectedItem().getValue().toString()));
							} else {
								parametro.setId_perfil_revisor_bitacora(0);
							}
							if (txtEtiqueta1.getText().length() > 0) {
								parametro.setEtiqueta_bitacora(txtEtiqueta1.getText());
							}
							if (intSecuencia2.getValue() == null) {
								parametro.setSecuencia_bitacora(0);
							} else {
								parametro.setSecuencia_bitacora(intSecuencia2.getValue());
							}
							if (chkRedireccion1.isChecked()) {
								parametro.setRedirecciona_usuario_control_cambio("S");
							} else {
								parametro.setRedirecciona_usuario_control_cambio("N");
							}
							if (chkRedireccion2.isChecked()) {
								parametro.setRedirecciona_usuario_diners("S");
							} else {
								parametro.setRedirecciona_usuario_diners("N");
							}
							if (txtEtiqueta2.getText().length() > 0) {
								parametro.setEtiqueta_tarea_proveedor(txtEtiqueta2.getText());
							}
							if (intSecuencia3.getValue() == null) {
								parametro.setSecuencia_tarea_proveedor(0);
							} else {
								parametro.setSecuencia_tarea_proveedor(intSecuencia3.getValue());
							}
							if (cmbEstado1.getSelectedItem() != null) {
								parametro.setEstado_bitacora_1(
										Long.parseLong(cmbEstado1.getSelectedItem().getValue().toString()));
							} else {
								parametro.setEstado_bitacora_1(0);
							}
							if (cmbEstado2.getSelectedItem() != null) {
								parametro.setEstado_bitacora_2(
										Long.parseLong(cmbEstado2.getSelectedItem().getValue().toString()));
							} else {
								parametro.setEstado_bitacora_2(0);
							}
							if (cmbUbicacion1.getSelectedItem() != null) {
								parametro.setId_ubicacion_reporte_1(
										Long.parseLong(cmbUbicacion1.getSelectedItem().getValue().toString()));
							} else {
								parametro.setId_ubicacion_reporte_1(0);
							}
							if (cmbUbicacion2.getSelectedItem() != null) {
								parametro.setId_ubicacion_reporte_2(
										Long.parseLong(cmbUbicacion2.getSelectedItem().getValue().toString()));
							} else {
								parametro.setId_ubicacion_reporte_2(0);
							}
							if (listaParametros.size() > 0) {
								parametro.setId_parametro(listaParametros.get(0).getId_parametro());
								try {
									dao.modificarParametros(parametro);
									Messagebox.show("Los parametros se guardaron correctamente.",
											".:: Parametros generales #1 ::.", Messagebox.OK, Messagebox.EXCLAMATION);

								} catch (Exception e) {
									Messagebox.show(
											"Error al guardar los parametros. \n\n" + "Mensaje de error: \n\n"
													+ e.getMessage(),
											".:: Parametros generales #1 ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								}
							} else {
								try {
									dao.insertarParametros(parametro, 1);
									Messagebox.show("Los parametros se guardaron correctamente.",
											".:: Parametros generales #1 ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								} catch (Exception e) {
									Messagebox.show(
											"Error al guardar los parametros. \n\n" + "Mensaje de error: \n\n"
													+ e.getMessage(),
											".:: Parametros generales #1 ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								}
							}
						}
					}
				});
	}

}
