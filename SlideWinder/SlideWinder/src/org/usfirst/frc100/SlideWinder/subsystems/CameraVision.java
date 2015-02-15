package org.usfirst.frc100.SlideWinder.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class CameraVision extends Subsystem {
	// private double cycleTime = 0; // Default value
	Timer timer = new Timer();
	VideoCapture camera;
	Mat frame;
	Mat greyFrame;
	Mat blurFrame;
	Mat roiFrame;
	Mat scharrFrame;
	Mat endFrame;
	int scanRow;
	int threshold;
	int target;
	int firstEdge;
	int secondEdge;
	int midTapeCoordinate;
	int offset;
	double visionProcessingTime;
	public static boolean cameraIsOpened;

	/**
	 * Called once when robot turns on
	 */
	public CameraVision() {
		System.load("/usr/local/lib/lib_OpenCV/java/libopencv_java2410.so");
		// // Initialization code here
	}

	@Override
	protected void initDefaultCommand() {
	}

	/**
	 * Called once in auto init
	 */
	public void initCamera() {
		camera = new VideoCapture("http://10.1.0.11/axis-cgi/mjpg/video.cgi?dummy=video.mjpg");
		if (!camera.isOpened()) {
			System.out.println("CAMERA NOT FOUND:");
			cameraIsOpened = false;
		}
		frame = new Mat();
		greyFrame = new Mat();
		blurFrame = new Mat();
		roiFrame = new Mat();
		scharrFrame = new Mat(3, 640, CvType.CV_16S);
		endFrame = new Mat();
		scanRow = 80;
		threshold = 1000;
		target = 160;
		// cycleTime = SmartDashboard.getNumber("CyleTime");
		// More init code here
	}

	/**
	 * Gets the distance in pixels from the line
	 * 
	 * @return The offset in pixels
	 */
	public int getLineOffset() {
		timer.reset();
		timer.start();
		if (camera.isOpened()) {
			cameraIsOpened = true;
			camera.read(frame);
			Imgproc.cvtColor(frame, greyFrame, Imgproc.COLOR_BGR2GRAY);
			Imgproc.blur(greyFrame, blurFrame, new Size(3, 3));
			roiFrame = blurFrame.rowRange(scanRow, scanRow + 3);
			Imgproc.Sobel(roiFrame, scharrFrame, CvType.CV_16S, 1, 0,Imgproc.CV_SCHARR, 1, Imgproc.BORDER_DEFAULT);
			endFrame = scharrFrame.rowRange(2, 3);
			short[] buff = new short[(int) (endFrame.total()) * endFrame.channels()];
			endFrame.get(0, 0, buff);
			int sum1 = 0;
			int sum2 = 0;
			int numOfTimes1 = 0;
			int numOfTimes2 = 0;
			for (int i = 0; i <= (buff.length - 1); i++) {
				if (buff[i] >= threshold) {
					sum1 = sum1 + i;
					numOfTimes1++;
				}
				if (buff[i] <= -threshold) {
					sum2 = sum2 + i;
					numOfTimes2++;
				}
			}
			if (numOfTimes1 == 0 || numOfTimes2 == 0) {
				numOfTimes1 = 1;
				numOfTimes2 = 1;
			}
			firstEdge = (int) (sum1 / numOfTimes1);
			secondEdge = (int) (sum2 / (numOfTimes2));
			midTapeCoordinate = (int) ((firstEdge + secondEdge) / 2);
			offset = midTapeCoordinate - target;
			System.out.println("First Edge: " + firstEdge + " Second Edge: " + secondEdge + " Error: " + offset);
			visionProcessingTime = timer.get();
			// Calculate offset from line
			return offset;
		} else {
			/**
			 * If there is no camera: cameraIsOpened = false.
			 * Therefore the offset will always be zero.
			 * We do not want any interference with other PID loops.
			 */
			cameraIsOpened = false;
			offset = 0;
			return offset;
		}
	}

	/**
	 * Updates the camera image
	 */
	public void updateCamera() {
		// camera.read(frame);
		// if(timer.get() < cycleTime){
		// return;
		// }
		// timer.reset();

		// Update camera here
	}

	/**
	 * 
	 * @param row
	 */
	public void setScanLine(int scanLineRow) {
		scanRow = scanLineRow;
	}

	/**
	 * 
	 * @param reference
	 */
	public void setThreshold(int contrastThreshold) {
		threshold = contrastThreshold;
	}

	public void setLineReference(int reference) {
		target = reference;
	}

	/**
	 * Updates the dashboard
	 */
	public void updateDashboard() {
		SmartDashboard.putNumber("First Edge", firstEdge);
		SmartDashboard.putNumber("Second Edge", secondEdge);
		SmartDashboard.putNumber("Offset", offset);
		SmartDashboard.putNumber("Vision Proc Time", visionProcessingTime);
	}
}
