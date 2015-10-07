package com.aziis98.fractals;

import com.aziis98.dare.math.*;
import com.aziis98.dare.rendering.*;
import com.aziis98.dare.util.*;

import java.awt.*;

public class Polyline {

    public static void drawPolyline(Graphics2D g, EList<Vector2f> polyline) {
        for (int i = 1; i < polyline.size(); i++)
        {
            G.drawLine( g, polyline.get( i - 1 ), polyline.get( i ) );
        }
    }

}
