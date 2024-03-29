package ev3USWSensorSample;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.utility.Delay;

public class UswSensorsample {
	static EV3UltrasonicSensor UltraSonicSensor = new EV3UltrasonicSensor(SensorPort.S4);

	public static void main(String[] args){
		float cm;		// 	超音波センサーからの距離(cm)

		// 以下の処理を無限に繰り返す
		while(true){
			// lcdの表示を消す
			lcdclear();

			// 超音波センサーからの距離を取得する
			cm = getUltraSonicDistance();

			// lcdのある列、ある行に文字列を表示する
			// 引数は表示する文字列（数値）、列数、行数
			drawString(cm,1,1);

			// 引数に指定した時間(ミリ秒単位)処理を待機する
			Delay(10);
		}
		// 繰り返しここまで
	}

////////////////////////////////////////////////////////////////////////
//ここから下は各関数を記しておきます．指示がない限り変更しないでください．

	//LCD上に表示されている文字をすべてクリアします．
	private static void  lcdclear(){
		LCD.refresh();
		LCD.clear();
	}

	//超音波センサから物体までの距離を測定し，値を得ます．
	private static float getUltraSonicDistance(){
		float Distance[] = new float[UltraSonicSensor.sampleSize()];
		UltraSonicSensor.getDistanceMode();
		UltraSonicSensor.fetchSample(Distance, 0);
		return (float) (Distance[0]*100.0);
	}

	//float型の値を表示できます．
	private static void drawString(float cm,int x,int y){
		LCD.drawString(String.format("%.1f"+"cm", cm), x, y);
	}

	//指定した時間(ミリ秒単位)処理を待機します．
	private static void Delay(int msec){
		Delay.msDelay(msec);
	}

}
