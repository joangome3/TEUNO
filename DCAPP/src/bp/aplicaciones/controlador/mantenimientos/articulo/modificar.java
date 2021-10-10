package bp.aplicaciones.controlador.mantenimientos.articulo;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;
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
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Vlayout;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Window;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_articulo;
import bp.aplicaciones.mantenimientos.DAO.dao_categoria;
import bp.aplicaciones.mantenimientos.DAO.dao_estado_articulo;
import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_1;
import bp.aplicaciones.mantenimientos.DAO.dao_relacion_articulo;
import bp.aplicaciones.mantenimientos.DAO.dao_relacion_estado_articulo;
import bp.aplicaciones.mantenimientos.DAO.dao_ubicacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_categoria;
import bp.aplicaciones.mantenimientos.modelo.modelo_estado_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_articulo_ubicacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_estado_articulo_ubicacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitud;
import bp.aplicaciones.mantenimientos.modelo.modelo_ubicacion;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;

@SuppressWarnings({ "serial", "deprecation" })
public class modificar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zModificar;
	@Wire
	Button btnGrabar, btnCancelar, btnCargar, btnBorrar;
	@Wire
	Textbox txtId, txtCodigo, txtDescripcion, txtMarca, txtModelo, txtSerie, txtCodigoActivo, txtBuscarUbicacion,
			txtComentario;
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

	String comentario_1 = "";
	String comentario_2 = "";

	Date fecha_comentario_1 = null;
	Date fecha_comentario_2 = null;
	Date fecha_comentario_3 = null;

	List<modelo_localidad> listaLocalidad = new ArrayList<modelo_localidad>();
	List<modelo_categoria> listaCategoria = new ArrayList<modelo_categoria>();
	List<modelo_ubicacion> listaUbicacion = new ArrayList<modelo_ubicacion>();
	List<modelo_ubicacion> listaUbicacion1 = new ArrayList<modelo_ubicacion>();
	List<modelo_estado_articulo> listaEstados = new ArrayList<modelo_estado_articulo>();
	List<modelo_estado_articulo> listaEstados1 = new ArrayList<modelo_estado_articulo>();
	List<modelo_estado_articulo> listaEstados2 = new ArrayList<modelo_estado_articulo>();
	List<modelo_parametros_generales_1> listaParametros = new ArrayList<modelo_parametros_generales_1>();
	List<modelo_articulo> listaArticulo = new ArrayList<modelo_articulo>();

	long id = 0;
	long id_mantenimiento = 6;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	modelo_articulo articulo = (modelo_articulo) Sessions.getCurrent().getAttribute("articulo");

	validar_datos validar = new validar_datos();

	modelo_solicitud solicitud = new modelo_solicitud();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();
	Validaciones validaciones = new Validaciones();

	Informativos informativos = new Informativos();
	Error error = new Error();

	byte[] buffer = null;

	Blob img_actual = null;

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
		txtComentario.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtComentario.setText(txtComentario.getText().toUpperCase());
			}
		});
		txtBuscarUbicacion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtBuscarUbicacion.setText(txtBuscarUbicacion.getText().toUpperCase());
			}
		});
		cargarLocalidades();
		cargarCategorias();
		cargarUbicaciones("");
		cargarEstados("", 1, id_dc);
		dibujarEstados();
		cargarDatos();
		cargarImagen();
		cargarParametros();
	}

	public void dibujarEstados() {
		for (int i = 0; i < listaEstados.size(); i++) {
			Listitem lItem;
			Listcell lCell;
			Checkbox chk;
			lItem = new Listitem();
			lCell = new Listcell();
			chk = new Checkbox();
			chk.addEventListener(Events.ON_CHECK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					Listitem lI;
					Listcell lC;
					long id = 0;
					lI = (Listitem) chk.getParent().getParent();
					lC = (Listcell) lI.getChildren().get(1);
					id = Long.valueOf(lC.getLabel().toString());
					modelo_estado_articulo estado = new modelo_estado_articulo();
					if (chk.isChecked()) {
						for (int i = 0; i < listaEstados.size(); i++) {
							if (listaEstados.get(i).getId_estado() == id) {
								estado = listaEstados.get(i);
								i = listaEstados.size() + 1;
							}
						}
						if (!listaEstados1.contains(estado) && !listaEstados2.contains(estado)) {
							listaEstados1.add(estado);
						}
					} else {
						for (int i = 0; i < listaEstados.size(); i++) {
							if (listaEstados.get(i).getId_estado() == id) {
								estado = listaEstados.get(i);
								i = listaEstados.size() + 1;
							}
						}
						if (listaEstados1.contains(estado) && !listaEstados2.contains(estado)) {
							listaEstados1.remove(estado);
						}
					}
				}
			});
			lCell.appendChild(chk);
			lItem.appendChild(lCell);
			lCell = new Listcell();
			lCell.setLabel(String.valueOf(listaEstados.get(i).getId_estado()));
			lItem.appendChild(lCell);
			lCell = new Listcell();
			lCell.setLabel(listaEstados.get(i).getNom_estado());
			lCell.setStyle("text-align: left;");
			lItem.appendChild(lCell);
			lbxEstados.appendChild(lItem);
		}
	}

	public void cargarDatos() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		cargarArticulos(String.valueOf(articulo.getId_articulo()), "", "");
		if (listaArticulo.size() > 0) {
			if (listaArticulo.get(0).getImg_articulo() != null) {
				img_actual = listaArticulo.get(0).getImg_articulo();
			}
		}
		txtId.setText(String.valueOf(articulo.getId_articulo()));
		txtCodigo.setText(articulo.getCod_articulo());
		txtDescripcion.setText(articulo.getDes_articulo());
		txtCantidad.setText(String.valueOf(articulo.getSto_articulo()));
		for (int i = 0; i < listaLocalidad.size(); i++) {
			if (listaLocalidad.get(i).getId_localidad() == articulo.getId_localidad()) {
				cmbLocalidad.setText(listaLocalidad.get(i).getNom_localidad());
				i = listaLocalidad.size() + 1;
			}
		}
		for (int i = 0; i < listaCategoria.size(); i++) {
			if (listaCategoria.get(i).getId_categoria() == articulo.getId_categoria()) {
				cmbCategoria.setText(listaCategoria.get(i).getNom_categoria());
				if (listaCategoria.get(i).getMos_marca().equals("S")) {
					rwMarca.setVisible(true);
					vmarca = "S";
					txtMarca.setText(articulo.getMarca());
				} else {
					rwMarca.setVisible(false);
					vmarca = "N";
				}
				if (listaCategoria.get(i).getMos_modelo().equals("S")) {
					rwModelo.setVisible(true);
					vmodelo = "S";
					txtModelo.setText(articulo.getModelo());
				} else {
					rwModelo.setVisible(false);
					vmodelo = "N";
				}
				if (listaCategoria.get(i).getMos_serie().equals("S")) {
					rwSerie.setVisible(true);
					vserie = "S";
					txtSerie.setText(articulo.getSerie());
				} else {
					rwSerie.setVisible(false);
					vserie = "N";
				}
				if (listaCategoria.get(i).getMos_cod_activo().equals("S")) {
					rwCodigoActivo.setVisible(true);
					vcodigoactivo = "S";
					txtCodigoActivo.setText(articulo.getCodig_activo());
				} else {
					rwCodigoActivo.setVisible(false);
					vcodigoactivo = "N";
				}
				i = listaCategoria.size() + 1;
			}
		}
		dao_relacion_articulo dao1 = new dao_relacion_articulo();
		String str = "";
		List<modelo_relacion_articulo_ubicacion> listaRelacionArticulos = new ArrayList<modelo_relacion_articulo_ubicacion>();
		for (int i = 0; i < lbxUbicaciones.getItems().size(); i++) {
			listaRelacionArticulos = dao1.obtenerRelacionesArticulosUbicaciones(0, articulo.getId_articulo(),
					listaUbicacion.get(i).getId_ubicacion(), 2);
			if (listaRelacionArticulos.size() > 0) {
				if (listaRelacionArticulos.get(0).getSto_articulo() > 0) {
					lbxUbicaciones.getItemAtIndex(i).setSelected(true);
					lbxUbicaciones.getItemAtIndex(i).setDisabled(true);
				}
				if (listaRelacionArticulos.get(0).getSto_articulo() == 0) {
					lbxUbicaciones.getItemAtIndex(i).setSelected(true);
					lbxUbicaciones.getItemAtIndex(i).setDisabled(false);
				}
				if (!str.isEmpty()) {
					str += ", ";
				}
				str += listaUbicacion.get(i).getPos_ubicacion();
				listaUbicacion1.add(listaUbicacion.get(i));
			}
		}
		bdxUbicacion.setText(str);
		bdxUbicacion.setTooltiptext(str);
		dao_relacion_estado_articulo dao2 = new dao_relacion_estado_articulo();
		List<modelo_relacion_estado_articulo_ubicacion> listaRelacionEstadoArticulos = new ArrayList<modelo_relacion_estado_articulo_ubicacion>();
		List<String> lEstado = new ArrayList<String>();
		Checkbox chk1, chk2;
		for (int i = 0; i < lbxEstados.getItems().size(); i++) {
			for (int j = 0; j < listaUbicacion1.size(); j++) {
				listaRelacionArticulos = dao1.obtenerRelacionesArticulosUbicaciones(0, articulo.getId_articulo(),
						listaUbicacion1.get(j).getId_ubicacion(), 2);
				if (listaRelacionArticulos.size() > 0) {
					listaRelacionEstadoArticulos = dao2.obtenerRelacionesEstadosArticulosUbicaciones(0, 0,
							listaRelacionArticulos.get(0).getId_relacion(), 2);
				}
				for (int k = 0; k < listaRelacionEstadoArticulos.size(); k++) {
					if (listaEstados.get(i).getId_estado() == listaRelacionEstadoArticulos.get(k).getId_estado()) {
						lEstado.add(String.valueOf(listaEstados.get(i).getId_estado()));
					}
				}
			}
		}
		for (int i = 0; i < lbxEstados.getItems().size(); i++) {
			chk1 = new Checkbox();
			chk1 = (Checkbox) lbxEstados.getItemAtIndex(i).getChildren().get(0).getChildren().get(0);
			for (int j = 0; j < lEstado.size(); j++) {
				if (listaEstados.get(i).getId_estado() == Long.valueOf(lEstado.get(j))) {
					chk1.setChecked(true);
					if (!listaEstados2.contains(listaEstados.get(i))) {
						listaEstados2.add(listaEstados.get(i));
					}
				}
			}
		}
		lEstado = new ArrayList<String>();
		for (int i = 0; i < lbxEstados.getItems().size(); i++) {
			for (int j = 0; j < listaUbicacion1.size(); j++) {
				listaRelacionArticulos = dao1.obtenerRelacionesArticulosUbicaciones(0, articulo.getId_articulo(),
						listaUbicacion1.get(j).getId_ubicacion(), 2);
				if (listaRelacionArticulos.size() > 0) {
					listaRelacionEstadoArticulos = dao2.obtenerRelacionesEstadosArticulosUbicaciones(0, 0,
							listaRelacionArticulos.get(0).getId_relacion(), 2);
				}
				for (int k = 0; k < listaRelacionEstadoArticulos.size(); k++) {
					if (listaEstados.get(i).getId_estado() == listaRelacionEstadoArticulos.get(k).getId_estado()
							&& listaRelacionEstadoArticulos.get(k).getStock() > 0) {
						lEstado.add(String.valueOf(listaEstados.get(i).getId_estado()));
					}
				}
			}
		}
		for (int i = 0; i < lbxEstados.getItems().size(); i++) {
			chk2 = new Checkbox();
			chk2 = (Checkbox) lbxEstados.getItemAtIndex(i).getChildren().get(0).getChildren().get(0);
			for (int j = 0; j < lEstado.size(); j++) {
				if (listaEstados.get(i).getId_estado() == Long.valueOf(lEstado.get(j))) {
					chk2.setDisabled(true);
				}
			}
		}
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, articulo.getId_articulo(), 7);
		if (solicitud != null) {
			if (solicitud.getId_tip_solicitud() == 1 || solicitud.getId_tip_solicitud() == 2) {
				if (solicitud.getEst_solicitud().equals("T")) {
					txtCantidad.setDisabled(false);
					cmbCategoria.setDisabled(false);
					bdxUbicacion.setDisabled(false);
					txtMarca.setDisabled(false);
					txtModelo.setDisabled(false);
					txtSerie.setDisabled(false);
					txtCodigoActivo.setDisabled(false);
					txtCodigo.setReadonly(false);
					txtDescripcion.setReadonly(false);
					btnCargar.setDisabled(false);
					btnBorrar.setDisabled(false);

				} else {
					if (solicitud.getId_campo() == 19) {
						txtCodigo.setDisabled(false);
					} else {
						txtCodigo.setDisabled(true);
					}
					if (solicitud.getId_campo() == 2) {
						txtDescripcion.setDisabled(false);
					} else {
						txtDescripcion.setDisabled(true);
					}
					if (solicitud.getId_campo() == 20) {
						cmbCategoria.setDisabled(false);
					} else {
						cmbCategoria.setDisabled(true);
					}
					if (solicitud.getId_campo() == 21) {
						bdxUbicacion.setDisabled(false);
					} else {
						bdxUbicacion.setDisabled(true);
					}
					if (solicitud.getId_campo() == 22) {
						txtMarca.setDisabled(false);
					} else {
						txtMarca.setDisabled(true);
					}
					if (solicitud.getId_campo() == 23) {
						txtModelo.setDisabled(false);
					} else {
						txtModelo.setDisabled(true);
					}
					if (solicitud.getId_campo() == 24) {
						txtSerie.setDisabled(false);
					} else {
						txtSerie.setDisabled(true);
					}
					if (solicitud.getId_campo() == 25) {
						txtCodigoActivo.setDisabled(false);
					} else {
						txtCodigoActivo.setDisabled(true);
					}
					if (solicitud.getId_campo() == 26) {
						btnCargar.setDisabled(false);
						btnBorrar.setDisabled(false);
					} else {
						btnCargar.setDisabled(true);
						btnBorrar.setDisabled(true);
					}
					if (solicitud.getId_campo() != 27) {
						Checkbox chk = new Checkbox();
						for (int i = 0; i < lbxEstados.getItemCount(); i++) {
							chk = (Checkbox) lbxEstados.getItemAtIndex(i).getChildren().get(0).getChildren().get(0);
							chk.setDisabled(true);
						}
					}
				}
			}
		}
	}

	public void cargarArticulos(String criterio, String empresa, String estado)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_articulo dao = new dao_articulo();
		try {
			listaArticulo = dao.obtenerArticulos2(criterio, String.valueOf(id_dc), empresa, 13, 0);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los articulos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Ver foto ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarImagen() throws SQLException, IOException, ClassNotFoundException {
		cargarArticulos(String.valueOf(articulo.getId_articulo()), "", "");
		if (listaArticulo.size() > 0) {
			if (listaArticulo.get(0).getImg_articulo() != null) {
				InputStream in = listaArticulo.get(0).getImg_articulo().getBinaryStream();
				BufferedImage image = ImageIO.read(in);
				img1.setContent(image);
				modificar.this.articulo.setImg_articulo(listaArticulo.get(0).getImg_articulo());
			} else {
				img1.setSrc("/img/principal/unnamed.jpg");
			}
		} else {
			img1.setSrc("/img/principal/unnamed.jpg");
		}
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
		for (int i = 0; i < lbxUbicaciones.getItems().size(); i++) {
			if (!lbxUbicaciones.getItemAtIndex(i).isSelected()) {
				continue;
			}
			if (lbxUbicaciones.getItemAtIndex(i).getLabel().equals("")) {
				continue;
			}
			for (int j = 0; j < listaUbicacion.size(); j++) {
				if (listaUbicacion.get(j).getId_ubicacion() == Long
						.valueOf(lbxUbicaciones.getItemAtIndex(i).getLabel().toString())) {
					if (!str.isEmpty()) {
						str += ", ";
					}
					str += listaUbicacion.get(j).getPos_ubicacion();
					j = listaUbicacion.size() + 1;
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
				if (dao.obtenerArticulos(txtCodigo.getText(), String.valueOf(id_dc),
						String.valueOf(articulo.getId_articulo()), 10, 0).size() > 0) {
					txtCodigo.setErrorMessage("El codigo ya se encuentra registrado.");
					return;
				}
			}
		}
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
		img_actual = null;
	}

	@Listen("onClick=#btnBorrar")
	public void onClick$btnBorrar() {
		img1.setSrc("/img/principal/unnamed.jpg");
		img_actual = null;
	}

	public boolean validarSiExisteSolicitudPendienteEjecucion()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_solicitud_pendiente = false;
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, articulo.getId_articulo(), 7);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, articulo.getId_articulo(), 7);
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
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", id_mantenimiento, articulo.getId_articulo(), 7);
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
					if (txtCodigo.getText().length() <= 0) {
						txtCodigo.setErrorMessage("Ingrese el codigo.");
						return;
					}
					if (listaParametros.size() > 0) {
						if (listaParametros.get(0).getValidar_codigo_articulo().equals("S")) {
							dao_articulo dao = new dao_articulo();
							if (dao.obtenerArticulos(txtCodigo.getText(), String.valueOf(id_dc),
									String.valueOf(articulo.getId_articulo()), 10, 0).size() > 0) {
								txtCodigo.setErrorMessage("El codigo ya se encuentra registrado.");
								return;
							}
						}
					}
					if (txtDescripcion.getText().length() <= 0) {
						txtDescripcion.setErrorMessage("Ingrese la descripcion.");
						return;
					}
					if (cmbCategoria.getSelectedItem() == null) {
						cmbCategoria.setErrorMessage("Seleccione una categoria.");
						return;
					}
					if (vmarca.equals("S")) {
						if (txtMarca.getText().length() <= 0) {
							txtMarca.setErrorMessage("Ingrese la marca.");
							return;
						}
					}
					if (vmodelo.equals("S")) {
						if (txtModelo.getText().length() <= 0) {
							txtModelo.setErrorMessage("Ingrese el modelo.");
							return;
						}
					}
					if (vserie.equals("S")) {
						if (txtSerie.getText().length() <= 0) {
							txtSerie.setErrorMessage("Ingrese la serie.");
							return;
						}
					}
					if (vcodigoactivo.equals("S")) {
						if (txtCodigoActivo.getText().length() <= 0) {
							txtCodigoActivo.setErrorMessage("Ingrese el codigo de activo.");
							return;
						}
					}
					if (lbxUbicaciones.getSelectedItem() == null) {
						bdxUbicacion.setErrorMessage("Seleccione una ubicación");
						return;
					}
					if (txtCantidad.getText().length() <= 0) {
						txtCantidad.setErrorMessage("Ingrese la cantidad.");
						return;
					}
					if (cmbLocalidad.getSelectedItem() == null) {
						cmbLocalidad.setErrorMessage("Seleccione una localidad.");
						return;
					}
				} else {
					if (solicitud.getId_campo() == 19) {
						if (txtCodigo.getText().length() <= 0) {
							txtCodigo.setErrorMessage("Ingrese el codigo.");
							return;
						}
						if (listaParametros.size() > 0) {
							if (listaParametros.get(0).getValidar_codigo_articulo().equals("S")) {
								dao_articulo dao = new dao_articulo();
								if (dao.obtenerArticulos(txtCodigo.getText(), String.valueOf(id_dc),
										String.valueOf(articulo.getId_articulo()), 10, 0).size() > 0) {
									txtCodigo.setErrorMessage("El codigo ya se encuentra registrado.");
									return;
								}
							}
						}
					}
					if (solicitud.getId_campo() == 2) {
						if (txtDescripcion.getText().length() <= 0) {
							txtDescripcion.setErrorMessage("Ingrese la descripcion.");
							return;
						}
					}
					if (solicitud.getId_campo() == 20) {
						if (cmbCategoria.getSelectedItem() == null) {
							cmbCategoria.setErrorMessage("Seleccione una categoria.");
							return;
						}
					}
					if (solicitud.getId_campo() == 21) {
						if (lbxUbicaciones.getSelectedItem() == null) {
							bdxUbicacion.setErrorMessage("Seleccione una ubicación");
							return;
						}
					}
					if (solicitud.getId_campo() == 22) {
						if (vmarca.equals("S")) {
							if (txtMarca.getText().length() <= 0) {
								txtMarca.setErrorMessage("Ingrese la marca.");
								return;
							}
						}
					}
					if (solicitud.getId_campo() == 23) {
						if (vmodelo.equals("S")) {
							if (txtModelo.getText().length() <= 0) {
								txtModelo.setErrorMessage("Ingrese el modelo.");
								return;
							}
						}
					}
					if (solicitud.getId_campo() == 24) {
						if (vserie.equals("S")) {
							if (txtSerie.getText().length() <= 0) {
								txtSerie.setErrorMessage("Ingrese la serie.");
								return;
							}
						}
					}
					if (solicitud.getId_campo() == 25) {
						if (vcodigoactivo.equals("S")) {
							if (txtCodigoActivo.getText().length() <= 0) {
								txtCodigoActivo.setErrorMessage("Ingrese el codigo de activo.");
								return;
							}
						}
					}
					Checkbox chk = new Checkbox();
					int contador = 0;
					for (int i = 0; i < lbxEstados.getItemCount(); i++) {
						chk = (Checkbox) lbxEstados.getItemAtIndex(i).getChildren().get(0).getChildren().get(0);
						if (chk.isChecked()) {
							contador = contador + 1;
						}
					}
					if (contador == 0) {
						lbxEstados.setFocus(true);
						Messagebox.show("Debe seleccionar al menos un estado para el artículo.",
								".:: Nuevo artículo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
						return;
					}
				}
			}
		}
		if (txtComentario.getText().length() <= 0) {
			txtComentario.setErrorMessage("Ingrese un comentario.");
			return;
		}
		setearDatosAntesDeGuardar();
		Messagebox.show("Esta seguro de modificar el articulo?", ".:: Modificar articulo ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_articulo dao = new dao_articulo();
							modelo_articulo articulo = new modelo_articulo();
							articulo.setId_articulo(Long.parseLong(txtId.getText()));
							articulo.setId_categoria(
									Long.parseLong(cmbCategoria.getSelectedItem().getValue().toString()));
							articulo.setCod_articulo(txtCodigo.getText().toString());
							articulo.setDes_articulo(txtDescripcion.getText().toString());
							articulo.setSto_articulo(Integer.valueOf(txtCantidad.getText().toString()));
							articulo.setId_localidad(
									Long.parseLong(cmbLocalidad.getSelectedItem().getValue().toString()));
							if (vmarca.equals("S")) {
								articulo.setMarca(txtMarca.getText());
							} else {
								articulo.setMarca(modificar.this.articulo.getMarca());
							}
							if (vmodelo.equals("S")) {
								articulo.setModelo(txtModelo.getText());
							} else {
								articulo.setModelo(modificar.this.articulo.getModelo());
							}
							if (vserie.equals("S")) {
								articulo.setSerie(txtSerie.getText());
							} else {
								articulo.setSerie(modificar.this.articulo.getSerie());
							}
							if (vcodigoactivo.equals("S")) {
								articulo.setCodig_activo(txtCodigoActivo.getText());
							} else {
								articulo.setCodig_activo(modificar.this.articulo.getCodig_activo());
							}
							if (img_actual != null) {
								articulo.setImg_articulo(modificar.this.articulo.getImg_articulo());
							} else {
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
							}
							articulo.setUsu_modifica(user);
							java.util.Date date = new Date();
							Timestamp timestamp = new Timestamp(date.getTime());
							articulo.setFec_modifica(timestamp);
							dao_relacion_articulo dao1 = new dao_relacion_articulo();
							List<modelo_relacion_articulo_ubicacion> listaRelacionArticulos = new ArrayList<modelo_relacion_articulo_ubicacion>();
							List<modelo_relacion_articulo_ubicacion> listaRelacion = new ArrayList<modelo_relacion_articulo_ubicacion>();
							listaRelacionArticulos = dao1.obtenerRelacionesArticulosUbicaciones(0,
									Long.parseLong(txtId.getText()), 0, 1);
							listaUbicacion1 = new ArrayList<modelo_ubicacion>();
							List<modelo_relacion_articulo_ubicacion> listaRelacionArticulos1 = new ArrayList<modelo_relacion_articulo_ubicacion>();
							List<modelo_relacion_estado_articulo_ubicacion> listaRelacionEstadoArticulos = new ArrayList<modelo_relacion_estado_articulo_ubicacion>();
							List<modelo_relacion_estado_articulo_ubicacion> listaRelacion1 = new ArrayList<modelo_relacion_estado_articulo_ubicacion>();
							List<modelo_relacion_estado_articulo_ubicacion> listaRelacion2 = new ArrayList<modelo_relacion_estado_articulo_ubicacion>();
							for (int i = 0; i < lbxUbicaciones.getItems().size(); i++) {
								if (lbxUbicaciones.getItemAtIndex(i).isSelected()) {
									modelo_relacion_articulo_ubicacion relacion_articulo_ubicacion = new modelo_relacion_articulo_ubicacion();
									relacion_articulo_ubicacion.setId_articulo(Long.parseLong(txtId.getText()));
									final int indice = lbxUbicaciones.getItemAtIndex(i).getIndex();
									if (!listaUbicacion1.contains(listaUbicacion.get(indice))) {
										listaUbicacion1.add(listaUbicacion.get(indice));
									}
									relacion_articulo_ubicacion
											.setId_ubicacion(listaUbicacion.get(indice).getId_ubicacion());
									for (int j = 0; j < listaRelacionArticulos.size(); j++) {
										if (listaRelacionArticulos.get(j).getId_ubicacion() == listaUbicacion
												.get(indice).getId_ubicacion()) {
											relacion_articulo_ubicacion
													.setSto_articulo(listaRelacionArticulos.get(j).getSto_articulo());
											j = listaRelacionArticulos.size() + 1;
										}
									}
									relacion_articulo_ubicacion.setEst_relacion("A");
									relacion_articulo_ubicacion.setUsu_ingresa(user);
									java.util.Date date1 = new Date();
									Timestamp timestamp1 = new Timestamp(date1.getTime());
									relacion_articulo_ubicacion.setFec_ingresa(timestamp1);
									listaRelacion.add(relacion_articulo_ubicacion);
								}
							}
							modelo_relacion_estado_articulo_ubicacion relacion_estado_articulo_ubicacion = null;
							Checkbox chk = new Checkbox();
							dao_relacion_estado_articulo dao2 = new dao_relacion_estado_articulo();
							for (int i = 0; i < lbxEstados.getItems().size(); i++) {
								chk = (Checkbox) lbxEstados.getItemAtIndex(i).getChildren().get(0).getChildren().get(0);
								if (chk.isChecked()) {
									for (int j = 0; j < listaUbicacion1.size(); j++) {
										listaRelacionArticulos1 = dao1.obtenerRelacionesArticulosUbicaciones(0,
												modificar.this.articulo.getId_articulo(),
												listaUbicacion1.get(j).getId_ubicacion(), 2);
										listaRelacionEstadoArticulos = new ArrayList<modelo_relacion_estado_articulo_ubicacion>();
										if (listaRelacionArticulos1.size() > 0) {
											listaRelacionEstadoArticulos = dao2
													.obtenerRelacionesEstadosArticulosUbicaciones(0, 0,
															listaRelacionArticulos1.get(0).getId_relacion(), 2);
											/*
											 * SE PREGUNTA POR LOS ESTADOS VIEJOS Y SE ALMACENAN EN UNA LISTA DE
											 * RELACION CON SU STOCK
											 */
											for (int k = 0; k < listaRelacionEstadoArticulos.size(); k++) {
												if (listaEstados.get(i).getId_estado() == listaRelacionEstadoArticulos
														.get(k).getId_estado()) {
													relacion_estado_articulo_ubicacion = new modelo_relacion_estado_articulo_ubicacion();
													final int indice = lbxEstados.getItemAtIndex(i).getIndex();
													relacion_estado_articulo_ubicacion
															.setId_estado(listaEstados.get(indice).getId_estado());
													relacion_estado_articulo_ubicacion
															.setId_ubicacion(listaUbicacion1.get(j).getId_ubicacion());
													relacion_estado_articulo_ubicacion
															.setStock(listaRelacionEstadoArticulos.get(k).getStock());
													relacion_estado_articulo_ubicacion.setEst_relacion("A");
													relacion_estado_articulo_ubicacion.setUsu_ingresa(user);
													java.util.Date date1 = new Date();
													Timestamp timestamp1 = new Timestamp(date1.getTime());
													relacion_estado_articulo_ubicacion.setFec_ingresa(timestamp1);
													listaRelacion1.add(relacion_estado_articulo_ubicacion);
												}
											}
										} else {
											relacion_estado_articulo_ubicacion = new modelo_relacion_estado_articulo_ubicacion();
											final int indice = lbxEstados.getItemAtIndex(i).getIndex();
											relacion_estado_articulo_ubicacion
													.setId_estado(listaEstados.get(indice).getId_estado());
											relacion_estado_articulo_ubicacion
													.setId_ubicacion(listaUbicacion1.get(j).getId_ubicacion());
											relacion_estado_articulo_ubicacion.setStock(0);
											relacion_estado_articulo_ubicacion.setEst_relacion("A");
											relacion_estado_articulo_ubicacion.setUsu_ingresa(user);
											java.util.Date date1 = new Date();
											Timestamp timestamp1 = new Timestamp(date1.getTime());
											relacion_estado_articulo_ubicacion.setFec_ingresa(timestamp1);
											listaRelacion1.add(relacion_estado_articulo_ubicacion);
										}
									}
								}
							}
							for (int i = 0; i < listaEstados1.size(); i++) {
								relacion_estado_articulo_ubicacion = new modelo_relacion_estado_articulo_ubicacion();
								relacion_estado_articulo_ubicacion.setId_estado(listaEstados1.get(i).getId_estado());
								relacion_estado_articulo_ubicacion.setStock(0);
								relacion_estado_articulo_ubicacion.setEst_relacion("A");
								relacion_estado_articulo_ubicacion.setUsu_ingresa(user);
								java.util.Date date1 = new Date();
								Timestamp timestamp1 = new Timestamp(date1.getTime());
								relacion_estado_articulo_ubicacion.setFec_ingresa(timestamp1);
								listaRelacion2.add(relacion_estado_articulo_ubicacion);
							}
							if (solicitud.getEst_solicitud().equals("T")) {
								articulo.setEst_articulo("PACP");
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
								articulo.setEst_articulo("AE");
								solicitud.setEst_solicitud("E");
								solicitud.setComentario_3(txtComentario.getText());
								solicitud.setId_user_3(id_user);
								solicitud.setFecha_3(timestamp);
								solicitud.setUsu_modifica(user);
								solicitud.setFec_modifica(timestamp);
							}
							int tipo = 1;
							try {
								dao.modificarArticulo(articulo, listaRelacion, listaRelacion1, listaRelacion2,
										solicitud, tipo);
								if (tipo == 1) {
									Messagebox.show("El articulo se modificó correctamente.",
											".:: Modificar articulo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								} else {
									Messagebox.show("No se realizaron cambios en el registro.",
											".:: Modificar articulo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
								}
								limpiarCampos();
								Sessions.getCurrent().removeAttribute("articulo");
								Events.postEvent(new Event("onClose", zModificar));
							} catch (Exception e) {
								Messagebox.show(
										"Error al modificar el articulo. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Modificar articulo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
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
	}

}
