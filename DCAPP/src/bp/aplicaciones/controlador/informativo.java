package bp.aplicaciones.controlador;

import bp.aplicaciones.mantenimientos.DAO.dao_relacion_informativo;
import bp.aplicaciones.mantenimientos.modelo.modelo_informativo;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_informativo_usuario;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.Html;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Timer;
import org.zkoss.zul.Window;

@SuppressWarnings({ "serial", "deprecation" })
public class informativo extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zInformativo;
	@Wire
	Timer cTimer;
	@Wire
	Listbox lbxInformativos;
	@Wire
	Label lInformativosSinLeer;

	int contadorInformativosSinLeer = 0;

	@SuppressWarnings("unchecked")
	List<modelo_informativo> listaInformativo = (List<modelo_informativo>) Sessions.getCurrent()
			.getAttribute("listaInformativo");
	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	String user = (String) Sessions.getCurrent().getAttribute("user");

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		lbxInformativos.setEmptyMessage("¡No existen informativos que mostrar.!");
		borrarListaConsulta();
		dibujarListaConsulta();
		cargarInformativosSinLeer();
	}

	public void cargarInformativosSinLeer() {
		lInformativosSinLeer.setVisible(false);
		if (contadorInformativosSinLeer == 0) {
			lInformativosSinLeer.setVisible(true);
			lInformativosSinLeer.setValue("No tiene informativos que leer");
		}
		if (contadorInformativosSinLeer == 1) {
			lInformativosSinLeer.setVisible(true);
			lInformativosSinLeer.setValue("Tiene " + contadorInformativosSinLeer + " informativo sin leer");
		}
		if (contadorInformativosSinLeer > 1) {
			lInformativosSinLeer.setVisible(true);
			lInformativosSinLeer.setValue("Tiene " + contadorInformativosSinLeer + " informativos sin leer");
		}
	}

	public void dibujarListaConsulta()
			throws WrongValueException, ClassNotFoundException, FileNotFoundException, IOException, SQLException {
		Listitem lItem;
		Listcell lCell;
		for (int i = 0; i < listaInformativo.size(); i++) {
			LocalDateTime localDateTime1 = null;
			LocalDateTime localDateTime2 = null;
			LocalDate localDate1 = listaInformativo.get(i).getFec_fin().toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();
			LocalDate localDate2 = new Date(System.currentTimeMillis()).toInstant().atZone(ZoneId.systemDefault())
					.toLocalDate();
			int year1 = localDate1.getYear();
			int year2 = localDate2.getYear();
			localDateTime1 = LocalDateTime.of(year1, listaInformativo.get(i).getFec_fin().getMonth() + 1,
					listaInformativo.get(i).getFec_fin().getDate(), listaInformativo.get(i).getFec_fin().getHours(),
					listaInformativo.get(i).getFec_fin().getMinutes());
			localDateTime2 = LocalDateTime.of(year2, new Date(System.currentTimeMillis()).getMonth() + 1,
					new Date(System.currentTimeMillis()).getDate(), new Date(System.currentTimeMillis()).getHours(),
					new Date(System.currentTimeMillis()).getMinutes());
			Date d1 = null;
			Date d2 = null;
			d1 = Date.from(localDateTime1.atZone(ZoneId.systemDefault()).toInstant());
			d2 = Date.from(localDateTime2.atZone(ZoneId.systemDefault()).toInstant());
			if (!d2.after(d1)) {
				lItem = new Listitem();
				Html hMensaje;
				Button btnLeido;
				Label lFecha;
				Div dLeido;
				/* ID */
				lCell = new Listcell();
				lCell.setLabel(String.valueOf(listaInformativo.get(i).getId_informativo()));
				lCell.setStyle("text-align: center !important;");
				lItem.appendChild(lCell);
				/* MENSAJE */
				lCell = new Listcell();
				hMensaje = new Html();
				hMensaje.setContent(listaInformativo.get(i).getDescripcion());
				dLeido = new Div();
				lFecha = new Label();
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
				String f1 = "", f2 = "";
				f1 = sdf.format(listaInformativo.get(i).getFec_inicio());
				f2 = sdf.format(listaInformativo.get(i).getFec_fin());
				lFecha.setValue(f1 + " - " + f2);
				lFecha.setStyle("font-weight: bold !important;");
				dLeido = new Div();
				dLeido.setHeight("25px");
				dLeido.appendChild(lFecha);
				lCell.appendChild(dLeido);
				btnLeido = new Button();
				List<modelo_relacion_informativo_usuario> listaRelacion = new ArrayList<modelo_relacion_informativo_usuario>();
				dao_relacion_informativo dao = new dao_relacion_informativo();
				listaRelacion = dao.obtenerRelacionesInformativos(
						String.valueOf(listaInformativo.get(i).getId_informativo()), String.valueOf(id_user), 1);
				if (listaRelacion.size() > 0) {
					btnLeido.setDisabled(true);
					btnLeido.setImage("/img/botones/ButtonOK.png");
					btnLeido.setTooltiptext("El informativo ya fue leido");
				} else {
					btnLeido.setDisabled(false);
					btnLeido.setImage("/img/botones/ButtonSave.png");
					btnLeido.setTooltiptext("Leer informativo");
					btnLeido.setAutodisable("true");
					contadorInformativosSinLeer++;
				}
				btnLeido.addEventListener(Events.ON_CLICK, new EventListener<Event>() {
					public void onEvent(Event event) throws Exception {
						final long id;
						Listitem lItem;
						Listcell lCell;
						lItem = (Listitem) btnLeido.getParent().getParent().getParent();
						lCell = (Listcell) lItem.getChildren().get(0);
						id = Long.valueOf(lCell.getLabel().toString());
						List<modelo_relacion_informativo_usuario> listaRelacion = new ArrayList<modelo_relacion_informativo_usuario>();
						dao_relacion_informativo dao = new dao_relacion_informativo();
						listaRelacion = dao.obtenerRelacionesInformativos(String.valueOf(id), String.valueOf(id_user),
								1);
						if (listaRelacion.size() > 0) {
							return;
						}
						modelo_relacion_informativo_usuario relacion = new modelo_relacion_informativo_usuario();
						dao = new dao_relacion_informativo();
						java.util.Date date = null;
						Timestamp timestamp = null;
						relacion.setId_informativo(id);
						relacion.setId_usuario(id_user);
						relacion.setEsta_leido("S");
						relacion.setEst_relacion("AE");
						relacion.setUsu_ingresa(user);
						date = new Date();
						timestamp = new Timestamp(date.getTime());
						relacion.setFec_ingresa(timestamp);
						try {
							dao.insertarRelacion(relacion);
							btnLeido.setImage("/img/botones/ButtonOK.png");
							btnLeido.setDisabled(true);
							btnLeido.setTooltiptext("El informativo ya fue leido");
							contadorInformativosSinLeer--;
							cargarInformativosSinLeer();
						} catch (Exception e) {
							btnLeido.setDisabled(false);
							Messagebox.show(
									"Error al leer el informativo. \n\n" + "Mensaje de error: \n\n" + e.getMessage(),
									".:: Leer informativo ::.", Messagebox.OK, Messagebox.EXCLAMATION);
						}
					}
				});
				dLeido = new Div();
				dLeido.appendChild(btnLeido);
				dLeido.setClass("m-div-g");
				lCell.appendChild(hMensaje);
				lCell.appendChild(dLeido);
				lItem.appendChild(lCell);
				/* AÑADIR ITEM A LISTBOX */
				lbxInformativos.appendChild(lItem);
			}
		}
	}

	public void borrarListaConsulta() {
		lbxInformativos.clearSelection();
		Listitem lItem;
		for (int i = lbxInformativos.getItemCount() - 1; i >= 0; i--) {
			lItem = (Listitem) lbxInformativos.getItemAtIndex(i);
			lbxInformativos.removeItemAt(lItem.getIndex());
		}
		binder.loadComponent(lbxInformativos);
	}

}
