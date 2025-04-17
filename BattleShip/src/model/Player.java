package model;

public class Player {
    private String name;
    private Board board;
    private int gamesWon;
    private TypePlayer type;

    public Player(String name, TypePlayer type) {
        this.name = name;
        this.board = new Board();
        this.gamesWon = 0;
        this.type = type;
    }

    /**
     * Descripción: Obtiene el nombre del jugador.
     * Pre: El jugador debe estar inicializado.
     * Pos: Retorna el nombre asignado al jugador.
     * @return String con el nombre del jugador.
     */

    public String getName() {

        return name;
        
    }

    /**
     * Descripción: Obtiene el tablero asociado al jugador.
     * Pre: El jugador debe estar inicializado.
     * Pos: Retorna el objeto Board del jugador.
     * @return Board del jugador.
     */

    public Board getBoard() {

        return board;

    }

    /**
     * Descripción: Obtiene el número de partidas ganadas por el jugador.
     * Pre: El jugador debe estar inicializado.
     * Pos: Retorna el contador de victorias.
     * @return int con el número de victorias.
     */

    public int getGamesWon() {

        return gamesWon;

    }

     /**
     * Descripción: Incrementa en 1 el contador de victorias del jugador.
     * Pre: El jugador debe estar inicializado.
     * Pos: El contador de victorias aumenta en 1.
     * @return void, modifica el estado interno del jugador.
     */

    public void incrementGamesWon() {

        gamesWon++;

    }

    /**
     * Descripción: Obtiene el tipo de jugador (Humano/Máquina).
     * Pre: El jugador debe estar inicializado.
     * Pos: Retorna el tipo del jugador.
     * @return TypePlayer indicando el tipo de jugador.
     */


    public TypePlayer getType() {

        return type;

    }
}