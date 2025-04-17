package model;

import java.util.Random;

public class Controller {
    private Player human;
    private Player machine;
    private int standardHumanWins;
    private int standardMachineWins;
    private int customHumanWins;
    private int customMachineWins;
    private Coordinate lastMachineAttack;
    private boolean lastMachineAttackHit;

    private int currentBoatIndex;
    private boolean isCustomGame;
    private int customBoatsCount;

    public Controller() {

        human = new Player("Humano", TypePlayer.HUMAN);

        machine = new Player("Máquina", TypePlayer.MACHINE);
    }
    


    /**
     * Descripción: Prepara el juego estándar reiniciando el índice de barcos y marcando como no personalizado.
     * Pos: El juego está listo para colocar barcos estándar.
     * @return void, configura el juego en modo estándar.
     */

    public void prepareStandardBoats() {

        currentBoatIndex = 0;

        isCustomGame = false;
    }

    /**
     * Descripción: Prepara el juego personalizado con la cantidad especificada de barcos.
     * Pre: boatCount debe ser un número positivo.
     * Pos: El juego está listo para colocar barcos personalizados.
     * @param boatCount Cantidad de barcos personalizados a colocar.
     * @return void, configura el juego en modo personalizado.
     */

    public void prepareCustomBoats(int boatCount) {

        this.customBoatsCount = boatCount;

        currentBoatIndex = 0;

        isCustomGame = true;
    }

    /**
     * Descripción: Obtiene información sobre el barco actual que se está colocando.
     * Pre: El juego debe estar en modo de colocación de barcos.
     * Pos: Retorna un String con la información del barco actual.
     * @return String con la información del barco actual.
     */

    public String getCurrentBoatInfo() {

        if (isCustomGame) {

            return "Colocando barco personalizado #" + (currentBoatIndex + 1);

        } else {

            TypeBoat type = TypeBoat.getStandardBoats()[currentBoatIndex];

            String orientation = "Horizontal";

            if (type.isDefaultVertical()) {

                orientation = "Vertical";
            }
            return "Colocando " + type.getName() +
                   " (Tamaño: " + type.getDefaultSize() + ") - " + orientation;
        }
    }

    /**
     * Descripción: Verifica si todos los barcos del humano han sido colocados.
     * Pre: El juego debe estar en modo de colocación de barcos.
     * Pos: Retorna true si todos los barcos han sido colocados, false en caso contrario.
     * @return boolean indicando si todos los barcos han sido colocados.
     */


    public boolean isAllHumanBoatsPlaced() {

        if (isCustomGame) {

            return currentBoatIndex >= customBoatsCount;
            
        } else {
            return currentBoatIndex >= TypeBoat.getStandardBoats().length;
        }
    }

    /**
     * Descripción: Obtiene el índice del barco actual que se está colocando.
     * Pre: El juego debe estar en modo de colocación de barcos.
     * Pos: Retorna el índice del barco actual.
     * @return int con el índice del barco actual.
     */


    public int getCurrentBoatIndex() {

        return currentBoatIndex;
    }


    /**
     * Descripción: Intenta colocar el barco estándar actual en las coordenadas especificadas.
     * Pre: Las coordenadas deben estar dentro del tablero.
     * Pos: Coloca el barco si es posible y avanza al siguiente barco.
     * @param x Coordenada X donde colocar el barco.
     * @param y Coordenada Y donde colocar el barco.
     * @return boolean indicando si el barco fue colocado exitosamente.
     */

    public boolean tryPlaceCurrentHumanBoat(int x, int y) {

        if (isCustomGame) {

            return false;
        }

        TypeBoat type = TypeBoat.getStandardBoats()[currentBoatIndex];
        Boat boat = new Boat();
        boat.initializeStandardBoat(type, x, y);

        if (human.getBoard().placeBoat(boat)) {
            currentBoatIndex++;
            return true;
        }
        return false;
    }

   /**
     * Descripción: Intenta colocar un barco personalizado en las coordenadas especificadas.
     * Pre: Las coordenadas deben estar dentro del tablero.
     * Pos: Coloca el barco si es posible y avanza al siguiente barco.
     * @param x Coordenada X donde colocar el barco.
     * @param y Coordenada Y donde colocar el barco.
     * @param size Tamaño del barco.
     * @param isVertical Orientación del barco.
     * @return boolean indicando si el barco fue colocado exitosamente.
     */

