package model;

public class Coordinate {
    private int x;
    private int y;

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }


    
    /**
     * Descripción: Obtiene el valor de la coordenada X.
     * Pre: La coordenada debe estar inicializada correctamente.
     * Pos: Devuelve el valor de la coordenada X.
     * @return int Devuelve el valor de la coordenada X.
     */

    public int getX() {

        return x;

    }

     /**
     * Descripción: Obtiene el valor de la coordenada Y.
     * Pre: La coordenada debe estar inicializada correctamente.
     * Pos: Devuelve el valor de la coordenada Y.
     * @return int Devuelve el valor de la coordenada Y.
     */

    public int getY() {

        return y;

    }

    /**
     * Descripción: Compara dos objetos Coordinate para verificar si son iguales.
     * Pre: El objeto a comparar debe ser una instancia de Coordinate.
     * Pos: Devuelve true si ambas coordenadas tienen el mismo valor para X e Y, de lo contrario, false.
     * @param obj El objeto a comparar con la coordenada actual.
     * @return boolean Devuelve true si las coordenadas son iguales, false si no lo son.
     */
    

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Coordinate that = (Coordinate) obj;
        return this.x == that.x && this.y == that.y;

    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}