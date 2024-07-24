package command.branch;

import command.Command;
import model.BranchResponse;
import model.Branch;
import service.LibraryService;
import java.util.List;

public class PrintBranchCommand implements Command {
    private final LibraryService libraryService;

    public PrintBranchCommand(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @Override
    public void execute(List<String> args) throws Exception {
        if (args.isEmpty()) {
            List<Branch> branches = libraryService.getAllBranches();
            for (int i = 0; i < branches.size(); i++) {
                printBranchDetails(branches.get(i));
                if (i != branches.size() - 1)
                    System.out.println();
            }
        } else if (args.size() == 1) {
            String branchId = args.get(0);
            Branch branch = libraryService.getBranch(branchId);
            printBranchDetails(branch);
        } else {
            throw new IllegalArgumentException("Usage: PRINT-BRANCH [branchId]");
        }
    }

    private void printBranchDetails(Branch branch) {
        System.out.println(new BranchResponse(branch.getId(), branch.getName(), branch.getAddress(), branch.getBookItems().size()));
    }
}
