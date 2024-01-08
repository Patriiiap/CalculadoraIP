import java.util.Scanner;

public class CalculadoraIP {


    public static void main(String[] args) {

        System.out.println("Inserta tu IP");
        String IP = validarIP();

        System.out.println("Tu IP es: " + IP);

        //System.out.println("Ahora elige cómo quieres insertar la máscara: 1 Decimal 2 CDIR: ");
        //int eleccionMascara = eleccionMascara();

        String mascara = "255.255.192.0";



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
        int [] ArrayIP = TransformarArray(octeto1IPBin, octeto2IPBin, octeto3IPBin, octeto4IPBin);
        System.out.println("Array IP");
        imprimirArray(ArrayIP);



        //Dividimos la máscara en 4 partes.
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




    }

    public static String validarIP(){

        Scanner sc = new Scanner(System.in);

        String IP = "";
        boolean valido = true;
        int contadorValido = 0;
        //boolean salir = true;


        do{
            contadorValido = 0;

            try{

                IP = sc.nextLine();

                valido = soloNumerosYPuntos(IP);     //Comprobamos que solo se introducen números y puntos.

                if (valido == false){
                    contadorValido++;
                }

                valido = maxYmin3Puntos(IP);         //Comprobamos que hay min 3 puntos y max 3 puntos.

                if (valido == false){
                    contadorValido++;
                }

                valido = noPuntosInicioFinal(IP);    //Comprobamos que no hay puntos ni al inicio ni al final.

                if (valido == false){
                    contadorValido++;
                }

                valido = noPuntosSeguidos(IP);       //Comprobamos que no hay 2 puntos seguidos

                if (valido == false){
                    contadorValido++;
                }

                valido = numerosDel0Al255(IP);       //Comprobamos que los números están entre el 0 y el 255

                if (valido == false){
                    contadorValido++;
                }

            }
            catch (Exception e){

            }

        } while(contadorValido != 0);

        return(IP);
    }


    //CAPTURA DE EXCEPCIONES

    //Captura excepción, solo pueden ser números y puntos.
    public static boolean soloNumerosYPuntos (String IP){

        boolean valido = true;

        for(int i = 0; i < IP.length(); i++){
            char caracter = IP.charAt(i);
            int caracterInt = (int) caracter;
            if(caracterInt != 46 && caracterInt < 48 || caracterInt > 57){
                System.out.println("Sólo se pueden ingresar carácteres numéricos y puntos, inserta tu IP de nuevo");
                valido = false;
            }
        }
        return(valido);
    }

    //Captura excepción, deben ser 3 puntos si o si
    public static boolean maxYmin3Puntos (String IP){

        boolean valido = true;
        char punto = '.';
        int contadorPunto = 0;

        for(int i = 0; i < IP.length(); i++){
            if (IP.charAt(i) == punto){
                contadorPunto++;
            }
        }
        if(contadorPunto != 3){
            System.out.println("IP no válida, se ha insertado una cantidad de puntos incorrecta");
            contadorPunto = 0;
            valido = false;
        }

        return(valido);
    }

    //Captura que no haya puntos en el índice 0 y último índice
    public static boolean noPuntosInicioFinal (String IP){

        boolean valido = true;

        if(IP.charAt(0) == '.'){
            System.out.println("IP no válida, has insertado un punto al inicio, inserta la IP de nuevo");
            valido = false;
        }
        if(IP.charAt(IP.length() - 1) == '.'){
            System.out.println("IP no válida, has insertado un punto al final, inserta la IP de nuevo");
            valido = false;
        }
        return (valido);
    }

    //Captura que no hayan 2 puntos seguidos
    public static boolean noPuntosSeguidos(String IP){

        boolean valido = true;

        int primeraPosPunto = 0;
        int segundaPosPunto = 0;
        int terceraPosPunto = 0;
        boolean salir1 = false;
        boolean salir2 = false;
        boolean salir3 = false;

        for(int i = 0; i < IP.length(); i++){
            if(IP.charAt(i) == '.' && salir1 == false){
                primeraPosPunto = i;
                salir1 = true;
            }
            if (IP.charAt(i) == '.' && salir2 == false && i != primeraPosPunto){
                segundaPosPunto = i;
                salir2 = true;
            }
            if (IP.charAt(i) == '.' && salir3 == false && i != primeraPosPunto && i != segundaPosPunto){
                terceraPosPunto = i;
                salir3 = true;
            }
        }

        if (segundaPosPunto == primeraPosPunto + 1 || terceraPosPunto == segundaPosPunto + 1){
            System.out.println("IP no válida, has introducido dos puntos seguidos, introduce la IP de nuevo:");
            valido = false;
        }
        return (valido);
    }

    //Verificar de los número solo pueden ir del 0 al 255
    public static boolean numerosDel0Al255(String IP){

        boolean valido = true;

        //Pasamos los substring a int

        String[] substring = IP.split("\\."); /*Split es para dividir la ip en partes. El caracter
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
            System.out.println("IP no válida, debes insertar valores entre el 0 y el 255, inserta tu IP de nuevo");
            valido = false;
        }

        return(valido);
    }




// FUNCION PARA PASAR A BINARIO

    public static String PasarBinario (String octeto){

        /*Esto para pasar los numeros a binario y ponerle los 0 necesarios a la izquierda.*/
        octeto = String.format("%8s", Integer.toBinaryString(Integer.parseInt(octeto))).replace(' ', '0');

        return (octeto);
    }



//Función para tener los octetos en un Array de Enteros

    public static int[] TransformarArray(String octeto1, String octeto2, String octeto3, String octeto4){

        int largo = octeto1.length() + octeto2.length() + octeto3.length() + octeto4.length();

        char [] array = new char[largo];
        int [] arrayInt = new int[array.length];
        int j = 0;

        for(int i = 0; i < largo; i++){

            if(i <= 7){
                array[i] = octeto1.charAt(j);
                j++;
                if (j > 7){
                    j = 0;
                }
            }
            if(i > 7 && i <= 14){
                array[i] = octeto2.charAt(j);
                j++;
                if (j > 7){
                    j = 0;
                }
            }
            if(i > 14 && i <= 23) {
                array[i] = octeto3.charAt(j);
                j++;
                if (j > 7) {
                    j = 0;
                }
            }
            if(i > 23 && i <= 31) {
                array[i] = octeto4.charAt(j);
                j++;
                if (j > 7) {
                    j = 0;
                }
            }
        }

        for (int i = 0; i < array.length; i++){
            arrayInt [i] = Integer.parseInt(String.valueOf(array[i]));
        }

        return(arrayInt);
    }

    public static void imprimirArray(int [] array){

        for(int i = 0; i < array.length; i++){
            System.out.print(array[i]);
        }

    }

}
