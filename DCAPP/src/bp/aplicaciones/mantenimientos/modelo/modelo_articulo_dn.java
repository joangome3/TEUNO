package bp.aplicaciones.mantenimientos.modelo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;

import bp.aplicaciones.extensiones.ConsultasABaseDeDatos;
import bp.aplicaciones.extensiones.Fechas;

public class modelo_articulo_dn {

	Fechas fechas = new Fechas();

	private long id_articulo;
	private String cod_articulo;
	private String des_articulo;
	private String nom_empresa;
	private long id_categoria;
	private String nom_categoria;
	private long id_ubicacion;
	private String nom_ubicacion;
	private int pos_ubicacion;
	private int sto_articulo;
	private Blob img_articulo;
	private long id_localidad;
	private String nom_localidad;
	private long id_capacidad;
	private String nom_capacidad;
	private String si_ing_fec_inicio_fin;
	private String es_fecha;
	private long id_fec_respaldo;
	private String nom_fec_respaldo;
	private Timestamp fec_inicio;
	private Timestamp fec_fin;
	private long id_tip_respaldo;
	private String nom_tip_respaldo;
	private String id_contenedor;
	private long hora_llegada_cutodia;
	private long hora_salida_custodia;
	private String remesa_ingreso_custodia;
	private String remesa_salida_custodia;
	private String est_articulo;
	private String usu_ingresa;
	private Timestamp fec_ingresa;
	private String usu_modifica;
	private Timestamp fec_modifica;

	public modelo_articulo_dn() {
		super();
	}

	public modelo_articulo_dn clone() {
		modelo_articulo_dn articulo_dn = new modelo_articulo_dn(this.id_articulo, this.est_articulo, this.usu_modifica,
				this.fec_modifica);
		return articulo_dn;
	}

	public modelo_articulo_dn clone1() {
		modelo_articulo_dn articulo_dn = new modelo_articulo_dn(this.id_articulo, this.cod_articulo, this.des_articulo,
				this.id_categoria, this.id_ubicacion, this.pos_ubicacion, this.sto_articulo, this.img_articulo,
				this.id_localidad, this.id_capacidad, this.si_ing_fec_inicio_fin, this.es_fecha, this.id_fec_respaldo,
				this.fec_inicio, this.fec_fin, this.id_tip_respaldo, this.id_contenedor, this.est_articulo,
				this.usu_ingresa, this.fec_ingresa, this.usu_modifica, this.fec_modifica);
		return articulo_dn;
	}

