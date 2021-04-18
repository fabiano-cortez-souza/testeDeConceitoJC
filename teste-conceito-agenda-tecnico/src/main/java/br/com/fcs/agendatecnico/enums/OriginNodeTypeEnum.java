package br.com.fcs.agendatecnico.enums;

public enum OriginNodeTypeEnum {
	EXT(1,"EXT"),
	AIR(2,"AIR"),
	ADM(3,"ADM"),
	UGW(4,"UGW"),
	IVR(5,"IVR"),
	OGW(6,"OGW"),
	SDP(7,"SDP");
	
    public final Integer code;
    private final String desc;
    
    private OriginNodeTypeEnum(Integer c, String s) {
        this.code = c;
        this.desc = s;
    }

    public Integer getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
    
    public String getEnumName() {
        return name();
    }
    
    public static OriginNodeTypeEnum getFromCode(Integer code) {
        if (code == null || code == 0) {
            return null;
        }

        for (OriginNodeTypeEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
    
    public static boolean contains(String string) {
        for (OriginNodeTypeEnum c : OriginNodeTypeEnum.values()) {
            if (c.name().equals(string)) {
                return true;
            }
        }
        return false;
    }
}
