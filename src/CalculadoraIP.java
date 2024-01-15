import java.util.InputMismatchException;
import java.util.Scanner;

public class CalculadoraIP {


    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        //Variables generales que se usan en el main
        String mascara = "";
        int CIDR = 0;
        int opcion;

        System.out.println("Inserta tu IP");
        String IP = validarIP();

        System.out.println("Tu IP es: " + IP);

        //DIVIDIMOS la IP en 4 partes.
        String[] octetoIP = IP.split("\\."); /*Split es para dividir la ip en partes. El caracter que marca el momento de división es el punto.*/

        String octeto1IP = octetoIP[0];
        String octeto2IP = octetoIP[1];
        String octeto3IP = octetoIP[2];
        String octeto4IP = octetoIP[3];

        String octeto1IPBin = PasarBinario(octeto1IP);
        String octeto2IPBin = PasarBinario(octeto2IP);
        String octeto3IPBin = PasarBinario(octeto3IP);
        String octeto4IPBin = PasarBinario(octeto4IP);

        //CREAMOS un Array de Enteros para guardar la IP en Binario.
        int [] ArrayIP = TransformarArray(octeto1IPBin, octeto2IPBin, octeto3IPBin, octeto4IPBin);

        System.out.println("Ahora, ¿qué notación quieres utilizar para introducir la máscara? (PULSA 1 o 2)");
        System.out.println("1. Notación CIDR       2. Notación decimal");

        boolean salir = true; //Variable para salir del try catch

