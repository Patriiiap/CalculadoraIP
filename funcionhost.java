public class funcionhost {
    public static void main(String[] args) {
        //Para calcular la cantidad de hosts por red, se usa la fórmula 2^n - 2 donde n corresponde a la cantidad de bits para hosts.
        // La aplicación de esta fórmula, (2^7 - 2 = 126) muestra que cada una de estas subredes puede tener 126 hosts. 2^n - 2.
        //int n =28;
        //int resultado= 2^n-2;
        //System.out.println("el numero de host es: " + resultado);
        int resultado = Calcularhost(7);
        System.out.println("el numero de host es: "+ resultado );
    }
         public static int Calcularhost (int n){
            // int resultado = 2^n - 2;
             int resultado = (int) Math.pow(2,7)-2;
            return resultado;
         }
    }