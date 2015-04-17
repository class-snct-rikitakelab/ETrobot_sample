package ev3colorsample;
import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;

public class colorsample {
	static EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
	public static void main(String[] args){
		float[] RGB 	= new float[3];
		int colorID	= 0;
		while(true){
			// lcdの表示をクリアする
			lcdclear();
			
			// 引数の配列にRGBを取得する
			getRGB(RGB);
			
			// colorIDを取得する
			colorID = getColorID();
			
			// RGBとcolorIDを取得する
			drawResult(RGB,colorID);
			
			// 指定した時間(ミリ秒単位)処理を待機する
			Delay(10);
		}
	}
	private static void  lcdclear(){
		LCD.refresh();
		LCD.clear();
	}

	private static void getRGB(float[] RGB){
		SensorMode  color = colorSensor.getMode(2);
		color.fetchSample(RGB, 0);
	}
	private static int getColorID(){
		float[] value = new float[1];
		SensorMode  color = colorSensor.getMode(0);
		color.fetchSample(value, 0);
		return (int)value[0];
	}
	private static void drawResult(float[] RGB,int colorID){
		LCD.drawString("R:"+RGB[0], 0, 0);
		LCD.drawString("G:"+RGB[1], 0, 1);
		LCD.drawString("B:"+RGB[2], 0, 2);
		switch(colorID){
		case 0:		LCD.drawString("Color:Red", 0, 4);
					break;
		case 1:		LCD.drawString("Color:Green", 0, 4);
					break;
		case 2:		LCD.drawString("Color:Blue", 0, 4);
					break;
		case 3: 	LCD.drawString("Color:Yellow", 0, 4);
					break;
		case 6: 	LCD.drawString("Color:White", 0, 4);
					break;
		case 7: 	LCD.drawString("Color:Black", 0, 4);
					break;
		default :	LCD.drawString("Color:Unkown", 0, 4);
					break;
		}

	}
	private static void Delay(int msec){
		Delay.msDelay(msec);
	}

}
