import java.util.Iterator;
/**
 * Clase para probar el funcionamiento de MinHeap
 * @author Prado Oropeza Karina Vianey
*/

public class PruebaHeap {
    public static void main (String[] args){
      Lista<Integer> una = new Lista<>();
      una.agregar(new Integer(5));
      una.agregar(new Integer(3));
      una.agregar(new Integer(2));
      una.agregar(new Integer(4));
      una.agregar(new Integer(7));
      una.agregar(new Integer(8));
      una.agregar(new Integer(1));
      System.out.println("LISTA UNO " + una);

      MaxHeap<Integer> lista = new MaxHeap<Integer>(una);
      System.out.println(lista);
      lista.agrega(new Integer(0));
      System.out.println(lista);
      System.out.println("Contiene 8"+lista.contiene(new Integer(8)));
      System.out.println("elimina " + lista.elimina());
      System.out.println(lista);

      Cola<Integer> cola = new Cola<>();
      cola.agrega(new Integer(5));
      cola.agrega(new Integer(3));
      cola.agrega(new Integer(2));
      cola.agrega(new Integer(4));
      cola.agrega(new Integer(7));
      cola.agrega(new Integer(8));
      cola.agrega(new Integer(1));


      MinHeap<Integer> colas = new MinHeap<Integer>(cola);
      System.out.println("vacio " + colas.esVacio());
      System.out.println("tam " + colas.getTamanio());
      System.out.println(colas);

      int j = 0;
      System.out.println(colas + "\n cola");
      while(j < 7){
        System.out.println("elimina " + colas.elimina());
        j++;
      }
        MinHeap<String> ejemplo = new MinHeap<String>();

        System.out.println("Arreglo de 3 elementos");
        ejemplo.agrega(new String("Zanahorias"));
        ejemplo.agrega(new String("Holaa"));
        ejemplo.agrega(new String("Elegante"));
        ejemplo.agrega(new String("Beso"));
        ejemplo.agrega(new String("Alegria"));
        ejemplo.agrega(new String("Vestido"));
        ejemplo.agrega(new String("Chiflan"));
        ejemplo.agrega(new String("Prueba"));
        ejemplo.agrega(new String("que"));
        ejemplo.agrega(new String("hacer"));

        System.out.println(ejemplo + "\n");

        ejemplo.agrega("Agranda");
        System.out.println("Agregar más elementos");
        System.out.println(ejemplo + "\n");

        System.out.print("Contiene \"Holaa\" : \t");
        System.out.println(ejemplo.contiene("Holaa"));

        System.out.println(ejemplo.elimina());
        System.out.println(ejemplo);
        System.out.println(ejemplo.elimina());
        System.out.println(ejemplo);
        Iterator it = ejemplo.iterator();
        System.out.println("Iterador");
        while(it.hasNext()){
          System.out.println(it.next());
        }

        MaxHeap<Integer> ordena = new MaxHeap<Integer>();
        ordena.agrega(1);
        ordena.agrega(2);
        ordena.agrega(3);
        ordena.agrega(4);
        ordena.agrega(5);
        ordena.agrega(6);
        ordena.agrega(7);
        ordena.agrega(8);
        ordena.agrega(9);
        ordena.agrega(10);
        int i = 0;
        System.out.println(ordena + "\n");
        while(i < 10){
          System.out.println("elimina " + ordena.elimina());
          System.out.println(ordena);
          i++;
        }
        System.out.println(ordena);


    }
}
