package com.vanishedmc.commandapi.expression;

import org.bukkit.command.CommandSender;

public class ExpressionConstant extends Expression<String> {

    private final String constant;

    public ExpressionConstant(String constant) {
        this.constant = constant;
    }

    @Override
    protected String createMatchRegex() {
        return "[a-zA-Z]+";
    }

    @Override
    public Expression<String> get() {
        return null;
    }

    @Override
    public boolean isExpressionFor(String commandExpression) {
        return matchesRegex(commandExpression);
    }

    @Override
    public boolean match(CommandSender sender, String[] args, int index, String fullCommand) {
        return constant.equalsIgnoreCase(fullCommand);
    }

    @Override
    public String parse(CommandSender sender, String[] args, int index, String fullCommand) {
        return fullCommand;
    }
}
