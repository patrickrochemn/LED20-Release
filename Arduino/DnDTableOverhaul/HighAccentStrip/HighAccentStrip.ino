#include <FastLED.h>
#define NUM_LEDS 300
#define DATA_PIN 2
#define VOLTS          5
#define MAX_MA       500
#define BRIGHTNESS  255
#define LED_TYPE   WS2812B
#define COLOR_ORDER   GRB
#define FASTLED_ALLOW_INTERRUPTS 0
#define AUTO_SELECT_BACKGROUND_COLOR 0
#define COOL_LIKE_INCANDESCENT 1
CRGBArray<NUM_LEDS> leds; 
CRGB gBackgroundColor = CRGB::Black;
CRGBPalette16 gCurrentPalette;
CRGBPalette16 gTargetPalette;

// effect indicators
char effect = 'F';
char prevEffect = 'F';

// utility stuffs
boolean serialFlag; //new input from GUI arrived
boolean flashbangOff = true;
int offset = 0; // offset to go led by led synchronously

bool upsideDown = false;

void setup(){
  delay( 3000 ); //safety startup delay
  FastLED.setMaxPowerInVoltsAndMilliamps( VOLTS, MAX_MA);
  FastLED.addLeds<LED_TYPE,DATA_PIN,COLOR_ORDER>(leds, NUM_LEDS).setCorrection(TypicalPixelString);
  FastLED.setBrightness(  BRIGHTNESS );
  delay(10);
}

void loop(){
  if(Serial.available()){
    serialFlag = true;
    getDataString();
    delay(10);
  }
  else{
    serialFlag = false;
  }

    switch (effect) {
      case 'd':
        day();
        break;
      case 'F':
        fire();
        break;
      case 'n':
        night();
        break;
      case 'w':
        water();
        break;
      case 'f':
        flashbang();
        break;
      case 'c':
        candleLight();
        break;
      case 'u':
        sexyTime();
        break;
      case 'h':
        cherry();
        break;
      case 't':
        forest();
        break;
      case 'U':
        upsideDown = false; // thunder is also used for upsideDown
        thunder(8);
        break;
      case 'm':
        morn();
        break;
      case 'S':
        summer();
        break;
      case 's':
        snow();
        break;
      case 'A': // fall/autumn
        fall();
        break;
      case 'e':
        eve();
        break;
      case 'b':
        cyber();
        break;
      case 'r':
        rain();
        break;
      case 'V':
        divine();
        break;
      case 'W':
        winter();
        break;
      case 'T':
        typhoon();
        break;
      case '?':
        upsideDown = true;
        thunder(8); // thunder is base function for upsideDown
        break;
      case 'R':
        ruby();
        break;
      case '1':
        Alden();
        break;
      case '2':
        Nadine();
        break;
      default:
        break;
    }
}

void getDataString(){
  String inString = "";
  char incoming;
  while (Serial.available()) {
    delay(1);
    if (Serial.available() > 0){
      incoming = Serial.read();
      inString += incoming;
      if (incoming == '#'){
        // reached end of data string, clear serial buffer
        while (Serial.available()){
          Serial.read();
        }
        break;
      }
    }
  }
  
  // INSTRING HAS THE INCOMING STRING
  parseDataString(inString);
}

void parseDataString(String toBeParsed){
  // EXAMPLE DATA STRING: SR0G0B1,R20G0B255,R247G85B0,R255G0B0,R0G0B1,R224G9B192,R130G70B0,R0G150B0,*Ct*Ea*P100.0,100.0,100.0,100.0,100.0,100.0,100.0,100.0,*D00,00,00,00,00,00,00,00,*#
  int startIndex = 0;

  // effect
  prevEffect = effect;
  effect = toBeParsed[toBeParsed.indexOf('E')+1];
  if (effect == 'f'){
    flashbangOff = !flashbangOff;
  } else{
    flashbangOff = true;
  }
  
  // don't want to fade for Nadine's Gift
  if(effect == '2') {
    Nadine();
    effect = prevEffect;
  } else {
    fade();
    // check if Alden's effect
    if (effect == '1') {
      AldenStart();
    }
  }
}
