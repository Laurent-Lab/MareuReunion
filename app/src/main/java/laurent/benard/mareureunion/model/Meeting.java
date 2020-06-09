package laurent.benard.mareureunion.model;

public class Meeting {

    private int color;
    private String hour;
    private String location;
    private String topic;
    private String participants;
    private String dated;

    /**
     * Constructeur
     * @param hour
     * @param location
     * @param topic
     * @param participants
     * @param dated
     */
    public Meeting(int color, String hour, String location, String topic, String participants, String dated) {
        this.color = color;
        this.hour = hour;
        this.location = location;
        this.topic = topic;
        this.participants = participants;
        this.dated = dated;
    }

    public int getColor(){return color;}

    public String getHour() {
        return hour;
    }

    public String getLocation() {
        return location;
    }

    public String getTopic() {
        return topic;
    }

    public String getParticipants() { return participants; }

    public String getDated() {
        return dated;
    }
}
