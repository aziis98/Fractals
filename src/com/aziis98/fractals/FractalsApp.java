package com.aziis98.fractals;

import com.aziis98.dare.*;
import com.aziis98.dare.SwingWindow.*;
import com.aziis98.dare.math.*;
import com.aziis98.dare.util.*;

import java.awt.*;
import java.util.stream.*;

public class FractalsApp extends ApplicationCore {

    public EList<EList<Vector2f>> iterations = new EList<>();

    @Override
    public void init() {
        // Window
        WindowData.width = 1000;
        WindowData.height = 800;
        WindowData.resizable = false;


        // Initial conditions
        EList<Vector2f> initials = new EList<>();
        {
            initials.add( new Vector2f( -100F, 0F ).plus( WindowData.getCenter() ) );
            initials.add( new Vector2f( +100F, 0F ).plus( WindowData.getCenter() ) );
        }
        iterations.add( initials );

        EList<Vector2f> iteration;
        for (int i = 0; i < 2; i++)
        {
            iteration = new EList<>();

            for (int j = 1; j < iterations.getLast().size(); j++)
            {
                Vector2f a = iterations.getLast().get( j - 1 );
                Vector2f b = iterations.getLast().get( j );

                iteration.addAll( fractalKoch( a, b ).stream().collect( Collectors.toList() ) );
            }

            iterations.add( iteration );
        }
    }

    public EList<Vector2f> fractalKoch(Vector2f a, Vector2f b) {
        // Construction Variables
        Vector2f ab                     = b.minus( a );
        float    equilateralThirdHeight = ab.scale( 1 / 3F ).length() * Maths.sqrt( 3 ) / 2;

        EList<Vector2f> ziCurve = new EList<>();
        {
            ziCurve.add( a );
            ziCurve.add( a.plus( ab.scale( 1 / 3F ) ) );

            ziCurve.add(
                    a.plus( ab.scale( 1 / 2F ) ).plus( ab.normalise().rotate( Maths.toRadians( 90F ) ).scale( equilateralThirdHeight ) )
            );

            ziCurve.add( a.plus( ab.scale( 2 / 3F ) ) );
            ziCurve.add( b );
        }
        return ziCurve;
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Graphics2D g) {
        g.setBackground( Color.WHITE );
        g.clearRect( 0, 0, WindowData.width, WindowData.height );

        Color iter = Color.GREEN.brighter();

        for (EList<Vector2f> iteration : iterations)
        {
            g.setColor( iter );
            Polyline.drawPolyline( g, iteration );

            iter = iter.darker();
        }
    }

    public static void main(String[] args) {
        launch( new FractalsApp() );
    }
}
