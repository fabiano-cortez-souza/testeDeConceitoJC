package br.com.fcs.agendatecnico.constants;

public final class Constants {
    public static final String TIME_ZONE_ID = "UTC";
    public static final String TIMESTAMP_FORMAT = "yyyMMdd'T'HH:mm:ssZ";
    public static final String DATE_FORMAT = "yyyMMdd";
    public static final String TIME_ZONE_LOCAL_ID = "America/Sao_Paulo";
    public static final String PIPES = "\\|\\|";
    public static final String PATTERN_TRANSACTION_HISTORY_REPROCESS_FILES = "*.log";
    public static final int MAX_PAGE_LENGTH = 100;
    public static final int MAX_REPROCESS_TRY_VALUE = 3;
    public static final int DEFAULT_MSISDN_LENGTH = 13;
    public static final int DEFAULT_TRANSACTION_ID_LENGTH = 20;
    public static final int DEFAULT_TRANSACTION_TYPE_LENGTH = 20;
    public static final int DEFAULT_TRANSACTION_CODE_LENGTH = 30;
    public static final int DEFAULT_ORIGIN_NODE_TYPE_LENGTH = 8;
    public static final int DEFAULT_ORIGIN_HOST_NAME_LENGTH = 30;	
    public static final String SINCRONO_PUBSUB_INPUT_CHANNEL  = "SincronoPubsubInputChannel";
	public static final String SINCRONO_PUBSUB_OUTPUT_CHANNEL = "SincronoPubsubOutputChannel";
    public static final String ASSINCRONO_PUBSUB_INPUT_CHANNEL  = "AssincronoPubsubInputChannel";
	public static final String ASSINCRONO_PUBSUB_OUTPUT_CHANNEL = "AssincronoPubsubOutputChannel";

}
