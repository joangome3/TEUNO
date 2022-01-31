package bp.aplicaciones.controlador.mantenimiento.manuales;

import bp.aplicaciones.mantenimientos.DAO.dao_manual;
import bp.aplicaciones.mantenimientos.modelo.modelo_manual;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.zkoss.util.media.AMedia;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Window;

@SuppressWarnings({ "serial", "deprecation" })
public class ver_manual extends SelectorComposer<Component> {

	AnnotateDataBinder binder;

	@Wire
	Window zManual;
	@Wire
	Iframe frManual;

	modelo_manual manual = (modelo_manual) Sessions.getCurrent().getAttribute("objeto");
	
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
		cargarManual();
	}
	
	public void cargarManual() throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		List<modelo_manual> listaManuales = new ArrayList<modelo_manual>();
		dao_manual dao = new dao_manual();
		listaManuales = dao.obtenerManuales("", String.valueOf(id_dc), 2, manual.getId_manual(), 0);
		if(listaManuales.size() > 0) {
			int blobLength = (int) listaManuales.get(0).getDir_manual().length();  
			byte[] buffer = listaManuales.get(0).getDir_manual().getBytes(1, blobLength);
			ByteArrayInputStream is = new ByteArrayInputStream(buffer);
			final AMedia amedia = new AMedia(listaManuales.get(0).getNom_manual()+"."+listaManuales.get(0).getExt_manual(), "doc", "application/vnd.ms-word", is);
	          frManual.setContent(amedia);
		}
	}

}
