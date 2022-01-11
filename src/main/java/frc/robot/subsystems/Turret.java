package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Turret extends SubsystemBase{

    // Class Variables
    
    // Instance Variables
    private CANSparkMax swivelMotor;
    private CANSparkMax scoopMotor;
    private RelativeEncoder swivelEncoder;
    private RelativeEncoder scoopEncoder;
    
    // wiring info
    private static final int swivelCANId = 12;
    private static final int scoopCANId = 13;

    private int loopCount = 0;

    // Constructors
    public Turret(){
        swivelMotor = new CANSparkMax(swivelCANId, MotorType.kBrushless);
        scoopMotor = new CANSparkMax(scoopCANId, MotorType.kBrushless);

        swivelEncoder = swivelMotor.getEncoder();
        scoopEncoder = scoopMotor.getEncoder();
    }

    // Getters & Setters

    // Class Methods

    // Instance Methods
    @Override
    public void periodic() {
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
