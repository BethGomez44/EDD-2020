import java.util.Iterator;

/**
 * <p>
 * Clase concreta para modelar la estructura de datos Lista
 * </p>
 * <p>
 * Esta clase implementa una Lista genérica, es decir que es homogénea pero
 * puede tener elementos de cualquier tipo.
 * 
 * @author Alejandro Hernández Mora <alejandrohmora@ciencias.unam.mx>
 * @version 1.0
 */
public class Lista<T> implements Listable<T>, Iterable<T> {

    /* Clase interna para construir la estructura */
    private class Nodo {
        /* Referencias a los nodos anterior y siguiente */
        public Nodo anterior, siguiente;
        /* El elemento que almacena un nodo */
        public T elemento;

        /* Unico constructor de la clase */
        public Nodo(T elemento) {
            this.elemento = elemento;
        }

        public boolean equals(Nodo n) {
            return this.elemento.equals(n.elemento);
        }
    }

    private class IteradorLista<T> implements Iterator<T> {
        /* La lista a recorrer */
        Lista<T> lista;
        /* Elementos del centinela que recorre la lista */
        private Lista<T>.Nodo siguiente;

        public IteradorLista() {
            siguiente = lista.cabeza;
        }

        @Override
        public boolean hasNext() {
            return siguiente != null;
        }

        @Override
        public T next() {

            if (this.siguiente == null) {
                return null;
            } else {
                Lista<T>.Nodo n = this.siguiente;
                this.siguiente = n.siguiente;
                return n.elemento;
            }
        }

        @Override
        public void remove() {
            Iterator.super.remove(); // To change body of generated methods, choose Tools | Templates.
        }
    }

    /* Atributos de la lista */
    private Nodo cabeza, cola;
    private int longitud;

    /**
     * Constructor por omisión de la clase, no recibe parámetros. Crea una nueva
     * lista con longitud 0.
     **/
    public Lista() {
        longitud = 0;
        cabeza = null;
        cola = null;
    }

    /**
     * Constructor copia de la clase. Recibe una lista con elementos. Crea una nueva
     * lista exactamente igual a la que recibimos como parámetro.
     * 
     * @param l -- lista a copiar.
     **/
    public Lista(Lista<T> l) {
        Lista<T> nueva = l.copia();
        this.longitud = nueva.longitud;
        this.cabeza = nueva.cabeza;
        this.cola = nueva.cola;
    }

    /**
     * Constructor de la clase que recibe parámetros. Crea una nueva lista con los
     * elementos de la estructura iterable que recibe como parámetro.
     **/
    public Lista(Iterable<T> iterable) {
        longitud = 0;
        cabeza = null;
        cola = null;
        for (T elem : iterable)
            agregar(elem);
    }

    /**
     * Método que nos dice si las lista está vacía.
     * 
     * @return <code>true</code> si el conjunto está vacío, <code>false</code> en
     *         otro caso.
     */
    @Override
    public boolean esVacia() {

        if (longitud == 0) {
            return true;
        }
        return false;
    }

    /**
     * Método para eliminar todos los elementos de una lista
     */
    @Override
    public void vaciar() {

        cabeza = null;
        cola = null;
        longitud = 0;
    }

    /**
     * Método para obtener el tamaño de la lista
     * 
     * @return tamanio Número de elementos de la lista.
     **/
    public int longitud() {

        return longitud;
    }

    /**
     * Método para agregar un elemento a la lista.
     * 
     * @param elemento Objeto que se agregará a la lista.
     */
    public void agregar(T elemento) {

        this.agregarAlInicio(elemento);

    }

    /**
     * Método para agregar al inicio un elemento a la lista.
     * 
     * @param elemento Objeto que se agregará al inicio de la lista.
     */
    public void agregarAlInicio(T elemento) {

        Nodo aux = new Nodo(elemento);
        if (!esVacia()) {
            this.cabeza.anterior = aux;
            aux.siguiente = cabeza;
        } else {
            this.cola = aux;
            this.cola.siguiente = null;
        }
        this.cabeza = aux;
        this.cabeza.anterior = null;
        longitud++;

    }