	/**
	 * @param id_articulo
	 * @param est_articulo
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_articulo_dn(long id_articulo, String est_articulo, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_articulo = id_articulo;
		this.est_articulo = est_articulo;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_articulo
	 * @param cod_articulo
	 * @param des_articulo
	 * @param nom_empresa
	 * @param id_categoria
	 * @param nom_categoria
	 * @param id_ubicacion
	 * @param pos_ubicacion
	 * @param nom_ubicacion
	 * @param sto_articulo
	 * @param img_articulo
	 * @param id_localidad
	 * @param nom_localidad
	 * @param id_capacidad
	 * @param nom_capacidad
	 * @param si_ing_fec_inicio_fin
	 * @param es_fecha
	 * @param id_fec_respaldo
	 * @param nom_fec_respaldo
	 * @param fec_inicio
	 * @param fec_fin
	 * @param id_tip_respaldo
	 * @param nom_tip_respaldo
	 * @param id_contenedor
	 * @param hora_llegada_cutodia
	 * @param hora_salida_custodia
	 * @param remesa_ingreso_custodia
	 * @param remesa_salida_custodia
	 * @param est_articulo
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_articulo_dn(long id_articulo, String cod_articulo, String des_articulo, String nom_empresa,
			long id_categoria, String nom_categoria, long id_ubicacion, int pos_ubicacion, String nom_ubicacion,
			int sto_articulo, Blob img_articulo, long id_localidad, String nom_localidad, long id_capacidad,
			String nom_capacidad, String si_ing_fec_inicio_fin, String es_fecha, long id_fec_respaldo,
			String nom_fec_respaldo, Timestamp fec_inicio, Timestamp fec_fin, long id_tip_respaldo,
			String nom_tip_respaldo, String id_contenedor, long hora_llegada_cutodia, long hora_salida_custodia,
			String remesa_ingreso_custodia, String remesa_salida_custodia, String est_articulo, String usu_ingresa,
			Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_articulo = id_articulo;
		this.cod_articulo = cod_articulo;
		this.des_articulo = des_articulo;
		this.nom_empresa = nom_empresa;
		this.id_categoria = id_categoria;
		this.nom_categoria = nom_categoria;
		this.id_ubicacion = id_ubicacion;
		this.pos_ubicacion = pos_ubicacion;
		this.nom_ubicacion = nom_ubicacion;
		this.sto_articulo = sto_articulo;
		this.img_articulo = img_articulo;
		this.id_localidad = id_localidad;
		this.nom_localidad = nom_localidad;
		this.id_capacidad = id_capacidad;
		this.nom_capacidad = nom_capacidad;
		this.si_ing_fec_inicio_fin = si_ing_fec_inicio_fin;
		this.es_fecha = es_fecha;
		this.id_fec_respaldo = id_fec_respaldo;
		this.nom_fec_respaldo = nom_fec_respaldo;
		this.fec_inicio = fec_inicio;
		this.fec_fin = fec_fin;
		this.id_tip_respaldo = id_tip_respaldo;
		this.nom_tip_respaldo = nom_tip_respaldo;
		this.id_contenedor = id_contenedor;
		this.hora_llegada_cutodia = hora_llegada_cutodia;
		this.hora_salida_custodia = hora_salida_custodia;
		this.remesa_ingreso_custodia = remesa_ingreso_custodia;
		this.remesa_salida_custodia = remesa_salida_custodia;
		this.est_articulo = est_articulo;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @param id_articulo
	 * @param cod_articulo
	 * @param des_articulo
	 * @param id_categoria
	 * @param id_ubicacion
	 * @param pos_ubicacion
	 * @param sto_articulo
	 * @param img_articulo
	 * @param id_localidad
	 * @param id_capacidad
	 * @param si_ing_fec_inicio_fin
	 * @param es_fecha
	 * @param id_fec_respaldo
	 * @param fec_inicio
	 * @param fec_fin
	 * @param id_tip_respaldo
	 * @param id_contenedor
	 * @param est_articulo
	 * @param usu_ingresa
	 * @param fec_ingresa
	 * @param usu_modifica
	 * @param fec_modifica
	 */
	public modelo_articulo_dn(long id_articulo, String cod_articulo, String des_articulo, long id_categoria,
			long id_ubicacion, int pos_ubicacion, int sto_articulo, Blob img_articulo, long id_localidad,
			long id_capacidad, String si_ing_fec_inicio_fin, String es_fecha, long id_fec_respaldo,
			Timestamp fec_inicio, Timestamp fec_fin, long id_tip_respaldo, String id_contenedor, String est_articulo,
			String usu_ingresa, Timestamp fec_ingresa, String usu_modifica, Timestamp fec_modifica) {
		super();
		this.id_articulo = id_articulo;
		this.cod_articulo = cod_articulo;
		this.des_articulo = des_articulo;
		this.id_categoria = id_categoria;
		this.id_ubicacion = id_ubicacion;
		this.pos_ubicacion = pos_ubicacion;
		this.sto_articulo = sto_articulo;
		this.img_articulo = img_articulo;
		this.id_localidad = id_localidad;
		this.id_capacidad = id_capacidad;
		this.si_ing_fec_inicio_fin = si_ing_fec_inicio_fin;
		this.es_fecha = es_fecha;
		this.id_fec_respaldo = id_fec_respaldo;
		this.fec_inicio = fec_inicio;
		this.fec_fin = fec_fin;
		this.id_tip_respaldo = id_tip_respaldo;
		this.id_contenedor = id_contenedor;
		this.est_articulo = est_articulo;
		this.usu_ingresa = usu_ingresa;
		this.fec_ingresa = fec_ingresa;
		this.usu_modifica = usu_modifica;
		this.fec_modifica = fec_modifica;
	}

	/**
	 * @return the nom_empresa
	 */
	public String getNom_empresa() {
		return nom_empresa;
	}

	/**
	 * @param nom_empresa the nom_empresa to set
	 */
	public void setNom_empresa(String nom_empresa) {
		this.nom_empresa = nom_empresa;
	}

