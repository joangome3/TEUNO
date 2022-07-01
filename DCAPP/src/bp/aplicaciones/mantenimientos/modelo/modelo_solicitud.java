package bp.aplicaciones.mantenimientos.modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;

public class modelo_solicitud {

	private long id_solicitud;
	private long id_tip_solicitud;
	private String nom_tipo_solicitud;
	private String comentario_1;
	private String comentario_2;
	private String comentario_3;
	private String comentario_4;
	private String comentario_5;
	private long id_mantenimiento;
	private String nom_mantenimiento;
	private long id_registro;
	private long id_campo;
	private long id_user_1;
	private long id_user_2;
	private long id_user_3;
	private long id_user_4;
	private long id_user_5;
	private Timestamp fecha_1;
	private Timestamp fecha_2;
	private Timestamp fecha_3;
	private Timestamp fecha_4;
	private Timestamp fecha_5;
	private String nom_registro;
	private String nom_campo;
	private String est_solicitud;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_solicitud() {
		super();
	}

	/**
	 * @param id_solicitud
	 * @param id_tip_solicitud
	 * @param nom_tipo_solicitud
	 * @param comentario_1
	 * @param comentario_2
	 * @param comentario_3
	 * @param comentario_4
	 * @param comentario_5
	 * @param id_mantenimiento
	 * @param nom_mantenimiento
	 * @param id_registro
	 * @param id_campo
	 * @param id_user_1
	 * @param id_user_2
	 * @param id_user_3
	 * @param id_user_4
	 * @param id_user_5
	 * @param fecha_1
	 * @param fecha_2
	 * @param fecha_3
	 * @param fecha_4
	 * @param fecha_5
	 * @param nom_registro
	 * @param nom_campo
	 * @param est_solicitud
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_solicitud(long id_solicitud, long id_tip_solicitud, String nom_tipo_solicitud, String comentario_1,
			String comentario_2, String comentario_3, String comentario_4, String comentario_5, long id_mantenimiento,
			String nom_mantenimiento, long id_registro, long id_campo, long id_user_1, long id_user_2, long id_user_3,
			long id_user_4, long id_user_5, Timestamp fecha_1, Timestamp fecha_2, Timestamp fecha_3, Timestamp fecha_4,
			Timestamp fecha_5, String nom_registro, String nom_campo, String est_solicitud, String usu_ingresa,
			Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_solicitud = id_solicitud;
		this.id_tip_solicitud = id_tip_solicitud;
		this.nom_tipo_solicitud = nom_tipo_solicitud;
		this.comentario_1 = comentario_1;
		this.comentario_2 = comentario_2;
		this.comentario_3 = comentario_3;
		this.comentario_4 = comentario_4;
		this.comentario_5 = comentario_5;
		this.id_mantenimiento = id_mantenimiento;
		this.nom_mantenimiento = nom_mantenimiento;
		this.id_registro = id_registro;
		this.id_campo = id_campo;
		this.id_user_1 = id_user_1;
		this.id_user_2 = id_user_2;
		this.id_user_3 = id_user_3;
		this.id_user_4 = id_user_4;
		this.id_user_5 = id_user_5;
		this.fecha_1 = fecha_1;
		this.fecha_2 = fecha_2;
		this.fecha_3 = fecha_3;
		this.fecha_4 = fecha_4;
		this.fecha_5 = fecha_5;
		this.nom_registro = nom_registro;
		this.nom_campo = nom_campo;
		this.est_solicitud = est_solicitud;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_solicitud
	 * @param id_tip_solicitud
	 * @param comentario_1
	 * @param comentario_2
	 * @param comentario_3
	 * @param comentario_4
	 * @param comentario_5
	 * @param id_mantenimiento
	 * @param id_registro
	 * @param id_campo
	 * @param id_user_1
	 * @param id_user_2
	 * @param id_user_3
	 * @param id_user_4
	 * @param id_user_5
	 * @param fecha_1
	 * @param fecha_2
	 * @param fecha_3
	 * @param fecha_4
	 * @param fecha_5
	 * @param est_solicitud
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_solicitud(long id_solicitud, long id_tip_solicitud, String comentario_1, String comentario_2,
			String comentario_3, String comentario_4, String comentario_5, long id_mantenimiento, long id_registro,
			long id_campo, long id_user_1, long id_user_2, long id_user_3, long id_user_4, long id_user_5,
			Timestamp fecha_1, Timestamp fecha_2, Timestamp fecha_3, Timestamp fecha_4, Timestamp fecha_5,
			String est_solicitud, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica,
			Timestamp fec_modifica) {
		super();
		this.id_solicitud = id_solicitud;
		this.id_tip_solicitud = id_tip_solicitud;
		this.comentario_1 = comentario_1;
		this.comentario_2 = comentario_2;
		this.comentario_3 = comentario_3;
		this.comentario_4 = comentario_4;
		this.comentario_5 = comentario_5;
		this.id_mantenimiento = id_mantenimiento;
		this.id_registro = id_registro;
		this.id_campo = id_campo;
		this.id_user_1 = id_user_1;
		this.id_user_2 = id_user_2;
		this.id_user_3 = id_user_3;
		this.id_user_4 = id_user_4;
		this.id_user_5 = id_user_5;
		this.fecha_1 = fecha_1;
		this.fecha_2 = fecha_2;
		this.fecha_3 = fecha_3;
		this.fecha_4 = fecha_4;
		this.fecha_5 = fecha_5;
		this.est_solicitud = est_solicitud;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_solicitud
	 */
	public long getId_solicitud() {
		return id_solicitud;
	}

