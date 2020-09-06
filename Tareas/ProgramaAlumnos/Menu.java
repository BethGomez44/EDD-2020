
/**
 * Menu para programa que procesa archivos de texto.
 * 
 * @author Del Moral Morales Francisco Emmanuel
 * @author Gómez de la Torre Heidi Lizbeth
 * @version 15/Mar/2020
 */

import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        int opcion;

        System.out.print(" \n");
        System.out.println("\t\t ¡Bienvenido! ¿Qué puedo hacer por ti? \n");
        System.out.println("1. Consultar alumno. \n");
        System.out.println("2. Consultar materia. \n");
        System.out.println("3. Salir del programa. \n");
        try {
            do {
                System.out.print("Por favor, ingresa tu opción: ");
                opcion = in.nextInt();
                if (opcion != 3) {
                    switch (opcion) {
                    case 1:
                        System.out.print(" \n");
                        System.out.println("Alumno: ");
                        System.out.println("Número de cuenta: ");
                        System.out.println("Materias: ");
                        break;
                    case 2:
                        System.out.print(" \n");
                        System.out.println("Materia: ");
                        System.out.println("Profesor: ");
                        System.out.println("Clave: ");
                        System.out.println("Alumnos: ");
                        break;
                    default:
                        System.out.print(" \n");
                        System.out.println("La opción ingresada no se encuentra en el menu, intentalo nuevamente.");
                        break;
                    }
                }
                System.out.print(" \n");
            } while (opcion != 3);
            System.out.println("\t\t\t ¡Hasta pronto! ");
        } catch (InputMismatchException e) {
            System.out.print(" \n");
            System.out.println("Únicamente cuentas con las opciones marcadas en el menu, intentalo nuevamente.\n");
            main(args);
        }
    }
}

/**
 * Observaciones:
 * 
 * La única forma de regresar de la excepción al menu que se me ocurrio fue
 * esta. Queda un poco mal ya que cuando pones una opcion númerica incorrecta
 * esta solamente te pide que ingreses otra opción, lo cual no pasa con la
 * excepción. Se me ocurrio que a la vez se puede crear un metodo que contenga a
 * menu y así solamente volver a llamar a ese método en cierta parte del menu,
 * pero a la hora de implementarlo no supe como. De igual forma si no te gusta,
 * lo corrijo mañana. Bonita noche.
 */