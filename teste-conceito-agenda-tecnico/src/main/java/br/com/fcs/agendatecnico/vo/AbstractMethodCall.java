package br.com.fcs.agendatecnico.vo;

import br.com.fcs.agendatecnico.utils.JsonUtils;

public class AbstractMethodCall extends MethodCall{

	@Override
	public String toString() {
		return JsonUtils.parseToJsonString(this);
	}
}
