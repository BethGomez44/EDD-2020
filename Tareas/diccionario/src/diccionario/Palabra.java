package diccionario;

import java.text.Normalizer;
import java.io.Serializable;

/**
 * Clase que permite almacenar palabras en el diccionario
 */
public class Palabra implements Comparable<Palabra>, Serializable {
    //
    private String palabra;
    private int tolerancia;
    private boolean busqueda;

    /**
     * Constructor por omision, vacio
     */
    public Palabra() {
        palabra = "";
        tolerancia = 0;
        busqueda = false;

    }

    /**
     * Crea una palabra a partir de la cadena pasada por paramatro
     * 
     * @param p cadena que representa la palabra
     * @param b booleano que controla el tipo de comparacion busqueda/probabilidad
     */
    public Palabra(String p, boolean b) {
        palabra = p;
        tolerancia = 0;
        busqueda = b;
    }

    /**
     * Metodo que compara dos palabras, note que si el booleno esta activo se hara
     * una busqueda por probabilidad
     */
    @Override
    public int compareTo(Palabra p) {
        int compatibilidad = casoSinAcentos(palabra).toLowerCase().compareTo(casoSinAcentos(p.palabra).toLowerCase());
        if (!busqueda) {
            return compatibilidad;
        }
        int aux = compatibilidad;
        compatibilidad = calcularCompatibilidad(p.palabra) + tolerancia;
        compatibilidad = compatibilidad > 100 ? 100 : compatibilidad;
        // System.out.println(this.palabra + "} " + p.palabra+ " " + compatibilidad +
        // "%");
        if (compatibilidad >= 80) {
            return 0;
        } else
            return aux;
    }

    /**
     * Determina la tolerenacia a los errores en la busqueda por probabilidad
     * 
     * @param n
     */
    public void asignarTolerancia(int n) {
        tolerancia = n >= 0 && n <= 100 ? n : 0;
    }

    @Override
    public String toString() {
        return palabra.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return palabra.equals(obj);
    }

    /**
     * Calcula la compatibilidad de la palabra con la cadena pasada por parametro
     * 
     * @param p1
     * @return
     */
    public int calcularCompatibilidad(String p1) {
        int compatibilidad = distanciaLevenshtein(p1);
        int tope = Math.max(this.palabra.length(), p1.length());
        return 100 - Math.floorDiv(compatibilidad * 100, tope);
    }

    private int minimo(int a, int b, int c) {
        return Math.min(a, Math.min(b, c));
    }

    /**
     * Calcula la distancia de Levenshtein entre la palabra y la cadena pasada por
     * parametro
     * 
     * @param s1 cadena a comparar
     * @return un entero positivo que representa el numero de operaciones minimas
     *         para pasar de una cadena de texto a otra
     */
    public int distanciaLevenshtein(String s1) {
        return calcularDistanciaLevenshtein(this.palabra.toCharArray(), s1.toCharArray()); // transformamos las cadenas
                                                                                           // a un
        // arreglo de caracteres
    }

    private int calcularDistanciaLevenshtein(char[] s1, char[] s2) {
        int[][] distancia = new int[s1.length + 1][s2.length + 1];

        for (int i = 0; i < s1.length + 1; i++) { // trasformaciones cadena vacia a s1
            distancia[i][0] = i;
        }

        for (int j = 0; j < s2.length + 1; j++) {// trasformaciones cadena vacia a s2
            distancia[0][j] = j;
        }
        // [i - 1][j] eliminar
        // [i][j - 1] insertar
        // [i - 1][j - 1] cambiar
        for (int i = 1; i < s1.length + 1; i++) {
            for (int j = 1; j < s2.length + 1; j++) {
                distancia[i][j] = minimo(distancia[i - 1][j] + 1, distancia[i][j - 1] + 1,
                        distancia[i - 1][j - 1] + ((s1[i - 1] == s2[j - 1]) ? 0 : 1));
            }
        }

        return distancia[s1.length][s2.length];
    }

    private static String casoSinAcentos(String cad) {
        if (cad == null) {
            return null;
        }
        cad = Normalizer.normalize(cad, Normalizer.Form.NFD); // separar en dos los caracteres especiales
        return cad.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }
}