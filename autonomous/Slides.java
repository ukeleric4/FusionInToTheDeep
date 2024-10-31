package org.firstinspires.ftc.teamcode.autonomous;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import java.util.Objects;

public class Slides {
    public DcMotor slideMotor;
    public int motorRPM;

    boolean exit = false;

    public Slides(HardwareMap hw, String name, int slideMotorRPM) {
        slideMotor = hw.get(DcMotor.class, name);
        motorRPM = slideMotorRPM;
        setModeResetEncoder();
        Thread te = new Thread(name);
        te.start();
    }

    public void setDirectionForward() {
        slideMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setDirectionReverse() {
        slideMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setModeResetEncoder() { slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER); }

    public void runToTargetPosition(int targetPosition, double power, String direction) {
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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

    public void runForward(double power, int timeMs) {
        slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setDirectionForward();
        slideMotor.setPower(power);
        if (timeMs > 0 ) {
            try {
                sleep(timeMs);
            } catch (InterruptedException e) {
            }
            stopSlide();
        }
    }

    public void runBackward(double power, int timeMs) {
        slideMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        setDirectionReverse();
        slideMotor.setPower(power);
        if (timeMs > 0 ) {
            try {
                sleep(timeMs);
            } catch (InterruptedException e) {
            }
            stopSlide();
        }
    }

    public void stopSlide() {
        slideMotor.setPower(0);
    }
}
