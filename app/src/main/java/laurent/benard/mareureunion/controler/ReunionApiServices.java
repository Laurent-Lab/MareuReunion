package laurent.benard.mareureunion.controler;

import android.widget.ImageView;

import java.util.List;

import laurent.benard.mareureunion.model.Reunion;
import laurent.benard.mareureunion.view.ReunionsAdapter;

public class ReunionApiServices implements InterfaceReunionApiServices {


    private List<Reunion> reunions = ReunionsGenerateur.generateReunions();
    private Reunion reunion;

    /**
     * Retourne la liste de réunions
     * @return
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
