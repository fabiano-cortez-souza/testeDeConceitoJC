package br.com.fcs.agendacliente.enums;

public enum XmlTagNameEnum {
	
	ACCOUNT_FLAGS_AFTER						(1, "accountFlagsAfter"),
	ACCOUNT_FLAGS_BEFORE					(2, "accountFlagsBefore"),
	ACCOUNT_VALUE1							(3, "accountValue1"),
	ACTIVATION_STATUS_FLAG					(4, "activationStatusFlag"),
	ADJUSTMENT_AMOUNT_RELATIVE				(5, "adjustmentAmountRelative"),
	AVAILABLE_SERVER_CAPABILITIES			(6, "availableServerCapabilities"),
	CURRENCY1								(7, "currency1"),
	DEDICATED_ACCOUNT_ACTIVE_VALUE1			(8, "dedicatedAccountActiveValue1"),
	DEDICATED_ACCOUNT_CHANGE_INFORMATION	(9, "dedicatedAccountChangeInformation"),
	DEDICATED_ACCOUNT_ID					(10,"dedicatedAccountID"),
	DEDICATED_ACCOUNT_UNIT_TYPE				(11,"dedicatedAccountUnitType"),
	DEDICATED_ACCOUNT_UPDATE_INFORMATION	(12,"dedicatedAccountUpdateInformation"),
	DEDICATED_ACCOUNT_VALUE1				(13,"dedicatedAccountValue1"),
	NEGATIVE_BARRING_STATUS_FLAG			(14,"negativeBarringStatusFlag"),
	NEGOTIATED_CAPABILITIES					(15,"negotiatedCapabilities"),
	ORIGIN_HOST_NAME						(16,"originHostName"),
	ORIGIN_NODE_TYPE						(17,"originNodeType"),
	ORIGIN_TIMESTAMP						(18,"originTimeStamp"),
	ORIGIN_TRANSACTION_ID					(19,"originTransactionID"),
	RESPONSE_CODE							(20,"responseCode"),
	SERVICE_FEE_PERIOD_EXPIRY_FLAG			(21,"serviceFeePeriodExpiryFlag"),
	SERVICE_FEE_PERIOD_WARNING_ACTIVE_FLAG	(22,"serviceFeePeriodWarningActiveFlag"),
	SUBSCRIBER_NUMBER						(23,"subscriberNumber"),
	SUPERVISION_PERIOD_EXPIRY_FLAG			(24,"supervisionPeriodExpiryFlag"),
	SUPERVISION_PERIOD_WARNING_ACTIVE_FLAG	(25,"supervisionPeriodWarningActiveFlag"),
	TRANSACTION_CURRENCY					(26,"transactionCurrency"),
	UPDATE_BALANCE_AND_DATE					(27,"UpdateBalanceAndDate");

	public final Integer code;
    private final String desc;

    public static boolean contains(String string) {
        for (XmlTagNameEnum c : XmlTagNameEnum.values()) {
            if (c.name().equals(string)) {
                return true;
            }
        }
        return false;
    }

    public static XmlTagNameEnum getFromCode(Integer code) {
        if (code == null || code == 0) {
            return null;
        }

        for (XmlTagNameEnum value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }

    private XmlTagNameEnum(Integer c, String s) {
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
}
