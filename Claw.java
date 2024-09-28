package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Claw {
    public Servos orientationServo;
    public HuskyLenses huskyLensColor;
    public int rotateDegrees;
    public boolean rotate;

    public double SIDEROTATE = 0.7;
    public double VERTICALROTATE = 0.0;

    public void init(HardwareMap hardwareMap) {
        huskyLensColor = new HuskyLenses(hardwareMap, "huskylenscolor", "color");
        orientationServo = new Servos(hardwareMap, "orientationservo");
    }

    public void loop() {
        huskyLensColor.observedObjects = huskyLensColor.huskyLens.blocks();
        huskyLensColor.currentTarget = huskyLensColor.getFirstObject();

        if (huskyLensColor.currentTarget != null) {
            chooseObject();
        }

        if (rotate && huskyLensColor.currentTarget != null) {
            orientationServo.moveSpecificPos(SIDEROTATE);
        } else {
            orientationServo.moveSpecificPos(VERTICALROTATE);
        }
    }

    public void chooseObject() {
        rotateDegrees = (int) ((-60 * huskyLensColor.getProportion()) + 120);
        rotate = rotateDegrees >= 60;
    }
}
