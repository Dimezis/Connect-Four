# Connect-Four

## Task
You need to create a client (bot) which will be playing [Connect Four](https://en.wikipedia.org/wiki/Connect_Four) with other bots.
Manual execution of moves is NOT allowed.
Manual connection to a socket and specific channel is allowed though.
Programming language doesn't matter.


## Flow
1) Open TCP socket connection to platinum.edu.pl on port 4242

2) Send command `NAME your_nick_name`. It will be displayed in the statistics. You will receive `SUCCESS` response

3) Send command `JOIN channel_name`, where `channel_name` is an arbitrary String. It's used to connect 2 opponents to a single channel to play with each other. As a response, you will receive either `SUCCESS` or `CHANNEL_IS_FULL`, if the corresponding channel already has 2 players in game

4) When both players are connected to same channel, you will receive Message `NEW_GAME`. This informs you, that you should reset your game board state. You can receive this message at any time, for example on a new round or when your opponent is changed

5) One of the players will receive `YOUR_MOVE` Message. After this you can send the command to put a disc into some column

6) This player now should send a command `PUT column_index`, where `column_index` is an integer from 0 to 6. He can do this ONLY after receiving `YOUR_MOVE` command

7) Opponent will receive command `PUT column_index`, which is reflecting previous player's move. This means, that you should update your local game board state

8) After this, player will receive `YOUR_MOVE` Message. If game is not finished, go to step 5

9) If someone wins, this player receives `YOU_WON`. Opponent receives `YOU_LOST`. If the board is full and there's no winner, every player will receive `DRAW`. You should NOT disconnect from the socket here

10) There will be 4 rounds with the same opponent, every player will receive `NEW_GAME` message, look at step 4. Colors of the players will be swapped, to allow other player make first move

11) At the very end, both players will be disconnected from the socket automatically


## Protocol
1) Commands are NOT case sensitive

3) Every Command must end with `\n`

4) Server doesn't send the current state of the board, you should store it yourself. It only sends you the moves of your opponent (`PUT column_index`)

5) Game board is 6x7 (6 rows, 7 columns)

6) Column index is an integer from 0 to 6

7) Messages sent from server can be 1 or 2 words long. Each one ends with `\n`. Example: `YOUR_MOVE` - 1 word. `PUT 5` - 2 words.

8) If you are trying to execute some illegal command, you will receive a corresponding error (see error list). Don't try to cheat in game (making double moves, making a move before receiving a signal, sending invalid index). You will be punished (auto lose) and disconnected from the socket in this case.

9) Errors format is `Error ERROR_NAME`


## Testing
If you need to test your client, you can run two instances of your bot and `JOIN` to the same channel, preferably with some more or less unique name.


## Errors list
1) INVALID_COMMAND

2) CHANNEL_IS_FULL

3) INVALID_COLUMN

4) COLUMN_IS_FULL

5) NOT_JOINED_TO_CHANNEL

6) WRONG_COLUMN_FORMAT

7) NOT_YOUR_TURN


## Messages list
1) YOUR_MOVE

2) YOU_WON

3) YOU_LOST

4) NEW_GAME

5) DRAW

6) SUCCESS
