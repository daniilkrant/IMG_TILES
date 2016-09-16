import java.util.ArrayList;

public class Main {
    private static ArrayList<userImage> listWithImages = new ArrayList<>();

    public static ArrayList<userImage> getList() {
        return listWithImages;
    }

    public static void main(String[] args) {
        final String pathToBase = "D:\\71_3dwall\\3dwall\\Resizable";
        Mozaika_GUI GUI = new Mozaika_GUI();
        Analyzer.analyzeBase(pathToBase);
        GUI.go();

    }
}
