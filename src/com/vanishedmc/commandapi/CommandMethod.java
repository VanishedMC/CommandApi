package com.vanishedmc.commandapi;

import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Method;
import org.bukkit.command.CommandSender;
import java.lang.reflect.InvocationTargetException;
import com.vanishedmc.commandapi.expression.Expression;
import com.vanishedmc.commandapi.expression.ExpressionRest;
import com.vanishedmc.commandapi.expression.ExpressionConstant;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

class CommandMethod {

    private String permission;
    private final Method method;
    private AllowedSender allowedSender;
    private final CommandExecutor executor;
    private Expression[] expressions = new Expression[0];
	
	CommandMethod(CommandExecutor executor, Method method, AllowedSender allowedSender, String syntax, String permission) throws UnknownExpressionException {
	    this.method = method;
        this.executor = executor;
        this.permission = permission;
        this.allowedSender = allowedSender;

		ArrayList<Expression> expressions = new ArrayList<>();
        String[] split = syntax.split(" ");

        if(syntax.length() <= 0) {
            return;
        }

		if(split.length >= 1) {
			for (String expression : split) {
			    if(expression.startsWith("<")) {
                    Expression ex = CommandManager.instance.GetExpression(expression);

                    if(ex == null){
                        throw new UnknownExpressionException(expression);
                    } else {
                        expressions.add(ex);
                    }
                } else {
                    ExpressionConstant constant = new ExpressionConstant(expression);
                    expressions.add(constant);
                }
            }

			this.expressions = expressions.toArray(this.expressions);
		}
	}

	boolean match(CommandSender sender, String[] split){
	    if(allowedSender == AllowedSender.PLAYER && !(sender instanceof Player)) {
	        return false;
        }

        if(allowedSender == AllowedSender.CONSOLE && !(sender instanceof ConsoleCommandSender)) {
            return false;
        }

		if(split.length == getExpressions().length){
			for(int i = 0; i < getExpressions().length; i++){
				if(!getExpressions()[i].match(sender, split, i, split[i])) {
					return false;
				}
			}

			return true;
		}
		
		if(split.length > expressions.length){
			if(expressions[expressions.length-1] instanceof ExpressionRest){
				for(int i = 0; i < expressions.length-1; i++){
					if(!expressions[i].match(sender, split, i, split[i])){
						return false;
					}
				}

				return true;
			}
		}
		
		return false;
	}
	
	void run(CommandSender sender, String[] arguments){
		Object[] paramaters = new Object[getExpressionWithoutConstant().length+1];
		paramaters[0] = sender;

		int offset = 0;

        for (int i = 0; i < getExpressions().length; i++) {
            Expression expression = getExpressions()[i];
            if(expression instanceof ExpressionConstant) {
                offset++;
                continue;
            }

            paramaters[i+1-offset] = expression.parse(sender, arguments, i, arguments[i]);
        }

		try {
			method.invoke(executor.getExecutor(), paramaters);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			System.out.println(" == FATAL ERROR: Something went wrong while passing parameters to executor objects.");
			e.printStackTrace();
		}
	}

	private Expression[] getExpressionWithoutConstant() {
	    List<Expression> list = new ArrayList<>();
        for (Expression expression : getExpressions()) {
            if(!(expression instanceof ExpressionConstant)) {
                list.add(expression);
            }
        }
        return list.toArray(new Expression[]{});
    }

    String getPermission() { return permission; }
    Expression[] getExpressions() { return expressions; }
}
