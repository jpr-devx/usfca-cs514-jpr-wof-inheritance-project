# Wheel of Fortune Inheritance & Mastermind

### Overview
<div>
    <p>
        This project builds on the foundation set by Project 1 of the same course (CS514, at the University of San Francisco) by providing a hierarchical structure. There are two parts:
    </p>
    <ol>
        <li>
            <p>
                <strong>Wheel of Fortune Refactor:</strong>
                <br>
                The existing code in Project 1 is refactored to compose of an overarching Game class that a WheelOfFortune class extends. Two other subclasses: 
            </p>
            <ul>
                <li>WheelOfFortuneUserGame</li>
                <li>WheelOfFortuneAIGame</li>
            </ul>
            <p>
            In addition to the parent Game class, there is a Player interface (later renamed to GuessingPlayer) that concrete AI classes will implement. Through this implementation,the stage for the players to play in (A WheelOfFortuneAIGame object and the class' main method) will call for the player's nextGuess method, whatever that player is at runtime. This leads to the appropriate player guessing given the choices the player makes (playing all games to completion, or quitting at any point to forfeit any/all games). Each game will conclude with returning a record containing the ID of whatever player is concluding that game and their score. Once all players have concluded their games an AllGamesRecord object is returned which composes of an ArrayList of gameRecords containing those playerId, score pairs.
            </p>
        </li>
        <li>
            <strong>Mastermind and WheelOfFortune Refactor:</strong>
            <br>
            <p>
                Following the completion of this first part of the project, an additional game was developed with the intent of reusing as much code developed for the first part of the project. To deliver on this intent, code within certain classes and the player interface would need to be tweaked to be compatible across both games and allow for the extension of that code to deliver on each game's unique playthrough (e.g. WoF features a single letter being guessed at a time whereas Mastermind features a string of four letters corresponding to colors being guessed at a time). Achieving the deliverance of this intent was done by changing the return type of the getGuess and, in turn the nextGuess method, to a String return type. By doing so, validation within WheelOfFortune code tree would allow single character strings to be ensured and align with a String return type. This modification would then allow for Mastermind's code to define its nextGuess method to handle guesses of a 4 characters in length. With the modifcation of the player interface (originally named "WheelOfFortunePlayer" interface) the name was changed to "GuessingPlayer" to reflect its generaliztion to not just WheelOfFortune but now Mastermind as both guessing games.
            </p>
        </li>
    </ol>
</div>