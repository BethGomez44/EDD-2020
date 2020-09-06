package main.java.app;

import java.io.*;
import java.util.Random;
import processing.core.PFont;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.KeyEvent;
import processing.core.PGraphics;

import main.java.sup.laberinto.*;
import main.java.sub.estructuras.*;
import main.java.sub.botones.*;

public class Interfaz extends PApplet {

    private static int ancho, alto, ancho_laberinto, alto_laberinto;
    private static LaberintoPaso laberinto_1;
    private static boolean laberinto_generado, solucionado, crearPasoAPaso, solucionPasoAPaso, editar_ancho,
            editar_alto, trabajo_enImagen;
    private String ruta_guardar, descripcion, aux_ancho, aux_alto;

    private BotonGenerador generarLaberinto;
    private BotonSolucion solucion;
    private BotonBorrar borrar;
    private BotonGeneraPasoAPaso generarLaberintoPasoAPaso;
    private BotonSolucionPasoAPaso generarSolucionPasoAPaso;
    private BotonGuardar guardar;
    private BotonAncho editarAncho;
    private BotonAlto editarAlto;

    private PGraphics lienzo;
    private PImage fondo;
    private PFont texto, descripciones;

    public static void main(String[] args) {
        PApplet.main("main.java.app.Interfaz");
    }

    @Override
    public void settings() {
        alto = displayHeight * 9 / 10;
        ancho = displayWidth * 8 / 10;
        size(ancho, alto);
    }

    @Override
    public void setup() {
        int x_inicioLaberinto = (ancho - alto) * 2 / 4; // line divisoria entre la barra de herramientas y el laberinto
        int divison = x_inicioLaberinto / 4;
        crearPasoAPaso = false;
        solucionPasoAPaso = false;
        editar_ancho = false;
        trabajo_enImagen = false;
        ancho_laberinto = 10;
        alto_laberinto = 10;
        aux_alto = "";
        aux_ancho = "";
        aux_alto += alto_laberinto;
        aux_ancho += ancho_laberinto;

        fondo = loadImage(this.getClass().getResource("/imagenes/fondos/fondoLaberinto.jpg").getPath());
        texto = createFont(this.getClass().getResource("/fuentes/Heroes_Legend.ttf").getPath(), 32);
        descripciones = createFont(this.getClass().getResource("/fuentes/JandaManateeSolid.ttf").getPath(), 32);

        generarLaberinto = new BotonGenerador(divison / 2, divison * 1, divison, divison,
                this.getClass().getResource("/imagenes/iconos/generar.png").getPath(),
                this.getClass().getResource("/imagenes/iconos/generarP.png").getPath());
        solucion = new BotonSolucion(divison / 2, divison * 3, divison, divison,
                this.getClass().getResource("/imagenes/iconos/solucion.png").getPath(),
                this.getClass().getResource("/imagenes/iconos/solucionP.png").getPath());
        borrar = new BotonBorrar(divison / 2, divison * 5, divison, divison,
                this.getClass().getResource("/imagenes/iconos/borrarSolucion.png").getPath(),
                this.getClass().getResource("/imagenes/iconos/borrarSolucionP.png").getPath());

        generarLaberintoPasoAPaso = new BotonGeneraPasoAPaso(divison * 2, divison * 1, divison, divison,
                this.getClass().getResource("/imagenes/iconos/crear.png").getPath(),
                this.getClass().getResource("/imagenes/iconos/crearP.png").getPath());
        generarSolucionPasoAPaso = new BotonSolucionPasoAPaso(divison * 2, divison * 3, divison, divison,
                this.getClass().getResource("/imagenes/iconos/solucionPaso.png").getPath(),
                this.getClass().getResource("/imagenes/iconos/solucionPasoP.png").getPath());
        guardar = new BotonGuardar(divison * 2, divison * 5, divison, divison,
                this.getClass().getResource("/imagenes/iconos/guardar.png").getPath(),
                this.getClass().getResource("/imagenes/iconos/guardarP.png").getPath());
        editarAlto = new BotonAlto(divison / 2, divison * 9, divison * 5 / 2, divison / 2);
        editarAncho = new BotonAncho(divison / 2, divison * 8, divison * 5 / 2, divison / 2);
        frameRate(30);
    }

