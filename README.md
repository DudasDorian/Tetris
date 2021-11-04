# Tetris
This is a personal rendition of Tetris.

## The basics
To start the game simply '_Run Main_'. You control the piece using W, A, S, and D or ↑, ←, ↓, and → keys, where A, S, D, ←, ↓, and → move the piece in the specified direction, and W and ↑ rotate the piece clockwise. Pressing the SPACE key will 'drop' the piece to the bottom-most position reachable. You may swap out the initially given piece with the next upcoming piece, seen in the _Next_ box on the righthand side, using the X key. This held piece will be shown in the _Hold_ box also on the righthand side. If the game is over, the highscore will be updated.

To exit the game, you can simply press the ESC key. 

## Example of gameplay:
![Screenshot](https://github.com/DudasDorian/Tetris/blob/main/Tetris%20gameplay%20example.png)

## Design details
This project uses multiple versions of Tetris for its base. Most of the gameplay is based around the [NES version](https://tetris.fandom.com/wiki/Tetris_(NES,_Nintendo)), with only a few additions from the [2009 and forward Tetris Guidelines](https://tetris.fandom.com/wiki/Tetris_Guideline) (i.e. Hold Piece, Ghost Piece, and the ability to drop the piece). The graphical interface is sleek, yet quite simple in appearance, it has been implemented using JavaFX's Canvas API.
