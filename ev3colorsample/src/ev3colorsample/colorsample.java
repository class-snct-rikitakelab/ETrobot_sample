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

			// RGBとcolorIDをLCDに表示
			drawResult(RGB,colorID);

			// 指定した時間(ミリ秒単位)処理を待機する
			Delay(10);
		}
	}

////////////////////////////////////////////////////////////////////////
//ここから下は各関数を記しておきます．指示がない限り変更しないでください．

	//LCDに表示されている文章をすべてクリアします．
	private static void  lcdclear(){
		LCD.refresh();
		LCD.clear();
	}

	//RGB値を取得します．配列のパスの順にRed,Green,Blueの値が格納されています．
	private static void getRGB(float[] RGB){
		SensorMode  color = colorSensor.getMode(2);
		color.fetchSample(RGB, 0);
	}

	//自動的にRGB値を取得しそれが何色なのか判定します．
	//色の種類はIDで表現され，IDを整数型で返します．
	//例：黒の場合のIDは7，赤なら0
	private static int getColorID(){
		float[] value = new float[1];
		SensorMode  color = colorSensor.getMode(0);
		color.fetchSample(value, 0);
		return (int)value[0];
	}

	//得られたRGB値とcolorIDをLCD上に表示します．
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

	//指定した時間(ミリ秒単位)処理を待機する
	private static void Delay(int msec){
		Delay.msDelay(msec);
	}

}
