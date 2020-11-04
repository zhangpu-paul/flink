package org.apache.flink.cep.functions;

import org.apache.flink.api.common.functions.Function;
import org.apache.flink.cep.pattern.Pattern;

import java.io.Serializable;


public interface InjectionPatternFunction<T> extends Function, Serializable {

	public void init();

	public Pattern<T,T> inject();

	public long getPeriod();

	public boolean isChanged();
}
