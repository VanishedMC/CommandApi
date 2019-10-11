package com.vanishedmc.commandapi;

import com.vanishedmc.commandapi.annotation.Command;
import com.vanishedmc.commandapi.annotation.DefaultCommand;

import java.util.List;
import java.util.Arrays;
import java.lang.reflect.Method;
import java.util.stream.Collectors;

class CommandHelper {

	static List<Method> getCommandMethods(Object object){
		return Arrays.stream(object.getClass().getDeclaredMethods()).filter(method -> method.isAnnotationPresent(Command.class)).collect(Collectors.toList());
	}

	static Method getDefaultMethod(Object object) {
		return Arrays.stream(object.getClass().getDeclaredMethods()).filter(method -> method.isAnnotationPresent(DefaultCommand.class)).findFirst().orElse(null);
	}

}
