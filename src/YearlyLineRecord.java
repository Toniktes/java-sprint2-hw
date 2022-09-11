public class YearlyLineRecord {//переменные данных для ArrayList(отчет по годам)
    private int month;
    private int amount;
    private boolean is_expense;

    public YearlyLineRecord(int month, int amount, boolean is_expense) {
        this.month = month;
        this.amount = amount;
        this.is_expense = is_expense;
    }

    @Override
    public String toString() {//переопределяем, иначе выводит ссылки
        return this.month + " " + this.amount + " " + this.is_expense;
    }
}

