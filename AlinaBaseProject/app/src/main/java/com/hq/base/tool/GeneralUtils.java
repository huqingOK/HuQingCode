package com.hq.base.tool;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup.LayoutParams;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <通用工具类> <功能详细描述>
 *
 * @author tgf
 * @version [版本号, 2012-7-5]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class GeneralUtils
{

    /**
     * 判断对象是否为null , 为null返回true,否则返回false
     *
     * @param obj 被判断的对象
     * @return boolean
     */
    public static boolean isNull(Object obj)
    {
        return (null == obj) ? true : false;
    }

    /**
     * 判断对象是否为null , 为null返回false,否则返回true
     *
     * @param obj 被判断的对象
     * @return boolean
     */
    public static boolean isNotNull(Object obj)
    {
        return !isNull(obj);
    }

    /**
     * 判断字符串是否为null或者0长度，字符串在判断长度时，先去除前后的空格,空或者0长度返回true,否则返回false
     *
     * @param str 被判断的字符串
     * @return boolean
     */
    public static boolean isNullOrZeroLenght(String str)
    {
        return (null == str || "".equals(str.trim())) ? true : false;
    }

    /**
     * 判断字符串是否为null或者0长度，字符串在判断长度时，先去除前后的空格,空或者0长度返回false,否则返回true
     *
     * @param str 被判断的字符串
     * @return boolean
     */
    public static boolean isNotNullOrZeroLenght(String str)
    {
        return !isNullOrZeroLenght(str);
    }

    /**
     * 判断集合对象是否为null或者0大小 , 为空或0大小返回true ,否则返回false
     *
     * @param c collection 集合接口
     * @return boolean 布尔值
     * @see [类、类#方法、类#成员]
     */
    public static boolean isNullOrZeroSize(Collection<? extends Object> c)
    {
        return isNull(c) || c.isEmpty();
    }

    /**
     * 判断集合对象是否为null或者0大小 , 为空或0大小返回false, 否则返回true
     *
     * @param c collection 集合接口
     * @return boolean 布尔值
     * @see [类、类#方法、类#成员]
     */
    public static boolean isNotNullOrZeroSize(Collection<? extends Object> c)
    {
        return !isNullOrZeroSize(c);
    }

    /**
     * 判断数字类型是否为null或者0，如果是返回true，否则返回false
     *
     * @param number 被判断的数字
     * @return boolean
     */
    public static boolean isNullOrZero(Number number)
    {
        if (GeneralUtils.isNotNull(number))
        {
            return (number.intValue() != 0) ? false : true;
        }
        return true;
    }

    /**
     * 判断数字类型是否不为null或者0，如果是返回true，否则返回false
     *
     * @param number 被判断的数字
     * @return boolean
     */
    public static boolean isNotNullOrZero(Number number)
    {
        return !isNullOrZero(number);
    }

    /**
     * <保留x位有效数字> <功能详细描述>
     *
     * @param num String
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String retained2SignificantFigures(String num, int x)
    {
        return new BigDecimal(num).setScale(x, RoundingMode.HALF_UP).toString();
    }

    /**
     * 提供精确的小数位四舍五入处理。
     *
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     */
    public static double round(double v, int scale)
    {
        if (scale < 0)
        {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 取整数
     */
    public static String textInt(String money)
    {
        if (isNotNullOrZeroLenght(money))
        {
            if (money.indexOf(".") > 0)
            {
                money = money.replaceAll("0+?$", "");//去掉后面无用的零
                money = money.replaceAll("[.]$", "");//如小数点后面全是零则去掉小数点
            }
            return money;
        }
        else
        {
            return "0";
        }
    }

    /**
     * <减法运算并保留两位有效数字> <功能详细描述>
     *
     * @param subt1 String
     * @param subt2 String
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String subtract(String subt1, String subt2)
    {
        BigDecimal sub1 = new BigDecimal(subt1);
        BigDecimal sub2 = new BigDecimal(subt2);
        BigDecimal result = sub1.subtract(sub2);
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result.toString();
    }

    /**
     * <加法运算并保留两位有效数字> <功能详细描述>
     *
     * @param addend1
     * @param addend2
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String add(String addend1, String addend2)
    {
        BigDecimal add1 = new BigDecimal(addend1);
        BigDecimal add2 = new BigDecimal(addend2);
        BigDecimal result = add1.add(add2);
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result.toString();
    }

    /**
     * <乘法运算并保留两位有效数字> <功能详细描述>
     *
     * @param factor1 String
     * @param factor2 String
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String multiply(String factor1, String factor2)
    {
        BigDecimal fac1 = new BigDecimal(factor1);
        BigDecimal fac2 = new BigDecimal(factor2);
        BigDecimal result = fac1.multiply(fac2);
        result = result.setScale(2, RoundingMode.HALF_UP);
        return result.toString();
    }

    public static String multiplyTen(String factor1, String factor2)
    {
        BigDecimal fac1 = new BigDecimal(factor1);
        BigDecimal fac2 = new BigDecimal(factor2);
        BigDecimal result = fac1.multiply(fac2);
        result = result.setScale(10, RoundingMode.HALF_UP);
        return result.toString();
    }

    /**
     * <除法运算并保留两位有效数字> <功能详细描述>
     *
     * @param divisor1 String
     * @param divisor2 String
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String divide(String divisor1, String divisor2)
    {
        BigDecimal div1 = new BigDecimal(divisor1);
        BigDecimal div2 = new BigDecimal(divisor2);
        BigDecimal result = div1.divide(div2, 2, RoundingMode.HALF_UP);
        return result.toString();
    }

    /**
     * <除法运算并保留一位有效数字> <功能详细描述>
     *
     * @param divisor1 String
     * @param divisor2 String
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String dividePoint1(String divisor1, String divisor2)
    {
        BigDecimal div1 = new BigDecimal(divisor1);
        BigDecimal div2 = new BigDecimal(divisor2);
        BigDecimal result = div1.divide(div2, 1, RoundingMode.HALF_UP);
        return result.toString();
    }

    /**
     * 规范经纬度的长度，8位数字，加上小数点后长度为9位
     */
    public static String standard(String str)
    {
        String return_string = null;
        StringBuffer sb = new StringBuffer(str);
        int length = sb.length();
        if (length < 9)
        {
            for (int i = length; i < 9; i++)
            {
                sb = sb.append("0");
            }
        }
        if (length > 9)
        {
            sb.delete(9, length);
        }
        return_string = sb.toString();
        return return_string;
    }

    /**
     * 获取版本信息
     *
     * @return
     * @throws Exception
     */
    public static String getVersionName(Context context)
    {
        try
        {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            String version = packInfo.versionName;
            return version;
        } catch (NameNotFoundException e)
        {
        }
        return "";
    }

    /**
     * <邮箱判断>
     * <功能详细描述>
     *
     * @param email
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isEmail(String email)
    {
        String str =
                "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    /**
     * <手机号码判断>
     *
     * @param tel
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isTel(String tel)
    {
        String str = "^[1][0-9]{10}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(tel);
        return m.matches();
    }

    /**
     * <邮编判断>
     * <功能详细描述>
     *
     * @param post
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean isPost(String post)
    {
        String patrn = "^[1-9][0-9]{5}$";
        Pattern p = Pattern.compile(patrn);
        Matcher m = p.matcher(post);
        return m.matches();
    }

    /**
     * <密码规则判断>
     * <功能详细描述>
     *
     * @param password
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean IsPassword(String password)
    {
//        String str = "^[A-Za-z0-9]{6,16}$";
        String str = "^(?![0-9]+$)(?![_]+$)(?![a-zA-Z]+$)[0-9A-Za-z_]{6,16}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * <密码位数判断>
     * <功能详细描述>
     *
     * @param password
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean IsPasswordDigit(String password)
    {
        String str = "^[^ ]{6,20}$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(password);
        return m.matches();
    }

    /**
     * <密码位数判断>
     * <功能详细描述>
     *
     * @param certificate
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static boolean Iscertificate(String certificate)
    {
        String str = "[0-9]{17}([0-9]|[xX])";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(certificate);
        return m.matches();
    }



    /**
     * http://stackoverflow.com/questions/3495890/how-can-i-put-a-listview-into-a-scrollview-without-it-collapsing/3495908#3495908
     *
     * @param listView
     */
    public static void setListViewHeightBasedOnChildrenExtend(ListView listView)
    {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null)
        {
            return;
        }
        int desiredWidth = MeasureSpec.makeMeasureSpec(listView.getWidth(), MeasureSpec.AT_MOST);
        int totalHeight = 0;
        View view = null;
        for (int i = 0; i < listAdapter.getCount(); i++)
        {
            view = listAdapter.getView(i, view, listView);
            if (i == 0)
            {
                view.setLayoutParams(new LayoutParams(desiredWidth, LayoutParams.WRAP_CONTENT));
            }
            view.measure(desiredWidth, MeasureSpec.UNSPECIFIED);
            totalHeight += view.getMeasuredHeight();
        }
        LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    // 去除textview的排版问题
    public static String ToDBC(String input)
    {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++)
        {
            if (c[i] == 12288)
            {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
            {
                c[i] = (char) (c[i] - 65248);
            }
        }
        return new String(c);
    }

    public static String ToShortString(String text, int num)
    {
        if (num < 0)
        {
            return text;
        }
        text = ToDBC(text);
        int length = text.length();
        int yu = length / num;
        String myString = "";
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < yu; i++)
        {
            list.add(text.substring(i * num, (i + 1) * num));
        }
        list.add(text.substring(yu * num, text.length()));
        for (int i = 0; i < list.size(); i++)
        {
            if (i == list.size() - 1)
            {
                myString = myString + list.get(i);
            }
            else
            {
                myString = myString + list.get(i) + "\n";
            }
        }
        return myString;
    }

    /**
     * <打电话>
     * <功能详细描述>
     *
     * @param context
     * @see [类、类#方法、类#成员]
     */
    public static void setTel(Context context, String tel)
    {
        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_DIAL);
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + tel));
        context.startActivity(intent);
    }

    /**
     * <处理姓名>
     * <功能详细描述>
     *
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String doUserName(String userName)
    {
        String name = "";
        if (GeneralUtils.isNotNullOrZeroLenght(userName))
        {
            if (userName.length() > 3)
            {
                name = "**" + userName.substring(2, userName.length());
            }
            else
            {
                name = "*" + userName.substring(1, userName.length());
            }

        }
        return name;
    }

    /**
     * <处理手机号>
     * <功能详细描述>
     *
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String doUserPhone(String phone)
    {
        String name = "";
        if (GeneralUtils.isNotNullOrZeroLenght(phone) && phone.length() == 11)
        {
            name = phone.substring(0, 3) + " **** " + phone.substring(phone.length() - 4, phone.length());
        }
        return name;
    }

    /**
     * <处理银行卡>
     */
    public static String doBankCardNumber(String bankCard)
    {
        String number = "";
        if (GeneralUtils.isNotNullOrZeroLenght(bankCard))
        {
            if (bankCard.length() > 4)
            {
                number = "**** **** **** " + bankCard.substring(bankCard.length() - 4, bankCard.length());
            }
            else
            {
                number = "**** **** **** " + bankCard;
            }

        }
        return number;
    }

    /**
     * <处理银行卡>
     */
    public static String doBankCardNumberOther(String bankCard)
    {
        String number = "";
        if (GeneralUtils.isNotNullOrZeroLenght(bankCard))
        {
            if (bankCard.length() > 8)
            {
                number = bankCard.substring(0, 4) + " **** **** " + bankCard.substring(bankCard.length() - 4, bankCard.length());
            }
            else if (bankCard.length() > 4)
            {
                number = "**** **** **** " + bankCard.substring(bankCard.length() - 4, bankCard.length());
            }
            else
            {
                number = "**** **** **** " + bankCard;
            }

        }
        return number;
    }

    /**
     * <参数排序>
     * <功能详细描述>
     *
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static ArrayList<String> doSort(Map<String, String> param)
    {
        Set<String> keySet = param.keySet();
        Iterator<String> iterator = keySet.iterator();
        ArrayList<String> keys = new ArrayList<String>();
        while (iterator.hasNext())
        {
            String key = iterator.next();
            keys.add(key);
        }
        Collections.sort(keys);
        return keys;
    }

    /**
     * 获取当前时间
     */
    public static String splitToLocalDate()
    {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String date = sDateFormat.format(new java.util.Date());
        return date.toString();
    }

    /**
     * <将时间戳 转换为mm:ss> <功能详细描述>
     */
    public static String splitToSecondTime(String str)
    {

        if (isNullOrZeroLenght(str))
        {
            return str;
        }
        double abc = Double.parseDouble(str);
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTimeInMillis(((int) abc) * 1000);
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("mm:ss");
        String time = format.format(gc.getTime());
        return time;
    }

    /**
     * <将毫秒 转换为mm:ss> <功能详细描述>
     */
    public static String splitToSecondTimeMore(String str)
    {

        if (isNullOrZeroLenght(str))
        {
            return str;
        }
        long l = Double.valueOf(str).longValue();
        Date date = new Date(l);
        SimpleDateFormat sf = new SimpleDateFormat("mm:ss");
        String time = sf.format(date);
        return time;
    }

    /**
     * <将YYYY-MM-DD hh:mm:ss转换为 YYYY-MM-DD>
     */
    public static String splitToshort(String str)
    {
        if (isNullOrZeroLenght(str) || str.length() != 19)
        {
            return str;
        }
        return str.substring(0, 10);
    }

    /**
     * 计算两个日期相隔天数
     */
    public static String dateToDays(String startDate, String endDate)
    {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date begin = null;
        Date end = null;
        try
        {
            begin = df.parse(startDate);
            end = df.parse(endDate);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        int day = (int) ((end.getTime() - begin.getTime()) / 1000 / 60 / 60 / 24);
        return day + "";
    }

    /**
     * 计算年化收益率
     */
    public static String summerYearMoney(String money, String days, String percent)
    {
        String textOne = multiply(days, money);
        String textTwo = multiply(percent, textOne);
        String textThree = divide(textTwo, "365");
        return textThree;
    }

    /**
     * 字符串的日期格式的计算
     * smdate 较小的时间
     * bdate  较大的时间
     */
    public static String daysBetween(String smdate, String bdate)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        long time1 = 0;
        long time2 = 0;
        try
        {
            cal.setTime(sdf.parse(smdate));
            time1 = cal.getTimeInMillis();
            cal.setTime(sdf.parse(bdate));
            time2 = cal.getTimeInMillis();
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        long between_days = (time2 - time1) / (1000 * 3600 * 24);
        return String.valueOf(between_days + 1);
    }

    /**
     * 百分比或千分比显示
     */
    public static String doPercent(String number)
    {
        String percent = "0";

        if (isNotNullOrZeroLenght(number))
        {
            if (Double.valueOf(multiply(number, "100")) >= 1)
            {
                percent = textInt(multiply(number, "100")) + "%";
            }
            else
            {
                percent = textInt(multiply(number, "1000")) + "‰";
            }
        }

        return percent;
    }

    /**
     * 月份间隔 YYYY-MM-DD hh:mm:ss
     */
    public static int differenceMonth(String start, String end)
    {
        if (isNullOrZeroLenght(start) || start.length() != 19)
        {
            return 0;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date d = null;
        Date d1 = null;
        try
        {
            d = sdf.parse(start);
            d1 = sdf.parse(end);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        c.setTime(d1);
        int year1 = c.get(Calendar.YEAR);
        int month1 = c.get(Calendar.MONTH);
        int result;
        if (year == year1)
        {
            result = month1 - month;//两个日期相差几个月，即月份差
        }
        else
        {
            result = 12 * (year1 - year) + month1 - month;//两个日期相差几个月，即月份差
        }
        return result == 0 ? 1 : Math.abs(result);
    }

    /**
     * 显示日期的下个月 YYYY-MM-DD
     */
    public static String showNextMonth(String mDate, int amount)
    {
        if (isNullOrZeroLenght(mDate) || mDate.length() != 10)
        {
            return mDate;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try
        {
            date = df.parse(mDate);
        } catch (ParseException e)
        {
            e.printStackTrace();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, amount);
        return df.format(calendar.getTime());
    }

    /**
     * <加法运算并保留三位位有效数字> <功能详细描述>
     */
    public static String addThree(String addend1, String addend2)
    {
        BigDecimal add1 = new BigDecimal(addend1);
        BigDecimal add2 = new BigDecimal(addend2);
        BigDecimal result = add1.add(add2);
        result = result.setScale(3, RoundingMode.HALF_UP);
        return result.toString();
    }

    /**
     * 计算每期天数数组
     */
    public static ArrayList<String> daysOfMonth(String startTime, String endTime)
    {
        ArrayList<String> days = new ArrayList<>();
        int diff = GeneralUtils.differenceMonth(startTime, endTime);
        String sTime = "";//开始日期
        String eTime;//下月日期
        for (int i = 0; i < diff; i++)
        {
            if (i == 0)
            {
                sTime = GeneralUtils.splitToshort(startTime);
            }
            eTime = showNextMonth(sTime, 1);
            days.add(dateToDays(sTime, eTime));
            sTime = eTime;
        }
        return days;
    }

    //如果字符串是科学：如123213e21，转化为字符串
    public static String getStringOutE(String str)
    {
        BigDecimal bd = new BigDecimal(str);
        return bd.toPlainString();
    }

    //两位有效数字
    public static String getCutTwo(double finalMoney)
    {
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(2);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
        String moneyMore = formater.format(finalMoney);
        return moneyMore;
    }

    /**
     * 数字显示设置，
     * 小数点后最多两位
     * 最少保留一位小数
     */
    public static String getDataIfWithZero(String dataStr)
    {
        DecimalFormat df2 = new DecimalFormat("#.##");
        String back = "";
        if (GeneralUtils.isNotNullOrZeroLenght(dataStr))
        {
            try
            {
                double dataFloat = Double.parseDouble(dataStr);
                back = df2.format(dataFloat) + "";
                return back;
            } catch (NumberFormatException e)
            {
                e.printStackTrace();
                return "";
            }
        }
        return back;
    }

    /**
     * 修改textview文字颜色
     */
    public static void setTextViewColor(TextView textView)
    {
        String text = textView.getText().toString().trim();
        String myText = "";
        for (int i = 0; i < text.length(); i++)
        {
            String str = text.substring(i, i + 1);
            if (isNumeric(str))
            {
                myText = myText + "<font color=\"#e1524b\">" + str + "</font>";
            }
            else
            {
                myText = myText + "<font color=\"#777777\">" + str + "</font>";
            }
        }
        textView.setText(Html.fromHtml(myText));
    }

    /**
     * 判断文字是否为数字
     */
    public static boolean isNumeric(String str)
    {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 计算两个日期相差的天数，如果date2 > date1 返回正数，否则返回负数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long dayDiff(Date date1, Date date2)
    {
        return (date2.getTime() - date1.getTime()) / 86400000;
    }

    /**
     * 把符合日期格式的字符串转换为日期类型
     *
     * @param dateStr
     * @return
     */
    public static java.util.Date stringtoDate(String dateStr)
    {
        Date d = null;
        SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try
        {
            formater.setLenient(false);
            d = formater.parse(dateStr);
        } catch (Exception e)
        {
            // log.error(e);
            d = null;
        }
        return d;
    }

    public static int dayDiffString(String str1, String str2)
    {
        String[] one = str1.split(" ");
        String[] two = str2.split(" ");
        String oneMore = one[0] + " " + "00:00:00";
        String twoMore = two[0] + " " + "00:00:00";
        Date date1 = stringtoDate(oneMore);
        Date date2 = stringtoDate(twoMore);
        long my = dayDiff(date1, date2);
        int day = new Long(my).intValue();
        return day;
    }

    /**
     * <将YYYY-MM-DD HH:mm:ss 转换为HH:mm> <功能详细描述> <功能详细描述>
     *
     * @param time
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String hourMinute(String time)
    {
        if (GeneralUtils.isNullOrZeroLenght(time))
        {
            return time;
        }
        String[] arr1 = time.split(" ");
        String[] arr2 = arr1[1].split(":");
        String hm = arr2[0] + ":" + arr2[1];
        return hm;
    }

    /**
     * <将YYYY-MM-DD HH:mm:ss 转换为MM-DD> <功能详细描述> <功能详细描述>
     *
     * @param time
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String monthDay(String time)
    {
        if (GeneralUtils.isNullOrZeroLenght(time))
        {
            return time;
        }
        String[] arr1 = time.split(" ");
        String[] arr2 = arr1[0].split("-");
        String hm = arr2[1] + "-" + arr2[2];
        return hm;
    }

    /**
     * 保留两位有效数字,不四舍五入
     */
    public static String twoNumberEnd(Double money)
    {
        DecimalFormat formater = new DecimalFormat();
        formater.setMaximumFractionDigits(2);
        formater.setGroupingSize(0);
        formater.setRoundingMode(RoundingMode.FLOOR);
        return formater.format(money);
    }

    /**
     * double 求余
     */
    public static double twoNumberLast(String moneyOne, String moneyTwo)
    {
        double val = Double.valueOf(moneyOne) % Double.valueOf(moneyTwo);
        BigDecimal bd = new BigDecimal(val);
        val = bd.setScale(2, BigDecimal.ROUND_DOWN).doubleValue();
        return val;
    }

    public static File saveFile(Bitmap bm, String fileName, String path)
    {

        File myCaptureFile = null;
        try
        {
            String subForder = path;
            File foder = new File(subForder);
            if (!foder.exists())
            {
                foder.mkdirs();
            }
            myCaptureFile = new File(subForder, fileName);
            if (!myCaptureFile.exists())
            {
                myCaptureFile.createNewFile();
            }
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e)
        {

            e.printStackTrace();
        }

        return myCaptureFile;

    }


}