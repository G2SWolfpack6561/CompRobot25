// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The methods in this class are called automatically corresponding to each mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;
  private final RobotContainer m_robotContainer;
  private XboxController xboxController;  // Xbox controller object
  private DoubleSolenoid doubleSolenoid;              // Solenoid object
  private boolean solenoidState;          // Track the solenoid's current state
  private boolean lastButtonState;        // Track the previous button state
  private boolean buttonPressed;
  
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  public Robot() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
    CameraServer.startAutomaticCapture();  
  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  @Override
    public void robotInit() {
        // Initialize the Xbox controller and solenoid
        xboxController = new XboxController(1);        // Assuming controller is on port 0
        doubleSolenoid = new DoubleSolenoid(PneumaticsModuleType.CTREPCM, 0, 1);     // DoubleSolenoid on PCM channels 0 and 1
        solenoidState = false;                         // Start with solenoid retracted (kReverse)
        lastButtonState = false;                       // Initially, the button isn't pressed
        buttonPressed = false;                         // No button press detected
    }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    // Read the current state of button A (button 1 on the Xbox controller)
    boolean currentButtonState = xboxController.getAButton();

    // Check if the button has been pressed
    if (currentButtonState && !lastButtonState) {
        // Button was just pressed, toggle the solenoid state
        if (solenoidState) {
            doubleSolenoid.set(DoubleSolenoid.Value.kReverse);  // Retract the solenoid
        } else {
            doubleSolenoid.set(DoubleSolenoid.Value.kForward);  // Extend the solenoid
        }
        solenoidState = !solenoidState;  // Toggle the state for the next button press
    }

    // Update the last button state for the next iteration
    lastButtonState = currentButtonState;
}


  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}