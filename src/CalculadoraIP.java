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

            }
            catch (Exception e){

            }
        } while (salir = false);

        return(IP);
    }
}
