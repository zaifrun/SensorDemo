package org.webdevelopment.sensordemo;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mRotationVectorSensor;
    private MyView gameView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameView = (MyView) findViewById(R.id.gameView);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        if (mSensorManager==null)
            System.out.println("SensorManager is null");
        else
            System.out.println("SensorManager is up and running!");

        mRotationVectorSensor = mSensorManager.getDefaultSensor(
                Sensor.TYPE_ROTATION_VECTOR);
        Sensor accelSensor = mSensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);
        Sensor gameRotation = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        Sensor magnetosensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor gyrosensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Sensor georotationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);


        //rotationvector sensor bruger gyroscopet
        //gyroscopet er ikke med i emulatoren.

        //magnetosensor og accelerometer ER MED i emulatoren

        if (mRotationVectorSensor==null)
            System.out.println("RotationVector is null");
        else
            System.out.println("RotationVector is up and running!");

        if (accelSensor==null)
            System.out.println("Acceleromter is null");
        else
            System.out.println("Accelerometer is up and running!");

        if (gameRotation==null)
            System.out.println("gamerotation is null");
        else
            System.out.println("gamerotation is up and running!");

        if (magnetosensor==null)
            System.out.println("magnetosensor is null");
        else
            System.out.println("magnetosensor is up and running!");

        if (gyrosensor==null)
            System.out.println("gyrosensor is null");
        else
            System.out.println("gyrosensor is up and running!");

        if (georotationSensor==null)
            System.out.println("georotationsensor is null");
        else
            System.out.println("georotationsensor is up and running!");





    }

    public void onAccuracyChanged(Sensor sensor,
                            int accuracy)
    {

    }

    @Override
    protected void onStart() {
        super.onStart();
    // enable our sensor when the activity is resumed, ask for
        // 10 ms updates, so that is 100 times per second
        mSensorManager.registerListener(this, mRotationVectorSensor, 10000);
    }

    @Override
    protected void onStop() {
        super.onStop();
    // make sure to turn our sensor off when the activity is paused
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event)
    {
        float[] mRotationMatrixFromVector = new float[9];
        float[] mRotationMatrix = new float[9];
        System.out.println("in onSensorChangedEvent");


        // float[] vals = new float[3];

        float orientationVals[] = new float[3];
        // It is good practice to check that we received the proper sensor event
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR)
        {
            // Convert the rotation-vector to a 4x4 matrix.
            SensorManager.getRotationMatrixFromVector(mRotationMatrixFromVector, event.values);
           /* SensorManager.remapCoordinateSystem(mRotationMatrixFromVector,
                    SensorManager.AXIS_X, SensorManager.AXIS_Z,
                    mRotationMatrix);*/
            SensorManager.getOrientation(mRotationMatrixFromVector, orientationVals);

            // Optionally convert the result from radians to degrees
            orientationVals[0] = (float) Math.toDegrees(orientationVals[0]);
            orientationVals[1] = (float) Math.toDegrees(orientationVals[1]);
            orientationVals[2] = (float) Math.toDegrees(orientationVals[2]);

            //Azimoth bruges til rotation omkring y-aksen - dvs nord/syd i formål til jorden
            //0 svarer til nord, 180 til syd, positive værdier til øst og negative til vest
            //pitch er vinklen i forhold til jorden (vertikalt - top/bund af tlf roteres).
            // 0 grader svarer til vandret på et bord.
            // når den derefter roteres ned mod jorden
            // (positive vinkler: fra 0 til 90) eller op mod himlen, så går vinkeln fra
            // 0 til-90.
            // roll, roteres fra kant til kant.
            //0 svarer til vandret. Går derefter fra 0 til 180 (tilt til højre)
            // den ene vej og fra 0 til -18 den anden vej (tilt til venstre)
            Log.d("Sensors","\nAzimoth: " + orientationVals[0] + ", Pitch: "
                    + orientationVals[1] + ",Roll: "
                    + orientationVals[2]);
            float roll = orientationVals[2];
            float pitch = orientationVals[1];

            if (roll>10 && roll<180)
            {
                gameView.moveRight(Math.round(roll/3));
            }
            else if (roll<-10 && roll>-180)
            {
                gameView.moveLeft(-Math.round(roll/3));

            }
            if (pitch>10)
                gameView.moveUp(Math.round(pitch/3));
            else if (pitch<-10)
                gameView.moveDown(-Math.round(pitch/3));

        }
    }
}
