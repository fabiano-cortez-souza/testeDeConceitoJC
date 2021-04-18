package br.com.fcs.agendatecnico.enums;

public enum OCSDataType {

	I4("i4"),
	ARRAY("array"),
	STRING("string"),
	ISO_8601("dateTime.iso8601"),
	INT("int");
	
    private String desc;

	private OCSDataType(String desc) {
		this.desc = desc;
	}

    public String getDesc() {
        return desc;
    }

}
