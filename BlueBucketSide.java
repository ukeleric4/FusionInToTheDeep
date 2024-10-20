package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class BlueBucketSide extends OpMode {
    public Slides slide;
    public Motors panningMotor;
    public Servos claw;
    public Servos orientation;
    public Servos panningServo;

    public int runFrames;

    @Override
    public void init() {
        slide = new Slides(hardwareMap, "slide", 6000);
        panningMotor = new Motors(hardwareMap, "panningmotor");
        claw = new Servos(hardwareMap, "claw");
        orientation = new Servos(hardwareMap, "orientation");
        panningServo = new Servos(hardwareMap, "panning");

        runFrames = 0;
    }

    @Override
    public void loop() {
        deposit(1000);

        runFrames++;
    }

    public void pickup(int targetPos) {
        /*
        Start pos:
            Claw: open
            Orientation: horizontal
            Panning (servo): up
            Panning (motor): down
            Slide: back
         */

        // Move slide out
        slide.runToTargetPosition(targetPos, 1.0);
        // Move panning down
        panningServo.moveForwardMAX();
        // Orient (add)

        // Pick up
        claw.moveForwardMAX();
        // Orient back to horizontal (add)

        // Move panning up
        panningServo.moveBackwardMIN();
        // Move slide back
        slide.runToTargetPosition(0, 1.0);
    }

    public void deposit(int targetPos) {
        /*
        Start pos:
            Claw: closed
            Orientation: horizontal
            Panning (servo): up
            Panning (motor): down
            Slide: back
         */

        // Pan up
        panningMotor.runToTargetPosition(500, 1.0);
        // Move slide up
        slide.runToTargetPosition(targetPos, 1.0);
        // Move panning servo forward
        panningServo.moveForwardMAX();
        // Deposit block
        claw.moveBackwardMIN();
        // Move panning servo back
        panningServo.moveBackwardMIN();
        // Move Slide down
        slide.runToTargetPosition(0, 1.0);
        // Pan down back to original position
        panningMotor.runToTargetPosition(0, 1.0);
    }
}
