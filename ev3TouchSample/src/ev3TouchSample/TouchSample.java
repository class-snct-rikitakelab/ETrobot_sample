package ev3TouchSample;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.utility.Delay;

public class TouchSample {

	//使用するセンサーを定義する
	//タッチセンサーはS2ポートにつながっているためこのように定義する
	static EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S2);
	static SensorMode touch;

	public static void main(String[] args) {

		//センサーの初期設定
		setTSensor();

		while(true){

			//LCD画面をクリアする
			lcdclear();

			//もしタッチセンサーが押されているかそうでないかを判定
			if(CheckTSensor()==true){
				//もしタッチセンサーが押されていたらLCDにTouchedと表示
				drawLCD("Touched",0,4);
			}else{
				//もしタッチセンサが押されていなかったらLCDにNot Touchedと表示
				drawLCD("Not Touched",0,4);
			}

			//0.5秒間待機
			Delay(500);
		}
	}


////////////////////////////////////////////////////////////////////////
//ここから下は各関数を記しておきます．指示がない限り変更しないでください．

	//タッチセンサの初期設定を行います．タッチセンサを使うときはこれをまず宣言してください．
	public static void setTSensor(){
		touch = touchSensor.getMode(0);
	}

	//タッチセンサが押されているかどうかを判定します．押されていたらtrueを，押されていなかったらfalseを返します．
	public static boolean CheckTSensor(){
		float value[] = new float[touch.sampleSize()];

		touch.fetchSample(value, 0);

		if(value[0]==1){
			return true;
		}else{
			return false;
		}
	}

	//LCDの表示をすべて消します．
	private static void  lcdclear(){
		LCD.refresh();
		LCD.clear();
	}

	//LCDに好きな文章を表示できます．
	//第1の引数はメッセージの文字列，第2の引数はメッセージの1文字目のx座標，第3の引数はy座標です．
	private static void drawLCD(String message,int x,int y){
		LCD.drawString(message, x, y);
	}

	private static void Delay(int msec){
		Delay.msDelay(msec);
	}
}
