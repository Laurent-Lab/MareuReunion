package laurent.benard.mareureunion.controler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import laurent.benard.mareureunion.model.Meeting;

public abstract class MeetingsGenerator {

    /**
     * Liste de réunions
     */
    public static List<Meeting> generateMeetings = Arrays.asList();

    /**
     * Retourne une réunion
     * @return
     */
    static List<Meeting> generateMeetings() { return new ArrayList<>(generateMeetings); }
}