	/**
	 * @param id_solicitud the id_solicitud to set
	 */
	public void setId_solicitud(long id_solicitud) {
		this.id_solicitud = id_solicitud;
	}

	/**
	 * @return the id_tip_solicitud
	 */
	public long getId_tip_solicitud() {
		return id_tip_solicitud;
	}

	/**
	 * @param id_tip_solicitud the id_tip_solicitud to set
	 */
	public void setId_tip_solicitud(long id_tip_solicitud) {
		this.id_tip_solicitud = id_tip_solicitud;
	}

	/**
	 * @return the nom_tipo_solicitud
	 */
	public String getNom_tipo_solicitud() {
		return nom_tipo_solicitud;
	}

	/**
	 * @param nom_tipo_solicitud the nom_tipo_solicitud to set
	 */
	public void setNom_tipo_solicitud(String nom_tipo_solicitud) {
		this.nom_tipo_solicitud = nom_tipo_solicitud;
	}

	/**
	 * @return the comentario_1
	 */
	public String getComentario_1() {
		return comentario_1;
	}

	/**
	 * @param comentario_1 the comentario_1 to set
	 */
	public void setComentario_1(String comentario_1) {
		this.comentario_1 = comentario_1;
	}

	/**
	 * @return the comentario_2
	 */
	public String getComentario_2() {
		return comentario_2;
	}

	/**
	 * @param comentario_2 the comentario_2 to set
	 */
	public void setComentario_2(String comentario_2) {
		this.comentario_2 = comentario_2;
	}

	/**
	 * @return the comentario_3
	 */
	public String getComentario_3() {
		return comentario_3;
	}

	/**
	 * @param comentario_3 the comentario_3 to set
	 */
	public void setComentario_3(String comentario_3) {
		this.comentario_3 = comentario_3;
	}

	/**
	 * @return the comentario_4
	 */
	public String getComentario_4() {
		return comentario_4;
	}

	/**
	 * @param comentario_4 the comentario_4 to set
	 */
	public void setComentario_4(String comentario_4) {
		this.comentario_4 = comentario_4;
	}

	/**
	 * @return the comentario_5
	 */
	public String getComentario_5() {
		return comentario_5;
	}

	/**
	 * @param comentario_5 the comentario_5 to set
	 */
	public void setComentario_5(String comentario_5) {
		this.comentario_5 = comentario_5;
	}

	/**
	 * @return the id_mantenimiento
	 */
	public long getId_mantenimiento() {
		return id_mantenimiento;
	}

	/**
	 * @param id_mantenimiento the id_mantenimiento to set
	 */
	public void setId_mantenimiento(long id_mantenimiento) {
		this.id_mantenimiento = id_mantenimiento;
	}

	/**
	 * @return the nom_mantenimiento
	 */
	public String getNom_mantenimiento() {
		return nom_mantenimiento;
	}

	/**
	 * @param nom_mantenimiento the nom_mantenimiento to set
	 */
	public void setNom_mantenimiento(String nom_mantenimiento) {
		this.nom_mantenimiento = nom_mantenimiento;
	}

	/**
	 * @return the id_registro
	 */
	public long getId_registro() {
		return id_registro;
	}

	/**
	 * @param id_registro the id_registro to set
	 */
	public void setId_registro(long id_registro) {
		this.id_registro = id_registro;
	}

	/**
	 * @return the id_campo
	 */
	public long getId_campo() {
		return id_campo;
	}

	/**
	 * @param id_campo the id_campo to set
	 */
	public void setId_campo(long id_campo) {
		this.id_campo = id_campo;
	}

