package bp.aplicaciones.extensiones;

import bp.aplicaciones.enums.EstadosActualizacion;
import bp.aplicaciones.enums.EstadosSeguimiento;
import bp.aplicaciones.enums.EstadosSolicitud;

public class ConsultasEnEnums {

	public static boolean existeEstadoEnSolicitud(String estado) {
		for (EstadosSolicitud e : EstadosSolicitud.values()) {
			if (e.name().equals(estado)) {
				return true;
			}
		}
		return false;
	}

	public static boolean existeEstadoEnSeguimiento(String estado) {
		for (EstadosSeguimiento e : EstadosSeguimiento.values()) {
			if (e.name().equals(estado)) {
				return true;
			}
		}
		return false;
	}

	public static boolean existeEstadoEnActualizacion(String estado) {
		for (EstadosActualizacion e : EstadosActualizacion.values()) {
			if (e.name().equals(estado)) {
				return true;
			}
		}
		return false;
	}

}
