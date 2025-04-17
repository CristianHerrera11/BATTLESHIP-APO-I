package model;

public enum TypeBoat {
    LANCHA("Lancha", 1, false),
    BARCO_MEDICO("Barco Médico", 2, true),
    BARCO_PROVISIONES("Barco de Provisiones", 3, false),
    BARCO_MUNICION("Barco de Munición", 3, true),
    BUQUE_GUERRA("Buque de Guerra", 4, false),
    PORTAAVIONES("Portaaviones", 5, true),
    PERSONALIZADO("Barco Personalizado", 0, false); 

    private final String name;
    private final int defaultSize;
    private final boolean defaultVertical;

    TypeBoat(String name, int defaultSize, boolean defaultVertical) {
        this.name = name;
        this.defaultSize = defaultSize;
        this.defaultVertical = defaultVertical;
    }

    public String getName() {

        return name;

    }

    public int getDefaultSize() {

        return defaultSize;

    }

    public boolean isDefaultVertical() {

        return defaultVertical;

    }

    public static TypeBoat[] getStandardBoats() {

        return new TypeBoat[]{LANCHA, BARCO_MEDICO, BARCO_PROVISIONES,BARCO_MUNICION, BUQUE_GUERRA, PORTAAVIONES};

    }
}