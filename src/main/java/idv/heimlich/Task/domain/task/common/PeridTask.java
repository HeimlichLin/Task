package idv.heimlich.Task.domain.task.common;

import java.io.Serializable;

public class PeridTask implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	private long period;

	public PeridTask() {
		super();

	}

	public String getName() {
		return this.name;
	}

	public long getPeriod() {
		return this.period;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPeriod(long period) {
		this.period = period;
	}

}
