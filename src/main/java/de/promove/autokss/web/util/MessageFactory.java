package de.promove.autokss.web.util;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * <a href="mailto:andreas@bambo.it">Andreas Baumgartner, andreas@bambo.it</a>
 */
public class MessageFactory {

	public static final String MESSAGE_PATH = "messages";

	private static ResourceBundle messages;

	private static void init() {
		Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
		if (locale == null) {
			locale = Locale.ENGLISH;
		}
		messages = ResourceBundle.getBundle(MESSAGE_PATH, locale);
	}

	public static String getMessage(String key) {
		try {
			if (messages == null) {
				init();
			}
			return messages.getString(key);
		}
		catch (Exception e) {
			return key;
		}
	}

	public static String getMessage(String key, String... params) {
		String message = getMessage(key);
		return MessageFormat.format(message, params);
	}

	public static FacesMessage getFacesMessage(String key, String... params) {
		String message = getMessage(key);
		MessageFormat.format(message, params);
		return new FacesMessage(message);
	}

	public static FacesMessage getFacesMessage(FacesMessage.Severity severity, String key, String... params) {
		String message = getMessage(key);
		MessageFormat.format(message, params);
		FacesMessage ret = new FacesMessage(message);
		ret.setSeverity(severity);
		return ret;
	}

}
