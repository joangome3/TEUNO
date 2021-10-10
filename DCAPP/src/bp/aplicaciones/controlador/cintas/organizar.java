package bp.aplicaciones.controlador.cintas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
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
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Window;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.mantenimientos.DAO.dao_articulo_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_articulo_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_categoria_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_ubicacion_dn;
import bp.aplicaciones.mensajes.Informativos;

@SuppressWarnings({ "serial", "deprecation" })
public class organizar extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zOrganizar;
	@Wire
	Button btnGrabar, btnCancelar, btnPasarItemDerecha, btnPasarItemsDerecha, btnPasarItemIzquierda,
			btnPasarItemsIzquierda, btnBajarItemDerecha, btnSubirItemDerecha, btnBajarItemIzquierda,
			btnSubirItemIzquierda, btnRefrescar1, btnRefrescar2;
	@Wire
	Listbox lbxUbicaciones1, lbxUbicaciones2, lbxArticulos1, lbxArticulos2;
	@Wire
	Bandbox bdxUbicacion1, bdxUbicacion2;
	@Wire
	Combobox cmbCategoria1, cmbCategoria2;
	@Wire
	Textbox txtBuscarUbicacion1, txtBuscarUbicacion2, txtBuscarArticulo1, txtBuscarArticulo2;
	@Wire
	Checkbox chkCategoria1, chkCategoria2;
	@Wire
	Label lblArticulos1, lblArticulos2;

	private ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	private Informativos informativos = new Informativos();

	List<modelo_articulo_dn> listaArticulo1 = new ArrayList<modelo_articulo_dn>();
	List<modelo_articulo_dn> listaArticulo2 = new ArrayList<modelo_articulo_dn>();
	List<modelo_categoria_dn> listaCategoria1 = new ArrayList<modelo_categoria_dn>();
	List<modelo_categoria_dn> listaCategoria2 = new ArrayList<modelo_categoria_dn>();
	List<modelo_ubicacion_dn> listaUbicacion1 = new ArrayList<modelo_ubicacion_dn>();
	List<modelo_ubicacion_dn> listaUbicacion2 = new ArrayList<modelo_ubicacion_dn>();
	List<modelo_parametros_generales_1> listaParametros = new ArrayList<modelo_parametros_generales_1>();

	long id = 0;
	long id_mantenimiento = 16;

	/*
	 * Variables de sesión
	 */

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	validar_datos validar = new validar_datos();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		inicializarListas();
		txtBuscarArticulo1.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtBuscarArticulo1.setText(txtBuscarArticulo1.getText().trim().toUpperCase());
			}
		});
		txtBuscarArticulo2.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtBuscarArticulo2.setText(txtBuscarArticulo2.getText().trim().toUpperCase());
			}
		});
	}

	private void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
		lbxUbicaciones1.setEmptyMessage(informativos.getMensaje_informativo_2());
		lbxUbicaciones2.setEmptyMessage(informativos.getMensaje_informativo_2());
		lbxArticulos1.setEmptyMessage(informativos.getMensaje_informativo_2());
		lbxArticulos2.setEmptyMessage(informativos.getMensaje_informativo_2());
		listaParametros = consultasABaseDeDatos.cargarParametros1();
		listaCategoria1 = consultasABaseDeDatos.cargarCategoriasDN("", id_dc, 4, 0, 0);
		listaCategoria2 = consultasABaseDeDatos.cargarCategoriasDN("", id_dc, 4, 0, 0);
		listaUbicacion1 = consultasABaseDeDatos.cargarUbicacionesDN("", id_dc, 6, 0, 0, 0, 0);
		listaUbicacion2 = consultasABaseDeDatos.cargarUbicacionesDN("", id_dc, 6, 0, 0, 0, 0);
		binder.loadComponent(lbxUbicaciones1);
		binder.loadComponent(lbxUbicaciones2);
		binder.loadComponent(cmbCategoria1);
		binder.loadComponent(cmbCategoria2);
	}

	public List<modelo_parametros_generales_1> obtenerParametros() {
		return listaParametros;
	}

	public List<modelo_categoria_dn> obtenerCategorias1() {
		return listaCategoria1;
	}

	public List<modelo_categoria_dn> obtenerCategorias2() {
		return listaCategoria2;
	}

	public List<modelo_ubicacion_dn> obtenerUbicaciones1() {
		return listaUbicacion1;
	}

	public List<modelo_ubicacion_dn> obtenerUbicaciones2() {
		return listaUbicacion2;
	}

	public List<modelo_articulo_dn> obtenerArticulos1() {
		return listaArticulo1;
	}

	public List<modelo_articulo_dn> obtenerArticulos2() {
		return listaArticulo2;
	}

	public void mostrarCantidadDeArticulos(int index, int direccion)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (direccion == 1) {
			int totalArticulos = consultasABaseDeDatos
					.totalArticulosEnUbicacionDN(listaUbicacion1.get(index).getId_ubicacion());
			if (totalArticulos == 0) {
				lblArticulos1.setValue(informativos.getMensaje_informativo_14());
			}
			if (totalArticulos == 1) {
				lblArticulos1.setValue(
						informativos.getMensaje_informativo_13().replace("?", String.valueOf(totalArticulos)));
			}
			if (totalArticulos > 1) {
				lblArticulos1.setValue(
						informativos.getMensaje_informativo_12().replace("?", String.valueOf(totalArticulos)));
			}
		} else {
			int totalArticulos = consultasABaseDeDatos
					.totalArticulosEnUbicacionDN(listaUbicacion2.get(index).getId_ubicacion());
			if (totalArticulos == 0) {
				lblArticulos2.setValue(informativos.getMensaje_informativo_14());
			}
			if (totalArticulos == 1) {
				lblArticulos2.setValue(
						informativos.getMensaje_informativo_13().replace("?", String.valueOf(totalArticulos)));
			}
			if (totalArticulos > 1) {
				lblArticulos2.setValue(
						informativos.getMensaje_informativo_12().replace("?", String.valueOf(totalArticulos)));
			}
		}
	}

	@Listen("onOK=#txtBuscarArticulo1")
	public void onOKtxtBuscarArticulo1()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (lbxUbicaciones1.getSelectedItem() == null) {
			bdxUbicacion1.setErrorMessage(informativos.getMensaje_informativo_3());
			return;
		}
		String id_categoria = "";
		if (cmbCategoria1.getSelectedItem() != null) {
			id_categoria = cmbCategoria1.getSelectedItem().getValue().toString();
		}
		int index = lbxUbicaciones1.getSelectedIndex();
		listaArticulo1 = consultasABaseDeDatos.cargarArticulosDN(txtBuscarArticulo1.getText().trim(), id_dc,
				id_categoria, 12, 0, String.valueOf(listaUbicacion1.get(index).getId_ubicacion()), "");
		mostrarCantidadDeArticulos(index, 1);
		binder.loadComponent(lbxArticulos1);
	}

	@Listen("onClick=#btnRefrescar1")
	public void onClickbtnRefrescar1()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		onOKtxtBuscarArticulo1();
	}

	@Listen("onClick=#btnRefrescar2")
	public void onClickbtnRefrescar2()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		onOKtxtBuscarArticulo2();
	}

	@Listen("onOK=#txtBuscarArticulo2")
	public void onOKtxtBuscarArticulo2()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (lbxUbicaciones2.getSelectedItem() == null) {
			bdxUbicacion2.setErrorMessage(informativos.getMensaje_informativo_3());
			return;
		}
		String id_categoria = "";
		if (cmbCategoria2.getSelectedItem() != null) {
			id_categoria = cmbCategoria2.getSelectedItem().getValue().toString();
		}
		int index = lbxUbicaciones2.getSelectedIndex();
		listaArticulo2 = consultasABaseDeDatos.cargarArticulosDN(txtBuscarArticulo2.getText().trim(), id_dc,
				id_categoria, 12, 0, String.valueOf(listaUbicacion2.get(index).getId_ubicacion()), "");
		mostrarCantidadDeArticulos(index, 2);
		binder.loadComponent(lbxArticulos2);
	}

	@Listen("onCheck=#chkCategoria1")
	public void onCheckCategoria1() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxUbicaciones1.getSelectedItem() == null) {
			lblArticulos1.setValue("");
			bdxUbicacion1.setErrorMessage(informativos.getMensaje_informativo_3());
			chkCategoria1.setChecked(true);
			return;
		}
		int index = lbxUbicaciones1.getSelectedIndex();
		if (!chkCategoria1.isChecked()) {
			cmbCategoria1.setDisabled(false);
			listaArticulo1 = new ArrayList<modelo_articulo_dn>();
		} else {
			cmbCategoria1.setDisabled(true);
			cmbCategoria1.setSelectedIndex(-1);
			listaArticulo1 = consultasABaseDeDatos.cargarArticulosDN("", id_dc,
					String.valueOf(listaUbicacion1.get(index).getId_ubicacion()), 11, 0,
					txtBuscarArticulo1.getText().trim(), "");
		}
		binder.loadComponent(lbxArticulos1);
	}

	@Listen("onCheck=#chkCategoria2")
	public void onCheckCategoria2() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxUbicaciones2.getSelectedItem() == null) {
			lblArticulos2.setValue("");
			bdxUbicacion2.setErrorMessage(informativos.getMensaje_informativo_3());
			chkCategoria2.setChecked(true);
			return;
		}
		int index = lbxUbicaciones2.getSelectedIndex();
		if (!chkCategoria2.isChecked()) {
			cmbCategoria2.setDisabled(false);
			listaArticulo2 = new ArrayList<modelo_articulo_dn>();
		} else {
			cmbCategoria2.setDisabled(true);
			cmbCategoria2.setSelectedIndex(-1);
			listaArticulo2 = consultasABaseDeDatos.cargarArticulosDN("", id_dc,
					String.valueOf(listaUbicacion2.get(index).getId_ubicacion()), 11, 0,
					txtBuscarArticulo2.getText().trim(), "");
		}
		binder.loadComponent(lbxArticulos2);
	}

	@Listen("onOK=#txtBuscarUbicacion1")
	public void onOKtxtBuscarUbicacion1()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		listaUbicacion1 = consultasABaseDeDatos.cargarUbicacionesDN(txtBuscarUbicacion1.getText().toString(), id_dc, 6,
				0, 0, 0, 0);
		txtBuscarUbicacion1.setTooltiptext("");
		binder.loadComponent(lbxUbicaciones1);
	}

	@Listen("onOK=#txtBuscarUbicacion2")
	public void onOKtxtBuscarUbicacion2()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		listaUbicacion2 = consultasABaseDeDatos.cargarUbicacionesDN(txtBuscarUbicacion2.getText().toString(), id_dc, 6,
				0, 0, 0, 0);
		txtBuscarUbicacion2.setTooltiptext("");
		binder.loadComponent(lbxUbicaciones2);
	}

	@Listen("onSelect=#lbxUbicaciones1")
	public void onSelectlbxUbicaciones1() throws Throwable {
		if (lbxUbicaciones1.getSelectedItem() == null) {
			lblArticulos1.setValue("");
			bdxUbicacion1.setText("");
			return;
		}
		int index = lbxUbicaciones1.getSelectedIndex();
		if (lbxUbicaciones2.getSelectedItem() != null) {
			if (listaUbicacion1.get(lbxUbicaciones1.getSelectedIndex()).getId_ubicacion() == listaUbicacion2
					.get(lbxUbicaciones2.getSelectedIndex()).getId_ubicacion()) {
				index = 0;
				lblArticulos1.setValue("");
				lbxUbicaciones1.clearSelection();
				listaArticulo1 = new ArrayList<modelo_articulo_dn>();
				bdxUbicacion1.setText("");
				bdxUbicacion1.setErrorMessage(informativos.getMensaje_informativo_4());
				binder.loadComponent(lbxArticulos1);
				return;
			}
		}
		String ubicacion = listaUbicacion1.get(index).getNom_ubicacion() + " - "
				+ listaUbicacion1.get(index).getPos_ubicacion();
		bdxUbicacion1.setText(ubicacion);
		if (chkCategoria1.isChecked()) {
			listaArticulo1 = consultasABaseDeDatos.cargarArticulosDN("", id_dc,
					String.valueOf(listaUbicacion1.get(index).getId_ubicacion()), 11, 0,
					txtBuscarArticulo1.getText().trim(), "");
		} else {
			listaArticulo1 = consultasABaseDeDatos.cargarArticulosDN(
					cmbCategoria1.getSelectedItem().getValue().toString(), id_dc,
					String.valueOf(listaUbicacion1.get(index).getId_ubicacion()), 11, 0,
					txtBuscarArticulo1.getText().trim(), "");
		}
		mostrarCantidadDeArticulos(index, 1);
		binder.loadComponent(lbxArticulos1);
	}

	@Listen("onSelect=#lbxUbicaciones2")
	public void onSelectlbxUbicaciones2() throws Throwable {
		if (lbxUbicaciones2.getSelectedItem() == null) {
			lblArticulos2.setValue("");
			bdxUbicacion2.setText("");
			return;
		}
		int index = lbxUbicaciones2.getSelectedIndex();
		if (lbxUbicaciones1.getSelectedItem() != null) {
			if (listaUbicacion1.get(lbxUbicaciones1.getSelectedIndex()).getId_ubicacion() == listaUbicacion2
					.get(lbxUbicaciones2.getSelectedIndex()).getId_ubicacion()) {
				index = 0;
				lblArticulos2.setValue("");
				lbxUbicaciones2.clearSelection();
				listaArticulo2 = new ArrayList<modelo_articulo_dn>();
				bdxUbicacion2.setText("");
				bdxUbicacion2.setErrorMessage(informativos.getMensaje_informativo_4());
				binder.loadComponent(lbxArticulos2);
				return;
			}
		}
		String ubicacion = listaUbicacion2.get(index).getNom_ubicacion() + " - "
				+ listaUbicacion2.get(index).getPos_ubicacion();
		bdxUbicacion2.setText(ubicacion);
		if (chkCategoria2.isChecked()) {
			listaArticulo2 = consultasABaseDeDatos.cargarArticulosDN("", id_dc,
					String.valueOf(listaUbicacion2.get(index).getId_ubicacion()), 11, 0,
					txtBuscarArticulo2.getText().trim(), "");
		} else {
			listaArticulo2 = consultasABaseDeDatos.cargarArticulosDN(
					cmbCategoria2.getSelectedItem().getValue().toString(), id_dc,
					String.valueOf(listaUbicacion2.get(index).getId_ubicacion()), 11, 0,
					txtBuscarArticulo2.getText().trim(), "");
		}
		mostrarCantidadDeArticulos(index, 2);
		binder.loadComponent(lbxArticulos2);
	}

	@Listen("onSelect=#cmbCategoria1")
	public void onSelectcmbCategoria1() throws Throwable {
		if (listaCategoria1.size() == 0) {
			return;
		}
		int index = lbxUbicaciones1.getSelectedIndex();
		listaArticulo1 = consultasABaseDeDatos.cargarArticulosDN(cmbCategoria1.getSelectedItem().getValue().toString(),
				id_dc, String.valueOf(listaUbicacion1.get(index).getId_ubicacion()), 11, 0,
				txtBuscarArticulo1.getText().trim(), "");
		binder.loadComponent(lbxArticulos1);
	}

	@Listen("onSelect=#cmbCategoria2")
	public void onSelectcmbCategoria2() throws Throwable {
		if (listaCategoria2.size() == 0) {
			return;
		}
		int index = lbxUbicaciones2.getSelectedIndex();
		listaArticulo2 = consultasABaseDeDatos.cargarArticulosDN(cmbCategoria2.getSelectedItem().getValue().toString(),
				id_dc, String.valueOf(listaUbicacion2.get(index).getId_ubicacion()), 11, 0,
				txtBuscarArticulo2.getText().trim(), "");
		binder.loadComponent(lbxArticulos2);
	}

	@Listen("onClick=#btnPasarItemDerecha")
	public void onClickbtnPasarItemDerecha() throws MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, SQLException, IOException {
		if (lbxUbicaciones1.getSelectedItem() == null) {
			bdxUbicacion1.setErrorMessage(informativos.getMensaje_informativo_3());
			return;
		}
		if (lbxUbicaciones2.getSelectedItem() == null) {
			bdxUbicacion2.setErrorMessage(informativos.getMensaje_informativo_3());
			return;
		}
		if (lbxArticulos1.getSelectedItem() == null) {
			Messagebox.show(informativos.getMensaje_informativo_6(), informativos.getMensaje_informativo_5(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		if (lbxArticulos1.getSelectedItems().size() > 1) {
			Messagebox.show(informativos.getMensaje_informativo_7(), informativos.getMensaje_informativo_5(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		int indexArticulo = lbxArticulos1.getSelectedIndex();
		int indexUbicacion = lbxUbicaciones2.getSelectedIndex();
		if (validarSiItemEstaEnListaUnico(indexArticulo, indexUbicacion, 1) == true) {
			Messagebox.show(informativos.getMensaje_informativo_86(), informativos.getMensaje_informativo_5(),
					Messagebox.OK, Messagebox.INFORMATION);
			cargarListasDeArticulos();
			return;
		}
		String seValidaCapacidad = consultasABaseDeDatos
				.seValidaCapacidadEnUbicacionDN(listaUbicacion2.get(indexUbicacion).getId_ubicacion());
		if (seValidaCapacidad.equals("S")) {
			int totalArticulos = consultasABaseDeDatos
					.totalArticulosEnUbicacionDN(listaUbicacion2.get(indexUbicacion).getId_ubicacion());
			int capacidadMaxima = consultasABaseDeDatos.capacidadMaximaEnUbicacionDN(
					listaUbicacion2.get(indexUbicacion).getId_ubicacion(), seValidaCapacidad);
			if ((totalArticulos + 1) > capacidadMaxima) {
				Messagebox.show(informativos.getMensaje_informativo_10(), informativos.getMensaje_informativo_5(),
						Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
		}
		pasarItemDerecha(indexArticulo, indexUbicacion);
		mostrarCantidadDeArticulos(lbxUbicaciones1.getSelectedIndex(), 1);
		mostrarCantidadDeArticulos(indexUbicacion, 2);
	}

	@Listen("onClick=#btnPasarItemIzquierda")
	public void onClickbtnPasarItemIzquierda() throws MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxUbicaciones2.getSelectedItem() == null) {
			bdxUbicacion2.setErrorMessage(informativos.getMensaje_informativo_3());
			return;
		}
		if (lbxUbicaciones1.getSelectedItem() == null) {
			bdxUbicacion1.setErrorMessage(informativos.getMensaje_informativo_3());
			return;
		}
		if (lbxArticulos2.getSelectedItem() == null) {
			Messagebox.show(informativos.getMensaje_informativo_6(), informativos.getMensaje_informativo_5(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		if (lbxArticulos2.getSelectedItems().size() > 1) {
			Messagebox.show(informativos.getMensaje_informativo_7(), informativos.getMensaje_informativo_5(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		int indexArticulo = lbxArticulos2.getSelectedIndex();
		int indexUbicacion = lbxUbicaciones1.getSelectedIndex();
		if (validarSiItemEstaEnListaUnico(indexArticulo, indexUbicacion, 2) == true) {
			Messagebox.show(informativos.getMensaje_informativo_86(), informativos.getMensaje_informativo_5(),
					Messagebox.OK, Messagebox.INFORMATION);
			cargarListasDeArticulos();
			return;
		}
		String seValidaCapacidad = consultasABaseDeDatos
				.seValidaCapacidadEnUbicacionDN(listaUbicacion1.get(indexUbicacion).getId_ubicacion());
		if (seValidaCapacidad.equals("S")) {
			int totalArticulos = consultasABaseDeDatos
					.totalArticulosEnUbicacionDN(listaUbicacion1.get(indexUbicacion).getId_ubicacion());
			int capacidadMaxima = consultasABaseDeDatos.capacidadMaximaEnUbicacionDN(
					listaUbicacion1.get(indexUbicacion).getId_ubicacion(), seValidaCapacidad);
			if ((totalArticulos + 1) > capacidadMaxima) {
				Messagebox.show(informativos.getMensaje_informativo_10(), informativos.getMensaje_informativo_5(),
						Messagebox.OK, Messagebox.INFORMATION);
				return;
			}
		}
		pasarItemIzquierda(indexArticulo, indexUbicacion);
		mostrarCantidadDeArticulos(lbxUbicaciones2.getSelectedIndex(), 2);
		mostrarCantidadDeArticulos(indexUbicacion, 1);
	}

	@Listen("onClick=#btnPasarItemsDerecha")
	public void onClickbtnPasarItemsDerecha() throws MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, SQLException, IOException {
		if (lbxUbicaciones1.getSelectedItem() == null) {
			bdxUbicacion1.setErrorMessage(informativos.getMensaje_informativo_3());
			return;
		}
		if (lbxUbicaciones2.getSelectedItem() == null) {
			bdxUbicacion2.setErrorMessage(informativos.getMensaje_informativo_3());
			return;
		}
		if (lbxArticulos1.getSelectedItem() == null) {
			Messagebox.show(informativos.getMensaje_informativo_6(), informativos.getMensaje_informativo_5(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		int indexUbicacion = lbxUbicaciones2.getSelectedIndex();
		Iterator<Listitem> it = lbxArticulos1.getSelectedItems().iterator();
		List<modelo_articulo_dn> listaArticulo = new ArrayList<modelo_articulo_dn>();
		while (it.hasNext()) {
			Listitem listItem = it.next();
			int index = listItem.getIndex();
			listaArticulo.add(listaArticulo1.get(index));
		}
		int contador = 0;
		for (int i = 0; i < listaArticulo.size(); i++) {
			if (validarSiItemEstaEnListaVarios(listaArticulo.get(i).getId_articulo(), indexUbicacion, 1) == true) {
				contador++;
				i = listaArticulo.size() + 1;
			}
		}
		if (contador > 0) {
			Messagebox.show(informativos.getMensaje_informativo_87(), informativos.getMensaje_informativo_5(),
					Messagebox.OK, Messagebox.INFORMATION);
			cargarListasDeArticulos();
			return;
		}
		String seValidaCapacidad = consultasABaseDeDatos
				.seValidaCapacidadEnUbicacionDN(listaUbicacion2.get(indexUbicacion).getId_ubicacion());
		if (seValidaCapacidad.equals("S")) {
			int totalArticulos = consultasABaseDeDatos
					.totalArticulosEnUbicacionDN(listaUbicacion2.get(indexUbicacion).getId_ubicacion());
			int capacidadMaxima = consultasABaseDeDatos.capacidadMaximaEnUbicacionDN(
					listaUbicacion2.get(indexUbicacion).getId_ubicacion(), seValidaCapacidad);
			if ((totalArticulos + lbxArticulos1.getSelectedCount()) > capacidadMaxima) {
				if (lbxArticulos1.getSelectedCount() == 1) {
					Messagebox.show(informativos.getMensaje_informativo_10(), informativos.getMensaje_informativo_5(),
							Messagebox.OK, Messagebox.INFORMATION);
				}
				if (lbxArticulos1.getSelectedCount() > 1) {
					Messagebox.show(informativos.getMensaje_informativo_11(), informativos.getMensaje_informativo_5(),
							Messagebox.OK, Messagebox.INFORMATION);
				}
				return;
			}
		}
		pasarItemsDerecha(listaArticulo, indexUbicacion);
		mostrarCantidadDeArticulos(lbxUbicaciones1.getSelectedIndex(), 1);
		mostrarCantidadDeArticulos(indexUbicacion, 2);
	}

	@Listen("onClick=#btnPasarItemsIzquierda")
	public void onClickbtnPasarItemsIzquierda() throws MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxUbicaciones2.getSelectedItem() == null) {
			bdxUbicacion2.setErrorMessage(informativos.getMensaje_informativo_3());
			return;
		}
		if (lbxUbicaciones1.getSelectedItem() == null) {
			bdxUbicacion1.setErrorMessage(informativos.getMensaje_informativo_3());
			return;
		}
		if (lbxArticulos2.getSelectedItem() == null) {
			Messagebox.show(informativos.getMensaje_informativo_6(), informativos.getMensaje_informativo_5(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		int indexUbicacion = lbxUbicaciones1.getSelectedIndex();
		Iterator<Listitem> it = lbxArticulos2.getSelectedItems().iterator();
		List<modelo_articulo_dn> listaArticulo = new ArrayList<modelo_articulo_dn>();
		while (it.hasNext()) {
			Listitem listItem = it.next();
			int index = listItem.getIndex();
			listaArticulo.add(listaArticulo2.get(index));
		}
		int contador = 0;
		for (int i = 0; i < listaArticulo.size(); i++) {
			if (validarSiItemEstaEnListaVarios(listaArticulo.get(i).getId_articulo(), indexUbicacion, 2) == true) {
				contador++;
				i = listaArticulo.size() + 1;
			}
		}
		if (contador > 0) {
			Messagebox.show(informativos.getMensaje_informativo_87(), informativos.getMensaje_informativo_5(),
					Messagebox.OK, Messagebox.INFORMATION);
			cargarListasDeArticulos();
			return;
		}
		String seValidaCapacidad = consultasABaseDeDatos
				.seValidaCapacidadEnUbicacionDN(listaUbicacion1.get(indexUbicacion).getId_ubicacion());
		if (seValidaCapacidad.equals("S")) {
			int totalArticulos = consultasABaseDeDatos
					.totalArticulosEnUbicacionDN(listaUbicacion1.get(indexUbicacion).getId_ubicacion());
			int capacidadMaxima = consultasABaseDeDatos.capacidadMaximaEnUbicacionDN(
					listaUbicacion1.get(indexUbicacion).getId_ubicacion(), seValidaCapacidad);
			if ((totalArticulos + lbxArticulos2.getSelectedCount()) > capacidadMaxima) {
				if (lbxArticulos2.getSelectedCount() == 1) {
					Messagebox.show(informativos.getMensaje_informativo_10(), informativos.getMensaje_informativo_5(),
							Messagebox.OK, Messagebox.INFORMATION);
				}
				if (lbxArticulos2.getSelectedCount() > 1) {
					Messagebox.show(informativos.getMensaje_informativo_11(), informativos.getMensaje_informativo_5(),
							Messagebox.OK, Messagebox.INFORMATION);
				}
				return;
			}
		}
		pasarItemsIzquierda(listaArticulo, indexUbicacion);
		mostrarCantidadDeArticulos(lbxUbicaciones2.getSelectedIndex(), 2);
		mostrarCantidadDeArticulos(indexUbicacion, 1);
	}

	public boolean validarSiItemEstaEnListaUnico(int indexArticulo, int indexUbicacion, int tipo)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		boolean esta_en_lista = false;
		List<modelo_articulo_dn> listaArticulo = new ArrayList<modelo_articulo_dn>();
		if (tipo == 1) {
			listaArticulo = consultasABaseDeDatos.cargarArticulosDN(
					String.valueOf(listaArticulo1.get(indexArticulo).getId_articulo()), id_dc, "", 13, 0, "", "");
			if (listaArticulo.size() == 1) {
				if (listaArticulo.get(0).getId_ubicacion() == listaUbicacion2.get(indexUbicacion).getId_ubicacion()) {
					esta_en_lista = true;
				}
			}
		} else {
			listaArticulo = consultasABaseDeDatos.cargarArticulosDN(
					String.valueOf(listaArticulo2.get(indexArticulo).getId_articulo()), id_dc, "", 13, 0, "", "");
			if (listaArticulo.size() == 1) {
				if (listaArticulo.get(0).getId_ubicacion() == listaUbicacion1.get(indexUbicacion).getId_ubicacion()) {
					esta_en_lista = true;
				}
			}
		}
		return esta_en_lista;
	}

	public boolean validarSiItemEstaEnListaVarios(long id_articulo, int indexUbicacion, int tipo)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		boolean esta_en_lista = false;
		List<modelo_articulo_dn> listaArticulo = new ArrayList<modelo_articulo_dn>();
		if (tipo == 1) {
			listaArticulo = consultasABaseDeDatos.cargarArticulosDN(String.valueOf(id_articulo), id_dc, "", 13, 0, "",
					"");
			if (listaArticulo.size() == 1) {
				if (listaArticulo.get(0).getId_ubicacion() == listaUbicacion2.get(indexUbicacion).getId_ubicacion()) {
					esta_en_lista = true;
				}
			}
		} else {
			listaArticulo = consultasABaseDeDatos.cargarArticulosDN(String.valueOf(id_articulo), id_dc, "", 13, 0, "",
					"");
			if (listaArticulo.size() == 1) {
				if (listaArticulo.get(0).getId_ubicacion() == listaUbicacion1.get(indexUbicacion).getId_ubicacion()) {
					esta_en_lista = true;
				}
			}
		}
		return esta_en_lista;
	}

	public void cargarListasDeArticulos()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		if (chkCategoria2.isChecked()) {
			listaArticulo2 = consultasABaseDeDatos.cargarArticulosDN("", id_dc,
					String.valueOf(listaUbicacion2.get(lbxUbicaciones2.getSelectedIndex()).getId_ubicacion()), 11, 0,
					txtBuscarArticulo2.getText().trim(), "");
		} else {
			listaArticulo2 = consultasABaseDeDatos.cargarArticulosDN(
					cmbCategoria2.getSelectedItem().getValue().toString(), id_dc,
					String.valueOf(listaUbicacion2.get(lbxUbicaciones2.getSelectedIndex()).getId_ubicacion()), 11, 0,
					txtBuscarArticulo2.getText().trim(), "");
		}
		mostrarCantidadDeArticulos(lbxUbicaciones2.getSelectedIndex(), 2);
		binder.loadComponent(lbxArticulos2);
		if (chkCategoria1.isChecked()) {
			listaArticulo1 = consultasABaseDeDatos.cargarArticulosDN("", id_dc,
					String.valueOf(listaUbicacion1.get(lbxUbicaciones1.getSelectedIndex()).getId_ubicacion()), 11, 0,
					txtBuscarArticulo1.getText().trim(), "");
		} else {
			listaArticulo1 = consultasABaseDeDatos.cargarArticulosDN(
					cmbCategoria1.getSelectedItem().getValue().toString(), id_dc,
					String.valueOf(listaUbicacion1.get(lbxUbicaciones1.getSelectedIndex()).getId_ubicacion()), 11, 0,
					txtBuscarArticulo1.getText().trim(), "");
		}
		mostrarCantidadDeArticulos(lbxUbicaciones1.getSelectedIndex(), 1);
		binder.loadComponent(lbxArticulos1);
	}

	public void pasarItemDerecha(int indexArticulo, int indexUbicacion)
			throws MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException,
			SQLException, IOException {
		final int pos_ubicacion = consultasABaseDeDatos
				.posicionMaximaEnUbicacionDN(listaUbicacion2.get(indexUbicacion).getId_ubicacion()) + 1;
		dao_articulo_dn dao = new dao_articulo_dn();
		if (dao.actualizarUbicacionArticuloIzquierdaDerecha(listaArticulo1.get(indexArticulo).getId_articulo(),
				listaUbicacion2.get(indexUbicacion).getId_ubicacion(), pos_ubicacion, 1)) {
			listaArticulo2.add(listaArticulo1.get(indexArticulo));
			listaArticulo2.get(listaArticulo2.size() - 1).setPos_ubicacion(pos_ubicacion);
			listaArticulo1.remove(indexArticulo);
			binder.loadComponent(lbxArticulos1);
			binder.loadComponent(lbxArticulos2);
		}
	}

	public void pasarItemIzquierda(int indexArticulo, int indexUbicacion)
			throws MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException,
			SQLException, IOException {
		final int pos_ubicacion = consultasABaseDeDatos
				.posicionMaximaEnUbicacionDN(listaUbicacion1.get(indexUbicacion).getId_ubicacion()) + 1;
		dao_articulo_dn dao = new dao_articulo_dn();
		if (dao.actualizarUbicacionArticuloIzquierdaDerecha(listaArticulo2.get(indexArticulo).getId_articulo(),
				listaUbicacion1.get(indexUbicacion).getId_ubicacion(), pos_ubicacion, 1)) {
			listaArticulo1.add(listaArticulo2.get(indexArticulo));
			listaArticulo1.get(listaArticulo1.size() - 1).setPos_ubicacion(pos_ubicacion);
			listaArticulo2.remove(indexArticulo);
			binder.loadComponent(lbxArticulos1);
			binder.loadComponent(lbxArticulos2);
		}
	}

	public void pasarItemsDerecha(List<modelo_articulo_dn> listaArticulos, int indexUbicacion)
			throws MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException,
			SQLException, IOException {
		final int pos_ubicacion = consultasABaseDeDatos
				.posicionMaximaEnUbicacionDN(listaUbicacion2.get(indexUbicacion).getId_ubicacion()) + 1;
		dao_articulo_dn dao = new dao_articulo_dn();
		if (dao.actualizarUbicacionArticulos(listaArticulos, listaUbicacion2.get(indexUbicacion).getId_ubicacion(),
				pos_ubicacion, 1)) {
			Iterator<Listitem> it = lbxArticulos1.getSelectedItems().iterator();
			int pos = pos_ubicacion;
			while (it.hasNext()) {
				Listitem listItem = it.next();
				int index = listItem.getIndex();
				listaArticulo2.add(listaArticulo1.get(index));
				listaArticulo2.get(listaArticulo2.size() - 1).setPos_ubicacion(pos);
				pos++;
			}
			listaArticulo1.removeAll(listaArticulos);
			binder.loadComponent(lbxArticulos1);
			binder.loadComponent(lbxArticulos2);
		}
	}

	public void pasarItemsIzquierda(List<modelo_articulo_dn> listaArticulos, int indexUbicacion)
			throws MySQLIntegrityConstraintViolationException, ClassNotFoundException, FileNotFoundException,
			SQLException, IOException {
		final int pos_ubicacion = consultasABaseDeDatos
				.posicionMaximaEnUbicacionDN(listaUbicacion1.get(indexUbicacion).getId_ubicacion()) + 1;
		dao_articulo_dn dao = new dao_articulo_dn();
		if (dao.actualizarUbicacionArticulos(listaArticulos, listaUbicacion1.get(indexUbicacion).getId_ubicacion(),
				pos_ubicacion, 1)) {
			Iterator<Listitem> it = lbxArticulos2.getSelectedItems().iterator();
			int pos = pos_ubicacion;
			while (it.hasNext()) {
				Listitem listItem = it.next();
				int index = listItem.getIndex();
				listaArticulo1.add(listaArticulo2.get(index));
				listaArticulo1.get(listaArticulo1.size() - 1).setPos_ubicacion(pos);
				pos++;
			}
			listaArticulo2.removeAll(listaArticulos);
			binder.loadComponent(lbxArticulos1);
			binder.loadComponent(lbxArticulos2);
		}
	}

	@Listen("onClick=#btnBajarItemIzquierda")
	public void onClickbtnBajarItemIzquierda() throws MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxUbicaciones1.getSelectedItem() == null) {
			bdxUbicacion1.setErrorMessage(informativos.getMensaje_informativo_3());
			return;
		}
		if (lbxArticulos1.getSelectedItem() == null) {
			Messagebox.show(informativos.getMensaje_informativo_6(), informativos.getMensaje_informativo_5(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		if (lbxArticulos1.getSelectedItems().size() > 1) {
			Messagebox.show(informativos.getMensaje_informativo_7(), informativos.getMensaje_informativo_5(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		int indexArticulo = lbxArticulos1.getSelectedIndex();
		bajarItemIzquierda(indexArticulo);
	}

	@Listen("onClick=#btnBajarItemDerecha")
	public void onClickbtnBajarItemDerecha() throws MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, SQLException, IOException {
		if (lbxUbicaciones2.getSelectedItem() == null) {
			bdxUbicacion2.setErrorMessage(informativos.getMensaje_informativo_3());
			return;
		}
		if (lbxArticulos2.getSelectedItem() == null) {
			Messagebox.show(informativos.getMensaje_informativo_6(), informativos.getMensaje_informativo_5(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		if (lbxArticulos2.getSelectedItems().size() > 1) {
			Messagebox.show(informativos.getMensaje_informativo_7(), informativos.getMensaje_informativo_5(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		int indexArticulo = lbxArticulos2.getSelectedIndex();
		bajarItemDerecha(indexArticulo);
	}

	public void bajarItemIzquierda(int indexArticulo) throws MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (indexArticulo == listaArticulo1.size() - 1) {
			Messagebox.show(informativos.getMensaje_informativo_9(), informativos.getMensaje_informativo_5(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		modelo_articulo_dn articulo1 = listaArticulo1.get(indexArticulo);
		modelo_articulo_dn articulo2 = listaArticulo1.get(indexArticulo + 1);
		final int pos_ubicacion_1 = articulo1.getPos_ubicacion();
		final int pos_ubicacion_2 = articulo2.getPos_ubicacion();
		dao_articulo_dn dao = new dao_articulo_dn();
		if (dao.actualizarUbicacionArticuloAbajoArriba(articulo1.getId_articulo(), pos_ubicacion_2,
				articulo2.getId_articulo(), pos_ubicacion_1, 0, 0, 2)) {
			listaArticulo1.get(indexArticulo).setPos_ubicacion(pos_ubicacion_2);
			listaArticulo1.get(indexArticulo + 1).setPos_ubicacion(pos_ubicacion_1);
			listaArticulo1.set(indexArticulo, articulo2);
			listaArticulo1.set(indexArticulo + 1, articulo1);
		}
		binder.loadComponent(lbxArticulos1);
	}

	public void bajarItemDerecha(int indexArticulo) throws MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (indexArticulo == listaArticulo2.size() - 1) {
			Messagebox.show(informativos.getMensaje_informativo_9(), informativos.getMensaje_informativo_5(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		modelo_articulo_dn articulo1 = listaArticulo2.get(indexArticulo);
		modelo_articulo_dn articulo2 = listaArticulo2.get(indexArticulo + 1);
		final int pos_ubicacion_1 = articulo1.getPos_ubicacion();
		final int pos_ubicacion_2 = articulo2.getPos_ubicacion();
		dao_articulo_dn dao = new dao_articulo_dn();
		if (dao.actualizarUbicacionArticuloAbajoArriba(articulo1.getId_articulo(), pos_ubicacion_2,
				articulo2.getId_articulo(), pos_ubicacion_1, 0, 0, 2)) {
			listaArticulo2.get(indexArticulo).setPos_ubicacion(pos_ubicacion_2);
			listaArticulo2.get(indexArticulo + 1).setPos_ubicacion(pos_ubicacion_1);
			listaArticulo2.set(indexArticulo, articulo2);
			listaArticulo2.set(indexArticulo + 1, articulo1);
		}
		binder.loadComponent(lbxArticulos2);
	}

	@Listen("onClick=#btnSubirItemIzquierda")
	public void onClickbtnSubirItemIzquierda() throws MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (lbxUbicaciones1.getSelectedItem() == null) {
			bdxUbicacion1.setErrorMessage(informativos.getMensaje_informativo_3());
			return;
		}
		if (lbxArticulos1.getSelectedItem() == null) {
			Messagebox.show(informativos.getMensaje_informativo_6(), informativos.getMensaje_informativo_5(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		if (lbxArticulos1.getSelectedItems().size() > 1) {
			Messagebox.show(informativos.getMensaje_informativo_7(), informativos.getMensaje_informativo_5(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		int indexArticulo = lbxArticulos1.getSelectedIndex();
		subirItemIzquierda(indexArticulo);
	}

	@Listen("onClick=#btnSubirItemDerecha")
	public void onClickbtnSubirItemDerecha() throws MySQLIntegrityConstraintViolationException, ClassNotFoundException,
			FileNotFoundException, SQLException, IOException {
		if (lbxUbicaciones2.getSelectedItem() == null) {
			bdxUbicacion2.setErrorMessage(informativos.getMensaje_informativo_3());
			return;
		}
		if (lbxArticulos2.getSelectedItem() == null) {
			Messagebox.show(informativos.getMensaje_informativo_6(), informativos.getMensaje_informativo_5(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		if (lbxArticulos2.getSelectedItems().size() > 1) {
			Messagebox.show(informativos.getMensaje_informativo_7(), informativos.getMensaje_informativo_5(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		int indexArticulo = lbxArticulos2.getSelectedIndex();
		subirItemDerecha(indexArticulo);
	}

	public void subirItemIzquierda(int indexArticulo) throws MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (indexArticulo == 0) {
			Messagebox.show(informativos.getMensaje_informativo_8(), informativos.getMensaje_informativo_5(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		modelo_articulo_dn articulo1 = listaArticulo1.get(indexArticulo);
		modelo_articulo_dn articulo2 = listaArticulo1.get(indexArticulo - 1);
		final int pos_ubicacion_1 = articulo1.getPos_ubicacion();
		final int pos_ubicacion_2 = articulo2.getPos_ubicacion();
		dao_articulo_dn dao = new dao_articulo_dn();
		if (dao.actualizarUbicacionArticuloAbajoArriba(articulo1.getId_articulo(), pos_ubicacion_2,
				articulo2.getId_articulo(), pos_ubicacion_1, 0, 0, 2)) {
			listaArticulo1.get(indexArticulo).setPos_ubicacion(pos_ubicacion_2);
			listaArticulo1.get(indexArticulo - 1).setPos_ubicacion(pos_ubicacion_1);
			listaArticulo1.set(indexArticulo, articulo2);
			listaArticulo1.set(indexArticulo - 1, articulo1);
		}
		binder.loadComponent(lbxArticulos1);
	}

	public void subirItemDerecha(int indexArticulo) throws MySQLIntegrityConstraintViolationException,
			ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		if (indexArticulo == 0) {
			Messagebox.show(informativos.getMensaje_informativo_8(), informativos.getMensaje_informativo_5(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		modelo_articulo_dn articulo1 = listaArticulo2.get(indexArticulo);
		modelo_articulo_dn articulo2 = listaArticulo2.get(indexArticulo - 1);
		final int pos_ubicacion_1 = articulo1.getPos_ubicacion();
		final int pos_ubicacion_2 = articulo2.getPos_ubicacion();
		dao_articulo_dn dao = new dao_articulo_dn();
		if (dao.actualizarUbicacionArticuloAbajoArriba(articulo1.getId_articulo(), pos_ubicacion_2,
				articulo2.getId_articulo(), pos_ubicacion_1, 0, 0, 2)) {
			listaArticulo2.get(indexArticulo).setPos_ubicacion(pos_ubicacion_2);
			listaArticulo2.get(indexArticulo - 1).setPos_ubicacion(pos_ubicacion_1);
			listaArticulo2.set(indexArticulo, articulo2);
			listaArticulo2.set(indexArticulo - 1, articulo1);
		}
		binder.loadComponent(lbxArticulos2);
	}

	@Listen("onDrop=#lbxArticulos1")
	public void onDroplbxArticulos1() {

	}

	@Listen("onDrop=#lbxArticulos2")
	public void onDroplbxArticulos2() {

	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zOrganizar));
	}

}
