package mastermind;

import java.util.Scanner;

public class Mastermind {

    //Este contador lo uso para saber si el jugador ha acertado
    //todos los colores o no.
    static int contador = 0;

    //Funciones generales
    //Esta primera función la utilizo para introducir los datos
    //devolvera un String comprobando con un trycatch las
    //excepciones.
    static String ScannerString() {
        String color = "";
        boolean error = true;
        Scanner sc = new Scanner(System.in);
        do {
            try {
                color = sc.nextLine();
                error = false;
            } catch (Exception e) {
                System.out.println("Error " + e.getMessage());
                error = true;
            }
        } while (error);
        return color;
    }

    //Esta función la utilizo para generar un numero del 1 al 8
    //Ese numero representa un color.
    //Los codigos son "traducidos" a los colores en String
    //en la función TraductorCodigoColor() y TraductorColorCodigo()
    static int RandomGen() {
        int random;
        random = (int) (Math.random() * (9 - 1) + 1);
        return random;
    }

    //Esta función genera la combinación de colores
    //que el usuario tiene que adivinar.
    //Las normas son que los colores no se pueden repetir
    static void RellenarArray(int[] codigo) {
        boolean check = false;
        int temp1;

        for (int i = 0; i < codigo.length; i++) {
            do {
                temp1 = RandomGen();
                for (int j = 0; j < codigo.length; j++) {

                    if (codigo[j] == temp1) {
                        check = false;
                        break;
                    } else {
                        check = true;
                    }
                }
            } while (!check);
            codigo[i] = temp1;
        }
    }

    //Esta función saca por pantalla el array que recibe.
    //Normalmente lo uso para darle al usuario los colores que a introducido
    //en el anterior intento. Tambien lo utilizo para sacar
    //por pantalla el array del codigo generado para hacer debugging
    static void ImprimirArray(int[] codigo) {

        System.out.println("*********************\nCodigo Mastermind insertado: ");

        for (int c : codigo) {
            System.out.print(TraductorCodigoColor(c) + " ");
        }
        System.out.println("");
    }

    //Esta funcion es la responsable de rellenar el array
    //del jugador. Pedira al usuario que introduzca el
    //color que va a querer insertar en la posición indicada
    //Funciona pasando posicion por posicion del array
    //ejecutando la funcion ScannerString() y luego
    //traduciendo el input a int para guardarlo en
    //Input[]. Esto me parece mas facil que trabajar con String's
    //Comprueba si el usuario a introducido un color valido
    //y solo saldra del do-while cuando introduzca un color
    //valido. Esto lo hago usando un switch que devuelve
    //los codigos de los colores, pero si devuelve 0
    //(que es el default) pues lo tomara como un error.
    static void RellenarInput(int[] input) {
        boolean check = false;
        String color;

        for (int i = 0; i < input.length; i++) {
            System.out.println("Dime el color de la posicion " + (i + 1));
            do {
                color = ScannerString();
                int respuesta;
                respuesta = TraductorColorCodigo(color);
                if (respuesta == 0) {
                    check = false;
                    System.out.println("Error, eliga un color");
                } else {
                    input[i] = respuesta;
                    check = true;
                }
            } while (!check);
        }
    }

    //Esta función comprueba el estado del juego
    //Si el contador es igual a 10, signifca que
    //el jugador a adivinado todos los colores
    //(las fichas negras valen 2 puntos)
    //Devuelve un boolean true o false dependiendo
    //de si el jugador a llegado o no al score deseado
    static boolean CheckGameStatus() {

        if (contador == 10) {
            return true;
        } else {
            contador = 0;
            return false;
        }
    }

    //Ejecutar Funciones en orden
    //Esta función simplemente ejecuta las funciones en orden.
    //Como cada que se tiene que comprobar los resultados hay que ejecutar
    //estas funciones, pues simplemente aqui generalizo las funciones
    //y esto me permite añadir y quitar funciones de forma mas clara.
    static void EjecutarFunciones(int[] codigo, int[] jugadorInput, String[] resultado) {

        FBlanca(codigo, jugadorInput, resultado);
        FNegra(codigo, jugadorInput, resultado);
        FixNullArray(resultado);
        ImprimirResultados(resultado);
        LimpiarResultados(resultado);
        //Debugging //System.out.println("El contador esta a: "+contador);
    }

