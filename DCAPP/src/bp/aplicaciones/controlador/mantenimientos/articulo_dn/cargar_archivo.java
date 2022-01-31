package bp.aplicaciones.controlador.mantenimientos.articulo_dn;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_turno;
import bp.aplicaciones.mantenimientos.DAO.dao_articulo_dn;
import bp.aplicaciones.mantenimientos.DAO.dao_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_turno;
import bp.aplicaciones.mantenimientos.modelo.modelo_ubicacion_dn;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;
import bp.aplicaciones.mantenimientos.modelo.modelo_articulo_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_capacidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_categoria_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_perfil;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_articulo_ubicacion_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_respaldo;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_4;

@SuppressWarnings({ "serial", "deprecation" })
public class cargar_archivo extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zCargar;
	@Wire
	Button btnGrabar, btnCargar, btnLimpiar;
	@Wire
	Combobox cmbDia;
	@Wire
	Textbox txtRuta;
	@Wire
	Listbox lbxArticulos;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	String consultar, insertar, modificar, relacionar, desactivar, eliminar, solicitar, revisar, aprobar, ejecutar;

	validar_datos validar = new validar_datos();

	List<modelo_perfil> listaPerfil = new ArrayList<modelo_perfil>();
	List<modelo_articulo_dn> listaArticulos = new ArrayList<modelo_articulo_dn>();
	List<modelo_parametros_generales_4> listaRelacionTarea = new ArrayList<modelo_parametros_generales_4>();
	List<modelo_capacidad> listaCapacidad = new ArrayList<modelo_capacidad>();
	List<modelo_categoria_dn> listaCategoria = new ArrayList<modelo_categoria_dn>();
	List<modelo_ubicacion_dn> listaUbicacion = new ArrayList<modelo_ubicacion_dn>();
	List<modelo_ubicacion_dn> listaUbicacion1 = new ArrayList<modelo_ubicacion_dn>();
	List<modelo_turno> listaTurno = new ArrayList<modelo_turno>();
	List<modelo_respaldo> listaRespaldo = new ArrayList<modelo_respaldo>();
	List<modelo_respaldo> listaRespaldo1 = new ArrayList<modelo_respaldo>();

	long id_opcion = 4;
	long id_mantenimiento = 16;

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
		lbxArticulos.setEmptyMessage(informativos.getMensaje_informativo_2());
		cargarPerfil();
		inicializarPermisos();
		inicializarListas();
		if (insertar.equals("S")) {
			btnGrabar.setDisabled(false);
			btnGrabar.setVisible(true);
		} else {
			btnGrabar.setDisabled(true);
			btnGrabar.setVisible(false);
		}
	}

	public void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaCategoria = consultasABaseDeDatos.cargarCategoriasDN("", id_dc, 1, 0, 0);
		listaCapacidad = consultasABaseDeDatos.cargarCapacidadesDN("", 1, 0, 0);
		listaRespaldo = consultasABaseDeDatos.cargarRespaldosDN("", 6, "", "", 0);
		listaRespaldo1 = consultasABaseDeDatos.cargarRespaldosDN("", 7, "", "", 0);
		listaUbicacion = consultasABaseDeDatos.cargarUbicacionesDN("", id_dc, 6, 0, 0, 0, 10);
	}

	public void cargarPerfil() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_perfil dao = new dao_perfil();
		String criterio = "";
		try {
			listaPerfil = dao.obtenerPerfiles(criterio, 4, id_perfil);
		} catch (SQLException e) {
			Messagebox.show("Error al cargar los perfiles. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
					".:: Cargar perfil ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void cargarTurnos() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_turno dao = new dao_turno();
		String criterio = "";
		try {
			listaTurno = dao.obtenerTurnos(criterio);
		} catch (SQLException e) {
			Messagebox.show(
					"Error al cargar los turnos. \n\n" + "Codigo de error: " + e.getErrorCode()
							+ "\n\nMensaje de error: \n\n" + e.getMessage(),
					".:: Cargar turnos ::.", Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	public void inicializarPermisos() {
		if (listaPerfil.size() == 1) {
			if (listaPerfil.get(0).getConsultar().equals("S")) {
				consultar = "S";
			} else {
				consultar = "N";
			}
			if (listaPerfil.get(0).getInsertar().equals("S")) {
				insertar = "S";
			} else {
				insertar = "N";
			}
			if (listaPerfil.get(0).getModificar().equals("S")) {
				modificar = "S";
			} else {
				modificar = "N";
			}
			if (listaPerfil.get(0).getRelacionar().equals("S")) {
				relacionar = "S";
			} else {
				relacionar = "N";
			}
			if (listaPerfil.get(0).getDesactivar().equals("S")) {
				desactivar = "S";
			} else {
				desactivar = "N";
			}
			if (listaPerfil.get(0).getEliminar().equals("S")) {
				eliminar = "S";
			} else {
				eliminar = "N";
			}
			if (listaPerfil.get(0).getSolicitar().equals("S")) {
				solicitar = "S";
			} else {
				solicitar = "N";
			}
			if (listaPerfil.get(0).getRevisar().equals("S")) {
				revisar = "S";
			} else {
				revisar = "N";
			}
			if (listaPerfil.get(0).getAprobar().equals("S")) {
				aprobar = "S";
			} else {
				aprobar = "N";
			}
			if (listaPerfil.get(0).getEjecutar().equals("S")) {
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

	public List<modelo_articulo_dn> obtenerArticulos() {
		return listaArticulos;
	}

	public List<modelo_turno> obtenerTurnos() {
		return listaTurno;
	}

	public List<modelo_capacidad> obtenerCapacidad() {
		return listaCapacidad;
	}

	public List<modelo_categoria_dn> obtenerCategorias() {
		return listaCategoria;
	}

	public List<modelo_ubicacion_dn> obtenerUbicaciones() {
		return listaUbicacion;
	}

	@Listen("onUpload=#btnCargar")
	public void onUpload$btnCargar(UploadEvent event) throws SerialException, SQLException, ClassNotFoundException,
			FileNotFoundException, IOException, URISyntaxException {
		media = event.getMedia();
		if (media == null) {
			Messagebox.show("No se añadio algún archivo.", ".:: Cargar archivo ::.", Messagebox.OK,
					Messagebox.INFORMATION);
			return;
		}
		if ((media.getName().indexOf(".xlsx") == -1)) {
			Messagebox.show("El archivo debe tener extensión (xlsx).", ".:: Cargar archivo ::.", Messagebox.OK,
					Messagebox.INFORMATION);
			return;
		}
		btnCargar.setDisabled(true);
		txtRuta.setText(media.getName());
		byte[] buffer = media.getByteData();
		InputStream is = new ByteArrayInputStream(buffer);
		cargarArchivo(is);
		dibujarListaConsulta();
	}

	public void borrarListaConsulta() {
		lbxArticulos.clearSelection();
		Listitem lItem;
		for (int i = lbxArticulos.getItemCount() - 1; i >= 0; i--) {
			lItem = (Listitem) lbxArticulos.getItemAtIndex(i);
			lbxArticulos.removeItemAt(lItem.getIndex());
		}
		binder.loadComponent(lbxArticulos);
	}

	public List<modelo_articulo_dn> cargarArchivo(InputStream inputStream) {
		modelo_articulo_dn articulo = null;
		try {
			DataFormatter formatter = new DataFormatter();
			// File file = new File("D:\\Escritorio\\Formato_articulos.xlsx");
			// FileInputStream inputStream = new FileInputStream(file);
			Workbook workbook = new XSSFWorkbook(inputStream);
			Sheet firstSheet = workbook.getSheetAt(0);
			Iterator<?> rowIterator = firstSheet.iterator();
			int i = 0;
			int j = 0;
			long id_articulo = 100000000;
			while (rowIterator.hasNext()) {
				Row nextRow = (Row) rowIterator.next();
				if (i > 0) {
					Iterator<?> cellIterator = nextRow.cellIterator();
					j = 0;
					articulo = new modelo_articulo_dn();
					articulo.setId_articulo(id_articulo);
					while (cellIterator.hasNext()) {
						Cell cell = (Cell) cellIterator.next();
						String contenidoCelda = formatter.formatCellValue(cell).toUpperCase().trim();
						System.out.println("celda: " + contenidoCelda);
						if (j == 0) {
							if (contenidoCelda != null) {
								if (contenidoCelda.length() > 0) {
									if (!contenidoCelda.equals("-")) {
										articulo.setCod_articulo(contenidoCelda);
									} else {
										articulo.setCod_articulo("");
									}
								} else {
									articulo.setCod_articulo("");
								}
							} else {
								articulo.setCod_articulo("");
							}
						}
						if (j == 1) {
							if (contenidoCelda != null) {
								if (contenidoCelda.length() > 0) {
									if (!contenidoCelda.equals("-")) {
										articulo.setDes_articulo(contenidoCelda);
									} else {
										articulo.setDes_articulo("");
									}
								} else {
									articulo.setDes_articulo("");
								}
							} else {
								articulo.setDes_articulo("");
							}
						}
						if (j == 2) {
							if (contenidoCelda != null) {
								if (contenidoCelda.length() > 0) {
									if (!contenidoCelda.equals("-")) {
										articulo.setNom_categoria(contenidoCelda);
									} else {
										articulo.setNom_categoria(null);
									}
								} else {
									articulo.setNom_categoria(null);
								}
							} else {
								articulo.setNom_categoria(null);
							}
						}
						if (j == 3) {
							if (contenidoCelda != null) {
								if (contenidoCelda.length() > 0) {
									if (!contenidoCelda.equals("-")) {
										articulo.setNom_capacidad(contenidoCelda);
									} else {
										articulo.setNom_capacidad(null);
									}
								} else {
									articulo.setNom_capacidad(null);
								}
							} else {
								articulo.setNom_capacidad(null);
							}
						}
						if (j == 4) {
							if (contenidoCelda != null) {
								if (contenidoCelda.length() > 0) {
									if (!contenidoCelda.equals("-")) {
										articulo.setSi_ing_fec_inicio_fin(contenidoCelda);
									} else {
										articulo.setSi_ing_fec_inicio_fin("NO");
									}
								} else {
									articulo.setSi_ing_fec_inicio_fin("NO");
								}
							} else {
								articulo.setSi_ing_fec_inicio_fin("NO");
							}
						}
						if (j == 5) {
							if (contenidoCelda != null) {
								if (contenidoCelda.length() > 0) {
									if (!contenidoCelda.equals("-")) {
										if (articulo.getSi_ing_fec_inicio_fin().equals("SI")) {
											articulo.setEs_fecha(contenidoCelda);
										} else {
											articulo.setEs_fecha("NO");
										}
									} else {
										articulo.setEs_fecha("NO");
									}
								} else {
									articulo.setEs_fecha("NO");
								}
							} else {
								articulo.setEs_fecha("NO");
							}
						}
						if (j == 6) {
							if (contenidoCelda != null) {
								if (contenidoCelda.length() > 0) {
									if (!contenidoCelda.equals("-")) {
										if (articulo.getSi_ing_fec_inicio_fin().equals("SI")) {
											if (articulo.getEs_fecha().equals("SI")) {
												articulo.setFec_inicio(fechas.obtenerTimestampDeDate(
														fechas.obtenerDateDeUnString(contenidoCelda, "yyyy-MM-dd")));
											} else {
												articulo.setFec_inicio(null);
											}
										} else {
											articulo.setFec_inicio(null);
										}
									} else {
										articulo.setFec_inicio(null);
									}
								} else {
									articulo.setFec_inicio(null);
								}
							} else {
								articulo.setFec_inicio(null);
							}
						}
						if (j == 7) {
							if (contenidoCelda != null) {
								if (contenidoCelda.length() > 0) {
									if (!contenidoCelda.equals("-")) {
										if (articulo.getSi_ing_fec_inicio_fin().equals("SI")) {
											if (articulo.getEs_fecha().equals("SI")) {
												articulo.setFec_fin(fechas.obtenerTimestampDeDate(
														fechas.obtenerDateDeUnString(contenidoCelda, "yyyy-MM-dd")));
											} else {
												articulo.setFec_fin(null);
											}
										} else {
											articulo.setFec_fin(null);
										}
									} else {
										articulo.setFec_fin(null);
									}

								} else {
									articulo.setFec_fin(null);
								}
							} else {
								articulo.setFec_fin(null);
							}
						}
						if (j == 8) {
							if (contenidoCelda != null) {
								if (contenidoCelda.length() > 0) {
									if (!contenidoCelda.equals("-")) {
										articulo.setNom_fec_respaldo(contenidoCelda);
									} else {
										articulo.setNom_fec_respaldo(null);
									}
								} else {
									articulo.setNom_fec_respaldo(null);
								}
							} else {
								articulo.setNom_fec_respaldo(null);
							}
						}
						if (j == 9) {
							if (contenidoCelda != null) {
								if (contenidoCelda.length() > 0) {
									if (!contenidoCelda.equals("-")) {
										articulo.setNom_tip_respaldo(contenidoCelda);
									} else {
										articulo.setNom_tip_respaldo(null);
									}
								} else {
									articulo.setNom_tip_respaldo(null);
								}
							} else {
								articulo.setNom_tip_respaldo(null);
							}
						}
						if (j == 10) {
							if (contenidoCelda != null) {
								if (contenidoCelda.length() > 0) {
									if (!contenidoCelda.equals("-")) {
										articulo.setId_contenedor(contenidoCelda);
									} else {
										articulo.setId_contenedor("");
									}
								} else {
									articulo.setId_contenedor("");
								}
							} else {
								articulo.setId_contenedor("");
							}
						}
						if (j == 11) {
							if (contenidoCelda != null) {
								if (contenidoCelda.length() > 0) {
									if (!contenidoCelda.equals("-")) {
										articulo.setNom_ubicacion(contenidoCelda);
									} else {
										articulo.setNom_ubicacion(null);
									}
								} else {
									articulo.setNom_ubicacion(null);
								}
							} else {
								articulo.setNom_ubicacion(null);
							}
						}
						j++;
					}
					listaArticulos.add(articulo);
				}
				i++;
				id_articulo++;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(listaArticulos);
		return listaArticulos;
	}

	public void dibujarListaConsulta()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		Listitem lItem;
		Listcell lCell;
		for (int i = 0; i < listaArticulos.size(); i++) {
			lItem = new Listitem();
			Datebox dtxFechaInicio, dtxFechaFin;
			Textbox txtCodigo, txtDescripcion, txtContenedor;
			Combobox cmbCapacidad, cmbCategoria, cmbCrearCaja, cmbRegistraFechaRespaldo, cmbEsFecha, cmbFechaRespaldo,
					cmbTipoRespaldo, cmbUbicacion;
			Comboitem cItem;
			Button btnEliminar;
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
					listaArticulos.remove(lbxArticulos.getIndexOfItem(lbxArticulos.getItemAtIndex(lItem.getIndex())));
					lbxArticulos.removeItemAt(lItem.getIndex());
				}
			});
			lCell.appendChild(btnEliminar);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ITEM */
			lCell = new Listcell();
			lCell.setLabel(String.valueOf(i + 1));
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* CODIGO */
			lCell = new Listcell();
			txtCodigo = new Textbox();
			txtCodigo.setWidth("180px");
			txtCodigo.setMaxlength(50);
			// txtCodigo.setRows(3);
			txtCodigo.setStyle("resize: none;");
			txtCodigo.setInplace(true);
			txtCodigo.setReadonly(false);
			if (listaArticulos.get(i).getId_contenedor() == null) {
				txtCodigo.setText("");
			} else {
				txtCodigo.setText(String.valueOf(listaArticulos.get(i).getCod_articulo()));
			}
			txtCodigo.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					txtCodigo.setText(txtCodigo.getText().toUpperCase().trim());
				}
			});
			lCell.appendChild(txtCodigo);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* DESCRIPCION */
			lCell = new Listcell();
			txtDescripcion = new Textbox();
			txtDescripcion.setWidth("180px");
			txtDescripcion.setMaxlength(2000);
			txtDescripcion.setRows(3);
			txtDescripcion.setStyle("resize: none;");
			txtDescripcion.setInplace(true);
			txtDescripcion.setReadonly(false);
			if (listaArticulos.get(i).getId_contenedor() == null) {
				txtDescripcion.setText("");
			} else {
				txtDescripcion.setText(String.valueOf(listaArticulos.get(i).getDes_articulo()));
			}
			txtDescripcion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					txtDescripcion.setText(txtDescripcion.getText().toUpperCase().trim());
				}
			});
			lCell.appendChild(txtDescripcion);
			lItem.appendChild(lCell);
			/* CATEGORIA */
			lCell = new Listcell();
			cmbCategoria = new Combobox();
			cItem = new Comboitem();
			cItem.setLabel("CINTA");
			cItem.setValue(1);
			cItem.setTooltiptext("CINTA");
			cmbCategoria.appendChild(cItem);
			cmbCategoria.setReadonly(true);
			cmbCategoria.setWidth("100px");
			cmbCategoria.setInplace(true);
			cmbCategoria.setText(listaArticulos.get(i).getNom_categoria());
			cmbCategoria.setStyle("text-align: center !important; font-weight: normal !important;");
			cmbCategoria.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					if (cmbCategoria.getSelectedItem() == null) {
						return;
					}
					Listitem lItem;
					Listcell lCell;
					Combobox cmbCaja;
					lItem = (Listitem) cmbCategoria.getParent().getParent();
					lCell = (Listcell) lItem.getChildren().get(5);
					cmbCaja = (Combobox) lCell.getChildren().get(0);
					if (Long.valueOf(cmbCategoria.getSelectedItem().getValue().toString()) == 1) {
						cmbCaja.setDisabled(false);
						cmbCaja.setText("NO");
					} else {
						cmbCaja.setDisabled(true);
						cmbCaja.setText("NO");
					}
				}
			});
			lCell.appendChild(cmbCategoria);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* DESEA CREAR CAJA */
			lCell = new Listcell();
			cmbCrearCaja = new Combobox();
			cItem = new Comboitem();
			cItem.setLabel("SI");
			cItem.setValue("S");
			cItem.setTooltiptext("SI");
			cmbCrearCaja.appendChild(cItem);
			cItem = new Comboitem();
			cItem.setLabel("NO");
			cItem.setValue("N");
			cItem.setTooltiptext("NO");
			cmbCrearCaja.appendChild(cItem);
			cmbCrearCaja.setReadonly(true);
			cmbCrearCaja.setWidth("80px");
			cmbCrearCaja.setInplace(true);
			cmbCrearCaja.setStyle("text-align: center !important; font-weight: normal !important;");
			if (listaArticulos.get(i).getNom_categoria() != null) {
				if (listaArticulos.get(i).getNom_categoria().length() > 0) {
					if (listaArticulos.get(i).getNom_categoria().equals("CINTA")) {
						cmbCrearCaja.setText("SI");
					} else {
						cmbCrearCaja.setText("NO");
					}
				} else {
					cmbCrearCaja.setText("NO");
				}
			} else {
				cmbCrearCaja.setText("NO");
			}
			lCell.appendChild(cmbCrearCaja);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* CAPACIDAD */
			lCell = new Listcell();
			cmbCapacidad = new Combobox();
			for (int j = 0; j < listaCapacidad.size(); j++) {
				cItem = new Comboitem();
				cItem.setLabel(listaCapacidad.get(j).getNom_capacidad());
				cItem.setValue(listaCapacidad.get(j).getId_capacidad());
				cItem.setTooltiptext(listaCapacidad.get(j).getNom_capacidad());
				cmbCapacidad.appendChild(cItem);
			}
			cmbCapacidad.setReadonly(true);
			cmbCapacidad.setWidth("100px");
			cmbCapacidad.setInplace(true);
			cmbCapacidad.setText(listaArticulos.get(i).getNom_capacidad());
			cmbCapacidad.setStyle("text-align: center !important; font-weight: normal !important;");
			lCell.appendChild(cmbCapacidad);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* DESEA REGISTRAR LA FECHA DE RESPALDO */
			lCell = new Listcell();
			cmbRegistraFechaRespaldo = new Combobox();
			cItem = new Comboitem();
			cItem.setLabel("SI");
			cItem.setValue("S");
			cItem.setTooltiptext("SI");
			cmbRegistraFechaRespaldo.appendChild(cItem);
			cItem = new Comboitem();
			cItem.setLabel("NO");
			cItem.setValue("N");
			cItem.setTooltiptext("NO");
			cmbRegistraFechaRespaldo.appendChild(cItem);
			cmbRegistraFechaRespaldo.setReadonly(true);
			cmbRegistraFechaRespaldo.setWidth("80px");
			cmbRegistraFechaRespaldo.setInplace(true);
			cmbRegistraFechaRespaldo.setText(listaArticulos.get(i).getSi_ing_fec_inicio_fin());
			cmbRegistraFechaRespaldo.setStyle("text-align: center !important; font-weight: normal !important;");
			cmbRegistraFechaRespaldo.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					if (cmbRegistraFechaRespaldo.getSelectedItem() == null) {
						return;
					}
					Listitem lItem;
					Listcell lCell;
					Datebox dtxInicio, dtxFin;
					Combobox cmbFecha, cmbRespaldo;
					lItem = (Listitem) cmbRegistraFechaRespaldo.getParent().getParent();
					lCell = (Listcell) lItem.getChildren().get(8);
					cmbFecha = (Combobox) lCell.getChildren().get(0);
					lCell = (Listcell) lItem.getChildren().get(9);
					dtxInicio = (Datebox) lCell.getChildren().get(0);
					lCell = (Listcell) lItem.getChildren().get(10);
					dtxFin = (Datebox) lCell.getChildren().get(0);
					lCell = (Listcell) lItem.getChildren().get(11);
					cmbRespaldo = (Combobox) lCell.getChildren().get(0);
					if (cmbRegistraFechaRespaldo.getSelectedItem().getValue().toString().equals("N")) {
						cmbFecha.setDisabled(true);
						cmbFecha.setText("NO");
						cmbRespaldo.setDisabled(true);
						cmbRespaldo.setText("");
						dtxInicio.setDisabled(true);
						dtxInicio.setValue(null);
						dtxFin.setDisabled(true);
						dtxFin.setValue(null);
					} else {
						cmbFecha.setDisabled(false);
						if (cmbFecha.getSelectedItem().getValue().toString().equals("N")) {
							cmbRespaldo.setDisabled(false);
							cmbRespaldo.setText("");
							dtxInicio.setDisabled(true);
							dtxInicio.setValue(null);
							dtxFin.setDisabled(true);
							dtxFin.setValue(null);
						} else {
							cmbRespaldo.setDisabled(true);
							cmbRespaldo.setText("");
							dtxInicio.setDisabled(false);
							dtxInicio.setValue(null);
							dtxFin.setDisabled(false);
							dtxFin.setValue(null);
						}
					}
				}
			});
			lCell.appendChild(cmbRegistraFechaRespaldo);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ES DEL TIPO FECHA */
			lCell = new Listcell();
			cmbEsFecha = new Combobox();
			cItem = new Comboitem();
			cItem.setLabel("SI");
			cItem.setValue("S");
			cItem.setTooltiptext("SI");
			cmbEsFecha.appendChild(cItem);
			cItem = new Comboitem();
			cItem.setLabel("NO");
			cItem.setValue("N");
			cItem.setTooltiptext("NO");
			cmbEsFecha.appendChild(cItem);
			cmbEsFecha.setReadonly(true);
			cmbEsFecha.setWidth("80px");
			cmbEsFecha.setInplace(true);
			cmbEsFecha.setText(listaArticulos.get(i).getEs_fecha());
			cmbEsFecha.setStyle("text-align: center !important; font-weight: normal !important;");
			if (listaArticulos.get(i).getSi_ing_fec_inicio_fin() != null) {
				if (listaArticulos.get(i).getSi_ing_fec_inicio_fin().equals("SI")) {
					cmbEsFecha.setDisabled(false);
				} else {
					cmbEsFecha.setDisabled(true);
				}
			} else {
				cmbEsFecha.setDisabled(true);
			}
			cmbEsFecha.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					if (cmbEsFecha.getSelectedItem() == null) {
						return;
					}
					Listitem lItem;
					Listcell lCell;
					Datebox dtxInicio, dtxFin;
					Combobox cmbRegistraFechaRespaldo, cmbRespaldo;
					lItem = (Listitem) cmbEsFecha.getParent().getParent();
					lCell = (Listcell) lItem.getChildren().get(7);
					cmbRegistraFechaRespaldo = (Combobox) lCell.getChildren().get(0);
					lCell = (Listcell) lItem.getChildren().get(9);
					dtxInicio = (Datebox) lCell.getChildren().get(0);
					lCell = (Listcell) lItem.getChildren().get(10);
					dtxFin = (Datebox) lCell.getChildren().get(0);
					lCell = (Listcell) lItem.getChildren().get(11);
					cmbRespaldo = (Combobox) lCell.getChildren().get(0);
					if (cmbRegistraFechaRespaldo.getSelectedItem().getValue().toString().equals("N")) {
						cmbEsFecha.setDisabled(true);
						cmbEsFecha.setText("NO");
						cmbRespaldo.setDisabled(true);
						cmbRespaldo.setText("");
						dtxInicio.setDisabled(true);
						dtxInicio.setValue(null);
						dtxFin.setDisabled(true);
						dtxFin.setValue(null);
					} else {
						cmbEsFecha.setDisabled(false);
						if (cmbEsFecha.getSelectedItem().getValue().toString().equals("N")) {
							cmbRespaldo.setDisabled(false);
							cmbRespaldo.setText("");
							dtxInicio.setDisabled(true);
							dtxInicio.setValue(null);
							dtxFin.setDisabled(true);
							dtxFin.setValue(null);
						} else {
							cmbRespaldo.setDisabled(true);
							cmbRespaldo.setText("");
							dtxInicio.setDisabled(false);
							dtxInicio.setValue(null);
							dtxFin.setDisabled(false);
							dtxFin.setValue(null);
						}
					}
				}
			});
			lCell.appendChild(cmbEsFecha);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* FECHA INICIO */
			lCell = new Listcell();
			dtxFechaInicio = new Datebox();
			dtxFechaInicio.setWidth("120px");
			dtxFechaInicio.setInplace(true);
			dtxFechaInicio.setFormat("dd/MM/yyyy");
			if (listaArticulos.get(i).getSi_ing_fec_inicio_fin() != null
					&& listaArticulos.get(i).getEs_fecha() != null) {
				if (listaArticulos.get(i).getSi_ing_fec_inicio_fin().equals("SI")) {
					if (listaArticulos.get(i).getEs_fecha().equals("SI")) {
						dtxFechaInicio.setDisabled(false);
					} else {
						dtxFechaInicio.setDisabled(true);
					}
				} else {
					dtxFechaInicio.setDisabled(true);
				}
			} else {
				dtxFechaInicio.setDisabled(true);
			}
			dtxFechaInicio.setValue(listaArticulos.get(i).getFec_inicio());
			lCell.appendChild(dtxFechaInicio);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* FECHA FIN */
			lCell = new Listcell();
			dtxFechaFin = new Datebox();
			dtxFechaFin.setWidth("120px");
			dtxFechaFin.setInplace(true);
			dtxFechaFin.setFormat("dd/MM/yyyy");
			dtxFechaFin.setValue(listaArticulos.get(i).getFec_fin());
			if (listaArticulos.get(i).getSi_ing_fec_inicio_fin() != null
					&& listaArticulos.get(i).getEs_fecha() != null) {
				if (listaArticulos.get(i).getSi_ing_fec_inicio_fin().equals("SI")) {
					if (listaArticulos.get(i).getEs_fecha().equals("SI")) {
						dtxFechaFin.setDisabled(false);
					} else {
						dtxFechaFin.setDisabled(true);
					}
				} else {
					dtxFechaFin.setDisabled(true);
				}
			} else {
				dtxFechaFin.setDisabled(true);
			}
			lCell.appendChild(dtxFechaFin);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* FECHA DE RESPALDO */
			lCell = new Listcell();
			cmbFechaRespaldo = new Combobox();
			for (int j = 0; j < listaRespaldo1.size(); j++) {
				cItem = new Comboitem();
				cItem.setLabel(listaRespaldo1.get(j).toNombreRespaldo());
				cItem.setValue(listaRespaldo1.get(j).getId_respaldo());
				cItem.setTooltiptext(listaRespaldo1.get(j).toNombreRespaldo());
				cmbFechaRespaldo.appendChild(cItem);
			}
			cmbFechaRespaldo.setReadonly(false);
			cmbFechaRespaldo.setWidth("300px");
			cmbFechaRespaldo.setInplace(true);
			cmbFechaRespaldo.setText(listaArticulos.get(i).getNom_fec_respaldo());
			cmbFechaRespaldo.setStyle("text-align: center !important; font-weight: normal !important;");
			if (listaArticulos.get(i).getSi_ing_fec_inicio_fin() != null
					&& listaArticulos.get(i).getEs_fecha() != null) {
				if (listaArticulos.get(i).getSi_ing_fec_inicio_fin().equals("SI")) {
					if (listaArticulos.get(i).getEs_fecha().equals("SI")) {
						cmbFechaRespaldo.setDisabled(true);
					} else {
						cmbFechaRespaldo.setDisabled(false);
					}
				} else {
					cmbFechaRespaldo.setDisabled(true);
				}
			} else {
				cmbFechaRespaldo.setDisabled(true);
			}
			lCell.appendChild(cmbFechaRespaldo);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* TIPO DE RESPALDO */
			lCell = new Listcell();
			cmbTipoRespaldo = new Combobox();
			for (int j = 0; j < listaRespaldo.size(); j++) {
				cItem = new Comboitem();
				cItem.setLabel(listaRespaldo.get(j).toNombreRespaldo());
				cItem.setValue(listaRespaldo.get(j).getId_respaldo());
				cItem.setTooltiptext(listaRespaldo.get(j).toNombreRespaldo());
				cmbTipoRespaldo.appendChild(cItem);
			}
			cmbTipoRespaldo.setReadonly(false);
			cmbTipoRespaldo.setWidth("300px");
			cmbTipoRespaldo.setInplace(true);
			cmbTipoRespaldo.setText(listaArticulos.get(i).getNom_tip_respaldo());
			cmbTipoRespaldo.setStyle("text-align: center !important; font-weight: normal !important;");
			lCell.appendChild(cmbTipoRespaldo);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ID CONTENEDOR */
			lCell = new Listcell();
			txtContenedor = new Textbox();
			txtContenedor.setWidth("180px");
			txtContenedor.setMaxlength(2000);
			txtContenedor.setRows(3);
			txtContenedor.setStyle("resize: none;");
			txtContenedor.setInplace(true);
			txtContenedor.setReadonly(false);
			if (listaArticulos.get(i).getId_contenedor() == null) {
				txtContenedor.setText("");
			} else {
				txtContenedor.setText(String.valueOf(listaArticulos.get(i).getId_contenedor()));
			}
			txtContenedor.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					txtContenedor.setText(txtContenedor.getText().toUpperCase().trim());
				}
			});
			lCell.appendChild(txtContenedor);
			lItem.appendChild(lCell);
			/* UBICACION */
			lCell = new Listcell();
			cmbUbicacion = new Combobox();
			for (int j = 0; j < listaUbicacion.size(); j++) {
				cItem = new Comboitem();
				cItem.setLabel(listaUbicacion.get(j).toStringUbicacion());
				cItem.setValue(listaUbicacion.get(j).getId_ubicacion());
				cItem.setTooltiptext(listaUbicacion.get(j).toStringUbicacion());
				cmbUbicacion.appendChild(cItem);
			}
			cmbUbicacion.setReadonly(true);
			cmbUbicacion.setWidth("350px");
			cmbUbicacion.setInplace(true);
			cmbUbicacion.setText(listaArticulos.get(i).getNom_ubicacion());
			cmbUbicacion.setStyle("text-align: center !important; font-weight: normal !important;");
			lCell.appendChild(cmbUbicacion);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ANADIR ITEM A LISTBOX */
			lbxArticulos.appendChild(lItem);
		}
	}

	@Listen("onClick=#btnLimpiar")
	public void onClick$btnLimpiar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		borrarListaConsulta();
		listaArticulos = new ArrayList<modelo_articulo_dn>();
		media = null;
		btnCargar.setDisabled(false);
		txtRuta.setText("");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (media == null) {
			Messagebox.show("No se añadio algún archivo.", ".:: Cargar archivo ::.", Messagebox.OK,
					Messagebox.INFORMATION);
			return;
		}
		if ((media.getName().indexOf(".xlsx") == -1)) {
			Messagebox.show("El archivo debe tener extensión (xlsx).", ".:: Cargar archivo ::.", Messagebox.OK,
					Messagebox.INFORMATION);
			return;
		}
		if (lbxArticulos.getItemCount() == 0) {
			Messagebox.show("No se han añadio registros a la lista.", ".:: Cargar archivo ::.", Messagebox.OK,
					Messagebox.INFORMATION);
			return;
		}
		if (validarItemsEnLista(lbxArticulos) == true) {
			return;
		}
		if (validarSiTienenMismoCodigoYFechaInicioEnLista() == true) {
			Messagebox.show(informativos.getMensaje_informativo_66(), informativos.getMensaje_informativo_131(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (validarSiTienenMismoCodigoYFechaInicioYFechaFinEnLista() == true) {
			Messagebox.show(informativos.getMensaje_informativo_67(), informativos.getMensaje_informativo_131(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (validarSiTienenMismoCodigoYFechaInicioEnBD() == true) {
			Messagebox.show(informativos.getMensaje_informativo_69(), informativos.getMensaje_informativo_131(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (validarSiTienenMismoCodigoYFechaInicioYFechaFinEnBD() == true) {
			Messagebox.show(informativos.getMensaje_informativo_70(), informativos.getMensaje_informativo_131(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		Messagebox.show("Esta seguro de guardar los registros?", ".:: Cargar archivo ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_articulo_dn dao = new dao_articulo_dn();
							modelo_articulo_dn articulo = new modelo_articulo_dn();
							modelo_articulo_dn articulo1 = new modelo_articulo_dn();
							modelo_relacion_articulo_ubicacion_dn relacion_articulo_ubicacion = new modelo_relacion_articulo_ubicacion_dn();
							modelo_relacion_articulo_ubicacion_dn relacion_articulo_ubicacion1 = new modelo_relacion_articulo_ubicacion_dn();
							List<modelo_relacion_articulo_ubicacion_dn> listaRelacionArticulos = new ArrayList<modelo_relacion_articulo_ubicacion_dn>();
							List<modelo_relacion_articulo_ubicacion_dn> listaRelacionArticulos1 = new ArrayList<modelo_relacion_articulo_ubicacion_dn>();
							Combobox cmbCategoria, cmbRegistraCaja;
							Listcell lCell;
							int pos_ubicacion = 0;
							int se_crea_caja = 0;
							for (int i = 0; i < lbxArticulos.getItemCount(); i++) {
								articulo = new modelo_articulo_dn();
								articulo1 = new modelo_articulo_dn();
								relacion_articulo_ubicacion = new modelo_relacion_articulo_ubicacion_dn();
								relacion_articulo_ubicacion1 = new modelo_relacion_articulo_ubicacion_dn();
								listaRelacionArticulos = new ArrayList<modelo_relacion_articulo_ubicacion_dn>();
								listaRelacionArticulos1 = new ArrayList<modelo_relacion_articulo_ubicacion_dn>();
								se_crea_caja = 0;
								pos_ubicacion = 0;
								lCell = (Listcell) lbxArticulos.getItemAtIndex(i).getChildren().get(4);
								cmbCategoria = (Combobox) lCell.getChildren().get(0);
								lCell = (Listcell) lbxArticulos.getItemAtIndex(i).getChildren().get(5);
								cmbRegistraCaja = (Combobox) lCell.getChildren().get(0);
								/* Se inicializan los objetos articulos */
								articulo = listaArticulos
										.get(lbxArticulos.getIndexOfItem(lbxArticulos.getItemAtIndex(i)));
								articulo1 = listaArticulos
										.get(lbxArticulos.getIndexOfItem(lbxArticulos.getItemAtIndex(i)));
								articulo1 = articulo.clone1();
								articulo.setId_localidad(id_dc);
								articulo1.setId_localidad(id_dc);
								articulo.setUsu_ingresa(user);
								articulo1.setUsu_ingresa(articulo.getUsu_ingresa());
								articulo.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
								articulo1.setFec_ingresa(articulo.getFec_ingresa());
								if (Long.valueOf(cmbCategoria.getSelectedItem().getValue().toString()) == 1) {
									if (validarSiTienenMismoCodigoEnBD(articulo) == true) {
										articulo.setEst_articulo("PAC");
										articulo1.setEst_articulo("AE");
									} else {
										articulo.setEst_articulo("AE");
										articulo1.setEst_articulo(articulo.getEst_articulo());
									}
								} else {
									articulo.setEst_articulo("AE");
									articulo1.setEst_articulo(articulo.getEst_articulo());
								}
								/* Se inicializan los objetos relacion articulo ubicacion */
								relacion_articulo_ubicacion.setId_ubicacion(
										listaArticulos.get(lbxArticulos.getIndexOfItem(lbxArticulos.getItemAtIndex(i)))
												.getId_ubicacion());
								relacion_articulo_ubicacion1
										.setId_ubicacion(relacion_articulo_ubicacion.getId_ubicacion()); //
								relacion_articulo_ubicacion.setSto_articulo(1);
								relacion_articulo_ubicacion1
										.setSto_articulo(relacion_articulo_ubicacion.getSto_articulo()); //
								pos_ubicacion = consultasABaseDeDatos.posicionMaximaEnUbicacionDN(
										(listaArticulos.get(lbxArticulos.getIndexOfItem(lbxArticulos.getItemAtIndex(i)))
												.getId_ubicacion()))
										+ 1;
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
								if (cmbRegistraCaja.getSelectedItem().getValue().toString().equals("S")) {
									se_crea_caja = 1;
								} else {
									se_crea_caja = 0;
								}
								dao.insertarArticulo(articulo, listaRelacionArticulos, se_crea_caja, articulo1,
										listaRelacionArticulos1);
							}
							try {
								Messagebox.show("Los registros se guardaron correctamente.", ".:: Cargar archivo ::.",
										Messagebox.OK, Messagebox.EXCLAMATION);
								onClick$btnLimpiar();

							} catch (Exception e) {
								Messagebox.show(
										"Error al guardar los registros. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										".:: Cargar archivo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
							}
						}
					}
				});
	}

	public boolean validarItemsEnLista(Listbox _lbxArticulos)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean existe_error = false;
		@SuppressWarnings("unused")
		Combobox cmbCategoria, cmbRegistraCaja, cmbCapacidad, cmbRegistraFecha, cmbEsFecha, cmbFechaRespaldo,
				cmbTipoRespaldo, cmbUbicacion;
		@SuppressWarnings("unused")
		Textbox txtCodigo, txtDescripcion, txtContenedor;
		Datebox dtxFechaInicio, dtxFechaFin;
		Listcell lCell;
		_lbxArticulos.clearSelection();
		for (int i = 0; i < _lbxArticulos.getItemCount(); i++) {
			lCell = (Listcell) _lbxArticulos.getItemAtIndex(i).getChildren().get(2);
			txtCodigo = (Textbox) lCell.getChildren().get(0);
			lCell = (Listcell) _lbxArticulos.getItemAtIndex(i).getChildren().get(3);
			txtDescripcion = (Textbox) lCell.getChildren().get(0);
			lCell = (Listcell) _lbxArticulos.getItemAtIndex(i).getChildren().get(4);
			cmbCategoria = (Combobox) lCell.getChildren().get(0);
			lCell = (Listcell) _lbxArticulos.getItemAtIndex(i).getChildren().get(5);
			cmbRegistraCaja = (Combobox) lCell.getChildren().get(0);
			lCell = (Listcell) _lbxArticulos.getItemAtIndex(i).getChildren().get(6);
			cmbCapacidad = (Combobox) lCell.getChildren().get(0);
			lCell = (Listcell) _lbxArticulos.getItemAtIndex(i).getChildren().get(7);
			cmbRegistraFecha = (Combobox) lCell.getChildren().get(0);
			lCell = (Listcell) _lbxArticulos.getItemAtIndex(i).getChildren().get(8);
			cmbEsFecha = (Combobox) lCell.getChildren().get(0);
			lCell = (Listcell) _lbxArticulos.getItemAtIndex(i).getChildren().get(9);
			dtxFechaInicio = (Datebox) lCell.getChildren().get(0);
			lCell = (Listcell) _lbxArticulos.getItemAtIndex(i).getChildren().get(10);
			dtxFechaFin = (Datebox) lCell.getChildren().get(0);
			lCell = (Listcell) _lbxArticulos.getItemAtIndex(i).getChildren().get(11);
			cmbFechaRespaldo = (Combobox) lCell.getChildren().get(0);
			lCell = (Listcell) _lbxArticulos.getItemAtIndex(i).getChildren().get(12);
			cmbTipoRespaldo = (Combobox) lCell.getChildren().get(0);
			lCell = (Listcell) _lbxArticulos.getItemAtIndex(i).getChildren().get(13);
			txtContenedor = (Textbox) lCell.getChildren().get(0);
			lCell = (Listcell) _lbxArticulos.getItemAtIndex(i).getChildren().get(14);
			cmbUbicacion = (Combobox) lCell.getChildren().get(0);
			if (txtCodigo.getText().length() <= 0) {
				_lbxArticulos.clearSelection();
				_lbxArticulos.addItemToSelection(_lbxArticulos.getItemAtIndex(i));
				txtCodigo.setFocus(true);
				txtCodigo.setErrorMessage(validacion.getMensaje_validacion_1());
				existe_error = true;
				break;
			}
			if (txtDescripcion.getText().length() <= 0) {
				_lbxArticulos.clearSelection();
				_lbxArticulos.addItemToSelection(_lbxArticulos.getItemAtIndex(i));
				txtDescripcion.setFocus(true);
				txtDescripcion.setErrorMessage(validacion.getMensaje_validacion_2());
				existe_error = true;
				break;
			}
			if (cmbCategoria.getSelectedItem() == null) {
				_lbxArticulos.clearSelection();
				_lbxArticulos.addItemToSelection(_lbxArticulos.getItemAtIndex(i));
				cmbCategoria.setFocus(true);
				cmbCategoria.setErrorMessage(validacion.getMensaje_validacion_3());
				existe_error = true;
				break;
			}
			if (cmbRegistraCaja.getSelectedItem() == null) {
				_lbxArticulos.clearSelection();
				_lbxArticulos.addItemToSelection(_lbxArticulos.getItemAtIndex(i));
				cmbRegistraCaja.setFocus(true);
				cmbRegistraCaja.setErrorMessage(validacion.getMensaje_validacion_33());
				existe_error = true;
				break;
			}
			if (cmbCapacidad.getSelectedItem() == null) {
				_lbxArticulos.clearSelection();
				_lbxArticulos.addItemToSelection(_lbxArticulos.getItemAtIndex(i));
				cmbCapacidad.setFocus(true);
				cmbCapacidad.setErrorMessage(validacion.getMensaje_validacion_5());
				existe_error = true;
				break;
			}
			if (cmbRegistraFecha.getSelectedItem() == null) {
				_lbxArticulos.clearSelection();
				_lbxArticulos.addItemToSelection(_lbxArticulos.getItemAtIndex(i));
				cmbRegistraFecha.setFocus(true);
				cmbRegistraFecha.setErrorMessage(validacion.getMensaje_validacion_33());
				existe_error = true;
				break;
			}
			if (cmbEsFecha.getSelectedItem() == null) {
				_lbxArticulos.clearSelection();
				_lbxArticulos.addItemToSelection(_lbxArticulos.getItemAtIndex(i));
				cmbEsFecha.setFocus(true);
				cmbEsFecha.setErrorMessage(validacion.getMensaje_validacion_33());
				existe_error = true;
				break;
			}
			if (cmbRegistraFecha.getSelectedItem().getValue().toString().equals("S")) {
				if (cmbEsFecha.getSelectedItem().getValue().toString().equals("S")) {
					if (dtxFechaInicio.getValue() == null) {
						_lbxArticulos.clearSelection();
						_lbxArticulos.addItemToSelection(_lbxArticulos.getItemAtIndex(i));
						dtxFechaInicio.setFocus(true);
						dtxFechaInicio.setErrorMessage(validacion.getMensaje_validacion_4());
						existe_error = true;
						break;
					}
				}
			}
			if (cmbRegistraFecha.getSelectedItem().getValue().toString().equals("S")) {
				if (cmbEsFecha.getSelectedItem().getValue().toString().equals("S")) {
					if (dtxFechaInicio.getValue() != null) {
						if (dtxFechaFin.getValue() != null) {
							Date d1 = fechas.obtenerFechaArmada(dtxFechaInicio.getValue(),
									dtxFechaInicio.getValue().getMonth(), dtxFechaInicio.getValue().getDate(),
									dtxFechaInicio.getValue().getHours(), dtxFechaInicio.getValue().getMinutes(), 0);
							Date d2 = fechas.obtenerFechaArmada(dtxFechaFin.getValue(),
									dtxFechaFin.getValue().getMonth(), dtxFechaFin.getValue().getDate(),
									dtxFechaFin.getValue().getHours(), dtxFechaFin.getValue().getMinutes(), 0);
							if (d2.before(d1)) {
								_lbxArticulos.clearSelection();
								_lbxArticulos.addItemToSelection(_lbxArticulos.getItemAtIndex(i));
								dtxFechaInicio.setFocus(true);
								dtxFechaInicio.setErrorMessage(validacion.getMensaje_validacion_10());
								existe_error = true;
								break;
							}
						}
					}
				}
			}
			if (cmbRegistraFecha.getSelectedItem().getValue().toString().equals("S")) {
				if (cmbEsFecha.getSelectedItem().getValue().toString().equals("N")) {
					if (cmbFechaRespaldo.getSelectedItem() == null) {
						_lbxArticulos.clearSelection();
						_lbxArticulos.addItemToSelection(_lbxArticulos.getItemAtIndex(i));
						cmbFechaRespaldo.setFocus(true);
						cmbFechaRespaldo.setErrorMessage(validacion.getMensaje_validacion_33());
						existe_error = true;
						break;
					}
				}
			}
			if (cmbUbicacion.getSelectedItem() == null) {
				_lbxArticulos.clearSelection();
				_lbxArticulos.addItemToSelection(_lbxArticulos.getItemAtIndex(i));
				cmbUbicacion.setFocus(true);
				cmbUbicacion.setErrorMessage(validacion.getMensaje_validacion_6());
				existe_error = true;
				break;
			}
			/*
			 * Se valida que no se registre el articulo en una ubicacion no permitida
			 */
			String nom_ubicacion = "";
			for (int j = 0; j < listaUbicacion.size(); j++) {
				if (listaUbicacion.get(j).getId_ubicacion() == Long
						.valueOf(cmbUbicacion.getSelectedItem().getValue().toString())) {
					nom_ubicacion = listaUbicacion.get(j).toStringUbicacion();
					j = listaUbicacion.size() + 1;
				}
			}
			if (cmbRegistraCaja.getSelectedItem().getValue().toString().equals("S")) {
				if (Long.valueOf(cmbUbicacion.getSelectedItem().getValue().toString()) <= 3
						|| Long.valueOf(cmbUbicacion.getSelectedItem().getValue().toString()) >= 134) {
					Messagebox.show(informativos.getMensaje_informativo_120().replace("?1", nom_ubicacion),
							informativos.getMensaje_informativo_131(), Messagebox.OK, Messagebox.EXCLAMATION);
					_lbxArticulos.clearSelection();
					_lbxArticulos.addItemToSelection(_lbxArticulos.getItemAtIndex(i));
					existe_error = true;
					break;
				}
			}
			if (Long.valueOf(cmbCategoria.getSelectedItem().getValue().toString()) == 2) {
				if (Long.valueOf(cmbUbicacion.getSelectedItem().getValue().toString()) <= 3
						|| Long.valueOf(cmbUbicacion.getSelectedItem().getValue().toString()) >= 134) {
					Messagebox.show(informativos.getMensaje_informativo_120().replace("?1", nom_ubicacion),
							informativos.getMensaje_informativo_131(), Messagebox.OK, Messagebox.EXCLAMATION);
					_lbxArticulos.clearSelection();
					_lbxArticulos.addItemToSelection(_lbxArticulos.getItemAtIndex(i));
					existe_error = true;
					break;
				}
			}
			/*
			 * Se valida que no se supere la capacidad permitida en la ubicacion
			 * seleccionada
			 */
			long id_ubicacion = Long.valueOf(cmbUbicacion.getSelectedItem().getValue().toString());
			int totalItemsEnUbicacionSinCaja = 0, totalItemsEnUbicacionConCaja = 0;
			Listcell lCell2;
			Combobox cmbUbicacion2;
			for (int k = _lbxArticulos.getItemCount() - 1; k >= 0; k--) {
				lCell2 = (Listcell) _lbxArticulos.getItemAtIndex(k).getChildren().get(14);
				cmbUbicacion2 = (Combobox) lCell2.getChildren().get(0);
				if (Long.valueOf(cmbUbicacion.getSelectedItem().getValue().toString()) == Long
						.valueOf(cmbUbicacion2.getSelectedItem().getValue().toString())) {
					if (Long.valueOf(cmbUbicacion.getSelectedItem().getValue().toString()) == id_ubicacion) {
						if (cmbRegistraCaja.getSelectedItem().getValue().toString().equals("S")) {
							totalItemsEnUbicacionConCaja = totalItemsEnUbicacionConCaja + 2;
						} else {
							totalItemsEnUbicacionSinCaja++;
						}
					}
				}
			}
			// System.out.println(cmbUbicacion.getSelectedItem().getLabel());
			// System.out.println(totalItemsEnUbicacionSinCaja);
			// System.out.println(totalItemsEnUbicacionConCaja);
			String seValidaCapacidad = consultasABaseDeDatos.seValidaCapacidadEnUbicacionDN(id_ubicacion);
			if (seValidaCapacidad.equals("S")) {
				int capacidadMaxima = consultasABaseDeDatos.capacidadMaximaEnUbicacionDN(id_ubicacion,
						seValidaCapacidad);
				int totalArticulos = consultasABaseDeDatos.totalArticulosEnUbicacionDN(id_ubicacion);
				if (cmbRegistraCaja.getSelectedItem().getValue().equals("N")) {
					if ((totalArticulos + totalItemsEnUbicacionSinCaja) > capacidadMaxima) {
						Messagebox.show(informativos.getMensaje_informativo_132().replace("?1", nom_ubicacion),
								informativos.getMensaje_informativo_131(), Messagebox.OK, Messagebox.INFORMATION);
						_lbxArticulos.clearSelection();
						_lbxArticulos.addItemToSelection(_lbxArticulos.getItemAtIndex(i));
						existe_error = true;
						break;
					}
				} else {
					if ((totalArticulos + totalItemsEnUbicacionConCaja) > capacidadMaxima) {
						Messagebox.show(informativos.getMensaje_informativo_133().replace("?1", nom_ubicacion),
								informativos.getMensaje_informativo_131(), Messagebox.OK, Messagebox.INFORMATION);
						_lbxArticulos.clearSelection();
						_lbxArticulos.addItemToSelection(_lbxArticulos.getItemAtIndex(i));
						existe_error = true;
						break;
					}
				}
			}
			/* ALMACENO MIS NUEVOS VALORES EN LA LISTA DE ARTICULOS */
			listaArticulos.get(lbxArticulos.getIndexOfItem(lbxArticulos.getItemAtIndex(i)))
					.setCod_articulo(txtCodigo.getText());
			listaArticulos.get(lbxArticulos.getIndexOfItem(lbxArticulos.getItemAtIndex(i)))
					.setDes_articulo(txtDescripcion.getText());
			listaArticulos.get(lbxArticulos.getIndexOfItem(lbxArticulos.getItemAtIndex(i)))
					.setId_categoria(Long.valueOf(cmbCategoria.getSelectedItem().getValue().toString()));
			listaArticulos.get(lbxArticulos.getIndexOfItem(lbxArticulos.getItemAtIndex(i)))
					.setId_capacidad(Long.valueOf(cmbCapacidad.getSelectedItem().getValue().toString()));
			listaArticulos.get(lbxArticulos.getIndexOfItem(lbxArticulos.getItemAtIndex(i)))
					.setSi_ing_fec_inicio_fin(cmbRegistraFecha.getSelectedItem().getValue().toString());
			listaArticulos.get(lbxArticulos.getIndexOfItem(lbxArticulos.getItemAtIndex(i)))
					.setEs_fecha(cmbEsFecha.getSelectedItem().getValue().toString());
			if (dtxFechaInicio.getValue() != null) {
				listaArticulos.get(lbxArticulos.getIndexOfItem(lbxArticulos.getItemAtIndex(i)))
						.setFec_inicio(fechas.obtenerTimestampDeDate(dtxFechaInicio.getValue()));
			} else {
				listaArticulos.get(lbxArticulos.getIndexOfItem(lbxArticulos.getItemAtIndex(i))).setFec_inicio(null);
			}
			if (dtxFechaFin.getValue() != null) {
				listaArticulos.get(lbxArticulos.getIndexOfItem(lbxArticulos.getItemAtIndex(i)))
						.setFec_fin(fechas.obtenerTimestampDeDate(dtxFechaFin.getValue()));
			} else {
				listaArticulos.get(lbxArticulos.getIndexOfItem(lbxArticulos.getItemAtIndex(i))).setFec_fin(null);
			}
			if (cmbFechaRespaldo.getSelectedItem() != null) {
				listaArticulos.get(lbxArticulos.getIndexOfItem(lbxArticulos.getItemAtIndex(i)))
						.setId_fec_respaldo(Long.valueOf(cmbFechaRespaldo.getSelectedItem().getValue().toString()));
			} else {
				listaArticulos.get(lbxArticulos.getIndexOfItem(lbxArticulos.getItemAtIndex(i))).setId_fec_respaldo(0);
			}
			if (cmbTipoRespaldo.getSelectedItem() != null) {
				listaArticulos.get(lbxArticulos.getIndexOfItem(lbxArticulos.getItemAtIndex(i)))
						.setId_tip_respaldo(Long.valueOf(cmbTipoRespaldo.getSelectedItem().getValue().toString()));
			} else {
				listaArticulos.get(lbxArticulos.getIndexOfItem(lbxArticulos.getItemAtIndex(i))).setId_tip_respaldo(0);
			}
			if (txtContenedor.getText().length() > 0) {
				listaArticulos.get(lbxArticulos.getIndexOfItem(lbxArticulos.getItemAtIndex(i)))
						.setId_contenedor(txtContenedor.getText());
			} else {
				listaArticulos.get(lbxArticulos.getIndexOfItem(lbxArticulos.getItemAtIndex(i))).setId_contenedor(null);
			}
			listaArticulos.get(lbxArticulos.getIndexOfItem(lbxArticulos.getItemAtIndex(i)))
					.setId_ubicacion(Long.valueOf(cmbUbicacion.getSelectedItem().getValue().toString()));
		}
		return existe_error;
	}

	public boolean validarSiTienenMismoCodigoYFechaInicioEnLista() {
		boolean tienen_mismos_datos = false;
		for (int i = 0; i < listaArticulos.size(); i++) {
			if (listaArticulos.get(i).getSi_ing_fec_inicio_fin().equals("S")) {
				if (listaArticulos.get(i).getEs_fecha().equals("S")) {
					if (listaArticulos.get(i).getFec_fin() == null) {
						for (int j = listaArticulos.size() - 1; j >= 0; j--) {
							if (listaArticulos.get(i).getId_articulo() != listaArticulos.get(j).getId_articulo()) {
								if (listaArticulos.get(j).getSi_ing_fec_inicio_fin().equals("S")) {
									if (listaArticulos.get(j).getEs_fecha().equals("S")) {
										if (listaArticulos.get(j).getFec_fin() == null) {
											if (listaArticulos.get(i).getCod_articulo()
													.equals(listaArticulos.get(j).getCod_articulo())
													&& listaArticulos.get(i).getFec_inicio()
															.equals(listaArticulos.get(j).getFec_inicio())) {
												lbxArticulos.clearSelection();
												lbxArticulos.addItemToSelection(lbxArticulos.getItemAtIndex(i));
												lbxArticulos.addItemToSelection(lbxArticulos.getItemAtIndex(j));
												tienen_mismos_datos = true;
												i = listaArticulos.size();
												j = 0;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return tienen_mismos_datos;
	}

	public boolean validarSiTienenMismoCodigoYFechaInicioYFechaFinEnLista() {
		boolean tienen_mismos_datos = false;
		for (int i = 0; i < listaArticulos.size(); i++) {
			if (listaArticulos.get(i).getSi_ing_fec_inicio_fin().equals("S")) {
				if (listaArticulos.get(i).getEs_fecha().equals("S")) {
					if (listaArticulos.get(i).getFec_fin() != null) {
						for (int j = listaArticulos.size() - 1; j >= 0; j--) {
							if (listaArticulos.get(i).getId_articulo() != listaArticulos.get(j).getId_articulo()) {
								if (listaArticulos.get(j).getSi_ing_fec_inicio_fin().equals("S")) {
									if (listaArticulos.get(j).getEs_fecha().equals("S")) {
										if (listaArticulos.get(i).getFec_fin() != null) {
											if (listaArticulos.get(i).getCod_articulo()
													.equals(listaArticulos.get(j).getCod_articulo())
													&& listaArticulos.get(i).getFec_inicio()
															.equals(listaArticulos.get(j).getFec_inicio())
													&& listaArticulos.get(i).getFec_fin()
															.equals(listaArticulos.get(j).getFec_fin())) {
												lbxArticulos.clearSelection();
												lbxArticulos.addItemToSelection(lbxArticulos.getItemAtIndex(i));
												lbxArticulos.addItemToSelection(lbxArticulos.getItemAtIndex(j));
												tienen_mismos_datos = true;
												i = listaArticulos.size();
												j = 0;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return tienen_mismos_datos;
	}

	public boolean validarSiTienenMismoCodigoYFechaInicioEnBD()
			throws ClassNotFoundException, FileNotFoundException, IOException {
		boolean tienen_mismos_datos = false;
		List<modelo_articulo_dn> _listaArticulos = new ArrayList<modelo_articulo_dn>();
		_listaArticulos = consultasABaseDeDatos.cargarArticulosDN("", id_dc, "0", 2, 0, "A", "0");
		for (int i = 0; i < listaArticulos.size(); i++) {
			if (listaArticulos.get(i).getSi_ing_fec_inicio_fin().equals("S")) {
				if (listaArticulos.get(i).getEs_fecha().equals("S")) {
					if (listaArticulos.get(i).getFec_fin() == null) {
						for (int j = _listaArticulos.size() - 1; j >= 0; j--) {
							if (listaArticulos.get(i).getId_articulo() != _listaArticulos.get(j).getId_articulo()) {
								if (_listaArticulos.get(j).getSi_ing_fec_inicio_fin().equals("S")) {
									if (_listaArticulos.get(j).getEs_fecha().equals("S")) {
										if (_listaArticulos.get(j).getFec_fin() == null) {
											if (_listaArticulos.get(j).getId_categoria() == 1) {
												if (listaArticulos.get(i).getCod_articulo()
														.equals(_listaArticulos.get(j).getCod_articulo())
														&& listaArticulos.get(i).getFec_inicio()
																.equals(_listaArticulos.get(j).getFec_inicio())) {
													lbxArticulos.clearSelection();
													lbxArticulos.addItemToSelection(lbxArticulos.getItemAtIndex(i));
													tienen_mismos_datos = true;
													i = listaArticulos.size();
													j = 0;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return tienen_mismos_datos;
	}

	public boolean validarSiTienenMismoCodigoYFechaInicioYFechaFinEnBD()
			throws ClassNotFoundException, FileNotFoundException, IOException {
		boolean tienen_mismos_datos = false;
		List<modelo_articulo_dn> _listaArticulos = new ArrayList<modelo_articulo_dn>();
		_listaArticulos = consultasABaseDeDatos.cargarArticulosDN("", id_dc, "0", 2, 0, "A", "0");
		for (int i = 0; i < listaArticulos.size(); i++) {
			if (listaArticulos.get(i).getSi_ing_fec_inicio_fin().equals("S")) {
				if (listaArticulos.get(i).getEs_fecha().equals("S")) {
					if (listaArticulos.get(i).getFec_fin() != null) {
						for (int j = _listaArticulos.size() - 1; j >= 0; j--) {
							if (listaArticulos.get(i).getId_articulo() != _listaArticulos.get(j).getId_articulo()) {
								if (_listaArticulos.get(j).getSi_ing_fec_inicio_fin().equals("S")) {
									if (_listaArticulos.get(j).getEs_fecha().equals("S")) {
										if (_listaArticulos.get(j).getId_categoria() == 1) {
											if (listaArticulos.get(i).getFec_fin() != null) {
												if (listaArticulos.get(i).getCod_articulo()
														.equals(_listaArticulos.get(j).getCod_articulo())
														&& listaArticulos.get(i).getFec_inicio()
																.equals(_listaArticulos.get(j).getFec_inicio())
														&& listaArticulos.get(i).getFec_fin()
																.equals(_listaArticulos.get(j).getFec_fin())) {
													lbxArticulos.clearSelection();
													lbxArticulos.addItemToSelection(lbxArticulos.getItemAtIndex(i));
													tienen_mismos_datos = true;
													i = listaArticulos.size();
													j = 0;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return tienen_mismos_datos;
	}

	public boolean validarSiTienenMismoCodigoEnBD(modelo_articulo_dn articulo)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		boolean tienen_mismos_datos = false;
		List<modelo_articulo_dn> _listaArticulos = new ArrayList<modelo_articulo_dn>();
		_listaArticulos = consultasABaseDeDatos.cargarArticulosDN("", id_dc, "0", 2, 0, "A", "0");
		if (articulo.getSi_ing_fec_inicio_fin().equals("N")) {
			if (articulo.getEs_fecha().equals("N")) {
				if (articulo.getFec_fin() == null) {
					for (int j = _listaArticulos.size() - 1; j >= 0; j--) {
						if (articulo.getId_articulo() != _listaArticulos.get(j).getId_articulo()) {
							if (_listaArticulos.get(j).getSi_ing_fec_inicio_fin().equals("N")) {
								if (_listaArticulos.get(j).getEs_fecha().equals("N")) {
									if (_listaArticulos.get(j).getFec_fin() == null) {
										if (_listaArticulos.get(j).getId_categoria() == 1) {
											if (articulo.getCod_articulo()
													.equals(_listaArticulos.get(j).getCod_articulo())) {
												tienen_mismos_datos = true;
												j = 0;
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return tienen_mismos_datos;
	}

}
