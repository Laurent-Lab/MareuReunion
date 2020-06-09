package laurent.benard.mareureunion;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import laurent.benard.mareureunion.controler.DI;
import laurent.benard.mareureunion.controler.InterfaceMeetingApiServices;
import laurent.benard.mareureunion.controler.MeetingsGenerator;
import laurent.benard.mareureunion.model.Meeting;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(JUnit4.class)
public class ReunionsServicesTests {

    private InterfaceMeetingApiServices services;

    private int color = 1;
    private String heure = "heure";
    private String lieu = "lieu";
    private String sujet = "sujet";
    private String participants = "participant";
    private String date = "date";


    @Before
    public void setup(){services = DI.getNewInstanceApiService();}

    /**
     * Test ajout de la liste de réunions
     */
    @Test
    public void getReunionWithSucces(){
        List<Meeting> meetings = services.getMeetings();
        List<Meeting> reunionsExpected = MeetingsGenerator.generateMeetings;
        assertThat(meetings, IsIterableContainingInAnyOrder.containsInAnyOrder(reunionsExpected.toArray()));
    }

    /**
     * Test création d'une réunion
     */
    @Test
    public void createReunionWithSucces(){
        Meeting meeting = new Meeting(color, heure, lieu, sujet, participants, date);
        services.createMeeting(meeting);
        assertTrue(services.getMeetings().contains(meeting));
    }

    /**
     * Test suppression d'une réunion
     */
    @Test
    public void deleteReunionWithSucces(){
        Meeting meeting = new Meeting(color, heure, lieu, sujet, participants, date);
        services.createMeeting(meeting);
        Meeting meetingToDelete = services.getMeetings().get(0);
        services.deleteMeeting(meetingToDelete);
        assertFalse(services.getMeetings().contains(meetingToDelete));
    }

    /**
     * Test vérification d'un mail
     */
    @Test
    public void isEmailValidTest() {
        assertFalse(services.isEmailValid("laurent"));
        assertTrue(services.isEmailValid("laurent@gmail.com"));
    }
}