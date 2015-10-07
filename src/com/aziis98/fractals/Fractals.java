package com.aziis98.fractals;

import com.aziis98.dare.math.*;
import com.aziis98.dare.util.*;

public class Fractals {

    public static EList<Vector2f> fractalKoch(Vector2f a, Vector2f b) {
        // Construction Variables
        Vector2f ab                     = b.minus( a );
        float    equilateralThirdHeight = ab.scale( 1 / 3F ).length() * Maths.sqrt( 3 ) / 2;

        EList<Vector2f> ziCurve = new EList<>();
        {
            ziCurve.add( a );
            ziCurve.add( a.plus( ab.scale( 1 / 3F ) ) );

            ziCurve.add(
                    a.plus( ab.scale( 1 / 2F ) ).plus( ab.normalise().rotate( Maths.toRadians( -90F ) ).scale( equilateralThirdHeight ) )
            );

            ziCurve.add( a.plus( ab.scale( 2 / 3F ) ) );
            ziCurve.add( b );
        }
        return ziCurve;
    }

    public static EList<Vector2f> fractalLevyC(Vector2f a, Vector2f b) {
        // Construction Variables
        Vector2f ab   = b.minus( a );
        Vector2f perp = ab.normalise().rotate( Maths.toRadians( 90F ) ).scale( ab.length() / 2F );

        EList<Vector2f> ziCurve = new EList<>();
        {
            ziCurve.add( a );
            ziCurve.add( a.plus( perp ) );

            ziCurve.add( a.plus( b ).scale( 0.5F ).plus( perp ) );

            ziCurve.add( b.plus( perp ) );
            ziCurve.add( b );
        }
        return ziCurve;
    }

    public static EList<Vector2f> fractalMy1(Vector2f a, Vector2f b) {
        // Construction Variables
        Vector2f ab   = b.minus( a );
        Vector2f perp = ab.normalise().rotate( Maths.toRadians( -90F ) ).scale( ab.length() / 4F );

        EList<Vector2f> ziCurve = new EList<>();
        {
            ziCurve.add( a );

            ziCurve.add( a.plus( ab.scale( 3 / 4F ) ).plus( perp ) );

            ziCurve.add( b );
        }
        return ziCurve;
    }

}
