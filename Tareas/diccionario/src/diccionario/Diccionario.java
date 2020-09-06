package diccionario;

import archivos.*;
import diccionario.*;
import estructuras.*;
import java.io.*;


public class Diccionario  implements Serializable{

    ArbolAVL<Palabra> banco;

    ArbolAVL<Sugerencia> sugerencias;
    Palabra busqueda;

    public Diccionario(String rutabanco) {
        banco = Lectura.getDiccionario(rutabanco);
        sugerencias = new ArbolAVL<>();
        busqueda = null;
    }

    public Diccionario() throws Exception{

        banco = Lectura.getDiccionarioSerializado("files/diccionario.dat");
        sugerencias = new ArbolAVL<>();
        busqueda = null;
    }

    /**
     * Realiza una busqueda estricta de la palabra a lo mas ignorando la falta de
     * acentos
     * 
     * @param s1 cadena a consultar en el banco
     * @return la cadena encontrada en el banco , "n/a" si no hay alguna
     *         coincidencia
     */
    public String hacerConsulta(String s1) {
        busqueda = new Palabra(s1, false); // busqueda estricta
        Palabra aux = banco.buscar(busqueda);
 
        return aux != null ? aux.toString() : "n/a";
    }

    /**
     * Metodo que hace una busqueda por probabilidad, llamar cuando la busqueda
     * estricta no tiene resultados
     * 
     * @param s1                 cadena a buscar en el banco
     * @param margen_error       margen de tope de la tolerancia al error, no mayor
     *                           al 50%
     * @param numero_sugerencias minimo de sugerencias a obtener
     * @return cadena con el numero de soluciones requeridas o "n/a" si ninguna
     *         cadena cumplio el criterio de busqueda
     */
    public String obtenerSugerencias(String s1, int margen_error, int numero_sugerencias) {
        busqueda = new Palabra(s1, true); // busqueda por porcentaje
        sugerencias = new ArbolAVL<>();
        margen_error = margen_error > 50 ? 50 : margen_error;
        int margen = 0, numPalabras = 1;

        while (sugerencias.getTamanio() < numero_sugerencias && margen <= margen_error) {

            Palabra resultados = banco.buscar(busqueda);

            if (resultados != null) {
                banco.elimina(resultados);
                
                Sugerencia nueva = new Sugerencia(resultados.toString(),
                        busqueda.calcularCompatibilidad(resultados.toString()));
                sugerencias.agrega(nueva);

            } else {

                busqueda.asignarTolerancia(margen);
                margen++;
            }

        }
        String exit = "";
        if (sugerencias.getTamanio() > 0) {
            for (Sugerencia a : sugerencias) {
                if( ! a.toString().equals(busqueda.toString())){
                    exit += numPalabras + " ) " + a.toString() + "\n";
                    numPalabras++;
                }
                banco.agrega(new Palabra(a.toString(), false));
            }
            return exit;
        } else {
            return "n/a";
        }
    }
    /**
     * Agrega una entrada al diccionario siempre y cuando esta no se encuentre contenida
     * @param s1 entrada a agregar
     * @return true si se agrego con exito, false en caso contrario
     */
    public boolean agregarEntrada(String s1) {
        String depurada ="";
        for (int i = 0; i < s1.length(); i++) {
            char aux = Character.toLowerCase(s1.charAt(i));
            depurada += Character.isLetter(aux) ? aux : "";
        }
        Palabra aux = new Palabra(depurada, false);
        if(!banco.contiene(aux)){
            banco.agrega(aux);
            return true;
        }
        else{
            return false;
        }
        
    }

    public void guardarDiccionario(){
        try {
            FileOutputStream respaldo;
            ObjectOutputStream salida = null;

            respaldo = new FileOutputStream("files/diccionario.dat");

            salida = new ObjectOutputStream(respaldo);
            salida.writeObject(banco);
            salida.close();

        } catch (Exception e) {
            System.out.println("NO ES POSIBLE ACTUALIZAR EL REGISTRO DEL DICCIONARIO");
            e.printStackTrace();
        }
    }

}