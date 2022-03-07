package bp.aplicaciones.controlador.mantenimiento.manuales;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Window;


import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_manual;
import bp.aplicaciones.mantenimientos.DAO.dao_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_manual;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;

@SuppressWarnings({ "serial", "deprecation" })
public class nuevo extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zNuevo;
	@Wire
	Button btnGrabar, btnCancelar, btnCargar, btnConsultar;
	@Wire
	Textbox txtId, txtNombre, txtDescripcion;
	@Wire
	Combobox cmbLocalidad;
	@Wire
	Iframe frManual;

	List<modelo_localidad> listaLocalidad = new ArrayList<modelo_localidad>();

	long id = 0;
	long id_mantenimiento = 5;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	Fechas fechas = new Fechas();
	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();

	Informativos informativos = new Informativos();
	Validaciones validacion = new Validaciones();
	Error error = new Error();

	org.zkoss.util.media.Media media = null;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		txtNombre.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtNombre.setText(txtNombre.getText().toUpperCase().trim());
			}
		});
		txtDescripcion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtDescripcion.setText(txtDescripcion.getText().toUpperCase().trim());
			}
		});
		obtenerId();
		cargarLocalidades();
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
		for (int i = 0; i < listaLocalidad.size(); i++) {
			if (listaLocalidad.get(i).getId_localidad() == id_dc) {
				cmbLocalidad.setText(listaLocalidad.get(i).getNom_localidad());
				i = listaLocalidad.size() + 1;
			}
		}
	}

	public void obtenerId() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_manual dao = new dao_manual();
		try {
			id = dao.obtenerNuevoId();
			txtId.setText(String.valueOf(id));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Messagebox.show("Error al obtener el nuevo Id. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Obtener ID ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onBlur=#txtNombre")
	public void onBlur$txtNombre()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (txtNombre.getText().length() <= 0) {
			return;
		}
		dao_manual dao = new dao_manual();
		if (dao.obtenerManuales(txtNombre.getText(), String.valueOf(id_dc), 3, 0, 0).size() > 0) {
			txtNombre.setFocus(true);
			txtNombre.setErrorMessage("El nombre ya se encuentra registrado.");
			return;
		}
	}

	@Listen("onUpload=#btnCargar")
	public void onUpload$btnCargar(UploadEvent event) throws SerialException, SQLException, ClassNotFoundException,
			FileNotFoundException, IOException, URISyntaxException {
		media = event.getMedia();
		if (media == null) {
			Messagebox.show("No se añadio algún archivo.", ".:: Nuevo manual ::.", Messagebox.OK,
					Messagebox.INFORMATION);
			return;
		}
		if ((media.getName().indexOf(".pdf") == -1) && (media.getName().indexOf(".xlsx") == -1)
				&& (media.getName().indexOf(".xls") == -1) && (media.getName().indexOf(".doc") == -1)
				&& (media.getName().indexOf(".docx") == -1)) {
			Messagebox.show("El archivo debe tener extensión (.pdf) o (.xlsx) o (.xls) o (.doc) o (.docx).",
					".:: Nuevo manual ::.", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
//		byte[] buffer = media.getByteData();
//		ByteArrayInputStream is1 = new ByteArrayInputStream(buffer);
//
//		// String outputFile = "output/toHTML_out.html";
//
//		PdfDocument pdf = new PdfDocument();
//		pdf.loadFromStream(is1);
//
//		// save every PDF to .png image
//		BufferedImage image = null;
//		for (int i = 0; i < pdf.getPages().getCount(); i++) {
//			image = pdf.saveAsImage(i);
//			File file = new File(String.format("ToImage-img-%d.png", i));
//			ImageIO.write(image, "PNG", file);
//			if (i == 0) {
//				break;
//			}
//		}
//		pdf.close();
//
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		ImageIO.write(image, "PNG", baos);
//		byte[] bytes = baos.toByteArray();
//
//		ByteArrayInputStream is2 = new ByteArrayInputStream(bytes);
//
//		/*
//		 * // Set the bool useEmbeddedSvg and useEmbeddedImg as true
//		 * pdf.getConvertOptions().setPdfToHtmlOptions(true, true); // Save to stream
//		 * File outFile = new File(outputFile); OutputStream outputStream = new
//		 * FileOutputStream(outFile); pdf.saveToStream(outputStream, FileFormat.HTML);
//		 * pdf.close(); // OutputStream ByteArrayOutputStream outStream = new
//		 * ByteArrayOutputStream(); outStream.writeTo(outputStream); // byte[] ->
//		 * InputStream ByteArrayInputStream inStream = new
//		 * ByteArrayInputStream(outStream.toByteArray()); InputStream targetStream =
//		 * inStream;
//		 */
//
//		final AMedia amedia = new AMedia(media.getName(), "", "image/png", is2);
//
//		// frManual.setSrc(outputFile)
//		frManual.setContent(amedia);
//		// frManual.setVisible(true);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (txtNombre.getText().length() <= 0) {
			txtNombre.setFocus(true);
			txtNombre.setErrorMessage("Ingrese la nombre del manual.");
			return;
		}
		dao_manual dao = new dao_manual();
		if (dao.obtenerManuales(txtNombre.getText(), String.valueOf(id_dc), 3, 0, 0).size() > 0) {
			txtNombre.setFocus(true);
			txtNombre.setErrorMessage("El nombre ya se encuentra registrado.");
			return;
		}
		if (txtDescripcion.getText().length() <= 0) {
			txtDescripcion.setFocus(true);
			txtDescripcion.setErrorMessage("Ingrese la descripción del manual.");
			return;
		}
		if (media == null) {
			Messagebox.show("No se añadio algún archivo.", ".:: Nuevo manual ::.", Messagebox.OK,
					Messagebox.INFORMATION);
			return;
		}
		if ((media.getName().indexOf(".pdf") == -1) && (media.getName().indexOf(".xlsx") == -1)
				&& (media.getName().indexOf(".xls") == -1) && (media.getName().indexOf(".doc") == -1)
				&& (media.getName().indexOf(".docx") == -1)) {
			Messagebox.show("El archivo debe tener extensión (.pdf) o (.xlsx) o (.xls) o (.doc) o (.docx).",
					".:: Nuevo manual ::.", Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		if (cmbLocalidad.getSelectedItem() == null) {
			cmbLocalidad.setFocus(true);
			cmbLocalidad.setErrorMessage("Seleccione una localidad.");
			return;
		}
		Messagebox.show("Esta seguro de guardar el manual?", ".:: Nuevo manual ::.", Messagebox.OK | Messagebox.CANCEL,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_manual dao = new dao_manual();
							modelo_manual manual = new modelo_manual();
							manual.setNom_manual(txtNombre.getText());
							manual.setDes_manual(txtDescripcion.getText());
							manual.setId_localidad(
									Long.parseLong(cmbLocalidad.getSelectedItem().getValue().toString()));
							String ruta1 = media.getName();
							// Obtengo lo que quiero mostrar en el textview (la subcadena)
							String extension = ruta1.substring(ruta1.lastIndexOf(".") + 1);
							// System.out.println(ultima);
							manual.setExt_manual(extension);
							byte[] buffer = media.getByteData();
							manual.setDir_manual(new SerialBlob(buffer));
							manual.setEst_manual("AE");
							manual.setUsu_ingresa(user);
							manual.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
							try {
								dao.insertarManual(manual);
								Messagebox.show("El manual se guardó correctamente.", ".:: Nuevo manual ::.",
										Messagebox.OK, Messagebox.EXCLAMATION);
								limpiarCampos();
								obtenerId();
								cargarLocalidades();
							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar el manual. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
										".:: Nuevo manual ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});

	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zNuevo));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtNombre.setText("");
		txtDescripcion.setText("");
		cmbLocalidad.setText("");
		media = null;
		frManual.setContent(null);
	}

}
