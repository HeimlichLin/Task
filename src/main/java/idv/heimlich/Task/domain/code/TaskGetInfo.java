package idv.heimlich.Task.domain.code;

import java.util.Collections;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.Sets;

import idv.heimlich.Task.domain.task.common.ITaskInfo;

/**
 * 任務取得資料
 */
public enum TaskGetInfo {

	;

	static {
		final Map<String, ITaskInfo> infoMap = new HashMap<>();
		final Set<ITaskInfo> infoList = Sets.newHashSet(//
				EnumSet.allOf(ExtractTasks.class));//
		infoList.addAll(EnumSet.allOf(DocTasks.class));//
		infoList.addAll(EnumSet.allOf(TaskIds.class));//
		infoList.addAll(EnumSet.allOf(CommonPeriodTasks.class));//
		infoList.addAll(EnumSet.allOf(TaskIds.class));//
		for (final ITaskInfo tasks : infoList) {
			infoMap.put(tasks.getProcNo(), tasks);
		}
		MAP = Collections.unmodifiableMap(infoMap);
	}

	private static final Map<String, ITaskInfo> MAP;

	private static final class ITaskInfoImplementation implements ITaskInfo {
		private final String procNo;

		private ITaskInfoImplementation(String procNo) {
			this.procNo = procNo;
		}

		@Override
		public String getProcNo() {
			return this.procNo;
		}

		@Override
		public String getProcName() {
			return "未知任務";
		}
	}

	public static String getProcName(String procNo) {
		return MAP.getOrDefault(procNo, new ITaskInfoImplementation(procNo)).getProcName();
	}

}
