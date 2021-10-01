package idv.heimlich.Task.domain.task.common;

public class TaskContextFactory {

	public static TaskContext getContext(final String procNo) {
		return new TaskFileContext(
				procNo.replaceAll("/", "_").replace("\\", "_"));
	}

}
