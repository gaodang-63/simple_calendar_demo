import java.util.Calendar;

class Information {
  static void help() {
    System.out.print("""
        Usage: java simple_calendar_demo [year, [month, [day]]] [options]

            The first number will be matched as a year,
            the second number as a month,
            and the third number as a day.

            Based on parameters output calendar with different levels of detail.
            If no arguments, output today's calendar.

        Options:
            --help     -h     This information
            --version  -v     Version information

        Repositories: <https://github.com/gaodang-63/simple_calendar_demo/>
        """);
  }

  static void version() {
    System.out.print("simple_calendar_demo 0.2");
  }

  static void usage() {
    System.out.print("""
        Usage: java simple_calendar_demo [year, [month, [day]]] [options]
        To see more detail use 'java simple_calendar_demo --help'
        """);
  }
}

class simple_calendar {
  private Calendar cal = Calendar.getInstance();

  private enum detail_level {
    YEAR,
    MONTH,
    DAY,
    TODAY,
  }

  private detail_level level;

  public simple_calendar() {
    level = detail_level.TODAY;
  }

  public simple_calendar(int year) {
    level = detail_level.YEAR;
    cal.set(year, 0, 1);
  }

  public simple_calendar(int year, int month) {
    level = detail_level.MONTH;
    cal.set(year, month - 1, 1);
  }

  public simple_calendar(int year, int month, int day) {
    level = detail_level.DAY;
    cal.set(year, month - 1, day);
  }

  public void display() {
    switch (level) {
      case YEAR -> print_year(cal);
      case MONTH -> print_month(cal);
      case DAY -> print_day(cal);
      case TODAY -> print_today(cal);
    }
  }

  private static void print_today(Calendar cal) {
    System.out.printf("%9d%3d   week%2d\n", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1,
        cal.get(Calendar.WEEK_OF_MONTH));
    print_day(cal);
  }

  private static void print_year(Calendar cal) {
    String[] month_name = { "January", "February", "March", "April", "May", "June", "July", "August", "September",
        "October", "November", "December" };
    for (int i = 1; i <= 12; i++) {
      if (i != 1) {
        System.out.println();
      }
      cal.set(Calendar.MONTH, i - 1);
      System.out.printf("· %s\n", month_name[cal.get(Calendar.MONTH)]);
      print_month(cal);
    }
  }

  private static void print_month(Calendar cal) {
    System.out.println("Mon Tue Wed Thu Fri Sat Sun");
    int month_have_day = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
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

  private static void print_day(Calendar cal) {
    System.out.println("Mon Tue Wed Thu Fri Sat Sun");
    int today = cal.get(Calendar.DAY_OF_MONTH);
    int day_of_week = cal.get(Calendar.DAY_OF_WEEK);
    day_of_week = --day_of_week == 0 ? 7 : day_of_week;
    String line = line_week_day(today, day_of_week, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    System.out.println(line);
  }

  private static String line_week_day(int today, int day_of_week, int month_days) {
    String ret = "";
    for (int i = 1; i < day_of_week; i++) {
      int day = today - (day_of_week - i);
      if (day <= 0) {
        ret = ret.concat("    ");
      } else {
        ret = ret.concat(String.format("%3d ", day));
      }
    }
    ret += String.format("·%2d ", today);
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
}

public class simple_calendar_demo {
  private static simple_calendar calendar;

  private static void options_handle(String[] args) {
    for (String arg : args) {
      if (arg.equals("--help") || arg.equals("-h")) {
        Information.help();
        System.exit(0);
      } else if (arg.equals("--version") || arg.equals("-v")) {
        Information.version();
        System.exit(0);
      } else if (arg.indexOf('-') >= 0) {
        System.out.println("Unknown arguments:    " + arg);
        Information.usage();
        System.exit(1);
      }
    }
  }

  private static int str_to_int(String str) {
    int number = 0;
    try {
      number = Integer.parseInt(str);
    } catch (NumberFormatException a) {
      System.out.printf("%s can't prase to int%n", str);
      System.out.println(a);
      System.exit(1);
    }
    return number;
  }

  private static int str_to_int(String str, int min, int max) {
    int number = 0;
    try {
      number = Integer.parseInt(str);
    } catch (NumberFormatException a) {
      System.out.printf("%s can't prase to int%n", str);
      System.out.println(a);
      System.exit(1);
    }
    if (number < min) {
      System.out.println("unqualified  " + str + " is less than " + min);
      System.exit(1);
    }
    if (number > max) {
      System.out.println("unqualified  " + str + " is more than " + max);
      System.exit(1);
    }
    return number;
  }

  private static void parameter_parsing(String[] args) {
    switch (args.length) {
      case 0:
        calendar = new simple_calendar();
        break;
      case 1:
        calendar = new simple_calendar(str_to_int(args[0]));
        break;
      case 2:
        calendar = new simple_calendar(str_to_int(args[0]), str_to_int(args[1], 1, 12));
        break;
      case 3:
        calendar = new simple_calendar(str_to_int(args[0]), str_to_int(args[1], 1, 12), str_to_int(args[2], 1, 31));
        break;
      default:
        System.out.println("Arguments parse error");
        System.out.println("Possible causes: Parameter mismatch");
        Information.usage();
        System.exit(1);
    }
  }

  public static void main(String[] args) {
    options_handle(args);
    parameter_parsing(args);
    calendar.display();
  }
}
