// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ClimbDown;
import frc.robot.commands.ClimbUp;
import frc.robot.commands.armcommand;
import frc.robot.commands.suckinalage;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.arm;
import frc.robot.subsystems.intake;
import frc.robot.subsystems.Pneumatics;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */

public class RobotContainer {

    // The Limelight is used for AprilTag processing and pose estimation but
    // may also provide the video stream for drive camera.
    LimelightDevice m_limelight = new LimelightDevice("limelight");
    intake m_Intake;
    arm m_Arm;

    // The climber will have buttons wired up in configureBindings.
    private final Climber m_climber = new Climber();
    
    // There are some commands available for the DriveTrain but the main feature
    // to be aware of is the default command wired up in the configureBindings.

    private final DriveTrain m_driveTrain = new DriveTrain(m_limelight);

    //private final Pneumatics m_Pneumatics = new Pneumatics();

    // This is the main driver controller.  Right now we'll also use this for climbing
    // but we may add a second controller to move the config over there.
    private final CommandXboxController m_driverController =
        new CommandXboxController(OperatorConstants.kDriverControllerPort);
        // add new for Op controller

    /** The container for the robot. Contains subsystems, OI devices, and commands. */
    public RobotContainer() {
        m_Intake = new intake();
        m_Arm = new arm();   
        configureBindings();
    }

    private final XboxController controller = new XboxController(0);  // Controller on port 0
    
    public XboxController getController() {
        return controller;
    }

    /**
     * Use this method to define your trigger->command mappings. Triggers can be created via the
     * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
     * predicate, or via the named factories in {@link
     * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
     * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
     * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
     * joysticks}.
     */
    private void configureBindings() {

        // we'll run the climb-up command while the right trigger is pressed; the left trigger will
        // run the climb-down command
        //
        // IF THE MOTOR RUNS WRONG DIRECTION, invert the motor in Climber subsystem
        m_driverController.leftTrigger().whileTrue(new ClimbUp(m_climber));
        m_driverController.rightTrigger().whileTrue(new ClimbDown(m_climber));
        
        //Algae intake
        m_driverController.rightBumper().whileTrue(new suckinalage(m_Intake));
        m_driverController.leftBumper().whileTrue(new suckinalage(m_Intake));
                      
        //Arm up/down        
        //m_Arm.setDefaultCommand(new armcommand(m_Arm, ()-> m_driverController.getRawAxis(5)));
        m_driverController.y().whileTrue(new armcommand(m_Arm, .1));

        // TODO - add elevator, intake, and any other controls we need here

        // wiring the default command for the drive train to the driver controller
        m_driveTrain.setDefaultCommand(m_driveTrain.moveManual(() -> -m_driverController.getLeftY(),
            () -> -m_driverController.getLeftX(), () -> -m_driverController.getRightX()));
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    
    public Command getAutonomousCommand()
    {
    return DriveTrain.getAutonomousCommand("New Auto");
    }
}
