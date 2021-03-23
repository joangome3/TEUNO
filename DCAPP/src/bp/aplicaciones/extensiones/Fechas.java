package bp.aplicaciones.extensiones;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

public class Fechas {

	public int obtenerEnteroDelDiaAPartirUnaFecha(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate.getDayOfWeek().getValue();
	}

	public int obtenerEnteroDelMesAPartirUnaFecha(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate.getMonthValue();
	}

	public int obtenerEnteroDeLaHoraAPartirUnaFecha(Date date) {
		int hora = 0;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		hora = c.get(Calendar.HOUR_OF_DAY);
		return hora;
	}

	public int obtenerEnteroDeLosMinutosAPartirUnaFecha(Date date) {
		int minute = 0;
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		minute = c.get(Calendar.MINUTE);
		return minute;
	}

	@SuppressWarnings("deprecation")
	public String obtenerFechaFormateada(Date date, String formato) {
		String fecha = "";
		LocalDateTime localDateTime = null;
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = localDate.getYear();
		localDateTime = LocalDateTime.of(year, date.getMonth() + 1, date.getDate(), date.getHours(), date.getMinutes(),
				date.getSeconds());
		Date d = null;
		d = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		SimpleDateFormat sdf = new SimpleDateFormat(formato);
		fecha = sdf.format(d);
		return fecha;
	}

	public Date obtenerFechaArmada(Date date, int month, int day, int hours, int minutes, int seconds) {
		Date d = null;
		LocalDateTime localDateTime = null;
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		int year = localDate.getYear();
		localDateTime = LocalDateTime.of(year, month + 1, day, hours, minutes, seconds);
		d = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
		return d;
	}

	public Timestamp obtenerTimestampDeDate(Date date) {
		Timestamp timestamp;
		timestamp = new Timestamp(date.getTime());
		return timestamp;
	}

	public Date obtenerFechaDeUnLong(long hora) {
		Date date = null;
		Timestamp timestamp = new Timestamp(hora);
		date = new Date(timestamp.getTime());
		return date;
	}

	public Long obtenerLongDeUnaFecha(Date fecha) {
		long dato = 0;
		Timestamp timestamp = new Timestamp(fecha.getTime());
		dato = timestamp.getTime();
		return dato;
	}

}
