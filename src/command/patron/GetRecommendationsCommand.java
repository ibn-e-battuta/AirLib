package command.patron;

import command.Command;
import service.RecommendationService;
import util.Logger;

import java.util.List;

public class GetRecommendationsCommand implements Command {
    private final RecommendationService _recommendationService;
    private final Logger _logger;

    public GetRecommendationsCommand(RecommendationService recommendationService, Logger logger) {
        _recommendationService = recommendationService;
        _logger = logger;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.size() != 1) {
            throw new IllegalArgumentException("Usage: GET-RECOMMENDATIONS [patronId]");
        }

        var patronId = args.get(0);

        var bookResponses = _recommendationService.getRecommendations(patronId);
        _logger.info("Recommended books :");
        for (var bookResponse : bookResponses) {
            _logger.info(bookResponse.toString());
        }
    }
}
