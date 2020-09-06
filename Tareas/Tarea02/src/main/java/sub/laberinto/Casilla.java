package main.java.sup.laberinto;


/**
 * Clase concreta para modelar las casillas del laberinto
 * 
 * @version 24/Abr/2020
 */
public class Casilla {

    private boolean pared_L, pared_R, pared_U, pared_D, visitada;

    private char info;

    private int x, y;

    /**
     * Constructor por omisión
     */
    public Casilla() {
        pared_L = true;
        pared_R = true;
        pared_D = true;
        pared_U = true;

        visitada = false;

        info = ' ';

        this.x = 0;
        this.y = 0;
    }

    /**
     * Costructor que recibe la posición en coordenadas dónde se creará la casilla.
     * 
     * @param x -- coordenada x dentro del laberinto
     * @param y -- coordenada y dentro del laberinto
     */
    public Casilla(int x, int y) {
        this.x = x;
        this.y = y;
        pared_L = true;
        pared_R = true;
        pared_D = true;
        pared_U = true;

        visitada = false;

        info = ' ';
    }

    public Casilla(Casilla f) {
        this.x = f.x;
        this.y = f.y;
        pared_L = f.pared_L;
        pared_R = f.pared_R;
        pared_D = f.pared_D;
        pared_U = f.pared_U;

        visitada = f.visitada;

        info = f.info;
    }

    /**
     * Retorna la posicion en x de la casilla dentro del laberinto
     * 
     * @return coordenada x dentro del laberinto
     */
    public int obtenerX() {
        return x;
    }

    /**
     * Retorna la posicion en y de la casilla dentro del laberinto
     * 
     * @return coordenada y dentro del laberinto
     */
    public int obtenerY() {
        return y;
    }

    /**
     * Método que nos dice si una casilla ya fue visitada o no
     * 
     * @return <code>true</code> si la casilla ya fue visitada, <code>false</code>
     *         en otro caso.
     */
    public boolean fueVisitada() {
        return visitada;
    }

    /**
     * Regresa el carácter contenido en la casilla
     * 
     * @return info -- carácter dentro de la casilla
     */
    public char obtenerSimbolo() {
        return info;
    }

    /**
     * Método para cambiar el estado de una casilla que ya fue visitada
     */
    public void marcar() {
        visitada = true;
    }

    public void desmarcar() {
        visitada = false;
    }

    /**
     * Método para asignar el elemento tipo carácter a una casilla.
     * 
     * @param c -- carácter asignado
     */
    public void asignarSimbolo(char c) {
        info = c;
    }

    /**
     * Borra la pared izquierda de la casilla
     */
    public void borrarParedIzquierda() {
        pared_L = false;
    }

    /**
     * Borra la pared derecha de la casilla
     */
    public void borrarParedDerecha() {
        pared_R = false;
    }

    /**
     * Borra la pared superior de la casilla
     */
    public void borrarParedArriba() {
        pared_U = false;
    }

    /**
     * Borra la pared inferior de la casilla
     */
    public void borrarParedAbajo() {
        pared_D = false;
    }

    /**
     * Método que nos dice si la casilla tiene pared hacia la izquierda.
     * 
     * @return <code>true</code> si hay pared a la izquierda, <code>false</code> en
     *         otro caso.
     */
    public boolean hayParedIzquierda() {
        return pared_L;
    }

    /**
     * Método que nos dice si la casilla tiene pared hacia la derecha.
     * 
     * @return <code>true</code> si hay pared a la derecha, <code>false</code> en
     *         otro caso.
     */
    public boolean hayParedDerecha() {
        return pared_R;
    }

    /**
     * Método que nos dice si la casilla tiene pared hacia arriba.
     * 
     * @return <code>true</code> si hay pared hacia arriba, <code>false</code> en
     *         otro caso.
     */
    public boolean hayParedArriba() {
        return pared_U;
    }

    /**
     * Método que nos dice si la casilla tiene pared hacia abajo.
     * 
     * @return <code>true</code> si hay pared hacia abajo, <code>false</code> en
     *         otro caso.
     */
    public boolean hayParedAbajo() {
        return pared_D;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Casilla c = (Casilla) o;
        return this.x == c.x && this.y == c.y;
    }

}