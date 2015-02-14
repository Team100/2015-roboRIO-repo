package org.usfirst.frc100.SlideWinder.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class CameraVision extends Subsystem {
	
	private double cycleTime = 0; // Default value
	
	Timer timer = new Timer();
	VideoCapture camera;
	Mat frame = new Mat();
	Mat greyFrame = new Mat();
	Mat blurFrame = new Mat();
	Mat roiFrame = new Mat();
	Mat scharrFrame = new Mat(3, 320, CvType.CV_16S);
	Mat endFrame = new Mat();
	int scanRow = 80; 
	int threshold = 1000;
	int target = 160;
	/**
	 * Called once when robot turns on
	 */
	public CameraVision() {
		SmartDashboard.putNumber("CycleTime", cycleTime);
		System.load("/usr/local/lib/lib_OpenCV/java/libopencv_java2410.so");
		VideoCapture camera = new VideoCapture("http://10.1.0.11/axis-cgi/mjpg/video.cgi?dummy=video.mjpg");
		// Initialization code here
	}
	
	@Override
	protected void initDefaultCommand() {		
	}

	/**
	 * Called once in auto init
	 */
	public void initCamera() {
		timer.start();
		cycleTime = SmartDashboard.getNumber("CyleTime");
		
		// More init code here
	}

	/**
	 * Gets the distance in pixels from the line
	 * @return The offset in pixels
	 */
	public int getLineOffset() {
		Imgproc.cvtColor(frame, greyFrame, Imgproc.COLOR_BGR2GRAY);
		Imgproc.blur(greyFrame, blurFrame, new Size(3, 3));
		roiFrame = blurFrame.rowRange(scanRow , scanRow + 3);
		Imgproc.Sobel(roiFrame, scharrFrame, CvType.CV_16S, 1, 0, Imgproc.CV_SCHARR, 1, Imgproc.BORDER_DEFAULT);
		endFrame = scharrFrame.rowRange(2, 3);
		short[] buff = new short[(int) (endFrame.total()) * endFrame.channels()];
		endFrame.get(0, 0, buff);
		int sum1 = 0;
		int sum2 = 0;
		int numOfTimes1 = 0;
		int numOfTimes2 = 0;
		for (int i = 0; i <= (buff.length - 1); i++) {
			if (buff[i] >= 1800) {
				sum1 = sum1 + i;
				numOfTimes1++;
			}
			if (buff[i] <= -1800) {
				sum2 = sum2 + i;
				numOfTimes2++;
			}
		}
		if (numOfTimes1 == 0 || numOfTimes2 == 0) {
			numOfTimes1 = 1;
			numOfTimes2 = 1;
		}
		int firstEdge = (int) (sum1 / numOfTimes1);
		int secondEdge = (int) (sum2 / (numOfTimes2));
		int midTapeCoordinate = (int) ((firstEdge + secondEdge)/2);
		int error = midTapeCoordinate - target;
		System.out.println("First Edge: " + firstEdge + " Second Edge: " + secondEdge + " Error: " + error);
		// Calculate offset from line
		return error;
	}
	
	/**
	 * Updates the camera image
	 */
	public void updateCamera() {
		camera.read(frame);
		if(timer.get() < cycleTime){
			return;
		}
		timer.reset();
		
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
	public void setThreshold(int contrastThreshold){
		threshold = contrastThreshold;
	}
	
	public void setLineReference(int reference) {
		target = reference;
	}
	
	/**
	 * Updates the dashboard
	 */
	public void updateDashboard() {
//		SmartDashboard.putNumber("Key", value);
	}
}
