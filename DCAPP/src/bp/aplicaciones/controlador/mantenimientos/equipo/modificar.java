package bp.aplicaciones.controlador.mantenimientos.equipo;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.IOUtils;
import org.zkoss.image.AImage;
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
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Window;

import bp.aplicaciones.conexion.pingRemote;
import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.DAO.dao_equipo;
import bp.aplicaciones.mantenimientos.DAO.dao_etiquetado_pdu;
import bp.aplicaciones.mantenimientos.DAO.dao_etiquetado_poder;
import bp.aplicaciones.mantenimientos.DAO.dao_observacion;
import bp.aplicaciones.mantenimientos.DAO.dao_relacion_equipo_tipo_conector;
import bp.aplicaciones.mantenimientos.DAO.dao_usuario;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_equipo;
import bp.aplicaciones.mantenimientos.modelo.modelo_usuario;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mantenimientos.modelo.modelo_equipo;
import bp.aplicaciones.mantenimientos.modelo.modelo_estado_equipo;
import bp.aplicaciones.mantenimientos.modelo.modelo_etiquetado_pdu;
import bp.aplicaciones.mantenimientos.modelo.modelo_etiquetado_poder;
import bp.aplicaciones.mantenimientos.modelo.modelo_localidad;
import bp.aplicaciones.mantenimientos.modelo.modelo_marca;
import bp.aplicaciones.mantenimientos.modelo.modelo_modelo;
import bp.aplicaciones.mantenimientos.modelo.modelo_observacion;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_equipo_tipo_conector;
import bp.aplicaciones.mantenimientos.modelo.modelo_tipo_conector;