        do{
            try {
                salir = true;

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
                            if(validarCIDR(CIDR)){
                                int[] ArrayMascaraCIDR = new int[32];
                                int contador = 0;

                                for(int i = 0; i < ArrayMascaraCIDR.length; i++){
                                    if(contador < CIDR){
                                        ArrayMascaraCIDR[i] = 1;
                                        contador++;
                                    }
                                    else{
                                        ArrayMascaraCIDR[i] = 0;
                                    }
                                }

                                mascara = SepararBinarios(ArrayMascaraCIDR);

                            }
                        } while (!validarCIDR(CIDR));
                        break;

                    case 2:
                        System.out.println("Introduce la máscara:");
                        mascara = validarMascara();
                        break;

                    default:
                }
            } catch (InputMismatchException e) {
                System.out.println("Solo puedes introducir números.");
                salir = false;
                sc.next();
            }
        }
        while (salir == false);


        //DIVIDIMOS la máscara en 4 partes.
        String[] octetoMascara = mascara.split("\\.");

        String octeto1Mascara = octetoMascara[0];
        String octeto2Mascara = octetoMascara[1];
        String octeto3Mascara = octetoMascara[2];
        String octeto4Mascara = octetoMascara[3];

        String octeto1MascaraBin = PasarBinario(octeto1Mascara);
        String octeto2MascaraBin = PasarBinario(octeto2Mascara);
        String octeto3MascaraBin = PasarBinario(octeto3Mascara);
        String octeto4MascaraBin = PasarBinario(octeto4Mascara);

        //CREAMOS un Array de Enteros para guardar la máscara en Binario.
        int[] ArrayMascara = TransformarArray(octeto1MascaraBin, octeto2MascaraBin, octeto3MascaraBin, octeto4MascaraBin);

            //En caso de no haber usado el CDIR, rellenamos la variable para después poder imprimirla.
        if(CIDR == 0){
            CIDR = numeroDe1s(ArrayMascara);
        }

        //CREAMOS un Array para guardar la dirección de red en Binario.
        int [] ArrayDireccRed = MultiplicarIPMasc(ArrayIP, ArrayMascara);

        //CREAMOS un Array para guardar la direcc de Broadcast en Binario, llamamos a la función para multiplicar IP y Máscara.
        int[] ArrayBroadcast = BroadcastBin(ArrayDireccRed, ArrayMascara);

        //CREAMOS una variable tipo string y llamamos a la función SepararBinarios (Que separa un Array de enteros en binario, y la convierte en una dirección tipo String)
        String direccionRed = SepararBinarios(ArrayDireccRed);
        System.out.println("La dirección de red es: " + direccionRed + "/" + CIDR);

        //Volvemos a usar la función para convertir un Array de Binarios en una dirección en String
        String direccionBroadcast = SepararBinarios(ArrayBroadcast);
        System.out.println("La dirección de Broadcast es: " + direccionBroadcast);

        System.out.println("Rango de direcciones disponibles: " + primerRango(direccionRed) + " - " + rangoBroadcast(direccionBroadcast));

        int numeroDe0s = numeroDe0s(ArrayMascara); //Contamos los 0s para calcular el numero de hosts disponibles.
        int numHosts = CalcularHosts(numeroDe0s);
        System.out.println("Número de Hosts disponibles: " + numHosts);

        //Averiguamos el tipo de IP y la Clase con las funciones.
        publicaPrivada(IP);
        AveriguarClase(octeto1IPBin);

        //Pasamos IP a Hexadecimal

        pasarHexadecimal(IP);

    }


    //FUNCIONES

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

                if (!valido){
                    contadorValido++;
                }

                valido = maxYmin3Puntos(IP);         //Comprobamos que hay min 3 puntos y max 3 puntos.

                if (!valido){
                    contadorValido++;
                }

                valido = noPuntosInicioFinal(IP);    //Comprobamos que no hay puntos ni al inicio ni al final.

                if (!valido){
                    contadorValido++;
                }

                valido = noPuntosSeguidos(IP);       //Comprobamos que no hay 2 puntos seguidos

                if (!valido){
                    contadorValido++;
                }

                valido = numerosDel0Al255(IP);       //Comprobamos que los números están entre el 0 y el 255

                if (!valido){
                    contadorValido++;
                }
            }
            catch (Exception e){

            }
        } while(contadorValido != 0);

        return(IP);
    }

    //VALIDAR MASCARA

    public static String validarMascara() {
        Scanner sc = new Scanner(System.in);
        String mask = "";
        boolean valido = true;
        int contadorValido = 0;

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

                valido = mascaraValida(mask);       //Comprobamos que en la máscara no hay 0s delante de 1s

                if (!valido) {
                    contadorValido++;
                }

            } catch (Exception e) {
            }

        } while (contadorValido != 0);


        return mask;
    }

    //Valida el CDIR

    public static Boolean validarCIDR(int CIDR) {
        return CIDR >= 0 && CIDR <= 32;
    }


    //CAPTURA DE EXCEPCIONES

    //Captura excepción, solo pueden ser números y puntos.
    public static boolean soloNumerosYPuntos (String IP){

        boolean valido = true;

        for(int i = 0; i < IP.length(); i++){   //Usamos el codigo ASCII para comprobar que solo se insertan numeros o puntos.
            char caracter = IP.charAt(i);
            int caracterInt = (int) caracter;
            if(caracterInt != 46 && caracterInt < 48 || caracterInt > 57){
                System.out.println("Formato no válido, sólo se pueden ingresar carácteres numéricos y puntos.");
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
            System.out.println("Formato no válido, se ha insertado una cantidad de puntos incorrecta.");
            contadorPunto = 0;
            valido = false;
        }

        return(valido);
    }

    //Captura que no haya puntos en el índice 0 y último índice
    public static boolean noPuntosInicioFinal (String IP){

        boolean valido = true;

        if(IP.charAt(0) == '.'){
            System.out.println("Formato no válido, has insertado un punto al inicio.");
            valido = false;
        }
        if(IP.charAt(IP.length() - 1) == '.'){
            System.out.println("Formato no válido, has insertado un punto al final.");
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
            System.out.println("Formato no válido, has introducido dos puntos seguidos.");
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
            System.out.println("Formato no válido, debes insertar valores entre el 0 y el 255.");
            valido = false;
        }

        return(valido);
    }

    //Verifica que la máscara  no tiene 0s delante de 1s

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
        int unos = 0;

        for (int i = 0; i < ArrayMascara.length; i++) {
            if (ArrayMascara[i] == 0) {
                if (ArrayMascara[i + 1] == 1) {
                    System.out.println("Formato de máscara no válido. Inténtalo de nuevo:");
                    valido = false;
                    break;
                }
            } else if (ArrayMascara[i] == 1) {
                unos = cont++;
            }
        }

        return (valido);
    }