	/**
	 * @return the id_articulo
	 */
	public long getId_articulo() {
		return id_articulo;
	}

	/**
	 * @param id_articulo the id_articulo to set
	 */
	public void setId_articulo(long id_articulo) {
		this.id_articulo = id_articulo;
	}

	/**
	 * @return the cod_articulo
	 */
	public String getCod_articulo() {
		return cod_articulo;
	}

	/**
	 * @param cod_articulo the cod_articulo to set
	 */
	public void setCod_articulo(String cod_articulo) {
		this.cod_articulo = cod_articulo;
	}

	/**
	 * @return the des_articulo
	 */
	public String getDes_articulo() {
		return des_articulo;
	}

	/**
	 * @param des_articulo the des_articulo to set
	 */
	public void setDes_articulo(String des_articulo) {
		this.des_articulo = des_articulo;
	}

	/**
	 * @return the id_categoria
	 */
	public long getId_categoria() {
		return id_categoria;
	}

	/**
	 * @param id_categoria the id_categoria to set
	 */
	public void setId_categoria(long id_categoria) {
		this.id_categoria = id_categoria;
	}

	/**
	 * @return the nom_categoria
	 */
	public String getNom_categoria() {
		return nom_categoria;
	}

	/**
	 * @param nom_categoria the nom_categoria to set
	 */
	public void setNom_categoria(String nom_categoria) {
		this.nom_categoria = nom_categoria;
	}

	/**
	 * @return the id_ubicacion
	 */
	public long getId_ubicacion() {
		return id_ubicacion;
	}

	/**
	 * @param id_ubicacion the id_ubicacion to set
	 */
	public void setId_ubicacion(long id_ubicacion) {
		this.id_ubicacion = id_ubicacion;
	}

	public int getPos_ubicacion() {
		return pos_ubicacion;
	}

	public void setPos_ubicacion(int pos_ubicacion) {
		this.pos_ubicacion = pos_ubicacion;
	}

	/**
	 * @return the nom_ubicacion
	 */
	public String getNom_ubicacion() {
		return nom_ubicacion;
	}

	/**
	 * @param nom_ubicacion the nom_ubicacion to set
	 */
	public void setNom_ubicacion(String nom_ubicacion) {
		this.nom_ubicacion = nom_ubicacion;
	}

	/**
	 * @return the sto_articulo
	 */
	public int getSto_articulo() {
		return sto_articulo;
	}

	/**
	 * @param sto_articulo the sto_articulo to set
	 */
	public void setSto_articulo(int sto_articulo) {
		this.sto_articulo = sto_articulo;
	}

	/**
	 * @return the img_articulo
	 */
	public Blob getImg_articulo() {
		return img_articulo;
	}

