package laurent.benard.mareureunion.controler;

public class DI {

    private static InterfaceMeetingApiServices services = new meetingApiServices();

    /**
     * Retourne une instance de l'API service
     * @return
     */
    public static InterfaceMeetingApiServices getMeetingsApiServices(){ return services; }

    /**
     * Pour les tests
     * @return
     */
    public static InterfaceMeetingApiServices getNewInstanceApiService() {
        services = new meetingApiServices();
        return services;
    }
}
