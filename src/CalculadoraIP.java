import java.util.Scanner;

public class CalculadoraIP {


    public static void main(String[] args) {

        System.out.println("Inserta tu IP");
        String IP = validarIP();

        System.out.println("Tu IP es: " + IP);

        //System.out.println("Ahora elige cómo quieres insertar la máscara: 1 Decimal 2 CDIR: ");
        //int eleccionMascara = eleccionMascara();

        String mascara = "255.240.0.0";



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



        /*System.out.println("Calculo Binario Dirección de Red");
        imprimirArrayInt(ArrayIP);
        System.out.println(" ");
        imprimirArrayInt(ArrayMascara);
        System.out.println(" ");*/

        //CREAMOS un Array para guardar la direcc de red en Binario.
        int [] ArrayDireccRed = MultiplicarIPMasc(ArrayIP, ArrayMascara);
        /*imprimirArrayInt(ArrayDireccRed);
        System.out.println(" ");*/
        int numeroDe0s = numeroDe0s(ArrayDireccRed); //COntamos los 0s para poder calcular el broadcast


        System.out.println("Calculo Binario Broadcast: ");
        imprimirArrayInt(ArrayIP);
        System.out.println(" ");
        imprimirArrayInt(ArrayMascara);
        System.out.println(" ");

        //CREAMOS un Array para guardar la direcc de Broadcast en Binario.
        int[] ArrayBroadcast = BroadcastBin(ArrayIP, ArrayDireccRed, numeroDe0s);
        imprimirArrayInt(ArrayBroadcast);
        System.out.println(" ");


        String direccion = SepararBinarios(ArrayDireccRed);
        System.out.println("La dirección de red es: " + direccion);

        String direccionBroadcast = SepararBinarios(ArrayBroadcast);
        System.out.println("La dirección de Broadcast es: " + direccionBroadcast);



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

        for(int i = 0; i < IP.length(); i++){   //Usamos el codigo ASCII para comprobar que solo se insertan numeros o puntos.
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

//FUNCION para contar en numero de 0s de la dirección de red.

    public static int numeroDe0s (int [] ArrayDireccRed){

        int numeroDe0s = 0;
        boolean entrar = true;

        for(int i = ArrayDireccRed.length - 1; i > 0; i = i-1){                                     //EN MANTENIMIENTO

            if (ArrayDireccRed[i] == 0 && entrar == true){
                numeroDe0s++;
            }
            if (ArrayDireccRed[i] == 1){
                entrar = false;
            }

        }

        return(numeroDe0s);
    }

//FUNCION para sacar la dirección de Broadcast en Binario

    public static int[] BroadcastBin (int[] ArrayIP, int[] ArrayDireccRed, int numeroDe0s){         //EN MANTENIMIENTO

        int[] ArrayBroadcast = new int[32];
        int posicion = ArrayDireccRed.length - numeroDe0s;

        for(int i = ArrayBroadcast.length - 1; i >= 0; i--) {

            if(i >= numeroDe0s){
                ArrayBroadcast[i] = 1;
            }
            else{
                ArrayBroadcast[i] = ArrayIP[i];
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

        /*imprimirArray(octeto1);
        System.out.println(" ");
        imprimirArray(octeto2);
        System.out.println(" ");
        imprimirArray(octeto3);
        System.out.println(" ");
        imprimirArray(octeto4);*/

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
