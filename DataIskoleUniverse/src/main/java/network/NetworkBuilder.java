package network;

import java.io.BufferedReader;
import java.io.FileReader;
import java.time.LocalDate;
import java.util.*;

public class NetworkBuilder {
    private Map<Integer, SuperHero> network = new HashMap<>();

    public void loadSuperheroes(String filepath) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String line;
        bufferedReader.readLine(); // skip header

        while ((line = bufferedReader.readLine()) != null) {
            String[] parts = line.split(",");
            int id = Integer.parseInt(parts[0].trim());
            String name = parts[1].trim();
            LocalDate addedDate = LocalDate.parse(parts[2].trim());

            network.put(id, new SuperHero(id, name, addedDate));
        }

        bufferedReader.close();
    }

    public void loadLinks(String filepath) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));
        String line;
        bufferedReader.readLine(); // skip header

        while ((line = bufferedReader.readLine()) != null) {
            String[] parts = line.split(",");
            int from = Integer.parseInt(parts[0].trim());
            int to = Integer.parseInt(parts[1].trim());

            if (network.containsKey(from) && network.containsKey(to)) {
                network.get(from).addFriend(to);
                network.get(to).addFriend(from); // friendship is bidirectional
            }
        }

        bufferedReader.close();
    }

    public Map<Integer, SuperHero> getNetwork() {
        return network;
    }


}
