package com.erroronserver.eventosdecaridade.database;

public class EventoCaridadeDBContract {

    public static class TableContract {
        public static String COLUMN_ID = "id";
    }

    public static final class Evento extends TableContract {
        public static String TABLE_NAME = "evento";

        public static String COLUMN_TIPO_EVENTO = "tipoEvento";
        public static String COLUMN_DATA_EVENTO = "dataEvento";
        public static String COLUMN_LATITUDE = "latitude";
        public static String COLUMN_LONGITUDE = "longitude";
        public static String COLUMN_DESCRICAO = "descricao";
    }

    public static final class Contato extends TableContract {
        public static String TABLE_NAME = "contato";

        public static String COLUMN_ASSUNTO = "assunto";
        public static String COLUMN_DESCRICAO = "descricao";
        public static String COLUMN_ENVIADA = "enviada";
    }

}
