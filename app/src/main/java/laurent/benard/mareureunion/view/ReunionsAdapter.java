package laurent.benard.mareureunion.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import laurent.benard.mareureunion.R;
import laurent.benard.mareureunion.model.Reunion;

public class ReunionsAdapter extends RecyclerView.Adapter<MyViewHolder> {

    List<Reunion> reunions;
    private ImageButton deleteButton;

    /**
     *
     * @param reunions
     */
    ReunionsAdapter(List<Reunion> reunions){this.reunions = reunions;}

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_item, parent, false);
        return new MyViewHolder(view);
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position){
        holder.display(reunions.get(position));

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeAt(position);
            }
        });
    }

    /**
     * Retourne la liste entière
     * @return
     */
    @Override
    public int getItemCount() {

        return reunions.size();
    }

    /**
     * Supprime un élément de la liste
     * @param position
     */
    private void removeAt(int position){
        reunions.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, reunions.size());
    }

}
