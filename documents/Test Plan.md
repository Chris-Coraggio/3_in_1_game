# 3 in 1 Game Test Plan

Chris Coraggio, Muhammad Hamza Farrukh, Brad Howell, Rizwanulla Mohammed, Syed Ahad Sagheer

# Functional Requirement 1

### Functional test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>0</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Setting up a nickname</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>1</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>At the choose nickname menu, type "Username001" and hit enter</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>On the main menu page, “Username001” is displayed next to Username:</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


### Equivalence class test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>1</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Nickname too long</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>1</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>At the change nickname menu, type in a username that is too log and hit enter</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The app displays a "Nickname too long" error</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>2</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Nickname too short</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>1</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>At the change nickname menu, type in a username that is too short and hit enter</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The app displays a "Nickname too short" error</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


### Boundary test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>3</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Username already taken</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>1</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>At the change nickname menu, type in a username that is already used by another player</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The app displays a "Nickname taken" error</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Critical</td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>4</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Username contains illegal character</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>1</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Type in a username that contains emojis, backslashes, or other illegal characters</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The app displays a "Illegal character" error</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Important</td>
  </tr>
</table>


# Functional Requirement 2

### Functional test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>5</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Changing a nickname</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>2</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>At the change nickname menu, type "Username002" and hit enter.</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>On the main menu page, “Username002” is displayed next to Username: </td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


### Equivalence class test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>6</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Changing a nickname</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>2</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>At the change nickname menu, type the same username that you already have. </td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The app displays a "Username is the same" error</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


		

### Boundary test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>7</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Username already taken</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>2</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>At the change nickname menu, type in a username that is already used by another player</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The app displays a "Nickname taken" error</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Critical</td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>8</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Username contains illegal character</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>2</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Type in a username that contains emojis, backslashes, or other illegal characters</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The app displays a "Illegal character" error</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Important</td>
  </tr>
</table>


# Functional Requirement 3

### Functionality test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>9</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Play Connect 4 with AI </td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>3</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select a location on the board to place a coin. </td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The coin should appear in the correct location on the board. </td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Critical</td>
  </tr>
</table>


### Equivalence class test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>10</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Play Connect 4 with AI </td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>3</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select a location on the board that is not in bounds to place a coin. </td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>A "Coin Location invalid" error </td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Critical</td>
  </tr>
</table>


### Boundary test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>11</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Play Connect 4 with AI </td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>3</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select a location on the left or right edge of the board that is in bounds to place a coin. </td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>A coin should appear in the correct location on the board. </td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Critical</td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>12</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Play Connect 4 with AI </td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>3</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select a location outside of the board to place a coin. </td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>A "Coin Location invalid" error </td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Critical</td>
  </tr>
</table>


# Functional Requirement 4

### Functionality test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>13</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Play Tic-Tac-Toe with AI </td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>3</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select a valid location which is not marked</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>Location becomes marked (with ‘X’ or ‘O’)</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Critical</td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>14</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Play Tic-Tac-Toe with AI </td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>3</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select an already marked location </td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>Board will not update</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Important</td>
  </tr>
</table>


### Equivalence class test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>15</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Play Hangman with AI </td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>3</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select a character which is not a letter (such as an #)</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>A "Character invalid" error </td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Important</td>
  </tr>
</table>


### Boundary test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>16</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Play Tic-Tac-Toe with AI </td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>3</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select a location outside of the board to place an ‘X’ or ‘O’. </td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>A "Marker Location invalid" error </td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Critical</td>
  </tr>
</table>


# Functional Requirement 5

### Functionality test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>17</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Play Hangman with AI </td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>3</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Guess a new letter by typing it</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>Certain locations become marked with chosen letter</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Critical</td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>18</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Play Hangman with AI </td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>3</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select an already chosen letter </td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>Board will not update</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Important</td>
  </tr>
</table>


### Equivalence class test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>19</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Play Tic-Tac-Toe with AI </td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>3</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select an already marked location </td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>Board will not update</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Important</td>
  </tr>
</table>


### Boundary test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>20</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Play Tic-Tac-Toe with AI </td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>3</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select a location outside of the board to place an ‘X’ or ‘O’. </td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>A "Marker Location invalid" error </td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Critical</td>
  </tr>
</table>


# Functional Requirement 6

