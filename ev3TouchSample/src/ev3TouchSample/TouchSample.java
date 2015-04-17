package ev3TouchSample;

import lejos.hardware.lcd.LCD;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorMode;

public class TouchSample {

	//使用するセンサーを定義する
	//タッチセンサーはS2ポートにつながっているためこのように定義する
	static EV3TouchSensor touchSensor = new EV3TouchSensor(SensorPort.S2);

	public static void main(String[] args) {
		//センサーモードを設定する
		SensorMode touch = touchSensor.getMode(0);

		//センサーが取得する値を格納する配列を用意する
		float value[] = new float[touch.sampleSize()];

		//LCDを初期化する
		LCD.clear();

		while(true){
			//センサーが取得する値を配列に格納する
			touch.fetchSample(value, 0);

			//もしセンサーが押されていたらLCDに表示する
			if(value[0]==1){LCD.drawString("Touch", 1, 1);}
			//センサーが押されていないならLCD表示を消す
			else{LCD.clearDisplay();}

			LCD.asyncRefresh();
		}
	}

}
