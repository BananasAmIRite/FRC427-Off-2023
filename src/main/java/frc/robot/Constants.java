// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.HashMap;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
 
  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
    public static final int kManipulatorControllerPort = 1; 
  }

  public static class DrivetrainConstants {
    // Swerve IDs
    public static class FrontLeft {
      public static final int kRotate = 3; // turn motor CAN ID
      public static final int kDrive = 4; // drive motor CAN ID
      public static final int kRotEncoder = 15; // turn encoder CAN ID
    }
    public static class FrontRight {
      public static final int kRotate = 5; 
      public static final int kDrive = 6; 
      public static final int kRotEncoder = 16; 
    }
    public static class BackRight {
      public static final int kRotate = 7; 
      public static final int kDrive = 8; 
      public static final int kRotEncoder = 13; 
    }
    public static class BackLeft {
      public static final int kRotate = 1; 
      public static final int kDrive = 2; 
      public static final int kRotEncoder = 14; 
    }

    // Gearing & Conversions
    public static final double kGearRatio = 6.12; // driving gear ratio of each swerve module
    public static final double kWheelRadiusInches = 1.5; // radius of the wheels
    public static final double kMetersPerRot = Units.inchesToMeters(2 * Math.PI * kWheelRadiusInches / kGearRatio); // calculate the position conversion factor of the swerve drive encoder
    public static final double kMetersPerSecondPerRPM = kMetersPerRot / 60; // calculate the velocity conversion factor of the swerve drive encoder

    public static final double kRotateGearRatio = 1; // gear ratio of the turn encoder (will be 1 as long as we use CANCoders on the output shaft)
    public static final double kDegreesPerRot = 360 / kRotateGearRatio; // position conversion factor of the turn encoder (converts from rotations to degrees)
    public static final double kDegreesPerSecondPerRPM = kDegreesPerRot / 60; // velocity conversion factor of the turn encoder 

    // Drivebase
    public static final double kTrackWidthMeters = Units.inchesToMeters(23.0); // horizontal dist between wheels
    public static final double kWheelBaseMeters = Units.inchesToMeters(23.0); // vertical dist between wheels

    // Kinematics
    public static final SwerveDriveKinematics kDriveKinematics = new SwerveDriveKinematics(
      // wheel locations relative to the center of the robot
      new Translation2d(kTrackWidthMeters / 2, kWheelBaseMeters / 2), // front left
      new Translation2d(kTrackWidthMeters / 2, -kWheelBaseMeters / 2), // front right
      new Translation2d(-kTrackWidthMeters / 2, kWheelBaseMeters / 2), // back left
      new Translation2d(-kTrackWidthMeters / 2, -kWheelBaseMeters / 2) // back right
    ); 

    // copied speeds (https://github.com/SwerveDriveSpecialties/swerve-template-2021-unmaintained/blob/master/src/main/java/frc/robot/subsystems/DrivetrainSubsystem.java)
    public static final double kMaxAttainableSpeedMetersPerSecond = 5880.0 / 60.0 / kGearRatio *
    2 * Units.inchesToMeters(kWheelRadiusInches) * Math.PI; // max attainable speed for each drive motor
    public static final double kMaxAttainableRotationRadPerSecond = kMaxAttainableSpeedMetersPerSecond /
    Math.hypot(kTrackWidthMeters / 2.0, kWheelBaseMeters / 2.0); // max rotation of robot
    
    // TODO: tune these
    public static final double kMaxSpeedMetersPerSecond = 1.0; // max velocity (no turning) of robot; may tune to be a fraction of the attainable module speed
    public static final double kMaxAccelerationMetersPerSecondSquared = kMaxSpeedMetersPerSecond / 1.0; // max acceleration of robot (accelerate to max speed in 1 second)
    public static final double kMaxRotationRadPerSecond = Math.PI; // max rotation speed of the robot
    public static final double kMaxRotationAccelerationRadPerSecondSquared = Math.PI; // max angular acceleration of robot

    // feedforward values (NO NEED to tune these)
    public static final double ksVolts = 0; 
    public static final double kvVoltSecondsPerMeter = 0; 
    public static final double kaVoltSecondsSquaredPerMeter = 0; 

    // drive speed PID values for a swerve module
    public static final double kModuleDrive_P = 0; 
    public static final double kModuleDrive_I = 0; 
    public static final double kModuleDrive_D = 0; 

    // found from sysid for one of the turn modules or tune by yourself
    // turn PID values for a swerve module
    public static final double kModuleTurn_P = 0.01; 
    public static final double kModuleTurn_I = 0; 
    public static final double kModuleTurn_D = 0.0001; 

    // turn in place PID for the whole robot
    public static final double kTurn_P = 0; 
    public static final double kTurn_I = 0; 
    public static final double kTurn_D = 0; 
    public static final double kTurn_FF = 0; 
    public static final double kTurnErrorThreshold = 0; 
    public static final double kTurnVelocityThreshold = 0; 

    // TODO: tune these but it should be fine
    // current limits for each motor
    public static final int kDriveCurrentLimit = 40; 
    public static final int kTurnCurrentLimit = 20; 

    // max acceleration/deceleration of each motor (used for high CG robots)
    public static final double kForwardSlewRate = kMaxAccelerationMetersPerSecondSquared; 
    public static final double kStrafeSlewRate = kMaxAccelerationMetersPerSecondSquared; 
    public static final double kTurnSlewRate = kMaxRotationAccelerationRadPerSecondSquared; 
  }

  public static class Trajectory {
    // translational PID of robot for trajectory use
    public static final double kDrive_P = 0; 
    public static final double kDrive_I = 0; 
    public static final double kDrive_D = 0;

    // angular PID (same as turn pid)
    public static final double kOmega_P = DrivetrainConstants.kTurn_P; 
    public static final double kOmega_I = DrivetrainConstants.kTurn_I; 
    public static final double kOmega_D = DrivetrainConstants.kTurn_D; 

    // max velocity & acceleration robot can go when following a trajectory
    public static final double kMaxVelocityMetersPerSecond = 3.21; 
    public static final double kMaxAccelerationMetersPerSecondSquared = 2.54; 

    public static final double kMaxCentripetalAcceleration = 0.8; 

    public static final HashMap<String, Command> eventMap = new HashMap<>(); 
  }
  
  public static class ArmConstants {
    public static final int kArmMotorId = 0;
    public static double kFF = 0;
    public static final double kS = 0;
    public static final double kV = 0;
    public static final double kG = 0;
    public static final double kA = 0;
    public static final double kT = 0;
    public static final double kArmLength = 0;
    public static final double kArmWeight = 0;
    public static final double kMortorOhms = 0;
    public static final double kGearBox = 0;
    public static final double kInitialAngle = 120;
    public static final double kGroundAngle = 0;
    public static final double kP = 0;
    public static final int kCurrentLimit = 40;
    public static final double kConversionFactor = 0.5;
    public static final double kVelocityConversionFactor = kConversionFactor/60;
    public static final double kAngleError = 0;
  }

  public static class IntakeConstants {
    public static final int kIntakeMotorRightId = 1;
    public static final int kIntakeMotorLeftId = 2;

    public static final int kIntakeMotorlimit = 40;
    
    public static final boolean kIntakeRightInversionState = true;
    public static final boolean kIntakeLeftInversionState = true;
  }

  public static class BalanceAutoConstants {
    public static final double kBalance_P = 0;
    public static final double kBalance_I = 0;
    public static final double kBalance_D = 0;
    public static final double kBalanceErrorThreshold = 0;
    ;


  }
}
