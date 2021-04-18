package br.com.fcs.agendacliente.enums;

import java.net.HttpURLConnection;

public enum ErrorType {
	
	FIELD_VALIDATION_REQUIRED_FIELD_NOT_FOUND(1, "Required field not found"),
	FIELD_VALIDATION_NUMERIC_FIELD_INVALID(1, "Numeric field is invalid"),
	FIELD_VALIDATION_MSISDN_INVALID(2, "Msisdn format is invalid"),
	FIELD_VALIDATION_AMOUNT_INVALID(3, "Amount format is invalid"),
	FIELD_VALIDATION_TIMESTAMP_INVALID(4, "Timestamp format is invalid"),
	FIELD_VALIDATION_TYPE_INVALID(5, "Type field is invalid"),
	FIELD_VALIDATION_TRANSACTIONID_INVALID(6, "TransactionId field is invalid"),
	FIELD_VALIDATION_DESCRIPTION_INVALID(7, "Description field is invalid"),
	FIELD_VALIDATION_DATE_INVALID(4, "Date format is invalid"),
	FIELD_VALIDATION_NUMRECORDS_INVALID(3,"NumRecord format is invalid"),
	FIELD_VALIDATION_NUMPAGE_INVALID(3,"NumPage format is invalid"),
	FIELD_VALIDATION_NUMRECORDS_OUTOFRANGE(3,"NumRecord value is invalid, max value is 100"),
	FIELD_VALIDATION_ORIGIN_NODE_TYPE_INVALID(5, "OriginNodeType field is invalid"),
	FIELD_VALIDATION_ORIGIN_HOSTNAME_INVALID(6, "OriginHostName field is invalid"),
	FIELD_VALIDATION_TRANSACTIONCODE_INVALID(7, "TransactionCode field is invalid"),
	FIELD_VALIDATION_TRANSACTIONTYPE_INVALID(8, "TransactionType field is invalid"),
	FIELD_VALIDATION_BFFKEY_INVALID(9, "Bffkey field is invalid"),

	DOCUMENT_ALREADY_EXISTS(8, "This event already exists"),
	HTTP_CLIENT_TIMEOUT(HttpURLConnection.HTTP_CLIENT_TIMEOUT, "Request timeout"),
	HTTP_UNSUPPORTED_TYPE(HttpURLConnection.HTTP_UNSUPPORTED_TYPE, "Unsupported media type"),
	HTTP_UNAUTHORIZED(HttpURLConnection.HTTP_UNAUTHORIZED, "Unauthorized access"),
	HTTP_RESPONSE_CUSTOMER_BALANCE_DENIED(8, "Customer balance request denied"),
    HTTP_RESPONSE_TRANSACTION_HISTORY_DENIED(8, "Transaction history request denied");
	
    public final Integer code;
    private final String desc;
    
    private ErrorType(Integer c, String s) {
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
    
    public static ErrorType getFromCode(Integer code) {
        if (code == null || code == 0) {
            return null;
        }

        for (ErrorType value : values()) {
            if (value.code.equals(code)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}
