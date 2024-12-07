package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HuskyLenses {
    public HuskyLens huskyLens;
    public int SCREENWIDTH = 320;
    public int MAXHEIGHT = 240;
    public int red = 3;
    public int blue = 2;
    public int yellow = 1;

    public HuskyLens.Block[] observedObjects = new HuskyLens.Block[30];
    public HuskyLens.Block currentTarget;

    public HuskyLenses(HardwareMap hw, String name, String mode) {
        huskyLens = hw.get(HuskyLens.class, name);
        switch (mode) {
            case "color":
                huskyLens.selectAlgorithm(HuskyLens.Algorithm.COLOR_RECOGNITION);
                break;
            case "obj_track":
                huskyLens.selectAlgorithm(HuskyLens.Algorithm.OBJECT_RECOGNITION);
                break;
            case "face":
                huskyLens.selectAlgorithm(HuskyLens.Algorithm.FACE_RECOGNITION);
                break;
            case "tag":
                huskyLens.selectAlgorithm(HuskyLens.Algorithm.TAG_RECOGNITION);
                break;
        }
    }

    public HuskyLens.Block getFirstObject() {
        if (observedObjects.length > 0) {
            return observedObjects[0];
        }
        return null;
    }

    public void updateObservedObjects() {
        observedObjects = huskyLens.blocks();
    }

    public int getTargetId() {
        return currentTarget.id;
    }

    public double getProportion() {
        return (double) currentTarget.height / currentTarget.width;
    }
}
