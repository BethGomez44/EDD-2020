public class Prueba {

    public static void main(String[] args) {

        Conjunto<Character> conjuntoA = new Conjunto<>();
        Conjunto<Character> conjuntoB = new Conjunto<>(
                new Character[] { 'm', 'u', 'r', 'c', 'i', 'e', 'l', 'a', 'g', 'o' });

        System.out.println(
                "El conjunto A: " + (conjuntoA.esVacio() ? "no tiene elementos " : "tiene al menos un elemento"));
        if (conjuntoA.esVacio()) {
            System.out.println("Agreguemos las vocales al conjunto A");
            conjuntoA.agrega('a');
            conjuntoA.agrega('e');
            conjuntoA.agrega('i');
            conjuntoA.agrega('o');
            conjuntoA.agrega('u');
            System.out.println("A es " + conjuntoA.toString());
        } else {
            System.out.println("Algo salio mal, verifica los constructores");
        }

        System.out.println("\nEl conjunto B se creo con las letras de la palabra: \" murcielago\" \nB es: "
                + conjuntoB.toString());
        conjuntoB.agrega('s');
        System.out.println("\nVamos a agregar la \'s\' y la \'u\' al conjunto  , ahora B es : \n" + conjuntoB.toString()
                + "\'u\' ya estaba en el conjunto! ");
        conjuntoA.eliminar('u');
        System.out.println("\nVamos a borrar \'u\' de A \nA es: " + conjuntoA.toString());
        System.out.println("\'u\' " + (conjuntoA.contiene('u') ? " sigue en A" : " ya no esta en A"));

        System.out.println("\n\nVamos a realizar unas operaciones: ");
        System.out.println("A = " + conjuntoA.toString());
        System.out.println("B = " + conjuntoB.toString());

        System.out.println("\nA union B = " + conjuntoA.union(conjuntoB).toString());
        System.out.println("\nA interseccion B = " + conjuntoA.interseccion(conjuntoB).toString());
        System.out.println("\nA/B = " + conjuntoA.diferencia(conjuntoB).toString());
        System.out.println("\nA diferencia simetrica B = " + conjuntoA.diferenciaSimetrica(conjuntoB).toString());

        System.out.println("A " + (conjuntoA.subconjunto(conjuntoB) ? "" : "no") + " es subconjunto de B");
        conjuntoB.vaciar();
        System.out.println("\nVaciemos B \n B es: " + conjuntoB.toString());
    }

}