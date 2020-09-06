package main.java.sup.laberinto;

import main.java.sub.estructuras.*;

public class LaberintoPaso extends Laberinto {

    private Pila<Casilla> genera;
    Pila<Casilla> solucion;
    private boolean primeraEjecucion, por_resolver;
    private Casilla auxiliar;
    private int longitud_solucion;

    public LaberintoPaso(int x, int y) {
        super(x, y);
        genera = new Pila<>();
        solucion = new Pila<>();
        primeraEjecucion = true;
        por_resolver = true;
    }

    public boolean generarPasoAPaso() {
        Casilla vecino;
        if (primeraEjecucion) {
            auxiliar = randomCasilla();
            laberinto[auxiliar.obtenerX()][auxiliar.obtenerY()].marcar();
            genera.push(auxiliar);
            primeraEjecucion = false;
        }
        if (!genera.esVacio()) {
            vecino = vecinoAleatorio(auxiliar);

            if (vecino != null) {
                laberinto[vecino.obtenerX()][vecino.obtenerY()].marcar();
                conectar(auxiliar, vecino);
                genera.push(vecino);
                auxiliar = vecino;

            } else {
                auxiliar = genera.pop();
            }
            return true;
        }
        fueGenerado = true;
        return false;
    }

    public boolean resolverPasoAPaso() {
        if (fueGenerado) {
            if (por_resolver) {
                this.borrarSolucion();
                solucion = obtenerSolucion();
                longitud_solucion = solucion.getTamanio();
                por_resolver = false;
            }

            Casilla aux;
            char simb = ' ';
            if (!solucion.esVacio()) {
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
                return true;
            }
        }
        por_resolver = true;
        return false;
    }
    /**
     * Devuelve el tamaño de la solucion del laberinto
     * @return numero de pasos para salir del laberinto
     */
    public int longitudSolucion() {
        return longitud_solucion;
    }

    public void limpiarSolucion() {
        solucion = null;
        por_resolver = true;
    }

}