package estructuras;
import java.util.NoSuchElementException;
import java.io.Serializable;

/**
 * <p>
 * Clase abstracta para modelar la estructura de datos Arbol Binario
 * </p>
 * <p>
 * Esta clase implementa una Lista genérica, es decir que es homogénea pero
 * puede tener elementos de cualquier tipo.
 * </p>
 * <p>
 * Puesto que todos los árboles binarios comparten algunas características
 * similares, esta clase sirve perfectamente para modelarlas. Sin embargo no es
 * lo suficientemente específica para modelar algun árbol completamente. Por lo
 * que la implementación final depende de las clases concretas que hereden de
 * ésta.
 * </p>
 * 
 * @author Alejandro Hernández Mora <alejandrohmora@ciencias.unam.mx>
 * @version 1.0
 */

public abstract class ArbolBinario<T> implements Coleccionable<T> , Serializable{
    /**
     * Clase interna protegida para nodos.
     */
    protected class Nodo<T> implements Serializable{

        /** El elemento del nodo. */
        public T elemento;
        /** Referencia a los nodos padre, e hijos. */
        public Nodo<T> padre, izquierdo, derecho;

        /**
         * Constructor único que recibe un elemento.
         * 
         * @param elemento el elemento del nodo.
         */
        public Nodo(T elemento) {
            this.elemento = elemento;
            padre = null;
            izquierdo = null;
            derecho = null;
        }

        /**
         * Nos dice si el nodo tiene un padre.
         * 
         * @return <tt>true</tt> si el nodo tiene padre, <tt>false</tt> en otro caso.
         */
        public boolean hayPadre() {
            return this.padre != null;
        }

        /**
         * Nos dice si el nodo tiene un izquierdo.
         * 
         * @return <tt>true</tt> si el nodo tiene izquierdo, <tt>false</tt> en otro
         *         caso.
         */
        public boolean hayIzquierdo() {
            return this.izquierdo != null;
        }

        /**
         * Nos dice si el nodo tiene un derecho.
         * 
         * @return <tt>true</tt> si el nodo tiene derecho, <tt>false</tt> en otro caso.
         */
        public boolean hayDerecho() {
            return this.derecho != null;
        }

        /**
         * Regresa la altura del nodo.
         * 
         * @return la altura del nodo.
         */
        public int altura() {
            if (this.derecho == null && this.izquierdo == null) {
                return 1;
            } else {
                int d = 0, i = 0;
                if (this.derecho != null)
                    d = derecho.altura();
                if (this.izquierdo != null)
                    i = izquierdo.altura();

                if (d > i) {
                    return d + 1;
                } else {
                    return i + 1;
                }

            }
        }

        /**
         * Regresa el elemento al que apunta el nodo.
         * 
         * @return el elemento al que apunta el nodo.
         */
        public T get() {
            return this.elemento;
        }

        /**
         * Compara el nodo con otro objeto. La comparación es <em>recursiva</em>. Las
         * clases que extiendan {@link Nodo} deben sobrecargar el método
         * {@link Nodo#equals}.
         * 
         * @param o el objeto con el cual se comparará el nodo.
         * @return <code>true</code> si el objeto es instancia de la clase {@link Nodo},
         *         su elemento es igual al elemento de éste nodo, y los descendientes de
         *         ambos son recursivamente iguales; <code>false</code> en otro caso.
         */
        @Override
        public boolean equals(Object o) {

            if (this == o) {
                return true;
            }

            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            @SuppressWarnings("unchecked")
            Nodo<T> c = (Nodo<T>) o;
            
            if (!this.elemento.equals(c.elemento)) {
                return false;
            }
            
            if (this.izquierdo != null && !this.izquierdo.equals(c.izquierdo)) {
                return false;
            } else if (this.izquierdo == null && c.izquierdo != null) {
                return false;
            }
          
            if (this.derecho != null && !this.derecho.equals(c.derecho)) {
                return false;
            } else if (this.derecho == null && c.derecho != null) {
                return false;
            }
          
            return true;

        }

        /**
         * Regresa una representación en cadena del nodo.
         * 
         * @return una representación en cadena del nodo.
         */
        public String toString() {
            return this.elemento.toString();
        }
    }

    /** La raíz del árbol. */
    protected Nodo<T> raiz;
    /** El número de elementos */
    protected int tamanio;

    /**
     * Constructor sin parámetros.
     */
    public ArbolBinario() {
        this.raiz = null;
        this.tamanio = 0;
    }

    /**
     * Construye un árbol binario a partir de una colección. El árbol binario tendrá
     * los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol binario.
     */
    public ArbolBinario(Coleccionable<T> coleccion) {
        for (T e : coleccion) {
            agrega(e);
        }
    }

