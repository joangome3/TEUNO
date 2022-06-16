package bp.aplicaciones.mantenimientos.modelo;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

import bp.aplicaciones.equipos.modelo.modelo_gestion_equipo;

@Entity
@Table(name = "nocap_equipo")
public class modelo_equipo {

	@Id
	@Column(name = "id_equipo", nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id_equipo;
	@Column(name = "nom_equipo", length = 500)
	private String nom_equipo;
	@Column(name = "serie")
	private String serie;
	@Column(name = "cod_activo")
	private String cod_activo;
	@Column(name = "cod_producto", length = 100)
	private String cod_producto;
	@Column(name = "peso")
	private float peso;
	@Column(name = "profundidad")
	private float profundidad;
	@Column(name = "btu")
	private float btu;
	@Column(name = "registra_fuente")
	private boolean registra_fuente;
	@Column(name = "etiq_cds")
	private boolean etiq_cds;
	@Column(name = "pein_cds")
	private boolean pein_cds;
	@Column(name = "cone_ats")
	private boolean cone_ats;
	@Column(name = "fec_ingreso")
	private Timestamp fec_ingreso;
	@Column(name = "path_img_frontal")
	private String path_img_frontal;
	@Column(name = "path_img_posterior")
	private String path_img_posterior;
	@Column(name = "est_equipo", length = 5)
	private String est_equipo;
	@Column(name = "usu_ingresa", length = 20)
	private String usu_ingresa;
	@Column(name = "fec_ingresa")
	private Timestamp fec_ingresa;
	@Column(name = "usu_modifica", length = 20)
	private String usu_modifica;
	@Column(name = "fec_modifica")
	private Timestamp fec_modifica;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_tipo_equipo")
	private modelo_tipo_equipo tipo_equipo;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_localidad")
	private modelo_localidad localidad;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_marca")
	private modelo_marca marca;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_modelo")
	private modelo_modelo modelo;

	@OneToMany(mappedBy = "equipo", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_relacion_equipo_tipo_conector> relacion_equipo_tipo_conector;

	@OneToMany(mappedBy = "equipo", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_gestion_equipo> gestion_equipos;

	@OneToMany(mappedBy = "equipo", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_relacion_rack_ur_equipo> relacion_racks_urs_equipos;

	@OneToMany(mappedBy = "equipo", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_etiquetado_poder> etiquetados_poder;

	@OneToMany(mappedBy = "equipo", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_etiquetado_pdu> etiquetados_pdu;

	@OneToMany(mappedBy = "equipo", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH,
			CascadeType.REFRESH })
	private List<modelo_observacion> observaciones;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH })
	@JoinColumn(name = "id_estado")
	private modelo_estado_equipo estado_equipo;

	/**
	 * 
	 */
	public modelo_equipo() {
	}

	/**
	 * @param nom_equipo
	 * @param serie
	 * @param cod_activo
	 * @param cod_producto
	 * @param peso
	 * @param profundidad
	 * @param btu
	 * @param registra_fuente
	 * @param etiq_cds
	 * @param pein_cds
	 * @param cone_ats
	 * @param fec_ingreso
	 * @param path_img_frontal
	 * @param path_img_posterior
	 * @param est_equipo
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_equipo(String nom_equipo, String serie, String cod_activo, String cod_producto, float peso,
			float profundidad, float btu, boolean registra_fuente, boolean etiq_cds, boolean pein_cds, boolean cone_ats,
			Timestamp fec_ingreso, String path_img_frontal, String path_img_posterior, String est_equipo,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.nom_equipo = nom_equipo;
		this.serie = serie;
		this.cod_activo = cod_activo;
		this.cod_producto = cod_producto;
		this.peso = peso;
		this.profundidad = profundidad;
		this.btu = btu;
		this.registra_fuente = registra_fuente;
		this.etiq_cds = etiq_cds;
		this.pein_cds = pein_cds;
		this.cone_ats = cone_ats;
		this.fec_ingreso = fec_ingreso;
		this.path_img_frontal = path_img_frontal;
		this.path_img_posterior = path_img_posterior;
		this.est_equipo = est_equipo;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_equipo
	 * @param nom_equipo
	 * @param serie
	 * @param cod_activo
	 * @param cod_producto
	 * @param peso
	 * @param profundidad
	 * @param btu
	 * @param registra_fuente
	 * @param etiq_cds
	 * @param pein_cds
	 * @param cone_ats
	 * @param fec_ingreso
	 * @param path_img_frontal
	 * @param path_img_posterior
	 * @param est_equipo
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_equipo(long id_equipo, String nom_equipo, String serie, String cod_activo, String cod_producto,
			float peso, float profundidad, float btu, boolean registra_fuente, boolean etiq_cds, boolean pein_cds,
			boolean cone_ats, Timestamp fec_ingreso, String path_img_frontal, String path_img_posterior,
			String est_equipo, String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_equipo = id_equipo;
		this.nom_equipo = nom_equipo;
		this.serie = serie;
		this.cod_activo = cod_activo;
		this.cod_producto = cod_producto;
		this.peso = peso;
		this.profundidad = profundidad;
		this.btu = btu;
		this.registra_fuente = registra_fuente;
		this.etiq_cds = etiq_cds;
		this.pein_cds = pein_cds;
		this.cone_ats = cone_ats;
		this.fec_ingreso = fec_ingreso;
		this.path_img_frontal = path_img_frontal;
		this.path_img_posterior = path_img_posterior;
		this.est_equipo = est_equipo;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the id_equipo
	 */
	public long getId_equipo() {
		return id_equipo;
	}

	/**
	 * @param id_equipo the id_equipo to set
	 */
	public void setId_equipo(long id_equipo) {
		this.id_equipo = id_equipo;
	}

	/**
	 * @return the nom_equipo
	 */
	public String getNom_equipo() {
		return nom_equipo;
	}

	/**
	 * @param nom_equipo the nom_equipo to set
	 */
	public void setNom_equipo(String nom_equipo) {
		this.nom_equipo = nom_equipo;
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
	 * @return the cod_producto
	 */
	public String getCod_producto() {
		return cod_producto;
	}

	/**
	 * @param cod_producto the cod_producto to set
	 */
	public void setCod_producto(String cod_producto) {
		this.cod_producto = cod_producto;
	}

	/**
	 * @return the peso
	 */
	public float getPeso() {
		return peso;
	}

	/**
	 * @param peso the peso to set
	 */
	public void setPeso(float peso) {
		this.peso = peso;
	}

	/**
	 * @return the profundidad
	 */
	public float getProfundidad() {
		return profundidad;
	}

	/**
	 * @param profundidad the profundidad to set
	 */
	public void setProfundidad(float profundidad) {
		this.profundidad = profundidad;
	}

	/**
	 * @return the btu
	 */
	public float getBtu() {
		return btu;
	}

	/**
	 * @param btu the btu to set
	 */
	public void setBtu(float btu) {
		this.btu = btu;
	}

	/**
	 * @return the registra_fuente
	 */
	public boolean isRegistra_fuente() {
		return registra_fuente;
	}

	/**
	 * @param registra_fuente the registra_fuente to set
	 */
	public void setRegistra_fuente(boolean registra_fuente) {
		this.registra_fuente = registra_fuente;
	}

	/**
	 * @return the etiq_cds
	 */
	public boolean isEtiq_cds() {
		return etiq_cds;
	}

	/**
	 * @param etiq_cds the etiq_cds to set
	 */
	public void setEtiq_cds(boolean etiq_cds) {
		this.etiq_cds = etiq_cds;
	}

	/**
	 * @return the pein_cds
	 */
	public boolean isPein_cds() {
		return pein_cds;
	}

	/**
	 * @param pein_cds the pein_cds to set
	 */
	public void setPein_cds(boolean pein_cds) {
		this.pein_cds = pein_cds;
	}

	/**
	 * @return the cone_ats
	 */
	public boolean isCone_ats() {
		return cone_ats;
	}

	/**
	 * @param cone_ats the cone_ats to set
	 */
	public void setCone_ats(boolean cone_ats) {
		this.cone_ats = cone_ats;
	}

	/**
	 * @return the fec_ingreso
	 */
	public Timestamp getFec_ingreso() {
		return fec_ingreso;
	}

	/**
	 * @param fec_ingreso the fec_ingreso to set
	 */
	public void setFec_ingreso(Timestamp fec_ingreso) {
		this.fec_ingreso = fec_ingreso;
	}

	/**
	 * @return the path_img_frontal
	 */
	public String getPath_img_frontal() {
		return path_img_frontal;
	}

	/**
	 * @param path_img_frontal the path_img_frontal to set
	 */
	public void setPath_img_frontal(String path_img_frontal) {
		this.path_img_frontal = path_img_frontal;
	}

	/**
	 * @return the path_img_posterior
	 */
	public String getPath_img_posterior() {
		return path_img_posterior;
	}

	/**
	 * @param path_img_posterior the path_img_posterior to set
	 */
	public void setPath_img_posterior(String path_img_posterior) {
		this.path_img_posterior = path_img_posterior;
	}

	/**
	 * @return the est_equipo
	 */
	public String getEst_equipo() {
		return est_equipo;
	}

	/**
	 * @param est_equipo the est_equipo to set
	 */
	public void setEst_equipo(String est_equipo) {
		this.est_equipo = est_equipo;
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
	 * @return the tipo_equipo
	 */
	public modelo_tipo_equipo getTipo_equipo() {
		return tipo_equipo;
	}

	/**
	 * @param tipo_equipo the tipo_equipo to set
	 */
	public void setTipo_equipo(modelo_tipo_equipo tipo_equipo) {
		this.tipo_equipo = tipo_equipo;
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
	 * @return the relacion_equipo_tipo_conector
	 */
	public List<modelo_relacion_equipo_tipo_conector> getRelacion_equipo_tipo_conector() {
		return relacion_equipo_tipo_conector;
	}

	/**
	 * @param relacion_equipo_tipo_conector the relacion_equipo_tipo_conector to set
	 */
	public void setRelacion_equipo_tipo_conector(
			List<modelo_relacion_equipo_tipo_conector> relacion_equipo_tipo_conector) {
		this.relacion_equipo_tipo_conector = relacion_equipo_tipo_conector;
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
	 * @return the relacion_racks_urs_equipos
	 */
	public List<modelo_relacion_rack_ur_equipo> getRelacion_racks_urs_equipos() {
		return relacion_racks_urs_equipos;
	}

	/**
	 * @param relacion_racks_urs_equipos the relacion_racks_urs_equipos to set
	 */
	public void setRelacion_racks_urs_equipos(List<modelo_relacion_rack_ur_equipo> relacion_racks_urs_equipos) {
		this.relacion_racks_urs_equipos = relacion_racks_urs_equipos;
	}

	/**
	 * @return the etiquetados_poder
	 */
	public List<modelo_etiquetado_poder> getEtiquetados_poder() {
		return etiquetados_poder;
	}

	/**
	 * @param etiquetados_poder the etiquetados_poder to set
	 */
	public void setEtiquetados_poder(List<modelo_etiquetado_poder> etiquetados_poder) {
		this.etiquetados_poder = etiquetados_poder;
	}

	/**
	 * @return the etiquetados_pdu
	 */
	public List<modelo_etiquetado_pdu> getEtiquetados_pdu() {
		return etiquetados_pdu;
	}

	/**
	 * @param etiquetados_pdu the etiquetados_pdu to set
	 */
	public void setEtiquetados_pdu(List<modelo_etiquetado_pdu> etiquetados_pdu) {
		this.etiquetados_pdu = etiquetados_pdu;
	}

	/**
	 * @return the observaciones
	 */
	public List<modelo_observacion> getObservaciones() {
		return observaciones;
	}

	/**
	 * @param observaciones the observaciones to set
	 */
	public void setObservaciones(List<modelo_observacion> observaciones) {
		this.observaciones = observaciones;
	}

	/**
	 * @return the estado_equipo
	 */
	public modelo_estado_equipo getEstado_equipo() {
		return estado_equipo;
	}

	/**
	 * @param estado_equipo the estado_equipo to set
	 */
	public void setEstado_equipo(modelo_estado_equipo estado_equipo) {
		this.estado_equipo = estado_equipo;
	}

	@Override
	public String toString() {
		return "modelo_equipo [id_equipo=" + id_equipo + ", nom_equipo=" + nom_equipo + ", serie=" + serie
				+ ", cod_activo=" + cod_activo + ", cod_producto=" + cod_producto + ", peso=" + peso + ", profundidad="
				+ profundidad + ", btu=" + btu + ", registra_fuente=" + registra_fuente + ", etiq_cds=" + etiq_cds
				+ ", pein_cds=" + pein_cds + ", cone_ats=" + cone_ats + ", fec_ingreso=" + fec_ingreso
				+ ", path_img_frontal=" + path_img_frontal + ", path_img_posterior=" + path_img_posterior
				+ ", est_equipo=" + est_equipo + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa
				+ ", usu_modifica=" + usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

	public String mostrarEstado() {
		String estado = "";
		if (est_equipo.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_equipo.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_equipo.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	/* Estilos en campos */

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_equipo.charAt(0) == 'A') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_equipo.charAt(0) == 'P') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_equipo.charAt(0) == 'I') {
			estilo = "text-align: center !important; font-weight: bold !important; font-style: normal !important; background-color: #FFDDDD;";
		}
		return estilo;
	}

	public String mostrarNombreEquipo() {
		String _nom_equipo = "";
		if (nom_equipo != null) {
			if (nom_equipo.length() > 0 && !nom_equipo.equals("N/A") && !nom_equipo.equals("NA")
					&& !nom_equipo.equals("N/D") && !nom_equipo.equals("ND")) {
				_nom_equipo = getNom_equipo();
			}
		}
		return _nom_equipo;
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

	public String mostrarPeso() {
		String _peso = "";
		if (peso != 0) {
			_peso = String.valueOf(getPeso());
		}
		return _peso;
	}

	public String mostrarProfundidad() {
		String _profundidad = "";
		if (profundidad != 0) {
			_profundidad = String.valueOf(getProfundidad());
		}
		return _profundidad;
	}

	public String mostrarBTU() {
		String _btu = "";
		if (btu != 0) {
			_btu = String.valueOf(getBtu());
		}
		return _btu;
	}

	public String mostrarProducto() {
		String _producto = "";
		if (cod_producto != null) {
			if (cod_producto.length() > 0 && !cod_producto.equals("N/A") && !cod_producto.equals("NA")
					&& !cod_producto.equals("N/D") && !cod_producto.equals("ND")) {
				_producto = getCod_producto();
			}
		}
		return _producto;
	}

	public String mostrarImagenNombreEquipo() {
		String imagen = "";
		if (nom_equipo == null) {
			imagen = "/img/botones/ButtonError.png";
		} else {
			if (nom_equipo.length() <= 0 || nom_equipo.equals("N/A") || nom_equipo.equals("NA")
					|| nom_equipo.equals("N/D") || nom_equipo.equals("ND")) {
				imagen = "/img/botones/ButtonError.png";
			}
		}
		return imagen;
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

	public String mostrarImagenPeso() {
		String imagen = "";
		if (peso == 0) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenProfundidad() {
		String imagen = "";
		if (profundidad == 0) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenBTU() {
		String imagen = "";
		if (btu == 0) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenProducto() {
		String imagen = "";
		if (cod_producto == null) {
			imagen = "/img/botones/ButtonError.png";
		} else {
			if (cod_producto.length() <= 0 || cod_producto.equals("N/A") || cod_producto.equals("NA")
					|| cod_producto.equals("N/D") || cod_producto.equals("ND")) {
				imagen = "/img/botones/ButtonError.png";
			}
		}
		return imagen;
	}

	public String estiloImagenNombreEquipo() {
		String estilo = "text-align: left !important;";
		if (nom_equipo == null) {
			estilo = "text-align: center !important; color: transparent;";
		} else {
			if (nom_equipo.length() <= 0 || nom_equipo.equals("N/A") || nom_equipo.equals("NA")
					|| nom_equipo.equals("N/D") || nom_equipo.equals("ND")) {
				estilo = "text-align: center !important; color: transparent;";
			}
		}
		return estilo;
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

	public String estiloImagenPeso() {
		String estilo = "text-align: center !important;";
		if (peso == 0) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenProfundidad() {
		String estilo = "text-align: center !important;";
		if (profundidad == 0) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenBTU() {
		String estilo = "text-align: center !important;";
		if (btu == 0) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenProducto() {
		String estilo = "text-align: center !important;";
		if (cod_producto == null) {
			estilo = "text-align: center !important; color: transparent;";
		} else {
			if (cod_producto.length() <= 0 || cod_producto.equals("N/A") || cod_producto.equals("NA")
					|| cod_producto.equals("N/D") || cod_producto.equals("ND")) {
				estilo = "text-align: center !important; color: transparent;";
			}
		}
		return estilo;
	}

}
