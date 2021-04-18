package br.com.fcs.agendatecnico.enums;

public enum OCSTypeCall {

	XML("XML"),
	JSON("JSON");
	
    private String desc;

	private OCSTypeCall(String desc) {
		this.desc = desc;
	}

    public String getDesc() {
        return desc;
    }

}
