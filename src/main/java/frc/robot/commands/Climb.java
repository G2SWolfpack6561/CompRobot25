package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.motorcontrol.Talon;

public class Climb {
    private Talon motor;
     
    private static final double REVOLUTIONS = 3;
    private static final double MOTOR_SPEED = 0.5;
    private static final double TIME_PER_REVOLUTION = 1.0;

    public Climb() {
        // Edit the channel 
        motor = new Talon(0);
    }
    public void turnRevolutions(){
        double timeTotal = REVOLUTIONS *TIME_PER_REVOLUTION;
        motor.set(MOTOR_SPEED);
        Timer.delay(timeTotal);
        motor.set(0);

    }
    public static void main(String[] args){
        Climb climb = new Climb();
        climb.turnRevolutions();
    }
}
