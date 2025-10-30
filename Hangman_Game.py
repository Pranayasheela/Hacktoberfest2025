import random

def play_hangman():
    words = ["python", "apple", "computer", "program", "hangman", "science"]
    word = random.choice(words)
    guessed = ""
    tries = 6

    print("\nWelcome to Hangman! ")

    while tries > 0:
        failed = 0
        for letter in word:
            if letter in guessed:
                print(letter, end=" ")
            else:
                print("_", end=" ")
                failed += 1

        print()  

        if failed == 0:
            print("You win! The word was:", word)
            break

        guess = input("Guess a letter: ").lower()

        if not guess.isalpha() or len(guess) != 1:
            print(" Enter only a single alphabet letter!")
            continue

        if guess in guessed:
            print("You already guessed that letter.")
            continue

        guessed += guess

        if guess not in word:
            tries -= 1
            print(" Wrong! You have", tries, "tries left.")

            if tries == 0:
                print(" You lost! The word was:", word)
                break


def main():
    while True:
        print("\n===== HANGMAN MENU =====")
        print("1. Play Game")
        print("2. Exit")
        choice = input("Enter your choice (1/2): ")

        if choice == "1":
            play_hangman()
        elif choice == "2":
            print("Thanks for playing! Goodbye!")
            break
        else:
            print("Invalid choice! Please enter 1 or 2.")


if __name__ == "__main__":
    main()
