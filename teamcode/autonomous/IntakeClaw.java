package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class IntakeClaw {
    public Servos orientationServo;
    public HuskyLenses huskyLensColor;
    public int rotateDegrees;
    public boolean rotate;

    public double SIDEROTATE = 0.5;
    public double VERTICALROTATE = 0.0;

    public void getHardware(HardwareMap hardwareMap) {
        huskyLensColor = new HuskyLenses(hardwareMap, "frontlens", "color");
        orientationServo = new Servos(hardwareMap, "orientation");
    }

    public void checkHuskyLens() {
        huskyLensColor.observedObjects = huskyLensColor.huskyLens.blocks();
        huskyLensColor.currentTarget = huskyLensColor.getFirstObject();

        if (huskyLensColor.currentTarget != null) {
            chooseObject();
        }
    }

    public void moveClawLeft() {
        checkHuskyLens();

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
