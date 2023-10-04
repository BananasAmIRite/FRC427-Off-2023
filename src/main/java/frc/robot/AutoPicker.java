package frc.robot;

import java.util.List;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.auto.PIDConstants;
import com.pathplanner.lib.auto.SwerveAutoBuilder;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drivetrain.Drivetrain;

public class AutoPicker {
    private SendableChooser<Command> chooser = new SendableChooser<>();

    private SwerveAutoBuilder autoBuilder;
    
    public AutoPicker(Drivetrain driveSubsystem) {
        autoBuilder = new SwerveAutoBuilder(
            driveSubsystem::getPose, // Pose2d supplier
            driveSubsystem::resetPose, // Pose2d consumer, used to reset odometry at the beginning of auto
            Constants.DrivetrainConstants.kDriveKinematics, // SwerveDriveKinematics
            new PIDConstants(Constants.Trajectory.kDrive_P, Constants.Trajectory.kDrive_I, Constants.Trajectory.kDrive_D), // PID constants to correct for translation error (used to create the X and Y PID controllers)
            new PIDConstants(Constants.Trajectory.kOmega_P, Constants.Trajectory.kOmega_I, Constants.Trajectory.kOmega_D), // PID constants to correct for rotation error (used to create the rotation controller)
            driveSubsystem::swerveDrive, // Module states consumer used to output to the drive subsystem
            Constants.Trajectory.eventMap,
            true, // Should the path be automatically mirrored depending on alliance color. Optional, defaults to true
            driveSubsystem // The drive subsystem. Used to properly set the requirements of path following commands
        ); 
    }

    public void addAuto(String name, Command auto) {
        this.chooser.addOption(name, auto);
    }

    public Command addPPSwerveAuto(String name, String fileName, double maxVel, double maxAccel) {
        List<PathPlannerTrajectory> group = PathPlanner.loadPathGroup(fileName, new PathConstraints(maxVel, maxAccel)); 

        return autoBuilder.fullAuto(group); 
    }

    public Command addPPSwerveAuto(String name, String fileName) {
        return addPPSwerveAuto(name, fileName, Constants.Trajectory.kMaxVelocityMetersPerSecond, Constants.Trajectory.kMaxAccelerationMetersPerSecondSquared); 
    }

    public Command getAuto() {
        return chooser.getSelected(); 
    }
}