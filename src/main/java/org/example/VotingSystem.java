package org.example;

import java.util.*;
import candidates.*;
import procedures.*;

import static procedures.BaldwinProcedure.addCandidatesBaldwin;
import static procedures.WarrenProcedure.warrenProcedure;


public class VotingSystem {
    public void run() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Выберите тип голосования:");
        System.out.println("1. Процедура Нaнсона");
        System.out.println("2. Процедура Уэйра");
        System.out.println("3. Процедура Болдуина");
        System.out.println("4. Процедура Кеммени");
        System.out.println("5. Процедура Янга");
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                List<CandidateNanson> candidatesNanson = new ArrayList<>();
                NansonProcedure.addCandidatesNanson(scanner, candidatesNanson); // Добавляем кандидатов
                CandidateNanson winnerNanson = NansonProcedure.nansonProcedure(candidatesNanson);
                System.out.println("Победитель: " + (winnerNanson != null ? winnerNanson.name : "No winner"));
                break;
            case 2:
                WarrenProcedure.main();
                break;
            case 3:
                List<CandidateBaldwin> candidatesBaldwin = new ArrayList<>();
                addCandidatesBaldwin(scanner, candidatesBaldwin); // Добавляем кандидатов
                CandidateBaldwin winnerBaldwin = BaldwinProcedure.baldwinProcedure(candidatesBaldwin);
                System.out.println("Победитель: " + (winnerBaldwin != null ? winnerBaldwin.name : "No winner"));
                break;
            case 4:
                KemenyProcedure.main();
                break;
            case 5:
                YoungProcedure.main();
                break;
        }

        scanner.close();
    }
}