### Functional test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>21</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>As a user, I would like to play Connect 4 with a friend online</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>6</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Connect to a user whose nickname you spelled correctly</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>Connect to a player</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


### Equivalence class test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>22</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>As a user, I would like to play Connect 4 with a friend online</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>6</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Connect to a user whose nickname you misspelled correctly</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>Player not found error</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>23</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>As a user, I would like to play Connect 4 with a friend online</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>6</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Try connecting to a user online while not connected to the internet</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>Throw "No internet connection" error </td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Important</td>
  </tr>
</table>


# Functional Requirement 7

### Functional test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>24</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>As a user, I would like to play Tic-Tac-Toe with a friend online</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>6</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Connect to a user whose nickname you spelled correctly</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>Connect to a player</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


### Equivalence class test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>25</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>As a user, I would like to play Tic-Tac-Toe with a friend online</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>6</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Connect to a user whose nickname you misspelled</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>Player not found error</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>26</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>As a user, I would like to play Tic-Tac-Toe with a friend online</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>6</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Try connecting to a user online while not connected to the internet</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>Throw "No internet connection" error </td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Important</td>
  </tr>
</table>


# Functional Requirement 8

### Functional test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>27</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>As a user, I would like to play Hangman with a friend online</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>6</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Connect to a user whose nickname you spelled correctly</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>Connect to a player</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


### Equivalence class test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>28</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>As a user, I would like to play Hangman with a friend online</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>6</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Connect to a user whose nickname you misspelled</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>Player not found error</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>29</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>As a user, I would like to play Hangman with a friend online</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>6</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Try connecting to a user online while not connected to the internet</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>Throw "No internet connection" error </td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Important</td>
  </tr>
</table>


# Functional Requirement 9

### Functional test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>30</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Display Score - connect 4</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>9</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Complete a game of connect 4 with the AI</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>A score is displayed to the user</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>31</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Display Score - Tic-Tac-Toe</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>9</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Complete a game of Tic-Tac-Toe with the AI</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>A score is displayed to the user</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>32</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Display Score - Hangman</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>9</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Complete a game of Hangman with the AI</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>A score is displayed to the user</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


# Functional Requirement 10

### Functional test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>33</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Display Game Menu</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>10</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Launch the application</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The home screen, which allows you to select a game, is displayed</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Critical</td>
  </tr>
</table>


# Functional Requirement 11

### Functional test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>34</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Select game - Connect 4</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>11</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>From the main menu, select the button to play connect 4</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The app displays the options to play single or multiplayer for connect 4</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Critical</td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>35</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Select Game - Tic-Tac-Toe</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>11</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>From the main menu, select the button to play Tic-Tac-Toe</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The app displays the options to play single or multiplayer for Tic-Tac-Toe</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Critical</td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>36</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Select Game - Hangman</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>11</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>From the main menu, select the button to play Hangman</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The app displays the options to play single or multiplayer for Hangman</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Critical</td>
  </tr>
</table>


# Functional Requirement 12

### Functional test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>37</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Enter friend’s nickname - Connect 4</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>12</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select the multiplayer option from the Connect 4 menu</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>An "enter friend’s nickname" option is displayed on the screen</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Critical </td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>38</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Enter friend’s nickname - Tic-Tac-Toe</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>12</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select the multiplayer option from the Tic-Tac-Toe menu</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>An "enter friend’s nickname" option is displayed on the screen</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Critical </td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>39</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Enter friend’s nickname - Hangman</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>12</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select the multiplayer option from the Hangman menu</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>An "enter friend’s nickname" option is displayed on the screen</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Critical </td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>40</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Enter Invalid Nickname</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>12</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>At the "enter friend’s nickname" menu, type in a nickname that is not associated with a user</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>A “Friend not found” error is displayed</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Important</td>
  </tr>
</table>


### Boundary test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>41</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Friend’s Nickname too long</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>12</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>At the "enter friend’s nickname" menu, type in a nickname that is too long</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The app displays a “Nickname too long” error</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>42</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Friend’s Nickname too short</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>12</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>At the "enter friend’s nickname" menu, type in a nickname that is too short</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The app displays a “Nickname too short” error</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


# Functional Requirement 13

### Functional test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>43</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Wait for friend to connect (host player)</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>13</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>After selecting a game for multiplayer wait for a friend to connect to you</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The app displays a waiting icon</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Important</td>
  </tr>
