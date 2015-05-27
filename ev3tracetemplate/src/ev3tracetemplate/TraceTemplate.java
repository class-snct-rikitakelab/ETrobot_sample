package ev3tracetemplate;
////////////////////////////////////////////////
// ライントレースに使えそうな関数のみ記述した //
// mainの内容を書き込む						  //
// getcolorIDについては工夫が必要
////////////////////////////////////////////////


import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class TraceTemplate {

	static EV3ColorSensor Sensor = new EV3ColorSensor(SensorPort.S3);
	static RegulatedMotor rmotor = Motor.B;
	static RegulatedMotor lmotor = Motor.C;
	static SensorMode color = Sensor.getColorIDMode();
	static SensorMode bright = Sensor.getRedMode();

	public static void main(String[] args) {

		while(true){

		}

	}

	// モータの初期設定
	private static void motor_init(){
		rmotor.resetTachoCount();
		lmotor.resetTachoCount();
		rmotor.rotate(0);
		lmotor.rotate(0);
	}

		// LCDの表示をクリアする
	private static void lcd_clear(){
		LCD.refresh();
		LCD.clear();
	}

	// colorIDを取得する
	private static int getcolorID(){
		int colorID;
		float value[] = new float[color.sampleSize()];
		float value2[] = new float[bright.sampleSize()];
		float border = (float) 0.3; 	// 黒とする輝度(灰を黒に含む)

		// センサより生のcolorIDと輝度を取得
		color.fetchSample(value,0);
		bright.fetchSample(value2, 0);

		// 戻り値を決定する
		if(value2[0] < border){
			// 黒とする輝度よりも低いときは黒
			colorID = 7;
		}
		else{
			// 黒でない場合は取得したcolorID
			colorID = (int) value[0];
		}
		// 輝度とcolorIDをLCDに表示する
		LCD.drawString("bright:"+value2[0],1,2);
		LCD.drawString("colorID:"+colorID, 1, 3);
		return colorID;
	}

	// 輝度値を取得する
	private static float getbright(){
		float value[] = new float[bright.sampleSize()];

		// センサより輝度を取得
		bright.fetchSample(value,0);

		return value[0];
	}

	// モータを制御する関数
	private static void motor(int rpower,int lpower){
		// モータの強さを設定する
		rmotor.setSpeed(rpower);
		lmotor.setSpeed(lpower);

		// 左モータに渡した値が正なら正方向、負なら負方向、0なら停止
		if(lpower >0){
			lmotor.forward();
		} else if(lpower<0){
			lmotor.backward();
		} else {
			lmotor.stop();
		}

		// 右モータに渡した値が正なら正方向、負なら負方向、0なら停止
		if(rpower >0){
			rmotor.forward();
		} else if(rpower<0){
			rmotor.backward();
		} else {
			rmotor.stop();
		}
	}

	// 処理を待機する関数,引数の単位はミリ秒
	private static void msdelay(int msec){
		Delay.msDelay(msec);
	}
}
