// NEW EFFECT METHODS --------------------------------------------------

void fade() {
//  for(byte i = 0; i < 200; i++) {
//    leds--;
//    FastLED.show();
//    FastLED.delay(10);
//    if(leds == 0) break;
//  }
  leds.fadeToBlackBy(100);
  FastLED.show();
}

void Off() {
  leds = CRGB(0,0,0);
  FastLED.show();
}

// ALDEN START --------------------------------------------------------------
void AldenStart() {
  // OBJECTIVE SHADE: CRGB(52,36,80)
//  leds = CHSV(185,48,33);
  for(byte i = 0; i < 80; i++) {
    if(i < 36) {
      leds = CRGB(i,i,i);
    } else if (i < 52) {
      leds = CRGB(i,36,i);
    } else {
      leds = CRGB(52,36,i);
    }
    FastLED.show();
    FastLED.delay(20);
  }
}

// ALDEN --------------------------------------------------------------
void Alden() {
//  byte randomBrightness = random(20,50);
//  byte upOrDown = random(0,2);
//
//  byte initial = leds[100].r;
//  if(upOrDown == 0) {
//    // up
//    for(byte j = 0; j < randomBrightness; j++) {
//      if(initial + j >= 100) break;
//      leds = CRGB(initial + j, 0, initial + j);
//      FastLED.show();
//    }
//  } else {
//    // down
//    for(byte j = 0; j < randomBrightness; j++) {
//      if(initial - j <= 0) break; 
//      leds = CRGB(initial - j, 0, initial - j);
//      FastLED.show();
//    }
//  }
//
//  FastLED.show();

  uint16_t wave1 = beatsin16(30,17,33,0);
  uint8_t wave2 = beatsin8(70,0,11,0);
  uint8_t wave3 = beatsin8(80,0,11,0);
//  byte wave1 = random(0,25);
//  byte wave2 = random(0,25);
//  byte wave3 = random(0,25);
  
  int sum = wave1 + wave2 + wave3;
  sum = wave1;

  leds = CHSV(186,48,sum);

//  for(int i = 0; i < NUM_LEDS; i++) {
//    leds[i].fadeLightBy(brightness);
//  }
//
////  leds = CRGB(sum, 0, sum);
//  FastLED.show();
//
//  brightness = brightness + fadeAmount;
//  if(brightness == 0 || brightness == 255) {
//    fadeAmount = -1 * fadeAmount;
//  }
  
  FastLED.delay(5);
}

void Nadine() {

  // set Nadine colors
  for(int i = 0; i < NUM_LEDS; i++) {
    if((i % 3 == 0) && (i % 6 != 0)) {
      leds[i] = CRGB::White;
      leds[i + 1] = CRGB::White;
      leds[i + 2] = CRGB::Black;
    } else if(i % 6 == 0) {
      leds[i] = CRGB::Gold;
      leds[i + 1] = CRGB::Gold;
      leds[i + 2] = CRGB::Black;
    }
  }
  FastLED.show();

  // swirling Nadine colors
  int starttime = millis();
  int endtime = starttime;
  while((endtime - starttime) <= 1750) {
    int lastR = leds[0].r;
    int lastG = leds[0].g;
    int lastB = leds[0].b;
    for(int i = 0; i < NUM_LEDS - 1; i++) {
      leds[i].r = leds[i+1].r;
      leds[i].g = leds[i+1].g;
      leds[i].b = leds[i+1].b;
    }
    leds[NUM_LEDS - 1].r = lastR;
    leds[NUM_LEDS - 1].g = lastG;
    leds[NUM_LEDS - 1].b = lastB;
    FastLED.show();
    FastLED.delay(3);
    endtime = millis();
  }

  // set Gift Colors
  for(int i = 0; i < NUM_LEDS; i++) {
    if((i % 3 == 0) && (i % 6 != 0)) {
      leds[i] = CRGB::Black;
      leds[i + 1] = CRGB::Black;
      leds[i + 2] = CRGB::Black;
    } else if(i % 6 == 0) {
      leds[i] = CRGB::Red;
      leds[i + 1] = CRGB::Red;
      leds[i + 2] = CRGB::Red;
    }
  }
  FastLED.show();

  // swirling Gift colors
  starttime = millis();
  endtime = starttime;
  while((endtime - starttime) <= 1750) {
    int firstR = leds[NUM_LEDS - 1].r;
    int firstG = leds[NUM_LEDS - 1].g;
    int firstB = leds[NUM_LEDS - 1].b;
    for(int i = NUM_LEDS - 1; i >= 0; i--) {
      leds[i].r = leds[i-1].r;
      leds[i].g = leds[i-1].g;
      leds[i].b = leds[i-1].b;
    }
    leds[0].r = firstR;
    leds[0].g = firstG;
    leds[0].b = firstB;
    FastLED.show();
    FastLED.delay(3);
    endtime = millis();
  }
  
}

