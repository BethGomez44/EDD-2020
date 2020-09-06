import java.util.Random;

public class PruebaCola {

    public static void main(String[] args) {
        Cola<Integer> a = new Cola<>();
        int testsAprobados = 0;
        // es vacio y constructor por omision
        System.out.println("Test\t\t\t\tEstado");

        if (a.esVacio()) {
            testStatus("Constructor por omision", true);
            testStatus("esVacio()", true);
            testsAprobados += 2;
        } else {
            testStatus("Constructor por omision", false);
            testStatus("esVacio()", false);
        }

        Random gen = new Random();
        Integer[] add = new Integer[20];
        for (int i = 0; i < add.length; i++) {
            add[i] = gen.nextInt(100);
        }

        // constructor por arreglo
        try {
            a = new Cola<>(add);
            boolean omision = true;
            int j = 0;
            for (Integer i : a) {
                if (i != add[j++]) {
                    omision = false;
                }
            }
            if (!omision) {
                throw new Exception("Implementacion erronea");
            }
            testStatus("Constructor por arreglo", true);
            testsAprobados++;
        } catch (Exception e) {
            testStatus("Constructor por arreglo", false);
            String exit = "[";
            for (int m = 0; m < add.length; m++) {
                exit += add[m] + ",";
            }
            System.out.println("Esperado : " + exit.substring(0, exit.length() - 1) + " ]");
            System.out.println("Obtenido: " + a.toString());
            e.printStackTrace();
        }
        // Constructor por copia
        Cola<Integer> b = null;
        try {
            b = new Cola<>(a);
            boolean copia = b.equals(a);
            if (!copia) {
                throw new Exception("Implementacion erronea");
            }
            // equals
            testStatus("equals()", true);
            testStatus("Constructor copia", true);
            testsAprobados += 2;
        } catch (Exception e) {
            testStatus("Constructor copia", false);
            testStatus("equals()", false);
            System.out.println("Esperado : " + a.toString());
            System.out.println("Obtenido: " + b.toString());
            e.printStackTrace();
        }

        // metodo elimina/dequeue()
        b = new Cola<>();
        try {
            b.elimina(1);
            testStatus("elimina/dequeue()", false);
        } catch (Exception e) {
            try {
                Integer c = a.dequeue();
                if (!c.equals(add[0])) {
                    throw new Exception("Implementacion erronea");
                }
                testStatus("elimina/dequeue()", true);
                testsAprobados++;
            } catch (Exception f) {
                testStatus("elimina/dequeue()", false);
                f.printStackTrace();
            }
        }
        // getTamanio()
        if (a.getTamanio() == add.length - 1) {
            testStatus("getTamanio()", true);
            testsAprobados++;
        } else {
            testStatus("getTamanio()", false);
            System.out.println("Longitud obtenida " + a.getTamanio() + " esperada: " + (add.length - 1));
        }
        // peek() queue()
        a.queue(7);
        for (int k = 1; k < add.length; k++) { 
            int c = a.dequeue();
            
        }
        if (a.peek() == 7) {
            testStatus("queue()", true);
            testStatus("peek()", true);
            testsAprobados += 2;
        } else {
            testStatus("queue()", false);
            testStatus("peek()", false);
        }

        // contiene
        if (a.contiene(7)) {
            testStatus("contiene()", true);
            testsAprobados++;
        } else {
            testStatus("contiene()", false);
        }

        if (testsAprobados == 10) {
            System.out.println("FELICIDADES TU PRACTICA ES TODO UN EXITO, COMO TU :D");
        } else if (testsAprobados > 5) {
            System.out.println("ya casi queda, pero vas muy bien!");
        } else {
            System.out.println("ey , solo son unos baches, toma te doy una llanta de repuesto");
        }
        System.out.println("Aprobados " + testsAprobados + " / 10");

    }

    public static void testStatus(String test, boolean estado) {
        System.out.println(test + "\t\t\t\t" + (estado ? "Correcto" : "Fallo"));
    }

}