    //Comprobar Ficha blanca
    //Esta funcion comprueba el array del usuario y mira
    //si hay colores que existan en el array.
    //Si el color existe en alguna posición llamara a la funcion
    //InterpretadorArray() y le enviara el array resultado, la posicion
    //y un score de valor 1 para el contador anterior mencionado.
    static void FBlanca(int[] codigo, int[] input, String[] resultado) {

        for (int i = 0; i < input.length; i++) {
            for (int j = 0; j < codigo.length; j++) {
                if (input[i] == codigo[j]) {
                    InterpretadorArray(resultado, i, 1);
                }
            }
        }
    }

    //Comprobar Ficha Negra
    //Esta funcion comprueba el array del usuario y mira
    //si el color introducido en cada una de las posiciones del array
    //input coincide con los colores en el array codigo.
    //Si el color en las dos posiciones coincide
    //llama a la función InterpretadorArray() y le
    //envia el array resultado, la posición en el array y un
    //score de 2.
    static void FNegra(int[] codigo, int[] input, String[] resultado) {

        for (int i = 0; i < input.length; i++) {
            if (codigo[i] == input[i]) {
                InterpretadorArray(resultado, i, 2);
            }
        }
    }

    //Arreglar el null
    //Esta función elimina los "null" del array resultado
    //y lo rellena con un String de espacio vacio
    //representando que el usuario no a acertado
    //ni el color ni la posicion
    static void FixNullArray(String[] resultado) {

        for (int i = 0; i < resultado.length; i++) {
            if (resultado[i] == null) {
                resultado[i] = "        ";
            }
        }
    }

    //Imprime el Array resultado
    //Imprime el array resultado conteniendo las "Fichas"
    //Si el usuario se a equivocado en color y posicion saldra la cadena
    //vacia. Si no se ha equivocado en el color pero si la posicion
    //saldra f. blanca. Si ha acertado saldra f. negra
    static void ImprimirResultados(String[] resultado) {
        System.out.println("*********************\nLos resultados son: ");
        for (String p : resultado) {
            System.out.print(p + " | ");
        }
        System.out.println("");
    }

    //Limpia el array de resultados
    //Limpia el array de resultados para que en el proximo
    //turno no haya resultados del turno anterior
    static void LimpiarResultados(String[] resultado) {
        for (int i = 0; i < resultado.length; i++) {
            resultado[i] = null;
        }
    }

    //Conversor de letras a numeros
    //Esta funcion traduce un color en string a 
    //un numero representativo y lo devuelve.
    //Si no es un color valido devuelve 0
    //representando un error.
    static int TraductorColorCodigo(String color) {

        switch ((color).toLowerCase()) {

            case "blanco":
                return 1;
            case "negro":
                return 2;
            case "rosa":
                return 3;
            case "naranja":
                return 4;
            case "rojo":
                return 5;
            case "amarillo":
                return 6;
            case "verde":
                return 7;
            case "azul":
                return 8;

            default:
                return 0;
        }

    }

    //Conversor de numeros a letras
    //Esta funcion hace exactamente lo contrario
    //Recibe un numero representativo y devuelve un String
    //Si el color no es valido devuelve un mensaje de error
    static String TraductorCodigoColor(int color) {

        switch (color) {

            case 1:
                return "blanco";
            case 2:
                return "negro";
            case 3:
                return "rosa";
            case 4:
                return "naranja";
            case 5:
                return "rojo";
            case 6:
                return "amarillo";
            case 7:
                return "verde";
            case 8:
                return "azul";

            default:
                return "Off bounds";
        }

    }