// RUBY --------------------------------------------------------------
void ruby() {
  uint8_t breath = beatsin8(15,6,30);
  leds = CRGB(breath, 0, 0);
//  leds = CHSV(262,17,57);
}

// SUMMER --------------------------------------------------------------
const TProgmemRGBPalette16 Summer_p FL_PROGMEM = 
{  CRGB::White, CRGB::Yellow, CRGB::Orange, CRGB::White, 
   CRGB::White, CRGB::Yellow, CRGB::Orange, CRGB::Yellow,
   CRGB::White, CRGB::Yellow, CRGB::Orange, CRGB::Yellow, 
   CRGB::White, CRGB::Yellow, CRGB::Orange, CRGB::White};
void summer() {
  gCurrentPalette = Summer_p;
  fireNightRain(3,8);
}

// SNOW --------------------------------------------------------------
const TProgmemRGBPalette16 Snow_p FL_PROGMEM = 
{  CRGB::White, CRGB::White, CRGB::White, CRGB::White, 
   CRGB::White, CRGB::White, CRGB::White, CRGB::White,
   CRGB::White, CRGB::White, CRGB::White, CRGB::White, 
   CRGB::White, CRGB::White, CRGB::White, CRGB::White};
void snow() {
  gCurrentPalette = Snow_p;
  fireNightRain(5,2);
}

// FALL --------------------------------------------------------------
const TProgmemRGBPalette16 Fall_p FL_PROGMEM = 
{  CRGB::Green, CRGB::Green, CRGB::Brown, CRGB::Orange, 
   CRGB::Yellow, CRGB::Red, CRGB::Orange, CRGB::Brown,
   CRGB::Yellow, CRGB::Red, CRGB::Green, CRGB::Orange, 
   CRGB::Brown, CRGB::Yellow, CRGB::Green, CRGB::Brown};
void fall() {
  gCurrentPalette = Fall_p;
  fireNightRain(3,6);
}

// CYBER --------------------------------------------------------------
const TProgmemRGBPalette16 Cyber_p FL_PROGMEM = 
{  CRGB::Blue, CRGB::Orange, CRGB::Blue, CRGB::Orange, 
   CRGB::Orange, CRGB::Black, CRGB::Yellow, CRGB::Blue, 
   CRGB::Yellow, CRGB::MediumBlue, CRGB::Yellow, CRGB::MediumBlue, 
   CRGB::Yellow, CRGB::MediumBlue, CRGB::MediumBlue, 0xffffaa };
void cyber() {
  gCurrentPalette = Cyber_p;
  fireNightRain(8,8);
}

// DIVINE --------------------------------------------------------------
const TProgmemRGBPalette16 Divine_p FL_PROGMEM = 
{  CRGB::Gold, CRGB::Goldenrod, CRGB::White, CRGB::Yellow, 
   CRGB::Gold, CRGB::Goldenrod, CRGB::White, CRGB::Gold,
   CRGB::Gold, CRGB::Goldenrod, CRGB::White, CRGB::Yellow, 
   CRGB::Gold, CRGB::Goldenrod, CRGB::White, CRGB::Gold};
