package ev3branchturntest;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

/* 反時計回りにトレースする場合のみ正常動作 */

public class ev3branchturntest {
	static EV3ColorSensor Sensor = new EV3ColorSensor(SensorPort.S3);
	static RegulatedMotor rmotor = Motor.B;
	static RegulatedMotor lmotor = Motor.C;
	static SensorMode color = Sensor.getColorIDMode();
	static SensorMode bright = Sensor.getRedMode();
	static Boolean branch = false;	// 分岐路にいるかどうかのフラグ
	public static void main(String[] args){
		int colorID;
		int branchcolor = 7;	// 分岐後にトレースするcolorID
		float bright=0;
		int turnf = 151;
		int turni = 0;


		// モーターの初期設定
		motor_init();

		//以下の処理を無条件に繰り返す
		while(true){
			// LCDの表示をクリアする
			lcd_clear();

			// colorIDを取得する
			colorID = getcolorID();

			// 輝度値を取得する
			bright = getbright();

			// LCDにcolorIDを表示する
			LCD.drawInt(colorID,0,0);

			// 灰色の誤検知を防ぐために灰色の回数をカウント
			if(bright<0.1f)turni = 0;
			else if((bright>0.16f)&&(bright<0.23f))++turni;
			LCD.drawString(turnf +"   "+ turni , 1, 5);

			// 回転する条件を満たした時に期間をあけて行う
			if((++turnf>150)&&(branch==false)&&(5<=turni)){
				// 回転フラグを消して一回転する
				turnf=turni=0;
				motor(-150,150);
				msdelay(4000);
			}
			// 分岐路にいない場合
			else if(branch == false){
				// 分岐路にいないことを表示する。
				LCD.drawString("branch:false", 0, 1);

				if(colorID == 7){
					// 黒を読み込んだときは左にカーブする
					motor(180,80);
					msdelay(50);
				}
				else if(colorID == 6) {
					// 白を読み込んだときは右にカーブする
					motor(80,180);
					msdelay(50);
				}
				else {
					// 分岐を検知したときは
					// 分岐路のフラグを立ててトレースする色を決定する
					branch = true;
					branchcolor = colorID;

					// 右に曲がる
					motor(150,30);
					msdelay(200);
				}
			}
			// 分岐をにいる場合
			else if(branch == true){
				// 分岐路にいることを表示する
				LCD.drawString("branch:ture", 0, 1);

				if(colorID == 7){
					// 黒を読み込んだときは
					// 分岐路のフラグを折ってトレースする色を決定する
					branch = false;
					branchcolor = colorID;

					//右に曲がる
					motor(150,30);
					msdelay(50);
				}
				else if(colorID == branchcolor){
					// トレースしていた色(分岐路の色)を読み込んだときは左にカーブ
					motor(200,60);
					msdelay(100);
				}
				else {
					// そのほかの色(白や灰)を読み込んだときは右にカーブ
					motor(80,150);
					msdelay(30);
				}
			}
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
		float border = (float) 0.3; 	// 分岐路でないときの黒とする輝度(灰を黒に含む)

		if(branch){
			// 分岐路にいるときは黒とする輝度を下げる(灰をはじくため)
			border = (float)0.15;
		}
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
