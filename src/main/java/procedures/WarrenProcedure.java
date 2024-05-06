package procedures;

import candidates.CandidateNanson;
import candidates.CandidateWarren;

import java.util.*;


public class WarrenProcedure {

    public static void main() {
        Scanner scanner = new Scanner(System.in);

        List<CandidateWarren> candidates = new ArrayList<>();
        List<List<String>> voterPreferences = new ArrayList<>();

        // Получение данных о кандидатах
        System.out.println("Введите количество кандидатов:");
        int numOfCandidates = scanner.nextInt();
        scanner.nextLine(); // Очистка буфера
        for (int i = 1; i <= numOfCandidates; i++) {
            System.out.println("Введите имя кандидата " + i + ":");
            String candidateName = scanner.nextLine();
            candidates.add(new CandidateWarren(candidateName));
        }

        // Получение предпочтений избирателей
        System.out.println("Введите количество избирателей:");
        int numOfVoters = scanner.nextInt();
        scanner.nextLine(); // Очистка буфера
        for (int i = 1; i <= numOfVoters; i++) {
            List<String> preferences = new ArrayList<>();
            System.out.println("Введите предпочтения избирателя " + i + ":");
            for (int j = 0; j < numOfCandidates; j++) {
                System.out.println("Введите предпочтение " + (j + 1) + ":");
                preferences.add(scanner.nextLine());
            }
            voterPreferences.add(preferences);
        }

        scanner.close();

        // Вызов процедуры Уэйра
        CandidateWarren winner = warrenProcedure(candidates, voterPreferences);
        System.out.println("Победитель: " + (winner != null ? winner.name : "Нет победителя"));
    }

    public static CandidateWarren warrenProcedure(List<CandidateWarren> candidates, List<List<String>> voterPreferences) {
        List<CandidateWarren> remainingCandidates = new ArrayList<>(candidates);

        while (true) {
            int[] totalVotes = new int[remainingCandidates.size()];

            // Подсчет голосов
            for (List<String> voterPreference : voterPreferences) {
                for (String candidateName : voterPreference) {
                    for (CandidateWarren candidate : remainingCandidates) {
                        if (candidate.name.equals(candidateName)) {
                            totalVotes[remainingCandidates.indexOf(candidate)]++;
                            break;
                        }
                    }
                }
            }

            int totalVoters = voterPreferences.size();
            int quota = totalVoters / 2 + 1;

            // Проверка наличия победителя
            for (int i = 0; i < totalVotes.length; i++) {
                if (totalVotes[i] >= quota) {
                    return remainingCandidates.get(i);
                }
            }

            // Удаление кандидатов с наименьшим числом голосов
            int minVotes = Integer.MAX_VALUE;
            for (int votes : totalVotes) {
                minVotes = Math.min(minVotes, votes);
            }
            if (minVotes == Integer.MAX_VALUE) {
                break;
            }

            List<CandidateWarren> candidatesToRemove = new ArrayList<>();
            for (int i = 0; i < totalVotes.length; i++) {
                if (totalVotes[i] == minVotes) {
                    candidatesToRemove.add(remainingCandidates.get(i));
                }
            }
            remainingCandidates.removeAll(candidatesToRemove);
        }

        return null;
    }
}