package automation.labb;

public class Person {
    double length;
    double weight;

    public Person(double length, double weight){
        this.length = length;
        this.weight = weight;
    }

     public double getLength() {
        return length;
    }

    public double getWeight() {
        return weight;
    }

    public double getBMI(double length, double weight){
        return getWeight()/(getLength()*getLength());
    }
    
    
}
