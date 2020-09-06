import java.util.Iterator;

/**
 * <p> Clase concreta para modelar la estructura de datos Lista</p>
 * <p>Esta clase implementa una Lista genérica, es decir que es homogénea pero
 * puede tener elementos de cualquier tipo.
 * @author Alejandro Hernández Mora <alejandrohmora@ciencias.unam.mx>
 * @version 1.0
 */
public class Lista<T> implements Listable<T>, Iterable<T>{

    /* Clase interna para construir la estructura */
    private class Nodo{
      	/* Referencias a los nodos anterior y siguiente */
      	public Nodo anterior, siguiente;
      	/* El elemento que almacena un nodo */
      	public T elemento;

      	/* Unico constructor de la clase */
      	public Nodo(T elemento){
      	    this.elemento = elemento;
      	}

        public boolean equals(Nodo n){
            return elemento.equals(n.elemento);
        }
    }

    private class IteradorLista implements Iterator<T>{
        /* La lista a recorrer*/
        /* Elementos del centinela que recorre la lista*/
        private Lista<T>.Nodo siguiente;


        public IteradorLista(){
            this.siguiente = cabeza;//(Lista<T>.Nodo) cabeza;
        }
        @Override
        public boolean hasNext() {
            return this.siguiente != null;
        }

        @Override
        public T next() {
            T elem = this.siguiente.elemento;
            this.siguiente = siguiente.siguiente;
            return elem;
        }

        @Override
        public void remove() {
            Iterator.super.remove(); //To change body of generated methods, choose Tools | Templates.
        }
    }

    /* Atributos de la lista */
    private Nodo cabeza, cola;
    private int longitud;

    /**
     *  Constructor por omisión de la clase, no recibe parámetros.
     *  Crea una nueva lista con longitud 0.
     **/
    public Lista(){
        longitud = 0;
        cabeza = null;
        cola = null;
    }

    /**
     *  Constructor copia de la clase. Recibe una lista con elementos.
     *  Crea una nueva lista exactamente igual a la que recibimos como parámetro.
     **/
    public Lista(Lista<T> l){
        Lista<T> nueva = l.copia();
        this.longitud = nueva.longitud;
        this.cabeza = nueva.cabeza;
        this.cola = nueva.cola;
      }

    /**
     *  Constructor de la clase que recibe parámetros.
     *  Crea una nueva lista con los elementos de la estructura iterable que recibe como parámetro.
     **/
    public Lista(Iterable<T> iterable){
        longitud = 0;
        cabeza = null;
        cola = null;
        for(T elem: iterable)
           agregar(elem);
    }

    /**
     * Método que nos dice si las lista está vacía.
     * @return <code>true</code> si el conjunto está vacío, <code>false</code>
     * en otro caso.
     */
    public boolean esVacia(){
        return longitud == 0;
    }

    /**
     * Método para eliminar todos los elementos de una lista
     */
    public void vaciar(){
        cabeza = null;
        cola = null;
        longitud = 0;
    }

    /**
     * Método para obtener el tamaño de la lista
     * @return tamanio Número de elementos de la lista.
     **/
    public int longitud(){
        return longitud;
    }

    /**
     * Método para agregar un elemento a la lista.
     * @param elemento Objeto que se agregará a la lista.
     */
    public void agregar(T elemento){
        agregarAlInicio(elemento);
    }

    /**
     * Método para agregar al inicio un elemento a la lista.
     * @param elemento Objeto que se agregará al inicio de la lista.
     */
    public void agregarAlInicio(T elemento){
        Nodo nuevo = new Nodo(elemento);
        if (esVacia()) {
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cabeza.anterior = nuevo;
            nuevo.siguiente = cabeza;
            cabeza = nuevo;
        }

        longitud ++;
    }

    /**
     * Método para agregar al final un elemento a la lista.
     * @param elemento Objeto que se agregará al inicio de la lista.
     */
    public void agregarAlFinal(T elemento){
        Nodo nuevo = new Nodo(elemento);
        if(esVacia()){
            cabeza = nuevo;
            cola = nuevo;
        } else {
            cola.siguiente = nuevo;
            nuevo.anterior = cola;
            cola = nuevo;
        }

        longitud ++;
    }

    /**
     * Método para verificar si un elemento pertenece a la lista y regresa una
     * una referencia al nodo que lo contiene.
     * @param elemento Objeto que se va a buscar en la lista.
     * @return aux Referencia al nodo que contiene al elemento, en otro caso null.
     */
    public Nodo buscaNodo(T elemento){
        Nodo aux = cabeza;
        while (aux != null) {
            if (aux.elemento.equals(elemento))
                return aux;
            aux = aux.siguiente;
        }
        return null;
    }

    /**
     * Método para verificar si un elemento pertenece a la lista.
     * @param elemento Objeto que se va a buscar en la lista.
     * @return <code>true</code> si el elemento esta en el lista y false en otro caso.
     */
    public boolean contiene(T elemento){
        return buscaNodo(elemento) != null;
    }

