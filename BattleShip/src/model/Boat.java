package model;

import java.util.ArrayList;

public class Boat {
    private TypeBoat type;
    private ArrayList<Coordinate> coordinates;
    private boolean isSunk;

    public Boat() {
        
        this.coordinates = new ArrayList<>();
        this.isSunk = false;
    }

    /**
     * Descripción: Inicializa un barco estándar basado en un tipo y posición inicial.
     * Pre: El tipo debe estar definido y debe haber una posición válida para el barco.
     * Pos: Se genera un barco estándar con sus coordenadas en una orientación predeterminada.
     * @param type Tipo del barco.
     * @param x Coordenada X de la posición inicial del barco.
     * @param y Coordenada Y de la posición inicial del barco.
     */

    public void initializeStandardBoat(TypeBoat type, int x, int y) {
        this.type = type;
        this.coordinates.clear();
        this.coordinates.add(new Coordinate(x, y));
        
        for (int i = 1; i < type.getDefaultSize(); i++) {
            if (type.isDefaultVertical()) {
                this.coordinates.add(new Coordinate(x, y + i));
            } else {
                this.coordinates.add(new Coordinate(x + i, y));
            }
        }
     }


     /**
     * Descripción: Inicializa un barco personalizado con tamaño y orientación definidos por el usuario.
     * Pre: Las coordenadas iniciales deben ser válidas, y el tamaño y orientación deben ser especificados.
     * Pos: Se genera un barco con las coordenadas correspondientes al tamaño y orientación proporcionados.
     * @param x Coordenada X de la posición inicial del barco.
     * @param y Coordenada Y de la posición inicial del barco.
     * @param size Tamaño del barco.
     * @param isVertical Determina si el barco es vertical o no.
     */

    public void initializeCustomBoat(int x, int y, int size, boolean isVertical) {
        this.type = TypeBoat.PERSONALIZADO;
        this.coordinates.clear();
        this.coordinates.add(new Coordinate(x, y));
        
        for (int i = 1; i < size; i++) {
            if (isVertical) {
                this.coordinates.add(new Coordinate(x, y + i));
            } else {
                this.coordinates.add(new Coordinate(x + i, y));
            }
        }
    }

    /**
     * Descripción: Obtiene el tipo de barco.
     * Pre: El barco debe estar creado y configurado.
     * Pos: Devuelve el tipo de barco.
     * @return TypeBoat Devuelve el tipo del barco.
     */
    public TypeBoat getType() {
        return type;
    }

    /**
     * Descripción: Obtiene las coordenadas ocupadas por el barco.
     * Pre: El barco debe estar creado con un conjunto de coordenadas.
     * Pos: Devuelve la lista de coordenadas ocupadas por el barco.
     * @return ArrayList<Coordinate> Devuelve una lista de coordenadas donde está ubicado el barco.
     */
    public ArrayList<Coordinate> getCoordinates() {
        return coordinates;
    }

    /**
     * Descripción: Verifica si el barco ha sido hundido.
     * Pre: El barco debe estar creado.
     * Pos: Indica si el barco está hundido.
     * @return boolean Devuelve true si el barco está hundido, false si no.
     */
    public boolean isSunk() {
        return isSunk;
    }

    /**
     * Descripción: Establece el estado de hundido del barco.
     * Pre: El barco debe estar creado.
     * Pos: El barco es marcado como hundido o no hundido, según el valor proporcionado.
     * @param sunk Estado de hundido del barco.
     */
    public void setSunk(boolean sunk) {
        isSunk = sunk;
    }

    /**
     * Descripción: Verifica si el barco contiene una coordenada específica.
     * Pre: El barco debe tener coordenadas definidas.
     * Pos: Indica si la coordenada está dentro del barco.
     * @param coord Coordenada a verificar.
     * @return boolean Devuelve true si la coordenada está dentro del barco, false si no.
     */
    public boolean containsCoordinate(Coordinate coord) {
        return coordinates.contains(coord);
    }
}
