import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Analyzer {
    private static ArrayList<File> listWithFileNames = new ArrayList<>();

    public static Color averageColorUI(BufferedImage image) {
        Raster raster = image.getRaster();
        double RSum = 0.0;
        double GSum = 0.0;
        double BSum = 0.0;
        for (int i = 0; i < image.getWidth(); i++) {
            for (int j = 0; j < image.getHeight(); j++) {
                RSum += raster.getSample(i, j, 0);
                GSum += raster.getSample(i, j, 1);
                BSum += raster.getSample(i, j, 2);
            }
        }
        int red = (int) (RSum / (image.getWidth() * image.getHeight()));
        int green = (int) (GSum / (image.getWidth() * image.getHeight()));
        int blue = (int) (BSum / (image.getWidth() * image.getHeight()));
        return new Color(red, green, blue);
    }

    public static void analyzeBase(String path) {
        BufferedImage t;
        userImage img;
        getListFiles(path);
        for (File fil : listWithFileNames) {
            try {
                t = ImageIO.read(fil);
            } catch (IOException e) {
                System.out.println("Not able to open image from database / Analyzer");
                t = null;
            }
            if (t != null) {
                img = new userImage(t);
                img.setPath(fil.getPath());
                img.setAverageColor(averageColorUI(img));
                System.out.println(img.getPath() + " " + img.getColor());
                Main.getList().add(img);
            }
        }
        listWithFileNames.clear();
    }

    public static void getListFiles(String str) {
        File f = new File(str);
        for (File s : f.listFiles()) {
            if (s.isFile()) {
                listWithFileNames.add(s);
            } else if (s.isDirectory()) {
                getListFiles(s.getAbsolutePath());
            }
        }
    }
}