    /**
     * Construye un nuevo nodo, usando una instancia de {@link Nodo}. Para crear
     * nodos se debe utilizar este método en lugar del operador <code>new</code>,
     * para que las clases herederas de ésta puedan sobrecargarlo y permitir que
     * cada estructura de árbol binario utilice distintos tipos de nodos.
     * 
     * @param elemento el elemento dentro del nodo.
     * @return un nuevo nodo con el elemento recibido dentro del mismo.
     */
    protected Nodo<T> nuevoNodo(T elemento) {
        return new Nodo<T>(elemento);
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol es la altura de su raíz.
     * 
     * @return la altura del árbol.
     */
    public int altura() {
        return this.raiz.altura();
    }

    /**
     * Regresa el número de elementos que se han agregado al árbol.
     * 
     * @return el número de elementos en el árbol.
     */
    @Override
    public int getTamanio() {
        return this.tamanio;
    }

    /**
     * Regresa el nodo que contiene la raíz del árbol.
     * 
     * @return el nodo que contiene la raíz del árbol.
     * @throws NoSuchElementException si el árbol es vacío.
     */
    public Nodo<T> raiz() {
        if (esVacio()) {
            throw new NoSuchElementException();
        }
        return this.raiz;
    }

    /**
     * Nos dice si el árbol es vacío.
     * 
     * @return <code>true</code> si el árbol es vacío, <code>false</code> en otro
     *         caso.
     */
    @Override
    public boolean esVacio() {
        // Aqui va tu codigo
        return raiz == null;
    }

    /**
     * Limpia el árbol de elementos, dejándolo vacío.
     */
    public void limpia() {
        this.raiz = null;
        tamanio = 0;
    }

    /**
     * Regresa una Cola con el los elementos en inorden del árbol.
     * 
     * @return Cola con los elementos del arbol.
     */
    public Lista<T> inOrden() {
        Lista<T> aux = new Lista<>();
        inordenAux(this.raiz, aux);
        return aux;
    }

    /**
     * Regresa una Cola con el los elementos en inorden del árbol.
     * 
     * @return Cola con los elementos del arbol.
     */

    public Lista<T> preOrden() {
        Lista<T> aux = new Lista<>();
        preordenAux(this.raiz, aux);
        return  aux;
    }

    /**
     * Regresa una Cola con el los elementos en inorden del árbol.
     * 
     * @return Cola con los elementos del arbol.
     */
    public Lista<T> postOrden() {
        Lista<T> aux = new Lista<>();
        postordenAux(this.raiz, aux);
        return aux;
    }

    private void inordenAux(Nodo<T> n, Lista<T> l) {
        if (n == null) {
            return;
        }
        inordenAux(n.izquierdo, l);
        l.agregarAlFinal(n.elemento);
        inordenAux(n.derecho, l);
    }

    private void preordenAux(Nodo<T> n, Lista<T> l) {
        if (n == null) {
            return;
        }
        l.agregarAlFinal(n.elemento);
        preordenAux(n.izquierdo, l);
        preordenAux(n.derecho, l);
    }

    private void postordenAux(Nodo<T> n, Lista<T> l) {
        if (n == null) {
            return;
        }
        postordenAux(n.izquierdo, l);
        postordenAux(n.derecho, l);
        l.agregarAlFinal(n.elemento);
    }

    /**
     * Compara el árbol con un objeto.
     * 
     * @param o el objeto con el que queremos comparar el árbol.
     * @return <code>true</code> si el objeto recibido es un árbol binario y los
     *         árboles son iguales; <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ArbolBinario c = (ArbolBinario) o;
        if (this.tamanio != c.tamanio) {
            return false;
        }
        if (this.esVacio() && c.esVacio()) {
            return true;
        }
        return this.raiz.equals(c.raiz);
    }

    /**
     * Regresa una representación en cadena del árbol.
     * 
     * @return una representación en cadena del árbol.
     */
    @Override
    public String toString() {
        if (raiz == null)
            return "";

        boolean[] r = new boolean[altura() + 1];
        for (int i = 0; i < altura() + 1; i++)
            r[i] = false;
        return cadena(raiz, 0, r);

    }

    private String cadena(Nodo v, int n, boolean[] r) {
        String s = v + "\n";
        r[n] = true;
        if (v.izquierdo != null && v.derecho != null) {
            s += dibujaEspacios(n, r);
            s += "├─›";
            s += cadena(v.izquierdo, n + 1, r);
            s += dibujaEspacios(n, r);
            s += "└─»";
            r[n] = false;
            s += cadena(v.derecho, n + 1, r);
        } else if (v.izquierdo != null) {
            s += dibujaEspacios(n, r);
            s += "└─›";
            r[n] = false;
            s += cadena(v.izquierdo, n + 1, r);
        } else if (v.derecho != null) {
            s += dibujaEspacios(n, r);
            s += "└─»";
            r[n] = false;
            s += cadena(v.derecho, n + 1, r);
        }
        return s;
    }

    private String dibujaEspacios(int n, boolean[] r) {
        String s = "";
        for (int i = 0; i < n; i++)
            if (r[i])
                s += "│  ";
            else
                s += "   ";
        return s;
    }
}
