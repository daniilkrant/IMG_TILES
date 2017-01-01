import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

public class Mozaika_GUI {

    static private userImage user_image;
    private image_panel img_panel;

    void go() {
        JFrame frame = new JFrame("Let's do it!");
        int SIZE_X = 500;
        int SIZE_Y = 500;
        frame.setSize(SIZE_X, SIZE_Y);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        img_panel = new image_panel();
        frame.getContentPane().add(BorderLayout.CENTER, img_panel);

        JPanel btn_panel = new JPanel();
        frame.getContentPane().add(BorderLayout.SOUTH, btn_panel);


        JButton open_btn = new JButton("Open file");
        open_btn.addActionListener(new OpenFile());
        btn_panel.add(BorderLayout.WEST, open_btn);

        JButton moz_btn = new JButton("Do this stuff");
        moz_btn.addActionListener(new DoStuff());
        btn_panel.add(BorderLayout.EAST, moz_btn);
        frame.setVisible(true);
    }

    private class DoStuff implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (user_image != null) {
                Thread t = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Combiner.make_result(user_image);
                    }
                });
                t.start();
            }
        }
    }

    private class OpenFile implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int ret = fileChooser.showDialog(null, "Choose your file");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File user_file = fileChooser.getSelectedFile();
                BufferedImage t = new BufferedImage(img_panel.getWidth(), img_panel.getHeight(), userImage.TYPE_INT_RGB);
                try {
                    t = ImageIO.read(user_file);
                } catch (IOException e1) {
                    System.out.println("Not able to open file /fileChooser");
                }
                user_image = new userImage(t);
                img_panel.setBackground(Analyzer.averageColorUI(user_image));
                img_panel.repaint();
            }
        }
    }

    private class SeeDiference implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }

    private class image_panel extends JPanel {
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (user_image != null) {
                if ((user_image.getWidth() + user_image.getHeight()) < (img_panel.getHeight() + img_panel.getHeight()))
                    g.drawImage(user_image, (img_panel.getWidth() - user_image.getWidth()) / 2, (img_panel.getHeight() - user_image.getHeight()) / 2, null);
                else {
                    double coef = Math.max(user_image.getWidth(), user_image.getHeight()) / Math.max(img_panel.getWidth(), img_panel.getHeight());
                    BufferedImage scaled = new BufferedImage((int) (user_image.getWidth() / coef), (int) (user_image.getHeight() / coef), userImage.TYPE_INT_RGB);
                    Graphics g1 = scaled.createGraphics();
                    g1.drawImage(user_image, 0, 0, (int) (user_image.getWidth() / coef), (int) (user_image.getHeight() / coef), null);
                    int positionX = (img_panel.getWidth() - (int) (user_image.getWidth() / coef)) / 2;
                    int positionY = (img_panel.getHeight() - (int) (user_image.getHeight() / coef)) / 2;
                    g.drawImage(scaled, positionX, positionY, null);
                    g1.dispose();
                }
            }
            g.dispose();
        }
    }
}
