package br.edu.utfpr.alunos.jeffersonlima.monisaudemental.utils;


import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;

public final class UtilsLocalDate {

    private UtilsLocalDate(){

    }

    public static String formatLocalDate(LocalDate date){
        if(date == null){
            return null;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);
        return date.format(formatter);
    }

    public static long toMilliseconds(LocalDate date){
        if(date == null){
            return 0;
        }

        return date.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    public static int differenceMonthsForToday(LocalDate date){
        return differenceMonths(date, LocalDate.now());
    }

    public static int differenceMonths(LocalDate date1, LocalDate date2) {
        if (date1 == null || date2 == null) {
            return 0;
        }
        return (int) ChronoUnit.MONTHS.between(date1, date2);
    }
}
