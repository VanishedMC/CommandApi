package com.vanishedmc.commandapi.expression;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.regex.Pattern;

public class ExpressionPlayer extends Expression<Player> {

    private Pattern playerRegex = Pattern.compile("[a-zA-Z1-9_]{2,16}");

	@Override
	protected String createMatchRegex() {
		return "<player>";
	}

	@Override
	public boolean isExpressionFor(String commandExpression) {
		return matchesRegex(commandExpression);
	}

	@Override
	public Expression<Player> get() {
		return new ExpressionPlayer();
	}

	@Override
	public boolean match(CommandSender sender, String[] p, int index, String enteredCommand) {
		if(playerRegex.matcher(enteredCommand).find()){
			Player player = Bukkit.getPlayerExact(enteredCommand);
			return player != null;
		}
		return false;
	}

	@Override
	public Player parse(CommandSender sender, String[] args, int index, String fullCommand) {
		return Bukkit.getPlayerExact(fullCommand);
	}
}
