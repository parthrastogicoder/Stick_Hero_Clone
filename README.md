# Stick Hero Game - JavaFX

## Description
https://github.com/parthrastogicoder/stickmanFX
Stick Hero Game is a JavaFX-based desktop game where players control a stick figure character which is mario in our case. It incorporates Object-Oriented Programming (OOP) principles such as encapsulation and inheritance, making the code more modular, reusable, and maintainable. The game features dynamic platform generation, stick growth and falling mechanics, and different sound effects for in-game actions.

## Features
- **Dynamic Platform Generation**: Challenges the player with new platforms .
- **Stick Mechanics**: Includes growth and falling mechanics that add strategic depth to the game.
- **Sound Effects**: Features background music and event-based sounds using a Factory pattern.
- **Player Control**: Allows the player to move and invert.
- **GameOver Logic**: Handles player falls and game restarts.
- **Score Tracking**: Keeps track of player performance throughout the game.
- **Reviving**: Using 5  mushrooms player revives.
- **Design Patterns**: Utilizes Factory and Null object design patterns for sound generation.
- **JUnit Testing**: Includes unit tests to ensure the reliability and correctness of the game components.
- **OOP Concepts**: Implements encapsulation and inheritance for a structured and efficient codebase.
- **Multithreading**: Implements multithreading in sound .
- **Serialization**: Used to save game and run game.

## How to Run
1. mvn clean
2. mvn compile
3. mvn package
4. mvn exec:java

   For JUnit Testing : Go to TestRunner class and start.

## Project Structure
- **`StickMan`**: Main application class for launching the game.
- **`Player`, `Stick`, `Platform`**: Core game entities.
- **`GameController`**: Manages the game logic.
- **`Sound` and `Soundable`**: Handles sound effects, demonstrating the Factory pattern.
- **`SoundFactory`**: Factory pattern implementation for creating sound objects.
- **`Tester` and `TestRunner`**: JUnit tests for game components.
- **Design Patterns**:
  - *Factory Pattern*: Implemented in `SoundFactory` and related classes for sound management.
  - *Null Object Pattern*: Used in `NullSound` Class .
  - **Theards**:
  - Implemented in 'Sound class'.
  - **Exception Handling**:
  - Implemented in 'InvalidGameStateException' and 'ResourceLoadException'.

## Contributors
Parth Sandeep Rastogi - 2022352
Anisha -2022078





---

Enjoy Stick Hero Game, a well-structured JavaFX application utilizing OOP principles and design patterns for an enhanced coding and gaming experience!

