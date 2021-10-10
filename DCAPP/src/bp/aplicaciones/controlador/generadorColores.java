package bp.aplicaciones.controlador;

import java.awt.Color;

public class generadorColores {

	public String generarColor() {
		String[] letters = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F" };
		String color = "#";
		for (int i = 0; i < 6; i++) {
			color += letters[(int) Math.round(Math.random() * 15)];
		}
		return color;
	}

	public String generarColoresSolidos() {
		float hue = (float) Math.random();
		int rgb = Color.HSBtoRGB(hue,(float) 0.5, (float) 0.5);
		Color color = new Color(rgb);
		String hex = Integer.toHexString(color.getRGB() & 0xffffff);
		if (hex.length() < 6) {
			hex = "0" + hex;
		}
		hex = "#" + hex;
		return hex;
	}
	
	public String generarColoresCalidos() {
		float hue = (float) Math.random();
		int rgb = Color.HSBtoRGB(hue,(float) 0.5, (float) 0.5);
		Color color = new Color(rgb).brighter();
		String hex = Integer.toHexString(color.getRGB() & 0xffffff);
		if (hex.length() < 6) {
			hex = "0" + hex;
		}
		hex = "#" + hex;
		return hex;
	}

}
