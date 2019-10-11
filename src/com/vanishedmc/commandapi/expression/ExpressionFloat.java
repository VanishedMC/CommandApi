package com.vanishedmc.commandapi.expression;

import org.bukkit.command.CommandSender;

public class ExpressionFloat extends ExpressionPrimitive<Float> {

	@Override
	protected String createMatchRegex() {
		return "<float>";
	}

	@Override
	public boolean isExpressionFor(String commandExpression) {
		return matchesRegex(commandExpression);
	}

	@Override
	public Expression<Float> get() {
		return new ExpressionFloat();
	}

	@Override
	public boolean match(CommandSender sender, String[] p, int index, String fullCommand) {
		try {
			Float.parseFloat(fullCommand.replace(',', '.'));
			return true;
		} catch(Exception e){
			return false;
		}
	}

	@Override
	public Float parse(CommandSender sender, String[] args, int index, String fullCommand) {
		return Float.parseFloat(fullCommand.replace(',', '.'));
	}
}
