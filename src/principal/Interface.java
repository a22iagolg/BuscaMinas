package principal;

import java.util.Scanner;
import buscaminas.*;

public class Interface {
    Scanner sc = new Scanner(System.in);
    static int contador = 1;
    BuscaMinas juego = new BuscaMinas();

    public Interface() {
        System.out.print("Por favor escoja dificultad (facil, normal, dificil, loteria): ");
        String dificultad = sc.nextLine();
        juego.setDificultad(dificultad);
        if (!dificultad.equals("loteria")) {
            System.out.println("\t\t        - TABLERO ACTUAL -");
            imprimirTablero(juego.getTablero());
        }
        playerTurn();
    }

    void playerTurn() {
        if (!juego.getVictoria()) {
            System.out.println("\t\t          TURNO " + contador);
            imprimirTablero(juego.getTablero_Tapado());
            contador++;
            System.out.println("Por favor elija casilla para destapar");
            System.out.print("Fila: ");
            int fila = sc.nextInt();
            sc.nextLine();
            System.out.print("Columna: ");
            int columna = sc.nextInt();
            sc.nextLine();

            if (juego.getTablero()[fila][columna] != '9') {
                juego.comprobarCasilla(fila, columna);
                playerTurn();
            } else {
                imprimirTablero(juego.getTablero());
                System.out.println("\t\t\t   \u001B[41mGAME OVER\u001B[0m ");
            }
        } else {
            imprimirTablero(juego.getTablero());
            System.out.println("Enhorabuena, has ganado!");
        }
    }

    public void imprimirTablero(char[][] tablero) {
        // Imprimir guía para las columnas
        System.out.print("    ");
        for (int columna = 0; columna < tablero[0].length; columna++) {
            System.out.print(columna + "     ");
        }
        System.out.println();

        for (int fila = 0; fila < tablero.length; fila++) {
            System.out.print(fila + " ");

            for (int columna = 0; columna < tablero[fila].length; columna++) {
                switch (tablero[fila][columna]) {
                    case '0':
                        System.out.print("\u001B[32m| " + tablero[fila][columna] + " | \u001B[0m"); // Verde
                        break;
                    case '1':
                        System.out.print("\u001B[36m| " + tablero[fila][columna] + " | \u001B[0m"); // Azul
                        break;
                    case '2':
                        System.out.print("\u001B[93m| " + tablero[fila][columna] + " | \u001B[0m"); // Amarillo
                        break;
                    case '3':
                        System.out.print("\u001B[31m| " + tablero[fila][columna] + " | \u001B[0m"); // Rojo
                        break;
                    case '9':
                        System.out.print("\u001B[41m| " + tablero[fila][columna] + " |\u001B[0m "); // Rojo SUB
                        break;
                    case 'T':
                        System.out.print("\u001B[35m| " + tablero[fila][columna] + " | \u001B[35m"); // Morado
                        break;
                    default:
                        System.out.print("\u001B[31m| " + tablero[fila][columna] + " | \u001B[0m"); // Rojo
                        break;
                }
                if (columna == tablero[0].length - 1) {
                    System.out.print("\u001B[0m" + fila);
                }
            }
            System.out.println("\u001B[0m");
        }
        // Imprimir guía para las columnas
        System.out.print("    ");
        for (int columna = 0; columna < tablero[0].length; columna++) {
            System.out.print(columna + "     ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        new Interface();
    }
}