    //Inserta el resultado en el array resultado en String
    //Aqui esta el interprete de los resultados
    //llama a la funcion InsertarEnPosicion() y le envia
    //un string dependiendo de si el usuario ha adivinado o no
    //el color. La funcion InsertarEnPoscion() recibe el array,
    //la posicion y el string a meter. 
    static void InterpretadorArray(String[] resultado, int pos, int res) {

        if (res == 1) {
            InsertarEnPosicion(resultado, pos, "F. Blanca");

        } else {
            if (res == 2) {
                InsertarEnPosicion(resultado, pos, "F. Negra");
                contador += 2;
            } else {
                InsertarEnPosicion(resultado, pos, "        ");
            }
        }

    }

    //Funciones arrays
    //Esta función simplemente inserta en una posicion que recibe un string
    static void InsertarEnPosicion(String[] resultado, int pos, String res) {
//
//        for (int i = resultado.length - 1; i > pos; i--) {
//            resultado[i] = resultado[i - 1];
//        }
        resultado[pos] = res;
    }

    //Saca por pantalla las normas del juego
    //Función encargada de sacar por pantalla el titulo
    //del juego y sus normas.
    static void ImprimirIntro() {
        System.out.println("BIENVENIDO A MASTERMIND");
        System.out.println("*********************");
        System.out.println("");
        System.out.println("Adivina el codigo mastermind.");
        System.out.println("Los colores son:\nBlanco, Negro, Rosa, Naranja, Rojo, Amarillo, Verde y Azul.");
        System.out.println("Si el color coincide en la posicion, saldra 'f. Negra'.\nSi el color existe pero no esta en la posicion correcta,\nsaldra 'f. Blanca'. Si el color no esta en el array saldra vacio.");
        System.out.println("Inserta los colores sin espacios.");
        System.out.println("Los colores no se repiten");
        System.out.println("Buena suerte!");
        System.out.println("");
        System.out.println("*********************");

    }

    //Generar el codigo Mastermind
    //Esta función actua como la funcion EjecutarFunciones()
    //pero tiene la segunda parte deshabilitada ya que es para
    //sacar por pantalla el codigo generado (la respuesta)
    //Llama a la funcion RellenarArray() y le envia el array codigo
    //que es donde se insertara el codigo generado.
    static void GenerarMastermind(int[] codigo) {

        RellenarArray(codigo);
        //Debugging (saca el patron generado por pantalla)
        ImprimirArray(codigo);
    }

    //Ejecuta las funciones para que el jugador introduzca una combinación
    //Esta función hace lo mismo que la funcion EjecutarFunciones()
    //Llama a la funcion RellenarInput() y le envia el array
    //jugadorInput.
    //La primera función rellenara el array con los colores
    //que inserte el usuario
    //La segunda funcion imprimira por pantalla lo que a introducido el
    //usuario.
    static void IntroducirMastermind(int[] jugadorInput) {

        //Pregunta al usuario por el patron
        RellenarInput(jugadorInput);
        //Debugging
        ImprimirArray(jugadorInput);

    }

    //Ejecuta las funciones basicas generales del juego
    //Esto lo podria haber metido en el main pero me ha apetecido
    //hacerle su propia funcion para poder expandir el juego si quiero desde
    //el main.
    //Aqui se ejecutar las diversas funciones y se generan los arrays.
    //También se controla el juego en general, como los intentos.
    static void JuegoMastermind() {

        boolean combinacion = false;
        int[] codigo = new int[5];
        int[] jugadorInput = new int[5];
        String[] resultado = new String[5];
        int intentos = 0;

        //Genera el patron
        GenerarMastermind(codigo);

        //Imprime la introduccion
        ImprimirIntro();
        do {
            intentos += 1;
            //Pregunta al jugador por el patron
            IntroducirMastermind(jugadorInput);
            EjecutarFunciones(codigo, jugadorInput, resultado);

            if (CheckGameStatus() == true && intentos <= 12) {
                System.out.println("Has ganado! Y has tardado: " + intentos + " intentos!");
                combinacion = true;
            } else {
                System.out.println("No has descifrado el mastermind\nvuelve a intntarlo");
                combinacion = false;
                if (intentos >= 12) {
                    System.out.println("Has perdido.");
                    break;
                }
            }

        } while (!combinacion);

    }

    //Esto esta aqui para decorar
    public static void main(String[] args) {

        JuegoMastermind();

    }

}
