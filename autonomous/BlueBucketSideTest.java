package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@Autonomous
public class BlueBucketSideTest extends OpMode {
    public Slides slide;
    public Motors panningMotor;
    public Servos claw;
    public Servos panningServo;

    public int runFrames;

    public HuskyLens.Block currentTarget;

    @Override
    public void init() {
        slide = new Slides(hardwareMap, "slide", 6000);
        panningMotor = new Motors(hardwareMap, "panningmotor");
        claw = new Servos(hardwareMap, "claw");
        panningServo = new Servos(hardwareMap, "panning");

        runFrames = 0;
    }

    @Override
    public void loop() {
        claw.moveForwardMAX();
        runToClipAngle(1800, 1000);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        runToClipAngle(1300, 800);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        otherThing(0, 0);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        runFrames++;
    }

    public void runToClipAngle(int targetPos1, int targetPos2) {
        panningMotor.runToTargetPosition(targetPos1, 1.0, "backward");
        slide.runToTargetPosition(targetPos2, 1.0, "forward");
    }
    public void otherThing(int targetPos1, int targetPos2) {
        slide.runToTargetPosition(targetPos2, 1.0, "forward");
        panningMotor.runToTargetPosition(targetPos1, 1.0, "backward");
    }

    public void bringBack() {
        panningMotor.runToTargetPosition(3000, 1.0, "forward");
    }

//    public void pickup(int targetPos) throws InterruptedException {
//        /*
//        Start pos:
//            Claw: open
//            Orientation: horizontal
//            Panning (servo): up
//            Panning (motor): down
//            Slide: back
//         */
//
//        // Move slide out
//        slide.runToTargetPosition(targetPos, 1.0, "forward");
//        // Move panning down
//        panningServo.moveBackwardMIN();
//        Thread.sleep(500);
//        // Orient (add)
//
//        // Pick up
//        claw.moveForwardMAX();
//        // Orient back to horizontal (add)
//
//        // Move slide back
//        slide.runToTargetPosition(0, 1.0, "backward");
//    }
//
//    public void deposit(int targetPos) throws InterruptedException {
//        /*
//        Start pos:
//            Claw: closed
//            Orientation: horizontal
//            Panning (servo): up
//            Panning (motor): down
//            Slide: back
//         */
//
//        // Pan up
//        panningMotor.runToTargetPosition(3000, 1.0, "backward");
//        // Move slide up
//        slide.runToTargetPosition(targetPos, 1.0, "forward");
//        // Move panning up
//        panningServo.moveForwardMAX();
//        Thread.sleep(250);
//        // Deposit block
//        claw.moveBackwardMIN();
//        Thread.sleep(250);
//        // Move panning back
//        panningServo.moveBackwardMIN();
//        // Move Slide down
//        slide.runToTargetPosition(100, 1.0, "backward");
//        panningServo.moveForwardMAX();
//        // Pan down back to original position
//        panningMotor.runToTargetPosition(100, 1.0, "forward");
//    }
}
