package com.bd.transformer.domain.dim.base;

import com.bd.transformer.common.DateEnum;
import com.bd.transformer.util.TimeUtils;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

/**
 * @Author: tangxc
 * @Description:
 * @Date: Created in 13:57 2018/11/29
 * @Modified by:
 */
public class DateDimension extends BaseDimension {

    private int id;
    private int year;
    private int season;
    private int month;
    private int week;
    private int day;
    private Date calendar = new Date();
    private String type;
    private Date createdTime = new Date();

    public DateDimension() {

    }

    public DateDimension(int year, int season, int month, int week, int day, Date calendar, String type, Date createdTime) {
        this.year = year;
        this.season = season;
        this.month = month;
        this.week = week;
        this.day = day;
        this.calendar = calendar;
        this.type = type;
        this.createdTime = createdTime;
    }

    public static DateDimension buildDate(long timestamp, DateEnum type) {
        int year = TimeUtils.getDateInfo(timestamp, DateEnum.YEAR);
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        if (DateEnum.YEAR.equals(type)) {
            calendar.set(year, 0, 1);
            return new DateDimension(year, 0, 0, 0, 0, calendar.getTime(), type.name, new Date());
        }
        int season = TimeUtils.getDateInfo(timestamp, DateEnum.SEASON);
        if (DateEnum.SEASON.equals(type)) {
            calendar.set(year, (season - 1) * 3, 1);
            return new DateDimension(year, season, 0, 0, 0, calendar.getTime(), type.name, new Date());
        }
        int month = TimeUtils.getDateInfo(timestamp, DateEnum.MONTH);
        if (DateEnum.MONTH.equals(type)) {
            calendar.set(year, month - 1, 1);
            return new DateDimension(year, season, month, 0, 0, calendar.getTime(), type.name, new Date());
        }
        int week = TimeUtils.getDateInfo(timestamp, DateEnum.WEEK);
        if (DateEnum.WEEK.equals(type)) {
            long firstDayOfWeek = TimeUtils.getFirstDayOfWeek(timestamp);
            year = TimeUtils.getDateInfo(firstDayOfWeek, DateEnum.YEAR);
            season = TimeUtils.getDateInfo(firstDayOfWeek, DateEnum.SEASON);
            month = TimeUtils.getDateInfo(firstDayOfWeek, DateEnum.MONTH);
            week = TimeUtils.getDateInfo(firstDayOfWeek, DateEnum.WEEK);
            if (month == 12 && week == 1) {
                week = 53;
            }
            return new DateDimension(year, season, month, week, 0, new Date(firstDayOfWeek), type.name, new Date());
        }
        int day = TimeUtils.getDateInfo(timestamp, DateEnum.DAY);
        if (DateEnum.DAY.equals(type)) {
            calendar.set(year, month - 1, day);
            if (month == 12 && week == 1) {
                week = 53;
            }
            return new DateDimension(year, season, month, week, day, calendar.getTime(), type.name, new Date());
        }
        throw new RuntimeException("不支持所要求的dateEnum类型来获取datedimension对象" + type);
    }

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeInt(this.id);
        out.writeInt(this.year);
        out.writeInt(this.season);
        out.writeInt(this.month);
        out.writeInt(this.week);
        out.writeInt(this.day);
        out.writeLong(this.calendar.getTime());
        out.writeUTF(this.type);
        out.writeLong(this.createdTime.getTime());
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.id = in.readInt();
        this.year = in.readInt();
        this.season = in.readInt();
        this.month = in.readInt();
        this.week = in.readInt();
        this.day = in.readInt();
        this.calendar.setTime(in.readLong());
        this.type = in.readUTF();
        this.createdTime.setTime(in.readLong());
    }
    @Override
    public int compareTo(BaseDimension o) {
        if (this == o) {
            return 0;
        }
        DateDimension other = (DateDimension) o;
        int tmp = Integer.compare(this.id, other.id);
        if (tmp != 0) {
            return tmp;
        }
        tmp = Integer.compare(this.year, other.year);
        if (tmp != 0) {
            return tmp;
        }
        tmp = Integer.compare(this.season, other.season);
        if (tmp != 0) {
            return tmp;
        }
        tmp = Integer.compare(this.month, other.month);
        if (tmp != 0) {
            return tmp;
        }
        tmp = Integer.compare(this.week, other.week);
        if (tmp != 0) {
            return tmp;
        }
        tmp = Integer.compare(this.day, other.day);
        if (tmp != 0) {
            return tmp;
        }
        tmp = this.type.compareTo(other.type);
        return tmp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public Date getCalendar() {
        return calendar;
    }

    public void setCalendar(Date calendar) {
        this.calendar = calendar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DateDimension that = (DateDimension) o;
        return id == that.id &&
                year == that.year &&
                season == that.season &&
                month == that.month &&
                week == that.week &&
                day == that.day &&
                Objects.equals(calendar, that.calendar) &&
                Objects.equals(type, that.type) &&
                Objects.equals(createdTime, that.createdTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, year, season, month, week, day, calendar, type, createdTime);
    }

    @Override
    public String toString() {
        return "DateDimension{" +
                "id=" + id +
                ", year=" + year +
                ", season=" + season +
                ", month=" + month +
                ", week=" + week +
                ", day=" + day +
                ", calendar=" + calendar +
                ", type='" + type + '\'' +
                ", createdTime=" + createdTime +
                '}';
    }

}
