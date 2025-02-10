package frc.robot.commands.drivebase;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveTrain;

//
// Command to stop the robot and put wheels in X formation
//
//
public class StopCommand extends Command {
    /**
     *
     */
    private final DriveTrain driveBaseSubsystem;

    // Constructor
    public StopCommand(DriveTrain subsystem) {
        super();
        driveBaseSubsystem = subsystem;
        addRequirements(driveBaseSubsystem);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        super.execute();
        driveBaseSubsystem.lockSwerveDrivePose();
    }
}