package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.autonomous.Motors;
import org.firstinspires.ftc.teamcode.autonomous.Servos;
import org.firstinspires.ftc.teamcode.autonomous.Slides;

@TeleOp(name="DRIVER CONTROL", group="Linear Opmode") // @Autonomous(...) is the other common choice
public class DRIVER_CONTROL extends LinearOpMode /*implements Runnable*/ {
    public Slides slide;
    public Motors panningMotor;
    public Servos claw;
    public Servos orientation;
    public Servos panningServo;

    public DcMotor fl;
    public DcMotor bl;
    public DcMotor fr;
    public DcMotor br;

    @Override
    public void runOpMode() {
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        slide = new Slides(hardwareMap, "slide", 6000);
        panningMotor = new Motors(hardwareMap, "panningmotor");
        claw = new Servos(hardwareMap, "claw");
        orientation = new Servos(hardwareMap, "orientation");
        panningServo = new Servos(hardwareMap, "panning");
        fl = hardwareMap.get(DcMotor.class, "fl");
        fr = hardwareMap.get(DcMotor.class, "fr");
        br = hardwareMap.get(DcMotor.class, "br");
        bl = hardwareMap.get(DcMotor.class, "bl");

        fr.setDirection(DcMotor.Direction.REVERSE);
        br.setDirection(DcMotor.Direction.REVERSE);

        while (opModeIsActive()) {
            if (gamepad1.a) {
                panningServo.moveBackwardMIN();
            } else if (gamepad1.y) {
                panningServo.moveForwardMAX();
            }

            if (gamepad1.x) {
                orientation.moveBackwardMIN();
            } else if (gamepad1.b) {
                orientation.moveForwardMAX();
            }

            // Slide movement
            if (gamepad1.right_bumper) {
                slide.runForward(1.0, 0);
            } else if (gamepad1.left_bumper) {
                slide.runBackward(1.0, 0);
            } else {
                slide.stopSlide();
            }

            if (gamepad1.right_trigger > 0.8) {
                panningMotor.rotateForward(1.0, 0);
            } else if (gamepad1.left_trigger > 0.8) {
                panningMotor.rotateBackward(1.0, 0);
            } else {
                panningMotor.stopRotation();
            }

            if (gamepad1.dpad_up) {
                claw.moveForwardMAX();
            } else if (gamepad1.dpad_down) {
                claw.moveBackwardMIN();
            }

            double y = -gamepad2.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad2.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad2.right_stick_x;

            // Denominator is the largest motor power (absolute value) or 1
            // This ensures all the powers maintain the same ratio,
            // but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;


            double velocity;

            if (gamepad2.left_trigger > 0){
                velocity = 0.35;
            } else if (gamepad2.right_trigger > 0){
                velocity = 1;
            } else {
                velocity = 0.7;
            }

            fl.setPower(frontLeftPower*velocity);
            bl.setPower(backLeftPower*velocity);
            fr.setPower(frontRightPower*velocity);
            br.setPower(backRightPower*velocity);
        }
    }
}
