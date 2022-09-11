import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class YearReport {
    public ArrayList<YearlyLineRecord> records = new ArrayList<>();//создаем лист для годового отчета
    public Map<Integer, Double> yearIncome = new HashMap<>();//мапа для сравнения(доходы)
    public Map<Integer, Double> yearExpense = new HashMap<>();//мапа для сравнения(расходы)

    public YearReport(String path) {//конструктор
        String data = readFileContentsOrNull(path);//Передаем значения из файла в строку
        String[] lines = data.split("\r?\n");//делим строку по разделителю
        for (int i = 1; i < lines.length; i++) {
            String line = lines[i];//циклом передаем значения построчно "01,1500,true"
            String[] parts = line.split(",");//теперь делим эти строки на значения и получаем массив["01" ,"1500", "true"]
            int month = Integer.parseInt(parts[0]);//парсим в переменные
            int amount = Integer.parseInt(parts[1]);
            boolean is_expense = Boolean.parseBoolean(parts[2]);
            YearlyLineRecord record = new YearlyLineRecord(month, amount, is_expense);
            records.add(record);//и передаем этот лист в классовый лист
        }
        int j = 1;
        for (int i = 0; i < records.size(); i = i + 2) {//далее считаем доходы и расходы, пойдут в мапы для сравнений
            double income = 0;
            double expense = 0;
            String firstLine = records.get(i).toString();//первая строка месяца
            String secondLine = records.get(i + 1).toString();//вторая строка месяца
            String[] array = firstLine.split(" ");//Строки разбитые на массив
            String[] array2 = secondLine.split(" ");
            if (array[2].equals("true")) {//расходы для первой строки месяца
                expense += Double.parseDouble(array[1]);
            } else {
                income += Double.parseDouble(array[1]);//доходы для первой строки месяца
            }
            if (array2[2].equals("true")) {//расходы для второй строки месяца
                expense += Double.parseDouble(array2[1]);
            } else {
                income += Double.parseDouble(array2[1]);//доходы для второй строки месяца
            }
            yearIncome.put(j, income);//добавлем доходы в мапу
            yearExpense.put(j, expense);//добавляем расходы в мапу
            j++;
        }
    }

    void dataForYear() {//метод для информации о годовом отчете
        double averegeIncome = 0;
        double averegeConsumption = 0;
        for (int i = 0; i < records.size(); i = i + 2) {
            double profit = 0;
            String firstLine = records.get(i).toString();//первая строка месяца
            String secondLine = records.get(i + 1).toString();//вторая строка месяца
            String[] array = firstLine.split(" ");//Строки разбитые на массив
            String[] array2 = secondLine.split(" ");
            if (array[2].equals("true")) {//Подсчет прибыли, срднего дохода и расхода
                profit -= Double.parseDouble(array[1]);
                averegeConsumption += Double.parseDouble(array[1]);
            } else {
                profit += Double.parseDouble(array[1]);
                averegeIncome += Double.parseDouble(array[1]);
            }
            if (array2[2].equals("true")) {
                profit -= Double.parseDouble(array2[1]);
                averegeConsumption += Double.parseDouble(array2[1]);
            } else {
                profit += Double.parseDouble(array2[1]);
                averegeIncome += Double.parseDouble(array2[1]);
            }
            System.out.println("Прибыль за " + array[0] + " месяц: " + profit);
        }
        System.out.println("Средний доход за все месяцы в году: " + averegeIncome / (records.size() / 2));
        System.out.println("Средний расход за все месяцы в году: " + averegeConsumption / (records.size() / 2));
    }

    double sumIncomeYear(int month) {//метод для сравнения доходов в отчетах
        return yearIncome.get(month);
    }

    double sumExpenseYear(int month) {//метода для сравнения расходов в отчетах
        return yearExpense.get(month);
    }


    private String readFileContentsOrNull(String path) {//метод считывания отчета из файла
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            System.out.println("Невозможно прочитать файл с месячным отчётом. Возможно, файл не находится в нужной директории или нет отчета за выбранный год");
            return null;
        }
    }

}
