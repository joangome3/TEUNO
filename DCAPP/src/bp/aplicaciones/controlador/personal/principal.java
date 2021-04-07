package bp.aplicaciones.controlador.personal;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Borderlayout;
import org.zkoss.zul.Center;
import org.zkoss.zul.Include;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;
import org.zkoss.zul.Tabpanels;
import org.zkoss.zul.Treecell;
import org.zkoss.zul.Window;

@SuppressWarnings("deprecation")
public class principal extends SelectorComposer<Component> {

	private static final long serialVersionUID = 1L;

	AnnotateDataBinder binder;

	@Wire
	Window zConsultar;
	@Wire
	Treecell tcOpcion1, tcOpcion2, tcOpcion3, tcReporte1, tcReporte2;
	@Wire
	Tabbox tTab;
	@Wire
	Tabpanels tPanel;

	long id_user = (long) Sessions.getCurrent().getAttribute("id_user");
	long id_perfil = (long) Sessions.getCurrent().getAttribute("id_perfil");
	long id_dc = (long) Sessions.getCurrent().getAttribute("id_dc");
	String user = (String) Sessions.getCurrent().getAttribute("user");
	String nom_ape_user = (String) Sessions.getCurrent().getAttribute("nom_ape_user");

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		binder = new AnnotateDataBinder(comp);
		binder.loadAll();
	}

	/**
	 * CONSUlTAR SOLICITUDES
	 */

	@Listen("onClick=#tcOpcion1")
	public void onClick$tcOpcion1() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcOpcion1.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcOpcion1.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE PERSONAL | CONSULTAR SOLICITUDES");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcOpcion1.getId());
			tab.setImage("/img/botones/ButtonSearch4.png");
			tTab.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanel.setVflex("max");
			tabpanel.setWidth("100%");
			tabpanel.setStyle("height: calc(100%);");
			tPanel.appendChild(tabpanel);
			Include include = new Include("/personal/solicitud/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcOpcion2")
	public void onClick$tcOpcion2() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcOpcion2.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcOpcion2.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE PERSONAL | NUEVA SOLICITUD");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcOpcion2.getId());
			tab.setImage("/img/botones/ButtonNew4.png");
			tTab.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanel.setVflex("max");
			tabpanel.setWidth("100%");
			tabpanel.setStyle("height: calc(100%);");
			tPanel.appendChild(tabpanel);
			Include include = new Include("/personal/solicitud/nuevo.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcOpcion3")
	public void onClick$tcOpcion3() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcOpcion3.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcOpcion3.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE PERSONAL | REGISTRO DE PERMANENCIA");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcOpcion3.getId());
			tab.setImage("/img/botones/ButtonPersonal2.png");
			tTab.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanel.setVflex("max");
			tabpanel.setWidth("100%");
			tabpanel.setStyle("height: calc(100%);");
			tPanel.appendChild(tabpanel);
			Include include = new Include("/personal/registro_permanencia/consultar.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcReporte1")
	public void onClick$tcReporte1() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcReporte1.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcReporte1.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE PERSONAL - REPORTES | TAREAS");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcReporte1.getId());
			tab.setImage("/img/botones/ButtonReport4.png");
			tTab.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanel.setVflex("max");
			tabpanel.setWidth("100%");
			tabpanel.setStyle("height: calc(100%);");
			tPanel.appendChild(tabpanel);
			Include include = new Include("/personal/reporte_personal_1.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcReporte2")
	public void onClick$tcReporte2() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcReporte2.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcReporte2.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("GESTION DE PERSONAL - REPORTES | INGRESO DE PERSONAL");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcReporte2.getId());
			tab.setImage("/img/botones/ButtonReport4.png");
			tTab.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanel.setVflex("max");
			tabpanel.setWidth("100%");
			tabpanel.setStyle("height: calc(100%);");
			tPanel.appendChild(tabpanel);
			Include include = new Include("/personal/reporte_personal_2.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
