package bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Timer implements Serializable {
    private String format = "yyyy-MM-dd HH:mm:ss E曜日";
    private SimpleDateFormat formatter = new SimpleDateFormat(format);
    private String[] timeZone;

    public Timer() {
    }

    public long getTime() {
        return new Date().getTime();
    }

    public String getFormatedTime() {
        return this.formatter.format(new Date(getTime()));
    }

    public String getFormat() {
        return this.format;
    }

    public void setFormat(String format) {
        try {
            this.formatter = new SimpleDateFormat(format);
            this.format = format;
        } catch (Exception e) {
            System.out.println("format invalid");
            System.out.println(format);
        }
    }

    public String getTimeZone() {
        StringBuffer sb = new StringBuffer();
        if (this.timeZone != null && this.timeZone.length > 0) {
            for (String s : this.timeZone)
                sb.append(String.format("%s,", s));
            if (sb.length() > 0)
                sb.deleteCharAt(sb.length() - 1);
            return sb.toString();
        }
        else
            return "None";
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone.split(",");
        System.out.println(timeZone);
    }

    public void setTimeZone(String[] timeZone) {
        this.timeZone = timeZone;
        System.out.println(timeZone);
    }
}
