package com.vanishedmc.commandapi.expression;

import java.util.regex.Pattern;

import org.bukkit.command.CommandSender;

abstract public class Expression<T> {

	private Pattern matchRegex;
		
	public Expression() {
		this.matchRegex = Pattern.compile(createMatchRegex());
	}
	
	abstract protected String createMatchRegex();
	public abstract Expression<T> get();
	
	abstract public boolean isExpressionFor(String commandExpression);
	abstract public boolean match(CommandSender sender, String[] args, int index, String fullCommand);
	abstract public T parse(CommandSender sender, String[] args, int index, String fullCommand);

	public boolean matchesRegex(String val){
		return matchRegex.matcher(val).find();
	}

}
