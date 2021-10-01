package idv.heimlich.Task.domain.code;

public enum TaskStatus {

	RUNNNING("(R)"), //
	IDLE("(I)"), //
	KILLED("(K)"), //
	;

	final String text;

	private TaskStatus(final String text) {
		this.text = text;

	}

	public String getText() {
		return this.text;
	}

}
