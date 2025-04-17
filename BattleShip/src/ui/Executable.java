package ui;

import java.util.Scanner;
import model.Controller;
import model.Coordinate;

public class Executable {
    private Scanner input;
    private Controller controller;

    public static void main(String[] args) {
        Executable exe = new Executable();
        exe.menu();
    }

    public Executable() {
        input = new Scanner(System.in);
        controller = new Controller();
    }

    /**
     * Descripción: Muestra el menú principal del juego y permite al usuario seleccionar una opción.
     * Pre: El Scanner debe estar inicializado.
     * Pos: Se ejecuta la opción correspondiente elegida por el usuario.
     * @return void, muestra el menú de opciones para que el usuario seleccione lo que desea hacer.
     */
    public void menu() {
        int option = 0;
        do {
            System.out.println("\nBienvenido a Batalla Naval");
            System.out.println("1) Juego estándar");
            System.out.println("2) Juego personalizado");
            System.out.println("3) Mostrar estadísticas");
            System.out.println("0) Salir"); 
            System.out.print("Seleccione una opción: ");
            option = input.nextInt();
            input.nextLine();

            switch (option) {
                case 1:
                    setupStandardGame();
                    break;
                case 2:
                    setupCustomGame();
                    break;
                case 3:
                    showStatistics();
                    break;
                case 0:
                    System.out.println("Gracias por disfrutar de nuestros servicios, feliz dia/tarde/noche :D.");
                    break;
                default:
                    System.out.println("Opción inválida, intente nuevamente.");
                    break;
            }
        } while (option != 0);
    }

    /**
     * Descripción: Configura y ejecuta una partida estándar.
     * Pre: El Scanner y Controller deben estar inicializados.
     * Pos: Se configura y juega una partida estándar.
     * @return void, configura y ejecuta una partida estándar.
     */
    public void setupStandardGame() {
        System.out.println("\n------CONFIGURACIÓN ESTANDAR----------");
        setupHumanStandardBoats();
        controller.setupMachineStandardBoats();
        playGame(1);
    }

    /**
     * Descripción: Configura y ejecuta una partida personalizada.
     * Pre: El Scanner y Controller deben estar inicializados.
     * Pos: Se configura y juega una partida personalizada.
     * @return void, configura y ejecuta una partida personalizada.
     */
    public void setupCustomGame() {
        System.out.println("\n-------CONFIGURACIÓN DE JUEGO PERSONALIZADO--------");
        setupHumanCustomBoats();
        controller.setupMachineCustomBoats();
        playGame(2);
    }

    /**
     * Descripción: Permite al usuario colocar los barcos estándar en el tablero.
     * Pre: Debe haberse inicializado el controlador y los barcos estándar deben estar preparados.
     * Pos: Todos los barcos estándar del humano quedan colocados en el tablero.
     * @return void, los barcos estándar se colocan exitosamente en el tablero del jugador.
     */
    public void setupHumanStandardBoats() {
        System.out.println("\nColoca tus barcos en el tablero.");
        controller.prepareStandardBoats();
        
        while(!controller.isAllHumanBoatsPlaced()) {
            System.out.println("\n" + controller.getCurrentBoatInfo());
            
            boolean placed = false;

            while(!placed) {
                
                System.out.print("Ingrese coordenada x (1-10): ");
                int x = input.nextInt();
                System.out.print("Ingrese coordenada y (1-10): ");
                int y = input.nextInt();
                input.nextLine();
                
                placed = controller.tryPlaceCurrentHumanBoat(x, y);
                
                if(!placed) {

                    System.out.println("¡Posición inválida! Intente nuevamente.");

                } else {
                    System.out.println("¡Barco colocado exitosamente!");
                    printHumanBoard();
                }
            }
        }
    }

    /**
     * Descripción: Permite al usuario colocar los barcos personalizados en el tablero.
     * Pre: Debe haberse inicializado el controlador y los barcos personalizados deben estar preparados.
     * Pos: Todos los barcos personalizados del humano quedan colocados en el tablero.
     * @return void, los barcos personalizados se colocan exitosamente en el tablero del jugador.
     */
    public void setupHumanCustomBoats() {
        System.out.print("\nIngrese la cantidad de barcos (máximo 10): ");
        int numBoats = input.nextInt();
        input.nextLine();
        
        if(numBoats < 1 || numBoats > 10) {
            System.out.println("Número inválido. Volviendo al menú.");
            return;
        }
        
        controller.prepareCustomBoats(numBoats);
        
        while(!controller.isAllHumanBoatsPlaced()) {
            System.out.println("\nConfigurando barco #" + (controller.getCurrentBoatIndex() + 1));
            
            System.out.print("Ingrese tamaño del barco (1-5): ");
            int size = input.nextInt();
            input.nextLine();
            
            System.out.print("Orientación (H para Horizontal, V para Vertical): ");
            boolean isVertical = input.nextLine().equalsIgnoreCase("V");
            
            System.out.print("Ingrese coordenada x (1-10): ");
            int x = input.nextInt();
            System.out.print("Ingrese coordenada y (1-10): ");
            int y = input.nextInt();
            input.nextLine();
            
            if(controller.tryPlaceCustomHumanBoat(x, y, size, isVertical)) {
                System.out.println("¡Barco colocado exitosamente!");
                printHumanBoard();
            } else {
                System.out.println("¡Posición inválida! Este barco no se contabilizará.");
             }
        }
    }

