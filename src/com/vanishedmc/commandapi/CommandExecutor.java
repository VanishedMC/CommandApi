package com.vanishedmc.commandapi;

import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Method;
import com.vanishedmc.commandapi.annotation.Command;
import com.vanishedmc.commandapi.annotation.DefaultCommand;
import com.vanishedmc.commandapi.expression.Expression;
import com.vanishedmc.commandapi.expression.ExpressionRest;
class CommandExecutor {

	private Object executor;
	private CommandMethod defaultMethod;
	private List<CommandMethod> methods = new ArrayList<>();
	
	CommandExecutor(Object o) throws UnknownExpressionException {
		executor = o;
		List<Method> methods = CommandHelper.getCommandMethods(o);
		if(CommandHelper.getDefaultMethod(o) != null) {
			DefaultCommand annotation = CommandHelper.getDefaultMethod(o).getAnnotation(DefaultCommand.class);
			defaultMethod = new CommandMethod(this, CommandHelper.getDefaultMethod(o), annotation.allowedSender(), "", annotation.permission());
		}

		for(Method m : methods){
			Command annotation = m.getAnnotation(Command.class);

			AllowedSender allowedSender = annotation.allowedSender();
			String syntax = annotation.syntax();

			this.methods.add(new CommandMethod(this, m, allowedSender, syntax, annotation.permission()));
		}

		this.methods = sortMethods(this.methods);
	}

	List<CommandMethod> getMethods() {
		return methods;
	}

	private static List<CommandMethod> sortMethods(List<CommandMethod> methods) {
		List<CommandMethod> noRest = new ArrayList<>(), withRest = new ArrayList<>();

		loop: for (CommandMethod method : methods) {
			for (Expression expression : method.getExpressions()) {
				if(expression instanceof ExpressionRest) {
					withRest.add(method);
					continue loop;
				}
			}
			noRest.add(method);
		}

		List<CommandMethod> finalOrder = new ArrayList<>();
		finalOrder.addAll(noRest);
		finalOrder.addAll(withRest);

		return finalOrder;
	}

	Object getExecutor() {
		return executor;
	}

	CommandMethod getDefaultMethod() {
		return defaultMethod;
	}
}

