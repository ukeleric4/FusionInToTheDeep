package org.firstinspires.ftc.teamcode.teleop;

import static java.lang.Thread.sleep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.dfrobot.HuskyLens;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.autonomous.HuskyLenses;
import org.firstinspires.ftc.teamcode.autonomous.IntakeClaw;
import org.firstinspires.ftc.teamcode.autonomous.Motors;
import org.firstinspires.ftc.teamcode.autonomous.Servos;
import org.firstinspires.ftc.teamcode.autonomous.Slides;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;

@TeleOp(name="DRIVER CONTROL", group="Linear Opmode") // @Autonomous(...) is the other common choice
public class DRIVER_CONTROL extends LinearOpMode /*implements Runnable*/ {
    public Slides slide;
    public Motors panningMotor;
    public Servos claw;
    public Servos orientation;
    public Servos panningServo;
    public HuskyLenses frontLens;
    public IntakeClaw intakeClaw = new IntakeClaw();
    public Pose2d currentPose;

    public DcMotor fl;
    public DcMotor bl;
    public DcMotor fr;
    public DcMotor br;

    boolean dpadRightPressed;
    boolean dpadLeftPressed;
    boolean yPressed;

    int runFrames = 0;
    double servoState = 0.0;
    double panningServoState = 0.0;
    double slidespeed = 1.0; // backward min

    @Override
    public void runOpMode() throws InterruptedException {
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        slide = new Slides(hardwareMap, "slide", 6000);
        panningMotor = new Motors(hardwareMap, "panningmotor");
        intakeClaw.getHardware(hardwareMap);
        claw = new Servos(hardwareMap, "claw");
        orientation = new Servos(hardwareMap, "orientation");
        panningServo = new Servos(hardwareMap, "panning");
        frontLens = new HuskyLenses(hardwareMap, "frontlens", "color");

        fl = hardwareMap.get(DcMotor.class, "fl");
        fr = hardwareMap.get(DcMotor.class, "fr");
        bl = hardwareMap.get(DcMotor.class, "bl");
        br = hardwareMap.get(DcMotor.class, "br");
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        drive.setPoseEstimate(new Pose2d(0, 0, Math.toRadians(90)));
        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        while (opModeIsActive()) {
            drive.update();
            currentPose = drive.getPoseEstimate();

            double velocity;
            if (gamepad1.right_trigger > 0.8) {
                velocity = 1.0;
            } else if (gamepad1.left_trigger > 0.8) {
                velocity = 0.3;
            } else {
                velocity = 0.75;
            }
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y * velocity,
                            -gamepad1.right_stick_x * velocity,
                            -gamepad1.left_stick_x * velocity
                    )
            );

            // runs slide forward until husky lens detects a yellow block
            if (gamepad2.y && !yPressed) {
                frontLens.updateObservedObjects();
                while (frontLens.observedObjects.length == 0) {
                    frontLens.updateObservedObjects();
                    slide.runForward(1.0, 0);
                }
                slide.stopSlide();
            }

            // when 'a' is pressed, panning servo will move based on current pos (up or down)
            if (gamepad2.a) {
                if (panningServoState == 0.0) {
                    panningServoState = 1.0;
                    panningServo.moveForwardMAX();
                } else if (panningServoState == 1.0) {
                    panningServoState = 0.0;
                    panningServo.moveForwardMAX();
                }
            }

            // If 'x' or 'b' is being pressed, the orientation will turn slightly ever 60 loops
            if (gamepad2.x) {
                if (runFrames % 60 == 0) {
                    if (servoState < 1.0) {
                        servoState += 0.1;
                    }
                    orientation.moveSpecificPos(servoState);
                }
            } else if (gamepad2.b) {
                if (runFrames % 60 == 0) {
                    if (servoState > 0.0) {
                        servoState -= 0.1;
                    }
                    orientation.moveSpecificPos(servoState);
                }
            }

            // slide movement
            if (gamepad2.right_bumper) {
                slide.runForward(slidespeed, 0);
            } else if (gamepad2.left_bumper) {
                slide.runBackward(slidespeed, 0);
            } else {
                slide.stopSlide();
            }

            // panning movement
            if (gamepad2.right_trigger > 0.8) {
                panningMotor.rotateForward(1.0, 0);
            } else if (gamepad2.left_trigger > 0.8) {
                panningMotor.rotateBackward(-1.0, 0);
            } else {
                panningMotor.stopRotation();
            }

            // claw opening and claw closing
            if (gamepad2.dpad_up) {
                claw.moveForwardMAX();
            } else if (gamepad2.dpad_down) {
                claw.moveBackwardMIN();
            }

            // If, dpad_left is pressed, the slide will run to a certain position
            if (gamepad2.dpad_left && !dpadLeftPressed) {
                slide.runToTargetPosition(600, 1.0, "forward");
            }

            dpadLeftPressed = gamepad2.dpad_left;
            dpadRightPressed = gamepad2.dpad_right;
            yPressed = gamepad2.y;

            runFrames++;
            }
        }

    /*public void strafeRight(int timeMs) {
        fl.setPower(1.0); // backward
        fr.setPower(-1.0); // forward
        bl.setPower(-1.0); // forward
        br.setPower(1.0); // backward
        if (timeMs > 0 ) {
            sleep(timeMs);
            stopMovement();
        }
    }

    public void moveBackward(int timeMs) {
        fl.setPower(-1.0);
        fr.setPower(-1.0);
        bl.setPower(-1.0);
        br.setPower(-1.0);
        if (timeMs > 0 ) {
            sleep(timeMs);
            stopMovement();
        }
    }

    public void stopMovement() {
        fl.setPower(0);
        fr.setPower(0);
        bl.setPower(0);
        br.setPower(0);
    }*/
    }