// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.teleop.CmdStartClimber;
import frc.robot.commands.teleop.CmdStopClimber;
import frc.robot.commands.teleop.FirstCommand;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Turret;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.ConditionalCommand;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.PerpetualCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final Turret turret = new Turret();
  private final Climber climber = new Climber();




  // private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final FirstCommand m_autoCommand = new FirstCommand(m_exampleSubsystem, turret);
  // private final Command commandWhenTrue;
  // private final Command commandWhenFalse;
  // private final ConditionalCommand conditionalCommand;
  private final Command commandClimb;
  private final Command commandStopClimb;

  private final XboxController xboxController = new XboxController(0);
  private final JoystickButton xboxStart = new JoystickButton(xboxController, XboxController.Button.kStart.value);
  private final JoystickButton xboxA = new JoystickButton(xboxController, XboxController.Button.kA.value);

  private NetworkTableEntry condInputEntry = NetworkTableInstance.getDefault().getTable("conditionalInput").getEntry("A.ispressed");

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // commandWhenTrue = new InstantCommand(()->condInputEntry.setBoolean(true), turret);
    // commandWhenFalse = new InstantCommand(()->condInputEntry.setBoolean(false), turret);
    // conditionalCommand = new ConditionalCommand(commandWhenTrue, commandWhenFalse, ()->xboxController.getAButton());
    commandClimb = new CmdStartClimber(climber);
    commandStopClimb = new CmdStopClimber(climber);
    

    // Configure the button bindings
    configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    
    // this works but not efficient
    // xboxA.whenReleased(conditionalCommand).whenPressed(conditionalCommand);
    xboxA
      .whileHeld(commandClimb)
      .whenReleased(commandStopClimb);


    // conditionalCommand.perpetually();
    xboxStart.whenPressed(m_autoCommand);

  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
