package com.shoe.minebot;
import java.lang.*;
import java.util.*;

public class Pathfinder {
    static final double distWeight = 1.0;
    static final double hungerWeight = 1.0;

    static double[] verticies = new double[30];

    public static double distBetweenTwoNodes(double x1, double x2, double y1, double y2, double z1, double z2) {
        return (Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2) + (z1 - z2) * (z1 - z2)));
    }

    /*
        First coordinate of arrays is player position
        Last is final goal
        Middle are the number of nodes randomly distributed between initial and final
    */
    public static double[] pathFindInefficent(double[] x, double[] y, double[] z) {
        int i;
        int j;


        for (i = 0; i <= (x.length - 1); i++) {
            for (j = i + 1; j <= x.length - i - 1; j++) {
                int index = i*x.length + j;
                verticies[(index)] = distBetweenTwoNodes(x[j - 1], y[j - 1], z[j - 1], x[j], y[j], z[j]);
            }
        }
        return verticies;
    }

    public static void main(String args[])
    {
        double[] x = new double[6];
        double[] y = new double[6];
        double[] z = new double[6];
        int n;
        for(n=0; n<=5; n++)
        {
            x[n] = Math.random();
            y[n] = Math.random();
            z[n] = Math.random();
        }
        double[] l = pathFindInefficent(x, y, z);
        int m;
        for(m=0; m<=l.length-1; m++)
        {
            System.out.println(pathFindInefficent(x, y, z)[m]);
        }
    }
}
