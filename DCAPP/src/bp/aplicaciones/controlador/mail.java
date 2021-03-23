package bp.aplicaciones.controlador;

import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class mail {

	public void enviarMail(String remitente, String clave, String destinatarios[], String asunto, String cuerpo,
			String host, String starttls, String port, String auth, String ssl, String debug) {
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
		if (debug.equals("S")) {
			session.setDebug(true);
		} else {
			session.setDebug(false);
		}
		try {
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
		} catch (MessagingException me) {
			me.printStackTrace(); // Si se produce un error
		}
	}

}
