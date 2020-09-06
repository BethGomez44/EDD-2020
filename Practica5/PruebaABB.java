import java.util.Iterator;
import java.util.Random;

public class PruebaABB {

    public static void main(String[] args) {
        String diagnostico = "";
        int aprobados = 0;
        boolean status = false;

        status = prueba01_Constructores();
        diagnostico += "Constructor por omision \t" + estado(status) + "\nConstructor por coleccionable \t"
                + estado(status) + "\nequals()\t\t\t" + estado(status) + "\n";
        if (status) {
            aprobados += 3;

            status = prueba06_agrega();
            aprobados += status ? 2 : 0;
            diagnostico += "agrega()\t\t\t" + estado(status) + "\n" + "agregaNodo()\t\t\t" + estado(status) + "\n";

            if (status) {
                status = prueba02_Altura();
                aprobados += status ? 1 : 0;
                diagnostico += "altura()\t\t\t" + estado(status) + "\n";

                status = prueba03_tamanio();
                aprobados += status ? 1 : 0;
                diagnostico += "getTamanio()\t\t\t" + estado(status) + "\n";

                status = prueba04_esVacio();
                aprobados += status ? 1 : 0;
                diagnostico += "esVacio()\t\t\t" + estado(status) + "\n";

                status = prueba05_limpia();
                aprobados += status ? 1 : 0;
                diagnostico += "limpia()\t\t\t" + estado(status) + "\n";

                status = prueba07_elimina();
                aprobados += status ? 4 : 0;
                diagnostico += "elimina()\t\t\t" + estado(status) + "\n" + "eliminaNodo()\t\t\t" + estado(status) + "\n"
                        + "buscaNodo()\t\t\t" + estado(status) + "\n" + "maximoSubarbolIzquierdo()\t" + estado(status)
                        + "\n";

                status = prueba08_contiene();
                aprobados += status ? 1 : 0;
                diagnostico += "contiene()\t\t\t" + estado(status) + "\n";

                status = prueba09_giraDerecha();
                aprobados += status ? 1 : 0;
                diagnostico += "giraDerecha()\t\t\t" + estado(status) + "\n";

                status = prueba10_giraIzquierda();
                aprobados += status ? 1 : 0;
                diagnostico += "giraIzquierda()\t\t\t" + estado(status) + "\n";

            }

        }
        diagnostico += "\nTests aprobados: " + aprobados + " de 16";

        System.out.println(diagnostico + "\n" + frases(aprobados));

    }

    // para pasar este test ambos constructores deben ser correctos y el equals debe
    // tener una implementacion correcta
    private static boolean prueba01_Constructores() {
        try {
            Cola<Integer> fuente = new Cola<>();
            ArbolBinarioBusqueda<Integer> a = new ArbolBinarioBusqueda<>();

            Random e = new Random();
            int n = e.nextInt(1000) + 20;

            for (int i = 0; i < n; i++) {
                int num = e.nextInt(n) * (e.nextInt() % 2 == 0 ? 1 : -1);
                fuente.agrega(num);
                a.agrega(num);
            }
            ArbolBinarioBusqueda<Integer> b = new ArbolBinarioBusqueda<Integer>(fuente);
            boolean test = b.equals(a);
            test &= a.equals(b);
            return test;
        } catch (Exception f) {
            f.printStackTrace();
            return false;
        }

    }

    private static boolean prueba02_Altura() {
        ArbolBinarioBusqueda<Integer> a = new ArbolBinarioBusqueda<>();
        Random e = new Random();
        int n = e.nextInt(50) + 20;

        for (int i = 0; i < n; i++) {
            a.agrega(i);
        }

        return a.altura() == n;
    }

    private static boolean prueba03_tamanio() {
        ArbolBinarioBusqueda<Integer> a = new ArbolBinarioBusqueda<>();
        Random e = new Random();
        int n = e.nextInt(50) + 20;

        for (int i = 0; i < n; i++) {
            a.agrega(e.nextInt());
        }
        return a.getTamanio() == n;
    }

    private static boolean prueba04_esVacio() {
        ArbolBinarioBusqueda<Integer> a = new ArbolBinarioBusqueda<>();
        return a.esVacio();
    }

    private static boolean prueba05_limpia() {
        ArbolBinarioBusqueda<Integer> a = new ArbolBinarioBusqueda<>();
        try {
            Random e = new Random();
            int n = e.nextInt(50) + 20;

            for (int i = 0; i < n; i++) {
                a.agrega(e.nextInt());
            }
            a.limpia();
        } catch (Exception f) {
            f.printStackTrace();
            return false;
        }
        return a.esVacio();
    }

    private static boolean prueba06_agrega() {
        ArbolBinarioBusqueda<Integer> a = new ArbolBinarioBusqueda<>();
        try {
            Random e = new Random();
            int n = e.nextInt(50) + 20;

            for (int i = 0; i < n; i++) {
                a.agrega(i);
            }
            boolean primero = true;
            int control = 0;
            for (Integer p : a) {
                if (primero) {
                    control = p;
                    primero = false;
                } else {
                    if (control > p) {
                        return false;
                    }
                    control = p;
                }
            }
        } catch (Exception f) {
            f.printStackTrace();
            return false;
        }
        return true;
    }

