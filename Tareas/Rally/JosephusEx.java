
/**
 * Clase que nos resuelve el problema de Josephus, con
 * nombres de soldados generados de Manera aleatoria.
 * 
 * @author Gómez de la Torre Heidi Lizbeth
 */
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Random;
import java.lang.Integer;

public class JosephusEx {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        System.out.println("\t\t Problema de Josephus");
        System.out.println(" ");
        System.out.print("Por favor, ingrese el número de soldados: ");
        int aux = in.nextInt();

        ListaCircular<Integer> soldados = new ListaCircular();
        Iterator itSol = soldados.iterator();
        int i = 0;
        while (i++ < aux) {
            soldados.agrega(i);
        }
        System.out.println(" ");
        System.out.println("Los soldados de Josephus se sentaban de la siguiente forma: ");
        System.out.println(soldados.toString());

        Random r = new Random();
        int n = r.nextInt(aux) + 2;

        System.out.println(" ");
        System.out.println(
                "Cada " + n + " lugares, el siguiente soldado muere. Ahora veremos el orden en que murieron: ");
        ListaCircular<Integer> muertos = new ListaCircular();
        Iterator itSol2 = soldados.iterator();
        Iterator itMue = muertos.iterator();
        int j = 0;
        int ultimo = 0;
        while (itSol2.hasNext() && j++ < aux) {
            itSol2.next();
            if (j == n - 1) {
                ultimo = (Integer) itSol2.next();
                muertos.agrega(ultimo);
                itSol2.remove();
                j = 0;
            }
        }
        System.out.println(muertos.toString());
        System.out.println(" ");
        System.out.println("Por lo tanto, Josephus debió colocarse en la posicion: " + ultimo);

        Nombre nombre = new Nombre();
        System.out.println(nombre.crearNombre(5));

    }

    private class Nombre extends Lista {

        /**
         * Clase privada que construye una lista con letras;
         */
        private class Letras extends Lista {

            private Lista<Character> letras = new Lista<>();
            private Random r;

            public Letras() {
                super();
            }

            public char letraRandom() {
                int numeroPos = r.nextInt(letras.longitud());
                return letras.getElemento(numeroPos);
            }

        }

        /**
         * Atributos de la clase Nombre;
         */
        private Letras vocales = new Letras();
        private Letras consonantes = new Letras();

        private Random r;

        /**
         * Constructor por omision de la clase Nombre.
         */
        public Nombre() {
            char v[] = { 'a', 'e', 'i', 'o', 'u' };
            char c[] = { 'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'ñ', 'p', 'q', 'r', 's', 't', 'v', 'w',
                    'x', 'y', 'z' };
            int i, j;
            for (i = 0; i < v.length; i++) {
                vocales.agregar(v[i]);
            }
            for (j = 0; j < v.length; j++) {
                consonantes.agregar(v[j]);
            }
            r = new Random();
        }

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
}