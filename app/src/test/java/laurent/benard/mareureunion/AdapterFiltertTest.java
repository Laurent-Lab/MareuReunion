package laurent.benard.mareureunion;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

import java.util.List;

import laurent.benard.mareureunion.controler.DI;
import laurent.benard.mareureunion.controler.InterfaceMeetingApiServices;
import laurent.benard.mareureunion.model.Meeting;
import laurent.benard.mareureunion.view.MeetingsAdapter;

@RunWith(RobolectricTestRunner.class)
public class AdapterFiltertTest {

    public MeetingsAdapter adapter;
    private InterfaceMeetingApiServices services;

    @Before
    public void setUp() throws Exception {
        services = DI.getNewInstanceApiService(); }

    /**
     * Tests ajout de réunions + filtre de la salle
     */
    @Test
    public void testAdapterFilteredSalle(){

        List<Meeting> meetings = services.getMeetings();
        Meeting meeting1 = new Meeting(1, "midi", "mario",
                "laurent", "aurelie", "lundi");
        Meeting meeting2 = new Meeting(2, "soir", "luigi",
                "moi", "denis", "mardi");
        services.createMeeting(meeting1);
        services.createMeeting(meeting2);
        adapter = new MeetingsAdapter(meetings);
        assertEquals(adapter.getItemCount(), 2);
        adapter.getFilter().filter("mario");
        assertEquals(adapter.getItemCount(), 1);
    }

    /**
     * Test ajout de réunions + filtre de la date
     */
    @Test
    public void TestAdapterFilteredDate(){
        List<Meeting> meetings = services.getMeetings();
        Meeting meeting3 = new Meeting(3, "midi", "mario",
                "laurent", "aurelie", "lundi");
        Meeting meeting4 = new Meeting(4, "soir", "luigi",
                "moi", "denis", "mardi");
        services.createMeeting(meeting3);
        services.createMeeting(meeting4);
        adapter = new MeetingsAdapter(meetings);
        assertEquals(adapter.getItemCount(), 2);
        adapter.getFilter().filter("mardi");
        assertEquals(adapter.getItemCount(), 1);
    }
}