    private static boolean prueba07_elimina() {
        Cola<Integer> fuente = new Cola<>();
        ArbolBinarioBusqueda<Integer> a = new ArbolBinarioBusqueda<>();

        Random e = new Random();
        int n = e.nextInt(1000) + 20;
        try {
            for (int i = 0; i < n; i++) {
                int num = e.nextInt(n) * (e.nextInt() % 2 == 0 ? 1 : -1);
                fuente.agrega(num);
                a.agrega(num);
            }

            while (!fuente.esVacio()) {
                a.elimina(fuente.dequeue());
            }

            return a.esVacio();
        } catch (Exception f) {
            f.printStackTrace();
            return false;
        }

    }

    private static boolean prueba08_contiene() {
        ArbolBinarioBusqueda<Integer> a = new ArbolBinarioBusqueda<>();

        Random e = new Random();
        int n = e.nextInt(1000) + 20;
        int index = e.nextInt(n);
        int eli = 0;
        try {
            for (int i = 0; i < n; i++) {
                int num = e.nextInt(n) * (e.nextInt() % 2 == 0 ? 1 : -1);
                if (i == index) {
                    eli = num;
                }
                a.agrega(num);
            }

            return a.contiene(eli);
        } catch (Exception f) {
            f.printStackTrace();
            return false;
        }

    }

    private static boolean prueba09_giraDerecha() {
        ArbolBinarioBusqueda<Integer> a = new ArbolBinarioBusqueda<>();

        Random e = new Random();
        int n = e.nextInt(1000) + 20;

        try {
            for (int i = 0; i < n; i++) {
                int num = e.nextInt(n) * (e.nextInt() % 2 == 0 ? 1 : -1);
                a.agrega(num);
            }

            while (a.raiz().hayIzquierdo()) {
                a.giraDerecha(a.raiz());
            }

            return enOrden(a);
        } catch (Exception f) {
            f.printStackTrace();
            return false;
        }

    }

    private static boolean prueba10_giraIzquierda() {
        ArbolBinarioBusqueda<Integer> a = new ArbolBinarioBusqueda<>();

        Random e = new Random();
        int n = e.nextInt(1000) + 20;

        try {
            for (int i = 0; i < n; i++) {
                int num = e.nextInt(n) * (e.nextInt() % 2 == 0 ? 1 : -1);
                a.agrega(num);
            }

            while (a.raiz().hayDerecho()) {
                a.giraIzquierda(a.raiz());
            }

            return enOrden(a);
        } catch (Exception f) {
            f.printStackTrace();
            return false;
        }

    }

    private static boolean enOrden(ArbolBinarioBusqueda<Integer> p) {
        Iterator<Integer> r = p.iterator();
        boolean primero = true;
        int anterior = 0;
        while (r.hasNext()) {
            if (primero) {
                anterior = r.next();
            } else {
                if (anterior > r.next()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static String estado(boolean s) {
        if (s)
            return "Aprobado";
        else
            return "Fallido";
    }

    private static void imprimirElementos(ArbolBinarioBusqueda<Integer> p) {
        Iterator<Integer> r = p.iterator();
        System.out.print("[");
        while (r.hasNext()) {
            System.out.print(r.next().toString() + (r.hasNext() ? "," : "]\n\n"));
        }
    }

    private static void imprimirArbol(ArbolBinarioBusqueda<Integer> p) {
        System.out.println(p.toString());
    }

    private static String frases(int i){
        Random e = new Random();
        int frase = e.nextInt(5)+1;
        if(i <= 4){
            switch(frase){
                case 1:
                return "Si bueno, quien tiene hambre?";
                case 2:
                return "Hey, al menos no tienes coronavirus";
                case 3:
                return "Vayaa, pero que porqueria de prueba quien hizo esto!!";
                case 4:
                return "Bueno, al menos no estudias en quimica";
                case 5:
                return "No te pongas triste, cuando acabe esto vamos por taquitos c;";
                default:
                return " mm esto no lo deberias ver";
            }
        }
        else if( i <= 8){
            switch(frase){
                case 1:
                return "Nada mal, solo unos pasos mas!";
                case 2:
                return "Vaya, pero miren ese potencial!";
                case 3:
                return "Eso es todo!, no te rindas";
                case 4:
                return "Pero que pasado de $%$#@ el que hizo esta prueba!";
                case 5:
                return "Si llevas mucho tiempo en esto, hechale un vistazo : https://www.youtube.com/watch?v=DqbXayEVhGo";
                default:
                return " mm esto no lo deberias ver";
                
            }  
        }
        else if(i <= 12){
            switch(frase){
                case 1:
                return "El olimpo te espera, solo sube un poco mas ";
                case 2:
                return "Mr. Robot comienza a temblar";
                case 3:
                return "Ve por un chocolate, te lo mereces";
                case 4:
                return "IMPRESIONANTE, en mi primer intento saque 0 :C";
                case 5:
                return "No te estres checa esto: https://www.youtube.com/watch?v=7WsWAGue9dc";
                default:
                return " mm esto no lo deberias ver";
            }
        }
        else if( i <= 15){
            switch(frase){
                case 1:
                return "O dios o dios o dios, que ya casi lo sacas, soy tu fan";
                case 2:
                return "Bro, de aqui a la NASA";
                case 3:
                return "Eso es! ya les mostraste quien manda a esos arboles";
                case 4:
                return "vienes violento papah";
                case 5:
                return "uy uy, ya huele  a mole";
                default:
                return " mm esto no lo deberias ver";
            }
        }
        else{
            return "FELICIDADES, NUEVO HACKERMAN, eres todo un crack :D";
        }
    }
}