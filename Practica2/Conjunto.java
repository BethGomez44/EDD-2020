
/**
 * Práctica 2. Conjuntos
 * @author Luis Manuel Martínez Dámaso
 * @version 02/Feb/2020
 */
import java.util.Iterator;

public class Conjunto<T> implements Iterable<T> {

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

    public Conjunto() {

        arreglo = (T[]) new Object[10];
        elementos = 0;

    }

    public Conjunto(T[] elementos) {

        Conjunto<T> aux = new Conjunto();
        for (T elem : elementos) {
            aux.agrega(elem);
        }
        this.arreglo = aux.arreglo;
        this.elementos = aux.elementos;

    }

    /**
     * Método que nos dice si el conjunto está vacío.
     * 
     * @return <code>true</code> si el conjunto está vacío, <code>false</code> en
     *         otro caso.
     */
    public boolean esVacio() {

        if (elementos == 0) {
            return true;
        }
        return false;

    }

    /**
     * Método para eliminar todos los elementos de un conjunto
     */
    public void vaciar() {

        elementos = 0;
        arreglo = (T[]) new Object[arreglo.length];

    }

    /**
     * Método para insertar un elemento al final del arreglo dinamico. Si el arreglo
     * no tiene espacio, crecer el arreglo al doble de tamaño.
     *
     * @param elem
     */
    public void agrega(T elem) {

        int aux = 0;
        if (!contiene(elem)) {
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
        
    }

    /**
     * Método para eliminar un <code>elemento</code> del conjunto
     * 
     * @param elemento Objeto que se eliminara del conjunto
     */
    public void eliminar(T elemento) {

        for (int i = 0; i < elementos; i++) {
            if (arreglo[i].equals(elemento)) {
                arreglo[i] = arreglo[i + 1];
                for (int j = i; j < elementos - 1; j++) {
                    arreglo[j] = arreglo[j + 1];
                }
                arreglo[elementos - 1] = null;
                elementos--;
            }
        }

    }

    /**
     * Método para saber si un elemento esta en el arreglo dinámico, devuelve true
     * si esta en el arreglo, false en otro caso.
     *
     * @param elem
     * @return
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
     * Método para calcular la union de dos conjuntos
     * 
     * @param c1 conjunto con el que se calculará la unión
     * @return Conjuntable conjunto que contiene la unión
     */
    public Conjunto<T> union(Conjunto<T> c) {

        Conjunto<T> union = new Conjunto();
        for (T elem : c) {
            union.agrega(elem);
        }
        for (T elem : this) {
            union.agrega(elem);
        }
        return union;

    }

    /**
     * Método para calcular la intersección de dos conjuntos
     * 
     * @param c conjunto con el que se calculará la intersección
     * @return Conjuntable que con tiene la intersección
     */
    public Conjunto<T> interseccion(Conjunto<T> c) {

        Conjunto<T> interseccion = new Conjunto();
        for (T elem : c) {
            if (c.contiene(elem) && this.contiene(elem)) {
                interseccion.agrega(elem);
            }
        }
        return interseccion;

    }

    /**
     * Método para calcular la diferencia de dos conjuntos
     * 
     * @param c conjunto con el que se va a calcular la diferencia
     * @return Conjuntable con la diferencia
     */
    public Conjunto<T> diferencia(Conjunto<T> c) {

        Conjunto<T> diferencia = new Conjunto();
        for (T elem : this) {
            if (!(c.contiene(elem))) {
                diferencia.agrega(elem);
            }
        }
        return diferencia;

    }

    public Conjunto<T> diferenciaSimetrica(Conjunto<T> c) {

        return this.union(c).diferencia(c.interseccion(this));
    }

    /**
     * Método para determinar si un conjunto esta contenido en otro
     * 
     * @param c conjunto en se va a probar si el que llama es subconjunto
     * @return boolean true si el conjunto que llama a este metodo es subconjunto
     *         del parametro y false en otro caso
     */
    public boolean subconjunto(Conjunto<T> c) {

        for (T elem : c) {
            if (this.contiene(elem)) {
                return true;
            }
        }
        return false;

    }

    @Override
    public java.util.Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Método para representar el arreglo en una cadena.
     *
     * @return
     */
    @Override
    public String toString() {

        String cad = new String();
        int aux = 1;
        cad = "[";
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
}