    @Override
    public void draw() {
        background(255);

        dibujarInterfaz();
        if (!trabajo_enImagen) {
            generacionesEspeciales();
        }
        if ((laberinto_generado || crearPasoAPaso || solucionPasoAPaso) && !trabajo_enImagen) {
            pintarLaberinto(laberinto_1);
        }
        actualizarEstadosBotones();
    }

    @Override
    public void mouseClicked() {
        actualizarDimensiones();
        if (mouseButton == LEFT) {
            if (generarLaberinto.presionado()) {
                generarLaberinto.comportamiento();
            } else if (solucion.presionado()) {

                solucion.comportamiento();
            } else if (borrar.presionado()) {

                borrar.comportamiento();
            } else if (generarLaberintoPasoAPaso.presionado()) {

                generarLaberintoPasoAPaso.comportamiento();
            } else if (generarSolucionPasoAPaso.presionado()) {

                generarSolucionPasoAPaso.comportamiento();
            } else if (editarAlto.presionado()) {
                editar_ancho = false;
                editarAlto.comportamiento();
            } else if (editarAncho.presionado()) {
                editar_alto = false;
                editarAncho.comportamiento();
            } else if (guardar.presionado()) {
                guardar.comportamiento();
            }

        }

    }

    @Override
    public void keyPressed() {
        if (editar_ancho) {
            aux_ancho = entradaInt(aux_ancho, 8, key, keyCode);
        } else if (editar_alto) {
            aux_alto = entradaInt(aux_alto, 8, key, keyCode);
        }
        if (keyCode == 10) {
            actualizarDimensiones();
        }
    }

    private void generacionesEspeciales() {
        int paso_genracionesEspeciales;

        if (crearPasoAPaso) {
            paso_genracionesEspeciales = laberinto_1.numeroCasillas() / 200;
            paso_genracionesEspeciales = paso_genracionesEspeciales < 1 ? 1 : paso_genracionesEspeciales;
            for (int i = 1; i <= paso_genracionesEspeciales && crearPasoAPaso; i++) {
                crearPasoAPaso = laberinto_1.generarPasoAPaso();
            }

            if (!crearPasoAPaso) {
                laberinto_generado = true;
                solucionado = false;
            }
        } else if (solucionPasoAPaso) {
            paso_genracionesEspeciales = laberinto_1.longitudSolucion() / 200;
            paso_genracionesEspeciales = paso_genracionesEspeciales < 1 ? 1 : paso_genracionesEspeciales;
            for (int i = 1; i <= paso_genracionesEspeciales && solucionPasoAPaso; i++) {
                solucionPasoAPaso = laberinto_1.resolverPasoAPaso();
            }
            if (!solucionPasoAPaso) {
                solucionado = true;
            }
        }
    }

    private void dibujarInterfaz() {
        int x_inicioLaberinto = (ancho - alto) * 2 / 4; // line divisoria entre la barra de herramientas y el laberinto
        int divison = x_inicioLaberinto / 4;

        PImage botonGenerarLaberinto = loadImage(generarLaberinto.recursoEnUso());
        PImage botonSolucion = loadImage(solucion.recursoEnUso());
        PImage botonBorrar = loadImage(borrar.recursoEnUso());
        PImage botonGenerarPasoAPaso = loadImage(generarLaberintoPasoAPaso.recursoEnUso());
        PImage botonSolucionPasoAPaso = loadImage(generarSolucionPasoAPaso.recursoEnUso());
        PImage botonGuardar = loadImage(guardar.recursoEnUso());

        tint(255, 100);
        image(fondo, x_inicioLaberinto, 0, ancho, alto);
        noTint();
        fill(223, 223, 223);
        stroke(223, 223, 223);
        rect(0, 0, x_inicioLaberinto, alto, 5); // barra de herramientas
        fill(205, 205, 205);
        rect(0, 0, x_inicioLaberinto, divison / 2);
        stroke(0, 0, 0);
        rect(divison / 2, divison * 8, divison * 5 / 2, divison / 2); // ancho
        rect(divison / 2, divison * 9, divison * 5 / 2, divison / 2);// largo
        fill(0, 0, 0);
        textFont(texto);
        textSize(divison / 5);
        text("Seleciona una opción", divison / 3, divison / 3);
        textFont(descripciones);
        textSize(divison / 3);
        text("Ancho", divison / 2, divison * 63 / 8);
        text("Alto", divison / 2, divison * 71 / 8);
        text(aux_ancho + (editar_ancho && frameCount % 5 == 0 ? "|" : ""), divison / 2, divison * 67 / 8);
        text(aux_alto + (editar_alto && frameCount % 5 == 0 ? "|" : ""), divison / 2, divison * 75 / 8);
        obtenerDescripcion();
        text(descripcion, divison / 2, divison * 10);

        image(botonGenerarLaberinto, divison / 2, divison * 1, divison, divison);
        if (trabajo_enImagen) {
            tint(255, 100);
        }
        image(botonGenerarPasoAPaso, divison * 2, divison * 1, divison, divison);
        noTint();
        if (!laberinto_generado) {
            tint(255, 100);
        }
        image(botonGuardar, divison * 2, divison * 5, divison, divison);
        noTint();
        if (!laberinto_generado || solucionado) {
            tint(255, 100);
        }
        image(botonSolucion, divison / 2, divison * 3, divison, divison);
        if (trabajo_enImagen) {
            tint(255, 100);
        }
        image(botonSolucionPasoAPaso, divison * 2, divison * 3, divison, divison);
        noTint();
        if (!solucionado) {
            tint(255, 100);
        }
        image(botonBorrar, divison / 2, divison * 5, divison, divison);
        noTint();
    }