void divine() {
  gCurrentPalette = Divine_p;
  fireNightRain(5,8);
}

// WINTER --------------------------------------------------------------
const TProgmemRGBPalette16 Winter_p FL_PROGMEM = 
{  CRGB::White, CRGB::White, CRGB::White, CRGB::White, 
   CRGB::White, CRGB::White, CRGB::White, CRGB::White,
   CRGB::White, CRGB::White, CRGB::White, CRGB::White, 
   CRGB::White, CRGB::White, CRGB::White, CRGB::White};
void winter() {
  gCurrentPalette = Winter_p;
  fireNightRain(3,2);
}

// TYPHOON --------------------------------------------------------------
const TProgmemRGBPalette16 Typhoon_p FL_PROGMEM = 
{  CRGB::Blue, CRGB::MediumBlue, CRGB::Grey, CRGB::White, 
   CRGB::Blue, CRGB::MediumBlue, CRGB::Grey, CRGB::White,
   CRGB::Blue, CRGB::MediumBlue, CRGB::Grey, CRGB::White, 
   CRGB::Blue, CRGB::MediumBlue, CRGB::Grey, CRGB::White};
void typhoon() {
  gCurrentPalette = Typhoon_p;
  fireNightRain(8,8);
}

// FOREST --------------------------------------------------------------
const TProgmemRGBPalette16 Forest_p FL_PROGMEM =
{ 0x00F705, 0x00F904, 0x00FB03, 0x00FD03, 0x00F002, 0x00F202, 0x00F401, 0x00F701, 
      0x00F900, 0x00FC00, 0x00F600, 0x00F100, 0x00FB00, 0x00F600, 0x1FF21F, 0x1FFE1F};
void forest() {
  gCurrentPalette = Forest_p;
  fireNightRain(4,7);
  //forest_loop(); // IT WILL TRY TO END YOUR BLOODLINE IF YOU DO IT WRONG
  //FastLED.show(); //
}

// CHERRY BLOSSOM
const TProgmemRGBPalette16 Cherry_p FL_PROGMEM =
{  0xFFCCCC,  0xFFCCCC,  0xFFCCCC,  0xFFCCCC, 
   0xFF0099,  0xFF0099,  0xFF0099,  0xFF0099,
   0xFFCCCC,  0xFFCCCC,  0xFFCCCC,  0xFFCCCC, 
   0xFF0099,  0xFF0099,  0xFF0099,  0xFF0099 };
void cherry() {
  gCurrentPalette = Cherry_p;
  fireNightRain(3,7);
}
// DAY -----------------------------------------------------------------
const TProgmemRGBPalette16 Day_p FL_PROGMEM =
{  0xffff28,  0xffff28,  0xffff28,  0xffff28, 
   0xffff28, 0xffff28, 0xffff28, 0xffff28,
   0xffff28,  0xffff28,  0xffff28,  0xffff28, 
   0xffff28, 0xffff28, 0xffff28, 0xffff28 };
   
void day() {
  gCurrentPalette = Day_p;
  fireNightRain(2,8);
  // RGB: 255,255,135
  /*
  for(int i = 0; i < NUM_LEDS; i++) {
    leds[i] = CRGB(255,255,135);
  }
  FastLED.show();
  */
}

// MORN ----------------------------------------------------------------
const TProgmemRGBPalette16 Morn_p FL_PROGMEM =
{  CRGB::Orange,  CRGB::Orange,  CRGB::Orange,  CRGB::Orange, 
   CRGB::Orange, CRGB::Orange, CRGB::Yellow, CRGB::Yellow,
   CRGB::Yellow,  CRGB::Yellow,  0xcccc55,  0xcccc55, 
   0xcccc55, 0xcccc55, 0xffffaa, 0xffffaa };
   
void morn() {
  gCurrentPalette = Morn_p;
  fireNightRain(2,4);
  // RGB: 255,255,135
}

