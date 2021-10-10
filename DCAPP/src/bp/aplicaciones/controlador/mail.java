package bp.aplicaciones.controlador;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import bp.aplicaciones.conexion.conexion;
import bp.aplicaciones.extensiones.Correo;
import bp.aplicaciones.extensiones.Fechas;
import bp.aplicaciones.sibod.DAO.dao_mail;
import bp.aplicaciones.sibod.modelo.modelo_mail_destinatarios;
import bp.aplicaciones.sibod.modelo.modelo_mail_parametros;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

@SuppressWarnings({ "deprecation", "unused" })
public class mail {

	public void enviarMailMasivo(String remitente, String clave, String[] destinatarios, String asunto, String cuerpo,
			String host, String starttls, String port, String auth, String ssl, String debug) {
		try {
			Properties props = new Properties();
			// Nombre del host de correo
			props.setProperty("mail.smtp.host", host);
			// TLS si está disponible
			props.setProperty("mail.smtp.starttls.enable", starttls);
			// Puerto para envio de correos
			props.setProperty("mail.smtp.port", port);
			// Nombre del usuario
			props.setProperty("mail.smtp.user", remitente);
			// Si requiere o no usuario y password para conectarse.
			props.setProperty("mail.smtp.auth", auth);
			// SSL si esta disponible
			props.put("mail.smtp.ssl.trust", ssl);
			Session session = Session.getDefaultInstance(props);
			if (debug.equals("true")) {
				session.setDebug(true);
			} else {
				session.setDebug(false);
			}
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(remitente));
			Address[] dests = new Address[destinatarios.length];// Aqui usamos el arreglo de destinatarios
			for (int i = 0; i < dests.length; i++) {
				dests[i] = new InternetAddress(destinatarios[i]);
			}
			message.addRecipients(Message.RecipientType.TO, dests);// agregamos los destinatarios
			message.setSubject(asunto);
			message.setText(cuerpo, "ISO-8859-1", "html");
			Transport transport = session.getTransport("smtp");
			transport.connect(host, remitente, clave);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();
		} catch (AddressException ex) {
			Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
		} catch (MessagingException ex) {
			Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void enviarMailMasivoConAdjunto(String remitente, String clave, String[] destinatarios, String asunto,
			String cuerpo, String host, String starttls, String port, String auth, String ssl, String debug,
			long id_localidad, String nom_localidad, Date fecha_inicio, Date fecha_fin)
			throws JRException, ClassNotFoundException, SQLException, IOException {
		Fechas fechas = new Fechas();
		try {
			Properties props = new Properties();
			// Nombre del host de correo
			props.setProperty("mail.smtp.host", host);
			// TLS si está disponible
			props.setProperty("mail.smtp.starttls.enable", starttls);
			// Puerto para envio de correos
			props.setProperty("mail.smtp.port", port);
			// Nombre del usuario
			props.setProperty("mail.smtp.user", remitente);
			// Si requiere o no usuario y password para conectarse.
			props.setProperty("mail.smtp.auth", auth);
			// SSL si esta disponible
			props.put("mail.smtp.ssl.trust", ssl);

			Session session = Session.getDefaultInstance(props);
			if (debug.equals("true")) {
				session.setDebug(true);
			} else {
				session.setDebug(false);
			}

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(remitente));
			Address[] dests = new Address[destinatarios.length];// Aqui usamos el arreglo de destinatarios
			for (int i = 0; i < dests.length; i++) {
				dests[i] = new InternetAddress(destinatarios[i]);
			}
			
			InputStream inputStream = getClass().getResourceAsStream("/FO-PV-07 Registro servicios postventa Datacenter.jrxml");

			JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
			Map<String, Object> parametros = new HashMap<String, Object>();
			conexion conexion = new conexion();

			/* INICIO PARAMETROS */
			parametros.put("localidad", String.valueOf(id_localidad));
			parametros.put("p_localidad", nom_localidad);
			parametros.put("p_fechainicio", fecha_inicio);
			parametros.put("p_fechafin", fecha_fin);
			parametros.put("fechainicio", fechas.obtenerFechaFormateada(fecha_inicio, "yyyy-MM-dd HH:mm:ss"));
			parametros.put("fechafin", fechas.obtenerFechaFormateada(fecha_fin, "yyyy-MM-dd HH:mm:ss"));
			/* FIN PARAMETROS */

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, conexion.abrir());

			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			// JasperExportManager.exportReportToPdfStream(jasperPrint, baos);

			JRXlsExporter exporter = new JRXlsExporter();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, baos);
			exporter.exportReport();

