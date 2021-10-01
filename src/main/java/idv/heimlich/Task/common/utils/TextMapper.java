package idv.heimlich.Task.common.utils;

public interface TextMapper<VO> {
	
	/**
	 * 根據text轉換成VO
	 * @param text
	 * @return
	 */
	VO toVo(String text);

}
