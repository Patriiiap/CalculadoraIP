import java.lang.reflect.Array;
import java.util.Scanner;
public class CalcularIP {

    public static void main(String[] args) {
        String IP = "";
        String mascara = "";
        int CIDR;
        int opcion;
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduce una IP para calcular sus parámetros:");
        IP = validarIP();

        System.out.println("Tu IP es: " + IP);

        System.out.println("Ahora, ¿qué notación quieres utilizar para introducir la máscara? (PULSA 1 o 2)");
        System.out.println("1. Notación CIDR       2. Notación decimal");

        try {
            do {
                opcion = sc.nextInt();
                if (opcion > 2 || opcion < 1) {
                    System.out.println("Introduce una opción válida.");
                }
            } while (opcion > 2 || opcion < 1);

            switch (opcion) {
                case 1:
                    do {
                        System.out.println("Introduce el CIDR:");
                        CIDR = sc.nextInt();
                        if (!validarCIDR(CIDR)) {
                            System.out.println("El CIDR debe ser un número del 0 al 32.");
                        }
                    } while (!validarCIDR(CIDR));
                    break;

                case 2:
                    System.out.println("Introduce la máscara:");
                    mascara = validarMascara();
                    String[] octetoMascara = mascara.split("\\.");

                    String octeto1Mascara = octetoMascara[0];
                    String octeto2Mascara = octetoMascara[1];
                    String octeto3Mascara = octetoMascara[2];
                    String octeto4Mascara = octetoMascara[3];

                    String octeto1MascaraBin = PasarBinario(octeto1Mascara);
                    String octeto2MascaraBin = PasarBinario(octeto2Mascara);
                    String octeto3MascaraBin = PasarBinario(octeto3Mascara);
                    String octeto4MascaraBin = PasarBinario(octeto4Mascara);

                    //Creamos un Array de Enteros para guardar la máscara en Binario.

                    int[] ArrayMascara = TransformarArray(octeto1MascaraBin, octeto2MascaraBin, octeto3MascaraBin, octeto4MascaraBin);
                    System.out.println("Array Máscara");
                    imprimirArray(ArrayMascara);
                    System.out.println();
                    break;

                default:
            }
        } catch (Exception e) {
            System.out.println("Solo puedes introducir números.");
        }

        //Dividimos la IP en 4 partes.
        String[] octetoIP = IP.split("\\."); /*Split es para dividir la ip en partes. El caracter que marca el momento de división es el punto.*/

        String octeto1IP = octetoIP[0];
        String octeto2IP = octetoIP[1];
        String octeto3IP = octetoIP[2];
        String octeto4IP = octetoIP[3];

        String octeto1IPBin = PasarBinario(octeto1IP);
        String octeto2IPBin = PasarBinario(octeto2IP);
        String octeto3IPBin = PasarBinario(octeto3IP);
        String octeto4IPBin = PasarBinario(octeto4IP);

        //Creamos un Array de Enteros para guardar la IP en Binario.
        int[] ArrayIP = TransformarArray(octeto1IPBin, octeto2IPBin, octeto3IPBin, octeto4IPBin);
        System.out.println("Array IP");
        imprimirArray(ArrayIP);
        System.out.println();

        //Dividimos la máscara en 4 partes.
    }

    public static String validarIP() {

        Scanner sc = new Scanner(System.in);
        String IP = "";
        boolean valido = true;
        int contadorValido = 0;
        //boolean salir = true;

        do {
            contadorValido = 0;

            try {

                IP = sc.nextLine();

                valido = soloNumerosYPuntos(IP);     //Comprobamos que solo se introducen números y puntos.

                if (!valido) {
                    contadorValido++;
                }

                valido = maxYmin3Puntos(IP);         //Comprobamos que hay min 3 puntos y max 3 puntos.

                if (!valido) {
                    contadorValido++;
                }

                valido = noPuntosInicioFinal(IP);    //Comprobamos que no hay puntos ni al inicio ni al final.

                if (!valido) {
                    contadorValido++;
                }

                valido = noPuntosSeguidos(IP);       //Comprobamos que no hay 2 puntos seguidos

                if (!valido) {
                    contadorValido++;
                }

                valido = numerosDel0Al255(IP);       //Comprobamos que los números están entre el 0 y el 255

                if (!valido) {
                    contadorValido++;
                }

            } catch (Exception e) {

            }

        } while (contadorValido != 0);

        return (IP);
    }

