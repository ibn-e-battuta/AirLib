package command;

import model.Book;
import model.Patron;
import service.PatronService;
import service.RecommendationService;

import java.util.List;
import java.util.Scanner;

public class GetRecommendationsCommand implements Command {
    private final RecommendationService recommendationService;
    private final PatronService patronService;

    public GetRecommendationsCommand(RecommendationService recommendationService, PatronService patronService) {
        this.recommendationService = recommendationService;
        this.patronService = patronService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: GET-RECOMMENDATIONS <patronId>");
        }

        String patronId = args.get(0);
        Patron patron = patronService.getPatron(patronId);

        if (patron == null) {
            System.out.println("Patron not found.");
            return;
        }

        List<Book> recommendations = recommendationService.getRecommendations(patron);
        System.out.println("Recommended books for " + patron.getName() + ":");
        for (Book book : recommendations) {
            System.out.println(book.getTitle() + " by " + book.getAuthor());
        }
    }
}
