package com.vanishedmc.commandapi;

class UnknownExpressionException extends Exception {

	private static final long serialVersionUID = 1L;

	private final String parameter;

	UnknownExpressionException(String parameter){
		this.parameter = parameter;
	}

	@Override
	public void printStackTrace() {
		super.printStackTrace();
		System.out.println("The Object/Parameter " + parameter + " should have been something else.");
	}

}
