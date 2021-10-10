package bp.aplicaciones.extensiones;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JOptionPane;

public class Correo {

	public Correo() {

	}

	private void enviarCorreo() {

		try {
			Properties props = new Properties();
			props.setProperty("mail.smtp.host", "smtp.office365.com");
			props.setProperty("mail.smtp.starttls.enable", "true");
			props.setProperty("mail.smtp.port", "587");
			props.setProperty("mail.smtp.auth", "true");

			Session session = Session.getDefaultInstance(props);

			String correoRemitente = "jose.gomez@teuno.com";
			String passwordRemitente = "Manhatan2005";
			String correoReceptor = "joangome2005@gmail.com";
			String asunto = "Mi primero correo en Java";
			String mensaje = "Hola<br>Este es el contenido de mi primer correo desde <b>java</b><br><br>Por <b>Códigos de Programación</b>";

			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(correoRemitente));

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(correoReceptor));
			message.setSubject(asunto);
			message.setText(mensaje, "ISO-8859-1", "html");

			Transport t = session.getTransport("smtp");
			t.connect(correoRemitente, passwordRemitente);
			t.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
			t.close();

			JOptionPane.showMessageDialog(null, "Correo Electronico Enviado");

		} catch (AddressException ex) {
			Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
		} catch (MessagingException ex) {
			Logger.getLogger(Correo.class.getName()).log(Level.SEVERE, null, ex);
		}

	}

	public static void main(String[] args) {
		Correo correo = new Correo();
		correo.enviarCorreo();
	}

}
