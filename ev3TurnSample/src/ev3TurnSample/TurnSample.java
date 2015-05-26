package ev3TurnSample;

import lejos.hardware.motor.Motor;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.hardware.sensor.SensorMode;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;


//ライントレース+障害物を見つけたら一回転してその後避けて進む
public class TurnSample {


	static EV3ColorSensor colorSensor = new EV3ColorSensor(SensorPort.S3);
	static EV3UltrasonicSensor UltraSonicSensor = new EV3UltrasonicSensor(SensorPort.S4);
	static RegulatedMotor MotorR = Motor.A;
	static RegulatedMotor MotorL = Motor.C;
	static float colorRef = 0.15f;

	public static void main(String[] args){

		//モーター初期化、変数定義
		init();
		int RGBcount = 0;
		float[] RGB = new float[3];
		float Distance[] = new float[UltraSonicSensor.sampleSize()];
		boolean turnf = true;

		while(true){

			if(getUltraSonicDistance(Distance)<13){
				setMotor(125,-125);
				Delaysec(1.0);
				//1度目は回転する
				if(turnf){
					turnf=false;
					setMotor(150,-150);
					Delaysec(3.3);
				}
				//左に行けたら左に避ける
				else if(getUltraSonicDistance(Distance)>20){
					setMotor(80,-80);
					Delaysec(0.2);
					setMotor(300,300);
					Delaysec(2.5);
					setMotor(-150,150);
					Delaysec(1.0);
					setMotor(200,250);
					Delaysec(2.5);
					setMotor(200,-200);
					Delaysec(0.5);
					setMotor(80,120);
					do{
						Delaysec(0.1);
						getRGB(RGB);
						RGBcount = 0;
						for(int i = 0;i<3;++i){
							if(RGB[i]>colorRef)++RGBcount;
						}
					}while(RGBcount>=2);
				}
				else{
					setMotor(-125,125);
					Delaysec(2.0);
					//右に行けたら右に避ける
					if(getUltraSonicDistance(Distance)>20){
					setMotor(-80,80);
					Delaysec(0.2);
					setMotor(300,300);
					Delaysec(2.0);
					setMotor(150,-150);
					Delaysec(1.0);
					setMotor(250,200);
					Delaysec(2.5);
					setMotor(-200,200);
					Delaysec(0.5);
					}
					//無理ならまた回転する
					else{
						setMotor(125,-125);
						Delaysec(1.0);
						turnf=true;
					}
				}
			}

			//RGB値を取得する
			getRGB(RGB);
			RGBcount = 0;
			for(int i = 0;i<3;++i){
				if(RGB[i]>colorRef)++RGBcount;
			}

			//色が白だったなら右に曲がる
			//黒だったなら左に曲がる
			if(RGBcount>=2){setMotor(150,80);}
			else{setMotor(80,150);}
			Delaysec(0.1);


		}

	}

//モーターを初期化する関数
static void init(){
MotorR.resetTachoCount();
MotorR.rotateTo(0);
MotorL.resetTachoCount();
MotorL.rotateTo(0);
}

//モーターを動かす関数
//右と左の速度を引数として受けとって、モーターを動かす
static void setMotor(int Right, int Left){

MotorR.setSpeed(Right);
if(Right>0)MotorR.forward();
else if(Right<0)MotorR.backward();
else MotorR.stop();

MotorL.setSpeed(Left);
if(Left>0)MotorL.forward();
else if(Left<0)MotorL.backward();
else MotorL.stop();
}

//RGB値を取得します．配列のパスの順にRed,Green,Blueの値が格納されています．
private static void getRGB(float[] RGB){
	SensorMode  color = colorSensor.getMode(2);
	color.fetchSample(RGB, 0);
}

//超音波センサから物体までの距離を測定し，値を得ます．
private static float getUltraSonicDistance(float[] Distance){
	SensorMode sonic = UltraSonicSensor.getMode(0);
	sonic.fetchSample(Distance, 0);
	return (float) (Distance[0]*100.0);
}

//指定した秒数だけ待機する関数
static void Delaysec(double d){
Delay.msDelay((int)(d*1000));
}


}
