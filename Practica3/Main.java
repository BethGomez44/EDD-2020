import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Lista<Character> letras = new Lista<>();
        Scanner in = new Scanner(System.in);
        letras.agregar('h');
        letras.agregar('o');
        letras.agregar('l');
        letras.agregar('a');
        letras.agregarAlInicio(' ');
        letras.agregarAlInicio('S');
        letras.agregarAlInicio('O');
        letras.agregarAlInicio('G');
        letras.agregarAlInicio('I');
        letras.agregarAlInicio('M');
        letras.agregarAlInicio('a');

        System.out.println(letras);
        System.out.print("Digita un caracter a buscar en la lista: \n> ");
        char aux = in.nextLine().charAt(0);
        System.out.println("La lista " + (letras.contiene(aux) ? " " : "no ") + "contiene la letra " + aux);
        System.out.print("Digita un caracter a borrar en la lista: \n> ");
        aux = in.nextLine().charAt(0);
        letras.eliminar(aux);
        System.out.println(letras);
        System.out.print("Digita el  elemento del cual  quieras saber su indice en la lista: \n> ");
        aux = in.nextLine().charAt(0);
        int index = letras.indiceDe(aux);
        System.out.println(index != -1 ? "el elemento esta en el indice " + index + " y es " + letras.getElemento(index)
                : "no esta en la lista");
        System.out.println("Asi se ve la lista al reves: " + letras.reversa().toString());
        letras = new Lista<>();
        letras.agregar('a');
        letras.agregar('n');
        letras.agregar('a');

        System.out.print("La lista " + letras.toString() + " es un palindromo por que ");
        Lista reversa = letras.reversa();
        System.out.println(letras.equals(reversa) ? "se lee igual al revees " : "algo anda mal");

        Lista<Integer> orden = new Lista<>();
        Random nums = new Random();
        for (int i = 0; i < 1000; i++) {
            orden.agregar(nums.nextInt(3) * (nums.nextInt(2) % 2 == 0 ? 1 : -1));
        }
        System.out.println("Ordenemos la lista : \n" + orden.toString());
        System.out.println("Ordenado: " + Lista.mergesort(orden).toString());
    }
}
