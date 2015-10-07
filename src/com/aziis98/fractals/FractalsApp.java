package com.aziis98.fractals;

import com.aziis98.dare.*;
import com.aziis98.dare.SwingWindow.*;
import com.aziis98.dare.math.*;
import com.aziis98.dare.util.*;

import java.awt.*;

public class FractalsApp extends ApplicationCore {

    public EList<EList<Vector2f>> iterations = new EList<>();

    @Override
    public void init() {
        // Window
        WindowData.width = 1000;
        WindowData.height = 800;
        WindowData.resizable = false;
        WindowData.title = "Fractals";

        // The fractal
        FractalFunction theFractal = Fractals::fractalKoch;

        // Initial conditions
        EList<Vector2f> initials = new EList<>();
        {
            initials.add( new Vector2f( -200F, 0F ).plus( WindowData.getCenter() ) );
            initials.add( new Vector2f( +200F, 0F ).plus( WindowData.getCenter() ) );
        }
        iterations.add( initials );

        EList<Vector2f> iteration, prevIteration;
        for (int i = 0; i < 5; i++)
        {
            iteration = new EList<>();
            prevIteration = iterations.getLast();

            for (int j = 1; j < prevIteration.size(); j++)
            {
                Vector2f a = prevIteration.get( j - 1 );
                Vector2f b = prevIteration.get( j );

                iteration.addAll( theFractal.iterate( a, b ) );
            }

            iterations.add( iteration );
        }
    }

    @Override
    public void update() {

    }

    public static BasicStroke stroke2 = new BasicStroke( 1.3F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_BEVEL );

    @Override
    public void render(Graphics2D g) {
        g.setBackground( Color.WHITE );
        g.clearRect( 0, 0, WindowData.width, WindowData.height );
        g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );

        g.setColor( Color.BLACK );
        g.setStroke( stroke2 );
        Polyline.drawPolyline( g, iterations.getLast() );

        /*
        g.setStroke( stroke1 );
        Color iter = Color.GREEN.darker();

        for (EList<Vector2f> iteration : iterations)
        {
            g.setColor( iter );
            // Polyline.drawPolyline( g, iteration );

            iter = iter.brighter().brighter();
        }
        */
    }

    public interface FractalFunction {
        EList<Vector2f> iterate(Vector2f a, Vector2f b);
    }

    public static void main(String[] args) {
        launch( new FractalsApp() );
    }
}
