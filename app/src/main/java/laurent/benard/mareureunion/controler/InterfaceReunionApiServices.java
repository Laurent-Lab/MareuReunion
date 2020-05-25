package laurent.benard.mareureunion.controler;

import java.util.List;

import laurent.benard.mareureunion.model.Reunion;

public interface InterfaceReunionApiServices {

    /**
     * Je récupère la liste de réunions
     * @return reunions
     */
    List<Reunion> getReunions();

    /**
     * Retourne une réunion
     * @param reunion
     */
    void createReunion(Reunion reunion);

    /**
     * Supprime une réunion
     * @param reunion
     */
    void deleteReunion(Reunion reunion);

}
