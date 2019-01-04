## Table of Contents ​ Page

- 1. Introduction
- 1.1 Overview
- 1.2 Glossary
- 2. System Architecture
- 2.1 Google Play Services
- 2.2 Single Player
- 2.3 Multiplayer
- 3. Round Functionalities
- 3.1 Letter Round
- 3.2 Number Round
- 3.3 Conundrum Round
- 4.1 Description of High Level Design 4. High Level Design
- 5. Problems and Resolution
- 5.1 Real Time Multiplayer
- 5.2 Dictionary
- 5.3 Pair Programming
- 5.4 Sounds
- 6. Installation Guide
- 6.1 Prerequisites
- 6.2 How To Install
- 7. Appendices ​ ​


## 1. Introduction

## 1.1 Overview

This is an application developed for Android which is made accessible to the public
via the Google Play Store. It is a game which is based on the popular British
television show Countdown involving word and number puzzles. The show itself was
the first show to air on Channel 4 in 1982, withstanding the test of time having over
77 seasons under its belt. This equates to over 6,500 episodes, proving itself to be
popular among viewers
Players compete in three disciplines: ​letters rounds​, in which the contestants attempt
to make the longest word possible from nine randomly chosen letters; ​numbers
rounds​, in which the contestants must use ​arithmetic​ to reach a random target
number from six other numbers; and the conundrum, a round in which the
contestants compete to solve a nine-letter ​anagram​.
The application contains design and sound cues reminiscent of the show in order to
appeal to the user. It has a single player and multiplayer mode for your to play by
yourself or with friends. It contains integration with the Google Play Games services
in order to implement leaderboards as a means of tracking high scores amongst
players. We track user input via a drop and drop feature, dragging letters and
numbers over to their corresponding boxes. Scoring is done based on how long your
correct word or how close your number is to the target to determine a winner.

## 1.2 Glossary

Letter Round: This is a round within the countdown game. A player must pick nine
letters to be used by all players, choosing from either vowels or consonants for each
individual letter. Then the thirty second timer begins and the players have to make
the longest word they can from these characters in the allocated time.
Number Round: This is a round within the countdown game. A player must pick six
numbers. They can choose between a set of large or small numbers. The most large
numbers you can choose is four. A randomly generated three digit number is
chosen. The players must now use the chosen numbers to reach the target number
using addition, subtraction, multiplication, etc. This is to be done within the allocated
time.


Conundrum Round: In this round nine random letters are shown. These letters are
able to form a word. The letters must be rearranged in order to form the word and all
letters must be used. This is to be done within the allocated time limit.
Google Play Game Services: This is what game developers use to create social
leaderboards in order to let players compete against one another for the top score.

