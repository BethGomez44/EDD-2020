package diccionario;
import java.io.Serializable;
/**
 * Clase que guarda sugerencias con respecto a una palaabra dada
 */
public class Sugerencia implements Comparable<Sugerencia> , Serializable {
    private int compatibilidad;
    private String palabra;
    /**
     * Constructor unico de clase
     * @param p palabra
     * @param i indice de compatibilidad
     */
    public Sugerencia(String p , int i){
        palabra = p;
        compatibilidad = i;
    }


    @Override
    public String toString() {
        return palabra.toString() ;
    }


    @Override
    public int compareTo(Sugerencia p) {
    
        return  p.compatibilidad  - this.compatibilidad;

    }
}