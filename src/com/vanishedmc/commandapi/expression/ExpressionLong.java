package com.vanishedmc.commandapi.expression;

import org.bukkit.command.CommandSender;

public class ExpressionLong extends ExpressionPrimitive<Long> {

	@Override
	protected String createMatchRegex() {
		return "<double>";
	}

	@Override
	public boolean isExpressionFor(String commandExpression) {
		return matchesRegex(commandExpression);
	}

	@Override
	public Expression<Long> get() {
		return new ExpressionLong();
	}

	@Override
	public boolean match(CommandSender sender, String[] p, int index, String fullCommand) {
		try {
			Long.parseLong(fullCommand);
			return true;
		} catch(Exception e){
			return false;
		}
	}

	@Override
	public Long parse(CommandSender sender, String[] args, int index, String fullCommand) {
		return Long.parseLong(fullCommand);
	}
}
