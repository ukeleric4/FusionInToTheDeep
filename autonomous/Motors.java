package org.firstinspires.ftc.teamcode.autonomous;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Objects;

public class Motors {
    public DcMotor motor;

    public Motors(HardwareMap hw, String name) {
        motor = hw.get(DcMotor.class, name);
        setModeResetEncoder();
    }

    public void rotateForward(double power, int timeMs) {
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setDirectionForward();
        motor.setPower(power);
        if (timeMs > 0 ) {
            try {
                sleep(timeMs);
            } catch (InterruptedException e) {
            }
            stopRotation();
        }
    }

    public void rotateBackward(double power, int timeMs) {
        motor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setDirectionReverse();
        motor.setPower(power);
        if (timeMs > 0 ) {
            try {
                sleep(timeMs);
            } catch (InterruptedException e) {
            }
            stopRotation();
        }
    }

    public void setModeResetEncoder() { motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); }

    public void runToTargetPosition(int targetPos, double power, String direction) {
        if (Objects.equals(direction, "forward")) {
            setDirectionForward();
        } else {
            setDirectionReverse();
        }
        motor.setTargetPosition(targetPos);
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(power);
        while (motor.isBusy());
        motor.setPower(0);
    }

    public void setDirectionForward() {
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setDirectionReverse() {
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void stopRotation() {
        motor.setPower(0);
    }
}
