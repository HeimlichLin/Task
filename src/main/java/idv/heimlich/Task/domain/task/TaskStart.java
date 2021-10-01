package idv.heimlich.Task.domain.task;

import idv.heimlich.Task.domain.task.common.TaskLaunch;

/**
 * 任務啟動
 */
public class TaskStart {

	public static void main(final String[] args) {
		TaskLaunch.getLaunch().start();
	}

}
