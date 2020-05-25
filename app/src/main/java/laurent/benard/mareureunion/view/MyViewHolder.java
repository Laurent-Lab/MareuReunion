package laurent.benard.mareureunion.view;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import laurent.benard.mareureunion.R;
import laurent.benard.mareureunion.model.Reunion;


public class MyViewHolder extends RecyclerView.ViewHolder {

    public ImageView color;
    public ImageButton deleteButton;
    private TextView sujet;
    private TextView heure;
    private TextView lieu;
    private TextView participants;
    Reunion reunion;

    /**
     *
     * @param itemView
     */
    public MyViewHolder(View itemView) {
        super(itemView);

        color = itemView.findViewById(R.id.fragment_item_img_circle);
        deleteButton = itemView.findViewById(R.id.but_fragment_delete);
        sujet = itemView.findViewById(R.id.txt_fragment_sujet);
        heure = itemView.findViewById(R.id.txt_fragment_horaire);
        lieu = itemView.findViewById(R.id.txt_fragment_lieu);
        participants = itemView.findViewById(R.id.txt_fragment_participants);

    }

    /**
     * Vue d'une réunion
     * @param reunions
     */
    void display(Reunion reunions){
        color.setImageResource(reunions.getColor());
        sujet.setText(reunions.getSujet());
        heure.setText(reunions.getHeure());
        lieu.setText(reunions.getLieu());
        participants.setText(reunions.getParticipants());

    }


}
