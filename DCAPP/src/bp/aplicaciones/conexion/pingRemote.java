package bp.aplicaciones.conexion;

import java.io.IOException;
import java.net.InetAddress;

public class pingRemote {

	public static void main(String[] args) {
		InetAddress ping;
		String ip = "10.207.55.29"; // Ip de la máquina remota
		try {
			ping = InetAddress.getByName(ip);
			if (ping.isReachable(5000)) {
				System.out.println(ip + " - Responde!");
			} else {
				System.out.println(ip + " - No responde!");
			}
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	public boolean validarConexionEquipo(String ip) {
		InetAddress ping;
		boolean conectado = false;
		try {
			ping = InetAddress.getByName(ip);
			if (ping.isReachable(5000)) {
				conectado = true;
			} else {
				conectado = false;
			}
		} catch (IOException ex) {
			System.out.println(ex);
		}
		return conectado;
	}
}
