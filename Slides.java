package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Objects;

public class Slides {
    public DcMotor slideMotor;
    public int motorRPM;

    public Slides(HardwareMap hw, String name, int slideMotorRPM) {
        slideMotor = hw.get(DcMotor.class, name);
        motorRPM = slideMotorRPM;
        setModeResetEncoder();
    }

    public void setDirectionForward() {
        slideMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setDirectionReverse() {
        slideMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setModeResetEncoder() { slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); }

    public void runToTargetPosition(int targetPosition, double power, String direction) {

        if (Objects.equals(direction, "forward")) {
            setDirectionForward();
        } else {
            setDirectionReverse();
        }
        slideMotor.setTargetPosition(targetPosition);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(power);
        while (slideMotor.isBusy());
        slideMotor.setPower(0);
    }

    public void runForTime() {

    }

    public void stopSlide() {
        slideMotor.setPower(0);
    }
}
