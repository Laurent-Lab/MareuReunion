package laurent.benard.mareureunion.view;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import laurent.benard.mareureunion.R;
import laurent.benard.mareureunion.model.Meeting;


public class MyViewHolder extends RecyclerView.ViewHolder {

    public ImageView color;
    public ImageButton deleteButton;
    private TextView topic;
    private TextView hour;
    private TextView location;
    private TextView participants;

    /**
     *
     * @param itemView
     */
    public MyViewHolder(View itemView) {
        super(itemView);

        color = itemView.findViewById(R.id.fragment_item_img_circle);
        deleteButton = itemView.findViewById(R.id.but_fragment_delete);
        topic = itemView.findViewById(R.id.txt_fragment_sujet);
        hour = itemView.findViewById(R.id.txt_fragment_horaire);
        location = itemView.findViewById(R.id.txt_fragment_lieu);
        participants = itemView.findViewById(R.id.txt_fragment_participants);
    }

    /**
     * Vue d'une réunion
     * @param reunions
     */
    void display(Meeting reunions){
        color.setImageResource(reunions.getColor());
        topic.setText(reunions.getTopic());
        hour.setText(reunions.getHour());
        location.setText(reunions.getLocation());
        participants.setText(reunions.getParticipants());
    }
}
