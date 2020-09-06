import java.time.DayOfWeek;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

/**
 * Clase que le muestra a los becarios el día que les toca lavar la cafetera,
 * sabiendo que esta solo se lava los días lunes.
 * 
 * @author Gómez de la Torre Heidi Lizbeth.
 */
public class BecariosA {

    public static void main(String[] args) {

        ListaCircular<String> becarios = new ListaCircular();
        ListaCircular<String> fechas = new ListaCircular();
        Calendar cal = GregorianCalendar.getInstance();
        int semanas = 0, i = 0;

        System.out.println("\t\t\t Adictos al café");
        System.out.println(" ");

        becarios.agrega("Ricardo");
        becarios.agrega("Alejandro");
        becarios.agrega("Nestaly");
        becarios.agrega("Alma");

        System.out.println("El día de hoy, estamos a " + cal.getTime().toGMTString());
        System.out.println("Los becarios son: " + becarios.toString());
        System.out.println(" ");

        System.out.println("Las fechas para limpiar la cafetera están repartidas de la siguiente forma: ");
        System.out.println(" ");

        int aux = cal.get(GregorianCalendar.DAY_OF_WEEK);

        if (aux == 1) {
            cal.add(5, 0);
        } else if (aux == 3) {
            cal.add(5, 5);
        } else if (aux == 4) {
            cal.add(5, 4);
        } else if (aux == 5) {
            cal.add(5, 3);
        } else if (aux == 6) {
            cal.add(5, 2);
        } else if (aux == 7) {
            cal.add(5, 1);
        }

        while (semanas < 16) {
            fechas.agrega(cal.getTime().toGMTString());
            cal.add(5, 7);
            semanas++;
        }

        System.out.println("Persona que debe lavar: " + "\t " + "Día que le toca lavar: ");
        Iterator itBec = becarios.iterator();
        Iterator itFech = fechas.iterator();
        while (itFech.hasNext() && i < semanas) {
            System.out.println(itBec.next() + "    |\t\t\t" + itFech.next());
            System.out.println(" ");
            i++;
        }

    }
}