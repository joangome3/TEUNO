package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class modelo_informativo {

	private long id_informativo;
	private String descripcion;
	private String se_publica;
	private Timestamp fec_inicio;
	private Timestamp fec_fin;
	private long id_localidad;
	private String est_informativo;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	/**
	 * 
	 */
	public modelo_informativo() {
		super();
	}

	/**
	 * @param id_informativo
	 * @param descripcion
	 * @param se_publica
	 * @param fec_inicio
	 * @param fec_fin
	 * @param id_localidad
	 * @param est_informativo
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_informativo(long id_informativo, String descripcion, String se_publica, Timestamp fec_inicio,
			Timestamp fec_fin, long id_localidad, String est_informativo, String usu_ingresa, Timestamp fec_ingresa,
			String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_informativo = id_informativo;
		this.descripcion = descripcion;
		this.se_publica = se_publica;
		this.fec_inicio = fec_inicio;
		this.fec_fin = fec_fin;
		this.id_localidad = id_localidad;
		this.est_informativo = est_informativo;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_informativo
	 */
	public long getId_informativo() {
		return id_informativo;
	}

	/**
	 * @param id_informativo the id_informativo to set
	 */
	public void setId_informativo(long id_informativo) {
		this.id_informativo = id_informativo;
	}

	/**
	 * @return the descripcion
	 */
	public String getDescripcion() {
		return descripcion;
	}

	/**
	 * @param descripcion the descripcion to set
	 */
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	 * @return the se_publica
	 */
	public String getSe_publica() {
		return se_publica;
	}

	/**
	 * @param se_publica the se_publica to set
	 */
	public void setSe_publica(String se_publica) {
		this.se_publica = se_publica;
	}

	/**
	 * @return the fec_inicio
	 */
	public Timestamp getFec_inicio() {
		return fec_inicio;
	}

	/**
	 * @param fec_inicio the fec_inicio to set
	 */
	public void setFec_inicio(Timestamp fec_inicio) {
		this.fec_inicio = fec_inicio;
	}

	/**
	 * @return the fec_fin
	 */
	public Timestamp getFec_fin() {
		return fec_fin;
	}

	/**
	 * @param fec_fin the fec_fin to set
	 */
	public void setFec_fin(Timestamp fec_fin) {
		this.fec_fin = fec_fin;
	}

	/**
	 * @return the id_localidad
	 */
	public long getId_localidad() {
		return id_localidad;
	}

	/**
	 * @param id_localidad the id_localidad to set
	 */
	public void setId_localidad(long id_localidad) {
		this.id_localidad = id_localidad;
	}

	/**
	 * @return the est_informativo
	 */
	public String getEst_informativo() {
		return est_informativo;
	}

	/**
	 * @param est_informativo the est_informativo to set
	 */
	public void setEst_informativo(String est_informativo) {
		this.est_informativo = est_informativo;
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
		return "modelo_informativo [id_informativo=" + id_informativo + ", descripcion=" + descripcion + ", se_publica="
				+ se_publica + ", fec_inicio=" + fec_inicio + ", fec_fin=" + fec_fin + ", id_localidad=" + id_localidad
				+ ", est_informativo=" + est_informativo + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa="
				+ fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

	public String mostrarImagenPublicacdo() {
		String imagen = "";
		if (se_publica.equals("S")) {
			imagen = "/img/botones/ButtonOK.png";
		} else {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarFechaInicio() {
		String s = new SimpleDateFormat("dd/MM/yyyy").format(getFec_inicio());
		return s;
	}

	public String mostrarFechaFin() {
		String s = new SimpleDateFormat("dd/MM/yyyy").format(getFec_fin());
		return s;
	}

	public String estiloImagenPublicacdo() {
		String estilo = "";
		estilo = "text-align: center !important; color: transparent;";
		return estilo;
	}

}
