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

 
