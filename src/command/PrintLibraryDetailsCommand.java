package command;

import model.Branch;
import service.LibraryService;

import java.util.List;

public class PrintLibraryDetailsCommand implements Command {
    private final LibraryService libraryService;

    public PrintLibraryDetailsCommand(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @Override
    public void execute(List<String> args) {
        if (!args.isEmpty()) {
            throw new IllegalArgumentException("Usage: PRINT-LIBRARY");
        }
        var library = libraryService.getLibrary();
        System.out.println(library);
        for (Branch branch : library.getBranches()) {
            System.out.println(branch);
        }
    }
}
