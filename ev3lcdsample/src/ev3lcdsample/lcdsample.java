package ev3lcdsample;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class lcdsample {

	public static void main(String[] args){;
		int 	sec	=	0;		//	カウントする秒数
		int 	msec=	1000;	//	待機する時間(単位はミリ秒)

		//	以下の処理を無限に繰り返す
		while(true){
			//	LCDの表示を消す
			lcdClear();

			//	数値をLCDのある行のある列に表示する
			//	引数は	数値、行数、列数
			drawInt(sec,0,0);

			//	文字列をLCDのある行のある列に表示する
			//	引数は	文字列、行数、列数
			drawStr("seconds",4,0);

			//	指定した時間だけ待機する
			//	引数は	ミリ秒
			Delay(msec);

			sec++;
		}
		//	繰り返しここまで
	}

	//	ここから下は変更しないでください
	private static void lcdClear(){
		LCD.clear();
	}
	private static void drawInt(int n, int x, int y){
		LCD.drawInt(n, x, y);
	}
	private static void drawStr(String str, int x, int y){
		LCD.drawString(str, x, y);
	}
	private static void Delay(int msec){
		Delay.msDelay(msec);
	}
}
