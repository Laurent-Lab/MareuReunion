package laurent.benard.mareureunion;


import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import laurent.benard.mareureunion.controler.DI;
import laurent.benard.mareureunion.controler.InterfaceReunionApiServices;
import laurent.benard.mareureunion.controler.ReunionsGenerateur;
import laurent.benard.mareureunion.model.Reunion;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

@RunWith(JUnit4.class)
public class ReunionsServicesTests {

    private InterfaceReunionApiServices services;

    private int color = 1;
    private String heure = "heure";
    private String lieu = "lieu";
    private String sujet = "sujet";
    private String participants = "participant";
    private String date = "date";

    @Before
    public void setup(){services = DI.getNewInstanceApiService();}

    @Test
    public void getReunionWithSucces(){
        List<Reunion> reunions = services.getReunions();
        List<Reunion> reunionsExpected = ReunionsGenerateur.GENERATE_REUNIONS;
        assertThat(reunions, IsIterableContainingInAnyOrder.containsInAnyOrder(reunionsExpected.toArray()));
    }

    @Test
    public void createReunionWithSucces(){
        Reunion reunion = new Reunion(color, heure, lieu, sujet, participants, date);
        services.createReunion(reunion);
        assertTrue(services.getReunions().contains(reunion));
        assertNotNull(reunion);
        assertThat(reunion.getColor(), equalTo(1));
        assertThat(reunion.getHeure(), containsString("heure"));
        assertThat(reunion.getLieu(), containsString("lieu"));
        assertThat(reunion.getSujet(), containsString("sujet"));
        assertThat(reunion.getParticipants(), containsString("participant"));
        assertThat(reunion.getDate(), containsString("date"));
    }

    @Test
    public void deleteReunionWithSucces(){
        Reunion reunion = new Reunion(color, heure, lieu, sujet, participants, date);
        services.createReunion(reunion);
        Reunion reunionToDelete = services.getReunions().get(0);
        services.deleteReunion(reunionToDelete);
        assertFalse(services.getReunions().contains(reunionToDelete));
    }




}