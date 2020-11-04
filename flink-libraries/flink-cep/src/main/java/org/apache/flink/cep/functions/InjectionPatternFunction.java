package org.apache.flink.cep.functions;

import org.apache.flink.api.common.functions.Function;
import org.apache.flink.cep.pattern.Pattern;

import java.io.Serializable;

/**
 *
 * @param <T>
 */
public interface InjectionPatternFunction<T> extends Function, Serializable {

	/**
	 * 你可能有一些初始化的工作
	 */
	public void init();

	/**
	 * 获取新的pattern
	 * @return
	 */
	public Pattern<T,T> inject();

	/**
	 * 一个扫描周期:ms
	 * @return
	 */
	public long getPeriod();

	/**
	 * 规则是否发生变更
	 * @return
	 */
	public boolean isChanged();
}
