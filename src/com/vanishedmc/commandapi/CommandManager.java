package com.vanishedmc.commandapi;

import java.util.List;
import org.bukkit.Bukkit;
import java.util.ArrayList;
import java.lang.reflect.Field;
import org.bukkit.plugin.Plugin;
import org.bukkit.command.CommandMap;
import java.lang.reflect.Constructor;
import org.bukkit.command.PluginCommand;
import com.vanishedmc.commandapi.expression.*;
import java.lang.reflect.InvocationTargetException;

public class CommandManager {

	static CommandManager instance = null;

	private Plugin plugin;
	private List<Class> commandClasses;

	private CommandMap map;
	private Constructor<PluginCommand> pluginCommandConstructor;

	private List<Expression> expressions;

	private CommandManager(Plugin plugin) {
		instance = this;
		this.plugin = plugin;
		this.commandClasses = new ArrayList<>();
		this.expressions = new ArrayList<>();

		loadExpressions();

		try {
			pluginCommandConstructor = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
			pluginCommandConstructor.setAccessible(true);

			Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			bukkitCommandMap.setAccessible(true);

			map = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());
		} catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException e) {
			plugin.getLogger().severe("Unable to load command map!");
			e.printStackTrace();

			instance = null;
		}
	}

	public static void RegisterExpression(Expression expression) {
		CommandManager manager = CommandManager.instance;
		if(manager == null) {
			throw new RuntimeException("Attempted to register an expression without initializing the command manager!");
		}

		manager.expressions.add(expression);
	}

	private void loadExpressions() {
		expressions.add(new ExpressionInteger());
		expressions.add(new ExpressionDouble());
		expressions.add(new ExpressionFloat());
		expressions.add(new ExpressionLong());

		expressions.add(new ExpressionString());
		expressions.add(new ExpressionPlayer());
		expressions.add(new ExpressionRest());
	}

	Expression GetExpression(String key) {
		for (Expression expression : expressions) {
			if(expression.isExpressionFor(key)) {
				return expression.get();
			}
		}
		return null;
	}

	public static void RegisterExecutor(String command, Object commandClass, String usage, String description, String permission, String permissionMessage){
		CommandManager manager = CommandManager.instance;
		if(manager == null) {
			throw new RuntimeException("Attempted to register a command without initializing the command manager!");
		}

		if(manager.commandClasses.contains(commandClass.getClass())) {
			throw new RuntimeException("CommandClass " + commandClass.getClass().getName() + " is already registered!");
		}

		try {
			CommandProcessor processor = new CommandProcessor(new CommandExecutor(commandClass));
			PluginCommand pluginCommand = manager.pluginCommandConstructor.newInstance(command, manager.plugin);

			pluginCommand.setUsage(usage);
			pluginCommand.setExecutor(processor);
			pluginCommand.setPermission(permission);
			pluginCommand.setDescription(description);
			pluginCommand.setPermissionMessage(permissionMessage);

			manager.commandClasses.add(commandClass.getClass());

			CommandManager.instance.map.register(command, pluginCommand);
		} catch (UnknownExpressionException | InvocationTargetException | InstantiationException | IllegalAccessException e ) {
			e.printStackTrace();
		}
	}

	public static void Initialize(Plugin plugin) {
		new CommandManager(plugin);
	}
}
