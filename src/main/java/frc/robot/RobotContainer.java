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
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    // This is the auton made selector
    private final SendableChooser<String> m_chooser = new SendableChooser<String>();
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
        configureDashboard();
        configureLimelight();
    }

    private final CommandXboxController controller = new CommandXboxController(OperatorConstants.kOperatorControllerPort);  // Controller on port 0
    
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
        controller.leftTrigger().whileTrue(new ClimbUp(m_climber));
        controller.rightTrigger().whileTrue(new ClimbDown(m_climber));
        
        //Algae intake
        controller.rightBumper().whileTrue(new suckinalage(m_Intake));
        controller.leftBumper().whileTrue(new suckinalage(m_Intake));
                      
        //Arm up/down        
        //m_Arm.setDefaultCommand(new armcommand(m_Arm, ()-> m_driverController.getRawAxis(5)));
        //controller.y().whileTrue(new armcommand(m_Arm, .1));

        // TODO - add elevator, intake, and any other controls we need here

        // wiring the default command for the drive train to the driver controller
        m_driveTrain.setDefaultCommand(m_driveTrain.moveManual(() -> deadZone(-m_driverController.getLeftY(), 0.1),
            () -> deadZone(-m_driverController.getLeftX(),0.1), () -> deadZone( -m_driverController.getRightX(),0.1)));
    }

    private void configureDashboard(){
        m_chooser.setDefaultOption("New auto", "New Auto");
        SmartDashboard.putData(m_chooser);
    }

    /**
     * We need to set the limelight pose and possibly configure the camera.
     */
    private void configureLimelight() {
        // distances are in meters and rotations are degrees
        // https://limelightlib-wpijava-reference.limelightvision.io/frc/robot/LimelightHelpers.html#setCameraPose_RobotSpace(java.lang.String,double,double,double,double,double,double)
        LimelightHelpers.setCameraPose_RobotSpace(m_limelight.getName(),
            0.0d, 0.0d, 0.0d, 0.0d, 0.0d, 90.0d);

        // set stream mode 2 so usb camera is main and limelight is thumbnail
        LimelightHelpers.setStreamMode_PiPSecondary(m_limelight.getName());
    }

    double deadZone(double value, double deadZone)
    {
        return value > deadZone ? value : value < -deadZone ? value : 0;
    }

    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    
    public Command getAutonomousCommand()
    {
        return DriveTrain.getAutonomousCommand(m_chooser.getSelected());
    }
}
