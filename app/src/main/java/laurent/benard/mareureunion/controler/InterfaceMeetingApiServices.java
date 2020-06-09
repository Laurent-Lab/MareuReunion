package laurent.benard.mareureunion.controler;

import java.util.List;

import laurent.benard.mareureunion.model.Meeting;

public interface InterfaceMeetingApiServices {

    /**
     * Je récupère la liste de réunions
     * @return reunions
     */
    List<Meeting> getMeetings();

    /**
     * Retourne une réunion
     * @param meeting
     */
    void createMeeting(Meeting meeting);

    /**
     * Supprime une réunion
     * @param meeting
     */
    void deleteMeeting(Meeting meeting);

    boolean isEmailValid(CharSequence email);
}
