package command.patron;

import java.util.List;

import command.Command;
import response.BookResponse;
import service.BookRecommendationService;
import util.Logger;

public class GetBookRecommendationsCommand implements Command {
    private final BookRecommendationService bookRecommendationService;
    private final Logger logger;

    public GetBookRecommendationsCommand(BookRecommendationService recommendationService, Logger logger) {
        this.bookRecommendationService = recommendationService;
        this.logger = logger;
    }

    @Override
    public void execute(final List<String> args) {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: GET-RECOMMENDATIONS [patronId]");
        }

        final String patronId = args.getFirst();

        final List<BookResponse> bookResponses = bookRecommendationService.getBookRecommendations(patronId);

        logger.info("Recommended books :");
        bookResponses.forEach(b -> logger.info(b.toString()));
    }
}
