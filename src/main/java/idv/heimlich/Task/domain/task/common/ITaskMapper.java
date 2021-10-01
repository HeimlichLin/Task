package idv.heimlich.Task.domain.task.common;

import idv.heimlich.Task.domain.task.AbtractTask;

public interface ITaskMapper {

	String getProcNo();

	Class<? extends AbtractTask> getTaskClass();

}