	/**
	 * @return the id_user_1
	 */
	public long getId_user_1() {
		return id_user_1;
	}

	/**
	 * @param id_user_1 the id_user_1 to set
	 */
	public void setId_user_1(long id_user_1) {
		this.id_user_1 = id_user_1;
	}

	/**
	 * @return the id_user_2
	 */
	public long getId_user_2() {
		return id_user_2;
	}

	/**
	 * @param id_user_2 the id_user_2 to set
	 */
	public void setId_user_2(long id_user_2) {
		this.id_user_2 = id_user_2;
	}

	/**
	 * @return the id_user_3
	 */
	public long getId_user_3() {
		return id_user_3;
	}

	/**
	 * @param id_user_3 the id_user_3 to set
	 */
	public void setId_user_3(long id_user_3) {
		this.id_user_3 = id_user_3;
	}

	/**
	 * @return the id_user_4
	 */
	public long getId_user_4() {
		return id_user_4;
	}

	/**
	 * @param id_user_4 the id_user_4 to set
	 */
	public void setId_user_4(long id_user_4) {
		this.id_user_4 = id_user_4;
	}

	/**
	 * @return the id_user_5
	 */
	public long getId_user_5() {
		return id_user_5;
	}

	/**
	 * @param id_user_5 the id_user_5 to set
	 */
	public void setId_user_5(long id_user_5) {
		this.id_user_5 = id_user_5;
	}

	/**
	 * @return the fecha_1
	 */
	public Timestamp getFecha_1() {
		return fecha_1;
	}

	/**
	 * @param fecha_1 the fecha_1 to set
	 */
	public void setFecha_1(Timestamp fecha_1) {
		this.fecha_1 = fecha_1;
	}

	/**
	 * @return the fecha_2
	 */
	public Timestamp getFecha_2() {
		return fecha_2;
	}

	/**
	 * @param fecha_2 the fecha_2 to set
	 */
	public void setFecha_2(Timestamp fecha_2) {
		this.fecha_2 = fecha_2;
	}

	/**
	 * @return the fecha_3
	 */
	public Timestamp getFecha_3() {
		return fecha_3;
	}

	/**
	 * @param fecha_3 the fecha_3 to set
	 */
	public void setFecha_3(Timestamp fecha_3) {
		this.fecha_3 = fecha_3;
	}

	/**
	 * @return the fecha_4
	 */
	public Timestamp getFecha_4() {
		return fecha_4;
	}

	/**
	 * @param fecha_4 the fecha_4 to set
	 */
	public void setFecha_4(Timestamp fecha_4) {
		this.fecha_4 = fecha_4;
	}

	/**
	 * @return the fecha_5
	 */
	public Timestamp getFecha_5() {
		return fecha_5;
	}

	/**
	 * @param fecha_5 the fecha_5 to set
	 */
	public void setFecha_5(Timestamp fecha_5) {
		this.fecha_5 = fecha_5;
	}

	/**
	 * @return the nom_registro
	 */
	public String getNom_registro() {
		return nom_registro;
	}

	/**
	 * @param nom_registro the nom_registro to set
	 */
	public void setNom_registro(String nom_registro) {
		this.nom_registro = nom_registro;
	}

	/**
	 * @return the nom_campo
	 */
	public String getNom_campo() {
		return nom_campo;
	}

	/**
	 * @param nom_campo the nom_campo to set
	 */
	public void setNom_campo(String nom_campo) {
		this.nom_campo = nom_campo;
	}

	/**
	 * @return the est_solicitud
	 */
	public String getEst_solicitud() {
		return est_solicitud;
	}

	/**
	 * @param est_solicitud the est_solicitud to set
	 */
	public void setEst_solicitud(String est_solicitud) {
		this.est_solicitud = est_solicitud;
	}

	/**
	 * @return the usu_ingresa
	 */
	public String getUsu_ingresa() {
		return usu_ingresa;
	}

	/**
	 * @param usu_ingresa the usu_ingresa to set
	 */
	public void setUsu_ingresa(String usu_ingresa) {
		this.usu_ingresa = usu_ingresa;
	}

	/**
	 * @return the fec_ingresa
	 */
	public Timestamp getFec_ingresa() {
		return fec_ingresa;
	}

	/**
	 * @param fec_ingresa the fec_ingresa to set
	 */
	public void setFec_ingresa(Timestamp fec_ingresa) {
		this.fec_ingresa = fec_ingresa;
	}

	/**
	 * @return the usu_modifica
	 */
	public String getUsu_modifica() {
		return usu_modifica;
	}

