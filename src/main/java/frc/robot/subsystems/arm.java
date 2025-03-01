// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class arm extends SubsystemBase {
  SparkMax armmotor;
  SparkMaxConfig config;
  SparkClosedLoopController maintain;
  /** Creates a new arm. */
  public arm() {
    armmotor = new SparkMax(6, MotorType.kBrushless);
      config = new SparkMaxConfig();
    config.idleMode(IdleMode.kBrake);

    armmotor.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    maintain = armmotor.getClosedLoopController();
}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void movearm (double speed){
    armmotor.set(speed);
  }
  public void staypostion(){
    maintain.setReference(armmotor.getEncoder().getPosition(), ControlType.kPosition);
  }
}
