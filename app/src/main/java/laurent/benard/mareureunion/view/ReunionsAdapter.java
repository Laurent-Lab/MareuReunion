package laurent.benard.mareureunion.view;

import android.app.DatePickerDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Filter;
import android.widget.Filterable;


import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;



import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


import laurent.benard.mareureunion.R;
import laurent.benard.mareureunion.controler.DI;
import laurent.benard.mareureunion.controler.DeleteReunionEvent;
import laurent.benard.mareureunion.controler.InterfaceReunionApiServices;
import laurent.benard.mareureunion.controler.QueryEvent;
import laurent.benard.mareureunion.model.Reunion;

public class ReunionsAdapter extends RecyclerView.Adapter<MyViewHolder> implements Filterable {

    List<Reunion> reunions;
    List<Reunion> reunionsAll;
    private InterfaceReunionApiServices services;


    /**
     *
     * @param reunions
     */
    ReunionsAdapter(List<Reunion> reunions){
        this.reunions = reunions;
        this.reunionsAll = new ArrayList<>(reunions);
        services = DI.getReunionsApiServices();
    }

    /**
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.reunion_item, parent, false);
        services = DI.getReunionsApiServices();
        return new MyViewHolder(view);
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position){
        final Reunion reunion = reunions.get(position);
        holder.display(reunions.get(position));
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteReunionEvent(reunion));

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



    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        //Background
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            reunionsAll = services.getReunions();
            List<Reunion> filteredList = new ArrayList<>();

            if (charSequence.toString().isEmpty()){
                filteredList.addAll(reunionsAll);
            } else {
                for (Reunion reunion : reunionsAll){
                    if (reunion.getLieu().contains(charSequence.toString())){
                        filteredList.add(reunion);
                    }
                    else if (reunion.getDate().contains(charSequence.toString())){
                        filteredList.add(reunion);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }


        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            reunions.clear();
            reunions.addAll((Collection<? extends Reunion>) filterResults.values);
            notifyDataSetChanged();
        }
    };


}