    private void obtenerDescripcion() {
        if (generarLaberinto.presionado()) {

            descripcion = "Genera un laberinto";
        } else if (solucion.presionado()) {

            descripcion = "Muestra la solucion\ndel laberinto";
        } else if (borrar.presionado()) {
            descripcion = "Borrar la solucion";

        } else if (generarLaberintoPasoAPaso.presionado()) {
            descripcion = "Observa como se \n genera el \n laberinto";
        } else if (generarSolucionPasoAPaso.presionado()) {
            descripcion = "Muestra la solucion\ndel laberinto\npaso a paso";
        } else if (guardar.presionado()) {
            descripcion = "Guarda tu laberinto";
        } else if (trabajo_enImagen) {
            descripcion = "UY!, no podemos \n imprimir esto en \n tu pantalla\n pero puedes elegir\n donde guardarlo";
        } else {
            descripcion = "";
        }
    }

    private void actualizarEstadosBotones() {
        generarLaberinto.actualizarEstado(mouseX, mouseY);
        solucion.actualizarEstado(mouseX, mouseY);
        borrar.actualizarEstado(mouseX, mouseY);
        generarLaberintoPasoAPaso.actualizarEstado(mouseX, mouseY);
        generarSolucionPasoAPaso.actualizarEstado(mouseX, mouseY);
        editarAlto.actualizarEstado(mouseX, mouseY);
        editarAncho.actualizarEstado(mouseX, mouseY);
        guardar.actualizarEstado(mouseX, mouseY);
    }

    private void actualizarDimensiones() {

        alto_laberinto = Integer.parseInt(aux_alto);
        alto_laberinto = alto_laberinto > 5 ? alto_laberinto : 5;
        descripcion = "tamaño minimo 5x5";
        aux_alto = "";
        aux_alto += alto_laberinto;

        ancho_laberinto = Integer.parseInt(aux_ancho);
        ancho_laberinto = ancho_laberinto > 5 ? ancho_laberinto : 5;
        descripcion = "tamaño minimo 5x5";
        aux_ancho = "";
        aux_ancho += ancho_laberinto;

        editar_alto = false;
        editar_ancho = false;

        int tam = (ancho - (ancho - alto)) / (ancho_laberinto > alto_laberinto ? ancho_laberinto : alto_laberinto);
        if (tam > 3 && alto_laberinto != 0 && ancho_laberinto != 0) {
            trabajo_enImagen = false;
        } else {
            trabajo_enImagen = true;
        }


    }

