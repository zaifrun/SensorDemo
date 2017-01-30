package org.webdevelopment.sensordemo;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import static android.hardware.Sensor.TYPE_RELATIVE_HUMIDITY;

public class MainActivity extends Activity implements SensorEventListener {

    //The sensormanager
    private SensorManager mSensorManager;
    //The sensor we use
    private Sensor mRotationVectorSensor;
    private Sensor pressureSensor;
    //The view to draw on
    private MyView gameView;

    private Sensor temperatureSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameView = (MyView) findViewById(R.id.gameView);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mRotationVectorSensor = mSensorManager.getDefaultSensor(
                Sensor.TYPE_ROTATION_VECTOR);
        Sensor accelSensor = mSensorManager.getDefaultSensor(
                Sensor.TYPE_ACCELEROMETER);

        //just to show the various sensors and check if they are available.
        Sensor gameRotation = mSensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR);
        Sensor magnetosensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        Sensor gyrosensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
        Sensor georotationSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR);
        temperatureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        pressureSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        Sensor humiditySensor = mSensorManager.getDefaultSensor(TYPE_RELATIVE_HUMIDITY);
        //rotationvector sensor uses the gyroscopet
        //gyroscope is not supported in the emulator.

        //magnetosensor and accelerometer IS supported in the  emulator
        //Also ambient_temperature og pressure sensor (barometer) is supported
        //in the emulator.

        if (mRotationVectorSensor==null)
            System.out.println("RotationVector not supported");
        else
            System.out.println("RotationVector is detected!");

        if (accelSensor==null)
            System.out.println("Accelerometer not supported");
        else
            System.out.println("Accelerometer is detected!");

        if (gameRotation==null)
            System.out.println("gamerotationsensor not supported");
        else
            System.out.println("gamerotationsensor detected!");

        if (magnetosensor==null)
            System.out.println("magnetosensor not supported");
        else
            System.out.println("magnetosensor detected!");

        if (gyrosensor==null)
            System.out.println("gyrosensor not supported");
        else
            System.out.println("gyrosensor detected!");

        if (georotationSensor==null)
            System.out.println("georotationsensor not supported");
        else
            System.out.println("georotationsensor detected!");

        if (temperatureSensor==null)
            System.out.println("temperaturesensor not supported");
        else
            System.out.println("temperaturesensor detected!");

        if (pressureSensor==null)
            System.out.println("presuresensor (barometer) not supported");
        else
            System.out.println("presuresensor (barometer) detected!");

        if (humiditySensor==null)
            System.out.println("humidity sensor not supported");
        else
            System.out.println("humidity sensor detected!");

    }

    //we did need to define this method to fullfill
    //the interface, but it does not have to do anything.
    public void onAccuracyChanged(Sensor sensor,
                            int accuracy)
    {

    }

    @Override
    protected void onStart() {
        super.onStart();
    // enable our sensor when the activity is resumed, ask for
        // 10 ms updates, so that is 100 times per second
        if (mRotationVectorSensor!=null)
            mSensorManager.registerListener(this, mRotationVectorSensor, 10000);
        if (temperatureSensor!=null)
            mSensorManager.registerListener(this,temperatureSensor,10000);
        if (pressureSensor!=null)
            mSensorManager.registerListener(this,pressureSensor,10000);
    }

    @Override
    protected void onStop() {
        super.onStop();
    // make sure to turn our sensor off when the activity is paused
        mSensorManager.unregisterListener(this);
    }

    //This method is also part of the interface we need
    //to implement. It is called every time there are new
    //sensor values (with the timeinterval specified in the
    // onCreate method).
    @Override
    public void onSensorChanged(SensorEvent event)
    {
        float[] mRotationMatrixFromVector = new float[9];
        //System.out.println("in onSensorChangedEvent");

        if (event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE)
        {
            float temperature = event.values[0];
            System.out.println("temperature = "+temperature+" degrees");
        }

        if (event.sensor.getType() == Sensor.TYPE_PRESSURE)
        {
            float pressure = event.values[0];
            System.out.println("pressure = "+pressure+" mbar");
        }


            float orientationVals[] = new float[3];
        // It is good practice to check that we received the proper sensor event
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR)
        {
            // Convert the rotation-vector to a 4x4 matrix.
            SensorManager.getRotationMatrixFromVector(mRotationMatrixFromVector, event.values);

            SensorManager.getOrientation(mRotationMatrixFromVector, orientationVals);

            // Optionally convert the result from radians to degrees
            orientationVals[0] = (float) Math.toDegrees(orientationVals[0]);
            orientationVals[1] = (float) Math.toDegrees(orientationVals[1]);
            orientationVals[2] = (float) Math.toDegrees(orientationVals[2]);

            /* Explanation on the various degrees
             Azimut (index 0): is used for rotation around the y-axis. That means like a normal
             compass detecting north/east/south/west - we don't use for this app
             But a value of 0 corresponds to north, 180 to south, positive values to
             East-side and negative values (-0 to -180) to West.

             Pitch (index 1): is the angle of the phone relative to the earth. The pitch is affected
             if you move the top of the phone down towards the ground or up towards the sky.
             0 degrees corresponds to when the phone is flat on a table.
             If you then tip the top of the phone towards the ground, the angles go from
             0 to positive 90, if you tip the top of the phone up then the angles go from
             0 to negative 90

             Roll (index 2):
             When the phone is tilted from "edge to edge". 0 corresponds to when the phone
             is flat on the table.
             If you then tilt the edge of the phone to "the right" then the degrees to
             from 0 to +180, if you til phone to the left, the degrees to from 0 to -180.
             */


          /*  Log.d("Sensors","\nAzimoth: " + orientationVals[0] + ", Pitch: "
                    + orientationVals[1] + ",Roll: "
                    + orientationVals[2]);*/
            float pitch = orientationVals[1];
            float roll = orientationVals[2];


            //we only take action when the angle is bigger/less
            //than +/-, otherwise the pacman would never sit still

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
