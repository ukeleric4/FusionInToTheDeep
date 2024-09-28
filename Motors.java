package org.firstinspires.ftc.teamcode.autonomous;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Motors {
    public DcMotor motor;

    public Motors(HardwareMap hw, String name) {
        motor = hw.get(DcMotor.class, name);
    }

    public void rotateForward(double power) {
        motor.setDirection(DcMotorSimple.Direction.FORWARD);
        motor.setPower(power);
    }

    public void rotateBackward(double power) {
        motor.setDirection(DcMotorSimple.Direction.REVERSE);
        motor.setPower(power);
        motor.setPower(0);
    }

    public void stopRotation() {
        motor.setPower(0);
    }
}
