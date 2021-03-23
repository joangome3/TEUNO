package bp.aplicaciones.mensajes;

public class Error {

	private String mensaje_error_1 = "Mensaje de error:";
	private String mensaje_error_2 = "Error al cargar la información ";
	private String mensaje_error_3 = "Existen inconsistencias con los permisos del perfil asignado al usuario. ¡Consulte al administrador del sistema!";
	private String mensaje_error_4 = "Error al guardar el registro";

	public Error() {

	}

	public String getMensaje_error_1() {
		return mensaje_error_1;
	}

	public String getMensaje_error_2() {
		return mensaje_error_2;
	}
	
	public String getMensaje_error_3() {
		return mensaje_error_3;
	}
	
	public String getMensaje_error_4() {
		return mensaje_error_4;
	}

}
