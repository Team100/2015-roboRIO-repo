JoyState_t joySt;

void setup()
{
        pinMode(3, INPUT_PULLUP);
        pinMode(5, INPUT_PULLUP);
        pinMode(6, INPUT_PULLUP);
        pinMode(9, INPUT_PULLUP);
        joySt.buttons = 0;
}

void loop()
{    
  int p3 = 1 - digitalRead(3);
  int p5 = 1 - digitalRead(5);
  int p6 = 1 - digitalRead(6);
  int p9 = 1 - digitalRead(9);
  
  int total = (1*p3) + (2*p5) + (4 *p6) + (8 * p9);
  
//        if (digitalRead(3) == LOW){
//            joySt.buttons = 2;
//        }
//        else{
//          joySt.buttons = 1;
//        }

       
        joySt.buttons = total;
        
	delay(10);

	// Call Joystick.move
	Joystick.setState(&joySt);
}
