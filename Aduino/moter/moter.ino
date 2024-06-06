#include <Servo.h>

int R = 2 ;
int G = 3 ;
int B = 4 ;
int Buzzer_Pin = 8;
Servo myServo1;
Servo myServo2;    
int servoPin1 = 6;
int servoPin2 = 7;
int motorValue;

void setup()
{
  Serial.begin(19200);
  pinMode(R, OUTPUT);
  pinMode(G, OUTPUT);
  pinMode(B, OUTPUT);
  myServo1.attach(servoPin1);
  myServo2.attach(servoPin2);
  myServo1.write(0);
  myServo2.write(0);

  
}
void loop()
{
  analogWrite(R, 0);
  analogWrite(G, 0);
  analogWrite(B, 0);

  if (Serial.available() > 0) {  // 시리얼 데이터를 사용할 수 있는지 확인
    motorValue = Serial.read();  // 시리얼 데이터를 읽어옴

  if (motorValue == 49 ) {  // 받은 값이 '1'이면
      analogWrite(R, 0);
      analogWrite(G, 0);
      analogWrite(B, 255);
      tone(Buzzer_Pin, 330);
      delay(100);
      tone(Buzzer_Pin, 250);
      delay(100);
      tone(Buzzer_Pin, 700);

      delay(100);
      noTone(Buzzer_Pin);     
      myServo1.write(180);
      myServo2.write(180);
      delay(2000);
      
      
      myServo1.write(0);
      myServo2.write(0);
           
    } else {
      myServo1.write(0);
      myServo2.write(0);
      analogWrite(R, 255);
      analogWrite(G, 0);
      analogWrite(B, 0);
      tone(Buzzer_Pin, 294);
      delay(100);
      noTone(Buzzer_Pin);

      delay(2000);


    }
  }

}


