
/**
 * Práctica 1. Arreglos Dinamicos
 * @author Luis Manuel Martínez Dámaso
 * @version 02/Feb/2020
 */
import java.lang.reflect.Array;
import java.util.Iterator;

public class ArregloDinamico<T> implements Iterable<T> {

    private T[] arreglo;
    private int elementos;

    private class Iterador<T> implements Iterator<T> {

        private int siguiente;

        public Iterador() {

            siguiente = 0;

        }

        @Override
        public boolean hasNext() {

            return siguiente < elementos;

        }

        @Override
        public T next() {

            if (hasNext()) {

                T aux = (T) arreglo[siguiente];
                siguiente++;
                return aux;

            }
            return null;
        }

        @Override
        public void remove() {
            Iterator.super.remove();
        }

    }

    /**
     * Constructor por omisión
     */
    public ArregloDinamico() {

        arreglo = (T[]) new Object[10];
        elementos = 0;
    }

    /**
     * Constructor que recibe el tamaño con el cual queremos inicializar el arreglo
     * dinámico.
     *
     * @param n -- Dimensión del arreglo.
     */
    public ArregloDinamico(int n) {

        arreglo = (T[]) new Object[n];
        elementos = 0;

    }

    /**
     * Método para insertar un elemento al final del arreglo dinamico. Si el arreglo
     * no tiene espacio, crecer el arreglo al doble de tamaño.
     *
     * @param elem -- El elemento por agregar.
     */
    public void agrega(T elem) {

        int aux = 0;
        if (arreglo.length <= elementos) {
            T[] arrDoble = (T[]) new Object[arreglo.length * 2];
            for (T i : arreglo) {
                arrDoble[aux] = i;
                aux++;
            }
            arrDoble[aux] = elem;
            elementos++;
            arreglo = arrDoble;
        } else {
            arreglo[elementos] = elem;
            elementos++;
        }

    }

    /**
     * Método para acceder al elemento n-esimo del arreglo dinámico. El método debe
     * devolver el elemento buscado. Si no existe elemento n-esimo, devolver null.
     *
     * @param n -- Posición donde se encuentra el elemento a buscar
     * @return
     */
    public T busca(int n) {

        if (n < arreglo.length && n >= 0) {
            return arreglo[n];
        }

        return null;
    }

    /**
     * Método para eliminar al elemento n-esimo del arreglo dinámico, no debe haber
     * espacios sin elementos. El método debe devolver el elemento eliminado. Si no
     * existe elemento n-esimo, devolver null.
     *
     * @param n -- Posición donde se encuentra el elemento a eliminar
     * @return elem -- El elemento que fue eliminado
     */
    public T elimina(int n) {

        T elem = arreglo[n - 1];
        if (n < elementos) {
            for (int i = n - 1; i < elementos; i++) {
                arreglo[i] = arreglo[i + 1];
            }
            elementos--;
            return elem;
        }
        return null;

    }

    /**
     * Método para saber si un elemento esta en el arreglo dinámico, devuelve true
     * si esta en el arreglo, false en otro caso.
     *
     * @param elem
     * @return true -- Si el elemento se encuentra en el arreglo.
     * @return false -- Si el elemento no se encuentra en el arreglo.
     */
    public boolean contiene(T elem) {

        for (T i : arreglo) {
            if (i == elem) {
                return true;
            }
        }
        return false;
    }

    /**
     * Ordena el arreglo dinámico pasado como parametro usando QuickSort.
     * 
     * @param <T>
     * @param array -- El arreglo dinámico a ordenar.
     */
    public static <T extends Comparable<T>> void quickSort(ArregloDinamico<T> array) {

        T[] nuevo = (T[]) new Comparable[array.elementos];
        for (int i = 0; i < nuevo.length; i++) {
            nuevo[i] = array.busca(i);
        }
        quickSort(nuevo, 0, array.elementos - 1);
        array.arreglo = nuevo;

    }

    /**
     * Ordena el arreglo pasado como parametro usando QuickSort.
     * 
     * @param <T>
     * @param a   -- El arreglo a ordenar.
     * @param ini -- La posición del primer elemento del arreglo
     * @param fin -- La posición del ultimo elemento del arreglo.
     */

    public static <T extends Comparable<T>> void quickSort(T[] a, int ini, int fin) {

        int i = ini + 1;
        int j = fin;
        if (fin - ini > 0) {

            while (i < j) {
                if (a[i].compareTo(a[ini]) > 0 && a[j].compareTo(a[ini]) <= 0) {
                    cambio(a, i++, j--);
                } else if (a[i].compareTo(a[ini]) <= 0) {
                    i++;
                } else {
                    j--;
                }
            }
            if (a[i].compareTo(a[ini]) > 0) {
                i--;
            }
            cambio(a, i, ini);
            quickSort(a, ini, i);
            quickSort(a, i + 1, fin);
        }
    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Método para representar el arreglo en una cadena.
     *
     * @return cad -- La cadena que contiene al arreglo.
     */
    @Override
    public String toString() {

        String cad = new String();
        int aux = 1;
        cad += "[";
        for (T i : arreglo) {
            if (i != null && aux < elementos) {
                cad += i;
                cad += ",";
                cad += " ";
            }

            if (aux == elementos && i != null) {
                cad += i;
            }

            aux++;
        }
        cad += "]";
        return cad;
        
    }

    /**
     * Ordena el arreglo pasado como parámetro usando SelectionSort.
     * 
     * @param a -- Arreglo a ordenar.
     */
    public static <T extends Comparable<T>> void selectionSort(T[] a) {

        for (int i = 0; i < a.length; i++) {
            int min = i;
            for (int j = i; j < a.length; j++) {
                if (a[j].compareTo(a[min]) < 0) {
                    min = j;
                }
            }
            cambio(a, i, min);
        }
    }

    /**
     * Intercambia dos elementos de un arreglo.
     * 
     * @param a -- El arreglo en el que intercabiará los elementos.
     * @param i -- La posición de un elemento a intercambiar.
     * @param j -- La posición de el segundo elemento intercambiar.
     */
    private static <T extends Comparable<T>> void cambio(T[] a, int i, int j) {
        T aux = a[i];
        a[i] = a[j];
        a[j] = aux;
    }

}
