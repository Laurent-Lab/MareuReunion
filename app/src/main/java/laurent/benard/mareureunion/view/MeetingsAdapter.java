package laurent.benard.mareureunion.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.recyclerview.widget.RecyclerView;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import laurent.benard.mareureunion.R;
import laurent.benard.mareureunion.controler.DI;
import laurent.benard.mareureunion.controler.DeleteMeetingEvent;
import laurent.benard.mareureunion.controler.InterfaceMeetingApiServices;
import laurent.benard.mareureunion.model.Meeting;

public class MeetingsAdapter extends RecyclerView.Adapter<MyViewHolder> implements Filterable {

    List<Meeting> meetings;
    List<Meeting> meetingsAll;
    private InterfaceMeetingApiServices services;

    /**
     *
     * @param meetings
     */
    public MeetingsAdapter(List<Meeting> meetings){
        this.meetings = meetings;
        this.meetingsAll = new ArrayList<>(meetings);
        services = DI.getMeetingsApiServices();
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
        services = DI.getMeetingsApiServices();
        return new MyViewHolder(view);
    }

    /**
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position){
        final Meeting meeting = meetings.get(position);
        holder.display(meetings.get(position));
        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new DeleteMeetingEvent(meeting));
            }
        });
    }

    /**
     * Retourne la liste entière
     * @return
     */
    @Override
    public int getItemCount() { return meetings.size();}

    /**
     * Méthodes pour filtrer les réunions
     * @return filter
     */
    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter = new Filter() {

        //Background
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            meetingsAll = services.getMeetings();
            List<Meeting> filteredList = new ArrayList<>();

            if (charSequence.toString().isEmpty()){
                filteredList.addAll(meetingsAll);
            } else {
                for (Meeting meeting : meetingsAll){
                    if (meeting.getLocation().contains(charSequence.toString().toLowerCase())){
                        filteredList.add(meeting);
                    }
                    else if (meeting.getDated().contains(charSequence.toString())){
                        filteredList.add(meeting);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            meetings.clear();
            meetings.addAll((Collection<? extends Meeting>) filterResults.values);
            notifyDataSetChanged();
        }
    };
}
