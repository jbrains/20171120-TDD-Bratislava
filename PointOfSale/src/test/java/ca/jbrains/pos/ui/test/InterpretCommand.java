package ca.jbrains.pos.ui.test;

public interface InterpretCommand {
    // CONTRACT: the command text is "normalized"
    void interpretCommand(String text);
}
