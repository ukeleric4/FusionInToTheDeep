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
        try {
            pickup(1500);
            deposit(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        runFrames++;
    }

    public void bringBack() {
        panningMotor.runToTargetPosition(3000, 1.0, "forward");
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
        Thread.sleep(2500);
        // Orient (add)

        // Pick up
        claw.moveForwardMAX();
        Thread.sleep(500);
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
        Thread.sleep(1500);
        // Move slide up
        slide.runToTargetPosition(targetPos, 1.0, "forward");
        // Move panning up
        panningServo.moveForwardMAX();
        Thread.sleep(500);
        // Deposit block
        claw.moveBackwardMIN();
        Thread.sleep(500);
        // Move panning back
        panningServo.moveBackwardMIN();
        // Move Slide down
        slide.runToTargetPosition(0, 1.0, "backward");
        panningServo.moveForwardMAX();
        // Pan down back to original position
        panningMotor.runToTargetPosition(0, 1.0, "forward");
        Thread.sleep(2000);
    }
}
