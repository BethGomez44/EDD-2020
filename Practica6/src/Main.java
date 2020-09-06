import java.util.Random;

public class Main {
    public static void main(String[] args) {
        checkAgrega();
        checkElimina();
        ArbolAVL<Integer> p = new ArbolAVL<>();
        p.agrega(2);
        p.agrega(3);
        p.agrega(5);
        p.agrega(10);
        p.agrega(6);
        p.agrega(20);
        p.agrega(4);
        System.out.println(p.toString());
    }

    public static void checkAgrega() {
        double exito = 10000;
        ArbolAVL<Integer> p = new ArbolAVL<>();
        Random a = new Random();
        for (int i = 0; i < 1000; i++) {
            try {
                p.limpia();
                for (int j = 0; j < 36; j++) {
                    p.agrega(a.nextInt());
                }
                boolean aux = !checarOrden(p.inOrden());
                if (!p.checarBalance() || aux) {
                    if (aux) {
                        System.out.println("Orden comprometido");
                    }
                    throw new Exception();
                }
            } catch (Exception e) {
                exito--;
            }

        }
        System.out.println("Porcentaje de exito de metodo agregar : " + (exito / 100) + " / 100");
    }

    public static void checkElimina() {
        double exito = 10000;
        ArbolAVL<Integer> p = new ArbolAVL<>();
        Random a = new Random();
        int aux;
        Cola<Integer> fuente = new Cola<>();

        for (int i = 0; i < 1000; i++) {
        }
        try {
            p.limpia();
            for (int j = 0; j < 36; j++) {
                aux = a.nextInt();
                p.agrega(aux);
                fuente.agrega(aux);
            }
            for (int x = 0; x < 18; x++) {
                p.elimina(fuente.dequeue());
            }
            p.elimina(a.nextInt());
            boolean au = !checarOrden(p.inOrden());
            if (!p.checarBalance() || au) {
                if (au) {
                    System.out.println("Orden comprometido");
                }
                throw new Exception();
            }
        } catch (Exception e) {
            System.out.println(e);
            exito--;
        }

        System.out.println("Porcentaje de exito de metodo elimina : " + exito / 100 + " / 100");
    }

    public static boolean checarOrden(Lista<Integer> l) {
        int[] map = new int[l.longitud()];
        for (int i = 0; i < map.length; i++) {
            map[i] = l.getElemento(i);
        }
        for (int i = 1; i < map.length; i++) {
            if (map[0] > map[i]) {
                return false;
            }
        }
        return true;
    }
    /*
     * public boolean checarBalance() { return stanlin(raiz); }
     * 
     * private boolean stanlin(Nodo n) { NodoAVL derecho, izquierdo; derecho =
     * (NodoAVL) n.derecho; izquierdo = (NodoAVL) n.izquierdo; int h_der, h_izq,
     * balanceo; h_der = derecho == null ? 0 : derecho.altura(); h_izq = izquierdo
     * == null ? 0 : izquierdo.altura(); balanceo = h_izq - h_der;
     * 
     * if (Math.abs(balanceo) > 1) { System.out.println("Arbol desbalanceado:\n" +
     * this.toString() ); return false; }else{ if(derecho == null || izquierdo ==
     * null){ return true; } return stanlin(derecho) && stanlin(izquierdo); } }
     */
}