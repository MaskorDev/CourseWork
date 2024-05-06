package procedures;

import candidates.*;

import java.util.*;

public class NansonProcedure {
    public static void addCandidatesNanson(Scanner scanner, List<CandidateNanson> candidates) {
        System.out.println("Сколько кандидатов вы хотите добавить?");
        int numOfCandidates = scanner.nextInt();

        for (int i = 0; i < numOfCandidates; i++) {
            System.out.println("Введите имя кандидата " + (i + 1) + ": ");
            String name = scanner.next();
            System.out.println("Введите ранги кандидата " + name + " через пробел:");
            List<Integer> ranks = new ArrayList<>();
            for (int j = 0; j < numOfCandidates - 1; j++) {
                ranks.add(scanner.nextInt());
            }
            System.out.println("Кандидат " + name + " добавлен с рангами: " + ranks);
            candidates.add(new CandidateNanson(name, ranks));
        }
    }

    public static CandidateNanson nansonProcedure(List<CandidateNanson> candidates) {
        Map<CandidateNanson, Integer> sums = new HashMap<>();

        for (CandidateNanson candidate : candidates) {
            int sum = 0;
            for (int rank : candidate.ranks) {
                sum += rank;
            }
            sums.put(candidate, sum);
        }

        List<CandidateNanson> remainingCandidates = new ArrayList<>(candidates);
        CandidateNanson winner = null;

        while (remainingCandidates.size() > 1) {
            double average = sums.values().stream().mapToInt(Integer::intValue).average().orElse(0);

            remainingCandidates.removeIf(candidate -> sums.get(candidate) <= average);

            if (remainingCandidates.size() == 1) {
                winner = remainingCandidates.get(0);
                break;
            }

            sums.clear();

            for (CandidateNanson candidate : remainingCandidates) {
                int sum = 0;
                for (int rank : candidate.ranks) {
                    sum += rank;
                }
                sums.put(candidate, sum);
            }
        }

        // Установка победителя, если остался только один кандидат
        if (remainingCandidates.size() == 1) {
            winner = remainingCandidates.get(0);
        }

        if (winner == null) {
            throw new IllegalStateException("No winner found");
        }

        return winner;
    }
}
