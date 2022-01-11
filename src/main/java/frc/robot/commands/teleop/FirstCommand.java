package frc.robot.commands.teleop;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class FirstCommand extends CommandBase{

    private String stateName = this.getClass().getSimpleName();
    private Timer stateTimer = new Timer();
    private double timeLimit = 10.5;
    private NetworkTableEntry entryStateTimer;

    private boolean firstPassInit = true;
    private boolean firstPassIsFinished = true;
    private boolean firstPassExecute = true;

    // Constructor -     // Use addRequirements() here to declare subsystem dependencies.
    public FirstCommand(){}

    public FirstCommand(Subsystem ...subsystems){
        System.out.println(String.format("Entering %s::%s", 
        this.getClass().getSimpleName(),
        new Throwable().getStackTrace()[0].getMethodName()));

        int i=1;
        for (Subsystem sub: subsystems){
            String subName = (sub instanceof SubsystemBase) ? ((SubsystemBase) sub).getName() : "Unnamed";
            System.out.println(String.format("Adding subsystem #%d -- %s", i, subName));
            this.addRequirements(sub);
            i++;
        }

    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {
        firstPassInit = true;
        firstPassIsFinished = true;
        firstPassExecute = true;

        if (firstPassInit){
            System.out.println(String.format("Entering %s::%s", 
            this.getClass().getSimpleName(),
            new Throwable().getStackTrace()[0].getMethodName()));
            firstPassInit = false;
        }

        stateTimer.reset();
        stateTimer.start();

        NetworkTableInstance inst = NetworkTableInstance.getDefault();
        NetworkTable table = inst.getTable("T_" + stateName);
        entryStateTimer = table.getEntry("stateTime");
    }
    
    // Returns true when the command should end.
    @Override
    public boolean isFinished() {
        if (firstPassIsFinished){
            System.out.println(String.format("Entering %s::%s", 
            this.getClass().getSimpleName(),
            new Throwable().getStackTrace()[0].getMethodName()));
            firstPassIsFinished = false;
        }
        
        return stateTimer.advanceIfElapsed(timeLimit);
    }

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {
        if (firstPassExecute){
            System.out.println(String.format("Entering %s::%s", 
            this.getClass().getSimpleName(),
            new Throwable().getStackTrace()[0].getMethodName()));
            firstPassExecute = false;
        }

        entryStateTimer.setDouble(stateTimer.get());        
    }
    

    // Called once the command ends or is interrupted.
    @Override
    public void end(boolean interrupted) {
        System.out.println(String.format("Entering %s::%s", 
            this.getClass().getSimpleName(),
            new Throwable().getStackTrace()[0].getMethodName()));
    }

    
}
