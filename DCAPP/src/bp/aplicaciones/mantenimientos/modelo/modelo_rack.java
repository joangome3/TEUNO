package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import bp.aplicaciones.equipos.modelo.modelo_gestion_equipo;

@Entity
@Table(name = "nocap_rack")
public class modelo_rack {

	@Id
	@Column(name = "id_rack", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_rack;
	@Column(name = "coord_rack", length = 20)
	private String coord_rack;
	@Column(name = "cant_ur")
	private int cant_ur;
	@Column(name = "serie", length = 150)
	private String serie;
	@Column(name = "cod_activo", length = 150)
	private String cod_activo;
	@Column(name = "est_rack", length = 5)
	private String est_rack;
	@Column(name = "usu_ingresa", length = 20)
	private String usu_ingresa;
	@Column(name = "fec_ingresa")
	private Timestamp fec_ingresa;
	@Column(name = "usu_modifica", length = 20)
	private String usu_modifica;
	@Column(name = "fec_modifica")
	private Timestamp fec_modifica;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_localidad")
	private modelo_localidad localidad;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_fila")
	private modelo_fila fila;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_cliente")
	private modelo_empresa empresa;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_marca")
	private modelo_marca marca;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_modelo")
	private modelo_modelo modelo;

	@OneToMany(mappedBy = "rack", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_relacion_rack_ur_equipo> relacion_gestion_racks_urs_equipos;

	@OneToMany(mappedBy = "rack", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_gestion_equipo> gestion_equipos;

	/**
	 * 
	 */
	public modelo_rack() {
	}

	/**
	 * @param coord_rack
	 * @param cant_ur
	 * @param serie
	 * @param cod_activo
	 * @param est_rack
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_rack(String coord_rack, int cant_ur, String serie, String cod_activo, String est_rack,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		this.coord_rack = coord_rack;
		this.cant_ur = cant_ur;
		this.serie = serie;
		this.cod_activo = cod_activo;
		this.est_rack = est_rack;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_rack
	 * @param coord_rack
	 * @param cant_ur
	 * @param serie
	 * @param cod_activo
	 * @param est_rack
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_rack(long id_rack, String coord_rack, int cant_ur, String serie, String cod_activo, String est_rack,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		this.id_rack = id_rack;
		this.coord_rack = coord_rack;
		this.cant_ur = cant_ur;
		this.serie = serie;
		this.cod_activo = cod_activo;
		this.est_rack = est_rack;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_rack
	 */
	public long getId_rack() {
		return id_rack;
	}

	/**
	 * @param id_rack the id_rack to set
	 */
	public void setId_rack(long id_rack) {
		this.id_rack = id_rack;
	}

	/**
	 * @return the nom_rack
	 */
	public String getCoord_rack() {
		return coord_rack;
	}

	/**
	 * @param nom_rack the nom_rack to set
	 */
	public void setCoord_rack(String coord_rack) {
		this.coord_rack = coord_rack;
	}

	/**
	 * @return the cant_ur
	 */
	public int getCant_ur() {
		return cant_ur;
	}

	/**
	 * @param cant_ur the cant_ur to set
	 */
	public void setCant_ur(int cant_ur) {
		this.cant_ur = cant_ur;
	}

	/**
	 * @return the serie
	 */
	public String getSerie() {
		return serie;
	}

	/**
	 * @param serie the serie to set
	 */
	public void setSerie(String serie) {
		this.serie = serie;
	}

	/**
	 * @return the cod_activo
	 */
	public String getCod_activo() {
		return cod_activo;
	}

	/**
	 * @param cod_activo the cod_activo to set
	 */
	public void setCod_activo(String cod_activo) {
		this.cod_activo = cod_activo;
	}

	/**
	 * @return the est_rack
	 */
	public String getEst_rack() {
		return est_rack;
	}

	/**
	 * @param est_rack the est_rack to set
	 */
	public void setEst_rack(String est_rack) {
		this.est_rack = est_rack;
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

	/**
	 * @return the localidad
	 */
	public modelo_localidad getLocalidad() {
		return localidad;
	}

	/**
	 * @param localidad the localidad to set
	 */
	public void setLocalidad(modelo_localidad localidad) {
		this.localidad = localidad;
	}

	/**
	 * @return the fila
	 */
	public modelo_fila getFila() {
		return fila;
	}

	/**
	 * @param fila the fila to set
	 */
	public void setFila(modelo_fila fila) {
		this.fila = fila;
	}

	/**
	 * @return the empresa
	 */
	public modelo_empresa getEmpresa() {
		return empresa;
	}

	/**
	 * @param empresa the empresa to set
	 */
	public void setEmpresa(modelo_empresa empresa) {
		this.empresa = empresa;
	}

	/**
	 * @return the marca
	 */
	public modelo_marca getMarca() {
		return marca;
	}

	/**
	 * @param marca the marca to set
	 */
	public void setMarca(modelo_marca marca) {
		this.marca = marca;
	}

	/**
	 * @return the modelo
	 */
	public modelo_modelo getModelo() {
		return modelo;
	}

	/**
	 * @param modelo the modelo to set
	 */
	public void setModelo(modelo_modelo modelo) {
		this.modelo = modelo;
	}

	/**
	 * @return the gestion_equipos
	 */
	public List<modelo_gestion_equipo> getGestion_equipos() {
		return gestion_equipos;
	}

	/**
	 * @param gestion_equipos the gestion_equipos to set
	 */
	public void setGestion_equipos(List<modelo_gestion_equipo> gestion_equipos) {
		this.gestion_equipos = gestion_equipos;
	}

	/**
	 * @return the relacion_gestion_racks_urs_equipos
	 */
	public List<modelo_relacion_rack_ur_equipo> getRelacion_gestion_racks_urs_equipos() {
		return relacion_gestion_racks_urs_equipos;
	}

	/**
	 * @param relacion_gestion_racks_urs_equipos the
	 *                                           relacion_gestion_racks_urs_equipos
	 *                                           to set
	 */
	public void setRelacion_gestion_racks_urs_equipos(
			List<modelo_relacion_rack_ur_equipo> relacion_gestion_racks_urs_equipos) {
		this.relacion_gestion_racks_urs_equipos = relacion_gestion_racks_urs_equipos;
	}

	@Override
	public String toString() {
		return "modelo_rack [id_rack=" + id_rack + ", coord_rack=" + coord_rack + ", cant_ur=" + cant_ur + ", serie="
				+ serie + ", cod_activo=" + cod_activo + ", est_rack=" + est_rack + ", usu_ingresa=" + usu_ingresa
				+ ", fec_ingresa=" + fec_ingresa + ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica
				+ "]";
	}

	public String mostrarEstado() {
		String estado = "";
		if (est_rack.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_rack.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_rack.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	/* Estilos en campos */

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_rack.charAt(0) == 'A') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_rack.charAt(0) == 'P') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_rack.charAt(0) == 'I') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FFDDDD;";
		}
		return estilo;
	}

	public String mostrarSerie() {
		String _serie = "";
		if (serie != null) {
			if (serie.length() > 0 && !serie.equals("N/A") && !serie.equals("NA") && !serie.equals("N/D")
					&& !serie.equals("ND")) {
				_serie = getSerie();
			}
		}
		return _serie;
	}

	public String mostrarCodigoActivo() {
		String codigo_activo = "";
		if (cod_activo != null) {
			if (cod_activo.length() > 0 && !cod_activo.equals("N/A") && !cod_activo.equals("NA")
					&& !cod_activo.equals("N/D") && !cod_activo.equals("ND")) {
				codigo_activo = getCod_activo();
			}
		}
		return codigo_activo;
	}

	public String mostrarImagenSerie() {
		String imagen = "";
		if (serie == null) {
			imagen = "/img/botones/ButtonError.png";
		} else {
			if (serie.length() <= 0 || serie.equals("N/A") || serie.equals("NA") || serie.equals("N/D")
					|| serie.equals("ND")) {
				imagen = "/img/botones/ButtonError.png";
			}
		}
		return imagen;
	}

	public String mostrarImagenCodigoActivo() {
		String imagen = "";
		if (cod_activo == null) {
			imagen = "/img/botones/ButtonError.png";
		} else {
			if (cod_activo.length() <= 0 || cod_activo.equals("N/A") || cod_activo.equals("NA")
					|| cod_activo.equals("N/D") || cod_activo.equals("ND")) {
				imagen = "/img/botones/ButtonError.png";
			}
		}
		return imagen;
	}

	public String estiloImagenSerie() {
		String estilo = "text-align: center !important;";
		if (serie == null) {
			estilo = "text-align: center !important; color: transparent;";
		} else {
			if (serie.length() <= 0 || serie.equals("N/A") || serie.equals("NA") || serie.equals("N/D")
					|| serie.equals("ND")) {
				estilo = "text-align: center !important; color: transparent;";
			}
		}
		return estilo;
	}

	public String estiloImagenCodigoActivo() {
		String estilo = "text-align: center !important;";
		if (cod_activo == null) {
			estilo = "text-align: center !important; color: transparent;";
		} else {
			if (cod_activo.length() <= 0 || cod_activo.equals("N/A") || cod_activo.equals("NA")
					|| cod_activo.equals("N/D") || cod_activo.equals("ND")) {
				estilo = "text-align: center !important; color: transparent;";
			}
		}
		return estilo;
	}

}
