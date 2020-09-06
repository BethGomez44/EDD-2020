/**
 * Clase que nos resuelve el problema de Josephus, con
 * nombres de soldados generados de manera aleatoria.
 * 
 * @author Gómez de la Torre Heidi Lizbeth
 */
import java.io.InputStreamReader;
import java.io.FileReader;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Random;
import java.lang.Integer;

public class JosephusEx {

    public static void main(String[] args) {

        /* Atributos utilizados en nuestra clase */
        Scanner in = new Scanner(System.in);
        ListaCircular<String> nombreSol = new ListaCircular();
        ListaCircular<String> muertos = new ListaCircular();
        Nombre nombre = new Nombre();
        Random r = new Random();
        int i = 0, aux = 0, n = 0, j = 0;

        /* Resolución del punto extra del problema de Josephus */
        System.out.println("\t\t\t Problema de Josephus (Con nombres al azar) ");
        System.out.println(" ");
        System.out.print("Por favor, ingrese el número de soldados: ");
        aux = in.nextInt();

        while (i++ < aux) {
            nombreSol.agrega(nombre.crearNombre(8));
        }
        System.out.println(" ");
        System.out.println("Los soldados se sentaban de la siguiente forma: ");
        System.out.println(nombreSol.toString());

        n = r.nextInt(aux) + 2;

        System.out.println(" ");
        System.out.println(
                "Cada " + n + " lugares, el siguiente soldado muere. Ahora veremos el orden en que murieron: ");
        Iterator itNom = nombreSol.iterator();
        String ultimo = new String();
        while (itNom.hasNext() && j++ < aux) {
            itNom.next();
            if (j == n - 1) {
                ultimo = (String) itNom.next();
                muertos.agrega(ultimo);
                itNom.remove();
                j = 0;
            }
        }
        System.out.println(muertos.toString());
        System.out.println(" ");
        System.out.println("Por lo tanto, el último soldado que murió fue: " + ultimo);

    }

}