// FUNCION PARA PASAR A BINARIO

    public static String PasarBinario (String octeto){

        //Esto para pasar los numeros a binario y ponerle los 0s necesarios a la izquierda.
        octeto = String.format("%8s", Integer.toBinaryString(Integer.parseInt(octeto))).replace(' ', '0');

        return (octeto);
    }


//FUNCION para tener los octetos en un Array de Enteros

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
            if(i > 7 && i <= 15){
                array[i] = octeto2.charAt(j);
                j++;
                if (j > 7){
                    j = 0;
                }
            }
            if(i > 15 && i <= 23) {
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


//FUNCION para multiplicar IP y Máscara Binario (Para conseguir la Direccion de red en Binario)

    public static int[] MultiplicarIPMasc (int[] ArrayIP, int[] ArrayMascara){

        int [] ArrayDireccRed = new int[32];

        for(int i = 0; i < ArrayDireccRed.length; i++){
            ArrayDireccRed[i] = (ArrayIP[i] * ArrayMascara[i]);
        }

        return(ArrayDireccRed);
    }

//FUNCIONES para calcular el rango de IPs disponibles

    public static String primerRango(String DireccRed) {
        String[] octetos = DireccRed.split("\\.");

        String primeraDirecc = "";

        String octeto1 = octetos[0];
        String octeto2 = octetos[1];
        String octeto3 = octetos[2];
        String octeto4 = octetos[3];

        int ultimoocteto = Integer.parseInt(octetos[3]);
        int penultimoocteto = Integer.parseInt(octetos[2]);

        if (ultimoocteto < 255) {
            primeraDirecc = (octeto1 + "." + octeto2 + "." + octeto3 + "." + (ultimoocteto + 1));
        } else  {
            primeraDirecc = (octeto1 + "." + octeto2 + "." + (penultimoocteto + 1) + "." + 0);
        }
        return (primeraDirecc);
    }

    public static String rangoBroadcast(String broadcast) {
        String[] octetos = broadcast.split("\\.");

        String ultimaDirecc = "";

        String octeto1 = octetos[0];
        String octeto2 = octetos[1];
        String octeto3 = octetos[2];
        String octeto4 = octetos[3];

        int ultimoocteto = Integer.parseInt(octetos[3]);
        int penultimoocteto = Integer.parseInt(octetos[2]);

        if (ultimoocteto > 0) {
            ultimaDirecc = (octeto1 + "." + octeto2 + "." + octeto3 + "." + (ultimoocteto - 1));
        } else {
            ultimaDirecc = (octeto1 + "." + octeto2 + "." + (penultimoocteto - 1) + "." + 255);
        }
        return (ultimaDirecc);
    }

//FUNCION para sacar la dirección de Broadcast en Binario

    public static int[] BroadcastBin (int[] ArrayDireccRed, int[] ArrayMascara){         //EN MANTENIMIENTO

        int[] ArrayBroadcast = new int[32];
        int[] ArrayNotMascara = new int[32];

        //Hacemos la operacion  NOT Mascara (Cambiamos los 0s por 1s)
        for(int i = 0; i < ArrayMascara.length; i++){
            if(ArrayMascara[i] == 0){
                ArrayNotMascara[i] = 1;
            }
            if (ArrayMascara[i] == 1){
                ArrayNotMascara[i] = 0;
            }
        }

        //Hacemos operacion IP OR NOT Mascara (Sumamos de forma lógica los 0s y 1s)
        for(int i = 0; i < ArrayDireccRed.length; i++){
            if(ArrayDireccRed[i] == 1 && ArrayNotMascara[i] == 1){
                ArrayBroadcast[i] = 1;
            }
            if(ArrayDireccRed[i] == 1 && ArrayNotMascara[i] == 0){
                ArrayBroadcast[i] = 1;
            }
            if(ArrayDireccRed[i] == 0 && ArrayNotMascara[i] == 1){
                ArrayBroadcast[i] = 1;
            }
            if(ArrayDireccRed[i] == 0 && ArrayNotMascara[i] == 0){
                ArrayBroadcast[i] = 0;
            }
        }

        return(ArrayBroadcast);
    }


//FUNCION Separar Arrays ENteros (Binario) e imprimir el resultado.

    public static String SepararBinarios (int[] ArrayBinarios){

        String[] ArrayCaracteres = new String[32];

        String[] octeto1 = new String[8];
        String[] octeto2 = new String[8];
        String[] octeto3 = new String[8];
        String[] octeto4 = new String[8];

        for(int i = 0; i < ArrayBinarios.length; i++){
            ArrayCaracteres[i]  = String.valueOf(ArrayBinarios[i]);     //Guardamos los caracteres en binario en un Array tipo String (porque en un Array tipo char no los guarda)
        }

        int j = 0;

        for (int i = 0; i < ArrayCaracteres.length; i++){
            if(i <= 7){
                octeto1[j] = ArrayCaracteres[i];
                j++;
                if (j > 7){
                    j = 0;
                }
            }
            if(i > 7 && i <= 15){
                octeto2[j] = ArrayCaracteres[i];
                j++;
                if (j > 7){
                    j = 0;
                }
            }
            if(i > 15 && i <= 23) {
                octeto3[j] = ArrayCaracteres[i];
                j++;
                if (j > 7) {
                    j = 0;
                }
            }
            if(i > 23 && i <= 31) {
                octeto4[j] = ArrayCaracteres[i];
                j++;
                if (j > 7) {
                    j = 0;
                }
            }
        }

                //Pasamos a String los Arrays
        StringBuffer octeto1String = new StringBuffer();
        StringBuffer octeto2String = new StringBuffer();
        StringBuffer octeto3String = new StringBuffer();
        StringBuffer octeto4String = new StringBuffer();

        for(int i = 0; i < octeto1.length; i++){
            octeto1String = octeto1String.append(octeto1[i]);
        }
        octeto1String.toString();


        for(int i = 0; i < octeto2.length; i++){
            octeto2String = octeto2String.append(octeto2[i]);
        }
        octeto2String.toString();


        for(int i = 0; i < octeto3.length; i++){
            octeto3String = octeto3String.append(octeto3[i]);
        }
        octeto3String.toString();


        for(int i = 0; i < octeto4.length; i++){
            octeto4String = octeto4String.append(octeto4[i]);
        }
        octeto4String.toString();


                //Pasamos a decimal los String en Binario

        int octeto1final = Integer.parseInt(String.valueOf(octeto1String), 2);
        int octeto2final = Integer.parseInt(String.valueOf(octeto2String), 2);
        int octeto3final = Integer.parseInt(String.valueOf(octeto3String), 2);
        int octeto4final = Integer.parseInt(String.valueOf(octeto4String), 2);

        String direccion = octeto1final + "." + octeto2final + "." + octeto3final + "." + octeto4final;

        return(direccion);
    }

//FUNCION para contar en numero de 0s de la dirección de red.

    public static int numeroDe0s (int [] ArrayMascara){

        int numeroDe0s = 0;

        for(int i = ArrayMascara.length - 1; i > 0; i = i-1){

            if (ArrayMascara[i] == 0){
                numeroDe0s++;
            }
        }

        return(numeroDe0s);
    }

//FUNCION para calcular el número de hosts disponibles.
    public static int CalcularHosts (int numDe0s){

        int numHosts = (int) Math.pow(2,numDe0s)-2;
        return (numHosts);
    }

//FUNCION para decir si la IP es pública o privada

    public static void publicaPrivada(String IP) {
        if (IP.startsWith("10.") || IP.startsWith("192.168.") || IP.startsWith("172.16.") ||
                IP.startsWith("172.17.") || IP.startsWith("172.18.") || IP.startsWith("172.19.") ||
                IP.startsWith("172.20.") || IP.startsWith("172.21.") || IP.startsWith("172.22.") ||
                IP.startsWith("172.23.") || IP.startsWith("172.24.") || IP.startsWith("172.25.") ||
                IP.startsWith("172.26.") || IP.startsWith("172.27.") || IP.startsWith("172.28.") ||
                IP.startsWith("172.29.") || IP.startsWith("172.30.") || IP.startsWith("172.31.")) {
            System.out.print("La IP es privada");
        } else {
            System.out.print("La IP es pública");
        }
    }

//FUNCION para decir la clase de la IP (A, B, C, D, E)

    public static void AveriguarClase(String octeto1binario) {
        char primerCaracter = octeto1binario.charAt(0);

        if (primerCaracter == '0') {
            System.out.println(" de clase A");
        } else if (primerCaracter == '1') {
            char segundoCaracter = octeto1binario.charAt(1);
            if (segundoCaracter == '0') {
                System.out.println(" de clase B");
            } else if (segundoCaracter == '1') {
                char tercerCaracter = octeto1binario.charAt(2);
                if (tercerCaracter == '0') {
                    System.out.println(" de clase C");
                } else if (tercerCaracter == '1') {
                    char cuartoCaracter = octeto1binario.charAt(3);
                    if (cuartoCaracter == '0') {
                        System.out.println(" de clase D");
                    } else if (cuartoCaracter == '1') {
                        System.out.println(" de clase E");
                    }
                }
            }
        }
    }

    //FUNCION para contar en numero de 1s de la Máscara en decimal.

    public static int numeroDe1s (int [] ArrayMascara){

        int numeroDe1s = 0;

        for(int i = 0; i < ArrayMascara.length; i++){
            if(ArrayMascara[i] == 1){
                numeroDe1s++;
            }
        }
        return(numeroDe1s);
    }

    //FUNCION para tener la IP en Hexadecimal

    public static void pasarHexadecimal (String IP){

        String[] octetos = IP.split("\\.");

        String octeto1 = octetos[0];
        String octeto2 = octetos[1];
        String octeto3 = octetos[2];
        String octeto4 = octetos[3];

        int octeto1Int = Integer.parseInt(octeto1);
        int octeto2Int = Integer.parseInt(octeto2);
        int octeto3Int = Integer.parseInt(octeto3);
        int octeto4Int = Integer.parseInt(octeto4);

        String octeto1Hex = String.format("%2s", Integer.toHexString(octeto1Int)).replace(' ', '0');
        String octeto2Hex = String.format("%2s", Integer.toHexString(octeto2Int)).replace(' ', '0');
        String octeto3Hex = String.format("%2s", Integer.toHexString(octeto3Int)).replace(' ', '0');
        String octeto4Hex = String.format("%2s", Integer.toHexString(octeto4Int)).replace(' ', '0');

        System.out.println("La IP en hexadecimal es: " + octeto1Hex.toUpperCase() + ":" + octeto2Hex.toUpperCase()
                + ":" + octeto3Hex.toUpperCase() + ":" + octeto4Hex.toUpperCase());
    }



//FUNCIONES para imprimir los Arrays de binario (No se están usando ahora).

    public static void imprimirArray(String [] array){

        for(int i = 0; i < array.length; i++){
            System.out.print(array[i]);
        }

    }

    public static void imprimirArrayInt(int [] array){

        for(int i = 0; i < array.length; i++){
            System.out.print(array[i]);
        }

    }

}
