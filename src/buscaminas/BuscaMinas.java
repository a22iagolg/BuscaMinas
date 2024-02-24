package buscaminas;

import java.util.Random;

/**
 * La clase BuscaMinas representa el juego del Buscaminas.
 */
public class BuscaMinas {
    private Random random = new Random();
    private char[][] tablero = new char[10][10];
    private char[][] tablero_Tapado = new char[10][10];
    private int destapadas = 0;
    private int dificultad = 0;
    private boolean victoria = false;

    /**
     * Establece la dificultad del juego.
     * 
     * @param dificultad La dificultad del juego ("facil", "normal", "dificil" o "loteria").
     */
    public void setDificultad(String dificultad) {
        this.dificultad = switch (dificultad) {
            case "facil" -> 5;
            case "normal" -> 10;
            case "dificil" -> 15;
            case "loteria" -> 99;
            default -> 5;
        };
        ponerMinas(this.dificultad);
    }

    /**
     * Obtiene si el jugador ha ganado.
     * 
     * @return true si el jugador ha ganado, false en caso contrario.
     */
    public boolean getVictoria() {
        return victoria;
    }

    /**
     * Obtiene una copia del tablero.
     * 
     * @return Una copia del tablero.
     */
    public char[][] getTablero() {
        return copyTablero(tablero);
    }

    /**
     * Obtiene una copia del tablero tapado.
     * 
     * @return Una copia del tablero tapado.
     */
    public char[][] getTablero_Tapado() {
        return copyTablero(tablero_Tapado);
    }

    /**
     * Constructor de la clase BuscaMinas.
     * Inicializa tableros con valores predeterminados.
     */
    public BuscaMinas() {
        // Rellenar tablero numérico de 0
        rellenarTablero('0', tablero);

        // Rellenar el tablero tapado de T
        rellenarTablero('T', tablero_Tapado);

    }

    /**
     * Rellena un tablero con un valor específico.
     * 
     * @param c           El valor con el que se desea rellenar el tablero.
     * @param tipoTablero El tablero que se desea rellenar.
     */
    private void rellenarTablero(char c, char[][] tipoTablero) {
        for (int i = 0; i < tipoTablero.length; i++)
            for (int j = 0; j < tipoTablero[i].length; j++)
                tipoTablero[i][j] = c;
    }

    /**
     * Coloca las minas en el tablero de manera aleatoria y actualiza los números adyacentes.
     * 
     * @param numero El número de minas a colocar.
     */
    private void ponerMinas(int numero) {
        // Colocar el valor de la mina aleatoriamente
        int minasColocadas = 0;
        while (minasColocadas < numero) {
            int fila = random.nextInt(10);
            int columna = random.nextInt(10);
            if (tablero[fila][columna] != '9') {
                tablero[fila][columna] = '9';
                minasColocadas++;
            }
        }

        // Sumar a las adyacentes a la mina +1
        for (int i = 0; i < tablero.length; i++) {
            for (int j = 0; j < tablero[i].length; j++) {
                if (tablero[i][j] == '9') {
                    sumar1Adyacentes(i, j);
                }
            }
        }
    }

    /**
     * Suma 1 a las casillas adyacentes a una mina.
     * 
     * @param fila    La fila de la mina.
     * @param columna La columna de la mina.
     */
    private void sumar1Adyacentes(int fila, int columna) {
        for (int f = Math.max(0, fila - 1); f < Math.min(tablero.length, fila + 2); f++) {
            for (int c = Math.max(0, columna - 1); c < Math.min(tablero[f].length, columna + 2); c++) {
                //si nos encontramos en la casilla actual o es una mina o ha alcanzado el valor máximo de minas rodeandola: skip
                if (f == fila && c == columna || tablero[f][c] == '9' || tablero[f][c] == '8')
                    continue;
                tablero[f][c] = (char) (tablero[f][c] + 1);
            }
        }
    }

    /**
     * Comprueba una casilla en el tablero tapado y actualiza el estado del juego.
     * 
     * @param fila    La fila de la casilla.
     * @param columna La columna de la casilla.
     */
    public void comprobarCasilla(int fila, int columna) {
        // Si no es casilla tapada volvemos
        if (tablero_Tapado[fila][columna] != 'T')
            return;

        // Destapamos y aumentamos contador
        tablero_Tapado[fila][columna] = tablero[fila][columna];
        destapadas++;

        if (destapadas == 100 - dificultad) {
            victoria = true;
            return;
        }

        if (tablero[fila][columna] == '0') {
            for (int f = Math.max(0, fila - 1); f < Math.min(tablero.length, fila + 2); f++) {
                for (int c = Math.max(0, columna - 1); c < Math.min(tablero[f].length, columna + 2); c++) {
                    if (f == fila && c == columna)
                        continue;
                    comprobarCasilla(f, c);
                }
            }
        }
    }

    /**
     * Copia un tablero en otro.
     * 
     * @param original El tablero original.
     * @return Una copia del tablero original.
     */
    private char[][] copyTablero(char[][] original) {
        char[][] copyTablero = new char[original.length][original[0].length];
        for (int i = 0; i < original.length; i++) {
            for (int j = 0; j < original[i].length; j++) {
                copyTablero[i][j] = original[i][j];
            }
        }
        return copyTablero;
    }
}
