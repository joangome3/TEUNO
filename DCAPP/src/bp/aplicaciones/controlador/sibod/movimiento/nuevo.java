package bp.aplicaciones.controlador.sibod.movimiento;

import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

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
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import bp.aplicaciones.bitacora.modelo.modelo_bitacora;
import bp.aplicaciones.bitacora.modelo.modelo_registra_turno;
import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_empresa;
import bp.aplicaciones.mantenimientos.DAO.dao_estado_articulo;
import bp.aplicaciones.mantenimientos.DAO.dao_parametros_generales_7;
import bp.aplicaciones.mantenimientos.DAO.dao_solicitante;
import bp.aplicaciones.mantenimientos.DAO.dao_ubicacion;
import bp.aplicaciones.mantenimientos.DAO.dao_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_estado_articulo;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_7;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitante;
import bp.aplicaciones.mantenimientos.modelo.modelo_turno;
import bp.aplicaciones.mantenimientos.modelo.modelo_ubicacion;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;
import bp.aplicaciones.sibod.DAO.dao_movimiento;
import bp.aplicaciones.sibod.modelo.modelo_movimiento;
import bp.aplicaciones.mantenimientos.modelo.modelo_articulo;

@SuppressWarnings({ "serial", "deprecation" })
public class nuevo extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zNuevo;
	@Wire
	Button btnGrabar, btnCancelar;
	@Wire
	Button btnAnadir, btnEliminar, btnCargar, btnBorrar;
	@Wire
	Textbox txtTicket, txtOperador, txtBuscarProveedor, txtBuscarArticulo, txtDocumento, txtNombre, txtCodigo,
			txtDescripcion, txtStock, txtUbicacion;
	@Wire
	Combobox cmbEmpresa, cmbTurno, cmbEstado;
	@Wire
	Datebox dtxFecha;
	@Wire
	Bandbox bdxArticulos, bdxSolicitantes;
	@Wire
	Listbox lbxMovimientos, lbxArticulos, lbxSolicitantes;
	@Wire
	Image img1;
	@Wire
	Checkbox chkTicket;

	List<modelo_parametros_generales_1> listaParametros1 = new ArrayList<modelo_parametros_generales_1>();

	List<modelo_solicitante> listaSolicitante = new ArrayList<modelo_solicitante>();
	List<modelo_empresa> listaEmpresa = new ArrayList<modelo_empresa>();
	List<modelo_articulo> listaArticulo = new ArrayList<modelo_articulo>();
	List<modelo_estado_articulo> listaEstados = new ArrayList<modelo_estado_articulo>();
	List<modelo_parametros_generales_7> listaParametros7 = new ArrayList<modelo_parametros_generales_7>();
	List<modelo_registra_turno> listaRegistroTurno = new ArrayList<modelo_registra_turno>();
	List<modelo_turno> listaTurnos1 = new ArrayList<modelo_turno>();
	List<modelo_turno> listaTurnos2 = new ArrayList<modelo_turno>();
	List<modelo_turno> listaTurno = new ArrayList<modelo_turno>();

	long id = 0;
	long id_turno = 0;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	validar_datos validar = new validar_datos();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();
	Validaciones validaciones = new Validaciones();

	modelo_bitacora bitacora = new modelo_bitacora();

	Informativos informativos = new Informativos();
	Error error = new Error();

	byte[] buffer = null;

	Date fecha_actual = null;
	Date fecha_inicio = null;
	Date fecha_fin = null;

	Date fecha_ingresa_formulario = null;

	Date fecha_inicio_turno_extendido = null;
	Date fecha_fin_turno_extendido = null;

	boolean es_turno_extendido = false;

	String turno = "";

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		txtOperador.setText(nom_ape_user);
		lbxArticulos.setEmptyMessage("!No existen datos que mostrar¡.");
		lbxSolicitantes.setEmptyMessage("!No existen datos que mostrar¡.");
		lbxMovimientos.setEmptyMessage("!No se han añadido articulos¡.");
		txtOperador.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtOperador.setText(validar.soloLetrasyNumeros(txtOperador.getText()));
			}
		});
		txtTicket.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtTicket.setText(txtTicket.getText().trim().toUpperCase());
			}
		});
		obtenerId();
		cargarFecha();
		cargarSolicitantes("");
		cargarEmpresas();
		setearFechaActual();
		setearFechaIngresaFormulario();
		inicializarListas();
		validarTurno();
	}

	public void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaParametros1 = consultasABaseDeDatos.cargarParametros1();
		listaTurno = consultasABaseDeDatos.cargarTurnos("A");
	}

	public void cargarFecha() {
		Date fechaActual = new Date();
		dtxFecha.setValue(fechaActual);
	}

	public void setearFechaActual() {
		Date d = null;
		d = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 0, 0, 0);
		fecha_actual = d;
	}

	public void setearFechaIngresaFormulario() {
		Date d = null;
		d = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), new Date().getHours(),
				new Date().getMinutes(), 0);
		fecha_ingresa_formulario = d;
	}

	public List<modelo_solicitante> obtenerSolicitantes() {
		return listaSolicitante;
	}

	public List<modelo_empresa> obtenerEmpresas() {
		return listaEmpresa;
	}

	public List<modelo_articulo> obtenerArticulos() {
		return listaArticulo;
	}

	public List<modelo_estado_articulo> obtenerEstados() {
		return listaEstados;
	}

	public List<modelo_parametros_generales_7> obtenerParametros7() {
		return listaParametros7;
	}

	public void validarTurno() {
		Date d1 = null;
		Date d2 = null;
		Date d3 = null;
		Date d4 = null;
		Date d5 = null;
		Date d6 = null;
		d1 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), new Date().getHours(),
				new Date().getMinutes(), new Date().getSeconds());
		for (int i = 0; i < listaTurno.size(); i++) {
			if (listaTurno.get(i).getEs_extendido().equals("N")) {
				d2 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(),
						listaTurno.get(i).getHora_inicio().getHours(), listaTurno.get(i).getHora_inicio().getMinutes(),
						listaTurno.get(i).getHora_inicio().getSeconds());
				fecha_inicio = d2;
				d3 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(),
						listaTurno.get(i).getHora_fin().getHours(), listaTurno.get(i).getHora_fin().getMinutes(),
						listaTurno.get(i).getHora_fin().getSeconds());
				fecha_fin = d3;
				if (d1.before(d3) && d1.after(d2)) {
					cmbTurno.setText(listaTurno.get(i).getNom_turno());
					id_turno = listaTurno.get(i).getId_turno();
					turno = listaTurno.get(i).getNom_turno();
					i = listaTurno.size() + 1;
				}
			} else {
				es_turno_extendido = true;
				d2 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(),
						listaTurno.get(i).getHora_inicio().getHours(), listaTurno.get(i).getHora_inicio().getMinutes(),
						listaTurno.get(i).getHora_inicio().getSeconds());
				fecha_inicio = d2;
				d3 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 23, 59, 59);
				fecha_fin_turno_extendido = d3;
				d4 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 0, 0, 0);
				fecha_inicio_turno_extendido = d4;
				d5 = fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(),
						listaTurno.get(i).getHora_fin().getHours(), listaTurno.get(i).getHora_fin().getMinutes(),
						listaTurno.get(i).getHora_fin().getSeconds());
				Date someDate = new Date();
				Date newDate = new Date(someDate.getTime() + TimeUnit.DAYS.toMillis(1));
				d6 = fechas.obtenerFechaArmada(newDate, newDate.getMonth(), newDate.getDate(),
						listaTurno.get(i).getHora_fin().getHours(), listaTurno.get(i).getHora_fin().getMinutes(),
						listaTurno.get(i).getHora_fin().getSeconds());
				fecha_fin = d6;
				// Si es antes de las 23:59
				if (d2.before(d1) && d1.before(d3)) {
				}
				// Si es despues de las 00:00
				if (d4.before(d1) && d1.before(d5)) {
				}
				if ((d2.before(d1) && d1.before(d3)) || (d4.before(d1) && d1.before(d5))) {
					cmbTurno.setText(listaTurno.get(i).getNom_turno());
					id_turno = listaTurno.get(i).getId_turno();
					turno = listaTurno.get(i).getNom_turno();
					i = listaTurno.size() + 1;
				}
			}
		}
	}

	public void cargarSolicitantes(String criterio) throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_solicitante dao = new dao_solicitante();
		try {
			listaSolicitante = dao.obtenerSolicitantes(criterio, 8, String.valueOf(id_dc), "1", 0);
			binder.loadComponent(lbxSolicitantes);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los solicitantes. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar solicitante ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarEmpresas() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_empresa dao = new dao_empresa();
		String criterio = "";
		try {
			listaEmpresa = dao.obtenerEmpresas(criterio, 2, String.valueOf(id_dc), "1", 0);
			binder.loadComponent(cmbEmpresa);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los clientes. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar clientes ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarArticulos(String criterio, String empresa, String estado)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_articulo dao = new dao_articulo();
		try {
			listaArticulo = dao.obtenerArticulos(criterio, String.valueOf(id_dc), empresa, 12, Integer.valueOf(estado));
			binder.loadComponent(lbxArticulos);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar las articulos. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar articulo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void obtenerId() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_movimiento dao = new dao_movimiento();
		try {
			id = dao.obtenerNuevoId();
			txtTicket.setText("TM-000" + String.valueOf(id));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Messagebox.show("Error al obtener el nuevo Id. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Obtener ID ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarEstados(String criterio, int tipo, long localidad)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_estado_articulo dao = new dao_estado_articulo();
		try {
			listaEstados = dao.obtenerEstados(criterio, tipo, String.valueOf(localidad));
			binder.loadComponent(cmbEstado);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los estados. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar estado ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarParametros7(long id_criticidad)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_parametros_generales_7 dao = new dao_parametros_generales_7();
		try {
			listaParametros7 = dao.obtenerRelacionesEstados(String.valueOf(id_criticidad), "", id_dc, 3);
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los parametros. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar parametros ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onClick=#chkTicket")
	public void onClick$chkTicket() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (chkTicket.isChecked()) {
			txtTicket.setReadonly(true);
			obtenerId();
		} else {
			txtTicket.setReadonly(false);
			txtTicket.setText("");
		}
	}

	@Listen("onSelect=#cmbEmpresa")
	public void onSelect$cmbEmpresa()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbEmpresa.getSelectedItem() == null) {
			bdxArticulos.setText("");
			bdxArticulos.setDisabled(true);
			btnAnadir.setDisabled(true);
			return;
		}
		bdxArticulos.setText("");
		bdxArticulos.setTooltiptext("");
		txtBuscarArticulo.setText("");
		bdxArticulos.setDisabled(false);
		btnAnadir.setDisabled(false);
		btnEliminar.setDisabled(false);
		lbxArticulos.clearSelection();
		txtCodigo.setText("");
		txtDescripcion.setText("");
		txtStock.setText("");
		txtUbicacion.setText("");
		img1.setSrc("/img/principal/unnamed.jpg");
		cargarEstados("", 1, id_dc);
		cmbEstado.setText("");
		listaArticulo = new ArrayList<modelo_articulo>();
		binder.loadComponent(lbxArticulos);
	}

	@Listen("onSelect=#cmbEstado")
	public void onSelect$cmbEstado()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbEstado.getSelectedItem() == null) {
			return;
		}
		bdxArticulos.setText("");
		bdxArticulos.setTooltiptext("");
		txtBuscarArticulo.setText("");
		bdxArticulos.setDisabled(false);
		lbxArticulos.clearSelection();
		txtCodigo.setText("");
		txtDescripcion.setText("");
		txtStock.setText("");
		txtUbicacion.setText("");
		img1.setSrc("/img/principal/unnamed.jpg");
		cargarArticulos("", cmbEmpresa.getSelectedItem().getValue().toString(),
				cmbEstado.getSelectedItem().getValue().toString());
	}

	@Listen("onOK=#txtBuscarArticulo")
	public void onOK$txtBuscarArticulo()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbEstado.getSelectedItem() == null) {
			cmbEstado.setErrorMessage("Debe seleccionar un estado antes de realizar una consulta.");
			cmbEstado.setFocus(true);
			return;
		}
		cargarArticulos(txtBuscarArticulo.getText().toString(), cmbEmpresa.getSelectedItem().getValue().toString(),
				cmbEstado.getSelectedItem().getValue().toString());
		txtBuscarArticulo.setTooltiptext("");
	}

	@Listen("onOK=#txtBuscarProveedor")
	public void onOK$txtBuscarProveedor()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		cargarSolicitantes(txtBuscarProveedor.getText().toString());
		txtBuscarProveedor.setTooltiptext("");
	}

	@Listen("onSelect=#lbxArticulos")
	public void onSelect$lbxArticulos()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (lbxArticulos.getSelectedItem() == null) {
			return;
		}
		int indice = lbxArticulos.getSelectedIndex();
		if (listaArticulo.get(indice).getEst_articulo().equals("I")) {
			bdxArticulos.setText("");
			Messagebox.show("El articulo se encuentra inactivo, debe estar activo para poder añadirlo.",
					".:: Añadir articulo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		bdxArticulos.setText(
				listaArticulo.get(indice).getCod_articulo() + " - " + listaArticulo.get(indice).getDes_articulo());
		bdxArticulos.setTooltiptext(
				listaArticulo.get(indice).getCod_articulo() + " - " + listaArticulo.get(indice).getDes_articulo());
		txtCodigo.setText(listaArticulo.get(indice).getCod_articulo());
		txtDescripcion.setText(listaArticulo.get(indice).getDes_articulo());
		txtStock.setText(String.valueOf(listaArticulo.get(indice).getSto_articulo()));
		txtUbicacion.setText(listaArticulo.get(indice).getNom_ubicacion());
		if (listaArticulo.get(indice).getImg_articulo() != null) {
			InputStream in = listaArticulo.get(indice).getImg_articulo().getBinaryStream();
			BufferedImage image = ImageIO.read(in);
			img1.setContent(image);
		} else {
			img1.setSrc("/img/principal/noimage.png");
		}
		btnCargar.setDisabled(false);
		btnBorrar.setDisabled(false);
	}

	@Listen("onSelect=#lbxSolicitantes")
	public void onSelect$lbxSolicitantes()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxSolicitantes.getSelectedItem() == null) {
			return;
		}
		int indice = lbxSolicitantes.getSelectedIndex();
		if (listaSolicitante.get(indice).getEst_solicitante().charAt(0) == 'I') {
			txtBuscarProveedor.setText("");
			cargarSolicitantes(txtBuscarProveedor.getText().toString());
			bdxSolicitantes.setText("");
			txtBuscarProveedor.setTooltiptext("");
			Messagebox.show("El solicitante se encuentra inactivo, debe estar activo para poder seleccionarlo.",
					".:: Añadir solicitante ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (listaSolicitante.get(indice).getEst_solicitante().charAt(0) == 'P') {
			txtBuscarProveedor.setText("");
			cargarSolicitantes(txtBuscarProveedor.getText().toString());
			bdxSolicitantes.setText("");
			txtBuscarProveedor.setTooltiptext("");
			Messagebox.show(
					"El solicitante se encuentra pendiente de que se apruebe su creación, debe estar aprobado y activo para poder seleccionarlo.",
					".:: Añadir solicitante ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		bdxSolicitantes.setText(listaSolicitante.get(indice).getNom_solicitante() + " "
				+ listaSolicitante.get(indice).getApe_solicitante());
		bdxSolicitantes.setTooltiptext(listaSolicitante.get(indice).getNom_solicitante() + " "
				+ listaSolicitante.get(indice).getApe_solicitante());
		txtDocumento.setText(listaSolicitante.get(indice).getNum_documento());
		txtDocumento.setTooltiptext(listaSolicitante.get(indice).getNum_documento());
		txtNombre.setText(listaSolicitante.get(indice).getNom_solicitante() + " "
				+ listaSolicitante.get(indice).getApe_solicitante());
		txtNombre.setTooltiptext(listaSolicitante.get(indice).getNom_solicitante() + " "
				+ listaSolicitante.get(indice).getApe_solicitante());
	}

	@Listen("onClick=#btnEliminar")
	public void onClick$btnEliminar() {
		if (lbxMovimientos.getSelectedItem() == null) {
			Messagebox.show("Seleccione el artículo que desea eliminar de la lista.",
					".:: Eliminar artículo de la lista ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		int indice = 0;
		indice = lbxMovimientos.getSelectedIndex();
		lbxMovimientos.removeItemAt(indice);
	}

	@Listen("onUpload=#btnCargar")
	public void onUpload$btnCargar(UploadEvent event)
			throws SerialException, SQLException, ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxArticulos.getSelectedItem() == null) {
			return;
		}
		int indice = lbxArticulos.getSelectedIndex();
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
		java.util.Date date1 = null;
		Timestamp timestamp1 = null;
		date1 = new Date();
		timestamp1 = new Timestamp(date1.getTime());
		modelo_articulo articulo = new modelo_articulo();
		articulo.setId_articulo(listaArticulo.get(indice).getId_articulo());
		if (img1.getSrc() != "/img/principal/unnamed.jpg") {
			articulo.setImg_articulo(new SerialBlob(buffer));
		}
		articulo.setUsu_modifica(user);
		articulo.setFec_modifica(timestamp1);
		dao_articulo dao = new dao_articulo();
		try {
			dao.actualizarImagenArticulo(articulo);
			Messagebox.show("La imagen se cargó correctamente.", ".:: Cargar imagen ::.", Messagebox.OK,
					Messagebox.INFORMATION);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Messagebox.show("La imagen no se cargó correctamente.", ".:: Cargar imagen ::.", Messagebox.OK,
					Messagebox.INFORMATION);
		}
		cargarArticulos("", cmbEmpresa.getSelectedItem().getValue().toString(),
				cmbEstado.getSelectedItem().getValue().toString());
		lbxArticulos.setSelectedIndex(indice);
	}

	@Listen("onClick=#btnBorrar")
	public void onClick$btnBorrar() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxArticulos.getSelectedItem() == null) {
			return;
		}
		int indice = lbxArticulos.getSelectedIndex();
		if (img1.getSrc() != "/img/principal/unnamed.jpg") {
			img1.setSrc("/img/principal/unnamed.jpg");
		}
		java.util.Date date1 = null;
		Timestamp timestamp1 = null;
		date1 = new Date();
		timestamp1 = new Timestamp(date1.getTime());
		modelo_articulo articulo = new modelo_articulo();
		articulo.setId_articulo(listaArticulo.get(indice).getId_articulo());
		articulo.setImg_articulo(null);
		articulo.setUsu_modifica(user);
		articulo.setFec_modifica(timestamp1);
		dao_articulo dao = new dao_articulo();
		try {
			dao.actualizarImagenArticulo(articulo);
			Messagebox.show("La imagen se borró correctamente.", ".:: Cargar imagen ::.", Messagebox.OK,
					Messagebox.INFORMATION);
		} catch (ClassNotFoundException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Messagebox.show("La imagen no se borró correctamente.", ".:: Cargar imagen ::.", Messagebox.OK,
					Messagebox.INFORMATION);
		}
		cargarArticulos("", cmbEmpresa.getSelectedItem().getValue().toString(),
				cmbEstado.getSelectedItem().getValue().toString());
		lbxArticulos.setSelectedIndex(indice);
	}

	@Listen("onDoubleClick=#lbxArticulos")
	public void onDoubleClick$lbxArticulos()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		onClick$btnAnadir();
	}

