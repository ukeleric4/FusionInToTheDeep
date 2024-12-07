package org.firstinspires.ftc.teamcode.autonomous;

import static java.lang.Thread.sleep;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Slide extends Motor1 implements Runnable {

    static final int ZERO = 0;
    static final int SLIDE_HIGHBUCKET = 2100;//2500
    static final int PAN_HIGHBUCKET = 750;
    static final int PAN_HIGHBAR = 1200;
    static final int PANIGGING = 400;

    public enum level{

        zero,
        slide_highbucket,
        pan_highbucket,
        pan_highbar,
        panigging,

    }
    boolean do_async = false;
    boolean exit = false;
    level targetLevel = level.zero;

    @Override
    public void run() {
        while (exit == false) {
            if (do_async) {
                MoveToLevel(targetLevel);
                do_async = false;
            }

        }
    }

    public Slide(HardwareMap hardwareMap, Telemetry t, String device_name) {
        super(hardwareMap, t);
        motor = hardwareMap.get(DcMotorEx.class, device_name);
        name = device_name;
        super.setTargetPosition(0);
        setModeResetEncoder();
        setModeRunToPosition();
        Thread te = new Thread(this, device_name);
        te.start();
    }



    public void terminate () {exit = true;}
    private void move (int l) {
        if (name == "panningmotor"){
            runforward(l);
        } else {
            runforward(l);
        }
    }

    public void setmanualcontrol (boolean manual){
        if (manual == true){
            setModeResetEncoder();
        } else {
            setModeRunToPosition();
        }
    }
    public boolean MoveToLevel(Slide.level l){
        if (l == level.zero) {
            move(ZERO);
            //RobotLog.d("MoveToLevel 3");
        } else if (l == level.pan_highbar) {
        move(PAN_HIGHBAR);
            //RobotLog.d("MoveToLevel 1");
        } else if (l == level.pan_highbucket) {
        move(PAN_HIGHBUCKET);
            //RobotLog.d("MoveToLevel 2");
        } else if (l == level.slide_highbucket) {
        move(SLIDE_HIGHBUCKET);
            //RobotLog.d("MoveToAUTO 2");
        } else if (l == level.zero) {
        move(ZERO);
            //RobotLog.d("MoveToAUTO 2");
        } else if (l == level.panigging) {
            move(PANIGGING);
            //RobotLog.d("MoveToAUTO 2");
        }
        return true;
    }

    public boolean MoveToLevelAsync (Slide.level l) {
        targetLevel = l;
        do_async = true;
        return true;
    }

}
