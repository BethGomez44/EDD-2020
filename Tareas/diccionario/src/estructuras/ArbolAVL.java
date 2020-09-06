package estructuras;
import java.io.Serializable;
// Del Moral Morales Francisco Emmanuel
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
public class ArbolAVL<T extends Comparable<T>> extends ArbolBinarioBusqueda<T> implements Serializable {

    /**
     * Clase interna protegida para nodos de árboles AVL. La única diferencia con
     * los nodos de árbol binario, es que tienen una variable de clase para la
     * altura del nodo.
     */
    protected class NodoAVL<T> extends Nodo<T> implements Serializable {

        /** La altura del nodo. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * 
         * @param elemento el elemento del nodo.
         */
        public NodoAVL(T elemento) {
            super(elemento);
            this.altura = 1;
        }

        /**
         * Regresa la altura del nodo.
         * 
         * @return la altura del nodo.
         */
        @Override
        public int altura() {
            return altura;
        }

        /**
         * Regresa una representación en cadena del nodo AVL.
         * 
         * @return una representación en cadena del nodo AVL.
         */
        @Override
        public String toString() {
            return "(" + elemento.toString() + ", " + this.altura + ")";
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
            return super.equals(o);
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

    protected Nodo<T> nuevoNodo(T elemento) {
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
    public void agrega(T elemento) {
        if (elemento != null) {
            NodoAVL<T> n = (NodoAVL<T>) nuevoNodo(elemento);
            agregaNodo(raiz, n);
            tamanio++;
            actualizarAltura(n);
            rebalanceo(n);
        } else {
            throw new IllegalArgumentException();
        }
    }

    /**
     * Método privado que rebalance el árbol.
     */
    private void rebalanceo(NodoAVL<T> nodo) {
        if (nodo != null) {
            NodoAVL<T> izquierdo, derecho, aux_der, aux_izq;
            izquierdo = (NodoAVL<T>) nodo.izquierdo;
            derecho = (NodoAVL<T>) nodo.derecho;
            int izq_h, der_h;
            izq_h = izquierdo == null ? 0 : izquierdo.altura();
            der_h = derecho == null ? 0 : derecho.altura();
            int balance = izq_h - der_h;

            if (balance == -2) {
                aux_der = (NodoAVL<T>) derecho.derecho;
                aux_izq = (NodoAVL<T>) derecho.izquierdo;
                izq_h = aux_izq == null ? 0 : aux_izq.altura();
                der_h = aux_der == null ? 0 : aux_der.altura();

                if (izq_h - der_h == 1) {
                    super.giraDerecha(derecho);
                    actualizarAltura(derecho);
                }
                super.giraIzquierda(nodo);
            } else if (balance == 2) {
                aux_der = (NodoAVL<T>) izquierdo.derecho;
                aux_izq = (NodoAVL<T>) izquierdo.izquierdo;
                izq_h = aux_izq == null ? 0 : aux_izq.altura();
                der_h = aux_der == null ? 0 : aux_der.altura();

                if (izq_h - der_h == -1) {
                    super.giraIzquierda(izquierdo);
                    actualizarAltura(izquierdo);
                }
                super.giraDerecha(nodo);
            }
            actualizarAltura(nodo);
            rebalanceo((NodoAVL<T>) nodo.padre);
        }

    }

    /**
     * Metodo que actualiza recursivamente hacia el padre la altura
     * 
     * @param nodo nodo que cambio su altura
     */
    private void actualizarAltura(NodoAVL<T> nodo) {
        if (nodo != null) {
            int h_izquierdo = nodo.izquierdo == null ? 0 : nodo.izquierdo.altura();
            int h_derecho = nodo.derecho == null ? 0 : nodo.derecho.altura();

            nodo.altura = maximo(h_izquierdo, h_derecho) + 1;
            if (nodo != this.raiz) {
                actualizarAltura((NodoAVL<T>) nodo.padre);
            }
        }

    }

    /**
     * Elimina un elemento del árbol. El método elimina el nodo que contiene el
     * elemento, y gira el árbol como sea necesario para rebalancearlo.
     * 
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override
    public void elimina(T elemento) {
        Nodo n = super.buscaNodo(raiz, elemento);
        if (n != null) {
            n = eliminaNodo(n);
            actualizarAltura((NodoAVL<T>) n);
            rebalanceo((NodoAVL<T>) n);
        }else{
            System.out.println("No lo encontre");
        }

    }

     /**
     * Busca cierto objeto en el arbol para su consulta
     * 
     * @param elemento elemento a buscar
     * @return null si no se encuentra , el objeto buscado en caso contrario.
     */
    public T buscar(T elemento) {
        Nodo<T> exit = super.buscaNodo(raiz, elemento);
        return exit != null ? exit.elemento : null;
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
    public void giraDerecha(Nodo<T> nodo) {
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
    public void giraIzquierda(Nodo<T> nodo) {
        throw new UnsupportedOperationException(
                "Los árboles AVL no  pueden " + "girar a la derecha por el " + "usuario.");
    }

    private int maximo(int i, int j) {
        return (i > j ? i : j);
    }

}
