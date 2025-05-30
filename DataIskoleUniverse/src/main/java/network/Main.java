package network;

import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final String SUPERHERO_FILE = "src/main/resources/superheroes.csv";
    private static final String LINKS_FILE = "src/main/resources/links.csv";

    public static void main(String[] args) {
        try {
            NetworkBuilder builder = new NetworkBuilder();
            builder.loadSuperheroes(SUPERHERO_FILE);
            builder.loadLinks(LINKS_FILE);
            Map<Integer, SuperHero> network = builder.getNetwork();

            while (true) {
                System.out.println("\n--- MENU ---");
                System.out.println("1. Show summary");
                System.out.println("2. Find a superhero");
                System.out.println("3. Add new superhero");
                System.out.println("4. Remove a superhero");
                System.out.println("5. Exit");
                System.out.print("Enter option: ");
                String option = scanner.nextLine();

                switch (option) {
                    case "1":
                        showSummary(network);
                        break;
                    case "2":
                        findSuperhero(network);
                        break;
                    case "3":
                        addSuperhero(network);
                        break;
                    case "4":
                        removeSuperhero(network);
                        break;
                    case "5":
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid option. Try again.");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void showSummary(Map<Integer, SuperHero> network) {
        System.out.println("Total superheroes: " + network.size());

        int totalConnections = network.values().stream()
                .mapToInt(hero -> hero.getFriends().size()).sum() / 2;
        System.out.println("Total connections: " + totalConnections);

        LocalDate threeDaysAgo = LocalDate.now().minusDays(3);
        System.out.println("\nSuperheroes added in the last 3 days:");
        network.values().stream()
                .filter(hero -> hero.getAddedDate().isAfter(threeDaysAgo))
                .forEach(hero -> System.out.println("- " + hero.getName()));

        System.out.println("\nTop 3 most connected superheroes:");
        network.values().stream()
                .sorted((a, b) -> Integer.compare(b.getFriends().size(), a.getFriends().size()))
                .limit(3)
                .forEach(hero -> System.out.println("- " + hero.getName() + ": " + hero.getFriends().size() + " friends"));
    }

    private static void findSuperhero(Map<Integer, SuperHero> network) {
        System.out.print("Enter superhero name: ");
        String name = scanner.nextLine().trim().toLowerCase();

        Optional<SuperHero> found = network.values().stream()
                .filter(hero -> hero.getName().equalsIgnoreCase(name))
                .findFirst();

        if (found.isPresent()) {
            SuperHero hero = found.get();
            System.out.println("\nSuperhero '" + hero.getName() + "' was added on: " + hero.getAddedDate());
            System.out.println("Friends:");
            hero.getFriends().forEach(friendId -> {
                SuperHero friend = network.get(friendId);
                if (friend != null) System.out.println("- " + friend.getName());
            });
        } else {
            System.out.println("Superhero '" + name + "' not found.");
        }
    }

    private static void addSuperhero(Map<Integer, SuperHero> network) {
        try {
            System.out.print("Enter superhero name: ");
            String name = scanner.nextLine().trim();

            int newId = network.keySet().stream().max(Integer::compare).orElse(0) + 1;
            LocalDate addedDate = LocalDate.now();

            SuperHero newHero = new SuperHero(newId, name, addedDate);
            network.put(newId, newHero);

            // Ask to connect with existing heroes
            System.out.print("Enter IDs of friends (comma separated): ");
            String friendLine = scanner.nextLine().trim();
            if (!friendLine.isEmpty()) {
                String[] friendIds = friendLine.split(",");
                for (String idStr : friendIds) {
                    int id = Integer.parseInt(idStr.trim());
                    if (network.containsKey(id)) {
                        newHero.addFriend(id);
                        network.get(id).addFriend(newId);
                        appendToCSV(LINKS_FILE, newId + "," + id);
                    } else {
                        System.out.println("ID " + id + " not found. Skipping.");
                    }
                }
            }

            appendToCSV(SUPERHERO_FILE, newId + "," + name + "," + addedDate);
            System.out.println("Superhero '" + name + "' added successfully with ID: " + newId);

        } catch (Exception e) {
            System.out.println("Failed to add superhero: " + e.getMessage());
        }
    }

    private static void appendToCSV(String filePath, String dataLine) {
        try {
            File file = new File(filePath);
            boolean isEmpty = !file.exists() || file.length() == 0;

            try (FileWriter writer = new FileWriter(filePath, true)) {
                if (!isEmpty) {
                    writer.append("\n");
                }
                writer.append(dataLine);
            }
        } catch (Exception e) {
            System.out.println("Error writing to file: " + filePath);
        }
    }

    private static void removeSuperhero(Map<Integer, SuperHero> network) {
        System.out.print("Enter superhero ID to remove: ");
        int idToRemove = Integer.parseInt(scanner.nextLine().trim());

        if (!network.containsKey(idToRemove)) {
            System.out.println("No superhero found with ID: " + idToRemove);
            return;
        }

        // Remove from friends lists of others
        SuperHero toRemove = network.get(idToRemove);
        for (Integer friendId : toRemove.getFriends()) {
            SuperHero friend = network.get(friendId);
            if (friend != null) {
                friend.getFriends().removeIf(f -> f == idToRemove);
            }
        }

        network.remove(idToRemove);
        System.out.println("Superhero with ID " + idToRemove + " removed.");

        rewriteSuperheroesCSV(network);
        rewriteLinksCSV(network);
    }

    private static void rewriteSuperheroesCSV(Map<Integer, SuperHero> network) {
        try (FileWriter writer = new FileWriter(SUPERHERO_FILE, false)) {
            writer.write("id,name,addedDate\n");
            for (SuperHero hero : network.values()) {
                writer.write(hero.getId() + "," + hero.getName() + "," + hero.getAddedDate() + "\n");
            }
        } catch (Exception e) {
            System.out.println("Failed to update superheroes.csv");
        }
    }

    private static void rewriteLinksCSV(Map<Integer, SuperHero> network) {
        try (FileWriter writer = new FileWriter(LINKS_FILE, false)) {
            writer.write("from,to\n");
            Set<String> written = new HashSet<>();
            for (SuperHero hero : network.values()) {
                for (Integer friendId : hero.getFriends()) {
                    String pair = hero.getId() + "," + friendId;
                    String reversePair = friendId + "," + hero.getId();
                    if (!written.contains(pair) && !written.contains(reversePair)) {
                        writer.write(pair + "\n");
                        written.add(pair);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to update links.csv");
        }
    }
}
