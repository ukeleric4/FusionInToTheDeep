package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp
public class BlueBucketSide extends OpMode {
    public Slides outtakeSlide;

    @Override
    public void init() {
        outtakeSlide = new Slides(hardwareMap, "outtakemotor", 6000);
    }

    @Override
    public void loop() {
        outtakeSlide.setDirectionForward();
        outtakeSlide.runToTargetPosition(1000, 1.0);
    }
}
