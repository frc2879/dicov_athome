package frc.robot;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.commands.ProfileDrive;

import frc.robot.commands.DoNothing;
import frc.robot.commands.DriveBack;


// class creates a drop down on shuffleboard to select an auto command to be run
public class AutoSelector {

    private SendableChooser<Programs> autoChooser = new SendableChooser<>();

    private Command autoCommand;

    public enum Programs {
        DO_NOTHING, DRIVE_RECKONING, DRIVE_PROFILING
    }

    private Programs autoChoice;

    public AutoSelector() {
        initialize();
    }

    public void initialize() {
        autoChooser.addOption("Do Nothing", Programs.DO_NOTHING);
        autoChooser.addOption("Drive Reckoning", Programs.DRIVE_RECKONING);
        autoChooser.addOption("Drive Profiling", Programs.DRIVE_PROFILING);
        SmartDashboard.putData("Auto", autoChooser);
    }

    // returns the selected command to be run
    public Command getAutoCommand() {
        autoChoice = autoChooser.getSelected();

        switch (autoChoice) {
            case DO_NOTHING:
                autoCommand = new DoNothing();
                break;

            case DRIVE_RECKONING:
                autoCommand = new DriveBack(1);
                // autoCommand = new DriveForward(1);
                break;

            case DRIVE_PROFILING:
                autoCommand = new ProfileDrive(Robot.drive).getProfilingCommand(TrajectoryBuilder.Paths.TRENCH_SHOOTING_POSE);
                break;
            
            default:
                autoCommand = new DoNothing();
                break;
        }

        return autoCommand;
    }
}