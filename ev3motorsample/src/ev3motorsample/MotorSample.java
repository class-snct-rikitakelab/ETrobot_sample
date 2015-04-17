package ev3motorsample;

import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

public class MotorSample {

	static RegulatedMotor MotorR = Motor.B;//ポートBに右のモーターがつながっている
	static RegulatedMotor MotorL = Motor.C;//ポートCに左のモーターがつながっている


	public static void main(String[] args){

		//まずは下のほうで定義されている関数 init で、モーターの初期化を行う
		init();

		//下のほうで定義している関数 setMotor で、モーターを動かす
		//二つの引数は右と左のモーターの速さをそれぞれ指定する
		//Delaysecは指定した秒数だけ待機する関数
		//Delaysecを使ってもマシンは止まらず、setMotorで指定した速度に従ってマシンは動き続ける
		setMotor(100,100);
		Delaysec(3);

		setMotor(-100,-100);
		Delaysec(3);

		setMotor(0,0);
		Delaysec(1);


		//自分で定義した関数を使わずに動かすには次のようにする
		//MotorR.setSpeedで右のモーターの速度を指定して、MotorR.forwardで前に動かす
		//Delay.msDelayは指定した時間だけ待機する関数(単位はミリ秒)
		MotorR.setSpeed(100);
		MotorR.forward();
		Delay.msDelay(1000);

		//MotorR.backwardで後ろに動く
		MotorR.setSpeed(-100);
		MotorR.backward();
		Delay.msDelay(1000);

		//MotorR.stopで止まる
		MotorR.setSpeed(0);
		MotorR.stop();
		Delay.msDelay(1000);


		//MotorRの部分をMotorLにすると左のモーターが動く
		MotorL.setSpeed(100);
		MotorL.forward();
		Delay.msDelay(1000);

		MotorL.setSpeed(-100);
		MotorL.backward();
		Delay.msDelay(1000);

		MotorL.setSpeed(0);
		MotorL.stop();
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

	//指定した秒数だけ待機する関数
	static void Delaysec(double d){
		Delay.msDelay((int)(d*1000));
	}


}
