import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Combiner {
    private static final int IMG_WIDTH = 300;
    private static final int IMG_HEIGHT = 400;
    private static final int MULTIPL = 70;
    private static File dir = new File("D:\\71_3dwall\\output");
    private static File output = new File("D:\\71_3dwall\\", "res.jpg");
    private static BigBufferedImage result;
    private static BufferedImage userConverted = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
    private static ArrayList<userImage> listWithImages = Main.getList();

    public static void make_result(BufferedImage user_image) {
        Convert(user_image);

        try {
            result = BigBufferedImage.create(dir, IMG_WIDTH * MULTIPL, IMG_HEIGHT * MULTIPL, BufferedImage.TYPE_INT_RGB);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Graphics g = result.createGraphics();

        userImage t;
        for (int i = 0, x = 0; i < result.getWidth(); i += MULTIPL, x++) {
            for (int j = 0, y = 0; j < result.getHeight(); j += MULTIPL, y++) {
                System.out.println("Searching img for " + x + " " + y);
                t = findImage(x, y);
                // System.out.println("Using color" + t.getColor());
                g.drawImage(t, i, j, null);
            }
        }

        try {
            System.out.println("Writing res img");
            ImageIO.write(result, "jpg", output);
            System.out.println("Writed");
        } catch (IOException e) {
            e.printStackTrace();
        }
        g.dispose();
        result = null;
        System.gc();
        System.out.println("Deleting");
        delete(dir);
        System.out.println("Deleted");
    }

    public static userImage findImage(int x, int y) {

        Raster raster = userConverted.getRaster();
        ColorModel model = userConverted.getColorModel();
        Object data = raster.getDataElements(x, y, null);
        int argb = model.getRGB(data);
        Color pixelColor = new Color(argb, true);
        userImage res = null;
        int r, g, b, minr, ming, minb;
        minb = 256;
        ming = 256;
        minr = 256;
        for (userImage f : listWithImages) {
            r = Math.abs(pixelColor.getRed() - f.getColor().getRed());
            g = Math.abs(pixelColor.getGreen() - f.getColor().getGreen());
            b = Math.abs(pixelColor.getBlue() - f.getColor().getBlue());
            if (r < minr && g < ming && b < minb) {
                minr = r;
                minb = b;
                ming = g;
                res = f;
            }
        }
        return res;
    }

    public static void Convert(BufferedImage user_image) {
        Graphics2D g = userConverted.createGraphics();
        g.drawImage(user_image, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
    }

    public static void delete(File file) {
        if (!file.exists())
            return;
        if (file.isDirectory()) {
            for (File f : file.listFiles())
                delete(f);
            file.delete();
        } else {
            file.delete();
        }
    }

}
