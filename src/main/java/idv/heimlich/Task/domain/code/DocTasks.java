package idv.heimlich.Task.domain.code;

import idv.heimlich.Task.domain.task.AbtractTask;
import idv.heimlich.Task.domain.task.common.ITaskInfo;
import idv.heimlich.Task.domain.task.common.ITaskMapper;

/**
 * 訊息類型任務
 */
public enum DocTasks implements ITaskMapper, ITaskInfo {

	// clfetchL6(ClfetchL6Task.class, 60 * 3l), // 解析移倉訊息
	// clprocL6(ClprocL6Task.class, 60 * 10l);//// 處理移倉訊息

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
	private DocTasks(final Class<? extends AbtractTask> taskClass, String procName, final Long period) {

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
		// TODO Auto-generated method stub
		return null;
	}

}