    public static String validarMascara() {
        Scanner sc = new Scanner(System.in);
        String mask = "";
        boolean valido = true;
        int contadorValido = 0;
        //boolean salir = true;

        do {
            contadorValido = 0;

            try {

                mask = sc.nextLine();

                valido = soloNumerosYPuntos(mask);     //Comprobamos que solo se introducen números y puntos.

                if (!valido) {
                    contadorValido++;
                }

                valido = maxYmin3Puntos(mask);         //Comprobamos que hay min 3 puntos y max 3 puntos.

                if (!valido) {
                    contadorValido++;
                }

                valido = noPuntosInicioFinal(mask);    //Comprobamos que no hay puntos ni al inicio ni al final.

                if (!valido) {
                    contadorValido++;
                }

                valido = noPuntosSeguidos(mask);       //Comprobamos que no hay 2 puntos seguidos

                if (!valido) {
                    contadorValido++;
                }

                valido = numerosDel0Al255(mask);       //Comprobamos que los números están entre el 0 y el 255

                if (!valido) {
                    contadorValido++;
                }

                valido = mascaraValida(mask);

                if (!valido) {
                    contadorValido++;
                }

            } catch (Exception e) {
            }

        } while (contadorValido != 0);


        return mask;
    }
    //CAPTURA DE EXCEPCIONES

    //Captura excepción, solo pueden ser números y puntos.
    public static boolean soloNumerosYPuntos(String IP) {

        boolean valido = true;

        for (int i = 0; i < IP.length(); i++) {
            char caracter = IP.charAt(i);
            int caracterInt = (int) caracter;
            if (caracterInt != 46 && caracterInt < 48 || caracterInt > 57) {
                System.out.println("Formato no válido, solo se pueden ingresar carácteres numéricos y puntos. Inténtalo de nuevo:");
                valido = false;
            }
        }
        return (valido);
    }

    //Captura excepción, deben ser 3 puntos si o si
    public static boolean maxYmin3Puntos(String IP) {

        boolean valido = true;
        char punto = '.';
        int contadorPunto = 0;

        for (int i = 0; i < IP.length(); i++) {
            if (IP.charAt(i) == punto) {
                contadorPunto++;
            }
        }
        if (contadorPunto != 3) {
            System.out.println("Formato no válido, se ha insertado una cantidad de puntos incorrecta. Inténtalo de nuevo:");
            contadorPunto = 0;
            valido = false;
        }

        return (valido);
    }

    //Captura que no haya puntos en el índice 0 y último índice
    public static boolean noPuntosInicioFinal(String IP) {

        boolean valido = true;

        if (IP.charAt(0) == '.') {
            System.out.println("Formato no válido, has insertado un punto al inicio. Inténtalo de nuevo:");
            valido = false;
        }
        if (IP.charAt(IP.length() - 1) == '.') {
            System.out.println("Formato no válido, has insertado un punto al final. Inténtalo de nuevo:");
            valido = false;
        }
        return (valido);
    }

    //Captura que no hayan 2 puntos seguidos
    public static boolean noPuntosSeguidos(String IP) {

        boolean valido = true;

        int primeraPosPunto = 0;
        int segundaPosPunto = 0;
        int terceraPosPunto = 0;
        boolean salir1 = false;
        boolean salir2 = false;
        boolean salir3 = false;

        for (int i = 0; i < IP.length(); i++) {
            if (IP.charAt(i) == '.' && !salir1) {
                primeraPosPunto = i;
                salir1 = true;
            }
            if (IP.charAt(i) == '.' && !salir2 && i != primeraPosPunto) {
                segundaPosPunto = i;
                salir2 = true;
            }
            if (IP.charAt(i) == '.' && !salir3 && i != primeraPosPunto && i != segundaPosPunto) {
                terceraPosPunto = i;
                salir3 = true;
            }
        }

        if (segundaPosPunto == primeraPosPunto + 1 || terceraPosPunto == segundaPosPunto + 1) {
            System.out.println("Formato no válido, has introducido dos puntos seguidos. Inténtalo de nuevo:");
            valido = false;
        }
        return (valido);
    }

