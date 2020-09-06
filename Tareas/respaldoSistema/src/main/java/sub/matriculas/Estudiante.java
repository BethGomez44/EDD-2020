package main.java.sub.matriculas;

import main.java.sub.matriculas.Materia;
import main.java.sub.libs.*;

import java.io.Serializable;
import java.text.Normalizer;

public class Estudiante implements Comparable<Estudiante> ,Serializable {
    private String nombre;
    private Lista<Materia> materias;
    private String numeroCuenta;
    private boolean numeroCuentaInicializado = false;

    /**
     * Constructor por default del estudiante
     * 
     * @param nombre nombre del estudiante
     */
    public Estudiante(String nombre) {
        this.nombre = nombre;
        this.numeroCuenta = "s/n";
    }

    /**
     * Método que devuelve el nombre del estudiante
     * 
     * @return nombre del estudiante
     */
    public String obtenerNombre() {
        return this.nombre;
    }

    /**
     * Método que devuelve el numero de cuenta del estudiante
     * 
     * @return s/n si aun no tiene asignado numero de cuenta, el numero de cuenta
     *         asignado encaso contrario
     */
    public String obtenerNumeroCta() {
        return this.numeroCuenta;
    }

    /**
     * Método que devuelve una lista con las materias que cursa el estudiante
     * 
     * @return null en caso de no cursar ninguna materia y una lista con todas las
     *         materias que cursa el alumno en caso contrario
     */
    public Lista<Materia> obtenerMaterias() {
        return this.materias;
    }

    /**
     * Asigna una UNICA vez el numero de cuenta , si el estudiante ya tenia un
     * numero de cuenta asignado arroja una advertencia
     * 
     * @param ncta numero de cuenta a asignar al estudiante
     * @return true si la asignacion fue exitosa , false en caso contrario
     */
    public boolean asignarNumeroCuenta(String ncta) {
        if (!numeroCuentaInicializado) {
            this.numeroCuentaInicializado = true;
            this.numeroCuenta = ncta;
            return true;
        } else {
            System.out.println("**Este estudiante ya tienen un numero de cuenta asignado**");
            return true;
        }
    }

    /**
     * Agrega una materia siempre y cuando el estudiante no la este cursando , si el
     * estudiante ya esta cursando la materia aroja una advertencia
     * 
     * @param m nueva materia agregar a su lista de materias
     */
    public void agregarMateria(Materia m) {
        if (this.materias == null) {
            materias = new Lista<>();
        }

        if (!this.cursa(m)) {
            this.materias.agregarAlFinal(m);
            materias = Lista.mergesort(materias);
        } else {
            System.out.println("**Este estudiante ya cursa ese grupo**");
        }

    }

    public void actualizarMateria(Materia m) {
        if (materias.contiene(m)) {
            materias.eliminar(m);
            materias.agregarAlFinal(m);
            materias = Lista.mergesort(materias);
        }
    }

    /**
     * Informa sobre si el estudiante cursa o no una materia
     * 
     * @param m materia a verificar
     * @return true si el estudiante cursa la materia , false en caso contrario
     */
    public boolean cursa(Materia m) {
        return this.materias.contiene(m);
    }

    @Override
    public int compareTo(Estudiante o) {

        return casoSinAcentos(this.nombre.toLowerCase()).compareTo(casoSinAcentos(o.nombre.toLowerCase()));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Estudiante comp = (Estudiante) o;
        return this.nombre.equals(comp.nombre)
                || (this.numeroCuenta.equals(comp.numeroCuenta) && !this.numeroCuenta.equals("s/n"));
    }

    @Override
    public String toString() {
        String exit = "";
        exit += numeroCuenta + "\t" + nombre;
        if (materias != null) {
            exit += "\nCursos:\n";
            for (Materia m : materias) {
                exit += m.toStringSimple() + "\n";
            }
        }

        return exit;
    }

    /**
     * Regresa la informacion del estudiante en formato simple
     * 
     * @return numero de cuenta + nombre de estudiante
     */
    public String toStringSimple() {
        return numeroCuenta + "\t" + nombre;
    }

    private static String casoSinAcentos(String cad) {
        if (cad == null) {
            return null;
        }
        cad = Normalizer.normalize(cad, Normalizer.Form.NFD); // separar en dos los caracteres especiales
        return cad.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }

}