    /**
     * Descripción: Ejecuta el ciclo del juego alternando turnos hasta que uno de los jugadores gane.
     * Pre: Los tableros deben estar configurados y los barcos colocados.
     * Pos: El juego finaliza y se anuncia un ganador.
     * @param int gameType 1 para juego estándar, 2 para juego personalizado.
     * @return void, ejecuta el ciclo del juego hasta que haya un ganador.
     */

    public void playGame(int gameType) {
        System.out.println("\n------COMIENZA EL JUEGO-------");
        
        while (!controller.isGameOver()) {

            humanTurn();
            if (controller.isGameOver()) break;
            
            machineTurn();
        }
        
        System.out.println("\n------FIN DEL JUEGO-------");
        
        System.out.println("El ganador es: " + controller.getWinnerName());
        
        System.out.println("\nTablero humano final:");
        printHumanBoard();
        
        System.out.println("\nTablero de la máquina final:");
        printMachineBoard();
        
        controller.recordGameResult(gameType);
    }

    /**
     * Descripción: Ejecuta el turno de ataque del jugador humano.
     * Pre: El juego debe estar en curso y el tablero del enemigo debe estar preparado.
     * Pos: Se realiza un ataque sobre el tablero de la máquina.
     * @return void, realiza un ataque sobre el tablero de la máquina.
     */

    public void humanTurn() {

        System.out.println("\n-------TU TURNO------");

        System.out.println("Tablero de la máquina (vista del humano):");

        printMachineBoard();
        
        int x = 0;
        int y = 0;
        boolean validCoords = false;
        
        while (!validCoords) {

            System.out.print("Ingrese coordenada x para atacar (1-10): ");
            x = input.nextInt();
            System.out.print("Ingrese coordenada y para atacar (1-10): ");
            y = input.nextInt();
            input.nextLine();
            
            if (x < 1 || x > 10 || y < 1 || y > 10) {

                System.out.println("Coordenadas fuera del tablero. Intente nuevamente.");

            } else if (controller.isCoordinateAlreadyAttacked(x, y)) {

                System.out.println("Ya has atacado esta coordenada. Intente nuevamente.");

            } else {

                validCoords = true;
            }
        }
        
        boolean hit = controller.humanAttack(x, y);

        if (hit) {

            System.out.println("¡Impacto!");

        } else {

            System.out.println("¡No ha pasado nada, siga jugando!");
        }
    }

    /**
     * Descripción: Ejecuta el turno de ataque de la máquina contra el tablero humano.
     * Pre: El juego debe estar en curso.
     * Pos: La máquina realiza un ataque y se informa si fue exitoso.
     * @return void, realiza un ataque de la máquina sobre el tablero del jugador humano.
     */
    public void machineTurn() {

        System.out.println("\n------TURNO DE LA MÁQUINA------");

        Coordinate attack = controller.machineAttack();

        System.out.println("La máquina ataca en: " + attack.getX() + "," + attack.getY());
        
        boolean hit = controller.isMachineAttackHit();

        if (hit) {

            System.out.println("¡La máquina impactó en tu barco!");

        } else {

            System.out.println("La máquina falló.");
        }
        
        System.out.println("\nTu tablero después del ataque:");
        printHumanBoard();
    }

    /**
     * Descripción: Muestra el estado actual del tablero del jugador humano.
     * Pre: El tablero humano debe estar inicializado.
     * Pos: Se imprime el tablero del jugador humano en consola.
     * @return void, muestra el tablero del jugador humano.
     */
    public void printHumanBoard() {

        int[][] grid = controller.getHumanBoard();

        System.out.println("\nTu Tablero:");

        for (int i = 0; i < 10; i++) {

            System.out.print(" | ");

            for (int j = 0; j < 10; j++) {

                System.out.print(grid[i][j] + " | ");
            }

            System.out.println();
        }
    }

    /**
     * Descripción: Muestra el estado visible del tablero de la máquina (sin mostrar sus barcos).
     * Pre: El tablero de la máquina debe estar inicializado.
     * Pos: Se imprime la vista del humano sobre el tablero enemigo.
     * @return void, muestra la vista del tablero de la máquina desde el punto de vista del jugador.
     */

    public void printMachineBoard() {

        int[][] grid = controller.getMachineBoard();

        System.out.println("\nTablero Enemigo:");

        for (int i = 0; i < 10; i++) {

            for (int j = 0; j < 10; j++) {

                if (grid[i][j] == 1) {

                    System.out.print("| 0 ");

                } else {

                    System.out.print("| " + grid[i][j] + " ");
                }
            }
            System.out.println("|");
        }
    }

    /**
     * Descripción: Muestra las estadísticas de partidas jugadas, ganadas y perdidas.
     * Pre: El controlador debe estar inicializado.
     * Pos: Se imprimen las estadísticas actuales del juego.
     * @return void, muestra las estadísticas de partidas jugadas.
     */

    public void showStatistics() {

        System.out.println(controller.showStatistics());
    }
}
