package bp.aplicaciones.controlador.emergentes;


import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Window;

import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.mantenimientos.modelo.modelo_relacion_equipo_tipo_conector;
import bp.aplicaciones.mensajes.Error;
import bp.aplicaciones.mensajes.Informativos;
import bp.aplicaciones.mensajes.Validaciones;

@SuppressWarnings({ "serial", "deprecation" })
public class fuente extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zFuente;
	@Wire
	Listbox lbxFuentes;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	@SuppressWarnings("unchecked")
	List<modelo_relacion_equipo_tipo_conector> relacion = (List<modelo_relacion_equipo_tipo_conector>) Sessions
			.getCurrent().getAttribute("relacion");

	ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
	Fechas fechas = new Fechas();
	Validaciones validaciones = new Validaciones();

	Informativos informativos = new Informativos();
	Error error = new Error();

	List<modelo_relacion_equipo_tipo_conector> listaFuentes = new ArrayList<modelo_relacion_equipo_tipo_conector>();

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
		lbxFuentes.setEmptyMessage(informativos.getMensaje_informativo_2());
		listaFuentes = relacion;
		binder.loadComponent(lbxFuentes);
	}

	public List<modelo_relacion_equipo_tipo_conector> obtenerFuentes() {
		return listaFuentes;
	}

	@Listen("onClick=#btnCancelar")
	public void onClick$btnCancelar() {
		Events.postEvent(new Event("onClose", zFuente));
	}

}
