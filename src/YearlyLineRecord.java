public class YearlyLineRecord {//переменные данных для ArrayList(отчет по годам)
    private int month;
    private int amount;
    private boolean expense;

    public YearlyLineRecord(int month, int amount, boolean expense) {
        this.month = month;
        this.amount = amount;
        this.expense = expense;
    }

    @Override
    public String toString() {//переопределяем, иначе выводит ссылки
        return this.month + " " + this.amount + " " + this.expense;
    }
}