    /**
     * True si se puee pintar el laberinto en pantalla , false si es demasiado
     * grande
     */
    private boolean pintarLaberinto(Laberinto laberinto) {
        int tam = (ancho - (ancho - alto))
                / (laberinto.obtenerAncho() > laberinto.obtenerAlto() ? laberinto.obtenerAncho()
                        : laberinto.obtenerAlto());
        if (tam * laberinto.obtenerAlto() >= alto - (2 * tam) || tam * laberinto.obtenerAncho() >= ancho - (2 * tam)) {
            tam = tam * 8 / 10;
        }
        if (tam > 3 && tam * laberinto.obtenerAlto() <= alto) {
            for (int i = 0; i < laberinto.obtenerAncho(); i++) {
                for (int j = 0; j < laberinto.obtenerAlto(); j++) {
                    pintarCasilla(laberinto.obtenerCasilla(i, j), tam, laberinto);
                }
            }
            return true;
        }
        return false;
    }

    private void pintarCasilla(Casilla c, int tam, Laberinto laberinto) {

        int x_distancia = (ancho - alto) * 3 / 4 + (((ancho - (ancho - alto)) - (laberinto.obtenerAncho() * tam)) / 2);
        int y_distancia = (alto - (laberinto.obtenerAlto() * tam)) / 2;
        int x_posicion = (x_distancia + tam * c.obtenerX());
        int y_posicion = (c.obtenerY() * tam) + y_distancia;

        if (c.hayParedDerecha() && !c.equals(laberinto.obtenerFinal())) {
            line(x_posicion + tam, y_posicion, x_posicion + tam, y_posicion + tam);
        }
        if (c.hayParedIzquierda() && !c.equals(laberinto.obtenerIncio())) {
            line(x_posicion, y_posicion, x_posicion, y_posicion + tam);
        }
        if (c.hayParedArriba()) {
            line(x_posicion, y_posicion, x_posicion + tam, y_posicion);
        }
        if (c.hayParedAbajo()) {
            line(x_posicion, y_posicion + tam, x_posicion + tam, y_posicion + tam);
        }

        if (c.obtenerSimbolo() != ' ') {
            fill(200, 0, 0);
            stroke(200, 0, 0);
            circle(x_posicion + tam / 2, y_posicion + tam / 2, tam / 2);
            stroke(0, 0, 0);
        }
    }

    private void guardarLaberinto(Laberinto laberinto) {

        int tam = (ancho - (ancho - alto))
                / (laberinto.obtenerAncho() > laberinto.obtenerAlto() ? laberinto.obtenerAncho()
                        : laberinto.obtenerAlto());
        if (tam < 6) {
            tam = 5;
        }
        int x_largo = laberinto.obtenerAncho() * tam + tam * 2;
        int y_ancho = laberinto.obtenerAlto() * tam + tam * 2;

        int x_distancia = tam;
        int y_distancia = tam;
        int x_posicion;
        int y_posicion;

        Casilla c;

        lienzo = createGraphics(x_largo, y_ancho);

        lienzo.beginDraw();
        lienzo.background(255, 255, 255);
        for (int i = 0; i < laberinto.obtenerAncho(); i++) {
            for (int j = 0; j < laberinto.obtenerAlto(); j++) {
                c = laberinto.obtenerCasilla(i, j);
                x_posicion = (x_distancia + tam * c.obtenerX());
                y_posicion = (c.obtenerY() * tam) + y_distancia;
                if (c.hayParedDerecha() && !c.equals(laberinto.obtenerFinal())) {
                    lienzo.line(x_posicion + tam, y_posicion, x_posicion + tam, y_posicion + tam);
                }
                if (c.hayParedIzquierda() && !c.equals(laberinto.obtenerIncio())) {
                    lienzo.line(x_posicion, y_posicion, x_posicion, y_posicion + tam);
                }
                if (c.hayParedArriba()) {
                    lienzo.line(x_posicion, y_posicion, x_posicion + tam, y_posicion);
                }
                if (c.hayParedAbajo()) {
                    lienzo.line(x_posicion, y_posicion + tam, x_posicion + tam, y_posicion + tam);
                }

                if (c.obtenerSimbolo() != ' ') {
                    lienzo.fill(200, 0, 0);
                    lienzo.stroke(200, 0, 0);
                    lienzo.circle(x_posicion + tam / 2, y_posicion + tam / 2, tam / 2);
                    lienzo.stroke(0, 0, 0);
                }
            }
        }
        lienzo.endDraw();
        noLoop();
        selectFolder("Selecciona donde quieres guardar tu laberinto", "folderSelected");

    }

