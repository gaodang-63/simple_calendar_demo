# 程序使用说明

## 使用帮助

```
Usage: java simple_calendar_demo [year, [month, [day]]] [options]

    The first number will be matched as a year,
    the second number as a month,
    and the third number as a day.

    Based on parameters output calendar with different levels of detail.
    If no arguments, output today's calendar.

Options:
--help -h This information
--version -v Version information

Repositories: <https://github.com/gaodang-63/simple_calendar_demo/>
```

## 使用示例

输出今天的日历

```
> java simple_calendar_demo
     2023  5   week 5
Mon Tue Wed Thu Fri Sat Sun
 22  23  24  25  26  27 ·28
```

输出指定天的日历

```
> java simple_calendar_demo 2020 1 1
Mon Tue Wed Thu Fri Sat Sun
        · 1   2   3   4   5
```

输出指定月的日历表

```
> java simple_calendar_demo 2020 1
Mon Tue Wed Thu Fri Sat Sun
          1   2   3   4   5
  6   7   8   9  10  11  12
 13  14  15  16  17  18  19
 20  21  22  23  24  25  26
 27  28  29  30  31
```

输出指定年的日历表

```
> java simple_calendar_demo 2020
· January
Mon Tue Wed Thu Fri Sat Sun
          1   2   3   4   5
  6   7   8   9  10  11  12
 13  14  15  16  17  18  19
 20  21  22  23  24  25  26
 27  28  29  30  31

· February
Mon Tue Wed Thu Fri Sat Sun
                      1   2
  3   4   5   6   7   8   9
... 此处省略部分月份

· December
Mon Tue Wed Thu Fri Sat Sun
      1   2   3   4   5   6
  7   8   9  10  11  12  13
 14  15  16  17  18  19  20
 21  22  23  24  25  26  27
 28  29  30  31
```

获取当前版本

```
> java simple_calendar_demo -v
simple_calendar_demo 0.2
```

获取帮助信息

```
> java simple_calendar_demo --help
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
```

# 程序流程

## 程序主干流程

```
    ┌─────────────┐
    │ option 处理 │      解析 help version 参数
    └──────┬──────┘
           │
           ▼
   ┌────────────────┐
   │ parameter 处理 │    解析 year month day 参数
   └───────┬────────┘
           │
           ▼
   ┌────────────────┐
   │ 日历表文本输出 │    输出文本格式的日历表
   └────────────────┘
```

## 程序类图

```
   ┌─────────────────────────────────────────┐
   │            simple_calendar_demo         │
   ├─────────────────────┬───────────────────┤
   │┌─────────────────┐  │ options           │
   ││ simple_calendar │  │ opt参数处理       │
   │├─────────────────┤  │                   │
   ││   +display()    │  │ parameter         │
   │└─────────────────┘  │ param参数解析     │
   │┌─────────────┐      │                   │
   ││ Information │      │ display           │
   ││─────────────│      │ 完成日历展示      │
   ││   help()    │      │                   │
   ││  version()  │      │                   │
   ││   usage()   │      │                   │
   │└─────────────┘      │                   │
   └─────────────────────┴───────────────────┘
```
