package laurent.benard.mareureunion.controler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import laurent.benard.mareureunion.model.Reunion;

public abstract class ReunionsGenerateur {

    /**
     * Liste de réunions
     */
    public static List<Reunion> GENERATE_REUNIONS = Arrays.asList();

    /**
     * Retourne une réunion
     * @return
     */
    static List<Reunion> generateReunions() { return new ArrayList<>(GENERATE_REUNIONS); }
}