## 2. System Architecture
![Imgur](https://i.imgur.com/mc1OPW5.png)
Fig 1.
The system consists of a splash screen which leads into the home activity. The main
intent from this home screen is to either choose to play single player or multiplayer.
Either choice will bring you to the subsequent rounds as follows.

## 2.1 Google Play Services

Upon entering the home activity dependant on the users Google Play settings, they
will either be automatically signed into Google Play Games or they will have the
option to sign in via the home screen manually. This can be done before a game is
initiated in the hoe activity or in the final screen. Upon signing in they will be able to
access the leaderboards and also have the option to submit their new score in order
to compete against friends.

## 2.2 Single Player

Upon initiating the single player game session the user will go through three rounds
which are the Letter, Number and Conundrum. In between these rounds the user will
be brought to a results screen in order to see their score before they progress to the
next stage. The ability the use the back button on the device to go back to previous
rounds is blocked in order to maintain fair play. You will be able to use the back
button but that will only exit you from the game and bring you back to the home
activity.


## 2.3 Multiplayer

This aspect of the game has the same playthrough as the single player version but
each round is played through twice to allow both players to get a score. The only
new activities is the screens to signal which players turn it is to play. The result
screen is changed to show both players score in between each round as the game
progresses.

## 3. Round Functionalities

## 3.1 Letter Round

In order to implement the letter round, we started off by handling the users input.
This was done by having nine text boxes which are filled via buttons which is either
vowels or consonants. Once the nine separate boxes are filled, the round
automatically begins. The user has 30 seconds to form the longest word they can
from the selection of letters. This is done with our drag and drop feature which is an
on drag listener.
Once the letter is dragged into the appropriate box, it becomes unavailable for
selection from the original nine letters chosen. This is repeated until the word is
formed, where the time runs out or the user can submit their answer early. The
submission is formed back into a string. A dictionary is read in from the assets
directory and all the words in the file are compared to the submission to check for
validity. This dictionary itself is taken straight from the scrabble official list of words. If
a word the score given to the player is the length of the word, otherwise zero points.

## 3.2 Number Round

This round plays similar in terms of user input. Six text boxes which are filled via two
buttons which is either large numbers or small numbers. The large numbers is made
up of four numbers (25, 50, 75, 100) and the small numbers consists of the numbers
1 to 10. Once the numbers are picked the round begins.
The target number is chosen at random in a range of 100 to 999. The player drags
their numbers and operands to complete arithmetic operations in order to reach the
target number which is all done via the drag listener. We then compare the users
number to the target number and score according to how close it is. 10 for reaching it
exactly, 7 for being 1-5 away, 5 for being 6-10 away.


## 3.3 Conundrum Round

Here a random word of a nine letter length is chosen at random from our dictionary
file. Once the user initiates the round a function jumbles up the word. The user drags
and drops the letters to try and form that particular word. If successful, nine points
are applied.

## 4. High-Level Design
![Imgur](https://i.imgur.com/dXfngeD.png)
Fig 1.


## 4.1 Description of High Level Design 4. High Level Design

```
● Splash screen - ​This is welcoming screen in for the application.
● Home Screen - ​This is the main screen with options to go to the leaderboard,
Sign in and out of Google Play along with the single player and multiplayer
modes.
● Letter Round - ​Players have to make the longest word they can using the
letters selected within the 30 second timer.​ : ​One of the players is randomly
selected to choose letters for the letter round.
● Numbers Round - ​Players must try reach the target number using the
numbers selected and basic operators such as addition, subtraction,
multiplication etc.
● Conundrum Round - ​Players must unscramble the word using the letters
provided
● Result / Multiplayer Screen - ​Displays results screen of the round just
played.
● Player 1 Screen - ​Player 1 round initiates.
● Player 2 Screen - ​Player 2 round initiates.
● Final Screen - ​Displays final scores along with option to submit scores to
leaderboards.
```
![Imgur](https://i.imgur.com/ZGeXGAN.png)
Fig 1.
This is a class diagram showing the main classes and their methods used in the
implementation of the game.

## 5. Problems and Resolution

## 5.1 Real Time Multiplayer

Problem - We encountered major problems in terms of implementing google play
services but the main one being getting two players connected to a game in real
time. We tried using multiple services we thought would help like firebase for
example but found out they did not exactly apply to our situation in what they offered.
Resolution - Under the advice of our supervisor we agreed it would take too much
valuable time to try and implement and could possibly waste our time if we were not
able to do it at all. We scrapped the idea but came up with a compromise resolution
of still delivering on the multiplayer experience by doing it locally.

## 5.2 Dictionary

Problem - Came into difficulty when validating words in terms of reading in the file.
We initially tried to read it in as a regular file using the file path on the computer
along with other methods.
Resolution - We then came across a tutorial that states you need to make an assets
directory within the android project in studio. We implemented the steps and
changed made sure it was reading in before using it to validate our words.


## 5.3 Pair Programming

Problem - For the first two weeks of the project we worked from separate locations in
order to get our tasks for the week done. We found this to be extremely slow and
inefficient in trying to complete all the tasks on our schedule for the week. Especially
being on different pages with certain ideas that you may not discuss unless you are
in person.
Resolution - We found that we worked a lot better when we were together. We had
days of pair programming where one person would sit at a screen and another would
watch on reviewing the code as it’s made spotting mistakes and making suggestions,
changing positions every hour. It was great in terms of discussing code on the spot
and not both doing different things with different ideas in mind on a tangent.

## 5.4 Sounds

Problem - We implemented two main sounds into the game, the clock countdown for
the rounds and the general theme tune that is always played in the opening and
closing of the show to. We implemented it and thought it was fine but upon user
feedback, found that many people did not enjoy the actual theme tune. They thought
it was in line with the show but it was actually quite annoying the more games they
played
Resolution - We simply took out the sound, we found out that in this case, less is
more.

## 6. Installation Guide

This application is only available on android from the Google Play Store.

## 6.1 Prerequisites

```
● It is only available in devices in Ireland.
● The application supports Android 4.0 Ice Cream Sandwich or above.
● Contains a PEGI 3 rating.
```

## 6.2 How To Install

**1.** ​ Open the Play Store application and type in “Barry Reynolds” into the
search bar on the top of the screen.
![Imgur](https://i.imgur.com/lCNBuyz.jpg)
**2.** ​ Click onto the first item in listed results called “Countdown: The
Game”.
![Imgur](https://i.imgur.com/UujdJgC.jpg)

**3.** ​Click on the “install” button.
![Imgur](https://i.imgur.com/bMINi98.jpg)
**4.** ​ The application is now installed, you can now access it by pressing
the “open” button on the screen.
![Imgur](https://i.imgur.com/0PZRbmX.jpg)


## 7. Appendices ​ ​

```
● http://www.umlet.com/
● https://en.wikipedia.org/wiki/Countdown_(game_show)
● https://developer.android.com/distribute/console/index.html
● https://developers.google.com/android-publisher/
```

