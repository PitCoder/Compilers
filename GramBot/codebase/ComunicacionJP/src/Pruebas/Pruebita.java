package Pruebas;

import java.util.ArrayList;

public class Pruebita {
    public static void main(String[] args) {
        ArrayList<String> nuevo = new ArrayList<>();
        String holaa = null;
        nuevo.add("45");
        nuevo.add("Mi");
        nuevo.add("Amigo");
        nuevo.add("Lolo");
        nuevo.add(holaa);
        nuevo.add("45");
        /*if(nuevo.get(0).equals(""))
        System.out.println(nuevo);
        nuevo.remove(0);
        System.out.println(nuevo);
        String prueba = "Cadena@adsa";
        if(prueba.startsWith("Cadena"))
            System.out.println("Empieza con cadena");
        if(nuevo.get(0).equals(nuevo)){
        }*/
        System.out.println(nuevo.get(0).length());
        System.out.println(nuevo.get(0).charAt(0));
        if(nuevo.get(0).startsWith("H")){
            System.out.println("Empieza con h");
        }
        if(nuevo.get(4)==null){
            System.out.println("Tiene nulo");
        }
        Double nuevo2 =  new Double(0);
        System.out.println(nuevo2);
        String numero = nuevo.get(0);
        float num = (float)Double.parseDouble(numero);
        System.out.println("El numero es: " + num);
        if(!nuevo.isEmpty())
            System.out.println("No está vacío");        
    }
}
