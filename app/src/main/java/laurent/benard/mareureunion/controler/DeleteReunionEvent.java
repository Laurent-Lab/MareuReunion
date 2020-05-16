package laurent.benard.mareureunion.controler;

import laurent.benard.mareureunion.model.Reunion;

public class DeleteReunionEvent {

    public Reunion reunion;

    public DeleteReunionEvent(Reunion reunion){
        this.reunion = reunion;

    }
}
