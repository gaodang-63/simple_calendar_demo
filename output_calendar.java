import java.util.Calendar;

public class output_calendar {
    public static void main(String[] args) {

        // options 处理
        for (String arg : args) {
            if (arg.equals("--help") || arg.equals("-h")) {
                info_help();
                return;
            } else if (arg.equals("--version") || arg.equals("-v")) {
                info_version();
                return;
            } else if (arg.indexOf('-') >= 0) {
                unknown_arg(arg);
                return;
            }
        }

        // year month day 处理
        int year, month = 1, day = 1;
        int detail_level = 0; // 1 年视图 2 月视图 3 天视图 0 天视图并且输出当天日期
        int argc = args.length;
        // calendar 生成
        Calendar cal = Calendar.getInstance();
        switch (argc) {
            default:
                System.out.println("Arguments parse error");
                System.out.println("Possible causes: Parameter mismatch");
                System.out.println("`java output_calendar --help` may be helpful to you");
                return;
            case 3:
                day = Integer.parseInt(args[2]);
                if (day <= 0 || day > 31) {
                    System.out.println("day error:\t\033[31m" + args[2] + "\033[0m");
                    return;
                }
                detail_level++;
            case 2:
                month = Integer.parseInt(args[1]);
                if (month <= 0 || month > 12) {
                    System.out.println("month error:\t\033[31m" + args[1] + "\033[0m");
                    return;
                }
                detail_level++;
            case 1:
                year = Integer.parseInt(args[0]);
                /* 月份从0开始数 如：1月是 0，12月是 11 */
                cal.set(year, month - 1, day);
                detail_level++;
            case 0:
        }

        // 根据 detail_level 输出日历
        switch (detail_level) {
            case 3 -> print_day(cal);
            case 2 -> print_month(cal);
            case 1 -> print_year(cal);
            case 0 -> {
                System.out.printf("%9d%3d   week%2d\n", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.WEEK_OF_MONTH));
                print_day(cal);
            }
        }
    }

    public static void print_year(Calendar cal) {
        String[] month_name = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        for (int i = 1; i <= 12; i++) {
            if (i != 1) {
                System.out.println();
            }
            cal.set(Calendar.MONTH, i - 1);
            System.out.printf("\033[1m%s\033[0m\n", month_name[cal.get(Calendar.MONTH)]);
            print_month(cal);
        }
    }

    public static void print_month(Calendar cal) {
        System.out.println("Mon Tue Wed Thu Fri Sat Sun");
        int month_have_day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
//        int month_have_week = cal.getActualMaximum(Calendar.WEEK_OF_MONTH);
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
        /* 由于 Calendar.Sunday 是 1，而输出的一周的第一天是星期一 */
        day_of_week = --day_of_week == 0 ? 7 : day_of_week;
        for (int i = 1; i < day_of_week; i++) {
            System.out.print("    ");
        }
        for (int i = 1; i <= month_have_day; i++) {
            if (day_of_week % 7 == 1 && i != 1) {
                System.out.println();
            }
            System.out.printf("%3d ", i);
            day_of_week++;
        }
        System.out.println();
    }

    public static void print_day(Calendar cal) {
        System.out.println("Mon Tue Wed Thu Fri Sat Sun");
        int today = cal.get(Calendar.DAY_OF_MONTH);
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
        /* 由于 Calendar.Sunday 是 1，而输出的一周的第一天是星期一 */
        day_of_week = --day_of_week == 0 ? 7 : day_of_week;
        String line = line_week_day(today, day_of_week, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        System.out.println(line);
    }

    public static String line_week_day(int today, int day_of_week, int month_days) {
        String ret = "";
        for (int i = 1; i < day_of_week; i++) {
            int day = today - (day_of_week - i);
            if (day <= 0) {
                ret = ret.concat("    ");
            } else {
                ret = ret.concat(String.format("%3d ", day));
            }
        }
        ret += "\033[1;32m" + String.format("%3d ", today) + "\033[0m";
        for (int i = day_of_week + 1; i <= 7; i++) {
            int day = today + (i - day_of_week);
            if (day > month_days) {
                ret = ret.concat("    ");
            } else {
                ret = ret.concat(String.format("%3d ", day));
            }
        }
        return ret;
    }

    public static void info_help() {
        System.out.println("""
                Usage: java output_calendar [year] [month] [day] [options]
                                        
                The first number will be matched as a year, the second number as a month, and the third number as a day
                Based on parameters output calendar with different levels of detail
                If no arguments, output today's calendar
                                        
                Options:
                    --help     -h     This information
                    --version  -v     Version information
                               
                Repositories: <https://github.com/gaodang-63/simple_calendar_demo/>
                        """);
    }

    public static void info_version() {
        System.out.println("output_calendar 0.0.1");
    }

    public static void unknown_arg(String arg) {
        System.out.println("Unknown argument:\t" + "\033[31m" + arg + "\033[0m");
    }
}