    /**
     * Método para agregar al final un elemento a la lista.
     * 
     * @param elemento Objeto que se agregará al inicio de la lista.
     */
    public void agregarAlFinal(T elemento) {

        Nodo aux = new Nodo(elemento);
        if (!esVacia()) {
            this.cola.siguiente = aux;
            aux.anterior = cola;
        } else {
            this.cabeza = aux;
            this.cabeza.anterior = null;
        }
        this.cola = aux;
        this.cola.siguiente = null;
        longitud++;
    }

    /**
     * Método para verificar si un elemento pertenece a la lista.
     * 
     * @param elemento Objeto que se va a buscar en la lista.
     * @return <code>true</code> si el elemento esta en el lista y false en otro
     *         caso.
     */
    public boolean contiene(T elemento) {

        if (longitud() == 0) {
            return false;
        } else {
            Nodo n = buscar(elemento, cabeza);
            if (n != null) {
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * Método para eliminar un elemento de la lista.
     * 
     * @param elemento Objeto que se eliminara de la lista.
     */
    public void eliminar(T elemento) {

        Nodo busqueda = buscar(elemento, cabeza);
        cabeza.anterior = busqueda;
        if (busqueda != null) {
            if (longitud == 1) {
                cabeza = cola = null;
            }
            if (busqueda == cabeza) {
                cabeza = busqueda.siguiente;
                cabeza.anterior = null;
            }
            if (busqueda == cola) {
                cola = busqueda.anterior;
                cola.siguiente = null;
            } else {
                busqueda.siguiente.anterior = busqueda.anterior;
                busqueda.anterior.siguiente = busqueda.siguiente;
            }
            longitud--;
        }

    }

    /**
     * Método que devuelve la posición en la lista que tiene la primera aparición
     * del <code> elemento</code>.
     * 
     * @param elemento El elemento del cuál queremos saber su posición.
     * @return i la posición del elemento en la lista, -1, si no se encuentra en
     *         ésta.
     */
    public int indiceDe(T elemento) {

        int i = 0;
        Nodo aux = this.cabeza;
        while (i < longitud) {
            if (aux.elemento.equals(elemento)) {
                return i;
            } else {
                aux = aux.siguiente;
            }
            i++;
        }
        return -1;
    }

    /**
     * Método que nos dice en qué posición está un elemento en la lista
     * 
     * @param i La posición cuyo elemento deseamos conocer.
     * @return <code> elemento </code> El elemento que contiene la lista,
     *         <code>null</code> si no se encuentra
     * @throws IndexOutOfBoundsException Si el índice es < 0 o >longitud()
     */
    public T getElemento(int i) throws IndexOutOfBoundsException {

        if (i > longitud() - 1 || i < 0) {
            throw new IndexOutOfBoundsException();
        } else {
            Nodo aux = this.cabeza;
            int l = 0;
            while (l < i) {
                aux = aux.siguiente;
                l++;
            }
            return aux.elemento;
        }
    }

    /**
     * Método que devuelve una copia de la lista, pero en orden inverso
     * 
     * @return Una copia con la lista l revés.
     */
    public Lista<T> reversa() {

        Lista<T> reversa = new Lista<T>();
        Nodo n = this.cabeza;
        reversa.longitud = 0;
        while (n != null) {
            reversa.agregarAlInicio(n.elemento);
            n = n.siguiente;
        }
        return reversa;

    }

    /**
     * Método que devuelve una copia exacta de la lista
     * 
     * @return la copia de la lista.
     */
    public Lista<T> copia() {

        Lista<T> copia = new Lista<T>();
        Nodo o = this.cabeza;
        copia.longitud = 0;
        while (o != null) {
            copia.agregarAlFinal(o.elemento);
            o = o.siguiente;
        }
        return copia;
    }

    /**
     * Método que nos dice si una lista es igual que o.
     * 
     * @param o objeto a comparar con la lista.
     * @return <code>true</code> si son iguales, <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object o) {

        boolean eq = true;
        Nodo aux1 = this.cabeza;
        if (!(o instanceof Lista)) {
            return false;
        }
        Lista<T> l = (Lista<T>) o;
        Nodo aux2 = l.cabeza;
        if (l.longitud != this.longitud) {
            return false;
        }
        while (aux1 != null && aux2 != null) {
            eq = aux1.elemento.equals(aux2.elemento);
            aux1 = aux1.siguiente;
            aux2 = aux2.siguiente;
            if (eq == false) {
                return false;
            }
        }
        return true;
    }

    /**
     * Método que devuelve un iterador sobre la lista
     * 
     * @return java.util.Iterador -- iterador sobre la lista
     */
    @Override
    public java.util.Iterator<T> iterator() {
        return new IteradorLista();

    }

    /**
     * Método que devuelve una copia de la lista ya ordenada.
     * 
     * @param <T> Debe ser un tipo que extienda Comparable, para poder distinguir el
     *            orden de los elementos en la lista.
     * @param l   La lista de elementos comparables.
     * @return copia de la lista ordenada.
     */
    public static <T extends Comparable<T>> Lista<T> mergesort(Lista<T> l) {

        Lista<T>.Nodo auxN = l.cabeza;
        Lista<T> aux = new Lista<>();
        Lista<T> aux2 = new Lista<>();
        int i = 0;

        if (l.longitud < 2) {
            return l.copia();
        }
        while (i < (l.longitud)) {
            if (i++ < (l.longitud / 2)) {
                aux.agregarAlFinal(auxN.elemento);
                auxN = auxN.siguiente;
            } else {
                aux2.agregarAlFinal(auxN.elemento);
                auxN = auxN.siguiente;
            }
        }

        Lista<T> l1 = mergesort(aux);
        Lista<T> l2 = mergesort(aux2);
        return merge(l1, l2);
    }

    /**
     * Metodo auxiliar al algoritmo de ordenamiento mergeSort.
     * 
     * @param l1
     * @param l2
     * @return el entre dos listas.
     */
    private static <T extends Comparable<T>> Lista<T> merge(Lista<T> l1, Lista<T> l2) {
        Lista<T> merge = new Lista<>();
        Lista<T>.Nodo aux1 = l1.cabeza;
        Lista<T>.Nodo aux2 = l2.cabeza;

        while (aux1 != null && aux2 != null) {
            if (aux1.elemento.compareTo(aux2.elemento) == -1) {
                merge.agregarAlFinal(aux1.elemento);
                aux1 = aux1.siguiente;
            } else {
                merge.agregarAlFinal(aux2.elemento);
                aux2 = aux2.siguiente;
            }
        }
        while (aux1 != null) {
            merge.agregarAlFinal(aux1.elemento);
            aux1 = aux1.siguiente;
        }
        while (aux2 != null) {
            merge.agregarAlFinal(aux2.elemento);
            aux2 = aux2.siguiente;
        }
        return merge;
    }

    /**
     * Método privado para buscar un elemento contenido en un nodo.
     * 
     * @param elemento
     * @param pivote
     * @return
     */
    private Nodo buscar(T elemento, Nodo pivote) {

        while (pivote != null) {
            if (pivote.elemento.equals(elemento)) {
                return pivote;
            } else {
                pivote = pivote.siguiente;
            }
        }
        return null;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * 
     * @return una cadena.
     */
    @Override
    public String toString() {
        Nodo l = this.cabeza;
        String cadena = "[";
        if (this.cabeza == null) {
            cadena.concat("]");
        } else {
            while (l.siguiente != null) {
                cadena = cadena.concat(l.elemento.toString());
                if (l.siguiente.siguiente != null) {
                    cadena = cadena.concat(", ");
                } else {
                    cadena = cadena.concat(", " + l.siguiente.elemento.toString() + "]");
                    break;
                }
                l = l.siguiente;
            }
        }
        return cadena;
    }

}