	/**
	 * @param img_articulo the img_articulo to set
	 */
	public void setImg_articulo(Blob img_articulo) {
		this.img_articulo = img_articulo;
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
	 * @return the nom_localidad
	 */
	public String getNom_localidad() {
		return nom_localidad;
	}

	/**
	 * @param nom_localidad the nom_localidad to set
	 */
	public void setNom_localidad(String nom_localidad) {
		this.nom_localidad = nom_localidad;
	}

	/**
	 * @return the id_capacidad
	 */
	public long getId_capacidad() {
		return id_capacidad;
	}

	/**
	 * @param id_capacidad the id_capacidad to set
	 */
	public void setId_capacidad(long id_capacidad) {
		this.id_capacidad = id_capacidad;
	}

	/**
	 * @return the nom_capacidad
	 */
	public String getNom_capacidad() {
		return nom_capacidad;
	}

	/**
	 * @param nom_capacidad the nom_capacidad to set
	 */
	public void setNom_capacidad(String nom_capacidad) {
		this.nom_capacidad = nom_capacidad;
	}

	/**
	 * @return the si_ing_fec_inicio_fin
	 */
	public String getSi_ing_fec_inicio_fin() {
		return si_ing_fec_inicio_fin;
	}

	/**
	 * @param si_ing_fec_inicio_fin the si_ing_fec_inicio_fin to set
	 */
	public void setSi_ing_fec_inicio_fin(String si_ing_fec_inicio_fin) {
		this.si_ing_fec_inicio_fin = si_ing_fec_inicio_fin;
	}

	/**
	 * @return the es_fecha
	 */
	public String getEs_fecha() {
		return es_fecha;
	}

	/**
	 * @param es_fecha the es_fecha to set
	 */
	public void setEs_fecha(String es_fecha) {
		this.es_fecha = es_fecha;
	}

	/**
	 * @return the id_fec_respaldo
	 */
	public long getId_fec_respaldo() {
		return id_fec_respaldo;
	}

	/**
	 * @param id_fec_respaldo the id_fec_respaldo to set
	 */
	public void setId_fec_respaldo(long id_fec_respaldo) {
		this.id_fec_respaldo = id_fec_respaldo;
	}

	/**
	 * @return the nom_fec_respaldo
	 */
	public String getNom_fec_respaldo() {
		return nom_fec_respaldo;
	}

	/**
	 * @param nom_fec_respaldo the nom_fec_respaldo to set
	 */
	public void setNom_fec_respaldo(String nom_fec_respaldo) {
		this.nom_fec_respaldo = nom_fec_respaldo;
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
	 * @return the id_tip_respaldo
	 */
	public long getId_tip_respaldo() {
		return id_tip_respaldo;
	}

	/**
	 * @return the nom_tip_respaldo
	 */
	public String getNom_tip_respaldo() {
		return nom_tip_respaldo;
	}

	/**
	 * @param nom_tip_respaldo the nom_tip_respaldo to set
	 */
	public void setNom_tip_respaldo(String nom_tip_respaldo) {
		this.nom_tip_respaldo = nom_tip_respaldo;
	}

	/**
	 * @param id_tip_respaldo the id_tip_respaldo to set
	 */
	public void setId_tip_respaldo(long id_tip_respaldo) {
		this.id_tip_respaldo = id_tip_respaldo;
	}

	/**
	 * @return the id_contenedor
	 */
	public String getId_contenedor() {
		return id_contenedor;
	}

	/**
	 * @param id_contenedor the id_contenedor to set
	 */
	public void setId_contenedor(String id_contenedor) {
		this.id_contenedor = id_contenedor;
	}

	/**
	 * @return the hora_llegada_cutodia
	 */
	public long getHora_llegada_cutodia() {
		return hora_llegada_cutodia;
	}

	/**
	 * @param hora_llegada_cutodia the hora_llegada_cutodia to set
	 */
	public void setHora_llegada_cutodia(long hora_llegada_cutodia) {
		this.hora_llegada_cutodia = hora_llegada_cutodia;
	}

	/**
	 * @return the hora_salida_custodia
	 */
	public long getHora_salida_custodia() {
		return hora_salida_custodia;
	}

	/**
	 * @param hora_salida_custodia the hora_salida_custodia to set
	 */
	public void setHora_salida_custodia(long hora_salida_custodia) {
		this.hora_salida_custodia = hora_salida_custodia;
	}

	/**
	 * @return the remesa_ingreso_custodia
	 */
	public String getRemesa_ingreso_custodia() {
		return remesa_ingreso_custodia;
	}

	/**
	 * @param remesa_ingreso_custodia the remesa_ingreso_custodia to set
	 */
	public void setRemesa_ingreso_custodia(String remesa_ingreso_custodia) {
		this.remesa_ingreso_custodia = remesa_ingreso_custodia;
	}

	/**
	 * @return the remesa_salida_custodia
	 */
	public String getRemesa_salida_custodia() {
		return remesa_salida_custodia;
	}

	/**
	 * @param remesa_salida_custodia the remesa_salida_custodia to set
	 */
	public void setRemesa_salida_custodia(String remesa_salida_custodia) {
		this.remesa_salida_custodia = remesa_salida_custodia;
	}

	/**
	 * @return the est_articulo
	 */
	public String getEst_articulo() {
		return est_articulo;
	}

	/**
	 * @param est_articulo the est_articulo to set
	 */
	public void setEst_articulo(String est_articulo) {
		this.est_articulo = est_articulo;
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
		return "modelo_articulo_dn [fechas=" + fechas + ", id_articulo=" + id_articulo + ", cod_articulo="
				+ cod_articulo + ", des_articulo=" + des_articulo + ", nom_empresa=" + nom_empresa + ", id_categoria="
				+ id_categoria + ", nom_categoria=" + nom_categoria + ", id_ubicacion=" + id_ubicacion
				+ ", nom_ubicacion=" + nom_ubicacion + ", pos_ubicacion=" + pos_ubicacion + ", sto_articulo="
				+ sto_articulo + ", img_articulo=" + img_articulo + ", id_localidad=" + id_localidad
				+ ", nom_localidad=" + nom_localidad + ", id_capacidad=" + id_capacidad + ", nom_capacidad="
				+ nom_capacidad + ", si_ing_fec_inicio_fin=" + si_ing_fec_inicio_fin + ", es_fecha=" + es_fecha
				+ ", id_fec_respaldo=" + id_fec_respaldo + ", nom_fec_respaldo=" + nom_fec_respaldo + ", fec_inicio="
				+ fec_inicio + ", fec_fin=" + fec_fin + ", id_tip_respaldo=" + id_tip_respaldo + ", nom_tip_respaldo="
				+ nom_tip_respaldo + ", id_contenedor=" + id_contenedor + ", hora_llegada_cutodia="
				+ hora_llegada_cutodia + ", hora_salida_custodia=" + hora_salida_custodia + ", remesa_ingreso_custodia="
				+ remesa_ingreso_custodia + ", remesa_salida_custodia=" + remesa_salida_custodia + ", est_articulo="
				+ est_articulo + ", usu_ingresa=" + usu_ingresa + ", fec_ingresa=" + fec_ingresa + ", usu_modifica="
				+ usu_modifica + ", fec_modifica=" + fec_modifica + "]";
	}

	public String mostrarEstado() {
		String estado = "";
		if (est_articulo.charAt(0) == 'A') {
			estado = "ACTIVO";
		}
		if (est_articulo.charAt(0) == 'P') {
			estado = "PENDIENTE APROBAR CREACIÓN";
		}
		if (est_articulo.charAt(0) == 'I') {
			estado = "INACTIVO";
		}
		return estado;
	}

	public String nombreCompletoDeArticulo() {
		return getCod_articulo() + " - " + getDes_articulo();
	}

	public String mostrarCapacidad() {
		String capacidad = "";
		if (nom_capacidad != null) {
			if (!nom_capacidad.equals("")) {
				capacidad = getNom_capacidad();
			}
		}
		return capacidad;
	}

	public String mostrarFechaInicio() {
		String fecha = "";
		if (si_ing_fec_inicio_fin.equals("S")) {
			if (es_fecha.equals("S")) {
				if (fec_inicio != null) {
					fecha = fechas.obtenerFechaFormateada(getFec_inicio(), "dd/MM/yyyy");
				}
			} else {
				fecha = getNom_fec_respaldo();
			}
		}
		return fecha;
	}

	public String mostrarFechaFin() {
		String fecha = "";
		if (fec_fin != null) {
			fecha = fechas.obtenerFechaFormateada(getFec_fin(), "dd/MM/yyyy");
		}
		return fecha;
	}

	public String mostrarTipoDeRespaldo() {
		String tipoDeRespaldo = "";
		if (nom_tip_respaldo != null) {
			if (!nom_tip_respaldo.equals("")) {
				tipoDeRespaldo = getNom_tip_respaldo();
			}
		}
		return tipoDeRespaldo;
	}

	public String mostrarIdContenedor() {
		String idContenedor = "";
		if (id_contenedor != null) {
			if (!id_contenedor.equals("")) {
				idContenedor = getId_contenedor();
			}
		}
		return idContenedor;
	}

	/* Se sobreescribe el metodo String para mostrar imagenes en campos */

	public String mostrarImagenCapacidad() {
		String imagen = "";
		if (nom_capacidad == null) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenFechaInicio() {
		String imagen = "";
		if (si_ing_fec_inicio_fin.equals("S")) {
			if (es_fecha.equals("S")) {
				if (fec_inicio == null) {
					imagen = "/img/botones/ButtonError.png";
				}
			} else {
				if (nom_fec_respaldo == null) {
					imagen = "/img/botones/ButtonError.png";
				}
			}
		} else {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenFechaFin() {
		String imagen = "";
		if (fec_fin == null) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenTipoDeRespaldo() {
		String imagen = "";
		if (nom_tip_respaldo == null) {
			imagen = "/img/botones/ButtonError.png";
		}
		return imagen;
	}

	public String mostrarImagenIdContenedor() {
		String imagen = "";
		if (id_contenedor == null) {
			imagen = "/img/botones/ButtonError.png";
		} else {
			if (id_contenedor.length() <= 0) {
				imagen = "/img/botones/ButtonError.png";
			}
		}
		return imagen;
	}

	/* Se sobreescribe el metodo String para personalizar estilos en campos */

	public String estiloImagenTipoDeRespaldo() {
		String estilo = "";
		if (nom_tip_respaldo == null) {
			estilo = "text-align: center !important; color: transparent;";
		}
		return estilo;
	}

	public String estiloImagenIdContenedor() {
		String estilo = "";
		if (id_contenedor == null) {
			estilo = "text-align: center !important; color: transparent;";
		} else {
			if (id_contenedor.length() <= 0) {
				estilo = "text-align: center !important; color: transparent;";
			}
		}
		return estilo;
	}

	public String estiloMostrarEstado() {
		String estilo = "";
		if (est_articulo.charAt(0) == 'A') {
			estilo = "font-weight: bold !important; font-style: normal !important; background-color: #CCFFE1;";
		}
		if (est_articulo.charAt(0) == 'P') {
			estilo = "font-weight: bold !important; font-style: normal !important; background-color: #FAF8D5;";
		}
		if (est_articulo.charAt(0) == 'I') {
			estilo = "font-weight: bold !important; font-style: normal !important; background-color: #FFDDDD;";
		}
		return estilo;
	}

	public String estiloMostrarCategoria() {
		String estilo = "";
		if (id_categoria == 1) {
			estilo = "font-weight: bold !important; font-style: normal !important; background-color: #d6ffba; text-align: center !important;";
		} else if (id_categoria == 2) {
			estilo = "font-weight: bold !important; font-style: normal !important; background-color: #d4efed; text-align: center !important;";
		} else {
			estilo = "font-weight: bold !important; font-style: normal !important; background-color: #ffbd62; text-align: center !important;";
		}
		return estilo;
	}

	public String mostrarImagenEstadoSolicitud()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		String imagen = "";
		if (validarSiExisteSolicitudCreada() == true) {
			imagen = "/img/botones/ButtonEye.png";
		} else if (validarSiExisteSolicitudPendienteEjecucionOActualizacion()) {
			imagen = "/img/botones/ButtonOK.png";
		} else {
			imagen = "/img/botones/ButtonRequire.png";
		}
		return imagen;
	}

	public boolean validarSiExisteSolicitudCreada()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
		boolean existe_solicitud_creada = false;
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 16, id_articulo, 7);
		if (solicitud != null) {
			String estado = solicitud.getEst_solicitud();
			if (estado != null) {
				if (estado.equals("P") || estado.equals("R")) {
					existe_solicitud_creada = true;
				}
			}
		}
		return existe_solicitud_creada;
	}

	public boolean validarSiExisteSolicitudCerrada()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
		boolean existe_solicitud_cerrada = false;
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 16, id_articulo, 9);
		if (solicitud != null) {
			String estado = solicitud.getEst_solicitud();
			if (estado != null) {
				if (estado.equals("E") || estado.equals("N") || estado.equals("A")) {
					existe_solicitud_cerrada = true;
				}
			}
		}
		return existe_solicitud_cerrada;
	}

	public boolean validarSiExisteSolicitudPendienteEjecucionOActualizacion()
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException {
		ConsultasABaseDeDatos consultasABaseDeDatos = new ConsultasABaseDeDatos();
		boolean existe_solicitud_pendiente = false;
		modelo_solicitud solicitud = new modelo_solicitud();
		solicitud = consultasABaseDeDatos.obtenerSolicitudesxEstado("", 16, id_articulo, 7);
		if (solicitud != null) {
			String estado = solicitud.getEst_solicitud();
			if (estado != null) {
				if (estado.equals("S") || estado.equals("T")) {
					existe_solicitud_pendiente = true;
				}
			}
		}
		return existe_solicitud_pendiente;
	}

}