    public boolean tryPlaceCustomHumanBoat(int x, int y, int size, boolean isVertical) {

        if (!isCustomGame) {

            return false;
        }

        Boat boat = new Boat();
        boat.initializeCustomBoat(x, y, size, isVertical);

        if (human.getBoard().placeBoat(boat)) {
            currentBoatIndex++;
            return true;
        }
        return false;
    }

    /**
     * Descripción: Configura los barcos estándar de la máquina en posiciones aleatorias.
     * Pre: El tablero de la máquina debe estar inicializado.
     * Pos: Los barcos están colocados en el tablero de la máquina.
     * @return void, coloca los barcos de la máquina aleatoriamente.
     */

    public void setupMachineStandardBoats() {
        TypeBoat[] boats = TypeBoat.getStandardBoats();
        for (int i = 0; i < boats.length; i++) {
            placeRandomStandardBoat(machine, boats[i]);
        }
    }

    /**
     * Descripción: Configura barcos personalizados de la máquina en posiciones aleatorias.
     * Pre: El tablero de la máquina debe estar inicializado.
     * Pos: Los barcos están colocados en el tablero de la máquina.
     * @return void, coloca los barcos personalizados de la máquina aleatoriamente.
     */

    public void setupMachineCustomBoats() {
        Random rand = new Random();
        int numBoats = rand.nextInt(10) + 1;

        for (int i = 0; i < numBoats; i++) {
            int size = rand.nextInt(5) + 1;
            boolean isVertical = rand.nextBoolean();
            placeRandomCustomBoat(machine, size, isVertical);
        }
    }

   /**
     * Descripción: Coloca un barco estándar en una posición aleatoria para un jugador.
     * Pre: El jugador y el tipo de barco deben ser válidos.
     * Pos: El barco está colocado en el tablero del jugador.
     * @param player Jugador al que pertenece el barco.
     * @param type Tipo de barco estándar.
     */

    
    public void placeRandomStandardBoat(Player player, TypeBoat type) {

        Random rand = new Random();
        boolean placed = false;

        while (!placed) {
            int x = rand.nextInt(10) + 1;
            int y = rand.nextInt(10) + 1;

            Boat boat = new Boat();
            boat.initializeStandardBoat(type, x, y);
            placed = player.getBoard().placeBoat(boat);
        }
    }

    /**
     * Descripción: Coloca un barco personalizado en una posición aleatoria para un jugador.
     * Pre: El jugador debe ser válido.
     * Pos: El barco está colocado en el tablero del jugador.
     * @param player Jugador al que pertenece el barco.
     * @param size Tamaño del barco.
     * @param isVertical Orientación del barco.
     */

    
    public void placeRandomCustomBoat(Player player, int size, boolean isVertical) {

        Random rand = new Random();
        boolean placed = false;

        while (!placed) {
            int x = rand.nextInt(10) + 1;
            int y = rand.nextInt(10) + 1;

            Boat boat = new Boat();
            boat.initializeCustomBoat(x, y, size, isVertical);
            placed = player.getBoard().placeBoat(boat);
        }
    }

    /**
     * Descripción: Verifica si el juego ha terminado.
     * Pre: Ambos jugadores deben tener sus tableros inicializados.
     * Pos: Retorna true si algún jugador ha perdido todos sus barcos.
     * @return boolean indicando si el juego ha terminado.
     */

    public boolean isGameOver() {
        return human.getBoard().allBoatsSunk() || machine.getBoard().allBoatsSunk();
    }

    /**
     * Descripción: Obtiene el nombre del ganador del juego.
     * Pre: El juego debe haber terminado.
     * Pos: Retorna el nombre del jugador ganador.
     * @return String con el nombre del ganador.
     */

    public String getWinnerName() {
        if (human.getBoard().allBoatsSunk()) {
            return machine.getName();
        } else {
            return human.getName();
        }
    }

