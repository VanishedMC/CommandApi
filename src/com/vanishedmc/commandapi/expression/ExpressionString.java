package com.vanishedmc.commandapi.expression;

import org.bukkit.command.CommandSender;

public class ExpressionString extends ExpressionPrimitive<String> {

	public ExpressionString() {}

	@Override
	protected String createMatchRegex() {
		return "<string>";
	}

	@Override
	public boolean isExpressionFor(String commandExpression) {
		return matchesRegex(commandExpression);
	}

	@Override
	public Expression<String> get() {
		return new ExpressionString();
	}

	@Override
	public boolean match(CommandSender sender, String[] p, int index, String enteredCommand) {
		return true;
	}

	@Override
	public String parse(CommandSender sender, String[] args, int index, String fullCommand) {
		return fullCommand;
	}
}
