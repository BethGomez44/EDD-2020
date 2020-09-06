import java.util.Iterator;
import java.util.NoSuchElementException;

import jdk.nashorn.internal.ir.ReturnNode;

/**
 * Clase para heaps mínimos (<i>min heaps</i>). Podemos crear un heap mínimo con
 * <em>n</em> elementos en tiempo <em>O</em>(<em>n</em>), y podemos agregar y
 * actualizar elementos en tiempo <em>O</em>(log <em>n</em>). Eliminar el
 * elemento mínimo también nos toma tiempo <em>O</em>(log <em>n</em>).
 * 
 * @param <T>
 */
public abstract class Heap<T extends Comparable<T>> implements Coleccionable<T> {

    /* Clase privada para iteradores de heaps. */
    private class Iterador implements Iterator<T> {

        /* Índice del iterador. */
        private int actual;

        /* Construye un nuevo iterador, auxiliándose del heap mínimo. */
        public Iterador() {
            this.actual = 0;
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override
        public boolean hasNext() {
            return actual < siguiente && arreglo[actual] != null;
        }

        /* Regresa el siguiente elemento. */
        @Override
        public T next() {
            if (hasNext()) {
                return arreglo[actual++];
            }
            return null;
        }

        /* No lo implementamos: siempre lanza una excepción. */
        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    /* El siguiente índice dónde agregar un elemento. */
    protected int siguiente;
    /*
     * El arreglo que guarda los datos con la estructura de árbol ue requiere el
     * heap.
     */
    protected T[] arreglo;

    /*
     * Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo Java
     * implementa sus genéricos; de otra forma obtenemos advertencias del
     * compilador.
     */
    @SuppressWarnings("unchecked")
    private T[] creaArregloGenerico(int n) {
        return (T[]) (new Comparable[n]);
    }

    /**
     * Constructor sin parámetros. Es más eficiente usar {@link #HeapMinimo(Lista)},
     * pero se ofrece este constructor por completez.
     */
    public Heap() {
        this.arreglo = creaArregloGenerico(10);
    }

    /**
     * Constructor para heap mínimo que recibe una lista. Es más barato construir un
     * heap con todos sus elementos de antemano (tiempo <i>O</i>(<i>n</i>)), que el
     * insertándolos uno por uno (tiempo <i>O</i>(<i>n</i> log <i>n</i>)).
     * 
     * @param lista la lista a partir de la cuál queremos construir el heap.
     */
    public Heap(Lista<T> lista) {
        arreglo = creaArregloGenerico(lista.longitud());
        for (T e : lista) {
            agrega(e);
        }
    }

    /**
     * Constructor para heap mínimo que recibe un coleccionable. Es más barato
     * construir un heap con todos sus elementos de antemano (tiempo
     * <i>O</i>(<i>n</i>)), que el insertándolos uno por uno (tiempo
     * <i>O</i>(<i>n</i> log <i>n</i>)).
     * 
     * @param coleccionable la lista a partir de la cuál queremos construir el heap.
     */
    public Heap(Coleccionable<T> coleccionable) {
        arreglo = creaArregloGenerico(coleccionable.getTamanio());
        for (T e : coleccionable) {
            agrega(e);
        }
    }

    /*
     * Métodos auxiliares.
     */
    private void intercambia(int i, int j) {
        T tmp = this.arreglo[i];
        this.arreglo[i] = this.arreglo[j];
        this.arreglo[j] = tmp;
    }

    /**
     * Método auxiliar que devuelve la posición del hijo izquierdo, si no tiene hijo
     * izquiero devuelve -1.
     */
    private int izquierdo(int i) {
        int izquierdo = (i * 2) + 1;
        int indice;
        if (izquierdo < arreglo.length && arreglo[izquierdo] != null) {
            indice = izquierdo;
        } else {
            indice = -1;
        }
        return indice;
    }

    /**
     * Método auxiliar que devuelve la posición del hijo derecho, si no tiene hijo
     * derecho devuelve -1.
     */
    private int derecho(int i) {
        int derecho = (i * 2) + 2;
        int indice;
        if (derecho < arreglo.length && arreglo[derecho] != null) {
            indice = derecho;
        } else {
            indice = -1;
        }
        return indice;
    }

    /**
     * Método auxiliar que devuelve la posición del padre. Si no tiene padre,
     * devuelve -1;
     */
    private int padre(int i) {
        int padre = (i == 0) ? -1 : (i - 1) / 2;
        return padre;
    }

    /**
     * Agrega un nuevo elemento en el heap.
     * 
     * @param elemento el elemento a agregar en el heap.
     */
    @Override
    public void agrega(T elemento) {
        if (siguiente >= arreglo.length) {
            T[] nuevo = creaArregloGenerico(arreglo.length * 2);
            for (int i = 0; i < arreglo.length; i++) {
                nuevo[i] = arreglo[i];
            }
            arreglo = nuevo;
        }
        arreglo[siguiente] = elemento;
        reordena(siguiente);
        siguiente++;
    }

    /**
     * Método que reordena hacia abajo un elemento en un heap. Si el elemento que
     * queremos está en un orden incorrecto, se intercambia con el hijo
     * correspondiente para que el heap sea válido. Es importante considerar si el
     * heap es mínimo o máximo.
     * 
     * @param n
     */
    protected void reordenaParaAbajo(int n, boolean esMin) {
        int izquierdo = izquierdo(n);
        int derecho = derecho(n);
        int minimo = getMenor(n, izquierdo, derecho), maximo = getMayor(n, izquierdo, derecho);
        if (esMin) {
            while (n != minimo) {
                if (minimo == izquierdo) {
                    intercambia(n, izquierdo);
                    reordenaParaAbajo(izquierdo, true);
                } else {
                    intercambia(n, derecho);
                    reordenaParaAbajo(derecho, true);
                }
                minimo = n;
            }
        } else {
            while (n != maximo) {
                if (maximo == izquierdo) {
                    intercambia(n, izquierdo);
                    reordenaParaAbajo(izquierdo, false);
                } else {
                    intercambia(n, derecho);
                    reordenaParaAbajo(derecho, false);
                }
                maximo = n;
            }
        }

    }

    /**
     * Elimina el elemento hasta arriba del heap.
     * 
     * @return el elemento mínimo o máximo del heap.
     * @throws IllegalStateException si el heap es vacío.
     */
    public abstract T elimina();

    /**
     * Puesto que no deberíamos eliminar un elemento que no sea el mínimo, lanzamos
     * una excepción. En una implementación más aplicada es posible implementar esta
     * operación con los métodos ya implementados sin mucho esfuerzo.
     * 
     * @param elemento a eliminar del heap.
     * @throws IllegalStateException. Esta operación no debería ser posible en un
     *                                Heap
     */
    @Override
    public void elimina(T elemento) {
        throw new IllegalStateException("Esta operación no debería ser válida para Heaps");
    }

    /**
     * Nos dice si un elemento está contenido en el heap.
     * 
     * @param elemento el elemento que queremos saber si está contenido.
     * @return <code>true</code> si el elemento está contenido, <code>false</code>
     *         en otro caso.
     */
    @Override
    public boolean contiene(T elemento) {
        for (T e : arreglo) {
            if (e == null) {
                return false;
            }
            if (e.equals(elemento)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Nos dice si el heap es vacío.
     * 
     * @return <tt>true</tt> si ya no hay elementos en el heap, <tt>false</tt> en
     *         otro caso.
     */
    public boolean esVacio() {
        return this.siguiente == 0;
    }

    /*
     * Método auxiliar que regresa el índice en el arreglo que tiene al elemento
     * menor de tres. Recibe tres índices de elementos en el arreglo.
     */
    private int getMenor(int elemento, int i, int d) {

        if (i != -1 || d != -1) {
            if (i != -1 && d == -1) {
                return arreglo[i].compareTo(arreglo[elemento]) <= 0 ? i : elemento;
            } else if (i == -1 && d != -1) {
                return arreglo[d].compareTo(arreglo[elemento]) <= 0 ? d : elemento;
            }
        } else {
            return elemento;
        }
        int aux = elemento;
        if (arreglo[i].compareTo(arreglo[aux]) <= 0) {
            aux = i;
        }
        if (arreglo[d].compareTo(arreglo[aux]) <= 0) {
            aux = d;
        }

        return aux;
    }

    /*
     * Método auxiliar que regresa el índice en el arreglo que tiene al elemento
     * mayor de tres. Recibe tres índices de elementos en el arreglo.
     */
    private int getMayor(int elemento, int i, int d) {

        if (i != -1 || d != -1) {
            if (i != -1 && d == -1) {
                return arreglo[i].compareTo(arreglo[elemento]) > 0 ? i : elemento;
            } else if (i == -1 && d != -1) {
                return arreglo[d].compareTo(arreglo[elemento]) > 0 ? d : elemento;
            }
        } else {
            return elemento;
        }
        int aux = elemento;
        if (arreglo[i].compareTo(arreglo[aux]) > 0) {
            aux = i;
        }
        if (arreglo[d].compareTo(arreglo[aux]) > 0) {
            aux = d;
        }

        return aux;
    }

    /**
     * Reordena un elemento en el árbol. La implementación se deja a la clase
     * concreta, pues el reordenamiento depende de si es un heap mínimo o máximo.
     * 
     * @param elemento el elemento que hay que reordenar.
     */
    protected abstract void reordena(int elemento);

    /**
     * Este método hace la chamba, dependiendo de si el heap es mínimo o máximo.
     * 
     * @param elemento el elemento a reordenar.
     * @param esMin    nos dice si el heap que manda a llamar esta operación es
     *                 mínimo.
     */
    protected void reordena(int elemento, boolean esMin) {
        int p;
        while (elemento != 0) {
            p = padre(elemento);

            if (esMin) {
                if (arreglo[p].compareTo(arreglo[elemento]) > 0) {
                    intercambia(p, elemento);
                    elemento = p;
                } else {
                    return;
                }
            } else {
                if (arreglo[p].compareTo(arreglo[elemento]) <= 0) {
                    intercambia(p, elemento);
                    elemento = p;
                } else {
                    return;
                }
            }

        }
    }

    /**
     * Regresa el número de elementos en el heap.
     * 
     * @return el número de elementos en el heap.
     */
    public int getTamanio() {
        // Aquí va su código.
        return this.siguiente;
    }

    /**
     * Regresa la representación en cadena del heap.
     * 
     * @return la representación en cadena del árbol.
     */
    public String toString() {
        String s = "[";
        for (int i = 0; i < siguiente; i++) {
            s += i == siguiente - 1 ? arreglo[i] : arreglo[i] + "|";
        }
        return s += "]";
    }

    /**
     * Regresa un iterador para recorrer el heap.
     * 
     * @return un iterador para recorrer el heap.
     */
    @Override
    public Iterator<T> iterator() {
        return new Iterador();
    }
}