//	@Listen("onDoubleClick=#lbxMovimientos")
//	public void onDoubleClick$lbxMovimientos()
//			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
//		onClick$btnEliminar();
//	}

	@Listen("onClick=#btnAnadir")
	public void onClick$btnAnadir()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxArticulos.getSelectedItem() == null) {
			return;
		}
		int indice = lbxArticulos.getSelectedIndex();
		if (listaArticulo.get(indice).getEst_articulo().charAt(0) == 'I') {
			txtBuscarArticulo.setText("");
			cargarArticulos(txtBuscarArticulo.getText().toString(), cmbEmpresa.getSelectedItem().getValue().toString(),
					cmbEstado.getSelectedItem().getValue().toString());
			bdxArticulos.setText("");
			bdxArticulos.setTooltiptext("");
			txtBuscarArticulo.setText("");
			lbxArticulos.clearSelection();
			txtCodigo.setText("");
			txtDescripcion.setText("");
			txtStock.setText("");
			txtUbicacion.setText("");
			img1.setSrc("/img/principal/unnamed.jpg");
			Messagebox.show("El articulo se encuentra inactivo, debe estar activo para poder añadirlo.",
					".:: Añadir articulo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (listaArticulo.get(indice).getEst_articulo().charAt(0) == 'P') {
			txtBuscarArticulo.setText("");
			cargarArticulos(txtBuscarArticulo.getText().toString(), cmbEmpresa.getSelectedItem().getValue().toString(),
					cmbEstado.getSelectedItem().getValue().toString());
			bdxArticulos.setText("");
			bdxArticulos.setTooltiptext("");
			txtBuscarArticulo.setText("");
			lbxArticulos.clearSelection();
			txtCodigo.setText("");
			txtDescripcion.setText("");
			txtStock.setText("");
			txtUbicacion.setText("");
			img1.setSrc("/img/principal/unnamed.jpg");
			Messagebox.show(
					"El articulo se encuentra pendiente de que se apruebe su creación, debe estar aprobado y activo para poder añadirlo.",
					".:: Añadir articulo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (validarItemEnLista(listaArticulo.get(indice).getId_articulo(), listaArticulo.get(indice).getNom_ubicacion(),
				cmbEstado.getSelectedItem().getValue().toString()) == true) {
			Messagebox.show("El articulo ya se encuentra en la lista.", ".:: Añadir articulo ::.", Messagebox.OK,
					Messagebox.EXCLAMATION);
			return;
		}
		Listitem lItem;
		Listcell lCell;
		Combobox cmbMovimiento;
		Comboitem cItem;
		Textbox txtObservacion;
		Intbox intCantidad;
		Button btnEliminar;
		lItem = new Listitem();
		/* ID */
		lCell = new Listcell();
		lCell.setLabel(String.valueOf(listaArticulo.get(indice).getId_articulo()));
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* CODIGO */
		lCell = new Listcell();
		lCell.setLabel(listaArticulo.get(indice).getCod_articulo());
		lItem.appendChild(lCell);
		/* DESCRIPCION */
		lCell = new Listcell();
		lCell.setLabel(listaArticulo.get(indice).getDes_articulo());
		lItem.appendChild(lCell);
		/* UBICACION */
		lCell = new Listcell();
		lCell.setLabel(listaArticulo.get(indice).getNom_ubicacion());
		lItem.appendChild(lCell);
		/* MOVIMIENTO */
		lCell = new Listcell();
		cmbMovimiento = new Combobox();
		cItem = new Comboitem();
		cItem.setLabel("INGRESO");
		cItem.setValue("I");
		cmbMovimiento.appendChild(cItem);
		cItem = new Comboitem();
		cItem.setLabel("EGRESO");
		cItem.setValue("E");
		cmbMovimiento.appendChild(cItem);
		cmbMovimiento.setReadonly(true);
		cmbMovimiento.setWidth("140px");
		cmbMovimiento.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				Listitem lItem;
				Listcell lCell;
				Intbox intCantidad;
				int stock;
				lItem = (Listitem) cmbMovimiento.getParent().getParent();
				lCell = (Listcell) lItem.getChildren().get(5);
				stock = Integer.valueOf(lCell.getLabel().toString());
				lCell = (Listcell) lItem.getChildren().get(8);
				intCantidad = (Intbox) lCell.getChildren().get(0);
				if (cmbMovimiento.getSelectedItem() == null) {
					return;
				}
				if (cmbMovimiento.getSelectedItem().getValue().equals("E")) {
					if (intCantidad.getText().length() <= 0) {
						return;
					}
					if (stock < intCantidad.getValue()) {
						intCantidad.setErrorMessage("No hay suficiente stock para este articulo.");
					}
				}

			}
		});
		lCell.appendChild(cmbMovimiento);
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* ID ESTADO */
		lCell = new Listcell();
		lCell.setLabel(cmbEstado.getSelectedItem().getValue().toString());
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* ESTADO */
		lCell = new Listcell();
		lCell.setLabel(cmbEstado.getSelectedItem().getLabel());
		long id_estado = 0;
		id_estado = Long.valueOf(cmbEstado.getSelectedItem().getValue().toString());
		String estilo = "";
		cargarParametros7(id_estado);
		if (listaParametros7.size() > 0) {
			estilo = "font-weight: bold !important; font-style: italic !important; background-color: "
					+ listaParametros7.get(0).getColor() + " !important; text-align: center !important;";
			lCell.setStyle(estilo);
		} else {
			lCell.setStyle(
					"font-weight: bold !important; font-style: italic !important; text-align: center !important;");
		}
		lItem.appendChild(lCell);
		/* STOCK */
		lCell = new Listcell();
		lCell.setLabel(String.valueOf(listaArticulo.get(indice).getSto_articulo()));
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* CANTIDAD */
		lCell = new Listcell();
		intCantidad = new Intbox();
		intCantidad.setConstraint("no negative, no zero");
		intCantidad.setWidth("50px");
		intCantidad.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				Listitem lItem;
				Listcell lCell;
				Combobox cmbMovimiento;
				int stock;
				lItem = (Listitem) intCantidad.getParent().getParent();
				lCell = (Listcell) lItem.getChildren().get(7);
				stock = Integer.valueOf(lCell.getLabel().toString());
				lCell = (Listcell) lItem.getChildren().get(4);
				cmbMovimiento = (Combobox) lCell.getChildren().get(0);
				if (cmbMovimiento.getSelectedItem() == null) {
					return;
				}
				if (cmbMovimiento.getSelectedItem().getValue().equals("E")) {
					if (intCantidad.getText().length() <= 0) {
						return;
					}
					if (stock < intCantidad.getValue()) {
						intCantidad.setErrorMessage("No hay suficiente stock para este articulo.");
					}
				}

			}
		});
		lCell.appendChild(intCantidad);
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* OBSERVACIONES */
		lCell = new Listcell();
		txtObservacion = new Textbox();
		txtObservacion.setRows(5);
		txtObservacion.setWidth("170px");
		txtObservacion.setMaxlength(500);
		txtObservacion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtObservacion.setText((txtObservacion.getText().toUpperCase()));
			}
		});
		txtObservacion.setStyle("resize: none;");
		lCell.appendChild(txtObservacion);
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* ACCION */
		lCell = new Listcell();
		btnEliminar = new Button();
		btnEliminar.setImage("/img/botones/ButtonClose.png");
		btnEliminar.setTooltiptext("Eliminar");
		btnEliminar.setAutodisable("self");
		btnEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				Listitem lItem;
				lItem = (Listitem) btnEliminar.getParent().getParent();
				lbxMovimientos.removeItemAt(lItem.getIndex());
			}
		});
		lCell.appendChild(btnEliminar);
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* UBICACION */
		lCell = new Listcell();
		lCell.setLabel(String.valueOf(listaArticulo.get(indice).getId_ubicacion()));
		lCell.setVisible(false);
		lItem.appendChild(lCell);
		/* ANADIR ITEM A LISTBOX */
		lbxMovimientos.appendChild(lItem);
		/* LIMPIAR CAMPOS */
		lbxArticulos.clearSelection();
		bdxArticulos.setText("");
		txtBuscarArticulo.setText("");
		bdxArticulos.setTooltiptext("");
		txtCodigo.setText("");
		txtDescripcion.setText("");
		txtStock.setText("");
		txtUbicacion.setText("");
		img1.setSrc("/img/principal/unnamed.jpg");
		btnCargar.setDisabled(true);
		btnBorrar.setDisabled(true);
	}

	public boolean validarItemEnLista(long id_articulo, String ubicacion, String estado) {
		boolean existe = false;
		Listitem lItem;
		Listcell lCell;
		long id;
		String ubi;
		String est;
		for (int i = 0; i < lbxMovimientos.getItems().size(); i++) {
			lItem = lbxMovimientos.getItemAtIndex(i);
			lCell = (Listcell) lItem.getChildren().get(0);
			id = Long.valueOf(lCell.getLabel().toString());
			lCell = (Listcell) lItem.getChildren().get(3);
			ubi = lCell.getLabel().toString();
			lCell = (Listcell) lItem.getChildren().get(5);
			est = lCell.getLabel().toString();
			if (id_articulo == id && ubi.equals(ubicacion) && est.equals(estado)) {
				existe = true;
				i = lbxMovimientos.getItems().size() + 1;
			}
		}
		return existe;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (validarSiSeIniciaTurno() == false) {
			Messagebox.show(informativos.getMensaje_informativo_33(), informativos.getMensaje_informativo_17(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (validarSiExistenTareasVencidas() == true) {
			Messagebox.show(informativos.getMensaje_informativo_38(), informativos.getMensaje_informativo_17(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (txtTicket.getText().trim().length() <= 0) {
			txtTicket.setErrorMessage("Ingrese el ticket.");
			return;
		}
		if (!chkTicket.isChecked()) {
			if (validarSiExistePrimeroApertura(txtTicket.getText().trim(), 1) == false) {
				Messagebox.show(informativos.getMensaje_informativo_96().replace("?1", txtTicket.getText().trim()),
						informativos.getMensaje_informativo_17(), Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
		}
		if (dtxFecha.getValue() == null) {
			Messagebox.show("seleccione una fecha.", ".:: Guardar articulo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
			dtxFecha.setErrorMessage("seleccione una fecha.");
			dtxFecha.setFocus(true);
			return;
		}
		if (dtxFecha.getValue().compareTo(new Date()) > 0) {
			Messagebox.show("La fecha seleccionada debe ser menor a la fecha actual.", ".:: Guardar articulo ::.",
					Messagebox.OK, Messagebox.EXCLAMATION);
			dtxFecha.setErrorMessage("La fecha seleccionada debe ser menor a la fecha actual.");
			dtxFecha.setFocus(true);
			return;
		}
		if (lbxSolicitantes.getSelectedItem() == null) {
			Messagebox.show("Seleccione un proveedor.", ".:: Guardar articulo ::.", Messagebox.OK,
					Messagebox.EXCLAMATION);
			bdxSolicitantes.setErrorMessage("Seleccione un proveedor.");
			bdxSolicitantes.setFocus(true);
			return;
		}
		if (cmbEmpresa.getSelectedItem() == null) {
			Messagebox.show("Seleccione un cliente.", ".:: Guardar articulo ::.", Messagebox.OK,
					Messagebox.EXCLAMATION);
			cmbEmpresa.setErrorMessage("Seleccione un cliente.");
			cmbEmpresa.setFocus(true);
			return;
		}
		if (lbxMovimientos.getItemCount() == 0) {
			Messagebox.show("Añada un articulo a la lista.", ".:: Guardar articulo ::.", Messagebox.OK,
					Messagebox.EXCLAMATION);
			bdxArticulos.setErrorMessage("Añada un articulo a la lista.");
			bdxArticulos.setFocus(true);
			return;
		}
		/*
		 * if (cmbTurno.getSelectedItem() == null) {
		 * cmbTurno.setErrorMessage("Seleccione un turno."); return; } if
		 * (txtOperador.getText().length() <= 0) {
		 * txtOperador.setErrorMessage("Ingrese el operador."); return; }
		 */
		Messagebox.show("Esta seguro de guardar el movimiento?", ".:: Nuevo movimiento ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							listaParametros1 = consultasABaseDeDatos.cargarParametros1();
							validarTurno();
							long secuencia = 0;
							dao_movimiento dao = new dao_movimiento();
							int indice = 0;
							indice = lbxSolicitantes.getSelectedIndex();
							java.util.Date date1, date2 = null;
							Timestamp timestamp1, timestamp2 = null;
							date1 = new Date();
							timestamp1 = new Timestamp(date1.getTime());
							List<modelo_movimiento> listaMovimiento = new ArrayList<modelo_movimiento>();
							modelo_movimiento movimiento;
							Listcell lCell;
							Combobox cmbMovimiento;
							Intbox intCantidad;
							Textbox txtObservacion;
							String descripcion;
							int sto_anterior = 0, sto_actual = 0;
							for (int i = 0; i < lbxMovimientos.getItemCount(); i++) {
								movimiento = new modelo_movimiento();
								lCell = (Listcell) lbxMovimientos.getItemAtIndex(i).getChildren().get(0);
								movimiento.setId_articulo(Long.valueOf(lCell.getLabel().toString()));
								lCell = (Listcell) lbxMovimientos.getItemAtIndex(i).getChildren().get(2);
								descripcion = lCell.getLabel().toString();
								lCell = (Listcell) lbxMovimientos.getItemAtIndex(i).getChildren().get(11);
								movimiento.setId_ubicacion(Long.valueOf(lCell.getLabel().toString()));
								lCell = (Listcell) lbxMovimientos.getItemAtIndex(i).getChildren().get(5);
								movimiento.setId_estado(Long.valueOf(lCell.getLabel().toString()));
								lCell = (Listcell) lbxMovimientos.getItemAtIndex(i).getChildren().get(4);
								cmbMovimiento = (Combobox) lCell.getChildren().get(0);
								if (cmbMovimiento.getSelectedItem() == null) {
									cmbMovimiento.setErrorMessage("Seleccione un movimiento.");
									Messagebox.show("Seleccione un movimiento para el artículo " + descripcion,
											".:: Guardar articulo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
									cmbMovimiento.setFocus(true);
									return;
								}
								movimiento.setTip_movimiento(cmbMovimiento.getSelectedItem().getValue().toString());
								lCell = (Listcell) lbxMovimientos.getItemAtIndex(i).getChildren().get(7);
								sto_anterior = Integer.valueOf(lCell.getLabel().toString());
								movimiento.setSto_anterior(Integer.valueOf(lCell.getLabel().toString()));
								lCell = (Listcell) lbxMovimientos.getItemAtIndex(i).getChildren().get(8);
								intCantidad = (Intbox) lCell.getChildren().get(0);
								if (intCantidad.getText().length() <= 0) {
									intCantidad.setErrorMessage("Ingrese la cantidad.");
									Messagebox.show(
											"Ingrese la cantidad que desea mover para el artículo " + descripcion,
											".:: Guardar articulo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
									intCantidad.setFocus(true);
									return;
								}
								movimiento.setCan_afectada(intCantidad.getValue());
								if (cmbMovimiento.getSelectedItem().getValue().toString().equals("I")) {
									sto_actual = sto_actual + (sto_anterior + intCantidad.getValue());
									movimiento.setSto_actual(sto_actual);
								} else {
									sto_actual = sto_actual + (sto_anterior - intCantidad.getValue());
									movimiento.setSto_actual(sto_actual);
								}
								if (chkTicket.isChecked()) {
									movimiento.setTck_movimiento(null);
								} else {
									movimiento.setTck_movimiento(txtTicket.getText().trim().toUpperCase());
								}
								movimiento.setId_usuario(id_user);
								movimiento.setId_localidad(id_dc);
								movimiento.setId_solicitante(listaSolicitante.get(indice).getId_solicitante());
								movimiento.setTur_movimiento(cmbTurno.getItemAtIndex(0).getValue().toString());
								date2 = dtxFecha.getValue();
								timestamp2 = new Timestamp(date2.getTime());
								movimiento.setFec_movimiento(timestamp2);
								lCell = (Listcell) lbxMovimientos.getItemAtIndex(i).getChildren().get(9);
								txtObservacion = (Textbox) lCell.getChildren().get(0);
								movimiento.setObs_movimiento(txtObservacion.getText().toString());
								movimiento.setEst_movimiento("A");
								movimiento.setUsu_ingresa(user);
								movimiento.setFec_ingresa(timestamp1);
								listaMovimiento.add(movimiento);
								/* REGISTRO EN LOG DE EVENTOS */
								bitacora = new modelo_bitacora();
								// Se debe crear un parametro para la configuracion de varios datos por defecto.
								bitacora.setId_cliente(setearEmpresaDesdeUbicacion(movimiento.getId_ubicacion()));
								if (chkTicket.isChecked()) {
									bitacora.setId_tipo_servicio(19);
									bitacora.setId_tipo_clasificacion(37);
								} else {
									bitacora.setId_tipo_servicio(obtenerIdTipoServicioAPartirDeTicket(
											txtTicket.getText().trim().toUpperCase(), 1, id_dc));
									bitacora.setId_tipo_clasificacion(obtenerIdTipoClasificacionAPartirDeTicket(
											txtTicket.getText().trim().toUpperCase(), 1, id_dc));
								}
								bitacora.setId_tipo_tarea(7);
								bitacora.setId_estado_bitacora(2);
								bitacora.setFec_inicio(fechas.obtenerTimestampDeDate(fechas.obtenerFechaArmada(
										fecha_ingresa_formulario, fecha_ingresa_formulario.getMonth(),
										fecha_ingresa_formulario.getDate(), fecha_ingresa_formulario.getHours(),
										fecha_ingresa_formulario.getMinutes(), 0)));
								bitacora.setFec_fin(fechas.obtenerTimestampDeDate(fechas.obtenerFechaArmada(new Date(),
										new Date().getMonth(), new Date().getDate(), new Date().getHours(),
										new Date().getMinutes(), 0)));
								bitacora.setCumplimiento("C");
								bitacora.setId_turno(id_turno);
								bitacora.setId_solicitante(movimiento.getId_solicitante());
								bitacora.setId_localidad(id_dc);
								bitacora.setEst_bitacora("AE");
								bitacora.setUsu_ingresa(user);
								bitacora.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
								if (listaParametros1.size() > 0) {
									secuencia = listaParametros1.get(0).getSecuencia_bitacora();
								}
							}
							try {
								dao.insertarMovimiento(listaMovimiento, bitacora, secuencia);
								Messagebox.show("El movimiento se guardo correctamente.", ".:: Nuevo movimiento ::.",
										Messagebox.OK, Messagebox.EXCLAMATION);
								limpiarCampos();
								obtenerId();
								cargarSolicitantes("");
								cargarEmpresas();
								cargarFecha();
							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar el movimiento. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Nuevo movimiento ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

	public Long setearEmpresaDesdeUbicacion(long id_ubicacion)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		long id_empresa = 0;
		List<modelo_ubicacion> listaUbicacion = new ArrayList<modelo_ubicacion>();
		dao_ubicacion dao = new dao_ubicacion();
		listaUbicacion = dao.obtenerUbicaciones("", String.valueOf(id_dc), 5, 0, 0, id_ubicacion, 0);
		if (listaUbicacion.size() > 0) {
			id_empresa = listaUbicacion.get(0).getId_empresa();
		}
		return id_empresa;
	}

	public String setearNombreUbicacion(long id_ubicacion)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String nombre_ubicacion = "";
		List<modelo_ubicacion> listaUbicacion = new ArrayList<modelo_ubicacion>();
		dao_ubicacion dao = new dao_ubicacion();
		listaUbicacion = dao.obtenerUbicaciones("", String.valueOf(id_dc), 5, 0, 0, id_ubicacion, 0);
		if (listaUbicacion.size() > 0) {
			nombre_ubicacion = listaUbicacion.get(0).toStringUbicacion();
		}
		return nombre_ubicacion;
	}

	public String setearNombreArticulo(long id_articulo)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String nombre_articulo = "";
		List<modelo_articulo> listaArticulos = new ArrayList<modelo_articulo>();
		dao_articulo dao = new dao_articulo();
		listaArticulos = dao.obtenerArticulos(String.valueOf(id_articulo), String.valueOf(id_dc), "", 11, 0);
		if (listaArticulos.size() > 0) {
			nombre_articulo = listaArticulos.get(0).toStringArticulo();
		}
		return nombre_articulo;
	}

	public String setearNombreEstadoArticulo(long id_estado)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String nombre_estado = "";
		List<modelo_estado_articulo> listaEstados = new ArrayList<modelo_estado_articulo>();
		dao_estado_articulo dao = new dao_estado_articulo();
		listaEstados = dao.obtenerEstados(String.valueOf(id_estado), 2, "");
		if (listaEstados.size() > 0) {
			nombre_estado = listaEstados.get(0).getNom_estado();
		}
		return nombre_estado;
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zNuevo));
	}

	public boolean validarSiSeIniciaTurno() throws ClassNotFoundException, FileNotFoundException, IOException {
		boolean se_inicio_turno = false;
		listaRegistroTurno = consultasABaseDeDatos.cargarRegistroTurnos("", 2, id_turno,
				fechas.obtenerFechaFormateada(fecha_actual, "yyyy/MM/dd"), id_dc);
		if (listaRegistroTurno.size() == 0) {
			se_inicio_turno = false;
		} else {
			if (listaRegistroTurno.get(0).getEst_registra_turno().equals("A")) {
				se_inicio_turno = true;
			} else {
				se_inicio_turno = false;
			}
		}
		return se_inicio_turno;
	}

	public boolean validarSiExistenTareasVencidas()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existen_tareas_vencidas = false;
		int totalTareasVencidas = consultasABaseDeDatos.validarSiExistenTareasProgramadasVencidas("1");
		if (totalTareasVencidas > 0) {
			existen_tareas_vencidas = true;
		} else {
			existen_tareas_vencidas = false;
		}
		return existen_tareas_vencidas;
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		setearFechaIngresaFormulario();
		cmbEmpresa.setText("");
		bdxSolicitantes.setText("");
		bdxSolicitantes.setTooltiptext("");
		txtDocumento.setText("");
		txtNombre.setText("");
		txtDocumento.setTooltiptext("");
		txtNombre.setTooltiptext("");
		bdxArticulos.setText("");
		txtBuscarProveedor.setText("");
		txtBuscarArticulo.setText("");
		bdxArticulos.setTooltiptext("");
		bdxArticulos.setDisabled(true);
		txtCodigo.setText("");
		txtDescripcion.setText("");
		txtStock.setText("");
		txtUbicacion.setText("");
		img1.setSrc("/img/principal/unnamed.jpg");
		btnAnadir.setDisabled(true);
		btnEliminar.setDisabled(true);
		btnCargar.setDisabled(true);
		btnBorrar.setDisabled(true);
		lbxArticulos.clearSelection();
		lbxSolicitantes.clearSelection();
		Listitem lItem;
		for (int i = lbxMovimientos.getItemCount() - 1; i >= 0; i--) {
			lItem = (Listitem) lbxMovimientos.getItemAtIndex(i);
			lbxMovimientos.removeItemAt(lItem.getIndex());
		}
		for (int i = lbxArticulos.getItemCount() - 1; i >= 0; i--) {
			lItem = (Listitem) lbxArticulos.getItemAtIndex(i);
			lbxArticulos.removeItemAt(lItem.getIndex());
		}
		listaArticulo = new ArrayList<modelo_articulo>();
		cmbEstado.setText("");
		binder.loadComponent(lbxMovimientos);
		binder.loadComponent(lbxArticulos);
		cmbTurno.setText("");
		bitacora = new modelo_bitacora();
		txtTicket.setReadonly(true);
		chkTicket.setChecked(true);
	}

	public boolean validarSiExistePrimeroApertura(String ticket_externo, long id_tipo_tarea)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		/*
		 * El metodo valida que exista primero una apertura antes de que se guarde un
		 * registro
		 */
		boolean existe_primero_apertura = true;
		if (consultasABaseDeDatos.validarSiExisteTareaRegistrada(ticket_externo, String.valueOf(id_tipo_tarea)) == 0) {
			existe_primero_apertura = false;
		}
		return existe_primero_apertura;
	}

	public long obtenerIdTipoServicioAPartirDeTicket(String ticket, long id_tipo_tarea, long id_dc)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		long id_tipo_servicio = 0;
		id_tipo_servicio = consultasABaseDeDatos.obtenerIdTipoServicioAPartirDeTicket(ticket, id_tipo_tarea, id_dc);
		return id_tipo_servicio;
	}

	public long obtenerIdTipoClasificacionAPartirDeTicket(String ticket, long id_tipo_tarea, long id_dc)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		long id_tipo_clasificacion = 0;
		id_tipo_clasificacion = consultasABaseDeDatos.obtenerIdTipoClasificacionAPartirDeTicket(ticket, id_tipo_tarea,
				id_dc);
		return id_tipo_clasificacion;
	}

}
