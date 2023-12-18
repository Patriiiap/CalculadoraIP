import java.util.Scanner;

public class CalculadoraIP {


    public static void main(String[] args) {

        System.out.println("Inserta tu IP");
        String IP = validarIP();
    }

    public static String validarIP(){

        Scanner sc = new Scanner(System.in);

        boolean salir = false;
        String IP = "";
        char punto = '.';
        int contadorPunto = 0;

        do{
            try{
                IP = sc.nextLine();

                for(int i = 0; i < IP.length(); i++){
                    char caracter = IP.charAt(i);
                    int caracterInt = (int) caracter;
                    if(caracterInt != 46 && caracterInt < 48 || caracterInt > 57){
                        System.out.println("Sólo se pueden ingresar carácteres numéricos y puntos");
                        System.out.println("Inserta la IP de nuevo: ");
                        IP = sc.nextLine();
                        i = -1;
                    }
                }

                //Captura excepción, deben ser 3 puntos si o si
                do{
                    for(int i = 0; i < IP.length(); i++){
                        if (IP.charAt(i) == punto){
                            contadorPunto++;
                        }
                    }
                    while(contadorPunto != 3){
                        System.out.println("IP no válida (se ha insertado una cantidad de puntos incorrecta)");
                        IP = sc.nextLine();
                        contadorPunto = 0;

                        for(int i = 0; i < IP.length(); i++){
                            if (IP.charAt(i) == punto){
                                contadorPunto++;
                            }
                        }
                    }
                } while (contadorPunto != 3);


                //Captura que no haya puntos en el índice 0 y último índice
                do{
                    if(IP.charAt(0) == '.'){
                        System.out.println("IP no válida, has insertado un punto al inicio, inserta la IP de nuevo");
                        IP = sc.nextLine();
                    }
                    if(IP.charAt(IP.length() - 1) == '.'){
                        System.out.println("IP no válida, has insertado un punto al final, inserta la IP de nuevo");
                        IP = sc.nextLine();
                    }
                }while(IP.charAt(0) == '.' && IP.charAt(IP.length() - 1) == '.');


                //Captura que no hayan 2 puntos seguidos

                int primeraPosPunto;
                int segundaPosPunto;
                int terceraPosPunto;
                boolean salir1;
                boolean salir2;
                boolean salir3;

                do{
                    //Devolvemos las variables a 0 para iniciar de nuevo el bucle
                    primeraPosPunto = 0;
                    segundaPosPunto = 0;
                    terceraPosPunto = 0;
                    salir1 = false;
                    salir2 = false;
                    salir3 = false;

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
                        IP = sc.nextLine();
                    }

                } while (segundaPosPunto == primeraPosPunto + 1 || terceraPosPunto == segundaPosPunto + 1);

                //Pasamos los substring a int (vamos a coger las variables de punto de la función anterior)
                //Sin Terminar

                int largo = IP.length();

                char[] IPChar = new char[largo];

                IPChar.toString();

                for(int i = 0; i < largo; i++){
                    IPChar[i] = IP.charAt(i);
                }

                String[] substring1 = new String[primeraPosPunto];
                String[] substring2 = new String[segundaPosPunto - primeraPosPunto];
                String[] substring3 = new String[terceraPosPunto - segundaPosPunto];
                String[] substring4 = new String[IP.length() - terceraPosPunto];

                for(int i = 0; i < primeraPosPunto; i++) {
                    substring1[i] = String.valueOf(IPChar[i]);
                    System.out.print(substring1[i]);
                }
                for(int i = primeraPosPunto + 1; i < segundaPosPunto - 1; i++) {
                    substring2[i] = String.valueOf(IPChar[i]);
                    System.out.print(substring2[i]);
                }
                for(int i = segundaPosPunto + 1; i < terceraPosPunto - 1; i++) {
                    substring2[i] = String.valueOf(IPChar[i]);
                    System.out.print(substring3[i]);
                }
                for(int i = terceraPosPunto + 1; i < IP.length() - 1; i++) {
                    substring2[i] = String.valueOf(IPChar[i]);
                    System.out.print(substring4[i]);
                }



            }
            catch (Exception e){

            }
        } while (salir = false);

        return(IP);
    }
}