// EVE -----------------------------------------------------------------
const TProgmemRGBPalette16 Eve_p FL_PROGMEM = 
{  CRGB::Black, CRGB::Orange, CRGB::Black, CRGB::Orange, 
   CRGB::Orange, CRGB::Black, CRGB::Yellow, CRGB::Blue, 
   CRGB::Yellow, CRGB::MediumBlue, CRGB::Yellow, CRGB::MediumBlue, 
   CRGB::Yellow, CRGB::MediumBlue, CRGB::MediumBlue, 0xffffaa };

void eve() {
  gCurrentPalette = Eve_p;
  fireNightRain(3, 3);
}

// NIGHT ---------------------------------------------------------------
const TProgmemRGBPalette16 Night_p FL_PROGMEM =
{  CRGB::Blue, CRGB::Blue, CRGB::Blue, CRGB::Blue, 
   CRGB::Black, CRGB::Black, CRGB::Black, CRGB::Blue, 
   CRGB::Blue, CRGB::Blue, CRGB::MediumBlue, CRGB::MediumBlue, 
   CRGB::MidnightBlue, CRGB::MidnightBlue, CRGB::MidnightBlue, 0xCAD5FA };
   
void night() {
  gCurrentPalette = Night_p;
  fireNightRain(3,5);
}

// WATER -----------------------------------------------------------
void water() {
  EVERY_N_MILLISECONDS( 50 ) {
    if (effect == 'w'){
      pacifica_loop();
      FastLED.show();
    }
  }
}

// FLASHBANG -----------------------------------------------------------
void flashbang() {
  if (!flashbangOff){
    for(int i = 0; i < 5; i++) {
      for(int i = 0; i < NUM_LEDS; i++) {
        leds[i] = CRGB(255,255,100);
      }
      FastLED.show();
      FastLED.delay(50);
      for(int i = 0; i < NUM_LEDS; i++) {
        leds[i] = CRGB(0,0,0);
      }
      FastLED.show();
      FastLED.delay(50);
    }
  }
  else{
    for (int i = 0; i < NUM_LEDS; i++){
      leds[i] = CRGB::Black;
    }
    FastLED.show();
  }
}

// RAIN ----------------------------------------------------------------
const TProgmemRGBPalette16 Rain_p FL_PROGMEM =
{  CRGB::Blue, CRGB::Blue, CRGB::Blue, CRGB::Blue, 
   CRGB::Blue, CRGB::Blue, CRGB::Blue, CRGB::Blue, 
   CRGB::Blue, CRGB::Blue, CRGB::LightBlue, CRGB::LightBlue, 
   CRGB::LightBlue, CRGB::Gray, CRGB::Gray, CRGB::Gray };

void rain() {
  gCurrentPalette = Rain_p;
  fireNightRain(6,4);
}

// UPSIDE DOWN ----------------------------------------------------------------
const TProgmemRGBPalette16 UpsideDown_p FL_PROGMEM = 
{CRGB::Red, CRGB::Red, CRGB::Red, CRGB::Red,
CRGB::Red, CRGB::Red, CRGB::Red, CRGB::Red,
CRGB::DarkRed, CRGB::DarkRed, CRGB::DarkRed, CRGB::DarkRed,
CRGB::DarkRed, CRGB::DarkRed, CRGB::DarkRed, CRGB::DarkRed};
void strange() {
  gCurrentPalette = UpsideDown_p;
  fireNightRain(5,2);
}

// FIRE ----------------------------------------------------------------
const TProgmemRGBPalette16 Fire_p FL_PROGMEM =
{  CRGB::Red,  CRGB::Red,  CRGB::Red,  CRGB::Red, 
   CRGB::OrangeRed, CRGB::OrangeRed, CRGB::Red, CRGB::OrangeRed,
   CRGB::Red,  CRGB::Red,  CRGB::Red,  CRGB::Red, 
   CRGB::OrangeRed, CRGB::Red, CRGB::Orange, CRGB::Red };

