package idv.heimlich.Task.domain.task;

import org.apache.commons.lang.StringUtils;

import idv.heimlich.Task.domain.task.common.TaskLaunch;

/**
 * 任務中斷
 */
public class TaskInterrupt {

	private static final int TIME_OUT = 1800;

	public static void main(final String[] args) {
		int timeout = TIME_OUT;
		if (args.length > 0) {
			final String value = args[0];
			if (StringUtils.isNumeric(value)) {
				timeout = Integer.parseInt(value);
			}
		}
		System.out.println("TaskInterrupt timeout:" + timeout);
		TaskLaunch.getLaunch().terminate(timeout);
	}

}
