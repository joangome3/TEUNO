package bp.aplicaciones.controlador.mantenimientos.parametros;

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
	Treecell tcParametros1, tcParametros2, tcParametros3, tcParametros4, tcParametros5, tcParametros6, tcParametros7,
			tcParametros8, tcParametros9, tcParametros10, tcParametros11, tcParametros12;
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
	 * PARAMETROS GENERALES
	 */

	@Listen("onClick=#tcParametros1")
	public void onClick$tcParametros1() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcParametros1.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcParametros1.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("PARAMETROS VARIOS");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcParametros1.getId());
			tTab.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanel.setVflex("max");
			tabpanel.setWidth("100%");
			tabpanel.setStyle("height: calc(100%);");
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/parametros/parametros1.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcParametros2")
	public void onClick$tcParametros2() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcParametros2.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcParametros2.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("RELACIÓN CONFIGURACIÓN/CAMPOS");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcParametros2.getId());
			tTab.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanel.setVflex("max");
			tabpanel.setWidth("100%");
			tabpanel.setStyle("height: calc(100%);");
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/parametros/parametros2.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcParametros3")
	public void onClick$tcParametros3() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcParametros3.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcParametros3.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("RELACIÓN OPCIÓN/EMPRESAS");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcParametros3.getId());
			tTab.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanel.setVflex("max");
			tabpanel.setWidth("100%");
			tabpanel.setStyle("height: calc(100%);");
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/parametros/parametros3.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcParametros4")
	public void onClick$tcParametros4() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcParametros4.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcParametros4.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("RELACIÓN DÍA/TAREA PERIÓDICA");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcParametros4.getId());
			tTab.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanel.setVflex("max");
			tabpanel.setWidth("100%");
			tabpanel.setStyle("height: calc(100%);");
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/parametros/parametros4.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcParametros5")
	public void onClick$tcParametros5() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcParametros5.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcParametros5.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("RELACIÓN OPCIÓN/USUARIOS");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcParametros5.getId());
			tTab.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanel.setVflex("max");
			tabpanel.setWidth("100%");
			tabpanel.setStyle("height: calc(100%);");
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/parametros/parametros5.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcParametros6")
	public void onClick$tcParametros6() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcParametros6.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcParametros6.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("RELACIÓN CRITICIDAD/COLOR");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcParametros6.getId());
			tTab.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanel.setVflex("max");
			tabpanel.setWidth("100%");
			tabpanel.setStyle("height: calc(100%);");
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/parametros/parametros6.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcParametros7")
	public void onClick$tcParametros7() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcParametros7.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcParametros7.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("RELACIÓN ESTADO ARTÍCULO/COLOR");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcParametros7.getId());
			tTab.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanel.setVflex("max");
			tabpanel.setWidth("100%");
			tabpanel.setStyle("height: calc(100%);");
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/parametros/parametros7.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcParametros8")
	public void onClick$tcParametros8() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcParametros8.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcParametros8.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("RELACIÓN ESTADO BITACORA/COLOR");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcParametros8.getId());
			tTab.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanel.setVflex("max");
			tabpanel.setWidth("100%");
			tabpanel.setStyle("height: calc(100%);");
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/parametros/parametros8.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcParametros9")
	public void onClick$tcParametros9() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcParametros9.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcParametros9.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("RELACIÓN EMPRESA/TICKET BITACORA");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcParametros9.getId());
			tTab.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanel.setVflex("max");
			tabpanel.setWidth("100%");
			tabpanel.setStyle("height: calc(100%);");
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/parametros/parametros9.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Listen("onClick=#tcParametros10")
	public void onClick$tcParametros10() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcParametros10.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcParametros10.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("RELACIÓN OPCIÓN/TIPO DE SERVICIO (Actualizar Tareas)");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcParametros10.getId());
			tTab.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanel.setVflex("max");
			tabpanel.setWidth("100%");
			tabpanel.setStyle("height: calc(100%);");
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/parametros/parametros10.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Listen("onClick=#tcParametros11")
	public void onClick$tcParametros11() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcParametros11.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcParametros11.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("RELACIÓN OPCIÓN/TIPO DE SERVICIO (Actividades internas)");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcParametros11.getId());
			tTab.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanel.setVflex("max");
			tabpanel.setWidth("100%");
			tabpanel.setStyle("height: calc(100%);");
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/parametros/parametros11.zul");
			Center c = new Center();
			// c.setAutoscroll(true);
			c.appendChild(include);
			bl.appendChild(c);
			tabpanel.appendChild(bl);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Listen("onClick=#tcParametros12")
	public void onClick$tcParametros12() {
		try {
			Borderlayout bl = new Borderlayout();
			if (tTab.hasFellow("Tab:" + tcParametros12.getId())) {
				Tab tab2 = (Tab) tTab.getFellow("Tab:" + tcParametros12.getId());
				tab2.focus();
				tab2.setSelected(true);
				return;
			}
			Tab tab = new Tab();
			tab.setLabel("RELACIÓN OPCIÓN/EMPRESA (Ticket repetido)");
			tab.setClosable(true);
			tab.setSelected(true);
			tab.setId("Tab:" + tcParametros12.getId());
			tTab.getTabs().appendChild(tab);
			Tabpanel tabpanel = new Tabpanel();
			tabpanel.setVflex("max");
			tabpanel.setWidth("100%");
			tabpanel.setStyle("height: calc(100%);");
			tPanel.appendChild(tabpanel);
			Include include = new Include("/mantenimientos/parametros/parametros12.zul");
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