			DataSource aAttachment = new ByteArrayDataSource(baos.toByteArray(), "application/xls");

			BodyPart texto = new MimeBodyPart();
			texto.setContent(cuerpo, "text/html");

			BodyPart adjunto = new MimeBodyPart();
			adjunto.setDataHandler(new DataHandler(aAttachment));
			adjunto.setFileName("FO-PV-07 Registro servicios postventa Datacenter.xls");

			MimeMultipart miltiParte = new MimeMultipart();
			miltiParte.addBodyPart(texto);
			miltiParte.addBodyPart(adjunto);

			message.addRecipients(Message.RecipientType.TO, dests);// agregamos los destinatarios
			message.setSubject(asunto);
			message.setText(cuerpo, "ISO-8859-1", "html");
			message.setContent(miltiParte);

			Transport transport = session.getTransport("smtp");
			transport.connect(host, remitente, clave);
			transport.sendMessage(message, message.getAllRecipients());
			
			transport.close();
			inputStream.close();
			baos.close();

		} catch (AddressException ex) {
			Logger.getLogger(mail.class.getName()).log(Level.SEVERE, null, ex);
		} catch (MessagingException ex) {
			Logger.getLogger(mail.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public static void main(String[] args)
			throws ClassNotFoundException, FileNotFoundException, SQLException, IOException, JRException {
		Fechas fechas = new Fechas();
		mail mail = new mail();
		dao_mail dao = new dao_mail();
		String destinatarios[] = null;
		String remitente = "", clave = "", asunto = "", cuerpo = "", host = "", starttls = "", port = "", auth = "",
				ssl = "", debug = "";
		List<modelo_mail_parametros> lista_parametros = new ArrayList<modelo_mail_parametros>();
		List<modelo_mail_destinatarios> lista_destinatarios = new ArrayList<modelo_mail_destinatarios>();
		lista_parametros = dao.obtenerParametros("1", 2);
		if (lista_parametros.size() == 1) {
			remitente = lista_parametros.get(0).getMail_remitente();
			clave = lista_parametros.get(0).getPass_remitente();
			host = lista_parametros.get(0).getSmtp_host();
			starttls = lista_parametros.get(0).getSmtp_starttls();
			port = lista_parametros.get(0).getSmtp_puerto();
			auth = lista_parametros.get(0).getSmtp_auth();
			debug = lista_parametros.get(0).getSmtp_debug();
			ssl = lista_parametros.get(0).getSmtp_trust();
			lista_destinatarios = dao.obtenerDestinatarios(String.valueOf(lista_parametros.get(0).getId_parametro()),
					1);
		} else {
			return;
		}
		if (lista_destinatarios.size() > 0) {
			destinatarios = new String[lista_destinatarios.size()];
			for (int i = 0; i < lista_destinatarios.size(); i++) {
				destinatarios[i] = lista_destinatarios.get(i).getEmail_destinatario();
			}
		} else {
			return;
		}
		asunto = "Este es un correo de prueba (NO TOMAR EN CUENTA) - DCAPP";
		cuerpo = "<p><strong>Estimad@s,</strong></p>\r\n"
				+ "<p>Se adjunta el reporte \"FO-PV-07 Registro servicios postventa Datacenter\" con los registros que se encuentran <strong>abiertos</strong> desde el <strong>" + "2021-08-01" + " hasta el " + "2021-08-31" + "</strong>.</p>";
		mail.enviarMailMasivoConAdjunto(remitente, clave, destinatarios, asunto, cuerpo, host, starttls, port, auth,
				ssl, debug, 1, "DATA CENTER PRINCIPAL", fechas.obtenerFechaArmada(new Date(), 7, 1, 0, 0, 0),
				fechas.obtenerFechaArmada(new Date(), 7, 31, 0, 0, 0));
	}

}
