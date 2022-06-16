package bp.aplicaciones.controlador.mantenimientos.manuales;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.fit.pdfdom.PDFDomTree;
import org.zkoss.util.media.AMedia;
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
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Window;

import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_manual;
import bp.aplicaciones.mantenimientos.modelo.modelo_manual;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;

@SuppressWarnings({ "serial", "deprecation" })
public class modificar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zModificar;
	@Wire
	Button btnGrabar, btnCancelar, btnCargar, btnConsultar;
	@Wire
	Textbox txtId, txtNombre, txtDescripcion;
	@Wire
	Iframe frManual;
	@Wire
	Combobox cmbLocalidad;
	@Wire
	Label lNombre, lDescripcion;
	@Wire
	Row rwManual;

	List<modelo_localidad> listaLocalidad = new ArrayList<modelo_localidad>();

	long id = 0;
	long id_mantenimiento = 20;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	modelo_manual manual = (modelo_manual) Sessions.getCurrent().getAttribute("objeto");

	Fechas fechas = new Fechas();
	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();

	Informativos informativos = new Informativos();
	Validaciones validacion = new Validaciones();
	Error error = new Error();

	org.zkoss.util.media.Media media = null;

	Button dSolicitudes = (Button) Sessions.getCurrent().getAttribute("btn");

	String path_i = "\\\\10.207.55.24\\Respaldos_Operacion\\Documentos_DCAPP\\Archivos\\";

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		txtDescripcion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtDescripcion.setText(txtDescripcion.getText().toUpperCase().trim());
			}
		});
		inicializarListas();
		cargarDatos();
		cargarHTML();
	}

	public void inicializarListas() {
		listaLocalidad = consultasABaseDeDatos.consultarLocalidades(id_dc, 0, "", "", 0, 2);
		binder.loadComponent(cmbLocalidad);
	}

	public List<modelo_localidad> obtenerLocalidades() {
		return listaLocalidad;
	}

	public void cargarLocalidad() {
		if (listaLocalidad.size() > 0) {
			cmbLocalidad.setText(listaLocalidad.get(0).getNom_localidad());
		}
	}

	public void cargarDatos() throws SQLException {
		txtId.setText(String.valueOf(manual.getId_manual()));
		txtNombre.setText(manual.getNom_manual());
		txtDescripcion.setText(manual.getDes_manual());
		cmbLocalidad.setText(manual.getLocalidad().getNom_localidad());
		lNombre.setValue(txtNombre.getText().trim().length() + "/100");
		lDescripcion.setValue(txtDescripcion.getText().length() + "/200");
	}

	public void cargarHTML() {
		String nom = manual.getNom_manual() + "." + manual.getExt_manual();
		String nom_temp = manual.getNom_manual() + ".html";
		frManual.setContent(null);
		frManual.setSrc("/img/principal/NoDisponible.png");
		rwManual.setVisible(true);
		if (manual.getExt_manual().equals("pdf")) {
			File initial = new File(path_i + nom);
			if (initial.length() == 0) {
				Clients.showNotification("Se presentó un error al mostrar el archivo.", Clients.NOTIFICATION_TYPE_ERROR,
						dSolicitudes, "top_right", 2000, true);
				frManual.setContent(null);
				frManual.setSrc("/img/principal/NoDisponible.png");
				return;
			} else {
				InputStream target = null;
				try {
					target = new FileInputStream(initial);
				} catch (FileNotFoundException e1) {
					Clients.showNotification("Se presentó un error al mostrar el archivo.",
							Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 2000, true);
					frManual.setContent(null);
					frManual.setSrc("/img/principal/NoDisponible.png");
					e1.printStackTrace();
				}
				PDDocument pdf = null;
				try {
					pdf = PDDocument.load(target);
				} catch (InvalidPasswordException e1) {
					Clients.showNotification("Se presentó un error al mostrar el archivo.",
							Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 2000, true);
					frManual.setContent(null);
					frManual.setSrc("/img/principal/NoDisponible.png");
					e1.printStackTrace();
				} catch (IOException e1) {
					Clients.showNotification("Se presentó un error al mostrar el archivo.",
							Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 2000, true);
					frManual.setContent(null);
					frManual.setSrc("/img/principal/NoDisponible.png");
					e1.printStackTrace();
				}
				Writer output = null;
				try {
					output = new PrintWriter(path_i + nom_temp, "utf-8");
					new PDFDomTree().writeText(pdf, output);
				} catch (FileNotFoundException e1) {
					Clients.showNotification("Se presentó un error al mostrar el archivo.",
							Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 2000, true);
					frManual.setContent(null);
					frManual.setSrc("/img/principal/NoDisponible.png");
					e1.printStackTrace();
				} catch (UnsupportedEncodingException e1) {
					Clients.showNotification("Se presentó un error al mostrar el archivo.",
							Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 2000, true);
					frManual.setContent(null);
					frManual.setSrc("/img/principal/NoDisponible.png");
					e1.printStackTrace();
				} catch (IOException e) {
					Clients.showNotification("Se presentó un error al mostrar el archivo.",
							Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 2000, true);
					frManual.setContent(null);
					frManual.setSrc("/img/principal/NoDisponible.png");
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					Clients.showNotification("Se presentó un error al mostrar el archivo.",
							Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 2000, true);
					frManual.setContent(null);
					frManual.setSrc("/img/principal/NoDisponible.png");
					e.printStackTrace();
				} finally {
					try {
						target.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {
						output.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				File initialFile = new File(path_i + nom_temp);
				if (initialFile.length() == 0) {
					Clients.showNotification("Se presentó un error al mostrar el archivo.",
							Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 2000, true);
					frManual.setContent(null);
					frManual.setSrc("/img/principal/NoDisponible.png");
					return;
				} else {
					InputStream targetStream = null;
					byte[] ba1 = new byte[1024];
					int baLength;
					ByteArrayOutputStream bios = new ByteArrayOutputStream();
					try {
						targetStream = new FileInputStream(initialFile);
						while ((baLength = targetStream.read(ba1)) != -1) {
							bios.write(ba1, 0, baLength);
						}
					} catch (Exception e) {
						Clients.showNotification("Se presentó un error al mostrar el archivo.",
								Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 2000, true);
						frManual.setContent(null);
						frManual.setSrc("/img/principal/NoDisponible.png");
						e.printStackTrace();
					} finally {
						try {
							targetStream.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					Path to = Paths.get(path_i + nom_temp);
					try {
						Files.delete(to);
						final AMedia amedia = new AMedia(nom_temp, "html", "text/html", bios.toByteArray());
						frManual.setContent(amedia);
					} catch (IOException e) {
						Clients.showNotification("Se presentó un error al mostrar el archivo.",
								Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 2000, true);
						frManual.setContent(null);
						frManual.setSrc("/img/principal/NoDisponible.png");
						e.printStackTrace();
					}
				}
			}
		}
	}

	@Listen("onBlur=#txtNombre")
	public void onBlur$txtNombre()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (txtNombre.getText().trim().length() <= 0) {
			return;
		}
		dao_manual dao = new dao_manual();
		if (dao.consultarManuales(manual.getId_manual(), 0, txtNombre.getText().trim(), "", 0, 4).size() > 0) {
			txtNombre.setFocus(true);
			Clients.showNotification("El nombre ya se encuentra registrado.", Clients.NOTIFICATION_TYPE_ERROR,
					dSolicitudes, "top_right", 2000, true);
			return;
		}
	}

	@Listen("onUpload=#btnCargar")
	public void onUpload$btnCargar(UploadEvent event) {
		media = event.getMedia();
		if (media == null) {
			Clients.showNotification("No se añadio algún archivo.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
					"top_right", 2000, true);
			return;
		}
		if ((media.getName().indexOf(".pdf") == -1) && (media.getName().indexOf(".xls") == -1)
				&& (media.getName().indexOf(".xlsx") == -1) && (media.getName().indexOf(".doc") == -1)
				&& (media.getName().indexOf(".docx") == -1)) {
			Clients.showNotification("El archivo debe ser de extensión (.pdf), (.xls), (.xlsx), (.doc), (.docx).",
					Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 5000, true);
			return;
		}
		String ruta1 = media.getName();
		String extension = ruta1.substring(ruta1.lastIndexOf(".") + 1);
		txtNombre.setDisabled(false);
		txtNombre.setText(media.getName().replace(extension, "").replace(".", "").trim());
		txtDescripcion.setDisabled(false);
		frManual.setContent(null);
		frManual.setSrc("/img/principal/NoDisponible.png");
		rwManual.setVisible(true);
		if (extension.equals("pdf")) {
			rwManual.setVisible(true);
			String nom_temp = media.getName().replace(extension, "") + "html";
			PDDocument pdf = null;
			try {
				pdf = PDDocument.load(media.getByteData());
			} catch (InvalidPasswordException e1) {
				Clients.showNotification("Se presentó un error al mostrar el archivo.", Clients.NOTIFICATION_TYPE_ERROR,
						dSolicitudes, "top_right", 2000, true);
				frManual.setContent(null);
				frManual.setSrc("/img/principal/NoDisponible.png");
				e1.printStackTrace();
			} catch (IOException e1) {
				Clients.showNotification("Se presentó un error al mostrar el archivo.", Clients.NOTIFICATION_TYPE_ERROR,
						dSolicitudes, "top_right", 2000, true);
				frManual.setContent(null);
				frManual.setSrc("/img/principal/NoDisponible.png");
				e1.printStackTrace();
			}
			Writer output = null;
			try {
				output = new PrintWriter(path_i + nom_temp, "utf-8");
				new PDFDomTree().writeText(pdf, output);
			} catch (IOException e) {
				Clients.showNotification("Se presentó un error al mostrar el archivo.", Clients.NOTIFICATION_TYPE_ERROR,
						dSolicitudes, "top_right", 2000, true);
				frManual.setContent(null);
				frManual.setSrc("/img/principal/NoDisponible.png");
				e.printStackTrace();
			} catch (ParserConfigurationException e) {
				Clients.showNotification("Se presentó un error al mostrar el archivo.", Clients.NOTIFICATION_TYPE_ERROR,
						dSolicitudes, "top_right", 2000, true);
				frManual.setContent(null);
				frManual.setSrc("/img/principal/NoDisponible.png");
				e.printStackTrace();
			} finally {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			File initialFile = new File(path_i + nom_temp);
			if (initialFile.length() == 0) {
				Clients.showNotification("Se presentó un error al mostrar el archivo.", Clients.NOTIFICATION_TYPE_ERROR,
						dSolicitudes, "top_right", 2000, true);
				frManual.setContent(null);
				frManual.setSrc("/img/principal/NoDisponible.png");
				return;
			}
			InputStream targetStream = null;
			ByteArrayOutputStream bios = new ByteArrayOutputStream();
			byte[] ba1 = new byte[1024];
			int baLength;
			try {
				targetStream = new FileInputStream(initialFile);
				while ((baLength = targetStream.read(ba1)) != -1) {
					bios.write(ba1, 0, baLength);
				}
			} catch (FileNotFoundException e1) {
				Clients.showNotification("Se presentó un error al mostrar el archivo.", Clients.NOTIFICATION_TYPE_ERROR,
						dSolicitudes, "top_right", 2000, true);
				frManual.setContent(null);
				frManual.setSrc("/img/principal/NoDisponible.png");
				e1.printStackTrace();
			} catch (IOException e1) {
				Clients.showNotification("Se presentó un error al mostrar el archivo.", Clients.NOTIFICATION_TYPE_ERROR,
						dSolicitudes, "top_right", 2000, true);
				frManual.setContent(null);
				frManual.setSrc("/img/principal/NoDisponible.png");
				e1.printStackTrace();
			} finally {
				try {
					targetStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			File deleteFile = new File(path_i + nom_temp);
			if (deleteFile.length() > 0) {
				Path to = Paths.get(path_i + nom_temp);
				try {
					Files.delete(to);
					final AMedia amedia = new AMedia(nom_temp, "html", "text/html", bios.toByteArray());
					frManual.setContent(amedia);
				} catch (IOException e) {
					Clients.showNotification("Se presentó un error al mostrar el archivo.",
							Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 2000, true);
					frManual.setContent(null);
					frManual.setSrc("/img/principal/NoDisponible.png");
					e.printStackTrace();
				}
			}
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar() {
		String nom = modificar.this.manual.getNom_manual() + "." + modificar.this.manual.getExt_manual();
		File initial = new File(path_i + nom);
		if (initial.length() == 0) {
			if (media == null) {
				Clients.showNotification(
						"Ocurrió un error con el archivo guardado para este manual, favor añada nuevamente el archivo.",
						Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 2000, true);
				return;
			}
			if ((media.getName().indexOf(".pdf") == -1)) {
				Clients.showNotification("El archivo debe ser de extensión (.pdf).", Clients.NOTIFICATION_TYPE_ERROR,
						dSolicitudes, "top_right", 2000, true);
				return;
			}
		}
		if (txtNombre.getText().trim().length() <= 0) {
			txtNombre.setFocus(true);
			Clients.showNotification("Ingrese el nombre.", Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right",
					2000, true);
			return;
		}
		dao_manual dao = new dao_manual();
		if (dao.consultarManuales(manual.getId_manual(), 0, txtNombre.getText().trim(), "", 0, 4).size() > 0) {
			txtNombre.setFocus(true);
			Clients.showNotification("El nombre ya se encuentra registrado.", Clients.NOTIFICATION_TYPE_ERROR,
					dSolicitudes, "top_right", 2000, true);
			return;
		}
		if (txtDescripcion.getText().length() <= 0) {
			txtDescripcion.setFocus(true);
			Clients.showNotification("Ingrese la descripción.", Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes,
					"top_right", 2000, true);
			return;
		}
		if (cmbLocalidad.getSelectedItem() == null) {
			cmbLocalidad.setFocus(true);
			Clients.showNotification("Seleccione una localidad.", Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes,
					"top_right", 2000, true);
			return;
		}
		Messagebox.show("Esta seguro de guardar el manual?", ".:: Modificar manual ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_manual dao = new dao_manual();
							modelo_manual manual = new modelo_manual();
							manual.setId_manual(modificar.this.manual.getId_manual());
							manual.setNom_manual(txtNombre.getText().trim());
							manual.setDes_manual(txtDescripcion.getText());
							manual.setEst_manual("AE");
							if (listaLocalidad.size() > 0) {
								manual.setLocalidad(listaLocalidad.get(0));
							} else {
								cmbLocalidad.setFocus(true);
								cmbLocalidad.setErrorMessage("Seleccione una localidad.");
								return;
							}
							manual.setUsu_ingresa(modificar.this.manual.getUsu_ingresa());
							manual.setUsu_modifica(user);
							manual.setFec_ingresa(modificar.this.manual.getFec_ingresa());
							manual.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
							if (media != null) {
								String nom = modificar.this.manual.getNom_manual() + "."
										+ modificar.this.manual.getExt_manual();
								File initial = new File(path_i + nom);
								if (initial.length() == 0) {
									System.out.println("Algo paso con el archivo guardado.");
								} else {
									Path to = Paths.get(path_i + modificar.this.manual.getNom_manual() + "."
											+ modificar.this.manual.getExt_manual());
									Files.delete(to);
								}
								String ruta1 = media.getName();
								// Obtengo lo que quiero mostrar en el textview (la subcadena)
								String extension = ruta1.substring(ruta1.lastIndexOf(".") + 1);
								// System.out.println(ultima);
								manual.setExt_manual(extension);
								manual.setDir_manual(path_i + txtNombre.getText().trim() + "." + extension);
								byte[] buffer = media.getByteData();
								// manual.setDir_manual(new SerialBlob(buffer));
								Path to = Paths.get(path_i + txtNombre.getText().trim() + "." + extension);
								Path tempFile = Files.createTempFile("tempfile", null);
								try {
									Files.write(tempFile, media.getByteData(), StandardOpenOption.WRITE);
									Files.copy(tempFile, to, StandardCopyOption.REPLACE_EXISTING);
									Files.delete(tempFile);
									Files.write(to, buffer, StandardOpenOption.WRITE);
									dao.actualizarManual(manual);
									Clients.showNotification("El manual se actualizó correctamente.", "info",
											dSolicitudes, "top_right", 4000);
									limpiarCampos();
									Events.postEvent(new Event("onClose", zModificar));
								} catch (IOException e) {
									Clients.showNotification("Error al guardar el manual.",
											Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 4000, true);
									e.printStackTrace();
								}
							} else {
								manual.setExt_manual(modificar.this.manual.getExt_manual());
								manual.setDir_manual(
										path_i + txtNombre.getText().trim() + "." + manual.getExt_manual());
								String nom = modificar.this.manual.getNom_manual() + "."
										+ modificar.this.manual.getExt_manual();
								File initial = new File(path_i + nom);
								if (initial.length() == 0) {
									System.out.println("Algo paso con el archivo guardado.");
								} else {
									Path to = Paths.get(path_i + modificar.this.manual.getNom_manual() + "."
											+ modificar.this.manual.getExt_manual());
									FileInputStream fl = null;
									byte[] arr = null;
									try {
										fl = new FileInputStream(initial);
										arr = new byte[(int) initial.length()];
										fl.read(arr);
									} catch (FileNotFoundException e1) {
										Clients.showNotification("Se presentó un error al mostrar el archivo.",
												Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 2000, true);
										frManual.setContent(null);
										e1.printStackTrace();
									} catch (IOException e1) {
										Clients.showNotification("Se presentó un error al mostrar el archivo.",
												Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 2000, true);
										frManual.setContent(null);
										e1.printStackTrace();
									} finally {
										try {
											fl.close();
										} catch (IOException e) {
											e.printStackTrace();
										}
									}
									try {
										Files.delete(to);
									} catch (IOException e) {
										Clients.showNotification("Error al guardar el manual.",
												Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 4000, true);
										e.printStackTrace();
									}
									to = Paths.get(path_i + txtNombre.getText().trim() + "." + manual.getExt_manual());
									Path tempFile = Files.createTempFile("tempfile", null);
									try {
										Files.write(tempFile, arr, StandardOpenOption.WRITE);
										Files.copy(tempFile, to, StandardCopyOption.REPLACE_EXISTING);
										Files.delete(tempFile);
										Files.write(to, arr, StandardOpenOption.WRITE);
										dao.actualizarManual(manual);
										Clients.showNotification("El manual se actualizó correctamente.", "info",
												dSolicitudes, "top_right", 4000);
										limpiarCampos();
										Events.postEvent(new Event("onClose", zModificar));
									} catch (IOException e) {
										Clients.showNotification("Error al guardar el manual.",
												Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 4000, true);
										e.printStackTrace();
									}
								}
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
		media = null;
		frManual.setContent(null);
		frManual.setSrc("/img/principal/NoDisponible.png");
		lNombre.setValue(txtNombre.getText().trim().length() + "/100");
		lDescripcion.setValue(txtDescripcion.getText().length() + "/200");
	}

}
