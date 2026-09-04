package net.fluxedmod;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class MandelbrotSet extends JComponent {

    public static final int WIDTH = 800;
    public static final int HEIGHT = 800;
    public static final int ITERATIONS = 100;

    public static final double startX = -2;
    public static final double width = 4;
    public static final double startY = 2;
    public static final double height = 4;
    public static final double zoom = 1;


    public static final double dx = width/(WIDTH-1);
    public static final double dy = height/(HEIGHT-1);

    private BufferedImage buffer;


    public MandelbrotSet() {

        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        render();

        JFrame frame = new JFrame("Mandelbrot Set");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(true);
        frame.getContentPane().add(this);

        frame.pack();
        frame.setVisible(true);


    }

    @Override
    public void addNotify() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(buffer, 0, 0, null);
    }

    public void render(){
        for (int x=0; x<WIDTH; x++){
            for (int y=0; y<HEIGHT; y++){
                int color = calculatePoint(x*zoom, y*zoom);
                buffer.setRGB(x, y, color);
            }
        }
    }



    public int calculatePoint(double x, double y){

        ComplexNumber number = convertToComplex(x, y);
        ComplexNumber z = number;
        int i;
        for (i=0; i<ITERATIONS; i++){

            z = z.times(z).add(number);

            if (z.abs()>2.0){
                break;
            }

        }

        if (i==ITERATIONS) {
            return 0x00000000;
        }
        else {
            return 0xFFFFFFFF;
        }

    }

    public static ComplexNumber convertToComplex(double x, double y){

        double real = startX + x*dx;
        double imaginary = startY - y*dy;
        return new ComplexNumber(real, imaginary);

    }


}
