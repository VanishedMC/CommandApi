package com.vanishedmc.commandapi.expression;

import org.bukkit.command.CommandSender;

public class ExpressionDouble extends ExpressionPrimitive<Double> {

	@Override
	protected String createMatchRegex() {
		return "<double>";
	}

	@Override
	public boolean isExpressionFor(String commandExpression) {
		return matchesRegex(commandExpression);
	}

	@Override
	public Expression<Double> get() {
		return new ExpressionDouble();
	}

	@Override
	public boolean match(CommandSender sender, String[] p, int index, String fullCommand) {
		try {
			Double.parseDouble(fullCommand.replace(',', '.'));
			return true;
		} catch(Exception e){
			return false;
		}
	}

	@Override
	public Double parse(CommandSender sender, String[] args, int index, String fullCommand) {
		return Double.parseDouble(fullCommand.replace(',', '.'));
	}
}
