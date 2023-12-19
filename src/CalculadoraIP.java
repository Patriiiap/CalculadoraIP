import java.util.Scanner;

public class CalculadoraIP {


    public static void main(String[] args) {

        System.out.println("Inserta tu IP");
        String IP = validarIP();

        System.out.println(IP);
    }

    public static String validarIP(){

        Scanner sc = new Scanner(System.in);

        String IP = "";
        char punto = '.';
        int contadorPunto = 0;
        boolean salir = true;

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



                //Verificar de los número solo pueden ir del 0 al 255
                //Pasamos los substring a int
                //Sin Terminar

                String[] substring = IP.split("\\."); /*Split es para dividir la ip en partes. El caracter
                                                  que marca el momento de división es el punto.*/
                String substring1 = substring[0];
                String substring2 = substring[1];
                String substring3 = substring[2];
                String substring4 = substring[3];;

                //Pasamos a int los string

                int subint1 = Integer.parseInt(substring1);
                int subint2 = Integer.parseInt(substring2);
                int subint3 = Integer.parseInt(substring3);
                int subint4 = Integer.parseInt(substring4);

                while (subint1 < 0 || subint1 > 255 || subint2 < 0 || subint2 > 255 || subint3 < 0 || subint3 > 255 || subint4 < 0 || subint4 > 255){
                    System.out.println("IP no válida, debes insertar valores entre el 0 y el 255");
                    IP = sc.nextLine();
                    //limpiamos las variables y rehacemos el proceso.
                    substring = IP.split("\\."); /*Split es para dividir la ip en partes. El caracter
                                                  que marca el momento de división es el punto.*/
                    substring1 = substring[0];
                    substring2 = substring[1];
                    substring3 = substring[2];
                    substring4 = substring[3];;

                    //Pasamos a int los string

                    subint1 = Integer.parseInt(substring1);
                    subint2 = Integer.parseInt(substring2);
                    subint3 = Integer.parseInt(substring3);
                    subint4 = Integer.parseInt(substring4);
                }

            }
            catch (Exception e){
                System.out.println("Se ha introducido una IP incorrrecta, inténtalo de nuevo");
                sc.nextLine();
            }
        } while();

        return(IP);
    }
}
