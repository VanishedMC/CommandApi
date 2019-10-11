package com.vanishedmc.commandapi.expression;

import org.bukkit.command.CommandSender;

public class ExpressionRest extends Expression<String> {

	@Override
	protected String createMatchRegex() {
		return "<rest>";
	}

	@Override
	public boolean isExpressionFor(String commandExpression) {
		return matchesRegex(commandExpression);
	}

	@Override
	public boolean match(CommandSender sender, String[] p, int index, String enteredCommand) {
		return true;
	}

	@Override
	public Expression<String> get() {
		return new ExpressionRest();
	}

	@Override
	public String parse(CommandSender sender, String[] args, int index, String fullCommand) {
		StringBuilder sb = new StringBuilder();
		for(int i = index; i < args.length; i++){
			sb.append(args[i]).append((i == args.length-1 ? "" : " "));
		}

		return sb.toString();
	}
}
