package procedures;

import voter.VoterProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class KemenyProcedure {

    public static List<String> kemenyMethod(List<VoterProfile> voterProfiles, List<String> candidates) {
        List<List<String>> allOrderings = generatePermutations(candidates);
        List<String> bestOrdering = new ArrayList<>();
        int maxScore = Integer.MIN_VALUE;

        for (List<String> ordering : allOrderings) {
            int score = calculateKemenyScore(voterProfiles, ordering);
            if (score > maxScore) {
                maxScore = score;
                bestOrdering = ordering;
            }
        }

        return bestOrdering;
    }

    public static int calculateKemenyScore(List<VoterProfile> voterProfiles, List<String> ordering) {
        int score = 0;
        for (int i = 0; i < ordering.size(); i++) {
            for (int j = i + 1; j < ordering.size(); j++) {
                String x = ordering.get(i);
                String y = ordering.get(j);
                score += countVotersPreferring(voterProfiles, x, y);
            }
        }
        return score;
    }

    public static int countVotersPreferring(List<VoterProfile> voterProfiles, String x, String y) {
        int count = 0;
        for (VoterProfile profile : voterProfiles) {
            if (profile.preferences.indexOf(x) < profile.preferences.indexOf(y)) {
                count++;
            }
        }
        return count;
    }

    // Generates all permutations of a list
    public static <T> List<List<T>> generatePermutations(List<T> list) {
        List<List<T>> permutations = new ArrayList<>();
        if (list.size() == 1) {
            permutations.add(list);
            return permutations;
        }
        for (int i = 0; i < list.size(); i++) {
            T element = list.get(i);
            List<T> sublist = new ArrayList<>(list);
            sublist.remove(i);
            for (List<T> perm : generatePermutations(sublist)) {
                List<T> permutation = new ArrayList<>();
                permutation.add(element);
                permutation.addAll(perm);
                permutations.add(permutation);
            }
        }
        return permutations;
    }

    public static void main() {
        Scanner scanner = new Scanner(System.in);
        List<VoterProfile> voterProfiles = new ArrayList<>();
        List<String> candidates = new ArrayList<>();

        System.out.println("Введите количество кандидатов:");
        int numOfCandidates = scanner.nextInt();
        scanner.nextLine(); // Съедаем символ новой строки
        for (int i = 1; i <= numOfCandidates; i++) {
            System.out.println("Введите имя кандидата " + i + ":");
            String candidateName = scanner.nextLine();
            candidates.add(candidateName);
        }

        System.out.println("Введите количество избирателей:");
        int numOfVoters = scanner.nextInt();
        scanner.nextLine(); // Съедаем символ новой строки
        for (int i = 1; i <= numOfVoters; i++) {
            List<String> preferences = new ArrayList<>();
            System.out.println("Введите предпочтения для избирателя " + i + ":");
            for (int j = 0; j < numOfCandidates; j++) {
                System.out.println("Введите предпочтение " + (j + 1) + ":");
                preferences.add(scanner.nextLine());
            }
            voterProfiles.add(new VoterProfile(preferences));
        }

        scanner.close();

        List<String> bestOrdering = kemenyMethod(voterProfiles, candidates);
        System.out.println("Лучшее упорядочение по методу Кемени: " + bestOrdering);
    }
}
