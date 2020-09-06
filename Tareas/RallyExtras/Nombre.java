/**
 * Clase que nos permite generar nombres de manera aleatoria.
 * 
 * @author Gómez de la Torre Heidi Lizbeth
 */

import java.util.Random;

public class Nombre {

    /**
     * Clase privada que construye un arreglo con letras
     */
    private class Letras {

        /* Atributos de la clase Letras. */
        private char[] letras;
        private Random r;

        /**
         * Constructor que recibe un arreglo de carácteres como parametro.
         * 
         * @param l -- Arreglo de carácteres.
         */
        public Letras(char l[]) {
            this.letras = l;
            r = new Random();
        }

        /**
         * Método que devuelve una letra aleatoria del arreglo en cuestion.
         * 
         * @return letra tomada aleatoriamente.
         */
        public char letraRandom() {
            int numeroPos = r.nextInt(letras.length);
            return letras[numeroPos];
        }

    }

    /**
     * Atributos de la clase Nombre
     */
    private Letras vocales;
    private Letras consonantes;

    private Random r;

    /**
     * Constructor por omision de la clase Nombre.
     */
    public Nombre() {
        char v[] = { 'a', 'e', 'i', 'o', 'u' };
        char c[] = { 'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'ñ', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x',
                'y', 'z' };
        vocales = new Letras(v);
        consonantes = new Letras(c);
        r = new Random();
    }

    /**
     * Método que genera un tamaño maximo a partir del número que se pasa como parametro.
     * 
     * @param maximo -- tamaño maximo pasado por el usuario.
     * @return tamaño generado
     */
    private int generarTamanio(int maximo) {
        if (maximo <= 3) {
            return 3;
        }
        int tam = r.nextInt(maximo - 3) + 3;
        return tam;
    }

    /**
     * Método que nos ayuda a crear un nombre, alternando vocales y consonantes.
     * 
     * @param max -- valor máximo de carácteres para el nombre.
     * @return  una cadena que contiene el nombre creado.
     */
    public String crearNombre(int max) {
        String nombre = "";
        max = generarTamanio(max);
        boolean vocal = r.nextBoolean();
        for (int i = 0; i <= max; i++) {
            if (vocal) {
                nombre += vocales.letraRandom();
            } else {
                nombre += consonantes.letraRandom();
            }
            vocal = !vocal;
        }
        String nombreMay = Character.toUpperCase(nombre.charAt(0)) + nombre.substring(1, nombre.length());
        return nombreMay;
    }

}