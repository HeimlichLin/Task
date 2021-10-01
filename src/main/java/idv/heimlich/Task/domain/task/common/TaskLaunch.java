package idv.heimlich.Task.domain.task.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import idv.heimlich.Task.common.utils.YYYYMMDDHHMMSSUtils;

/**
 * 任務執行
 **/
public class TaskLaunch {

	private static final String S_T_S_S_T_S = "[%s].[%s >>> %s ]:%s";

	private final static Logger LOG = LoggerFactory.getLogger(TaskLaunch.class);
	protected static final SimpleDateFormat SDF = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
	private static TaskLaunch INSTANCT = new TaskLaunch();

	private TaskLaunch() {

	}

	public static TaskLaunch getLaunch() {
		return TaskLaunch.INSTANCT;
	}

	/**
	 * 執行
	 * 
	 * @param procNo
	 * @param task
	 */
	public void startNow(final String procNo, final Task task) {
		final Long startTime = System.currentTimeMillis();

		try {
			LOG.info(String.format(S_T_S_S_T_S, procNo, YYYYMMDDHHMMSSUtils.getText(new Date(startTime)), "running ",
					"start"));
			final TaskContext context = TaskContextFactory.getContext(procNo);
			final CommonTask loopWrapRunnable = new CommonTask(task, context);
			loopWrapRunnable.execute();
		} catch (final Exception e) {
			LOG.error("job Exception procNo:" + procNo, e);
		} finally {
			final long endTime = System.currentTimeMillis();
			final Long diff = endTime - startTime;

			LOG.info(String.format(S_T_S_S_T_S, procNo, YYYYMMDDHHMMSSUtils.getText(new Date(startTime)),
					YYYYMMDDHHMMSSUtils.getText(new Date(endTime)), "spent: " + diff / 1000 + "s"));
		}

	}

	/**
	 * 
	 * @param procNo
	 * @param task
	 * @param timeout
	 *            [超時通知]
	 */
	public void startNow(final String procNo, final long timeout, final Task task) {

		final Timer timer = new Timer();
		timer.scheduleAtFixedRate(new MyTimerTask(procNo, timeout), timeout, timeout);
		try {
			this.startNow(procNo, task);
		} finally {
			timer.cancel();
		}

	}

	/**
	 * 計時器
	 */
	private class MyTimerTask extends TimerTask {
		private int time = 0;
		final String procNo;
		final long timeout;

		public MyTimerTask(final String procNo, final long timeout) {
			super();
			this.procNo = procNo;
			this.timeout = timeout;
		}

		@Override
		public void run() {
			LOG.error(String.format("第[%d]次處理逾期通知%s:執行時間已達 %ds", ++this.time, this.procNo, this.timeout / 1000));
		}

	}

	public void terminate(final int timeout) {
		final TaskContext context = TaskContextFactory.getContext(TaskLaunch.class.getName());
		context.terminate(timeout);

	}

	public void start() {
		final TaskContext context = TaskContextFactory.getContext(TaskLaunch.class.getName());
		context.start();
	}

}
