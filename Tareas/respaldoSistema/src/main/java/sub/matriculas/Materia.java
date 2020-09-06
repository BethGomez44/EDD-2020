package main.java.sub.matriculas;

import main.java.sub.libs.*;
import main.java.sub.matriculas.Estudiante;

import java.io.Serializable;
import java.text.Normalizer;

public class Materia implements Comparable<Materia>, Serializable {
    private String nombre;
    private String profesor;
    private String clave_delCurso;
    private Lista<Estudiante> estudiantes;
    private boolean prof_incializado = false, clave_inicializado = false;

    /**
     * Constructor por default de la materia
     * 
     * @param nombre nombre de la materia
     */
    public Materia(String nombre) {
        this.nombre = nombre;
        this.profesor = "s/a";
        this.clave_delCurso = "s/c";
    }

    /**
     * Regresa el nombre de la materia
     * 
     * @return nombre de la materia
     */
    public String obtenerNombre() {
        return this.nombre;
    }

    /**
     * Obtiene el profesor que imparte el curso
     * 
     * @return s/a si no tiene profesor asignado , el nombre del profesor que
     *         imparte el curso en caso contrario
     */
    public String obtenerProfesor() {
        return this.profesor;
    }

    /**
     * Obtiene la clave del curso
     * 
     * @return s/c si no tiene clave asignada , la clave del curso en caso contrario
     */
    public String obtenerClave() {
        return this.clave_delCurso;
    }

    /**
     * Metodo que regresa una lista con los estudiantes inscritos en la materia
     * 
     * @return lista de los estudiantes que estan inscritos en la materia
     */
    public Lista<Estudiante> obtenerEstudiantes() {
        return this.estudiantes;
    }

    /**
     * Metodo que asigna profesor a la materia, si la materia ya tiene un profesor
     * asignado arroja una advertencia por pantalla
     * 
     * @param profe profesor que imparte la materia
     * @return true si la asignacion fue exitosa , false en caso contrario
     */
    public boolean asignarProfesor(String profe) {
        if (!prof_incializado) {
            this.profesor = profe;
            prof_incializado = true;
            return true;
        } else {
            System.out.println("**Este curso ya tiene asignado un profesor**");
            return false;
        }
    }

    /***
     * Metodo que asigna una clave a la materia, si la materia ya tiene una clave
     * asignada, arroja una advertencia por pantalla
     * 
     * @param clv clave del curso
     * @return true si la asignacion fue exitosa , false en caso contrario
     */
    public boolean asignarClave(String clv) {
        if (!clave_inicializado) {
            this.clave_delCurso = clv;
            clave_inicializado = true;
            return true;
        } else {
            System.out.println("**Este curso ya tiene asignada una clave**");
            return false;
        }
    }

    /**
     * Metodo que agrega un estudiante siempre y cuando este no este inscrito
     * 
     * @param e estudiante a agregar
     */
    public void agregarEstudiante(Estudiante e) {
        if (this.estudiantes == null) {
            estudiantes = new Lista<>();
        }
        if (!this.inscrito(e)) {
            this.estudiantes.agregar(e);
            estudiantes = Lista.mergesort(estudiantes);
        } else {
            System.out.println("**Este estudiante ya esta inscrito en el curso*");
        }
    }

    public void actualizarEstudiante(Estudiante e) {
        if (estudiantes.contiene(e)) {
            estudiantes.eliminar(e);
            estudiantes.agregarAlFinal(e);
            estudiantes = Lista.mergesort(estudiantes);
        }
    }

    /**
     * Metodo que nos dice si un estudiante pasado por parametro esta inscrito en el
     * curso
     * 
     * @param e estudiante a buscar en el curso
     * @return true si esta inscrito , false en caso contrario
     */
    public boolean inscrito(Estudiante e) {
        return this.estudiantes.contiene(e);
    }

    @Override
    public int compareTo(Materia o) {

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
        Materia comp = (Materia) o;
        return this.nombre.equals(comp.nombre)
                || (this.clave_delCurso.equals(comp.clave_delCurso) && !this.clave_delCurso.equals("s/c"));
    }

    @Override
    public String toString() {
        String exit = "";
        exit += clave_delCurso + "\t" + nombre + "\t" + profesor + "\n Alumnos: \n";
        if (estudiantes != null) {
            for (Estudiante e : estudiantes) {
                exit += e.toStringSimple() + "\n";
            }
        }
        return exit;
    }

    /**
     * Regresa la informacion del estudiante en formato simple
     * 
     * @return clave del curso + materia + profesor
     */
    public String toStringSimple() {
        return clave_delCurso + "\t" + nombre + "\t" + profesor;
    }

    private static String casoSinAcentos(String cad) {
        if (cad == null) {
            return null;
        }
        cad = Normalizer.normalize(cad, Normalizer.Form.NFD); // separar en dos los caracteres especiales
        return cad.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }
}
