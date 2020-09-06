
/**
 * Práctica 2. Conjuntos
 * @author Luis Manuel Martínez Dámaso
 * @version 02/Feb/2020
 */
import java.lang.reflect.Array;
import java.util.Iterator;

public class Conjuntos<T> implements Iterable<T> {

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
     * Constructor por omisión.
     */
    public Conjunto() {

        arreglo = (T[]) new Object[10];
        elementos = 0;
    }

    /**
     * Constructor que recibe el tamaño con el cual queremos inicializar el conjunto.
     *
     * @param n -- Dimensión del conjunto.
     */
    public Conjunto(int n) {

        arreglo = (T[]) new Object[n];
        elementos = 0;

    }

    public boolean contiene(T elem) {

        for (T i : arreglo) {
            if (i == elem) {
                return true;
            }
        }
        return false;
    }

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

    public void elimina(T elem) {

        for(int i = 0; i < elementos; i++) {
            if (arreglo[i].equals(elem)) {
                arreglo[i] = arreglo[i+1];
                for (int j = i; j < elementos - 1; j++) {
                    arreglo[j] = arreglo[j + 1];
                }
                arreglo[elementos - 1] = null;
                elementos--;
            }
        }

    }
    public boolean subconjunto(Conjunto<T> c) {

        if (elementos < c.elementos) {
            return false;
        }

        for (T elem : c) {
            if (!contiene(elem)) {
                return false;
            }
        }

        return true;
    }

    public Conjunto<T> union(Conjunto<T> c) {

        Conjunto<T> union = new Conjunto(elementos + c.elementos);
        for (T elem : c) {
            union.agrega(elem);
        }
        for (T elem : this) {
            union.agrega(elem);
        }
    }

    public Conjunto<T> interseccion(Conjunto<T> c) {

        Conjunto<T> interseccion = new Conjunto(elementos + c.elementos);
        for (T elem : c) {
            if ()
            interseccion.agrega(elem);
        }
        for (T elem : this) {
            interseccion.agrega(elem);
        }
    }

    public void vaciar() {
        elementos = 0;
        arreglo = (T[]) new Object[arreglo.length];
        arreglo = null;
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

    @Override
    public java.util.Iterator<T> iterator() {
        return new Iterador();
    }

}