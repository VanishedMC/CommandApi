package com.vanishedmc.commandapi;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class CommandProcessor implements org.bukkit.command.CommandExecutor {

    private CommandExecutor executor;

    CommandProcessor(CommandExecutor executor) {
        this.executor = executor;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(args.length == 0 && executor.getDefaultMethod() != null) {
            CommandMethod defaultMethod = executor.getDefaultMethod();
            if(defaultMethod.getPermission().length() > 0 && !sender.hasPermission(defaultMethod.getPermission())) {
                sender.sendMessage(cmd.getPermissionMessage());
                return true;
            }
            executor.getDefaultMethod().run(sender, args);
            return true;
        }

        for(CommandMethod method : executor.getMethods()) {
            if(method.match(sender, args)) {
                if(method.getPermission().length() > 0) {
                    if(!sender.hasPermission(method.getPermission())) {
                        cmd.getPermissionMessage();
                        return true;
                    }
                }

                method.run(sender, args);
                return true;
            }
        }
        return false;
    }
}
