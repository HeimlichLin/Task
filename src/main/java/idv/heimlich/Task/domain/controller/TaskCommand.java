package idv.heimlich.Task.domain.controller;

import idv.heimlich.Task.common.exception.ApBusinessException;
import idv.heimlich.Task.domain.task.common.TaskLaunch;

public class TaskCommand {

	private static final String UP = "UP";
	private static final String DOWN = "DOWN";
	private static final String ON = "ON";

	private static final int TIME_OUT = 300;

	public static void main(final String[] args) {
		if (args.length == 0) {
			throw new IllegalArgumentException("args only on/down");
		}

		final String action = args[0];

		if (action.equalsIgnoreCase(ON) || action.equalsIgnoreCase(UP)) {
			System.out.println(
					"call " + action + " Launch time ... Don't  Stop ");
			TaskLaunch.getLaunch().start();
		} else if (action.equalsIgnoreCase(DOWN)) {
			System.out.println("call " + action + " Launch time time_out :"
					+ TIME_OUT + " ... Don't  Stop ");
			TaskLaunch.getLaunch().terminate(TIME_OUT);
		} else {
			throw new ApBusinessException("Undefined ACTION:" + action);
		}
	}

}
