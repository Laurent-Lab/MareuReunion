package laurent.benard.mareureunion.controler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import laurent.benard.mareureunion.model.Reunion;

public abstract class ReunionsGenerateur {

    public static List<Reunion> GENERATE_REUNIONS = Arrays.asList(
        new Reunion("10:00","Pink","Papa","Romeo, Juliette","Mardi")
    );

    static List<Reunion> generateReunions() {

        return new ArrayList<>(GENERATE_REUNIONS);
    }

}
