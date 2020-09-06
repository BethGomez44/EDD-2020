import archivos.*;
import diccionario.*;
import estructuras.*;

import java.util.Scanner;

public class Main {
    public static Diccionario diccionario;

    public static void main(String[] args) {
        try{
            diccionario = new Diccionario();
        }catch(Exception e){
            diccionario = new Diccionario("files/diccionario.txt");
        }
        
        String op;
        Scanner in = new Scanner(System.in);
        do {
            System.out.println("** Consolnario** \n Por favor seleccione una opción \n "
                    + "1) Realizar busqueda \n 2) Agregar entrada \n 3) salir");
            System.out.print("> ");
            op = in.nextLine();
            switch (op) {
                case "1":
                    busqueda();
                    break;
                case "2":
                    agregarEntrada();
                    System.out.println("Proximamente");
                    break;
                case "3":
                    System.out.println("Hasta pronto (^･ｪ･^)");
                    break;
                default:
                    System.out.println("Disculpa, pero no te entendí");
            }

        } while (!op.equals("3"));

    }

    public static void busqueda() {
        String search, result;
        Scanner in = new Scanner(System.in);
        boolean llamar_agregar = false;

        System.out.println("Digite la palabra a buscar");
        System.out.print("> ");
        search = in.nextLine();
        result = diccionario.hacerConsulta(search);
        if (result.equals("n/a")) {
            System.out.println("No encontramos " + search);
            llamar_agregar = true;
        } else {
            if(result.equals(search)){
                System.out.println("Todo correcto por aquí!");
            }
            else{
                System.out.println("Encontramos " + result);
            }
            
        }

        result = diccionario.obtenerSugerencias(search, 25, 4);
        if (result.equals("n/a")) {
            System.out.println("No encontramos ninguna sugerencia ");
        } else {
            System.out.println("O tal vez querias decir \n" + result);
        }

        if (llamar_agregar) {

            do {
                System.out.println(
                        "Deseas agregar esta palabra como un nueva entrada al diccionario? \n \n[1] si \n[2] no");
                System.out.print("> ");
                result = in.nextLine();

                if (result.equals("1")) {
                    diccionario.agregarEntrada(search);
                    System.out.println("Entrada agregada con exito");
                    diccionario.guardarDiccionario();
                } else if (!result.equals("2")) {
                    System.out.println("Disculpa, pero no te entendí");
                }
            } while (!result.equals("1") && !result.equals("2"));

        }

    }

    public static void  agregarEntrada(){
        Scanner in = new Scanner(System.in);
        String search;
    

        System.out.println("Digite la nueva entrada:");
        System.out.print("> ");
        search = in.nextLine();

        
        if(diccionario.agregarEntrada(search)){
            System.out.println("Agregado al diccionario");
            diccionario.guardarDiccionario();
        }
        else{
            System.out.println("Esta palabra ya se encuetra contenida en el diccionario");
        }
    }

}
