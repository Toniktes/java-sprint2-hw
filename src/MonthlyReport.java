import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MonthlyReport {
    public Map<Integer, ArrayList<MonthlyRecord>> records = new HashMap<>();
    Map<Integer, String> months = new HashMap<>();
    public Map<Integer, Double> monthIncome = new HashMap<>();
    public Map<Integer, Double> monthExpense = new HashMap<>();
    public Map<Integer, ArrayList<String>> forStat = new HashMap<>();

    public MonthlyReport(String path) {
        for (int i = 1; i < 4; i++) {
            ArrayList<MonthlyRecord> list = new ArrayList<>();
            String data = readFileContentsOrNull(path + i + ".csv");//Передаем значения из файла в строку
            String[] lines = data.split("\r?\n");//делим строку по разделителю
            for (int j = 1; j < lines.length; j++) {
                String line = lines[j];//циклом передаем значения построчно "Новогодняя ёлка,TRUE,1,100000"
                String[] parts = line.split(",");//теперь делим эти строки на значения и получаем массив["Новогодняя ёлка", "TRUE", "1" , "100000"]
                String item_name = parts[0];//парсим в переменные
                boolean is_expense = Boolean.parseBoolean(parts[1]);
                int quantity = Integer.parseInt(parts[2]);
                double sum_of_one = Double.parseDouble(parts[3]);
                MonthlyRecord record = new MonthlyRecord(item_name, is_expense, quantity, sum_of_one);
                list.add(record);//передаем значения в лист
            }
            records.put(i, list);//добавляем в мапу лист
        }
        for (Integer key : records.keySet()) {//тут приводим отчет к нужному для сплита виду т.к в месячных отчетах есть пробелы в именах товаров
            ArrayList<String> lines = new ArrayList<>();
            String result = null;
            double sumExpense = 0;
            double sumIncome = 0;
            for (int i = 0; i < records.get(key).size(); i++) {
                String firstLine = records.get(key).get(i).toString();
                try {
                    int index = firstLine.indexOf(" true");//для расходов
                    String last = firstLine.substring(index);
                    last = last.replace(" ", ",");
                    String first = firstLine.substring(0, index);
                    result = first + last;
                } catch (StringIndexOutOfBoundsException e) {//для доходов
                    int index = firstLine.indexOf(" false");
                    String last = firstLine.substring(index);
                    last = last.replace(" ", ",");
                    String first = firstLine.substring(0, index);
                    result = first + last;
                }
                lines.add(result);//теперь строку в нужном виде добавляем в лист
                String[] array = result.split(",");//далее считаем траты и расходы, это понадобится для методов, которые будем вызывать при сравнении отчетов
                if (array[1].equalsIgnoreCase("TRUE")) {
                    double expense = Double.parseDouble(array[2]) * Double.parseDouble(array[3]);
                    sumExpense += expense;
                } else {
                    double profit = Double.parseDouble(array[2]) * Double.parseDouble(array[3]);
                    sumIncome += profit;
                }
            }
            monthIncome.put(key, sumIncome);//добавляем доходы в мапу, будем использовать ее для сравнения отчетов
            monthExpense.put(key, sumExpense);//добавляем расходы в мапу
            forStat.put(key, lines);//отправляем лист с нужным видом строк в мапу(работать с ним будем уже в методе на подсчет показателей)
        }
    }

    private void addMonths() {//метод для добавления месяцев в мапу, если месячных отчетов станет больше, тут можно будет добавить еще месяцев
        months.put(1, "Январь:");
        months.put(2, "Февраль:");
        months.put(3, "Март:");
    }

    void dataOfMonths() {//метод для информации о всех месячных отчетах
        addMonths();//добавляем месяцы
        for (Integer key : forStat.keySet()) {
            String nameProfit = null;
            String nameExpense = null;
            double max = 0;
            double min = 0;
            for (int i = 0; i < forStat.get(key).size(); i++) {//поиск масимума прибыли и максимума трат за каждый месяц
                ArrayList<String> arrayList = forStat.get(key);//получаем лист одного месяца
                String line = arrayList.get(i);//получаем одну строку месяца
                String[] array = line.split(",");// разбиваем строку
                if (array[1].equalsIgnoreCase("TRUE")) {
                    double expense = Double.parseDouble(array[2]) * Double.parseDouble(array[3]);//считаем трату и ищем максимум
                    if (expense > min) {
                        min = expense;
                        nameExpense = array[0];
                    }
                } else {
                    double profit = Double.parseDouble(array[2]) * Double.parseDouble(array[3]);//считаем доход и ищем максимум
                    if (profit > max) {
                        max = profit;
                        nameProfit = array[0];
                    }
                }
            }
            System.out.println(months.get(key));
            System.out.println("Самый прибыльный товар: " + nameProfit + " на сумму: " + max);
            System.out.println("Самая большай трата на товар: " + nameExpense + " на сумму: " + min);
        }
    }

    double sumIncomeMonth(int month) {//метод для сравнения доходов в отчетах
        return monthIncome.get(month);
    }

    double sumExpenseMonth(int month) {//метода для сравнения расходов в отчетах
        return monthExpense.get(month);
    }

    private String readFileContentsOrNull(String path) {//метод считывания отчетов из файла(год)
        String stream = null;
        try {
            stream = stream + Files.readString(Path.of(path));
            return stream;
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории или нет отчета за выбранный год");
            return null;
        }
    }
}
