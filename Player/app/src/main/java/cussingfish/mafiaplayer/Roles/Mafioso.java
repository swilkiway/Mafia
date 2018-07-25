package cussingfish.mafiaplayer.Roles;

public class Mafioso extends Player {
    public static Mafioso get() {
        if (Mafioso == null) {
            Mafioso = new Mafioso();
        }
        return Mafioso;
    }
    private static Mafioso Mafioso = null;
    private Mafioso() { }

    public void snuffEm(String victim) {

    }
}
