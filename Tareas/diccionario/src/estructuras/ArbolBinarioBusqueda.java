package estructuras;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.lang.Comparable;
import java.io.Serializable;

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
public class ArbolBinarioBusqueda<T extends Comparable<T>> extends ArbolBinario<T> implements Serializable {

    /* Clase privada para iteradores de árboles binarios ordenados. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los nodos en DFS in-order. */
        private Pila<Nodo> pila;

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
        if (this.esVacio()) {
            raiz = nuevo;
            return;
        }
        if (nuevo.elemento.compareTo(n.elemento) < 0) {
            if (n.izquierdo == null) {
                n.izquierdo = nuevo;
                nuevo.padre = n;

            } else {
                agregaNodo(n.izquierdo, nuevo);
            }
        } else {

            if (n.derecho == null) {
                n.derecho = nuevo;
                nuevo.padre = n;

            } else {
                agregaNodo(n.derecho, nuevo);

            }
        }

    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * 
     * @param elemento el elemento a agregar.
     */
    @Override
    public void agrega(T elemento) {
        if (elemento != null) {
            Nodo aux = nuevoNodo(elemento);
            agregaNodo(raiz, aux);
            tamanio++;
        } else {
            throw new IllegalArgumentException();
        }

    }

    protected Nodo<T> eliminaNodo(Nodo<T> n) {
        Nodo exit;
        if (n.derecho == null && n.izquierdo == null) { // Cuando es una hoja
            exit = n.padre;
            if (tamanio == 1) {
                this.limpia();
            } else if (n.padre.derecho == n) {
                n.padre.derecho = null;
            } else {
                n.padre.izquierdo = null;
            }
        } else if (n.derecho == null || n.izquierdo == null) { // Cuando alguno de sus hijos es null

            if (n.derecho == null) {
                n.elemento = n.izquierdo.elemento;

                n.derecho = n.izquierdo.derecho;
                n.izquierdo = n.izquierdo.izquierdo;
            } else {
                n.elemento = n.derecho.elemento;
                n.izquierdo = n.derecho.izquierdo;
                n.derecho = n.derecho.derecho;
            }

            if (n.derecho != null) {
                n.derecho.padre = n;
            }

            if (n.izquierdo != null) {
                n.izquierdo.padre = n;
            }
            exit = n;
        } else { // Cuando los dos nodos son sub-arboles
            Nodo<T> aux = minimoEnSubarbolDerecho(n.derecho); // otengo el minimo del subarbol derecho
            // realizamos intercambio de valores;
            n.elemento = aux.elemento;
            // llamada recursiva
            return eliminaNodo(aux);
        }
        tamanio--;
        return exit;
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
        if (elemento != null) {
            Nodo<T> n = buscaNodo(raiz, elemento);
            if (n != null) {
                eliminaNodo(n);
            }
        } else {
            throw new IllegalArgumentException();
        }
    }

    private Nodo<T> minimoEnSubarbolDerecho(Nodo n) {
        if (!n.hayIzquierdo()) {
            return n;
        } else {
            return minimoEnSubarbolDerecho(n.izquierdo);
        }
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
        return buscaNodo(raiz, elemento) != null;
    }
    /**
     * Retorna el elemento de menor maagnitud contenido en el arbol
     * @return
     */
    public T minElemento(){
        return minimoEnArbol(raiz).elemento;
    }

    public T maxElemento(){
        return maximoEnArbol(raiz).elemento;

    }

    private Nodo<T> maximoEnArbol(Nodo n) {
        if (!n.hayDerecho()) {
            return n;
        } else {
            return maximoEnArbol(n.derecho);
        }
    }

    private Nodo<T> minimoEnArbol(Nodo n) {
        if (!n.hayIzquierdo()) {
            return n;
        } else {
            return minimoEnArbol(n.izquierdo);
        }
    }


    protected Nodo<T> buscaNodo(Nodo<T> n, T elemento) {
        int i = elemento.compareTo(n.elemento);
        if (  i == 0) {
            return n;
        } else if (i > 0) {
            if (n.hayDerecho()) {
                return buscaNodo(n.derecho, elemento);
            }
        } else {
            if (n.hayIzquierdo()) {
                return buscaNodo(n.izquierdo, elemento);
            }
        }
        return null;
    }

    /**
     * Gira el árbol a la derecha sobre el nodo recibido. Si el nodo no tiene hijo
     * izquierdo, el método no hace nada.
     * 
     * @param nodo el nodo sobre el que vamos a girar.
     */
    public void giraDerecha(Nodo<T> nodo) {
        if (nodo != null && nodo.hayIzquierdo()) {

            if (nodo == this.raiz) {
                this.raiz = nodo.izquierdo;
                nodo.izquierdo.padre = null;
            } else {
                // referencia padre-nodo
                if (nodo.padre.izquierdo == nodo) {
                    nodo.padre.izquierdo = nodo.izquierdo;
                } else {
                    nodo.padre.derecho = nodo.izquierdo;
                }
                nodo.izquierdo.padre = nodo.padre;
            }
            // referencia nodo-padre
            nodo.padre = nodo.izquierdo;
            Nodo<T> aux = nodo.izquierdo.derecho;

            nodo.izquierdo.derecho = nodo;

            nodo.izquierdo = aux;
            if (aux != null) {
                aux.padre = nodo;
            }

        }
    }

    /**
     * Gira el árbol a la izquierda sobre el nodo recibido. Si el nodo no tiene hijo
     * derecho, el método no hace nada.
     * 
     * @param nodo el nodo sobre el que vamos a girar.
     */
    public void giraIzquierda(Nodo<T> nodo) {
        if (nodo != null && nodo.hayDerecho()) {

            if (nodo == this.raiz) {
                this.raiz = nodo.derecho;
                nodo.derecho.padre = null;
            } else {
                // referencia padre-nodo
                if (nodo.padre.izquierdo == nodo) {
                    nodo.padre.izquierdo = nodo.derecho;
                } else {
                    nodo.padre.derecho = nodo.derecho;
                }
                nodo.derecho.padre = nodo.padre;
            }
            // referencia nodo-padre
            nodo.padre = nodo.derecho;
            Nodo<T> aux = nodo.derecho.izquierdo;

            nodo.derecho.izquierdo = nodo;
            nodo.derecho = aux;
            if (aux != null) {
                aux.padre = nodo;
            }

        }
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