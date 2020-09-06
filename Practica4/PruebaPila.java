import java.util.Random;

public class PruebaPila {

    public static void main(String[] args) {
        Pila<Integer> a = new Pila<>();
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
            a = new Pila<>(add);
            boolean omision = true;
            int j = add.length - 1;
            for (Integer i : a) {
                if (i != add[j--]) {
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
            for (int m = add.length - 1; m >= 0; m--) {
                exit += add[m] + ",";
            }
            System.out.println("Esperado : " + exit.substring(0, exit.length() - 1) + " ]");
            System.out.println("Obtenido: " + a.toString());
            e.printStackTrace();
        }
        // Constructor por copia
        Pila<Integer> b = null;
        try {
            b = new Pila<>(a);
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

        // metodo elimina/pop()
        b = new Pila<>();
        try {
            b.elimina(1);
            testStatus("elimina()/pop()", false);
        } catch (Exception e) {
            try {
                Integer c = a.pop();
                if (!c.equals(add[add.length - 1])) {
                    throw new Exception("Implementacion erronea");
                }
                testStatus("elimina()/pop()", true);
                testsAprobados++;
            } catch (Exception f) {
                testStatus("elimina()/pop()", false);
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
        // push() peek()
        a.push(7);
        if (a.peek() == 7) {
            testStatus("push()", true);
            testStatus("peek()", true);
            testsAprobados += 2;
        } else {
            testStatus("push()", false);
            testStatus("peek()", false);
        }
        if (testsAprobados == 9) {
            System.out.println("FELICIDADES TU PRACTICA ES TODO UN EXITO, COMO TU :D");
        } else if (testsAprobados > 5) {
            System.out.println("ya casi queda, pero vas muy bien!");
        } else {
            System.out.println("ey , solo son unos baches, toma te doy una llanta de repuesto");
        }
        System.out.println("Aprobados " + testsAprobados + " / 9");

    }

    public static void testStatus(String test, boolean estado) {
        System.out.println(test + "\t\t\t\t" + (estado ? "Correcto" : "Fallo"));
    }

}