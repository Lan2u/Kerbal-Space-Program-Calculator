package KerbalSpaceProgram;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Paul Lancaster on 08/08/2016
 *
 * @author paul
 */
class OrbitCalculator {
    /*
        Kepler's 3rd Law
        T^2 / R^3 = (4 * pi^2) / (G * MSun )

        Energy Total in orbit = Ek + Ep = 0.5*m*v^2 - (GMm/r) = -GMm/2a

        Solving for velocity results in:

        The Vis-viva equation:
        v^2 = GM((2/r) - (1/a))
        where:
        v is the relative speed of the two bodies
        r is the distance between the two bodies
        a is the semi-major axis (a > 0 for ellipses, a = ∞ or 1/a = 0 for parabolas, and a < 0 for hyperbolas)
        G is the gravitational constant
        M is the mass of the central body

        T = 2pi * sqrt(a^3 / GM)
        T = Period (s)

    */
    
    OrbitCalculator() {
        
    }
    
    // Returns first the phase angle and then the delta V required rounded to 1 decimal place
    // Radii must be in meters
    double[] calculateIntercept(ksp.Planet planet, double r1, double r2) {
        // r1 = initial radius of circular orbit
        // r2 = the targets radius of circular orbit
        // SGP = standard gravitation parameter = GM and is different for each astronomical body
        double SGP = planet.standardGravitationalParameter;
        // The radius's provided do not include the planets radius so this must be added
        r1 = r1 + planet.radius;
        r2 = r2 + planet.radius;
        
        // TODO Trying (360 - ) rather than (180 - ) because otherwise following the kerbal engineer readout you (might? not tested) burn at the wrong time
        double phaseAngle = 360 - (Math.sqrt(SGP / Math.pow(r2, 3)) * Math.PI * Math.sqrt(Math.pow((r1 + r2), 3) / (8 * SGP)));
        double deltaV = Math.sqrt(SGP / r1) * (Math.sqrt((2 * r2) / (r1 + r2)) - 1);
        
        // Round the values to 1 decimal place
        phaseAngle = round(phaseAngle, 1);
        deltaV = round(deltaV, 1);
        
        // System.out.println("Radius 1 " + r1 + " , Radius 2 " + r2 + " SGP " + SGP + " phaseAngle " + phaseAngle + " delta V " + deltaV);
        return new double[]{deltaV, phaseAngle};
    }
    
    private double round(double value, int decimalPlaces) {
        // Rounds a double value to the specified decimal places
        return new BigDecimal(value).setScale(decimalPlaces, RoundingMode.HALF_UP).doubleValue();
    }
}
