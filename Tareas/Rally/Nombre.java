import java.util.Random;

public class Nombre {

    /**
     * Clase privada que construye una arreglo con letras
     */
    private class Letras {

        private char[] letras;
        private Random r;

        public Letras(char l[]) {
            this.letras = l;
            r = new Random();
        }

        /**
         * Método que devuelve una letra aleatoria del arreglo utilizado.
         * 
         * @return letra tomada aleatoriamente.
         */
        public char letraRandom() {
            int numeroPos = r.nextInt(letras.length);
            return letras[numeroPos];
        }

    }

    /**
     * Atributos de la clase Nombre;
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
     * Método que genera un número maximo a partir del que se pasa como parametro.
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
        return nombre;
    }
}