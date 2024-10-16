package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Slides {
    public DcMotor slideMotor;
    public int motorRPM;

    public Slides(HardwareMap hw, String name, int slideMotorRPM) {
        slideMotor = hw.get(DcMotor.class, name);
        motorRPM = slideMotorRPM;
    }

    public void setDirectionForward() {
        slideMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setDirectionReverse() {
        slideMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void runToTargetPosition(int targetPosition, double power) {
        setDirectionForward();
        slideMotor.setTargetPosition(targetPosition);
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slideMotor.setPower(power);
        while (slideMotor.isBusy());
        slideMotor.setPower(0);
    }

    public void stopSlide() {
        slideMotor.setPower(0);
    }
}
