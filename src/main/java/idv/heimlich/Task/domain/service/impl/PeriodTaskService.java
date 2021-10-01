package idv.heimlich.Task.domain.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import idv.heimlich.Task.common.utils.XmlUtils;
import idv.heimlich.Task.domain.bean.SyscodeDo;
import idv.heimlich.Task.domain.task.TaskExecute;
import idv.heimlich.Task.domain.task.common.PeridTask;
import idv.heimlich.Task.domain.task.common.TaskContext;
import idv.heimlich.Task.domain.task.common.TaskContextFactory;

/**
 * 週期性任務服務
 */
public class PeriodTaskService implements Serializable {

	public enum PeriodType {
		comm, doc
	}

	private static final String PER_JOB = "perjob";
	private final String serviceName;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private final List<TaskHolder> tasks = new ArrayList<>();
//	final static CommonLogger logger = MyLoggerFactory.getLogger(Mylogs.clPeriodTaskService);
	private ScheduledExecutorService service;
	private final long timeout = 60 * 4;
	private final TaskExecute taskExecute = new TaskExecute();

	public PeriodTaskService(final String serviceName) {
		this.serviceName = serviceName;
	}

	private class TaskHolder {
		String name;
		Long period;

		public TaskHolder(final String name, final Long period) {
			super();
			this.name = name;
			this.period = period;
		}

	}

	/**
	 * 加入週期性任務
	 * 
	 * @param name
	 *            作業名稱
	 * 
	 * @param period
	 *            下次週期
	 * @param taskClass
	 * 
	 */
	public void addPeriodTask(final String name, final Long period) {
		this.tasks.add(new TaskHolder(name, period));
	}

	public void addXmlFile(final File xmlFile) throws IOException {
		@SuppressWarnings("unchecked")
		final List<PeridTask> tasks = XmlUtils.deserializeFromXML(xmlFile, List.class);
		for (final PeridTask task : tasks) {
			this.tasks.add(new TaskHolder(task.getName(), task.getPeriod()));
		}
	}

	public void addDBPeriodTask(final PeriodType type) {
		final List<TaskPersiodData> periodTasks = this.getPeriodTasks(type.name());
		for (final TaskPersiodData data : periodTasks) {
			this.addPeriodTask(data.proc, data.period);
		}

	}

	private List<TaskPersiodData> getPeriodTasks(final String type) {

//		final DoXdaoSession doXdaoSession = XdaoSessionManager.getDoXdaoSession();
//
//		final SqlWhere sqlWhere = new SqlWhere();
//		sqlWhere.add(SyscodeDo.COLUMNS.TYPE_ID.name(), PER_JOB);
//		sqlWhere.add(SyscodeDo.COLUMNS.CODE_ID.name(), type);
//		sqlWhere.add(SyscodeDo.COLUMNS.CODE_DATA1.name(), "Y");
//
//		final List<SyscodeDo> syscodeDos = doXdaoSession.selectPo(SyscodeDo.class, sqlWhere);
//		final List<TaskPersiodData> dataList = new ArrayList<>();
//		for (final SyscodeDo syscodeDo : syscodeDos) {
//			final TaskPersiodData taskCronData = new TaskPersiodData(syscodeDo);
//			dataList.add(taskCronData);
//		}
//		return dataList;
		return null;
	}

	class TaskPersiodData {
		final String proc;
		final Long period;

		public TaskPersiodData(final SyscodeDo syscodeDo) {
			super();
			this.proc = syscodeDo.getCodeData2();
			this.period = Long.parseLong(syscodeDo.getCodeData3());
		}

	}

	public void start() {
		final TaskContext context = TaskContextFactory.getContext(this.serviceName);
		final boolean isContinue = context.isContinueNotExit();
		if (isContinue) {
			try {
				context.pinRunning();
				this.runTasks();
				this.checkStatus(context);
			} finally {
				context.pingKilled();
			}
		} else {
//			logger.info("can not file .done ,so service can not execute");
		}

	}

	/**
	 * 檢查執行狀況
	 * 
	 * @param context
	 */
	private void checkStatus(final TaskContext context) {
		boolean isContinue = context.isContinueNotExit();
		while (isContinue) {
			context.pinRunning();
			try {
//				logger.debug(this.serviceName + " is execute..");
				TimeUnit.SECONDS.sleep(10);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
			isContinue = context.isContinueNotExit();
		}
//		logger.info("call interrupt:" + this.serviceName);
		this.service.shutdown();

		try {
			if (!this.service.awaitTermination(this.timeout, TimeUnit.SECONDS)) {
				this.service.shutdownNow();
//				logger.info("shutdownNow:"+ this.serviceName);
			}
		} catch (final InterruptedException e) {
//			logger.info("time out call shutdownNow  " + this.serviceName,e);
		}
//		logger.info("stop PeriodTaskService:" + this.serviceName);
	}

	private void runTasks() {
//		logger.info(this.serviceName + "啟動任務數量:" + this.tasks.size());
		this.service = Executors.newScheduledThreadPool(this.tasks.size());
		int delay = 0;// 延遲時間
		for (final TaskHolder task : this.tasks) {
			this.service.scheduleWithFixedDelay(new Runnable() {
				@Override
				public void run() {
//					logger.info(PeriodTaskService.this.serviceName + " 啟動" + task.name);
					PeriodTaskService.this.taskExecute.startTaskNow(task.name);

				}
			}, delay++, task.period, TimeUnit.SECONDS);
		}
	}

}
