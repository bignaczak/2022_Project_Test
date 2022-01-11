// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ExampleSubsystem extends SubsystemBase {

  private int loopCount = 0;
  /** Creates a new ExampleSubsystem. */
  public ExampleSubsystem() {}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    if (loopCount < 10){
      System.out.println(String.format("Entering %s::%s during loop %d", 
          this.getClass().getSimpleName(),
          new Throwable().getStackTrace()[0].getMethodName(),
          loopCount));
    }
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
    if (loopCount < 10){
      System.out.println(String.format("Entering %s::%s during loop %d", 
          this.getClass().getSimpleName(),
          new Throwable().getStackTrace()[0].getMethodName(),
          loopCount));
      loopCount++;

    }
  }
}
