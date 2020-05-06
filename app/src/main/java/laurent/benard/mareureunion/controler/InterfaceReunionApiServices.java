package laurent.benard.mareureunion.controler;

import java.util.List;

import laurent.benard.mareureunion.model.Reunion;

public interface InterfaceReunionApiServices {

    /**
     * Je récupère la liste de réunions
     * @return
     */
    List<Reunion> getReunions();

    /**
     * Je supprime une réunion
     * @param reunion
     */
    void deleteReunion(Reunion reunion);

    /**
     * Je crée une réunion
     * @param reunion
     */
    void createReunion(Reunion reunion);

}