    /**
     * Método para eliminar un elemento de la lista.
     * @param elemento Objeto que se eliminara de la lista.
     */
    public void eliminar(T elemento){
        Nodo nodoEliminar = buscaNodo(elemento);
        if (longitud == 1) {
            vaciar();
            return;
        }
        if (nodoEliminar == cabeza) {
            cabeza = cabeza.siguiente;
            cabeza.anterior = null;
        } else if (nodoEliminar == cola) {
            cola = cola.anterior;
            cola.siguiente = null;
        } else {
            nodoEliminar.anterior.siguiente = nodoEliminar.siguiente;
            nodoEliminar.siguiente.anterior = nodoEliminar.anterior;
        }

        longitud --;
    }

    /**
     * Método que devuelve la posición en la lista que tiene la primera
     * aparición del <code> elemento</code>.
     * @param elemento El elemnto del cuál queremos saber su posición.
     * @return i la posición del elemento en la lista, -1, si no se encuentra en ésta.
     */
    public int indiceDe(T elemento){
        int indice = 0;
        Nodo aux = cabeza;
        while (aux != null) {
            if (aux.elemento.equals(elemento))
                return indice;
            aux = aux.siguiente;
            indice ++;
        }
        return -1;
    }

    /**
     * Método que nos dice en qué posición está un elemento en la lista
     * @param i La posición cuyo elemento deseamos conocer.
     * @return <code> elemento </code> El elemento que contiene la lista,
     * <code>null</code> si no se encuentra
     * @throws IndexOutOfBoundsException Si el índice es < 0 o >longitud()
     */
    public T getElemento(int i)throws IndexOutOfBoundsException{
        if (i < 0 || i > longitud) {
            throw new IndexOutOfBoundsException("Índice fuera de rango.");
        }

        int c = 0;

        for (T elem: this )
            if (c ++ == i)
                return elem;

        return null;
    }

    /**
     * Método que devuelve una copia de la lista, pero en orden inverso
     * @return Una copia con la lista l revés.
     */
    public Lista<T> reversa(){
        Lista<T> nueva = new Lista<>();

        for (T elem : this)
            nueva.agregar(elem);

        return nueva;
    }

    /**
     * Método que devuelve una copia exacta de la lista
     * @return la copia de la lista.
     */
    public Lista<T> copia(){
        Lista<T> copia = new Lista<>();

        for (T elem :this ) {
            copia.agregar(elem);
        }
	
        return copia;
    }

    /**
     * Método que nos dice si una lista es igual que otra.
     * @param o objeto a comparar con la lista.
     * @return <code>true</code> si son iguales, <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object o){
        if (!(o instanceof Lista))
            return false;

        Lista<T> l = (Lista<T>) o;

        if (longitud != l.longitud)
            return false;

        Nodo aux = cabeza;
        Nodo aux1 = l.cabeza;

        while (aux != null) {
            if (!aux.elemento.equals(aux1.elemento))
                return false;
            aux = aux.siguiente;
            aux1 = aux1.siguiente;
        }

        return true;

    }

    /**
     * Método que devuelve un iterador sobre la lista
     * @return java.util.Iterador -- iterador sobre la lista
     */
    @Override
    public java.util.Iterator<T> iterator(){
        return new IteradorLista();
    }

    /**
     * Método que devuelve una lista ordenada, a partir de las 
     * dos listas que recibe como parámetro.
     * @param <T> Dede ser un tipo que extienda de Comparable, para poder distinguir
     * el orden de los elementos en la lista.
     * @param l1 Lista con elementos comparables.
     * @param l2 Lista con elementos comparablles.
     * @return lista compuesta por los elemetos de l1 y l2, odenada.
     */
    public static <T extends Comparable<T>> Lista <T> merge(Lista<T> l1, Lista<T> l2){
        Lista<T> mezcla = new Lista<>();

        Lista<T>.Nodo a = l1.cabeza;
        Lista<T>.Nodo b = l2.cabeza;

        while(a != null && b != null){
            if (a.elemento.compareTo(b.elemento) < 0) {
                mezcla.agregarAlFinal(a.elemento);
                a = a.siguiente;
            } else {
                mezcla.agregarAlFinal(b.elemento);
                b = b.siguiente;
            }
        }

        while(a != null){
            mezcla.agregarAlFinal(a.elemento);
            a = a.siguiente;
        }

        while(b != null){
            mezcla.agregarAlFinal(b.elemento);
            b = b.siguiente;
        }
        
        return mezcla;
    }

    /**
     * Método que devuelve una copia de la lista.
     * @param <T> Debe ser un tipo que extienda Comparable, para poder distinguir
     * el orden de los elementos en la lista.
     * @param l La lista de elementos comparables.
     * @return copia de la lista ordenada.
     */
    public static <T extends Comparable<T>> Lista <T> mergesort(Lista<T> l){

        if (l.longitud < 2)
            return l;

        Lista<T> mitad1 = new Lista<>();
        Lista<T> mitad2 = new Lista<>();
        int c = 0;

        for (T elem: l)
            if (c++ < l.longitud/2)
                mitad1.agregarAlFinal(elem);
            else
                mitad2.agregarAlFinal(elem);

        return merge(mergesort(mitad1),mergesort(mitad2));
    }

    /**
     * Método para representar la lista en una cadena.
     * @return cadena con los elementos de la lista
     */
    @Override
    public String toString() {
        String cadena = new String("{ ");

        for (T elem : this) {
            cadena += elem + " , ";
        }

        cadena = cadena.substring(0,cadena.length()-2) + "}";

        return cadena;
    }
}
