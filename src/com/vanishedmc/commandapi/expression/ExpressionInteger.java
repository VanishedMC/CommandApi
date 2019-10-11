package com.vanishedmc.commandapi.expression;

import org.bukkit.command.CommandSender;

public class ExpressionInteger extends ExpressionPrimitive<Integer> {

	@Override
	protected String createMatchRegex() {
		return "<int>";
	}

	@Override
	public boolean isExpressionFor(String commandExpression) {
		return matchesRegex(commandExpression);
	}

	@Override
	public Expression<Integer> get() {
		return new ExpressionInteger();
	}

	@Override
	public boolean match(CommandSender sender, String[] p, int index, String enteredCommand) {
		try {
			Integer.parseInt(enteredCommand);
			return true;
		} catch(Exception e){
			return false;
		}
	}

	@Override
	public Integer parse(CommandSender sender, String[] args, int index, String fullCommand) {
		return Integer.parseInt(fullCommand);
	}
}
