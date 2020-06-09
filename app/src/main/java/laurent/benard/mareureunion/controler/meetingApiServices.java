package laurent.benard.mareureunion.controler;

import java.util.List;
import java.util.regex.Pattern;

import laurent.benard.mareureunion.model.Meeting;

public class meetingApiServices implements InterfaceMeetingApiServices {

    private List<Meeting> meetings = MeetingsGenerator.generateMeetings();

    /**
     * Retourne la liste de réunions
     * @return reunions
     */
    @Override
    public List<Meeting> getMeetings() {return meetings;}

    /**
     * Création d'une réunion
     * @param meeting
     */
    @Override
    public void createMeeting(Meeting meeting){ meetings.add(meeting); }

    /**
     * Supprime une réunion
     * @param meeting
     */
    @Override
    public void deleteMeeting(Meeting meeting){ meetings.remove(meeting);}

    @Override
    public boolean isEmailValid(CharSequence email){
        boolean emailFlag = false;
        String emailArr[] = email.toString().split("[,| ]");
        for(int i = 0; i < emailArr.length; i++){
            emailFlag = Pattern.compile("[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+").matcher(emailArr[i].trim()
            ).matches();
        }
        return emailFlag;
    }
}
