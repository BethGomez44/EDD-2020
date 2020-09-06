package main.java.sub.botones;

public abstract class Boton {

    int xi, xf, yi, yf;
    boolean presionado;
    String recursoBase;
    String recursoCambio;

    /**
     * Constructor por omision
     */
    public Boton() {
        xi = 0;
        xf = 0;
        yi = 0;
        yf = 0;

        presionado = false;
        recursoBase = "";
        recursoCambio = "";
    }

    /**
     * Constructor por posicion
     * 
     * @param xi x- esquina superior izquierda
     * @param yi y- esquina superior izquierda
     * @param xf incremento en x
     * @param yf incremento en y
     */
    public Boton(int xi, int yi, int xf, int yf) {
        this.xi = xi;
        this.xf = xi + xf;
        this.yi = yi;
        this.yf = yi + yf;
        presionado = false;
        recursoBase = "";
        recursoCambio = "";
    }

    /**
     * Constructor por posicion y un recurso de fondo
     * 
     * @param xi x- esquina superior izquierda
     * @param yi y- esquina superior izquierda
     * @param xf incremento en x
     * @param yf incremento en y
     * @param r1 recurso de fondo
     */
    public Boton(int xi, int yi, int xf, int yf, String r1) {
        this.xi = xi;
        this.xf = xi + xf;
        this.yi = yi;
        this.yf = yi + yf;
        presionado = false;
        recursoBase = r1;
        recursoCambio = "";
    }

    /**
     * Constructor por posicion y un recurso de fondo
     * 
     * @param xi x- esquina superior izquierda
     * @param yi y- esquina superior izquierda
     * @param xf incremento en x
     * @param yf incremento en y
     * @param r1 recurso de fondo
     * @param r2 recurso de intercambio con el fondo
     */
    public Boton(int xi, int yi, int xf, int yf, String r1, String r2) {
        this.xi = xi;
        this.xf = xi + xf;
        this.yi = yi;
        this.yf = yi + yf;
        presionado = false;
        recursoBase = r1;
        recursoCambio = r2;
    }

    public void actualizarEstado(int x, int y) {
        if ((x >= xi && x <= xf) && (y >= yi && y <= yf)) {
            presionado = true;
        } else {
            presionado = false;
        }
    }

    public boolean presionado() {
        return presionado;
    }

    public String recursoEnUso() {
        if (recursoBase.equals("") && recursoCambio.equals("")) {
            return null;
        } else {
            if (this.presionado && !recursoCambio.equals("")) {
                return recursoCambio;
            } else {
                return recursoBase;
            }
        }
    }

    public abstract void comportamiento();

}