	/**
	 * @param usu_modifica the usu_modifica to set
	 */
	public void setUsu_modifica(String usu_modifica) {
		this.usu_modifica = usu_modifica;
	}

	/**
	 * @return the fec_modifica
	 */
	public Timestamp getFec_modifica() {
		return fec_modifica;
	}

	/**
	 * @param fec_modifica the fec_modifica to set
	 */
	public void setFec_modifica(Timestamp fec_modifica) {
		this.fec_modifica = fec_modifica;
	}

	@Override
	public String toString() {
		return "modelo_solicitud [id_solicitud=" + id_solicitud + ", id_tip_solicitud=" + id_tip_solicitud
				+ ", nom_tipo_solicitud=" + nom_tipo_solicitud + ", comentario_1=" + comentario_1 + ", comentario_2="
				+ comentario_2 + ", comentario_3=" + comentario_3 + ", comentario_4=" + comentario_4 + ", comentario_5="
				+ comentario_5 + ", id_mantenimiento=" + id_mantenimiento + ", nom_mantenimiento=" + nom_mantenimiento
				+ ", id_registro=" + id_registro + ", id_campo=" + id_campo + ", id_user_1=" + id_user_1
				+ ", id_user_2=" + id_user_2 + ", id_user_3=" + id_user_3 + ", id_user_4=" + id_user_4 + ", id_user_5="
				+ id_user_5 + ", fecha_1=" + fecha_1 + ", fecha_2=" + fecha_2 + ", fecha_3=" + fecha_3 + ", fecha_4="
				+ fecha_4 + ", fecha_5=" + fecha_5 + ", nom_registro=" + nom_registro + ", nom_campo=" + nom_campo
				+ ", est_solicitud=" + est_solicitud + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa
				+ ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

	public String toStringEstado() {
		if (est_solicitud.charAt(0) == 'P') {
			return "ABIERTA";
		} else if (est_solicitud.charAt(0) == 'R') {
			return "EN REVISIÓN";
		} else if (est_solicitud.charAt(0) == 'S') {
			return "PENDIENTE DE EJECUCIÓN";
		} else if (est_solicitud.charAt(0) == 'T') {
			return "PENDIENTE DE ACTUALIZACIÓN";
		} else if (est_solicitud.charAt(0) == 'A') {
			return "APROBADA";
		} else {
			return "NO APROBADA";
		}
	}

	public String mostrarFecha1() {
		String s = new SimpleDateFormat("dd/MM/yyyy").format(getFecha_1());
		return s;
	}
//
//	public String mostrarSolicitante() throws ClassNotFoundException, FileNotFoundException, IOException {
//		String nombre_usuario = "";
//		ConsultasABaseDeDatos consultaABaseDeDatos = new ConsultasABaseDeDatos();
//		List<modelo_usuario_bk> listaUsuario = new ArrayList<modelo_usuario_bk>();
//		listaUsuario = consultaABaseDeDatos.cargarUsuarios("", 1, 0);
//		Iterator<modelo_usuario_bk> it = listaUsuario.iterator();
//		while (it.hasNext()) {
//			modelo_usuario_bk usuario = it.next();
//			if (usuario.getId_usuario() == getId_user_1()) {
//				nombre_usuario = usuario.verNombreCompleto();
//				break;
//			}
//		}
//		return nombre_usuario;
//	}

	public String mostrarNombreOpcion() throws ClassNotFoundException, FileNotFoundException, IOException {
		String nombre_opcion = "";
		if (id_tip_solicitud == 7) { // Valido para solicitudes desde opciones
			if (id_mantenimiento == 4) { // Gestion de cintas
				ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
				List<modelo_opcion> listaOpcion = new ArrayList<modelo_opcion>();
				listaOpcion = consultasABaseDeDatos.cargarOpciones("");
				Iterator<modelo_opcion> it = listaOpcion.iterator();
				while (it.hasNext()) {
					modelo_opcion opcion = it.next();
					if (opcion.getId_opcion() == id_mantenimiento) {
						nombre_opcion = opcion.getNom_opcion();
						break;
					}
				}
			}
		} else {
			nombre_opcion = getNom_mantenimiento();
		}
		return nombre_opcion;
	}

	public String mostrarCampo() {
		String campo = "";
		if (id_campo != 0) {
			campo = getNom_campo();
		}
		return campo;
	}

	public String mostrarImagenCampo() {
		String imagen = "";
		if (id_campo == 0) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String estiloImagenCampo() {
		String estilo = "";
		if (id_campo == 0) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

}
