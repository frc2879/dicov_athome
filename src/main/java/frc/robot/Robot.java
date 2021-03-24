
package frc.robot;

import java.util.List;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import frc.robot.recorder.*;

// the robot's main class, used to run all its systems and programs at the right time
public class Robot extends TimedRobot {

    public static Controllers controllers;


    public static Drive drive;


    // public static StateRecorder recorder;
    // public static StateRunner runner;

    public static Camera camera;


    private AutoSelector autoSelector;
    private Command autonomousCommand;

    @Override
    public void robotInit() {
        controllers = new Controllers();


        drive = new Drive();
        



        TrajectoryBuilder.buildTrajectories(); // very important this is in robot init before the autoSelector
        autoSelector = new AutoSelector();



        // recorder = new StateRecorder();
        // runner = new StateRunner();

        // GsonSmartDash.put();

        // camera = new Camera();
        // new Thread(camera).start();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run(); // used to trigger a selected auto command to run during autonomous
        // limelight.dashboard();

        // camera.stop(controllers.getStopCameraButton());
    }

    @Override
    public void autonomousInit() {
        setMotorsBrake();
        

        drive.initalize();


        

       

        // autonomous implimentation of commands
        // all that's needed to run any command once selected from dashboard
        autonomousCommand = autoSelector.getAutoCommand();
        if (autonomousCommand != null) {
            autonomousCommand.schedule();
        }
    }

    @Override
    public void autonomousPeriodic() {
        drive.dashboard();

        // run methods so systems can be auto controlled from auto commands

    }

    @Override
    public void teleopInit() {
        setMotorsBrake();

        drive.initalize();


       

       

        // recorder.initialize();
        // runner.counterInitialize();
    }

    @Override
    public void teleopPeriodic() {
        //

        // mobility controls
        //TODO:THIS IS PROBABLY IMPORTANT
        drive.robotDrive(controllers.getDriveSpeedAxis(), controllers.getDriveTurnAxis(), controllers.isDriveSideToggle());
        drive.dashboard();


        // manipulator controls
        
       

        // recorder.record();
    }

    @Override
    public void disabledInit() {
        setMotorsCoast();
        drive.resetSensors();

        // if (GsonSmartDash.shouldRecord) {
        // List<State> states = recorder.getStates();
        // try {
        // StatesWriter.writeStates(states, GsonSmartDash.gsonFileName);
        // } catch (Exception e) {
        // e.printStackTrace();
        // }
        // }
        // StateLister.getStateNames();
        // SmartDashboard.putBoolean("Should Record", false);
    }

    @Override
    public void disabledPeriodic() {
        // GsonSmartDash.set();

        // if (!GsonSmartDash.shouldRecord) {
        // SmartDashboard.putString("Gson File Name", "");
        // }
    }

    // used when the robot is enabled so systems don't overrun when told to stop
    public void setMotorsBrake() {
        
        drive.setMotorsBrake();
      
    }

    // used when robot disabled so systems can move freely
    public void setMotorsCoast() {
       
        drive.setMotorsCoast();
    }
}