void fire() {
  gCurrentPalette = Fire_p;
  fireNightRain(5,4);
}

// CANDLELIGHT ---------------------------------------------------------
// 0xff9600 - yellow
// 0xff6400 - orange
const TProgmemRGBPalette16 Candle_p FL_PROGMEM =
{  0xff6400,  0xff6400,  0xff6400,  0xff9600, 
   0xff6400,  0xff6400,  0xff6400,  0xff6400,
   0xff6400,  0xff6400,  0xff6400,  0xff6400, 
   0xff6400,  0xff6400,  0xff6400,  0xff9600};

void candleLight() {
  gCurrentPalette = Candle_p;
  fireNightRain(4,1);
}

// SEXY TIME ---------------------------------------------- UwU -------
// pink 0x76008B
// purple 0x7800FF
const TProgmemRGBPalette16 Sexy_p FL_PROGMEM =
{  0x76008B,  0x76008B,  0x76008B,  0x76008B, 
   0x7800FF,  0x7800FF,  0x7800FF,  0x7800FF,
   0x76008B,  0x76008B,  0x76008B,  0x76008B, 
   0x7800FF,  0x7800FF,  0x7800FF,  0x7800FF};

void sexyTime() {
  gCurrentPalette = Sexy_p;
  fireNightRain(4,8);
}

// LIGHTNING -----------------------------------------------------------
void thunder(int FLASHES){
  unsigned int dimmer = 1;
  int randomNumber = random(150);
  if(randomNumber != 1) {
    if(upsideDown) {
      strange();
    } else {
      rain();
    }
  } else {
    for (int flashCounter = 0; flashCounter < random8(3,FLASHES); flashCounter++)
    {
      if(flashCounter == 0) dimmer = 5;     // the brightness of the leader is scaled down by a factor of 5
      else dimmer = random8(1,3);           // return strokes are brighter than the leader
      for(int i = 0; i < NUM_LEDS; i+=5) {
        if(upsideDown) {
          leds[i] = CRGB::Red;
        } else {
          leds[i] = CHSV(255, 0 , 255/dimmer);
        }
      }
      FastLED.show();
      //FastLED.showColor(CHSV(255, 0, 255/dimmer));
      FastLED.delay(random8(4,10));                 // each flash only lasts 4-10 milliseconds
      FastLED.showColor(CHSV(255, 0, 0));
      
      if (flashCounter == 0) FastLED.delay (150);   // longer delay until next flash after the leader
      FastLED.delay(50+random8(100));               // shorter delay between strikes  
    }
    //delay(random8(FREQUENCY)*100);          // delay between strikes
  }
}

// ANIMATION STUFF DOWN HERE -------------------------------------------
void fireNightRain(int TWINKLE_SPEED, int TWINKLE_DENSITY){
  
  drawTwinkles( leds, TWINKLE_SPEED, TWINKLE_DENSITY);
  
  FastLED.show();
}

