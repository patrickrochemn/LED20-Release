# LED20-Production

**Task List**
- [x] Selectable COM port
- [x] Investigate combat mode bug - not being able to exit combat mode. Need to try this with scraper code disabled

*GUI TASKS*
- [x] Manual HP controls
- [x] Manual HP write to JSON
- [x] Manual HP read from JSON
- [ ] Bug testing for Manual HP Controls
- [x] Manual death save controls
- [ ] Manual death saves write to JSON
- [ ] Manual death saves read from JSON
- [ ] Bug testing for Manual Death Save Controls
- [ ] Add a way to change player max HP values
- [ ] Confirm JSON functionality for all values
- [ ] Debug Player 4 death saves entrance
- [ ] Customizable GUI theme
- [ ] export as .jar and/or .exe
- [ ] add "off" effect button

*ARDUINO TASKS*
- [ ] add "waiting" animation/indicator for before COM port selection
- [ ] add "paired" animation/indicator for successfull COM port selection
- [ ] add parsing of conditions in data string
- this will probably need to be changed from an array of int to a single hex digit
- [ ] add parsing of conditions per player
- [ ] add one color for any condition
- [ ] add color for conditions
- [ ] add animations for conditions (if possible)

** Known Bugs **
- [ ] When scraper isn't running, health is still read from the txts when entering each combat mode
- [ ] Overhealed Players dont have blue health when entering combat mode (may be due to above bug)

https://github.com/somewhatlurker/FastLED/tree/d4598691e1a6fd2d5973119af82dd7235855180a
