package bp.aplicaciones.controlador;

public class validar_datos {

	static public String soloLetras(String valor) {
		// valor = quitarTildes(valor);
		return valor.toUpperCase().replaceAll("[^a-zA-Z������� ]", "");
	}

	static public String soloNumeros(String valor) {
		return valor.toUpperCase().replaceAll("[^0-9������� ]+", "");
	}

	/**
	 * Numeros, Letras % . , ()
	 * 
	 * @param valor
	 * @return
	 */
	static public String soloLetrasyNumeros(String valor) {
		// valor = quitarTildes(valor);
		// return valor.replaceAll("[^a-zA-Z�������0-9.,_ ]", "");
		return valor.toUpperCase().replaceAll("[^a-zA-Z�������0-9.,_ ]", "");
	}

	static public String general(String valor) {
		return valor.toUpperCase().replaceAll("[^a-zA-Z�������0-9.,_%$ ]", "");
		// return valor.toUpperCase().replaceAll("[^a-zA-Z 0-9]+","");
	}

	static public int validarPassword(String contrasena) {
		String regex = "^(?=.*\\d)(?=.*[\\u0021-\\u002b\\u003c-\\u0040])(?=.*[A-Z])(?=.*[a-z])\\S{8,16}$";
		int nivel_seguridad = 0;
		if (contrasena.matches(regex)) {
			nivel_seguridad = 1;
		} else {
			nivel_seguridad = 0;
		}
		return nivel_seguridad;
	}

	static public int validarCedula(String cedula) {

		int validacion = 0;

		/**
		 * Algoritmo para validar cedulas de Ecuador
		 * 
		 * @Pasos del algoritmo 1.- Se debe validar que tenga 10 numeros 2.- Se extrae
		 *        los dos primero digitos de la izquierda y compruebo que existan las
		 *        regiones 3.- Extraigo el ultimo digito de la cedula 4.- Extraigo Todos
		 *        los pares y los sumo 5.- Extraigo Los impares los multiplico x 2 si el
		 *        numero resultante es mayor a 9 le restamos 9 al resultante 6.-
		 *        Extraigo el primer Digito de la suma (sumaPares + sumaImpares) 7.-
		 *        Conseguimos la decena inmediata del digito extraido del paso 6 (digito
		 *        + 1) * 10 8.- restamos la decena inmediata - suma / si la suma nos
		 *        resulta 10, el decimo digito es cero 9.- Paso 9 Comparamos el digito
		 *        resultante con el ultimo digito de la cedula si son iguales todo OK
		 *        sino existe error.
		 */

		// Preguntamos si la cedula consta de 10 digitos
		if (cedula.length() == 10) {

			// Obtenemos el digito de la region que son los dos primeros digitos
			int digito_region = Integer.valueOf(cedula.substring(0, 2));

			// Pregunto si la region existe ecuador se divide en 24 regiones
			if (digito_region >= 1 && digito_region <= 24) {

				// Extraigo el ultimo digito
				int ultimo_digito = Integer.valueOf(cedula.substring(9, 10));

				// Agrupo todos los pares y los sumo
				int pares = Integer.valueOf(cedula.substring(1, 2)) + Integer.valueOf(cedula.substring(3, 4))
						+ Integer.valueOf(cedula.substring(5, 6)) + Integer.valueOf(cedula.substring(7, 8));

				// Agrupo los impares, los multiplico por un factor de 2, si la resultante es >
				// que 9 le restamos el 9 a la resultante
				int numero1 = Integer.valueOf(cedula.substring(0, 1));
				numero1 = (numero1 * 2);
				if (numero1 > 9) {
					numero1 = (numero1 - 9);
				}

				int numero3 = Integer.valueOf(cedula.substring(2, 3));
				numero3 = (numero3 * 2);
				if (numero3 > 9) {
					numero3 = (numero3 - 9);
				}

				int numero5 = Integer.valueOf(cedula.substring(4, 5));
				numero5 = (numero5 * 2);
				if (numero5 > 9) {
					numero5 = (numero5 - 9);
				}

				int numero7 = Integer.valueOf(cedula.substring(6, 7));
				numero7 = (numero7 * 2);
				if (numero7 > 9) {
					numero7 = (numero7 - 9);
				}

				int numero9 = Integer.valueOf(cedula.substring(8, 9));
				numero9 = (numero9 * 2);
				if (numero9 > 9) {
					numero9 = (numero9 - 9);
				}

				int impares = numero1 + numero3 + numero5 + numero7 + numero9;

				// Suma total
				int suma_total = (pares + impares);

				// extraemos el primero digito
				String primer_digito_suma = String.valueOf(suma_total).substring(0, 1);

				// Obtenemos la decena inmediata
				int decena = (Integer.valueOf((primer_digito_suma)) + 1) * 10;

				// Obtenemos la resta de la decena inmediata - la suma_total esto nos da el
				// digito validador
				int digito_validador = decena - suma_total;

				// Si el digito validador es = a 10 toma el valor de 0
				if (digito_validador == 10)
					digito_validador = 0;

				// Validamos que el digito validador sea igual al de la cedula
				if (digito_validador == ultimo_digito) {
					validacion = 1;
				} else {
					validacion = 0;
				}

			} else {
				// imprimimos en consola si la region no pertenece
				validacion = 0;
			}
		} else {
			// imprimimos en consola si la cedula tiene mas o menos de 10 digitos
			validacion = 0;
		}

		return validacion;
	}

//	public static String quitarTildes(String valor){
//		valor = valor.toUpperCase().replace("�","A" );
//		valor = valor.replace("�","E" );
//		valor = valor.replace("�","I" );
//		valor = valor.replace("�","O" );
//		valor = valor.replace("�","U" );
//		valor = valor.replace("�","N" );
//		return valor;
//	}

	public static void main(String[] args) {
		String var = "� nasdfadspoio � � 9i@3�j�:..;;-2323�_�[";
		System.out.println(var);// replaceAll("[^a-zA-Z ]+","");
		// var = var.toUpperCase(). replaceAll("[^a-zA-Z������]","");
		var = validar_datos.soloLetrasyNumeros(var);
		System.out.println(var);
	}

}
