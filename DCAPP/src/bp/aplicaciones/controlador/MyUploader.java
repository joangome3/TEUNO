package bp.aplicaciones.controlador;

import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.zkoss.zk.au.http.AuUploader;

public class MyUploader extends AuUploader {

	protected String handleError(Throwable ex) {
		if (ex instanceof SizeLimitExceededException) {
			return "El Tamaño de la imagen no debe superar los 1 Mb.";
		}
		return super.handleError(ex);
	}
}