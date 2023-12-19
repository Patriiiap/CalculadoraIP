import java.util.Scanner;

public class deBinarioaDecimal {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("Pon una IP válida de prueba: ");
        String IP = sc.nextLine();

        String[] octeto = IP.split("\\."); /*Split es para dividir la ip en partes. El caracter
                                                  que marca el momento de división es el punto.*/

        String octeto1 = octeto[0]; //Estas son las partes.
        String octeto2 = octeto[1];
        String octeto3 = octeto[2];
        String octeto4 = octeto[3];


        /*Esto para pasar los numeros a binario y ponerle los 0 necesarios a la izquierda.
          Esto es lo que más tiempo me ha llevado. Casi toda la tarde xD)*/
        String octeto1binario = String.format("%8s", Integer.toBinaryString(Integer.parseInt(octeto1))).replace(' ', '0');
        String octeto2binario = String.format("%8s", Integer.toBinaryString(Integer.parseInt(octeto2))).replace(' ', '0');
        String octeto3binario = String.format("%8s", Integer.toBinaryString(Integer.parseInt(octeto3))).replace(' ', '0');
        String octeto4binario = String.format("%8s", Integer.toBinaryString(Integer.parseInt(octeto4))).replace(' ', '0');

        /*Esto, en teoría, es para pasar los números a INT pero, no me saleeeeeeeeee!!!! Cuando voy a sacarlos con
        println no los muestra en binario. El número 2 le indica al programa que estamos trabajando en base 2, que
        es la base binaria. Si no pones la base, automáticamente te lo da como base 10, que es la base decimal y
        después puede haber problemas a la hora de hacer operaciones.*/
        int octeto1final = Integer.parseInt(octeto1binario, 2);
        int octeto2final = Integer.parseInt(octeto2binario, 2);
        int octeto3final = Integer.parseInt(octeto3binario, 2);
        int octeto4final = Integer.parseInt(octeto4binario, 2);

        /* Por último, esto es para mostrar los resultados*/
        //AND x&Y -- OR x|y
        System.out.println("--IP convertida a binario--");
        System.out.println("Octeto 1: " + octeto1binario);
        System.out.println("Octeto 2: " + octeto2binario);
        System.out.println("Octeto 3: " + octeto3binario);
        System.out.println("Octeto 4: " + octeto4binario);


        System.out.println("Ahora inserta una máscara válida: ");
        String mascara = sc.nextLine();

        String[] octetomascara = mascara.split("\\.");


        String octetomascara1 = octetomascara[0]; //Estas son las partes.
        String octetomascara2 = octetomascara[1];
        String octetomascara3 = octetomascara[2];
        String octetomascara4 = octetomascara[3];


        String octetomascara1binario = String.format("%8s", Integer.toBinaryString(Integer.parseInt(octetomascara1))).replace(' ', '0');
        String octetomascara2binario = String.format("%8s", Integer.toBinaryString(Integer.parseInt(octetomascara2))).replace(' ', '0');
        String octetomascara3binario = String.format("%8s", Integer.toBinaryString(Integer.parseInt(octetomascara3))).replace(' ', '0');
        String octetomascara4binario = String.format("%8s", Integer.toBinaryString(Integer.parseInt(octetomascara4))).replace(' ', '0');

        int octetomascara1final = Integer.parseInt(octetomascara1binario, 2);
        int octetomascara2final = Integer.parseInt(octetomascara2binario, 2);
        int octetomascara3final = Integer.parseInt(octetomascara3binario, 2);
        int octetomascara4final = Integer.parseInt(octetomascara4binario, 2);


        System.out.println("--Máscara convertida a binario--");
        System.out.println("Octeto 1: " + octetomascara1binario);
        System.out.println("Octeto 2: " + octetomascara2binario);
        System.out.println("Octeto 3: " + octetomascara3binario);
        System.out.println("Octeto 4: " + octetomascara4binario);


        System.out.println("---------------------------------");
        AveriguarClase(octeto1binario);
    }

    /*Para averiguar la clase hay que tener el primer octeto de la ip en binario de 8 dígitos y mirar los caracteres.
    Si el primer caracter (se miran de izq a der) es un 0 es clase A. Si es un 1 pasamos al segundo número. Si ese
    segundo número es un 0, la IP es clase B. Si es un uno, pasamos al tercer caracter, y así sucesivamente*/
    public static void AveriguarClase(String octeto1binario) {
        char primercaracter = octeto1binario.charAt(0);

        switch (primercaracter) {
            case '0':
                System.out.println("La IP es: CLASE A");
                break;
            case '1':
                char segundocaracter = octeto1binario.charAt(1);
                switch (segundocaracter) {
                    case '0':
                        System.out.println("La IP es: CLASE B");
                        break;
                    case '1':
                        char tercercaracter = octeto1binario.charAt(2);
                        switch (tercercaracter) {
                            case '0':
                                System.out.println("La IP es: CLASE C");
                                break;
                            case '1':
                                char cuartocaracter = octeto1binario.charAt(3);
                                switch (cuartocaracter) {
                                    case '0':
                                        System.out.println("La IP es: CLASE D");
                                        break;
                                    case '1':
                                        System.out.println("La IP es: CLASE E");
                                        break;
                                }
                        }
                    }
                }
            }
        }
