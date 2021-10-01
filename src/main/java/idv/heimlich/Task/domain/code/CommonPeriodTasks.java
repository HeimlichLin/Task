package idv.heimlich.Task.domain.code;

import idv.heimlich.Task.domain.task.AbtractTask;
import idv.heimlich.Task.domain.task.common.ITaskInfo;
import idv.heimlich.Task.domain.task.common.ITaskMapper;

/**
 * 週期任務表
 */
public enum CommonPeriodTasks implements ITaskMapper, ITaskInfo {

	;//

	final String procNo;
	final Long period;
	final Class<? extends AbtractTask> taskClass;
	final String procName;

	/**
	 *
	 * @param procNo
	 * @param period
	 * @param taskClass
	 */
	private CommonPeriodTasks(final Class<? extends AbtractTask> taskClass, String procName, final Long period) {

		this.taskClass = taskClass;
		this.period = period;
		this.procNo = this.name();
		this.procName = procName;

	}

	@Override
	public String getProcNo() {
		return this.procNo;
	}

	public Long getPeriod() {
		return this.period;
	}

	@Override
	public Class<? extends AbtractTask> getTaskClass() {
		return this.taskClass;
	}

	@Override
	public String getProcName() {
		return this.procName;
	}

}