    public void folderSelected(File selection) {
        Random e = new Random();
        if (selection == null) {
            try {
                File miDir = new File(".");
                ruta_guardar = miDir.getCanonicalPath() + "/laberintosGuardados/" + "laberinto" + e.nextInt() + ".jpg";
            } catch (Exception g) {
                g.printStackTrace();
            }
        } else {
            ruta_guardar = selection.getAbsolutePath() + "/laberinto" + e.nextInt() + ".jpg";
        }
        lienzo.save(ruta_guardar);
        loop();
    }

    public class BotonGenerador extends Boton {

        public BotonGenerador(int xi, int yi, int xf, int yf, String r1, String r2) {
            super(xi, yi, xf, yf, r1, r2);
        }

        @Override
        public void comportamiento() {
            if (this.presionado()) {
                laberinto_1 = new LaberintoPaso(ancho_laberinto, alto_laberinto);
                solucionado = false;
                laberinto_generado = true;
                laberinto_1.generarLaberinto();
                if (trabajo_enImagen) {
                    guardarLaberinto(laberinto_1);
                }

            }

        }
    }

    public class BotonSolucion extends Boton {

        public BotonSolucion(int xi, int yi, int xf, int yf, String r1, String r2) {
            super(xi, yi, xf, yf, r1, r2);
        }

        @Override
        public void comportamiento() {
            if (this.presionado() && laberinto_generado && !solucionado) {
                laberinto_1.borrarSolucion();
                solucionado = true;
                solucionPasoAPaso = false;
                laberinto_1.resolver();
                if (trabajo_enImagen) {
                    guardarLaberinto(laberinto_1);
                }
            }
        }
    }

    public class BotonBorrar extends Boton {

        public BotonBorrar(int xi, int yi, int xf, int yf, String r1, String r2) {
            super(xi, yi, xf, yf, r1, r2);
        }

        @Override
        public void comportamiento() {
            if (this.presionado() && laberinto_generado && solucionado) {
                solucionado = false;
                laberinto_1.borrarSolucion();
            }
        }
    }

    public class BotonGeneraPasoAPaso extends Boton {
        public BotonGeneraPasoAPaso(int xi, int yi, int xf, int yf, String r1, String r2) {
            super(xi, yi, xf, yf, r1, r2);
        }

        @Override
        public void comportamiento() {
            if (this.presionado() && !trabajo_enImagen) {
                laberinto_1 = new LaberintoPaso(ancho_laberinto, alto_laberinto);
                crearPasoAPaso = true;
                laberinto_generado = false;
            }
        }
    }

    private String entradaInt(String campo, int largo, char letra, int codigo) {
        if (codigo == 8) {
            return campo.length() > 0 ? campo.substring(0, campo.length() - 1) : campo;
        }
        if (campo.length() <= largo && keyPressed) {
            return campo + (Character.isDigit(letra) ? letra : "");
        }
        return campo;
    }

    public class BotonSolucionPasoAPaso extends Boton {

        public BotonSolucionPasoAPaso(int xi, int yi, int xf, int yf, String r1, String r2) {
            super(xi, yi, xf, yf, r1, r2);
        }

        @Override
        public void comportamiento() {
            if (this.presionado() && laberinto_generado && !solucionado && !trabajo_enImagen) {
                solucionPasoAPaso = true;
            }
        }

    }

    public class BotonAncho extends Boton {
        public BotonAncho(int xi, int yi, int xf, int yf) {
            super(xi, yi, xf, yf);
        }

        @Override
        public void comportamiento() {
            editar_ancho = true;
            laberinto_generado = false;
            solucionado = false;
        }
    }

    public class BotonAlto extends Boton {
        public BotonAlto(int xi, int yi, int xf, int yf) {
            super(xi, yi, xf, yf);
        }

        @Override
        public void comportamiento() {
            editar_alto = true;
            laberinto_generado = false;
            solucionado = false;
        }
    }

    public class BotonGuardar extends Boton {

        public BotonGuardar(int xi, int yi, int xf, int yf, String r1, String r2) {
            super(xi, yi, xf, yf, r1, r2);
        }

        @Override
        public void comportamiento() {
            if (laberinto_generado) {
                guardarLaberinto(laberinto_1);
            }
        }
    }

}