</table>


# Functional Requirement 14

### Functional test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>44</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Display Connect 4 Board - Single Player</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>14</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select the single player option in the connect 4 options.</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The app displays the connect 4 board</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Important</td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>45</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Display Connect 4 Board - Multiplayer</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>14</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select the multiplayer option in the connect 4 options, then type in a valid friend’s nickname and connect to the friend</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The app displays the connect 4 board</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Important</td>
  </tr>
</table>


# Functional Requirement 15

### Functional test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>46</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Display Tic-Tac-Toe 4 Board - Single Player</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>15</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select the single player option in the Tic-Tac-Toe options.</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The app displays the TIc-Tac-Toe board</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Important</td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>47</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Display Tic-Tac-Toe Board - Multiplayer</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>14</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select the multiplayer option in the Tic-Tac-Toe options, then type in a valid friend’s nickname and connect to the friend</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The app displays the Tic-Tac-Toe board</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Important</td>
  </tr>
</table>


# Functional Requirement 16

### Functional test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>48</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Display Hangman Board - Single Player</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>16</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select the single player option in the Hangman options.</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The app displays the Hangman board</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Important</td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>49</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Display Hangman Board - Multiplayer</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>16</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select the multiplayer option in the Hangman options, then type in a valid friend’s nickname and connect to the friend</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The app displays the Hangman board</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Important</td>
  </tr>
</table>


# Functional Requirement 17

### Functional test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>50</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Forfeit - Connect 4 Single Player</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>17</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select forfeit while playing connect 4 against the AI</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The game ends and the app displays game over</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>51</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Forfeit - Connect 4 Multiplayer</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>17</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select forfeit while playing connect 4 against the a friend</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The game ends and the app displays game over for both users</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


# Functional Requirement 18

### Functional test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>52</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Forfeit - Tic-Tac-Toe Single Player</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>18</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select forfeit while playing tic-tac-toe against the AI</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The game ends and the app displays game over</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>53</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Forfeit - Tic-Tac-Toe Multiplayer</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>18</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select forfeit while playing Tic-Tac-Toe against the a friend</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The game ends and the app displays game over for both users</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


# Functional Requirement 19

### Functional test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>54</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Forfeit - Hangman Single Player</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>19</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select forfeit while playing hangman against the AI</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The game ends and the app displays game over</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>55</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Forfeit - Hangman Multiplayer</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>18</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>Select forfeit while playing Hangman against the a friend</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The game ends and the app displays game over for both users</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


# Functional Requirement 20

### Functional test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>56</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Close app while playing connect 4 - Single Player</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>20</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>While playing a single player game of connect 4, close the app and then restart it. Finally, select connect 4 from the menu</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>A new game of connect 4 should launch</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>57</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Close app while playing Connect 4- Multiplayer</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>20</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>While playing a multiplayer game of connect 4, close the app.</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The opponent’s app should display that the game ended</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


# Functional Requirement 21

### Functional test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>58</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Close app while playing tic-tac-toe - Single Player</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>21</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>While playing a single player game of tic-tac-toe, close the app and then restart it. Finally, select tic-tac-toe from the menu</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>A new game of tic-tac-toe should launch</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>59</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Close app while playing Tic-Tac-Toe- Multiplayer</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>21</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>While playing a multiplayer game of Tic-Tac-Toe, close the app.</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The opponent’s app should display that the game ended</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


# Functional Requirement 22

### Functional test cases

<table>
  <tr>
    <td>ID Number</td>
    <td>60</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Close app while playing hangman - Single Player</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>22</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>While playing a single player game of hangman, close the app and then restart it. Finally, select hangman from the menu</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>A new game of hangman should launch</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


<table>
  <tr>
    <td>ID Number</td>
    <td>61</td>
  </tr>
  <tr>
    <td>Title</td>
    <td>Close app while playing hangman - Multiplayer</td>
  </tr>
  <tr>
    <td>Functional Requirement </td>
    <td>22</td>
  </tr>
  <tr>
    <td>Instructions</td>
    <td>While playing a multiplayer game of hangman, close the app.</td>
  </tr>
  <tr>
    <td>Expected Result </td>
    <td>The opponent’s app should display that the game ended</td>
  </tr>
  <tr>
    <td>Severity</td>
    <td>Workaround</td>
  </tr>
</table>


