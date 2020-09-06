import java.util.Iterator;

/**
 * Clase que nos resuelve el problema de Josephus a)
 * 
 * @author Gómez de la Torre Heidi Lizbeth
 */
public class JosephusA {

    public static void main(String[] args) {
        System.out.println("\t\t Problema de Josephus");
        ListaCircular<Integer> soldados = new ListaCircular();
        ListaCircular<Integer> muertos = new ListaCircular();
        int i = 0;
        while (i++ < 40) {
            soldados.agrega(i);
        }
        System.out.println(" ");
        System.out.println("Los soldados de Josephus se sentaban de la siguiente forma: ");
        System.out.println(soldados.toString());
        System.out.println(" ");
        System.out.println("Cada 3 saltos, el cuarto soldado muere. Ahora veremos el orden en que murieron: ");
        Iterator itSol = soldados.iterator();
        int j = 0;
        while (itSol.hasNext() && j++ < 40) {
            itSol.next();
            if (j == 2) {
                muertos.agrega((Integer) itSol.next());
                itSol.remove();
                j = 0;
            }
        }
        System.out.println(muertos.toString());
        System.out.println(" ");
        System.out.println("Por lo tanto, Josephus debió colocarse en la posicion: 7");

    }
}