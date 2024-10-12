package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@Autonomous
public class BlueBucketSide extends OpMode {
    public Slides outtakeSlide;
    public Slides intakeSlide;
    public Servos outtakeServoClaw;
    public Servos outtakeServo;
    public Servos turnServo;
    public Servos pickupServo;
    public IntakeClaw intakeClaw;

    public int runFrames;

    @Override
    public void init() {
        outtakeSlide = new Slides(hardwareMap, "outtakemotor", 6000);
        intakeSlide = new Slides(hardwareMap, "intakemotor", 6000);
        outtakeServoClaw = new Servos(hardwareMap, "outtakeservoclaw");
        outtakeServo = new Servos(hardwareMap, "outtakeservo");
        turnServo = new Servos(hardwareMap, "turnservo");
        pickupServo = new Servos(hardwareMap, "pickupservo");
        intakeClaw = new IntakeClaw(hardwareMap);
        runFrames = 0;
    }

    @Override
    public void loop() {
        if (runFrames == 0) {
            getBlock(1000);
        }
    }

    public void depositBlock(int targetPos) {
        outtakeServo.moveForwardMAX();
        outtakeServoClaw.moveForwardMAX();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        outtakeSlide.runToTargetPosition(targetPos, 1.0);
        outtakeServo.moveBackwardMIN();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        outtakeServoClaw.moveBackwardMIN();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        outtakeServoClaw.moveSpecificPos(0.5);
        outtakeServo.moveForwardMAX();
        outtakeSlide.runToTargetPosition(0, 1.0);
    }

    public void getBlock(int targetPos) {
        intakeSlide.runToTargetPosition(targetPos, 1.0);
        intakeClaw.checkHuskyLens();
        intakeClaw.moveClaw();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        pickupServo.moveForwardMAX();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        intakeSlide.runToTargetPosition(0, 1.0);
        turnServo.moveForwardMAX();
        pickupServo.moveBackwardMIN();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        pickupServo.moveSpecificPos(0.7);
        turnServo.moveBackwardMIN();
    }
}
