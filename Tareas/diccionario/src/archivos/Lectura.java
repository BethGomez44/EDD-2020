package archivos;

import estructuras.ArbolAVL;
import java.io.*;
import java.util.Scanner;
import diccionario.*;

public  class Lectura {
    /**
     * Metod estatico que crea un arbol de palabras a partir de un txt 
     * @param ruta ruta relativa del txt
     * @return un arbol avl con las palabras contenidas en el archivo
     */
    public static ArbolAVL<Palabra> getDiccionario(String ruta) {
        ArbolAVL<Palabra> salida = new ArbolAVL();
        
        try {
            Scanner input = new Scanner(new File(ruta));
            while (input.hasNextLine()) {
                String line = input.nextLine();
                line = line.trim();
                salida.agrega(new Palabra(line,false));
            }
            return salida;
        } catch (Exception e) {
            System.out.println("El diccionario no ha sido encontrado, por favor revise la carpeta /files");
        }
        return null;
    }

    /**
     * Metodo que crea un arbol de palabras a partir de un .dat archivo serializable
     * @param ruta ruta relativa del archivo
     * @return un arbol avl con las palabras contenidas en el archivo
     * @throws Exception Archivo no encotrado o no del tipo del ArbolAVL<palabra>
     */
    public static ArbolAVL<Palabra> getDiccionarioSerializado(String ruta) throws Exception {
        FileInputStream respaldo;
        ObjectInputStream entrada = null;
        ArbolAVL<Palabra> salida = new ArbolAVL();

        respaldo = new FileInputStream(ruta);
        entrada = new ObjectInputStream(respaldo);
        salida = (ArbolAVL<Palabra>) entrada.readObject();
        entrada.close();
        return salida;
    }
}