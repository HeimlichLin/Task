package idv.heimlich.Task.common.utils;

import java.beans.ExceptionListener;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;

import idv.heimlich.Task.common.exception.ApBusinessException;

public class XmlUtils {

	public static void serializeToXML(final Object o, File out) throws IOException {

		Optional.of(o);
		Optional.of(out);
		try (FileOutputStream fos = new FileOutputStream(out)) {
			try (final XMLEncoder encoder = new XMLEncoder(fos)) {
				encoder.setExceptionListener(new ExceptionListener() {
					@Override
					public void exceptionThrown(Exception e) {
						throw new ApBusinessException("erroro:" + e);
					}
				});
				encoder.writeObject(o);
			}
		}
	}

	public static <T> T deserializeFromXML(File out, Class<T> pClass) throws IOException {
		Optional.of(out);
		Optional.of(pClass);
		try (FileInputStream fis = new FileInputStream(out)) {
			try (final XMLDecoder decoder = new XMLDecoder(fis)) {
				@SuppressWarnings("unchecked")
				final T decodedSettings = (T) decoder.readObject();
				decoder.close();
				fis.close();
				return decodedSettings;
			}
		}
	}

}
