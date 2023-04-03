package org.example.dataEditor;

import com.minis.beans.PropertyEditor;
import com.minis.util.StringUtils;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class CustomDateEditor implements PropertyEditor {
    private Class<?> dateClass;
    private DateTimeFormatter datetimeFormatter;
    private boolean allowEmpty;
    private Date value;

    public CustomDateEditor() throws IllegalArgumentException {
        this(Date.class, "yyyy-MM-dd", true);
    }

    public CustomDateEditor(Class<?> dateClass) throws IllegalArgumentException {
        this(dateClass, "yyyy-MM-dd", true);
    }

    public CustomDateEditor(Class<?> dateClass, boolean allowEmpty) throws IllegalArgumentException {
        this(dateClass, "yyyy-MM-dd", allowEmpty);
    }

    public CustomDateEditor(Class<?> dateClass, String pattern, boolean allowEmpty) throws IllegalArgumentException {
        this.dateClass = dateClass;
        this.datetimeFormatter = DateTimeFormatter.ofPattern(pattern);
        this.allowEmpty = allowEmpty;
    }

    @Override
    public void setAsText(String text) {
        if (this.allowEmpty && !StringUtils.hasText(text)) {
            setValue(null);
        } else {
            LocalDate localdate = LocalDate.parse(text, datetimeFormatter);
            setValue(Date.from(localdate.atStartOfDay(ZoneId.systemDefault()).toInstant()));
        }
    }

    @Override
    public void setValue(Object value) {
        this.value = (Date) value;
    }

    @Override
    public Object getValue() {
        return this.value;
    }

    @Override
    public String getAsText() {
        Date value = this.value;
        if (value == null) {
            return "";
        } else {
            LocalDate localDate = value.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            return localDate.format(datetimeFormatter);
        }
    }
}
