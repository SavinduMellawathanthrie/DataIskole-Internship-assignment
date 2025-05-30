# DataIskole-Internship-assignment

This is a simple CLI-based Java application to manage a network of superheroes and their friendships. You can view summaries, search superheroes, add new ones, or remove them. All data is persisted using CSV files.

How to Run the Code

Compile the Program - Open your terminal or command prompt and navigate to the project directory (where the `Main.java` file is located).

cd path/to/your/project/src/main/java/network
javac Main.java
java network.Main

🛠️ Tools / Libraries Used

1. Java SE 21(JDK 21)
2. Standard libraries:
          java.util.*
          java.io.*
          java.time.*
3. CSV file manipulation using FileReader, FileWriter, and BufferedReader

⚠️ Important Notes

When adding new superheroes, make sure you don't use a duplicate name. Currently, the program does not enforce uniqueness of names or check for duplicates.
If you add a superhero with a name that already exists and then try to remove it, the program might behave unpredictably or crash.
The system assumes each superhero's ID is unique, automatically generated based on the highest existing ID.

📌 Extra Info

Friendships are bidirectional — if A is friends with B, B is automatically friends with A.
The application auto-saves all additions and deletions to the corresponding CSV files.
You can freely modify the .csv files outside the app if needed, just make sure to avoid empty lines and keep the format consistent.
You can Modify the code not to accept duplicate values.

 
--------------------------------------------Sample Output----------------------------------------------

--- MENU ---
1. Show summary
2. Find a superhero
3. Add new superhero
4. Remove a superhero
5. Exit
Enter option: 1
Total superheroes: 11
Total connections: 42

Superheroes added in the last 3 days:

Top 3 most connected superheroes:
- Spider-Man: 10 friends
- Captain America: 10 friends
- Iron Man: 9 friends

--- MENU ---
1. Show summary
2. Find a superhero
3. Add new superhero
4. Remove a superhero
5. Exit
Enter option: 2
Enter superhero name: Dataiskole

Superhero 'dataiskole' was added on: 2025-05-26
Friends:
- Spider-Man
- Captain America
- Scarlet Witch

--- MENU ---
1. Show summary
2. Find a superhero
3. Add new superhero
4. Remove a superhero
5. Exit
Enter option: 5
Exiting...

Process finished with exit code 0

---------------------------------Sample Output------------------------------------------