void drawTwinkles( CRGBSet& L, int TWINKLE_SPEED, int TWINKLE_DENSITY)
{

  uint16_t PRNG16 = 11337;
  
  uint32_t clock32 = millis();

  CRGB bg;
  if( (AUTO_SELECT_BACKGROUND_COLOR == 1) &&
      (gCurrentPalette[0] == gCurrentPalette[1] )) {
    bg = gCurrentPalette[0];
    uint8_t bglight = bg.getAverageLight();
    if( bglight > 64) {
      bg.nscale8_video( 16); // very bright, so scale to 1/16th
    } else if( bglight > 16) {
      bg.nscale8_video( 64); // not that bright, so scale to 1/4th
    } else {
      bg.nscale8_video( 86); // dim, scale to 1/3rd.
    }
  } else {
    bg = gBackgroundColor; // just use the explicitly defined background color
  }

  uint8_t backgroundBrightness = bg.getAverageLight();
  
  for( CRGB& pixel: L) {
    if (serialFlag==true){
      return;
    }
    PRNG16 = (uint16_t)(PRNG16 * 2053) + 1384; // next 'random' number
    uint16_t myclockoffset16= PRNG16; // use that number as clock offset
    PRNG16 = (uint16_t)(PRNG16 * 2053) + 1384; // next 'random' number
    // use that number as clock speed adjustment factor (in 8ths, from 8/8ths to 23/8ths)
    uint8_t myspeedmultiplierQ5_3 =  ((((PRNG16 & 0xFF)>>4) + (PRNG16 & 0x0F)) & 0x0F) + 0x08;
    uint32_t myclock30 = (uint32_t)((clock32 * myspeedmultiplierQ5_3) >> 3) + myclockoffset16;
    uint8_t  myunique8 = PRNG16 >> 8; // get 'salt' value for this pixel


    CRGB c = computeOneTwinkle( myclock30, myunique8, TWINKLE_SPEED, TWINKLE_DENSITY);

    uint8_t cbright = c.getAverageLight();
    int16_t deltabright = cbright - backgroundBrightness;
    if( deltabright >= 32 || (!bg)) {

      pixel = c;
    } else if( deltabright > 0 ) {

      pixel = blend( bg, c, deltabright * 8);
    } else { 
      pixel = bg;
    }
  }
}

CRGB computeOneTwinkle( uint32_t ms, uint8_t salt, int TWINKLE_SPEED, int TWINKLE_DENSITY)
{
  uint16_t ticks = ms >> (8-TWINKLE_SPEED);
  uint8_t fastcycle8 = ticks;
  uint16_t slowcycle16 = (ticks >> 8) + salt;
  slowcycle16 += sin8( slowcycle16);
  slowcycle16 =  (slowcycle16 * 2053) + 1384;
  uint8_t slowcycle8 = (slowcycle16 & 0xFF) + (slowcycle16 >> 8);
  
  uint8_t bright = 0;
  if( ((slowcycle8 & 0x0E)/2) < TWINKLE_DENSITY) {
    bright = attackDecayWave8( fastcycle8);
  }

  uint8_t hue = slowcycle8 - salt;
  CRGB c;
  if( bright > 0) {
    c = ColorFromPalette( gCurrentPalette, hue, bright, NOBLEND);
    if( COOL_LIKE_INCANDESCENT == 1 ) {
      coolLikeIncandescent( c, fastcycle8);
    }
  } else {
    c = CRGB::Black;
  }
  return c;
}

uint8_t attackDecayWave8( uint8_t i)
{
  if( i < 86) {
    return i * 3;
  } else {
    i -= 86;
    return 255 - (i + (i/2));
  }
}

void coolLikeIncandescent( CRGB& c, uint8_t phase)
{
  if( phase < 128) return;

  uint8_t cooling = (phase - 128) >> 4;
  c.g = qsub8( c.g, cooling);
  c.b = qsub8( c.b, cooling * 2);
}

// WATER STUFF HERE -------------------------------------------------------------------------
CRGBPalette16 pacifica_palette_1 = 
    { 0x000507, 0x000409, 0x00030B, 0x00030D, 0x000210, 0x000212, 0x000114, 0x000117, 
      0x000019, 0x00001C, 0x000026, 0x000031, 0x00003B, 0x000046, 0x14554B, 0x28AA50 };
CRGBPalette16 pacifica_palette_2 = 
    { 0x000507, 0x000409, 0x00030B, 0x00030D, 0x000210, 0x000212, 0x000114, 0x000117, 
      0x000019, 0x00001C, 0x000026, 0x000031, 0x00003B, 0x000046, 0x0C5F52, 0x19BE5F };
CRGBPalette16 pacifica_palette_3 = 
    { 0x000208, 0x00030E, 0x000514, 0x00061A, 0x000820, 0x000927, 0x000B2D, 0x000C33, 
      0x000E39, 0x001040, 0x001450, 0x001860, 0x001C70, 0x002080, 0x1040BF, 0x2060FF };


