package idv.heimlich.Task.domain.code;

import idv.heimlich.Task.domain.task.AbtractTask;
import idv.heimlich.Task.domain.task.common.CheckTaskStatusTask;
import idv.heimlich.Task.domain.task.common.ITaskInfo;
import idv.heimlich.Task.domain.task.common.ITaskMapper;

/**
 * 週期任務表****
 */
public enum ExtractTasks implements ITaskMapper, ITaskInfo {

//	clMailService(MailServiceTask.class, "寄信服務", 5 * 60l), //
	clCheckTaskStatus(CheckTaskStatusTask.class, "檢查後端任務狀況", 5 * 60l), //
//	CronCLock(CornClock.class, "測試任務", 60l * 60 * 60), //
//	clprocF3(ClprocF3Task.class, "建立料清表(PFTZB)", 60 * 3l), //
//	clBackendMonitor(MonitorTask.class, "後端訊息監控", 5 * 60l), // 後端訊息監控
//	clJobCmd(JobCmdTask.class, "任務消費者", 5 * 60l), // Job comd命令
//	clBumpFile(BumpFileTask.class, "放行報單碰撞服務", 60 * 60l), // 碰檔程式，包含放行、傳送註記l
//	clRedoDeclar(RedoDeclarTask.class, "報單重新收任務", 10 * 60l), //
//	clRedoDeclarNotice(RedoDeclarNoticeTask.class, "報單重收通知[預先]", 10 * 60l), //

	;//

	public final String procNo;
	public final Long period;
	final Class<? extends AbtractTask> taskClass;
	final String procName;

	/**
	 *
	 * @param procNo
	 * @param period
	 * @param taskClass
	 */
	private ExtractTasks(final Class<? extends AbtractTask> taskClass, String procName, final Long period) {

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
