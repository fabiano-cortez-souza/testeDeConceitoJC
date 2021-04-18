package br.com.fcs.agendacliente.enums;

public enum TransactionTypeEnum {

    EUC(1,"EUC"),
	PIN(2,"PIN"),
	TT(3,"TT"),
	GSM(4,"GSM");
			
    public final Integer code;
    private final String desc;
    
    private TransactionTypeEnum(Integer c, String s) {
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
    
    public static TransactionTypeEnum getFromCode(Integer code) {
        if (code == null || code == 0) {
            return null;
        }

        for (TransactionTypeEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }

    public static boolean contains(String string) {
        for (TransactionTypeEnum c : TransactionTypeEnum.values()) {
            if (c.name().equals(string)) {
                return true;
            }
        }
        return false;
    }
    

}
