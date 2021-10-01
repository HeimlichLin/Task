package idv.heimlich.Task.domain.task.common;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import idv.heimlich.Task.common.utils.DateUtils;
import idv.heimlich.Task.common.utils.DateUtils.SimpleDates;
import idv.heimlich.Task.common.utils.FileUtils;
import idv.heimlich.Task.common.utils.FreeMarkerUtils;
import idv.heimlich.Task.domain.code.CommonPeriodTasks;
import idv.heimlich.Task.domain.code.DocTasks;
import idv.heimlich.Task.domain.code.ExtractTasks;
import idv.heimlich.Task.domain.code.TaskGetInfo;
import idv.heimlich.Task.domain.code.TaskIds;
import idv.heimlich.Task.domain.task.AbtractTask;

/**
 * 檢查執行任務狀態
 */
public class CheckTaskStatusTask extends AbtractTask {

	private static final int EXECUTE_TIME = 60 * 40;// 加執行時間
	// private long TIMEOUT = 60 * 60 * 1000;// 1小時未更新

	private static final String PCLMS_LOG_TASKS = "/PCLMS/log/tasks";

	public CheckTaskStatusTask() {
		for (final CommonPeriodTasks task : CommonPeriodTasks.values()) {
			this.timeoutMap.put(task.name(), (task.getPeriod() + EXECUTE_TIME) * 1000l);
		}
		for (final DocTasks task : DocTasks.values()) {
			this.timeoutMap.put(task.name(), (task.getPeriod() + EXECUTE_TIME) * 1000l);
		}
		for (final ExtractTasks task : ExtractTasks.values()) {
			this.timeoutMap.put(task.name(), (task.getPeriod() + EXECUTE_TIME) * 1000l);
		}
		this.timeoutMap.put(TaskIds.CleanJobSct.name(), ((60 * 60 * 24) + EXECUTE_TIME) * 1000l);// 一天未執行
	}

	private final Map<String, Long> timeoutMap = new HashMap<>();
//	private final MailService mailService = MailServiceImpl.get();

	public static void main(final String[] args) throws IOException {

		new CheckTaskStatusTask().doMyWork();
	}

	@Override
	public void doMyWork() {
		final List<Object> list = this.getList();
		final Map<String, Object> map = new HashMap<String, Object>();
		map.put("checkTime", DateUtils.format(SimpleDates.YYYYMMDDHHMMSS_2));
		map.put("items", list);

		if (CollectionUtils.isNotEmpty(list)) {
			final String msg = FreeMarkerUtils.getTemplate("checkTaskStatus.ftl", map);

//			final String mails = StringUtils.join(ApContext.getContext().getSettingArray("mails"), ",");
//			final MailCreateDTO mailCreateDTO = new MailCreateDTO();
//			mailCreateDTO.setFilePath("");
//			mailCreateDTO.setMailBcc("");
//			mailCreateDTO.setMailCc("");
//			mailCreateDTO.setMailTo(mails);
//			mailCreateDTO.setMessage(msg);
//
//			mailCreateDTO.setPgmId("checkTasks");
//			mailCreateDTO.setRemarks("");
//			mailCreateDTO.setSubject("逾期未執行通知");
//
//			this.mailService.create(mailCreateDTO);

		}
	}

	private List<Object> getList() {
		final List<File> files = FileUtils.filter(PCLMS_LOG_TASKS,
				pathname -> pathname.isFile() && pathname.getName().matches("\\([R|I]{1}\\).*"));
//		logger.info("Idol or Runiing proc size:" + files.size());
		final List<Object> list = new ArrayList<>();
		for (final File file : files) {
			if (file.exists()) {
				final String taskid = StringUtils.substringAfter(file.getName(), ".");
				final Long timeout = this.getTimeout(taskid);
				if (timeout != null) {
					final Long overtime = System.currentTimeMillis() - FileUtils.lastModified(file);
					if (overtime >= timeout) {
						final Map<String, String> item = new HashMap<String, String>();
						final String format = String.format("%s(%s)", taskid, TaskGetInfo.getProcName(taskid));
						item.put("taskid", format);
						item.put("overtTime", (timeout / (1000 * 60)) + "");
						item.put("nowOverTime", DateUtils.getDiff(overtime));
						list.add(item);
					}
				}

			}

		}
		return list;
	}

	private Long getTimeout(final String proc) {
		if (this.timeoutMap.containsKey(proc)) {
			return this.timeoutMap.get(proc);
		}
		return null;
	}

}