void pacifica_loop()
{
  // Increment the four "color index start" counters, one for each wave layer.
  // Each is incremented at a different speed, and the speeds vary over time.
  static uint16_t sCIStart1, sCIStart2, sCIStart3, sCIStart4;
  static uint32_t sLastms = 0;
  uint32_t ms = GET_MILLIS();
  uint32_t deltams = ms - sLastms;
  sLastms = ms;
  uint16_t speedfactor1 = beatsin16(9, 179, 269);
  uint16_t speedfactor2 = beatsin16(12, 179, 269);
  uint32_t deltams1 = (deltams * speedfactor1) / 256;
  uint32_t deltams2 = (deltams * speedfactor2) / 256;
  uint32_t deltams21 = (deltams1 + deltams2) / 2;
  sCIStart1 += (deltams1 * beatsin88(1011,10,13));
  sCIStart2 -= (deltams21 * beatsin88(777,8,11));
  sCIStart3 -= (deltams1 * beatsin88(501,5,7));
  sCIStart4 -= (deltams2 * beatsin88(257,4,6));

  // Clear out the LED array to a dim background blue-green
  fill_solid( leds, NUM_LEDS, CRGB( 2, 6, 10));

  // Render each of four layers, with different scales and speeds, that vary over time
  pacifica_one_layer( pacifica_palette_1, sCIStart1, beatsin16( 3, 11 * 256, 14 * 256), beatsin8( 10, 70, 130), 0-beat16( 301) );
  pacifica_one_layer( pacifica_palette_2, sCIStart2, beatsin16( 4,  6 * 256,  9 * 256), beatsin8( 17, 40,  80), beat16( 401) );
  pacifica_one_layer( pacifica_palette_3, sCIStart3, 6 * 256, beatsin8( 9, 10,38), 0-beat16(503));
  pacifica_one_layer( pacifica_palette_3, sCIStart4, 5 * 256, beatsin8( 8, 10,28), beat16(601));

  // Add brighter 'whitecaps' where the waves lines up more
  pacifica_add_whitecaps();

  // Deepen the blues and greens a bit
  pacifica_deepen_colors();
}

// Add one layer of waves into the led array
void pacifica_one_layer( CRGBPalette16& p, uint16_t cistart, uint16_t wavescale, uint8_t bri, uint16_t ioff)
{
  uint16_t ci = cistart;
  uint16_t waveangle = ioff;
  uint16_t wavescale_half = (wavescale / 2) + 20;
  for( uint16_t i = 0; i < NUM_LEDS; i++) {
    waveangle += 250;
    uint16_t s16 = sin16( waveangle ) + 32768;
    uint16_t cs = scale16( s16 , wavescale_half ) + wavescale_half;
    ci += cs;
    uint16_t sindex16 = sin16( ci) + 32768;
    uint8_t sindex8 = scale16( sindex16, 240);
    CRGB c = ColorFromPalette( p, sindex8, bri, LINEARBLEND);
    leds[i] += c;
  }
}

// Add extra 'white' to areas where the four layers of light have lined up brightly
void pacifica_add_whitecaps()
{
  uint8_t basethreshold = beatsin8( 9, 55, 65);
  uint8_t wave = beat8( 7 );
  
  for( uint16_t i = 0; i < NUM_LEDS; i++) {
    uint8_t threshold = scale8( sin8( wave), 20) + basethreshold;
    wave += 7;
    uint8_t l = leds[i].getAverageLight();
    if( l > threshold) {
      uint8_t overage = l - threshold;
      uint8_t overage2 = qadd8( overage, overage);
      leds[i] += CRGB( overage, overage2, qadd8( overage2, overage2));
    }
  }
}

// Deepen the blues and greens
void pacifica_deepen_colors()
{
  for( uint16_t i = 0; i < NUM_LEDS; i++) {
    leds[i].blue = scale8( leds[i].blue,  145); 
    leds[i].green= scale8( leds[i].green, 175); 
    leds[i] |= CRGB( 2, 5, 7);
  }
}
