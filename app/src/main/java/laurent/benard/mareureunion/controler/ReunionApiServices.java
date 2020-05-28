package laurent.benard.mareureunion.controler;

import java.util.List;

import laurent.benard.mareureunion.model.Reunion;

public class ReunionApiServices implements InterfaceReunionApiServices {

    private List<Reunion> reunions = ReunionsGenerateur.generateReunions();

    /**
     * Retourne la liste de réunions
     * @return reunions
     */
    @Override
    public List<Reunion> getReunions() {return reunions;}

    /**
     * Création d'une réunion
     * @param reunion
     */
    @Override
    public void createReunion(Reunion reunion){ reunions.add(reunion); }

    /**
     * Supprime une réunion
     * @param reunion
     */
    @Override
    public void deleteReunion(Reunion reunion){ reunions.remove(reunion);}
}
