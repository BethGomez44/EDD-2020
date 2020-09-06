import java.util.NoSuchElementException;

/**
 * <p>
 * Clase para árboles AVL.
 * </p>
 *
 * <p>
 * Un árbol AVL cumple que para cada uno de sus nodos, la diferencia entre la
 * áltura de sus subárboles izquierdo y derecho está entre -1 y 1.
 * </p>
 */
public class ArbolAVL<T extends Comparable<T>> extends ArbolBinarioBusqueda<T> {

    /**
     * Clase interna protegida para nodos de árboles AVL. La única diferencia con
     * los nodos de árbol binario, es que tienen una variable de clase para la
     * altura del nodo.
     */
    protected class NodoAVL extends Nodo {

        /** La altura del nodo. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * 
         * @param elemento el elemento del nodo.
         */
        public NodoAVL(T elemento) {
            super(elemento);
            altura = 1;
        }

        /**
         * Regresa la altura del nodo.
         * 
         * @return la altura del nodo.
         */
        public int altura() {
            return this == null ? 0 : this.altura;
        }

        /**
         * Regresa una representación en cadena del nodo AVL.
         * 
         * @return una representación en cadena del nodo AVL.
         */
        @Override
        public String toString() {
            int balance = balance(this);
            return this.elemento + " " + this.altura + "/" + balance(this);
        }

        /**
         * Compara el nodo con otro objeto. La comparación es <em>recursiva</em>.
         * 
         * @param o el objeto con el cual se comparará el nodo.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link nodoAVL}, su elemento es igual al elemento de éste nodo, los
         *         descendientes de ambos son recursivamente iguales, y las alturas son
         *         iguales; <code>false</code> en otro caso.
         */
        @Override
        public boolean equals(Object o) {
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            NodoAVL nodo = (NodoAVL) o;
            if (this == null && nodo == null) {
                return true;
            }
            if (this == null && nodo != null || this != null && nodo == null || !this.elemento.equals(nodo.elemento)
                    || this.altura != nodo.altura) {
                return false;
            }
            return this.izquierdo.equals(nodo.izquierdo) && this.derecho.equals(nodo.derecho);
        }

    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros de
     * {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() {
        super();
    }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol rojinegro
     * tiene los mismos elementos que la colección recibida.
     * 
     * @param coleccion la colección a partir de la cual creamos el árbol rojinegro.
     */
    public ArbolAVL(Coleccionable<T> coleccion) {
        super(coleccion);
    }

    /**
     * Construye un nuevo nodo, usando una instancia de {@link nodoAVL}.
     * 
     * @param elemento el elemento dentro del nodo.
     * @return un nuevo nodo con el elemento recibido dentro del mismo.
     */
    @Override
    protected NodoAVL nuevoNodo(T elemento) {
        return new NodoAVL(elemento);
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método
     * {@link ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo
     * como sea necesario.
     * 
     * @param elemento el elemento a agregar.
     */
    @Override
    public void agrega(T elemento) throws IllegalArgumentException {
        NodoAVL nodo = nuevoNodo(elemento);
        super.agregaNodo(super.raiz, nodoAVL(nodo));
        calcularAltura(nodo);
        rebalanceo(nodoAVL(nodo));
    }

    /**
     * Método privado que rebalance el árbol.
     */
    private void rebalanceo(NodoAVL nodo) {
        NodoAVL aux1, aux2;
        if (nodo == null) {
            return;
        }
        this.calcularAltura(nodo);
        if (this.balance(nodo) == -2) {
            if (this.balance(nodoAVL(nodo.derecho)) == 1) {
                aux1 = nodoAVL(nodo.derecho);
                super.giraDerecha(aux1);
                this.calcularAltura(aux1);
                this.calcularAltura(nodoAVL(aux1.padre));
            }
            super.giraIzquierda(nodo);
            this.calcularAltura(nodo);
        } else if (this.balance(nodo) == 2) {
            if (this.balance(nodoAVL(nodo.izquierdo)) == -1) {
                aux2 = nodoAVL(nodo.izquierdo);
                super.giraIzquierda(aux2);
                this.calcularAltura(aux2);
                this.calcularAltura(nodoAVL(aux2.padre));
            }
            super.giraDerecha(nodo);
            this.calcularAltura(nodo);
        }
        this.rebalanceo(nodoAVL(nodo.padre));
    }

    private int balance(NodoAVL nodo) {
        return altura(nodoAVL(nodo.izquierdo)) - altura(nodoAVL(nodo.derecho));
    }

    private int altura(NodoAVL nodo) {
        return nodo == null ? 0 : nodo.altura;
    }

    private int calcularAltura(NodoAVL nodo) {
        int izquierdoH, derechoH;
        izquierdoH = altura(nodoAVL(nodo.izquierdo));
        derechoH = altura(nodoAVL(nodo.derecho));
        nodo.altura = Math.max(izquierdoH, derechoH) + 1;
        return nodo.altura;
    }

    /**
     * Elimina un elemento del árbol. El método elimina el nodo que contiene el
     * elemento, y gira el árbol como sea necesario para rebalancearlo.
     * 
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override
    public void elimina(T elemento) {
        Nodo nodo = super.buscaNodo(raiz, elemento);
        super.elimina(elemento);
        calcularAltura(nodoAVL(nodo));
        rebalanceo(nodoAVL(nodo));
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL no
     * pueden ser girados a la derecha por los usuarios de la clase, porque se
     * desbalancean.
     * 
     * @param nodo el nodo sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override
    protected void giraDerecha(Nodo<T> nodo) {
        throw new UnsupportedOperationException(
                "Los árboles AVL no  pueden " + "girar a la izquierda por el " + "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL no
     * pueden ser girados a la izquierda por los usuarios de la clase, porque se
     * desbalancean.
     * 
     * @param nodo el nodo sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override
    protected void giraIzquierda(Nodo<T> nodo) {
        throw new UnsupportedOperationException(
                "Los árboles AVL no  pueden " + "girar a la derecha por el " + "usuario.");
    }

    /**
     * Realiza cast del nodo (visto como instancia de {@link ArbolBinario.Nodo}) en
     * nodo (visto como instancia de {@link NodoAVL}).
     * 
     * @param nodo el nodo de árbol binario que queremos como nodo AVL.
     * @return el nodo recibido visto como nodo AVL.
     * @throws ClassCastException si el nodo no es instancia de {@link NodoAVL}.
     */
    protected NodoAVL nodoAVL(Nodo<T> nodo) {
        return (NodoAVL) nodo;
    }

}
