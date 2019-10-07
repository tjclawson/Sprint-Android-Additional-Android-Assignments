# Media Services

#### *Sprint 9: Module 1*

Assignment

---

## Overview
Build an app which updates the position of a seekbar for a mediaplayer. Also show time elapsed and time remaining.

## Requirements
This app will consist of a Media acitvity with your own UI that will play a locally stored media file (video).    
1)The position of the seekbar should be updated every second as the media is playing along with time elapsed values.  
2)The user should be able to play/pause the media and seek to any position at any time. 


## Outline
1. Create a project and add any media source in the raw directory(video)
2. Add your own UI to the app for the seekbar, play/pause buttons
3. Add clicklisteners for the play/pause buttons to properly prepare and play the media.
4. Write a function that runs every second to update the position of the seekbar and also shows the time elapsed value
and time remaining value in this format: mm:ss (Hint: You could use the Handler class for this)

## Go Further
- Repeat this project using ExoPlayer instead of the traditional MediaPlayer.
- Implement fastforward_increment and rewind_increment in the UI and implement it.      
(Hint:It's really easy using ExoPlayer, just a matter of a setting) 
  
