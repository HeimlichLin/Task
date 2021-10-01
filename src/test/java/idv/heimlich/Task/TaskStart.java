package idv.heimlich.Task;

import org.slf4j.Logger;

import idv.heimlich.Task.common.log.LogFactory;
import idv.heimlich.Task.domain.task.common.TaskLaunch;

public class TaskStart {

	private static Logger LOGGER = LogFactory.getInstance();

	public static void main(String[] args) {
		LOGGER.debug("Test Start");
		TaskLaunch.getLaunch().start();
	}

}
