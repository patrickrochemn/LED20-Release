#include <FastLED.h>

#define NUM_LEDS 600
#define DATA_PIN 7
#define VOLTS          5
#define MAX_MA       500 
#define BRIGHTNESS  255
#define LED_TYPE   WS2812B
#define COLOR_ORDER   GRB
#define AUTO_SELECT_BACKGROUND_COLOR 0

#define COOL_LIKE_INCANDESCENT 1

CRGBArray<NUM_LEDS> leds; 
CRGB gBackgroundColor = CRGB::Black;
CRGBPalette16 gCurrentPalette;

bool monochrome = false;
bool upsideDown = false;

uint8_t breath = 0;
boolean breatheIn = true;

// effect indicators
char effect = 'w';

byte brightness = 0;
byte fadeAmount = 5;

char prevEffect = 'X';
boolean combatMode = false;
boolean wasInCombatMode = false;

boolean healthChanged = false;
boolean deathSavesChanged = false;

// utility stuffs
byte numPlayers = 7;  // default value
int separatorIndices[16] = {0, 8, 9, 46, 47, 81, 82, 119, 120, 174, 175, 212, 213, 248, 249, 286};  // default value
boolean serialFlag; //new input from GUI arrived
boolean flashbangOff = true;
boolean needsUpdate = false;
boolean doneIterating = false;  //determines when synchronous damage/healing ends

void setup(){
  FastLED.delay( 2000 ); //safety startup delay - probably should be set to 3000 but Patrick is impatient
  FastLED.setMaxPowerInVoltsAndMilliamps( VOLTS, MAX_MA);
  FastLED.addLeds<LED_TYPE,DATA_PIN,COLOR_ORDER>(leds, NUM_LEDS).setCorrection(TypicalLEDStrip).setTemperature(OvercastSky);
  FastLED.setMaxRefreshRate(100);
  FastLED.setBrightness(  BRIGHTNESS );
  Serial.begin(9600); // opens serial port, sets data rate to 9600 bps
//  Serial.begin(115200); // opens serial port, sets data rate to 115200 bps (FOR ARTEMIS BOARDS)
  FastLED.delay(10);
  
  // initialize health bar start and end indices
}

void loop(){
  if(Serial.available()) {
    serialFlag = true;
    getDataString();
//    FastLED.delay(10);
  } else {
    serialFlag = false;
    if (!combatMode) {
      if (wasInCombatMode) {
      }
      wasInCombatMode = false;
      if(effect == 'w') water();
      else {
        doEffect();
      }
    } else {
    }
  }
//  FastLED.delay(10);
}

void doEffect() {
  switch (effect) {
      case '#':
        break;
      case '$':
        // clear the LEDs
        leds = 0;
        FastLED.show();
        FastLED.show();
        effect = '#'; // hold until given a new effect
        break;
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
      case 'X':
        Off();
      default:
        break;
    }
}

void getDataString(){
  String inString;
  inString.reserve(200);
  inString = "";
  char incoming;
//  while (Serial.available()) {
//    FastLED.delay(1);
//    if (Serial.available() > 0){
//      incoming = Serial.read();
//      inString += incoming;
//      if ((char)incoming == '#'){
//        // reached end of data string, clear serial buffer
////        while (Serial.available()){
////          Serial.read();
////        }
//          while(Serial.read() >= 0);
//        break;
//      }
//    }
//  }
  Serial.setTimeout(10);
  inString = Serial.readString();

  // INSTRING HAS THE INCOMING STRING
  parseDataString(inString);
}

void parseDataString(String toBeParsed){
  // EXAMPLE DATA STRING: S*Ct*Ea*P100,100,100,100,100,100,100,100,*D00,00,00,00,00,00,00,00,*#
  // NEED TO ADD SEPARATOR LED INDICES TO DATA STRING
  // NEW EXAMPLE DATA STRING: S*Ct*Ea*P100,100,100,100,100,100,100,100,*D00,00,00,00,00,00,00,00,*I0,1,2,3,4,5,6,7*c1,2,3,4,5,6,1e,9*#
  int startIndex = 0;

  // combat mode
  if (toBeParsed[toBeParsed.indexOf('C')+1] == 't')
    combatMode = true;
  else
    combatMode = false;

  // effect
  prevEffect = effect;
  effect = toBeParsed[toBeParsed.indexOf('E')+1];
  if (effect == 'f'){
    flashbangOff = !flashbangOff;
  }
  else{
    flashbangOff = true;
  }

  // number of active players
  numPlayers = (int)toBeParsed.substring(toBeParsed.indexOf('N')+1, toBeParsed.indexOf('N')+2).toFloat();

  // HP percentages
  startIndex = toBeParsed.indexOf('P')+1;
  
  // death saving throws
  // PARSING HERE IS MEGACURSED BUT IT WORKS. MIGHT CHANGE LATER (PROBABLY NOT)
  startIndex = toBeParsed.indexOf('D')+1;
 
}
