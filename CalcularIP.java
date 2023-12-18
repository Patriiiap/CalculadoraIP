import java.util.InputMismatchException;
import java.util.Scanner;
public class CalcularIP {

    public static void obtenerParametros (String IP, int CDIR){

    }

    public static void obtenerParametros2 (String IP, String mask){

    }

    public static Boolean validarMascara (String mask) {
        int cont = 0;
        for (int i = 0; i < mask.length(); i++) {
            if (mask.charAt(i) == '.') {
                cont++;
            }
            if (cont > 3) {
                cont = 0;
                return false; //Contiene más de tres puntos.
            }
            if (mask.charAt(0) == '.' || mask.charAt(mask.length() - 1) == '.') {
                cont = 0;
                return false; //Contiene puntos al principio o al final.
            }
        }
        if (cont != 3){
            return false; //Contiene menos de tres puntos.
        }
        for (int i = 0; i < mask.length(); i++) {
            char caracter = mask.charAt(i);
            int caracterInt = (int) caracter;
            if (caracterInt != 46 && caracterInt < 48 || caracterInt > 57) {
                return false; //Contiene carácteres inválidos.
            }
        }
        return true; //Formato válido.
    }


    public static Boolean validarCIDR (int CIDR){
        return CIDR >= 0 && CIDR <= 32;
    }

    public static void main(String[] args){
        String ip;
        String mask;
        int CIDR;
        int cont = 0;
        int opcion;
        Scanner sc = new Scanner (System.in);

        System.out.println("Introduce una IP para cálcular sus parámetros: ");
        // IP
        System.out.println("¿Qué notación quieres utilizar para introducir la máscara? (PULSA 1 o 2)");
        System.out.println("1. Notación CIDR       2. Notación decimal");


        try {
            do {
                opcion = sc.nextInt();
                if (opcion > 2 || opcion < 1){
                    System.out.println("Introduce una opción válida.");
                }
            } while (opcion > 2 || opcion < 1);

            switch (opcion){
                case 1:
                    do {
                        System.out.println("Introduce el CIDR:");
                        CIDR = sc.nextInt();
                       if (!validarCIDR(CIDR)){
                           System.out.println("El CDIR debe ser un número del 0 al 32.");
                       }
                    } while (!validarCIDR(CIDR));
                     break;

                case 2:
                    System.out.println("Introduce la máscara:");
                    do {
                        mask = sc.next();
                        if (!validarMascara(mask)){
                            System.out.print("Formato de máscara inválido. Por favor, introduzca una máscara válida:");
                            System.out.println();
                        }
                    } while (!validarMascara(mask));
                    break;


                default:
            }
        } catch (InputMismatchException e){
            System.out.println("Solo puedes introducir números.");
        }
    }
}
