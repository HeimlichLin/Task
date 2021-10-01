package idv.heimlich.Task.common.utils;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.FileTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import idv.heimlich.Task.common.exception.ApBusinessException;

public class FileUtils {

	public static final Comparator<File> LASTMODIFIED_SROT = (o1, o2) -> o1.lastModified() == o2.lastModified() ? 0
			: (o1.lastModified() < o2.lastModified() ? 1 : -1);

//	final static Logger logger = MyLoggerFactory.getLogger();

	/**
	 * 排序[根據異動時間舊>新]
	 * 
	 * @param folder
	 *            [路徑]
	 * @param fileFilter[過濾器]
	 * @return
	 */
	public static List<File> filter(final String folder, final FileFilter fileFilter) {
		return filter(folder, fileFilter, LASTMODIFIED_SROT);
	}

	public static List<File> filter(final String folder, final FileFilter fileFilter,
			final Comparator<File> comparator) {
		if (StringUtils.isBlank(folder)) {
			throw new ApBusinessException("路徑不得空白");
		}
		final File file = new File(folder);
		if (!file.exists()) {
			throw new ApBusinessException("folder路徑" + folder + "不存在");
		}
		if (!file.isDirectory()) {
			throw new ApBusinessException("folder路徑" + folder + "不屬於資料夾");
		}
		final File[] tmpFiles = file.listFiles(fileFilter);
		Arrays.sort(tmpFiles, comparator);
		return Arrays.asList(tmpFiles);
	}

	public static void renameTo(final File source, final File target) {
		if (source == null) {
			throw new ApBusinessException("source is null");
		}
		if (target == null) {
			throw new ApBusinessException("target is null");
		}
		if (!target.getParentFile().exists()) {
			target.getParentFile().mkdirs();
		}
		if (source.exists()) {
			source.setLastModified(System.currentTimeMillis());
			final boolean renameTo = source.renameTo(target);
			if (!renameTo) {
				try {
					org.apache.commons.io.FileUtils.copyFile(source, target);
					Files.delete(source.toPath());
				} catch (final IOException e) {
//					logger.error("檔案不存在", e);
				}
			}
		}

	}

	public static Long lastModified(File file) {
		if (!file.exists()) {
			return null;
		}
		try {
			final FileTime lastModifiedTime = java.nio.file.Files.getLastModifiedTime(file.toPath());
			return lastModifiedTime.toMillis();
		} catch (final IOException e) {
//			logger.error("檔案不存在", e);
		}
		return System.currentTimeMillis();

	}

}
