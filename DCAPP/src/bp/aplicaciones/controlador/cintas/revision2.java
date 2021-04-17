package bp.aplicaciones.controlador.cintas;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menuseparator;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Timebox;
import org.zkoss.zul.Window;

import bp.aplicaciones.cintas.modelo.modelo_movimiento_detalle_dn;
import bp.aplicaciones.cintas.modelo.modelo_movimiento_dn;
import bp.aplicaciones.controlador.validar_datos;
import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.modelo.modelo_empresa;
import bp.aplicaciones.mantenimientos.modelo.modelo_parametros_generales_1;
import bp.aplicaciones.mantenimientos.modelo.modelo_solicitante;
import bp.aplicaciones.mantenimientos.modelo.modelo_turno;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;
import bp.aplicaciones.bitacora.modelo.modelo_bitacora;
import bp.aplicaciones.bitacora.modelo.modelo_registra_turno;
import bp.aplicaciones.cintas.DAO.dao_movimiento_dn;
import bp.aplicaciones.mantenimientos.modelo.modelo_articulo_dn;

@SuppressWarnings({ "serial", "deprecation" })
public class revision2 extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zRevision;
	@Wire
	Button btnGrabar, btnCancelar, btnLimpiar;
	@Wire
	Label lblRespuesta, lTitulo;
	@Wire
	Textbox txtId, txtOperador, txtBuscarProveedor, txtBuscarArticulo, txtObservacion;
	@Wire
	Combobox cmbEmpresa, cmbTurno, cmbPedido, cmbEstado;
	@Wire
	Datebox dtxFechaSolicitud, dtxFechaRespuesta, dtxFechaEjecucion;
	@Wire
	Timebox tmxHoraSolicitud, tmxHoraRespuesta, tmxHoraEjecucion, tmx;
	@Wire
	Bandbox bdxArticulos, bdxSolicitantes;
	@Wire
	Listbox lbxMovimientos, lbxArticulos, lbxSolicitantes;
	@Wire
	Menuitem mModificar, mEliminar, mSiActualizaInventario, mNoActualizaInventario;
	@Wire
	Menuseparator mSeparador1, mSeparador2;
	@Wire
	Div winList;

	Window window;

	List<modelo_parametros_generales_1> listaParametros1 = new ArrayList<modelo_parametros_generales_1>();

	List<modelo_solicitante> listaSolicitante = new ArrayList<modelo_solicitante>();
	List<modelo_empresa> listaEmpresa = new ArrayList<modelo_empresa>();
	List<modelo_articulo_dn> listaArticulo = new ArrayList<modelo_articulo_dn>();
	List<modelo_registra_turno> listaRegistroTurno = new ArrayList<modelo_registra_turno>();
	List<modelo_turno> listaTurno = new ArrayList<modelo_turno>();
	List<modelo_movimiento_detalle_dn> listaMovimientoDetalle = new ArrayList<modelo_movimiento_detalle_dn>();

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();
	Validaciones validaciones = new Validaciones();

	Informativos informativos = new Informativos();
	Error error = new Error();

	long id = 0;
	long id_opcion = 4;
	long id_turno = 0;

	boolean es_turno_extendido = false;
	boolean se_inicia_turno = false;

	boolean ingresa_a_modificar = false;

	Date fecha_actual = null;
	Date fecha_inicio = null;
	Date fecha_fin = null;

	Date fecha_ingresa_formulario = null;

	Date fecha_inicio_turno_extendido = null;
	Date fecha_fin_turno_extendido = null;

	String turno = "";

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");
	String cod_sesion = (String) Sessions.getCurrent().getAttribute("cod_sesion");

	validar_datos validar = new validar_datos();

	modelo_movimiento_dn movimiento = (modelo_movimiento_dn) Sessions.getCurrent().getAttribute("movimiento");

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		Sessions.getCurrent().removeAttribute("movimiento");
		txtOperador.setText(nom_ape_user);
		lbxArticulos.setEmptyMessage(informativos.getMensaje_informativo_2());
		lbxSolicitantes.setEmptyMessage(informativos.getMensaje_informativo_2());
		lbxMovimientos.setEmptyMessage(informativos.getMensaje_informativo_2());
		txtId.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtId.setText(txtId.getText().trim().toUpperCase());
			}
		});
		txtOperador.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			@SuppressWarnings("static-access")
			public void onEvent(Event event) throws Exception {
				txtOperador.setText(txtOperador.getText().toUpperCase());
			}
		});
		txtObservacion.addEventListener(Events.ON_BLUR, new EventListener<Event>() {
			public void onEvent(Event event) throws Exception {
				txtObservacion.setText(txtObservacion.getText().toUpperCase());
			}
		});
		inicializarListas();
		setearFechaActual();
		setearFechaIngresaFormulario();
		inicializarFechas();
		cargarDatos();
	}

	public void cargarDatos() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtId.setText(movimiento.getTck_movimiento());
		if (movimiento.getTip_pedido().equals("M")) {
			cmbPedido.setSelectedIndex(0);
		} else {
			cmbPedido.setSelectedIndex(1);
		}
		dtxFechaSolicitud.setValue(movimiento.getFec_solicitud());
		tmxHoraSolicitud.setValue(fechas.obtenerFechaDeUnLong(movimiento.getHor_solicitud()));
		dtxFechaRespuesta.setValue(movimiento.getFec_respuesta());
		tmxHoraRespuesta.setValue(fechas.obtenerFechaDeUnLong(movimiento.getHor_respuesta()));
		dtxFechaEjecucion.setValue(movimiento.getFec_ejecucion());
		tmxHoraEjecucion.setValue(fechas.obtenerFechaDeUnLong(movimiento.getHor_ejecucion()));
		setearSolicitante(movimiento.getId_solicitante());
		if (movimiento.getEst_movimiento().equals("E")) {
			cmbEstado.setSelectedIndex(0);
		} else {
			cmbEstado.setSelectedIndex(1);
		}
		txtObservacion.setText(movimiento.getObs_movimiento());
		id_turno = Long.valueOf(movimiento.getTur_movimiento());
		cmbTurno.setText(movimiento.getNom_turno());
		listaMovimientoDetalle = consultasABaseDeDatos.cargarDetalleMovimientosDN(
				String.valueOf(movimiento.getId_movimiento()), "", "", String.valueOf(id_dc), 0, 3, "");
		setearActualizarInventarioInicial();
		binder.loadComponent(lbxMovimientos);
	}

	public void setearActualizarInventarioInicial() {
		for (int i = 0; i < listaMovimientoDetalle.size(); i++) {
			listaMovimientoDetalle.get(i).setActualiza_inventario("N");
		}
	}

	public void setearSolicitante(long id_solicitante)
			throws ClassNotFoundException, FileNotFoundException, IOException {
		listaSolicitante = consultasABaseDeDatos.cargarSolicitantes("", 8, String.valueOf(id_dc),
				String.valueOf(id_opcion), 0);
		binder.loadComponent(lbxSolicitantes);
		Iterator<modelo_solicitante> it = listaSolicitante.iterator();
		int indice = 0;
		boolean se_encuentra = false;
		while (it.hasNext()) {
			modelo_solicitante modelo = it.next();
			if (modelo.getId_solicitante() == id_solicitante) {
				bdxSolicitantes.setText(modelo.toStringSolicitante());
				lbxSolicitantes.setSelectedIndex(indice);
				se_encuentra = true;
				break;
			}
			indice++;
		}
		if (se_encuentra == false) {
			bdxSolicitantes.setText("");
		}
	}

	public void setearCamposInicio() {
		cmbPedido.setSelectedIndex(0);
		cmbEstado.setSelectedIndex(0);
	}

	public void inicializarListas() throws ClassNotFoundException, FileNotFoundException, IOException {
		listaParametros1 = consultasABaseDeDatos.cargarParametros1();
		listaSolicitante = consultasABaseDeDatos.cargarSolicitantes("", 8, String.valueOf(id_dc),
				String.valueOf(id_opcion), 0);
		listaEmpresa = consultasABaseDeDatos.cargarEmpresas("", 2, String.valueOf(id_dc), String.valueOf(id_opcion), 0);
		listaTurno = consultasABaseDeDatos.cargarTurnos("A");
		binder.loadComponent(lbxSolicitantes);
		binder.loadComponent(cmbEmpresa);
		binder.loadComponent(cmbTurno);
	}

	public void inicializarFechas() {
		dtxFechaSolicitud
				.setValue(fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 0, 0, 0));
		dtxFechaRespuesta
				.setValue(fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 0, 0, 0));
		dtxFechaEjecucion
				.setValue(fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 0, 0, 0));
		tmxHoraSolicitud
				.setValue(fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 0, 0, 0));
		tmxHoraRespuesta
				.setValue(fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 0, 0, 0));
		tmxHoraEjecucion
				.setValue(fechas.obtenerFechaArmada(new Date(), new Date().getMonth(), new Date().getDate(), 0, 0, 0));
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

	public List<modelo_solicitante> obtenerSolicitantes() {
		return listaSolicitante;
	}

	public List<modelo_empresa> obtenerEmpresas() {
		return listaEmpresa;
	}

	public List<modelo_articulo_dn> obtenerArticulos() {
		return listaArticulo;
	}

	public List<modelo_turno> obtenerTurnos() {
		return listaTurno;
	}

	public List<modelo_movimiento_detalle_dn> obtenerMovimientoDetalles() {
		return listaMovimientoDetalle;
	}

	public void obtenerId() throws ClassNotFoundException, FileNotFoundException, IOException {
		dao_movimiento_dn dao = new dao_movimiento_dn();
		try {
			id = dao.obtenerNuevoId();
			txtId.setText(String.valueOf(id));
		} catch (SQLException e) {
			Messagebox.show(error.getMensaje_error_2() + error.getMensaje_error_1() + e.getMessage(),
					informativos.getMensaje_informativo_1(), Messagebox.OK, Messagebox.EXCLAMATION);
		}
	}

	@Listen("onSelect=#cmbEmpresa")
	public void onSelect$cmbEmpresa()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbEmpresa.getSelectedItem() == null) {
			bdxArticulos.setText("");
			bdxArticulos.setDisabled(true);
			return;
		}
		bdxArticulos.setText("");
		bdxArticulos.setTooltiptext("");
		txtBuscarArticulo.setText("");
		bdxArticulos.setDisabled(false);
		listaArticulo = consultasABaseDeDatos.cargarArticulosDN("", id_dc,
				cmbEmpresa.getSelectedItem().getValue().toString(), 2, 0, "A", "");
		lbxArticulos.clearSelection();
		binder.loadComponent(lbxArticulos);
	}

	@Listen("onSelect=#cmbEstado")
	public void onSelect$cmbEstado()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbEstado.getSelectedItem() == null) {
			return;
		}
		if (cmbEstado.getSelectedItem().getValue().toString().equals("E")) {
			indicarSiSeActualizaInventario("S");
		} else {
			indicarSiSeActualizaInventario("N");
		}
		binder.loadComponent(lbxMovimientos);
		lbxMovimientos.clearSelection();
	}

	public void indicarSiSeActualizaInventario(String estado) {
		Iterator<modelo_movimiento_detalle_dn> it = listaMovimientoDetalle.iterator();
		while (it.hasNext()) {
			modelo_movimiento_detalle_dn movimiento_detalle = it.next();
			movimiento_detalle.setActualiza_inventario(estado);
		}
	}

	@Listen("onRightClick=#lbxMovimientos")
	public void onRightClick$lbxMovimientos()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxMovimientos.getSelectedItem() == null) {
			return;
		}
		int indice = lbxMovimientos.getSelectedIndex();
		if (cmbEstado.getSelectedItem() != null) {
			if (cmbEstado.getSelectedItem().getValue().toString().equals("NE")) {
				mSiActualizaInventario.setVisible(false);
				mNoActualizaInventario.setVisible(false);
				mSeparador1.setVisible(false);
				mSeparador2.setVisible(false);
			} else {
				if (listaMovimientoDetalle.get(indice).getActualiza_inventario().equals("N")) {
					mSiActualizaInventario.setVisible(true);
					mNoActualizaInventario.setVisible(false);
					mSeparador1.setVisible(true);
					mSeparador2.setVisible(false);
				} else {
					mSiActualizaInventario.setVisible(false);
					mNoActualizaInventario.setVisible(true);
					mSeparador1.setVisible(false);
					mSeparador2.setVisible(true);
				}
			}
		}
	}

	@Listen("onOK=#txtBuscarArticulo")
	public void onOK$txtBuscarArticulo()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		bdxArticulos.setText("");
		bdxArticulos.setTooltiptext("");
		listaArticulo = consultasABaseDeDatos.cargarArticulosDN(txtBuscarArticulo.getText().toString(), id_dc,
				cmbEmpresa.getSelectedItem().getValue().toString(), 2, 0, "A", "");
		lbxArticulos.clearSelection();
		binder.loadComponent(lbxArticulos);
	}

	@Listen("onClick=#bdxArticulos")
	public void onClick$bdxArticulos()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (cmbEmpresa.getSelectedItem() == null) {
			return;
		}
		listaArticulo = consultasABaseDeDatos.cargarArticulosDN(txtBuscarArticulo.getText().toString(), id_dc,
				cmbEmpresa.getSelectedItem().getValue().toString(), 2, 0, "A", "");
		binder.loadComponent(lbxArticulos);
	}

	@Listen("onOK=#txtBuscarProveedor")
	public void onOK$txtBuscarProveedor()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		bdxSolicitantes.setText("");
		bdxSolicitantes.setTooltiptext("");
		listaSolicitante = consultasABaseDeDatos.cargarSolicitantes(txtBuscarProveedor.getText().toString(), 8,
				String.valueOf(id_dc), String.valueOf(id_opcion), 0);
		lbxSolicitantes.clearSelection();
		binder.loadComponent(lbxSolicitantes);
	}

	@Listen("onSelect=#lbxArticulos")
	public void onSelect$lbxArticulos()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxArticulos.getSelectedItem() == null) {
			return;
		}
		int indice = lbxArticulos.getSelectedIndex();
		bdxArticulos.setText(
				listaArticulo.get(indice).getCod_articulo() + " - " + listaArticulo.get(indice).getDes_articulo());
		bdxArticulos.setTooltiptext(
				listaArticulo.get(indice).getCod_articulo() + " - " + listaArticulo.get(indice).getDes_articulo());
	}

	@Listen("onSelect=#lbxSolicitantes")
	public void onSelect$lbxSolicitantes()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxSolicitantes.getSelectedItem() == null) {
			return;
		}
		int indice = lbxSolicitantes.getSelectedIndex();
		bdxSolicitantes.setText(listaSolicitante.get(indice).getNom_solicitante() + " "
				+ listaSolicitante.get(indice).getApe_solicitante());
		bdxSolicitantes.setTooltiptext(listaSolicitante.get(indice).getNom_solicitante() + " "
				+ listaSolicitante.get(indice).getApe_solicitante());
	}

	public void validarFechasConHorasEnCabecera() {
		if (dtxFechaRespuesta.getValue() == null || dtxFechaSolicitud.getValue() == null
				|| tmxHoraSolicitud.getValue() == null || tmxHoraRespuesta.getValue() == null) {
			return;
		}
		Date fecha_1 = fechas.obtenerFechaArmada(dtxFechaSolicitud.getValue(), dtxFechaSolicitud.getValue().getMonth(),
				dtxFechaSolicitud.getValue().getDay(), tmxHoraSolicitud.getValue().getHours(),
				tmxHoraSolicitud.getValue().getMinutes(), 0);
		Date fecha_2 = fechas.obtenerFechaArmada(dtxFechaRespuesta.getValue(), dtxFechaRespuesta.getValue().getMonth(),
				dtxFechaRespuesta.getValue().getDay(), tmxHoraRespuesta.getValue().getHours(),
				tmxHoraRespuesta.getValue().getMinutes(), 0);
		float diferencia_minutos = obtenerDiferenciaEnMinutosYSegundos(fecha_1, fecha_2);
		if ((diferencia_minutos <= 15.0 && diferencia_minutos >= 0.0)) {
			lblRespuesta.setVisible(true);
			lblRespuesta.setValue(informativos.getMensaje_informativo_63());
		} else {
			lblRespuesta.setVisible(true);
			lblRespuesta.setValue(informativos.getMensaje_informativo_64());
		}
	}

	private float obtenerDiferenciaEnMinutosYSegundos(Date fechaInicio, Date fechaTermino) {
		float segundos = (float) ((fechaTermino.getTime() / 1000) - (fechaInicio.getTime() / 1000));
		if (segundos < 60)
			return segundos;
		return segundos / 60;
	}

	@Listen("onBlur=#tmxHoraRespuesta")
	public void onBlur$tmxHoraRespuesta() {
		validarFechasConHorasEnCabecera();
	}

	@Listen("onBlur=#tmxHoraSolicitud")
	public void onBlur$tmxHoraSolicitud() {
		validarFechasConHorasEnCabecera();
	}

	@Listen("onBlur=#dtxFechaRespuesta")
	public void onBlur$dtxFechaRespuesta() {
		validarFechasConHorasEnCabecera();
	}

	@Listen("onBlur=#dtxFechaSolicitud")
	public void onBlur$dtxFechaSolicitud() {
		validarFechasConHorasEnCabecera();
	}

	@Listen("onClick=#lbxArticulos")
	public void onDoubleClick$lbxArticulos()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxArticulos.getSelectedItem() == null) {
			return;
		}
		int indice = lbxArticulos.getSelectedIndex();
		if (validarSiArticuloEstaEnLista(indice) == true) {
			Messagebox.show(informativos.getMensaje_informativo_65(), informativos.getMensaje_informativo_47(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		anadirArticuloAListaDetalle(indice);
		lbxArticulos.clearSelection();
	}

	public void actualizarDatosAnterioresDeArticulosAntesDeGuardar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		List<modelo_articulo_dn> listaArticulo = new ArrayList<modelo_articulo_dn>();
		listaArticulo = consultasABaseDeDatos.cargarArticulosDN(txtBuscarArticulo.getText().toString(), id_dc, "0", 2,
				0, "A", "");
		for (int i = 0; i < listaArticulo.size(); i++) {
			for (int j = 0; j < listaMovimientoDetalle.size(); j++) {
				if (listaArticulo.get(i).getId_articulo() == listaMovimientoDetalle.get(j).getId_articulo()) {
					if (listaMovimientoDetalle.get(j).getActualiza_inventario().equals("S")) {
						listaMovimientoDetalle.get(j).setCod_articulo_anterior(listaArticulo.get(i).getCod_articulo());
						listaMovimientoDetalle.get(j).setDes_articulo_anterior(listaArticulo.get(i).getDes_articulo());
						listaMovimientoDetalle.get(j)
								.setId_cat_articulo_anterior(listaArticulo.get(i).getId_categoria());
						listaMovimientoDetalle.get(j)
								.setNom_cat_articulo_anterior(listaArticulo.get(i).getNom_categoria());
						listaMovimientoDetalle.get(j)
								.setId_cap_articulo_anterior(listaArticulo.get(i).getId_capacidad());
						listaMovimientoDetalle.get(j)
								.setNom_id_cap_articulo_anterior(listaArticulo.get(i).getNom_capacidad());
						listaMovimientoDetalle.get(j).setId_ubicacion_anterior(listaArticulo.get(i).getId_ubicacion());
						listaMovimientoDetalle.get(j)
								.setNom_ubicacion_anterior(listaArticulo.get(i).getNom_ubicacion());
						listaMovimientoDetalle.get(j)
								.setSi_ing_fec_inicio_fin_anterior(listaArticulo.get(i).getSi_ing_fec_inicio_fin());
						listaMovimientoDetalle.get(j).setEs_fecha_anterior(listaArticulo.get(i).getEs_fecha());
						listaMovimientoDetalle.get(j)
								.setId_fec_respaldo_anterior(listaArticulo.get(i).getId_fec_respaldo());
						listaMovimientoDetalle.get(j)
								.setNom_id_fec_respaldo_anterior(listaArticulo.get(i).getNom_fec_respaldo());
						listaMovimientoDetalle.get(j).setFec_inicio_anterior(listaArticulo.get(i).getFec_inicio());
						listaMovimientoDetalle.get(j).setFec_fin_anterior(listaArticulo.get(i).getFec_fin());
						listaMovimientoDetalle.get(j)
								.setTip_respaldo_anterior(String.valueOf(listaArticulo.get(i).getId_tip_respaldo()));
						listaMovimientoDetalle.get(j)
								.setNom_tip_respaldo_anterior(listaArticulo.get(i).getNom_tip_respaldo());
						listaMovimientoDetalle.get(j)
								.setId_contenedor_anterior(listaArticulo.get(i).getId_contenedor());
						listaMovimientoDetalle.get(j)
								.setHora_llegada_custodia_anterior(listaArticulo.get(i).getHora_llegada_cutodia());
						listaMovimientoDetalle.get(j)
								.setHora_salida_custodia_anterior(listaArticulo.get(i).getHora_salida_custodia());
						listaMovimientoDetalle.get(j)
								.setRemesa_ingreso_custodia_anterior(listaArticulo.get(i).getRemesa_ingreso_custodia());
						listaMovimientoDetalle.get(j)
								.setRemesa_salida_custodia_anterior(listaArticulo.get(i).getRemesa_salida_custodia());
					}
				}
			}
		}
	}

	public void anadirArticuloAListaDetalle(int indice) {
		modelo_movimiento_detalle_dn movimiento_detalle = new modelo_movimiento_detalle_dn();
		modelo_articulo_dn articulo = listaArticulo.get(indice);
		movimiento_detalle.setId_articulo(articulo.getId_articulo());
		/* Datos anteriores del articulo */
		movimiento_detalle.setCod_articulo_anterior(articulo.getCod_articulo());
		movimiento_detalle.setDes_articulo_anterior(articulo.getDes_articulo());
		movimiento_detalle.setId_cat_articulo_anterior(articulo.getId_categoria());
		movimiento_detalle.setNom_cat_articulo_anterior(articulo.getNom_categoria());
		movimiento_detalle.setId_cap_articulo_anterior(articulo.getId_capacidad());
		movimiento_detalle.setNom_id_cap_articulo_anterior(articulo.getNom_capacidad());
		movimiento_detalle.setId_ubicacion_anterior(articulo.getId_ubicacion());
		movimiento_detalle.setNom_ubicacion_anterior(articulo.getNom_ubicacion());
		movimiento_detalle.setSi_ing_fec_inicio_fin_anterior(articulo.getSi_ing_fec_inicio_fin());
		movimiento_detalle.setEs_fecha_anterior(articulo.getEs_fecha());
		movimiento_detalle.setId_fec_respaldo_anterior(articulo.getId_fec_respaldo());
		movimiento_detalle.setNom_id_fec_respaldo_anterior(articulo.getNom_fec_respaldo());
		movimiento_detalle.setFec_inicio_anterior(articulo.getFec_inicio());
		movimiento_detalle.setFec_fin_anterior(articulo.getFec_fin());
		movimiento_detalle.setTip_respaldo_anterior(String.valueOf(articulo.getId_tip_respaldo()));
		movimiento_detalle.setNom_tip_respaldo_anterior(articulo.getNom_tip_respaldo());
		movimiento_detalle.setId_contenedor_anterior(articulo.getId_contenedor());
		movimiento_detalle.setHora_llegada_custodia_anterior(articulo.getHora_llegada_cutodia());
		movimiento_detalle.setHora_salida_custodia_anterior(articulo.getHora_salida_custodia());
		movimiento_detalle.setRemesa_ingreso_custodia_anterior(articulo.getRemesa_ingreso_custodia());
		movimiento_detalle.setRemesa_salida_custodia_anterior(articulo.getRemesa_salida_custodia());
		/* Datos actuales del articulo */
		movimiento_detalle.setCod_articulo_actual(articulo.getCod_articulo());
		movimiento_detalle.setDes_articulo_actual(articulo.getDes_articulo());
		movimiento_detalle.setId_cat_articulo_actual(articulo.getId_categoria());
		movimiento_detalle.setNom_cat_articulo_actual(articulo.getNom_categoria());
		movimiento_detalle.setId_cap_articulo_actual(articulo.getId_capacidad());
		movimiento_detalle.setNom_id_cap_articulo_actual(articulo.getNom_capacidad());
		movimiento_detalle.setId_ubicacion_actual(articulo.getId_ubicacion());
		movimiento_detalle.setNom_ubicacion_actual(articulo.getNom_ubicacion());
		movimiento_detalle.setSi_ing_fec_inicio_fin_actual(articulo.getSi_ing_fec_inicio_fin());
		movimiento_detalle.setEs_fecha_actual(articulo.getEs_fecha());
		movimiento_detalle.setId_fec_respaldo_actual(articulo.getId_fec_respaldo());
		movimiento_detalle.setNom_id_fec_respaldo_actual(articulo.getNom_fec_respaldo());
		movimiento_detalle.setFec_inicio_actual(articulo.getFec_inicio());
		movimiento_detalle.setFec_fin_actual(articulo.getFec_fin());
		movimiento_detalle.setTip_respaldo_actual(String.valueOf(articulo.getId_tip_respaldo()));
		movimiento_detalle.setNom_tip_respaldo_actual(articulo.getNom_tip_respaldo());
		movimiento_detalle.setId_contenedor_actual(articulo.getId_contenedor());
		movimiento_detalle.setHora_llegada_custodia_actual(articulo.getHora_llegada_cutodia());
		movimiento_detalle.setHora_salida_custodia_actual(articulo.getHora_salida_custodia());
		movimiento_detalle.setRemesa_ingreso_custodia_actual(articulo.getRemesa_ingreso_custodia());
		movimiento_detalle.setRemesa_salida_custodia_actual(articulo.getRemesa_salida_custodia());
		/* Se setean otros parametros */
		if (cmbEstado.getSelectedItem().getValue().toString().equals("E")) {
			movimiento_detalle.setActualiza_inventario("S");
		} else {
			movimiento_detalle.setActualiza_inventario("N");
		}
		/*
		 * Se guarda en las listas donde se va a mostrar para el usuario y donde se
		 * guardara en la base de datos
		 */
		listaMovimientoDetalle.add(movimiento_detalle); // Esta lista es para mostrar los datos actuales al usuario y en
														// donde se almacenan los datos que se guardaran en la BD
		binder.loadComponent(lbxMovimientos);
	}

	public boolean validarSiArticuloEstaEnLista(int indice) {
		boolean existe_articulo = false;
		long id_articulo = listaArticulo.get(indice).getId_articulo();
		Iterator<modelo_movimiento_detalle_dn> it = listaMovimientoDetalle.iterator();
		while (it.hasNext()) {
			modelo_movimiento_detalle_dn movimiento_detalle = it.next();
			if (movimiento_detalle.getId_articulo() == id_articulo) {
				existe_articulo = true;
				break;
			}
		}
		return existe_articulo;
	}

	@Listen("onClick=#mEliminar")
	public void onClick$mEliminar() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxMovimientos.getSelectedItem() == null) {
			return;
		}
		int indice = 0;
		Iterator<Listitem> it1 = lbxMovimientos.getSelectedItems().iterator();
		List<modelo_movimiento_detalle_dn> listaMovimiento = new ArrayList<modelo_movimiento_detalle_dn>();
		while (it1.hasNext()) {
			Listitem item = it1.next();
			indice = item.getIndex();
			listaMovimiento.add(listaMovimientoDetalle.get(indice));
		}
		Iterator<modelo_movimiento_detalle_dn> it2 = listaMovimiento.iterator();
		while (it2.hasNext()) {
			modelo_movimiento_detalle_dn movimiento_detalle = it2.next();
			listaMovimientoDetalle.remove(movimiento_detalle);
		}
		binder.loadComponent(lbxMovimientos);
	}

	@Listen("onClick=#mSiActualizaInventario")
	public void onClick$mSiActualizaInventario() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxMovimientos.getSelectedItem() == null) {
			Messagebox.show(informativos.getMensaje_informativo_3(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		Iterator<Listitem> it = lbxMovimientos.getSelectedItems().iterator();
		while (it.hasNext()) {
			Listitem item = it.next();
			if (cmbEstado.getSelectedItem().getValue().toString().equals("E")) {
				listaMovimientoDetalle.get(item.getIndex()).setActualiza_inventario("S");
			} else {
				listaMovimientoDetalle.get(item.getIndex()).setActualiza_inventario("N");
			}
		}
		binder.loadComponent(lbxMovimientos);
	}

	@Listen("onClick=#mNoActualizaInventario")
	public void onClick$mNoActualizaInventario() throws ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxMovimientos.getSelectedItem() == null) {
			Messagebox.show(informativos.getMensaje_informativo_3(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		Iterator<Listitem> it = lbxMovimientos.getSelectedItems().iterator();
		while (it.hasNext()) {
			Listitem item = it.next();
			listaMovimientoDetalle.get(item.getIndex()).setActualiza_inventario("N");
		}
		binder.loadComponent(lbxMovimientos);
	}

	@Listen("onClick=#mModificar")
	public void oonClick$mModificar()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException {
		if (lbxMovimientos.getSelectedItem() == null) {
			return;
		}
		if (ingresa_a_modificar == false) {
			ingresa_a_modificar = true;
			if (lbxMovimientos.getSelectedItems().size() > 1) {
				ingresa_a_modificar = false;
				Messagebox.show(informativos.getMensaje_informativo_7(), informativos.getMensaje_informativo_24(),
						Messagebox.OK, Messagebox.EXCLAMATION);
				return;
			}
			int indice = lbxMovimientos.getSelectedIndex();
			Sessions.getCurrent().setAttribute("movimiento_detalle_1", listaMovimientoDetalle.get(indice));
			window = (Window) Executions.createComponents("/cintas/movimiento/modificar.zul", null, null);
			if (window instanceof Window) {
				window.addEventListener("onClose", new EventListener<org.zkoss.zk.ui.event.Event>() {
					@Override
					public void onEvent(org.zkoss.zk.ui.event.Event arg0) throws Exception {
						ingresa_a_modificar = false;
						modelo_movimiento_detalle_dn movimiento_detalle1 = new modelo_movimiento_detalle_dn();
						modelo_movimiento_detalle_dn movimiento_detalle2 = (modelo_movimiento_detalle_dn) Sessions
								.getCurrent().getAttribute("movimiento_detalle_2");
						Sessions.getCurrent().removeAttribute("movimiento_detalle_2");
						if (movimiento_detalle2 != null) {
							movimiento_detalle1 = movimiento_detalle2.clone();
							guardarEnListaParaRegistroDeBaseDeDatos(movimiento_detalle1);
							binder.loadComponent(lbxMovimientos);
						}
					}
				});
			}
			window.setParent(winList);
		}
	}

	public void guardarEnListaParaRegistroDeBaseDeDatos(modelo_movimiento_detalle_dn movimiento_detalle) {
		int indice = 0;
		Iterator<modelo_movimiento_detalle_dn> it = listaMovimientoDetalle.iterator();
		while (it.hasNext()) {
			if (it.next().getId_articulo() == movimiento_detalle.getId_articulo()) {
				break;
			}
			indice++;
		}
		/* Datos actuales del articulo */
		listaMovimientoDetalle.get(indice).setCod_articulo_actual(movimiento_detalle.getCod_articulo_actual());
		listaMovimientoDetalle.get(indice).setDes_articulo_actual(movimiento_detalle.getDes_articulo_actual());
		listaMovimientoDetalle.get(indice).setId_cat_articulo_actual(movimiento_detalle.getId_cat_articulo_actual());
		listaMovimientoDetalle.get(indice).setNom_cat_articulo_actual(movimiento_detalle.getNom_cat_articulo_actual());
		listaMovimientoDetalle.get(indice).setId_cap_articulo_actual(movimiento_detalle.getId_cap_articulo_actual());
		listaMovimientoDetalle.get(indice)
				.setNom_id_cap_articulo_actual(movimiento_detalle.getNom_id_cap_articulo_actual());
		listaMovimientoDetalle.get(indice).setId_ubicacion_actual(movimiento_detalle.getId_ubicacion_actual());
		listaMovimientoDetalle.get(indice).setNom_ubicacion_actual(movimiento_detalle.getNom_ubicacion_actual());
		listaMovimientoDetalle.get(indice)
				.setSi_ing_fec_inicio_fin_actual(movimiento_detalle.getSi_ing_fec_inicio_fin_actual());
		listaMovimientoDetalle.get(indice).setEs_fecha_actual(movimiento_detalle.getEs_fecha_actual());
		listaMovimientoDetalle.get(indice).setId_fec_respaldo_actual(movimiento_detalle.getId_fec_respaldo_actual());
		listaMovimientoDetalle.get(indice)
				.setNom_id_fec_respaldo_actual(movimiento_detalle.getNom_id_fec_respaldo_actual());
		listaMovimientoDetalle.get(indice).setFec_inicio_actual(movimiento_detalle.getFec_inicio_actual());
		listaMovimientoDetalle.get(indice).setFec_fin_actual(movimiento_detalle.getFec_fin_actual());
		listaMovimientoDetalle.get(indice)
				.setTip_respaldo_actual(String.valueOf(movimiento_detalle.getTip_respaldo_actual()));
		listaMovimientoDetalle.get(indice).setNom_tip_respaldo_actual(movimiento_detalle.getNom_tip_respaldo_actual());
		listaMovimientoDetalle.get(indice).setId_contenedor_actual(movimiento_detalle.getId_contenedor_actual());
		listaMovimientoDetalle.get(indice)
				.setHora_llegada_custodia_actual(movimiento_detalle.getHora_llegada_custodia_actual());
		listaMovimientoDetalle.get(indice)
				.setHora_salida_custodia_actual(movimiento_detalle.getHora_salida_custodia_actual());
		listaMovimientoDetalle.get(indice)
				.setRemesa_ingreso_custodia_actual(movimiento_detalle.getRemesa_ingreso_custodia_actual());
		listaMovimientoDetalle.get(indice)
				.setRemesa_salida_custodia_actual(movimiento_detalle.getRemesa_salida_custodia_actual());
		if (cmbEstado.getSelectedItem().getValue().toString().equals("E")) {
			listaMovimientoDetalle.get(indice).setActualiza_inventario(movimiento_detalle.getActualiza_inventario());
		} else {
			listaMovimientoDetalle.get(indice).setActualiza_inventario("N");
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Listen("onClick=#btnGrabar")
	public void onClick$btnGrabar() throws ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		actualizarDatosAnterioresDeArticulosAntesDeGuardar();
		if (validarSiSeIniciaTurno() == false) {
			Messagebox.show(informativos.getMensaje_informativo_33(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (validarSiExistenTareasVencidas() == true) {
			Messagebox.show(informativos.getMensaje_informativo_38(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (validarSiPedidoHaSidoRevisado() == true) {
			Messagebox.show(informativos.getMensaje_informativo_75(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (txtId.getText().trim().length() <= 0) {
			txtId.setErrorMessage(validaciones.getMensaje_validacion_31());
			txtId.setFocus(true);
			return;
		}
		if (validarSiExistePrimeroApertura(txtId.getText().trim(), 1) == false) {
			Messagebox.show(informativos.getMensaje_informativo_96().replace("?1", txtId.getText().trim()),
					informativos.getMensaje_informativo_24(), Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (cmbPedido.getSelectedItem() == null) {
			cmbPedido.setErrorMessage(validaciones.getMensaje_validacion_24());
			cmbPedido.setFocus(true);
			return;
		}
		if (dtxFechaSolicitud.getValue() == null) {
			dtxFechaSolicitud.setErrorMessage(validaciones.getMensaje_validacion_4());
			dtxFechaSolicitud.setFocus(true);
			return;
		}
		if (tmxHoraSolicitud.getValue() == null) {
			tmxHoraSolicitud.setErrorMessage(validaciones.getMensaje_validacion_25());
			tmxHoraSolicitud.setFocus(true);
			return;
		}
		if (dtxFechaRespuesta.getValue() == null) {
			dtxFechaRespuesta.setErrorMessage(validaciones.getMensaje_validacion_4());
			dtxFechaRespuesta.setFocus(true);
			return;
		}
		if (tmxHoraRespuesta.getValue() == null) {
			tmxHoraRespuesta.setErrorMessage(validaciones.getMensaje_validacion_25());
			tmxHoraRespuesta.setFocus(true);
			return;
		}
		if (dtxFechaEjecucion.getValue() == null) {
			dtxFechaEjecucion.setErrorMessage(validaciones.getMensaje_validacion_4());
			dtxFechaEjecucion.setFocus(true);
			return;
		}
		if (tmxHoraEjecucion.getValue() == null) {
			tmxHoraEjecucion.setErrorMessage(validaciones.getMensaje_validacion_25());
			tmxHoraEjecucion.setFocus(true);
			return;
		}
		Date d1 = fechas.obtenerFechaArmada(dtxFechaSolicitud.getValue(), dtxFechaSolicitud.getValue().getMonth(),
				dtxFechaSolicitud.getValue().getDate(), tmxHoraSolicitud.getValue().getHours(),
				tmxHoraSolicitud.getValue().getMinutes(), tmxHoraSolicitud.getValue().getSeconds());
		Date d2 = fechas.obtenerFechaArmada(dtxFechaRespuesta.getValue(), dtxFechaRespuesta.getValue().getMonth(),
				dtxFechaRespuesta.getValue().getDate(), tmxHoraRespuesta.getValue().getHours(),
				tmxHoraRespuesta.getValue().getMinutes(), tmxHoraRespuesta.getValue().getSeconds());
		Date d3 = fechas.obtenerFechaArmada(dtxFechaEjecucion.getValue(), dtxFechaEjecucion.getValue().getMonth(),
				dtxFechaEjecucion.getValue().getDate(), tmxHoraEjecucion.getValue().getHours(),
				tmxHoraEjecucion.getValue().getMinutes(), tmxHoraEjecucion.getValue().getSeconds());
		if (d1.after(d2)) {
			dtxFechaRespuesta.setErrorMessage(validaciones.getMensaje_validacion_26());
			dtxFechaRespuesta.setFocus(true);
			return;
		}
		if (d1.after(d3)) {
			dtxFechaEjecucion.setErrorMessage(validaciones.getMensaje_validacion_27());
			dtxFechaEjecucion.setFocus(true);
			return;
		}
		if (lbxSolicitantes.getSelectedItem() == null) {
			bdxSolicitantes.setErrorMessage(validaciones.getMensaje_validacion_29());
			bdxSolicitantes.setFocus(true);
			return;
		}
		if (lbxMovimientos.getItemCount() == 0) {
			if (cmbEmpresa.getSelectedItem() == null) {
				cmbEmpresa.setErrorMessage(validaciones.getMensaje_validacion_12());
				cmbEmpresa.setFocus(true);
				return;
			}
		}
		if (lbxMovimientos.getItemCount() == 0) {
			bdxArticulos.setErrorMessage(validaciones.getMensaje_validacion_30());
			bdxArticulos.setFocus(true);
			return;
		}
		if (cmbEstado.getSelectedItem() == null) {
			cmbEstado.setErrorMessage(validaciones.getMensaje_validacion_18());
			cmbEstado.setFocus(true);
			return;
		}
		/*
		 * if (validarSiTienenMismoCodigoEnLista() == true) {
		 * Messagebox.show(informativos.getMensaje_informativo_68(),
		 * informativos.getMensaje_informativo_24(), Messagebox.OK,
		 * Messagebox.EXCLAMATION); return; }
		 */
		if (validarSiTienenMismoCodigoYFechaInicioEnLista() == true) {
			Messagebox.show(informativos.getMensaje_informativo_66(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (validarSiTienenMismoCodigoYFechaInicioYFechaFinEnLista() == true) {
			Messagebox.show(informativos.getMensaje_informativo_67(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		/*
		 * if (validarSiTienenMismoCodigoEnBD() == true) {
		 * Messagebox.show(informativos.getMensaje_informativo_68(),
		 * informativos.getMensaje_informativo_24(), Messagebox.OK,
		 * Messagebox.EXCLAMATION); return; }
		 */
		if (validarSiTienenMismoCodigoYFechaInicioEnBD() == true) {
			Messagebox.show(informativos.getMensaje_informativo_69(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (validarSiTienenMismoCodigoYFechaInicioYFechaFinEnBD() == true) {
			Messagebox.show(informativos.getMensaje_informativo_70(), informativos.getMensaje_informativo_24(),
					Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (validarSiEstaDentroDeStockPermitido() == true) {
			Messagebox.show(informativos.getMensaje_informativo_71(), informativos.getMensaje_informativo_15(),
					Messagebox.OK, Messagebox.INFORMATION);
			return;
		}
		Messagebox.show(informativos.getMensaje_informativo_16(), informativos.getMensaje_informativo_24(),
				Messagebox.OK | Messagebox.CANCEL, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					@Override
					public void onEvent(Event event) throws Exception {
						if (event.getName().equals("onOK")) {
							validarTurno();
							long secuencia = 0;
							dao_movimiento_dn dao = new dao_movimiento_dn();
							/* CABECERA */
							modelo_movimiento_dn movimiento = new modelo_movimiento_dn();
							movimiento.setId_movimiento(revision2.this.movimiento.getId_movimiento());
							movimiento.setTck_movimiento(txtId.getText().trim().toUpperCase());
							movimiento.setTip_pedido(cmbPedido.getSelectedItem().getValue().toString());
							movimiento.setFec_solicitud(fechas.obtenerTimestampDeDate(dtxFechaSolicitud.getValue()));
							movimiento.setFec_respuesta(fechas.obtenerTimestampDeDate(dtxFechaRespuesta.getValue()));
							movimiento.setFec_ejecucion(fechas.obtenerTimestampDeDate(dtxFechaEjecucion.getValue()));
							movimiento.setHor_solicitud(fechas.obtenerLongDeUnaFecha(tmxHoraSolicitud.getValue()));
							movimiento.setHor_respuesta(fechas.obtenerLongDeUnaFecha(tmxHoraRespuesta.getValue()));
							movimiento.setHor_ejecucion(fechas.obtenerLongDeUnaFecha(tmxHoraEjecucion.getValue()));
							movimiento.setId_solicitante(
									listaSolicitante.get(lbxSolicitantes.getSelectedIndex()).getId_solicitante());
							movimiento.setEst_movimiento(cmbEstado.getSelectedItem().getValue().toString());
							movimiento.setEst_validacion("RV3");
							movimiento.setObs_movimiento(txtObservacion.getText().toUpperCase());
							movimiento.setId_localidad(id_dc);
							movimiento.setTur_movimiento(String.valueOf(id_turno));
							movimiento.setId_usuario(revision2.this.movimiento.getId_usuario());
							movimiento.setFec_movimiento(revision2.this.movimiento.getFec_movimiento());
							movimiento.setUsu_revision_1(revision2.this.movimiento.getUsu_revision_1());
							movimiento.setUsu_revision_2(revision2.this.movimiento.getUsu_revision_2());
							movimiento.setUsu_revision_3(user);
							movimiento.setUsu_ingresa(revision2.this.movimiento.getUsu_ingresa());
							movimiento.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
							movimiento.setUsu_modifica(user);
							movimiento.setFec_modifica(fechas.obtenerTimestampDeDate(new Date()));
							/* DETALLE */
							for (int i = 0; i < listaMovimientoDetalle.size(); i++) {
								listaMovimientoDetalle.get(i).setEst_detalle_movimiento("AE");
								listaMovimientoDetalle.get(i).setRevision_1("S");
								listaMovimientoDetalle.get(i).setRevision_2("S");
								listaMovimientoDetalle.get(i).setRevision_3("N");
								listaMovimientoDetalle.get(i).setUsu_ingresa(user);
								listaMovimientoDetalle.get(i).setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
							}
							/* REGISTRO EN LOG DE EVENTOS */
							modelo_bitacora bitacora = new modelo_bitacora();
							// Se debe crear un parametro para la configuracion de varios datos por defecto.
							bitacora.setId_cliente(9);
							bitacora.setTicket_externo(txtId.getText().trim().toUpperCase());
							bitacora.setId_tipo_servicio(7);
							bitacora.setId_tipo_tarea(14);
							bitacora.setId_estado_bitacora(2);
							bitacora.setFec_inicio(fechas.obtenerTimestampDeDate(fechas.obtenerFechaArmada(
									fecha_ingresa_formulario, fecha_ingresa_formulario.getMonth(),
									fecha_ingresa_formulario.getDate(), fecha_ingresa_formulario.getHours(),
									fecha_ingresa_formulario.getMinutes(), 0)));
							bitacora.setFec_fin(fechas
									.obtenerTimestampDeDate(fechas.obtenerFechaArmada(new Date(), new Date().getMonth(),
											new Date().getDate(), new Date().getHours(), new Date().getMinutes(), 0)));
							bitacora.setCumplimiento("C");
							bitacora.setDescripcion(
									"SE REALIZA LA VALIDACION CRUZADA (REVISION EN HORARIO DE [00:00 - 07:59]) DEL "
											+ cmbPedido.getSelectedItem().getLabel().toString() + " DE "
											+ listaMovimientoDetalle.size() + " CINTA(S)");
							bitacora.setId_turno(id_turno);
							bitacora.setId_solicitante(
									listaSolicitante.get(lbxSolicitantes.getSelectedIndex()).getId_solicitante());
							bitacora.setId_localidad(id_dc);
							bitacora.setEst_bitacora("AE");
							bitacora.setUsu_ingresa(user);
							bitacora.setFec_ingresa(fechas.obtenerTimestampDeDate(new Date()));
							if (listaParametros1.size() > 0) {
								secuencia = listaParametros1.get(0).getSecuencia_bitacora();
							}
							List<modelo_bitacora> listaRegistrosBitacora = new ArrayList<modelo_bitacora>();
							listaRegistrosBitacora = consultasABaseDeDatos.cargarBitacoras(
									revision2.this.movimiento.getTck_movimiento(), 5, 0, "", "", id_dc, "", "", 0, "",
									0);
							try {
								dao.revisarMovimiento(movimiento, listaMovimientoDetalle, bitacora,
										listaRegistrosBitacora, secuencia);
								if (txtId.getText().trim().equals(revision2.this.movimiento.getTck_movimiento())
										&& listaSolicitante.get(lbxSolicitantes.getSelectedIndex())
												.getId_solicitante() == revision2.this.movimiento.getId_solicitante()) {
									Messagebox.show(informativos.getMensaje_informativo_20(),
											informativos.getMensaje_informativo_24(), Messagebox.OK,
											Messagebox.EXCLAMATION);
								} else {
									Messagebox.show(informativos.getMensaje_informativo_74(),
											informativos.getMensaje_informativo_24(), Messagebox.OK,
											Messagebox.EXCLAMATION);
								}
							} catch (Exception e) {
								Messagebox.show(error.getMensaje_error_4() + ", " + e.getLocalizedMessage(),
										informativos.getMensaje_informativo_24(), Messagebox.OK,
										Messagebox.EXCLAMATION);
							}
						}
					}
				});

	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() throws ClassNotFoundException, FileNotFoundException, IOException {
		Events.postEvent(new Event("onClose", zRevision));
	}

	@Listen("onClick=#btnLimpiar")
	public void onClick$btnLimpiar() throws ClassNotFoundException, FileNotFoundException, IOException {
		limpiarCamposTotal();
	}

	public boolean validarSiPedidoHaSidoRevisado() throws ClassNotFoundException, FileNotFoundException, IOException {
		boolean esta_revisado = false;
		List<modelo_movimiento_dn> listaMovimiento = new ArrayList<modelo_movimiento_dn>();
		listaMovimiento = consultasABaseDeDatos.cargarMovimientosDN(
				String.valueOf(revision2.this.movimiento.getId_movimiento()), "", "", String.valueOf(id_dc), 0, 4, "");
		if (listaMovimiento.size() > 0) {
			if (!listaMovimiento.get(0).getEst_validacion().equals("RV2")) {
				esta_revisado = true;
			}
		}
		return esta_revisado;
	}

	/*
	 * Metodos para consultar si existe algun registro con mismo codigo, fecha de
	 * inicio y fecha de fin en la misma lista o Base de datos
	 * 
	 */

	public boolean validarSiTienenMismoCodigoEnLista() {
		boolean tienen_mismos_datos = false;
		for (int i = 0; i < listaMovimientoDetalle.size(); i++) {
			if (listaMovimientoDetalle.get(i).getActualiza_inventario().equals("S")) {
				if (listaMovimientoDetalle.get(i).getSi_ing_fec_inicio_fin_actual().equals("N")) {
					if (listaMovimientoDetalle.get(i).getEs_fecha_actual().equals("N")) {
						if (listaMovimientoDetalle.get(i).getFec_fin_actual() == null) {
							for (int j = listaMovimientoDetalle.size() - 1; j >= 0; j--) {
								if (listaMovimientoDetalle.get(i).getId_articulo() != listaMovimientoDetalle.get(j)
										.getId_articulo()) {
									if (listaMovimientoDetalle.get(j).getActualiza_inventario().equals("S")) {
										if (listaMovimientoDetalle.get(j).getSi_ing_fec_inicio_fin_actual()
												.equals("N")) {
											if (listaMovimientoDetalle.get(j).getEs_fecha_actual().equals("N")) {
												if (listaMovimientoDetalle.get(j).getFec_fin_actual() == null) {
													if (listaMovimientoDetalle.get(i).getCod_articulo_actual().equals(
															listaMovimientoDetalle.get(j).getCod_articulo_actual())) {
														lbxMovimientos.clearSelection();
														lbxMovimientos
																.addItemToSelection(lbxMovimientos.getItemAtIndex(i));
														lbxMovimientos
																.addItemToSelection(lbxMovimientos.getItemAtIndex(j));
														tienen_mismos_datos = true;
														i = listaMovimientoDetalle.size();
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
		}
		return tienen_mismos_datos;
	}

	public boolean validarSiTienenMismoCodigoEnBD() throws ClassNotFoundException, FileNotFoundException, IOException {
		boolean tienen_mismos_datos = false;
		List<modelo_articulo_dn> listaArticulos = new ArrayList<modelo_articulo_dn>();
		listaArticulos = consultasABaseDeDatos.cargarArticulosDN("", id_dc, "0", 2, 0, "A", "");
		for (int i = 0; i < listaMovimientoDetalle.size(); i++) {
			if (listaMovimientoDetalle.get(i).getActualiza_inventario().equals("S")) {
				if (listaMovimientoDetalle.get(i).getSi_ing_fec_inicio_fin_actual().equals("N")) {
					if (listaMovimientoDetalle.get(i).getEs_fecha_actual().equals("N")) {
						if (listaMovimientoDetalle.get(i).getFec_fin_actual() == null) {
							for (int j = listaArticulos.size() - 1; j >= 0; j--) {
								if (listaMovimientoDetalle.get(i).getId_articulo() != listaArticulos.get(j)
										.getId_articulo()) {
									if (listaArticulos.get(j).getSi_ing_fec_inicio_fin().equals("N")) {
										if (listaArticulos.get(j).getEs_fecha().equals("N")) {
											if (listaArticulos.get(j).getFec_fin() == null) {
												if (listaMovimientoDetalle.get(i).getCod_articulo_actual()
														.equals(listaArticulos.get(j).getCod_articulo())) {
													lbxMovimientos.clearSelection();
													lbxMovimientos.addItemToSelection(lbxMovimientos.getItemAtIndex(i));
													tienen_mismos_datos = true;
													i = listaMovimientoDetalle.size();
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

	public boolean validarSiTienenMismoCodigoYFechaInicioEnLista() {
		boolean tienen_mismos_datos = false;
		for (int i = 0; i < listaMovimientoDetalle.size(); i++) {
			if (listaMovimientoDetalle.get(i).getActualiza_inventario().equals("S")) {
				if (listaMovimientoDetalle.get(i).getSi_ing_fec_inicio_fin_actual().equals("S")) {
					if (listaMovimientoDetalle.get(i).getEs_fecha_actual().equals("S")) {
						if (listaMovimientoDetalle.get(i).getFec_fin_actual() == null) {
							for (int j = listaMovimientoDetalle.size() - 1; j >= 0; j--) {
								if (listaMovimientoDetalle.get(i).getId_articulo() != listaMovimientoDetalle.get(j)
										.getId_articulo()) {
									if (listaMovimientoDetalle.get(j).getActualiza_inventario().equals("S")) {
										if (listaMovimientoDetalle.get(j).getSi_ing_fec_inicio_fin_actual()
												.equals("S")) {
											if (listaMovimientoDetalle.get(j).getEs_fecha_actual().equals("S")) {
												if (listaMovimientoDetalle.get(j).getFec_fin_actual() == null) {
													if (listaMovimientoDetalle.get(i).getCod_articulo_actual().equals(
															listaMovimientoDetalle.get(j).getCod_articulo_actual())
															&& listaMovimientoDetalle.get(i).getFec_inicio_actual()
																	.equals(listaMovimientoDetalle.get(j)
																			.getFec_inicio_actual())) {
														lbxMovimientos.clearSelection();
														lbxMovimientos
																.addItemToSelection(lbxMovimientos.getItemAtIndex(i));
														lbxMovimientos
																.addItemToSelection(lbxMovimientos.getItemAtIndex(j));
														tienen_mismos_datos = true;
														i = listaMovimientoDetalle.size();
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
		}
		return tienen_mismos_datos;
	}

	public boolean validarSiTienenMismoCodigoYFechaInicioEnBD()
			throws ClassNotFoundException, FileNotFoundException, IOException {
		boolean tienen_mismos_datos = false;
		List<modelo_articulo_dn> listaArticulos = new ArrayList<modelo_articulo_dn>();
		listaArticulos = consultasABaseDeDatos.cargarArticulosDN("", id_dc, "0", 2, 0, "A", "");
		for (int i = 0; i < listaMovimientoDetalle.size(); i++) {
			if (listaMovimientoDetalle.get(i).getActualiza_inventario().equals("S")) {
				if (listaMovimientoDetalle.get(i).getSi_ing_fec_inicio_fin_actual().equals("S")) {
					if (listaMovimientoDetalle.get(i).getEs_fecha_actual().equals("S")) {
						if (listaMovimientoDetalle.get(i).getFec_fin_actual() == null) {
							for (int j = listaArticulos.size() - 1; j >= 0; j--) {
								if (listaMovimientoDetalle.get(i).getId_articulo() != listaArticulos.get(j)
										.getId_articulo()) {
									if (listaArticulos.get(j).getSi_ing_fec_inicio_fin().equals("S")) {
										if (listaArticulos.get(j).getEs_fecha().equals("S")) {
											if (listaArticulos.get(j).getFec_fin() == null) {
												if (listaMovimientoDetalle.get(i).getCod_articulo_actual()
														.equals(listaArticulos.get(j).getCod_articulo())
														&& listaMovimientoDetalle.get(i).getFec_inicio_actual()
																.equals(listaArticulos.get(j).getFec_inicio())) {
													lbxMovimientos.clearSelection();
													lbxMovimientos.addItemToSelection(lbxMovimientos.getItemAtIndex(i));
													tienen_mismos_datos = true;
													i = listaMovimientoDetalle.size();
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

	public boolean validarSiTienenMismoCodigoYFechaInicioYFechaFinEnLista() {
		boolean tienen_mismos_datos = false;
		for (int i = 0; i < listaMovimientoDetalle.size(); i++) {
			if (listaMovimientoDetalle.get(i).getActualiza_inventario().equals("S")) {
				if (listaMovimientoDetalle.get(i).getSi_ing_fec_inicio_fin_actual().equals("S")) {
					if (listaMovimientoDetalle.get(i).getEs_fecha_actual().equals("S")) {
						if (listaMovimientoDetalle.get(i).getFec_fin_actual() != null) {
							for (int j = listaMovimientoDetalle.size() - 1; j >= 0; j--) {
								if (listaMovimientoDetalle.get(i).getId_articulo() != listaMovimientoDetalle.get(j)
										.getId_articulo()) {
									if (listaMovimientoDetalle.get(j).getActualiza_inventario().equals("S")) {
										if (listaMovimientoDetalle.get(j).getSi_ing_fec_inicio_fin_actual()
												.equals("S")) {
											if (listaMovimientoDetalle.get(j).getEs_fecha_actual().equals("S")) {
												if (listaMovimientoDetalle.get(i).getFec_fin_actual() != null) {
													if (listaMovimientoDetalle.get(i).getCod_articulo_actual().equals(
															listaMovimientoDetalle.get(j).getCod_articulo_actual())
															&& listaMovimientoDetalle.get(i).getFec_inicio_actual()
																	.equals(listaMovimientoDetalle.get(j)
																			.getFec_inicio_actual())
															&& listaMovimientoDetalle.get(i).getFec_fin_actual()
																	.equals(listaMovimientoDetalle.get(j)
																			.getFec_fin_actual())) {
														lbxMovimientos.clearSelection();
														lbxMovimientos
																.addItemToSelection(lbxMovimientos.getItemAtIndex(i));
														lbxMovimientos
																.addItemToSelection(lbxMovimientos.getItemAtIndex(j));
														tienen_mismos_datos = true;
														i = listaMovimientoDetalle.size();
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
		}
		return tienen_mismos_datos;
	}

	public boolean validarSiTienenMismoCodigoYFechaInicioYFechaFinEnBD()
			throws ClassNotFoundException, FileNotFoundException, IOException {
		boolean tienen_mismos_datos = false;
		List<modelo_articulo_dn> listaArticulos = new ArrayList<modelo_articulo_dn>();
		listaArticulos = consultasABaseDeDatos.cargarArticulosDN("", id_dc, "0", 2, 0, "A", "");
		for (int i = 0; i < listaMovimientoDetalle.size(); i++) {
			if (listaMovimientoDetalle.get(i).getActualiza_inventario().equals("S")) {
				if (listaMovimientoDetalle.get(i).getSi_ing_fec_inicio_fin_actual().equals("S")) {
					if (listaMovimientoDetalle.get(i).getEs_fecha_actual().equals("S")) {
						if (listaMovimientoDetalle.get(i).getFec_fin_actual() != null) {
							for (int j = listaArticulos.size() - 1; j >= 0; j--) {
								if (listaMovimientoDetalle.get(i).getId_articulo() != listaArticulos.get(j)
										.getId_articulo()) {
									if (listaArticulos.get(j).getSi_ing_fec_inicio_fin().equals("S")) {
										if (listaArticulos.get(j).getEs_fecha().equals("S")) {
											if (listaMovimientoDetalle.get(i).getFec_fin_actual() != null) {
												if (listaMovimientoDetalle.get(i).getCod_articulo_actual()
														.equals(listaArticulos.get(j).getCod_articulo())
														&& listaMovimientoDetalle.get(i).getFec_inicio_actual()
																.equals(listaArticulos.get(j).getFec_inicio())
														&& listaMovimientoDetalle.get(i).getFec_fin_actual()
																.equals(listaArticulos.get(j).getFec_fin())) {
													lbxMovimientos.clearSelection();
													lbxMovimientos.addItemToSelection(lbxMovimientos.getItemAtIndex(i));
													tienen_mismos_datos = true;
													i = listaMovimientoDetalle.size();
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

	/*
	 * Se valida que no se supere el stock en la ubicacion, si es que se encuentra
	 * activa la validacion de ubicacion
	 * 
	 */

	public boolean validarSiEstaDentroDeStockPermitido()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		boolean esta_dentro_de_stock = false;
		/*
		 * Se valida que no se supere la capacidad permitida en la ubicacion
		 * seleccionada
		 */
		int contador_ingresa = 0;
		int cantidad_articulos_en_ubicacion_seleccionada_anterior = 0;
		int cantidad_articulos_en_ubicacion_seleccionada_actual = 0;
		lbxMovimientos.clearSelection();
		for (int i = 0; i < listaMovimientoDetalle.size(); i++) {
			// Se valida si se el registro actualiza el inventario
			if (listaMovimientoDetalle.get(i).getActualiza_inventario().equals("S")) {
				// Se valida si la ubicacion anterior es diferente a la ubicacion actual
				if (listaMovimientoDetalle.get(i).getId_ubicacion_actual() != listaMovimientoDetalle.get(i)
						.getId_ubicacion_anterior()) {
					// Se consulta en la BD si se debe validar capacidad en la ubicacion
					// seleccionada
					String seValidaCapacidad = consultasABaseDeDatos
							.seValidaCapacidadEnUbicacionDN(listaMovimientoDetalle.get(i).getId_ubicacion_actual());
					// Se valida si se encuentra activa la validacion de capacidad en la ubicacion
					// seleccionada
					if (seValidaCapacidad.equals("S")) {
						// Se consulta en la BD la capacidad maxima permitida en la ubicacion
						// seleccionada
						int capacidadMaxima = consultasABaseDeDatos.capacidadMaximaEnUbicacionDN(
								listaMovimientoDetalle.get(i).getId_ubicacion_actual(), seValidaCapacidad);
						// Se consulta en la BD la cantidad de registros ingresados en la ubicacion
						// seleccionada
						int totalArticulos = consultasABaseDeDatos
								.totalArticulosEnUbicacionDN(listaMovimientoDetalle.get(i).getId_ubicacion_actual());
						cantidad_articulos_en_ubicacion_seleccionada_anterior = cantidadDeArticulosEnUbicacionSeleccionadaAnterior(
								listaMovimientoDetalle.get(i).getId_ubicacion_actual());
						cantidad_articulos_en_ubicacion_seleccionada_actual = cantidadDeArticulosEnUbicacionSeleccionadaActual(
								listaMovimientoDetalle.get(i).getId_ubicacion_actual());
						if (((totalArticulos + cantidad_articulos_en_ubicacion_seleccionada_actual)
								- cantidad_articulos_en_ubicacion_seleccionada_anterior) > capacidadMaxima) {
							lbxMovimientos.addItemToSelection(lbxMovimientos.getItemAtIndex(i));
							contador_ingresa++;
						}
					}
				}
			}
		}
		if (contador_ingresa > 0) {
			esta_dentro_de_stock = true;
		}
		return esta_dentro_de_stock;
	}

	public int cantidadDeArticulosEnUbicacionSeleccionadaAnterior(long id_ubicacion) {
		int cantidad_articulos = 0;
		for (int i = 0; i < listaMovimientoDetalle.size(); i++) {
			if (listaMovimientoDetalle.get(i).getActualiza_inventario().equals("S")) {
				if (listaMovimientoDetalle.get(i).getId_ubicacion_anterior() != listaMovimientoDetalle.get(i)
						.getId_ubicacion_actual()
						&& listaMovimientoDetalle.get(i).getId_ubicacion_anterior() == id_ubicacion) {
					cantidad_articulos++;
				}
			}
		}
		return cantidad_articulos;
	}

	public int cantidadDeArticulosEnUbicacionSeleccionadaActual(long id_ubicacion) {
		int cantidad_articulos = 0;
		for (int i = 0; i < listaMovimientoDetalle.size(); i++) {
			if (listaMovimientoDetalle.get(i).getActualiza_inventario().equals("S")) {
				if (listaMovimientoDetalle.get(i).getId_ubicacion_anterior() != listaMovimientoDetalle.get(i)
						.getId_ubicacion_actual()
						&& listaMovimientoDetalle.get(i).getId_ubicacion_actual() == id_ubicacion) {
					cantidad_articulos++;
				}
			}
		}
		return cantidad_articulos;
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
		listaArticulo = consultasABaseDeDatos.cargarArticulosDN(txtBuscarArticulo.getText().toString(), id_dc,
				cmbEmpresa.getSelectedItem().getValue().toString(), 2, 0, "A", "");
		binder.loadComponent(lbxArticulos);
		lbxArticulos.clearSelection();
		bdxArticulos.setText("");
		bdxArticulos.setTooltiptext("");
		listaMovimientoDetalle = new ArrayList<modelo_movimiento_detalle_dn>();
		binder.loadComponent(lbxMovimientos);
		setearFechaIngresaFormulario();
	}

	public void limpiarCamposTotal() throws ClassNotFoundException, FileNotFoundException, IOException {
		txtId.setText("");
		cmbPedido.setSelectedIndex(0);
		inicializarFechas();
		bdxSolicitantes.setText("");
		lbxSolicitantes.clearSelection();
		cmbEstado.setSelectedIndex(0);
		txtObservacion.setText("");
		cmbEmpresa.setText("");
		listaArticulo = new ArrayList<modelo_articulo_dn>();
		binder.loadComponent(lbxArticulos);
		lbxArticulos.clearSelection();
		bdxArticulos.setText("");
		bdxArticulos.setTooltiptext("");
		bdxArticulos.setDisabled(true);
		validarTurno();
		listaMovimientoDetalle = new ArrayList<modelo_movimiento_detalle_dn>();
		binder.loadComponent(lbxMovimientos);
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

}
