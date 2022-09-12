import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        YearReport report = null;
        String year = null;// null оставил, если сразу сделать new то при неправильном адресе отчета, начинает при запуске выдавать, что невозможно прочитать файл, а мне нужно только при вводе команды
        MonthlyReport reports = null;
        while (true) {
            printMenu();
            try {
                int userInput = Integer.parseInt(scanner.nextLine());
                switch (userInput) {
                    case 1:
                        reports = new MonthlyReport("resources/m.20210");
                        System.out.println("Отчет по месяцам считан");
                        break;
                    case 2:
                        System.out.println("Введите год за который хотите получить отчет:");
                        year = scanner.nextLine();
                        report = new YearReport("resources/y." + year + ".csv");
                        break;
                    case 3:
                        if (report != null && reports != null) {
                            for (int i = 1; i < reports.months.size(); i++) {
                                double incomeYear = report.sumIncomeYear(i);
                                double incomeMonth = reports.sumIncomeMonth(i);
                                double expenseYear = report.sumExpenseYear(i);
                                double expenseMonth = reports.sumExpenseMonth(i);
                                if (incomeYear != incomeMonth || expenseYear != expenseMonth) {
                                    System.out.println("В " + i + " отчеты не сходятся");
                                    break;
                                }
                            }
                            System.out.println("Все в порядке, годовой и месячные отчеты сходятся");
                        } else {
                            System.out.println("Сначала считайте отчеты за год и месяцы");
                        }
                        break;
                    case 4:
                        if (reports != null) {
                            System.out.println("Информация о всех месячных отчетах:");
                            reports.dataOfMonths();
                        } else {
                            System.out.println("Сначала считайте отчет за месяцы");
                        }
                        break;
                    case 5:
                        if (report != null) {
                            System.out.println("Информация о годовом отчете:");
                            System.out.println("Год: " + year);
                            report.dataForYear();
                            break;
                        } else {
                            System.out.println("Сначала считайте отчет за год");
                            break;
                        }
                    case 0:
                        return;
                    default:
                        System.out.println("Такой команды нет, попробуйте снова");
                }
            } catch (NumberFormatException e) {
                System.out.println("Это не число(");
            }
        }
    }

    private static void printMenu() {
        System.out.println("Что вы хотите сделать? (введите число)");
        System.out.println("1 - Считать все месячные отчёты");
        System.out.println("2 - Считать годовой отчёт");
        System.out.println("3 - Сверить отчёты");
        System.out.println("4 - Вывести информацию о всех месячных отчётах");
        System.out.println("5 - Вывести информацию о годовом отчёте");
        System.out.println("0 - Завершить программу");
    }
}

