package com.aziis98.fractals;

import com.aziis98.dare.*;
import com.aziis98.dare.SwingWindow.*;
import com.aziis98.dare.inputs.*;
import com.aziis98.dare.math.*;
import com.aziis98.dare.rendering.*;
import com.aziis98.dare.system.*;
import com.aziis98.dare.util.*;

import java.awt.*;
import java.awt.event.*;

public class FractalsApp extends ApplicationCore {

    public EList<EList<Vector2f>> iterations       = new EList<>();
    public int                    currentIteration = 4;
    public boolean                showShadow       = true;

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
        float focalDist = 300F;
        EList<Vector2f> initials = new EList<>();
        {
            initials.add( new Vector2f( -focalDist, 0F ).plus( WindowData.getCenter() ) );
            initials.add( new Vector2f( +focalDist, 0F ).plus( WindowData.getCenter() ) );
        }
        iterations.add( initials );

        EList<Vector2f> iteration, prevIteration;
        for (int i = 0; i < 7; i++)
        {
            System.out.println( " Iteration #" + (i + 1) );
            long stTime = Time.milliTime();

            iteration = new EList<>();
            prevIteration = iterations.getLast();

            for (int j = 1; j < prevIteration.size(); j++)
            {
                Vector2f a = prevIteration.get( j - 1 );
                Vector2f b = prevIteration.get( j );

                iteration.addAll( theFractal.iterate( a, b ) );
            }

            iterations.add( iteration );

            System.out.println( "   Duration: " + (Time.milliTime() - stTime) + "ms" );
        }


        // Keycontrols
        Keyboard.key( KeyEvent.VK_UP ).addChangeListner( value -> {
            if ( value && iterations.size() > currentIteration + 1 ) currentIteration++;
        } );
        Keyboard.key( KeyEvent.VK_DOWN ).addChangeListner( value -> {
            if ( value && currentIteration - 1 >= 0 ) currentIteration--;
        } );
        Keyboard.key( KeyEvent.VK_S ).addChangeListner( value -> {
            if ( value ) showShadow ^= true;
        } );
    }

    @Override
    public void update() {

    }

    public static BasicStroke stroke2      = new BasicStroke( 1.3F, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND );
    public static Color       outlineColor = new Color( 0xE7E7E7 );

    @Override
    public void render(Graphics2D g) {
        g.setBackground( Color.WHITE );
        g.clearRect( 0, 0, WindowData.width, WindowData.height );
        g.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
        g.setStroke( stroke2 );

        if ( showShadow )
        {
            g.setColor( outlineColor );

            for (EList<Vector2f> iteration : iterations)
            {
                Polyline.drawPolyline( g, iteration );
            }
        }

        try
        {
            g.setColor( Color.BLACK );
            Polyline.drawPolyline( g, iterations.get( currentIteration ) );
        }
        catch (Exception e)
        {
            g.setColor( Color.RED );
            G.drawLine( g, new Vector2f(), WindowData.getDimensions() );
            G.drawLine( g, new Vector2f( 0, WindowData.getHeight() ), new Vector2f( WindowData.getWidth(), 0 ) );
        }

    }

    public interface FractalFunction {
        EList<Vector2f> iterate(Vector2f a, Vector2f b);
    }

    public static void main(String[] args) {
        launch( new FractalsApp() );
    }
}
