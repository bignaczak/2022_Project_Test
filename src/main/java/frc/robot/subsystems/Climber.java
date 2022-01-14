package frc.robot.subsystems;

import javax.xml.validation.Schema;

import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxRelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.motorcontrol.Spark;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.commands.teleop.CmdStopClimber;

public class Climber extends SubsystemBase{
    
    // *****************************************
    // Class Variables
    // *****************************************
    private static final double CLIMB_SPEED = 0.55;

    // *****************************************
    // Instance Variables
    // *****************************************

    // state
    private boolean isPowered = false;
    private Command previousCommand = null;
    
    // Actuators
    private CANSparkMax climberRight;
    private CANSparkMax climberLeft;
    
    // Sensors
    private SparkMaxRelativeEncoder encoderRight;
    private SparkMaxRelativeEncoder encoderLeft;

    // Telemetry
    private NetworkTableEntry entryIsPowered;
    private NetworkTableEntry entryCurrentCommand;
    private NetworkTableEntry entryMotorPower;

    // *****************************************
    // Constructors
    // *****************************************

    public Climber(){
        System.out.println(String.format("Entering %s::%s", this.getClass().getSimpleName(), new Throwable().getStackTrace()[0].getMethodName()));
        System.out.println("About to instantiate motors");
        climberRight = new CANSparkMax(0, MotorType.kBrushless);
        climberLeft = new CANSparkMax(1, MotorType.kBrushless);
        System.out.println("Motors instantiated");

        // this.setDefaultCommand((new InstantCommand(this::stop, this)).perpetually());

        NetworkTable networkTable = NetworkTableInstance.getDefault().getTable(this.getClass().getSimpleName());
        this.entryIsPowered = networkTable.getEntry("isPowered");
        this.entryCurrentCommand = networkTable.getEntry("Current Command");
        this.entryMotorPower = networkTable.getEntry("Motor Power");

        //updateNetworkTable();

    }

    // *****************************************
    // Getters & Setters
    // *****************************************


    // *****************************************
    // Class Methods
    // *****************************************


    // *****************************************
    // Instance Methods
    // *****************************************

    @Override
    public void periodic() {        
        // TODO Auto-generated method stub
        try{
            updateNetworkTable();
        } catch (Exception e){

        }

    }

    @Override
    public void simulationPeriodic() {
        // TODO Auto-generated method stub
        super.simulationPeriodic();
    }

    public void stop(){
        System.out.println(String.format("Entering %s::%s", this.getClass().getSimpleName(), new Throwable().getStackTrace()[0].getMethodName()));

        climberRight.stopMotor();
        climberLeft.stopMotor();
        isPowered = false;
    }

    public void climb(){
        System.out.println(String.format("Entering %s::%s", this.getClass().getSimpleName(), new Throwable().getStackTrace()[0].getMethodName()));

        climberRight.set(CLIMB_SPEED);
        climberLeft.set(CLIMB_SPEED);
        isPowered = true;

    }

    private void updateNetworkTable(){
        // System.out.println(String.format("Entering %s::%s", this.getClass().getSimpleName(), new Throwable().getStackTrace()[0].getMethodName()));

        // String currentCommand = this.getRunningCommand();
        try{
            entryIsPowered.setBoolean(isPowered);
            // entryCurrentCommand.setString(currentCommand);
            entryMotorPower.setDouble(climberRight.getBusVoltage());
        }catch (Exception e){
            System.out.println(String.format("Entering %s::%s", this.getClass().getSimpleName(), new Throwable().getStackTrace()[0].getMethodName()));
            System.out.println(e.getMessage());
        }
    }

    private String getRunningCommand(){
        // This didn't work because the previous command was not captured
        // It is an instant command which runs ones and then removes from schedule
        // So it never gets registered as a running command and evades capture
        String currentCommand = "Unknown";
        if (this.getCurrentCommand() == null){
            if (this.previousCommand != null) currentCommand = previousCommand.getName();
            // otherwise accept the default value "Unknown"
        } else {
            currentCommand = this.getCurrentCommand().getName();
            this.previousCommand = this.getCurrentCommand();
        }
        return currentCommand;
    }

    


    
}
