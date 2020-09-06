import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Random;
import java.lang.Integer;

/**
 * Clase que nos resuelve el problema de Josephus b)
 * 
 * @author Gómez de la Torre Heidi Lizbeth
 */

public class JosephusB {

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

    }
}