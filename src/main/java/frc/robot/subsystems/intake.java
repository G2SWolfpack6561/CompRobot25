// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class intake extends SubsystemBase {
  SparkMax inatkeMax;
  SparkMaxConfig config;
  /** Creates a new intake. */
  public intake() {
    inatkeMax = new SparkMax(24??, MotorType.kBrushless);
    config = new SparkMaxConfig();
    config.idleMode(IdleMode.kBrake);

    inatkeMax.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  public void suckin(){
    inatkeMax.set(-.4);
  }
  public void spitout(){
    inatkeMax.set(.4);
  }
  public void stop(){
    inatkeMax.set(0);
  }
}
