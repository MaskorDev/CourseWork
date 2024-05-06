package procedures;

import candidates.CandidateBaldwin;
import candidates.CandidateNanson;

import java.util.*;

public class BaldwinProcedure {
    public static void addCandidatesBaldwin(Scanner scanner, List<CandidateBaldwin> candidates) {
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
            candidates.add(new CandidateBaldwin(name, ranks));
        }
    }

        public static CandidateBaldwin baldwinProcedure(List<CandidateBaldwin> candidates) {
            List<CandidateBaldwin> remainingCandidates = new ArrayList<>(candidates);

            // Повторяем процесс до тех пор, пока не останется один кандидат
            while (remainingCandidates.size() > 1) {
                int[] sums = new int[remainingCandidates.size()];

                // Вычисляем суммы рангов для каждого кандидата
                for (int i = 0; i < remainingCandidates.size(); i++) {
                    CandidateBaldwin candidate = remainingCandidates.get(i);
                    for (int rank : candidate.ranks) {
                        sums[i] += rank;
                    }
                }

                // Находим кандидата с минимальной суммой рангов
                int minSum = Integer.MAX_VALUE;
                int indexToRemove = -1;

                for (int i = 0; i < sums.length; i++) {
                    if (sums[i] < minSum) {
                        minSum = sums[i];
                        indexToRemove = i;
                    }
                }

                // Удаляем найденного кандидата из списка оставшихся кандидатов
                remainingCandidates.remove(indexToRemove);

                // Пересчитываем ранги оставшихся кандидатов
                for (CandidateBaldwin candidate : remainingCandidates) {
                    for (int j = 0; j < candidate.ranks.size(); j++) {
                        int rank = candidate.ranks.get(j);
                        if (rank > 1) {
                            candidate.ranks.set(j, rank - 1);
                        }
                    }                }
            }

            return remainingCandidates.isEmpty() ? null : remainingCandidates.get(0);
        }
}
