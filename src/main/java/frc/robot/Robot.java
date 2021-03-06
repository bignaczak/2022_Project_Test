// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;
  private NetworkTableEntry xEntry;
  private NetworkTableEntry yEntry;

  private double x = 0;
  private double y = 0;

  // runtime metrics variables
  private int loopCount = 0;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    System.out.println(String.format("Entering %s::%s", 
        this.getClass().getSimpleName(),
        new Throwable().getStackTrace()[0].getMethodName()));
    
    m_robotContainer = new RobotContainer();

    //Get the default instance of NetworkTables that was created automatically
    //when your program starts
    NetworkTableInstance inst = NetworkTableInstance.getDefault();

    //Get the table within that instance that contains the data. There can
    //be as many tables as you like and exist to make it easier to organize
    //your data. In this case, it's a table called datatable.
    NetworkTable table = inst.getTable("datatable");

    //Get the entries within that table that correspond to the X and Y values
    //for some operation in your program.
    xEntry = table.getEntry("X");
    yEntry = table.getEntry("Y");
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
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

    //Using the entry objects, set the value to a double that is constantly
    //increasing. The keys are actually "/datatable/X" and "/datatable/Y".
    //If they don't already exist, the key/value pair is added.
    xEntry.setDouble(x);
    yEntry.setDouble(y);
    x += 0.05;
    y += 1.0;
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    System.out.println(String.format("Entering %s::%s", 
    this.getClass().getSimpleName(),
    new Throwable().getStackTrace()[0].getMethodName()));


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

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  @Override
  public void simulationInit() {
    if (loopCount < 10){
      System.out.println(String.format("Entering %s::%s during loop %d", 
          this.getClass().getSimpleName(),
          new Throwable().getStackTrace()[0].getMethodName(),
          loopCount));
    }
  }

  @Override
  public void simulationPeriodic() {
    if (loopCount < 10){
      System.out.println(String.format("Entering %s::%s during loop %d", 
          this.getClass().getSimpleName(),
          new Throwable().getStackTrace()[0].getMethodName(),
          loopCount));
      loopCount++;
    }
  }
}
