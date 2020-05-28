package laurent.benard.mareureunion.controler;

public class DI {

    private static InterfaceReunionApiServices services = new ReunionApiServices();

    /**
     * Retourne une instance de l'API service
     * @return
     */
    public static InterfaceReunionApiServices getReunionsApiServices(){
        return services;
    }

    /**
     * Pour les tests
     * @return
     */
    public static InterfaceReunionApiServices getNewInstanceApiService() {
        services = new ReunionApiServices();
        return services;
    }
}
