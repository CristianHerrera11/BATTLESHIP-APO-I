package model;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    public static final int SIZE = 10;
    private int[][] grid;
    private ArrayList<Boat> boats;

    public Board() {
        grid = new int[SIZE][SIZE];
        boats = new ArrayList<>();
        initializeGrid();
    }

    /**
     * Descripción: Inicializa el tablero configurando todas las casillas como agua (0).
     * Pre: El tablero debe ser creado, pero no inicializado.
     * Pos: Se establece un tablero con todas las casillas vacías (agua).
     * @return void, esto mostrará el tablero con los ceros.
     */
    public void initializeGrid() {

        for (int i = 0; i < SIZE; i++) {

            for (int j = 0; j < SIZE; j++) {

                grid[i][j] = 0; 
            }
        }
    }

    /**
     * Descripción: Intenta colocar un barco en el tablero.
     * Pre: El barco debe estar completamente definido con sus coordenadas.
     * Pos: El barco se coloca en el tablero si las coordenadas son válidas y libres.
     * @param boat El barco a colocar en el tablero.
     * @return boolean Devuelve true si el barco se colocó con éxito, false si no.
     */

    public boolean placeBoat(Boat boat) {
        ArrayList<Coordinate> coords = boat.getCoordinates();

       
        for (int i = 0; i < coords.size(); i++) {
            Coordinate coord = coords.get(i);
            if (coord.getX() < 1 || coord.getX() > SIZE || coord.getY() < 1 || coord.getY() > SIZE) {
                return false;
            }
        }

       
        for (int i = 0; i < coords.size(); i++) {

            Coordinate coord = coords.get(i);
            
            if (grid[coord.getY() - 1][coord.getX() - 1] != 0) {
                return false;
            }
        }

        
        for (int i = 0; i < coords.size(); i++) {

            Coordinate coord = coords.get(i);

            grid[coord.getY() - 1][coord.getX() - 1] = 1; 
        }

        boats.add(boat);
        return true;
    }

    /**
     * Descripción: Recibe un ataque en una coordenada dada.
     * Pre: Las coordenadas del ataque deben estar dentro de los límites del tablero.
     * Pos: El tablero se actualiza si el ataque impacta en un barco.
     * @param coord Coordenada en la que se realiza el ataque.
     * @return boolean Devuelve true si el ataque impacta en un barco, false si no.
     */

    public boolean receiveAttack(Coordinate coord) {

        int x = coord.getX() - 1;
        int y = coord.getY() - 1;

        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {

            return false;
        }

        if (grid[y][x] == 1) { 

            grid[y][x] = 2; 

            for (int i = 0; i < boats.size(); i++) {

                Boat boat = boats.get(i);

                if (boat.containsCoordinate(coord)) {

                    boolean allHit = true;

                    ArrayList<Coordinate> boatCoords = boat.getCoordinates();

                    for (int j = 0; j < boatCoords.size(); j++) {

                        Coordinate boatCoord = boatCoords.get(j);

                        if (grid[boatCoord.getY() - 1][boatCoord.getX() - 1] != 2) {

                            allHit = false;

                            break;
                        }
                    }

                    if (allHit) {

                        boat.setSunk(true);

                        for (int j = 0; j < boatCoords.size(); j++) {

                            Coordinate boatCoord = boatCoords.get(j);

                            grid[boatCoord.getY() - 1][boatCoord.getX() - 1] = 3;

                        }
                    }

                    break;
                }
            }
            return true;
        }

        return false;
    }

    /**
     * Descripción: Verifica si una coordenada ha sido atacada previamente.
     * Pre: El tablero debe estar configurado.
     * Pos: Devuelve si la coordenada ha sido atacada.
     * @param coord Coordenada que se verifica.
     * @return boolean Devuelve true si la coordenada fue atacada, false si no.
     */

    public boolean isCoordinateAttacked(Coordinate coord) {

        int x = coord.getX() - 1;
        int y = coord.getY() - 1;

        if (x < 0 || x >= SIZE || y < 0 || y >= SIZE) {

            return false;
        }

        if (grid[y][x] == 2) {

            return true;
        }

        if (grid[y][x] == 3) {

            return true;
        }

        return false;
    }

    /**
     * Descripción: Verifica si todos los barcos han sido hundidos.
     * Pre: Los barcos deben estar correctamente colocados en el tablero.
     * Pos: Devuelve los barcos que han sido hundidos.
     * @return boolean Devuelve true si todos los barcos están hundidos, false si no.
     */

    public boolean allBoatsSunk() {

        for (int i = 0; i < boats.size(); i++) {

            Boat boat = boats.get(i);

            if (!boat.isSunk()) {

                return false;

            }

        }
        return true;
    }

    /**
     * Descripción: Genera un ataque aleatorio para la máquina.
     * Pre: El tablero debe estar configurado y la máquina debe estar lista para atacar.
     * Pos: Devuelve una coordenada aleatoria no atacada previamente.
     * @return Coordinate Coordenada generada aleatoriamente para un ataque.
     */

    public Coordinate generateRandomAttack() {

        Random random = new Random();

        int x = 0;

        int y = 0;

        Coordinate coord = null;

        do {

            x = random.nextInt(SIZE) + 1;

            y = random.nextInt(SIZE) + 1;

            coord = new Coordinate(x, y);

        } while (isCoordinateAttacked(coord));

        return coord;

    }

    /**
     * Descripción: Obtiene el estado actual del tablero.
     * Pre: El tablero debe estar configurado.
     * Pos: Devuelve una matriz con el estado actual del tablero.
     * @return int[][] Matriz que representa el estado del tablero.
     */

    public int[][] getGrid() {

        return grid;
    }
}
