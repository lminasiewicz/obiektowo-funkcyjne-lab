package lab3;

import java.util.ArrayList;

public class GradeList {
    private ArrayList<Float> grades;

    public GradeList() {
        this.grades = new ArrayList<Float>();
    }

    public void addGrade(Float grade) {
        this.grades.add(grade);
    }

    public float calculateAverage() {
        if (this.grades.isEmpty()) {
            return 0;
        }
        float sum = 0;
        for (Float grade : this.grades) {
            sum += grade;
        }
        return sum / this.grades.size();
    }

    public float findMax() {
        if (this.grades.isEmpty()) {
            return 0;
        }
        float largest = this.grades.get(0);
        for (Float grade : this.grades) {
            if (grade > largest) {
                largest = grade;
            }
        }
        return largest;
    }

    public float findMin() {
        if (this.grades.isEmpty()) {
            return 0;
        }
        float smallest = this.grades.get(0);
        for (Float grade : this.grades) {
            if (grade < smallest) {
                smallest = grade;
            }
        }
        return smallest;
    }

    public boolean isEmpty() {
        return this.grades.isEmpty();
    }
}
