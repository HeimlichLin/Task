package idv.heimlich.Task;

import java.util.concurrent.TimeUnit;

import idv.heimlich.Task.common.utils.DateUtils;
import idv.heimlich.Task.domain.task.common.TaskLaunch;

/**
 * 數完睡10秒
 */
public class HelloLoop {

	String string = "HelloLoop- ";

	public void sayHell() {

		for (int i = 0; i < 1000; i++) {
			System.out.println(this.string + (i + 1));
		}
	}

	public void sayLoop() {
		TaskLaunch.getLaunch().startNow(HelloLoop.class.getName(), () -> {
			HelloLoop.this.sayHell();
			try {
				System.out.println(DateUtils.getDiff(System.currentTimeMillis()));
				TimeUnit.SECONDS.sleep(10);
				System.out.println(DateUtils.getDiff(System.currentTimeMillis()));

			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		});
	}

	public static void main(final String[] args) {
		new HelloLoop().sayLoop();
	}

}
