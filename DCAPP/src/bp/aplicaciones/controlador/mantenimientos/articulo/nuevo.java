package bp.aplicaciones.controlador.mantenimientos.articulo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.mantenimientos.DAO.dao_articulo;
import bp.aplicaciones.mantenimientos.DAO.dao_categoria;
import bp.aplicaciones.mantenimientos.DAO.dao_estado_articulo;
import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_1;
import bp.aplicaciones.mantenimientos.DAO.dao_ubicacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_categoria;
import bp.aplicaciones.mantenimientos.modelo.modelo_estado_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_articulo_ubicacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_estado_articulo_ubicacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_ubicacion;

@SuppressWarnings({ "serial", "deprecation" })
public class nuevo extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zNuevo;
	@Wire
	Button btnCargar, btnBorrar, btnGrabar, btnCancelar;
	@Wire
	Textbox txtId, txtCodigo, txtDescripcion, txtMarca, txtModelo, txtSerie, txtCodigoActivo, txtBuscarUbicacion;
	@Wire
	Intbox txtCantidad;
	@Wire
	Combobox cmbLocalidad, cmbCategoria;
	@Wire
	Row rwMarca, rwModelo, rwSerie, rwCodigoActivo;
	@Wire
	Listbox lbxUbicaciones, lbxEstados;
	@Wire
	Bandbox bdxUbicacion;
	@Wire
	Image img1;
	@Wire
	Vlayout vPics;

	String vmarca, vmodelo, vserie, vcodigoactivo;

	List<modelo_localidad> listaLocalidad = new ArrayList<modelo_localidad>();
	List<modelo_categoria> listaCategoria = new ArrayList<modelo_categoria>();
	List<modelo_ubicacion> listaUbicacion = new ArrayList<modelo_ubicacion>();
	List<modelo_estado_articulo> listaEstados = new ArrayList<modelo_estado_articulo>();
	List<modelo_parametros_generales_1> listaParametros = new ArrayList<modelo_parametros_generales_1>();

	long id = 0;
	long id_mantenimiento = 6;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	validar_datos validar = new validar_datos();

	byte[] buffer = null;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		lbxUbicaciones.setEmptyMessage("!No se han añadido ubicaciones¡.");
		lbxEstados.setEmptyMessage("!No se han añadido estados¡.");
		txtCodigo.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtCodigo.setText(txtCodigo.getText().toUpperCase());
			}
		});
		txtDescripcion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtDescripcion.setText(txtDescripcion.getText().toUpperCase());
			}
		});
		txtMarca.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtMarca.setText(txtMarca.getText().toUpperCase());
			}
		});
		txtModelo.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtModelo.setText(txtModelo.getText().toUpperCase());
			}
		});
		txtSerie.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtSerie.setText(txtSerie.getText().toUpperCase());
			}
		});
		txtCodigoActivo.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtCodigoActivo.setText(txtCodigoActivo.getText().toUpperCase());
			}
		});
		txtCantidad.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtCantidad.setText(validar.soloNumeros(txtCantidad.getText()));
			}
		});
		obtenerId();
		cargarLocalidades();
		cargarCategorias();
		cargarUbicaciones("");
		cargarEstados("", 1, id_dc);
		cargarParametros();
	}

	public List<modelo_localidad> obtenerLocalidades() {
		return listaLocalidad;
	}

	public List<modelo_categoria> obtenerCategorias() {
		return listaCategoria;
	}

	public List<modelo_ubicacion> obtenerUbicaciones() {
		return listaUbicacion;
	}

	public List<modelo_estado_articulo> obtenerEstados() {
		return listaEstados;
	}

	public List<modelo_parametros_generales_1> obtenerParametros() {
		return listaParametros;
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
	}

	public void cargarCategorias() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_categoria dao = new dao_categoria();
		String criterio = "";
		try {
			listaCategoria = dao.obtenerCategorias(criterio, String.valueOf(id_dc), 4, 0, 0);
			binder.loadComponent(cmbCategoria);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las categorias. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar categoria ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarUbicaciones(String criterio) throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_ubicacion dao = new dao_ubicacion();
		try {
			listaUbicacion = dao.obtenerUbicaciones(criterio, String.valueOf(id_dc), 6, 0, 0, 0, 0);
			binder.loadComponent(lbxUbicaciones);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las ubicaciones. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar ubicacion ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarEstados(String criterio, int tipo, long localidad)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_estado_articulo dao = new dao_estado_articulo();
		try {
			listaEstados = dao.obtenerEstados(criterio, tipo, String.valueOf(localidad));
			binder.loadComponent(lbxEstados);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los estados. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar estado ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void obtenerId() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_articulo dao = new dao_articulo();
		try {
			id = dao.obtenerNuevoId();
			txtId.setText(String.valueOf(id));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Messagebox.show("Error al obtener el nuevo Id. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Obtener ID ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onOK=#txtBuscarUbicacion")
	public void onOK$txtBuscarUbicacion()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		cargarUbicaciones(txtBuscarUbicacion.getText().toString());
		txtBuscarUbicacion.setTooltiptext("");
	}

	@Listen("onSelect=#lbxUbicaciones")
	public void onSelect$lbxUbicaciones()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		String str = "";

		for (Listitem li : lbxUbicaciones.getItems()) {
			if (!li.isSelected()) {
				continue;
			}
			if (li.getLabel().equals("")) {
				continue;
			}
			for (int i = 0; i < listaUbicacion.size(); i++) {
				if (listaUbicacion.get(i).getId_ubicacion() == Long.valueOf(li.getLabel().toString())) {
					if (!str.isEmpty()) {
						str += ", ";
					}
					str += listaUbicacion.get(i).getPos_ubicacion();
					i = listaUbicacion.size() + 1;
				}
			}
		}
		bdxUbicacion.setText(str);
		bdxUbicacion.setTooltiptext(str);
	}

	@Listen("onSelect=#cmbCategoria")
	public void onOK$cmbCategoria()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (listaCategoria.size() == 0) {
			return;
		}
		if (cmbCategoria.getSelectedItem() == null) {
			return;
		}
		int index = cmbCategoria.getSelectedIndex();
		if (listaCategoria.get(index).getMos_marca().equals("S")) {
			rwMarca.setVisible(true);
			vmarca = "S";
		} else {
			rwMarca.setVisible(false);
			vmarca = "N";
		}
		if (listaCategoria.get(index).getMos_modelo().equals("S")) {
			rwModelo.setVisible(true);
			vmodelo = "S";
		} else {
			rwModelo.setVisible(false);
			vmodelo = "N";
		}
		if (listaCategoria.get(index).getMos_serie().equals("S")) {
			rwSerie.setVisible(true);
			vserie = "S";
		} else {
			rwSerie.setVisible(false);
			vserie = "N";
		}
		if (listaCategoria.get(index).getMos_cod_activo().equals("S")) {
			rwCodigoActivo.setVisible(true);
			vcodigoactivo = "S";
		} else {
			rwCodigoActivo.setVisible(false);
			vcodigoactivo = "N";
		}
	}

	@Listen("onBlur=#txtCodigo")
	public void onBlur$txtCodigo()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (txtCodigo.getText().length() <= 0) {
			return;
		}
		if (listaParametros.size() > 0) {
			if (listaParametros.get(0).getValidar_codigo_articulo().equals("S")) {
				dao_articulo dao = new dao_articulo();
				if (dao.obtenerArticulos(txtCodigo.getText(), String.valueOf(id_dc), "", 3, 0).size() > 0) {
					txtCodigo.setFocus(true);
					txtCodigo.setErrorMessage("El código ya se encuentra registrado.");
					return;
				}
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (txtCodigo.getText().length() <= 0) {
			txtCodigo.setFocus(true);
			txtCodigo.setErrorMessage("Ingrese el codigo.");
			return;
		}
		if (listaParametros.size() > 0) {
			if (listaParametros.get(0).getValidar_codigo_articulo().equals("S")) {
				dao_articulo dao = new dao_articulo();
				if (dao.obtenerArticulos(txtCodigo.getText(), String.valueOf(id_dc), "", 3, 0).size() > 0) {
					txtCodigo.setFocus(true);
					txtCodigo.setErrorMessage("El código ya se encuentra registrado.");
					return;
				}
			}
		}
		if (txtDescripcion.getText().length() <= 0) {
			txtDescripcion.setFocus(true);
			txtDescripcion.setErrorMessage("Ingrese la descripción.");
			return;
		}
		if (cmbCategoria.getSelectedItem() == null) {
			cmbCategoria.setFocus(true);
			cmbCategoria.setErrorMessage("Seleccione una categoría.");
			return;
		}
		if (vmarca.equals("S")) {
			if (txtMarca.getText().length() <= 0) {
				txtMarca.setFocus(true);
				txtMarca.setErrorMessage("Ingrese la marca.");
				return;
			}
		}
		if (vmodelo.equals("S")) {
			if (txtModelo.getText().length() <= 0) {
				txtModelo.setFocus(true);
				txtModelo.setErrorMessage("Ingrese el modelo.");
				return;
			}
		}
		if (vserie.equals("S")) {
			if (txtSerie.getText().length() <= 0) {
				txtSerie.setFocus(true);
				txtSerie.setErrorMessage("Ingrese la serie.");
				return;
			}
		}
		if (vcodigoactivo.equals("S")) {
			if (txtCodigoActivo.getText().length() <= 0) {
				txtCodigoActivo.setFocus(true);
				txtCodigoActivo.setErrorMessage("Ingrese el codigo de activo fijo.");
				return;
			}
		}
		if (lbxUbicaciones.getSelectedItem() == null) {
			bdxUbicacion.setFocus(true);
			bdxUbicacion.setErrorMessage("Seleccione una ubicación");
			return;
		}
		if (lbxEstados.getSelectedItem() == null) {
			lbxEstados.setFocus(true);
			Messagebox.show("Debe seleccionar al menos un estado para el artículo.", ".:: Nuevo artículo ::.",
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (txtCantidad.getText().length() <= 0) {
			txtCantidad.setFocus(true);
			txtCantidad.setErrorMessage("Ingrese la cantidad.");
			return;
		}
		if (cmbLocalidad.getSelectedItem() == null) {
			cmbLocalidad.setFocus(true);
			cmbLocalidad.setErrorMessage("Seleccione una localidad.");
			return;
		}
		if (img1.getSrc() != null) {
			if (img1.getSrc().equals("/img/principal/unnamed.jpg")) {
				Messagebox.show("Ingrese la imagen del articulo.", ".:: Nuevo artículo ::.", Messagebox.OK,
						Messagebox.EXCLAMATION);
				return;
			}
		} else {
			if (img1.getContent() == null) {
				Messagebox.show("Ingrese la imagen del articulo.", ".:: Nuevo artículo ::.", Messagebox.OK,
						Messagebox.EXCLAMATION);
				return;
			}
		}
		Messagebox.show("Esta seguro de guardar el artículo?", ".:: Nuevo artículo ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_articulo dao = new dao_articulo();
							modelo_articulo articulo = new modelo_articulo();
							articulo.setId_categoria(
									Long.parseLong(cmbCategoria.getSelectedItem().getValue().toString()));
							articulo.setCod_articulo(txtCodigo.getText().toString());
							articulo.setDes_articulo(txtDescripcion.getText().toString());
							articulo.setId_localidad(
									Long.parseLong(cmbLocalidad.getSelectedItem().getValue().toString()));
							if (vmarca.equals("S")) {
								articulo.setMarca(txtMarca.getText());
							} else {
								articulo.setMarca("");
							}
							if (vmodelo.equals("S")) {
								articulo.setModelo(txtModelo.getText());
							} else {
								articulo.setModelo("");
							}
							if (vserie.equals("S")) {
								articulo.setSerie(txtSerie.getText());
							} else {
								articulo.setSerie("");
							}
							if (vcodigoactivo.equals("S")) {
								articulo.setCodig_activo(txtCodigoActivo.getText());
							} else {
								articulo.setCodig_activo("");
							}
							if (img1.getSrc() != null) {
								if (!img1.getSrc().equals("/img/principal/unnamed.jpg")) {
									articulo.setImg_articulo(new SerialBlob(buffer));
								} else {
									articulo.setImg_articulo(null);
								}
							} else {
								if (img1.getContent() != null) {
									articulo.setImg_articulo(new SerialBlob(buffer));
								} else {
									articulo.setImg_articulo(null);
								}
							}
							articulo.setEst_articulo("PAC");
							articulo.setUsu_ingresa(user);
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							articulo.setFec_ingresa(timestamp);
							List<modelo_relacion_articulo_ubicacion> listaRelacionArticulos = new ArrayList<modelo_relacion_articulo_ubicacion>();
							for (int i = 0; i < lbxUbicaciones.getItems().size(); i++) {
								if (lbxUbicaciones.getItemAtIndex(i).isSelected()) {
									modelo_relacion_articulo_ubicacion relacion_articulo_ubicacion = new modelo_relacion_articulo_ubicacion();
									relacion_articulo_ubicacion.setId_articulo(Long.parseLong(txtId.getText()));
									final int indice = lbxUbicaciones.getItemAtIndex(i).getIndex();
									relacion_articulo_ubicacion
											.setId_ubicacion(listaUbicacion.get(indice).getId_ubicacion());
									relacion_articulo_ubicacion.setSto_articulo(0);
									relacion_articulo_ubicacion.setEst_relacion("A");
									relacion_articulo_ubicacion.setUsu_ingresa(user);
									java.util.Date date1 = new Date();
									Timestamp timestamp1 = new Timestamp(date1.getTime());
									relacion_articulo_ubicacion.setFec_ingresa(timestamp1);
									listaRelacionArticulos.add(relacion_articulo_ubicacion);
								}
							}
							List<modelo_relacion_estado_articulo_ubicacion> listaRelacionEstadoArticulos = new ArrayList<modelo_relacion_estado_articulo_ubicacion>();
							for (int i = 0; i < lbxEstados.getItems().size(); i++) {
								if (lbxEstados.getItemAtIndex(i).isSelected()) {
									modelo_relacion_estado_articulo_ubicacion relacion_estado_articulo_ubicacion = new modelo_relacion_estado_articulo_ubicacion();
									final int indice = lbxEstados.getItemAtIndex(i).getIndex();
									relacion_estado_articulo_ubicacion
											.setId_estado(listaEstados.get(indice).getId_estado());
									relacion_estado_articulo_ubicacion.setStock(0);
									relacion_estado_articulo_ubicacion.setEst_relacion("A");
									relacion_estado_articulo_ubicacion.setUsu_ingresa(user);
									java.util.Date date1 = new Date();
									Timestamp timestamp1 = new Timestamp(date1.getTime());
									relacion_estado_articulo_ubicacion.setFec_ingresa(timestamp1);
									listaRelacionEstadoArticulos.add(relacion_estado_articulo_ubicacion);
								}
							}
							try {
								dao.insertarArticulo(articulo, listaRelacionArticulos, listaRelacionEstadoArticulos);
								Messagebox.show("El artículo se guardó correctamente.", ".:: Nuevo artículo ::.",
										Messagebox.OK, Messagebox.EXCLAMATION);
								limpiarCampos();
								obtenerId();
								cargarLocalidades();
							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar el artículo. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Nuevo artículo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});

	}

	@Listen("onUpload=#btnCargar")
	public void onUpload$btnCargar(UploadEvent event) {
		org.zkoss.util.media.Media media = event.getMedia();
		if (media == null) {
			Messagebox.show("No se añadio alguna imagen.", ".:: Cargar imagen ::.", Messagebox.OK,
					Messagebox.INFORMATION);
			return;
		}
		if ((media.getName().indexOf(".jpg") == -1) && (media.getName().indexOf(".jpeg") == -1)
				&& (media.getName().indexOf(".png") == -1) && (media.getName().indexOf(".JPG") == -1)
				&& (media.getName().indexOf(".PNG") == -1) && (media.getName().indexOf(".JPEG") == -1)) {
			Messagebox.show("La imagen debe tener el formato (.jpg), (.jpeg), (.png).", ".:: Cargar Imagen ::.",
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		img1.setContent((org.zkoss.image.Image) media);
		buffer = media.getByteData();
	}

	@Listen("onClick=#btnBorrar")
	public void onClick$btnBorrar() {
		img1.setSrc("/img/principal/unnamed.jpg");
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zNuevo));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtCodigo.setText("");
		txtDescripcion.setText("");
		txtCantidad.setText("0");
		cmbLocalidad.setText("");
		lbxUbicaciones.clearSelection();
		lbxEstados.clearSelection();
		bdxUbicacion.setText("");
		bdxUbicacion.setTooltiptext("");
		txtBuscarUbicacion.setText("");
		cmbCategoria.setText("");
		txtMarca.setText("");
		txtModelo.setText("");
		txtSerie.setText("");
		txtCodigoActivo.setText("");
		rwMarca.setVisible(false);
		rwModelo.setVisible(false);
		rwSerie.setVisible(false);
		rwCodigoActivo.setVisible(false);
		img1.setSrc("/img/principal/unnamed.jpg");
		buffer = null;
	}

}
