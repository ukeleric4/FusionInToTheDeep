package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous
public class BlueBucketSide extends OpMode {
    public Slides slide;
    public Motors panningMotor;
    public Servos claw;
    public Servos panningServo;
    public IntakeClaw orientation;

    public int runFrames;

    public double partWidthIn = 3.5;
    public double huskyFocalLengthIn = 0.141732;

    public double distanceIn;
    public double convertToTargetPosRatio = 42.8571429;

    public HuskyLens.Block currentTarget;

    @Override
    public void init() {
        slide = new Slides(hardwareMap, "slide", 6000);
        panningMotor = new Motors(hardwareMap, "panningmotor");
        claw = new Servos(hardwareMap, "claw");
        panningServo = new Servos(hardwareMap, "panning");
        orientation = new IntakeClaw(hardwareMap);

        runFrames = 0;
    }

    @Override
    public void loop() {
        orientation.checkHuskyLens();
        currentTarget = orientation.huskyLensColor.getFirstObject();
        distanceIn = getDistanceIn(currentTarget.width);
        telemetry.addData("Distance: ", distanceIn + " in");

        try {
            pickup((int) (distanceIn * convertToTargetPosRatio));
            deposit(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        runFrames++;
    }

    public void bringBack() {
        panningMotor.runToTargetPosition(3000, 1.0, "forward");
    }

    public double getDistanceIn(int width) {
        return (partWidthIn * huskyFocalLengthIn) / width;
    }

    public void pickup(int targetPos) throws InterruptedException {
        /*
        Start pos:
            Claw: open
            Orientation: horizontal
            Panning (servo): up
            Panning (motor): down
            Slide: back
         */

        // Move slide out
        slide.runToTargetPosition(targetPos, 1.0, "forward");
        // Move panning down
        panningServo.moveBackwardMIN();
        Thread.sleep(500);
        // Orient (add)

        // Pick up
        claw.moveForwardMAX();
        // Orient back to horizontal (add)

        // Move slide back
        slide.runToTargetPosition(0, 1.0, "backward");
    }

    public void deposit(int targetPos) throws InterruptedException {
        /*
        Start pos:
            Claw: closed
            Orientation: horizontal
            Panning (servo): up
            Panning (motor): down
            Slide: back
         */

        // Pan up
        panningMotor.runToTargetPosition(3000, 1.0, "backward");
        // Move slide up
        slide.runToTargetPosition(targetPos, 1.0, "forward");
        // Move panning up
        panningServo.moveForwardMAX();
        Thread.sleep(250);
        // Deposit block
        claw.moveBackwardMIN();
        Thread.sleep(250);
        // Move panning back
        panningServo.moveBackwardMIN();
        // Move Slide down
        slide.runToTargetPosition(100, 1.0, "backward");
        panningServo.moveForwardMAX();
        // Pan down back to original position
        panningMotor.runToTargetPosition(100, 1.0, "forward");
    }
}