    //Verificar de los número solo pueden ir del 0 al 255
    public static boolean numerosDel0Al255(String ipmask) {

        boolean valido = true;

        //Pasamos los substring a int

        String[] substring = ipmask.split("\\."); /*Split es para dividir la ip/máscara en partes. El caracter
                                                       que marca el momento de división es el punto.*/
        String substring1 = substring[0];
        String substring2 = substring[1];
        String substring3 = substring[2];
        String substring4 = substring[3];

        //Pasamos a int los string

        int subint1 = Integer.parseInt(substring1);
        int subint2 = Integer.parseInt(substring2);
        int subint3 = Integer.parseInt(substring3);
        int subint4 = Integer.parseInt(substring4);

        if (subint1 < 0 || subint1 > 255 || subint2 < 0 || subint2 > 255 || subint3 < 0 || subint3 > 255 || subint4 < 0 || subint4 > 255) {
            System.out.println("Formato no válido, debes insertar valores entre el 0 y el 255. Inténtalo de nuevo:");
            valido = false;
        }
        return (valido);
    }
// FUNCION PARA PASAR A BINARIO

    public static String PasarBinario(String octeto) {

        /*Esto para pasar los numeros a binario y ponerle los 0 necesarios a la izquierda.*/
        octeto = String.format("%8s", Integer.toBinaryString(Integer.parseInt(octeto))).replace(' ', '0');

        return (octeto);
    }
//Función para tener los octetos en un Array de Enteros

    public static int[] TransformarArray(String octeto1, String octeto2, String octeto3, String octeto4) {

        int largo = octeto1.length() + octeto2.length() + octeto3.length() + octeto4.length();

        char[] array = new char[largo];
        int[] arrayInt = new int[array.length];
        int j = 0;

        for (int i = 0; i < largo; i++) {

            if (i <= 7) {
                array[i] = octeto1.charAt(j);
                j++;
                if (j > 7) {
                    j = 0;
                }
            }
            if (i > 7 && i <= 14) {
                array[i] = octeto2.charAt(j);
                j++;
                if (j > 7) {
                    j = 0;
                }
            }
            if (i > 14 && i <= 23) {
                array[i] = octeto3.charAt(j);
                j++;
                if (j > 7) {
                    j = 0;
                }
            }
            if (i > 23 && i <= 31) {
                array[i] = octeto4.charAt(j);
                j++;
                if (j > 7) {
                    j = 0;
                }
            }
        }

        for (int i = 0; i < array.length; i++) {
            arrayInt[i] = Integer.parseInt(String.valueOf(array[i]));
        }

        return (arrayInt);
    }

    public static void imprimirArray(int[] array) {

        for (int j : array) {
            System.out.print(j);
        }

    }

    public static Boolean validarCIDR(int CIDR) {
        return CIDR >= 0 && CIDR <= 32;
    }

    public static Boolean mascaraValida(String mask) {

        boolean valido = true;

        String[] octetoMascara = mask.split("\\.");

        String octeto1Mascara = octetoMascara[0];
        String octeto2Mascara = octetoMascara[1];
        String octeto3Mascara = octetoMascara[2];
        String octeto4Mascara = octetoMascara[3];

        String octeto1MascaraBin = PasarBinario(octeto1Mascara);
        String octeto2MascaraBin = PasarBinario(octeto2Mascara);
        String octeto3MascaraBin = PasarBinario(octeto3Mascara);
        String octeto4MascaraBin = PasarBinario(octeto4Mascara);

        //Creamos un Array de Enteros para guardar la máscara en Binario.

        int[] ArrayMascara = TransformarArray(octeto1MascaraBin, octeto2MascaraBin, octeto3MascaraBin, octeto4MascaraBin);
        int cont = 0;

        for (int i = 0; i < ArrayMascara.length; i++) {
            if (ArrayMascara[i] == 0) {
                if (ArrayMascara[i + 1] == 1) {
                    System.out.println("Formato de máscara no válido. Inténtalo de nuevo:");
                    valido = false;
                    break;
                } else if (ArrayMascara[i + 1] == 0){
                    cont++;
                }
            } else if (ArrayMascara[i] == 1) {
                cont++;
            }
        }

        return (valido);
    }
}