package org.zwickmann.greenfoot;

public class Command
{
	public Command(String key, String title) {
		this.key = key;
		this.title = title;
	}

	public boolean matches(String key) {
		return this.key.equals(key);
	}

	public String title() {
		return title;
	}

	public String key() {
		return key;
	}

	@Override
	public String toString() {
		return "[" + key + "]: " + title;
	}

	private final String key;
	private final String title;
};
