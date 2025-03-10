// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.arm;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class armcommand extends Command {
  boolean maintain;
  arm m_Arm;
  DoubleSupplier m_speed;
  /** Creates a new armcommand. */
  public armcommand(arm sentArm, DoubleSupplier sentspeed) {
    m_Arm = sentArm;
    m_speed = sentspeed;
    addRequirements(m_Arm);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  public armcommand(arm m_Arm2, double d) {
    //TODO Auto-generated constructor stub
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    maintain = false;
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    if (Math.abs(m_speed.getAsDouble())<.1){
      m_Arm.staypostion();
    }else{
      m_Arm.movearm(m_speed.getAsDouble());
    }

  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
