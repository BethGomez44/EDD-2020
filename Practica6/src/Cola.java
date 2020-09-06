import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>
 * Clase concreta para modelar la estructura de datos Cola
 * </p>
 * <p>
 * Esta clase implementa una Cola genérica, es decir que es homogénea pero puede
 * tener elementos de cualquier tipo.
 * 
 * @author Alejandro Hernández Mora <alejandrohmora@ciencias.unam.mx>
 * @version 1.0
 * @param <T> Tipo que tienen los objetos que guarda esta cola.
 */
public class Cola<T> implements Coleccionable<T> {

    private class Nodo {

        /**
         * El elemento del nodo.
         */
        public T elemento;
        /**
         * El siguiente nodo.
         */
        public Nodo siguiente;

        /**
         * Construye un nodo con un elemento.
         *
         * @param elemento el elemento del nodo.
         */
        public Nodo(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;

        }
    }

    private class IteradorCola implements Iterator<T> {
        public Nodo siguiente;

        public IteradorCola() {
            siguiente = inicio;
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override
        public boolean hasNext() {
            return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override
        public T next() {
            T aux = siguiente.elemento;
            siguiente = siguiente.siguiente;
            return aux;
        }

        @Override
        public void remove() {
            Iterator.super.remove();
        }

    }

    /* Nodo inicial de la cola */
    private Nodo inicio;
    /* Nodo final de la cola */
    private Nodo rabo;
    /* Tamaño de la cola */
    private int elementos;

    /**
     * Constructor por omisión de la clase.
     */
    public Cola() {
        // Aqui no hay que hacer nada,
        // ya que los valores por default nos sirven al crear un objeto.
    }

    /**
     * Constructor que recibe un arreglo de elementos de tipo <code>T</code>. Crea
     * una cola con los elementos del arreglo.
     * 
     * @param elementos El arreglo que se recibe como parámetro.
     */
    public Cola(T[] elementos) {
        for (int i = 0; i < elementos.length; i++) {
            this.queue(elementos[i]);
        }
    }

    /**
     * Constructor que recibe una colección de tipo {@link Coleccionable} de
     * elementos de tipo <code>T</code> y los agrega a la nueva cola.
     * 
     * @param elementos La colección de elementos a agregar.
     */
    public Cola(Coleccionable<T> elementos) {

        for (T elem : elementos) {
            this.agrega(elem);
        }
    }

    /**
     * Constructor de la clase, que recibe una cola y regresa una copia exacta de
     * ésta.
     * 
     * @param cola La cola que se va a copiar.
     */
    public Cola(Cola<T> cola) {

        Nodo o = cola.inicio;
        do {
            this.queue(o.elemento);
            o = o.siguiente;
        } while (o != null);

    }

    /**
     * Agrega un elemento en el rabo de la Cola.
     * 
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    public void queue(T elemento) throws IllegalArgumentException {

        Nodo aux = new Nodo(elemento);
        if (elemento == null) {
            throw new IllegalArgumentException("El elemento pasado como parámetro no es válido");
        } else if (this.esVacio()) {
            rabo = inicio = aux;
        } else {
            rabo.siguiente = aux;
            rabo = aux;
        }
        elementos++;

    }

    /**
     * Elimina el elemento del inicio de la Cola y lo regresa.
     * 
     * @throws NoSuchElementException si la cola es vacía
     * @return el elemento en el inicio de la Cola.
     */
    public T dequeue() throws NoSuchElementException {
        if (this.inicio == null) {
            throw new NoSuchElementException("No existen elementos suficientes");
        }
        T aux = inicio.elemento;
        inicio = inicio.siguiente;
        elementos--;
        return aux;

    }

    /**
     * Nos permite ver el elemento en el inicio de la Cola
     *
     * @return el elemento en un extremo de la estructura.
     */
    public T peek() {
        return inicio != null ? inicio.elemento : null;
    }

    /**
     * Agrega un elemento a la Cola.
     *
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *                                  <code>null</code>.
     */
    @Override
    public void agrega(T elemento) throws IllegalArgumentException {
        if (elemento == null) {
            throw new IllegalArgumentException("El elemento pasado como parámetro no es válido");
        }
        queue(elemento);
    }

    /**
     * Nos dice si un elemento está contenido en la Cola.
     *
     * @param elemento el elemento que queremos verificar si está contenido en la
     *                 Cola.
     * @return <code>true</code> si el elemento está contenido en la Cola,
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean contiene(T elemento) {
        Iterator it = this.iterator();
        while (it.hasNext()) {
            if (it.next().equals(elemento)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Elimina un elemento de la Cola.
     * 
     * @throws NoSuchElementException si la cola es vacía
     * @param elemento el elemento a eliminar.
     */
    @Override
    public void elimina(T elemento) throws NoSuchElementException {
        if (this.inicio == null) {
            throw new NoSuchElementException("No existen elementos suficientes");
        }
        dequeue();
    }

    /**
     * Nos dice si la Cola está vacía.
     *
     * @return <tt>true</tt> si la Cola no tiene elementos, <tt>false</tt> en otro
     *         caso.
     */
    @Override
    public boolean esVacio() {
        return elementos == 0 ? true : false;
    }

    /**
     * Regresa el número de elementos en la Cola.
     *
     * @return el número de elementos en la Cola.
     */
    @Override
    public int getTamanio() {
        return elementos;
    }

    /**
     * Sobreescribe el constructor de un iterador.
     * 
     * @return un nuevo iterador.
     */
    @Override
    public Iterator<T> iterator() {
        return new IteradorCola();
    }

    /**
     * Regresa una cola representada en cadena.
     */
    @Override
    public String toString() {
        String s = "[";
        Nodo n = this.inicio;
        while (n != null) {
            if (n.siguiente == null) {
                s += n.elemento;
            } else {
                s += n.elemento + ",";
            }
            n = n.siguiente;
        }
        s += "]";
        return s;

    }

    /**
     * Método que nos dice si una cola es igual que o.
     * 
     * @param o objeto a comparar con la cola.
     * @return <code>true</code> si son iguales, <code>false</code> en otro caso.
     */
    public boolean equals(Object o) {

        boolean eq = true;
        Nodo aux1 = this.inicio;
        if (!(o instanceof Cola)) {
            return false;
        }
        Cola<T> l = (Cola<T>) o;
        Nodo aux2 = l.inicio;
        if (l.elementos != this.elementos) {
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

}
