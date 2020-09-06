package main.java.sup.laberinto;

import main.java.sub.estructuras.*;
import java.util.Random;

/**
 * <p>
 * Clase concreta para modelar el laberinto
 * <p>
 * <p>
 * 
 * @version 24/Abr/2020
 */
public class Laberinto {

    protected Casilla laberinto[][];
    private int m, n;
    private int ci, cf;
    protected boolean fueGenerado;
    // m columnas
    // n filas

    /**
     * Constructor por omision de la clase
     */
    public Laberinto() {
        this.m = 10;
        this.n = 10;
        laberinto = new Casilla[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                laberinto[i][j] = new Casilla(i, j);
            }
        }

        // genera punto de inicio en ci y final en cf
        Random aux = new Random();
        ci = aux.nextInt(m);
        cf = aux.nextInt(m);
        laberinto[0][ci].asignarSimbolo('i');
        laberinto[n - 1][cf].asignarSimbolo('f');
        fueGenerado = false;
    }

    /**
     * Constructor que recibe como parametro sus dimensiones.
     *
     * @param m el número de filas
     * @param n el número de columnas
     */
    public Laberinto(int m, int n) {
        this.m = m;
        this.n = n;
        laberinto = new Casilla[m][n];

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                laberinto[i][j] = new Casilla(i, j);
            }
        }

        // genera punto de inicio en ci y final en cf
        Random aux = new Random();
        ci = aux.nextInt(n);
        cf = aux.nextInt(n);
        laberinto[0][ci].asignarSimbolo('i');
        laberinto[m - 1][cf].asignarSimbolo('F');
        fueGenerado = false;
    }

    public int obtenerAncho() {
        return m;
    }

    public int obtenerAlto() {
        return n;
    }

    /**
     * Obtiene la casilla del laberinto en cierta posicion
     * 
     * @param m valor en x
     * @param n valor en y
     * @return casilla en la posicin (m,n)
     */
    public Casilla obtenerCasilla(int m, int n) {
        return laberinto[m][n];
    }

    /**
     * Método que genera aleatoreamente las paredes y los caminos del laberinto.
     */
    public void generarLaberinto() {
        Pila<Casilla> gen = new Pila<>();
        Casilla aux;
        Casilla vecino;
        aux = randomCasilla();
        aux.marcar();
        gen.push(aux);

        while (!gen.esVacio()) {

            vecino = vecinoAleatorio(aux);
            if (vecino == null) {
                aux = gen.pop();
            } else {
                conectar(aux, vecino);
                vecino.marcar();
                gen.push(vecino);
                aux = vecino;
            }

        }
        fueGenerado = true;
    }

    /**
     * Metodo que retorna una pila con el camino solucion del laberinto, este metodo
     * solo debe ser llamada si existe un laberinto generado
     * 
     * @return
     */
    public Pila<Casilla> obtenerSolucion() {
        if (fueGenerado) {
            desmarcarTodo();
            Pila<Casilla> gen = new Pila<>();
            Casilla aux = obtenerIncio();
            Casilla terminal = obtenerFinal();
            Casilla camino;

            laberinto[aux.obtenerX()][aux.obtenerY()].marcar();
            gen.push(aux);
            while (!aux.equals(terminal)) {
                camino = obtenerCamino(aux);

                if (camino != null) {
                    gen.push(aux);
                    laberinto[camino.obtenerX()][camino.obtenerY()].marcar();
                    aux = camino;
                    gen.push(camino);
                } else {
                    aux = gen.pop();
                }
            }

            return voltearPila(gen);
        } else {
            System.out.println("El laberinto aun no ha sido generado");
            return null;
        }
    }

    public void borrarSolucion() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!laberinto[i][j].equals(obtenerIncio()) && !laberinto[i][j].equals(obtenerFinal())) {
                    laberinto[i][j].asignarSimbolo(' ');
                }
            }
        }
    }

    /**
     * Escribe la solucion dentro del laberinto
     */
    public void resolver() {
        Pila<Casilla> solucion = obtenerSolucion();
        Casilla aux;
        char simb = ' ';
        while (!solucion.esVacio()) {
            aux = solucion.pop();
            if (!aux.equals(obtenerIncio()) && !aux.equals(obtenerFinal())) {
                switch (obtenerReferencia(aux, solucion.peek())) {
                    case 1:
                        simb = '↑';
                        break;
                    case 2:
                        simb = '↓';
                        break;
                    case 3:
                        simb = '←';
                        break;
                    case 4:
                        simb = '→';
                    default:
                        break;
                }
                aux.asignarSimbolo(simb);
            }
        }
    }

    private Pila<Casilla> voltearPila(Pila<Casilla> c) {
        Pila<Casilla> exit = new Pila<>();
        while (!c.esVacio()) {
            exit.push(c.pop());
        }
        return exit;
    }

    public int numeroCasillas() {
        return n * m;
    }

    public Casilla obtenerIncio() {
        return laberinto[0][ci];
    }

    public Casilla obtenerFinal() {
        return laberinto[m - 1][cf];
    }

    protected Casilla randomCasilla() {
        Random a = new Random();
        return laberinto[a.nextInt(m)][a.nextInt(n)];
    }

    protected void desmarcarTodo() {
        if (fueGenerado) {
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    laberinto[i][j].desmarcar();
                }
            }
        } else {
            System.out.println("El laberinto aun no ha sido generado por completo");
        }
    }

    protected Casilla obtenerCamino(Casilla e) {
        Random a = new Random();
        int vecinos = 0, x, y;
        Pila<Casilla> salida = new Pila<>();
        x = e.obtenerX();
        y = e.obtenerY();

        if (x < m - 1) {
            if (!laberinto[x + 1][y].fueVisitada() && !laberinto[x][y].hayParedDerecha()) {
                salida.push(laberinto[x + 1][y]);
                vecinos++;
            }
        }
        if (x > 0) {
            if (!laberinto[x - 1][y].fueVisitada() && !laberinto[x][y].hayParedIzquierda()) {
                salida.push(laberinto[x - 1][y]);
                vecinos++;
            }
        }
        if (y < n - 1) {
            if (!laberinto[x][y + 1].fueVisitada() && (!laberinto[x][y].hayParedAbajo())) {
                salida.push(laberinto[x][y + 1]);
                vecinos++;
            }
        }
        if (y > 0) {
            if (!laberinto[x][y - 1].fueVisitada() && (!laberinto[x][y].hayParedArriba())) {
                salida.push(laberinto[x][y - 1]);
                vecinos++;
            }
        }

        if (vecinos == 0) {
            return null;
        } else {
            int j = a.nextInt(vecinos);
            for (int i = 0; i < j; i++) {
                salida.pop();
            }
            return salida.pop();
        }
    }

    protected Casilla vecinoAleatorio(Casilla e) {
        Random a = new Random();
        int vecinos = 0, x, y;
        Pila<Casilla> salida = new Pila<>();
        x = e.obtenerX();
        y = e.obtenerY();

        if (x < m - 1) {
            if (!laberinto[x + 1][y].fueVisitada()) {
                salida.push(laberinto[x + 1][y]);
                vecinos++;
            }
        }
        if (x > 0) {
            if (!laberinto[x - 1][y].fueVisitada()) {
                salida.push(laberinto[x - 1][y]);
                vecinos++;
            }
        }
        if (y < n - 1) {
            if (!laberinto[x][y + 1].fueVisitada()) {
                salida.push(laberinto[x][y + 1]);
                vecinos++;
            }
        }
        if (y > 0) {
            if (!laberinto[x][y - 1].fueVisitada()) {
                salida.push(laberinto[x][y - 1]);
                vecinos++;
            }
        }

        if (vecinos == 0) {

            return null;
        } else {
            int j = a.nextInt(vecinos);
            for (int i = 0; i < j; i++) {
                salida.pop();
            }

            return salida.pop();
        }

    }

    protected void conectar(Casilla c1, Casilla c2) {
        int x1, x2, y1, y2;
        x1 = c1.obtenerX();
        y1 = c1.obtenerY();

        x2 = c2.obtenerX();
        y2 = c2.obtenerY();

        switch (obtenerReferencia(c1, c2)) {
            case 1:
                laberinto[x1][y1].borrarParedArriba();
                laberinto[x2][y2].borrarParedAbajo();
                break;
            case 2:
                laberinto[x1][y1].borrarParedAbajo();
                laberinto[x2][y2].borrarParedArriba();
                break;
            case 3:
                laberinto[x1][y1].borrarParedIzquierda();
                laberinto[x2][y2].borrarParedDerecha();
                break;
            case 4:
                laberinto[x1][y1].borrarParedDerecha();
                laberinto[x2][y2].borrarParedIzquierda();
                break;
            default:
                break;
        }
    }

    /**
     * Pemite ver la relacion que mantienen dos casillas conectadas
     * 
     * @param c1 casilla 1
     * @param c2 casilla 2
     * @return (1) si c2 esta arriba de c1 (2) si c2 esta por debajo de c1 (3) si c2
     *         esta a la izquierda de c1 (4) si c2 esta a la derecha de c1
     */
    protected int obtenerReferencia(Casilla c1, Casilla c2) {
        int x1, x2, y1, y2;
        x1 = c1.obtenerX();
        y1 = c1.obtenerY();

        x2 = c2.obtenerX();
        y2 = c2.obtenerY();

        if (x1 - x2 == 0) {
            if (y1 - y2 == 1) {
                return 1;
            } else {
                return 2;
            }
        } else {
            if (x1 - x2 == 1) {
                return 3;
            } else {
                return 4;
            }
        }
    }

    public void estado() {

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                System.out.println("Casilla: (" + laberinto[i][j].obtenerX() + "," + laberinto[i][j].obtenerY() + ")"
                        + " Derecha " + laberinto[i][j].hayParedDerecha() + " Izquierda "
                        + laberinto[i][j].hayParedIzquierda() + " Arriba " + laberinto[i][j].hayParedArriba()
                        + " Abajo " + laberinto[i][j].hayParedAbajo() + "\t visitado: "
                        + laberinto[i][j].fueVisitada());
            }
            System.out.println();
        }
    }

    public String toString() {
        String cadena = "";

        for (int p = 0; p < m; p++) {
            cadena += "__";
        }
        cadena += "\n";
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (j == 0) {
                    cadena += "|";
                }

                if (laberinto[j][i].hayParedAbajo() && laberinto[j][i].obtenerSimbolo() == ' ') {
                    cadena += "_";
                } else if (laberinto[j][i].obtenerSimbolo() != ' ') {
                    cadena += laberinto[j][i].obtenerSimbolo();
                } else {
                    cadena += " ";
                }

                if (laberinto[j][i].hayParedDerecha()) {
                    cadena += "|";
                } else {
                    cadena += " ";
                }
            }
            cadena += "\n";
        }

        return cadena;
    }

}