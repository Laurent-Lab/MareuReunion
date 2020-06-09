package laurent.benard.mareureunion.controler;

import laurent.benard.mareureunion.model.Meeting;

public class DeleteMeetingEvent {

    public Meeting meeting;

    public DeleteMeetingEvent(Meeting meeting){ this.meeting = meeting;}
}
