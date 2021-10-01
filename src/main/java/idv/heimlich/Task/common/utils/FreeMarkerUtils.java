package idv.heimlich.Task.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import idv.heimlich.Task.common.exception.ApBusinessException;

public class FreeMarkerUtils {

	public static String getTemplate(final String ftlName, final Map<String, ?> map) {
		final String dir = "/APCLMS/CLASS/ftl";
		final Configuration conf = new Configuration(Configuration.VERSION_2_3_0);
		try {
			conf.setDirectoryForTemplateLoading(new File(dir));
			conf.setEncoding(Locale.TAIWAN, "UTF-8");
			final Template template = conf.getTemplate(ftlName);

			try (Writer writer = new StringWriter()) {
				try {
					template.process(map, writer);
				} catch (final TemplateException e) {
					throw new ApBusinessException("樣板錯誤" + ftlName, e);
				}
				return writer.toString();
			}
		} catch (final IOException e) {
			throw new ApBusinessException("資料夾不存在:" + dir + " name:" + ftlName, e);
		}
	}

	public static void getTemplate(final String ftlName, final Map<String, ?> map, final Writer writer)
			throws TemplateException {
		final String dir = "\\APCLMS\\CLASS\\ftl";
		final Configuration conf = new Configuration(Configuration.VERSION_2_3_0);
		try {
			conf.setDirectoryForTemplateLoading(new File(dir));
			conf.setEncoding(Locale.TAIWAN, "UTF-8");
			final Template template = conf.getTemplate(ftlName);
			template.process(map, writer);
		} catch (final TemplateException e) {
			throw new ApBusinessException("樣板異常" + ftlName, e);
		} catch (final IOException e) {
			throw new ApBusinessException("資料夾不存在:" + dir, e);
		}
	}

	// == [Method] Block Stop
	// ================================================
	// == [Inner Class] Block Start
	// == [Inner Class] Block Stop
	// ================================================

}
