package idv.heimlich.Task.domain.task.common;

/**
 * 任務 內容
 **/
public interface TaskContext {

	/**
	 * 啟動
	 */
	void start();

	/**
	 * 是否進行中
	 * 
	 * @return
	 */
	boolean isContinue();

	boolean isContinueNotExit();

	/**
	 * 中斷
	 * 
	 * @param timeout
	 */
	public void terminate(int timeout);

	/**
	 * 傳送執行中訊息
	 */
	void pinRunning();

	/**
	 * 傳送閒置訊息
	 */
	void pingIdle();

	/**
	 * 傳送kill訊息
	 */
	void pingKilled();

	/**
	 * 執行
	 * 
	 * @param task
	 */
	public default void execute(final Task task) {
		if (this.isContinue()) {
			try {
				this.pinRunning();
				task.execute();
			} finally {
				this.pingIdle();
			}
		}
	}

}
