package estructuras;

import java.util.Iterator;
import java.io.Serializable;

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
public class Lista<T> implements Listable<T>, Iterable<T>, Serializable{

    /* Clase interna para construir la estructura */
    private class Nodo implements Serializable {
        /* Referencias a los nodos anterior y siguiente */
        public Nodo anterior, siguiente;
        /* El elemento que almacena un nodo */
        public T elemento;

        /* Unico constructor de la clase */
        public Nodo(T elemento) {
            this.elemento = elemento;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass()) {
                return false;
            }
            Nodo comp = (Nodo) obj;
            return this.elemento.equals(comp);// && this.siguiente.equals(comp.siguiente) &&
                                              // this.anterior.equals(comp.anterior);
        }
    }

    private class IteradorLista<T> implements Iterator<T> {
        /* La lista a recorrer */
        /* Elementos del centinela que recorre la lista */
        private Lista<T>.Nodo siguiente;

        public IteradorLista() {
            this.siguiente = (Lista<T>.Nodo) cabeza;
        }

        @Override
        public boolean hasNext() {
            return this.siguiente != null;
        }

        @Override
        public T next() {
            T aux = null;

            aux = this.siguiente.elemento;
            this.siguiente = this.siguiente.siguiente;

            return aux;
        }

        @Override
        public void remove() {
            Iterator.super.remove(); // To change body of generated methods, choose Tools | Templates.
        }
    }

    /* Atributos de la lista */
    private Nodo cabeza, cola;
    private int longitud;

    public Lista() {
        cabeza = null;
        cola = null;
        longitud = 0;
    }

    /**
     * Constructor copia de la clase. Recibe una lista con elementos. Crea una nueva
     * lista exactamente igual a la que recibimos como parámetro.
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
    public boolean esVacia() {
        return longitud == 0;

    }

    /**
     * Método para eliminar todos los elementos de una lista
     */
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
        return this.longitud;

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
        if (cabeza == null) {
            cabeza = new Nodo(elemento);
            cola = cabeza;
        } else {
            cabeza.anterior = new Nodo(elemento);
            cabeza.anterior.siguiente = cabeza;
            cabeza = cabeza.anterior;
        }
        longitud++;
    }

    /**
     * Método para agregar al final un elemento a la lista.
     * 
     * @param elemento Objeto que se agregará al inicio de la lista.
     */
    public void agregarAlFinal(T elemento) {
        if (cabeza == null) {
            cabeza = new Nodo(elemento);
            cola = cabeza;
        } else {
            cola.siguiente = new Nodo(elemento);
            cola.siguiente.anterior = cola;
            cola = cola.siguiente;
        }
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
        boolean contiene = false;
        IteradorLista<T> explorador = new IteradorLista<>();

        while (explorador.hasNext() && !contiene) {
            contiene = explorador.next().equals(elemento);
        }
        return contiene;
    }

    /**
     * Método para eliminar un elemento de la lista.
     * 
     * @param elemento Objeto que se eliminara de la lista.
     */
    public void eliminar(T elemento) {
        Nodo i = cabeza;
        while (i != null) {
            if (i.elemento.equals(elemento)) {
                if (i == cabeza) {
                    cabeza = cabeza.siguiente;
                    cabeza.anterior = null;
                    if (longitud == 1)
                        cola = null;
                } else if (i == cola) {
                    cola = cola.anterior;
                    cola.siguiente = null;
                } else {
                    i.anterior.siguiente = i.siguiente;
                    i.siguiente.anterior = i.anterior;
                }
                longitud--;
                return;
            } else {
                i = i.siguiente;
            }
        }
    }

    /**
     * Regresa el primer elemento de la lista y lo elimina
     * 
     * @return primer elemento
     */
    public T pop() {
        T salida;
        salida = cabeza.elemento;
        cabeza = cabeza.siguiente;
        if (cabeza != null)
            cabeza.anterior = null;
        if (longitud == 1)
            cola = null;
        longitud--;
        return salida;
    }

    /**
     * Método que devuelve la posición en la lista que tiene la primera aparición
     * del <code> elemento</code>.
     * 
     * @param elemento El elemnto del cuál queremos saber su posición.
     * @return i la posición del elemento en la lista, -1, si no se encuentra en
     *         ésta.
     */
    public int indiceDe(T elemento) {
        int i = 0;
        boolean encuentra = false;
        IteradorLista<T> explorador = new IteradorLista<>();
        while (explorador.hasNext() && !encuentra) {
            encuentra = explorador.next().equals(elemento);
            i += encuentra ? 0 : 1;
        }

        return encuentra ? i : -1;
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
        IteradorLista<T> explorador = new IteradorLista<>();

        if (i < 0 || i > longitud) {
            throw new IndexOutOfBoundsException();
        } else {
            do {
                T aux = explorador.next();
                if (i == 0)
                    return aux;
                else
                    i--;
            } while (i >= 0);
        }
        return null;
    }

    /**
     * Método que devuelve una copia de la lista, pero en orden inverso
     * 
     * @return Una copia con la lista l revés.
     */
    public Lista<T> reversa() {
        IteradorLista<T> cpy = new IteradorLista<>();
        Lista<T> reversa = new Lista<>();
        while (cpy.hasNext()) {
            reversa.agregarAlInicio(cpy.next());
        }
        return reversa;
    }

    /**
     * Método que devuelve una copi exacta de la lista
     * 
     * @return la copia de la lista.
     */
    public Lista<T> copia() {
        IteradorLista<T> cpy = new IteradorLista<>();
        Lista<T> exit = new Lista<>();
        while (cpy.hasNext()) {
            exit.agregar(cpy.next());
        }
        return exit;
    }

    /**
     * Método que nos dice si una lista es igual que otra.
     * 
     * @param o objeto a comparar con la lista.
     * @return <code>true</code> si son iguales, <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lista<T> comp = (Lista<T>) o;
        if (comp.longitud != this.longitud) {
            return false;
        }
        Iterator<T> original = this.iterator();
        Iterator<T> ignoto = comp.iterator();
        while (original.hasNext() && ignoto.hasNext()) {
            if (!original.next().equals(ignoto.next()))
                return false;
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
     * Método que devuelve una copia de la lista.
     * 
     * @param <T> Debe ser un tipo que extienda Comparable, para poder distinguir el
     *            orden de los elementos en la lista.
     * @param l   La lista de elementos comparables.
     * @return copia de la lista ordenada.
     */
    public static <T extends Comparable<T>> Lista<T> mergesort(Lista<T> l) {
        Lista<T> laux = new Lista<>();
        if (l.longitud == 1 || l.longitud == 0) {
            return l;
        } else {
            int n = l.longitud / 2;
            int i = 0;

            while (i < n) {
                laux.agregar(l.pop());
                i++;
            }
        }
        Lista<T> l1 = mergesort(laux);
        Lista<T> l2 = mergesort(l);

        return mezcla(l1, l2);
    }

    private static <T extends Comparable<T>> Lista<T> mezcla(Lista<T> l1, Lista<T> l2) {
        Lista<T> salida = new Lista<>();
        Lista<T>.Nodo aux1 = l1.cabeza;
        Lista<T>.Nodo aux2 = l2.cabeza;

        while (aux1 != null && aux2 != null) {
            if (aux1.elemento.compareTo(aux2.elemento) <= 0) {
                salida.agregarAlFinal(aux1.elemento);
                aux1 = aux1.siguiente;
            } else {
                salida.agregarAlFinal(aux2.elemento);
                aux2 = aux2.siguiente;
            }
        }

        if (aux1 != null) {
            while (aux1 != null) {
                salida.agregarAlFinal(aux1.elemento);
                aux1 = aux1.siguiente;
            }
        } else {
            while (aux2 != null) {
                salida.agregarAlFinal(aux2.elemento);
                aux2 = aux2.siguiente;
            }
        }

        return salida;

    }

    @Override

    public String toString() {
        String exit = "[";
        IteradorLista<T> out = new IteradorLista<>();
        while (out.hasNext()) {
            exit += out.next().toString() + ",";
        }
        return exit.substring(0, exit.length() - 1) + "]";
    }

}
