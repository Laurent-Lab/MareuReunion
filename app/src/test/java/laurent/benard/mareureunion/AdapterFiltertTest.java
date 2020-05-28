package laurent.benard.mareureunion;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

import java.util.List;

import laurent.benard.mareureunion.controler.DI;
import laurent.benard.mareureunion.controler.InterfaceReunionApiServices;
import laurent.benard.mareureunion.model.Reunion;
import laurent.benard.mareureunion.view.ReunionsAdapter;

@RunWith(RobolectricTestRunner.class)
public class AdapterFiltertTest {

    public ReunionsAdapter adapter;
    private InterfaceReunionApiServices services;

    @Before
    public void setUp() throws Exception {
        services = DI.getNewInstanceApiService(); }

    /**
     * Tests ajout de réunions + filtre de la salle
     */
    @Test
    public void testAdapterFilteredSalle(){

        List<Reunion> reunions = services.getReunions();
        Reunion reunion1 = new Reunion(1, "midi", "mario",
                "laurent", "aurelie", "lundi");
        Reunion reunion2 = new Reunion(2, "soir", "luigi",
                "moi", "denis", "mardi");
        services.createReunion(reunion1);
        services.createReunion(reunion2);
        adapter = new ReunionsAdapter(reunions);
        assertEquals(adapter.getItemCount(), 2);
        adapter.getFilter().filter("mario");
        assertEquals(adapter.getItemCount(), 1);
    }

    /**
     * Test ajout de réunions + filtre de la date
     */
    @Test
    public void TestAdapterFilteredDate(){
        List<Reunion> reunions = services.getReunions();
        Reunion reunion3 = new Reunion(3, "midi", "mario",
                "laurent", "aurelie", "lundi");
        Reunion reunion4 = new Reunion(4, "soir", "luigi",
                "moi", "denis", "mardi");
        services.createReunion(reunion3);
        services.createReunion(reunion4);
        adapter = new ReunionsAdapter(reunions);
        assertEquals(adapter.getItemCount(), 2);
        adapter.getFilter().filter("mardi");
        assertEquals(adapter.getItemCount(), 1);
    }
}