    /**
     * Descripción: Realiza un ataque del humano a la máquina en las coordenadas especificadas.
     * Pre: Las coordenadas deben estar dentro del tablero.
     * Pos: Registra el ataque y actualiza el estado del juego.
     * @param x Coordenada X del ataque.
     * @param y Coordenada Y del ataque.
     * @return boolean indicando si el ataque fue exitoso.
     */

    public boolean humanAttack(int x, int y) {
        Coordinate attack = new Coordinate(x, y);
        lastMachineAttackHit = machine.getBoard().receiveAttack(attack);
        return lastMachineAttackHit;
    }

    /**
     * Descripción: Realiza un ataque de la máquina al jugador humano en coordenadas aleatorias.
     * Pre: El tablero del jugador humano debe estar inicializado.
     * Pos: Se genera una coordenada aleatoria, se registra el ataque y se guarda el resultado.
     * @return Coordinate con la posición atacada por la máquina.
     */

    public Coordinate machineAttack() {

        lastMachineAttack = human.getBoard().generateRandomAttack();

        lastMachineAttackHit = human.getBoard().receiveAttack(lastMachineAttack);

        return lastMachineAttack;
    }

    /**
     * Descripción: Indica si el último ataque de la máquina fue un acierto.
     * Pre: Se debe haber realizado un ataque de la máquina previamente.
     * Pos: Retorna el estado del último ataque de la máquina.
     * @return boolean que indica si el último ataque de la máquina acertó.
     */

    public boolean isMachineAttackHit() {

        return lastMachineAttackHit;
    }

    /**
     * Descripción: Verifica si una coordenada ya ha sido atacada por el jugador humano.
     * Pre: Las coordenadas deben estar dentro del tablero.
     * Pos: Retorna true si la coordenada ya ha sido atacada previamente.
     * @param x Coordenada X a verificar.
     * @param y Coordenada Y a verificar.
     * @return boolean indicando si la coordenada ya fue atacada.
     */

    public boolean isCoordinateAlreadyAttacked(int x, int y) {

        return machine.getBoard().isCoordinateAttacked(new Coordinate(x, y));
    }

    /**
     * Descripción: Obtiene el estado actual del tablero del jugador humano.
     * Pre: El tablero del jugador humano debe estar inicializado.
     * Pos: Retorna una matriz con los valores del tablero del jugador humano.
     * @return int[][] que representa el estado del tablero humano.
     */

    public int[][] getHumanBoard() {

        return human.getBoard().getGrid();
    }

    /**
     * Descripción: Obtiene el estado actual del tablero del jugador máquina.
     * Pre: El tablero del jugador máquina debe estar inicializado.
     * Pos: Retorna una matriz con los valores del tablero del jugador máquina.
     * @return int[][] que representa el estado del tablero máquina.
     */

    public int[][] getMachineBoard() {

        return machine.getBoard().getGrid();

    }

    /**
     * Descripción: Registra el resultado de la partida en las estadísticas según el tipo de juego.
     * Pre: El juego debe haber terminado.
     * Pos: Se actualiza el contador de victorias del jugador correspondiente.
     * @param gameType Tipo de partida: 1 para estándar, otro valor para personalizada.
     * @return void, actualiza las estadísticas de partidas jugadas.
     */

    public void recordGameResult(int gameType) {

        boolean humanWon = !human.getBoard().allBoatsSunk();

        if (gameType == 1) {

            if (humanWon) {

                standardHumanWins++;

            } else {

                standardMachineWins++;
            }

        } else {

            if (humanWon) {

                customHumanWins++;

            } else {

                customMachineWins++;
            }
        }
    }

    /**
     * Descripción: Muestra un resumen con las estadísticas de partidas ganadas por tipo de juego.
     * Pre: Se deben haber jugado al menos una o más partidas.
     * Pos: Retorna un texto con las estadísticas de victorias de humano y máquina.
     * @return String con el resumen de estadísticas.
     */

    public String showStatistics() {
        
        return "----ESTADISTICAS-------\n" +
               "Estándar - Humano: " + standardHumanWins + " | Máquina: " + standardMachineWins + "\n" +
               "Personalizado - Humano: " + customHumanWins + " | Máquina: " + customMachineWins;
    }
}
