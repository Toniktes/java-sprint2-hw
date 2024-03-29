public class MonthlyRecord {//переменные данных для ArrayList(отчеты по месяцам)
    private String item_name;
    private boolean expense;
    private int quantity;
    private double sum_of_one;

    public MonthlyRecord(String item_name, boolean expense, int quantity, double sum_of_one) {
        this.item_name = item_name;
        this.expense = expense;
        this.quantity = quantity;
        this.sum_of_one = sum_of_one;
    }

    @Override
    public String toString() {//переопределям, иначе на выводе ссылки
        return this.item_name + " " + this.expense + " " + this.quantity + " " + this.sum_of_one;
    }
}
