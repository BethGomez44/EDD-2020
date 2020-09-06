import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.Comparable;

/**
 * <p>
 * Clase para modelar árboles binarios de búsqueda genéricos.
 * </p>
 *
 * <p>
 * Un árbol instancia de esta clase siempre cumple que:
 * </p>
 * <ul>
 * <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 * descendientes por la izquierda.</li>
 * <li>Cualquier elemento en el árbol es menor o igual que todos sus
 * descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioBusqueda<T extends Comparable<T>> extends ArbolBinario<T> {

    /* Clase privada para iteradores de árboles binarios ordenados. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los nodos en DFS in-order. */
        private Pila<ArbolBinario.Nodo> pila;

        /* Construye un iterador con el nodo recibido. */
        public Iterador() {
            pila = new Pila();
            Nodo<T> aux = raiz;

            while (aux != null) {
                pila.push(aux);
                aux = aux.izquierdo;
            }
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override
        public boolean hasNext() {
            return !pila.esVacio();

        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override
        public T next() throws NoSuchElementException {
            if (hasNext()) {
                Nodo<T> nodo = pila.peek();
                pila.pop();
                if (nodo.derecho != null) {
                    Nodo<T> aux = nodo.derecho;

                    while (aux != null) {
                        pila.push(aux);
                        aux = aux.izquierdo;
                    }
                }
                return nodo.elemento;
            } else {
                throw new NoSuchElementException("No existe un elemento siguiente.");
            }
        }
    }

    /**
     * Constructor que no recibe parámeteros. {@link ArbolBinario}.
     */
    public ArbolBinarioBusqueda() {
        super();
    }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol binario
     *                  ordenado.
     */

    public ArbolBinarioBusqueda(Coleccionable<T> coleccion) {
        super(coleccion);
    }

    protected void agregaNodo(Nodo<T> n, Nodo<T> nuevo) {
        if (tamanio == 0) {
            raiz = nuevo;
        } else {
            while (n != null) {
                // Caso si hay que agregar por el lado izquierdo
                if (nuevo.elemento.compareTo(n.elemento) <= 0) {
                    // Caso en donde ya hay que agregar
                    if (n.izquierdo == null) {
                        n.izquierdo = nuevo;
                        nuevo.padre = n;
                        n = null;
                        // Caso en el que hacemos recursión.
                    } else {
                        n = n.izquierdo;
                    }
                } else {
                    // Todos los casos del lado derecho
                    if (n.derecho == null) {
                        n.derecho = nuevo;
                        nuevo.padre = n;
                        n = null;
                        // Caso en el que hacemos recursión.
                    } else {
                        n = n.derecho;
                    }
                }
            }
        }
        tamanio++;
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * 
     * @param elemento el elemento a agregar.
     */
    @Override
    public void agrega(T elemento) {
        Nodo nuevo = nuevoNodo(elemento);
        agregaNodo(raiz, nuevo);
    }

    protected Nodo<T> eliminaNodo(Nodo<T> n) {
        // En todos los casos hay que ver que pasa si eliminamos la raiz
        // Caso en el que n es hoja
        Nodo<T> aux = null;

        if (n.padre != this.raiz) {
            aux = n.padre;
        }

        if (n.izquierdo == null && n.derecho == null) {
            if (n.padre != null) {
                // Caso n es hoja izquierda
                if (esHijoIzquierdo(n)) {
                    n.padre.izquierdo = null;
                    n.padre = null;
                    // Caso n es hoja derecha.
                } else {
                    n.padre.derecho = null;
                    n.padre = null;
                }

            } else {
                this.raiz = null;
            }
        } else {
            // Caso en el que n no tiene alguno de sus hijos
            if (n.izquierdo == null || n.derecho == null) {
                // Caso donde n no es hoja y tiene hijo derecho.
                if (n.izquierdo == null) {
                    if (n.padre != null) {
                        if (esHijoIzquierdo(n)) {
                            n.padre.izquierdo = n.derecho;
                            n.derecho.padre = n.padre;
                        } else {
                            n.padre.derecho = n.derecho;
                            n.derecho.padre = n.padre;
                        }
                    } else {
                        n.derecho.padre = null;
                        raiz = n.derecho;
                        n.derecho = null;
                    }
                } else {
                    if (esHijoDerecho(n)) {
                        n.padre.derecho = n.izquierdo;
                        n.izquierdo.padre = n.padre;
                    } else if (n != this.raiz) {
                        n.padre.izquierdo = n.izquierdo;
                        n.izquierdo.padre = n.padre;
                    }
                }
            } else {
                // Caso en el que n tiene a sus dos hijos
                Nodo<T> min = minimoEnSubarbolDerecho(n);
                n.elemento = min.elemento;
                return eliminaNodo(min);
            }
        }
        tamanio--;
        return aux;
    }

    protected boolean esHijoIzquierdo(Nodo<T> n) {
        if (!n.hayPadre()) {
            return false;
        } else {
            return n.padre.izquierdo == n;
        }
    }

    protected boolean esHijoDerecho(Nodo<T> n) {
        if (!n.hayPadre()) {
            return false;
        } else {
            return n.padre.derecho == n;
        }
    }

    protected Nodo<T> maximoNodo(Nodo<T> n) {
        while (n.hayDerecho()) {
            n = n.derecho;
        }
        return n;
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * 
     * @param elemento el elemento a eliminar.
     */
    @Override
    public void elimina(T elemento) {
        Nodo n = buscaNodo(raiz, elemento);
        eliminaNodo(n);
    }

    protected Nodo maximoEnSubarbolIzquierdo(Nodo n) {
        return maximoNodo(n.izquierdo);
    }

    protected Nodo minimoEnSubarbolDerecho(Nodo n) {
        return minimoNodo(n.derecho);
    }

    protected Nodo<T> minimoNodo(Nodo<T> n) {
        while (n.hayIzquierdo()) {
            n = n.izquierdo;
        }
        return n;
    }

    /**
     * Nos dice si un elemento está contenido en el arbol.
     * 
     * @param elemento el elemento que queremos verificar si está contenido en la
     *                 arbol.
     * @return <code>true</code> si el elemento está contenido en el arbol,
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean contiene(T elemento) {
        Nodo<T> n = nuevoNodo(elemento);
        return buscaNodo(n, elemento) != null;
    }

    protected Nodo<T> buscaNodo(Nodo<T> n, T elemento) {
        if (n == null) {
            return null;
        } else if (n.get().equals(elemento)) {
            return n;
        }
        Nodo nodoIzq = buscaNodo(n.izquierdo, elemento);
        Nodo nodoDer = buscaNodo(n.derecho, elemento);
        return nodoIzq != null ? nodoIzq : nodoDer;
    }

    /**
     * Gira el árbol a la derecha sobre el nodo recibido. Si el nodo no tiene hijo
     * izquierdo, el método no hace nada.
     * 
     * @param nodo el nodo sobre el que vamos a girar.
     */
    protected void giraDerecha(Nodo<T> nodo) {
        if (nodo == null || !nodo.hayIzquierdo()) {
            return;
        }

        Nodo nodoIzq = nodo.izquierdo;
        nodoIzq.padre = nodo.padre;

        if (nodo != this.raiz()) {
            if (esHijoIzquierdo(nodo)) {
                nodo.padre.izquierdo = nodoIzq;
            } else {
                nodo.padre.derecho = nodoIzq;
            }
        } else {
            this.raiz = nodoIzq;
        }
        nodo.izquierdo = nodoIzq.derecho;
        if (nodoIzq.hayDerecho()) {
            nodoIzq.derecho.padre = nodo;
        }
        nodoIzq.derecho = nodo;
        nodo.padre = nodoIzq;

    }

    /**
     * Gira el árbol a la izquierda sobre el nodo recibido. Si el nodo no tiene hijo
     * derecho, el método no hace nada.
     * 
     * @param nodo el nodo sobre el que vamos a girar.
     */
    protected void giraIzquierda(Nodo<T> nodo) {
        if (nodo == null || !nodo.hayDerecho()) {
            return;
        }

        Nodo nodoDer = nodo.derecho;
        nodoDer.padre = nodo.padre;

        if (nodo != this.raiz()) {
            if (esHijoIzquierdo(nodo)) {
                nodo.padre.izquierdo = nodoDer;
            } else {
                nodo.padre.derecho = nodoDer;
            }
        } else {
            this.raiz = nodoDer;
        }
        nodo.derecho = nodoDer.izquierdo;
        if (nodoDer.hayIzquierdo()) {
            nodoDer.izquierdo.padre = nodo;
        }
        nodoDer.izquierdo = nodo;
        nodo.padre = nodoDer;
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * 
     * @return un iterador para iterar el árbol.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }

}
