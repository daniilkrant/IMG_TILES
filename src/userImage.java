import java.awt.*;
import java.awt.image.BufferedImage;

public class userImage extends BufferedImage {
    private String path;
    private Color averageColor;

    /**
     * ghfhgfgh</br>
     * @param img jj
     */
    public userImage(BufferedImage img) {
        super(img.getColorModel(), img.getRaster(), img.getColorModel().isAlphaPremultiplied(), null);
    }

    public void setAverageColor(Color clr) {
        this.averageColor = clr;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Color getColor() {
        return averageColor;
    }
}
