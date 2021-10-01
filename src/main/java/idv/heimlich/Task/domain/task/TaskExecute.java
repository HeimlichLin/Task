package idv.heimlich.Task.domain.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import idv.heimlich.Task.domain.code.CommonPeriodTasks;
import idv.heimlich.Task.domain.code.DocTasks;
import idv.heimlich.Task.domain.code.ExtractTasks;
import idv.heimlich.Task.domain.task.common.ITaskMapper;

public class TaskExecute {

	List<ITaskMapper> mapperList = new ArrayList<>();

	public TaskExecute() {
		final CommonPeriodTasks[] commonPeriodTasks = CommonPeriodTasks.values();
		for (final CommonPeriodTasks task : commonPeriodTasks) {
			this.mapperList.add(task);
		}
		final ExtractTasks[] extractPeriodTasks = ExtractTasks.values();
		for (final ExtractTasks task : extractPeriodTasks) {
			this.mapperList.add(task);
		}
		final DocTasks[] docTasks = DocTasks.values();
		for (final DocTasks task : docTasks) {
			this.mapperList.add(task);
		}
		// this.mapperList.addAll(this.getTasks());

	}

	public static void main(final String[] args) {
		if (args.length == 0) {
//			logger.warn("pls enter class name ex: " + ExtractTasks.clBackendMonitor.procNo);
		} else {
			final String procNo = args[0];

			final TaskExecute taskExecute = new TaskExecute();
			taskExecute.startTaskNow(procNo);

		}

	}

	public void startTaskNow(final String procNo) {
		final Class<? extends AbtractTask> taskClass = this.getTaskClass(procNo);
		if (taskClass != null) {
			AbtractTask newInstance;
			try {
				newInstance = taskClass.newInstance();
				newInstance.setName(procNo);
				newInstance.execute();
			} catch (final InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		} else {
			System.out.print("無此任務代碼procNo" + procNo);
		}
	}

	/**
	 * 取得任務
	 * 
	 * @param procNo
	 * @return
	 */
	private Class<? extends AbtractTask> getTaskClass(final String procNo) {

		for (final ITaskMapper mapper : this.mapperList) {
			if (StringUtils.equals(procNo, mapper.getProcNo())) {
				return mapper.getTaskClass();
			}

		}
		return null;
	}

}
