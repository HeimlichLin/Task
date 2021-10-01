package idv.heimlich.Task.domain.task;

import idv.heimlich.Task.domain.code.ExtractTasks;
import idv.heimlich.Task.domain.service.impl.PeriodTaskService;

public class CheckTaskStatusSct {

	public static void main(final String[] args) {
		PeriodTaskService service = new PeriodTaskService("CheckTaskStatusService");
		final ExtractTasks task = ExtractTasks.clCheckTaskStatus;
		service.addPeriodTask(task.procNo, task.period);
		service.start();
		service = null;

	}

}
