package br.com.fcs.agendatecnico.enums;

public enum TransactionCodeEnum {
	
	SCC(1,"SCC"),
	FAF(2,"FAF"),
	CBE(3,"CBE"),
	ADJ(4,"ADJ"),
	TC(5,"TC"),
	TV(6,"TV"),
	REBATE(7,"REBATE"),
	DEBIT(8,"DEBIT"),
	DEDUCTIONGSM(9,"DEDUCTIONGSM"),
	DEDUCTIONPERIODIC(10,"DEDUCTIONPERIODIC");
	
    public final Integer code;
    private final String desc;
    
    private TransactionCodeEnum(Integer c, String s) {
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
    
    public static TransactionCodeEnum getFromCode(Integer code) {
        if (code == null || code == 0) {
            return null;
        }

        for (TransactionCodeEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }

    public static boolean contains(String string) {
        for (TransactionCodeEnum c : TransactionCodeEnum.values()) {
            if (c.name().equals(string)) {
                return true;
            }
        }
        return false;
    }
    

}