@SuppressWarnings({ "serial", "deprecation" })
public class modificar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zModificar;
	@Wire
	Button btnGrabar, btnCancelar, btnAnadirFuente, btnAnadirEtiquetaCableadoPoder, btnAnadirEtiquetaCableadoPdu,
			btnAnadirObservacion, btnImagenFrontal, btnImagenPosterior;
	@Wire
	Textbox txtId, txtNombreEquipo, txtSerie, txtCodigoActivo, txtCodigoProducto, txtNombreRack, txtNombreImagenFrontal,
			txtNombreImagenPosterior;
	@Wire
	Label lNombreEquipo, lSerie, lCodigoActivo, lCodigoProducto;
	@Wire
	Combobox cmbTipoEquipo, cmbMarca, cmbModelo, cmbRegistraFuente, cmbRegistraEtiquetaCableadoPoder,
			cmbRegistraEtiquetaCableadoPdu, cmbRegistraObservacion, cmbEstado, cmbEtiquetadoCableadoDatos,
			cmbPeinadoCableadoDatos, cmbConectadoATS;;
	@Wire
	Decimalbox decPeso, decProfundidad, decBTU;
	@Wire
	Listbox lbxFuentes, lbxEtiquetasCableadoPoder, lbxEtiquetasCableadoPdu, lbxObservaciones, lbxUrs;
	@Wire
	Tab tab1, tab2, tab3, tab4, tab5, tab6, tab7;
	@Wire
	Checkbox chkNombreEquipo, chkSerie, chkCodigoActivo, chkCodigoProducto, chkPeso, chkProfundidad, chkBTU;
	@Wire
	Datebox dtxFechaIngreso;
	@Wire
	Image imgFrontal, imgPosterior;

	org.zkoss.util.media.Media media = null;

	String path_i_f = "\\\\10.207.55.24\\Respaldos_Operacion\\Documentos_DCAPP\\Equipos\\DCP\\Imagenes_frontal\\";
	String path_i_p = "\\\\10.207.55.24\\Respaldos_Operacion\\Documentos_DCAPP\\Equipos\\DCP\\Imagenes_posterior\\";

	String ip = "10.207.55.24";

	int num_fuente = 5;

	Button dSolicitudes = (Button) Sessions.getCurrent().getAttribute("btn");
	modelo_equipo equipo = (modelo_equipo) Sessions.getCurrent().getAttribute("equipo");

	long id = 0;
	long id_mantenimiento = 26;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	validar_datos validar = new validar_datos();
	Fechas fechas = new Fechas();
	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();

	Informativos informativos = new Informativos();

	List<modelo_tipo_equipo> listaTiposEquipo = new ArrayList<modelo_tipo_equipo>();
	List<modelo_marca> listaMarcas = new ArrayList<modelo_marca>();
	List<modelo_estado_equipo> listaEstadosEquipo = new ArrayList<modelo_estado_equipo>();
	List<modelo_modelo> listaModelos = new ArrayList<modelo_modelo>();

	List<modelo_tipo_conector> listaTipoConector = new ArrayList<modelo_tipo_conector>();

	pingRemote ping = new pingRemote();

	@SuppressWarnings("rawtypes")
	private ListModel mySubModel;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		lbxFuentes.setEmptyMessage(informativos.getMensaje_informativo_2());
		lbxUrs.setEmptyMessage(informativos.getMensaje_informativo_2());
		lbxEtiquetasCableadoPoder.setEmptyMessage(informativos.getMensaje_informativo_2());
		lbxEtiquetasCableadoPdu.setEmptyMessage(informativos.getMensaje_informativo_2());
		lbxObservaciones.setEmptyMessage(informativos.getMensaje_informativo_2());
		txtNombreEquipo.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtNombreEquipo.setText(txtNombreEquipo.getText().toUpperCase().trim());
			}
		});
		txtSerie.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtSerie.setText(txtSerie.getText().toUpperCase().trim());
			}
		});
		txtCodigoActivo.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtCodigoActivo.setText(txtCodigoActivo.getText().toUpperCase().trim());
			}
		});
		txtCodigoProducto.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtCodigoProducto.setText(txtCodigoProducto.getText().toUpperCase().trim());
			}
		});
		cmbRegistraFuente.setSelectedIndex(1);
		cmbRegistraEtiquetaCableadoPoder.setSelectedIndex(1);
		cmbRegistraEtiquetaCableadoPdu.setSelectedIndex(1);
		cmbRegistraObservacion.setSelectedIndex(1);
		btnAnadirFuente.setDisabled(true);
		btnAnadirEtiquetaCableadoPoder.setDisabled(true);
		btnAnadirEtiquetaCableadoPdu.setDisabled(true);
		btnAnadirObservacion.setDisabled(false);
		cargarTiposEquipo();
		cargarMarcas();
		cargarModelos();
		cargarEstadosEquipo();
		cargarInformacionGeneral();
		cargarFuentes();
		cargarUrs();
		cargarEtiquetasCableadoPoder();
		cargarEtiquetasCableadoPdu();
		cargarObservaciones();
		if (ping.validarConexionEquipo(ip)) {
			cargarImagenFrontal();
			cargarImagenPosterior();
		} else {
			Clients.showNotification("No se puede establecer una conexión con el servidor " + ip + ".",
					Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 7000, true);
			return;
		}
	}

	public void cargarInformacionGeneral() {
		txtId.setText(String.valueOf(equipo.getId_equipo()));
		cmbTipoEquipo.setText(equipo.getTipo_equipo().getNom_tipo_equipo());
		cmbMarca.setText(equipo.getMarca().getNom_marca());
		cmbModelo.setText(equipo.getModelo().getNom_modelo());
		cmbEstado.setText(equipo.getEstado_equipo().getNom_estado());
		if (equipo.isEtiq_cds()) {
			cmbEtiquetadoCableadoDatos.setSelectedIndex(0);
		} else {
			cmbEtiquetadoCableadoDatos.setSelectedIndex(1);
		}
		if (equipo.isPein_cds()) {
			cmbPeinadoCableadoDatos.setSelectedIndex(0);
		} else {
			cmbPeinadoCableadoDatos.setSelectedIndex(1);
		}
		if (equipo.isCone_ats()) {
			cmbConectadoATS.setSelectedIndex(0);
		} else {
			cmbConectadoATS.setSelectedIndex(1);
		}
		if (equipo.getFec_ingreso() != null) {
			dtxFechaIngreso.setValue(equipo.getFec_ingreso());
		}
		if (equipo.isRegistra_fuente()) {
			cmbRegistraFuente.setSelectedIndex(0);
			btnAnadirFuente.setDisabled(false);
		} else {
			cmbRegistraFuente.setSelectedIndex(1);
			btnAnadirFuente.setDisabled(true);
		}
		if (equipo.getNom_equipo() == null) {
			txtNombreEquipo.setText("");
			chkNombreEquipo.setChecked(true);
			txtNombreEquipo.setDisabled(true);
		} else {
			if (equipo.getNom_equipo().length() <= 0 || equipo.getNom_equipo().equals("N/A")
					|| equipo.getNom_equipo().equals("N/D") || equipo.getNom_equipo().equals("NA")
					|| equipo.getNom_equipo().equals("ND")) {
				txtNombreEquipo.setText("");
				chkNombreEquipo.setChecked(true);
				txtNombreEquipo.setDisabled(true);
			} else {
				txtNombreEquipo.setText(equipo.getNom_equipo());
				chkNombreEquipo.setChecked(false);
				txtNombreEquipo.setDisabled(false);
			}
		}
		if (equipo.getSerie() == null) {
			txtSerie.setText("");
			chkSerie.setChecked(true);
			txtSerie.setDisabled(true);
		} else {
			if (equipo.getSerie().length() <= 0 || equipo.getSerie().equals("N/A") || equipo.getSerie().equals("N/D")
					|| equipo.getSerie().equals("NA") || equipo.getSerie().equals("ND")) {
				txtSerie.setText("");
				chkSerie.setChecked(true);
				txtSerie.setDisabled(true);
			} else {
				txtSerie.setText(equipo.getSerie());
				chkSerie.setChecked(false);
				txtSerie.setDisabled(false);
			}
		}
		if (equipo.getCod_activo() == null) {
			txtCodigoActivo.setText("");
			chkCodigoActivo.setChecked(true);
			txtCodigoActivo.setDisabled(true);
		} else {
			if (equipo.getCod_activo().length() <= 0 || equipo.getCod_activo().equals("N/A")
					|| equipo.getCod_activo().equals("N/D") || equipo.getCod_activo().equals("NA")
					|| equipo.getCod_activo().equals("ND")) {
				txtCodigoActivo.setText("");
				chkCodigoActivo.setChecked(true);
				txtCodigoActivo.setDisabled(true);
			} else {
				txtCodigoActivo.setText(equipo.getCod_activo());
				chkCodigoActivo.setChecked(false);
				txtCodigoActivo.setDisabled(false);
			}
		}
		if (equipo.getCod_producto() == null) {
			txtCodigoProducto.setText("");
			chkCodigoProducto.setChecked(true);
			txtCodigoProducto.setDisabled(true);
		} else {
			if (equipo.getCod_producto().length() <= 0 || equipo.getCod_producto().equals("N/A")
					|| equipo.getCod_producto().equals("N/D") || equipo.getCod_producto().equals("NA")
					|| equipo.getCod_producto().equals("ND")) {
				txtCodigoProducto.setText("");
				chkCodigoProducto.setChecked(true);
				txtCodigoProducto.setDisabled(true);
			} else {
				txtCodigoProducto.setText(equipo.getCod_producto());
				chkCodigoProducto.setChecked(false);
				txtCodigoProducto.setDisabled(false);
			}
		}
		if (equipo.getPeso() == 0) {
			decPeso.setText("");
			chkPeso.setChecked(true);
			decPeso.setDisabled(true);
		} else {
			decPeso.setValue(BigDecimal.valueOf(equipo.getPeso()));
			chkPeso.setChecked(false);
			decPeso.setDisabled(false);
		}
		if (equipo.getProfundidad() == 0) {
			decProfundidad.setText("");
			chkProfundidad.setChecked(true);
			decProfundidad.setDisabled(true);
		} else {
			decProfundidad.setValue(BigDecimal.valueOf(equipo.getProfundidad()));
			chkProfundidad.setChecked(false);
			decProfundidad.setDisabled(false);
		}
		if (equipo.getBtu() == 0) {
			decBTU.setText("");
			chkBTU.setChecked(true);
			decBTU.setDisabled(true);
		} else {
			decBTU.setValue(BigDecimal.valueOf(equipo.getBtu()));
			chkBTU.setChecked(false);
			decBTU.setDisabled(false);
		}
		lNombreEquipo.setValue(txtNombreEquipo.getText().length() + "/25");
		lSerie.setValue(txtSerie.getText().length() + "/25");
		lCodigoActivo.setValue(txtCodigoActivo.getText().length() + "/25");
		lCodigoProducto.setValue(txtCodigoProducto.getText().length() + "/25");
	}

	public void cargarFuentes() {
		listaTipoConector = consultasABaseDeDatos.consultarTipoConectores(0, 0, "", "", 0, 3);
		for (int i = 0; i < equipo.getRelacion_equipo_tipo_conector().size(); i++) {
			Listitem lItem;
			Listcell lCell;
			Combobox cmbTipoConector;
			Textbox txtVoltaje;
			Decimalbox decPotencia;
			Comboitem cItem;
			Button btnEliminar;
			lItem = new Listitem();
			/* ACCION */
			lCell = new Listcell();
			btnEliminar = new Button();
			btnEliminar.setIconSclass("z-icon-minus-circle");
			btnEliminar.setTooltiptext("Eliminar");
			btnEliminar.setAutodisable("self");
			btnEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					Listitem lItem;
					lItem = (Listitem) btnEliminar.getParent().getParent();
					lbxFuentes.removeItemAt(lItem.getIndex());
					// num_fuente = num_fuente - 1;
				}
			});
			lCell.appendChild(btnEliminar);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* TIPO CONECTOR */
			lCell = new Listcell();
			cmbTipoConector = new Combobox();
			for (int j = 0; j < listaTipoConector.size(); j++) {
				cItem = new Comboitem();
				cItem.setLabel(listaTipoConector.get(j).getNom_tipo_conector());
				cItem.setValue(listaTipoConector.get(j).getId_tipo_conector());
				cmbTipoConector.appendChild(cItem);
			}
			cmbTipoConector.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					Listitem lItem;
					Listcell lCell;
					Textbox txtVoltaje;
					lItem = (Listitem) cmbTipoConector.getParent().getParent();
					lCell = (Listcell) lItem.getChildren().get(2);
					txtVoltaje = (Textbox) lCell.getChildren().get(0);
					txtVoltaje.setText(String.valueOf(
							listaTipoConector.get(Integer.valueOf(cmbTipoConector.getSelectedItem().getIndex()))
									.getVol_tipo_conector()));
				}
			});
			cmbTipoConector.setText(
					equipo.getRelacion_equipo_tipo_conector().get(i).getTipo_conector().getNom_tipo_conector());
			cmbTipoConector.setReadonly(true);
			cmbTipoConector.setWidth("180px");
			lCell.appendChild(cmbTipoConector);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* VOLTAJE */
			lCell = new Listcell();
			txtVoltaje = new Textbox();
			txtVoltaje.setReadonly(true);
			txtVoltaje.setWidth("110px");
			txtVoltaje.setClass("z-textbox-2");
			txtVoltaje.setText(String.valueOf(
					equipo.getRelacion_equipo_tipo_conector().get(i).getTipo_conector().getVol_tipo_conector()));
			lCell.appendChild(txtVoltaje);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* POTENCIA */
			lCell = new Listcell();
			decPotencia = new Decimalbox();
			decPotencia.setDisabled(false);
			decPotencia.setWidth("110px");
			decPotencia.setFormat("#,##0.##");
			decPotencia.setValue(BigDecimal.valueOf(equipo.getRelacion_equipo_tipo_conector().get(i).getPotencia()));
			decPotencia.setTooltiptext(
					"El formato es de la siguiente manera #,##0.## la coma es el separador de decimales y el punto de miles.");
			decPotencia.setClass("z-textbox-2");
			lCell.appendChild(decPotencia);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ANADIR ITEM A LISTBOX */
			lbxFuentes.appendChild(lItem);
			/* LIMPIAR CAMPOS */
			lbxFuentes.clearSelection();
			// num_fuente = num_fuente + 1;
		}
	}

	public void cargarEtiquetasCableadoPoder() {
		if (equipo.getEtiquetados_poder().size() > 0) {
			cmbRegistraEtiquetaCableadoPoder.setSelectedIndex(0);
			btnAnadirEtiquetaCableadoPoder.setDisabled(false);
		}
		for (int i = 0; i < equipo.getEtiquetados_poder().size(); i++) {
			Listitem lItem;
			Listcell lCell;
			Textbox txtEtiqueta;
			Button btnEliminar;
			lItem = new Listitem();
			/* ACCION */
			lCell = new Listcell();
			btnEliminar = new Button();
			btnEliminar.setIconSclass("z-icon-minus-circle");
			btnEliminar.setTooltiptext("Eliminar");
			btnEliminar.setAutodisable("self");
			btnEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					Listitem lItem;
					lItem = (Listitem) btnEliminar.getParent().getParent();
					lbxEtiquetasCableadoPoder.removeItemAt(lItem.getIndex());
				}
			});
			lCell.appendChild(btnEliminar);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ETIQUETA */
			lCell = new Listcell();
			txtEtiqueta = new Textbox();
			txtEtiqueta.setDisabled(false);
			txtEtiqueta.setWidth("325px");
			txtEtiqueta.setClass("z-textbox-2");
			txtEtiqueta.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					txtEtiqueta.setText(txtEtiqueta.getText().toUpperCase().trim());
				}
			});
			txtEtiqueta.setText(equipo.getEtiquetados_poder().get(i).getNom_etiqueta());
			lCell.appendChild(txtEtiqueta);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ANADIR ITEM A LISTBOX */
			lbxEtiquetasCableadoPoder.appendChild(lItem);
			/* LIMPIAR CAMPOS */
			lbxEtiquetasCableadoPoder.clearSelection();
			// num_fuente = num_fuente + 1;
			binder.loadComponent(lbxEtiquetasCableadoPoder);
		}
	}

	public void cargarEtiquetasCableadoPdu() {
		if (equipo.getEtiquetados_pdu().size() > 0) {
			cmbRegistraEtiquetaCableadoPdu.setSelectedIndex(0);
			btnAnadirEtiquetaCableadoPdu.setDisabled(false);
		}
		for (int i = 0; i < equipo.getEtiquetados_pdu().size(); i++) {
			Listitem lItem;
			Listcell lCell;
			Textbox txtEtiqueta;
			Button btnEliminar;
			lItem = new Listitem();
			/* ACCION */
			lCell = new Listcell();
			btnEliminar = new Button();
			btnEliminar.setIconSclass("z-icon-minus-circle");
			btnEliminar.setTooltiptext("Eliminar");
			btnEliminar.setAutodisable("self");
			btnEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					Listitem lItem;
					lItem = (Listitem) btnEliminar.getParent().getParent();
					lbxEtiquetasCableadoPdu.removeItemAt(lItem.getIndex());
				}
			});
			lCell.appendChild(btnEliminar);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ETIQUETA */
			lCell = new Listcell();
			txtEtiqueta = new Textbox();
			txtEtiqueta.setDisabled(false);
			txtEtiqueta.setWidth("325px");
			txtEtiqueta.setClass("z-textbox-2");
			txtEtiqueta.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					txtEtiqueta.setText(txtEtiqueta.getText().toUpperCase().trim());
				}
			});
			txtEtiqueta.setText(equipo.getEtiquetados_pdu().get(i).getNom_etiqueta());
			lCell.appendChild(txtEtiqueta);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ANADIR ITEM A LISTBOX */
			lbxEtiquetasCableadoPdu.appendChild(lItem);
			/* LIMPIAR CAMPOS */
			lbxEtiquetasCableadoPdu.clearSelection();
			// num_fuente = num_fuente + 1;
			binder.loadComponent(lbxEtiquetasCableadoPdu);
		}
	}

	public void cargarObservaciones()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		for (int i = 0; i < equipo.getObservaciones().size(); i++) {
			Listitem lItem;
			Listcell lCell;
			Textbox txtUsuario, txtOPerador, txtObservacion;
			Datebox dtxFecha;
			Button btnEliminar;
			lItem = new Listitem();
			/* ACCION */
			lCell = new Listcell();
			btnEliminar = new Button();
			btnEliminar.setIconSclass("z-icon-minus-circle");
			btnEliminar.setTooltiptext("Eliminar");
			btnEliminar.setAutodisable("self");
			btnEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					Listitem lItem;
					lItem = (Listitem) btnEliminar.getParent().getParent();
					lbxObservaciones.removeItemAt(lItem.getIndex());
				}
			});
			btnEliminar.setDisabled(true);
			lCell.appendChild(btnEliminar);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* FECHA */
			lCell = new Listcell();
			dtxFecha = new Datebox();
			dtxFecha.setDisabled(true);
			dtxFecha.setWidth("175px");
			dtxFecha.setClass("z-textbox-2");
			dtxFecha.setFormat("dd/MM/yyyy HH:mm:ss");
			dtxFecha.setValue(equipo.getObservaciones().get(i).getFec_ingresa());
			lCell.appendChild(dtxFecha);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* USUARIO */
			lCell = new Listcell();
			txtUsuario = new Textbox();
			txtUsuario.setReadonly(true);
			txtUsuario.setWidth("100px");
			txtUsuario.setText(equipo.getObservaciones().get(i).getUsu_ingresa());
			lCell.appendChild(txtUsuario);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* OPERADOR */
			lCell = new Listcell();
			txtOPerador = new Textbox();
			txtOPerador.setReadonly(true);
			txtOPerador.setWidth("300px");
			txtOPerador.setText(obtenerUsuario(equipo.getObservaciones().get(i).getUsu_ingresa()));
			lCell.appendChild(txtOPerador);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* OBSERVACION */
			lCell = new Listcell();
			txtObservacion = new Textbox();
			txtObservacion.setReadonly(true);
			txtObservacion.setWidth("255px");
			txtObservacion.setRows(4);
			txtObservacion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
				public void onEvent(Event event) throws Exception {
					txtObservacion.setText(txtObservacion.getText().toUpperCase().trim());
				}
			});
			txtObservacion.setText(equipo.getObservaciones().get(i).getNom_observacion());
			lCell.appendChild(txtObservacion);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ANADIR ITEM A LISTBOX */
			lbxObservaciones.appendChild(lItem);
			/* LIMPIAR CAMPOS */
			lbxObservaciones.clearSelection();
			// num_fuente = num_fuente + 1;
			binder.loadComponent(lbxObservaciones);
		}
	}

	public void cargarImagenFrontal() {
		if (equipo.getPath_img_frontal() != null) {
			if (equipo.getPath_img_frontal().length() > 0) {
				String nom_img = equipo.getPath_img_frontal()
						.substring(equipo.getPath_img_frontal().lastIndexOf("\\") + 1);
				File initial = new File(path_i_f + nom_img);
				if (initial.length() != 0) {
					InputStream is = null;
					try {
						is = new FileInputStream(initial);
						byte[] bytes = IOUtils.toByteArray(is);
						AImage image = new AImage("", bytes);
						txtNombreImagenFrontal.setText(nom_img);
						imgFrontal.setContent(image);
						imgFrontal.setVisible(true);
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	public void cargarImagenPosterior() {
		if (equipo.getPath_img_posterior() != null) {
			if (equipo.getPath_img_posterior().length() > 0) {
				String nom_img = equipo.getPath_img_posterior()
						.substring(equipo.getPath_img_posterior().lastIndexOf("\\") + 1);
				File initial = new File(path_i_p + nom_img);
				if (initial.length() != 0) {
					InputStream is = null;
					try {
						is = new FileInputStream(initial);
						byte[] bytes = IOUtils.toByteArray(is);
						AImage image = new AImage("", bytes);
						txtNombreImagenPosterior.setText(nom_img);
						imgPosterior.setContent(image);
						imgPosterior.setVisible(true);
						is.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}

	}

	public List<modelo_tipo_equipo> obtenerTiposEquipo() {
		return listaTiposEquipo;
	}

	public List<modelo_marca> obtenerMarcas() {
		return listaMarcas;
	}

	public List<modelo_modelo> obtenerModelos() {
		return listaModelos;
	}

	public String obtenerUsuario(String user)
			throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		String usuario = "";
		dao_usuario dao = new dao_usuario();
		modelo_usuario _usuario = dao.obtenerUsuario(user, "", 1);
		if (_usuario != null) {
			usuario = _usuario.verNombreCompleto();
		}
		return usuario;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarTiposEquipo() {
		listaTiposEquipo = consultasABaseDeDatos.consultarTipoEquipos(id_dc, id_mantenimiento, "", "", 0, 3);
		Comparator myComparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String input = (String) o1;
				modelo_tipo_equipo bean = (modelo_tipo_equipo) o2;
				return bean.getNom_tipo_equipo().contains(input.toUpperCase().trim()) ? 0 : 1;
			}
		};
		mySubModel = ListModels.toListSubModel(new ListModelList(listaTiposEquipo), myComparator, 15);
		cmbTipoEquipo.setModel(mySubModel);
		ComboitemRenderer<modelo_tipo_equipo> myRenderer = new ComboitemRenderer<modelo_tipo_equipo>() {
			@Override
			public void render(Comboitem item, modelo_tipo_equipo bean, int index) throws Exception {
				item.setLabel(bean.getNom_tipo_equipo());
				item.setValue(bean);
			}
		};
		cmbTipoEquipo.setItemRenderer(myRenderer);
		binder.loadComponent(cmbTipoEquipo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarMarcas() {
		listaMarcas = consultasABaseDeDatos.consultarMarcas(0, 0, "", "", 0, 5);
		Comparator myComparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String input = (String) o1;
				modelo_marca bean = (modelo_marca) o2;
				return bean.getNom_marca().contains(input.toUpperCase().trim()) ? 0 : 1;
			}
		};
		mySubModel = ListModels.toListSubModel(new ListModelList(listaMarcas), myComparator, 15);
		cmbMarca.setModel(mySubModel);
		ComboitemRenderer<modelo_marca> myRenderer = new ComboitemRenderer<modelo_marca>() {
			@Override
			public void render(Comboitem item, modelo_marca bean, int index) throws Exception {
				item.setLabel(bean.getNom_marca());
				item.setValue(bean);
			}
		};
		cmbMarca.setItemRenderer(myRenderer);
		binder.loadComponent(cmbMarca);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarModelos() {
		listaModelos = consultasABaseDeDatos.consultarModelos(0, 0, "", "", 0, 5);
		Comparator myComparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String input = (String) o1;
				modelo_modelo bean = (modelo_modelo) o2;
				return bean.getNom_modelo().contains(input.toUpperCase().trim()) ? 0 : 1;
			}
		};
		mySubModel = ListModels.toListSubModel(new ListModelList(listaModelos), myComparator, 15);
		cmbModelo.setModel(mySubModel);
		ComboitemRenderer<modelo_modelo> myRenderer = new ComboitemRenderer<modelo_modelo>() {
			@Override
			public void render(Comboitem item, modelo_modelo bean, int index) throws Exception {
				item.setLabel(bean.getNom_modelo());
				item.setValue(bean);
			}
		};
		cmbModelo.setItemRenderer(myRenderer);
		binder.loadComponent(cmbModelo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void cargarEstadosEquipo() {
		listaEstadosEquipo = consultasABaseDeDatos.consultarEstadosEquipo(0, 0, "", "", 0, 5);
		Comparator myComparator = new Comparator() {
			@Override
			public int compare(Object o1, Object o2) {
				String input = (String) o1;
				modelo_estado_equipo bean = (modelo_estado_equipo) o2;
				return bean.getNom_estado().contains(input.toUpperCase().trim()) ? 0 : 1;
			}
		};
		mySubModel = ListModels.toListSubModel(new ListModelList(listaEstadosEquipo), myComparator, 15);
		cmbEstado.setModel(mySubModel);
		ComboitemRenderer<modelo_estado_equipo> myRenderer = new ComboitemRenderer<modelo_estado_equipo>() {
			@Override
			public void render(Comboitem item, modelo_estado_equipo bean, int index) throws Exception {
				item.setLabel(bean.getNom_estado());
				item.setValue(bean);
			}
		};
		cmbEstado.setItemRenderer(myRenderer);
		binder.loadComponent(cmbEstado);
	}

	@Listen("onSelect=#cmbRegistraFuente")
	public void onSelect$cmbRegistraFuente()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbRegistraFuente.getSelectedItem() == null) {
			btnAnadirFuente.setDisabled(true);
			borrarListaFuentes();
			return;
		}
		if (Integer.valueOf(cmbRegistraFuente.getSelectedItem().getValue().toString()) == 1) {
			btnAnadirFuente.setDisabled(false);
			borrarListaFuentes();
		} else {
			btnAnadirFuente.setDisabled(true);
			borrarListaFuentes();
		}
	}

	@Listen("onSelect=#cmbRegistraEtiquetaCableadoPoder")
	public void onSelect$cmbRegistraEtiquetaCableadoPoder()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbRegistraEtiquetaCableadoPoder.getSelectedItem() == null) {
			btnAnadirEtiquetaCableadoPoder.setDisabled(true);
			borrarListaEtiquetasCableadoPoder();
			return;
		}
		if (Integer.valueOf(cmbRegistraEtiquetaCableadoPoder.getSelectedItem().getValue().toString()) == 1) {
			btnAnadirEtiquetaCableadoPoder.setDisabled(false);
			borrarListaEtiquetasCableadoPoder();
		} else {
			btnAnadirEtiquetaCableadoPoder.setDisabled(true);
			borrarListaEtiquetasCableadoPoder();
		}
	}

	@Listen("onSelect=#cmbRegistraEtiquetaCableadoPdu")
	public void onSelect$cmbRegistraEtiquetaCableadoPdu()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbRegistraEtiquetaCableadoPdu.getSelectedItem() == null) {
			btnAnadirEtiquetaCableadoPdu.setDisabled(true);
			borrarListaEtiquetasCableadoPdu();
			return;
		}
		if (Integer.valueOf(cmbRegistraEtiquetaCableadoPdu.getSelectedItem().getValue().toString()) == 1) {
			btnAnadirEtiquetaCableadoPdu.setDisabled(false);
			borrarListaEtiquetasCableadoPdu();
		} else {
			btnAnadirEtiquetaCableadoPdu.setDisabled(true);
			borrarListaEtiquetasCableadoPdu();
		}
	}

	@Listen("onSelect=#cmbRegistraObservacion")
	public void onSelect$cmbRegistraObservacion()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbRegistraObservacion.getSelectedItem() == null) {
			btnAnadirObservacion.setDisabled(true);
			borrarListaObservaciones();
			return;
		}
		if (Integer.valueOf(cmbRegistraObservacion.getSelectedItem().getValue().toString()) == 1) {
			btnAnadirObservacion.setDisabled(false);
			borrarListaObservaciones();
		} else {
			btnAnadirObservacion.setDisabled(true);
			borrarListaObservaciones();
		}
	}

	@Listen("onClick=#btnAnadirFuente")
	public void onClick$btnAnadirFuente()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxFuentes.getItemCount() <= num_fuente) {
			anadirFuenteALista();
		} else {
			tab2.setSelected(true);
			Clients.showNotification(
					"No se puede añadir mas de " + String.valueOf(num_fuente + 1) + " fuentes a la lista.",
					Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 7000, true);
			return;
		}
	}

	@Listen("onClick=#btnAnadirEtiquetaCableadoPoder")
	public void onClick$btnAnadirEtiquetaCableadoPoder()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxFuentes.getItemCount() == 0) {
			tab2.setSelected(true);
			Clients.showNotification(
					"Debe registrar al menos una fuente a la lista, para registrar el etiquetado de cableado de poder.",
					Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 7000, true);
			return;
		}
		if (lbxEtiquetasCableadoPoder.getItemCount() < lbxFuentes.getItemCount()) {
			anadirEtiquetasCableadoPoderALista();
		} else {
			tab4.setSelected(true);
			Clients.showNotification("No se puede registrar mas etiquetas, que las fuentes registradas.",
					Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 7000, true);
			return;
		}
	}

	@Listen("onClick=#btnAnadirEtiquetaCableadoPdu")
	public void onClick$btnAnadirEtiquetaCableadoPdu()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxFuentes.getItemCount() == 0) {
			tab2.setSelected(true);
			Clients.showNotification(
					"Debe registrar al menos una fuente a la lista, para registrar el etiquetado de cableado de poder.",
					Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 7000, true);
			return;
		}
		if (lbxEtiquetasCableadoPdu.getItemCount() < lbxFuentes.getItemCount()) {
			anadirEtiquetasCableadoPduALista();
		} else {
			tab5.setSelected(true);
			Clients.showNotification("No se puede registrar mas etiquetas, que las fuentes registradas.",
					Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 7000, true);
			return;
		}
	}

	@Listen("onClick=#btnAnadirObservacion")
	public void onClick$btnAnadirObservacion()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		anadirObservacionesALista();
	}

	public void anadirFuenteALista()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		listaTipoConector = consultasABaseDeDatos.consultarTipoConectores(0, 0, "", "", 0, 3);
		Listitem lItem;
		Listcell lCell;
		Combobox cmbTipoConector;
		Textbox txtVoltaje;
		Decimalbox decPotencia;
		Comboitem cItem;
		Button btnEliminar;
		lItem = new Listitem();
		/* ACCION */
		lCell = new Listcell();
		btnEliminar = new Button();
		btnEliminar.setIconSclass("z-icon-minus-circle");
		btnEliminar.setTooltiptext("Eliminar");
		btnEliminar.setAutodisable("self");
		btnEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				Listitem lItem;
				lItem = (Listitem) btnEliminar.getParent().getParent();
				lbxFuentes.removeItemAt(lItem.getIndex());
				// num_fuente = num_fuente - 1;
			}
		});
		lCell.appendChild(btnEliminar);
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* TIPO CONECTOR */
		lCell = new Listcell();
		cmbTipoConector = new Combobox();
		for (int i = 0; i < listaTipoConector.size(); i++) {
			cItem = new Comboitem();
			cItem.setLabel(listaTipoConector.get(i).getNom_tipo_conector());
			cItem.setValue(listaTipoConector.get(i).getId_tipo_conector());
			cmbTipoConector.appendChild(cItem);
		}
		cmbTipoConector.addEventListener(Events.ON_SELECT, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				Listitem lItem;
				Listcell lCell;
				Textbox txtVoltaje;
				lItem = (Listitem) cmbTipoConector.getParent().getParent();
				lCell = (Listcell) lItem.getChildren().get(2);
				txtVoltaje = (Textbox) lCell.getChildren().get(0);
				txtVoltaje.setText(String.valueOf(listaTipoConector
						.get(Integer.valueOf(cmbTipoConector.getSelectedItem().getIndex())).getVol_tipo_conector()));
			}
		});
		cmbTipoConector.setReadonly(true);
		cmbTipoConector.setWidth("180px");
		lCell.appendChild(cmbTipoConector);
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* VOLTAJE */
		lCell = new Listcell();
		txtVoltaje = new Textbox();
		txtVoltaje.setDisabled(true);
		txtVoltaje.setWidth("110px");
		txtVoltaje.setClass("z-textbox-2");
		lCell.appendChild(txtVoltaje);
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* POTENCIA */
		lCell = new Listcell();
		decPotencia = new Decimalbox();
		decPotencia.setDisabled(false);
		decPotencia.setWidth("110px");
		decPotencia.setFormat("#,##0.##");
		decPotencia.setTooltiptext(
				"El formato es de la siguiente manera #,##0.## la coma es el separador de decimales y el punto de miles.");
		decPotencia.setClass("z-textbox-2");
		lCell.appendChild(decPotencia);
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* ANADIR ITEM A LISTBOX */
		lbxFuentes.appendChild(lItem);
		/* LIMPIAR CAMPOS */
		lbxFuentes.clearSelection();
		// num_fuente = num_fuente + 1;
		binder.loadComponent(lbxFuentes);
	}

	public void anadirEtiquetasCableadoPoderALista()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		Listitem lItem;
		Listcell lCell;
		Textbox txtEtiqueta;
		Button btnEliminar;
		lItem = new Listitem();
		/* ACCION */
		lCell = new Listcell();
		btnEliminar = new Button();
		btnEliminar.setIconSclass("z-icon-minus-circle");
		btnEliminar.setTooltiptext("Eliminar");
		btnEliminar.setAutodisable("self");
		btnEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				Listitem lItem;
				lItem = (Listitem) btnEliminar.getParent().getParent();
				lbxEtiquetasCableadoPoder.removeItemAt(lItem.getIndex());
			}
		});
		lCell.appendChild(btnEliminar);
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* ETIQUETA */
		lCell = new Listcell();
		txtEtiqueta = new Textbox();
		txtEtiqueta.setDisabled(false);
		txtEtiqueta.setWidth("325px");
		txtEtiqueta.setClass("z-textbox-2");
		txtEtiqueta.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtEtiqueta.setText(txtEtiqueta.getText().toUpperCase().trim());
			}
		});
		lCell.appendChild(txtEtiqueta);
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* ANADIR ITEM A LISTBOX */
		lbxEtiquetasCableadoPoder.appendChild(lItem);
		/* LIMPIAR CAMPOS */
		lbxEtiquetasCableadoPoder.clearSelection();
		// num_fuente = num_fuente + 1;
		binder.loadComponent(lbxEtiquetasCableadoPoder);
	}

	public void anadirEtiquetasCableadoPduALista()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		Listitem lItem;
		Listcell lCell;
		Textbox txtEtiqueta;
		Button btnEliminar;
		lItem = new Listitem();
		/* ACCION */
		lCell = new Listcell();
		btnEliminar = new Button();
		btnEliminar.setIconSclass("z-icon-minus-circle");
		btnEliminar.setTooltiptext("Eliminar");
		btnEliminar.setAutodisable("self");
		btnEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				Listitem lItem;
				lItem = (Listitem) btnEliminar.getParent().getParent();
				lbxEtiquetasCableadoPdu.removeItemAt(lItem.getIndex());
			}
		});
		lCell.appendChild(btnEliminar);
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* ETIQUETA */
		lCell = new Listcell();
		txtEtiqueta = new Textbox();
		txtEtiqueta.setDisabled(false);
		txtEtiqueta.setWidth("325px");
		txtEtiqueta.setClass("z-textbox-2");
		txtEtiqueta.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtEtiqueta.setText(txtEtiqueta.getText().toUpperCase().trim());
			}
		});
		lCell.appendChild(txtEtiqueta);
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* ANADIR ITEM A LISTBOX */
		lbxEtiquetasCableadoPdu.appendChild(lItem);
		/* LIMPIAR CAMPOS */
		lbxEtiquetasCableadoPdu.clearSelection();
		// num_fuente = num_fuente + 1;
		binder.loadComponent(lbxEtiquetasCableadoPdu);
	}

	public void anadirObservacionesALista()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		Listitem lItem;
		Listcell lCell;
		Textbox txtUsuario, txtOPerador, txtObservacion;
		Datebox dtxFecha;
		Button btnEliminar;
		lItem = new Listitem();
		/* ACCION */
		lCell = new Listcell();
		btnEliminar = new Button();
		btnEliminar.setIconSclass("z-icon-minus-circle");
		btnEliminar.setTooltiptext("Eliminar");
		btnEliminar.setAutodisable("self");
		btnEliminar.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				Listitem lItem;
				lItem = (Listitem) btnEliminar.getParent().getParent();
				lbxObservaciones.removeItemAt(lItem.getIndex());
			}
		});
		lCell.appendChild(btnEliminar);
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* FECHA */
		lCell = new Listcell();
		dtxFecha = new Datebox();
		dtxFecha.setDisabled(true);
		dtxFecha.setWidth("175px");
		dtxFecha.setClass("z-textbox-2");
		dtxFecha.setFormat("dd/MM/yyyy HH:mm:ss");
		dtxFecha.setValue(new Date());
		lCell.appendChild(dtxFecha);
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* USUARIO */
		lCell = new Listcell();
		txtUsuario = new Textbox();
		txtUsuario.setReadonly(true);
		txtUsuario.setWidth("100px");
		txtUsuario.setText(user);
		lCell.appendChild(txtUsuario);
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* OPERADOR */
		lCell = new Listcell();
		txtOPerador = new Textbox();
		txtOPerador.setReadonly(true);
		txtOPerador.setWidth("300px");
		txtOPerador.setText(nom_ape_user);
		lCell.appendChild(txtOPerador);
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* OBSERVACION */
		lCell = new Listcell();
		txtObservacion = new Textbox();
		txtObservacion.setDisabled(false);
		txtObservacion.setWidth("255px");
		txtObservacion.setRows(4);
		txtObservacion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtObservacion.setText(txtObservacion.getText().toUpperCase().trim());
			}
		});
		lCell.appendChild(txtObservacion);
		lCell.setStyle("text-align: center !important;");
		lItem.appendChild(lCell);
		/* ANADIR ITEM A LISTBOX */
		lbxObservaciones.appendChild(lItem);
		/* LIMPIAR CAMPOS */
		lbxObservaciones.clearSelection();
		// num_fuente = num_fuente + 1;
		binder.loadComponent(lbxObservaciones);
	}

	public boolean validarFuentesRegistradas() {
		boolean exito = true;
		Listcell lCell;
		Combobox cmBox;
		Decimalbox decP;
		for (int i = 0; i < lbxFuentes.getItems().size(); i++) {
			lCell = (Listcell) lbxFuentes.getItemAtIndex(i).getChildren().get(1);
			cmBox = (Combobox) lCell.getChildren().get(0);
			if (cmBox.getSelectedItem() == null) {
				tab2.setSelected(true);
				// cmBox.setFocus(true);
				Clients.showNotification("Seleccione un tipo de conector.", Clients.NOTIFICATION_TYPE_WARNING,
						dSolicitudes, "top_right", 2000, true);
				exito = false;
				break;
			}
			lCell = (Listcell) lbxFuentes.getItemAtIndex(i).getChildren().get(3);
			decP = (Decimalbox) lCell.getChildren().get(0);
			float rg1 = 0;
			if (decP.getValue() == null) {
				tab2.setSelected(true);
				decP.setFocus(true);
				Clients.showNotification("Ingrese la potencia de la fuente.", Clients.NOTIFICATION_TYPE_WARNING,
						dSolicitudes, "top_right", 5000, true);
				exito = false;
				break;
			}
			if (decP.getValue().floatValue() < rg1) {
				tab2.setSelected(true);
				decP.setFocus(true);
				Clients.showNotification("Ingrese un valor correcto para la potencia de la fuente.",
						Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 2000, true);
				exito = false;
				break;
			}
		}
		return exito;
	}

	public boolean validarEtiquetasCableadoPoderRegistradas() {
		boolean exito = true;
		Listcell lCell;
		Textbox txtE;
		for (int i = 0; i < lbxEtiquetasCableadoPoder.getItems().size(); i++) {
			lCell = (Listcell) lbxEtiquetasCableadoPoder.getItemAtIndex(i).getChildren().get(1);
			txtE = (Textbox) lCell.getChildren().get(0);
			if (txtE.getText().length() <= 0) {
				tab4.setSelected(true);
				txtE.setFocus(true);
				Clients.showNotification("Ingrese el nombre de la etiqueta.", Clients.NOTIFICATION_TYPE_WARNING,
						dSolicitudes, "top_right", 5000, true);
				exito = false;
				break;
			}
		}
		return exito;
	}

	public boolean validarEtiquetasCableadoPduRegistradas() {
		boolean exito = true;
		Listcell lCell;
		Textbox txtE;
		for (int i = 0; i < lbxEtiquetasCableadoPdu.getItems().size(); i++) {
			lCell = (Listcell) lbxEtiquetasCableadoPdu.getItemAtIndex(i).getChildren().get(1);
			txtE = (Textbox) lCell.getChildren().get(0);
			if (txtE.getText().length() <= 0) {
				tab5.setSelected(true);
				txtE.setFocus(true);
				Clients.showNotification("Ingrese el nombre de la etiqueta.", Clients.NOTIFICATION_TYPE_WARNING,
						dSolicitudes, "top_right", 5000, true);
				exito = false;
				break;
			}
		}
		return exito;
	}

	public boolean validarObservacionesRegistradas() {
		boolean exito = true;
		Listcell lCell;
		Textbox txtE;
		for (int i = 0; i < lbxObservaciones.getItems().size(); i++) {
			lCell = (Listcell) lbxObservaciones.getItemAtIndex(i).getChildren().get(3);
			txtE = (Textbox) lCell.getChildren().get(0);
			if (txtE.getText().length() <= 0) {
				tab6.setSelected(true);
				txtE.setFocus(true);
				Clients.showNotification("Ingrese la observación.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
						"top_right", 5000, true);
				exito = false;
				break;
			}
		}
		return exito;
	}

	@Listen("onClick=#chkNombreEquipo")
	public void onClick$chkNombreEquipo() {
		if (chkNombreEquipo.isChecked()) {
			txtNombreEquipo.setDisabled(true);
			txtNombreEquipo.setText("");
		} else {
			txtNombreEquipo.setDisabled(false);
			txtNombreEquipo.setText("");
		}
	}

	@Listen("onClick=#chkSerie")
	public void onClick$chkSerie() {
		if (chkSerie.isChecked()) {
			txtSerie.setDisabled(true);
			txtSerie.setText("");
		} else {
			txtSerie.setDisabled(false);
			txtSerie.setText("");
		}
	}

	@Listen("onClick=#chkCodigoActivo")
	public void onClick$chkCodigoActivo() {
		if (chkCodigoActivo.isChecked()) {
			txtCodigoActivo.setDisabled(true);
			txtCodigoActivo.setText("");
		} else {
			txtCodigoActivo.setDisabled(false);
			txtCodigoActivo.setText("");
		}
	}

	@Listen("onClick=#chkCodigoProducto")
	public void onClick$chkCodigoProducto() {
		if (chkCodigoProducto.isChecked()) {
			txtCodigoProducto.setDisabled(true);
			txtCodigoProducto.setText("");
		} else {
			txtCodigoProducto.setDisabled(false);
			txtCodigoProducto.setText("");
		}
	}

	@Listen("onClick=#chkPeso")
	public void onClick$chkPeso() {
		if (chkPeso.isChecked()) {
			decPeso.setDisabled(true);
			decPeso.setText("");
		} else {
			decPeso.setDisabled(false);
			decPeso.setText("");
		}
	}

	@Listen("onClick=#chkProfundidad")
	public void onClick$chkProfundidad() {
		if (chkProfundidad.isChecked()) {
			decProfundidad.setDisabled(true);
			decProfundidad.setText("");
		} else {
			decProfundidad.setDisabled(false);
			decProfundidad.setText("");
		}
	}

	@Listen("onClick=#chkBTU")
	public void onClick$chkBTU() {
		if (chkBTU.isChecked()) {
			decBTU.setDisabled(true);
			decBTU.setText("");
		} else {
			decBTU.setDisabled(false);
			decBTU.setText("");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (cmbTipoEquipo.getSelectedItem() == null) {
			tab1.setSelected(true);
			cmbTipoEquipo.setFocus(true);
			Clients.showNotification("Seleccione un tipo de equipo.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
					"top_right", 2000, true);
			return;
		}
		if (!chkNombreEquipo.isChecked()) {
			if (txtNombreEquipo.getText().length() <= 0) {
				tab1.setSelected(true);
				txtNombreEquipo.setFocus(true);
				Clients.showNotification("Ingrese el nombre de equipo.", Clients.NOTIFICATION_TYPE_WARNING,
						dSolicitudes, "top_right", 2000, true);
				return;
			}
		}
		if (cmbMarca.getSelectedItem() == null) {
			tab1.setSelected(true);
			cmbMarca.setFocus(true);
			Clients.showNotification("Seleccione una marca.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
					"top_right", 2000, true);
			return;
		}
		if (cmbModelo.getSelectedItem() == null) {
			tab1.setSelected(true);
			cmbModelo.setFocus(true);
			Clients.showNotification("Seleccione un modelo.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
					"top_right", 2000, true);
			return;
		}
		if (!chkSerie.isChecked()) {
			if (txtSerie.getText().length() <= 0) {
				tab1.setSelected(true);
				txtSerie.setFocus(true);
				Clients.showNotification("Ingrese la serie.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
						"top_right", 2000, true);
				return;
			}
		}
		if (!chkCodigoActivo.isChecked()) {
			if (txtCodigoActivo.getText().length() <= 0) {
				tab1.setSelected(true);
				txtCodigoActivo.setFocus(true);
				Clients.showNotification("Ingrese el código de activo.", Clients.NOTIFICATION_TYPE_WARNING,
						dSolicitudes, "top_right", 2000, true);
				return;
			}
		}
		if (!chkCodigoProducto.isChecked()) {
			if (txtCodigoProducto.getText().length() <= 0) {
				tab1.setSelected(true);
				txtCodigoProducto.setFocus(true);
				Clients.showNotification("Ingrese el número de producto.", Clients.NOTIFICATION_TYPE_WARNING,
						dSolicitudes, "top_right", 2000, true);
				return;
			}
		}
		float rg1 = 0;
		float rg2 = 2;
		if (!chkPeso.isChecked()) {
			if (decPeso.getValue() == null) {
				tab1.setSelected(true);
				decPeso.setFocus(true);
				Clients.showNotification("Ingrese el peso.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
						"top_right", 2000, true);
				return;
			}
			if (decPeso.getValue().floatValue() < rg1) {
				tab1.setSelected(true);
				decPeso.setFocus(true);
				Clients.showNotification("Ingrese un valor correcto para el peso.", Clients.NOTIFICATION_TYPE_WARNING,
						dSolicitudes, "top_right", 2000, true);
				return;
			}
		}
		if (!chkProfundidad.isChecked()) {
			if (decProfundidad.getValue() == null) {
				tab1.setSelected(true);
				decProfundidad.setFocus(true);
				Clients.showNotification("Ingrese la profundidad.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
						"top_right", 2000, true);
				return;
			}
			if (decProfundidad.getValue().floatValue() < rg1 || decProfundidad.getValue().floatValue() > rg2) {
				tab1.setSelected(true);
				decProfundidad.setFocus(true);
				Clients.showNotification(
						"Ingrese un valor correcto para la profundidad, el valor debe ser entre [0 - 2].",
						Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 2000, true);
				return;
			}
		}
		if (!chkBTU.isChecked()) {
			if (decBTU.getValue() == null) {
				tab1.setSelected(true);
				decBTU.setFocus(true);
				Clients.showNotification("Ingrese los BTU.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
						"top_right", 2000, true);
				return;
			}
			if (decBTU.getValue().floatValue() < rg1) {
				tab1.setSelected(true);
				decBTU.setFocus(true);
				Clients.showNotification("Ingrese un valor correcto para los BTU.", Clients.NOTIFICATION_TYPE_WARNING,
						dSolicitudes, "top_right", 2000, true);
				return;
			}
		}
		if (cmbEstado.getSelectedItem() == null) {
			tab1.setSelected(true);
			cmbEstado.setFocus(true);
			Clients.showNotification("Seleccione un estado.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
					"top_right", 2000, true);
			return;
		}
		if (cmbEtiquetadoCableadoDatos.getSelectedItem() == null) {
			tab1.setSelected(true);
			cmbEtiquetadoCableadoDatos.setFocus(true);
			Clients.showNotification("Indique si el cableado de datos quedo etiquetado.",
					Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 2000, true);
			return;
		}
		if (cmbPeinadoCableadoDatos.getSelectedItem() == null) {
			tab1.setSelected(true);
			cmbPeinadoCableadoDatos.setFocus(true);
			Clients.showNotification("Indique si el cableado de datos quedo peinado.",
					Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 2000, true);
			return;
		}
		if (cmbConectadoATS.getSelectedItem() == null) {
			tab1.setSelected(true);
			cmbConectadoATS.setFocus(true);
			Clients.showNotification("Indique si el equipo quedó conectado a ATS.", Clients.NOTIFICATION_TYPE_WARNING,
					dSolicitudes, "top_right", 2000, true);
			return;
		}
		if (dtxFechaIngreso.getValue() == null) {
			tab1.setSelected(true);
			dtxFechaIngreso.setFocus(true);
			Clients.showNotification("Ingrese una fecha valida.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
					"top_right", 2000, true);
			return;
		}
		if (fechas.esFechaValida(fechas.obtenerFechaFormateada(dtxFechaIngreso.getValue(), "dd/MM/yyyy"),
				"dd/MM/yyyy") == false) {
			tab1.setSelected(true);
			dtxFechaIngreso.setFocus(true);
			Clients.showNotification("Ingrese una fecha valida.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
					"top_right", 2000, true);
			return;
		}
		if (Integer.valueOf(cmbRegistraFuente.getSelectedItem().getValue().toString()) == 1) {
			if (lbxFuentes.getItemCount() == 0) {
				tab2.setSelected(true);
				Clients.showNotification("Registre al menos una fuente para el equipo.",
						Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 2000, true);
				return;
			}
			if (validarFuentesRegistradas() == false) {
				return;
			}
		}
		if (Integer.valueOf(cmbRegistraEtiquetaCableadoPoder.getSelectedItem().getValue().toString()) == 1) {
			if (lbxEtiquetasCableadoPoder.getItemCount() == 0) {
				tab4.setSelected(true);
				Clients.showNotification("Registre al menos una etiqueta para el equipo.",
						Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 2000, true);
				return;
			}
			if (validarEtiquetasCableadoPoderRegistradas() == false) {
				return;
			}
		}
		if (Integer.valueOf(cmbRegistraEtiquetaCableadoPdu.getSelectedItem().getValue().toString()) == 1) {
			if (lbxEtiquetasCableadoPdu.getItemCount() == 0) {
				tab5.setSelected(true);
				Clients.showNotification("Registre al menos una etiqueta para el equipo.",
						Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 2000, true);
				return;
			}
			if (validarEtiquetasCableadoPduRegistradas() == false) {
				return;
			}
		}
		if (validarObservacionesRegistradas() == false) {
			return;
		}
		Messagebox.show("Esta seguro de actualizar el equipo?", ".:: Modificar equipo ::.",
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							dao_equipo dao1 = new dao_equipo();
							modelo_equipo equipo = new modelo_equipo();
							equipo = modificar.this.equipo;
							if (!chkNombreEquipo.isChecked()) {
								equipo.setNom_equipo(txtNombreEquipo.getText());
							} else {
								equipo.setNom_equipo("N/A");
							}
							if (!chkSerie.isChecked()) {
								equipo.setSerie(txtSerie.getText());
							} else {
								equipo.setSerie("N/A");
							}
							if (!chkCodigoActivo.isChecked()) {
								equipo.setCod_activo(txtCodigoActivo.getText());
							} else {
								equipo.setCod_activo("N/A");
							}
							if (!chkCodigoProducto.isChecked()) {
								equipo.setCod_producto(txtCodigoProducto.getText());
							} else {
								equipo.setCod_producto("N/A");
							}
							if (!chkPeso.isChecked()) {
								equipo.setPeso(decPeso.getValue().floatValue());
							} else {
								equipo.setPeso(0);
							}
							if (!chkProfundidad.isChecked()) {
								equipo.setProfundidad(decProfundidad.getValue().floatValue());
							} else {
								equipo.setProfundidad(0);
							}
							if (!chkBTU.isChecked()) {
								equipo.setBtu(decBTU.getValue().floatValue());
							} else {
								equipo.setBtu(0);
							}
							if (Integer.valueOf(cmbRegistraFuente.getSelectedItem().getValue().toString()) == 1) {
								equipo.setRegistra_fuente(true);
							} else {
								equipo.setRegistra_fuente(false);
							}
							if (Integer.valueOf(cmbRegistraFuente.getSelectedItem().getValue().toString()) == 1) {
								equipo.setRegistra_fuente(true);
							} else {
								equipo.setRegistra_fuente(false);
							}
							if (Integer
									.valueOf(cmbEtiquetadoCableadoDatos.getSelectedItem().getValue().toString()) == 1) {
								equipo.setEtiq_cds(true);
							} else {
								equipo.setEtiq_cds(false);
							}
							if (Integer.valueOf(cmbPeinadoCableadoDatos.getSelectedItem().getValue().toString()) == 1) {
								equipo.setPein_cds(true);
							} else {
								equipo.setPein_cds(false);
							}
							if (Integer.valueOf(cmbConectadoATS.getSelectedItem().getValue().toString()) == 1) {
								equipo.setCone_ats(true);
							} else {
								equipo.setCone_ats(false);
							}
							equipo.setFec_ingreso(fechas.obtenerTimestampDeDate(dtxFechaIngreso.getValue()));
							equipo.setEst_equipo("AE");
							equipo.setUsu_modifica(user);
							equipo.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
							equipo.setTipo_equipo((modelo_tipo_equipo) cmbTipoEquipo.getSelectedItem().getValue());
							equipo.setMarca((modelo_marca) cmbMarca.getSelectedItem().getValue());
							equipo.setModelo((modelo_modelo) cmbModelo.getSelectedItem().getValue());
							equipo.setEstado_equipo((modelo_estado_equipo) cmbEstado.getSelectedItem().getValue());
							if (obtenerLocalidad() != null) {
								equipo.setLocalidad(obtenerLocalidad());
							} else {
								tab1.setSelected(true);
								Clients.showNotification(
										"Se presentó un error al actualizar el equipo, no se pudo encontrar la localidad.",
										Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 4000, true);
								return;
							}
							List<modelo_relacion_equipo_tipo_conector> lista1 = new ArrayList<modelo_relacion_equipo_tipo_conector>();
							modelo_relacion_equipo_tipo_conector relacion_equipo_tipo_conector = null;
							dao_relacion_equipo_tipo_conector dao2 = new dao_relacion_equipo_tipo_conector();
							Listcell lCell;
							Combobox cmBox;
							Decimalbox decP;
							Datebox dtxFecha;
							Textbox txtUsuario, txtEtiqueta, txtObservacion;
							/* FUENTES */
							for (int i = 0; i < lbxFuentes.getItems().size(); i++) {
								relacion_equipo_tipo_conector = new modelo_relacion_equipo_tipo_conector();
								lCell = (Listcell) lbxFuentes.getItemAtIndex(i).getChildren().get(1);
								cmBox = (Combobox) lCell.getChildren().get(0);
								relacion_equipo_tipo_conector.setEquipo(equipo);
								if (obtenerTipoConector(
										Long.valueOf(cmBox.getSelectedItem().getValue().toString())) != null) {
									relacion_equipo_tipo_conector.setTipo_conector(obtenerTipoConector(
											Long.valueOf(cmBox.getSelectedItem().getValue().toString())));
								} else {
									tab2.setSelected(true);
									Clients.showNotification(
											"Se presentó un error al actualizar el equipo, no se pudo encontrar el tipo de conector.",
											Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 4000, true);
									return;
								}
								lCell = (Listcell) lbxFuentes.getItemAtIndex(i).getChildren().get(3);
								decP = (Decimalbox) lCell.getChildren().get(0);
								relacion_equipo_tipo_conector.setPotencia(decP.getValue().floatValue());
								relacion_equipo_tipo_conector.setEst_relacion("AE");
								relacion_equipo_tipo_conector.setUsu_ingresa(user);
								relacion_equipo_tipo_conector.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
								lista1.add(relacion_equipo_tipo_conector);
							}
							/* ETIQUETADO DE PODER */
							List<modelo_etiquetado_poder> lista2 = new ArrayList<modelo_etiquetado_poder>();
							modelo_etiquetado_poder etiquetado_poder = null;
							dao_etiquetado_poder dao3 = new dao_etiquetado_poder();
							for (int i = 0; i < lbxEtiquetasCableadoPoder.getItems().size(); i++) {
								etiquetado_poder = new modelo_etiquetado_poder();
								etiquetado_poder.setEquipo(equipo);
								lCell = (Listcell) lbxEtiquetasCableadoPoder.getItemAtIndex(i).getChildren().get(1);
								txtEtiqueta = (Textbox) lCell.getChildren().get(0);
								etiquetado_poder.setNom_etiqueta(txtEtiqueta.getText());
								etiquetado_poder.setEst_etiqueta("AE");
								etiquetado_poder.setUsu_ingresa(user);
								etiquetado_poder.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
								lista2.add(etiquetado_poder);
							}
							/* ETIQUETADO DE PDU */
							List<modelo_etiquetado_pdu> lista3 = new ArrayList<modelo_etiquetado_pdu>();
							modelo_etiquetado_pdu etiquetado_pdu = null;
							dao_etiquetado_pdu dao4 = new dao_etiquetado_pdu();
							for (int i = 0; i < lbxEtiquetasCableadoPdu.getItems().size(); i++) {
								etiquetado_pdu = new modelo_etiquetado_pdu();
								etiquetado_pdu.setEquipo(equipo);
								lCell = (Listcell) lbxEtiquetasCableadoPdu.getItemAtIndex(i).getChildren().get(1);
								txtEtiqueta = (Textbox) lCell.getChildren().get(0);
								etiquetado_pdu.setNom_etiqueta(txtEtiqueta.getText());
								etiquetado_pdu.setEst_etiqueta("AE");
								etiquetado_pdu.setUsu_ingresa(user);
								etiquetado_pdu.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
								lista3.add(etiquetado_pdu);
							}
							/* OBSERVACION */
							List<modelo_observacion> lista4 = new ArrayList<modelo_observacion>();
							modelo_observacion observacion = null;
							dao_observacion dao5 = new dao_observacion();
							for (int i = 0; i < lbxObservaciones.getItems().size(); i++) {
								observacion = new modelo_observacion();
								observacion.setEquipo(equipo);
								lCell = (Listcell) lbxObservaciones.getItemAtIndex(i).getChildren().get(1);
								dtxFecha = (Datebox) lCell.getChildren().get(0);
								observacion.setFec_ingresa(fechas.obtenerTimestampDeDate(dtxFecha.getValue()));
								lCell = (Listcell) lbxObservaciones.getItemAtIndex(i).getChildren().get(2);
								txtUsuario = (Textbox) lCell.getChildren().get(0);
								observacion.setUsu_ingresa(txtUsuario.getText());
								lCell = (Listcell) lbxObservaciones.getItemAtIndex(i).getChildren().get(4);
								txtObservacion = (Textbox) lCell.getChildren().get(0);
								observacion.setNom_observacion(txtObservacion.getText());
								observacion.setEst_observacion("AE");
								lista4.add(observacion);
							}
							try {
								dao1.actualizarEquipo(equipo);
								/* ELIMINAR */
								/* FUENTES */
								for (int i = 0; i < equipo.getRelacion_equipo_tipo_conector().size(); i++) {
									dao2.eliminarRelacionEquipoTipoConector(
											equipo.getRelacion_equipo_tipo_conector().get(i));
								}
								/* ETIQUETADOS DE PODER */
								for (int i = 0; i < equipo.getEtiquetados_poder().size(); i++) {
									dao3.eliminarEtiquetadoPoder(equipo.getEtiquetados_poder().get(i));
								}
								/* ETIQUETADOS DE PDU */
								for (int i = 0; i < equipo.getEtiquetados_pdu().size(); i++) {
									dao4.eliminarEtiquetadoPdu(equipo.getEtiquetados_pdu().get(i));
								}
								/* OBSERVACION */
								for (int i = 0; i < equipo.getObservaciones().size(); i++) {
									dao5.eliminarObservacion(equipo.getObservaciones().get(i));
								}
								/* INSERTAR */
								/* FUENTES */
								if (Integer.valueOf(cmbRegistraFuente.getSelectedItem().getValue().toString()) == 1) {
									for (int i = 0; i < lista1.size(); i++) {
										dao2.insertarRelacionEquipoTipoConector(lista1.get(i));
									}
								}
								/* ETIQUETADOS DE PODER */
								if (Integer.valueOf(cmbRegistraEtiquetaCableadoPoder.getSelectedItem().getValue()
										.toString()) == 1) {
									for (int i = 0; i < lista2.size(); i++) {
										dao3.insertarEtiquetadoPoder(lista2.get(i));
									}
								}
								/* ETIQUETADOS DE PDU */
								if (Integer.valueOf(
										cmbRegistraEtiquetaCableadoPdu.getSelectedItem().getValue().toString()) == 1) {
									for (int i = 0; i < lista3.size(); i++) {
										dao4.insertarEtiquetadoPdu(lista3.get(i));
									}
								}
								/* OBSERVACION */
								for (int i = 0; i < lista4.size(); i++) {
									dao5.insertarObservacion(lista4.get(i));
								}
								Clients.showNotification("El equipo se actualizó correctamente.",
										Clients.NOTIFICATION_TYPE_INFO, dSolicitudes, "dSolicitudes", 4000, true);
								limpiarCampos();
							} catch (Exception e) {
								Clients.showNotification(
										"Error al actualizar el equipo. \n\n" + "Mensaje de error: \n\n"
												+ e.getMessage(),
										Clients.NOTIFICATION_TYPE_ERROR, dSolicitudes, "top_right", 4000, true);
							}
						}
					}
				});
	}

	public modelo_localidad obtenerLocalidad() {
		List<modelo_localidad> lista = new ArrayList<modelo_localidad>();
		modelo_localidad localidad = new modelo_localidad();
		lista = consultasABaseDeDatos.consultarLocalidades(id_dc, 0, "", "", 0, 2);
		if (lista.size() > 0) {
			localidad = lista.get(0);
		}
		return localidad;
	}

	public modelo_tipo_conector obtenerTipoConector(long id) {
		List<modelo_tipo_conector> lista = new ArrayList<modelo_tipo_conector>();
		modelo_tipo_conector tipo_conector = new modelo_tipo_conector();
		lista = consultasABaseDeDatos.consultarTipoConectores(id, 0, "", "", 0, 2);
		if (lista.size() > 0) {
			tipo_conector = lista.get(0);
		}
		return tipo_conector;
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zModificar));
	}

	public void limpiarCampos() throws ClassNotFoundException, FileNotFoundException, IOException {
		tab1.setSelected(true);
		cmbTipoEquipo.setText("");
		txtNombreEquipo.setText("");
		lNombreEquipo.setValue("0/25");
		cmbMarca.setText("");
		cmbModelo.setText("");
		txtSerie.setText("");
		lSerie.setValue("0/25");
		txtCodigoActivo.setText("");
		lCodigoActivo.setValue("0/25");
		txtCodigoProducto.setText("");
		lCodigoProducto.setValue("0/25");
		decPeso.setText("");
		decProfundidad.setText("");
		decBTU.setText("");
		borrarListaFuentes();
		borrarListaEtiquetasCableadoPoder();
		borrarListaEtiquetasCableadoPdu();
		borrarListaObservaciones();
		chkNombreEquipo.setChecked(true);
		chkSerie.setChecked(true);
		chkCodigoActivo.setChecked(true);
		chkCodigoProducto.setChecked(true);
		chkPeso.setChecked(true);
		chkProfundidad.setChecked(true);
		chkBTU.setChecked(true);
		txtNombreEquipo.setDisabled(true);
		txtSerie.setDisabled(true);
		txtCodigoActivo.setDisabled(true);
		txtCodigoProducto.setDisabled(true);
		decPeso.setDisabled(true);
		decProfundidad.setDisabled(true);
		decBTU.setDisabled(true);
		Events.postEvent(new Event("onClose", zModificar));
		binder.loadComponent(lbxFuentes);
		binder.loadComponent(lbxEtiquetasCableadoPoder);
		binder.loadComponent(lbxUrs);
	}

	public void borrarListaFuentes() {
		Listitem lItem;
		for (int i = lbxFuentes.getItemCount() - 1; i >= 0; i--) {
			lItem = (Listitem) lbxFuentes.getItemAtIndex(i);
			lbxFuentes.removeItemAt(lItem.getIndex());
		}
		binder.loadComponent(lbxFuentes);
	}

	public void borrarListaEtiquetasCableadoPoder() {
		Listitem lItem;
		for (int i = lbxEtiquetasCableadoPoder.getItemCount() - 1; i >= 0; i--) {
			lItem = (Listitem) lbxEtiquetasCableadoPoder.getItemAtIndex(i);
			lbxEtiquetasCableadoPoder.removeItemAt(lItem.getIndex());
		}
		binder.loadComponent(lbxEtiquetasCableadoPoder);
	}

	public void borrarListaEtiquetasCableadoPdu() {
		Listitem lItem;
		for (int i = lbxEtiquetasCableadoPdu.getItemCount() - 1; i >= 0; i--) {
			lItem = (Listitem) lbxEtiquetasCableadoPdu.getItemAtIndex(i);
			lbxEtiquetasCableadoPdu.removeItemAt(lItem.getIndex());
		}
		binder.loadComponent(lbxEtiquetasCableadoPdu);
	}

	public void borrarListaObservaciones() {
		Listitem lItem;
		for (int i = lbxObservaciones.getItemCount() - 1; i >= 0; i--) {
			lItem = (Listitem) lbxObservaciones.getItemAtIndex(i);
			lbxObservaciones.removeItemAt(lItem.getIndex());
		}
		binder.loadComponent(lbxObservaciones);
	}

	public void cargarUrs() {
		for (int i = 0; i < equipo.getRelacion_racks_urs_equipos().size(); i++) {
			if (i == 0) {
				txtNombreRack.setText(equipo.getRelacion_racks_urs_equipos().get(i).getRack().getCoord_rack());
			}
			Listitem lItem;
			Listcell lCell;
			Checkbox chkSeleccionar;
			Textbox txtUr, txtEstado;
			lItem = new Listitem();
			/* ACCION */
			lCell = new Listcell();
			chkSeleccionar = new Checkbox();
			if (equipo.getRelacion_racks_urs_equipos().get(i).isOcupado()) {
				chkSeleccionar.setChecked(true);
			} else {
				chkSeleccionar.setChecked(false);
			}
			chkSeleccionar.setDisabled(true);
			lCell.appendChild(chkSeleccionar);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* UR */
			lCell = new Listcell();
			txtUr = new Textbox();
			txtUr.setText(String.valueOf(equipo.getRelacion_racks_urs_equipos().get(i).getUr().getNom_ur()));
			txtUr.setClass("z-textbox-2");
			txtUr.setReadonly(true);
			lCell.appendChild(txtUr);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ESTADO */
			lCell = new Listcell();
			txtEstado = new Textbox();
			if (equipo.getRelacion_racks_urs_equipos().get(i).isOcupado()) {
				txtEstado.setText("OCUPADO");
			} else {
				txtEstado.setText("LIBRE");
			}
			txtEstado.setClass("z-textbox-2");
			txtEstado.setReadonly(true);
			lCell.appendChild(txtEstado);
			lCell.setStyle("text-align: center !important;");
			lItem.appendChild(lCell);
			/* ANADIR ITEM A LISTBOX */
			lbxUrs.appendChild(lItem);
			/* LIMPIAR CAMPOS */
			lbxUrs.clearSelection();
			// num_fuente = num_fuente + 1;
		}
		binder.loadComponent(lbxUrs);
	}

	@Listen("onUpload=#btnImagenFrontal")
	public void onUpload$btnImagenFrontal(UploadEvent event) {
		media = event.getMedia();
		if (media == null) {
			Clients.showNotification("No se añadio algún archivo.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
					"top_right", 2000, true);
			return;
		}
		if (media instanceof org.zkoss.image.Image) {
			imgFrontal.setContent((org.zkoss.image.Image) media);
			BufferedImage bimg = null;
			try {
				bimg = ImageIO.read(media.getStreamData());
			} catch (IOException e) {
				e.printStackTrace();
			}
			int width = bimg.getWidth();
			int height = bimg.getHeight();
			if (width > 300) {
				width = 300;
			}
			if (height > 300) {
				height = 300;
			}
			txtNombreImagenFrontal.setText(media.getName());
			imgFrontal.setWidth(String.valueOf(width) + "px");
			imgFrontal.setHeight(String.valueOf(height) + "px");
			imgFrontal.setVisible(true);
			if (ping.validarConexionEquipo(ip)) {
				modelo_equipo equipo = new modelo_equipo();
				dao_equipo dao = new dao_equipo();
				equipo = modificar.this.equipo;
				/* ELIMINAR IMAGEN SI EXISTE */
				if (equipo.getPath_img_frontal() != null) {
					if (equipo.getPath_img_frontal().length() > 0) {
						File initial = new File(equipo.getPath_img_frontal());
						if (initial.length() != 0) {
							Path to = Paths.get(equipo.getPath_img_frontal());
							try {
								Files.delete(to);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
				/* GUARDAR IMAGEN FRONTAL */
				byte[] buffer = media.getByteData();
				Path to = Paths.get(path_i_f + media.getName());
				try {
					Path tempFile = Files.createTempFile("tempfile", null);
					Files.write(tempFile, media.getByteData(), StandardOpenOption.WRITE);
					Files.copy(tempFile, to, StandardCopyOption.REPLACE_EXISTING);
					Files.delete(tempFile);
					Files.write(to, buffer, StandardOpenOption.WRITE);
					equipo.setPath_img_frontal(path_i_f + media.getName());
					dao.actualizarEquipo(equipo);
					Clients.showNotification("La imagen frontal se cargó correctamente.", "info", dSolicitudes,
							"top_right", 4000);
				} catch (IOException e) {
					Clients.showNotification("Error al guardar la imagen frontal.", Clients.NOTIFICATION_TYPE_ERROR,
							dSolicitudes, "top_right", 4000, true);
					e.printStackTrace();
				}
			} else {
				Clients.showNotification("No se puede establecer una conexión con el servidor " + ip + ".",
						Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 7000, true);
				return;
			}
		} else if (media != null) {
			Clients.showNotification("El archivo seleccionado no es una imagen.", Clients.NOTIFICATION_TYPE_WARNING,
					dSolicitudes, "top_right", 7000, true);
			return;
		}
	}

	@Listen("onUpload=#btnImagenPosterior")
	public void onUpload$btnImagenPosterior(UploadEvent event) {
		media = event.getMedia();
		if (media == null) {
			Clients.showNotification("No se añadio algún archivo.", Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes,
					"top_right", 2000, true);
			return;
		}
		if (media instanceof org.zkoss.image.Image) {
			imgPosterior.setContent((org.zkoss.image.Image) media);
			BufferedImage bimg = null;
			try {
				bimg = ImageIO.read(media.getStreamData());
			} catch (IOException e) {
				e.printStackTrace();
			}
			int width = bimg.getWidth();
			int height = bimg.getHeight();
			if (width > 300) {
				width = 300;
			}
			if (height > 300) {
				height = 300;
			}
			txtNombreImagenPosterior.setText(media.getName());
			imgPosterior.setWidth(String.valueOf(width) + "px");
			imgPosterior.setHeight(String.valueOf(height) + "px");
			imgPosterior.setVisible(true);
			if (ping.validarConexionEquipo(ip)) {
				modelo_equipo equipo = new modelo_equipo();
				dao_equipo dao = new dao_equipo();
				equipo = modificar.this.equipo;
				/* ELIMINAR IMAGEN SI EXISTE */
				if (equipo.getPath_img_posterior() != null) {
					if (equipo.getPath_img_posterior().length() > 0) {
						File initial = new File(equipo.getPath_img_posterior());
						if (initial.length() != 0) {
							Path to = Paths.get(equipo.getPath_img_posterior());
							try {
								Files.delete(to);
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
				/* GUARDAR IMAGEN POSTERIOR */
				byte[] buffer = media.getByteData();
				Path to = Paths.get(path_i_p + media.getName());
				try {
					Path tempFile = Files.createTempFile("tempfile", null);
					Files.write(tempFile, media.getByteData(), StandardOpenOption.WRITE);
					Files.copy(tempFile, to, StandardCopyOption.REPLACE_EXISTING);
					Files.delete(tempFile);
					Files.write(to, buffer, StandardOpenOption.WRITE);
					equipo.setPath_img_posterior(path_i_p + media.getName());
					dao.actualizarEquipo(equipo);
					Clients.showNotification("La imagen posterior se cargó correctamente.", "info", dSolicitudes,
							"top_right", 4000);
				} catch (IOException e) {
					Clients.showNotification("Error al guardar la imagen posterior.", Clients.NOTIFICATION_TYPE_ERROR,
							dSolicitudes, "top_right", 4000, true);
					e.printStackTrace();
				}
			} else {
				Clients.showNotification("No se puede establecer una conexión con el servidor " + ip + ".",
						Clients.NOTIFICATION_TYPE_WARNING, dSolicitudes, "top_right", 7000, true);
				return;
			}
		} else if (media != null) {
			Clients.showNotification("El archivo seleccionado no es una imagen.", Clients.NOTIFICATION_TYPE_WARNING,
					dSolicitudes, "top_right", 7000, true);
			return;
		}
	}

}
