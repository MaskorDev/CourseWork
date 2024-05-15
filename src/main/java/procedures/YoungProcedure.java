package procedures;


import voter.VoterProfile;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class YoungProcedure {

    public static String youngMethod(List<VoterProfile> voterProfiles, List<String> candidates) {
        int maxSize = 0;
        String winner = "";

        for (String candidate : candidates) {
            int candidateScore = maxSublistSize(voterProfiles, candidate, candidates);
            if (candidateScore > maxSize) {
                maxSize = candidateScore;
                winner = candidate;
            }
        }

        return winner;
    }

    public static int maxSublistSize(List<VoterProfile> voterProfiles, String candidate, List<String> candidates) {
        int maxSize = 0;

        for (int i = 1; i <= voterProfiles.size(); i++) {
            List<List<String>> sublists = combinations(voterProfiles, i);
            for (List<String> sublist : sublists) {
                if (isCandidatePreferred(sublist, candidate, candidates)) {
                    maxSize = Math.max(maxSize, sublist.size());
                }
            }
        }

        return maxSize;
    }

    public static boolean isCandidatePreferred(List<String> voterPreferences, String candidate, List<String> candidates) {
        for (String other : candidates) {
            if (!candidate.equals(other)) {
                int preferredCount = 0;
                for (String preference : voterPreferences) {
                    if (preference.equals(candidate)) {
                        preferredCount++;
                    } else if (preference.equals(other)) {
                        break;
                    }
                }
                if (preferredCount <= voterPreferences.size() / 2) {
                    return false;
                }
            }
        }
        return true;
    }

    // Generate all combinations of a list
    public static List<List<String>> combinations(List<VoterProfile> voterProfiles, int length) {
        List<List<String>> result = new ArrayList<>();
        if (length == 0) {
            result.add(new ArrayList<>());
            return result;
        }
        if (voterProfiles.isEmpty()) {
            return result;
        }
        List<String> profile = voterProfiles.get(0).preferences;
        List<VoterProfile> rest = voterProfiles.subList(1, voterProfiles.size());
        List<List<String>> withElement = combinations(rest, length - 1);
        for (List<String> sublist : withElement) {
            List<String> combined = new ArrayList<>();
            combined.addAll(sublist);
            combined.addAll(profile);
            result.add(combined);
        }
        result.addAll(combinations(rest, length));
        return result;
    }

    public static void main() {
        Scanner scanner = new Scanner(System.in);
        List<VoterProfile> voterProfiles = new ArrayList<>();
        List<String> candidates = new ArrayList<>();

        System.out.println("Сколько кандидатов вы хотите добавить?");
        int numOfCandidates = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        for (int i = 1; i <= numOfCandidates; i++) {
            System.out.println("Введите имя кандидата " + i + ":");
            String candidateName = scanner.nextLine();
            candidates.add(candidateName);
        }

        System.out.println("Введите количество избирателей:");
        int numOfVoters = scanner.nextInt();
        scanner.nextLine(); // Consume newline
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

        String winner = youngMethod(voterProfiles, candidates);
        System.out.println("Победитель: " + (winner.isEmpty() ? "No winner" : winner));
    }
}
