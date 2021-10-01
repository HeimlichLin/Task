package idv.heimlich.Task.domain.code;

import idv.heimlich.Task.domain.task.common.ITaskInfo;

public enum TaskIds implements ITaskInfo {

	clprocL6("移倉訊息處理"), //
	CleanJobSct("清檔服務"), //
	JobCmd("命令處理"), //
	CheckTaskStatusService("任務狀態監控服務"),//
	;

	String procName;

	private TaskIds(String procName) {
		this.procName = procName;
	}

	@Override
	public String getProcNo() {
		return this.name();
	}

	@Override
	public String getProcName() {
		return this.procName;
	}

}
