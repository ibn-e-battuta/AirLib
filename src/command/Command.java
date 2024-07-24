package command;

import exception.NonEmptyBranchException;

import java.util.List;

public interface Command {
    void execute(List<String> args) throws Exception, NonEmptyBranchException;
}
